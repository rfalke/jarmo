import collections
import os
import re

BASE_DIR = "src/main/java/holdec/jarmo/arm64"
INPUT_FILE = BASE_DIR + "/a64.asm-patterns"

verbose = False


def toHex(binStr):
    v = 0
    for c in binStr:
        v *= 2
        if c == '1':
            v += 1
        else:
            assert c == "0"
    return "0x%04x" % v


allKnown = []


def mysplit(str):
    current = ""
    inside = 0
    res = []
    for i in str:
        if i == "{" or i == "[":
            inside += 1
        elif i == "}" or i == "]":
            inside -= 1
        if i == "," and inside == 0:
            res.append(current)
            current = ""
            continue
        current += i
    assert inside == 0, [str, inside, current, res]
    res.append(current)
    if (len(res) > 2 and
            (res[-1] in ["#<imm>", "#<imm2>", "#<simm>", "<imm>", "<imm-f1>", "<imm-f2>", "<imm-f3>", "<imm-f4>"]) and
            res[-2].startswith("[") and res[-2].endswith("]")):
        a = res[-1]
        b = res[-2]
        del res[-1]
        del res[-1]
        res.append(b + "," + a)
    return res


def is_simd(word):
    for i in "BHSDQ":
        if "<" + i + "t>" in word:
            return True
    return False


def toJavaBool(bool):
    if bool:
        return "true"
    return "false"


def matches(input, words):
    if " " not in input:
        return input == words[0]
    opc, rest = input.split(" ", 1)
    words2 = [opc] + mysplit(rest)
    return words == words2


