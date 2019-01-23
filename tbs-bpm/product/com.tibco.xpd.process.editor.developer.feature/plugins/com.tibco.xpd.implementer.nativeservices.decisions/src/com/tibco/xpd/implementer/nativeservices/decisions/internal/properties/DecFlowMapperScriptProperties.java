/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * @author mtorres
 */
public class DecFlowMapperScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public DecFlowMapperScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.DEC_FLOW_SERVICE_TASK;
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return new DecFlowServiceTaskScriptProvider(scriptGrammar,
                getMappingDirection());
    }

}
