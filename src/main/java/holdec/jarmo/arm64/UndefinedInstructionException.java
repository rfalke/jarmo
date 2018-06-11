package holdec.jarmo.arm64;

public class UndefinedInstructionException extends IllegalArgumentException {
    public UndefinedInstructionException(String s) {
        super(s);
    }
}
