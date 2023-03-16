package org.me._410.gptsearch;

public class Unescaper {
    public static String unescape(String input) {
        StringBuilder builder = new StringBuilder(input.length());
        int i = 0;
        while (i < input.length()) {
            char current = input.charAt(i);
            if (current == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
                String unicodeStr = input.substring(i + 2, i + 6);
                try {
                    int unicodeVal = Integer.parseInt(unicodeStr, 16);
                    builder.append((char) unicodeVal);
                } catch (NumberFormatException e) {
                    // ignore invalid Unicode escape sequences
                }
                i += 6; // skip over the escaped characters
            } else {
                builder.append(current);
                i++;
            }
        }
        return builder.toString();
    }
}
