/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.signalevent;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Catch signal event mapping script properties section.
 * 
 * @author aallway
 * @since 30 Apr 2012
 */
public class CatchSignalMapperScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public CatchSignalMapperScriptProperties() {
        super(MappingDirection.OUT);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new CatchSignalEventScriptProvider(scriptGrammar,
                getMappingDirection());
    }

}
