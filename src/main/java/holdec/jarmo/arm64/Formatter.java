package holdec.jarmo.arm64;

public interface Formatter {
    enum MemAccessType {
        OnlyOffset,
        PreIndex,
        PostIndex;
    }

    String formatMemAccess(MemAccessType memAccessType, String register, int offset);

    String formatLabel(long addressValue);

    String formatImmWithLsl(int imm16, int lslBits);

    boolean formatVectorRegisterListAsRange();
}
