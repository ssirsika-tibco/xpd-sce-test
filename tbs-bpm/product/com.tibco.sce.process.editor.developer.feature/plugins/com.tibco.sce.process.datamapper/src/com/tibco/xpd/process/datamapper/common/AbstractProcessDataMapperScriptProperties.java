/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.datamapper.common;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Data Mapper scripts info provider will be sub-classed by data mapper sections
 * to provide their respective scriptsInfoProvider based on their context.
 * 
 * @author Ali
 * @since 21 Jan 2015
 */
public abstract class AbstractProcessDataMapperScriptProperties extends
        MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public AbstractProcessDataMapperScriptProperties() {
        super(MappingDirection.IN);
    }

    /**
     * 
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptInfoProvider(java.lang.String)
     * 
     * @param scriptGrammar
     * @return
     */
    @Override
    protected final AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        return getScriptSectionInfoProvider();
    }

    /**
     * @return The user defined Mapping Script info provider for this data
     *         mapper script context.
     */
    protected abstract AbstractScriptInfoProvider getScriptSectionInfoProvider();

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties#getCurrentSetScriptGrammarId()
     * 
     * @return
     */
    @Override
    public String getCurrentSetScriptGrammarId() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }
}
