package com.tibco.xpd.ui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NameUtil {

    public static String INVALID = "[^a-zA-Z0-9_]"; //$NON-NLS-1$

    private static Pattern VALID = Pattern.compile("[a-zA-Z0-9_]*"); //$NON-NLS-1$

    private static Pattern VALID2 = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"); //$NON-NLS-1$

    public static final String JAVA_PACKAGE_SEPARATOR = "\\."; //$NON-NLS-1$

    public static final String UML_PACKAGE_SEPARATOR = "::"; //$NON-NLS-1$
    private NameUtil() {
    }

    /**
     * Provides an internal name from a display name by removing anything other
     * than alpha-numeric characters and underscores and optionally any leading
     * numeric characters.
     * 
     * @param displayName
     * @return
     */
    public static String getInternalName(String displayName,
            boolean removeLeadingNumerics) {
        String newName = displayName.replaceAll(INVALID, ""); //$NON-NLS-1$
        if (removeLeadingNumerics) {
            while (newName.length() > 0 && Character.isDigit(newName.charAt(0))) {
                newName = newName.substring(1);
            }
        }
        return newName;
    }

    /**
     * Checks to see if a name contains only alpha-numberic and underscore
     * characters and optionally checks for leading numerics.
     * 
     * @param name
     * @return
     */
    public static boolean isValidName(String name, boolean allowLeadingNumerics) {
        boolean isValid = false;
        if (name != null && name.length() != 0) {
            Matcher matcher =
                    allowLeadingNumerics ? VALID.matcher(name) : VALID2
                            .matcher(name);
            if (matcher.matches()) {
                isValid = true;
            }
        } else {
            isValid = true;
        }
        return isValid;
    }

    /**
     * This method formats the qualified name for scripting purpose, used for
     * Enumeration. [com.example::Color/com.example.Color -> com_example_Color]
     * 
     * @param bomEnum
     * @return
     */
    public static String formatQualifiedNameForScripting(String qualifiedName) {

        return qualifiedName.replaceAll(UML_PACKAGE_SEPARATOR,
                JAVA_PACKAGE_SEPARATOR).replaceAll(JAVA_PACKAGE_SEPARATOR, "_");

    }
    
    /**
	 * XPD-8327: Sanitize the message part variable name to ensure that it's valid for javascript.
	 * 
	 * @param messagePart
	 * 
	 * @return sanitized message part variable name.
	 */
	public static String sanitizeMessagePartVariableName(String msgPartName) {
		String sanitizedMessagePartName = msgPartName;
		if (!isValidName(msgPartName, true)) {
			sanitizedMessagePartName = getInternalName(msgPartName, false);
		}
		return sanitizedMessagePartName;
	}
}
