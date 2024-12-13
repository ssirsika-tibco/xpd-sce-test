/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.swagger.datamapper;

import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.datamapper.scripts.DataMapperUserDefinedMappingScriptsProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * Script properties section for use with the Swagger Data Mapper.
 *
 * @author nkelkar
 * @since Sep 30, 2024
 */
public class SwaggerMapperScriptProperties extends MapperScriptProperties
{

	private AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider;
	/**
	 * @param mappingDirection
	 *            The mapping direction of the mapper.
	 */
	public SwaggerMapperScriptProperties(MappingDirection mappingDirection,
			AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider)
	{
		super(mappingDirection);
		this.scriptDataMapperEditorProvider = scriptDataMapperEditorProvider;
	}

	/**
	 * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptContext()
	 * 
	 * @return The script context (not section context) for this script.
	 */
	@Override
	public String getScriptContext()
	{
		return MappingDirection.IN.equals(getMappingDirection())
				? ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS
				: ProcessScriptContextConstants.DATA_MAPPER_SWAGGER_MAPPING_SCRIPTS;
	}

	/**
	 * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#getScriptInfoProvider(java.lang.String)
	 * 
	 * @param grammarId
	 * @return
	 */
	@Override
	protected AbstractScriptInfoProvider getScriptInfoProvider(String grammarId)
	{
		return new DataMapperUserDefinedMappingScriptsProvider(this.scriptDataMapperEditorProvider);
	}

}
