package holdec.jarmo.arm64;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usageAndExit();
            throw new RuntimeException("Unreached");
        }
        List<Integer> opcodesInput = new ArrayList<>();
        if ("-s".equals(args[0])) {
            String s = "";
            for (int i = 1; i < args.length; i++) {
                s += args[i];
            }
            s = s.replace(" ", "");
            if ((s.length() % 8) != 0) {
                System.out.println("Multiple of 4 bytes required");
                usageAndExit();
            }
            int numInst = s.length() / 8;
            for (int i = 0; i < numInst; i++) {
                String asString = s.substring(i * 8, i * 8 + 8);
                long asLong = Long.parseLong(asString, 16);
                opcodesInput.add((int) asLong);
            }
        } else if ("-f".equals(args[0])) {
            try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(args[1]))) {
                opcodesInput.add(dataInputStream.readInt());
            }
        } else {
            usageAndExit();
            throw new RuntimeException("Unreached");
        }
        ArmDisasmDecoder decoder = new ArmDisasmDecoder();
        for (int i = 0; i < opcodesInput.size(); i++) {
            int opcode = opcodesInput.get(i);
            AsmStatement asmStatement = decoder.decode(i * 4, opcode);
            System.out.printf("%4x %s\n", asmStatement.address, asmStatement.format());
        }
    }

    private static void usageAndExit() {
        System.out.println("java -jar jarmo.jar [options]");
        System.out.println("  -f <filename>                 disassemble a raw binary file");
        System.out.println("  -s <bytes in hex>             disassemble bytes");
        System.out.println();
        System.out.println("Examples");
        System.out.println("  java -jar jarmo.jar -s d5 03 20 1f 4dda105b 6a 69 4c 30");
        System.out.println("  echo -n jiL0 >testfile");
        System.out.println("  java -jar jarmo.jar -f testfile");
        System.exit(1);
    }
}
