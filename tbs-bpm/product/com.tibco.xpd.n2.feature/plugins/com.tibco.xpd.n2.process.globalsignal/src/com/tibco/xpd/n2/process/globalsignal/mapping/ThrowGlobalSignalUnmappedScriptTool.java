/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * Script Tool specific to Throw Global Signal unmapped events.
 * 
 * @author ssirsika
 * @since 03-May-2016
 */
public class ThrowGlobalSignalUnmappedScriptTool extends
        AbstractGlobalSignalMappingScriptTool {

    /**
     * @param mappingScript
     */
    public ThrowGlobalSignalUnmappedScriptTool(ScriptInformation mappingScript) {
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