def doOneLine(line):
    left = line.left
    mask = line.mask
    value = line.value
    regions = line.regions
    nameToRegion = {}
    for i in regions:
        nameToRegion[i.name] = i
    if verbose:
        print("==", left)
        # print("  mask and value:", mask, value)
        print("  regions:", regions)

    def combine(mask, value):
        r = ""
        for i in range(32):
            if mask[i] == "0":
                r += "x"
            else:
                r += value[i]
            if (i + 1) % 8 == 0:
                r += " "
        return r.strip()

    def write_get_raw(name, start, size):
        if searchRegion(name, size, True) is None:
            out.write(indent + "    final int %s = getPart(opcode32Bit, %d, %d); // raw\n" % (name, start, size))

    def has_regions(**kwargs):
        for key in kwargs.keys():
            if key not in nameToRegion or nameToRegion[key].size != kwargs[key]:
                return False
        return True

    def searchRegion(name, expectedSize, returnNoneIfNotFound=False):
        for i in regions:
            if i.name == name:
                assert i.size == expectedSize, [name, i.size, expectedSize]
                return i
        if returnNoneIfNotFound:
            return None
        assert 0, "Failed to find region with name " + repr(name) + " have " + repr(regions)

    def require(name, numBits):
        searchRegion(name, numBits)

    def requireSigned(name, numBits):
        require(name, numBits)
        out.write(indent + "    final int %sSigned = asSigned(%s, %d);\n" % (name, name, numBits))

    if " " in left:
        opc, rest = left.split(" ", 1)
        words = [opc] + mysplit(rest)
    else:
        words = [left]
    if verbose:
        print("  words:", words, flush=True)

    indent = "        "
    out.write(indent + '// %s\n' % combine(mask, value))
    out.write(indent + "if ((opcode32Bit & %s) == %s) {\n" % (
        toHex(mask), toHex(value)))
    out.write(indent + '    if (verbose) {\n')
    out.write(indent + '        System.out.println("   trying %s");\n' % left.strip())
    out.write(indent + '    }\n')

    test = []
    second = []

    if 1:
        for region in regions:
            out.write(indent + "    final int %s = getPart(opcode32Bit, %d, %d);\n" % (
                region.name, region.start, region.size))

        word = words[0]
        if word == "B.<cond>":
            require("cond", 4)
            second.append('stmt.opcode = "B." + decodeBranchCondition(cond);')
        elif "{2}" in word:
            require("Q", 1)
            second.append('stmt.opcode = "%s" + (Q==0?"":"2");' % word.replace("{2}", ""))
        else:
            second.append('stmt.opcode = "%s";' % word)
        second.append('stmt.key = "opcode & %s = %s";' % (toHex(mask), toHex(value)))

        ALIAS_MAP = {}
        ALIAS_MAP["BFM"] = [("BFC", "Rn == 31 && imms < immr"),
                            ("BFI", "Rn != 31 && imms < immr"),
                            ("BFXIL", "imms >= immr")]
        ALIAS_MAP["CSINC"] = [("CINC", "Rn == Rm && Rm != 31 && Rn != 31 && ((cond >> 1) & 7) != 7"),
                              ("CSET", "Rm == 31 && Rn == 31 && ((cond >> 1) & 7) != 7")]
        ALIAS_MAP["CSINV"] = [("CINV", "Rn == Rm && Rm != 31 && Rn != 31 && ((cond >> 1) & 7) != 7"),
                              ("CSETM", "Rm == 31 && Rn == 31 && ((cond >> 1) & 7) != 7")]
        ALIAS_MAP["CSNEG"] = [("CNEG", "(cond & 0b1110) != 0b1110  && Rn == Rm")]
        ALIAS_MAP["SBFM"] = [("ASR <Wd>,<Wn>,#<shift>", "imms == 31"),
                             ("ASR <Xd>,<Xn>,#<shift>", "imms == 63"),
                             ("SBFIZ", "imms < immr"),
                             ("SBFX", "isBfxPreferred(sf, 0, imms, immr)"),
                             ("SXTB", "immr == 0 && imms == 7"),
                             ("SXTH", "immr == 0 && imms == 15"),
                             ("SXTW", "immr == 0 && imms == 31")]
        ALIAS_MAP["UBFM"] = [("LSL <Wd>,<Wn>,#<shift>", "imms != 31 && imms + 1 == immr"),
                             ("LSL <Xd>,<Xn>,#<shift>", "imms != 63 && imms + 1 == immr"),
                             ("LSR <Wd>,<Wn>,#<shift>", "imms == 31"),
                             ("LSR <Xd>,<Xn>,#<shift>", "imms == 63"),
                             ("UBFIZ", "imms < immr && imms + 1 != immr"),
                             ("UBFX", "isBfxPreferred(sf, 0, imms, immr)"),
                             ("UXTB", "immr == 0 && imms == 7"),
                             ("UXTH", "immr == 0 && imms == 15")]
        ALIAS_MAP["USHLL{2}"] = [("UXTL{2}", "bitCount(immh) == 1 && immb == 0")]
        ALIAS_MAP["SUBS <Wd>,<Wn>,<Wm>{,<shift2> #<amount>}"] = [
            ("CMP <Wn>,<Wm>{,<shift2> #<amount>}", "Rd == 31 && Rn != 31"),
            ("NEGS <Wd>,<Wm>{,<shift2> #<amount>}", "Rn == 31")]
        ALIAS_MAP["SUBS <Xd>,<Xn>,<Xm>{,<shift2> #<amount>}"] = [
            ("CMP <Xn>,<Xm>{,<shift2> #<amount>}", "Rd == 31 && Rn != 31"),
            ("NEGS <Xd>,<Xm>{,<shift2> #<amount>}", "Rn == 31")]
        ALIAS_MAP["SSHLL{2}"] = [("SXTL{2}", "immb == 0 && bitCount(immh) == 1")]
        ALIAS_MAP["UMOV <Wd>,<Vn>.<Ts>[<index-if1>]"] = [("MOV <Wd>,<Vn>.S[<index-if1>]", "(imm5 & 0b111) == 0b100")]
        ALIAS_MAP["UMOV <Xd>,<Vn>.<Ts>[<index-if2>]"] = [("MOV <Xd>,<Vn>.D[<index-if2>]", "(imm5 & 0b1111) == 0b1000")]
        ALIAS_MAP["SBCS"] = [("NGCS", "Rn == 31")]
        ALIAS_MAP["ADD <Wd|WSP>,<Wn|WSP>,#<imm>{,<shift>}"] = [
            ("MOV <Wd|WSP>,<Wn|WSP>", "shift == 0 && imm12 == 0 && (Rd == 31 || Rn == 31)")]
        ALIAS_MAP["ADD <Xd|SP>,<Xn|SP>,#<imm>{,<shift>}"] = [
            ("MOV <Xd|SP>,<Xn|SP>", "shift == 0 && imm12 == 0 && (Rd == 31 || Rn == 31)")]
        ALIAS_MAP["SYS"] = [("AT", 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[0].equals("AT")'),
                            ("DC", 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[0].equals("DC")'),
                            ("IC", 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[0].equals("IC")'),
                            ("TLBI", 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[0].equals("TLBI")')]
        ALIAS_MAP["MOVN <Wd>,#<imm>{,LSL #<shift>}"] = [
            ("MOV <Wd>,#<imm-inv-hw>", '!(imm16==0 && hw!=0) && imm16!=0xffff')]
        ALIAS_MAP["MOVN <Xd>,#<imm>{,LSL #<shift>}"] = [("MOV <Xd>,#<imm-inv-hw>", '!(imm16==0 && hw!=0)')]
        ALIAS_MAP["MOVZ <Wd>,#<imm>{,LSL #<shift>}"] = [("MOV <Wd>,#<imm-nor-hw>", '!(imm16==0 && hw!=0)')]
        ALIAS_MAP["MOVZ <Xd>,#<imm>{,LSL #<shift>}"] = [("MOV <Xd>,#<imm-nor-hw>", '!(imm16==0 && hw!=0)')]

        for main, aliases in ALIAS_MAP.items():
            if matches(main, words):
                for name, cond in aliases:
                    if cond:
                        test.append("!(%s)" % cond)
            for name, cond in aliases:
                if matches(name, words) and cond:
                    test.append(cond)
        if word in ["LSL", "LSR"] and words[-1] == '#<shift>':
            require("immr", 6)
            require("imms", 6)
            write_get_raw("n", 22, 1)
            if word == "LSR":
                test.append("(n == 0 && imms == 0b11111) || (n == 1 && imms == 0b111111)")
            else:
                test.append("imms +1 == immr")
                test.append("!((n == 0 && imms == 0b11111) || (n == 1 && imms == 0b111111))")
        if words == ["MOV", "<Xd|SP>", "<Xn|SP>"]:
            test.append("Rd != Rn")
        if words == ["MOV", "<Xd>", "#<imm-nor-hw>"]:
            test.append("!(imm16 == 0 && hw != 0)")
        if word == "MADD":
            test.append("Ra != 31")
        if words == ["ORR", "<Vd>.<T>", "<Vn>.<T>", "<Vm>.<T>"]:
            test.append("Rn != Rm")
        if words == ["MOV", "<Vd>.<T>", "<Vn>.<T>"]:
            require("Rm", 5)
            test.append("Rn == Rm")
        if word == "EXTR":
            test.append("Rn != Rm")
        if word == "ROR" and words[-1] == "#<shift>":
            require("Rm", 5)
            test.append("Rn == Rm")
        # is pre/post-index aka write-back
        if words[-1] in ["[<Xn|SP>],#<imm>", "[<Xn|SP>,#<imm>]!"] and has_regions(Rt=5, Rt2=5) and word in ["LDPSW",
                                                                                                            "LDNP"]:
            test.append("(Rn != Rt && Rn != Rt2) || Rn == 31")
        if has_regions(Rt=5, Rt2=5) and word in ["LDPSW"]:
            test.append("Rt != Rt2")
        if word in ["FCMLA"] and has_regions(size=2, L=1, H=1):
            test.append("size != 2 || (L!=1 && Q!=0)")
            test.append("size != 1 || H!=1 || Q!=0")
        if word in ["FCVTZS", "FCVTZU", "SCVTF", "UCVTF"] and has_regions(scale=6):
            write_get_raw("sf", 31, 1)
            require("scale", 6)
            test.append("sf == 1 || (scale>>5)>0")

    for wordi in range(1, len(words)):
        word = words[wordi]
        if word == '[<Xn|SP> {,#0}]' or word == '[<Xn|SP>{,#0}]':
            word = '[<Xn|SP>]'
        arg = "stmt.arg%d = " % wordi

        pattern = "^<[WXBDHQS](a|d|n|m|s|t|(t1)|(t2))([|]W?SP)?>$"
        if re.match(pattern, word) or (word.startswith("{") and word.endswith("}") and re.match(pattern, word[1:-1])):
            optional = word.startswith("{")
            if optional:
                word = word[1:-1]
            assert word.startswith("<") and word.endswith(">")
            word = word[1:-1]
            registerPrefix = word[0]
            word = word[1:]
            if "|" in word:
                x, sp = word.split("|")
                assert sp == "SP" or sp == "WSP"
                if sp == "WSP":
                    assert registerPrefix == "W"
                else:
                    assert registerPrefix == "X"
                word = x
                withSp = True
            else:
                withSp = False
            name = word
            del word
            ### End splitting

            withSp = toJavaBool(withSp)
            if words[0] == "RET" and optional:
                optionalReg = 30
            else:
                optionalReg = -1

            if name == "t1":
                name = "t"

            require("R" + name, 5)
            second.append(
                arg + 'getRegisterName("%s", %s, %s, %d);' % (registerPrefix, "R" + name, withSp, optionalReg))
        elif word in ["<W(s+1)>", "<W(t+1)>",
                      "<X(s+1)>", "<X(t+1)>"]:
            reg_type = word[1]
            reg_name = word[3]
            require("R" + reg_name, 5)
            assert reg_type in "XW", reg_type
            second.append(
                arg + 'getRegisterName("%s", (%s+1)%%32, false, -1);' % (reg_type, "R" + reg_name))
        elif word in ["#<imm-inv-hw>", "#<imm-nor-hw>"] and words[0] in ["MOV"]:
            require("hw", 2)
            require("imm16", 16)
            negate = toJavaBool("inv" in word)
            assert words[1][1] in "XW", words[0]
            second.append(arg + 'decodeShiftedImm16("%s", imm16, hw, %s);' % (words[1][1], negate))
        elif word in ["#<imm>"] and words[0] in ["TST", "EOR", "AND", "ANDS", "ORR", "MOV"]:
            require("immr", 6)
            require("imms", 6)
            require("N", 1)
            write_get_raw("sf", 31, 1)

            second.append(arg + 'decodeAluImm(N, immr, imms, sf==0?32:64);')
        elif word in ["#<lsb>"] and words[0] in ["SBFIZ", "BFC", "BFI", "BFXIL", "SBFX", "UBFIZ", "UBFX"]:
            require("immr", 6)
            require("imms", 6)
            write_get_raw("sf", 31, 1)

            second.append(
                arg + 'decodeLsb(immr, imms, sf==0?32:64, %s);' % (toJavaBool(words[0] in ["BFXIL", "SBFX", "UBFX"])))
        elif word in ["#<lsb>"] and words[0] in ["ROR", "EXTR"]:
            require("imms", 6)
            write_get_raw("sf", 31, 1)

            second.append(arg + 'decodeLsbRor(imms, sf==0?32:64);')
        elif word in ["#<width>"] and words[0] in ["SBFIZ", "BFC", "BFI", "BFXIL", "SBFX", "UBFIZ", "UBFX"]:
            require("immr", 6)
            require("imms", 6)
            write_get_raw("sf", 31, 1)

            second.append(
                arg + 'decodeWidth(immr, imms, sf==0?32:64, %s);' % toJavaBool(words[0] in ["BFXIL", "SBFX", "UBFX"]))
        elif word in ["#<imm>"] and words[0] in ["TBNZ", "TBZ"]:
            require("b5", 1)
            require("b40", 5)

            second.append(arg + 'formatDecimalImm((b5<<5)+b40);')
        elif word in ["#<imm>"] and words[0] in ["CCMP", "CCMN"]:
            require("imm5", 5)

            second.append(arg + 'formatHexImm(imm5);')
        elif word in ["#<imm>"] and words[0] in ["BRK", "SMC"]:
            require("imm16", 16)

            second.append(arg + 'formatHexImm(imm16);')
        elif word in ["#<imm>"] and words[0] in ["FMOV"]:
            if has_regions(imm8=8):
                second.append(arg + 'decodeFmovConstant(imm8);')
            else:
                require("a", 1)
                require("b", 1)
                require("c", 1)
                require("d", 1)
                require("e", 1)
                require("f", 1)
                require("g", 1)
                require("h", 1)

                second.append(arg + 'decodeFmovConstant(a,b,c,d,e,f,g,h);')
        elif word in ["#<imm>"] and words[0] in ["MOVI"]:
            require("a", 1)
            require("b", 1)
            require("c", 1)
            require("d", 1)
            require("e", 1)
            require("f", 1)
            require("g", 1)
            require("h", 1)

            second.append(arg + 'decodeMovi64Constant(a,b,c,d,e,f,g,h);')
        elif word in ["#<imm8>"] and words[0] in ["MOVI", "MVNI"]:
            require("a", 1)
            require("b", 1)
            require("c", 1)
            require("d", 1)
            require("e", 1)
            require("f", 1)
            require("g", 1)
            require("h", 1)

            second.append(arg + 'decodeMovi8Constant(a,b,c,d,e,f,g,h);')
        elif word in ["#<imm6>"]:
            require("imm6", 6)

            second.append(arg + 'formatDecimalImm(imm6);')
        elif word in ["{#<imm>}"] and words[0].startswith("DCPS"):
            require("imm16", 16)

            second.append(arg + 'formatHexImm(imm16);')
        elif word in ["#<nzcv>"] and words[0] in ["CCMP", "CCMN", "FCCMP", "FCCMPE"]:
            require("nzcv", 4)

            second.append(arg + 'formatHexImm(nzcv);')
        elif word in ["#<imm>{,<shift>}"]:
            require("shift", 2)
            require("imm12", 12)

            second.append(arg + 'decodeShiftedImm12(imm12, shift);')
        elif word in ["#<imm>{,LSL #<shift>}"]:
            require("hw", 2)
            require("imm16", 16)
            write_get_raw("s", 31, 1)

            second.append(arg + 'decodeMovShiftedImm16(imm16, hw, s==0?32:64);')
        elif word in ["#<immr>"]:
            require("immr", 6)

            second.append(arg + 'formatDecimalImm(immr);')
        elif word in ["#<imms>"]:
            require("imms", 6)

            second.append(arg + 'formatDecimalImm(imms);')
        elif re.match(r"^\[<[XW]n\|W?SP>,#<imm[0-9]*>\]!$", word):
            registerPrefix = word[2]
            require("Rn", 5)
            require("opc", 2)
            requireSigned("imm7", 7)

            variant = word.split("#<imm")[1].split(">")[0]
            if variant == "":
                shift = "(2 + opc/2)"
            elif variant == "2":
                shift = "(2 + opc)"
            else:
                assert False, "Don't know about variant " + repr(variant) + " words=" + repr(words)

            second.append(arg + 'formatPreIndexMemAccess("%s", Rn, imm7Signed<<%s);' % (registerPrefix, shift))
        elif word == "[<Xn|SP>,#<simm>]!" or word == "[<Wn|WSP>,#<simm>]!":
            registerPrefix = word[2]
            require("Rn", 5)
            requireSigned("imm9", 9)

            second.append(arg + 'formatPreIndexMemAccess("%s", Rn, imm9Signed);' % registerPrefix)
        elif re.match(r"^\[<[XW]n\|W?SP>\],#<imm[0-9]*>$", word):
            registerPrefix = word[2]
            require("Rn", 5)
            require("opc", 2)
            requireSigned("imm7", 7)

            variant = word.split("#<imm")[1].split(">")[0]
            if variant == "":
                shift = "(2 + opc/2)"
            elif variant == "2":
                shift = "(2 + opc)"
            else:
                assert False, "Don't know about variant " + repr(variant) + " words=" + repr(words)

            second.append(
                arg + 'formatPostIndexMemAccess("%s", Rn, imm7Signed<<%s );' % (registerPrefix, shift))
        elif word == "[<Xn|SP>],#<simm>" or word == "[<Wn|WSP>],#<simm>":
            registerPrefix = word[2]
            require("Rn", 5)
            requireSigned("imm9", 9)

            second.append(arg + 'formatPostIndexMemAccess("%s", Rn, imm9Signed);' % registerPrefix)
        elif re.match(r"^\[<[WX]n\|W?SP>\{,#<imm[0-9]*>\}\]$", word):
            registerPrefix = word[2]
            require("Rn", 5)
            require("opc", 2)
            requireSigned("imm7", 7)

            variant = word.split("#<imm")[1].split(">")[0]
            if variant == "":
                shift = "(2 + opc/2)"
            elif variant == "2":
                shift = "(2 + opc)"
            else:
                assert False, "Don't know about variant " + repr(variant) + " words=" + repr(words)

            second.append(arg + 'formatMemAccessWithOffset("%s", Rn, imm7Signed<<%s);' % (registerPrefix, shift))
        elif word == "[<Xn|SP>{,#<simm>}]" or word == "[<Wn|WSP>{,#<simm>}]":
            registerPrefix = word[2]
            require("Rn", 5)
            requireSigned("imm9", 9)

            second.append(arg + 'formatMemAccessWithOffset("%s", Rn, imm9Signed);' % registerPrefix)
        elif word == "[<Xn|SP>{,#<simm2>}]" or word == "[<Wn|WSP>{,#<simm2>}]":
            registerPrefix = word[2]
            require("Rn", 5)
            require("imm9", 9)
            require("S", 1)

            second.append(
                arg + 'formatMemAccessWithOffset("%s", Rn, decodeLdraaOffset(imm9, S));' % registerPrefix)
        elif word == "[<Xn|SP>{,#<simm2>}]!" or word == "[<Wn|WSP>{,#<simm2>}]!":
            registerPrefix = word[2]
            require("Rn", 5)
            require("imm9", 9)
            require("S", 1)

            second.append(
                arg + 'formatPreIndexMemAccess("%s", Rn, decodeLdraaOffset(imm9, S));' % registerPrefix)
        elif word == "[<Xn|SP>{,#<pimm>}]" or word == "[<Wn|WSP>{,#<pimm>}]":
            registerPrefix = word[2]
            require("Rn", 5)
            require("imm12", 12)

            if words[0] in ["LDRB", "STRB"]:
                shift = "0"
            else:
                if not has_regions(size=2):
                    write_get_raw("size", 30, 2)
                if is_simd(words[1]):
                    write_get_raw("opcPart", 23, 1)
                    shift = "(opcPart*4+size)"
                else:
                    shift = "size"
            second.append(arg + 'formatMemAccessWithOffset("%s", Rn, imm12 << %s);' % (
                registerPrefix, shift))
        elif word == '[<Xn|SP>,(<Wm>|<Xm>){,<extend> {<amount>}}]' or word == '[<Xn|SP>,(<Wm>|<Xm>),<extend> {<amount>}]':
            registerPrefix = "X"
            require("Rn", 5)
            require("Rm", 5)
            require("option", 3)
            require("S", 1)
            if not has_regions(size=2):
                write_get_raw("size", 30, 2)
            showDefaultLsl = "false"
            amount = "S==1?size:0"
            if is_simd(words[1]):
                require("opc", 2)
                amount = "S==1?(size+4*(opc/2)):0"
            is8Bit = toJavaBool(words[1] == "<Bt>")
            second.append(arg + 'formatMemAccessWithTwoRegisters("%s", Rn, Rm, option, %s, %s, S, %s);' % (
                registerPrefix, amount, showDefaultLsl, is8Bit))
        elif word == '[<Xn|SP>,<Xm>{,LSL <amount>}]':
            registerPrefix = "X"
            require("Rn", 5)
            require("Rm", 5)
            require("S", 1)
            if not has_regions(size=2):
                write_get_raw("size", 30, 2)
            amount = "S==1?size:0"
            if is_simd(words[1]):
                require("opc", 2)
                amount = "S==1?(size+4*(opc/2)):0"
            second.append(arg + 'formatMemAccessWithTwoRegistersLsl("%s", Rn, Rm, %s, S);' % (
                registerPrefix, amount))
        elif word == "[<Xn|SP>]":
            registerPrefix = "X"
            require("Rn", 5)

            second.append(arg + 'formatMemAccessWithOffset("%s", Rn, 0);' % registerPrefix)
        elif word == "<label>" and words[0] in ["LDR", "STR", "CBZ", "CBNZ", "B.<cond>", "LDRSW", "PRFM"]:
            requireSigned("imm19", 19)

            second.append(arg + 'decodeLoadStoreRegAddrLabel(pc, imm19Signed);')
        elif word == "<label>" and words[0] in ["TBNZ", "TBZ"]:
            requireSigned("imm14", 14)

            second.append(arg + 'decodePcRelativeLabel(pc, 4 * imm14Signed);')
        elif word == "<label>" and words[0] in ["ADRP", "ADR"]:
            require("immhi", 19)
            require("immlo", 2)
            page = toJavaBool(words[0] == "ADRP")

            second.append(arg + 'decodeAdrLabel(pc, asSigned((immhi<<2)+immlo, 21), %s);' % page)
        elif word == "<label>" and words[0] in ["BL", "B"]:
            requireSigned("imm26", 26)

            second.append(arg + 'decodeLongBranchLabel(pc, imm26Signed);')
        elif word in ["<Xm>{,<shift> #<amount>}",
                      "<Wm>{,<shift> #<amount>}",
                      "<Xm>{,<shift2> #<amount>}",
                      "<Wm>{,<shift2> #<amount>}"]:
            require("Rm", 5)
            require("shift", 2)
            require("imm6", 6)

            rorEnabled = toJavaBool("<shift>" in word)
            second.append(arg + 'decodeShiftedRegister("%s", Rm, shift, imm6, %s);' % (word[1], rorEnabled))
        elif word == "#<shift>" and has_regions(immh=4, immb=3):
            second.append(arg + 'decodeShift_constMinusImm(immh, immb, true, true, true, false);')
        elif word == "#<shift-hb2>":
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeShift_immMinusConst(immh, immb, false, false, false, true);')
        elif word == "#<shift-hb3>":
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeShift_immMinusConst(immh, immb, true, true, true, true);')
        elif word == "#<shift-hb4>":
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeShift_constMinusImm(immh, immb, false, false, false, true);')
        elif word == "#<shift-hb5>":
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeShift_constMinusImm(immh, immb, true, true, true, true);')
        elif word == "#<shift-hb6>":
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeShift_immMinusConst(immh, immb, true, true, true, false);')
        elif word == "#<shift>" and words[0].startswith("ROR"):
            require("imms", 6)

            second.append(arg + 'formatDecimalImm(imms);')
        elif word == "#<shift>" and words[0].startswith("SHLL"):
            require("size", 2)

            second.append(arg + 'decodeShift2(size);')
        elif word == "#<shift>":
            require("immr", 6)
            require("imms", 6)

            assert words[1].startswith("<X") or words[1].startswith("<W")
            regSize = 64
            if words[1].startswith("<W"):
                regSize = 32

            second.append(arg + 'decodeShift(immr, imms, %d);' % regSize)
        elif word == "#<index>":
            require("Q", 1)
            require("imm4", 4)

            second.append(arg + 'decodeIndex(Q, imm4);')
        elif word == "#<imm8>{,LSL #<amount>}":
            require("a", 1)
            require("b", 1)
            require("c", 1)
            require("d", 1)
            require("e", 1)
            require("f", 1)
            require("g", 1)
            require("h", 1)
            require("op", 1)
            require("cmode", 4)

            second.append(arg + 'decodeImm8WithLsl(a,b,c,d,e,f,g,h,op,cmode);')
        elif word == "#<imm8>{,LSL #<amount>}":
            require("a", 1)
            require("b", 1)
            require("c", 1)
            require("d", 1)
            require("e", 1)
            require("f", 1)
            require("g", 1)
            require("h", 1)
            require("op", 1)
            require("cmode", 4)

            second.append(arg + 'decodeImm8WithLsl(a,b,c,d,e,f,g,h,op,cmode);')
        elif word == "MSL #<amount>":
            require("cmode", 4)

            second.append(arg + 'decodeMslAmount(cmode);')
        elif word == "<R><t>":
            require("Rt", 5)
            require("b5", 1)

            second.append(arg + 'getRegisterName(b5==0 ? "W" : "X", Rt, false, -1);')
        elif word == "<R><m>":
            require("Rm", 5)
            require("option", 3)

            second.append(arg + 'getRegisterName(option&3==3 ? "X" : "W", Rm, false, -1);')
        elif word == "<R><n>":
            require("Rn", 5)
            require("imm5", 5)

            second.append(arg + 'decodeBaseRegister(imm5, Rn);')
        elif word == "<R><n2>":
            require("Rn", 5)
            require("imm5", 5)

            second.append(arg + 'decodeBaseRegister2(imm5, Rn);')
        elif word == '<R><m>{,<extend> {#<amount>}}':
            require("Rm", 5)
            require("option", 3)
            require("imm3", 3)
            haveSp = "Rn == 31"
            if has_regions(Rd=5) and searchRegion("Rd", 5).value != "11111":
                haveSp += " || Rd == 31"
            second.append(
                arg + 'decodeRegisterWithExtend((option&3)==3 ? "X" : "W", Rm, option, imm3, %s, true);' % haveSp)
        elif word == '<Wm>{,<extend> {#<amount>}}':
            require("Rm", 5)
            require("option", 3)
            require("imm3", 3)
            haveSp = "Rn == 31"
            if has_regions(Rd=5) and searchRegion("Rd", 5).value != "11111":
                haveSp += " || Rd == 31"
            second.append(arg + 'decodeRegisterWithExtend("W", Rm, option, imm3, %s, false);' % haveSp)
        elif word == "<cond>":
            require("cond", 4)

            second.append(arg + 'decodeBranchCondition(cond);')
        elif word == "<cond2>":
            require("cond", 4)

            second.append(arg + 'decodeBranchCondition(cond ^ 1);')
        elif word in ['{ <Vt>.<T> }',
                      '{ <Vt>.<T>,<Vt2>.<T> }',
                      '{ <Vt>.<T>,<Vt2>.<T>,<Vt3>.<T> }',
                      '{ <Vt>.<T>,<Vt2>.<T>,<Vt3>.<T>,<Vt4>.<T> }']:
            require("Rt", 5)
            require("Q", 1)
            require("size", 2)

            enable1D = toJavaBool(words[0] in ["LD1", "LD1R", "LD2R", "LD3R", "LD4R",
                                               "ST1", "ST1R", "ST2R", "ST3R", "ST4R"])
            second.append("final int numRegs = %d;" % word.count("<T>"))
            second.append(arg + 'decodeLd1(Q, size, Rt, numRegs, %s);' % enable1D)
        elif word in ['{ <Vn>.16B }',
                      '{ <Vn>.16B,<Vn+1>.16B }',
                      '{ <Vn>.16B,<Vn+1>.16B,<Vn+2>.16B }',
                      '{ <Vn>.16B,<Vn+1>.16B,<Vn+2>.16B,<Vn+3>.16B }']:
            require("Rn", 5)

            second.append("final int numRegs = %d;" % word.count("16B"))
            second.append(arg + 'decodeTbx(Rn, numRegs);')
        elif word == '[<Xn|SP>],<imm>':
            registerPrefix = "X"
            require("Rn", 5)
            require("Q", 1)

            second.append(
                arg + 'formatPostIndexMemAccess("%s", Rn, decodeLd1Offset(Q, numRegs));' % registerPrefix)
        elif re.match('\[<Xn\|SP>\],<imm-f[1234]>$', word):
            registerPrefix = "X"
            require("Rn", 5)
            require("Q", 1)
            require("size", 2)
            factor = int(word[-2])

            second.append(arg + 'formatPostIndexMemAccess("%s", Rn, %d << size);' % (registerPrefix, factor))
        elif word in ['{ <Vt>.B }[<index>]',
                      '{ <Vt>.D }[<index>]',
                      '{ <Vt>.H }[<index>]',
                      '{ <Vt>.S }[<index>]']:
            registerPrefix = word[7]
            assert registerPrefix in "BDHS"

            require("Rt", 5)
            require("Q", 1)
            require("S", 1)
            require("size", 2)

            second.append(arg + 'decodeLd1Index("%s", Rt, Q, S, size);' % registerPrefix)
        elif word in ['{ <Vt>.B,<Vt2>.B }[<index>]',
                      '{ <Vt>.D,<Vt2>.D }[<index>]',
                      '{ <Vt>.H,<Vt2>.H }[<index>]',
                      '{ <Vt>.S,<Vt2>.S }[<index>]']:
            registerPrefix = word[7]
            assert registerPrefix in "BDHS"

            require("Rt", 5)
            require("Q", 1)
            require("S", 1)
            require("size", 2)

            second.append(arg + 'decodeLd2Index("%s", Rt, Q, S, size);' % registerPrefix)
        elif word in ['{ <Vt>.B,<Vt2>.B,<Vt3>.B }[<index>]',
                      '{ <Vt>.D,<Vt2>.D,<Vt3>.D }[<index>]',
                      '{ <Vt>.H,<Vt2>.H,<Vt3>.H }[<index>]',
                      '{ <Vt>.S,<Vt2>.S,<Vt3>.S }[<index>]']:
            registerPrefix = word[7]
            assert registerPrefix in "BDHS"

            require("Rt", 5)
            require("Q", 1)
            require("S", 1)
            require("size", 2)

            second.append(arg + 'decodeLd3Index("%s", Rt, Q, S, size);' % registerPrefix)
        elif word in ['{ <Vt>.B,<Vt2>.B,<Vt3>.B,<Vt4>.B }[<index>]',
                      '{ <Vt>.D,<Vt2>.D,<Vt3>.D,<Vt4>.D }[<index>]',
                      '{ <Vt>.H,<Vt2>.H,<Vt3>.H,<Vt4>.H }[<index>]',
                      '{ <Vt>.S,<Vt2>.S,<Vt3>.S,<Vt4>.S }[<index>]']:
            registerPrefix = word[7]
            assert registerPrefix in "BDHS"

            require("Rt", 5)
            require("Q", 1)
            require("S", 1)
            require("size", 2)

            second.append(arg + 'decodeLd4Index("%s", Rt, Q, S, size);' % registerPrefix)
        elif re.match("^<[DHS][dnma]>$", word):
            reg_type = word[1]
            reg_name = word[2]
            require("R" + reg_name, 5)

            second.append(arg + 'formatFpuRegister("%s", R%s);' % (reg_type, reg_name))
        elif re.match("^<V><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)

            if has_regions(size=2):
                second.append(arg + 'decodeFpuRegister_size(size, %s, null, null, null, "D");' % reg_name)
            elif has_regions(sz=1):
                second.append(arg + 'decodeFpuRegister_sz(%s, sz, "S", "D");' % reg_name)
            elif has_regions(immh=4):
                second.append(arg + 'decodeFpuRegister_leadingZeros(immh, %s, null, "H", "S", "D");' % reg_name)
            elif has_regions(imm5=5):
                second.append(arg + 'decodeFpuRegister_custom1(imm5, %s);' % reg_name)
            else:
                assert 0, "Don't know how to decode " + word + ". Have " + repr(regions)

        elif re.match("^<V-si1><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeFpuRegister_size(size, %s, "B", "H", "S", null);' % reg_name)
        elif re.match("^<V-si2><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeFpuRegister_size(size, %s, null, "S", "D", null);' % reg_name)
        elif re.match("^<V-si3><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeFpuRegister_size(size, %s, null, "H", "S", null);' % reg_name)
        elif re.match("^<V-si4><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeFpuRegister_size(size, %s, "B", "H", "S", "D");' % reg_name)
        elif re.match("^<V-si5><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeFpuRegister_size(size, %s, "H", "S", "D", null);' % reg_name)
        elif re.match("^<V-ih1><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("immh", 4)

            second.append(arg + 'decodeFpuRegister_leadingZeros(immh, %s, "H", "S", "D", null);' % reg_name)
        elif re.match("^<V-ih2><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("immh", 4)

            second.append(arg + 'decodeFpuRegister_leadingZeros(immh, %s, "B", "H", "S", null);' % reg_name)
        elif re.match("^<V-ih3><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("immh", 4)

            second.append(arg + 'decodeFpuRegister_leadingZeros(immh, %s, "B", "H", "S", "D");' % reg_name)
        elif re.match("^<V-ih4><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("immh", 4)

            second.append(arg + 'decodeFpuRegister_leadingZeros(immh, %s, null, null, null, "D");' % reg_name)
        elif re.match("^<V-sz1><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeFpuRegister_sz(%s, sz, "H", null);' % reg_name)
        elif re.match("^<V-sz2><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeFpuRegister_sz(%s, sz, "S", "D");' % reg_name)
        elif re.match("^<V-sz3><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeFpuRegister_sz(%s, sz, null, "S");' % reg_name)
        elif re.match("^<V-sz4><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeFpuRegister_sz(%s, sz, null, "D");' % reg_name)
        elif re.match("^<V-sz5><[dnm]>$", word):
            reg_name = "R" + word[-2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeFpuRegister_sz(%s, sz, "S", null);' % reg_name)
        ########################################################################################
        elif word == '<Vm>.<Ts>[<index-sz1>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)
            require("sz", 1)

            second.append(arg + 'decodeVectorRegWithIndex_szHml1(%s, sz, H, L, M);' % reg_name)
        ########################################################################################
        elif word == '<Vm>.<Ts>[<index-si1>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)
            require("size", 2)

            second.append(arg + 'decodeVectorRegWithIndex2(%s, size, H, L, M);' % reg_name)
        elif word == '<Vm>.<Ts>[<index-si2>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)
            require("size", 2)

            second.append(arg + 'decodeVectorRegWithIndex6(%s, size, H, L, M);' % reg_name)
        elif word == '<Vm>.<Ts>[<index-si3>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)
            require("size", 2)

            second.append(arg + 'decodeVectorRegWithIndex7(%s, size, H, L, M);' % reg_name)
        ########################################################################################
        elif word == '<Vn>.<Ts>[<index>]' or word == '<Vn>.<T>[<index>]' or word == '<Vd>.<Ts>[<index>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm5", 5)

            second.append(arg + 'decodeVectorRegWithIndex1(%s, imm5, true, true, true, true);' % reg_name)
        elif word == '<Vd>.<Ts>[<index-if1>]' or word == '<Vn>.<Ts>[<index-if1>]' or word == '<Vn>.S[<index-if1>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm5", 5)

            second.append(arg + 'decodeVectorRegWithIndex1(%s, imm5, true, true, true, false);' % reg_name)
        elif word == '<Vd>.<Ts>[<index-if2>]' or word == '<Vn>.<Ts>[<index-if2>]' or word == '<Vn>.D[<index-if2>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm5", 5)

            second.append(arg + 'decodeVectorRegWithIndex1(%s, imm5, false, false, false, true);' % reg_name)
        elif word == '<Vd>.<Ts>[<index-if3>]' or word == '<Vn>.<Ts>[<index-if3>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm5", 5)

            second.append(arg + 'decodeVectorRegWithIndex1(%s, imm5, true, true, false, false);' % reg_name)
        ########################################################################################
        elif word == '<Vm>.H[<index>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)

            second.append(arg + 'decodeVectorRegWithIndex3(%s, H, L, M);' % reg_name)
        elif word == '<Vm>.4B[<index>]':
            reg_name = "R" + word[2]
            require(reg_name, 4)
            require("H", 1)
            require("M", 1)
            require("L", 1)

            second.append(arg + 'decodeVectorRegWithIndex11(%s, M, H, L);' % reg_name)
        elif word == '<Vn>.<Ts>[<index2>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm5", 5)
            require("imm4", 4)

            second.append(arg + 'decodeVectorRegWithIndex10(%s, imm5, imm4);' % reg_name)
        elif word == '<Vm>.S[<imm2>]':
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("imm2", 2)

            second.append(arg + 'decodeVectorRegWithIndex12(%s, imm2);' % reg_name)
        ########################################################################################
        elif re.match("^<V[dnm]>.<T>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)

            if has_regions(size=2, Q=1):
                second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "______r_");' % reg_name)
            elif has_regions(Q=1, imm5=5):
                second.append(arg + 'decodeVectorReg5(Q, imm5, %s);' % reg_name)
            elif has_regions(Q=1, sz=1):
                second.append(arg + 'decodeVectorReg6(sz, Q, %s);' % reg_name)
            elif has_regions(Q=1, immh=4):
                second.append(arg + 'decodeVectorReg9(immh, Q, %s);' % reg_name)
            elif has_regions(Q=1):
                second.append(arg + 'decodeVectorRegBWithQ(Q, %s);' % reg_name)
            elif has_regions(size=2):
                second.append(arg + 'decodeVectorReg2(size, %s);' % reg_name)
            else:
                assert 0, "Don't know how to decode " + word
        elif re.match("^<V[dnm]>.<T-siq1>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "______rr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq2>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "__rrrr__");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq3>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "rr____rr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq4>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "rr____r_");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq5>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "rr__r_rr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq6>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "____r_rr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq7>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "__rrrrrr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq8>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "____rrrr");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq9>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ(size, Q, %s, "_______r");' % reg_name)
        elif re.match("^<V[dnm]>.<T-siq10>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_sizeQ_custom1(%s, Q, size);' % reg_name)
        ########################################################################################
        elif re.match("^<V[dnm]>.<T-si1>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeVectorReg_size1(size, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-si2>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeVectorReg_size2(size, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-si3>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("size", 2)

            second.append(arg + 'decodeVectorReg_size3(size, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-h>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("Q", 1)

            second.append(arg + 'decodeVectorRegHWithQ(Q, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-b>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("Q", 1)

            second.append(arg + 'decodeVectorRegBWithQ(Q, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-s>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("Q", 1)

            second.append(arg + 'decodeVectorRegSWithQ(Q, %s);' % reg_name)
        ########################################################################################
        elif re.match("^<V[dnm]>.<T-sz1>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeVectorReg_sz(%s, sz, "2H", null);' % reg_name)
        elif re.match("^<V[dnm]>.<T-sz2>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeVectorReg_sz(%s, sz, "2S", "2D");' % reg_name)
        elif re.match("^<V[dnm]>.<T-sz3>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeVectorReg_sz(%s, sz, null, "2D");' % reg_name)
        elif re.match("^<V[dnm]>.<T-sz4>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)

            second.append(arg + 'decodeVectorReg_sz(%s, sz, "4S", "2D");' % reg_name)
        ########################################################################################
        elif re.match("^<V[dnm]>.<T-szq1>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("Q", 1)
            require("sz", 1)

            second.append(arg + 'decodeVectorReg_szQ1(%s, sz, Q);' % reg_name)
        elif re.match("^<V[dnm]>.<T-szq2>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_szQ2(sz, Q, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T-szq3>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_szQ3(%s, sz, Q);' % reg_name)
        elif re.match("^<V[dnm]>.<T-szq4>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("sz", 1)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg_szQ4(sz, Q, %s);' % reg_name)
        ########################################################################################
        elif re.match("^<V[dnm]>.<T-cu1>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("immh", 4)

            second.append(arg + 'decodeVectorReg10(immh, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T10>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("immh", 4)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg11(immh, Q, %s);' % reg_name)
        elif re.match("^<V[dnm]>.<T11>$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            require("immh", 4)
            require("Q", 1)

            second.append(arg + 'decodeVectorReg12(immh, Q, %s);' % reg_name)
        elif re.match("^<V[dnma]>.[0-9]*[BSDHQ]$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)
            suffix = word.split(".")[1]

            second.append(arg + 'formatVectorRegister(%s, "%s");' % (reg_name, suffix))
        elif re.match(r"^<V[dnma]>.D\[1\]$", word):
            reg_name = "R" + word[2]
            require(reg_name, 5)

            second.append(arg + 'formatVectorRegister(%s, "D[1]");' % reg_name)
        elif re.match("^#[0-9]+$", word) or word == "#0.0":
            second.append(arg + '"%s";' % word)
        elif word == "<dc_op>" or word == '<ic_op>{,<Xt>}':
            require("op1", 3)
            require("CRm", 4)
            require("op2", 3)
            require("Rt", 5)

            second.append(arg + 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[1];')
        elif word == "<at_op>":
            require("op1", 3)
            require("CRm", 4)
            require("op2", 3)

            second.append(arg + 'getSysOp(op1, op2, CRm, CRn, Rt).split(" ")[1];')
        elif word == '<option>|#<imm>':
            require("CRm", 4)

            second.append(arg + 'decodeDmbOption(CRm);')
        elif word == '{<option>|#<imm>}':
            require("CRm", 4)

            second.append(arg + 'decodeIsbOption(CRm);')
        elif word == '#<imm>' and words[0] in ["HVC", "SVC", "HLT"]:
            require("imm16", 16)

            second.append(arg + 'formatHexImm(imm16);')
        elif word in ['#<immh>'] and words[0] in ["HINT"]:
            require("CRm", 4)
            require("op2", 3)

            second.append(arg + 'formatHexImm((CRm<<3) + op2);')
        elif word == '{#<imm>}':
            require("CRm", 4)

            second.append(arg + 'CRm==15?"":formatHexImm(CRm);')
        elif word == '#<rotate1>':
            require("rot", 1)

            second.append(arg + 'formatDecimalImm(rot==0?90:270);')
        elif word == '#<rotate2>':
            require("rot", 2)

            second.append(arg + 'formatDecimalImm(rot * 90);')
        elif word == '#<fbits>':
            require("scale", 6)

            second.append(arg + 'decodeFbitsFromScale(scale);')
        elif word == '#<fbits2>':
            require("immh", 4)
            require("immb", 3)

            second.append(arg + 'decodeFbits2(immh, immb);')
        elif word == '(<prfop>|#<imm5>)':
            require("Rt", 5)

            second.append(arg + 'decodePrefetchOp(Rt);')
        elif word == '(<systemreg>|S<op0>_<op1>_<Cn>_<Cm>_<op2>)':
            require("o0", 1)
            require("op1", 3)
            require("op2", 3)
            require("CRm", 4)
            require("CRn", 4)

            second.append(arg + 'decodeSystemRegister(o0+2, op1, CRn, CRm, op2);')
        elif word == '<tlbi_op>{,<Xt>}':
            require("op1", 3)
            require("op2", 3)
            require("CRm", 4)
            require("Rt", 5)

            second.append(arg + 'decodeTlbiRegister(Rt, op1, CRm, op2);')
        elif word == '#<op1>':
            require("op1", 3)

            second.append(arg + 'formatDecimalImm(op1);')
        elif word == '#<op2>':
            require("op2", 3)

            second.append(arg + 'formatDecimalImm(op2);')
        elif word == '#<op2>{,<Xt>}':
            require("Rt", 5)
            require("op2", 3)

            second.append(arg + 'formatDecimalImm(op2) + (Rt==31 ? "" : ("," + getRegisterName("X", Rt, false, -1)));')
        elif word == '<Cn>':
            require("CRn", 4)

            second.append(arg + '"C" + CRn;')
        elif word == '<Cm>':
            require("CRm", 4)

            second.append(arg + '"C" + CRm;')
        elif word == 'CSYNC':
            second.append(arg + '"%s";' % word)
        else:
            assert 0, [word, words[0], regions, words]

    def write_common_part(indent):
        out.write(indent + '    if (numMatching > 0) {\n')
        out.write(indent + '        if (numMatching == 1) {\n')
        out.write(indent + '            matching = new ArrayList<>();\n')
        out.write(indent + '        }\n')
        out.write(indent + '        matching.add(stmt.format());\n')
        out.write(indent + '    }\n')
        out.write(indent + '    numMatching += 1;\n')
        for i in second:
            out.write(indent + "    " + i + "\n")
        out.write(indent + '    if (showMatching) {\n')
        out.write(indent + '        System.out.println(stmt.format() + " " + stmt.key);\n')
        out.write(indent + '    }\n')

    if test:
        test2 = ["(" + x + ")" for x in test]
        out.write(indent + '    if (%s) {\n' % (" && ".join(test2)))
        write_common_part(indent + "    ")
        out.write(indent + '    }\n')
    else:
        write_common_part(indent)
    out.write(indent + '}\n\n')


Region = collections.namedtuple("Region", ["name", "end", "size", "start", "value"])


def parseLine(line):
    left = line[:77].strip()
    right = line[77:].strip()
    right = right.replace(")<", ") <")
    right = right.replace("><", "> <")
    right = right.replace(">(", "> (")
    regions = []
    mask = ""
    value = ""
    for region in right.split(" "):
        if region.startswith("("):
            assert region.endswith(")")
            region = region[1:-1]
            for c in region:
                assert c in "01", line
                mask += "1"
                value += c
        else:
            assert region.startswith("<"), [line, region]
            assert region.endswith(">")
            region = region[1:-1]
            name, region = region.split(":")
            for c in region:
                if c in "01":
                    mask += "1"
                    value += c
                else:
                    assert c == "x"
                    mask += "0"
                    value += "0"
            offset = 32 - len(mask)
            bits = len(region)
            regions.append(Region(name, offset + bits, bits, offset, region))
    assert len(value) == 32, line
    assert len(mask) == 32, line
    numMaskBits = mask.count("1")
    allKnown.append((left, eval(toHex(mask)), eval(toHex(value))))
    ParsedLine = collections.namedtuple("ParsedLine", ["left", "mask", "value", "regions", "numMaskBits"])
    return ParsedLine(left, mask, value, regions, numMaskBits)


out = open(BASE_DIR + "/ArmDisasmDecoder.java.new", "w")
out.write('''
//
// This is a generated file. Do not edit.
//
package holdec.jarmo.arm64;

import java.util.List;
import java.util.ArrayList;

import static holdec.jarmo.arm64.ArmDisasmHelper.*;

@SuppressWarnings("ConstantConditions")
public class ArmDisasmDecoder {
    private boolean verbose;
    private boolean showMatching;

    public AsmStatement decode(long pc, int opcode32Bit) {
        AsmStatement stmt = new AsmStatement(pc);

''')

lines = [x.strip() for x in open(INPUT_FILE).readlines() if x.strip()]
parsedLines = []
for line in [x for x in lines if x[0] != "#"]:
    left = line[:70].strip()
    parsedLines.append(parseLine(line))
out.write('''
        try {
            if(false) {
                // Nothing''')
for bits in range(32, 5, -1):
    out.write('''
            } else if(decodeWith%dBitsSet(pc, opcode32Bit, stmt)) {
                // Nothing''' % bits)
out.write('''
            } else {
                if (verbose) {
                    logUnknownOpcode(opcode32Bit);
                }
                stmt.markAsUnknown(opcode32Bit);
            }
        } catch(UndefinedInstructionException e) {
            if(verbose) {
                e.printStackTrace();
            }
            stmt.markAsUnknown(opcode32Bit);
        }
        postProcessStatement(stmt);
        return stmt;
    }

''')

for bits in range(32, 5, -1):
    out.write('''
    private boolean decodeWith%dBitsSet(long pc, int opcode32Bit, AsmStatement stmt) {
        int numMatching = 0;
        List<String> matching = null;

''' % bits)
    for line in [x for x in parsedLines if x.numMaskBits == bits]:
        doOneLine(line)

    out.write('''
        if (numMatching == 0) {
            return false;
        } else if (numMatching == 1) {
            return true;
        }
        matching.add(stmt.format());
        throw new RuntimeException("Multiple interpretations possible for "+String.format("0x%08x", opcode32Bit)+": " + matching);
    }
''')

out.write('''
}
''')

out.close()

os.rename(BASE_DIR + "/ArmDisasmDecoder.java.new", BASE_DIR + "/ArmDisasmDecoder.java")
