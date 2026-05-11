package holdec.jarmo.arm64;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static holdec.jarmo.arm64.A64DisasmTest.normalizeAsmLine;
import static java.util.Arrays.asList;

public class A64RandomDataTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    @Ignore
    public void testForExceptionsWithRandomData() throws Exception {
        int numInstructions = 1_000_000;
        long seed = System.currentTimeMillis();
        System.out.println("Using seed " + seed);
        Random random = new Random(seed);
        ArmDisasmDecoder decoder = new ArmDisasmDecoder();
        Instant start = Instant.now();
        for (int i = 0; i < numInstructions; i++) {
            int opcode = random.nextInt();
            try {
                decoder.decode(0x123456780L, opcode);
            } catch (Exception e) {
                System.out.printf("Problem with 0x%08x\n", opcode);
                reportNewTest(false, new Line(opcode, "xxx"), null);
                throw e;
            }
        }
        Duration duration = Duration.between(start, Instant.now());
        Duration durationPerInstruction = duration.dividedBy(numInstructions);
        System.out.println("Finished " + numInstructions + " instructions in " + duration + " = " + durationPerInstruction + " / instruction");
    }

    @Test
    @Ignore
    public void compareWithObjdump() throws Exception {
        int numRepeats = 100_000;
        for (int i = 0; i < numRepeats; i++) {
            performTest(100_000, true);
        }
    }

    private void performTest(int numInstructions, boolean logProblemsToFile) throws Exception {
        System.out.printf("Testing with %d instructions\n", numInstructions);
        File randomFile = writeRandomFile(numInstructions);
        List<Line> objDumpLines = disassembleWithObjdump(randomFile);
        List<Line> jarmoLines = disassembleWithJarmo(randomFile);

        for (int i = 0; i < jarmoLines.size(); i++) {
            Line jarmo = jarmoLines.get(i);
            Line objdump = objDumpLines.get(i);
            if (jarmo.as32bit != objdump.as32bit) {
                throw new RuntimeException("Index = " + i + " jarmo=" + jarmo + " objdump=" + objdump);
            }

            if (jarmo.isValid || !jarmo.isValid) {
                if (!jarmo.decoded.equals(objdump.decoded)) {
                    // objdump converts orr to mov alias even when register is shifted
                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23193
                    boolean skip1 = jarmo.decoded.contains("orr") && jarmo.decoded.contains("zr,") && objdump.decoded.contains("mov");
                    boolean skip2 = jarmo.decoded.contains("mov") && objdump.decoded.contains("orr") && objdump.decoded.contains("zr,");

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23192
                    boolean skip4 = !objdump.isValid && jarmo.decoded.contains("fcmla");

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23204
                    boolean skip5 = !objdump.isValid && (jarmo.decoded.contains("sqrdmlah") || jarmo.decoded.contains("sqrdmlsh"));

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23230
                    boolean skip6 = objdump.isValid && !jarmo.isValid && objdump.decoded.contains("s0") && (objdump.decoded.contains("mrs ") || objdump.decoded.contains("msr "));

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23212
                    boolean skip7 = objdump.isValid && !jarmo.isValid && (objdump.decoded.contains("fmla") || objdump.decoded.contains("fmls") || objdump.decoded.contains("fmul"));

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23109
                    boolean skip8 = objdump.isValid && !jarmo.isValid && (objdump.decoded.contains("sdot") || objdump.decoded.contains("udot"));

                    // see https://sourceware.org/bugzilla/show_bug.cgi?id=23242
                    boolean skip9 = !objdump.isValid && jarmo.isValid && (jarmo.decoded.contains("ldapr") || jarmo.decoded.contains("ldar") || jarmo.decoded.contains("ldar"));

                    if (!(skip1 || skip2 || skip4 || skip5 || skip6 || skip7 || skip8 || skip9)) {
                        System.out.println(" objdump: " + objdump.decoded);
                        System.out.println("   jarmo: " + jarmo.decoded);
                        reportNewTest(logProblemsToFile, objdump, jarmo);
                        if (!logProblemsToFile) {
                            throw new RuntimeException("Different");
                        }
                    }
                }
            }
        }
    }

    private int parseHex(String opcodeInHex) {
        long l = Long.parseLong(opcodeInHex, 16);
        int i = (int) l;
        String backToString = String.format("%08x", i);
        if (!backToString.equals(opcodeInHex)) {
            throw new RuntimeException(opcodeInHex + " int=" + i + " long=" + l + " hex=" + backToString);
        }
        return i;
    }

    private List<Line> disassembleWithObjdump(File inputFile) throws Exception {
        Instant start = Instant.now();
        File cFile = new File(temporaryFolder.getRoot(), "c-output");
        ProcessBuilder processBuilder = new ProcessBuilder("objdump", "-D", "-b", "binary", "-maarch64", inputFile.getAbsolutePath()).redirectOutput(cFile);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        Duration duration = Duration.between(start, Instant.now());
        if (exitCode != 0) {
            throw new RuntimeException();
        }

        List<Line> result = new ArrayList<>();
        try (BufferedReader otherFile = new BufferedReader(new FileReader(cFile))) {
            while (true) {
                String line;
                do {
                    line = otherFile.readLine();
                } while (line != null && !line.startsWith(" "));
                if (line == null) {
                    break;
                }
                line = line.split(";")[0];
                line = line.split("//")[0];
                String opcodeInHex = line.split("\t")[1].trim();
                String cDis = normalizeAsmLine(line.split("\t", 3)[2]);
                result.add(new Line(parseHex(opcodeInHex), cDis));
            }
        }
        System.out.printf("objdump disassembled %d opcodes in %.1fsec into %s\n", result.size(), duration.toMillis() / 1000.0, cFile);

        return result;
    }

    private List<Line> disassembleWithJarmo(File inputFile) throws IOException {
        Instant start = Instant.now();
        ArmDisasmDecoder decoder = new ArmDisasmDecoder();
        List<Line> result = new ArrayList<>();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(inputFile))) {
            for (int pc = 0; ; pc += 4) {
                int opcode32Bit;
                try {
                    opcode32Bit = Integer.reverseBytes(dataInputStream.readInt());
                } catch (EOFException e) {
                    break;
                }

                AsmStatement asmStatement = decoder.decode(pc, opcode32Bit);
                String asString = asmStatement.format();
                String disassembled = normalizeAsmLine(asString);
                result.add(new Line(opcode32Bit, disassembled));
            }
        }
        Duration duration = Duration.between(start, Instant.now());
        System.out.printf("jarmo disassembled %d opcodes in %.1fsec\n", result.size(), duration.toMillis() / 1000.0);

        return result;
    }

    private void reportNewTest(boolean logToFile, Line expected, Line actual) throws IOException {
        String junitStmt;
        if (expected.isValid) {
            junitStmt = String.format("  assertDecoding(%s, \"%s\");\n", String.format("0x%08x", expected.as32bit), expected.decoded);
        } else {
            junitStmt = String.format("  assertInvalidOpcode(%s); // %s\n", String.format("0x%08x", expected.as32bit), actual.decoded);
        }
        if (logToFile) {
            Files.write(new File("new-tests.txt").toPath(), asList(junitStmt), StandardOpenOption.APPEND);
        } else {
            System.out.println("INSERT into A64DisasmTest >>>");
            System.out.println(junitStmt);
            System.out.println("<<<");
        }
    }

    private File writeRandomFile(int numInstructions) throws Exception {
        long seed = System.currentTimeMillis();
        System.out.println("Using seed " + seed);
        Random random = new Random(seed);
        byte[] bytes = new byte[numInstructions * 4];
        random.nextBytes(bytes);
        File result = new File(temporaryFolder.getRoot(), "binary");
        Files.write(result.toPath(),bytes);
        return result;
    }

    private static class Line {
        public final int as32bit;
        public final String decoded;
        public final boolean isValid;

        @Override
        public String toString() {
            return "Line{" +
                    "as32bit=" + as32bit +
                    ", decoded='" + decoded + '\'' +
                    ", isValid=" + isValid +
                    '}';
        }

        private Line(int as32bit, String decoded) {
            this.as32bit = as32bit;
            this.decoded = decoded;
            isValid = !decoded.contains(".inst");
        }
    }
}
