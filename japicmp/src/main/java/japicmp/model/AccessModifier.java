package japicmp.model;

public enum AccessModifier {
    PUBLIC(3), PACKAGE_PROTECTED(2), PROTECTED(1), PRIVATE(0);

    private int level;

    AccessModifier(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static String listOfAccessModifier() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (AccessModifier am : AccessModifier.values()) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(am.toString());
            i++;
        }
        return sb.toString();
    }
}
