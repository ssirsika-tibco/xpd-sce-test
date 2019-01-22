/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.implementer.resources.xpdl2.properties.SubProcAndCatchBpmnErrorEventScriptProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchBpmnErrorMapperScriptProperties extends
        MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public CatchBpmnErrorMapperScriptProperties() {
        super(MappingDirection.OUT);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.SUB_PROCESS_TASK;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new SubProcAndCatchBpmnErrorEventScriptProvider(scriptGrammar,
                MappingDirection.OUT);
    }
}
