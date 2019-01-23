/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Script Tool specific to Throw Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 4, 2015
 */
public class ThrowGlobalSignalMappingScriptTool extends
        AbstractGlobalSignalMappingScriptTool {

    /**
     * @param mappingScript
     */
    public ThrowGlobalSignalMappingScriptTool(ScriptInformation mappingScript) {
        super(mappingScript);

    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
    }
}
