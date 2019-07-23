/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;

/**
 * Script Provider for Catch Global Signal Map to Signal Script.
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public class CatchGlobalSignalMapperScriptProvider extends
        AbstractCatchSignalMapperScriptProvider {

    /**
     * @param mappingDirection
     */
    public CatchGlobalSignalMapperScriptProvider(String grammar,
            MappingDirection direction) {
        super(grammar, direction);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.CatchGlobalSignalMapperScriptProvider_CatchGlobalSignalScriptMAppingSection_title;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.mapping.AbstractCatchSignalMapperScriptProvider#getCommandLabel()
     *
     * @return
     */
    @Override
    protected String getCommandLabel() {
        return Messages.CatchGlobalSignalMapperScriptProvider_SetGlobalSignalScriptCommand_label;
    }
}
