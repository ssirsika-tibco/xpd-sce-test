/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * @author nwilson
 */
public class SubProcMapperScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public SubProcMapperScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.SUB_PROCESS_TASK;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new SubProcAndCatchBpmnErrorEventScriptProvider(scriptGrammar,
                getMappingDirection());
    }

}
