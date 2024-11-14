/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.rest.ui.RestDataMapperConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script properties section for use with the REST Data Mapper.
 * 
 * @author nwilson
 * @since 30 Apr 2015
 */
public class RestMapperScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     *            The mapping direction of the mapper.
     */
    public RestMapperScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
     * 
     * @return The script context (not section context) for this script.
     */
	@Override
    public String getScriptContext() {
        return MappingDirection.IN.equals(getMappingDirection()) ? ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS
                : ProcessScriptContextConstants.DATA_MAPPER_REST_MAPPING_SCRIPTS;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptInfoProvider(java.lang.String)
     * 
     * @param grammarId
     * @return
     */
    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(String grammarId) {

		/* Sid ACE-8864 Use new constructor for RestScriptDataMapperProvider */
        MappingDirection direction = getMappingDirection();

		String mapperContext = MappingDirection.IN.equals(direction) ? RestDataMapperConstants.PROCESS_TO_REST_SERVICE
				: RestDataMapperConstants.REST_SERVICE_TO_PROCESS;

		return new DataMapperUserDefinedMappingScriptsProvider(
				new RestScriptDataMapperProvider(direction, mapperContext));
    }

}
