/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger INPUT PAYLOAD mapping script for
 * primitive BOOLEAN ARRAY ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputArrayBooleanPayloadTest extends SwaggerScriptGeneratorExecutionTest
{
	/**
	 * @see com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerScriptGeneratorExecutionTest#getProjectsForTest()
	 *
	 * @return
	 */
	@Override
	protected String[] getProjectsForTest()
	{
		return new String[]{"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_REST/",
				"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_Data/",
				"resources/SwaggerScriptGenTests/SwaggerScriptGenTests_Process/"};
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * BOOLEAN ARRAY PAYLOAD from BOOLEAN ARRAY FIELD / ATTRIBUTE
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to BOOLEAN ARRAY input payload - mapping SOURCE process BOOLEAN ARRAY FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_BooleanArray_From_BooleanField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[]");
	}

	/**
	 * Test mapping to BOOLEAN ARRAY input payload - mapping SOURCE process BOOLEAN ARRAY FIELD = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_BooleanArray_From_BooleanField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.BooleanArrayField= [true, false, true, false];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[true,false,true,false]");
	}

	/**
	 * Test mapping to BOOLEAN ARRAY input payload - mapping SOURCE process BOOLEAN ARRAY ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_BooleanArray_From_BooleanAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayInput - from Boolean Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be empty array */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[]");
	}

	/**
	 * Test mapping to BOOLEAN ARRAY input payload - mapping SOURCE process BOOLEAN ARRAY ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_BooleanArray_From_BooleanAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayInput - from Boolean Attr",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.boolean1= [true, false, true, false];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the value.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[true,false,true,false]");
	}

}
