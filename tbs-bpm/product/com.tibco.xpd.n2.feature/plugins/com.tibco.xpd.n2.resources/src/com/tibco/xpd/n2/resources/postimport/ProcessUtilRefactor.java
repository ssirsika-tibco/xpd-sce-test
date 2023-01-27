/*
 * (c) 2004-2023 Cloud Software Group, Inc.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Map;

/**
 * Replace AMX BPM script Process methods with the equivalent BPMe bpm.scriptUtil methods.
 *
 * @author aallway
 * @since Jan 2023
 */
public class ProcessUtilRefactor extends AbstractScriptClassRefactor {
    private static final String OLD_UTIL = "Process"; //$NON-NLS-1$

    private static final String NEW_UTIL = "bpm.process"; //$NON-NLS-1$

    /**
     * Creates a MethodRefactorRule to replace multiple method names with a corresponding value from ScriptUtil class to
     * bpm.scriptUtil class.
     * 
     * @param aMethodMap
     *            the map of method names to be replaced. The key is the old method name.
     */
    public ProcessUtilRefactor(Map<String, String> aMethodMap) {
        super(OLD_UTIL, NEW_UTIL, aMethodMap, true);
    }

}
