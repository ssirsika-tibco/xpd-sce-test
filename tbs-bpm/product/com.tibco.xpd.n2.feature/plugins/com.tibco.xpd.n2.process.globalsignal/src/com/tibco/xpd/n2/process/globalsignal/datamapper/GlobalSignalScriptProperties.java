/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper;

import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Global Signal Script properties.
 * 
 * @author sajain
 * @since Apr 26, 2016
 */
public class GlobalSignalScriptProperties extends MapperScriptProperties {

    /**
     * The mapping direction.
     */
    private MappingDirection mappingDirection;

    /**
     * @param mappingDirection
     *            The mapping direction of the mapper.
     */
    public GlobalSignalScriptProperties(MappingDirection mappingDirection) {

        super(mappingDirection);
        this.mappingDirection = mappingDirection;

    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return The script context (not section context) for this script.
     */
    @Override
    public String getScriptContext() {

        return MappingDirection.IN.equals(mappingDirection) ? ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING
                : ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
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
                new GlobalSignalScriptDataMapperProvider(getMappingDirection()));
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties#getCurrentSetScriptGrammarId()
     * 
     * @return
     */
    @Override
    public String getCurrentSetScriptGrammarId() {

        /*
         * Default implementation of this method uses ScriptGrammarFactory to
         * decide what the default grammar which NOW may return "DataMapper".
         * However, the user defined mapping scripts are actually JavaScript
         * even in DataMapper - so have to coerce for script editing to
         * JavaScript.
         */
        return ScriptGrammarFactory.JAVASCRIPT;
    }

}
