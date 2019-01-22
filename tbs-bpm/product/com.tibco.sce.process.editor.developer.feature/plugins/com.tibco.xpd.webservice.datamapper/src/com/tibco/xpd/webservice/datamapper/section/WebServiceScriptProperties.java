/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.webservice.datamapper.WebServiceScriptDataMapperProvider;

/**
 * Script section for Web Service scripts.
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class WebServiceScriptProperties extends MapperScriptProperties {

    /**
     * Data mapper context.
     */
    private String mapperContext;

    /**
     * @param mappingDirection
     *            The mapping direction of the mapper.
     */
    public WebServiceScriptProperties(String mapperContext,
            MappingDirection mappingDirection) {
        super(mappingDirection);
        this.mapperContext = mapperContext;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return The script context (not section context) for this script.
     */
    @Override
    public String getScriptContext() {

        return ProcessScriptContextConstants.WEB_SERVICE_TASK;
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
                new WebServiceScriptDataMapperProvider(mapperContext,
                        getMappingDirection()));
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
