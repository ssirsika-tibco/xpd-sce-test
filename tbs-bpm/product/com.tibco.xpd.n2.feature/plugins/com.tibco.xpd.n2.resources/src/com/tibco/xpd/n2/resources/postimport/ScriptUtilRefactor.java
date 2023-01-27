/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Map;

/**
 * Replace AMX BPM script Process methods with the equivalent BPMe bpm.scriptUtil methods.
 *
 * Sid ACE-6615 Class content refactored into {@link AbstractScriptClassRefactor}
 * 
 * @author pwatson
 * @since 12 Aug 2019
 */
class ScriptUtilRefactor extends AbstractScriptClassRefactor {
    private static final String OLD_UTIL = "ScriptUtil"; //$NON-NLS-1$

    private static final String NEW_UTIL = "bpm.scriptUtil"; //$NON-NLS-1$

    /**
     * Creates a MethodRefactorRule to replace multiple method names with a corresponding value from ScriptUtil class to
     * bpm.scriptUtil class.
     * 
     * @param aMethodMap
     *            the map of method names to be replaced. The key is the old method name.
     */
    public ScriptUtilRefactor(Map<String, String> aMethodMap) {
        super(OLD_UTIL, NEW_UTIL, aMethodMap, false);
    }

}
