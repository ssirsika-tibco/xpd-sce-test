package com.tibco.xpd.xpdl2.edit.util;

/**
 * Field assist helper that allows all charactors entered to pop up the assistance box
 * @author glewis
 *
 */
public class FieldAssistUtil {
    private FieldAssistUtil() { }

    public static final char[] getAlphaNumericChars() {
        String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; //$NON-NLS-1$
        String deleteChar = new String(new char[] {8});
    
        String combo = alphanum + deleteChar;

        return combo.toCharArray();
    }
}
