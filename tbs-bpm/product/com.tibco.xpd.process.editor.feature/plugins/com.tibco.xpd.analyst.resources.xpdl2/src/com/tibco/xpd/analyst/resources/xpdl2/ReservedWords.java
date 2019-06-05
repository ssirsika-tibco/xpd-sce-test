/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2;

import java.util.ArrayList;
import java.util.List;

/**
 * class to return the list of all JavaScript reserved words, JavaScript
 * keywords, Java keywords, and ECMAScript reserved words
 * 
 * 
 * @author bharge
 * @since 3.3 (14 Jul 2010)
 */
public class ReservedWords {

    /**
     * Sid ACE-1317 the name of the process data wrapper object introduced for
     * ACE.
     */
    public static final String PROCESS_DATA_WRAPPER_OBJECT_NAME = "data"; //$NON-NLS-1$

    private static List<String> symbolTableKeyWords;

    public static List<String> getSymbolTableKeyWords() {
        return symbolTableKeyWords;
    }

    static {
        populateReservedWords();
    }

    /**
     * 
     */
    private static void populateReservedWords() {
        if (null == symbolTableKeyWords) {
            symbolTableKeyWords = new ArrayList<String>();

            // JavaScript reserved words
            symbolTableKeyWords.add("break"); //$NON-NLS-1$
            symbolTableKeyWords.add("case"); //$NON-NLS-1$
            symbolTableKeyWords.add("continue"); //$NON-NLS-1$
            symbolTableKeyWords.add("default"); //$NON-NLS-1$
            symbolTableKeyWords.add("delete"); //$NON-NLS-1$
            symbolTableKeyWords.add("do"); //$NON-NLS-1$
            symbolTableKeyWords.add("else"); //$NON-NLS-1$
            symbolTableKeyWords.add("export"); //$NON-NLS-1$
            symbolTableKeyWords.add("for"); //$NON-NLS-1$
            symbolTableKeyWords.add("function"); //$NON-NLS-1$
            symbolTableKeyWords.add("if"); //$NON-NLS-1$
            symbolTableKeyWords.add("import"); //$NON-NLS-1$
            symbolTableKeyWords.add("in"); //$NON-NLS-1$
            symbolTableKeyWords.add("new"); //$NON-NLS-1$
            symbolTableKeyWords.add("return"); //$NON-NLS-1$
            symbolTableKeyWords.add("switch"); //$NON-NLS-1$
            symbolTableKeyWords.add("this"); //$NON-NLS-1$
            symbolTableKeyWords.add("typeof"); //$NON-NLS-1$
            symbolTableKeyWords.add("var"); //$NON-NLS-1$
            symbolTableKeyWords.add("void"); //$NON-NLS-1$
            symbolTableKeyWords.add("while"); //$NON-NLS-1$
            symbolTableKeyWords.add("with"); //$NON-NLS-1$

            // Java Keywords (reserved by JavaScript)
            symbolTableKeyWords.add("abstract"); //$NON-NLS-1$
            symbolTableKeyWords.add("byte"); //$NON-NLS-1$
            symbolTableKeyWords.add("char"); //$NON-NLS-1$
            symbolTableKeyWords.add("double"); //$NON-NLS-1$
            symbolTableKeyWords.add("false"); //$NON-NLS-1$
            symbolTableKeyWords.add("final"); //$NON-NLS-1$
            symbolTableKeyWords.add("float"); //$NON-NLS-1$
            symbolTableKeyWords.add("goto"); //$NON-NLS-1$
            symbolTableKeyWords.add("implements"); //$NON-NLS-1$
            symbolTableKeyWords.add("int"); //$NON-NLS-1$
            symbolTableKeyWords.add("interface"); //$NON-NLS-1$
            symbolTableKeyWords.add("long"); //$NON-NLS-1$
            symbolTableKeyWords.add("native"); //$NON-NLS-1$
            symbolTableKeyWords.add("null"); //$NON-NLS-1$
            symbolTableKeyWords.add("package"); //$NON-NLS-1$
            symbolTableKeyWords.add("private"); //$NON-NLS-1$
            symbolTableKeyWords.add("protected"); //$NON-NLS-1$
            symbolTableKeyWords.add("public"); //$NON-NLS-1$
            symbolTableKeyWords.add("short"); //$NON-NLS-1$
            symbolTableKeyWords.add("static"); //$NON-NLS-1$
            symbolTableKeyWords.add("synchronized"); //$NON-NLS-1$
            symbolTableKeyWords.add("throws"); //$NON-NLS-1$
            symbolTableKeyWords.add("transient"); //$NON-NLS-1$
            symbolTableKeyWords.add("true"); //$NON-NLS-1$

            // ECMAScript reserved words
            symbolTableKeyWords.add("catch"); //$NON-NLS-1$
            symbolTableKeyWords.add("enum"); //$NON-NLS-1$
            symbolTableKeyWords.add("throw"); //$NON-NLS-1$
            symbolTableKeyWords.add("class"); //$NON-NLS-1$
            symbolTableKeyWords.add("extends"); //$NON-NLS-1$
            symbolTableKeyWords.add("try"); //$NON-NLS-1$
            symbolTableKeyWords.add("const"); //$NON-NLS-1$
            symbolTableKeyWords.add("finally"); //$NON-NLS-1$
            symbolTableKeyWords.add("debugger"); //$NON-NLS-1$
            symbolTableKeyWords.add("super"); //$NON-NLS-1$

            // Other JavaScript keywords
            symbolTableKeyWords.add("Array"); //$NON-NLS-1$
            symbolTableKeyWords.add("Math"); //$NON-NLS-1$
            symbolTableKeyWords.add("Boolean"); //$NON-NLS-1$
            symbolTableKeyWords.add("String"); //$NON-NLS-1$
            symbolTableKeyWords.add("Number"); //$NON-NLS-1$
            symbolTableKeyWords.add("Object"); //$NON-NLS-1$
            symbolTableKeyWords.add("RegExp"); //$NON-NLS-1$
            symbolTableKeyWords.add("Date"); //$NON-NLS-1$
            symbolTableKeyWords.add("RETURN_VALUE"); //$NON-NLS-1$
            symbolTableKeyWords.add("INPUT_MESSAGE"); //$NON-NLS-1$
            symbolTableKeyWords.add("OUTPUT_MESSAGE"); //$NON-NLS-1$

            /*
             * Sid ACE-1317 In ACE all process data is wrapped in an object
             * called "data" therefore this is a reserved word.
             */
            symbolTableKeyWords.add(PROCESS_DATA_WRAPPER_OBJECT_NAME);

        }
    }

}
