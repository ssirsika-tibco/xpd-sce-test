/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to return the list of all Global data reserved words.
 * 
 * @author sajain
 * @since Dec 16, 2013
 */
public class GlobalDataReservedWords {
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

            // Global data reserved words
            symbolTableKeyWords.add("globalDataRef"); //$NON-NLS-1$

        }
    }

}