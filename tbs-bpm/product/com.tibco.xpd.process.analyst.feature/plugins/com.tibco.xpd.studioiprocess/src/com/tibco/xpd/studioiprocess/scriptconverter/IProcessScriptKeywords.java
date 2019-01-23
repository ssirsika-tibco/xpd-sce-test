/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;

/**
 * Keywords from iProcessScript grammar.
 * 
 * @author aallway
 * @since 20 May 2011
 */
public class IProcessScriptKeywords {

    public static final String IF = "IF"; //$NON-NLS-1$

    public static final String ELSEIF = "ELSEIF"; //$NON-NLS-1$

    public static final String ELSE = "ELSE"; //$NON-NLS-1$

    public static final String ENDIF = "ENDIF"; //$NON-NLS-1$

    public static final String WHILE = "WHILE"; //$NON-NLS-1$

    public static final String WEND = "WEND"; //$NON-NLS-1$

    public static final String AND = "AND"; //$NON-NLS-1$

    public static final String OR = "OR"; //$NON-NLS-1$

    public static final String NOT = "NOT"; //$NON-NLS-1$

    public static final String NOT_EQUAL = "<>"; //$NON-NLS-1$

    public static final String ASSIGNMENT_EQUAL = ":="; //$NON-NLS-1$

    public static final String CONDITION_EQUAL = "="; //$NON-NLS-1$

    public static boolean isConditionKeyword(String word) {
        if (IF.equalsIgnoreCase(word) || ELSEIF.equalsIgnoreCase(word)
                || ELSE.equalsIgnoreCase(word) || ENDIF.equalsIgnoreCase(word)
                || WHILE.equalsIgnoreCase(word) || WEND.equalsIgnoreCase(word)
                || AND.equalsIgnoreCase(word) || OR.equalsIgnoreCase(word)) {
            return true;
        }
        return false;
    }
}
