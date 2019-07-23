/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper;

import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script properties implementation for Local Signal mappings.
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalScriptProperties extends MapperScriptProperties {

    /**
     * Script properties implementation for Local Signal mappings.
     * 
     * @param mappingDirection
     *            The mapping direction of the mapper.
     */
    public LocalSignalScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return The script context (not section context) for this script.
     */
    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptInfoProvider(java.lang.String)
     * 
     * @param grammarId
     * @return
     */
    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(String grammarId) {
        return new DataMapperUserDefinedMappingScriptsProvider(
                new LocalSignalScriptDataMapperProvider(getMappingDirection()));
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties#getCurrentSetScriptGrammarId()
     * 
     * @return
     */
    @Override
    public String getCurrentSetScriptGrammarId() {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

}