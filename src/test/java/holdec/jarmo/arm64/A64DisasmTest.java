package holdec.jarmo.arm64;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class A64DisasmTest {

    @Test
    public void test_abs() {
        assertDecoding(0x5ee0bb0d, "abs d13,d24");
        assertDecoding(0x0e60b9d0, "abs v16.4h,v14.4h");
        assertDecoding(0x4e60b841, "abs v1.8h,v2.8h");
        assertDecoding(0x4e20b8ab, "abs v11.16b,v5.16b");
        assertDecoding(0x4ea0ba8d, "abs v13.4s,v20.4s");
        assertDecoding(0x4e20b9f0, "abs v16.16b,v15.16b");
        assertDecoding(0x0ea0b910, "abs v16.2s,v8.2s");
        assertDecoding(0x4ea0bb90, "abs v16.4s,v28.4s");
        assertDecoding(0x4ee0b9bc, "abs v28.2d,v13.2d");
        assertDecoding(0x0e20b888, "abs v8.8b,v4.8b");
    }

    @Test
    public void test_adc() {
        // Forms
        assertDecoding(0x1a1a001a, "adc w26, w0, w26");
        assertDecoding(0x9a000000, "adc x0, x0, x0");

        // Register 31
        assertDecoding(0x9a1f0000, "adc x0, x0, xzr");
        assertDecoding(0x9a1f0020, "adc x0, x1, xzr");
        assertDecoding(0x9a1f01a0, "adc x0, x13, xzr");
        assertDecoding(0x9a1f03e1, "adc x1, xzr, xzr");
        assertDecoding(0x9a1f039c, "adc x28, x28, xzr");
        assertDecoding(0x9a1f03fc, "adc x28, xzr, xzr");

        // Rest
        assertDecoding(0x9a000040, "adc x0, x2, x0");
        assertDecoding(0x9a1b0280, "adc x0, x20, x27");
        assertDecoding(0x9a0200eb, "adc x11, x7, x2");
        assertDecoding(0x9a1b00d1, "adc x17, x6, x27");
        assertDecoding(0x9a0e037c, "adc x28, x27, x14");
        assertDecoding(0x9a1c00bc, "adc x28, x5, x28");
    }

    @Test
    public void test_adcs() {
        // Forms
        assertDecoding(0xba0b0021, "adcs x1, x1, x11");

        // Register 31
        assertDecoding(0xba1f020a, "adcs x10, x16, xzr");
        assertDecoding(0xba1f00ad, "adcs x13, x5, xzr");
        assertDecoding(0xba0d03fa, "adcs x26, xzr, x13");
        assertDecoding(0xba1f0084, "adcs x4, x4, xzr");

        // Rest
        assertDecoding(0xba0b014a, "adcs x10, x10, x11");
        assertDecoding(0xba0500ca, "adcs x10, x6, x5");
        assertDecoding(0xba13022d, "adcs x13, x17, x19");
        assertDecoding(0xba1401ef, "adcs x15, x15, x20");
        assertDecoding(0xba050231, "adcs x17, x17, x5");
        assertDecoding(0xba0b0293, "adcs x19, x20, x11");
        assertDecoding(0xba1100d3, "adcs x19, x6, x17");
        assertDecoding(0xba050042, "adcs x2, x2, x5");
        assertDecoding(0xba10035a, "adcs x26, x26, x16");
        assertDecoding(0xba11035a, "adcs x26, x26, x17");
        assertDecoding(0xba11039a, "adcs x26, x28, x17");
        assertDecoding(0xba070084, "adcs x4, x4, x7");
    }

    @Test
    public void test_add_w() {
        assertDecoding(0x0b3d4c1f, "add wsp, w0, w29, lsl #3");

        // Forms
        assertDecoding(0x0b010000, "add w0, w0, w1");
        assertDecoding(0x0b801000, "add w0, w0, w0, asr #4");
        assertDecoding(0x11000400, "add w0, w0, #0x1");
        assertDecoding(0x11400400, "add w0, w0, #0x1, lsl #12");
        assertDecoding(0x0b210000, "add w0, w0, w1, uxtb");

        // Register 31
        assertDecoding(0x11186b9f, "add wsp, w28, #0x61a");
        assertDecoding(0x117214bf, "add wsp, w5, #0xc85, lsl #12");

        // Rest
        assertDecoding(0x11004000, "add w0, w0, #0x10");
        assertDecoding(0x11404000, "add w0, w0, #0x10, lsl #12");
        assertDecoding(0x11040000, "add w0, w0, #0x100");
        assertDecoding(0x11440000, "add w0, w0, #0x100, lsl #12");
        assertDecoding(0x11040400, "add w0, w0, #0x101");
        assertDecoding(0x11440400, "add w0, w0, #0x101, lsl #12");
        assertDecoding(0x11406000, "add w0, w0, #0x18, lsl #12");
        assertDecoding(0x11060000, "add w0, w0, #0x180");
        assertDecoding(0x11007c00, "add w0, w0, #0x1f");
        assertDecoding(0x1107fc00, "add w0, w0, #0x1ff");
        assertDecoding(0x11180000, "add w0, w0, #0x600");
        assertDecoding(0x11580000, "add w0, w0, #0x600, lsl #12");
        assertDecoding(0x1103fc00, "add w0, w0, #0xff");
        assertDecoding(0x0b000400, "add w0, w0, w0, lsl #1");
        assertDecoding(0x0b002800, "add w0, w0, w0, lsl #10");
        assertDecoding(0x0b003c00, "add w0, w0, w0, lsl #15");
        assertDecoding(0x0b000800, "add w0, w0, w0, lsl #2");
        assertDecoding(0x0b006000, "add w0, w0, w0, lsl #24");
        assertDecoding(0x0b000c00, "add w0, w0, w0, lsl #3");
        assertDecoding(0x0b001000, "add w0, w0, w0, lsl #4");
        assertDecoding(0x0b001400, "add w0, w0, w0, lsl #5");
        assertDecoding(0x0b001800, "add w0, w0, w0, lsl #6");
        assertDecoding(0x0b404000, "add w0, w0, w0, lsr #16");
        assertDecoding(0x0b404400, "add w0, w0, w0, lsr #17");
        assertDecoding(0x0b400c00, "add w0, w0, w0, lsr #3");
        assertDecoding(0x0b407c00, "add w0, w0, w0, lsr #31");
        assertDecoding(0x0b401000, "add w0, w0, w0, lsr #4");
        assertDecoding(0x0b401400, "add w0, w0, w0, lsr #5");
        assertDecoding(0x0b401800, "add w0, w0, w0, lsr #6");
        assertDecoding(0x0b402000, "add w0, w0, w0, lsr #8");
        assertDecoding(0x0b810800, "add w0, w0, w1, asr #2");
        assertDecoding(0x0b010400, "add w0, w0, w1, lsl #1");
        assertDecoding(0x0b012800, "add w0, w0, w1, lsl #10");
        assertDecoding(0x0b014000, "add w0, w0, w1, lsl #16");
        assertDecoding(0x0b010800, "add w0, w0, w1, lsl #2");
        assertDecoding(0x0b016000, "add w0, w0, w1, lsl #24");
        assertDecoding(0x0b010c00, "add w0, w0, w1, lsl #3");
        assertDecoding(0x0b011000, "add w0, w0, w1, lsl #4");
        assertDecoding(0x0b011400, "add w0, w0, w1, lsl #5");
        assertDecoding(0x0b011800, "add w0, w0, w1, lsl #6");
        assertDecoding(0x0b011c00, "add w0, w0, w1, lsl #7");
        assertDecoding(0x0b012000, "add w0, w0, w1, lsl #8");
        assertDecoding(0x0b012400, "add w0, w0, w1, lsl #9");
        assertDecoding(0x0b415c00, "add w0, w0, w1, lsr #23");
        assertDecoding(0x0b417000, "add w0, w0, w1, lsr #28");
        assertDecoding(0x0b417c00, "add w0, w0, w1, lsr #31");
        assertDecoding(0x0b411c00, "add w0, w0, w1, lsr #7");
        assertDecoding(0x0b412000, "add w0, w0, w1, lsr #8");
        assertDecoding(0x0b210000, "add w0, w0, w1, uxtb");
        assertDecoding(0x0b212000, "add w0, w0, w1, uxth");
        assertDecoding(0x0b0a0800, "add w0, w0, w10, lsl #2");
        assertDecoding(0x0b0a1400, "add w0, w0, w10, lsl #5");
        assertDecoding(0x0b0b0c00, "add w0, w0, w11, lsl #3");
        assertDecoding(0x0b0b1400, "add w0, w0, w11, lsl #5");
        assertDecoding(0x0b0c3c00, "add w0, w0, w12, lsl #15");
        assertDecoding(0x0b0c0800, "add w0, w0, w12, lsl #2");
        assertDecoding(0x0b2c2000, "add w0, w0, w12, uxth");
        assertDecoding(0x0b0d3c00, "add w0, w0, w13, lsl #15");
        assertDecoding(0x0b0e3c00, "add w0, w0, w14, lsl #15");
        assertDecoding(0x0b0e0800, "add w0, w0, w14, lsl #2");
        assertDecoding(0x0b0f0800, "add w0, w0, w15, lsl #2");
        assertDecoding(0x0b0f1c00, "add w0, w0, w15, lsl #7");
        assertDecoding(0x0b123400, "add w0, w0, w18, lsl #13");
        assertDecoding(0x0b124400, "add w0, w0, w18, lsl #17");
        assertDecoding(0x0b820400, "add w0, w0, w2, asr #1");
        assertDecoding(0x0b020400, "add w0, w0, w2, lsl #1");
        assertDecoding(0x0b222000, "add w0, w0, w2, uxth");
        assertDecoding(0x0b052000, "add w0, w0, w5, lsl #8");
        assertDecoding(0x0b258000, "add w0, w0, w5, sxtb");
        assertDecoding(0x11408020, "add w0, w1, #0x20, lsl #12");
        assertDecoding(0x11003c20, "add w0, w1, #0xf");
        assertDecoding(0x113ffc20, "add w0, w1, #0xfff");
        assertDecoding(0x0b800420, "add w0, w1, w0, asr #1");
        assertDecoding(0x0b800820, "add w0, w1, w0, asr #2");
        assertDecoding(0x0b801020, "add w0, w1, w0, asr #4");
        assertDecoding(0x0b000420, "add w0, w1, w0, lsl #1");
        assertDecoding(0x0b002820, "add w0, w1, w0, lsl #10");
        assertDecoding(0x0b004020, "add w0, w1, w0, lsl #16");
        assertDecoding(0x0b000820, "add w0, w1, w0, lsl #2");
        assertDecoding(0x0b005020, "add w0, w1, w0, lsl #20");
        assertDecoding(0x0b006020, "add w0, w1, w0, lsl #24");
        assertDecoding(0x0b000c20, "add w0, w1, w0, lsl #3");
        assertDecoding(0x0b001020, "add w0, w1, w0, lsl #4");
        assertDecoding(0x0b001420, "add w0, w1, w0, lsl #5");
        assertDecoding(0x0b001820, "add w0, w1, w0, lsl #6");
        assertDecoding(0x0b001c20, "add w0, w1, w0, lsl #7");
        assertDecoding(0x0b002020, "add w0, w1, w0, lsl #8");
        assertDecoding(0x0b400420, "add w0, w1, w0, lsr #1");
        assertDecoding(0x0b402820, "add w0, w1, w0, lsr #10");
        assertDecoding(0x0b407c20, "add w0, w1, w0, lsr #31");
        assertDecoding(0x0b200020, "add w0, w1, w0, uxtb");
        assertDecoding(0x0b202020, "add w0, w1, w0, uxth");
        assertDecoding(0x0b01007b, "add w27, w3, w1");
        assertDecoding(0x0b1b03db, "add w27, w30, w27");
        assertDecoding(0x1100049b, "add w27, w4, #0x1");
        assertDecoding(0x0b1b10fb, "add w27, w7, w27, lsl #4");
        assertDecoding(0x0b030143, "add w3, w10, w3");
        assertDecoding(0x0b830943, "add w3, w10, w3, asr #2");
        assertDecoding(0x0b034143, "add w3, w10, w3, lsl #16");
        assertDecoding(0x11000563, "add w3, w11, #0x1");
        assertDecoding(0x0b080129, "add w9, w9, w8");
        assertDecoding(0x0b084d29, "add w9, w9, w8, lsl #19");
        assertDecoding(0x0b090929, "add w9, w9, w9, lsl #2");
        assertDecoding(0x11000120, "add w0,w9,#0x0");
        assertDecoding(0x9100034b, "add x11,x26,#0x0");
    }

    @Test
    public void test_add_x() {
        // Forms
        assertDecoding(0x8b000020, "add x0, x1, x0");
        assertDecoding(0x8b800420, "add x0, x1, x0, asr #1");
        assertDecoding(0x91000420, "add x0, x1, #0x1");
        assertDecoding(0x91400420, "add x0, x1, #0x1, lsl #12");
        assertDecoding(0x8b20a820, "add x0, x1, w0, sxth #2");

        // Register 31
        assertDecoding(0x910043ff, "add sp, sp, #0x10");
        assertDecoding(0x914043ff, "add sp, sp, #0x10, lsl #12");
        assertDecoding(0x8b3063ff, "add sp, sp, x16");
        assertDecoding(0x910043e1, "add x1, sp, #0x10");
        assertDecoding(0x8b2563e1, "add x1, sp, x5");
        assertDecoding(0x910403e0, "add x0, sp, #0x100");
        assertDecoding(0x8b041bfa, "add x26, xzr, x4, lsl #6");
        assertDecoding(0x8b8001df, "add xzr, x14, x0, asr #0");
        assertDecoding(0x8b12607f, "add xzr, x3, x18, lsl #24");
        assertDecoding(0x8b8a07fc, "add x28, xzr, x10, asr #1");
        assertDecoding(0x8b4cc3fd, "add x29, xzr, x12, lsr #48");
        assertDecoding(0x8b1f2f04, "add x4, x24, xzr, lsl #11");
        assertDecoding(0x8b8137e7, "add x7, xzr, x1, asr #13");
        assertDecoding(0x8b96bfe8, "add x8, xzr, x22, asr #47");

        // Rest
        assertDecoding(0x91000000, "add x0, x0, #0x0");
        assertDecoding(0x91400000, "add x0, x0, #0x0, lsl #12");
        assertDecoding(0x91002000, "add x0, x0, #0x8");
        assertDecoding(0x91402000, "add x0, x0, #0x8, lsl #12");

        assertDecoding(0x8b21c000, "add x0, x0, w1, sxtw");
        assertDecoding(0x8b21c400, "add x0, x0, w1, sxtw #1");
        assertDecoding(0x8b21c800, "add x0, x0, w1, sxtw #2");
        assertDecoding(0x8b21cc00, "add x0, x0, w1, sxtw #3");
        assertDecoding(0x8b21d000, "add x0, x0, w1, sxtw #4");
        assertDecoding(0x8b210000, "add x0, x0, w1, uxtb");
        assertDecoding(0x8b210800, "add x0, x0, w1, uxtb #2");
        assertDecoding(0x8b212000, "add x0, x0, w1, uxth");
        assertDecoding(0x8b214000, "add x0, x0, w1, uxtw");
        assertDecoding(0x8b214400, "add x0, x0, w1, uxtw #1");
        assertDecoding(0x8b214800, "add x0, x0, w1, uxtw #2");
        assertDecoding(0x8b214c00, "add x0, x0, w1, uxtw #3");
        assertDecoding(0x8b215000, "add x0, x0, w1, uxtw #4");

        assertDecoding(0x8b000400, "add x0, x0, x0, lsl #1");
        assertDecoding(0x8b000800, "add x0, x0, x0, lsl #2");
        assertDecoding(0x8b000c00, "add x0, x0, x0, lsl #3");
        assertDecoding(0x8b001000, "add x0, x0, x0, lsl #4");
        assertDecoding(0x8b001400, "add x0, x0, x0, lsl #5");
        assertDecoding(0x8b001c00, "add x0, x0, x0, lsl #7");
        assertDecoding(0x8b402000, "add x0, x0, x0, lsr #8");
        assertDecoding(0x8b002800, "add x0, x0, x0, lsl #10");
        assertDecoding(0x8b404000, "add x0, x0, x0, lsr #16");
        assertDecoding(0x8b016000, "add x0, x0, x1, lsl #24");
        assertDecoding(0x8b007c00, "add x0, x0, x0, lsl #31");
        assertDecoding(0x8b408000, "add x0, x0, x0, lsr #32");
        assertDecoding(0x8b40fc00, "add x0, x0, x0, lsr #63");

        assertDecoding(0x8b410400, "add x0, x0, x1, lsr #1");
        assertDecoding(0x8b413000, "add x0, x0, x1, lsr #12");
        assertDecoding(0x8b418000, "add x0, x0, x1, lsr #32");
        assertDecoding(0x8b411c00, "add x0, x0, x1, lsr #7");
        assertDecoding(0x8b916800, "add x0, x0, x17, asr #26");
        assertDecoding(0x8b8d8000, "add x0, x0, x13, asr #32");
        assertDecoding(0x8b420400, "add x0, x0, x2, lsr #1");
        assertDecoding(0x8b424000, "add x0, x0, x2, lsr #16");
        assertDecoding(0x8b428000, "add x0, x0, x2, lsr #32");
        assertDecoding(0x8b42c000, "add x0, x0, x2, lsr #48");
        assertDecoding(0x8b42f400, "add x0, x0, x2, lsr #61");
        assertDecoding(0x8b421c00, "add x0, x0, x2, lsr #7");
    }

    @Test
    public void test_add_fpu() {
        assertDecoding(0x5ee18482, "add d2, d4, d1");
        assertDecoding(0x5ee184e7, "add d7, d7, d1");
    }

    @Test
    public void test_add_simd() {
        assertDecoding(0x4e338400, "add v0.16b, v0.16b, v19.16b");
        assertDecoding(0x4ee28420, "add v0.2d, v1.2d, v2.2d");
        assertDecoding(0x0ea18400, "add v0.2s, v0.2s, v1.2s");
        assertDecoding(0x4ea18400, "add v0.4s, v0.4s, v1.4s");
        assertDecoding(0x4e658484, "add v4.8h, v4.8h, v5.8h");
        assertDecoding(0x4ebf87be, "add v30.4s, v29.4s, v31.4s");
        assertDecoding(0x4ea087de, "add v30.4s, v30.4s, v0.4s");
    }

    @Test
    public void test_addhn_simd() {
        assertDecoding(0x0eb34089, "addhn v9.2s, v4.2d, v19.2d");
    }

    @Test
    public void test_addp_simd() {
        assertDecoding(0x5ef1b800, "addp d0, v0.2d");
        assertDecoding(0x5ef1ba10, "addp d16, v16.2d");
        assertDecoding(0x5ef1b842, "addp d2, v2.2d");
        assertDecoding(0x5ef1b8a5, "addp d5, v5.2d");
        assertDecoding(0x4e66bc0a, "addp v10.8h, v0.8h, v6.8h");
        assertDecoding(0x4e3abc0b, "addp v11.16b, v0.16b, v26.16b");
        assertDecoding(0x0ebcbd2c, "addp v12.2s, v9.2s, v28.2s");
        assertDecoding(0x4e26bcb0, "addp v16.16b, v5.16b, v6.16b");
        assertDecoding(0x0e3cbfb6, "addp v22.8b, v29.8b, v28.8b");
        assertDecoding(0x4ef0be10, "addp v16.2d, v16.2d, v16.2d");
        assertDecoding(0x4ef7bef7, "addp v23.2d, v23.2d, v23.2d");
        assertDecoding(0x4e71bfbe, "addp v30.8h, v29.8h, v17.8h");
        assertDecoding(0x4e24bc66, "addp v6.16b, v3.16b, v4.16b");
    }

    @Test
    public void test_adds() {
        assertDecoding(0x31000400, "adds w0, w0, #0x1");
        assertDecoding(0x2b010000, "adds w0, w0, w1");
        assertDecoding(0x2b011800, "adds w0, w0, w1, lsl #6");
        assertDecoding(0xb1002a60, "adds x0, x19, #0xa");
        assertDecoding(0xab214260, "adds x0, x19, w1, uxtw");
        assertDecoding(0xab000260, "adds x0, x19, x0");
    }

    @Test
    public void test_addv_simd() {
        assertDecoding(0x0e31b800, "addv b0, v0.8b");
        assertDecoding(0x4eb1b800, "addv s0, v0.4s");
        assertDecoding(0x4eb1b881, "addv s1, v4.4s");
        assertDecoding(0x4eb1b8a5, "addv s5, v5.4s");
    }

    @Test
    public void test_adr_with_label() {
        assertDecodingWithPc(0x10229c, 0x300737c0, "adr x0, 0x110995");
        assertDecodingWithPc(0xff0a0, 0x70112900, "adr x0, 0x1215c3");
        assertDecodingWithPc(0x6eac0, 0x50bcec00, "adr x0, 0xfffffffffffe8842");
        assertDecodingWithPc(0xf2050, 0x7006d430, "adr x16, 0xffad7");
        assertDecodingWithPc(0xfa804, 0x70806510, "adr x16, 0xffffffffffffb4a7");
        assertDecodingWithPc(0xfbecc, 0x30ea12fc, "adr x28, 0xd0129");
        assertDecodingWithPc(0xed5fc, 0x30892b3c, "adr x28, 0xfffffffffffffb61");
        assertDecodingWithPc(0xfe708, 0x10000fc4, "adr x4, 0xfe900");
        assertDecodingWithPc(0xec8cc, 0x30887344, "adr x4, 0xffffffffffffd735");
        assertDecodingWithPc(0xec870, 0x301d835f, "adr xzr, 0x1278d9");
        assertDecodingWithPc(0x108f10, 0x701a785f, "adr xzr, 0x13de1b");
        assertDecodingWithPc(0xee544, 0x1089137f, "adr xzr, 0x7b0");
    }

    @Test
    public void test_adrp_with_label() {
        assertDecodingWithPc(0x22ec, 0xd0ffffe0, "adrp x0, 0x0");
        assertDecodingWithPc(0x10e4, 0xf0ffffe0, "adrp x0, 0x0");
        assertDecodingWithPc(0xd2c, 0x90000000, "adrp x0, 0x0");
        assertDecodingWithPc(0x109a38, 0xd0ffffe0, "adrp x0, 0x107000");
        assertDecodingWithPc(0x1077cc, 0x90000000, "adrp x0, 0x107000");
        assertDecodingWithPc(0x472c0, 0xb0b0b000, "adrp x0, 0xffffffff61648000");
        assertDecodingWithPc(0x11efc, 0x90000001, "adrp x1, 0x11000");
        assertDecodingWithPc(0x1235c, 0xf0ffffe1, "adrp x1, 0x11000");
        assertDecodingWithPc(0x103248, 0xf033dc9f, "adrp xzr, 0x67c96000");
        assertDecodingWithPc(0xf0c94, 0xb08d1a1f, "adrp xzr, 0xffffffff1a431000");
    }

    @Test
    public void test_aesd_simd() {
        assertDecoding(0x4e285a00, "aesd v0.16b, v16.16b");
    }

    @Test
    public void test_aese_simd() {
        assertDecoding(0x4e284bc0, "aese v0.16b, v30.16b");
    }

    @Test
    public void test_aesimc_simd() {
        assertDecoding(0x4e287a52, "aesimc v18.16b, v18.16b");
    }

    @Test
    public void test_aesmc_simd() {
        assertDecoding(0x4e286a51, "aesmc v17.16b, v18.16b");
    }

    @Test
    public void test_32_bit_imm_bit_patterns_using_and() {
        assertDecoding(0x12000000, "and w0, w0, #0x1");
        assertDecoding(0x121c0000, "and w0, w0, #0x10");
        assertDecoding(0x12180000, "and w0, w0, #0x100");
        assertDecoding(0x12140000, "and w0, w0, #0x1000");
        assertDecoding(0x12100000, "and w0, w0, #0x10000");
        assertDecoding(0x120c0000, "and w0, w0, #0x100000");
        assertDecoding(0x12080000, "and w0, w0, #0x1000000");
        assertDecoding(0x12040020, "and w0, w1, #0x10000000");
        assertDecoding(0x12008022, "and w2, w1, #0x10001");
        assertDecoding(0x12088000, "and w0, w0, #0x1000100");
        assertDecoding(0x1204c084, "and w4, w4, #0x10101010");
        assertDecoding(0x121d0400, "and w0, w0, #0x18");
        assertDecoding(0x12190400, "and w0, w0, #0x180");
        assertDecoding(0x12150400, "and w0, w0, #0x1800");
        assertDecoding(0x12090580, "and w0, w12, #0x1800000");
        assertDecoding(0x121e0800, "and w0, w0, #0x1c");
        assertDecoding(0x121a0820, "and w0, w1, #0x1c0");
        assertDecoding(0x12160800, "and w0, w0, #0x1c00");
        assertDecoding(0x12120800, "and w0, w0, #0x1c000");
        assertDecoding(0x120e0b60, "and w0, w27, #0x1c0000");
        assertDecoding(0x121f0c00, "and w0, w0, #0x1e");
        assertDecoding(0x12130c00, "and w0, w0, #0x1e000");
        assertDecoding(0x12001000, "and w0, w0, #0x1f");
        assertDecoding(0x121c1021, "and w1, w1, #0x1f0");
        assertDecoding(0x12101061, "and w1, w3, #0x1f0000");
        assertDecoding(0x121d1400, "and w0, w0, #0x1f8");
        assertDecoding(0x121e1800, "and w0, w0, #0x1fc");
        assertDecoding(0x12161ab5, "and w21, w21, #0x1fc00");
        assertDecoding(0x12002000, "and w0, w0, #0x1ff");
        assertDecoding(0x121422e0, "and w0, w23, #0x1ff000");
        assertDecoding(0x12102294, "and w20, w20, #0x1ff0000");
        assertDecoding(0x12192463, "and w3, w3, #0x1ff80");
        assertDecoding(0x12003000, "and w0, w0, #0x1fff");
        assertDecoding(0x12153400, "and w0, w0, #0x1fff800");
        assertDecoding(0x12113421, "and w1, w1, #0x1fff8000");
        assertDecoding(0x121e3ad6, "and w22, w22, #0x1fffc");
        assertDecoding(0x121a3aa0, "and w0, w21, #0x1fffc0");
        assertDecoding(0x121f3f00, "and w0, w24, #0x1fffe");
        assertDecoding(0x121b3c21, "and w1, w1, #0x1fffe0");
        assertDecoding(0x12004000, "and w0, w0, #0x1ffff");
        assertDecoding(0x12184000, "and w0, w0, #0x1ffff00");
        assertDecoding(0x12194443, "and w3, w2, #0x1ffff80");
        assertDecoding(0x12005000, "and w0, w0, #0x1fffff");
        assertDecoding(0x121a5a80, "and w0, w20, #0x1fffffc0");
        assertDecoding(0x121f5c00, "and w0, w0, #0x1fffffe");
        assertDecoding(0x12006000, "and w0, w0, #0x1ffffff");
        assertDecoding(0x121f6e94, "and w20, w20, #0x1ffffffe");
        assertDecoding(0x12007000, "and w0, w0, #0x1fffffff");
        assertDecoding(0x121f0000, "and w0, w0, #0x2");
        assertDecoding(0x121b0000, "and w0, w0, #0x20");
        assertDecoding(0x12170000, "and w0, w0, #0x200");
        assertDecoding(0x12130000, "and w0, w0, #0x2000");
        assertDecoding(0x120f0000, "and w0, w0, #0x20000");
        assertDecoding(0x120b0000, "and w0, w0, #0x200000");
        assertDecoding(0x12070261, "and w1, w19, #0x2000000");
        assertDecoding(0x12030000, "and w0, w0, #0x20000000");
        assertDecoding(0x120f8000, "and w0, w0, #0x20002");
        assertDecoding(0x121b82a6, "and w6, w21, #0x200020");
        assertDecoding(0x12000400, "and w0, w0, #0x3");
        assertDecoding(0x121c0400, "and w0, w0, #0x30");
        assertDecoding(0x12180400, "and w0, w0, #0x300");
        assertDecoding(0x12140680, "and w0, w20, #0x3000");
        assertDecoding(0x12100400, "and w0, w0, #0x30000");
        assertDecoding(0x120c0421, "and w1, w1, #0x300000");
        assertDecoding(0x12040422, "and w2, w1, #0x30000000");
        assertDecoding(0x1200e400, "and w0, w0, #0x33333333");
        assertDecoding(0x121d0a80, "and w0, w20, #0x38");
        assertDecoding(0x12190800, "and w0, w0, #0x380");
        assertDecoding(0x12150822, "and w2, w1, #0x3800");
        assertDecoding(0x121e0dce, "and w14, w14, #0x3c");
        assertDecoding(0x121a0c00, "and w0, w0, #0x3c0");
        assertDecoding(0x121f1021, "and w1, w1, #0x3e");
        assertDecoding(0x12171000, "and w0, w0, #0x3e00");
        assertDecoding(0x12001400, "and w0, w0, #0x3f");
        assertDecoding(0x12141400, "and w0, w0, #0x3f000");
        assertDecoding(0x12101421, "and w1, w1, #0x3f0000");
        assertDecoding(0x121d1821, "and w1, w1, #0x3f8");
        assertDecoding(0x12151800, "and w0, w0, #0x3f800");
        assertDecoding(0x12111821, "and w1, w1, #0x3f8000");
        assertDecoding(0x121e1c21, "and w1, w1, #0x3fc");
        assertDecoding(0x12002400, "and w0, w0, #0x3ff");
        assertDecoding(0x1230a40c, "and w12, w0, #0x3ff03ff");
        assertDecoding(0x12003400, "and w0, w0, #0x3fff");
        assertDecoding(0x121c3661, "and w1, w19, #0x3fff0");
        assertDecoding(0x12183400, "and w0, w0, #0x3fff00");
        assertDecoding(0x121d3820, "and w0, w1, #0x3fff8");
        assertDecoding(0x12004400, "and w0, w0, #0x3ffff");
        assertDecoding(0x12164c00, "and w0, w0, #0x3ffffc00");
        assertDecoding(0x12005400, "and w0, w0, #0x3fffff");
        assertDecoding(0x121e5c01, "and w1, w0, #0x3fffffc");
        assertDecoding(0x12006400, "and w0, w0, #0x3ffffff");
        assertDecoding(0x121f72c0, "and w0, w22, #0x3ffffffe");
        assertDecoding(0x12007400, "and w0, w0, #0x3fffffff");
        assertDecoding(0x121e0000, "and w0, w0, #0x4");
        assertDecoding(0x121a0000, "and w0, w0, #0x40");
        assertDecoding(0x12160000, "and w0, w0, #0x400");
        assertDecoding(0x12120000, "and w0, w0, #0x4000");
        assertDecoding(0x120e0000, "and w0, w0, #0x40000");
        assertDecoding(0x120a0081, "and w1, w4, #0x400000");
        assertDecoding(0x12060000, "and w0, w0, #0x4000000");
        assertDecoding(0x12020000, "and w0, w0, #0x40000000");
        assertDecoding(0x1200f000, "and w0, w0, #0x55555555");
        assertDecoding(0x121f0400, "and w0, w0, #0x6");
        assertDecoding(0x121b0400, "and w0, w0, #0x60");
        assertDecoding(0x12170400, "and w0, w0, #0x600");
        assertDecoding(0x12130440, "and w0, w2, #0x6000");
        assertDecoding(0x120f0700, "and w0, w24, #0x60000");
        assertDecoding(0x120b0401, "and w1, w0, #0x600000");
        assertDecoding(0x12000800, "and w0, w0, #0x7");
        assertDecoding(0x121c0800, "and w0, w0, #0x70");
        assertDecoding(0x12180800, "and w0, w0, #0x700");
        assertDecoding(0x12140801, "and w1, w0, #0x7000");
        assertDecoding(0x120c0aa0, "and w0, w21, #0x700000");
        assertDecoding(0x1238ca53, "and w19, w18, #0x7070707");
        assertDecoding(0x121d0c00, "and w0, w0, #0x78");
        assertDecoding(0x12190c21, "and w1, w1, #0x780");
        assertDecoding(0x12298f4f, "and w15, w26, #0x7800780");
        assertDecoding(0x12121200, "and w0, w16, #0x7c000");
        assertDecoding(0x120a12b1, "and w17, w21, #0x7c00000");
        assertDecoding(0x12131400, "and w0, w0, #0x7e000");
        assertDecoding(0x12001800, "and w0, w0, #0x7f");
        assertDecoding(0x12081a73, "and w19, w19, #0x7f000000");
        assertDecoding(0x1200d800, "and w0, w0, #0x7f7f7f7f");
        assertDecoding(0x121d1c01, "and w1, w0, #0x7f8");
        assertDecoding(0x12091c00, "and w0, w0, #0x7f800000");
        assertDecoding(0x12122020, "and w0, w1, #0x7fc000");
        assertDecoding(0x12002800, "and w0, w0, #0x7ff");
        assertDecoding(0x120c2800, "and w0, w0, #0x7ff00000");
        assertDecoding(0x121a32d6, "and w22, w22, #0x7ffc0");
        assertDecoding(0x12003800, "and w0, w0, #0x7fff");
        assertDecoding(0x121c3800, "and w0, w0, #0x7fff0");
        assertDecoding(0x12103805, "and w5, w0, #0x7fff0000");
        assertDecoding(0x1200b821, "and w1, w1, #0x7fff7fff");
        assertDecoding(0x121f4420, "and w0, w1, #0x7fffe");
        assertDecoding(0x12004800, "and w0, w0, #0x7ffff");
        assertDecoding(0x12005800, "and w0, w0, #0x7fffff");
        assertDecoding(0x121c5800, "and w0, w0, #0x7fffff0");
        assertDecoding(0x12185aa2, "and w2, w21, #0x7fffff00");
        assertDecoding(0x121a62a0, "and w0, w21, #0x7fffffc0");
        assertDecoding(0x121b6420, "and w0, w1, #0x7fffffe0");
        assertDecoding(0x12006863, "and w3, w3, #0x7ffffff");
        assertDecoding(0x121d6c00, "and w0, w0, #0x7ffffff8");
        assertDecoding(0x121e7000, "and w0, w0, #0x7ffffffc");
        assertDecoding(0x12007800, "and w0, w0, #0x7fffffff");
        assertDecoding(0x121d0000, "and w0, w0, #0x8");
        assertDecoding(0x12190000, "and w0, w0, #0x80");
        assertDecoding(0x12150000, "and w0, w0, #0x800");
        assertDecoding(0x12110000, "and w0, w0, #0x8000");
        assertDecoding(0x120d0000, "and w0, w0, #0x80000");
        assertDecoding(0x12090000, "and w0, w0, #0x800000");
        assertDecoding(0x12050000, "and w0, w0, #0x8000000");
        assertDecoding(0x12010000, "and w0, w0, #0x80000000");
        assertDecoding(0x12011c00, "and w0, w0, #0x8000007f");
        assertDecoding(0x12015c21, "and w1, w1, #0x807fffff");
        assertDecoding(0x1231c33d, "and w29, w25, #0x80808080");
        assertDecoding(0x1201a30b, "and w11, w24, #0x80ff80ff");
        assertDecoding(0x12016273, "and w19, w19, #0x80ffffff");
        assertDecoding(0x1239cc6f, "and w15, w3, #0x87878787");
        assertDecoding(0x1201f000, "and w0, w0, #0xaaaaaaaa");
        assertDecoding(0x1225eb21, "and w1, w25, #0xbbbbbbbb");
        assertDecoding(0x12017800, "and w0, w0, #0xbfffffff");
        assertDecoding(0x121e0400, "and w0, w0, #0xc");
        assertDecoding(0x121a0400, "and w0, w0, #0xc0");
        assertDecoding(0x12160400, "and w0, w0, #0xc00");
        assertDecoding(0x12120401, "and w1, w0, #0xc000");
        assertDecoding(0x120a0400, "and w0, w0, #0xc00000");
        assertDecoding(0x12060421, "and w1, w1, #0xc000000");
        assertDecoding(0x12020400, "and w0, w0, #0xc0000000");
        assertDecoding(0x12020884, "and w4, w4, #0xc0000001");
        assertDecoding(0x1212abba, "and w26, w29, #0xc1ffc1ff");
        assertDecoding(0x1202e463, "and w3, w3, #0xcccccccc");
        assertDecoding(0x12027442, "and w2, w2, #0xcfffffff");
        assertDecoding(0x12027a80, "and w0, w20, #0xdfffffff");
        assertDecoding(0x121f0800, "and w0, w0, #0xe");
        assertDecoding(0x121b0800, "and w0, w0, #0xe0");
        assertDecoding(0x12130b0d, "and w13, w24, #0xe000");
        assertDecoding(0x12070821, "and w1, w1, #0xe000000");
        assertDecoding(0x120308a1, "and w1, w5, #0xe0000000");
        assertDecoding(0x12031000, "and w0, w0, #0xe0000003");
        assertDecoding(0x12031c00, "and w0, w0, #0xe000001f");
        assertDecoding(0x12033400, "and w0, w0, #0xe00007ff");
        assertDecoding(0x12235919, "and w25, w8, #0xe00fffff");
        assertDecoding(0x1213cf44, "and w4, w26, #0xe1e1e1e1");
        assertDecoding(0x12037aa0, "and w0, w21, #0xefffffff");
        assertDecoding(0x12000c00, "and w0, w0, #0xf");
        assertDecoding(0x121c0c00, "and w0, w0, #0xf0");
        assertDecoding(0x12180e60, "and w0, w19, #0xf00");
        assertDecoding(0x12140c00, "and w0, w0, #0xf000");
        assertDecoding(0x12100c01, "and w1, w0, #0xf0000");
        assertDecoding(0x120c0c23, "and w3, w1, #0xf00000");
        assertDecoding(0x12080c84, "and w4, w4, #0xf000000");
        assertDecoding(0x12040e62, "and w2, w19, #0xf0000000");
        assertDecoding(0x12041694, "and w20, w20, #0xf0000003");
        assertDecoding(0x120446d2, "and w18, w22, #0xf0003fff");
        assertDecoding(0x12044c21, "and w1, w1, #0xf000ffff");
        assertDecoding(0x1200cc00, "and w0, w0, #0xf0f0f0f");
        assertDecoding(0x1204cc21, "and w1, w1, #0xf0f0f0f0");
        assertDecoding(0x12046e60, "and w0, w19, #0xf0ffffff");
        assertDecoding(0x12047542, "and w2, w10, #0xf3ffffff");
        assertDecoding(0x12047800, "and w0, w0, #0xf7ffffff");
        assertDecoding(0x121d1000, "and w0, w0, #0xf8");
        assertDecoding(0x12191021, "and w1, w1, #0xf80");
        assertDecoding(0x12051000, "and w0, w0, #0xf8000000");
        assertDecoding(0x12051463, "and w3, w3, #0xf8000001");
        assertDecoding(0x12252463, "and w3, w3, #0xf800001f");
        assertDecoding(0x120d9048, "and w8, w2, #0xf800f8");
        assertDecoding(0x12055000, "and w0, w0, #0xf800ffff");
        assertDecoding(0x1235a360, "and w0, w27, #0xf80ff80f");
        assertDecoding(0x12057800, "and w0, w0, #0xfbffffff");
        assertDecoding(0x121e1400, "and w0, w0, #0xfc");
        assertDecoding(0x1216148a, "and w10, w4, #0xfc00");
        assertDecoding(0x12061442, "and w2, w2, #0xfc000000");
        assertDecoding(0x12062800, "and w0, w0, #0xfc00001f");
        assertDecoding(0x12067800, "and w0, w0, #0xfdffffff");
        assertDecoding(0x121f1800, "and w0, w0, #0xfe");
        assertDecoding(0x12171801, "and w1, w0, #0xfe00");
        assertDecoding(0x12131800, "and w0, w0, #0xfe000");
        assertDecoding(0x12071800, "and w0, w0, #0xfe000000");
        assertDecoding(0x123798d9, "and w25, w6, #0xfe00fe00");
        assertDecoding(0x120776d6, "and w22, w22, #0xfe7fffff");
        assertDecoding(0x1207d865, "and w5, w3, #0xfefefefe");
        assertDecoding(0x12077800, "and w0, w0, #0xfeffffff");
        assertDecoding(0x12001c00, "and w0, w0, #0xff");
        assertDecoding(0x121c1c00, "and w0, w0, #0xff0");
        assertDecoding(0x12181c00, "and w0, w0, #0xff00");
        assertDecoding(0x12141c81, "and w1, w4, #0xff000");
        assertDecoding(0x12101c00, "and w0, w0, #0xff0000");
        assertDecoding(0x120c1f7b, "and w27, w27, #0xff00000");
        assertDecoding(0x12081c00, "and w0, w0, #0xff000000");
        assertDecoding(0x12082400, "and w0, w0, #0xff000003");
        assertDecoding(0x12083000, "and w0, w0, #0xff00001f");
        assertDecoding(0x12083c00, "and w0, w0, #0xff0000ff");
        assertDecoding(0x12084c00, "and w0, w0, #0xff000fff");
        assertDecoding(0x12009c00, "and w0, w0, #0xff00ff");
        assertDecoding(0x12089c63, "and w3, w3, #0xff00ff00");
        assertDecoding(0x12085c21, "and w1, w1, #0xff00ffff");
        assertDecoding(0x12087800, "and w0, w0, #0xff7fffff");
        assertDecoding(0x12152329, "and w9, w25, #0xff800");
        assertDecoding(0x12092046, "and w6, w2, #0xff800000");
        assertDecoding(0x12092821, "and w1, w1, #0xff800003");
        assertDecoding(0x12093421, "and w1, w1, #0xff80001f");
        assertDecoding(0x12094000, "and w0, w0, #0xff8000ff");
        assertDecoding(0x12094442, "and w2, w2, #0xff8001ff");
        assertDecoding(0x12095800, "and w0, w0, #0xff803fff");
        assertDecoding(0x12095c00, "and w0, w0, #0xff807fff");
        assertDecoding(0x120972d6, "and w22, w22, #0xff8fffff");
        assertDecoding(0x12097800, "and w0, w0, #0xffbfffff");
        assertDecoding(0x121e2421, "and w1, w1, #0xffc");
        assertDecoding(0x121a2421, "and w1, w1, #0xffc0");
        assertDecoding(0x12122400, "and w0, w0, #0xffc000");
        assertDecoding(0x120a6000, "and w0, w0, #0xffc07fff");
        assertDecoding(0x120a7400, "and w0, w0, #0xffcfffff");
        assertDecoding(0x120a7800, "and w0, w0, #0xffdfffff");
        assertDecoding(0x12172800, "and w0, w0, #0xffe00");
        assertDecoding(0x120b2800, "and w0, w0, #0xffe00000");
        assertDecoding(0x120b4c00, "and w0, w0, #0xffe001ff");
        assertDecoding(0x120b7800, "and w0, w0, #0xffefffff");
        assertDecoding(0x12002c00, "and w0, w0, #0xfff");
        assertDecoding(0x121c2eb7, "and w23, w21, #0xfff0");
        assertDecoding(0x12182c00, "and w0, w0, #0xfff00");
        assertDecoding(0x120c2c00, "and w0, w0, #0xfff00000");
        assertDecoding(0x122c3612, "and w18, w16, #0xfff00003");
        assertDecoding(0x120c6000, "and w0, w0, #0xfff01fff");
        assertDecoding(0x120c6421, "and w1, w1, #0xfff03fff");
        assertDecoding(0x123cac31, "and w17, w1, #0xfff0fff0");
        assertDecoding(0x120c6e65, "and w5, w19, #0xfff0ffff");
        assertDecoding(0x120c7800, "and w0, w0, #0xfff7ffff");
        assertDecoding(0x12193000, "and w0, w0, #0xfff80");
        assertDecoding(0x12113020, "and w0, w1, #0xfff8000");
        assertDecoding(0x120d3000, "and w0, w0, #0xfff80000");
        assertDecoding(0x120d4c00, "and w0, w0, #0xfff8007f");
        assertDecoding(0x120d5800, "and w0, w0, #0xfff803ff");
        assertDecoding(0x120d6129, "and w9, w9, #0xfff80fff");
        assertDecoding(0x120d6421, "and w1, w1, #0xfff81fff");
        assertDecoding(0x120d7126, "and w6, w9, #0xfff8ffff");
        assertDecoding(0x120d7800, "and w0, w0, #0xfffbffff");
        assertDecoding(0x121e3400, "and w0, w0, #0xfffc");
        assertDecoding(0x120e3402, "and w2, w0, #0xfffc0000");
        assertDecoding(0x120e3c00, "and w0, w0, #0xfffc0003");
        assertDecoding(0x120e4800, "and w0, w0, #0xfffc001f");
        assertDecoding(0x120e52d6, "and w22, w22, #0xfffc007f");
        assertDecoding(0x120e5400, "and w0, w0, #0xfffc00ff");
        assertDecoding(0x120e5c00, "and w0, w0, #0xfffc03ff");
        assertDecoding(0x120e6c00, "and w0, w0, #0xfffc3fff");
        assertDecoding(0x120e7000, "and w0, w0, #0xfffc7fff");
        assertDecoding(0x120e7660, "and w0, w19, #0xfffcffff");
        assertDecoding(0x120e7800, "and w0, w0, #0xfffdffff");
        assertDecoding(0x121f3800, "and w0, w0, #0xfffe");
        assertDecoding(0x120f3c00, "and w0, w0, #0xfffe0001");
        assertDecoding(0x120f4800, "and w0, w0, #0xfffe000f");
        assertDecoding(0x120f4c21, "and w1, w1, #0xfffe001f");
        assertDecoding(0x120f5800, "and w0, w0, #0xfffe00ff");
        assertDecoding(0x120f6800, "and w0, w0, #0xfffe0fff");
        assertDecoding(0x120f6c00, "and w0, w0, #0xfffe1fff");
        assertDecoding(0x120f7400, "and w0, w0, #0xfffe7fff");
        assertDecoding(0x120fb942, "and w2, w10, #0xfffefffe");
        assertDecoding(0x120f7800, "and w0, w0, #0xfffeffff");
        assertDecoding(0x12003c00, "and w0, w0, #0xffff");
        assertDecoding(0x12183c00, "and w0, w0, #0xffff00");
        assertDecoding(0x12103c00, "and w0, w0, #0xffff0000");
        assertDecoding(0x12104000, "and w0, w0, #0xffff0001");
        assertDecoding(0x12104400, "and w0, w0, #0xffff0003");
        assertDecoding(0x12104800, "and w0, w0, #0xffff0007");
        assertDecoding(0x12105c00, "and w0, w0, #0xffff00ff");
        assertDecoding(0x12106ab5, "and w21, w21, #0xffff07ff");
        assertDecoding(0x12106c00, "and w0, w0, #0xffff0fff");
        assertDecoding(0x12107042, "and w2, w2, #0xffff1fff");
        assertDecoding(0x12107aa0, "and w0, w21, #0xffff7fff");
        assertDecoding(0x12114020, "and w0, w1, #0xffff8000");
        assertDecoding(0x12114442, "and w2, w2, #0xffff8001");
        assertDecoding(0x12117800, "and w0, w0, #0xffffbfff");
        assertDecoding(0x12125c00, "and w0, w0, #0xffffc03f");
        assertDecoding(0x12126021, "and w1, w1, #0xffffc07f");
        assertDecoding(0x12126442, "and w2, w2, #0xffffc0ff");
        assertDecoding(0x12126ae0, "and w0, w23, #0xffffc1ff");
        assertDecoding(0x12126c42, "and w2, w2, #0xffffc3ff");
        assertDecoding(0x12127753, "and w19, w26, #0xffffcfff");
        assertDecoding(0x12127800, "and w0, w0, #0xffffdfff");
        assertDecoding(0x12134800, "and w0, w0, #0xffffe000");
        assertDecoding(0x12134c63, "and w3, w3, #0xffffe001");
        assertDecoding(0x12136823, "and w3, w1, #0xffffe0ff");
        assertDecoding(0x12136ca5, "and w5, w5, #0xffffe1ff");
        assertDecoding(0x12137000, "and w0, w0, #0xffffe3ff");
        assertDecoding(0x12137400, "and w0, w0, #0xffffe7ff");
        assertDecoding(0x12137800, "and w0, w0, #0xffffefff");
        assertDecoding(0x12004c00, "and w0, w0, #0xfffff");
        assertDecoding(0x121c4c21, "and w1, w1, #0xfffff0");
        assertDecoding(0x12144c00, "and w0, w0, #0xfffff000");
        assertDecoding(0x12146800, "and w0, w0, #0xfffff07f");
        assertDecoding(0x12146e65, "and w5, w19, #0xfffff0ff");
        assertDecoding(0x12147000, "and w0, w0, #0xfffff1ff");
        assertDecoding(0x12147421, "and w1, w1, #0xfffff3ff");
        assertDecoding(0x12147800, "and w0, w0, #0xfffff7ff");
        assertDecoding(0x12155000, "and w0, w0, #0xfffff800");
        assertDecoding(0x12157042, "and w2, w2, #0xfffff8ff");
        assertDecoding(0x12157478, "and w24, w3, #0xfffff9ff");
        assertDecoding(0x12157800, "and w0, w0, #0xfffffbff");
        assertDecoding(0x12165400, "and w0, w0, #0xfffffc00");
        assertDecoding(0x12167400, "and w0, w0, #0xfffffcff");
        assertDecoding(0x12167800, "and w0, w0, #0xfffffdff");
        assertDecoding(0x121f5801, "and w1, w0, #0xfffffe");
        assertDecoding(0x121b5840, "and w0, w2, #0xfffffe0");
        assertDecoding(0x12175800, "and w0, w0, #0xfffffe00");
        assertDecoding(0x12177021, "and w1, w1, #0xfffffe3f");
        assertDecoding(0x12177400, "and w0, w0, #0xfffffe7f");
        assertDecoding(0x12177800, "and w0, w0, #0xfffffeff");
        assertDecoding(0x12005c00, "and w0, w0, #0xffffff");
        assertDecoding(0x12185c00, "and w0, w0, #0xffffff00");
        assertDecoding(0x12186484, "and w4, w4, #0xffffff03");
        assertDecoding(0x12186c21, "and w1, w1, #0xffffff0f");
        assertDecoding(0x12187400, "and w0, w0, #0xffffff3f");
        assertDecoding(0x12187800, "and w0, w0, #0xffffff7f");
        assertDecoding(0x12196000, "and w0, w0, #0xffffff80");
        assertDecoding(0x12196440, "and w0, w2, #0xffffff81");
        assertDecoding(0x12196c00, "and w0, w0, #0xffffff87");
        assertDecoding(0x12197020, "and w0, w1, #0xffffff8f");
        assertDecoding(0x12197400, "and w0, w0, #0xffffff9f");
        assertDecoding(0x12197800, "and w0, w0, #0xffffffbf");
        assertDecoding(0x121a6400, "and w0, w0, #0xffffffc0");
        assertDecoding(0x121a6800, "and w0, w0, #0xffffffc1");
        assertDecoding(0x121a6c00, "and w0, w0, #0xffffffc3");
        assertDecoding(0x121a7000, "and w0, w0, #0xffffffc7");
        assertDecoding(0x121a7400, "and w0, w0, #0xffffffcf");
        assertDecoding(0x121a7800, "and w0, w0, #0xffffffdf");
        assertDecoding(0x121b6800, "and w0, w0, #0xffffffe0");
        assertDecoding(0x121b6c00, "and w0, w0, #0xffffffe1");
        assertDecoding(0x121b7000, "and w0, w0, #0xffffffe3");
        assertDecoding(0x121b7400, "and w0, w0, #0xffffffe7");
        assertDecoding(0x121b7800, "and w0, w0, #0xffffffef");
        assertDecoding(0x12006c00, "and w0, w0, #0xfffffff");
        assertDecoding(0x121c6c00, "and w0, w0, #0xfffffff0");
        assertDecoding(0x121c7000, "and w0, w0, #0xfffffff1");
        assertDecoding(0x121c7400, "and w0, w0, #0xfffffff3");
        assertDecoding(0x121c7800, "and w0, w0, #0xfffffff7");
        assertDecoding(0x121d7000, "and w0, w0, #0xfffffff8");
        assertDecoding(0x121d7400, "and w0, w0, #0xfffffff9");
        assertDecoding(0x121d7800, "and w0, w0, #0xfffffffb");
        assertDecoding(0x121e7400, "and w0, w0, #0xfffffffc");
        assertDecoding(0x121e7800, "and w0, w0, #0xfffffffd");
        assertDecoding(0x121f7800, "and w0, w0, #0xfffffffe");
    }

    @Test
    public void test_64_bit_imm_bit_patterns_using_and() {
        assertDecoding(0x92400000, "and x0, x0, #0x1");
        assertDecoding(0x927c0260, "and x0, x19, #0x10");
        assertDecoding(0x92780000, "and x0, x0, #0x100");
        assertDecoding(0x92740281, "and x1, x20, #0x1000");
        assertDecoding(0x92700001, "and x1, x0, #0x10000");
        assertDecoding(0x926c0277, "and x23, x19, #0x100000");
        assertDecoding(0x92680000, "and x0, x0, #0x1000000");
        assertDecoding(0x92640000, "and x0, x0, #0x10000000");
        assertDecoding(0x926002c0, "and x0, x22, #0x100000000");
        assertDecoding(0x925c0265, "and x5, x19, #0x1000000000");
        assertDecoding(0x924c0021, "and x1, x1, #0x10000000000000");
        assertDecoding(0x92480084, "and x4, x4, #0x100000000000000");
        assertDecoding(0x92180000, "and x0, x0, #0x10000000100");
        assertDecoding(0x9200c201, "and x1, x16, #0x101010101010101");
        assertDecoding(0x92710421, "and x1, x1, #0x18000");
        assertDecoding(0x92610400, "and x0, x0, #0x180000000");
        assertDecoding(0x920d868d, "and x13, x20, #0x18001800180018");
        assertDecoding(0x926e0800, "and x0, x0, #0x1c0000");
        assertDecoding(0x922a0a0d, "and x13, x16, #0x1c0000001c00000");
        assertDecoding(0x92378da6, "and x6, x13, #0x1e001e001e001e00");
        assertDecoding(0x92401000, "and x0, x0, #0x1f");
        assertDecoding(0x927c1063, "and x3, x3, #0x1f0");
        assertDecoding(0x92701000, "and x0, x0, #0x1f0000");
        assertDecoding(0x92009292, "and x18, x20, #0x1f001f001f001f");
        assertDecoding(0x92711400, "and x0, x0, #0x1f8000");
        assertDecoding(0x927e18b4, "and x20, x5, #0x1fc");
        assertDecoding(0x927f1c42, "and x2, x2, #0x1fe");
        assertDecoding(0x92402000, "and x0, x0, #0x1ff");
        assertDecoding(0x925b2f79, "and x25, x27, #0x1ffe000000000");
        assertDecoding(0x92403000, "and x0, x0, #0x1fff");
        assertDecoding(0x926d3778, "and x24, x27, #0x1fff80000");
        assertDecoding(0x927e3801, "and x1, x0, #0x1fffc");
        assertDecoding(0x92523b07, "and x7, x24, #0x1fffc00000000000");
        assertDecoding(0x927f3c00, "and x0, x0, #0x1fffe");
        assertDecoding(0x92404000, "and x0, x0, #0x1ffff");
        assertDecoding(0x921c404a, "and x10, x2, #0x1ffff0001ffff0");
        assertDecoding(0x92405021, "and x1, x1, #0x1fffff");
        assertDecoding(0x92665889, "and x9, x4, #0x1fffffc000000");
        assertDecoding(0x92406280, "and x0, x20, #0x1ffffff");
        assertDecoding(0x92407280, "and x0, x20, #0x1fffffff");
        assertDecoding(0x927c7000, "and x0, x0, #0x1fffffff0");
        assertDecoding(0x927d7421, "and x1, x1, #0x1fffffff8");
        assertDecoding(0x927e7822, "and x2, x1, #0x1fffffffc");
        assertDecoding(0x92408000, "and x0, x0, #0x1ffffffff");
        assertDecoding(0x927c8000, "and x0, x0, #0x1ffffffff0");
        assertDecoding(0x927c9000, "and x0, x0, #0x1fffffffff0");
        assertDecoding(0x9240c000, "and x0, x0, #0x1ffffffffffff");
        assertDecoding(0x9240d000, "and x0, x0, #0x1fffffffffffff");
        assertDecoding(0x927ad800, "and x0, x0, #0x1fffffffffffffc0");
        assertDecoding(0x9240e000, "and x0, x0, #0x1ffffffffffffff");
        assertDecoding(0x927de421, "and x1, x1, #0x1ffffffffffffff8");
        assertDecoding(0x9240f021, "and x1, x1, #0x1fffffffffffffff");
        assertDecoding(0x927f0000, "and x0, x0, #0x2");
        assertDecoding(0x927b0000, "and x0, x0, #0x20");
        assertDecoding(0x92770000, "and x0, x0, #0x200");
        assertDecoding(0x92730280, "and x0, x20, #0x2000");
        assertDecoding(0x926b0000, "and x0, x0, #0x200000");
        assertDecoding(0x92670001, "and x1, x0, #0x2000000");
        assertDecoding(0x92630281, "and x1, x20, #0x20000000");
        assertDecoding(0x925f0281, "and x1, x20, #0x200000000");
        assertDecoding(0x924f0120, "and x0, x9, #0x2000000000000");
        assertDecoding(0x921f0000, "and x0, x0, #0x200000002");
        assertDecoding(0x923f82a4, "and x4, x21, #0x2000200020002");
        assertDecoding(0x92400400, "and x0, x0, #0x3");
        assertDecoding(0x927c0400, "and x0, x0, #0x30");
        assertDecoding(0x92780780, "and x0, x28, #0x300");
        assertDecoding(0x92700400, "and x0, x0, #0x30000");
        assertDecoding(0x9200e400, "and x0, x0, #0x3333333333333333");
        assertDecoding(0x927d0ad6, "and x22, x22, #0x38");
        assertDecoding(0x92710861, "and x1, x3, #0x38000");
        assertDecoding(0x92650a73, "and x19, x19, #0x38000000");
        assertDecoding(0x9225c92c, "and x12, x9, #0x3838383838383838");
        assertDecoding(0x927e0e73, "and x19, x19, #0x3c");
        assertDecoding(0x92620e1a, "and x26, x16, #0x3c0000000");
        assertDecoding(0x92731000, "and x0, x0, #0x3e000");
        assertDecoding(0x923712a8, "and x8, x21, #0x3e0000003e00");
        assertDecoding(0x92401400, "and x0, x0, #0x3f");
        assertDecoding(0x927c1402, "and x2, x0, #0x3f0");
        assertDecoding(0x92701402, "and x2, x0, #0x3f0000");
        assertDecoding(0x92751800, "and x0, x0, #0x3f800");
        assertDecoding(0x92651801, "and x1, x0, #0x3f8000000");
        assertDecoding(0x92761c20, "and x0, x1, #0x3fc00");
        assertDecoding(0x92402400, "and x0, x0, #0x3ff");
        assertDecoding(0x92782780, "and x0, x28, #0x3ff00");
        assertDecoding(0x92792800, "and x0, x0, #0x3ff80");
        assertDecoding(0x92722c85, "and x5, x4, #0x3ffc000");
        assertDecoding(0x92403400, "and x0, x0, #0x3fff");
        assertDecoding(0x92404400, "and x0, x0, #0x3ffff");
        assertDecoding(0x926445d5, "and x21, x14, #0x3ffff0000000");
        assertDecoding(0x923c4725, "and x5, x25, #0x3ffff0003ffff0");
        assertDecoding(0x927d4819, "and x25, x0, #0x3ffff8");
        assertDecoding(0x92405421, "and x1, x1, #0x3fffff");
        assertDecoding(0x9240648a, "and x10, x4, #0x3ffffff");
        assertDecoding(0x927c6484, "and x4, x4, #0x3ffffff0");
        assertDecoding(0x926968ba, "and x26, x5, #0x3ffffff800000");
        assertDecoding(0x927e6ea0, "and x0, x21, #0x3ffffffc");
        assertDecoding(0x92407400, "and x0, x0, #0x3fffffff");
        assertDecoding(0x927c7400, "and x0, x0, #0x3fffffff0");
        assertDecoding(0x927e7c63, "and x3, x3, #0x3fffffffc");
        assertDecoding(0x92408400, "and x0, x0, #0x3ffffffff");
        assertDecoding(0x927c8400, "and x0, x0, #0x3ffffffff0");
        assertDecoding(0x927f9021, "and x1, x1, #0x3ffffffffe");
        assertDecoding(0x9240b442, "and x2, x2, #0x3fffffffffff");
        assertDecoding(0x9240e400, "and x0, x0, #0x3ffffffffffffff");
        assertDecoding(0x927ce421, "and x1, x1, #0x3ffffffffffffff0");
        assertDecoding(0x9240f400, "and x0, x0, #0x3fffffffffffffff");
        assertDecoding(0x927e0000, "and x0, x0, #0x4");
        assertDecoding(0x927a0001, "and x1, x0, #0x40");
        assertDecoding(0x92760021, "and x1, x1, #0x400");
        assertDecoding(0x92720021, "and x1, x1, #0x4000");
        assertDecoding(0x926e0001, "and x1, x0, #0x40000");
        assertDecoding(0x926a0320, "and x0, x25, #0x400000");
        assertDecoding(0x92660043, "and x3, x2, #0x4000000");
        assertDecoding(0x92620281, "and x1, x20, #0x40000000");
        assertDecoding(0x925e0321, "and x1, x25, #0x400000000");
        assertDecoding(0x924e0121, "and x1, x9, #0x4000000000000");
        assertDecoding(0x92460021, "and x1, x1, #0x400000000000000");
        assertDecoding(0x92420065, "and x5, x3, #0x4000000000000000");
        assertDecoding(0x920a0052, "and x18, x2, #0x40000000400000");

        assertDecoding(0x9200f000, "and x0, x0, #0x5555555555555555");
        assertDecoding(0x927f0660, "and x0, x19, #0x6");
        assertDecoding(0x927b0739, "and x25, x25, #0x60");
        assertDecoding(0x92730415, "and x21, x0, #0x6000");
        assertDecoding(0x92670701, "and x1, x24, #0x6000000");
        assertDecoding(0x922b8587, "and x7, x12, #0x60006000600060");
        assertDecoding(0x922bc724, "and x4, x25, #0x6060606060606060");
        assertDecoding(0x921fe57c, "and x28, x11, #0x6666666666666666");
        assertDecoding(0x92400800, "and x0, x0, #0x7");
        assertDecoding(0x927c0b39, "and x25, x25, #0x70");
        assertDecoding(0x92740814, "and x20, x0, #0x7000");
        assertDecoding(0x923808c8, "and x8, x6, #0x70000000700");
        assertDecoding(0x9230e8cd, "and x13, x6, #0x7777777777777777");
        assertDecoding(0x92690e80, "and x0, x20, #0x7800000");
        assertDecoding(0x924e1260, "and x0, x19, #0x7c000000000000");
        assertDecoding(0x921a11b0, "and x16, x13, #0x7c0000007c0");
        assertDecoding(0x92401800, "and x0, x0, #0x7f");
        assertDecoding(0x927c1800, "and x0, x0, #0x7f0");
        assertDecoding(0x92741822, "and x2, x1, #0x7f000");
        assertDecoding(0x92701a80, "and x0, x20, #0x7f0000");
        assertDecoding(0x9200d800, "and x0, x0, #0x7f7f7f7f7f7f7f7f");
        assertDecoding(0x927d1d2a, "and x10, x9, #0x7f8");
        assertDecoding(0x92691c42, "and x2, x2, #0x7f800000");
        assertDecoding(0x92402800, "and x0, x0, #0x7ff");
        assertDecoding(0x927c2800, "and x0, x0, #0x7ff0");
        assertDecoding(0x924c2800, "and x0, x0, #0x7ff0000000000000");
        assertDecoding(0x92663042, "and x2, x2, #0x7ffc000000");
        assertDecoding(0x92733401, "and x1, x0, #0x7ffe000");
        assertDecoding(0x92403800, "and x0, x0, #0x7fff");
        assertDecoding(0x92503b00, "and x0, x24, #0x7fff000000000000");
        assertDecoding(0x927e4000, "and x0, x0, #0x7fffc");
        assertDecoding(0x92404800, "and x0, x0, #0x7ffff");
        assertDecoding(0x927c4800, "and x0, x0, #0x7ffff0");
        assertDecoding(0x9264482d, "and x13, x1, #0x7ffff0000000");
        assertDecoding(0x923a526e, "and x14, x19, #0x7ffffc007ffffc0");
        assertDecoding(0x926d5cc6, "and x6, x6, #0x7fffff80000");
        assertDecoding(0x927e62a0, "and x0, x21, #0x7fffffc");
        assertDecoding(0x92406821, "and x1, x1, #0x7ffffff");
        assertDecoding(0x92407800, "and x0, x0, #0x7fffffff");
        assertDecoding(0x927c7800, "and x0, x0, #0x7fffffff0");
        assertDecoding(0x92607a81, "and x1, x20, #0x7fffffff00000000");
        assertDecoding(0x927d7c00, "and x0, x0, #0x7fffffff8");
        assertDecoding(0x927f8400, "and x0, x0, #0x7fffffffe");
        assertDecoding(0x92408821, "and x1, x1, #0x7ffffffff");
        assertDecoding(0x927c8800, "and x0, x0, #0x7ffffffff0");
        assertDecoding(0x92409800, "and x0, x0, #0x7fffffffff");
        assertDecoding(0x927c9b8b, "and x11, x28, #0x7fffffffff0");
        assertDecoding(0x9240c800, "and x0, x0, #0x7ffffffffffff");
        assertDecoding(0x927ce800, "and x0, x0, #0x7ffffffffffffff0");
        assertDecoding(0x9240f800, "and x0, x0, #0x7fffffffffffffff");
        assertDecoding(0x927d0000, "and x0, x0, #0x8");
        assertDecoding(0x92790001, "and x1, x0, #0x80");
        assertDecoding(0x927502e0, "and x0, x23, #0x800");
        assertDecoding(0x92710000, "and x0, x0, #0x8000");
        assertDecoding(0x92690081, "and x1, x4, #0x800000");
        assertDecoding(0x92650281, "and x1, x20, #0x8000000");
        assertDecoding(0x92610000, "and x0, x0, #0x80000000");
        assertDecoding(0x924d0081, "and x1, x4, #0x8000000000000");
        assertDecoding(0x92490043, "and x3, x2, #0x80000000000000");
        assertDecoding(0x92410000, "and x0, x0, #0x8000000000000000");
        assertDecoding(0x924181ad, "and x13, x13, #0x80000000ffffffff");
        assertDecoding(0x9241c021, "and x1, x1, #0x8000ffffffffffff");
        assertDecoding(0x920144b7, "and x23, x5, #0x8001ffff8001ffff");
        assertDecoding(0x9241d021, "and x1, x1, #0x800fffffffffffff");
        assertDecoding(0x9241e017, "and x23, x0, #0x80ffffffffffffff");
        assertDecoding(0x9231d2b7, "and x23, x21, #0x8f8f8f8f8f8f8f8f");
        assertDecoding(0x920de53b, "and x27, x9, #0x9999999999999999");
        assertDecoding(0x9235f19d, "and x29, x12, #0xaaaaaaaaaaaaaaaa");
        assertDecoding(0x927e0420, "and x0, x1, #0xc");
        assertDecoding(0x927a0400, "and x0, x0, #0xc0");
        assertDecoding(0x92760631, "and x17, x17, #0xc00");
        assertDecoding(0x926a04e0, "and x0, x7, #0xc00000");
        assertDecoding(0x927f0820, "and x0, x1, #0xe");
        assertDecoding(0x9243894a, "and x10, x10, #0xe0000000ffffffff");
        assertDecoding(0x92236ec9, "and x9, x22, #0xe1ffffffe1ffffff");
        assertDecoding(0x92037014, "and x20, x0, #0xe3ffffffe3ffffff");
        assertDecoding(0x92400c00, "and x0, x0, #0xf");
        assertDecoding(0x927c0c00, "and x0, x0, #0xf0");
        assertDecoding(0x92740c00, "and x0, x0, #0xf000");
        assertDecoding(0x92700c20, "and x0, x1, #0xf0000");
        assertDecoding(0x92680ce7, "and x7, x7, #0xf000000");
        assertDecoding(0x92640c03, "and x3, x0, #0xf0000000");
        assertDecoding(0x92440eb5, "and x21, x21, #0xf000000000000000");
        assertDecoding(0x9244c04b, "and x11, x2, #0xf0001fffffffffff");
        assertDecoding(0x92088de9, "and x9, x15, #0xf000f000f000f00");
        assertDecoding(0x9200cc00, "and x0, x0, #0xf0f0f0f0f0f0f0f");
        assertDecoding(0x927d1000, "and x0, x0, #0xf8");
        assertDecoding(0x9245914a, "and x10, x10, #0xf8000000ffffffff");
        assertDecoding(0x9245cd77, "and x23, x11, #0xf8007fffffffffff");
        assertDecoding(0x923dd833, "and x19, x1, #0xfbfbfbfbfbfbfbfb");
        assertDecoding(0x927e154a, "and x10, x10, #0xfc");
        assertDecoding(0x924a15a8, "and x8, x13, #0xfc0000000000000");
        assertDecoding(0x92464af3, "and x19, x23, #0xfc00000000001fff");
        assertDecoding(0x9246b93d, "and x29, x9, #0xfc0001ffffffffff");
        assertDecoding(0x92169b89, "and x9, x28, #0xfc01fc01fc01fc01");
        assertDecoding(0x92471a80, "and x0, x20, #0xfe00000000000000");
        assertDecoding(0x9247994a, "and x10, x10, #0xfe000000ffffffff");
        assertDecoding(0x9227304e, "and x14, x2, #0xfe00003ffe00003f");
        assertDecoding(0x92073ff4, "and x20, xzr, #0xfe0001fffe0001ff");
        assertDecoding(0x921799a9, "and x9, x13, #0xfe00fe00fe00fe00");
        assertDecoding(0x92401c00, "and x0, x0, #0xff");
        assertDecoding(0x92781c00, "and x0, x0, #0xff00");
        assertDecoding(0x92701c21, "and x1, x1, #0xff0000");
        assertDecoding(0x92681c00, "and x0, x0, #0xff000000");
        assertDecoding(0x92601c00, "and x0, x0, #0xff00000000");
        assertDecoding(0x92581d80, "and x0, x12, #0xff0000000000");
        assertDecoding(0x92501c00, "and x0, x0, #0xff000000000000");
        assertDecoding(0x92181c45, "and x5, x2, #0xff000000ff00");
        assertDecoding(0x92285a1e, "and x30, x16, #0xff007fffff007fff");
        assertDecoding(0x92009d8c, "and x12, x12, #0xff00ff00ff00ff");
        assertDecoding(0x92089c00, "and x0, x0, #0xff00ff00ff00ff00");
        assertDecoding(0x9208a610, "and x16, x16, #0xff03ff03ff03ff03");
        assertDecoding(0x927d2263, "and x3, x19, #0xff8");
        assertDecoding(0x92497674, "and x20, x19, #0xff800000001fffff");
        assertDecoding(0x924aa58c, "and x12, x12, #0xffc00000ffffffff");
        assertDecoding(0x924ba424, "and x4, x1, #0xffe000007fffffff");
        assertDecoding(0x920b52f9, "and x25, x23, #0xffe003ffffe003ff");
        assertDecoding(0x924bf821, "and x1, x1, #0xffefffffffffffff");
        assertDecoding(0x92402c00, "and x0, x0, #0xfff");
        assertDecoding(0x92782c23, "and x3, x1, #0xfff00");
        assertDecoding(0x926c2c21, "and x1, x1, #0xfff00000");
        assertDecoding(0x924c66be, "and x30, x21, #0xfff0000000003fff");
        assertDecoding(0x924c9705, "and x5, x24, #0xfff0000003ffffff");
        assertDecoding(0x92182f63, "and x3, x27, #0xfff00000fff00");
        assertDecoding(0x920c527e, "and x30, x19, #0xfff001fffff001ff");
        assertDecoding(0x924cf801, "and x1, x0, #0xfff7ffffffffffff");
        assertDecoding(0x924d5021, "and x1, x1, #0xfff80000000000ff");
        assertDecoding(0x924db1ef, "and x15, x15, #0xfff80000ffffffff");
        assertDecoding(0x924df000, "and x0, x0, #0xfff8ffffffffffff");
        assertDecoding(0x924df800, "and x0, x0, #0xfffbffffffffffff");
        assertDecoding(0x927e3400, "and x0, x0, #0xfffc");
        assertDecoding(0x920e4d62, "and x2, x11, #0xfffc003ffffc003f");
        assertDecoding(0x924ef820, "and x0, x1, #0xfffdffffffffffff");
        assertDecoding(0x921338b0, "and x16, x5, #0xfffe0000fffe000");
        assertDecoding(0x924ff920, "and x0, x9, #0xfffeffffffffffff");
        assertDecoding(0x92403c00, "and x0, x0, #0xffff");
        assertDecoding(0x927c3c84, "and x4, x4, #0xffff0");
        assertDecoding(0x92703c00, "and x0, x0, #0xffff0000");
        assertDecoding(0x92683c21, "and x1, x1, #0xffff000000");
        assertDecoding(0x92503c60, "and x0, x3, #0xffff000000000000");
        assertDecoding(0x92505c00, "and x0, x0, #0xffff0000000000ff");
        assertDecoding(0x92519de5, "and x5, x15, #0xffff8000007fffff");
        assertDecoding(0x9253c9ad, "and x13, x13, #0xffffe000ffffffff");
        assertDecoding(0x92404c00, "and x0, x0, #0xfffff");
        assertDecoding(0x927c4c00, "and x0, x0, #0xfffff0");
        assertDecoding(0x92744c00, "and x0, x0, #0xfffff000");
        assertDecoding(0x92544c40, "and x0, x2, #0xfffff00000000000");
        assertDecoding(0x9254586b, "and x11, x3, #0xfffff00000000007");
        assertDecoding(0x927d5320, "and x0, x25, #0xfffff8");
        assertDecoding(0x92556fd4, "and x20, x30, #0xfffff8000000007f");
        assertDecoding(0x9255a264, "and x4, x19, #0xfffff800000fffff");
        assertDecoding(0x92765400, "and x0, x0, #0xfffffc00");
        assertDecoding(0x9256a4c6, "and x6, x6, #0xfffffc00000fffff");
        assertDecoding(0x9256d56b, "and x11, x11, #0xfffffc00ffffffff");
        assertDecoding(0x92405c00, "and x0, x0, #0xffffff");
        assertDecoding(0x92705c42, "and x2, x2, #0xffffff0000");
        assertDecoding(0x926c5c00, "and x0, x0, #0xffffff00000");
        assertDecoding(0x92585c23, "and x3, x1, #0xffffff0000000000");
        assertDecoding(0x9258798d, "and x13, x12, #0xffffff000000007f");
        assertDecoding(0x925883d1, "and x17, x30, #0xffffff00000001ff");
        assertDecoding(0x9258bc21, "and x1, x1, #0xffffff0000ffffff");
        assertDecoding(0x92796360, "and x0, x27, #0xffffff80");
        assertDecoding(0x92596821, "and x1, x1, #0xffffff8000000003");
        assertDecoding(0x9259a442, "and x2, x2, #0xffffff800001ffff");
        assertDecoding(0x9259e14a, "and x10, x10, #0xffffff80ffffffff");
        assertDecoding(0x927a6421, "and x1, x1, #0xffffffc0");
        assertDecoding(0x925a6c21, "and x1, x1, #0xffffffc000000003");
        assertDecoding(0x925ad042, "and x2, x2, #0xffffffc007ffffff");
        assertDecoding(0x925ae400, "and x0, x0, #0xffffffc0ffffffff");
        assertDecoding(0x925b7021, "and x1, x1, #0xffffffe000000003");
        assertDecoding(0x925be96b, "and x11, x11, #0xffffffe0ffffffff");
        assertDecoding(0x923b7603, "and x3, x16, #0xffffffe7ffffffe7");
        assertDecoding(0x92406c00, "and x0, x0, #0xfffffff");
        assertDecoding(0x927c6c00, "and x0, x0, #0xfffffff0");
        assertDecoding(0x927d7021, "and x1, x1, #0xfffffff8");
        assertDecoding(0x925df14a, "and x10, x10, #0xfffffff8ffffffff");
        assertDecoding(0x927e7400, "and x0, x0, #0xfffffffc");
        assertDecoding(0x925e74ef, "and x15, x7, #0xfffffffc00000000");
        assertDecoding(0x925e7c00, "and x0, x0, #0xfffffffc00000003");
        assertDecoding(0x925ed651, "and x17, x18, #0xfffffffc00ffffff");
        assertDecoding(0x925ef421, "and x1, x1, #0xfffffffcffffffff");
        assertDecoding(0x927f7821, "and x1, x1, #0xfffffffe");
        assertDecoding(0x925fe821, "and x1, x1, #0xfffffffe0fffffff");
        assertDecoding(0x925ff400, "and x0, x0, #0xfffffffe7fffffff");
        assertDecoding(0x925ff94a, "and x10, x10, #0xfffffffeffffffff");
        assertDecoding(0x92407c00, "and x0, x0, #0xffffffff");
        assertDecoding(0x927c7c00, "and x0, x0, #0xffffffff0");
        assertDecoding(0x92707c00, "and x0, x0, #0xffffffff0000");
        assertDecoding(0x92607c00, "and x0, x0, #0xffffffff00000000");
        assertDecoding(0x9260814a, "and x10, x10, #0xffffffff00000001");
        assertDecoding(0x92608400, "and x0, x0, #0xffffffff00000003");
        assertDecoding(0x9260894a, "and x10, x10, #0xffffffff00000007");
        assertDecoding(0x92608c00, "and x0, x0, #0xffffffff0000000f");
        assertDecoding(0x926091ce, "and x14, x14, #0xffffffff0000001f");
        assertDecoding(0x92609800, "and x0, x0, #0xffffffff0000007f");
        assertDecoding(0x92609c21, "and x1, x1, #0xffffffff000000ff");
        assertDecoding(0x9260a5ad, "and x13, x13, #0xffffffff000003ff");
        assertDecoding(0x9260b14a, "and x10, x10, #0xffffffff00001fff");
        assertDecoding(0x9260bc00, "and x0, x0, #0xffffffff0000ffff");
        assertDecoding(0x9260c94a, "and x10, x10, #0xffffffff0007ffff");
        assertDecoding(0x9260d54a, "and x10, x10, #0xffffffff003fffff");
        assertDecoding(0x9260dc00, "and x0, x0, #0xffffffff00ffffff");
        assertDecoding(0x9260e12b, "and x11, x9, #0xffffffff01ffffff");
        assertDecoding(0x9260e800, "and x0, x0, #0xffffffff07ffffff");
        assertDecoding(0x9260ec01, "and x1, x0, #0xffffffff0fffffff");
        assertDecoding(0x9260f18c, "and x12, x12, #0xffffffff1fffffff");
        assertDecoding(0x9260f422, "and x2, x1, #0xffffffff3fffffff");
        assertDecoding(0x9260f801, "and x1, x0, #0xffffffff7fffffff");
        assertDecoding(0x92618042, "and x2, x2, #0xffffffff80000000");
        assertDecoding(0x9261f842, "and x2, x2, #0xffffffffbfffffff");
        assertDecoding(0x92629021, "and x1, x1, #0xffffffffc0000007");
        assertDecoding(0x9264f801, "and x1, x0, #0xfffffffff7ffffff");
        assertDecoding(0x92669400, "and x0, x0, #0xfffffffffc000000");
        assertDecoding(0x9266bc84, "and x4, x4, #0xfffffffffc0003ff");
        assertDecoding(0x9266d021, "and x1, x1, #0xfffffffffc007fff");
        assertDecoding(0x9266f800, "and x0, x0, #0xfffffffffdffffff");
        assertDecoding(0x927f9821, "and x1, x1, #0xfffffffffe");
        assertDecoding(0x92679800, "and x0, x0, #0xfffffffffe000000");
        assertDecoding(0x9267f800, "and x0, x0, #0xfffffffffeffffff");
        assertDecoding(0x92409c00, "and x0, x0, #0xffffffffff");
        assertDecoding(0x9268f800, "and x0, x0, #0xffffffffff7fffff");
        assertDecoding(0x9269a2b5, "and x21, x21, #0xffffffffff800000");
        assertDecoding(0x9269b000, "and x0, x0, #0xffffffffff80000f");
        assertDecoding(0x9269d000, "and x0, x0, #0xffffffffff800fff");
        assertDecoding(0x9269f800, "and x0, x0, #0xffffffffffbfffff");
        assertDecoding(0x926af800, "and x0, x0, #0xffffffffffdfffff");
        assertDecoding(0x926bf800, "and x0, x0, #0xffffffffffefffff");
        assertDecoding(0x926cac21, "and x1, x1, #0xfffffffffff00000");
        assertDecoding(0x926cec00, "and x0, x0, #0xfffffffffff0ffff");
        assertDecoding(0x926cf400, "and x0, x0, #0xfffffffffff3ffff");
        assertDecoding(0x926cf800, "and x0, x0, #0xfffffffffff7ffff");
        assertDecoding(0x926df800, "and x0, x0, #0xfffffffffffbffff");
        assertDecoding(0x926eb414, "and x20, x0, #0xfffffffffffc0000");
        assertDecoding(0x926ec000, "and x0, x0, #0xfffffffffffc0007");
        assertDecoding(0x926ed000, "and x0, x0, #0xfffffffffffc007f");
        assertDecoding(0x926ee842, "and x2, x2, #0xfffffffffffc1fff");
        assertDecoding(0x926ef821, "and x1, x1, #0xfffffffffffdffff");
        assertDecoding(0x926fd400, "and x0, x0, #0xfffffffffffe007f");
        assertDecoding(0x926fd800, "and x0, x0, #0xfffffffffffe00ff");
        assertDecoding(0x926fdc00, "and x0, x0, #0xfffffffffffe01ff");
        assertDecoding(0x926fe400, "and x0, x0, #0xfffffffffffe07ff");
        assertDecoding(0x926fe800, "and x0, x0, #0xfffffffffffe0fff");
        assertDecoding(0x926ff000, "and x0, x0, #0xfffffffffffe3fff");
        assertDecoding(0x926ff800, "and x0, x0, #0xfffffffffffeffff");
        assertDecoding(0x9240bc00, "and x0, x0, #0xffffffffffff");
        assertDecoding(0x9270bc00, "and x0, x0, #0xffffffffffff0000");
        assertDecoding(0x9270c800, "and x0, x0, #0xffffffffffff0007");
        assertDecoding(0x9270d800, "and x0, x0, #0xffffffffffff007f");
        assertDecoding(0x9270dc00, "and x0, x0, #0xffffffffffff00ff");
        assertDecoding(0x9270f800, "and x0, x0, #0xffffffffffff7fff");
        assertDecoding(0x9271c000, "and x0, x0, #0xffffffffffff8000");
        assertDecoding(0x9271f800, "and x0, x0, #0xffffffffffffbfff");
        assertDecoding(0x9272c6b5, "and x21, x21, #0xffffffffffffc000");
        assertDecoding(0x9272f235, "and x21, x17, #0xffffffffffffc7ff");
        assertDecoding(0x9272f421, "and x1, x1, #0xffffffffffffcfff");
        assertDecoding(0x9272f821, "and x1, x1, #0xffffffffffffdfff");
        assertDecoding(0x9273c800, "and x0, x0, #0xffffffffffffe000");
        assertDecoding(0x9273f800, "and x0, x0, #0xffffffffffffefff");
        assertDecoding(0x9240cc00, "and x0, x0, #0xfffffffffffff");
        assertDecoding(0x9274cc00, "and x0, x0, #0xfffffffffffff000");
        assertDecoding(0x9274f821, "and x1, x1, #0xfffffffffffff7ff");
        assertDecoding(0x9275d294, "and x20, x20, #0xfffffffffffff800");
        assertDecoding(0x9275f800, "and x0, x0, #0xfffffffffffffbff");
        assertDecoding(0x9276d400, "and x0, x0, #0xfffffffffffffc00");
        assertDecoding(0x9276f800, "and x0, x0, #0xfffffffffffffdff");
        assertDecoding(0x9277d800, "and x0, x0, #0xfffffffffffffe00");
        assertDecoding(0x9277f6c1, "and x1, x22, #0xfffffffffffffe7f");
        assertDecoding(0x9277f800, "and x0, x0, #0xfffffffffffffeff");
        assertDecoding(0x9240dc00, "and x0, x0, #0xffffffffffffff");
        assertDecoding(0x9278dc00, "and x0, x0, #0xffffffffffffff00");
        assertDecoding(0x9278f800, "and x0, x0, #0xffffffffffffff7f");
        assertDecoding(0x9279e340, "and x0, x26, #0xffffffffffffff80");
        assertDecoding(0x9279f000, "and x0, x0, #0xffffffffffffff8f");
        assertDecoding(0x9279f400, "and x0, x0, #0xffffffffffffff9f");
        assertDecoding(0x9279f800, "and x0, x0, #0xffffffffffffffbf");
        assertDecoding(0x927ae400, "and x0, x0, #0xffffffffffffffc0");
        assertDecoding(0x927af800, "and x0, x0, #0xffffffffffffffdf");
        assertDecoding(0x927be800, "and x0, x0, #0xffffffffffffffe0");
        assertDecoding(0x927bec22, "and x2, x1, #0xffffffffffffffe1");
        assertDecoding(0x927bf800, "and x0, x0, #0xffffffffffffffef");
        assertDecoding(0x9240ec21, "and x1, x1, #0xfffffffffffffff");
        assertDecoding(0x927cec00, "and x0, x0, #0xfffffffffffffff0");
        assertDecoding(0x927cf401, "and x1, x0, #0xfffffffffffffff3");
        assertDecoding(0x927cf800, "and x0, x0, #0xfffffffffffffff7");
        assertDecoding(0x927df000, "and x0, x0, #0xfffffffffffffff8");
        assertDecoding(0x927df800, "and x0, x0, #0xfffffffffffffffb");
        assertDecoding(0x927ef400, "and x0, x0, #0xfffffffffffffffc");
        assertDecoding(0x927ef800, "and x0, x0, #0xfffffffffffffffd");
        assertDecoding(0x927ff800, "and x0, x0, #0xfffffffffffffffe");
    }

    @Test
    public void test_and() {
        // Forms
        // Register 31
        assertDecoding(0x927cec3f, "and sp, x1, #0xfffffffffffffff0");
        assertDecoding(0x92157b7f, "and sp, x27, #0xfffffbfffffffbff");
        assertDecoding(0x9233a8df, "and sp, x6, #0xe0ffe0ffe0ffe0ff");
        assertDecoding(0x0a9f536f, "and w15, w27, wzr, asr #20");
        assertDecoding(0x0ad42bf4, "and w20, wzr, w20, ror #10");
        assertDecoding(0x0acb439f, "and wzr, w28, w11, ror #16");
        assertDecoding(0x8a9f9d8e, "and x14, x12, xzr, asr #39");
        assertDecoding(0x8ac76d7f, "and xzr, x11, x7, ror #27");
        assertDecoding(0x8ade1f1f, "and xzr, x24, x30, ror #7");
        assertDecoding(0x8a45a3ff, "and xzr, xzr, x5, lsr #40");

        // Rest
        assertDecoding(0x12180000, "and w0, w0, #0x100");
        assertDecoding(0x12011c00, "and w0, w0, #0x8000007f");
        assertDecoding(0x12017800, "and w0, w0, #0xbfffffff");
        assertDecoding(0x121f7800, "and w0, w0, #0xfffffffe");
        assertDecoding(0x0a010000, "and w0, w0, w1");
        assertDecoding(0x0a810800, "and w0, w0, w1, asr #2");
        assertDecoding(0x0a013c00, "and w0, w0, w1, lsl #15");
        assertDecoding(0x0a413800, "and w0, w0, w1, lsr #14");
        assertDecoding(0x0a827800, "and w0, w0, w2, asr #30");
        assertDecoding(0x0a020400, "and w0, w0, w2, lsl #1");
        assertDecoding(0x0a817d81, "and w1, w12, w1, asr #31");

        assertDecoding(0x8a000021, "and x1, x1, x0");
        assertDecoding(0x92780000, "and x0, x0, #0x100");
        assertDecoding(0x8a000400, "and x0, x0, x0, lsl #1");
        assertDecoding(0x8a000800, "and x0, x0, x0, lsl #2");
        assertDecoding(0x8a001000, "and x0, x0, x0, lsl #4");
        assertDecoding(0x8a002000, "and x0, x0, x0, lsl #8");
        assertDecoding(0x8a013c00, "and x0, x0, x1, lsl #15");
        assertDecoding(0x8a004000, "and x0, x0, x0, lsl #16");
        assertDecoding(0x8a008000, "and x0, x0, x0, lsl #32");
        assertDecoding(0x8a8a8a00, "and x0, x16, x10, asr #34");
        assertDecoding(0x8a800821, "and x1, x1, x0, asr #2");
        assertDecoding(0x8a010421, "and x1, x1, x1, lsl #1");
        assertDecoding(0x8a014021, "and x1, x1, x1, lsl #16");
        assertDecoding(0x8a010821, "and x1, x1, x1, lsl #2");
        assertDecoding(0x8a018021, "and x1, x1, x1, lsl #32");
        assertDecoding(0x8a011021, "and x1, x1, x1, lsl #4");
        assertDecoding(0x8a012021, "and x1, x1, x1, lsl #8");
        assertDecoding(0x8a4e7daa, "and x10, x13, x14, lsr #31");
        assertDecoding(0x8a18a1aa, "and x10, x13, x24, lsl #40");
        assertDecoding(0x8a4365aa, "and x10, x13, x3, lsr #25");
        assertDecoding(0x8a8a008a, "and x10, x4, x10, asr #0");
        assertDecoding(0x8ad4242b, "and x11, x1, x20, ror #9");
        assertDecoding(0x8ad8d7ab, "and x11, x29, x24, ror #53");
        assertDecoding(0x8ac08c12, "and x18, x0, x0, ror #35");
        assertDecoding(0x8a0a8412, "and x18, x0, x10, lsl #33");
        assertDecoding(0x8a120012, "and x18, x0, x18");
        assertDecoding(0x92403c32, "and x18, x1, #0xffff");
        assertDecoding(0x8a54c972, "and x18, x11, x20, lsr #50");
    }

    @Test
    public void test_and_simd() {
        assertDecoding(0x0e201c21, "and v1.8b, v1.8b, v0.8b");
        assertDecoding(0x4e311d09, "and v9.16b, v8.16b, v17.16b");
    }

    @Test
    public void test_ands() {
        assertDecoding(0x72001c20, "ands w0, w1, #0xff");
        assertDecoding(0x6a140080, "ands w0, w4, w20");
        assertDecoding(0x6a093f60, "ands w0, w27, w9, lsl #15");
        assertDecoding(0x6a407c21, "ands w1, w1, w0, lsr #31");
        assertDecoding(0x6ad6598b, "ands w11, w12, w22, ror #22");
        assertDecoding(0x6a85204b, "ands w11, w2, w5, asr #8");

        assertDecoding(0xf240e020, "ands x0, x1, #0x1ffffffffffffff");
        assertDecoding(0xea000020, "ands x0, x1, x0");
        assertDecoding(0xea512d80, "ands x0, x12, x17, lsr #11");
        assertDecoding(0xea84e9c0, "ands x0, x14, x4, asr #58");
        assertDecoding(0xead7ca80, "ands x0, x20, x23, ror #50");
        assertDecoding(0xea04004a, "ands x10, x2, x4");
    }

    @Test
    public void test_asr() {
        assertDecoding(0x13147c20, "asr w0, w1, #20");
        assertDecoding(0x1ac22820, "asr w0, w1, w2");

        assertDecoding(0x9362fc20, "asr x0, x1, #34");
        assertDecoding(0x9ac22860, "asr x0, x3, x2");
    }

    @Test
    public void test_at() {
        assertDecoding(0xd508787b, "at s1e0w,x27");
        assertDecoding(0xd508782a, "at s1e1w,x10");
        assertDecoding(0xd50c780a, "at s1e2r,x10");
        assertDecoding(0xd50c780f, "at s1e2r,x15");
        assertDecoding(0xd50c782b, "at s1e2w,x11");
        assertDecoding(0xd50e7811, "at s1e3r,x17");
        assertDecoding(0xd5087918, "at s1e1rp,x24");
        assertDecoding(0xd5087928, "at s1e1wp,x8");
    }

    @Test
    public void test_autFoo() {
        assertDecoding(0xdac1190f, "autda x15,x8");
        assertDecoding(0xdac11f10, "autdb x16,x24");
        assertDecoding(0xdac1106b, "autia x11,x3");
        assertDecoding(0xdac113fa, "autia x26,sp");
        assertDecoding(0xdac11478, "autib x24,x3");
        assertDecoding(0xd503219f, "autia1716");
    }

    @Test
    public void test_b_CC_with_label() {
        // all condition codes
        assertDecodingWithPc(0x10076c, 0x540ce18e, "b.al 0x11a39c");
        assertDecodingWithPc(0x1002c8, 0x54fff683, "b.cc 0x100198");
        assertDecodingWithPc(0xca8, 0x54001ac2, "b.cs 0x1000");
        assertDecodingWithPc(0xffa34, 0x54002fe0, "b.eq 0x100030");
        assertDecodingWithPc(0xfeb0, 0x5400004a, "b.ge 0xfeb8");
        assertDecodingWithPc(0x100a30, 0x54fff50c, "b.gt 0x1008d0");
        assertDecodingWithPc(0x1003f4, 0x54ffff68, "b.hi 0x1003e0");
        assertDecodingWithPc(0x1003a8, 0x5400008d, "b.le 0x1003b8");
        assertDecodingWithPc(0x100494, 0x540000c9, "b.ls 0x1004ac");
        assertDecodingWithPc(0x101588, 0x54fffe8b, "b.lt 0x101558");
        assertDecodingWithPc(0x11840, 0x54000304, "b.mi 0x118a0");
        assertDecodingWithPc(0x100414, 0x54fff301, "b.ne 0x100274");
        assertDecodingWithPc(0xebc98, 0x544f9b0f, "b.nv 0x18aff8");
        assertDecodingWithPc(0x11314, 0x54fffee5, "b.pl 0x112f0");
        assertDecodingWithPc(0x567f4, 0x54ffede7, "b.vc 0x565b0");
        assertDecodingWithPc(0x3bdb8, 0x54000286, "b.vs 0x3be08");

        // Rest
        assertDecodingWithPc(0x3bdb8, 0x54000286, "b.vs 0x3be08");
        assertDecodingWithPc(0xaac, 0x54003463, "b.cc 0x1138");
        assertDecodingWithPc(0x115f88, 0x54000203, "b.cc 0x115fc8");
    }

    @Test
    public void test_b_with_label() {
        assertDecodingWithPc(0x1011c, 0x17ffffb9, "b 0x10000");
        assertDecodingWithPc(0xffe4, 0x14000007, "b 0x10000");
        assertDecodingWithPc(0x10980, 0x14000000, "b 0x10980");
        assertDecodingWithPc(0x12628c, 0x17ffffe4, "b 0x12621c");
        assertDecodingWithPc(0x100cc, 0x17ffff98, "b 0xff2c");
        assertDecodingWithPc(0x105b24, 0x17bf2c1d, "b 0xffffffffff0d0b98");
    }

    @Test
    public void test_bcax_simd() {
        assertDecoding(0xce260e90, "bcax v16.16b, v20.16b, v6.16b, v3.16b");
        assertDecoding(0xce287871, "bcax v17.16b, v3.16b, v8.16b, v30.16b");
        assertDecoding(0xce296b13, "bcax v19.16b, v24.16b, v9.16b, v26.16b");
        assertDecoding(0xce297435, "bcax v21.16b, v1.16b, v9.16b, v29.16b");
        assertDecoding(0xce2a0159, "bcax v25.16b, v10.16b, v10.16b, v0.16b");
        assertDecoding(0xce29441c, "bcax v28.16b, v0.16b, v9.16b, v17.16b");
        assertDecoding(0xce2e073c, "bcax v28.16b, v25.16b, v14.16b, v1.16b");
        assertDecoding(0xce23635d, "bcax v29.16b, v26.16b, v3.16b, v24.16b");
        assertDecoding(0xce356868, "bcax v8.16b, v3.16b, v21.16b, v26.16b");
    }

    @Test
    public void test_bfc() {
        assertDecoding(0xb35f43f2, "bfc x18, #33, #17");
    }

    @Test
    public void test_decoding_bitoperation_width() {
        assertDecoding(0x331f0020, "bfi w0, w1, #1, #1");
        assertDecoding(0x331f0420, "bfi w0, w1, #1, #2");
        assertDecoding(0x331e0820, "bfi w0, w1, #2, #3");
        assertDecoding(0x331d0c20, "bfi w0, w1, #3, #4");
        assertDecoding(0x33161001, "bfi w1, w0, #10, #5");
        assertDecoding(0x331e1420, "bfi w0, w1, #2, #6");
        assertDecoding(0xb37df0a0, "bfi x0, x5, #3, #61");
        assertDecoding(0x331f1ae0, "bfi w0, w23, #1, #7");
        assertDecoding(0x33081c20, "bfi w0, w1, #24, #8");
        assertDecoding(0x331f2062, "bfi w2, w3, #1, #9");
        assertDecoding(0x33182460, "bfi w0, w3, #8, #10");
        assertDecoding(0xb34c2860, "bfi x0, x3, #52, #11");
        assertDecoding(0x331f2c66, "bfi w6, w3, #1, #12");
        assertDecoding(0x330e30d8, "bfi w24, w6, #18, #13");
        assertDecoding(0x33183820, "bfi w0, w1, #8, #15");
        assertDecoding(0x33103c00, "bfi w0, w0, #16, #16");
        assertDecoding(0x331642a1, "bfi w1, w21, #10, #17");
        assertDecoding(0xb3604841, "bfi x1, x2, #32, #19");
        assertDecoding(0x33144ea1, "bfi w1, w21, #12, #20");
        assertDecoding(0x33155300, "bfi w0, w24, #11, #21");
        assertDecoding(0xb37f5c27, "bfi x7, x1, #1, #24");
        assertDecoding(0x331e62a1, "bfi w1, w21, #2, #25");
        assertDecoding(0x331a66a1, "bfi w1, w21, #6, #26");
        assertDecoding(0xb3756a4b, "bfi x11, x18, #11, #27");
        assertDecoding(0x331c6ea1, "bfi w1, w21, #4, #28");
        assertDecoding(0x331d72a1, "bfi w1, w21, #3, #29");
        assertDecoding(0x331f7aa1, "bfi w1, w21, #1, #31");
        assertDecoding(0xb3607c20, "bfi x0, x1, #32, #32");
        assertDecoding(0xb37e81a1, "bfi x1, x13, #2, #33");
        assertDecoding(0xb37b8930, "bfi x16, x9, #5, #35");
        assertDecoding(0xb3718faf, "bfi x15, x29, #15, #36");
        assertDecoding(0xb3709105, "bfi x5, x8, #16, #37");
        assertDecoding(0xb3689a26, "bfi x6, x17, #24, #39");
        assertDecoding(0xb372af50, "bfi x16, x26, #14, #44");
    }

    @Test
    public void test_decoding_bitoperation_lsb() {
        assertDecoding(0x331f0840, "bfi w0, w2, #1, #3");
        assertDecoding(0x331e0020, "bfi w0, w1, #2, #1");
        assertDecoding(0x331d72a1, "bfi w1, w21, #3, #29");
        assertDecoding(0x331c6ea1, "bfi w1, w21, #4, #28");
        assertDecoding(0x331c6ea1, "bfi w1, w21, #4, #28");
        assertDecoding(0x331a66a1, "bfi w1, w21, #6, #26");
        assertDecoding(0x331962a1, "bfi w1, w21, #7, #25");
        assertDecoding(0x33182460, "bfi w0, w3, #8, #10");

        assertDecoding(0x33161c62, "bfi w2, w3, #10, #8");
        assertDecoding(0x3315007c, "bfi w28, w3, #11, #1");
        assertDecoding(0x33143ea0, "bfi w0, w21, #12, #16");
        assertDecoding(0x33120801, "bfi w1, w0, #14, #3");
        assertDecoding(0x33110081, "bfi w1, w4, #15, #1");
        assertDecoding(0x33100002, "bfi w2, w0, #16, #1");
        assertDecoding(0x330f0402, "bfi w2, w0, #17, #2");
        assertDecoding(0x330e30d8, "bfi w24, w6, #18, #13");
        assertDecoding(0x330d1c02, "bfi w2, w0, #19, #8");

        assertDecoding(0x330b06ef, "bfi w15, w23, #21, #2");
        assertDecoding(0x33091c61, "bfi w1, w3, #23, #8");
        assertDecoding(0xb3689a26, "bfi x6, x17, #24, #39");
        assertDecoding(0x33081c20, "bfi w0, w1, #24, #8");
        assertDecoding(0xb3660749, "bfi x9, x26, #26, #2");
        assertDecoding(0x33040a6b, "bfi w11, w19, #28, #3");

        assertDecoding(0x33020301, "bfi w1, w24, #30, #1");
        assertDecoding(0x33010043, "bfi w3, w2, #31, #1");
        assertDecoding(0xb3603c61, "bfi x1, x3, #32, #16");
        assertDecoding(0xb35f16ed, "bfi x13, x23, #33, #6");

        assertDecoding(0xb3581c80, "bfi x0, x4, #40, #8");
        assertDecoding(0xb3532714, "bfi x20, x24, #45, #10");
        assertDecoding(0xb3503c41, "bfi x1, x2, #48, #16");
        assertDecoding(0xb34d2279, "bfi x25, x19, #51, #9");
        assertDecoding(0xb34c2860, "bfi x0, x3, #52, #11");
        assertDecoding(0xb34b14c0, "bfi x0, x6, #53, #6");
        assertDecoding(0xb3481c20, "bfi x0, x1, #56, #8");
        assertDecoding(0xb3410040, "bfi x0, x2, #63, #1");
    }

    @Test
    public void test_bfi() {
        assertDecoding(0x331f0420, "bfi w0, w1, #1, #2");
        assertDecoding(0xb34c2861, "bfi x1, x3, #52, #11");
    }

    @Test
    public void test_bfxil() {
        assertDecoding(0x33000280, "bfxil w0, w20, #0, #1");
        assertDecoding(0x330106a0, "bfxil w0, w21, #1, #1");
        assertDecoding(0x33061860, "bfxil w0, w3, #6, #1");
        assertDecoding(0x330a6c82, "bfxil w2, w4, #10, #18");
        assertDecoding(0xb3407ea0, "bfxil x0, x21, #0, #32");
        assertDecoding(0xb3407ca0, "bfxil x0, x5, #0, #32");
        assertDecoding(0xb36fe7e0, "bfxil x0, xzr, #47, #11");
        assertDecoding(0xb366b457, "bfxil x23, x2, #38, #8");
        assertDecoding(0xb355d63f, "bfxil xzr, x17, #21, #33");
    }

    @Test
    public void test_bic() {
        // Forms
        assertDecoding(0x0a220020, "bic w0, w1, w2");
        assertDecoding(0x0ae12900, "bic w0, w8, w1, ror #10");
        assertDecoding(0x0a225a2c, "bic w12, w17, w2, lsl #22");
        assertDecoding(0x0a690961, "bic w1, w11, w9, lsr #2");
        assertDecoding(0x0aa34501, "bic w1, w8, w3, asr #17");
        assertDecoding(0x8a2e0020, "bic x0, x1, x14");
        assertDecoding(0x8a37a381, "bic x1, x28, x23, lsl #40");
        assertDecoding(0x8ab8c921, "bic x1, x9, x24, asr #50");
        assertDecoding(0x8aee44ea, "bic x10, x7, x14, ror #17");
        assertDecoding(0x8a70a5d1, "bic x17, x14, x16, lsr #41");

        // Register 31
        assertDecoding(0x0abf386e, "bic w14, w3, wzr, asr #14");
        assertDecoding(0x0a7f2517, "bic w23, w8, wzr, lsr #9");
        assertDecoding(0x0aff0719, "bic w25, w24, wzr, ror #1");
        assertDecoding(0x0a3f7c49, "bic w9, w2, wzr, lsl #31");
        assertDecoding(0x0a2f621f, "bic wzr, w16, w15, lsl #24");
        assertDecoding(0x0ae22f1f, "bic wzr, w24, w2, ror #11");
        assertDecoding(0x8a3f2f8d, "bic x13, x28, xzr, lsl #11");
        assertDecoding(0x8ab7cbee, "bic x14, xzr, x23, asr #50");
        assertDecoding(0x8a6a4c1f, "bic xzr, x0, x10, lsr #19");
    }

    @Test
    public void test_bic_simd() {
        assertDecoding(0x6f04b798, "bic v24.8h, #0x9c, lsl #8");
        assertDecoding(0x2f0314e1, "bic v1.2s,#0x67");
        assertDecoding(0x6f017561, "bic v1.4s,#0x2b,lsl #24");
        assertDecoding(0x6f0634e1, "bic v1.4s,#0xc7,lsl #8");
        assertDecoding(0x6f0736a1, "bic v1.4s,#0xf5,lsl #8");
        assertDecoding(0x6f075721, "bic v1.4s,#0xf9,lsl #16");
        assertDecoding(0x2f04368a, "bic v10.2s,#0x94,lsl #8");
        assertDecoding(0x6f01166a, "bic v10.4s,#0x33");
        assertDecoding(0x6f05778a, "bic v10.4s,#0xbc,lsl #24");
        assertDecoding(0x6f07578a, "bic v10.4s,#0xfc,lsl #16");
        assertDecoding(0x6f05948a, "bic v10.8h,#0xa4");
        assertDecoding(0x2f05374b, "bic v11.2s,#0xba,lsl #8");
        assertDecoding(0x2f07956b, "bic v11.4h,#0xeb");
        assertDecoding(0x6f0497eb, "bic v11.8h,#0x9f");
        assertDecoding(0x6f009554, "bic v20.8h,#0xa");
    }

    @Test
    public void test_bics() {
        assertDecoding(0x6a3a0360, "bics w0, w27, w26");
        assertDecoding(0x6a6a6a00, "bics w0, w16, w10, lsr #26");
        assertDecoding(0x6a21001f, "bics wzr, w0, w1");
        assertDecoding(0xea290105, "bics x5, x8, x9");
        assertDecoding(0xeaabb5a0, "bics x0, x13, x11, asr #45");
        assertDecoding(0xea21001f, "bics xzr, x0, x1");
        assertDecoding(0xeae5759f, "bics xzr, x12, x5, ror #29");
    }

    @Test
    public void test_bif_simd() {
        assertDecoding(0x2ee31c20, "bif v0.8b, v1.8b, v3.8b");
        assertDecoding(0x6eeb1f4c, "bif v12.16b, v26.16b, v11.16b");
    }

    @Test
    public void test_bit_simd() {
        assertDecoding(0x2eb51c20, "bit v0.8b, v1.8b, v21.8b");
        assertDecoding(0x6ea21c01, "bit v1.16b, v0.16b, v2.16b");
    }

    @Test
    public void test_bl_with_label() {
        assertDecodingWithPc(0x4294, 0x97fff35b, "bl 0x1000");
        assertDecodingWithPc(0x2ff4, 0x97fff803, "bl 0x1000");
        assertDecodingWithPc(0xa9e0, 0x9400158a, "bl 0x10008");
        assertDecodingWithPc(0x10388, 0x97ffff20, "bl 0x10008");
        assertDecodingWithPc(0xf9a28, 0x97f45d02, "bl 0xffffffffffe10e30");
    }

    @Test
    public void test_blr() {
        assertDecoding(0xd63f01a0, "blr x13");
    }

    @Test
    public void test_blraa() {
        assertDecoding(0xd73f0bb9, "blraa x29,x25");
    }

    @Test
    public void test_blrab() {
        assertDecoding(0xd73f0d58, "blrab x10,x24");
    }

    @Test
    public void test_br() {
        assertDecoding(0xd61f01e0, "br x15");
    }

    @Test
    public void test_braa() {
        assertDecoding(0xd71f0a03, "braa x16,x3");
        assertDecoding(0xd71f0abf, "braa x21,sp");
    }

    @Test
    public void test_brab() {
        assertDecoding(0xd71f0f74, "brab x27,x20");
    }

    @Test
    public void test_brk() {
        assertDecoding(0xd427a7c0, "brk #0x3d3e");
        assertDecoding(0xd4207d00, "brk #0x3e8");
    }

    @Test
    public void test_bsl_simd() {
        assertDecoding(0x6e611c40, "bsl v0.16b, v2.16b, v1.16b");
        assertDecoding(0x2e621c20, "bsl v0.8b, v1.8b, v2.8b");
        assertDecoding(0x2e641d69, "bsl v9.8b, v11.8b, v4.8b");
    }

    @Test
    public void test_cas() {
        assertDecoding(0x88bc7e38, "cas w28, w24, [x17]");
        assertDecoding(0x88a47da7, "cas w4, w7, [x13]");
        assertDecoding(0xc8a67cd2, "cas x6, x18, [x6]");
    }

    @Test
    public void test_cash() {
        assertDecoding(0x48bc7eb9, "cash w28,w25,[x21]");
    }

    @Test
    public void test_casp() {
        assertDecoding(0x082a7e0c, "casp w10,w11,w12,w13,[x16]");
        assertDecoding(0x08347dc2, "casp w20,w21,w2,w3,[x14]");
        assertDecoding(0x482a7c72, "casp x10,x11,x18,x19,[x3]");
        assertDecoding(0x483e7f6a, "casp x30,xzr,x10,x11,[x27]");
    }

    @Test
    public void test_caspa() {
        assertDecoding(0x086e7d62, "caspa w14,w15,w2,w3,[x11]");
        assertDecoding(0x48747c88, "caspa x20,x21,x8,x9,[x4]");
    }

    @Test
    public void test_caspal() {
        assertDecoding(0x086cfef0, "caspal w12,w13,w16,w17,[x23]");
        assertDecoding(0x4862ff6e, "caspal x2,x3,x14,x15,[x27]");
    }

    @Test
    public void test_caspl() {
        assertDecoding(0x082efe7c, "caspl w14,w15,w28,w29,[x19]");
        assertDecoding(0x4836ff70, "caspl x22,x23,x16,x17,[x27]");
    }

    @Test
    public void test_cbnz_with_label() {
        assertDecodingWithPc(0xf7c, 0x35000420, "cbnz w0, 0x1000");
        assertDecodingWithPc(0xfff4, 0x35000060, "cbnz w0, 0x10000");
        assertDecodingWithPc(0xf6d8, 0xb5fffb89, "cbnz x9, 0xf648");
        assertDecodingWithPc(0xf2934, 0xb5071c7f, "cbnz xzr, 0x100cc0");
    }

    @Test
    public void test_cbz_with_label() {
        assertDecodingWithPc(0xffec, 0x340000a0, "cbz w0, 0x10000");
        assertDecodingWithPc(0xedbf4, 0xb490ffa9, "cbz x9, 0xfbe8");
        assertDecodingWithPc(0xec438, 0xb4115f5f, "cbz xzr, 0x10f020");
    }

    @Test
    public void test_conditionCodes_for_cond_compare() {
        assertDecoding(0x3a4d3804, "ccmn w0, #0xd, #0x4, cc");
        assertDecoding(0x3a412804, "ccmn w0, #0x1, #0x4, cs");
        assertDecoding(0x3a410800, "ccmn w0, #0x1, #0x0, eq");
        assertDecoding(0x3a41a801, "ccmn w0, #0x1, #0x1, ge");
        assertDecoding(0x3a41c800, "ccmn w0, #0x1, #0x0, gt");
        assertDecoding(0x3a418800, "ccmn w0, #0x1, #0x0, hi");
        assertDecoding(0x3a41d804, "ccmn w0, #0x1, #0x4, le");
        assertDecoding(0x3a419801, "ccmn w0, #0x1, #0x1, ls");
        assertDecoding(0x3a41b804, "ccmn w0, #0x1, #0x4, lt");
        assertDecoding(0x7a404824, "ccmp w1, #0x0, #0x4, mi");
        assertDecoding(0x3a411800, "ccmn w0, #0x1, #0x0, ne");
        assertDecoding(0x3a43fa2e, "ccmn w17, #0x3, #0xe, nv");
        assertDecoding(0xba495105, "ccmn x8, x9, #0x5, pl");
        assertDecoding(0x3a417804, "ccmn w0, #0x1, #0x4, vc");
        assertDecoding(0x3a5b69c1, "ccmn w14, #0x1b, #0x1, vs");
    }

    @Test
    public void test_ccmn() {
        assertDecoding(0x3a410800, "ccmn w0, #0x1, #0x0, eq");
        assertDecoding(0x3a412804, "ccmn w0, #0x1, #0x4, cs");
        assertDecoding(0x3a500800, "ccmn w0, #0x10, #0x0, eq");
        assertDecoding(0x3a5b69c1, "ccmn w14, #0x1b, #0x1, vs");
        assertDecoding(0x3a4292c3, "ccmn w22, w2, #0x3, ls");
        assertDecoding(0xba433822, "ccmn x1, #0x3, #0x2, cc");
        assertDecoding(0xba567147, "ccmn x10, x22, #0x7, vc");
        assertDecoding(0xba5cbbec, "ccmn xzr, #0x1c, #0xc, lt");
    }

    @Test
    public void test_ccmp() {
        assertDecoding(0x7a410802, "ccmp w0, #0x1, #0x2, eq");
        assertDecoding(0x7a412002, "ccmp w0, w1, #0x2, cs");
        assertDecoding(0xfa5f1802, "ccmp x0, #0x1f, #0x2, ne");
        assertDecoding(0xfa41a004, "ccmp x0, x1, #0x4, ge");
    }

    @Test
    public void test_conditionCodes_for_inv_cond_select() {
        assertDecoding(0x1a802400, "cinc w0, w0, cc");
        assertDecoding(0x1a813421, "cinc w1, w1, cs");
        assertDecoding(0x1a801400, "cinc w0, w0, eq");
        assertDecoding(0x1a80b400, "cinc w0, w0, ge");
        assertDecoding(0x1a80d400, "cinc w0, w0, gt");
        assertDecoding(0x1a809400, "cinc w0, w0, hi");
        assertDecoding(0x1a9ac75a, "cinc w26, w26, le");
        assertDecoding(0x1a828441, "cinc w1, w2, ls");
        assertDecoding(0x1a80a400, "cinc w0, w0, lt");
        assertDecoding(0x1a805400, "cinc w0, w0, mi");
        assertDecoding(0x1a800400, "cinc w0, w0, ne");
        assertDecoding(0xda932265, "cinv x5, x19, cc");
        assertDecoding(0x5a801000, "cinv w0, w0, eq");
        assertDecoding(0xda80d000, "cinv x0, x0, gt");
        assertDecoding(0x5a83a063, "cinv w3, w3, lt");
        assertDecoding(0x5a800000, "cinv w0, w0, ne");
    }

    @Test
    public void test_cinc() {
        assertDecoding(0x1a811420, "cinc w0, w1, eq");
        assertDecoding(0x9a8d15a0, "cinc x0, x13, eq");
    }

    @Test
    public void test_cinv() {
        assertDecoding(0x5a940280, "cinv w0, w20, ne");
        assertDecoding(0xda8600c1, "cinv x1, x6, ne");
    }

    @Test
    public void test_clc() {
        assertDecoding(0x0e204ba0, "cls v0.8b,v29.8b");
        assertDecoding(0x4e204aab, "cls v11.16b,v21.16b");
        assertDecoding(0x4e6049cb, "cls v11.8h,v14.8h");
        assertDecoding(0x0ea04950, "cls v16.2s,v10.2s");
        assertDecoding(0x4ea04b14, "cls v20.4s,v24.4s");
        assertDecoding(0x0e604b33, "cls v19.4h,v25.4h");
        assertDecoding(0x5ac015f8, "cls w24,w15");
    }

    @Test
    public void test_clrex() {
        assertDecoding(0xd5033f5f, "clrex");
        assertDecoding(0xd503325f, "clrex #0x2");
    }

    @Test
    public void test_clz() {
        assertDecoding(0x5ac01020, "clz w0, w1");
        assertDecoding(0xdac01320, "clz x0, x25");
    }

    @Test
    public void test_cmeq_simd() {
        assertDecoding(0x6e268c00, "cmeq v0.16b, v0.16b, v6.16b");
        assertDecoding(0x4ee09800, "cmeq v0.2d, v0.2d, #0");
        assertDecoding(0x6ee78ca4, "cmeq v4.2d, v5.2d, v7.2d");
        assertDecoding(0x6ea38c00, "cmeq v0.4s, v0.4s, v3.4s");
        assertDecoding(0x4ea09820, "cmeq v0.4s, v1.4s, #0");
    }

    @Test
    public void test_cmge_fpu() {
        assertDecoding(0x5ef93d75, "cmge d21, d11, d25");
    }

    @Test
    public void test_cmge_simd() {
        assertDecoding(0x0e2c3ddd, "cmge v29.8b, v14.8b, v12.8b");
        assertDecoding(0x0e623c47, "cmge v7.4h, v2.4h, v2.4h");
    }

    @Test
    public void test_cmgt_fpu() {
        assertDecoding(0x5efb364e, "cmgt d14,d18,d27");
    }

    @Test
    public void test_cmgt_simd() {
        assertDecoding(0x4e3136a0, "cmgt v0.16b,v21.16b,v17.16b");
        assertDecoding(0x4e208ac0, "cmgt v0.16b,v22.16b,#0");
        assertDecoding(0x4eeb3460, "cmgt v0.2d,v3.2d,v11.2d");
        assertDecoding(0x0ea03740, "cmgt v0.2s,v26.2s,v0.2s");
        assertDecoding(0x0e7d3580, "cmgt v0.4h,v12.4h,v29.4h");
        assertDecoding(0x4eb63560, "cmgt v0.4s,v11.4s,v22.4s");
        assertDecoding(0x0e2736a0, "cmgt v0.8b,v21.8b,v7.8b");
        assertDecoding(0x4e7f3680, "cmgt v0.8h,v20.8h,v31.8h");
        assertDecoding(0x4ea0896a, "cmgt v10.4s,v11.4s,#0");
    }

    @Test
    public void test_cmhi_fpu() {
        assertDecoding(0x7ee83654, "cmhi d20, d18, d8");
    }

    @Test
    public void test_cmhi_simd() {
        assertDecoding(0x6ee13440, "cmhi v0.2d, v2.2d, v1.2d");
        assertDecoding(0x6e763653, "cmhi v19.8h, v18.8h, v22.8h");
    }

    @Test
    public void test_cmhs_simd() {
        assertDecoding(0x6ea03c80, "cmhs v0.4s, v4.4s, v0.4s");
        assertDecoding(0x6e3d3fe4, "cmhs v4.16b, v31.16b, v29.16b");
    }

    @Test
    public void test_cmle_simd() {
        assertDecoding(0x6ee09b41, "cmle v1.2d,v26.2d,#0");
        assertDecoding(0x6e60992a, "cmle v10.8h,v9.8h,#0");
        assertDecoding(0x6e209b0f, "cmle v15.16b,v24.16b,#0");
        assertDecoding(0x2e609aef, "cmle v15.4h,v23.4h,#0");
        assertDecoding(0x6ea09b32, "cmle v18.4s,v25.4s,#0");
        assertDecoding(0x2e209b66, "cmle v6.8b,v27.8b,#0");
    }

    @Test
    public void test_cmlt_fpu() {
        assertDecoding(0x5ee0a90d, "cmlt d13,d8,#0");
    }

    @Test
    public void test_cmlt_simd() {
        assertDecoding(0x4ea0a840, "cmlt v0.4s,v2.4s,#0");
        assertDecoding(0x4ee0aaec, "cmlt v12.2d,v23.2d,#0");
        assertDecoding(0x0ea0a84c, "cmlt v12.2s,v2.2s,#0");
        assertDecoding(0x0e60a90d, "cmlt v13.4h,v8.4h,#0");
        assertDecoding(0x0e20ab13, "cmlt v19.8b,v24.8b,#0");
        assertDecoding(0x4e20ab18, "cmlt v24.16b,v24.16b,#0");
    }

    @Test
    public void test_cmn() {
        assertDecoding(0x3100041f, "cmn w0, #0x1");
        assertDecoding(0x3140041f, "cmn w0, #0x1, lsl #12");
        assertDecoding(0x3104081f, "cmn w0, #0x102");
        assertDecoding(0x3100081f, "cmn w0, #0x2");
        assertDecoding(0x3140081f, "cmn w0, #0x2, lsl #12");
        assertDecoding(0x2b02001f, "cmn w0, w2");

        assertDecoding(0x2b4e695f, "cmn w10, w14, lsr #26");
        assertDecoding(0xab95b65f, "cmn x18, x21, asr #45");
        assertDecoding(0xb140067f, "cmn x19, #0x1, lsl #12");
        assertDecoding(0xb1008a9f, "cmn x20, #0x22");
        assertDecoding(0xb14c2a9f, "cmn x20, #0x30a, lsl #12");
        assertDecoding(0xab4d2fdf, "cmn x30, x13, lsr #11");
        assertDecoding(0xab1f03ff, "cmn xzr, xzr");

        assertDecoding(0xab376c3f, "cmn x1,x23,uxtx #3");
        assertDecoding(0xab246d3f, "cmn x9,x4,uxtx #3");
        assertDecoding(0xab20703f, "cmn x1,x0,uxtx #4");
    }

    @Test
    public void test_cmp() {
        assertDecoding(0x7114001f, "cmp w0, #0x500");
        assertDecoding(0x7154001f, "cmp w0, #0x500, lsl #12");
        assertDecoding(0x713ffc1f, "cmp w0, #0xfff");
        assertDecoding(0x6b01001f, "cmp w0, w1");
        assertDecoding(0x6b01041f, "cmp w0, w1, lsl #1");
        assertDecoding(0x6b01081f, "cmp w0, w1, lsl #2");
        assertDecoding(0x6b010c1f, "cmp w0, w1, lsl #3");
        assertDecoding(0x6b41041f, "cmp w0, w1, lsr #1");
        assertDecoding(0x6b41081f, "cmp w0, w1, lsr #2");
        assertDecoding(0x6b410c1f, "cmp w0, w1, lsr #3");
        assertDecoding(0x6b417c1f, "cmp w0, w1, lsr #31");
        assertDecoding(0x6b411c1f, "cmp w0, w1, lsr #7");
        assertDecoding(0x6b21001f, "cmp w0, w1, uxtb");
        assertDecoding(0x6b21201f, "cmp w0, w1, uxth");
        assertDecoding(0x6b53081f, "cmp w0, w19, lsr #2");
        assertDecoding(0x6b82601f, "cmp w0, w2, asr #24");
        assertDecoding(0x6b26a01f, "cmp w0, w6, sxth");
        assertDecoding(0x6b20803f, "cmp w1, w0, sxtb");
        assertDecoding(0xeb1f005f, "cmp x2, xzr");
        assertDecoding(0xf158efff, "cmp sp, #0x63b, lsl #12");
        assertDecoding(0x714ff3ff, "cmp wsp, #0x3fc, lsl #12");
        assertDecoding(0xeb30721f, "cmp x16,x16,uxtx #4");
        assertDecoding(0x6b244c7f, "cmp w3,w4,uxtw #3");
        assertDecoding(0xeb29719f, "cmp x12,x9,uxtx #4");
        assertDecoding(0x6b2c413f, "cmp w9,w12,uxtw");
    }

    @Test
    public void test_cmtst_fpu() {
        assertDecoding(0x5ee18e15, "cmtst d21, d16, d1");
    }

    @Test
    public void test_cmtst_simd() {
        assertDecoding(0x4efe8f8b, "cmtst v11.2d, v28.2d, v30.2d");
    }

    @Test
    public void test_cneg() {
        assertDecoding(0x5a81b420, "cneg w0, w1, ge");
        assertDecoding(0x5a80a41b, "cneg w27, w0, lt");
        assertDecoding(0x5a8624dc, "cneg w28, w6, cc");
        assertDecoding(0xda96d6c1, "cneg x1, x22, gt");
        assertDecoding(0xda9c0781, "cneg x1, x28, ne");
    }

    @Test
    public void test_cnt_simd() {
        assertDecoding(0x0e205800, "cnt v0.8b, v0.8b");
        assertDecoding(0x4e205806, "cnt v6.16b, v0.16b");
    }

    @Test
    public void test_crc32cb() {
        assertDecoding(0x1ad3501c, "crc32cb w28, w0, w19");
    }

    @Test
    public void test_crc32cw() {
        assertDecoding(0x1ad95b42, "crc32cw w2, w26, w25");
    }

    @Test
    public void test_crc32cx() {
        assertDecoding(0x9ad55ede, "crc32cx w30, w22, x21");
    }

    @Test
    public void test_crc32w() {
        assertDecoding(0x1ac649fb, "crc32w w27, w15, w6");
    }

    @Test
    public void test_crc32x() {
        assertDecoding(0x9ac24fe8, "crc32x w8, wzr, x2");
    }

    @Test
    public void test_csel() {
        assertDecoding(0x1a8cd020, "csel w0, w1, w12, le");
        assertDecoding(0x1a9f0020, "csel w0, w1, wzr, eq");
        assertDecoding(0x1a8133e0, "csel w0, wzr, w1, cc");
        assertDecoding(0x9a930020, "csel x0, x1, x19, eq");
        assertDecoding(0x9a9f2020, "csel x0, x1, xzr, cs");
        assertDecoding(0x9a8033e0, "csel x0, xzr, x0, cc");
        assertDecoding(0x9a9f5129, "csel x9, x9, xzr, pl");
        assertDecoding(0x9a9523e9, "csel x9, xzr, x21, cs");
    }

    @Test
    public void test_cset() {
        assertDecoding(0x1a9f37e1, "cset w1, cs");
        assertDecoding(0x1a9f17e1, "cset w1, eq");
        assertDecoding(0x1a9fb7e1, "cset w1, ge");
        assertDecoding(0x1a9fd7e1, "cset w1, gt");
        assertDecoding(0x1a9f97e1, "cset w1, hi");
        assertDecoding(0x1a9fc7e1, "cset w1, le");
        assertDecoding(0x1a9f87e1, "cset w1, ls");
        assertDecoding(0x1a9fa7e1, "cset w1, lt");
        assertDecoding(0x1a9f57e1, "cset w1, mi");
        assertDecoding(0x1a9f07e1, "cset w1, ne");
        assertDecoding(0x1a9f67e1, "cset w1, vc");
        assertDecoding(0x9a9f07e1, "cset x1, ne");
    }

    @Test
    public void test_csetm() {
        assertDecoding(0x5a9f23e0, "csetm w0, cc");
        assertDecoding(0x5a9f13e0, "csetm w0, eq");
        assertDecoding(0x5a9fb3e0, "csetm w0, ge");
        assertDecoding(0x5a9fd3e0, "csetm w0, gt");
        assertDecoding(0x5a9f93e0, "csetm w0, hi");
        assertDecoding(0x5a9fc3e0, "csetm w0, le");
        assertDecoding(0x5a9f83e0, "csetm w0, ls");
        assertDecoding(0x5a9fa3e0, "csetm w0, lt");
        assertDecoding(0x5a9f03e0, "csetm w0, ne");
        assertDecoding(0xda9f13e0, "csetm x0, eq");
    }

    @Test
    public void test_csinc() {
        assertDecoding(0x1a960420, "csinc w0, w1, w22, eq");
        assertDecoding(0x1a9f0420, "csinc w0, w1, wzr, eq");
        assertDecoding(0x1a8107e0, "csinc w0, wzr, w1, eq");
        assertDecoding(0x9a930680, "csinc x0, x20, x19, eq");
        assertDecoding(0x9a9fd420, "csinc x0, x1, xzr, le");
        assertDecoding(0x9a80a59f, "csinc xzr, x12, x0, ge");
    }

    @Test
    public void test_csinv() {
        assertDecoding(0x5a93d040, "csinv w0, w2, w19, le");
        assertDecoding(0x5a9f3020, "csinv w0, w1, wzr, cc");
        assertDecoding(0xda96e2e0, "csinv x0, x23, x22, al");
        assertDecoding(0xda9fa020, "csinv x0, x1, xzr, ge");
        assertDecoding(0x5a99e32e, "csinv w14, w25, w25, al");
    }

    @Test
    public void test_csneg() {
        assertDecoding(0x5a9a4402, "csneg w2, w0, w26, mi");
        assertDecoding(0x5a9307e0, "csneg w0, wzr, w19, eq");

        assertDecoding(0xda9a240e, "csneg x14, x0, x26, cs");
        assertDecoding(0xda8307e3, "csneg x3, xzr, x3, eq");

        assertDecoding(0x5a90e601, "csneg w1,w16,w16,al");
        assertDecoding(0x5a91f621, "csneg w1,w17,w17,nv");
        assertDecoding(0xda81e42e, "csneg x14,x1,x1,al");
        assertDecoding(0xda89f526, "csneg x6,x9,x9,nv");
    }

    @Test
    public void test_dc() {
        assertDecoding(0xd50b7b22, "dc cvau, x2");
        assertDecoding(0xd50b7423, "dc zva, x3");
        assertDecoding(0xd5087e52, "dc cisw,x18");
        assertDecoding(0xd50b7e28, "dc civac,x8");
        assertDecoding(0xd50b7a29, "dc cvac,x9");
        assertDecoding(0xd508765e, "dc isw,x30");
        assertDecoding(0xd5087635, "dc ivac,x21");
        assertDecoding(0xd50b7c3c, "dc cvap,x28");
    }

    @Test
    public void test_dcps2() {
        assertDecoding(0xd4b00be2, "dcps2 #0x805f");
    }

    @Test
    public void test_dcps3() {
        assertDecoding(0xd4aa7a43, "dcps3 #0x53d2");
    }

    @Test
    public void test_dmb() {
        assertDecoding(0xd5033bbf, "dmb ish");
        assertDecoding(0xd50339bf, "dmb ishld");
    }

    @Test
    public void test_dsb() {
        assertDecoding(0xd503309f, "dsb #0x00");
        assertDecoding(0xd503319f, "dsb oshld");
        assertDecoding(0xd503329f, "dsb oshst");
        assertDecoding(0xd503339f, "dsb osh");
        assertDecoding(0xd503349f, "dsb #0x04");
        assertDecoding(0xd503359f, "dsb nshld");
        assertDecoding(0xd503369f, "dsb nshst");
        assertDecoding(0xd503379f, "dsb nsh");
        assertDecoding(0xd503389f, "dsb #0x08");
        assertDecoding(0xd503399f, "dsb ishld");
        assertDecoding(0xd5033a9f, "dsb ishst");
        assertDecoding(0xd5033b9f, "dsb ish");
        assertDecoding(0xd5033c9f, "dsb #0x0c");
        assertDecoding(0xd5033d9f, "dsb ld");
        assertDecoding(0xd5033e9f, "dsb st");
        assertDecoding(0xd5033f9f, "dsb sy");
    }

    @Test
    public void test_dup_simd() {
        assertDecoding(0x0e0e0e00, "dup v0.4h, w16");
        assertDecoding(0x4e040c20, "dup v0.4s, w1");
        assertDecoding(0x0e0d0c0f, "dup v15.8b, w0");
        assertDecoding(0x4e010c20, "dup v0.16b, w1");

        assertDecoding(0x4e080c20, "dup v0.2d, x1");

        assertDecoding(0x0e040442, "dup v2.2s, v2.s[0]");
        assertDecoding(0x4e080570, "dup v16.2d, v11.d[0]");
        assertDecoding(0x4e1c04e0, "dup v0.4s, v7.s[3]");
        assertDecoding(0x4e0c0631, "dup v17.4s, v17.s[1]");
        assertDecoding(0x0e02047c, "dup v28.4h, v3.h[0]");
        assertDecoding(0x0e05060f, "dup v15.8b, v16.b[2]");
        assertDecoding(0x4e180c18, "dup v24.2d,x0");
    }


    @Test
    public void test_eon() {
        assertDecoding(0x4a250081, "eon w1, w4, w5");
        assertDecoding(0x4a211000, "eon w0, w0, w1, lsl #4");
        assertDecoding(0x4aff5b80, "eon w0, w28, wzr, ror #22");
        assertDecoding(0x4a674a61, "eon w1, w19, w7, lsr #18");
        assertDecoding(0x4abc614b, "eon w11, w10, w28, asr #24");
        assertDecoding(0x4a7f748b, "eon w11, w4, wzr, lsr #29");
        assertDecoding(0xcab5ae9f, "eon xzr, x20, x21, asr #43");
        assertDecoding(0xca3ff75f, "eon xzr, x26, xzr, lsl #61");
        assertDecoding(0xca79ebff, "eon xzr, xzr, x25, lsr #58");
    }

    @Test
    public void test_eor() {
        assertDecoding(0xd265657f, "eor sp, x11, #0x1ffffff8000000");
        assertDecoding(0x52001420, "eor w0, w1, #0x3f");
        assertDecoding(0x4a420420, "eor w0, w1, w2, lsr #1");
        assertDecoding(0x4ac40940, "eor w0, w10, w4, ror #2");
        assertDecoding(0x4a4a4a00, "eor w0, w16, w10, lsr #18");
        assertDecoding(0x4a005440, "eor w0, w2, w0, lsl #21");
        assertDecoding(0x4a4e7fe1, "eor w1, wzr, w14, lsr #31");
        assertDecoding(0x5229ca5f, "eor wsp, w18, #0x83838383");
        assertDecoding(0x4ac016bf, "eor wzr, w21, w0, ror #5");

        assertDecoding(0xd2410020, "eor x0, x1, #0x8000000000000000");
        assertDecoding(0xca800820, "eor x0, x1, x0, asr #2");
        assertDecoding(0xca001c20, "eor x0, x1, x0, lsl #7");
        assertDecoding(0xcac0ac20, "eor x0, x1, x0, ror #43");
        assertDecoding(0xca418020, "eor x0, x1, x1, lsr #32");
        assertDecoding(0xd2393be0, "eor x0, xzr, #0x3fff80003fff80");
        assertDecoding(0xd2393be0, "eor x0, xzr, #0x3fff80003fff80");
        assertDecoding(0xca5f49f0, "eor x16, x15, xzr, lsr #18");
        assertDecoding(0xcac8745f, "eor xzr, x2, x8, ror #29");
    }


    @Test
    public void test_eor3_simd() {
        assertDecoding(0xce15128c, "eor3 v12.16b, v20.16b, v21.16b, v4.16b");
        assertDecoding(0xce083411, "eor3 v17.16b, v0.16b, v8.16b, v13.16b");
    }

    @Test
    public void test_eor_simd() {
        assertDecoding(0x6e301c20, "eor v0.16b, v1.16b, v16.16b");
        assertDecoding(0x2e211c00, "eor v0.8b, v0.8b, v1.8b");
    }

    @Test
    public void test_ext_simd() {
        assertDecoding(0x6e062840, "ext v0.16b, v2.16b, v6.16b, #5");
        assertDecoding(0x6e047901, "ext v1.16b, v8.16b, v4.16b, #15");
        assertDecoding(0x2e1b2138, "ext v24.8b, v9.8b, v27.8b, #4");
    }

    @Test
    public void test_extr() {
        assertDecoding(0x138e09e0, "extr w0, w15, w14, #2");
        assertDecoding(0x13847c21, "extr w1, w1, w4, #31");
        assertDecoding(0x138d06df, "extr wzr, w22, w13, #1");
        assertDecoding(0x93c38020, "extr x0, x1, x3, #32");
        assertDecoding(0x93c0fd40, "extr x0, x10, x0, #63");
        assertDecoding(0x93ca8930, "extr x16, x9, x10, #34");
        assertDecoding(0x93d0fff0, "extr x16, xzr, x16, #63");
    }

    @Test
    public void test_fabd_fpu() {
        assertDecoding(0x7ee1d402, "fabd d2, d0, d1");
        assertDecoding(0x7ea7d72d, "fabd s13, s25, s7");
    }

    @Test
    public void test_fabd_simd() {
        assertDecoding(0x6ecf17ea, "fabd v10.8h, v31.8h, v15.8h");
        assertDecoding(0x6eb1d60c, "fabd v12.4s, v16.4s, v17.4s");
    }

    @Test
    public void test_fabs_fpu() {
        assertDecoding(0x1e60c221, "fabs d1, d17");
        assertDecoding(0x1e20c101, "fabs s1, s8");
        assertDecoding(0x1e60c3fa, "fabs d26,d31");
    }

    @Test
    public void test_fabs_simd() {
        assertDecoding(0x0ea0fb03, "fabs v3.2s, v24.2s");
    }

    @Test
    public void test_facge_fpu() {
        assertDecoding(0x7e4f2dd3, "facge h19, h14, h15");
        assertDecoding(0x7e422fe7, "facge h7, h31, h2");
    }

    @Test
    public void test_facge_simd() {
        assertDecoding(0x2e592ea0, "facge v0.4h, v21.4h, v25.4h");
        assertDecoding(0x6e7cee2e, "facge v14.2d, v17.2d, v28.2d");
        assertDecoding(0x6e4d2c67, "facge v7.8h, v3.8h, v13.8h");
        assertDecoding(0x6e6eec88, "facge v8.2d, v4.2d, v14.2d");
    }

    @Test
    public void test_facgt_fpu() {
        assertDecoding(0x7efced42, "facgt d2, d10, d28");
        assertDecoding(0x7eb0ec4c, "facgt s12, s2, s16");
    }

    @Test
    public void test_facgt_simd() {
        assertDecoding(0x6eafed8b, "facgt v11.4s, v12.4s, v15.4s");
    }

    @Test
    public void test_fadd_fpu() {
        assertDecoding(0x1e722820, "fadd d0, d1, d18");
        assertDecoding(0x1e772a7e, "fadd d30, d19, d23");
        assertDecoding(0x1e222820, "fadd s0, s1, s2");
        assertDecoding(0x1e2328b3, "fadd s19, s5, s3");
        assertDecoding(0x1e7f295e, "fadd d30,d10,d31");
    }

    @Test
    public void test_fadd_simd() {
        assertDecoding(0x0e33d4eb, "fadd v11.2s, v7.2s, v19.2s");
        assertDecoding(0x0e51176e, "fadd v14.4h, v27.4h, v17.4h");
        assertDecoding(0x4e66d61d, "fadd v29.2d, v16.2d, v6.2d");
    }

    @Test
    public void test_faddp_simd() {
        assertDecoding(0x2e3bd4f1, "faddp v17.2s, v7.2s, v27.2s");
        assertDecoding(0x7e70d83c, "faddp d28,v1.2d");
        assertDecoding(0x5e30d942, "faddp h2,v10.2h");
        assertDecoding(0x7e30d9be, "faddp s30,v13.2s");
    }

    @Test
    public void test_fcadd_simd() {
        assertDecoding(0x2e81e630, "fcadd v16.2s, v17.2s, v1.2s, #90");
        assertDecoding(0x2e46e412, "fcadd v18.4h, v0.4h, v6.4h, #90");
    }

    @Test
    public void test_fccmp_fpu() {
        assertDecoding(0x1e611404, "fccmp d0, d1, #0x4, ne");
        assertDecoding(0x1e7596e1, "fccmp d23, d21, #0x1, ls");
        assertDecoding(0x1ef3340d, "fccmp h0, h19, #0xd, cc");
        assertDecoding(0x1e224401, "fccmp s0, s2, #0x1, mi");
        assertDecoding(0x1e7ff403, "fccmp d0,d31,#0x3,nv");
        assertDecoding(0x1e6b77ee, "fccmp d31,d11,#0xe,vc");
    }

    @Test
    public void test_fccmpe_fpu() {
        assertDecoding(0x1e621431, "fccmpe d1, d2, #0x1, ne");
        assertDecoding(0x1ee3543a, "fccmpe h1, h3, #0xa, pl");
        assertDecoding(0x1e3396b4, "fccmpe s21, s19, #0x4, ls");
        assertDecoding(0x1e7fb412, "fccmpe d0,d31,#0x2,lt");
        assertDecoding(0x1e6d17f3, "fccmpe d31,d13,#0x3,ne");
    }

    @Test
    public void test_fcmeq_fpu() {
        assertDecoding(0x5e6ee63a, "fcmeq d26, d17, d14");
        assertDecoding(0x5e462538, "fcmeq h24, h9, h6");
    }

    @Test
    public void test_fcmeq_simd() {
        assertDecoding(0x4e26e76a, "fcmeq v10.4s, v27.4s, v6.4s");
        assertDecoding(0x0e4525d3, "fcmeq v19.4h, v14.4h, v5.4h");
    }

    @Test
    public void test_fcmge_simd() {
        assertDecoding(0x2e2be4c8, "fcmge v8.2s, v6.2s, v11.2s");
    }

    @Test
    public void test_fcmgt_fpu() {
        assertDecoding(0x7ecb27cb, "fcmgt h11, h30, h11");
        assertDecoding(0x7ea2e400, "fcmgt s0, s0, s2");
    }

    @Test
    public void test_fcmgt_simd() {
        assertDecoding(0x6ea6e483, "fcmgt v3.4s, v4.4s, v6.4s");
    }

    @Test
    public void test_fcmla_simd() {
        assertDecoding(0x6ec3cecc, "fcmla v12.2d, v22.2d, v3.2d, #90");
        assertDecoding(0x6ecec455, "fcmla v21.2d, v2.2d, v14.2d, #0");
        assertDecoding(0x2e59d681, "fcmla v1.4h, v20.4h, v25.4h, #180");
        assertDecoding(0x6e46d5c6, "fcmla v6.8h, v14.8h, v6.8h, #180");

        assertDecoding(0x2f421253, "fcmla v19.4h, v18.4h, v2.h[0], #0");
        assertDecoding(0x6f9b5182, "fcmla v2.4s, v12.4s, v27.s[0], #180");
        assertDecoding(0x6f66736e, "fcmla v14.8h, v27.8h, v6.h[1], #270");
    }

    @Test
    public void test_fcmle_simd() {
        assertDecoding(0x6ea0dbd4, "fcmle v20.4s, v30.4s, #0.0");
    }

    @Test
    public void test_fcmlt_fpu() {
        assertDecoding(0x5ee0eb4c, "fcmlt d12,d26,#0.0");
        assertDecoding(0x5ef8e9bb, "fcmlt h27,h13,#0.0");
        assertDecoding(0x5ea0eb94, "fcmlt s20,s28,#0.0");
    }

    @Test
    public void test_fcmlt_simd() {
        assertDecoding(0x0ea0eb0f, "fcmlt v15.2s,v24.2s,#0.0");
        assertDecoding(0x0ef8e9f2, "fcmlt v18.4h,v15.4h,#0.0");
        assertDecoding(0x4ea0e975, "fcmlt v21.4s,v11.4s,#0.0");
        assertDecoding(0x4ef8ea5b, "fcmlt v27.8h,v18.8h,#0.0");
        assertDecoding(0x4ee0ebff, "fcmlt v31.2d,v31.2d,#0.0");
        assertDecoding(0x4ef8eb7f, "fcmlt v31.8h,v27.8h,#0.0");
    }

    @Test
    public void test_fcmp_fpu() {
        assertDecoding(0x1e602020, "fcmp d1, d0");
        assertDecoding(0x1e202060, "fcmp s3, s0");
        assertDecoding(0x1e602028, "fcmp d1,#0.0");
        assertDecoding(0x1e2020e8, "fcmp s7,#0.0");
        assertDecoding(0x1e7f2168, "fcmp d11,#0.0");
        assertDecoding(0x1e6322e8, "fcmp d23,#0.0");
        assertDecoding(0x1ee220a8, "fcmp h5,#0.0");
        assertDecoding(0x1e242148, "fcmp s10,#0.0");
        assertDecoding(0x1e222048, "fcmp s2,#0.0");
        assertDecoding(0x1e262368, "fcmp s27,#0.0");
    }

    @Test
    public void test_fcmpe_fpu() {
        assertDecoding(0x1e602038, "fcmpe d1, #0.0");
        assertDecoding(0x1e602030, "fcmpe d1, d0");
        assertDecoding(0x1e202038, "fcmpe s1, #0.0");
        assertDecoding(0x1e202030, "fcmpe s1, s0");
    }

    @Test
    public void test_fcsel_fpu() {
        assertDecoding(0x1e684d40, "fcsel d0, d10, d8, mi");
        assertDecoding(0x1e619d4c, "fcsel d12, d10, d1, ls");
        assertDecoding(0x1ee9dd4c, "fcsel h12, h10, h9, le");
        assertDecoding(0x1e2a4d60, "fcsel s0, s11, s10, mi");
    }

    @Test
    public void test_fcvt_fpu() {
        assertDecoding(0x1e22c020, "fcvt d0, s1");
        assertDecoding(0x1e23c200, "fcvt h0, s16");
        assertDecoding(0x1e63c021, "fcvt h1, d1");
        assertDecoding(0x1e624060, "fcvt s0, d3");
        assertDecoding(0x1ee24000, "fcvt s0, h0");
        assertDecoding(0x1e624001, "fcvt s1, d0");
    }

    @Test
    public void test_fcvtas_fpu() {
        assertDecoding(0x1e640020, "fcvtas w0, d1");
        assertDecoding(0x9e640000, "fcvtas x0, d0");
        assertDecoding(0x9e240000, "fcvtas x0, s0");
        assertDecoding(0x5e61c9b2, "fcvtas d18,d13");
        assertDecoding(0x5e61ca53, "fcvtas d19,d18");
        assertDecoding(0x5e21ca02, "fcvtas s2,s16");
        assertDecoding(0x5e21c895, "fcvtas s21,s4");
    }

    @Test
    public void test_fcvtas_simd() {
        assertDecoding(0x0e79caaa, "fcvtas v10.4h,v21.4h");
        assertDecoding(0x4e79cb76, "fcvtas v22.8h,v27.8h");
    }

    @Test
    public void test_fcvtau_simd() {
        assertDecoding(0x2e79cb2c, "fcvtau v12.4h,v25.4h");
        assertDecoding(0x6e79c9ef, "fcvtau v15.8h,v15.8h");
    }

    @Test
    public void test_fcvtl_simd() {
        assertDecoding(0x0e61780a, "fcvtl v10.2d,v0.2s");
        assertDecoding(0x0e21798e, "fcvtl v14.4s,v12.4h");
        assertDecoding(0x0e617afd, "fcvtl v29.2d,v23.2s");
    }

    @Test
    public void test_fcvtl2_simd() {
        assertDecoding(0x4e217bcf, "fcvtl2 v15.4s,v30.8h");
        assertDecoding(0x4e617ab0, "fcvtl2 v16.2d,v21.4s");
        assertDecoding(0x4e217b7f, "fcvtl2 v31.4s,v27.8h");
        assertDecoding(0x4e617907, "fcvtl2 v7.2d,v8.4s");
    }

    @Test
    public void test_fcvtms_fpu() {
        assertDecoding(0x1e700020, "fcvtms w0, d1");
        assertDecoding(0x9e700120, "fcvtms x0, d9");
        assertDecoding(0x5e61bae5, "fcvtms d5,d23");
    }

    @Test
    public void test_fcvtms_simd() {
        assertDecoding(0x0e21b898, "fcvtms v24.2s,v4.2s");
        assertDecoding(0x4e21ba6a, "fcvtms v10.4s,v19.4s");
        assertDecoding(0x0e79ba19, "fcvtms v25.4h,v16.4h");
        assertDecoding(0x4e79b817, "fcvtms v23.8h,v0.8h");
        assertDecoding(0x4e61b9fc, "fcvtms v28.2d,v15.2d");
    }

    @Test
    public void test_fcvtmu_fpu() {
        assertDecoding(0x9e710020, "fcvtmu x0, d1");
        assertDecoding(0x7e61b9ee, "fcvtmu d14,d15");
    }

    @Test
    public void test_fcvtmu_simd() {
        assertDecoding(0x2e21bb8e, "fcvtmu v14.2s,v28.2s");
        assertDecoding(0x6e21b8ab, "fcvtmu v11.4s,v5.4s");
        assertDecoding(0x2e79b9d1, "fcvtmu v17.4h,v14.4h");
        assertDecoding(0x6e79b9ec, "fcvtmu v12.8h,v15.8h");
        assertDecoding(0x6e61badd, "fcvtmu v29.2d,v22.2d");
    }

    @Test
    public void test_fcvtn_simd() {
        assertDecoding(0x0e216aba, "fcvtn v26.4h,v21.4s");
    }

    @Test
    public void test_fcvtn2_simd() {
        assertDecoding(0x4e216a60, "fcvtn2 v0.8h,v19.4s");
        assertDecoding(0x4e616813, "fcvtn2 v19.4s,v0.2d");
    }

    @Test
    public void test_fcvtns_fpu() {
        assertDecoding(0x5e61ab74, "fcvtns d20,d27");
        assertDecoding(0x5e21a8b9, "fcvtns s25,s5");
    }

    @Test
    public void test_fcvtns_simd() {
        assertDecoding(0x4e79ab0b, "fcvtns v11.8h,v24.8h");
        assertDecoding(0x4e21aa6f, "fcvtns v15.4s,v19.4s");
        assertDecoding(0x0e79a890, "fcvtns v16.4h,v4.4h");
        assertDecoding(0x0e21a812, "fcvtns v18.2s,v0.2s");
    }

    @Test
    public void test_fcvtnu_simd() {
        assertDecoding(0x2e21ab43, "fcvtnu v3.2s, v26.2s");
    }

    @Test
    public void test_fcvtps_fpu() {
        assertDecoding(0x9e680001, "fcvtps x1, d0");
        assertDecoding(0x5ee1a93c, "fcvtps d28,d9");
        assertDecoding(0x5ea1aa98, "fcvtps s24,s20");
        assertDecoding(0x1e6803e7, "fcvtps w7,d31");
    }

    @Test
    public void test_fcvtps_simd() {
        assertDecoding(0x4ea1aaae, "fcvtps v14.4s,v21.4s");
        assertDecoding(0x0ef9ab79, "fcvtps v25.4h,v27.4h");
        assertDecoding(0x4ee1a95a, "fcvtps v26.2d,v10.2d");
        assertDecoding(0x0ea1abdb, "fcvtps v27.2s,v30.2s");
        assertDecoding(0x4ee1ab5c, "fcvtps v28.2d,v26.2d");
        assertDecoding(0x0ef9aafe, "fcvtps v30.4h,v23.4h");
        assertDecoding(0x4ee1aa65, "fcvtps v5.2d,v19.2d");
    }

    @Test
    public void test_fcvtpu_fpu() {
        assertDecoding(0x9e690001, "fcvtpu x1, d0");
        assertDecoding(0x7ee1aaeb, "fcvtpu d11,d23");
        assertDecoding(0x7ea1a80d, "fcvtpu s13,s0");
    }

    @Test
    public void test_fcvtpu_simd() {
        assertDecoding(0x6ef9aa2a, "fcvtpu v10.8h,v17.8h");
        assertDecoding(0x2ea1a8f2, "fcvtpu v18.2s,v7.2s");
        assertDecoding(0x6ea1aade, "fcvtpu v30.4s,v22.4s");
        assertDecoding(0x2ef9a9a7, "fcvtpu v7.4h,v13.4h");
    }

    @Test
    public void test_fcvtxn_fpu() {
        assertDecoding(0x7e6169cb, "fcvtxn s11,d14");
    }

    @Test
    public void test_fcvtxn_simd() {
        assertDecoding(0x2e61693c, "fcvtxn v28.2s,v9.2d");
    }

    @Test
    public void test_fcvtxn2_simd() {
        assertDecoding(0x6e616bd6, "fcvtxn2 v22.4s,v30.2d");
    }

    @Test
    public void test_fcvtzs_fpu_2_regs() {
        assertDecoding(0x1e780020, "fcvtzs w0, d1");
        assertDecoding(0x1e380020, "fcvtzs w0, s1");

        assertDecoding(0x9e780020, "fcvtzs x0, d1");
        assertDecoding(0x9e380000, "fcvtzs x0, s0");
        assertDecoding(0x1e7803ff, "fcvtzs wzr,d31");
    }

    @Test
    public void test_fcvtzs_fpu_2_regs_imm() {
        assertDecoding(0x9ed8a664, "fcvtzs x4, h19, #23");
        assertDecoding(0x9e588015, "fcvtzs x21, d0, #32");
        assertDecoding(0x1e18f841, "fcvtzs w1, s2, #2");
        assertDecoding(0x1e58dc20, "fcvtzs w0, d1, #9");
        assertDecoding(0x5f57fd81, "fcvtzs d1,d12,#41");
        assertDecoding(0x5f57fd9f, "fcvtzs d31,d12,#41");
        assertDecoding(0x5f10fd11, "fcvtzs h17,h8,#16");
        assertDecoding(0x5f1fffe7, "fcvtzs h7,h31,#1");
        assertDecoding(0x5f28ff4a, "fcvtzs s10,s26,#24");
        assertDecoding(0x5f31ffe1, "fcvtzs s1,s31,#15");
        assertDecoding(0x1e588be0, "fcvtzs w0,d31,#30");
        assertDecoding(0x1e58e3fa, "fcvtzs w26,d31,#8");
        assertDecoding(0x9e5887ee, "fcvtzs x14,d31,#31");
    }

    @Test
    public void test_fcvtzs_simd() {
        assertDecoding(0x4ee1b800, "fcvtzs v0.2d, v0.2d");
        assertDecoding(0x0ef9bbd7, "fcvtzs v23.4h,v30.4h");
        assertDecoding(0x4ef9bb99, "fcvtzs v25.8h,v28.8h");
    }

    @Test
    public void test_fcvtzu_fpu() {
        assertDecoding(0x1e790020, "fcvtzu w0, d1");
        assertDecoding(0x1e390001, "fcvtzu w1, s0");
        assertDecoding(0x9e790001, "fcvtzu x1, d0");
        assertDecoding(0x9e390001, "fcvtzu x1, s0");
        assertDecoding(0x9e5945ad, "fcvtzu x13, d13, #47");
        assertDecoding(0x9e196d98, "fcvtzu x24, s12, #37");
        assertDecoding(0x9ed91b1d, "fcvtzu x29, h24, #58");
        assertDecoding(0x9ed9c3ff, "fcvtzu xzr, h31, #16");
        assertDecoding(0x7f5bff34, "fcvtzu d20, d25, #37");
        assertDecoding(0x1e59fbe2, "fcvtzu w2,d31,#2");
        assertDecoding(0x9e5933fc, "fcvtzu x28,d31,#52");
        assertDecoding(0x9e599bff, "fcvtzu xzr,d31,#26");
    }

    @Test
    public void test_fcvtzu_simd() {
        assertDecoding(0x2f2cfe80, "fcvtzu v0.2s, v20.2s, #20");
        assertDecoding(0x6f34fcc3, "fcvtzu v3.4s, v6.4s, #12");
        assertDecoding(0x6f61fd69, "fcvtzu v9.2d, v11.2d, #31");
        assertDecoding(0x2ef9bb6e, "fcvtzu v14.4h,v27.4h");
        assertDecoding(0x6ef9b88f, "fcvtzu v15.8h,v4.8h");
    }

    @Test
    public void test_fdiv_fpu() {
        assertDecoding(0x1e6a1820, "fdiv d0, d1, d10");
        assertDecoding(0x1efb19a9, "fdiv h9, h13, h27");
        assertDecoding(0x1e2a1820, "fdiv s0, s1, s10");
        assertDecoding(0x1e7f19ab, "fdiv d11,d13,d31");
    }

    @Test
    public void test_fjcvtzs_fpu() {
        assertDecoding(0x1e7e020d, "fjcvtzs w13,d16");
    }

    @Test
    public void test_fmadd_fpu() {
        assertDecoding(0x1f524c20, "fmadd d0, d1, d18, d19");
        assertDecoding(0x1fc574eb, "fmadd h11, h7, h5, h29");
        assertDecoding(0x1f024420, "fmadd s0, s1, s2, s17");
        assertDecoding(0x1f577d80, "fmadd d0,d12,d23,d31");
    }

    @Test
    public void test_fmax_fpu() {
        assertDecoding(0x1e7f484e, "fmax d14,d2,d31");
    }

    @Test
    public void test_fmax_simd() {
        assertDecoding(0x0e23f73a, "fmax v26.2s, v25.2s, v3.2s");
        assertDecoding(0x0e403587, "fmax v7.4h, v12.4h, v0.4h");
    }

    @Test
    public void test_fmaxnm_fpu() {
        assertDecoding(0x1e616800, "fmaxnm d0, d0, d1");
        assertDecoding(0x1e606929, "fmaxnm d9, d9, d0");
        assertDecoding(0x1e216800, "fmaxnm s0, s0, s1");
        assertDecoding(0x1e7f6a6a, "fmaxnm d10,d19,d31");
    }

    @Test
    public void test_fmaxnmp_simd() {
        assertDecoding(0x2e39c619, "fmaxnmp v25.2s, v16.2s, v25.2s");
        assertDecoding(0x7e70c835, "fmaxnmp d21,v1.2d");
        assertDecoding(0x7e30c9d9, "fmaxnmp s25,v14.2s");
    }

    @Test
    public void test_fmaxnmv_simd() {
        assertDecoding(0x4e30c88e, "fmaxnmv h14,v4.8h");
        assertDecoding(0x0e30cb14, "fmaxnmv h20,v24.4h");
        assertDecoding(0x6e30c977, "fmaxnmv s23,v11.4s");
    }

    @Test
    public void test_fminnm_fpu() {
        assertDecoding(0x1e617800, "fminnm d0, d0, d1");
        assertDecoding(0x1ee278d1, "fminnm h17, h6, h2");
        assertDecoding(0x1e217800, "fminnm s0, s0, s1");
    }

    @Test
    public void test_fminnm_simd() {
        assertDecoding(0x4eebc6d3, "fminnm v19.2d, v22.2d, v11.2d");
        assertDecoding(0x4ee1c597, "fminnm v23.2d, v12.2d, v1.2d");
    }

    @Test
    public void test_fminnmp_simd() {
        assertDecoding(0x2ebac78f, "fminnmp v15.2s, v28.2s, v26.2s");
    }

    @Test
    public void test_fminp_simd() {
        assertDecoding(0x6ee9f610, "fminp v16.2d, v16.2d, v9.2d");
        assertDecoding(0x2ec735c7, "fminp v7.4h, v14.4h, v7.4h");
        assertDecoding(0x7eb0f888, "fminp s8,v4.2s");
    }

    @Test
    public void test_fmla_simd_2fpu_index() {
        assertDecoding(0x5fba11ae, "fmla s14, s13, v26.s[1]");
    }

    @Ignore("See https://sourceware.org/bugzilla/show_bug.cgi?id=23212")
    @Test
    public void test_fmla_simd_2fpu_index_with_sz1L1() {
        assertDecoding(0x5ffe1a2a, "fmla d10, d17, v30.d[1]");
        assertDecoding(0x4ff119eb, "fmla v11.2d,v15.2d,v17.d[1]");
    }

    @Test
    public void test_fmla_simd_2vec_index() {
        assertDecoding(0x4e3ecce0, "fmla v0.4s, v7.4s, v30.4s");
        assertDecoding(0x0f9719e6, "fmla v6.2s, v15.2s, v23.s[2]");
        assertDecoding(0x0f0312c9, "fmla v9.4h, v22.4h, v3.h[0]");
    }

    @Ignore("See https://sourceware.org/bugzilla/show_bug.cgi?id=23212")
    @Test
    public void test_fmlal_simd() {
        assertDecoding(0x0e6aefeb, "fmlal v11.2s,v31.2h,v10.2h");
        assertDecoding(0x4e75efae, "fmlal v14.4s,v29.4h,v21.4h");

        assertDecoding(0x4f9c032a, "fmlal v10.4s,v25.4h,v12.h[1]");
        assertDecoding(0x0fba01ec, "fmlal v12.2s,v15.2h,v10.h[3]");
    }

    @Ignore("See https://sourceware.org/bugzilla/show_bug.cgi?id=23212")
    @Test
    public void test_fmlal2_simd() {
        assertDecoding(0x2e31ce80, "fmlal2 v0.2s,v20.2h,v17.2h");
        assertDecoding(0x6e35cd41, "fmlal2 v1.4s,v10.4h,v21.4h");

        assertDecoding(0x6faf8b8b, "fmlal2 v11.4s,v28.4h,v15.h[6]");
        assertDecoding(0x2f8382ac, "fmlal2 v12.2s,v21.2h,v3.h[0]");
    }

    @Test
    public void test_fmls_simd() {
        assertDecoding(0x5fae5bef, "fmls s15, s31, v14.s[3]");
        assertDecoding(0x5f395366, "fmls h6, h27, v9.h[3]");

        assertDecoding(0x4f8f53e0, "fmls v0.4s, v31.4s, v15.s[0]");
        assertDecoding(0x4f8b50f8, "fmls v24.4s, v7.4s, v11.s[0]");
        assertDecoding(0x4fad5072, "fmls v18.4s,v3.4s, v13.s[1]");
        assertDecoding(0x4f375200, "fmls v0.8h, v16.8h, v7.h[3]");
        assertDecoding(0x4f1b53eb, "fmls v11.8h, v31.8h, v11.h[1]");
        assertDecoding(0x0f36518f, "fmls v15.4h, v12.4h, v6.h[3]");
        assertDecoding(0x0ed50c22, "fmls v2.4h, v1.4h, v21.4h");
        assertDecoding(0x4fd95014, "fmls v20.2d, v0.2d, v25.d[0]");
        assertDecoding(0x0f08501e, "fmls v30.4h, v0.4h, v8.h[0]");
        assertDecoding(0x4f2a59df, "fmls v31.8h, v14.8h, v10.h[6]");
        assertDecoding(0x4f2b5ba5, "fmls v5.8h, v29.8h, v11.h[6]");
        assertDecoding(0x4ef3cc09, "fmls v9.2d, v0.2d, v19.2d");
    }

    @Ignore("see https://sourceware.org/bugzilla/show_bug.cgi?id=23192")
    @Test
    public void test_fmls_bug() {
        assertDecoding(0x5fe158bb, "fmls d27, d5, v1.d[1]");
        assertDecoding(0x5fef5020, "fmls d0, d1, v15.d[0]");
    }

    @Ignore("see https://sourceware.org/bugzilla/show_bug.cgi?id=23192")
    @Test
    public void test_fmlsl_simd() {
        assertDecoding(0x0eb0ed30, "fmlsl v16.2s,v9.2h,v16.2h");
        assertDecoding(0x4ea3ec21, "fmlsl v1.4s,v1.4h,v3.4h");

        assertDecoding(0x4faa42c1, "fmlsl v1.4s,v22.4h,v10.h[2]");
        assertDecoding(0x0f9a49af, "fmlsl v15.2s,v13.2h,v10.h[5]");
    }

    @Ignore("see https://sourceware.org/bugzilla/show_bug.cgi?id=23192")
    @Test
    public void test_fmlsl2_simd() {
        assertDecoding(0x2efccf21, "fmlsl2 v1.2s,v25.2h,v28.2h");
        assertDecoding(0x6ef7cd6c, "fmlsl2 v12.4s,v11.4h,v23.4h");
        assertDecoding(0x6f92cb8a, "fmlsl2 v10.4s,v28.4h,v2.h[5]");
        assertDecoding(0x2f9cc1cc, "fmlsl2 v12.2s,v14.2h,v12.h[1]");
    }

    @Test
    public void test_fmov_fpu() {
        assertDecoding(0x1e7e1000, "fmov d0, #-1.000000000000000000e+00");
        assertDecoding(0x1e749000, "fmov d0, #-1.000000000000000000e+01");
        assertDecoding(0x1e701000, "fmov d0, #-2.000000000000000000e+00");
        assertDecoding(0x1e76d000, "fmov d0, #-2.200000000000000000e+01");
        assertDecoding(0x1e7a1000, "fmov d0, #-2.500000000000000000e-01");
        assertDecoding(0x1e7c1000, "fmov d0, #-5.000000000000000000e-01");
        assertDecoding(0x1e7c5000, "fmov d0, #-5.625000000000000000e-01");
        assertDecoding(0x1e741000, "fmov d0, #-8.000000000000000000e+00");
        assertDecoding(0x1e6e1000, "fmov d0, #1.000000000000000000e+00");
        assertDecoding(0x1e649000, "fmov d0, #1.000000000000000000e+01");
        assertDecoding(0x1e64d000, "fmov d0, #1.100000000000000000e+01");
        assertDecoding(0x1e651000, "fmov d0, #1.200000000000000000e+01");
        assertDecoding(0x1e681000, "fmov d0, #1.250000000000000000e-01");
        assertDecoding(0x1e655000, "fmov d0, #1.300000000000000000e+01");
        assertDecoding(0x1e659000, "fmov d0, #1.400000000000000000e+01");
        assertDecoding(0x1e6f1000, "fmov d0, #1.500000000000000000e+00");
        assertDecoding(0x1e65d000, "fmov d0, #1.500000000000000000e+01");
        assertDecoding(0x1e661000, "fmov d0, #1.600000000000000000e+01");
        assertDecoding(0x1e663000, "fmov d0, #1.700000000000000000e+01");
        assertDecoding(0x1e665000, "fmov d0, #1.800000000000000000e+01");
        assertDecoding(0x1e691000, "fmov d0, #1.875000000000000000e-01");
        assertDecoding(0x1e667000, "fmov d0, #1.900000000000000000e+01");
        assertDecoding(0x1e601000, "fmov d0, #2.000000000000000000e+00");
        assertDecoding(0x1e669000, "fmov d0, #2.000000000000000000e+01");
        assertDecoding(0x1e66b000, "fmov d0, #2.100000000000000000e+01");
        assertDecoding(0x1e66d000, "fmov d0, #2.200000000000000000e+01");
        assertDecoding(0x1e66f000, "fmov d0, #2.300000000000000000e+01");
        assertDecoding(0x1e671000, "fmov d0, #2.400000000000000000e+01");
        assertDecoding(0x1e609000, "fmov d0, #2.500000000000000000e+00");
        assertDecoding(0x1e673000, "fmov d0, #2.500000000000000000e+01");
        assertDecoding(0x1e6a1000, "fmov d0, #2.500000000000000000e-01");
        assertDecoding(0x1e675000, "fmov d0, #2.600000000000000000e+01");
        assertDecoding(0x1e677000, "fmov d0, #2.700000000000000000e+01");
        assertDecoding(0x1e679000, "fmov d0, #2.800000000000000000e+01");
        assertDecoding(0x1e67b000, "fmov d0, #2.900000000000000000e+01");
        assertDecoding(0x1e611000, "fmov d0, #3.000000000000000000e+00");
        assertDecoding(0x1e67d000, "fmov d0, #3.000000000000000000e+01");
        assertDecoding(0x1e67f000, "fmov d0, #3.100000000000000000e+01");
        assertDecoding(0x1e619000, "fmov d0, #3.500000000000000000e+00");
        assertDecoding(0x1e6b1000, "fmov d0, #3.750000000000000000e-01");
        assertDecoding(0x1e621000, "fmov d0, #4.000000000000000000e+00");
        assertDecoding(0x1e625000, "fmov d0, #4.500000000000000000e+00");
        assertDecoding(0x1e629000, "fmov d0, #5.000000000000000000e+00");
        assertDecoding(0x1e6c1000, "fmov d0, #5.000000000000000000e-01");
        assertDecoding(0x1e62d000, "fmov d0, #5.500000000000000000e+00");
        assertDecoding(0x1e631000, "fmov d0, #6.000000000000000000e+00");
        assertDecoding(0x1e6c9000, "fmov d0, #6.250000000000000000e-01");
        assertDecoding(0x1e635000, "fmov d0, #6.500000000000000000e+00");
        assertDecoding(0x1e639000, "fmov d0, #7.000000000000000000e+00");
        assertDecoding(0x1e63d000, "fmov d0, #7.500000000000000000e+00");
        assertDecoding(0x1e6d1000, "fmov d0, #7.500000000000000000e-01");
        assertDecoding(0x1e641000, "fmov d0, #8.000000000000000000e+00");
        assertDecoding(0x1e643000, "fmov d0, #8.500000000000000000e+00");
        assertDecoding(0x1e6d9000, "fmov d0, #8.750000000000000000e-01");
        assertDecoding(0x1e645000, "fmov d0, #9.000000000000000000e+00");
        assertDecoding(0x1e604020, "fmov d0, d1");
        assertDecoding(0x9e670140, "fmov d0, x10");
        assertDecoding(0x1e6c100a, "fmov d10, #5.000000000000000000e-01");
        assertDecoding(0x1e3c5000, "fmov s0, #-5.625000000000000000e-01");
        assertDecoding(0x1e2cf000, "fmov s0, #7.187500000000000000e-01");
        assertDecoding(0x1e204020, "fmov s0, s1");
        assertDecoding(0x1e270260, "fmov s0, w19");
        assertDecoding(0x1e260020, "fmov w0, s1");
        assertDecoding(0x9e660020, "fmov x0, d1");

    }

    @Ignore("See https://sourceware.org/bugzilla/show_bug.cgi?id=20319")
    @Test
    public void test_fmov_fpu_objdump_bug() {
        assertDecoding(0x1e670055, "fmov d21,w2");
        assertDecoding(0x9e2700d3, "fmov s19,x6");
        assertDecoding(0x1e66016d, "fmov w13,d11");
        assertDecoding(0x9e26032f, "fmov x15,s25");
        assertDecoding(0x9e26001f, "fmov xzr,s0");
    }

    @Test
    public void test_fmov_simd() {
        assertDecoding(0x9eaf0140, "fmov v0.d[1], x10");
        assertDecoding(0x0f01fdf6, "fmov v22.4h, #1.550000000000000000e+01");
        assertDecoding(0x9eae0041, "fmov x1, v2.d[1]");
        assertDecoding(0x4f04f434, "fmov v20.4s,#-2.125000000000000000e+00");
    }

    @Test
    public void test_fmsub_fpu() {
        assertDecoding(0x1f4bb020, "fmsub d0, d1, d11, d12");
        assertDecoding(0x1fc9d600, "fmsub h0, h16, h9, h21");
        assertDecoding(0x1f1fc5e0, "fmsub s0, s15, s31, s17");
    }

    @Test
    public void test_fmul_fpu() {
        assertDecoding(0x1e620820, "fmul d0, d1, d2");
        assertDecoding(0x1e220820, "fmul s0, s1, s2");
    }

    @Test
    public void test_fmul_simd() {
        assertDecoding(0x5fc49ad1, "fmul d17, d22, v4.d[1]");
        assertDecoding(0x5f1b9292, "fmul h18, h20, v11.h[1]");
        assertDecoding(0x5fa49306, "fmul s6, s24, v4.s[1]");
        assertDecoding(0x4f89926b, "fmul v11.4s, v19.4s, v9.s[0]");
        assertDecoding(0x0f2f9195, "fmul v21.4h, v12.4h, v15.h[2]");
        assertDecoding(0x2e5f1efd, "fmul v29.4h,v23.4h,v31.4h");
        assertDecoding(0x0fb59b1c, "fmul v28.2s, v24.2s, v21.s[3]");
        assertDecoding(0x4f159a1c, "fmul v28.8h, v16.8h, v5.h[5]");
    }

    @Ignore("See https://sourceware.org/bugzilla/show_bug.cgi?id=23212")
    @Test
    public void test_fmul_simd_objdump_bug() {
        assertDecoding(0x5fe49a80, "fmul d0,d20,v4.d[1]");
    }

    @Test
    public void test_fmulx_fpu() {
        assertDecoding(0x5e67de9d, "fmulx d29, d20, d7");
    }

    @Test
    public void test_fmulx_simd() {
        assertDecoding(0x7f159a13, "fmulx h19, h16, v5.h[5]");
        assertDecoding(0x7fa69842, "fmulx s2, s2, v6.s[3]");
        assertDecoding(0x6f899132, "fmulx v18.4s, v9.4s, v9.s[0]");
        assertDecoding(0x6f9c9215, "fmulx v21.4s, v16.4s, v28.s[0]");
        assertDecoding(0x2fa99a95, "fmulx v21.2s, v20.2s, v9.s[3]");
        assertDecoding(0x0e5b1e38, "fmulx v24.4h, v17.4h, v27.4h");
    }

    @Test
    public void test_fneg_fpu() {
        assertDecoding(0x1e614020, "fneg d0, d1");
        assertDecoding(0x1e214100, "fneg s0, s8");
    }

    @Test
    public void test_fneg_simd() {
        assertDecoding(0x6ee0f800, "fneg v0.2d, v0.2d");
        assertDecoding(0x2ea0f800, "fneg v0.2s, v0.2s");
        assertDecoding(0x6ee0f821, "fneg v1.2d, v1.2d");
        assertDecoding(0x2ea0f821, "fneg v1.2s, v1.2s");
        assertDecoding(0x2ea0f842, "fneg v2.2s, v2.2s");
        assertDecoding(0x2ef8fae0, "fneg v0.4h,v23.4h");
        assertDecoding(0x6ef8f8c0, "fneg v0.8h,v6.8h");
    }

    @Test
    public void test_fnmadd_fpu() {
        assertDecoding(0x1f68160c, "fnmadd d12, d16, d8, d5");
        assertDecoding(0x1fe47eec, "fnmadd h12, h23, h4, h31");
        assertDecoding(0x1f3777ed, "fnmadd s13, s31, s23, s29");
    }

    @Test
    public void test_fnmsub_fpu() {
        assertDecoding(0x1f618c40, "fnmsub d0, d2, d1, d3");
        assertDecoding(0x1f63c613, "fnmsub d19, d16, d3, d17");
        assertDecoding(0x1fedd0b1, "fnmsub h17, h5, h13, h20");
        assertDecoding(0x1f228c20, "fnmsub s0, s1, s2, s3");
    }

    @Test
    public void test_fnmul_fpu() {
        assertDecoding(0x1e608961, "fnmul d1, d11, d0");
        assertDecoding(0x1e228800, "fnmul s0, s0, s2");
    }

    @Test
    public void test_frecpe_simd() {
        assertDecoding(0x0ef9db51, "frecpe v17.4h,v26.4h");
    }

    @Test
    public void test_frecps_simd() {
        assertDecoding(0x4e38fd21, "frecps v1.4s, v9.4s, v24.4s");
        assertDecoding(0x0e423fac, "frecps v12.4h, v29.4h, v2.4h");
    }

    @Test
    public void test_frinta_fpu() {
        assertDecoding(0x1e664100, "frinta d0, d8");
        assertDecoding(0x1e664001, "frinta d1, d0");
        assertDecoding(0x1e264000, "frinta s0, s0");
    }

    @Test
    public void test_frinti_fpu() {
        assertDecoding(0x1e67c000, "frinti d0, d0");
        assertDecoding(0x1e67c021, "frinti d1, d1");
        assertDecoding(0x1e27c000, "frinti s0, s0");
    }

    @Test
    public void test_frintm_fpu() {
        assertDecoding(0x1e654020, "frintm d0, d1");
        assertDecoding(0x1e254000, "frintm s0, s0");
    }

    @Test
    public void test_frintm_simd() {
        assertDecoding(0x4e799bfe, "frintm v30.8h, v31.8h");
    }

    @Test
    public void test_frintn_simd() {
        assertDecoding(0x4e798998, "frintn v24.8h,v12.8h");
    }

    @Test
    public void test_frintp_fpu() {
        assertDecoding(0x1e64c008, "frintp d8, d0");
        assertDecoding(0x1e24c000, "frintp s0, s0");
    }

    @Test
    public void test_frintp_simd() {
        assertDecoding(0x0ea1890b, "frintp v11.2s, v8.2s");
    }

    @Test
    public void test_frintx_fpu() {
        assertDecoding(0x1e674000, "frintx d0, d0");
        assertDecoding(0x1e274000, "frintx s0, s0");
    }

    @Test
    public void test_frintx_fpu_simd() {
        assertDecoding(0x6e7998ba, "frintx v26.8h,v5.8h");
    }

    @Test
    public void test_frintz_fpu() {
        assertDecoding(0x1e65c022, "frintz d2, d1");
        assertDecoding(0x1e25c000, "frintz s0, s0");
    }

    @Test
    public void test_frsqrts_fpu() {
        assertDecoding(0x5ec23c88, "frsqrts h8, h4, h2");
    }

    @Test
    public void test_frsqrts_simd() {
        assertDecoding(0x4eaffccc, "frsqrts v12.4s, v6.4s, v15.4s");
        assertDecoding(0x4ec23db1, "frsqrts v17.8h, v13.8h, v2.8h");
        assertDecoding(0x0eaefcda, "frsqrts v26.2s, v6.2s, v14.2s");
        assertDecoding(0x4edf3d9d, "frsqrts v29.8h, v12.8h, v31.8h");
    }

    @Test
    public void test_frsqrte_simd() {
        assertDecoding(0x2ef9db6c, "frsqrte v12.4h,v27.4h");
        assertDecoding(0x6ef9da2c, "frsqrte v12.8h,v17.8h");
    }

    @Test
    public void test_frsqrt_simd() {
        assertDecoding(0x2ef9fbea, "fsqrt v10.4h,v31.4h");
        assertDecoding(0x2ef9f8fc, "fsqrt v28.4h,v7.4h");
        assertDecoding(0x6ef9f910, "fsqrt v16.8h,v8.8h");
    }

    @Test
    public void test_fsqrt_fpu() {
        assertDecoding(0x1e61c020, "fsqrt d0, d1");
        assertDecoding(0x1e21c00e, "fsqrt s14, s0");
    }

    @Test
    public void test_fsub_fpu() {
        assertDecoding(0x1e6b3820, "fsub d0, d1, d11");
        assertDecoding(0x1e283961, "fsub s1, s11, s8");
    }

    @Test
    public void test_fsub_simd() {
        assertDecoding(0x0ed91558, "fsub v24.4h, v10.4h, v25.4h");
        assertDecoding(0x4ef9d4e8, "fsub v8.2d, v7.2d, v25.2d");
    }


    @Test
    public void test_hint() {
        assertDecoding(0xd50320df, "hint #0x6");
        assertDecoding(0xd503213f, "hint #0x9");
        assertDecoding(0xd503217f, "hint #0xb");
        assertDecoding(0xd50321bf, "hint #0xd");
        assertDecoding(0xd50321ff, "hint #0xf");
        assertDecoding(0xd503225f, "hint #0x12");
        assertDecoding(0xd503227f, "hint #0x13");
        assertDecoding(0xd50322bf, "hint #0x15");
        assertDecoding(0xd50322df, "hint #0x16");
        assertDecoding(0xd50322ff, "hint #0x17");
        assertDecoding(0xd5032a7f, "hint #0x53");
    }

    @Test
    public void test_hvc() {
        assertDecoding(0xd400f702, "hvc #0x7b8");
    }

    @Test
    public void test_ic() {
        assertDecoding(0xd50b7520, "ic ivau, x0");
        assertDecoding(0xd508711c, "ic ialluis");
        assertDecoding(0xd50b753f, "ic ivau,xzr");
    }

    @Test
    public void test_isb() {
        assertDecoding(0xd5033fdf, "isb");
        assertDecoding(0xd5033bdf, "isb #0xb");
    }

    @Test
    public void test_ld1_simd_index() {
        assertDecoding(0x0d4080a0, "ld1 {v0.s}[0], [x5]");
        assertDecoding(0x0d408001, "ld1 {v1.s}[0], [x0]");
        assertDecoding(0x0dc300cc, "ld1 {v12.b}[0], [x6], x3");
        assertDecoding(0x0dd4400d, "ld1 {v13.h}[0], [x0], x20");
        assertDecoding(0x4dd40eb6, "ld1 {v22.b}[11], [x21], x20");
        assertDecoding(0x4dc30b5a, "ld1 {v26.b}[10], [x26], x3");
        assertDecoding(0x4dda105b, "ld1 {v27.b}[12], [x2], x26");

    }

    @Test
    public void test_ld1_simd_other() {
        assertDecoding(0x4cdf7000, "ld1 {v0.16b}, [x0], #16");
        assertDecoding(0x4cc87000, "ld1 {v0.16b}, [x0], x8");
        assertDecoding(0x0c407c20, "ld1 {v0.1d}, [x1]");
        assertDecoding(0x4c40a260, "ld1 {v0.16b, v1.16b}, [x19]");
        assertDecoding(0x4cdfac00, "ld1 {v0.2d, v1.2d}, [x0], #32");
        assertDecoding(0x4c40ac20, "ld1 {v0.2d, v1.2d}, [x1]");
        assertDecoding(0x4cdf2020, "ld1 {v0.16b-v3.16b}, [x1], #64");
        assertDecoding(0x4cdf7c00, "ld1 {v0.2d}, [x0], #16");
        assertDecoding(0x4c407c20, "ld1 {v0.2d}, [x1]");
        assertDecoding(0x4c40a800, "ld1 {v0.4s, v1.4s}, [x0]");
        assertDecoding(0x4cdf29e0, "ld1 {v0.4s-v3.4s}, [x15], #64");
        assertDecoding(0x4c407840, "ld1 {v0.4s}, [x2]");
        assertDecoding(0x4cdf7840, "ld1 {v0.4s}, [x2], #16");
        assertDecoding(0x4cdfa041, "ld1 {v1.16b, v2.16b}, [x2], #32");
        assertDecoding(0x4cdfa061, "ld1 {v1.16b, v2.16b}, [x3], #32");
        assertDecoding(0x4cdf2021, "ld1 {v1.16b-v4.16b}, [x1], #64");
        assertDecoding(0x4cdf2041, "ld1 {v1.16b-v4.16b}, [x2], #64");
        assertDecoding(0x4cdf7021, "ld1 {v1.16b}, [x1], #16");
        assertDecoding(0x4cdf7041, "ld1 {v1.16b}, [x2], #16");
        assertDecoding(0x4c407061, "ld1 {v1.16b}, [x3]");
        assertDecoding(0x4c407d41, "ld1 {v1.2d}, [x10]");
        assertDecoding(0x4cdf7d61, "ld1 {v1.2d}, [x11], #16");
        assertDecoding(0x4c407d01, "ld1 {v1.2d}, [x8]");
        assertDecoding(0x4cdfa861, "ld1 {v1.4s, v2.4s}, [x3], #32");
        assertDecoding(0x4c407801, "ld1 {v1.4s}, [x0]");
        assertDecoding(0x4cdf7841, "ld1 {v1.4s}, [x2], #16");
        assertDecoding(0x4c407861, "ld1 {v1.4s}, [x3]");
        assertDecoding(0x4cdf202c, "ld1 {v12.16b-v15.16b}, [x1], #64");
        assertDecoding(0x4cdfa00e, "ld1 {v14.16b, v15.16b}, [x0], #32");
        assertDecoding(0x4cdfa030, "ld1 {v16.16b, v17.16b}, [x1], #32");
        assertDecoding(0x4cdf2030, "ld1 {v16.16b-v19.16b}, [x1], #64");
        assertDecoding(0x4cc87010, "ld1 {v16.16b}, [x0], x8");
        assertDecoding(0x4cdf7030, "ld1 {v16.16b}, [x1], #16");
        assertDecoding(0x4c407090, "ld1 {v16.16b}, [x4]");
        assertDecoding(0x0cdf7c30, "ld1 {v16.1d}, [x1], #8");
        assertDecoding(0x4cdfac30, "ld1 {v16.2d, v17.2d}, [x1], #32");
        assertDecoding(0x4cdf7c30, "ld1 {v16.2d}, [x1], #16");
        assertDecoding(0x4cdf7c50, "ld1 {v16.2d}, [x2], #16");
        assertDecoding(0x4ccc7c50, "ld1 {v16.2d}, [x2], x12");
        assertDecoding(0x4cdf7d30, "ld1 {v16.2d}, [x9], #16");
        assertDecoding(0x4c40a870, "ld1 {v16.4s, v17.4s}, [x3]");
        assertDecoding(0x4c402890, "ld1 {v16.4s-v19.4s}, [x4]");
        assertDecoding(0x4c4079d0, "ld1 {v16.4s}, [x14]");
        assertDecoding(0x4cdf7870, "ld1 {v16.4s}, [x3], #16");
        assertDecoding(0x4c4078d0, "ld1 {v16.4s}, [x6]");
        assertDecoding(0x4cdf78f0, "ld1 {v16.4s}, [x7], #16");
        assertDecoding(0x0cd723b0, "ld1 {v16.8b-v19.8b}, [x29], x23");
        assertDecoding(0x4cdf2011, "ld1 {v17.16b-v20.16b}, [x0], #64");
        assertDecoding(0x4cdf7031, "ld1 {v17.16b}, [x1], #16");
        assertDecoding(0x4c407c11, "ld1 {v17.2d}, [x0]");
        assertDecoding(0x4c407c31, "ld1 {v17.2d}, [x1]");
        assertDecoding(0x4ccc7c51, "ld1 {v17.2d}, [x2], x12");
        assertDecoding(0x4c407991, "ld1 {v17.4s}, [x12]");
        assertDecoding(0x4c407871, "ld1 {v17.4s}, [x3]");
        assertDecoding(0x4cdf7871, "ld1 {v17.4s}, [x3], #16");
        assertDecoding(0x4c4078f1, "ld1 {v17.4s}, [x7]");
        assertDecoding(0x4cdf78f1, "ld1 {v17.4s}, [x7], #16");
        assertDecoding(0x4cdfa032, "ld1 {v18.16b, v19.16b}, [x1], #32");
        assertDecoding(0x4cdf7012, "ld1 {v18.16b}, [x0], #16");
        assertDecoding(0x4cdf7032, "ld1 {v18.16b}, [x1], #16");
        assertDecoding(0x4cdfad52, "ld1 {v18.2d, v19.2d}, [x10], #32");
        assertDecoding(0x4cdfac32, "ld1 {v18.2d, v19.2d}, [x1], #32");
        assertDecoding(0x4c402d52, "ld1 {v18.2d-v21.2d}, [x10]");
        assertDecoding(0x4cdfa8f2, "ld1 {v18.4s, v19.4s}, [x7], #32");
        assertDecoding(0x4c407013, "ld1 {v19.16b}, [x0]");
        assertDecoding(0x4cdf7013, "ld1 {v19.16b}, [x0], #16");
        assertDecoding(0x4cdf7033, "ld1 {v19.16b}, [x1], #16");
        assertDecoding(0x4c40a002, "ld1 {v2.16b, v3.16b}, [x0]");
        assertDecoding(0x4c40a022, "ld1 {v2.16b, v3.16b}, [x1]");
        assertDecoding(0x4c40a0e2, "ld1 {v2.16b, v3.16b}, [x7]");
        assertDecoding(0x4cdf6042, "ld1 {v2.16b-v4.16b}, [x2], #48");
        assertDecoding(0x4c407002, "ld1 {v2.16b}, [x0]");
        assertDecoding(0x4cdf7002, "ld1 {v2.16b}, [x0], #16");
        assertDecoding(0x4ccc7002, "ld1 {v2.16b}, [x0], x12");
        assertDecoding(0x4cdf7042, "ld1 {v2.16b}, [x2], #16");
        assertDecoding(0x4c407102, "ld1 {v2.16b}, [x8]");
        assertDecoding(0x4cdfac02, "ld1 {v2.2d, v3.2d}, [x0], #32");
        assertDecoding(0x4c407d62, "ld1 {v2.2d}, [x11]");
        assertDecoding(0x4c40a8e2, "ld1 {v2.4s, v3.4s}, [x7]");
        assertDecoding(0x4cdfa074, "ld1 {v20.16b, v21.16b}, [x3], #32");
        assertDecoding(0x4cdf2034, "ld1 {v20.16b-v23.16b}, [x1], #64");
        assertDecoding(0x4c402094, "ld1 {v20.16b-v23.16b}, [x4]");
        assertDecoding(0x0cc72e34, "ld1 {v20.1d-v23.1d}, [x17], x7");
        assertDecoding(0x4c40ad74, "ld1 {v20.2d, v21.2d}, [x11]");
        assertDecoding(0x4c40ac34, "ld1 {v20.2d, v21.2d}, [x1]");
        assertDecoding(0x4cdfac34, "ld1 {v20.2d, v21.2d}, [x1], #32");
        assertDecoding(0x4cdf2d54, "ld1 {v20.2d-v23.2d}, [x10], #64");
        assertDecoding(0x4cdf2d74, "ld1 {v20.2d-v23.2d}, [x11], #64");
        assertDecoding(0x4cdfa8f4, "ld1 {v20.4s, v21.4s}, [x7], #32");
        assertDecoding(0x4c402894, "ld1 {v20.4s-v23.4s}, [x4]");
        assertDecoding(0x4cdf2015, "ld1 {v21.16b-v24.16b}, [x0], #64");
        assertDecoding(0x4cdfa076, "ld1 {v22.16b, v23.16b}, [x3], #32");
        assertDecoding(0x4cce7076, "ld1 {v22.16b}, [x3], x14");
        assertDecoding(0x0c407c36, "ld1 {v22.1d}, [x1]");
        assertDecoding(0x0cdf7c36, "ld1 {v22.1d}, [x1], #8");
        assertDecoding(0x4c40ad76, "ld1 {v22.2d, v23.2d}, [x11]");
        assertDecoding(0x4c407c36, "ld1 {v22.2d}, [x1]");
        assertDecoding(0x4cdf7c36, "ld1 {v22.2d}, [x1], #16");
        assertDecoding(0x4ccc7c36, "ld1 {v22.2d}, [x1], x12");
        assertDecoding(0x4cdfa8f6, "ld1 {v22.4s, v23.4s}, [x7], #32");
        assertDecoding(0x4cdf2038, "ld1 {v24.16b-v27.16b}, [x1], #64");
        assertDecoding(0x4c4070d8, "ld1 {v24.16b}, [x6]");
        assertDecoding(0x4c402d58, "ld1 {v24.2d-v27.2d}, [x10]");
        assertDecoding(0x4cdf2d58, "ld1 {v24.2d-v27.2d}, [x10], #64");
        assertDecoding(0x4cdf2d78, "ld1 {v24.2d-v27.2d}, [x11], #64");
        assertDecoding(0x4cdf78b8, "ld1 {v24.4s}, [x5], #16");
        assertDecoding(0x4cdf6019, "ld1 {v25.16b-v27.16b}, [x0], #48");
        assertDecoding(0x4c40a879, "ld1 {v25.4s, v26.4s}, [x3]");
        assertDecoding(0x4cdc25d9, "ld1 {v25.8h-v28.8h}, [x14], x28");
        assertDecoding(0x4c407c3a, "ld1 {v26.2d}, [x1]");
        assertDecoding(0x4cdf7c3a, "ld1 {v26.2d}, [x1], #16");
        assertDecoding(0x4ccc7c3a, "ld1 {v26.2d}, [x1], x12");
        assertDecoding(0x4c40789b, "ld1 {v27.4s}, [x4]");
        assertDecoding(0x4cdfa01c, "ld1 {v28.16b, v29.16b}, [x0], #32");
        assertDecoding(0x4cdf2d5c, "ld1 {v28.2d-v31.2d}, [x10], #64");
        assertDecoding(0x4c402d7c, "ld1 {v28.2d-v31.2d}, [x11]");
        assertDecoding(0x4cdf6043, "ld1 {v3.16b-v5.16b}, [x2], #48");
        assertDecoding(0x4c407003, "ld1 {v3.16b}, [x0]");
        assertDecoding(0x4cdf7003, "ld1 {v3.16b}, [x0], #16");
        assertDecoding(0x4cdf7043, "ld1 {v3.16b}, [x2], #16");
        assertDecoding(0x4dda51e3, "ld1 {v3.h}[6], [x15], x26");
        assertDecoding(0x4c40a01e, "ld1 {v30.16b, v31.16b}, [x0]");
        assertDecoding(0x4cc9ad3e, "ld1 {v30.2d, v31.2d}, [x9], x9");
        assertDecoding(0x4c4078bf, "ld1 {v31.4s}, [x5]");
        assertDecoding(0x4c40a004, "ld1 {v4.16b, v5.16b}, [x0]");
        assertDecoding(0x4c40a024, "ld1 {v4.16b, v5.16b}, [x1]");
        assertDecoding(0x4cdf2024, "ld1 {v4.16b-v7.16b}, [x1], #64");
        assertDecoding(0x4c407004, "ld1 {v4.16b}, [x0]");
        assertDecoding(0x4c407d44, "ld1 {v4.2d}, [x10]");
        assertDecoding(0x4cdf29e4, "ld1 {v4.4s-v7.4s}, [x15], #64");
        assertDecoding(0x0cdf7004, "ld1 {v4.8b}, [x0], #8");
        assertDecoding(0x4cdf6045, "ld1 {v5.16b-v7.16b}, [x2], #48");
        assertDecoding(0x4cdf7045, "ld1 {v5.16b}, [x2], #16");
        assertDecoding(0x4c407105, "ld1 {v5.16b}, [x8]");
        assertDecoding(0x4c407d45, "ld1 {v5.2d}, [x10]");
        assertDecoding(0x4c40a006, "ld1 {v6.16b, v7.16b}, [x0]");
        assertDecoding(0x4c40a026, "ld1 {v6.16b, v7.16b}, [x1]");
        assertDecoding(0x4cdf6046, "ld1 {v6.16b-v8.16b}, [x2], #48");
        assertDecoding(0x4c407086, "ld1 {v6.16b}, [x4]");
        assertDecoding(0x4c407126, "ld1 {v6.16b}, [x9]");
        assertDecoding(0x4c407007, "ld1 {v7.16b}, [x0]");
        assertDecoding(0x4cdf7007, "ld1 {v7.16b}, [x0], #16");
        assertDecoding(0x4c407147, "ld1 {v7.16b}, [x10]");
        assertDecoding(0x4c4078e7, "ld1 {v7.4s}, [x7]");
        assertDecoding(0x4cdf2028, "ld1 {v8.16b-v11.16b}, [x1], #64");
        assertDecoding(0x4cce6c08, "ld1 {v8.2d-v10.2d}, [x0], x14");
        assertDecoding(0x4c407d48, "ld1 {v8.2d}, [x10]");
        assertDecoding(0x4c4079e8, "ld1 {v8.4s}, [x15]");
        assertDecoding(0x4cc66f69, "ld1 {v9.2d-v11.2d}, [x27], x6");
        assertDecoding(0x4c407d69, "ld1 {v9.2d}, [x11]");
    }

    @Test
    public void test_ld1r_simd() {
        assertDecoding(0x4d40cc52, "ld1r {v18.2d}, [x2]");
    }

    @Test
    public void test_ld2_simd() {
        assertDecoding(0x4cdf8c40, "ld2 {v0.2d, v1.2d}, [x2], #32");
        assertDecoding(0x4dee04ea, "ld2 {v10.b, v11.b}[9], [x7], x14");
        assertDecoding(0x4de192d1, "ld2 {v17.s, v18.s}[3], [x22], x1");
    }

    @Test
    public void test_ld2r_simd() {
        assertDecoding(0x0df0c425, "ld2r {v5.4h, v6.4h}, [x1], x16");
        assertDecoding(0x0d60c488, "ld2r {v8.4h, v9.4h}, [x4]");
        assertDecoding(0x4dffce67, "ld2r {v7.2d,v8.2d},[x19],#16");
    }

    @Test
    public void test_ld3_simd() {
        assertDecoding(0x0cd246c2, "ld3 {v2.4h-v4.4h}, [x22], x18");
        assertDecoding(0x4cc249da, "ld3 {v26.4s-v28.4s}, [x14], x2");
        assertDecoding(0x0cc8407b, "ld3 {v27.8b-v29.8b}, [x3], x8");
        assertDecoding(0x4cd5443f, "ld3 {v31.8h, v0.8h, v1.8h}, [x1], x21");

        assertDecoding(0x0dc0a3b2, "ld3 {v18.s-v20.s}[0], [x29], x0");
        assertDecoding(0x0dd33da2, "ld3 {v2.b-v4.b}[7], [x13], x19");
        assertDecoding(0x0dd16997, "ld3 {v23.h-v25.h}[1], [x12], x17");
        assertDecoding(0x4dc625f8, "ld3 {v24.b-v26.b}[9], [x15], x6");
        assertDecoding(0x0dc63819, "ld3 {v25.b-v27.b}[6], [x0], x6");
        assertDecoding(0x0dd632bb, "ld3 {v27.b-v29.b}[4], [x21], x22");
        assertDecoding(0x0dde261c, "ld3 {v28.b-v30.b}[1], [x16], x30");
        assertDecoding(0x4dd3a1c6, "ld3 {v6.s-v8.s}[2], [x14], x19");
        assertDecoding(0x0ddca4e8, "ld3 {v8.d-v10.d}[0], [x7], x28");
    }

    @Test
    public void test_ld3r_simd() {
        assertDecoding(0x0dd4e414, "ld3r {v20.4h-v22.4h}, [x0], x20");
    }

    @Test
    public void test_ld4_simd() {
        assertDecoding(0x4cda0fe0, "ld4 {v0.2d-v3.2d}, [sp], x26");
        assertDecoding(0x4c400e90, "ld4 {v16.2d-v19.2d}, [x20]");
        assertDecoding(0x4cdb0fb2, "ld4 {v18.2d-v21.2d}, [x29], x27");
        assertDecoding(0x4dfab272, "ld4 {v18.s-v21.s}[3], [x19], x26");
        assertDecoding(0x4cd406b3, "ld4 {v19.8h-v22.8h}, [x21], x20");
        assertDecoding(0x0de22293, "ld4 {v19.b-v22.b}[0], [x20], x2");
        assertDecoding(0x4cc10c75, "ld4 {v21.2d-v24.2d}, [x3], x1");
        assertDecoding(0x0dfd6a38, "ld4 {v24.h-v27.h}[1], [x17], x29");
        assertDecoding(0x4de22799, "ld4 {v25.b-v28.b}[9], [x28], x2");
        assertDecoding(0x4cd40ffd, "ld4 {v29.2d, v30.2d, v31.2d, v0.2d}, [sp], x20");
        assertDecoding(0x4df32aa3, "ld4 {v3.b-v6.b}[10], [x21], x19");
        assertDecoding(0x0de62aff, "ld4 {v31.b, v0.b, v1.b, v2.b}[2], [x23], x6");
        assertDecoding(0x4c400c64, "ld4 {v4.2d-v7.2d}, [x3]");
        assertDecoding(0x4c400cc4, "ld4 {v4.2d-v7.2d}, [x6]");
        assertDecoding(0x4c400ce4, "ld4 {v4.2d-v7.2d}, [x7]");
        assertDecoding(0x0def2928, "ld4 {v8.b-v11.b}[2], [x9], x15");
    }

    @Test
    public void test_ld4r_simd() {
        assertDecoding(0x0debe39b, "ld4r {v27.8b-v30.8b}, [x28], x11");
        assertDecoding(0x0de8ef49, "ld4r {v9.1d-v12.1d}, [x26], x8");
        assertDecoding(0x4dffe454, "ld4r {v20.8h-v23.8h}, [x2], #8");
    }

    @Test
    public void test_ldadd() {
        assertDecoding(0xb8260137, "ldadd w6, w23, [x9]");
    }

    @Test
    public void test_ldadda() {
        assertDecoding(0xb8b800b8, "ldadda w24, w24, [x5]");
        assertDecoding(0xf8be014a, "ldadda x30, x10, [x10]");
    }

    @Test
    public void test_ldaddah() {
        assertDecoding(0x78a90343, "ldaddah w9, w3, [x26]");
    }

    @Test
    public void test_ldaddal() {
        assertDecoding(0xf8f800f8, "ldaddal x24, x24, [x7]");
    }

    @Test
    public void test_ldaddb() {
        assertDecoding(0x38380038, "ldaddb w24, w24, [x1]");
    }

    @Test
    public void test_ldaddlb() {
        assertDecoding(0x386d03a7, "ldaddlb w13, w7, [x29]");
    }

    @Test
    public void test_ldaddlh() {
        assertDecoding(0x787a0148, "ldaddlh w26, w8, [x10]");
    }

    @Test
    public void test_ldaprb() {
        assertDecoding(0x38bfc197, "ldaprb w23, [x12]");
    }

    @Test
    public void test_ldar() {
        assertDecoding(0x88dffe60, "ldar w0, [x19]");
        assertDecoding(0x88dffeb3, "ldar w19, [x21]");
        assertDecoding(0xc8dffed3, "ldar x19, [x22]");
    }

    @Test
    public void test_ldarb() {
        assertDecoding(0x08dffe60, "ldarb w0, [x19]");
        assertDecoding(0x08dffc01, "ldarb w1, [x0]");
    }

    @Test
    public void test_ldarh() {
        assertDecoding(0x48cffd8c, "ldarh w12,[x12]");
    }

    @Test
    public void test_ldaxp() {
        assertDecoding(0x8876950b, "ldaxp w11, w5, [x8]");
        assertDecoding(0xc872bc6b, "ldaxp x11, x15, [x3]");
    }

    @Test
    public void test_ldaxr() {
        assertDecoding(0x8842eda0, "ldaxr w0, [x13]");
        assertDecoding(0x884ae9cb, "ldaxr w11, [x14]");
        assertDecoding(0xc85ffe61, "ldaxr x1, [x19]");
    }

    @Test
    public void test_ldaxrb() {
        assertDecoding(0x08568ced, "ldaxrb w13, [x7]");
    }

    @Test
    public void test_ldaxrh() {
        assertDecoding(0x484fb47d, "ldaxrh w29, [x3]");
    }

    @Test
    public void test_ldclral() {
        assertDecoding(0xf8fa1067, "ldclral x26, x7, [x3]");
    }

    @Test
    public void test_ldclrl() {
        assertDecoding(0xb860105d, "ldclrl w0, w29, [x2]");
    }

    @Test
    public void test_ldeorab() {
        assertDecoding(0x38b12385, "ldeorab w17, w5, [x28]");
    }

    @Test
    public void test_ldeoralb() {
        assertDecoding(0x38ed20d1, "ldeoralb w13, w17, [x6]");
    }

    @Test
    public void test_ldeorh() {
        assertDecoding(0x783c2096, "ldeorh w28, w22, [x4]");
    }

    @Test
    public void test_ldeorlb() {
        assertDecoding(0x386b203a, "ldeorlb w11, w26, [x1]");
        assertDecoding(0x387e22e6, "ldeorlb w30, w6, [x23]");
    }

    @Test
    public void test_ldlar() {
        assertDecoding(0x88d20dab, "ldlar w11, [x13]");
        assertDecoding(0x88cc66ff, "ldlar wzr, [x23]");
        assertDecoding(0xc8c311b0, "ldlar x16, [x13]");
    }

    @Test
    public void test_ldlarb() {
        assertDecoding(0x08cf1dd1, "ldlarb w17, [x14]");
        assertDecoding(0x08c973f3, "ldlarb w19, [sp]");
    }

    @Test
    public void test_ldlarh() {
        assertDecoding(0x48df2e37, "ldlarh w23, [x17]");
        assertDecoding(0x48c72ffd, "ldlarh w29, [sp]");
        assertDecoding(0x48c4343f, "ldlarh wzr, [x1]");
    }

    @Test
    public void test_ldnp() {
        assertDecoding(0x285be360, "ldnp w0, w24, [x27, #220]");
        assertDecoding(0x286492ca, "ldnp w10, w4, [x22, #-220]");
        assertDecoding(0x287fb056, "ldnp w22, w12, [x2, #-4]");
        assertDecoding(0xa87183fd, "ldnp x29, x0, [sp, #-232]");
        assertDecoding(0xa8530ba3, "ldnp x3, x2, [x29, #304]");
        assertDecoding(0xa8654a5f, "ldnp xzr, x18, [x18, #-432]");
    }

    @Test
    public void test_ldnp_fpu() {
        assertDecoding(0x6c62a7ab, "ldnp d11, d9, [x29, #-472]");
        assertDecoding(0xac7962cb, "ldnp q11, q24, [x22, #-224]");
        assertDecoding(0x2c49cc6a, "ldnp s10, s19, [x3, #76]");
    }

    @Test
    public void test_ldp() {
        assertDecoding(0x29400400, "ldp w0, w1, [x0]");
        assertDecoding(0x29410400, "ldp w0, w1, [x0, #8]");
        assertDecoding(0x295e0660, "ldp w0, w1, [x19, #240]");
        assertDecoding(0x294103e1, "ldp w1, w0, [sp, #8]");
        assertDecoding(0x28c2fdd1, "ldp w17, wzr, [x14], #20");
        assertDecoding(0x295a363f, "ldp wzr, w13, [x17, #208]");
        assertDecoding(0x29f94e1f, "ldp wzr, w19, [x16, #-56]!");
        assertDecoding(0x28e9d09f, "ldp wzr, w20, [x4], #-180");
        assertDecoding(0x29656c9f, "ldp wzr, w27, [x4, #-216]");
        assertDecoding(0x296326bf, "ldp wzr, w9, [x21, #-232]");
        assertDecoding(0x28db0b41, "ldp w1, w2, [x26], #216");
        assertDecoding(0x29f1378b, "ldp w11, w13, [x28, #-120]!");
        assertDecoding(0x28e733ed, "ldp w13, w12, [sp], #-200");
        assertDecoding(0x29fdcffb, "ldp w27, w19, [sp, #-20]!");

        assertDecoding(0xa94107e0, "ldp x0, x1, [sp, #16]");
        assertDecoding(0xa94d87e0, "ldp x0, x1, [sp, #216]");
        assertDecoding(0xa94f07e0, "ldp x0, x1, [sp, #240]");
        assertDecoding(0xa94407e0, "ldp x0, x1, [sp, #64]");
        assertDecoding(0xa9788400, "ldp x0, x1, [x0, #-120]");
        assertDecoding(0xa9700400, "ldp x0, x1, [x0, #-256]");
        assertDecoding(0xa95e0400, "ldp x0, x1, [x0, #480]");
        assertDecoding(0xa9400400, "ldp x0, x1, [x0]");
        assertDecoding(0xa9400be0, "ldp x0, x2, [sp]");
        assertDecoding(0xa8c10680, "ldp x0, x1, [x20], #16");
        assertDecoding(0xa9e44680, "ldp x0, x17, [x20, #-448]!");
        assertDecoding(0xa9f0fdcb, "ldp x11, xzr, [x14, #-248]!");
        assertDecoding(0xa8de7c0f, "ldp x15, xzr, [x0], #480");
        assertDecoding(0xa8cf385f, "ldp xzr, x14, [x2], #240");
        assertDecoding(0xa9c74dbf, "ldp xzr, x19, [x13, #112]!");
        assertDecoding(0xa8e1d63f, "ldp xzr, x21, [x17], #-488");
        assertDecoding(0xa8f5ff09, "ldp x9, xzr, [x24], #-168");
    }

    @Test
    public void test_ldp_fpu() {
        assertDecoding(0x6d410400, "ldp d0, d1, [x0, #16]");
        assertDecoding(0x6d400400, "ldp d0, d1, [x0]");
        assertDecoding(0x6decb6a0, "ldp d0, d13, [x21, #-312]!");
        assertDecoding(0x6cf2fb60, "ldp d0, d30, [x27], #-216");
        assertDecoding(0x6dc723ea, "ldp d10, d8, [sp, #112]!");

        assertDecoding(0xad4287e0, "ldp q0, q1, [sp, #80]");

        assertDecoding(0x2d4407a0, "ldp s0, s1, [x29, #32]");
    }

    @Test
    public void test_ldpsw() {
        assertDecoding(0x695b87a0, "ldpsw x0, x1, [x29, #220]");
        assertDecoding(0x69f6af00, "ldpsw x0, x11, [x24, #-76]!");
        assertDecoding(0x68c76260, "ldpsw x0, x24, [x19], #56");
        assertDecoding(0x69400aa1, "ldpsw x1, x2, [x21]");
        assertDecoding(0x69757d1a, "ldpsw x26, xzr, [x8, #-88]");
        assertDecoding(0x68dd331f, "ldpsw xzr, x12, [x24], #232");
        assertDecoding(0x694bb8df, "ldpsw xzr, x14, [x6, #92]");
        assertDecoding(0x69f54fe9, "ldpsw x9, x19, [sp, #-88]!");
    }

    @Test
    public void test_ldr_b() {
        assertDecoding(0x3c7a4ac0, "ldr b0, [x22, w26, uxtw]");
        assertDecoding(0x3c6c597d, "ldr b29, [x11, w12, uxtw #0]");
        assertDecoding(0x3d7ff6e0, "ldr b0, [x23, #4093]");
        assertDecoding(0x3c4acf20, "ldr b0, [x25, #172]!");
        assertDecoding(0x3c7079c1, "ldr b1, [x14, x16, lsl #0]");
        assertDecoding(0x3c65fa4d, "ldr b13, [x18, x5, sxtx #0]");
        assertDecoding(0x3c70ca53, "ldr b19, [x18, w16, sxtw]");
        assertDecoding(0x3d7ba3e2, "ldr b2, [sp, #3816]");
        assertDecoding(0x3c7d4b04, "ldr b4, [x24, w29, uxtw]");
        assertDecoding(0x3d72b41f, "ldr b31,[x0,#3245]");
    }

    @Test
    public void test_ldr_d() {
        assertDecoding(0xfc626800, "ldr d0, [x0, x2]");
        assertDecoding(0xfd4007e0, "ldr d0, [sp, #8]");
        assertDecoding(0xfc67d800, "ldr d0, [x0, w7, sxtw #3]");
        assertDecoding(0xfc747800, "ldr d0, [x0, x20, lsl #3]");
        assertDecoding(0xfd400000, "ldr d0, [x0]");
        assertDecoding(0xfc414400, "ldr d0, [x0], #20");
        assertDecoding(0xfd42a420, "ldr d0, [x1, #1352]");
        assertDecoding(0xfc745820, "ldr d0, [x1, w20, uxtw #3]");
        assertDecoding(0xfc607820, "ldr d0, [x1, x0, lsl #3]");
        assertDecoding(0xfc745840, "ldr d0, [x2, w20, uxtw #3]");
        assertDecoding(0xfc5f8401, "ldr d1, [x0], #-8");
        assertDecoding(0xfc469ebe, "ldr d30, [x21, #105]!");
    }

    @Test
    public void test_ldr_h() {
        assertDecoding(0x7d481bc0, "ldr h0, [x30, #1036]");
        assertDecoding(0x7c51754d, "ldr h13, [x10], #-233");
        assertDecoding(0x7c4c5cde, "ldr h30, [x6, #197]!");
        assertDecoding(0x7c755bdf, "ldr h31, [x30, w21, uxtw #1]");
        assertDecoding(0x7c4d4cdf, "ldr h31, [x6, #212]!");
        assertDecoding(0x7c7e4948, "ldr h8, [x10, w30, uxtw]");
    }

    @Test
    public void test_ldr_q() {
        assertDecoding(0x3cfffa95, "ldr q21, [x20, xzr, sxtx #4]");
        assertDecoding(0x3dc01c20, "ldr q0, [x1, #112]");
        assertDecoding(0x3dc11c20, "ldr q0, [x1, #1136]");
        assertDecoding(0x3ce36940, "ldr q0, [x10, x3]");
        assertDecoding(0x3dc00260, "ldr q0, [x19]");
        assertDecoding(0x3cc10660, "ldr q0, [x19], #16");
        assertDecoding(0x3cc28e4e, "ldr q14, [x18, #40]!");
        assertDecoding(0x3dee73e6, "ldr q6, [sp, #47552]");
        assertDecoding(0x3ce2faf7, "ldr q23, [x23, x2, sxtx #4]");
    }

    @Test
    public void test_ldr_w() {
        assertDecoding(0xb94077e0, "ldr w0, [sp, #116]");
        assertDecoding(0xb861d800, "ldr w0, [x0, w1, sxtw #2]");
        assertDecoding(0xb861c800, "ldr w0, [x0, w1, sxtw]");
        assertDecoding(0xb873c800, "ldr w0, [x0, w19, sxtw]");
        assertDecoding(0xb8615800, "ldr w0, [x0, w1, uxtw #2]");
        assertDecoding(0xb8735800, "ldr w0, [x0, w19, uxtw #2]");
        assertDecoding(0xb862d800, "ldr w0, [x0, w2, sxtw #2]");
        assertDecoding(0xb8625800, "ldr w0, [x0, w2, uxtw #2]");
        assertDecoding(0xb8617800, "ldr w0, [x0, x1, lsl #2]");
        assertDecoding(0xb9401820, "ldr w0, [x1, #24]");
        assertDecoding(0xb8418c20, "ldr w0, [x1, #24]!");
        assertDecoding(0xb949be48, "ldr w8, [x18, #2492]");
        assertDecoding(0xb9400648, "ldr w8, [x18, #4]");
        assertDecoding(0xb8707a48, "ldr w8, [x18, x16, lsl #2]");
        assertDecoding(0xb8674848, "ldr w8, [x2, w7, uxtw]");
        assertDecoding(0xb8685848, "ldr w8, [x2, w8, uxtw #2]");
        assertDecoding(0xb8617848, "ldr w8, [x2, x1, lsl #2]");
        assertDecoding(0xb84f6e1f, "ldr wzr, [x16, #246]!");
    }

    @Test
    public void test_ldr_x() {
        assertDecoding(0xf9413a61, "ldr x1, [x19, #624]");
        assertDecoding(0xf8627a61, "ldr x1, [x19, x2, lsl #3]");
        assertDecoding(0xf8746a61, "ldr x1, [x19, x20]");
        assertDecoding(0xf85f8c41, "ldr x1, [x2, #-8]!");
        assertDecoding(0xf8470681, "ldr x1, [x20], #112");
        assertDecoding(0xf94017ea, "ldr x10, [sp, #40]");
        assertDecoding(0xf94003e8, "ldr x8, [sp]");
    }

    @Test
    public void test_ldr_with_label() {
        assertDecodingWithPc(0x102c6c, 0x181db461, "ldr w1, 0x13e2f8");
        assertDecodingWithPc(0x1968, 0x18000041, "ldr w1, 0x1970");
        assertDecodingWithPc(0xef04c, 0x18f1294a, "ldr w10, 0xd1574");
        assertDecodingWithPc(0x404018, 0x580000c0, "ldr x0, 0x404030");
        assertDecodingWithPc(0xfc048, 0x5886e30b, "ldr x11, 0x9ca8");
        assertDecodingWithPc(0xea160, 0x58842014, "ldr x20, 0xffffffffffff2560");
        assertDecodingWithPc(0xf0974, 0x58fd8b5f, "ldr xzr, 0xebadc");
        assertDecodingWithPc(0xeac8c, 0x588a651f, "ldr xzr, 0xfffffffffffff92c");
    }

    @Test
    public void test_ldr_with_label_fpu() {
        assertDecodingWithPc(0xfeef4, 0x5c0ea48a, "ldr d10, 0x11c384");
        assertDecodingWithPc(0xebbec, 0x5c81f442, "ldr d2, 0xfffffffffffefa74");
        assertDecodingWithPc(0x1069bc, 0x9c7aa14b, "ldr q11, 0x1fbde4");
        assertDecodingWithPc(0x47500, 0x9c9c9c00, "ldr q0, 0xfffffffffff80880");
        assertDecodingWithPc(0xf6a50, 0x1c23f9c1, "ldr s1, 0x13e988");
        assertDecodingWithPc(0xeaa64, 0x1c8804d8, "ldr s24, 0xffffffffffffaafc");
    }


    @Test
    public void test_ldraa() {
        assertDecoding(0xf87d25c0, "ldraa x0, [x14, #-368]");
        assertDecoding(0xf87826c0, "ldraa x0, [x22, #-1008]");
        assertDecoding(0xf87f1eea, "ldraa x10, [x23, #-120]!");
        assertDecoding(0xf867feec, "ldraa x12, [x23, #-3080]!");
        assertDecoding(0xf8679d2c, "ldraa x12, [x9, #-3128]!");
        assertDecoding(0xf8380e8e, "ldraa x14, [x20, #3072]!");
        assertDecoding(0xf87fb48e, "ldraa x14, [x4, #-40]");
        assertDecoding(0xf86ec551, "ldraa x17, [x10, #-2208]");
        assertDecoding(0xf86f2eb1, "ldraa x17, [x21, #-2160]!");
        assertDecoding(0xf83b1751, "ldraa x17, [x26, #3464]");
        assertDecoding(0xf87e2cf2, "ldraa x18, [x7, #-240]!");
        assertDecoding(0xf83dc794, "ldraa x20, [x28, #3808]");
        assertDecoding(0xf861ae55, "ldraa x21, [x18, #-3888]!");
        assertDecoding(0xf8699656, "ldraa x22, [x18, #-2872]");
        assertDecoding(0xf825fcd6, "ldraa x22, [x6, #760]!");
        assertDecoding(0xf865c65b, "ldraa x27, [x18, #-3360]");
        assertDecoding(0xf825ce7c, "ldraa x28, [x19, #736]!");
        assertDecoding(0xf865645c, "ldraa x28, [x2, #-3408]");
        assertDecoding(0xf83eaf1d, "ldraa x29, [x24, #3920]!");
        assertDecoding(0xf8763406, "ldraa x6, [x0, #-1256]");
        assertDecoding(0xf8278e46, "ldraa x6, [x18, #960]!");
        assertDecoding(0xf86b6fc6, "ldraa x6, [x30, #-2640]!");
        assertDecoding(0xf8604e49, "ldraa x9, [x18, #-4064]!");
        assertDecoding(0xf8627769, "ldraa x9, [x27, #-3784]");
        assertDecoding(0xf83c8dff, "ldraa xzr, [x15, #3648]!");
        assertDecoding(0xf838c4ff, "ldraa xzr, [x7, #3168]");
    }

    @Test
    public void test_ldrab() {
        assertDecoding(0xf8ee35ec, "ldrab x12, [x15, #-2280]");
        assertDecoding(0xf8ba940f, "ldrab x15, [x0, #3400]");
        assertDecoding(0xf8a8dd91, "ldrab x17, [x12, #1128]!");
        assertDecoding(0xf8e0eea2, "ldrab x2, [x21, #-3984]!");
        assertDecoding(0xf8f76e16, "ldrab x22, [x16, #-1104]!");
        assertDecoding(0xf8b56c1a, "ldrab x26, [x0, #2736]!");
        assertDecoding(0xf8bbed5a, "ldrab x26, [x10, #3568]!");
        assertDecoding(0xf8bdae9a, "ldrab x26, [x20, #3792]!");
        assertDecoding(0xf8a9363b, "ldrab x27, [x17, #1176]");
        assertDecoding(0xf8a2563b, "ldrab x27, [x17, #296]");
        assertDecoding(0xf8a5ee5b, "ldrab x27, [x18, #752]!");
        assertDecoding(0xf8e6ae1e, "ldrab x30, [x16, #-3248]!");
        assertDecoding(0xf8ae9d84, "ldrab x4, [x12, #1864]!");
        assertDecoding(0xf8ea5ee4, "ldrab x4, [x23, #-2776]!");
        assertDecoding(0xf8b5ae65, "ldrab x5, [x19, #2768]!");
        assertDecoding(0xf8b745c7, "ldrab x7, [x14, #2976]");
    }

    @Test
    public void test_ldrb() {
        assertDecoding(0x394543e0, "ldrb w0, [sp, #336]");
        assertDecoding(0x394003e0, "ldrb w0, [sp]");
        assertDecoding(0x39400140, "ldrb w0, [x10]");
        assertDecoding(0x38401540, "ldrb w0, [x10], #1");
        assertDecoding(0x39404160, "ldrb w0, [x11, #16]");
        assertDecoding(0x39400560, "ldrb w0, [x11, #1]");
        assertDecoding(0x38401d60, "ldrb w0, [x11, #1]!");
        assertDecoding(0x3860c960, "ldrb w0, [x11, w0, sxtw]");
        assertDecoding(0x38604960, "ldrb w0, [x11, w0, uxtw]");
        assertDecoding(0x394003f3, "ldrb w19, [sp]");
        assertDecoding(0x39406be1, "ldrb w1, [sp, #26]");
        assertDecoding(0x386968a7, "ldrb w7, [x5, x9]");
        assertDecoding(0x394000a7, "ldrb w7, [x5]");
        assertDecoding(0x385ff4a7, "ldrb w7, [x5], #-1");
        assertDecoding(0x384014a7, "ldrb w7, [x5], #1");
        assertDecoding(0x3840ecc7, "ldrb w7, [x6, #14]!");
    }

    @Test
    public void test_ldrh() {
        assertDecoding(0x7940e420, "ldrh w0, [x1, #114]");
        assertDecoding(0x79400420, "ldrh w0, [x1, #2]");
        assertDecoding(0x78402c20, "ldrh w0, [x1, #2]!");
        assertDecoding(0x7860d820, "ldrh w0, [x1, w0, sxtw #1]");
        assertDecoding(0x78605820, "ldrh w0, [x1, w0, uxtw #1]");
        assertDecoding(0x78604820, "ldrh w0, [x1, w0, uxtw]");
        assertDecoding(0x78607820, "ldrh w0, [x1, x0, lsl #1]");
        assertDecoding(0x786c6820, "ldrh w0, [x1, x12]");
        assertDecoding(0x78402580, "ldrh w0, [x12], #2");
        assertDecoding(0x7942e3ea, "ldrh w10, [sp, #368]");
        assertDecoding(0x796b1fff, "ldrh wzr, [sp, #5518]");
        assertDecoding(0x785a955f, "ldrh wzr, [x10], #-87");
        assertDecoding(0x7946d39f, "ldrh wzr, [x28, #872]");
        assertDecoding(0x786468e9, "ldrh w9, [x7, x4]");
    }

    @Test
    public void test_ldrsb() {
        assertDecoding(0x38e06be1, "ldrsb w1, [sp, x0]");
        assertDecoding(0x39c23001, "ldrsb w1, [x0, #140]");
        assertDecoding(0x39c08001, "ldrsb w1, [x0, #32]");
        assertDecoding(0x38c20c01, "ldrsb w1, [x0, #32]!");
        assertDecoding(0x38e2c801, "ldrsb w1, [x0, w2, sxtw]");
        assertDecoding(0x39c00001, "ldrsb w1, [x0]");
        assertDecoding(0x38c05401, "ldrsb w1, [x0], #5");
        assertDecoding(0x38bfc863, "ldrsb x3, [x3, wzr, sxtw]");
        assertDecoding(0x38bb5965, "ldrsb x5, [x11, w27, uxtw #0]");
        assertDecoding(0x39800001, "ldrsb x1, [x0]");
        assertDecoding(0x39816261, "ldrsb x1, [x19, #88]");
        assertDecoding(0x38801e61, "ldrsb x1, [x19, #1]!");
        assertDecoding(0x38a26a61, "ldrsb x1, [x19, x2]");
        assertDecoding(0x388016f5, "ldrsb x21, [x23], #1");
    }

    @Test
    public void test_ldrsh() {
        assertDecoding(0x79c1a7ef, "ldrsh w15, [sp, #210]");
        assertDecoding(0x789907f0, "ldrsh x16, [sp], #-112");
        assertDecoding(0x79aa33f1, "ldrsh x17, [sp, #5400]");
        assertDecoding(0x79c0e801, "ldrsh w1, [x0, #116]");
        assertDecoding(0x78e17801, "ldrsh w1, [x0, x1, lsl #1]");
        assertDecoding(0x79c00001, "ldrsh w1, [x0]");
        assertDecoding(0x78c10401, "ldrsh w1, [x0], #16");
        assertDecoding(0x78e3d821, "ldrsh w1, [x1, w3, sxtw #1]");
        assertDecoding(0x78fb7821, "ldrsh w1, [x1, x27, lsl #1]");
        assertDecoding(0x78d19f50, "ldrsh w16, [x26, #-231]!");
        assertDecoding(0x78c197df, "ldrsh wzr, [x30], #25");
        assertDecoding(0x79803801, "ldrsh x1, [x0, #28]");
        assertDecoding(0x79800001, "ldrsh x1, [x0]");
        assertDecoding(0x78a27821, "ldrsh x1, [x1, x2, lsl #1]");
        assertDecoding(0x789fee61, "ldrsh x1, [x19, #-2]!");
        assertDecoding(0x79800021, "ldrsh x1, [x1]");
        assertDecoding(0x78a1d841, "ldrsh x1, [x2, w1, sxtw #1]");
    }

    @Test
    public void test_ldrsw() {
        assertDecoding(0xb98003ec, "ldrsw x12, [sp]");
        assertDecoding(0xb9806801, "ldrsw x1, [x0, #104]");
        assertDecoding(0xb8a1d801, "ldrsw x1, [x0, w1, sxtw #2]");
        assertDecoding(0xb8b56801, "ldrsw x1, [x0, x21]");
        assertDecoding(0xb9800001, "ldrsw x1, [x0]");
        assertDecoding(0xb8804401, "ldrsw x1, [x0], #4");
        assertDecoding(0xb983ec21, "ldrsw x1, [x1, #1004]");
        assertDecoding(0xb8850d30, "ldrsw x16, [x9, #80]!");
        assertDecoding(0xb8984e9f, "ldrsw xzr, [x20, #-124]!");
        assertDecoding(0xb9b30eff, "ldrsw xzr, [x23, #13068]");
    }

    @Test
    public void test_ldrsw_with_label() {
        assertDecodingWithPc(0xfdc0c, 0x98228580, "ldrsw x0, 0x142cbc");
        assertDecodingWithPc(0x47b70, 0x98989800, "ldrsw x0, 0xfffffffffff78e70");
        assertDecodingWithPc(0xee38c, 0x98d1b596, "ldrsw x22, 0x91a3c");
        assertDecodingWithPc(0xf9c44, 0x98801c56, "ldrsw x22, 0xffffffffffff9fcc");
        assertDecodingWithPc(0xf61fc, 0x9837e8bf, "ldrsw xzr, 0x165f10");
    }

    @Test
    public void test_ldsetab() {
        assertDecoding(0x38af3297, "ldsetab w15, w23, [x20]");
        assertDecoding(0x38a932df, "ldsetab w9, wzr, [x22]");
    }

    @Test
    public void test_ldsetal() {
        assertDecoding(0xf8f133bf, "ldsetal x17, xzr, [x29]");
    }

    @Test
    public void test_ldsetalb() {
        assertDecoding(0x38ff32a5, "ldsetalb wzr, w5, [x21]");
    }

    @Test
    public void test_ldsetalh() {
        assertDecoding(0x78f030dd, "ldsetalh w16, w29, [x6]");
    }

    @Test
    public void test_ldseth() {
        assertDecoding(0x78383187, "ldseth w24, w7, [x12]");
        assertDecoding(0x782831cd, "ldseth w8, w13, [x14]");
    }

    @Test
    public void test_ldsetlb() {
        assertDecoding(0x386b3140, "ldsetlb w11, w0, [x10]");
    }

    @Test
    public void test_ldsmaxah() {
        assertDecoding(0x78b24220, "ldsmaxah w18, w0, [x17]");
    }

    @Test
    public void test_ldsmaxalh() {
        assertDecoding(0x78ef42a0, "ldsmaxalh w15, w0, [x21]");
        assertDecoding(0x78f94057, "ldsmaxalh w25, w23, [x2]");
    }

    @Test
    public void test_ldsmina() {
        assertDecoding(0xb8b7517b, "ldsmina w23, w27, [x11]");
    }

    @Test
    public void test_ldsminal() {
        assertDecoding(0xf8ff51d2, "ldsminal xzr, x18, [x14]");
    }

    @Test
    public void test_ldsminh() {
        assertDecoding(0x782c5244, "ldsminh w12, w4, [x18]");
    }

    @Test
    public void test_ldtr() {
        assertDecoding(0xb84a79b2, "ldtr w18, [x13, #167]");
        assertDecoding(0xb85d5abe, "ldtr w30, [x21, #-43]");
        assertDecoding(0xf842ea80, "ldtr x0, [x20, #46]");
        assertDecoding(0xf859c888, "ldtr x8, [x4, #-100]");
        assertDecoding(0xf852995f, "ldtr xzr, [x10, #-215]");
    }

    @Test
    public void test_ldtrb() {
        assertDecoding(0x3843cb5b, "ldtrb w27, [x26, #60]");
        assertDecoding(0x3856b8e6, "ldtrb w6, [x7, #-149]");
        assertDecoding(0x3846ab49, "ldtrb w9, [x26, #106]");
        assertDecoding(0x3848f869, "ldtrb w9, [x3, #143]");
    }

    @Test
    public void test_ldtrh() {
        assertDecoding(0x784f6a82, "ldtrh w2, [x20, #246]");
        assertDecoding(0x784ca957, "ldtrh w23, [x10, #202]");
        assertDecoding(0x78439bfc, "ldtrh w28, [sp, #57]");
    }

    @Test
    public void test_ldtrsb() {
        assertDecoding(0x38d39bb3, "ldtrsb w19, [x29, #-199]");
        assertDecoding(0x38c7b9f9, "ldtrsb w25, [x15, #123]");
        assertDecoding(0x389f0b02, "ldtrsb x2, [x24, #-16]");
        assertDecoding(0x38891b77, "ldtrsb x23, [x27, #145]");
    }

    @Test
    public void test_ldtrsh() {
        assertDecoding(0x78d2aaa0, "ldtrsh w0, [x21, #-214]");
        assertDecoding(0x78c9596b, "ldtrsh w11, [x11, #149]");
        assertDecoding(0x788d590b, "ldtrsh x11, [x8, #213]");
        assertDecoding(0x789caac2, "ldtrsh x2, [x22, #-54]");
    }

    @Test
    public void test_ldtrsw() {
        assertDecoding(0xb88efbd2, "ldtrsw x18, [x30, #239]");
        assertDecoding(0xb8814b3c, "ldtrsw x28, [x25, #20]");
    }

    @Test
    public void test_ldumaxah() {
        assertDecoding(0x78a5636f, "ldumaxah w5, w15, [x27]");
        assertDecoding(0x78a663bd, "ldumaxah w6, w29, [x29]");
    }

    @Test
    public void test_ldumaxal() {
        assertDecoding(0xb8f56188, "ldumaxal w21, w8, [x12]");
    }

    @Test
    public void test_ldumina() {
        assertDecoding(0xf8b972a9, "ldumina x25, x9, [x21]");
    }

    @Test
    public void test_lduminalb() {
        assertDecoding(0x38f17150, "lduminalb w17, w16, [x10]");
    }

    @Test
    public void test_lduminalh() {
        assertDecoding(0x78ed7251, "lduminalh w13, w17, [x18]");
    }

    @Test
    public void test_lduminb() {
        assertDecoding(0x38367068, "lduminb w22, w8, [x3]");
    }

    @Test
    public void test_ldur() {
        assertDecoding(0x3c59f1c0, "ldur b0, [x14, #-97]");
        assertDecoding(0x3c47833c, "ldur b28, [x25, #120]");
        assertDecoding(0x7c5332aa, "ldur h10, [x21, #-205]");
        assertDecoding(0x7c4b301a, "ldur h26, [x0, #179]");
        assertDecoding(0xb8520020, "ldur w0, [x1, #-224]");
        assertDecoding(0xb8403020, "ldur w0, [x1, #3]");
        assertDecoding(0x384670bf, "ldurb wzr, [x5, #103]");
        assertDecoding(0xf8407001, "ldur x1, [x0, #7]");
        assertDecoding(0xf8598021, "ldur x1, [x1, #-104]");
    }

    @Test
    public void test_ldur_fpu() {
        assertDecoding(0xfc4991ad, "ldur d13, [x13, #153]");
        assertDecoding(0xfc54c199, "ldur d25, [x12, #-180]");
        assertDecoding(0x3cdf0161, "ldur q1, [x11, #-16]");
        assertDecoding(0x3cc08261, "ldur q1, [x19, #8]");
        assertDecoding(0xbc5f32aa, "ldur s10, [x21, #-13]");
        assertDecoding(0xbc495214, "ldur s20, [x16, #149]");
    }

    @Test
    public void test_ldurb() {
        assertDecoding(0x385f1001, "ldurb w1, [x0, #-15]");
        assertDecoding(0x384a50e1, "ldurb w1, [x7, #165]");
    }

    @Test
    public void test_ldurh() {
        assertDecoding(0x785f8001, "ldurh w1, [x0, #-8]");
        assertDecoding(0x78415001, "ldurh w1, [x0, #21]");
    }

    @Test
    public void test_ldursb() {
        assertDecoding(0x38dff261, "ldursb w1, [x19, #-1]");
        assertDecoding(0x38dfe121, "ldursb w1, [x9, #-2]");
        assertDecoding(0x38c47358, "ldursb w24, [x26, #71]");
        assertDecoding(0x389d910e, "ldursb x14, [x8, #-39]");
        assertDecoding(0x388091f1, "ldursb x17, [x15, #9]");
    }

    @Test
    public void test_ldursh() {
        assertDecoding(0x78de534f, "ldursh w15, [x26, #-27]");
        assertDecoding(0x78cfa1bd, "ldursh w29, [x13, #250]");
        assertDecoding(0x78c123ff, "ldursh wzr, [sp, #18]");
        assertDecoding(0x78801273, "ldursh x19, [x19, #1]");
        assertDecoding(0x789181da, "ldursh x26, [x14, #-232]");
        assertDecoding(0x7887605f, "ldursh xzr, [x2, #118]");
    }

    @Test
    public void test_ldursw() {
        assertDecoding(0xb89f0281, "ldursw x1, [x20, #-16]");
        assertDecoding(0xb8802281, "ldursw x1, [x20, #2]");
    }

    @Test
    public void test_ldxp() {
        assertDecoding(0x887b1881, "ldxp w1, w6, [x4]");
        assertDecoding(0xc86d44df, "ldxp xzr, x17, [x6]");
    }

    @Test
    public void test_ldxr() {
        assertDecoding(0x885f7d41, "ldxr w1, [x10]");
        assertDecoding(0xc85f7e75, "ldxr x21, [x19]");
        assertDecoding(0xc840647f, "ldxr xzr, [x3]");
    }

    @Test
    public void test_ldxrb() {
        assertDecoding(0x08565388, "ldxrb w8, [x28]");
        assertDecoding(0x084431ff, "ldxrb wzr, [x15]");
    }

    @Test
    public void test_ldxrh() {
        assertDecoding(0x48566221, "ldxrh w1, [x17]");
    }

    @Test
    public void test_all_lsl_shift_amounts_w() {
        assertDecoding(0x531f7800, "lsl w0, w0, #1");
        assertDecoding(0x53165400, "lsl w0, w0, #10");
        assertDecoding(0x53155001, "lsl w1, w0, #11");
        assertDecoding(0x53144c00, "lsl w0, w0, #12");
        assertDecoding(0x53134820, "lsl w0, w1, #13");
        assertDecoding(0x53124400, "lsl w0, w0, #14");
        assertDecoding(0x53114260, "lsl w0, w19, #15");
        assertDecoding(0x53103c00, "lsl w0, w0, #16");
        assertDecoding(0x530f3871, "lsl w17, w3, #17");
        assertDecoding(0x530e3400, "lsl w0, w0, #18");
        assertDecoding(0x530d3021, "lsl w1, w1, #19");
        assertDecoding(0x531e7400, "lsl w0, w0, #2");
        assertDecoding(0x530c2c00, "lsl w0, w0, #20");
        assertDecoding(0x530b2800, "lsl w0, w0, #21");
        assertDecoding(0x530a2400, "lsl w0, w0, #22");
        assertDecoding(0x53092021, "lsl w1, w1, #23");
        assertDecoding(0x53081c00, "lsl w0, w0, #24");
        assertDecoding(0x53061421, "lsl w1, w1, #26");
        assertDecoding(0x53051021, "lsl w1, w1, #27");
        assertDecoding(0x53040c00, "lsl w0, w0, #28");
        assertDecoding(0x531d7000, "lsl w0, w0, #3");
        assertDecoding(0x53020400, "lsl w0, w0, #30");
        assertDecoding(0x531c6c00, "lsl w0, w0, #4");
        assertDecoding(0x531b6800, "lsl w0, w0, #5");
        assertDecoding(0x531a6400, "lsl w0, w0, #6");
        assertDecoding(0x53196000, "lsl w0, w0, #7");
        assertDecoding(0x53185c00, "lsl w0, w0, #8");
        assertDecoding(0x53175800, "lsl w0, w0, #9");
    }

    @Test
    public void test_all_lsl_shift_amounts_x() {
        assertDecoding(0xd37ff800, "lsl x0, x0, #1");
        assertDecoding(0xd376d400, "lsl x0, x0, #10");
        assertDecoding(0xd375d021, "lsl x1, x1, #11");
        assertDecoding(0xd374cc00, "lsl x0, x0, #12");
        assertDecoding(0xd373c821, "lsl x1, x1, #13");
        assertDecoding(0xd371c040, "lsl x0, x2, #15");
        assertDecoding(0xd370bc00, "lsl x0, x0, #16");
        assertDecoding(0xd36fb841, "lsl x1, x2, #17");
        assertDecoding(0xd36eb580, "lsl x0, x12, #18");
        assertDecoding(0xd36db16f, "lsl x15, x11, #19");
        assertDecoding(0xd37ef400, "lsl x0, x0, #2");
        assertDecoding(0xd36cac00, "lsl x0, x0, #20");
        assertDecoding(0xd36aa50b, "lsl x11, x8, #22");
        assertDecoding(0xd3689c00, "lsl x0, x0, #24");
        assertDecoding(0xd3679840, "lsl x0, x2, #25");
        assertDecoding(0xd36694b0, "lsl x16, x5, #26");
        assertDecoding(0xd36590aa, "lsl x10, x5, #27");
        assertDecoding(0xd3648cb8, "lsl x24, x5, #28");
        assertDecoding(0xd3638a0a, "lsl x10, x16, #29");
        assertDecoding(0xd37df000, "lsl x0, x0, #3");
        assertDecoding(0xd3628400, "lsl x0, x0, #30");
        assertDecoding(0xd3618021, "lsl x1, x1, #31");
        assertDecoding(0xd3607c00, "lsl x0, x0, #32");
        assertDecoding(0xd35f78aa, "lsl x10, x5, #33");
        assertDecoding(0xd35e7420, "lsl x0, x1, #34");
        assertDecoding(0xd35b6ae1, "lsl x1, x23, #37");
        assertDecoding(0xd35a6400, "lsl x0, x0, #38");
        assertDecoding(0xd37cec00, "lsl x0, x0, #4");
        assertDecoding(0xd3585c20, "lsl x0, x1, #40");
        assertDecoding(0xd3555042, "lsl x2, x2, #43");
        assertDecoding(0xd3503e80, "lsl x0, x20, #48");
        assertDecoding(0xd37be800, "lsl x0, x0, #5");
        assertDecoding(0xd34e3401, "lsl x1, x0, #50");
        assertDecoding(0xd34b2800, "lsl x0, x0, #53");
        assertDecoding(0xd3481c42, "lsl x2, x2, #56");
        assertDecoding(0xd37ae400, "lsl x0, x0, #6");
        assertDecoding(0xd3440c00, "lsl x0, x0, #60");
        assertDecoding(0xd3430822, "lsl x2, x1, #61");
        assertDecoding(0xd3420400, "lsl x0, x0, #62");
        assertDecoding(0xd3410021, "lsl x1, x1, #63");
        assertDecoding(0xd379e000, "lsl x0, x0, #7");
        assertDecoding(0xd378dc00, "lsl x0, x0, #8");
        assertDecoding(0xd377d800, "lsl x0, x0, #9");
    }

    @Test
    public void test_all_lsr_shift_amounts_w() {
        assertDecoding(0x53007ee0, "lsr w0, w23, #0");
        assertDecoding(0x53017c00, "lsr w0, w0, #1");
        assertDecoding(0x530a7c00, "lsr w0, w0, #10");
        assertDecoding(0x530b7c00, "lsr w0, w0, #11");
        assertDecoding(0x530c7c00, "lsr w0, w0, #12");
        assertDecoding(0x530d7c00, "lsr w0, w0, #13");
        assertDecoding(0x530e7ec0, "lsr w0, w22, #14");
        assertDecoding(0x530f7c00, "lsr w0, w0, #15");
        assertDecoding(0x53107c00, "lsr w0, w0, #16");
        assertDecoding(0x53117c00, "lsr w0, w0, #17");
        assertDecoding(0x53127c00, "lsr w0, w0, #18");
        assertDecoding(0x53137c00, "lsr w0, w0, #19");
        assertDecoding(0x53027c00, "lsr w0, w0, #2");
        assertDecoding(0x53147e80, "lsr w0, w20, #20");
        assertDecoding(0x53157f01, "lsr w1, w24, #21");
        assertDecoding(0x53167da0, "lsr w0, w13, #22");
        assertDecoding(0x53177ea0, "lsr w0, w21, #23");
        assertDecoding(0x53187c00, "lsr w0, w0, #24");
        assertDecoding(0x53197f20, "lsr w0, w25, #25");
        assertDecoding(0x531a7c00, "lsr w0, w0, #26");
        assertDecoding(0x531b7f00, "lsr w0, w24, #27");
        assertDecoding(0x531c7c20, "lsr w0, w1, #28");
        assertDecoding(0x531d7c00, "lsr w0, w0, #29");
        assertDecoding(0x53037c00, "lsr w0, w0, #3");
        assertDecoding(0x531e7c00, "lsr w0, w0, #30");
        assertDecoding(0x531f7c00, "lsr w0, w0, #31");
        assertDecoding(0x53047c00, "lsr w0, w0, #4");
        assertDecoding(0x53057c00, "lsr w0, w0, #5");
        assertDecoding(0x53067c00, "lsr w0, w0, #6");
        assertDecoding(0x53077c00, "lsr w0, w0, #7");
        assertDecoding(0x53087c00, "lsr w0, w0, #8");
        assertDecoding(0x53097c00, "lsr w0, w0, #9");
    }

    @Test
    public void test_all_lsr_shift_amounts_x() {
        assertDecoding(0xd341fc00, "lsr x0, x0, #1");
        assertDecoding(0xd34afc00, "lsr x0, x0, #10");
        assertDecoding(0xd34bfc00, "lsr x0, x0, #11");
        assertDecoding(0xd34cfc00, "lsr x0, x0, #12");
        assertDecoding(0xd34dfc00, "lsr x0, x0, #13");
        assertDecoding(0xd34efc01, "lsr x1, x0, #14");
        assertDecoding(0xd34ffc00, "lsr x0, x0, #15");
        assertDecoding(0xd350fc00, "lsr x0, x0, #16");
        assertDecoding(0xd351fc00, "lsr x0, x0, #17");
        assertDecoding(0xd352fc00, "lsr x0, x0, #18");
        assertDecoding(0xd353fc00, "lsr x0, x0, #19");
        assertDecoding(0xd342fc00, "lsr x0, x0, #2");
        assertDecoding(0xd354fc00, "lsr x0, x0, #20");
        assertDecoding(0xd355ff13, "lsr x19, x24, #21");
        assertDecoding(0xd356fc00, "lsr x0, x0, #22");
        assertDecoding(0xd357fc00, "lsr x0, x0, #23");
        assertDecoding(0xd358fc00, "lsr x0, x0, #24");
        assertDecoding(0xd359fce0, "lsr x0, x7, #25");
        assertDecoding(0xd35afc00, "lsr x0, x0, #26");
        assertDecoding(0xd35bfc20, "lsr x0, x1, #27");
        assertDecoding(0xd35cfc00, "lsr x0, x0, #28");
        assertDecoding(0xd35dfd4a, "lsr x10, x10, #29");
        assertDecoding(0xd343fc00, "lsr x0, x0, #3");
        assertDecoding(0xd35efc00, "lsr x0, x0, #30");
        assertDecoding(0xd35ffc00, "lsr x0, x0, #31");
        assertDecoding(0xd360fc00, "lsr x0, x0, #32");
        assertDecoding(0xd361fc00, "lsr x0, x0, #33");
        assertDecoding(0xd362fc00, "lsr x0, x0, #34");
        assertDecoding(0xd363fc00, "lsr x0, x0, #35");
        assertDecoding(0xd364fc00, "lsr x0, x0, #36");
        assertDecoding(0xd365fc00, "lsr x0, x0, #37");
        assertDecoding(0xd366fc00, "lsr x0, x0, #38");
        assertDecoding(0xd367fc00, "lsr x0, x0, #39");
        assertDecoding(0xd344fc00, "lsr x0, x0, #4");
        assertDecoding(0xd368fc00, "lsr x0, x0, #40");
        assertDecoding(0xd369fc00, "lsr x0, x0, #41");
        assertDecoding(0xd36afc00, "lsr x0, x0, #42");
        assertDecoding(0xd36bfc00, "lsr x0, x0, #43");
        assertDecoding(0xd36cfc42, "lsr x2, x2, #44");
        assertDecoding(0xd36dfc21, "lsr x1, x1, #45");
        assertDecoding(0xd36efc00, "lsr x0, x0, #46");
        assertDecoding(0xd36ffc03, "lsr x3, x0, #47");
        assertDecoding(0xd370fc00, "lsr x0, x0, #48");
        assertDecoding(0xd371fc00, "lsr x0, x0, #49");
        assertDecoding(0xd345fc00, "lsr x0, x0, #5");
        assertDecoding(0xd372fc20, "lsr x0, x1, #50");
        assertDecoding(0xd373fc00, "lsr x0, x0, #51");
        assertDecoding(0xd374fc00, "lsr x0, x0, #52");
        assertDecoding(0xd376ff20, "lsr x0, x25, #54");
        assertDecoding(0xd377ff20, "lsr x0, x25, #55");
        assertDecoding(0xd378fc00, "lsr x0, x0, #56");
        assertDecoding(0xd379fc60, "lsr x0, x3, #57");
        assertDecoding(0xd37afc01, "lsr x1, x0, #58");
        assertDecoding(0xd37bfe80, "lsr x0, x20, #59");
        assertDecoding(0xd346fc00, "lsr x0, x0, #6");
        assertDecoding(0xd37cfc20, "lsr x0, x1, #60");
        assertDecoding(0xd37dfc20, "lsr x0, x1, #61");
        assertDecoding(0xd37efc00, "lsr x0, x0, #62");
        assertDecoding(0xd37ffc00, "lsr x0, x0, #63");
        assertDecoding(0xd347fc00, "lsr x0, x0, #7");
        assertDecoding(0xd348fc00, "lsr x0, x0, #8");
        assertDecoding(0xd349fc00, "lsr x0, x0, #9");
    }


    @Test
    public void test_lsl() {
        assertDecoding(0x1ad02255, "lsl w21, w18, w16");
        assertDecoding(0x531f7a75, "lsl w21, w19, #1");
        assertDecoding(0x531a6675, "lsl w21, w19, #6");
        assertDecoding(0xd37bea81, "lsl x1, x20, #5");
        assertDecoding(0x9ad322a1, "lsl x1, x21, x19");
    }

    @Test
    public void test_lsr() {
        assertDecoding(0x53197f01, "lsr w1, w24, #25");
        assertDecoding(0x1ac025b2, "lsr w18, w13, w0");
        assertDecoding(0xd34dfc40, "lsr x0, x2, #13");
        assertDecoding(0x9ac52440, "lsr x0, x2, x5");
    }

    @Test
    public void test_madd() {
        assertDecoding(0x1b061420, "madd w0, w1, w6, w5");
        assertDecoding(0x1b1b470d, "madd w13, w24, w27, w17");
        assertDecoding(0x9b1a6020, "madd x0, x1, x26, x24");
        assertDecoding(0x9b1c01b2, "madd x18, x13, x28, x0");
    }

    @Test
    public void test_mla_simd() {
        assertDecoding(0x6f6f006f, "mla v15.8h, v3.8h, v15.h[2]");
        assertDecoding(0x2fa50835, "mla v21.2s, v1.2s, v5.s[3]");
        assertDecoding(0x2f4b0198, "mla v24.4h, v12.4h, v11.h[0]");
        assertDecoding(0x0e6f96fb, "mla v27.4h, v23.4h, v15.4h");
    }

    @Test
    public void test_mls_simd() {
        assertDecoding(0x6eb29401, "mls v1.4s, v0.4s, v18.4s");
        assertDecoding(0x6f7e4b81, "mls v1.8h, v28.8h, v14.h[7]");
        assertDecoding(0x6f4d4ab7, "mls v23.8h, v21.8h, v13.h[4]");
        assertDecoding(0x2f61485a, "mls v26.4h, v2.4h, v1.h[6]");
    }

    @Test
    public void test_mneg() {
        assertDecoding(0x9b03fc00, "mneg x0, x0, x3");
        assertDecoding(0x9b04fc00, "mneg x0, x0, x4");
        assertDecoding(0x9b04fcad, "mneg x13, x5, x4");
        assertDecoding(0x9b19fc42, "mneg x2, x2, x25");
        assertDecoding(0x9b00fe94, "mneg x20, x20, x0");
    }


    @Test
    public void test_mov() {
        assertDecoding(0x9100003f, "mov sp, x1");
        assertDecoding(0x528200e0, "mov w0, #0x1007");
        assertDecoding(0x52a56000, "mov w0, #0x2b000000");
        assertDecoding(0x12b9f000, "mov w0, #0x307fffff");
        assertDecoding(0x52ba0000, "mov w0, #0xd0000000");
        assertDecoding(0x12800000, "mov w0, #0xffffffff");
        assertDecoding(0x2a1a03e0, "mov w0, w26");
        assertDecoding(0x92800000, "mov x0, #0xffffffffffffffff");
        assertDecoding(0x910003e0, "mov x0, sp");
        assertDecoding(0xaa0103e0, "mov x0, x1");
        assertDecoding(0xaa0a03e0, "mov x0, x10");
        assertDecoding(0x12b5c27f, "mov wzr, #0x51ecffff");
        assertDecoding(0xaa1f03e0, "mov x0, xzr");
        assertDecoding(0xd2e6c2df, "mov xzr, #0x3616000000000000");
        assertDecoding(0xd2c074bf, "mov xzr, #0x3a500000000");
        assertDecoding(0xd29c1e9f, "mov xzr, #0xe0f4");
        assertDecoding(0x92a884ff, "mov xzr, #0xffffffffbbd8ffff");

        assertDecoding(0xd2800000, "mov x0,#0x0");
        assertDecoding(0xd2800020, "mov x0,#0x1");
        assertDecoding(0xd2800040, "mov x0,#0x2");
        assertDecoding(0xd2800060, "mov x0,#0x3");
        assertDecoding(0xd2800080, "mov x0,#0x4");
        assertDecoding(0xd2800100, "mov x0,#0x8");
        assertDecoding(0xd2800200, "mov x0,#0x10");
        assertDecoding(0xd2802000, "mov x0,#0x100");
        assertDecoding(0xd2820000, "mov x0,#0x1000");
        assertDecoding(0xd2a00020, "mov x0,#0x10000");
        assertDecoding(0xd2a00200, "mov x0,#0x100000");
        assertDecoding(0xd2a02000, "mov x0,#0x1000000");
        assertDecoding(0xd2a20000, "mov x0,#0x10000000");
        assertDecoding(0xd2c00020, "mov x0,#0x100000000");
        assertDecoding(0xd2c00200, "mov x0,#0x1000000000");
        assertDecoding(0xd2c00e00, "mov x0,#0x7000000000");
    }

    @Test
    public void test_mov_simd() {
        assertDecoding(0x5e0105d4, "mov b20, v14.b[0]");
        assertDecoding(0x5e1604cb, "mov h11, v6.h[5]");
        assertDecoding(0x4e183d9d, "mov x29,v12.d[1]");
        assertDecoding(0x4e183eea, "mov x10,v23.d[1]");
        assertDecoding(0x4e083f6d, "mov x13,v27.d[0]");
        assertDecoding(0x4ea21c40, "mov v0.16b, v2.16b");
        assertDecoding(0x0ea31c60, "mov v0.8b, v3.8b");
        assertDecoding(0x0ea41c80, "mov v0.8b, v4.8b");
        assertDecoding(0x4e051e60, "mov v0.b[2], w19");
        assertDecoding(0x4e0f1cc0, "mov v0.b[7], w6");

        assertDecoding(0x4e081d60, "mov v0.d[0], x11");
        assertDecoding(0x4e181ce0, "mov v0.d[1], x7");
        assertDecoding(0x6e180441, "mov v1.d[1], v2.d[0]");
        assertDecoding(0x4e021e60, "mov v0.h[0], w19");
        assertDecoding(0x6e0444e0, "mov v0.s[0], v7.s[2]");
        assertDecoding(0x4e0c1c60, "mov v0.s[1], w3");
        assertDecoding(0x6e142ea0, "mov v0.s[2], v21.s[1]");
    }

    @Test
    public void test_movi_fpu() {
        assertDecoding(0x2f00e410, "movi d16, #0x0");
    }

    @Test
    public void test_movi_simd() {
        assertDecoding(0x4f02e640, "movi v0.16b, #0x52");
        assertDecoding(0x6f00e400, "movi v0.2d, #0x0");
        assertDecoding(0x0f000420, "movi v0.2s, #0x1");
        assertDecoding(0x0f046400, "movi v0.2s, #0x80, lsl #24");
        assertDecoding(0x0f008400, "movi v0.4h, #0x0");
        assertDecoding(0x4f010600, "movi v0.4s, #0x30");
        assertDecoding(0x4f046400, "movi v0.4s, #0x80, lsl #24");
        assertDecoding(0x6f07e7ff, "movi v31.2d, #0xffffffffffffffff");
        assertDecoding(0x0f000409, "movi v9.2s, #0x0");
        assertDecoding(0x0f02d5b2, "movi v18.2s,#0x4d,msl #16");
        assertDecoding(0x0f03a6cf, "movi v15.4h,#0x76,lsl #8");
        assertDecoding(0x4f03269c, "movi v28.4s,#0x74,lsl #8");
        assertDecoding(0x0f02471a, "movi v26.2s,#0x58,lsl #16");
        assertDecoding(0x4f04457c, "movi v28.4s,#0x8b,lsl #16");
    }

    @Test
    public void test_movk() {
        assertDecoding(0x72bbd5a8, "movk w8, #0xdead, lsl #16");
        assertDecoding(0xf2a00000, "movk x0, #0x0, lsl #16");
        assertDecoding(0xf2e00000, "movk x0, #0x0, lsl #48");
        assertDecoding(0xf2a00400, "movk x0, #0x20, lsl #16");
        assertDecoding(0xf2c00400, "movk x0, #0x20, lsl #32");
        assertDecoding(0xf2e00400, "movk x0, #0x20, lsl #48");
        assertDecoding(0xf2804000, "movk x0, #0x200");
        assertDecoding(0xf285e5e0, "movk x0, #0x2f2f");
        assertDecoding(0xf2800149, "movk x9, #0xa");
        assertDecoding(0xf29999a9, "movk x9, #0xcccd");
        assertDecoding(0xf2e19ca9, "movk x9, #0xce5, lsl #48");
        assertDecoding(0xf2c0259f, "movk xzr, #0x12c, lsl #32");
    }

    @Test
    public void test_movz() {
        assertDecoding(0xd2a00016, "movz x22, #0x0, lsl #16");
        assertDecoding(0x52a00019, "movz w25, #0x0, lsl #16");
    }

    @Test
    public void test_mrs_named() {
        assertDecoding(0xd53be040, "mrs x0, cntvct_el0");
        assertDecoding(0xd53b4400, "mrs x0, fpcr");
        assertDecoding(0xd53b4420, "mrs x0, fpsr");
        assertDecoding(0xd5380000, "mrs x0, midr_el1");
        assertDecoding(0xd53bd04b, "mrs x11, tpidr_el0");
        assertDecoding(0xd53b440c, "mrs x12, fpcr");
        assertDecoding(0xd53006b3, "mrs x19,dbgbcr6_el1");
        assertDecoding(0xd5382141, "mrs x1,apibkeylo_el1");
        assertDecoding(0xd53004ca, "mrs x10,dbgwvr4_el1");
        assertDecoding(0xd53e600a, "mrs x10,far_el3");
        assertDecoding(0xd53be8ef, "mrs x15,pmevcntr7_el0");
        assertDecoding(0xd53c6011, "mrs x17,far_el2");
        assertDecoding(0xd5380434, "mrs x20,id_aa64pfr1_el1");
        assertDecoding(0xd53e1215, "mrs x21,zcr_el3");
        assertDecoding(0xd53d6017, "mrs x23,far_el12");
        assertDecoding(0xd538053d, "mrs x29,id_aa64dfr1_el1");
        assertDecoding(0xd53bedbe, "mrs x30,pmevtyper13_el0");
        assertDecoding(0xd53be305, "mrs x5,cntv_tval_el0");
        assertDecoding(0xd53beda7, "mrs x7,pmevtyper13_el0");
        assertDecoding(0xd5385108, "mrs x8,afsr0_el1");
        assertDecoding(0xd53c1009, "mrs x9,sctlr_el2");
    }

    @Test
    public void test_mrs_unknown() {
        assertDecoding(0xd5313420, "mrs x0,s2_1_c3_c4_1");
    }

    @Test
    public void test_msr_named() {
        assertDecoding(0xd51b4403, "msr fpcr, x3");
        assertDecoding(0xd51bd058, "msr tpidr_el0, x24");
        assertDecoding(0xd5100680, "msr dbgbvr6_el1,x0");
        assertDecoding(0xd518072b, "msr id_aa64mmfr1_el1,x11");
        assertDecoding(0xd51c5273, "msr vsesr_el2,x19");
        assertDecoding(0xd5181012, "msr sctlr_el1,x18");
        assertDecoding(0xd5184118, "msr sp_el0,x24");
        assertDecoding(0xd518420b, "msr spsel,x11");
        assertDecoding(0xd51e400f, "msr spsr_el3,x15");
        assertDecoding(0xd51c4353, "msr spsr_und,x19");
        assertDecoding(0xd51d205f, "msr tcr_el12,xzr");
        assertDecoding(0xd51ed052, "msr tpidr_el3,x18");
        assertDecoding(0xd51bd071, "msr tpidrro_el0,x17");
        assertDecoding(0xd5182000, "msr ttbr0_el1,x0");
        assertDecoding(0xd5184284, "msr uao,x4");
        assertDecoding(0xd51dc01a, "msr vbar_el12,x26");
        assertDecoding(0xd51ec002, "msr vbar_el3,x2");
        assertDecoding(0xd51c00b5, "msr vmpidr_el2,x21");
        assertDecoding(0xd51c0008, "msr vpidr_el2,x8");
        assertDecoding(0xd51c2611, "msr vsttbr_el2,x17");
        assertDecoding(0xd51c2150, "msr vtcr_el2,x16");
        assertDecoding(0xd51c2112, "msr vttbr_el2,x18");
        assertDecoding(0xd51d5128, "msr afsr1_el12,x8");
    }

    @Test
    public void test_msr_unknown() {
        assertDecoding(0xd510a7a0, "msr s2_0_c10_c7_5,x0");
        assertDecoding(0xd51847d9, "msr s3_0_c4_c7_6,x25");
    }

    @Test
    public void test_msub() {
        assertDecoding(0x1b05d020, "msub w0, w1, w5, w20");
        assertDecoding(0x1b03f141, "msub w1, w10, w3, w28");
        assertDecoding(0x1b1cd17f, "msub wzr, w11, w28, w20");
        assertDecoding(0x1b03ddbf, "msub wzr, w13, w3, w23");
        assertDecoding(0x9b059260, "msub x0, x19, x5, x4");
        assertDecoding(0x9b138022, "msub x2, x1, x19, x0");
        assertDecoding(0x9b1fd0e5, "msub x5, x7, xzr, x20");
    }

    @Test
    public void test_mul() {
        assertDecoding(0x1b137c20, "mul w0, w1, w19");
        assertDecoding(0x1b087c8c, "mul w12, w4, w8");
        assertDecoding(0x1b047c5e, "mul w30, w2, w4");
        assertDecoding(0x9b047d61, "mul x1, x11, x4");
        assertDecoding(0x9b0b7db1, "mul x17, x13, x11");
        assertDecoding(0x9b147c27, "mul x7, x1, x20");
    }

    @Test
    public void test_mul_simd() {
        assertDecoding(0x4eb19c00, "mul v0.4s, v0.4s, v17.4s");
        assertDecoding(0x4eb29c21, "mul v1.4s, v1.4s, v18.4s");
        assertDecoding(0x0f9e828e, "mul v14.2s, v20.2s, v30.s[0]");
        assertDecoding(0x4eb29c42, "mul v2.4s, v2.4s, v18.4s");
        assertDecoding(0x4f788b94, "mul v20.8h, v28.8h, v8.h[7]");
        assertDecoding(0x0f76889a, "mul v26.4h, v4.4h, v6.h[7]");
        assertDecoding(0x0e259ffa, "mul v26.8b, v31.8b, v5.8b");
        assertDecoding(0x4ea99ee3, "mul v3.4s, v23.4s, v9.4s");
        assertDecoding(0x4eb49c63, "mul v3.4s, v3.4s, v20.4s");
    }

    @Test
    public void test_mvn() {
        assertDecoding(0x2a2207e0, "mvn w0, w2, lsl #1");
        assertDecoding(0x2a7a33e0, "mvn w0, w26, lsr #12");
        assertDecoding(0x2a3303ea, "mvn w10, w19");
        assertDecoding(0x2abb6bf5, "mvn w21, w27, asr #26");
        assertDecoding(0x2a2a7ffd, "mvn w29, w10, lsl #31");
        assertDecoding(0x2aff4fe6, "mvn w6, wzr, ror #19");

        assertDecoding(0xaaa00be0, "mvn x0, x0, asr #2");
        assertDecoding(0xaa3407e1, "mvn x1, x20, lsl #1");
        assertDecoding(0xaa600be0, "mvn x0, x0, lsr #2");
        assertDecoding(0xaa640fe0, "mvn x0, x4, lsr #3");
        assertDecoding(0xaa3103ea, "mvn x10, x17");
        assertDecoding(0xaaa9efea, "mvn x10, x9, asr #59");
    }

    @Test
    public void test_mvn_simd() {
        assertDecoding(0x6e205ad6, "mvn v22.16b, v22.16b");
        assertDecoding(0x2e205ad6, "mvn v22.8b, v22.8b");
    }

    @Test
    public void test_mvni_simd() {
        assertDecoding(0x2f000400, "mvni v0.2s, #0x0");
        assertDecoding(0x2f046400, "mvni v0.2s, #0x80, lsl #24");
        assertDecoding(0x6f000400, "mvni v0.4s, #0x0");
        assertDecoding(0x6f000401, "mvni v1.4s, #0x0");
        assertDecoding(0x2f030471, "mvni v17.2s, #0x63");
        assertDecoding(0x6f000411, "mvni v17.4s, #0x0");
        assertDecoding(0x2f0465f3, "mvni v19.2s, #0x8f, lsl #24");
        assertDecoding(0x6f000415, "mvni v21.4s, #0x0");
        assertDecoding(0x6f000403, "mvni v3.4s, #0x0");
        assertDecoding(0x2f07d484, "mvni v4.2s, #0xe4, msl #16");
        assertDecoding(0x6f000404, "mvni v4.4s, #0x0");
        assertDecoding(0x6f000405, "mvni v5.4s, #0x0");
        assertDecoding(0x6f0105e5, "mvni v5.4s, #0x2f");
        assertDecoding(0x6f000407, "mvni v7.4s, #0x0");
        assertDecoding(0x2f000408, "mvni v8.2s, #0x0");
        assertDecoding(0x2f0245c0, "mvni v0.2s,#0x4e,lsl #16");
        assertDecoding(0x6f07a620, "mvni v0.8h,#0xf1,lsl #8");
        assertDecoding(0x2f07c49d, "mvni v29.2s,#0xe4,msl #8");
    }

    @Test
    public void test_movn() {
        assertDecoding(0x129ffff4, "movn w20,#0xffff");
    }

    @Test
    public void test_neg() {
        assertDecoding(0x4b1303e1, "neg w1, w19");
        assertDecoding(0x4b8007e0, "neg w0, w0, asr #1");
        assertDecoding(0x4b800fe0, "neg w0, w0, asr #3");
        assertDecoding(0x4b000fe0, "neg w0, w0, lsl #3");
        assertDecoding(0x4b400be0, "neg w0, w0, lsr #2");
        assertDecoding(0x4b0b0fed, "neg w13, w11, lsl #3");
        assertDecoding(0x4b1433e1, "neg w1, w20, lsl #12");

        assertDecoding(0xcb0b03ea, "neg x10, x11");
        assertDecoding(0xcb010bea, "neg x10, x1, lsl #2");
        assertDecoding(0xcb130fea, "neg x10, x19, lsl #3");
        assertDecoding(0xcb510beb, "neg x11, x17, lsr #2");
    }

    @Test
    public void test_neg_fpu() {
        assertDecoding(0x7ee0b801, "neg d1, d0");
    }

    @Test
    public void test_negs() {
        assertDecoding(0x6b1303e1, "negs w1, w19");
        assertDecoding(0xeb5bc7ea, "negs x10, x27, lsr #49");
        assertDecoding(0xeb00ebeb, "negs x11, x0, lsl #58");
        assertDecoding(0xeb0503eb, "negs x11, x5");
        assertDecoding(0xeb876bf2, "negs x18, x7, asr #26");
        assertDecoding(0xeb1f03ff, "negs xzr, xzr");
    }

    @Test
    public void test_ngc() {
        assertDecoding(0xda0203e1, "ngc x1, x2");
        assertDecoding(0xda1f03e1, "ngc x1, xzr");
    }

    @Test
    public void test_ngcs() {
        assertDecoding(0x7a0603e1, "ngcs w1,w6");
        assertDecoding(0x7a1c03ff, "ngcs wzr,w28");
        assertDecoding(0xfa1703f5, "ngcs x21,x23");
    }

    @Test
    public void test_nop() {
        assertDecoding(0xd503201f, "nop");
    }

    @Test
    public void test_orn() {
        assertDecoding(0x2a360020, "orn w0, w1, w22");
        assertDecoding(0x2a2a2a00, "orn w0, w16, w10, lsl #10");
        assertDecoding(0x2af452a0, "orn w0, w21, w20, ror #20");
        assertDecoding(0x2aec0a61, "orn w1, w19, w12, ror #2");
        assertDecoding(0x2aaa4701, "orn w1, w24, w10, asr #17");
        assertDecoding(0x2a6b4e4e, "orn w14, w18, w11, lsr #19");
        assertDecoding(0x2a704f7f, "orn wzr, w27, w16, lsr #19");

        assertDecoding(0xaa39018a, "orn x10, x12, x25");
        assertDecoding(0xaaaf0d8a, "orn x10, x12, x15, asr #3");
        assertDecoding(0xaa75ba0a, "orn x10, x16, x21, lsr #46");
        assertDecoding(0xaae77c4a, "orn x10, x2, x7, ror #31");
        assertDecoding(0xaa2e990a, "orn x10, x8, x14, lsl #38");
        assertDecoding(0xaa78079f, "orn xzr, x28, x24, lsr #1");
        assertDecoding(0xaab5f0bf, "orn xzr, x5, x21, asr #60");
    }

    @Test
    public void test_orn_simd() {
        assertDecoding(0x4ee11c81, "orn v1.16b, v4.16b, v1.16b");
        assertDecoding(0x4ee21c62, "orn v2.16b, v3.16b, v2.16b");
    }

    @Test
    public void test_orr() {
        assertDecoding(0xb26b1d5f, "orr sp, x10, #0x1fe00000");
        assertDecoding(0x32140020, "orr w0, w1, #0x1000");
        assertDecoding(0x2a0b0020, "orr w0, w1, w11");
        assertDecoding(0x2a022820, "orr w0, w1, w2, lsl #10");
        assertDecoding(0x323b55bf, "orr wsp, w13, #0x7ffffe0");
        assertDecoding(0x2a9f60dd, "orr w29, w6, wzr, asr #24");

        assertDecoding(0xb2780020, "orr x0, x1, #0x100");
        assertDecoding(0xaa130020, "orr x0, x1, x19");
        assertDecoding(0xb264a220, "orr x0, x17, #0xfffffffff000001f");
        assertDecoding(0xaa002c40, "orr x0, x2, x0, lsl #11");
        assertDecoding(0xaa41d2a0, "orr x0, x21, x1, lsr #52");
        assertDecoding(0xaa1e515f, "orr xzr, x10, x30, lsl #20");
        assertDecoding(0xaa8247e0, "orr x0, xzr, x2,asr #17");
    }

    @Test
    public void test_orr_simd() {
        assertDecoding(0x0ea11c00, "orr v0.8b, v0.8b, v1.8b");
        assertDecoding(0x4ea61cb0, "orr v16.16b, v5.16b, v6.16b");
        assertDecoding(0x4f01b556, "orr v22.8h, #0x2a, lsl #8");
    }

    @Test
    public void test_pacia1716() {
        assertDecoding(0xd503211f, "pacia1716");
    }

    @Test
    public void test_pmul_simd() {
        assertDecoding(0x2e259dee, "pmul v14.8b,v15.8b,v5.8b");
        assertDecoding(0x6e2e9d69, "pmul v9.16b,v11.16b,v14.16b");
    }

    @Test
    public void test_pmull2_simd() {
        assertDecoding(0x4ef2e2a1, "pmull2 v1.1q, v21.2d, v18.2d");
    }

    @Test
    public void test_pmull_simd() {
        assertDecoding(0x0ef3e281, "pmull v1.1q, v20.1d, v19.1d");
    }

    @Test
    public void test_prfm() {
        assertDecoding(0xf9800020, "prfm pldl1keep, [x1]");
        assertDecoding(0xf980c021, "prfm pldl1strm, [x1, #384]");
        assertDecoding(0xf9b23232, "prfm pstl2keep, [x17, #25696]");
        assertDecoding(0xf9a6be13, "prfm pstl2strm, [x16, #19832]");
        assertDecoding(0xf983e7a6, "prfm #0x06, [x29, #1992]");
        assertDecoding(0xf98b527e, "prfm #0x1e, [x19, #5792]");
    }

    @Test
    public void test_prfm_with_label() {
        assertDecodingWithPc(0x10cb94, 0xd8041f00, "prfm pldl1keep, 0x114f74");
        assertDecodingWithPc(0xfed9c, 0xd81696a1, "prfm pldl1strm, 0x12c070");
        assertDecodingWithPc(0x10b8b8, 0xd80dd8c2, "prfm pldl2keep, 0x1273d0");
        assertDecodingWithPc(0xfe5e0, 0xd8556823, "prfm pldl2strm, 0x1a92e4");
        assertDecodingWithPc(0xed75c, 0xd8452364, "prfm pldl3keep, 0x177bc8");
        assertDecodingWithPc(0xeca24, 0xd83bc2c5, "prfm pldl3strm, 0x16427c");
        assertDecodingWithPc(0xedaf8, 0xd81be008, "prfm plil1keep, 0x1256f8");
        assertDecodingWithPc(0x10eed4, 0xd803c2e9, "prfm plil1strm, 0x116730");
        assertDecodingWithPc(0x1051f8, 0xd8c936ea, "prfm plil2keep, 0x978d4");
        assertDecodingWithPc(0xf270c, 0xd8098a2b, "prfm plil2strm, 0x105850");
        assertDecodingWithPc(0xfeb04, 0xd8a9142c, "prfm plil3keep, 0x50d88");
        assertDecodingWithPc(0x108ecc, 0xd81bfc6d, "prfm plil3strm, 0x140e58");
        assertDecodingWithPc(0x10c3e0, 0xd846b3b0, "prfm pstl1keep, 0x199a54");
        assertDecodingWithPc(0xed1f0, 0xd817d8f1, "prfm pstl1strm, 0x11cd0c");
        assertDecodingWithPc(0x105d24, 0xd8891232, "prfm pstl2keep, 0x17f68");
        assertDecodingWithPc(0x101fa4, 0xd832e7f3, "prfm pstl2strm, 0x167ca0");
        assertDecodingWithPc(0xedf44, 0xd857b394, "prfm pstl3keep, 0x19d5b4");
        assertDecodingWithPc(0x107924, 0xd84b7a75, "prfm pstl3strm, 0x19e870");
        assertDecodingWithPc(0x10e530, 0xd846c257, "prfm #0x17, 0x19bd78");
        assertDecodingWithPc(0x473fc, 0xd8d800d8, "prfm #0x18, 0xffffffffffff7414");
    }

    @Test
    public void test_prfum() {
        assertDecoding(0xf897617b, "prfum #0x1b, [x11, #-138]");
        assertDecoding(0xf883d325, "prfum pldl3strm, [x25, #61]");
        assertDecoding(0xf885a3e8, "prfum plil1keep, [sp, #90]");
        assertDecoding(0xf8895135, "prfum pstl3strm, [x9, #149]");
    }

    @Test
    public void test_raddhn2_simd() {
        assertDecoding(0x6e65406b, "raddhn2 v11.8h, v3.4s, v5.4s");
        assertDecoding(0x6eaa4355, "raddhn2 v21.4s, v26.2d, v10.2d");
    }

    @Test
    public void test_raddhn_simd() {
        assertDecoding(0x2eaa4334, "raddhn v20.2s, v25.2d, v10.2d");
    }

    @Test
    public void test_rbit() {
        assertDecoding(0x5ac00102, "rbit w2, w8");
        assertDecoding(0x5ac00376, "rbit w22, w27");

        assertDecoding(0xdac00281, "rbit x1, x20");
        assertDecoding(0xdac00083, "rbit x3, x4");
    }

    @Test
    public void test_rbit_simd() {
        assertDecoding(0x6e605800, "rbit v0.16b, v0.16b");
        assertDecoding(0x6e605a73, "rbit v19.16b, v19.16b");
        assertDecoding(0x6e605863, "rbit v3.16b, v3.16b");
        assertDecoding(0x6e605884, "rbit v4.16b, v4.16b");
        assertDecoding(0x6e6058a5, "rbit v5.16b, v5.16b");
        assertDecoding(0x6e6058c6, "rbit v6.16b, v6.16b");
        assertDecoding(0x6e6058e7, "rbit v7.16b, v7.16b");
        assertDecoding(0x6e605908, "rbit v8.16b, v8.16b");
    }

    @Test
    public void test_ret() {
        assertDecoding(0xd65f03c0, "ret");
        assertDecoding(0xd65f01e0, "ret x15");
    }

    @Test
    public void test_rev() {
        assertDecoding(0x5ac00981, "rev w1, w12");
        assertDecoding(0xdac00f02, "rev x2, x24");
    }

    @Test
    public void test_rev16() {
        assertDecoding(0x5ac00522, "rev16 w2, w9");
    }

    @Test
    public void test_rev32() {
        assertDecoding(0xdac0096b, "rev32 x11, x11");
    }

    @Test
    public void test_rev32_simd() {
        assertDecoding(0x2e200800, "rev32 v0.8b, v0.8b");
        assertDecoding(0x6e200a81, "rev32 v1.16b, v20.16b");
        assertDecoding(0x6e60096b, "rev32 v11.8h, v11.8h");
    }

    @Test
    public void test_rev64_simd() {
        assertDecoding(0x4e2008c1, "rev64 v1.16b, v6.16b");
    }


    @Test
    public void test_ror() {
        assertDecoding(0x138a4d40, "ror w0, w10, #19");
        assertDecoding(0x1acb2d4a, "ror w10, w10, w11");
        assertDecoding(0x93c18820, "ror x0, x1, #34");
        assertDecoding(0x9ac22e62, "ror x2, x19, x2");
    }

    @Test
    public void test_rshrn_simd() {
        assertDecoding(0x0f258fac, "rshrn v12.2s, v29.2d, #27");
        assertDecoding(0x0f0c8eae, "rshrn v14.8b, v21.8h, #4");
        assertDecoding(0x0f1e8eb4, "rshrn v20.4h, v21.4s, #2");
    }

    @Test
    public void test_rsubhn2_simd() {
        assertDecoding(0x6e606246, "rsubhn2 v6.8h, v18.4s, v0.4s");
    }


    @Test
    public void test_saba_simd() {
        assertDecoding(0x0ea27e20, "saba v0.2s, v17.2s, v2.2s");
        assertDecoding(0x4e687c7b, "saba v27.8h, v3.8h, v8.8h");
    }

    @Test
    public void test_sabal_simd() {
        assertDecoding(0x0e7a53e1, "sabal v1.4s, v31.4h, v26.4h");
    }

    @Test
    public void test_sabd_simd() {
        assertDecoding(0x4e747792, "sabd v18.8h, v28.8h, v20.8h");
        assertDecoding(0x0e6b74b4, "sabd v20.4h, v5.4h, v11.4h");
    }

    @Test
    public void test_sabdl_simd() {
        assertDecoding(0x0e77716f, "sabdl v15.4s, v11.4h, v23.4h");
    }

    @Test
    public void test_sadalp_simd() {
        assertDecoding(0x0ea06a00, "sadalp v0.1d,v16.2s");
    }

    @Test
    public void test_saddw_simd() {
        assertDecoding(0x0e231156, "saddw v22.8h, v10.8h, v3.8b");
    }

    @Test
    public void test_saddlp() {
        assertDecoding(0x0e602897, "saddlp v23.2s,v4.4h");
        assertDecoding(0x0e6029a6, "saddlp v6.2s,v13.4h");
        assertDecoding(0x0e202b5a, "saddlp v26.4h,v26.8b");
        assertDecoding(0x0ea02bcf, "saddlp v15.1d,v30.2s");
    }

    @Test
    public void test_saddlv() {
        assertDecoding(0x0e303b00, "saddlv h0,v24.8b");
    }

    @Test
    public void test_sbc() {
        assertDecoding(0x5a1f0040, "sbc w0, w2, wzr");
        assertDecoding(0xda030161, "sbc x1, x11, x3");
        assertDecoding(0xda1f0181, "sbc x1, x12, xzr");
    }

    @Test
    public void test_sbcs() {
        assertDecoding(0xfa1f020a, "sbcs x10, x16, xzr");
        assertDecoding(0xfa0e026a, "sbcs x10, x19, x14");
        assertDecoding(0xfa1f027f, "sbcs xzr, x19, xzr");
    }

    @Test
    public void test_sbfiz() {
        assertDecoding(0x13131300, "sbfiz w0, w24, #13, #5");
        assertDecoding(0x131c5e01, "sbfiz w1, w16, #4, #24");
        assertDecoding(0x131d42b2, "sbfiz w18, w21, #3, #17");
        assertDecoding(0x937d7c20, "sbfiz x0, x1, #3, #32");
        assertDecoding(0x93738260, "sbfiz x0, x19, #13, #33");
        assertDecoding(0x93704001, "sbfiz x1, x0, #16, #17");
        assertDecoding(0x93501c4e, "sbfiz x14, x2, #48, #8");
        assertDecoding(0x9375c76e, "sbfiz x14, x27, #11, #50");
        assertDecoding(0x934404b1, "sbfiz x17, x5, #60, #2");
        assertDecoding(0x937a47ff, "sbfiz xzr, xzr, #6, #18");
    }

    @Test
    public void test_sbfx() {
        assertDecoding(0x13006121, "sbfx w1, w9, #0, #25");
        assertDecoding(0x1306510e, "sbfx w14, w8, #6, #15");
        assertDecoding(0x131b750f, "sbfx w15, w8, #27, #3");
        assertDecoding(0x93420820, "sbfx x0, x1, #2, #1");
        assertDecoding(0x93405ea0, "sbfx x0, x21, #0, #24");
        assertDecoding(0x9350c5d8, "sbfx x24, x14, #16, #34");
        assertDecoding(0x935dd4dd, "sbfx x29, x6, #29, #25");
        assertDecoding(0x9358f81f, "sbfx xzr, x0, #24, #39");
    }

    @Test
    public void test_scvtf_fpu() {
        assertDecoding(0x1e62026a, "scvtf d10, w19");
        assertDecoding(0x5e61d802, "scvtf d2, d0");
        assertDecoding(0x9e620282, "scvtf d2, x20");
        assertDecoding(0x1e220261, "scvtf s1, w19");
        assertDecoding(0x9e220041, "scvtf s1, x2");
    }

    @Test
    public void test_scvtf_simd() {
        assertDecoding(0x4e79db8b, "scvtf v11.8h,v28.8h");
        assertDecoding(0x0e79db8c, "scvtf v12.4h,v28.4h");
        assertDecoding(0x4e21d800, "scvtf v0.4s, v0.4s");
        assertDecoding(0x4e61d821, "scvtf v1.2d, v1.2d");
        assertDecoding(0x4f3ae5cb, "scvtf v11.4s, v14.4s, #6");
    }

    @Test
    public void test_sdiv() {
        assertDecoding(0x1ad70e95, "sdiv w21, w20, w23");
        assertDecoding(0x9ac20e41, "sdiv x1, x18, x2");
    }

    @Test
    public void test_sha1c_simd() {
        assertDecoding(0x5e050062, "sha1c q2, s3, v5.4s");
    }

    @Test
    public void test_sha1h_fpu() {
        assertDecoding(0x5e280843, "sha1h s3, s2");
    }

    @Test
    public void test_sha1m_simd() {
        assertDecoding(0x5e052062, "sha1m q2, s3, v5.4s");
    }

    @Test
    public void test_sha1p_simd() {
        assertDecoding(0x5e051062, "sha1p q2, s3, v5.4s");
    }

    @Test
    public void test_sha1su0_simd() {
        assertDecoding(0x5e133251, "sha1su0 v17.4s, v18.4s, v19.4s");
    }

    @Test
    public void test_sha1su1_simd() {
        assertDecoding(0x5e281a70, "sha1su1 v16.4s, v19.4s");
    }

    @Test
    public void test_sha256h2_simd() {
        assertDecoding(0x5e105041, "sha256h2 q1, q2, v16.4s");
    }

    @Test
    public void test_sha256h_simd() {
        assertDecoding(0x5e144082, "sha256h q2, q4, v20.4s");
    }

    @Test
    public void test_sha256su0_simd() {
        assertDecoding(0x5e282a30, "sha256su0 v16.4s, v17.4s");
    }

    @Test
    public void test_sha256su1_simd() {
        assertDecoding(0x5e136250, "sha256su1 v16.4s, v18.4s, v19.4s");
    }

    @Test
    public void test_shadd_simd() {
        assertDecoding(0x0e3b07a5, "shadd v5.8b, v29.8b, v27.8b");
        assertDecoding(0x4eab05e8, "shadd v8.4s, v15.4s, v11.4s");
    }

    @Test
    public void test_shl_fpu() {
        assertDecoding(0x5f605400, "shl d0, d0, #32");
        assertDecoding(0x5f605421, "shl d1, d1, #32");
    }

    @Test
    public void test_shl_simd() {
        assertDecoding(0x4f455400, "shl v0.2d, v0.2d, #5");
        assertDecoding(0x4f2157e0, "shl v0.4s, v31.4s, #1");
        assertDecoding(0x4f095421, "shl v1.16b, v1.16b, #1");
        assertDecoding(0x4f795673, "shl v19.2d, v19.2d, #57");
        assertDecoding(0x0f375442, "shl v2.2s, v2.2s, #23");
        assertDecoding(0x4f4257bd, "shl v29.2d, v29.2d, #2");
        assertDecoding(0x4f415463, "shl v3.2d, v3.2d, #1");
        assertDecoding(0x4f2257ff, "shl v31.4s, v31.4s, #2");
    }

    @Test
    public void test_shll_fpu() {
        assertDecoding(0x2ea13a50, "shll v16.2d,v18.2s,#32");
    }

    @Test
    public void test_shrn2_simd() {
        assertDecoding(0x4f2286f6, "shrn2 v22.4s, v23.2d, #30");
    }

    @Test
    public void test_shrn_simd() {
        assertDecoding(0x0f26867d, "shrn v29.2s, v19.2d, #26");
        assertDecoding(0x0f388783, "shrn v3.2s, v28.2d, #8");
    }

    @Test
    public void test_sli_fpu() {
        assertDecoding(0x7f595432, "sli d18, d1, #25");
        assertDecoding(0x7f5f5479, "sli d25, d3, #31");
    }

    @Test
    public void test_sli_simd() {
        assertDecoding(0x6f275681, "sli v1.4s, v20.4s, #7");
        assertDecoding(0x6f28574b, "sli v11.4s, v26.4s, #8");
        assertDecoding(0x6f2c5701, "sli v1.4s, v24.4s, #12");
        assertDecoding(0x6f12577a, "sli v26.8h, v27.8h, #2");
        assertDecoding(0x6f58570e, "sli v14.2d, v24.2d, #24");
    }

    @Test
    public void test_sm3partw2_simd() {
        assertDecoding(0xce73c50d, "sm3partw2 v13.4s, v8.4s, v19.4s");
    }

    @Test
    public void test_sm3ss1_simd() {
        assertDecoding(0xce447e21, "sm3ss1 v1.4s, v17.4s, v4.4s, v31.4s");
    }

    @Test
    public void test_sm3tt1a_simd() {
        assertDecoding(0xce58908a, "sm3tt1a v10.4s, v4.4s, v24.s[1]");
    }

    @Test
    public void test_sm3tt1b_simd() {
        assertDecoding(0xce5884bb, "sm3tt1b v27.4s, v5.4s, v24.s[0]");
    }

    @Test
    public void test_sm3tt2a_simd() {
        assertDecoding(0xce45b877, "sm3tt2a v23.4s, v3.4s, v5.s[3]");
    }

    @Test
    public void test_sm3tt2b_simd() {
        assertDecoding(0xce549c6d, "sm3tt2b v13.4s, v3.4s, v20.s[1]");
    }

    @Test
    public void test_smaddl() {
        assertDecoding(0x9b231261, "smaddl x1, w19, w3, x4");
        assertDecoding(0x9b254bbf, "smaddl xzr, w29, w5, x18");
    }

    @Test
    public void test_smax_simd() {
        assertDecoding(0x4e7965cf, "smax v15.8h, v14.8h, v25.8h");
        assertDecoding(0x0eaa64d0, "smax v16.2s, v6.2s, v10.2s");
        assertDecoding(0x4e3a6613, "smax v19.16b, v16.16b, v26.16b");
    }

    @Test
    public void test_smaxp_simd() {
        assertDecoding(0x0ea8a6b1, "smaxp v17.2s, v21.2s, v8.2s");
    }

    @Test
    public void test_smaxv_simd() {
        assertDecoding(0x0e30abc1, "smaxv b1,v30.8b");
        assertDecoding(0x4e30aa2e, "smaxv b14,v17.16b");
        assertDecoding(0x0e70aa41, "smaxv h1,v18.4h");
        assertDecoding(0x4e70a88d, "smaxv h13,v4.8h");
        assertDecoding(0x4eb0ab70, "smaxv s16,v27.4s");
    }

    @Test
    public void test_smc() {
        assertDecoding(0xd411e943, "smc #0x8f4a");
        assertDecoding(0xd417e883, "smc #0xbf44");
    }

    @Test
    public void test_smnegl() {
        assertDecoding(0x9b3cfecf, "smnegl x15,w22,w28");
    }

    @Test
    public void test_smin_simd() {
        assertDecoding(0x0eb36c9f, "smin v31.2s, v4.2s, v19.2s");
    }

    @Test
    public void test_sminp_simd() {
        assertDecoding(0x4e3fade0, "sminp v0.16b, v15.16b, v31.16b");
    }

    @Test
    public void test_sminv_simd() {
        assertDecoding(0x4e31a9eb, "sminv b11,v15.16b");
        assertDecoding(0x0e31ab8b, "sminv b11,v28.8b");
        assertDecoding(0x0e71aa01, "sminv h1,v16.4h");
        assertDecoding(0x4e71aa01, "sminv h1,v16.8h");
        assertDecoding(0x4eb1aaf2, "sminv s18,v23.4s");
    }

    @Test
    public void test_smlal2_simd() {
        assertDecoding(0x4ea3822f, "smlal2 v15.2d, v17.4s, v3.4s");
        assertDecoding(0x4f81218a, "smlal2 v10.2d, v12.4s, v1.s[0]");
        assertDecoding(0x4f6d2acf, "smlal2 v15.4s, v22.8h, v13.h[6]");
        assertDecoding(0x4fa621b2, "smlal2 v18.2d, v13.4s, v6.s[1]");
        assertDecoding(0x4faa20c2, "smlal2 v2.2d, v6.4s, v10.s[1]");
        assertDecoding(0x4f4f2819, "smlal2 v25.4s, v0.8h, v15.h[4]");
    }

    @Test
    public void test_smlal_simd() {
        assertDecoding(0x0eb483ea, "smlal v10.2d, v31.2s, v20.2s");
        assertDecoding(0x0e74822a, "smlal v10.4s, v17.4h, v20.4h");
        assertDecoding(0x0fae236f, "smlal v15.2d, v27.2s, v14.s[1]");
        assertDecoding(0x0f9c2b22, "smlal v2.2d, v25.2s, v28.s[2]");
        assertDecoding(0x0fb32b1f, "smlal v31.2d, v24.2s, v19.s[3]");
    }

    @Test
    public void test_smlsl2_simd() {
        assertDecoding(0x4e3ca209, "smlsl2 v9.8h, v16.16b, v28.16b");
        assertDecoding(0x4fbd6ad3, "smlsl2 v19.2d, v22.4s, v29.s[3]");
        assertDecoding(0x4fa661fc, "smlsl2 v28.2d, v15.4s, v6.s[1]");
    }

    @Test
    public void test_smlsl_simd() {
        assertDecoding(0x0e23a372, "smlsl v18.8h, v27.8b, v3.8b");
        assertDecoding(0x0e38a167, "smlsl v7.8h, v11.8b, v24.8b");
        assertDecoding(0x0f75604f, "smlsl v15.4s, v2.4h, v5.h[3]");
        assertDecoding(0x0f736814, "smlsl v20.4s, v0.4h, v3.h[7]");
    }

    @Test
    public void test_smov_simd() {
        assertDecoding(0x4e112e80, "smov x0, v20.b[8]");
    }

    @Test
    public void test_smsubl() {
        assertDecoding(0x9b389c2d, "smsubl x13, w1, w24, x7");
    }

    @Test
    public void test_smulh() {
        assertDecoding(0x9b577c41, "smulh x1, x2, x23");
        assertDecoding(0x9b5a7f8b, "smulh x11, x28, x26");
        assertDecoding(0x9b4c2f9f, "smulh xzr, x28, x12");
    }

    @Test
    public void test_smull() {
        assertDecoding(0x9b2b7d81, "smull x1, w12, w11");
    }

    @Test
    public void test_smull2_simd() {
        assertDecoding(0x4eb0c0c2, "smull2 v2.2d, v6.4s, v16.4s");
        assertDecoding(0x4f94a27c, "smull2 v28.2d, v19.4s, v20.s[0]");
    }

    @Test
    public void test_smull_simd() {
        assertDecoding(0x0ea7c061, "smull v1.2d, v3.2s, v7.2s");
        assertDecoding(0x0e67c074, "smull v20.4s, v3.4h, v7.4h");
        assertDecoding(0x0f91a356, "smull v22.2d, v26.2s, v17.s[0]");
        assertDecoding(0x0f93a303, "smull v3.2d, v24.2s, v19.s[0]");
        assertDecoding(0x0f5cab91, "smull v17.4s,v28.4h,v12.h[5]");
    }

    @Test
    public void test_sqadd_simd() {
        assertDecoding(0x0e720fb7, "sqadd v23.4h, v29.4h, v18.4h");
    }

    @Test
    public void test_sqabs_fpu() {
        assertDecoding(0x5e207a0a, "sqabs b10,b16");
        assertDecoding(0x5e607a52, "sqabs h18,h18");
        assertDecoding(0x5ea07a0b, "sqabs s11,s16");
    }

    @Test
    public void test_sqdmlal2_simd() {
        assertDecoding(0x4f4b332c, "sqdmlal2 v12.4s, v25.8h, v11.h[0]");
        assertDecoding(0x4f753292, "sqdmlal2 v18.4s, v20.8h, v5.h[3]");
        assertDecoding(0x4f5c3af8, "sqdmlal2 v24.4s, v23.8h, v12.h[5]");
    }

    @Test
    public void test_sqdmlal_simd() {
        assertDecoding(0x5f5c3a71, "sqdmlal s17, h19, v12.h[5]");
        assertDecoding(0x5f6830c3, "sqdmlal s3, h6, v8.h[2]");
        assertDecoding(0x5f6b325e, "sqdmlal s30, h18, v11.h[2]");
        assertDecoding(0x0e6e9241, "sqdmlal v1.4s, v18.4h, v14.4h");
        assertDecoding(0x0f97326d, "sqdmlal v13.2d, v19.2s, v23.s[0]");
        assertDecoding(0x0ea89103, "sqdmlal v3.2d, v8.2s, v8.2s");
    }

    @Test
    public void test_sqdmlsl2_simd() {
        assertDecoding(0x4fb078ed, "sqdmlsl2 v13.2d, v7.4s, v16.s[3]");
        assertDecoding(0x4fa77a9a, "sqdmlsl2 v26.2d, v20.4s, v7.s[3]");
        assertDecoding(0x4f82711b, "sqdmlsl2 v27.2d, v8.4s, v2.s[0]");
        assertDecoding(0x4fa07803, "sqdmlsl2 v3.2d, v0.4s, v0.s[3]");
    }

    @Test
    public void test_sqdmlsl_simd() {
        assertDecoding(0x5fa072f1, "sqdmlsl d17, s23, v0.s[1]");
        assertDecoding(0x5fa17be7, "sqdmlsl d7, s31, v1.s[3]");
        assertDecoding(0x5f557b19, "sqdmlsl s25, h24, v5.h[5]");
        assertDecoding(0x0fac796d, "sqdmlsl v13.2d, v11.2s, v12.s[3]");
        assertDecoding(0x0f767955, "sqdmlsl v21.4s, v10.4h, v6.h[7]");
        assertDecoding(0x0f707895, "sqdmlsl v21.4s, v4.4h, v0.h[7]");
        assertDecoding(0x0f5a7003, "sqdmlsl v3.4s, v0.4h, v10.h[1]");
    }

    @Test
    public void test_sqdmulh() {
        assertDecoding(0x5e7bb5c2, "sqdmulh h2, h14, h27");
    }

    @Test
    public void test_sqdmulh_simd() {
        assertDecoding(0x5f59c862, "sqdmulh h2, h3, v9.h[5]");
        assertDecoding(0x5f41c018, "sqdmulh h24, h0, v1.h[0]");
        assertDecoding(0x5fa1c00c, "sqdmulh s12, s0, v1.s[1]");
        assertDecoding(0x5fb7c2ee, "sqdmulh s14, s23, v23.s[1]");
        assertDecoding(0x5fabc8f6, "sqdmulh s22, s7, v11.s[3]");
        assertDecoding(0x5fa0c118, "sqdmulh s24, s8, v0.s[1]");
        assertDecoding(0x0e6eb4ea, "sqdmulh v10.4h, v7.4h, v14.4h");
        assertDecoding(0x0fbec2d9, "sqdmulh v25.2s, v22.2s, v30.s[1]");
        assertDecoding(0x4ea9b49a, "sqdmulh v26.4s, v4.4s, v9.4s");
        assertDecoding(0x4f42c29b, "sqdmulh v27.8h, v20.8h, v2.h[0]");
        assertDecoding(0x0f68c0dc, "sqdmulh v28.4h, v6.4h, v8.h[2]");
        assertDecoding(0x4fabc1fc, "sqdmulh v28.4s, v15.4s, v11.s[1]");
        assertDecoding(0x4f6ac0dc, "sqdmulh v28.8h, v6.8h, v10.h[2]");
        assertDecoding(0x4f4cc85e, "sqdmulh v30.8h, v2.8h, v12.h[4]");
    }

    @Test
    public void test_sqdmull2_simd() {
        assertDecoding(0x4f89b2fa, "sqdmull2 v26.2d, v23.4s, v9.s[0]");
        assertDecoding(0x4eb9d3ff, "sqdmull2 v31.2d, v31.4s, v25.4s");
        assertDecoding(0x4fb3b804, "sqdmull2 v4.2d, v0.4s, v19.s[3]");
        assertDecoding(0x4f5ab804, "sqdmull2 v4.4s, v0.8h, v10.h[5]");
    }

    @Test
    public void test_sqdmull_fpu() {
        assertDecoding(0x5eb2d0a5, "sqdmull d5, s5, s18");
        assertDecoding(0x5e77d12e, "sqdmull s14, h9, h23");
    }

    @Test
    public void test_sqdmull_simd() {
        assertDecoding(0x5f5bb00f, "sqdmull s15, h0, v11.h[1]");
        assertDecoding(0x5f59b98f, "sqdmull s15, h12, v9.h[5]");
        assertDecoding(0x5f5fb9f9, "sqdmull s25, h15, v15.h[5]");
        assertDecoding(0x5f60bac6, "sqdmull s6, h22, v0.h[6]");
        assertDecoding(0x0f91b1ea, "sqdmull v10.2d, v15.2s, v17.s[0]");
        assertDecoding(0x0f8ebb6d, "sqdmull v13.2d, v27.2s, v14.s[2]");
    }

    @Test
    public void test_sqrdmlah_simd() {
        assertDecoding(0x7f6fd311, "sqrdmlah h17, h24, v15.h[2]");
        assertDecoding(0x7f6ad0f3, "sqrdmlah h19, h7, v10.h[2]");
        assertDecoding(0x7f7fd2de, "sqrdmlah h30, h22, v15.h[3]");
        assertDecoding(0x2f6cdb4e, "sqrdmlah v14.4h, v26.4h, v12.h[6]");
        assertDecoding(0x2f9cd2b0, "sqrdmlah v16.2s, v21.2s, v28.s[0]");
    }

    @Test
    public void test_sqneg_fpu() {
        assertDecoding(0x7ea07bf8, "sqneg s24,s31");
    }

    @Test
    public void test_sqrdmlsh_simd() {
        assertDecoding(0x7f53f0cf, "sqrdmlsh h15, h6, v3.h[1]");
        assertDecoding(0x7f75f1be, "sqrdmlsh h30, h13, v5.h[3]");
        assertDecoding(0x7fb6f3f9, "sqrdmlsh s25, s31, v22.s[1]");
        assertDecoding(0x6f8ef14e, "sqrdmlsh v14.4s, v10.4s, v14.s[0]");
        assertDecoding(0x2f74f090, "sqrdmlsh v16.4h, v4.4h, v4.h[3]");
        assertDecoding(0x2fa4f837, "sqrdmlsh v23.2s, v1.2s, v4.s[3]");
        assertDecoding(0x2f88f3a8, "sqrdmlsh v8.2s, v29.2s, v8.s[0]");
    }

    @Test
    public void test_sqrdmulh_fpu() {
        assertDecoding(0x7ea0b71c, "sqrdmulh s28, s24, s0");
    }

    @Test
    public void test_sqrdmulh_simd() {
        assertDecoding(0x5f45d950, "sqrdmulh h16, h10, v5.h[4]");
        assertDecoding(0x5f47db73, "sqrdmulh h19, h27, v7.h[4]");
        assertDecoding(0x5f89d186, "sqrdmulh s6, s12, v9.s[0]");
        assertDecoding(0x6ebdb480, "sqrdmulh v0.4s, v4.4s, v29.4s");
        assertDecoding(0x4f9edb0a, "sqrdmulh v10.4s, v24.4s, v30.s[2]");
        assertDecoding(0x4f8fd02f, "sqrdmulh v15.4s, v1.4s, v15.s[0]");
        assertDecoding(0x0f84d059, "sqrdmulh v25.2s, v2.2s, v4.s[0]");
        assertDecoding(0x0f94d2a4, "sqrdmulh v4.2s, v21.2s, v20.s[0]");
    }

    @Test
    public void test_sqrshl_fpu() {
        assertDecoding(0x5ee75da6, "sqrshl d6, d13, d7");
        assertDecoding(0x5e395e65, "sqrshl b5,b19,b25");
        assertDecoding(0x5e7f5d79, "sqrshl h25,h11,h31");
        assertDecoding(0x5eb15fec, "sqrshl s12,s31,s17");
    }

    @Test
    public void test_sqrshl_simd() {
        assertDecoding(0x0eb45e92, "sqrshl v18.2s, v20.2s, v20.2s");
        assertDecoding(0x0e6b5ff4, "sqrshl v20.4h, v31.4h, v11.4h");
        assertDecoding(0x4e665e65, "sqrshl v5.8h, v19.8h, v6.8h");
    }

    @Test
    public void test_sqrshrn() {
        assertDecoding(0x5f2c9f8a, "sqrshrn s10,d28,#20");
    }

    @Test
    public void test_sqrshrn2_simd() {
        assertDecoding(0x4f329dcb, "sqrshrn2 v11.4s, v14.2d, #14");
    }

    @Test
    public void test_sqrshrun_fpu() {
        assertDecoding(0x7f268df3, "sqrshrun s19, d15, #26");
    }

    @Test
    public void test_sqrshrun_simd() {
        assertDecoding(0x2f0c8cd8, "sqrshrun v24.8b, v6.8h, #4");
        assertDecoding(0x2f3d8e65, "sqrshrun v5.2s, v19.2d, #3");
        assertDecoding(0x2f138dc6, "sqrshrun v6.4h, v14.4s, #13");
    }

    @Test
    public void test_sqshl() {
        assertDecoding(0x5e204fd7, "sqshl b23, b30, b0");
        assertDecoding(0x5e604eef, "sqshl h15, h23, h0");
        assertDecoding(0x5f2a7673, "sqshl s19, s19, #10");
    }

    @Test
    public void test_sqshl_simd() {
        assertDecoding(0x0f277493, "sqshl v19.2s, v4.2s, #7");
        assertDecoding(0x0f32777e, "sqshl v30.2s, v27.2s, #18");
        assertDecoding(0x4ea44ca7, "sqshl v7.4s, v5.4s, v4.4s");
        assertDecoding(0x4f0a7695, "sqshl v21.16b,v20.16b,#2");
    }

    @Test
    public void test_sqshlu_simd() {
        assertDecoding(0x6f356520, "sqshlu v0.4s,v9.4s,#21");
        assertDecoding(0x6f0f64c1, "sqshlu v1.16b,v6.16b,#7");
        assertDecoding(0x2f0f66b3, "sqshlu v19.8b,v21.8b,#7");
    }

    @Test
    public void test_sqshrn_simd() {
        assertDecoding(0x0f2897aa, "sqshrn v10.2s, v29.2d, #24");
        assertDecoding(0x0f1b9592, "sqshrn v18.4h, v12.4s, #5");
        assertDecoding(0x5f2796f0, "sqshrn s16,d23,#25");
    }

    @Test
    public void test_sqshrun_fpu() {
        assertDecoding(0x7f1b8647, "sqshrun h7, s18, #5");
    }

    @Test
    public void test_sqshrun_simd() {
        assertDecoding(0x2f3b8642, "sqshrun v2.2s, v18.2d, #5");
        assertDecoding(0x2f3c8723, "sqshrun v3.2s, v25.2d, #4");
    }

    @Test
    public void test_sqsub_fpu() {
        assertDecoding(0x5efc2d4d, "sqsub d13, d10, d28");
        assertDecoding(0x5eac2ca3, "sqsub s3, s5, s12");
    }

    @Test
    public void test_sqsub_simd() {
        assertDecoding(0x0e222e9e, "sqsub v30.8b, v20.8b, v2.8b");
    }

    @Test
    public void test_sqxtn_fpu() {
        assertDecoding(0x5e214a0b, "sqxtn b11,h16");
        assertDecoding(0x5e21497f, "sqxtn b31,h11");
    }

    @Test
    public void test_sqxtun_fpu() {
        assertDecoding(0x7e212931, "sqxtun b17,h9");
    }

    @Test
    public void test_srhadd_simd() {
        assertDecoding(0x4e7e160b, "srhadd v11.8h, v16.8h, v30.8h");
    }

    @Test
    public void test_sri_fpu() {
        assertDecoding(0x7f6e4560, "sri d0,d11,#18");
        assertDecoding(0x7f5145aa, "sri d10,d13,#47");
        assertDecoding(0x7f5a476c, "sri d12,d27,#38");
        assertDecoding(0x7f7d478d, "sri d13,d28,#3");
        assertDecoding(0x7f424442, "sri d2,d2,#62");
        assertDecoding(0x7f7c4614, "sri d20,d16,#4");
        assertDecoding(0x7f7b45bf, "sri d31,d13,#5");
        assertDecoding(0x7f704545, "sri d5,d10,#16");
    }

    @Test
    public void test_sri_simd() {
        assertDecoding(0x2f08444f, "sri v15.8b,v2.8b,#8");
        assertDecoding(0x6f724636, "sri v22.2d,v17.2d,#14");
        assertDecoding(0x6f6c47b6, "sri v22.2d,v29.2d,#20");
        assertDecoding(0x6f5e4659, "sri v25.2d,v18.2d,#34");
        assertDecoding(0x6f55465b, "sri v27.2d,v18.2d,#43");
        assertDecoding(0x6f72471c, "sri v28.2d,v24.2d,#14");
        assertDecoding(0x6f7a475e, "sri v30.2d,v26.2d,#6");
        assertDecoding(0x6f0b46c6, "sri v6.16b,v22.16b,#5");
    }

    @Test
    public void test_srshl_simd() {
        assertDecoding(0x4e3b55d1, "srshl v17.16b, v14.16b, v27.16b");
        assertDecoding(0x4e2d5612, "srshl v18.16b, v16.16b, v13.16b");
        assertDecoding(0x4e2a56dc, "srshl v28.16b, v22.16b, v10.16b");
        assertDecoding(0x4eb0579d, "srshl v29.4s, v28.4s, v16.4s");
    }

    @Test
    public void test_srshr_fpu() {
        assertDecoding(0x5f592439, "srshr d25, d1, #39");
    }

    @Test
    public void test_srshr_simd() {
        assertDecoding(0x4f2124ad, "srshr v13.4s, v5.4s, #31");
        assertDecoding(0x4f282652, "srshr v18.4s, v18.4s, #24");
        assertDecoding(0x4f122785, "srshr v5.8h, v28.8h, #14");
        assertDecoding(0x0f082533, "srshr v19.8b,v9.8b,#8");
        assertDecoding(0x4f092535, "srshr v21.16b,v9.16b,#7");
    }

    @Test
    public void test_srsra_simd() {
        assertDecoding(0x0f3c34e5, "srsra v5.2s, v7.2s, #4");
        assertDecoding(0x4f0d3496, "srsra v22.16b,v4.16b,#3");
        assertDecoding(0x0f0d345f, "srsra v31.8b,v2.8b,#3");
    }

    @Test
    public void test_sshl_simd() {
        assertDecoding(0x4e7445f6, "sshl v22.8h, v15.8h, v20.8h");
    }

    @Test
    public void test_sshll_simd() {
        assertDecoding(0x0f38a695, "sshll v21.2d,v20.2s,#24");
    }

    @Test
    public void test_sshll2_simd() {
        assertDecoding(0x4f1ba439, "sshll2 v25.4s, v1.8h, #11");
        assertDecoding(0x4f38a68f, "sshll2 v15.2d,v20.4s,#24");

    }

    @Test
    public void test_sshr_simd() {
        assertDecoding(0x4f390400, "sshr v0.4s, v0.4s, #7");
        assertDecoding(0x0f0b0780, "sshr v0.8b, v28.8b, #5");
        assertDecoding(0x0f0a0500, "sshr v0.8b, v8.8b, #6");
        assertDecoding(0x4f3b0401, "sshr v1.4s, v0.4s, #5");
        assertDecoding(0x4f2104c1, "sshr v1.4s, v6.4s, #31");
        assertDecoding(0x4f210631, "sshr v17.4s, v17.4s, #31");
        assertDecoding(0x4f390442, "sshr v2.4s, v2.4s, #7");
        assertDecoding(0x4f7607fb, "sshr v27.2d, v31.2d, #10");
        assertDecoding(0x4f3b0443, "sshr v3.4s, v2.4s, #5");
        assertDecoding(0x4f210404, "sshr v4.4s, v0.4s, #31");
        assertDecoding(0x4f210466, "sshr v6.4s, v3.4s, #31");
    }

    @Test
    public void test_ssra_simd() {
        assertDecoding(0x0f0f15c9, "ssra v9.8b, v14.8b, #1");
    }

    @Test
    public void test_ssubl2_simd() {
        assertDecoding(0x4e7c2297, "ssubl2 v23.4s, v20.8h, v28.8h");
        assertDecoding(0x4e76209d, "ssubl2 v29.4s, v4.8h, v22.8h");
        assertDecoding(0x4eb42207, "ssubl2 v7.2d, v16.4s, v20.4s");
        assertDecoding(0x4eaa20e9, "ssubl2 v9.2d, v7.4s, v10.4s");
        assertDecoding(0x4e3421a9, "ssubl2 v9.8h, v13.16b, v20.16b");
    }

    @Test
    public void test_ssubw2_simd() {
        assertDecoding(0x4e693350, "ssubw2 v16.4s, v26.4s, v9.8h");
    }

    @Test
    public void test_ssubw_simd() {
        assertDecoding(0x0e71311b, "ssubw v27.4s, v8.4s, v17.4h");
    }

    @Test
    public void test_st1_simd() {
        assertDecoding(0x4c00a020, "st1 {v0.16b, v1.16b}, [x1]");
        assertDecoding(0x4c9fa020, "st1 {v0.16b, v1.16b}, [x1], #32");
        assertDecoding(0x4c00a280, "st1 {v0.16b, v1.16b}, [x20]");
        assertDecoding(0x4c00a100, "st1 {v0.16b, v1.16b}, [x8]");
        assertDecoding(0x4c0023e0, "st1 {v0.16b-v3.16b}, [sp]");
        assertDecoding(0x4c9f2000, "st1 {v0.16b-v3.16b}, [x0], #64");
        assertDecoding(0x4c007020, "st1 {v0.16b}, [x1]");
        assertDecoding(0x4c9f7020, "st1 {v0.16b}, [x1], #16");
        assertDecoding(0x4c007080, "st1 {v0.16b}, [x4]");
        assertDecoding(0x4c007c40, "st1 {v0.2d}, [x2]");
        assertDecoding(0x4c00a800, "st1 {v0.4s, v1.4s}, [x0]");
        assertDecoding(0x4c847800, "st1 {v0.4s}, [x0], x4");
        assertDecoding(0x4c9fa021, "st1 {v1.16b, v2.16b}, [x1], #32");
        assertDecoding(0x4c9f2021, "st1 {v1.16b-v4.16b}, [x1], #64");
        assertDecoding(0x4c9f7841, "st1 {v1.4s}, [x2], #16");
        assertDecoding(0x4c9f200c, "st1 {v12.16b-v15.16b}, [x0], #64");
        assertDecoding(0x0c8f2d8f, "st1 {v15.1d-v18.1d}, [x12], x15");
        assertDecoding(0x4c007090, "st1 {v16.16b}, [x4]");
        assertDecoding(0x0c946fb2, "st1 {v18.1d-v20.1d}, [x29], x20");
        assertDecoding(0x4c9f7033, "st1 {v19.16b}, [x1], #16");
        assertDecoding(0x4c00a022, "st1 {v2.16b, v3.16b}, [x1]");
        assertDecoding(0x0c9f7c02, "st1 {v2.1d}, [x0], #8");
        assertDecoding(0x4c9f6034, "st1 {v20.16b-v22.16b}, [x1], #48");
        assertDecoding(0x4c9f2014, "st1 {v20.16b-v23.16b}, [x0], #64");
        assertDecoding(0x4c00ac15, "st1 {v21.2d, v22.2d}, [x0]");
        assertDecoding(0x4d9911b5, "st1 {v21.b}[12], [x13], x25");
        assertDecoding(0x4c8663b6, "st1 {v22.16b-v24.16b}, [x29], x6");
        assertDecoding(0x4c00a037, "st1 {v23.16b, v24.16b}, [x1]");
        assertDecoding(0x0d8d0237, "st1 {v23.b}[0], [x17], x13");
        assertDecoding(0x0d008017, "st1 {v23.s}[0], [x0]");
        assertDecoding(0x0d8a0198, "st1 {v24.b}[0], [x12], x10");
        assertDecoding(0x4c007c1a, "st1 {v26.2d}, [x0]");
        assertDecoding(0x4c9f7c1a, "st1 {v26.2d}, [x0], #16");
        assertDecoding(0x4c8c7c1a, "st1 {v26.2d}, [x0], x12");
        assertDecoding(0x4c9fa023, "st1 {v3.16b, v4.16b}, [x1], #32");
        assertDecoding(0x4c007023, "st1 {v3.16b}, [x1]");
        assertDecoding(0x4c9f2004, "st1 {v4.16b-v7.16b}, [x0], #64");
        assertDecoding(0x4c8160e6, "st1 {v6.16b-v8.16b}, [x7], x1");
        assertDecoding(0x4c9f2008, "st1 {v8.16b-v11.16b}, [x0], #64");
        assertDecoding(0x4d9682a3, "st1 {v3.s}[2], [x21], x22");
    }

    @Test
    public void test_st2_simd() {
        assertDecoding(0x4c008c20, "st2 {v0.2d, v1.2d}, [x1]");
        assertDecoding(0x4c9f8c20, "st2 {v0.2d, v1.2d}, [x1], #32");
        assertDecoding(0x4c008c40, "st2 {v0.2d, v1.2d}, [x2]");
        assertDecoding(0x4c9f8c40, "st2 {v0.2d, v1.2d}, [x2], #32");
        assertDecoding(0x4c008880, "st2 {v0.4s, v1.4s}, [x4]");
        assertDecoding(0x4c0088a0, "st2 {v0.4s, v1.4s}, [x5]");
        assertDecoding(0x4c0088c0, "st2 {v0.4s, v1.4s}, [x6]");
        assertDecoding(0x0da400c0, "st2 {v0.b, v1.b}[0], [x6], x4");
        assertDecoding(0x4da840c1, "st2 {v1.h, v2.h}[4], [x6], x8");
        assertDecoding(0x4da89181, "st2 {v1.s, v2.s}[3], [x12], x8");
        assertDecoding(0x4da90d2a, "st2 {v10.b, v11.b}[11], [x9], x9");
        assertDecoding(0x4db5048e, "st2 {v14.b, v15.b}[9], [x4], x21");
        assertDecoding(0x4c008c30, "st2 {v16.2d, v17.2d}, [x1]");
        assertDecoding(0x0db21890, "st2 {v16.b, v17.b}[6], [x4], x18");
        assertDecoding(0x4c9f8c32, "st2 {v18.2d, v19.2d}, [x1], #32");
        assertDecoding(0x4c8c8f53, "st2 {v19.2d, v20.2d}, [x26], x12");
        assertDecoding(0x4c008c22, "st2 {v2.2d, v3.2d}, [x1]");
        assertDecoding(0x4c008c34, "st2 {v20.2d, v21.2d}, [x1]");
        assertDecoding(0x4dbe4875, "st2 {v21.h, v22.h}[5], [x3], x30");
        assertDecoding(0x4c9f8c36, "st2 {v22.2d, v23.2d}, [x1], #32");
        assertDecoding(0x0dbd42bd, "st2 {v29.h, v30.h}[0], [x21], x29");
        assertDecoding(0x0dba18be, "st2 {v30.b, v31.b}[6], [x5], x26");
        assertDecoding(0x4c008c24, "st2 {v4.2d, v5.2d}, [x1]");
        assertDecoding(0x4c9f8c24, "st2 {v4.2d, v5.2d}, [x1], #32");
        assertDecoding(0x4c008c44, "st2 {v4.2d, v5.2d}, [x2]");
        assertDecoding(0x4db28004, "st2 {v4.s, v5.s}[2], [x0], x18");
        assertDecoding(0x0da50225, "st2 {v5.b, v6.b}[0], [x17], x5");
        assertDecoding(0x4c9f8c26, "st2 {v6.2d, v7.2d}, [x1], #32");
        assertDecoding(0x4c9f8c46, "st2 {v6.2d, v7.2d}, [x2], #32");
    }

    @Test
    public void test_st3_simd() {
        assertDecoding(0x0c90430a, "st3 {v10.8b-v12.8b}, [x24], x16");
        assertDecoding(0x0d84698a, "st3 {v10.h-v12.h}[1], [x12], x4");
        assertDecoding(0x4c004c4c, "st3 {v12.2d-v14.2d}, [x2]");
        assertDecoding(0x4c9b4bf0, "st3 {v16.4s-v18.4s}, [sp], x27");
        assertDecoding(0x0d843bb0, "st3 {v16.b-v18.b}[6], [x29], x4");
        assertDecoding(0x0d9d2ae2, "st3 {v2.b-v4.b}[2], [x23], x29");
        assertDecoding(0x0d8f377a, "st3 {v26.b-v28.b}[5], [x27], x15");
        assertDecoding(0x4d002a7c, "st3 {v28.b-v30.b}[10], [x19]");
        assertDecoding(0x4d8870dc, "st3 {v28.h-v30.h}[6], [x6], x8");
        assertDecoding(0x0c914324, "st3 {v4.8b-v6.8b}, [x25], x17");
    }

    @Test
    public void test_st4_simd() {
        assertDecoding(0x4da72541, "st4 {v1.b-v4.b}[9], [x10], x7");
        assertDecoding(0x0db7b08b, "st4 {v11.s-v14.s}[1], [x4], x23");
        assertDecoding(0x0db427cc, "st4 {v12.b-v15.b}[1], [x30], x20");
        assertDecoding(0x0c800550, "st4 {v16.4h-v19.4h}, [x10], x0");
        assertDecoding(0x0c950731, "st4 {v17.4h-v20.4h}, [x25], x21");
        assertDecoding(0x4d207972, "st4 {v18.h-v21.h}[7], [x11]");
        assertDecoding(0x0db06253, "st4 {v19.h-v22.h}[0], [x18], x16");
        assertDecoding(0x0dbfa013, "st4 {v19.s-v22.s}[0], [x0], #16");
        assertDecoding(0x0db624b6, "st4 {v22.b-v25.b}[1], [x5], x22");
        assertDecoding(0x4dbf203e, "st4 {v30.b, v31.b, v0.b, v1.b}[8], [x1], #4");
        assertDecoding(0x4c900749, "st4 {v9.8h-v12.8h}, [x26], x16");
    }


    @Test
    public void test_stadd() {
        assertDecoding(0xf83e037f, "stadd x30, [x27]");
    }

    @Test
    public void test_stllr() {
        assertDecoding(0x888d5a33, "stllr w19, [x17]");
        assertDecoding(0xc891560b, "stllr x11, [x16]");
        assertDecoding(0xc88755df, "stllr xzr,[x14]");
    }

    @Test
    public void test_stllrb() {
        assertDecoding(0x089f7f61, "stllrb w1,[x27]");
        assertDecoding(0x08990b30, "stllrb w16, [x25]");
    }

    @Test
    public void test_stllrh() {
        assertDecoding(0x488e1a2a, "stllrh w10, [x17]");
        assertDecoding(0x489c1edf, "stllrh wzr, [x22]");
        assertDecoding(0x489f7ff9, "stllrh w25,[sp]");
    }

    @Test
    public void test_stlr() {
        assertDecoding(0x888ff9b0, "stlr w16, [x13]");
        assertDecoding(0x889ffe7f, "stlr wzr, [x19]");
        assertDecoding(0xc89ffe93, "stlr x19, [x20]");
        assertDecoding(0xc89fff3f, "stlr xzr, [x25]");
    }

    @Test
    public void test_stlrb() {
        assertDecoding(0x0898d334, "stlrb w20, [x25]");
        assertDecoding(0x089ffc1f, "stlrb wzr, [x0]");
    }

    @Test
    public void test_stlrh() {
        assertDecoding(0x489ca6ca, "stlrh w10, [x22]");
    }

    @Test
    public void test_stlxp() {
        assertDecoding(0x882acf41, "stlxp w10, w1, w19, [x26]");
        assertDecoding(0x8827ff24, "stlxp w7, w4, wzr, [x25]");
    }

    @Test
    public void test_stlxr() {
        assertDecoding(0x8801fe62, "stlxr w1, w2, [x19]");
        assertDecoding(0xc81f8a29, "stlxr wzr, x9, [x17]");
    }

    @Test
    public void test_stlxrb() {
        assertDecoding(0x080f9fb0, "stlxrb w15, w16, [x29]");
    }

    @Test
    public void test_stlxrh() {
        assertDecoding(0x480cd372, "stlxrh w12, w18, [x27]");
    }

    @Test
    public void test_stnp() {
        assertDecoding(0x28248b8a, "stnp w10, w2, [x28, #-220]");
        assertDecoding(0x28002828, "stnp w8, w10, [x1]");
        assertDecoding(0x2807b4bf, "stnp wzr, w13, [x5, #60]");
        assertDecoding(0xa81a664b, "stnp x11, x25, [x18, #416]");
    }

    @Test
    public void test_stnp_fpu() {
        assertDecoding(0x6c13cf4b, "stnp d11, d19, [x26, #312]");
        assertDecoding(0x6c006c6c, "stnp d12, d27, [x3]");
        assertDecoding(0x6c1d248e, "stnp d14, d9, [x4, #464]");
        assertDecoding(0xac3434a1, "stnp q1, q13, [x5, #-384]");
        assertDecoding(0x2c002c2c, "stnp s12, s11, [x1]");
        assertDecoding(0x2c38764d, "stnp s13, s29, [x18, #-64]");
    }

    @Test
    public void test_stp() {
        assertDecoding(0x29042841, "stp w1, w10, [x2, #32]");
        assertDecoding(0x29002c01, "stp w1, w11, [x0]");
        assertDecoding(0x29152441, "stp w1, w9, [x2, #168]");
        assertDecoding(0x293f7c01, "stp w1, wzr, [x0, #-8]");
        assertDecoding(0x29007c01, "stp w1, wzr, [x0]");
        assertDecoding(0x293fa83f, "stp wzr, w10, [x1, #-4]");
        assertDecoding(0x288a3a7f, "stp wzr, w14, [x19], #80");
        assertDecoding(0x29057fff, "stp wzr, wzr, [sp, #40]");
        assertDecoding(0x293e7c1f, "stp wzr, wzr, [x0, #-16]");
        assertDecoding(0xa9004c01, "stp x1, x19, [x0]");
        assertDecoding(0xa90bce61, "stp x1, x19, [x19, #184]");
        assertDecoding(0xa9078be1, "stp x1, x2, [sp, #120]");
        assertDecoding(0xa9000be1, "stp x1, x2, [sp]");
        assertDecoding(0xa9bf0a61, "stp x1, x2, [x19, #-16]!");
        assertDecoding(0xa8812c0a, "stp x10, x11, [x0], #16");
        assertDecoding(0xa901fff5, "stp x21, xzr, [sp, #24]");
        assertDecoding(0xa9007ff5, "stp x21, xzr, [sp]");
        assertDecoding(0xa90c87bf, "stp xzr, x1, [x29, #200]");
        assertDecoding(0xa9157d1f, "stp xzr, xzr, [x8, #336]");
    }

    @Test
    public void test_stp_fpu() {
        assertDecoding(0x6d0e8981, "stp d1, d2, [x12, #232]");
        assertDecoding(0x6c97d441, "stp d1, d21, [x2], #376");
        assertDecoding(0x6d326fe1, "stp d1, d27, [sp, #-224]");
        assertDecoding(0x6d001001, "stp d1, d4, [x0]");
        assertDecoding(0xac85bbca, "stp q10, q14, [x30], #176");
        assertDecoding(0xad0c710b, "stp q11, q28, [x8, #384]");
        assertDecoding(0xadb8fc2b, "stp q11, q31, [x1, #-240]!");
        assertDecoding(0x2db1f2c1, "stp s1, s28, [x22, #-116]!");
        assertDecoding(0x2d0a13a1, "stp s1, s4, [x29, #80]");
        assertDecoding(0x2c9d8aea, "stp s10, s2, [x23], #236");
    }

    @Test
    public void test_str() {
        assertDecoding(0x3d17a941, "str b1, [x10, #1514]");
        assertDecoding(0x3d000281, "str b1, [x20]");
        assertDecoding(0x3c0cbdca, "str b10, [x14, #203]!");
        assertDecoding(0x3c19778a, "str b10, [x28], #-105");
        assertDecoding(0x3c207962, "str b2, [x11, x0, lsl #0]");
        assertDecoding(0x3c26cb86, "str b6, [x28, w6, sxtw]");
        assertDecoding(0x7c3d5b4b, "str h11, [x26, w29, uxtw #1]");
        assertDecoding(0x7d324fbb, "str h27, [x29, #6438]");
        assertDecoding(0x7c00c59f, "str h31, [x12], #12");
        assertDecoding(0x7d257c5f, "str h31, [x2, #4798]");
        assertDecoding(0xb82578e1, "str w1, [x7, x5, lsl #2]");
        assertDecoding(0xb82678e1, "str w1, [x7, x6, lsl #2]");
        assertDecoding(0xb82668e1, "str w1, [x7, x6]");
        assertDecoding(0xb90000e1, "str w1, [x7]");
        assertDecoding(0xb80044e1, "str w1, [x7], #4");
        assertDecoding(0xb81fcd01, "str w1, [x8, #-4]!");
        assertDecoding(0xb9101901, "str w1, [x8, #4120]");
        assertDecoding(0xb90003ea, "str w10, [sp]");
        assertDecoding(0xb9006bff, "str wzr, [sp, #104]");
        assertDecoding(0xf82a79a1, "str x1, [x13, x10, lsl #3]");
        assertDecoding(0xf82269a1, "str x1, [x13, x2]");
        assertDecoding(0xf90001a1, "str x1, [x13]");
        assertDecoding(0xf907b9c1, "str x1, [x14, #3952]");
        assertDecoding(0xf81f0de1, "str x1, [x15, #-16]!");
        assertDecoding(0xf90b91e1, "str x1, [x15, #5920]");
        assertDecoding(0xf900c07f, "str xzr, [x3, #384]");
        assertDecoding(0xf835d93f, "str xzr, [x9, w21, sxtw #3]");
        assertDecoding(0xf900013f, "str xzr, [x9]");
        assertDecoding(0xbc04af23, "str S3,[X25,#74]!");
    }

    @Test
    public void test_str_fpu() {
        assertDecoding(0xfd0057b5, "str d21, [x29, #168]");
        assertDecoding(0x3c9eb53b, "str q27, [x9], #-21");
        assertDecoding(0xbd3acd57, "str s23, [x10, #15052]");
    }

    @Test
    public void test_strb() {
        assertDecoding(0x39023a61, "strb w1, [x19, #142]");
        assertDecoding(0x39000261, "strb w1, [x19]");
        assertDecoding(0x38001661, "strb w1, [x19], #1");
        assertDecoding(0x381f0c41, "strb w1, [x2, #-16]!");
        assertDecoding(0x3820ca81, "strb w1, [x20, w0, sxtw]");
        assertDecoding(0x38204a81, "strb w1, [x20, w0, uxtw]");
        assertDecoding(0x39003fff, "strb wzr, [sp, #15]");
    }

    @Test
    public void test_strh() {
        assertDecoding(0x790001cf, "strh w15, [x14]");
        assertDecoding(0x79002a6f, "strh w15, [x19, #20]");
        assertDecoding(0x78277950, "strh w16, [x10, x7, lsl #1]");
        assertDecoding(0x781a4e70, "strh w16, [x19, #-92]!");
        assertDecoding(0x783b5871, "strh w17, [x3, w27, uxtw #1]");
        assertDecoding(0x7900967f, "strh wzr, [x19, #74]");
    }

    @Test
    public void test_sttr() {
        assertDecoding(0xb80d4a6c, "sttr w12, [x19, #212]");
        assertDecoding(0xf800eaab, "sttr x11, [x21, #14]");
        assertDecoding(0xf81a197f, "sttr xzr, [x11, #-95]");
    }

    @Test
    public void test_sttrb() {
        assertDecoding(0x381a0beb, "sttrb w11, [sp, #-96]");
        assertDecoding(0x381728a4, "sttrb w4, [x5, #-142]");
    }

    @Test
    public void test_sttrh() {
        assertDecoding(0x78007878, "sttrh w24, [x3, #7]");
        assertDecoding(0x7800089a, "sttrh w26, [x4]");
        assertDecoding(0x78131b05, "sttrh w5, [x24, #-207]");
    }

    @Test
    public void test_stur() {
        assertDecoding(0x3c1051f2, "stur b18, [x15, #-251]");
        assertDecoding(0x7c1131ac, "stur h12, [x13, #-237]");
        assertDecoding(0xb8091283, "stur w3, [x20, #145]");
        assertDecoding(0xb813103f, "stur wzr, [x1, #-207]");
        assertDecoding(0xf81802b3, "stur x19, [x21, #-128]");
        assertDecoding(0xf800429f, "stur xzr, [x20, #4]");
    }

    @Test
    public void test_stur_fpu() {
        assertDecoding(0xfc0f51b7, "stur d23, [x13, #245]");
        assertDecoding(0x3c8c537d, "stur q29, [x27, #197]");
        assertDecoding(0xbc1783d1, "stur s17, [x30, #-136]");
    }

    @Test
    public void test_sturb() {
        assertDecoding(0x381ff06d, "sturb w13, [x3, #-1]");
        assertDecoding(0x381c831f, "sturb wzr, [x24, #-56]");
    }

    @Test
    public void test_sturh() {
        assertDecoding(0x780010cc, "sturh w12, [x6, #1]");
        assertDecoding(0x78160350, "sturh w16, [x26, #-160]");
        assertDecoding(0x780dd27f, "sturh wzr, [x19, #221]");
    }

    @Test
    public void test_stxp() {
        assertDecoding(0x882a4439, "stxp w10, w25, w17, [x1]");
        assertDecoding(0x882f7c97, "stxp w15, w23, wzr, [x4]");
    }

    @Test
    public void test_stxr() {
        assertDecoding(0x88133c51, "stxr w19, w17, [x2]");
        assertDecoding(0x881f0b34, "stxr wzr, w20, [x25]");
    }

    @Test
    public void test_stxrb() {
        assertDecoding(0x08017c44, "stxrb w1, w4, [x2]");
    }

    @Test
    public void test_stxrh() {
        assertDecoding(0x480a64dd, "stxrh w10, w29, [x6]");
        assertDecoding(0x480e691f, "stxrh w14, wzr, [x8]");
        assertDecoding(0x481f27ca, "stxrh wzr, w10, [x30]");
    }


    @Test
    public void test_sub() {
        assertDecoding(0x4b2f62c1, "sub w1,w22,w15,uxtx");
        assertDecoding(0x4b3343c9, "sub w9,w30,w19,uxtw");
        assertDecoding(0xcb2d47f7, "sub x23,sp,w13,uxtw #1");
    }

    @Test
    public void test_sub_fpu() {
        assertDecoding(0x7efa8786, "sub d6, d28, d26");
        assertDecoding(0x7efa84a7, "sub d7, d5, d26");
    }

    @Test
    public void test_sub_simd() {
        assertDecoding(0x6ef1859e, "sub v30.2d, v12.2d, v17.2d");
        assertDecoding(0x2ebb87a1, "sub v1.2s, v29.2s, v27.2s");
        assertDecoding(0x6ea0879c, "sub v28.4s, v28.4s, v0.4s");
        assertDecoding(0x2e36840f, "sub v15.8b, v0.8b, v22.8b");
        assertDecoding(0x6e268442, "sub v2.16b, v2.16b, v6.16b");
        assertDecoding(0x6e6484bc, "sub v28.8h, v5.8h, v4.8h");
    }

    @Test
    public void test_subhn_simd() {
        assertDecoding(0x0ea460f6, "subhn v22.2s, v7.2d, v4.2d");
        assertDecoding(0x0e3c6247, "subhn v7.8b, v18.8h, v28.8h");
    }

    @Test
    public void test_subs() {
        assertDecoding(0x71000661, "subs w1, w19, #0x1");
        assertDecoding(0x6b010261, "subs w1, w19, w1");
        assertDecoding(0x6b810441, "subs w1, w2, w1, asr #1");
        assertDecoding(0x6b196c41, "subs w1, w2, w25, lsl #27");
        assertDecoding(0x7177aea1, "subs w1, w21, #0xdeb, lsl #12");
        assertDecoding(0x714ce0c1, "subs w1, w6, #0x338, lsl #12");
        assertDecoding(0x710ca16a, "subs w10, w11, #0x328");
        assertDecoding(0x6b2f11ea, "subs w10, w15, w15, uxtb #4");
        assertDecoding(0x6b006b6b, "subs w11, w27, w0, lsl #26");
        assertDecoding(0x6b015fac, "subs w12, w29, w1, lsl #23");
        assertDecoding(0x6b31c22e, "subs w14, w17, w17, sxtw");
        assertDecoding(0x6b3ba30e, "subs w14, w24, w27, sxth");
        assertDecoding(0x6b25ed6f, "subs w15, w11, w5, sxtx #3");
        assertDecoding(0x6b8d0ecf, "subs w15, w22, w13, asr #3");
        assertDecoding(0x6b9000af, "subs w15, w5, w16, asr #0");
        assertDecoding(0x6b5f46dd, "subs w29, w22, wzr, lsr #17");
        assertDecoding(0x6b524109, "subs w9, w8, w18, lsr #16");
        assertDecoding(0xeb19c041, "subs x1, x2, x25, lsl #48");
        assertDecoding(0xf161ffef, "subs x15, sp, #0x87f, lsl #12");
        assertDecoding(0xeb5f74f1, "subs x17, x7, xzr, lsr #29");
    }

    @Test
    public void test_suqadd_fpu() {
        assertDecoding(0x5ea03871, "suqadd s17,s3");
    }

    @Test
    public void test_svc() {
        assertDecoding(0xd4000001, "svc #0x0");
    }

    @Test
    public void test_swp() {
        assertDecoding(0xb82e8089, "swp w14, w9, [x4]");
    }

    @Test
    public void test_swpa() {
        assertDecoding(0xf8b68149, "swpa x22, x9, [x10]");
    }

    @Test
    public void test_swpl() {
        assertDecoding(0xb87a80b9, "swpl w26, w25, [x5]");
    }

    @Test
    public void test_sxtb() {
        assertDecoding(0x13001e7b, "sxtb w27, w19");
        assertDecoding(0x93401e76, "sxtb x22, w19");
    }

    @Test
    public void test_sxth() {
        assertDecoding(0x13003dcd, "sxth w13, w14");
        assertDecoding(0x13003f59, "sxth w25, w26");
        assertDecoding(0x93403e75, "sxth x21, w19");
        assertDecoding(0x93403e69, "sxth x9, w19");
    }

    @Test
    public void test_sxtl2_simd() {
        assertDecoding(0x4f20a440, "sxtl2 v0.2d, v2.4s");
        assertDecoding(0x4f10a400, "sxtl2 v0.4s, v0.8h");
        assertDecoding(0x4f08a400, "sxtl2 v0.8h, v0.16b");
        assertDecoding(0x4f10a421, "sxtl2 v1.4s, v1.8h");
    }

    @Test
    public void test_sxtl_simd() {
        assertDecoding(0x0f20a401, "sxtl v1.2d, v0.2s");
        assertDecoding(0x0f20a441, "sxtl v1.2d, v2.2s");
        assertDecoding(0x0f08a401, "sxtl v1.8h, v0.8b");
        assertDecoding(0x0f10a423, "sxtl v3.4s, v1.4h");
    }

    @Test
    public void test_sxtw() {
        assertDecoding(0x93407ead, "sxtw x13, w21");
        assertDecoding(0x93407f97, "sxtw x23, w28");
        assertDecoding(0x93407ce3, "sxtw x3, w7");
    }

    @Test
    public void test_sys() {
        assertDecoding(0xd508de78, "sys #0, C13, C14, #3, x24");
        assertDecoding(0xd5082c13, "sys #0, C2, C12, #0, x19");
        assertDecoding(0xd5085679, "sys #0, C5, C6, #3, x25");
        assertDecoding(0xd50d074d, "sys #5, C0, C7, #2, x13");
        assertDecoding(0xd50dc833, "sys #5, C12, C8, #1, x19");
        assertDecoding(0xd50e2362, "sys #6, C2, C3, #3, x2");
        assertDecoding(0xd50f41d8, "sys #7, C4, C1, #6, x24");
        assertDecoding(0xd50f609f, "sys #7, c6, c0, #4");
        assertDecoding(0xd5098364, "sys #1,c8,c3,#3,x4");
        assertDecoding(0xd509874a, "sys #1,c8,c7,#2,x10");
        assertDecoding(0xd50a8569, "sys #2,c8,c5,#3,x9");
        assertDecoding(0xd50c8a4d, "sys #4,c8,c10,#2,x13");
        assertDecoding(0xd50f83fa, "sys #7,c8,c3,#7,x26");
        assertDecoding(0xd50f84a0, "sys #7,c8,c4,#5,x0");
        assertDecoding(0xd50f85c2, "sys #7,c8,c5,#6,x2");
    }

    @Test
    public void test_sysl() {
        assertDecoding(0xd52fb16b, "sysl x11, #7, C11, C1, #3");
        assertDecoding(0xd52ba0b2, "sysl x18, #3, C10, C0, #5");
        assertDecoding(0xd5286b95, "sysl x21, #0, C6, C11, #4");
        assertDecoding(0xd52c6b75, "sysl x21, #4, C6, C11, #3");
        assertDecoding(0xd52bad3b, "sysl x27, #3, C10, C13, #1");
        assertDecoding(0xd52ad85d, "sysl x29, #2, C13, C8, #2");
        assertDecoding(0xd52e2fff, "sysl xzr, #6, C2, C15, #7");
    }


    @Test
    public void test_tbl_simd() {
        assertDecoding(0x4e09026a, "tbl v10.16b, {v19.16b}, v9.16b");
        assertDecoding(0x4e0a036d, "tbl v13.16b, {v27.16b}, v10.16b");
        assertDecoding(0x0e0f0096, "tbl v22.8b, {v4.16b}, v15.8b");
    }

    @Test
    public void test_tbnz_with_label() {
        assertDecodingWithPc(0xffaf4, 0x370000e0, "tbnz w0, #0, 0xffb10");
        assertDecodingWithPc(0xffd0, 0x37f80100, "tbnz w0, #31, 0xfff0");
        assertDecodingWithPc(0x4ae34, 0x37f800b4, "tbnz w20, #31, 0x4ae48");
        assertDecodingWithPc(0x572b8, 0x37181d75, "tbnz w21, #3, 0x57664");
        assertDecodingWithPc(0x657d4, 0x37380069, "tbnz w9, #7, 0x657e0");
        assertDecodingWithPc(0x10ce38, 0x37a236ff, "tbnz wzr, #20, 0x111514");
        assertDecodingWithPc(0x102fa8, 0xb71b5ca2, "tbnz x2, #35, 0x109b3c");
        assertDecodingWithPc(0xf9748, 0xb71e289f, "tbnz xzr, #35, 0xf5c58");
        assertDecodingWithPc(0x1067e0, 0xb7f0ed3f, "tbnz xzr, #62, 0x108584");
    }

    @Test
    public void test_tbx_simd() {
        assertDecoding(0x0e065111, "tbx v17.8b, {v8.16b-v10.16b}, v6.8b");
        assertDecoding(0x4e1e125d, "tbx v29.16b, {v18.16b}, v30.16b");
        assertDecoding(0x0e1b7288, "tbx v8.8b, {v20.16b-v23.16b}, v27.8b");
    }

    @Test
    public void test_tbz_with_label() {
        assertDecodingWithPc(0x1f0c0, 0x3657e761, "tbz w1, #10, 0x1edac");
        assertDecodingWithPc(0x12b570, 0x36fffa61, "tbz w1, #31, 0x12b4bc");
        assertDecodingWithPc(0x10c06c, 0x367d3f9f, "tbz wzr, #15, 0x10685c");
        assertDecodingWithPc(0xd8d8, 0xb6f00041, "tbz x1, #62, 0xd8e0");
        assertDecodingWithPc(0x4e6b4, 0xb69808c4, "tbz x4, #51, 0x4e7cc");
        assertDecodingWithPc(0xfa800, 0xb655551f, "tbz xzr, #42, 0xf52a0");
    }

    @Test
    public void test_tlbi() {
        assertDecoding(0xd5088753, "tlbi aside1,x19");
        assertDecoding(0xd50c8033, "tlbi ipas2e1is,x19");
        assertDecoding(0xd50c803b, "tlbi ipas2e1is,x27");
        assertDecoding(0xd50c803c, "tlbi ipas2e1is,x28");
        assertDecoding(0xd50c8028, "tlbi ipas2e1is,x8");
        assertDecoding(0xd508837a, "tlbi vaae1is,x26");
        assertDecoding(0xd5088334, "tlbi vae1is,x20");
        assertDecoding(0xd50c872b, "tlbi vae2,x11");
        assertDecoding(0xd50c8328, "tlbi vae2is,x8");
        assertDecoding(0xd50e872a, "tlbi vae3,x10");
        assertDecoding(0xd50e8335, "tlbi vae3is,x21");
        assertDecoding(0xd50887b8, "tlbi vale1,x24");
        assertDecoding(0xd50887a8, "tlbi vale1,x8");
        assertDecoding(0xd50c83aa, "tlbi vale2is,x10");
        assertDecoding(0xd50c8416, "tlbi ipas2e1os,x22");
        assertDecoding(0xd50c845a, "tlbi ripas2e1,x26");
        assertDecoding(0xd50c847d, "tlbi ripas2e1os,x29");
        assertDecoding(0xd50c80d5, "tlbi ripas2le1is,x21");
        assertDecoding(0xd508826a, "tlbi rvaae1is,x10");
        assertDecoding(0xd50886f1, "tlbi rvaale1,x17");
        assertDecoding(0xd50e8529, "tlbi rvae3os,x9");
        assertDecoding(0xd50885ae, "tlbi rvale1os,x14");
        assertDecoding(0xd50e82bd, "tlbi rvale3is,x29");
        assertDecoding(0xd508817e, "tlbi vaae1os,x30");
        assertDecoding(0xd50881fb, "tlbi vaale1os,x27");
        assertDecoding(0xd50c82ab, "tlbi rvale2is,x11");
    }

    @Ignore("Objdump doesn't output the register")
    @Test
    public void test_tlbi_no_register() {
        assertDecoding(0xd50c8799, "tlbi alle1");
        assertDecoding(0xd50c8389, "tlbi alle1is");
        assertDecoding(0xd50c8199, "tlbi alle1os");
        assertDecoding(0xd50c8195, "tlbi alle1os");
        assertDecoding(0xd50c8708, "tlbi alle2");
        assertDecoding(0xd50c810d, "tlbi alle2os");
        assertDecoding(0xd50c8311, "tlbi alle2is");
        assertDecoding(0xd50e8703, "tlbi alle3");
        assertDecoding(0xd50e8119, "tlbi alle3os");
        assertDecoding(0xd50e8319, "tlbi alle3is");
        assertDecoding(0xd50e831d, "tlbi alle3is");
        assertDecoding(0xd5088701, "tlbi vmalle1");
        assertDecoding(0xd50c87d0, "tlbi vmalls12e1");
        assertDecoding(0xd50c87d4, "tlbi vmalls12e1");
    }

    @Ignore("Objdump knowns about them but the docu for 8.3 doesn't mention them")
    @Test
    public void test_tlbi_unknown_ops() {
        assertDecoding(0xd50c8498, "tlbi ipas2le1os,x24");
        assertDecoding(0xd50c8040, "tlbi ripas2e1is,x0");
        assertDecoding(0xd5088668, "tlbi rvaae1,x8");
        assertDecoding(0xd5088273, "tlbi rvaae1is,x19");
        assertDecoding(0xd5088265, "tlbi rvaae1is,x5");
        assertDecoding(0xd508856a, "tlbi rvaae1os,x10");
        assertDecoding(0xd5088573, "tlbi rvaae1os,x19");
        assertDecoding(0xd5088633, "tlbi rvae1,x19");
        assertDecoding(0xd5088528, "tlbi rvae1os,x8");
        assertDecoding(0xd50c823b, "tlbi rvae2is,x27");
        assertDecoding(0xd50c8226, "tlbi rvae2is,x6");
        assertDecoding(0xd50e862c, "tlbi rvae3,x12");
        assertDecoding(0xd50886af, "tlbi rvale1,x15");
        assertDecoding(0xd50882a2, "tlbi rvale1is,x2");
        assertDecoding(0xd50885b7, "tlbi rvale1os,x23");
        assertDecoding(0xd50885a9, "tlbi rvale1os,x9");
        assertDecoding(0xd50c82bf, "tlbi rvale2is,xzr");
        assertDecoding(0xd50e86ac, "tlbi rvale3,x12");
        assertDecoding(0xd50e86bc, "tlbi rvale3,x28");
        assertDecoding(0xd50c8130, "tlbi vae2os,x16");
        assertDecoding(0xd50c813c, "tlbi vae2os,x28");
        assertDecoding(0xd50c81b7, "tlbi vale2os,x23");
        assertDecoding(0xd50e81ae, "tlbi vale3os,x14");
    }

    @Test
    public void test_trn1_simd() {
        assertDecoding(0x0e072bee, "trn1 v14.8b, v31.8b, v7.8b");
        assertDecoding(0x4ecf2b9f, "trn1 v31.2d, v28.2d, v15.2d");
        assertDecoding(0x4e532bc4, "trn1 v4.8h, v30.8h, v19.8h");
    }

    @Test
    public void test_trn2_simd() {
        assertDecoding(0x4e0c68ca, "trn2 v10.16b, v6.16b, v12.16b");
        assertDecoding(0x0e476b92, "trn2 v18.4h, v28.4h, v7.4h");
    }

    @Test
    public void test_tst() {
        assertDecoding(0x7200001f, "tst w0, #0x1");
        assertDecoding(0x721c001f, "tst w0, #0x10");
        assertDecoding(0x7218001f, "tst w0, #0x100");
        assertDecoding(0x7214001f, "tst w0, #0x1000");
        assertDecoding(0x7210001f, "tst w0, #0x10000");
        assertDecoding(0x720c001f, "tst w0, #0x100000");
        assertDecoding(0x7208001f, "tst w0, #0x1000000");
        assertDecoding(0x7206081f, "tst w0, #0x1c000000");
        assertDecoding(0x721f001f, "tst w0, #0x2");
        assertDecoding(0x721b001f, "tst w0, #0x20");
        assertDecoding(0x7217001f, "tst w0, #0x200");
        assertDecoding(0x7213001f, "tst w0, #0x2000");
        assertDecoding(0x720f001f, "tst w0, #0x20000");
        assertDecoding(0x720b001f, "tst w0, #0x200000");
        assertDecoding(0x7207001f, "tst w0, #0x2000000");
        assertDecoding(0x7200041f, "tst w0, #0x3");
        assertDecoding(0x721c041f, "tst w0, #0x30");
        assertDecoding(0x7218041f, "tst w0, #0x300");
        assertDecoding(0x7214041f, "tst w0, #0x3000");
        assertDecoding(0x7210041f, "tst w0, #0x30000");
        assertDecoding(0x720c041f, "tst w0, #0x300000");
        assertDecoding(0x7200741f, "tst w0, #0x3fffffff");
        assertDecoding(0x721e001f, "tst w0, #0x4");
        assertDecoding(0x721a001f, "tst w0, #0x40");
        assertDecoding(0x7216001f, "tst w0, #0x400");
        assertDecoding(0x7212001f, "tst w0, #0x4000");
        assertDecoding(0x720e001f, "tst w0, #0x40000");
        assertDecoding(0x720a001f, "tst w0, #0x400000");
        assertDecoding(0x721f041f, "tst w0, #0x6");
        assertDecoding(0x721b041f, "tst w0, #0x60");
        assertDecoding(0x7217041f, "tst w0, #0x600");
        assertDecoding(0x7213041f, "tst w0, #0x6000");
        assertDecoding(0x720f041f, "tst w0, #0x60000");
        assertDecoding(0x720b041f, "tst w0, #0x600000");
        assertDecoding(0x721d001f, "tst w0, #0x8");
        assertDecoding(0x7219001f, "tst w0, #0x80");
        assertDecoding(0x7215001f, "tst w0, #0x800");
        assertDecoding(0x720d001f, "tst w0, #0x80000");
        assertDecoding(0x7209001f, "tst w0, #0x800000");
        assertDecoding(0x7201001f, "tst w0, #0x80000000");
        assertDecoding(0x7210781f, "tst w0, #0xffff7fff");
        assertDecoding(0x7211781f, "tst w0, #0xffffbfff");
        assertDecoding(0x721e781f, "tst w0, #0xfffffffd");
        assertDecoding(0x721f781f, "tst w0, #0xfffffffe");
        assertDecoding(0x6a01001f, "tst w0, w1");
        assertDecoding(0x6a41041f, "tst w0, w1, lsr #1");
        assertDecoding(0x6a41341f, "tst w0, w1, lsr #13");
        assertDecoding(0x6a410c1f, "tst w0, w1, lsr #3");
        assertDecoding(0x6a417c1f, "tst w0, w1, lsr #31");
        assertDecoding(0x6a411c1f, "tst w0, w1, lsr #7");
        assertDecoding(0x6a1b017f, "tst w11, w27");
        assertDecoding(0x7217059f, "tst w12, #0x600");
        assertDecoding(0x72081d9f, "tst w12, #0xff000000");
        assertDecoding(0x72103d9f, "tst w12, #0xffff0000");
        assertDecoding(0x7216799f, "tst w12, #0xfffffdff");
        assertDecoding(0x7217799f, "tst w12, #0xfffffeff");
        assertDecoding(0xf243003f, "tst x1, #0x2000000000000000");
        assertDecoding(0xf240f43f, "tst x1, #0x3fffffffffffffff");
        assertDecoding(0xea16027f, "tst x19, x22");
    }

    @Test
    public void test_uaba_simd() {
        assertDecoding(0x2e217f24, "uaba v4.8b, v25.8b, v1.8b");
    }

    @Test
    public void test_uabd_simd() {
        assertDecoding(0x6eac7577, "uabd v23.4s, v11.4s, v12.4s");
        assertDecoding(0x2ebc74bd, "uabd v29.2s, v5.2s, v28.2s");
    }

    @Test
    public void test_uabdl_simd() {
        assertDecoding(0x2e7073ff, "uabdl v31.4s, v31.4h, v16.4h");
    }

    @Test
    public void test_uadalp_simd() {
        assertDecoding(0x6e2068c4, "uadalp v4.8h, v6.16b");
        assertDecoding(0x6e2068e5, "uadalp v5.8h, v7.16b");
        assertDecoding(0x2ea06829, "uadalp v9.1d,v1.2s");
        assertDecoding(0x2e606992, "uadalp v18.2s,v12.4h");
        assertDecoding(0x2e606828, "uadalp v8.2s,v1.4h");
    }

    @Test
    public void test_uaddl2_simd() {
        assertDecoding(0x6e6e006e, "uaddl2 v14.4s, v3.8h, v14.8h");
    }

    @Test
    public void test_uaddl_simd() {
        assertDecoding(0x2e2e002e, "uaddl v14.8h, v1.8b, v14.8b");
    }

    @Test
    public void test_uaddlp_simd() {
        assertDecoding(0x6ea02884, "uaddlp v4.2d, v4.4s");
        assertDecoding(0x6e602884, "uaddlp v4.4s, v4.8h");
        assertDecoding(0x2e602901, "uaddlp v1.2s,v8.4h");
        assertDecoding(0x2e2029c1, "uaddlp v1.4h,v14.8b");
        assertDecoding(0x2ea02a0a, "uaddlp v10.1d,v16.2s");
    }

    @Test
    public void test_uaddw2_simd() {
        assertDecoding(0x6e6c116f, "uaddw2 v15.4s, v11.4s, v12.8h");
        assertDecoding(0x6e2e119a, "uaddw2 v26.8h, v12.8h, v14.16b");
    }

    @Test
    public void test_uaddw_simd() {
        assertDecoding(0x2e7d1105, "uaddw v5.4s, v8.4s, v29.4h");
    }

    @Test
    public void test_ubfiz() {
        assertDecoding(0x53141541, "ubfiz w1, w10, #12, #6");
        assertDecoding(0x530e1541, "ubfiz w1, w10, #18, #6");
        assertDecoding(0x53081541, "ubfiz w1, w10, #24, #6");
        assertDecoding(0x531d1141, "ubfiz w1, w10, #3, #5");
        assertDecoding(0x531a1581, "ubfiz w1, w12, #6, #6");
        assertDecoding(0x53081a75, "ubfiz w21, w19, #24, #7");
        assertDecoding(0x531f02b5, "ubfiz w21, w21, #1, #1");
        assertDecoding(0xd37e7a61, "ubfiz x1, x19, #2, #31");
        assertDecoding(0xd37d7e61, "ubfiz x1, x19, #3, #32");
        assertDecoding(0xd37acd6b, "ubfiz x11, x11, #6, #52");
        assertDecoding(0xd37e0402, "ubfiz x2, x0, #2, #2");
        assertDecoding(0xd3635e3f, "ubfiz xzr, x17, #29, #24");
        assertDecoding(0xd37f893f, "ubfiz xzr, x9, #1, #35");
    }

    @Test
    public void test_ubfx() {
        assertDecoding(0x53083de1, "ubfx w1, w15, #8, #8");
        assertDecoding(0x53052841, "ubfx w1, w2, #5, #6");
        assertDecoding(0x530540f5, "ubfx w21, w7, #5, #12");
        assertDecoding(0xd370fa61, "ubfx x1, x19, #48, #15");
        assertDecoding(0xd371d47f, "ubfx xzr, x3, #49, #5");
        assertDecoding(0xd346ec9f, "ubfx xzr, x4, #6, #54");

        assertDecoding(0xd3407fcd, "ubfx x13,x30,#0,#32");
        assertDecoding(0xd3407c16, "ubfx x22,x0,#0,#32");
        assertDecoding(0xd3403dbc, "ubfx x28,x13,#0,#16");
        assertDecoding(0xd3401c07, "ubfx x7,x0,#0,#8");
        assertDecoding(0xd3401dbf, "ubfx xzr,x13,#0,#8");
        assertDecoding(0xd3407cff, "ubfx xzr,x7,#0,#32");
    }

    @Test
    public void test_ucvtf() {
        assertDecoding(0x1ec3f6d5, "ucvtf h21, w22, #3");
    }

    @Test
    public void test_ucvtf_fpu() {
        assertDecoding(0x1e630261, "ucvtf d1, w19");
        assertDecoding(0x9e6302c1, "ucvtf d1, x22");
        assertDecoding(0x7f6ae42c, "ucvtf d12, d1, #22");
        assertDecoding(0x1e230361, "ucvtf s1, w27");
        assertDecoding(0x9e230321, "ucvtf s1, x25");
    }

    @Test
    public void test_ucvtf_simd() {
        assertDecoding(0x6f54e4a8, "ucvtf v8.2d, v5.2d, #44");
        assertDecoding(0x6e79d99c, "ucvtf v28.8h,v12.8h");
        assertDecoding(0x2e79db5e, "ucvtf v30.4h,v26.4h");
    }

    @Test
    public void test_udiv() {
        assertDecoding(0x1ac90941, "udiv w1, w10, w9");
        assertDecoding(0x9ada0aa1, "udiv x1, x21, x26");
    }

    @Test
    public void test_udot_simd() {
        assertDecoding(0x2f87e1d0, "udot v16.2s, v14.8b, v7.4b[0]");
        assertDecoding(0x2fabe893, "udot v19.2s, v4.8b, v11.4b[3]");
        assertDecoding(0x6f82e875, "udot v21.4s, v3.16b, v2.4b[2]");
        assertDecoding(0x2fb9e85a, "udot v26.2s, v2.8b, v25.4b[3]");
        assertDecoding(0x2f94ea83, "udot v3.2s, v20.8b, v20.4b[2]");
        assertDecoding(0x6f84e909, "udot v9.4s, v8.16b, v4.4b[2]");
        assertDecoding(0x6e889655, "udot v21.4s,v18.16b,v8.16b");
    }

    @Test
    public void test_uhadd_simd() {
        assertDecoding(0x2e7e07d3, "uhadd v19.4h, v30.4h, v30.4h");
    }

    @Test
    public void test_uhsub_simd() {
        assertDecoding(0x2e66260d, "uhsub v13.4h, v16.4h, v6.4h");
    }

    @Test
    public void test_umaddl() {
        assertDecoding(0x9ba21d81, "umaddl x1, w12, w2, x7");
        assertDecoding(0x9bba6661, "umaddl x1, w19, w26, x25");
        assertDecoding(0x9ba44ab6, "umaddl x22, w21, w4, x18");
    }

    @Test
    public void test_umax_simd() {
        assertDecoding(0x2e3a64cf, "umax v15.8b, v6.8b, v26.8b");
    }

    @Test
    public void test_umaxp_simd() {
        assertDecoding(0x2e29a7ee, "umaxp v14.8b, v31.8b, v9.8b");
        assertDecoding(0x6e20a6e7, "umaxp v7.16b, v23.16b, v0.16b");
    }

    @Test
    public void test_umaxv_simd() {
        assertDecoding(0x6e30a82f, "umaxv b15,v1.16b");
    }

    @Test
    public void test_umin_simd() {
        assertDecoding(0x6e6e6e00, "umin v0.8h, v16.8h, v14.8h");
        assertDecoding(0x6e286d93, "umin v19.16b, v12.16b, v8.16b");
    }

    @Test
    public void test_uminp_simd() {
        assertDecoding(0x6e32af0e, "uminp v14.16b, v24.16b, v18.16b");
        assertDecoding(0x2e78ad6f, "uminp v15.4h, v11.4h, v24.4h");
        assertDecoding(0x2e7aac3e, "uminp v30.4h, v1.4h, v26.4h");
    }

    @Test
    public void test_uminv_simd() {
        assertDecoding(0x6e31abcc, "uminv b12,v30.16b");
        assertDecoding(0x2e31a92c, "uminv b12,v9.8b");
        assertDecoding(0x2e71aa15, "uminv h21,v16.4h");
        assertDecoding(0x6e71abf6, "uminv h22,v31.8h");
        assertDecoding(0x6eb1a96f, "uminv s15,v11.4s");
    }

    @Test
    public void test_umlal2_simd() {
        assertDecoding(0x6eaa82ca, "umlal2 v10.2d, v22.4s, v10.4s");
        assertDecoding(0x6f66206e, "umlal2 v14.4s, v3.8h, v6.h[2]");
        assertDecoding(0x6ea081d3, "umlal2 v19.2d, v14.4s, v0.4s");
        assertDecoding(0x6ea881f3, "umlal2 v19.2d, v15.4s, v8.4s");
        assertDecoding(0x6ea48233, "umlal2 v19.2d, v17.4s, v4.4s");
        assertDecoding(0x6ea28253, "umlal2 v19.2d, v18.4s, v2.4s");
        assertDecoding(0x6f662053, "umlal2 v19.4s, v2.8h, v6.h[2]");
        assertDecoding(0x6ea181d4, "umlal2 v20.2d, v14.4s, v1.4s");
        assertDecoding(0x6ea081f4, "umlal2 v20.2d, v15.4s, v0.4s");
        assertDecoding(0x6ea68234, "umlal2 v20.2d, v17.4s, v6.4s");
        assertDecoding(0x6ea48254, "umlal2 v20.2d, v18.4s, v4.4s");
        assertDecoding(0x6ea381d5, "umlal2 v21.2d, v14.4s, v3.4s");
        assertDecoding(0x6ea181f5, "umlal2 v21.2d, v15.4s, v1.4s");
        assertDecoding(0x6ea88235, "umlal2 v21.2d, v17.4s, v8.4s");
        assertDecoding(0x6ea68255, "umlal2 v21.2d, v18.4s, v6.4s");
        assertDecoding(0x6ea581d6, "umlal2 v22.2d, v14.4s, v5.4s");
        assertDecoding(0x6ea381f6, "umlal2 v22.2d, v15.4s, v3.4s");
        assertDecoding(0x6ea08236, "umlal2 v22.2d, v17.4s, v0.4s");
        assertDecoding(0x6ea88256, "umlal2 v22.2d, v18.4s, v8.4s");
        assertDecoding(0x6ea781d7, "umlal2 v23.2d, v14.4s, v7.4s");
        assertDecoding(0x6ea581f7, "umlal2 v23.2d, v15.4s, v5.4s");
        assertDecoding(0x6ea18237, "umlal2 v23.2d, v17.4s, v1.4s");
        assertDecoding(0x6ea08257, "umlal2 v23.2d, v18.4s, v0.4s");
        assertDecoding(0x6f662048, "umlal2 v8.4s, v2.8h, v6.h[2]");
    }

    @Test
    public void test_umlal_simd() {
        assertDecoding(0x2ea88153, "umlal v19.2d, v10.2s, v8.2s");
        assertDecoding(0x2f882153, "umlal v19.2d, v10.2s, v8.s[0]");
        assertDecoding(0x2ea68173, "umlal v19.2d, v11.2s, v6.2s");
        assertDecoding(0x2f862173, "umlal v19.2d, v11.2s, v6.s[0]");
        assertDecoding(0x2ea48193, "umlal v19.2d, v12.2s, v4.2s");
        assertDecoding(0x2f842193, "umlal v19.2d, v12.2s, v4.s[0]");
        assertDecoding(0x2ea281b3, "umlal v19.2d, v13.2s, v2.2s");
        assertDecoding(0x2f8221b3, "umlal v19.2d, v13.2s, v2.s[0]");
        assertDecoding(0x2f8829f3, "umlal v19.2d, v15.2s, v8.s[2]");
        assertDecoding(0x2f862a13, "umlal v19.2d, v16.2s, v6.s[2]");
        assertDecoding(0x2f842a33, "umlal v19.2d, v17.2s, v4.s[2]");
        assertDecoding(0x2f822a53, "umlal v19.2d, v18.2s, v2.s[2]");
        assertDecoding(0x2ea08133, "umlal v19.2d, v9.2s, v0.2s");
        assertDecoding(0x2f802133, "umlal v19.2d, v9.2s, v0.s[0]");
        assertDecoding(0x2e7e8342, "umlal v2.4s, v26.4h, v30.4h");
        assertDecoding(0x2ea08154, "umlal v20.2d, v10.2s, v0.2s");
        assertDecoding(0x2f802154, "umlal v20.2d, v10.2s, v0.s[0]");
        assertDecoding(0x2ea88174, "umlal v20.2d, v11.2s, v8.2s");
        assertDecoding(0x2f882174, "umlal v20.2d, v11.2s, v8.s[0]");
        assertDecoding(0x2ea68194, "umlal v20.2d, v12.2s, v6.2s");
        assertDecoding(0x2f862194, "umlal v20.2d, v12.2s, v6.s[0]");
        assertDecoding(0x2ea481b4, "umlal v20.2d, v13.2s, v4.2s");
        assertDecoding(0x2f8421b4, "umlal v20.2d, v13.2s, v4.s[0]");
        assertDecoding(0x2f8029f4, "umlal v20.2d, v15.2s, v0.s[2]");
        assertDecoding(0x2f882a14, "umlal v20.2d, v16.2s, v8.s[2]");
        assertDecoding(0x2f862a34, "umlal v20.2d, v17.2s, v6.s[2]");
        assertDecoding(0x2f842a54, "umlal v20.2d, v18.2s, v4.s[2]");
        assertDecoding(0x2ea18134, "umlal v20.2d, v9.2s, v1.2s");
        assertDecoding(0x2f812134, "umlal v20.2d, v9.2s, v1.s[0]");
        assertDecoding(0x2ea18155, "umlal v21.2d, v10.2s, v1.2s");
        assertDecoding(0x2f812155, "umlal v21.2d, v10.2s, v1.s[0]");
        assertDecoding(0x2ea08175, "umlal v21.2d, v11.2s, v0.2s");
        assertDecoding(0x2f802175, "umlal v21.2d, v11.2s, v0.s[0]");
        assertDecoding(0x2ea88195, "umlal v21.2d, v12.2s, v8.2s");
        assertDecoding(0x2f882195, "umlal v21.2d, v12.2s, v8.s[0]");
        assertDecoding(0x2ea681b5, "umlal v21.2d, v13.2s, v6.2s");
        assertDecoding(0x2f8621b5, "umlal v21.2d, v13.2s, v6.s[0]");
        assertDecoding(0x2f8129f5, "umlal v21.2d, v15.2s, v1.s[2]");
        assertDecoding(0x2f802a15, "umlal v21.2d, v16.2s, v0.s[2]");
        assertDecoding(0x2f882a35, "umlal v21.2d, v17.2s, v8.s[2]");
        assertDecoding(0x2f862a55, "umlal v21.2d, v18.2s, v6.s[2]");
        assertDecoding(0x2ea38135, "umlal v21.2d, v9.2s, v3.2s");
        assertDecoding(0x2f832135, "umlal v21.2d, v9.2s, v3.s[0]");
        assertDecoding(0x2ea38156, "umlal v22.2d, v10.2s, v3.2s");
        assertDecoding(0x2f832156, "umlal v22.2d, v10.2s, v3.s[0]");
        assertDecoding(0x2ea18176, "umlal v22.2d, v11.2s, v1.2s");
        assertDecoding(0x2f812176, "umlal v22.2d, v11.2s, v1.s[0]");
        assertDecoding(0x2ea08196, "umlal v22.2d, v12.2s, v0.2s");
        assertDecoding(0x2f802196, "umlal v22.2d, v12.2s, v0.s[0]");
        assertDecoding(0x2ea881b6, "umlal v22.2d, v13.2s, v8.2s");
        assertDecoding(0x2f8821b6, "umlal v22.2d, v13.2s, v8.s[0]");
        assertDecoding(0x2f8329f6, "umlal v22.2d, v15.2s, v3.s[2]");
        assertDecoding(0x2f812a16, "umlal v22.2d, v16.2s, v1.s[2]");
        assertDecoding(0x2f802a36, "umlal v22.2d, v17.2s, v0.s[2]");
        assertDecoding(0x2f882a56, "umlal v22.2d, v18.2s, v8.s[2]");
        assertDecoding(0x2ea58136, "umlal v22.2d, v9.2s, v5.2s");
        assertDecoding(0x2f852136, "umlal v22.2d, v9.2s, v5.s[0]");
        assertDecoding(0x2ea58157, "umlal v23.2d, v10.2s, v5.2s");
        assertDecoding(0x2f852157, "umlal v23.2d, v10.2s, v5.s[0]");
        assertDecoding(0x2ea38177, "umlal v23.2d, v11.2s, v3.2s");
        assertDecoding(0x2f832177, "umlal v23.2d, v11.2s, v3.s[0]");
        assertDecoding(0x2ea18197, "umlal v23.2d, v12.2s, v1.2s");
        assertDecoding(0x2f812197, "umlal v23.2d, v12.2s, v1.s[0]");
        assertDecoding(0x2ea081b7, "umlal v23.2d, v13.2s, v0.2s");
        assertDecoding(0x2f8021b7, "umlal v23.2d, v13.2s, v0.s[0]");
        assertDecoding(0x2f8529f7, "umlal v23.2d, v15.2s, v5.s[2]");
        assertDecoding(0x2f832a17, "umlal v23.2d, v16.2s, v3.s[2]");
        assertDecoding(0x2f812a37, "umlal v23.2d, v17.2s, v1.s[2]");
        assertDecoding(0x2f802a57, "umlal v23.2d, v18.2s, v0.s[2]");
        assertDecoding(0x2fb92b17, "umlal v23.2d, v24.2s, v25.s[3]");
        assertDecoding(0x2ea78137, "umlal v23.2d, v9.2s, v7.2s");
        assertDecoding(0x2f872137, "umlal v23.2d, v9.2s, v7.s[0]");
        assertDecoding(0x2f93229e, "umlal v30.2d, v20.2s, v19.s[0]");
    }

    @Test
    public void test_umlsl2_simd() {
        assertDecoding(0x6f6c6220, "umlsl2 v0.4s, v17.8h, v12.h[2]");
        assertDecoding(0x6f696174, "umlsl2 v20.4s, v11.8h, v9.h[2]");
        assertDecoding(0x6f646997, "umlsl2 v23.4s, v12.8h, v4.h[6]");
    }

    @Test
    public void test_umov_simd() {
        assertDecoding(0x0e013c01, "umov w1, v0.b[0]");
        assertDecoding(0x0e023e01, "umov w1, v16.h[0]");
        assertDecoding(0x0e023e41, "umov w1, v18.h[0]");
        assertDecoding(0x0e023e61, "umov w1, v19.h[0]");
        assertDecoding(0x0e023c41, "umov w1, v2.h[0]");
        assertDecoding(0x0e023c61, "umov w1, v3.h[0]");
        assertDecoding(0x0e013c02, "umov w2, v0.b[0]");
        assertDecoding(0x0e193e42, "umov w2, v18.b[12]");
        assertDecoding(0x0e023e82, "umov w2, v20.h[0]");
        assertDecoding(0x0e023e63, "umov w3, v19.h[0]");
    }

    @Test
    public void test_umsubl() {
        assertDecoding(0x9bb4c04e, "umsubl x14, w2, w20, x16");
        assertDecoding(0x9bbffbe4, "umsubl x4, wzr, wzr, x30");
    }

    @Test
    public void test_umulh() {
        assertDecoding(0x9bce7d61, "umulh x1, x11, x14");
        assertDecoding(0x9bda7cee, "umulh x14, x7, x26");
        assertDecoding(0x9bc42820, "umulh x0,x1,x4");
        assertDecoding(0x9bd80dc0, "umulh x0,x14,x24");
    }

    @Test
    public void test_umull() {
        assertDecoding(0x9bab7d41, "umull x1, w10, w11");
        assertDecoding(0x9bb77eb3, "umull x19, w21, w23");
    }

    @Test
    public void test_umull2_simd() {
        assertDecoding(0x6ea6c213, "umull2 v19.2d, v16.4s, v6.4s");
        assertDecoding(0x6e69c1e6, "umull2 v6.4s, v15.8h, v9.8h");
        assertDecoding(0x6f84a8c7, "umull2 v7.2d, v6.4s, v4.s[2]");
    }

    @Test
    public void test_umull_simd() {
        assertDecoding(0x2e72c112, "umull v18.4s, v8.4h, v18.4h");
        assertDecoding(0x2eb2c133, "umull v19.2d, v9.2s, v18.2s");
        assertDecoding(0x2f81a9d4, "umull v20.2d, v14.2s, v1.s[2]");
        assertDecoding(0x2f55a005, "umull v5.4s, v0.4h, v5.h[1]");
    }

    @Test
    public void test_uqadd_fpu() {
        assertDecoding(0x7efb0f84, "uqadd d4, d28, d27");
    }

    @Test
    public void test_uqrshl_fpu() {
        assertDecoding(0x7e625c3d, "uqrshl h29, h1, h2");
        assertDecoding(0x7eb05cd1, "uqrshl s17, s6, s16");
    }

    @Test
    public void test_uqrshl_simd() {
        assertDecoding(0x6ead5fc4, "uqrshl v4.4s, v30.4s, v13.4s");
        assertDecoding(0x2e765f27, "uqrshl v7.4h, v25.4h, v22.4h");
    }

    @Test
    public void test_uqrshrn_fpu() {
        assertDecoding(0x7f3a9cc7, "uqrshrn s7, d6, #6");
    }

    @Test
    public void test_uqrshrn_simd() {
        assertDecoding(0x2f0f9fe6, "uqrshrn v6.8b, v31.8h, #1");
    }

    @Test
    public void test_uqshl() {
        assertDecoding(0x7e364d74, "uqshl b20, b11, b22");
        assertDecoding(0x7f0f75e7, "uqshl b7, b15, #7");
    }

    @Test
    public void test_uqshl_simd() {
        assertDecoding(0x6f697461, "uqshl v1.2d, v3.2d, #41");
        assertDecoding(0x2e654d1e, "uqshl v30.4h, v8.4h, v5.4h");
        assertDecoding(0x6f0b75c1, "uqshl v1.16b,v14.16b,#3");
        assertDecoding(0x2f0f7601, "uqshl v1.8b,v16.8b,#7");
    }

    @Test
    public void test_uqshrn() {
        assertDecoding(0x7f10950b, "uqshrn h11,s8,#16");
    }

    @Test
    public void test_uqsub() {
        assertDecoding(0x7e732c33, "uqsub h19, h1, h19");
    }

    @Test
    public void test_uqsub_simd() {
        assertDecoding(0x2e2e2e00, "uqsub v0.8b, v16.8b, v14.8b");
        assertDecoding(0x6eed2c1d, "uqsub v29.2d, v0.2d, v13.2d");
        assertDecoding(0x2ebc2cc7, "uqsub v7.2s, v6.2s, v28.2s");
    }

    @Test
    public void test_uqxtn_fpu() {
        assertDecoding(0x7e214b2c, "uqxtn b12,h25");
    }

    @Test
    public void test_urhadd_simd() {
        assertDecoding(0x2e3a14a8, "urhadd v8.8b, v5.8b, v26.8b");
    }

    @Test
    public void test_urshl_simd() {
        assertDecoding(0x6ea75671, "urshl v17.4s, v19.4s, v7.4s");
    }

    @Test
    public void test_urshr_fpu() {
        assertDecoding(0x7f6e274f, "urshr d15,d26,#18");
        assertDecoding(0x7f5b27f3, "urshr d19,d31,#37");
    }

    @Test
    public void test_urshr_simd() {
        assertDecoding(0x6f2d253c, "urshr v28.4s, v9.4s, #19");
        assertDecoding(0x6f5c278c, "urshr v12.2d,v28.2d,#36");
        assertDecoding(0x2f0f27d0, "urshr v16.8b,v30.8b,#1");
        assertDecoding(0x6f0a24fe, "urshr v30.16b,v7.16b,#6");
    }

    @Test
    public void test_ursra_simd() {
        assertDecoding(0x6f5b3696, "ursra v22.2d, v20.2d, #37");
        assertDecoding(0x2f0a35ef, "ursra v15.8b,v15.8b,#6");
        assertDecoding(0x6f083535, "ursra v21.16b,v9.16b,#8");
    }

    @Test
    public void test_ushl_simd() {
        assertDecoding(0x2eae44bf, "ushl v31.2s, v5.2s, v14.2s");
    }

    @Test
    public void test_ushll_simd() {
        assertDecoding(0x2f30a790, "ushll v16.2d,v28.2s,#16");
    }

    @Test
    public void test_ushll2_simd() {
        assertDecoding(0x6f09a645, "ushll2 v5.8h, v18.16b, #1");
    }

    @Test
    public void test_ushr_fpu() {
        assertDecoding(0x7f600421, "ushr d1, d1, #32");
    }

    @Test
    public void test_ushr_simd() {
        assertDecoding(0x2f26077e, "ushr v30.2s, v27.2s, #26");
        assertDecoding(0x6f0c05e8, "ushr v8.16b, v15.16b, #4");
        assertDecoding(0x6f6e07a7, "ushr v7.2d, v29.2d, #18");
        assertDecoding(0x6f410472, "ushr v18.2d, v3.2d, #63");
        assertDecoding(0x6f6606be, "ushr v30.2d, v21.2d, #26");
        assertDecoding(0x6f270681, "ushr v1.4s, v20.4s, #25");
        assertDecoding(0x6f3b0533, "ushr v19.4s, v9.4s, #5");
        assertDecoding(0x6f2706a5, "ushr v5.4s, v21.4s, #25");
    }

    @Test
    public void test_usqadd_fpu() {
        assertDecoding(0x7e203971, "usqadd b17,b11");
    }

    @Test
    public void test_usqadd_simd() {
        assertDecoding(0x6ea03aa6, "usqadd v6.4s, v21.4s");
    }

    @Test
    public void test_usra_fpu() {
        assertDecoding(0x7f65164a, "usra d10,d18,#27");
        assertDecoding(0x7f5317f0, "usra d16,d31,#45");
    }

    @Test
    public void test_usra_simd() {
        assertDecoding(0x2f271781, "usra v1.2s, v28.2s, #25");
        assertDecoding(0x6f3a1793, "usra v19.4s, v28.4s, #6");
        assertDecoding(0x6f0b1400, "usra v0.16b,v0.16b,#5");
        assertDecoding(0x6f76166a, "usra v10.2d,v19.2d,#10");
        assertDecoding(0x2f0d17b2, "usra v18.8b,v29.8b,#3");
    }

    @Test
    public void test_usubl2_simd() {
        assertDecoding(0x6e3c2259, "usubl2 v25.8h, v18.16b, v28.16b");
    }

    @Test
    public void test_uxtl2_simd() {
        assertDecoding(0x6f10a442, "uxtl2 v2.4s, v2.8h");
        assertDecoding(0x6f08a400, "uxtl2 v0.8h, v0.16b");
        assertDecoding(0x6f20a467, "uxtl2 v7.2d, v3.4s");
    }

    @Test
    public void test_uxtl_simd() {
        assertDecoding(0x2f20a425, "uxtl v5.2d, v1.2s");
        assertDecoding(0x2f10a401, "uxtl v1.4s, v0.4h");
        assertDecoding(0x2f08a402, "uxtl v2.8h, v0.8b");
        assertDecoding(0x2f10a443, "uxtl v3.4s, v2.4h");
    }

    @Test
    public void test_uzp1_simd() {
        assertDecoding(0x4e551b5b, "uzp1 v27.8h, v26.8h, v21.8h");
    }

    @Test
    public void test_uzp2_simd() {
        assertDecoding(0x4e825800, "uzp2 v0.4s, v0.4s, v2.4s");
        assertDecoding(0x4e805820, "uzp2 v0.4s, v1.4s, v0.4s");
        assertDecoding(0x4e835842, "uzp2 v2.4s, v2.4s, v3.4s");
        assertDecoding(0x4e9c59d5, "uzp2 v21.4s, v14.4s, v28.4s");
        assertDecoding(0x4eda5887, "uzp2 v7.2d, v4.2d, v26.2d");
    }


    @Test
    public void test_xar_simd() {
        assertDecoding(0xce95f30a, "xar v10.2d, v24.2d, v21.2d, #60");
        assertDecoding(0xce931d4d, "xar v13.2d, v10.2d, v19.2d, #7");
        assertDecoding(0xce8101f9, "xar v25.2d, v15.2d, v1.2d, #0");
    }

    @Test
    public void test_xpaclri() {
        assertDecoding(0xd50320ff, "xpaclri");
    }

    @Test
    public void test_xtn2_simd() {
        assertDecoding(0x4ea12bf0, "xtn2 v16.4s, v31.2d");
        assertDecoding(0x4ea12bbe, "xtn2 v30.4s, v29.2d");
        assertDecoding(0x4e6128d0, "xtn2 v16.8h, v6.4s");
        assertDecoding(0x4e612955, "xtn2 v21.8h, v10.4s");
        assertDecoding(0x4e212a08, "xtn2 v8.16b, v16.8h");
        assertDecoding(0x4e212909, "xtn2 v9.16b, v8.8h");
    }

    @Test
    public void test_xtn_simd() {
        assertDecoding(0x0ea12aea, "xtn v10.2s, v23.2d");
        assertDecoding(0x0e612b70, "xtn v16.4h, v27.4s");
        assertDecoding(0x0e212aa8, "xtn v8.8b, v21.8h");
    }

    @Test
    public void test_zip1_simd() {
        assertDecoding(0x4ed1388c, "zip1 v12.2d, v4.2d, v17.2d");
        assertDecoding(0x0e0f3aaf, "zip1 v15.8b, v21.8b, v15.8b");
    }

    @Test
    public void test_zip2_simd() {
        assertDecoding(0x0e04798a, "zip2 v10.8b, v12.8b, v4.8b");
        assertDecoding(0x4e577b8d, "zip2 v13.8h, v28.8h, v23.8h");
        assertDecoding(0x4e0b7973, "zip2 v19.16b, v11.16b, v11.16b");
        assertDecoding(0x4e1f7b68, "zip2 v8.16b, v27.16b, v31.16b");
    }

    @Test
    public void test_invalid_opcodes() {
        assertInvalidOpcode(0xebc8de81);
        assertInvalidOpcode(0x6afed9fe);
        assertInvalidOpcode(0xe8ed0899);
        assertInvalidOpcode(0x2b149936);
        assertInvalidOpcode(0x6805424c);
        assertInvalidOpcode(0x4b41c7f7);
        assertInvalidOpcode(0x12e5a3f5);
        assertInvalidOpcode(0xe91587ee);
        assertInvalidOpcode(0x5313e4b5);
        assertInvalidOpcode(0x2bda7f77);
        assertInvalidOpcode(0x69c26310);
        assertInvalidOpcode(0x68ddedef);
        assertInvalidOpcode(0xe86b5ef5);
        assertInvalidOpcode(0x6ef0a7dc);
        assertInvalidOpcode(0x8bdeb51b);
        assertInvalidOpcode(0xeb263f8a);
        assertInvalidOpcode(0x6948a7c9);
        assertInvalidOpcode(0x0b3bbb5e);
        assertInvalidOpcode(0x72d96ccf);
        assertInvalidOpcode(0xcbc43a7f);
        assertInvalidOpcode(0x120efe34);
        assertInvalidOpcode(0x1224f5e1);
        assertInvalidOpcode(0x133f7cc1);
        assertInvalidOpcode(0x0eb1bb6b);
        assertInvalidOpcode(0x6ee04992);
        assertInvalidOpcode(0x0e605b8b);
        assertInvalidOpcode(0x6e0ef56a);
        assertInvalidOpcode(0x2f68186b);
        assertInvalidOpcode(0x1e18072b);
        assertInvalidOpcode(0x1e1954bf);
        assertInvalidOpcode(0x1ef5018d);
        assertInvalidOpcode(0x0cdd5e00);
        assertInvalidOpcode(0x69e586c1);
        assertInvalidOpcode(0x532f7cea);
        assertInvalidOpcode(0x4ee596e1);
        assertInvalidOpcode(0x4ef89e60);
        assertInvalidOpcode(0x4ee019f2);
        assertInvalidOpcode(0x6ee00abd);
        assertInvalidOpcode(0x4ee00adf);
        assertInvalidOpcode(0x4efc7d80);
        assertInvalidOpcode(0x4efd770a);
        assertInvalidOpcode(0x1e4234e0);
        assertInvalidOpcode(0x0ffee820);
        assertInvalidOpcode(0x0ec09540);
        assertInvalidOpcode(0x4eff0500);
        assertInvalidOpcode(0x0cc88ef0);
        assertInvalidOpcode(0x4ef764e1);
        assertInvalidOpcode(0x6f56eaa9);
        assertInvalidOpcode(0x4bd627e1);
        assertInvalidOpcode(0x4ef0392a);
        assertInvalidOpcode(0x4ef0a9a2);
        assertInvalidOpcode(0x0e1c2eaa);
        assertInvalidOpcode(0x4e182fe3);
        assertInvalidOpcode(0x0e2d91e0);
        assertInvalidOpcode(0x0e369370);
        assertInvalidOpcode(0x4e3a91ea);
        assertInvalidOpcode(0x0e2cb0a0);
        assertInvalidOpcode(0x4e29b39c);
        assertInvalidOpcode(0x0e2db761);
        assertInvalidOpcode(0x0e20d2f0);
        assertInvalidOpcode(0x4e2ed311);
        assertInvalidOpcode(0x0e082ecc);
        assertInvalidOpcode(0x6e008620);
        assertInvalidOpcode(0x6e108c00);
        assertInvalidOpcode(0x6e33b7a0);
        assertInvalidOpcode(0x4ee016c0);
        assertInvalidOpcode(0x6ee57fab);
        assertInvalidOpcode(0x6ee97780);
        assertInvalidOpcode(0x6ee00650);
        assertInvalidOpcode(0x6ee1274a);
        assertInvalidOpcode(0x4ee1cb32);
        assertInvalidOpcode(0x6eec1681);
        assertInvalidOpcode(0x7f192461);
        assertInvalidOpcode(0x7f2826d7);
        assertInvalidOpcode(0x6ee1c932);
        assertInvalidOpcode(0x7f1e17cc);
        assertInvalidOpcode(0x7f371481);
        assertInvalidOpcode(0x6ef03bc1);
        assertInvalidOpcode(0x08377c75);
        assertInvalidOpcode(0x483f7fe6);
        assertInvalidOpcode(0x086f7e36);
        assertInvalidOpcode(0x486a7eb7);
        assertInvalidOpcode(0x0867fd20);
        assertInvalidOpcode(0x4875ffe4);
        assertInvalidOpcode(0x0827fded);
        assertInvalidOpcode(0x4820fe41);
        assertInvalidOpcode(0x4ee04a2e);
        assertInvalidOpcode(0x2e216a2a);
        assertInvalidOpcode(0x6e21688f);
        assertInvalidOpcode(0x4ef42540);
    }

    @Ignore("see https://sourceware.org/bugzilla/show_bug.cgi?id=23204")
    @Test
    public void test_invalid_opcodes_sqrdmlah() {
        assertInvalidOpcode(0x6e9286c9);
        assertInvalidOpcode(0x6e818720);
        assertInvalidOpcode(0x6e8d8eb7);
    }

    @Ignore("see https://sourceware.org/bugzilla/show_bug.cgi?id=23242")
    @Test
    public void test_sbo_bits() {
        assertInvalidOpcode(0xb8a2c221);
        assertInvalidOpcode(0xf8a7c3aa);
        assertInvalidOpcode(0x38bcc1ca);
        assertInvalidOpcode(0x78aec1a1);
        assertInvalidOpcode(0x88d9e5c1);
        assertInvalidOpcode(0xc8d2be61);
    }

    private void assertInvalidOpcode(int opcode) {
        assertDecoding(opcode, String.format(".inst 0x%08x", opcode));
    }

    private void assertDecodingWithPc(int pc, int opcode, String expected) {
        assertDecoding(pc, opcode, expected);
    }

    private void assertDecoding(int opcode, String expected) {
        assertDecoding(0, opcode, expected);
    }

    private void assertDecoding(int frontPadding, int opcode, String expected) {
        ArmDisasmDecoder decoder = new ArmDisasmDecoder();
        AsmStatement decoded = decoder.decode(frontPadding, opcode);
        String formated = decoded.format();
        assertThat(normalizeAsmLine(formated), is(normalizeAsmLine(expected)));
    }

    public static String normalizeAsmLine(String s) {
        return s.trim().replaceAll("\t", " ").replaceAll(" +", " ").replaceAll(", ", ",").trim().toLowerCase();
    }
}
