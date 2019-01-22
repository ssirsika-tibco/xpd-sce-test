/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.process.globalsignal.mapping;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script Properties for Throw Global Signal Map to Signal Script.
 * 
 * 
 * @author kthombar
 * @since Feb 4, 2015
 */
public class ThrowGlobalSignalMapperScriptProperties extends
        MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public ThrowGlobalSignalMapperScriptProperties() {
        super(MappingDirection.IN);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new ThrowGlobalSignalMapperScriptProvider(scriptGrammar,
                getMappingDirection());
    }

}
