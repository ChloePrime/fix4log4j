package chloeprime.fix4log4j;

/**
 * @author Administrator
 */
public class Censor {
    private static final String VIRUS_CHARACTERISTIC = "${jndi:}";

    /**
     * Is the given message malicious ?
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static boolean isMaliciousMessage(String message) {
        if (!message.contains("$")) {
            return false;
        }

        int cursor = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == VIRUS_CHARACTERISTIC.charAt(cursor)) {
                ++cursor;
                if (cursor >= VIRUS_CHARACTERISTIC.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Censor() {
    }
}
