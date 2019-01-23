/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Collection of Javascript reserved words for validating against variable names
 * 
 * @author rgreen
 * 
 */
public class JavaScriptReservedWords {
    private JavaScriptReservedWords() {
    };

    private static final Set<String> reservedWords = new HashSet<String>();

    static {

        // Javascript reserved words
        reservedWords.add("break"); //$NON-NLS-1$
        reservedWords.add("case"); //$NON-NLS-1$
        reservedWords.add("continue"); //$NON-NLS-1$
        reservedWords.add("default"); //$NON-NLS-1$
        reservedWords.add("delete"); //$NON-NLS-1$
        reservedWords.add("do"); //$NON-NLS-1$
        reservedWords.add("else"); //$NON-NLS-1$
        reservedWords.add("export"); //$NON-NLS-1$
        reservedWords.add("for"); //$NON-NLS-1$
        reservedWords.add("function"); //$NON-NLS-1$
        reservedWords.add("if"); //$NON-NLS-1$
        reservedWords.add("import"); //$NON-NLS-1$
        reservedWords.add("in"); //$NON-NLS-1$
        reservedWords.add("new"); //$NON-NLS-1$
        reservedWords.add("return"); //$NON-NLS-1$
        reservedWords.add("switch"); //$NON-NLS-1$
        reservedWords.add("this"); //$NON-NLS-1$
        reservedWords.add("typeof"); //$NON-NLS-1$
        reservedWords.add("var"); //$NON-NLS-1$
        reservedWords.add("void"); //$NON-NLS-1$
        reservedWords.add("while"); //$NON-NLS-1$
        reservedWords.add("with"); //$NON-NLS-1$

        // Java keywords reserved by Javascript
        reservedWords.add("abstract"); //$NON-NLS-1$
        reservedWords.add("byte"); //$NON-NLS-1$
        reservedWords.add("char"); //$NON-NLS-1$
        reservedWords.add("double"); //$NON-NLS-1$
        reservedWords.add("false"); //$NON-NLS-1$
        reservedWords.add("final"); //$NON-NLS-1$
        reservedWords.add("float"); //$NON-NLS-1$
        reservedWords.add("goto"); //$NON-NLS-1$
        reservedWords.add("implements"); //$NON-NLS-1$
        reservedWords.add("int"); //$NON-NLS-1$
        reservedWords.add("interface"); //$NON-NLS-1$
        reservedWords.add("long"); //$NON-NLS-1$
        reservedWords.add("native"); //$NON-NLS-1$
        reservedWords.add("null"); //$NON-NLS-1$
        reservedWords.add("package"); //$NON-NLS-1$
        reservedWords.add("private"); //$NON-NLS-1$
        reservedWords.add("protected"); //$NON-NLS-1$
        reservedWords.add("public"); //$NON-NLS-1$
        reservedWords.add("short"); //$NON-NLS-1$
        reservedWords.add("static"); //$NON-NLS-1$
        reservedWords.add("synchronized"); //$NON-NLS-1$
        reservedWords.add("throws"); //$NON-NLS-1$
        reservedWords.add("transient"); //$NON-NLS-1$
        reservedWords.add("true"); //$NON-NLS-1$

        // ECMAScript reserved words
        reservedWords.add("catch"); //$NON-NLS-1$
        reservedWords.add("enum"); //$NON-NLS-1$
        reservedWords.add("throw"); //$NON-NLS-1$
        reservedWords.add("class"); //$NON-NLS-1$
        reservedWords.add("extends"); //$NON-NLS-1$
        reservedWords.add("try"); //$NON-NLS-1$
        reservedWords.add("const"); //$NON-NLS-1$
        reservedWords.add("finally"); //$NON-NLS-1$
        reservedWords.add("debugger"); //$NON-NLS-1$
        reservedWords.add("upper"); //$NON-NLS-1$

        // Other javascript keywords
        reservedWords.add("Array"); //$NON-NLS-1$
        reservedWords.add("Math"); //$NON-NLS-1$
        reservedWords.add("Boolean"); //$NON-NLS-1$
        reservedWords.add("String"); //$NON-NLS-1$
        reservedWords.add("Number"); //$NON-NLS-1$
        reservedWords.add("Object"); //$NON-NLS-1$
        reservedWords.add("RegExp"); //$NON-NLS-1$
        reservedWords.add("Date"); //$NON-NLS-1$

    }

    public static Set<String> getReservedWords() {

        return reservedWords;
    }
}
