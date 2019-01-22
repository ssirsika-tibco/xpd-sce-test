/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Collection of Java reserved words for validating against variable names
 * 
 * @author wzurek
 * 
 */
public class JavaKeywords {
    private JavaKeywords() {
    };

    private static final Set<String> reservedWords = new HashSet<String>();

    static {
        // reservedWords.add("Event"); //$NON-NLS-1$
        // reservedWords.add("TimeEvent"); //$NON-NLS-1$
        // reservedWords.add("Concept"); //$NON-NLS-1$
        // reservedWords.add("ContainedConcept"); //$NON-NLS-1$
        // reservedWords.add("SimpleEvent"); //$NON-NLS-1$

        reservedWords.add("abstract"); //$NON-NLS-1$
        reservedWords.add("continue"); //$NON-NLS-1$
        reservedWords.add("for"); //$NON-NLS-1$
        reservedWords.add("new"); //$NON-NLS-1$
        reservedWords.add("switch"); //$NON-NLS-1$
        reservedWords.add("assert***"); //$NON-NLS-1$
        reservedWords.add("default"); //$NON-NLS-1$
        reservedWords.add("goto*"); //$NON-NLS-1$
        reservedWords.add("package"); //$NON-NLS-1$
        reservedWords.add("synchronized"); //$NON-NLS-1$
        reservedWords.add("boolean"); //$NON-NLS-1$
        reservedWords.add("do"); //$NON-NLS-1$
        reservedWords.add("if"); //$NON-NLS-1$
        reservedWords.add("private"); //$NON-NLS-1$
        reservedWords.add("this"); //$NON-NLS-1$
        reservedWords.add("break"); //$NON-NLS-1$
        reservedWords.add("double"); //$NON-NLS-1$
        reservedWords.add("implements"); //$NON-NLS-1$
        reservedWords.add("protected"); //$NON-NLS-1$
        reservedWords.add("throw"); //$NON-NLS-1$
        reservedWords.add("byte"); //$NON-NLS-1$
        reservedWords.add("else"); //$NON-NLS-1$
        reservedWords.add("import"); //$NON-NLS-1$
        reservedWords.add("public"); //$NON-NLS-1$
        reservedWords.add("throws"); //$NON-NLS-1$
        reservedWords.add("case"); //$NON-NLS-1$
        reservedWords.add("enum****"); //$NON-NLS-1$
        reservedWords.add("instanceof"); //$NON-NLS-1$
        reservedWords.add("return"); //$NON-NLS-1$
        reservedWords.add("transient"); //$NON-NLS-1$
        reservedWords.add("catch"); //$NON-NLS-1$
        reservedWords.add("extends"); //$NON-NLS-1$
        reservedWords.add("int"); //$NON-NLS-1$
        reservedWords.add("short"); //$NON-NLS-1$
        reservedWords.add("try"); //$NON-NLS-1$
        reservedWords.add("char"); //$NON-NLS-1$
        reservedWords.add("final"); //$NON-NLS-1$
        reservedWords.add("interface"); //$NON-NLS-1$
        reservedWords.add("static"); //$NON-NLS-1$
        reservedWords.add("void"); //$NON-NLS-1$
        reservedWords.add("class"); //$NON-NLS-1$
        reservedWords.add("finally"); //$NON-NLS-1$
        reservedWords.add("long"); //$NON-NLS-1$
        reservedWords.add("strictfp**"); //$NON-NLS-1$
        reservedWords.add("volatile"); //$NON-NLS-1$
        reservedWords.add("const*"); //$NON-NLS-1$
        reservedWords.add("float"); //$NON-NLS-1$
        reservedWords.add("native"); //$NON-NLS-1$
        reservedWords.add("super"); //$NON-NLS-1$
        reservedWords.add("while"); //$NON-NLS-1$
    }

    public static Set<String> getReservedWords() {

        return reservedWords;
    }
}
