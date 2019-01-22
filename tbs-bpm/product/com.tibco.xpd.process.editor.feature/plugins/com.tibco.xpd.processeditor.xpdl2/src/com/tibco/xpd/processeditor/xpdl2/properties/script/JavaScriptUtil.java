/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

/**
 * @author nwilson
 */
public final class JavaScriptUtil {

    /** Includes optional spaces, checks for but excludes + or end of string. */
    private static final String END_PLUS = "\\s*(?=(\\z|\\+))"; //$NON-NLS-1$

    /** Checks & excludes string start or +, then includes optional spaces. */
    private static final String START_PLUS = "(?=(\\A|\\+))\\s*"; //$NON-NLS-1$

    /** Checks & excludes string start or + followed by optional spaces. */
    private static final String START = "(?=(\\A|\\+\\s*))"; //$NON-NLS-1$

    /** Checks & excludes end of string or optional spaces followed by +. */
    private static final String END = "(?=(\\z|\\s*\\+))"; //$NON-NLS-1$

    /** Includes + surrounded by optional spaces. */
    private static final String PLUS = "\\s*\\+\\s*"; //$NON-NLS-1$

    /** Checks & excludes end of string or optional spaces followed by +. */
    private static final String Q = "\\Q"; //$NON-NLS-1$

    /** Includes + surrounded by optional spaces. */
    private static final String E = "\\E"; //$NON-NLS-1$

    /** Valid javascript identifier. */
    private static final String IDENTIFIER = "[a-zA-Z_$][a-zA-Z0-9_$]*"; //$NON-NLS-1$

    /**
     * Private constructor.
     */
    private JavaScriptUtil() {
    }

    /**
     * @param script
     *            The script to remove the parameter from.
     * @param parameter
     *            The parameter to remove.
     * @return The modified script.
     */
    public static String removeParameter(String script, String parameter) {
        // When a parameter has changed modes or been removed then it may be
        // null here so have to cope else can't remove broken mapping.
        if (parameter == null || parameter.length() == 0) {
            return ""; //$NON-NLS-1$
        }
        
        int semi = script.lastIndexOf(';');
        if (semi != -1) {
            script = script.substring(0, semi);
        }

//        if (parameter.contains(" ")) { //$NON-NLS-1$
//            parameter = "this['" + parameter + "']"; //$NON-NLS-1$ //$NON-NLS-2$
//        }
        String regex1 = PLUS + Q + parameter + E + END;
        String regex2 = START + Q + parameter + E + PLUS;
        String regex3 = START_PLUS + Q + parameter + E + END_PLUS;
        String modified = script.replaceAll(regex1, ""); //$NON-NLS-1$
        modified = modified.replaceAll(regex2, ""); //$NON-NLS-1$
        modified = modified.replaceAll(regex3, ""); //$NON-NLS-1$
        if (semi != -1) {
            modified += ";"; //$NON-NLS-1$
        }
        return modified;
    }

    /**
     * @param name
     *            The identifier name to check.
     * @return true if it allowed as a JavaScript identifier.
     */
    public static boolean isValidJavascriptIdentifier(String name) {
        return name.matches(IDENTIFIER);
    }
}
