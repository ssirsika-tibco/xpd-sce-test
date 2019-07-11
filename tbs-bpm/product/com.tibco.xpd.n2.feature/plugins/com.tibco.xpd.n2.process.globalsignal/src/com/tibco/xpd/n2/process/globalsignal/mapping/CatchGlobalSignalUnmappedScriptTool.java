/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Script Tool specific to Catch Global Signal unmapped events.
 * 
 * @author ssirsika
 * @since 02-May-2016
 */
public class CatchGlobalSignalUnmappedScriptTool extends
        AbstractGlobalSignalMappingScriptTool {

    /**
     * @param mappingScript
     */
    public CatchGlobalSignalUnmappedScriptTool(ScriptInformation mappingScript) {
        super(mappingScript);
    }

    /**
     * @see com.tibco.xpd.js.validation.tools.ScriptTool#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }
}
