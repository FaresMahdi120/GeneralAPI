package mc.thearcade.commons;

public final class EnumUtils {

    private EnumUtils() {
    }

    public static String fancyName(Enum<?> e) {
        String name = e.name();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i == 0) {
                builder.append(Character.toUpperCase(c));
            }
            else if (c == '_') {
                builder.append(' ');
            }
            else {
                builder.append(Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }

}
