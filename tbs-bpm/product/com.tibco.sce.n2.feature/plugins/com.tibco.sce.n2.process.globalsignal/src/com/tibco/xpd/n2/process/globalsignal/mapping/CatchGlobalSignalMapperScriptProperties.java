/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script Properties for Catch Global Signal Map to Signal Script.
 * 
 * @author kthombar
 * @since Feb 12, 2015
 */
public class CatchGlobalSignalMapperScriptProperties extends
        MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public CatchGlobalSignalMapperScriptProperties() {
        super(MappingDirection.OUT);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new CatchGlobalSignalMapperScriptProvider(scriptGrammar,
                getMappingDirection());
    }
}
