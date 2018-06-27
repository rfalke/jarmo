package holdec.jarmo.arm64;

public class ObjdumpFormatter implements Formatter {

    @Override
    public String formatMemAccess(MemAccessType memAccessType, String register, int offset) {
        if (memAccessType == MemAccessType.PreIndex) {
            return String.format("[%s,#%d]!", register, offset);
        }
        if (memAccessType == MemAccessType.PostIndex) {
            return String.format("[%s],#%d", register, offset);
        }
        if (memAccessType == MemAccessType.OnlyOffset) {
            if (offset == 0) {
                return String.format("[%s]", register);
            } else {
                return String.format("[%s,#%d]", register, offset);
            }
        }
        throw new RuntimeException("Unknown type " + memAccessType);
    }

    @Override
    public String formatLabel(long addressValue) {
        return String.format("0x%x", addressValue);
    }
}
