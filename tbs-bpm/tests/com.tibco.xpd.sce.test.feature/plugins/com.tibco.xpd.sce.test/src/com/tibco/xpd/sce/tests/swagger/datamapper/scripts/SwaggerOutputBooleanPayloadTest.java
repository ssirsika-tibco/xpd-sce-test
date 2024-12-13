/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger OUTPUT PAYLOAD mapping script for
 * primitive BOOLEAN type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputBooleanPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * BOOLEAN FIELD / ATTRIBUTE from BOOLEAN PAYLOAD
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
	 * Test mapping to BOOLEAN FIELD - mapping SOURCE is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Field", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(204, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.BooleanField", null);
		assertVariableValue(scriptContext, "data.BooleanFieldFromHeader", null);
	}

	/**
	 * Test mapping to BOOLEAN FIELD - mapping SOURCE is = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanField_True() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Field", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify(true);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = true;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.BooleanField", Boolean.TRUE);
		assertVariableValue(scriptContext, "data.BooleanFieldFromHeader", Boolean.TRUE);
	}

	/**
	 * Test mapping to BOOLEAN FIELD - mapping SOURCE is FALSE
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanField_False() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Field", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify(false);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = false;\n" //
						, "com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.BooleanField", Boolean.FALSE);
		assertVariableValue(scriptContext, "data.BooleanFieldFromHeader", Boolean.FALSE);
	}

	/**
	 * Test mapping to BOOLEAN ATTRIBUTE - mapping SOURCE is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(204, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.boolean1", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.boolean1", null);
	}

	/**
	 * Test mapping to BOOLEAN ATTRIBUTE - mapping SOURCE isTRUE
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanAttr_True() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Attr", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify(true);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = true;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.boolean1", Boolean.TRUE);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.boolean1", Boolean.TRUE);
	}

	/**
	 * Test mapping to BOOLEAN ATTRIBUTE - mapping SOURCE is FALSE
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_BooleanArray_To_BooleanAttr_False() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanTests_Process", "booleanOutput - to Boolean Attr", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify(false);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = false;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.boolean1", Boolean.FALSE);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.boolean1", Boolean.FALSE);
	}

}
