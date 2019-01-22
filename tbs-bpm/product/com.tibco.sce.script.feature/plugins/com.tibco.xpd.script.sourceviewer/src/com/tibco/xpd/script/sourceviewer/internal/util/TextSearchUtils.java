package com.tibco.xpd.script.sourceviewer.internal.util;

public class TextSearchUtils {

    private TextSearchUtils() {
    }

    /*
     * public static int getStart(String string, int end) { int start = end - 1;
     * for (boolean dot = false; start > 0 &&
     * (Character.isJavaIdentifierPart(string.charAt(start)) || string
     * .charAt(start) == '.' && !dot); start--) dot = dot ||
     * string.charAt(start) == '.';
     * 
     * if (start > 0 && string.charAt(start) != '.') start++; return start; }
     */

    public static int getJavaScriptStart(String string, int end) {
        int start;
        if (end < 1) {
            end = 1;
        }
        for (start = end - 1; start > 0
                && (Character.isJavaIdentifierPart(string.charAt(start))
                        || string.charAt(start) == '.'
                        || string.charAt(start) == '['
                        || string.charAt(start) == ']'
                        || string.charAt(start) == '('
                        || string.charAt(start) == ')' || Character
                            .isWhitespace(string.charAt(start))); start--) {
            ;
        }

        if (start > 0 && string.charAt(start) != '.')
            start++;
        return start;
    }

    public static String getWordAt(String string, int offset) {
        int start = offset;
        int end = offset;
        for (; start > 0
                && Character.isJavaIdentifierPart(string.charAt(start)); start--)
            ;
        for (; end <= string.length()
                && Character.isJavaIdentifierPart(string.charAt(end)); end++)
            ;
        if (start + 2 < end)
            return string.substring(start + 1, end);
        else
            return ""; //$NON-NLS-1$
    }

}
