/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * Sid ACE-ACE-1599 the name of the sub-process parameters wrapping object
     * for sub-process mapping scripts introduced for ACE.
     */
    public static final String SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME = "parameters"; //$NON-NLS-1$

    /**
     * Sid ACE-542 the name of the BOM class factory wrapper object introduced
     * for ACE.
     */
    public static final String BOM_FACTORY_WRAPPER_OBJECT_NAME = "factory"; //$NON-NLS-1$

    /**
     * Sid ACE-542 the name of the BOM package wrapper object introduced for
     * ACE.
     */
    public static final String BOM_PACKAGE_WRAPPER_OBJECT_NAME = "pkg"; //$NON-NLS-1$

    /**
     * Sid ACE-2088 the "bpm" object that the utility scripts are now located
     * within should be a reserved word.
     */
    public static final String BPM_UTIL_CLASS_WRAPPER_OBJECT_NAME = "bpm"; //$NON-NLS-1$

    /**
     * Sid ACE-2020 the name of the Global Signal Payload parameter prefix (e.g.
     * SIGNAL_payloadParam1) used in generated BPEL to create unique variables
     */
    public static final String BX_SIGNAL_PAYLOAD_PREFIX = "SIGNAL_"; //$NON-NLS-1$

    /**
     * Sid ACE-1694 The prefix often used for temporary variables
     */
    public static final String BX_TEMP_VAR_PREFIX = "_BX"; //$NON-NLS-1$

    /**
     * Sid ACE-1599 The prefix used for ERROR_DETAIL and ERROR_CODE temporary
     * variables
     */
    public static final String BX_ERROR_VAR_PREFIX = "var_"; //$NON-NLS-1$

    /**
     * Sid ACE-1599 The prefix used for ERROR_DETAIL and ERROR_CODE temporary
     * variables
     */
    public static final String BX_PROCESS_ID_VAR_NAME = "__PROCESS_ID__"; //$NON-NLS-1$

	/**
	 * ACE-7400 : name of the process script library (PSL) wrapper.
	 */
	public static final String	PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME		= "bpmScripts";		//$NON-NLS-1$

    /* Sid ACE-1118 Changed to set for lookup efficiency */
    private static Set<String> symbolTableKeyWords;

    private static List<String> prefixReservedKeyWords;


    static {
        populateReservedWords();
    }

    /**
     * 
     */
    private static void populateReservedWords() {
        if (null == symbolTableKeyWords) {
            symbolTableKeyWords = new HashSet<String>();
            prefixReservedKeyWords = new ArrayList<String>();

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
             * 
             * Sid ACE-XXXX + others...
             */
            symbolTableKeyWords.add(PROCESS_DATA_WRAPPER_OBJECT_NAME);
            symbolTableKeyWords.add(BPM_UTIL_CLASS_WRAPPER_OBJECT_NAME);

            /**
             * Sid ACE-ACE-1599 the name of the sub-process parameters wrapping
             * object for sub-process mapping scripts introduced for ACE.
             */
            symbolTableKeyWords.add(SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME);
            symbolTableKeyWords.add(BX_PROCESS_ID_VAR_NAME);

            /**
             * Sid ACE-542 the name of the BOM class factory/pkg wrapper object
             * introduced for ACE.
             */
            symbolTableKeyWords.add(BOM_FACTORY_WRAPPER_OBJECT_NAME);
            symbolTableKeyWords.add(BOM_PACKAGE_WRAPPER_OBJECT_NAME);


            prefixReservedKeyWords.add(BX_SIGNAL_PAYLOAD_PREFIX);
            prefixReservedKeyWords.add(BX_TEMP_VAR_PREFIX);
            prefixReservedKeyWords.add(BX_ERROR_VAR_PREFIX);
        }
    }

    /**
     * @param word
     * @return <code>true</code> if word is on the reserved words list.
     */
    public static boolean isReservedWord(String word) {
        return symbolTableKeyWords.contains(word);
    }

    /**
     * If the given word starts with a reserved prefix keyword then return that
     * prefix
     * 
     * @param word
     * @return {@link String} reserved prefix that the word starts with or
     *         <code>null</code> if word does not start with a reserved prefix
     */
    public static String getReservedPrefix(String word) {
        for (String prefix : prefixReservedKeyWords) {
            if (word.startsWith(prefix)) {
                return prefix;
            }
        }
        return null;
    }
}
