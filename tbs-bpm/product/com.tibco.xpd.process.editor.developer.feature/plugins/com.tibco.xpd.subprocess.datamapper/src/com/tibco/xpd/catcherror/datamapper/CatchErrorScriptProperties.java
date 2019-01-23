/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;

import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script section for Catch Error scripts.
 * 
 * @author sajain
 * @since Feb 26, 2016
 */
public class CatchErrorScriptProperties extends MapperScriptProperties {

    private final String dataMapperContext;

    /**
     * @param mappingDirection
     *            The mapping direction of the mapper.
     * @param dataMapperContext
     */
    public CatchErrorScriptProperties(MappingDirection mappingDirection,
            String dataMapperContext) {
        super(mappingDirection);
        this.dataMapperContext = dataMapperContext;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return The script context (not section context) for this script.
     */
    @Override
    public String getScriptContext() {

        /*
         * TODO Saket This is getting used to set the default grammar. Use same
         * as CatchBpmnErrorMapperScriptProperties. Is it correct, verify with
         * Sid
         */
        return ProcessScriptContextConstants.SUB_PROCESS_TASK;
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
                new CatchErrorScriptDataMapperProvider(dataMapperContext));
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
