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
 * primitive STRING type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputStringPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * STRING FIELD from STRING PAYLOAD / HEADER
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
	 * Test mapping to STRING FIELD - mapping SOURCE is STRING UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_String_To_StringField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringField - from String Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.TextField", null);
		assertVariableValue(scriptContext, "data.TextFieldFromHeader", null);
	}

	/**
	 * Test mapping to STRING FIELD - mapping SOURCE is STRING
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_String_To_StringField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringField - from String Output", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('a string value');\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 'a string header value';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.TextField", "a string value");
		assertVariableValue(scriptContext, "data.TextFieldFromHeader", "a string header value");
	}

	/**
	 * Test mapping from a text/plain output service.
	 * 
	 * @throws ScriptException
	 */
	public void testPlainTextResponsePayloadScript_String_To_StringField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPlainTextPayloadTests_Process", "Uses text/plain service", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = 'a string value';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.TextField01", "a string value");
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
	 * STRING BOM ATTRIBUTE from STRING PAYLOAD / HEADER
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
	 * Test mapping to STRING BOM ATTRIBUTE - mapping SOURCE is STRING UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_String_To_StringAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringAttr - from String Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.text", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.text", null);
	}

	/**
	 * Test mapping to STRING BOM ATTRIBUTE - mapping SOURCE is STRING
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_String_To_StringAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringAttr - from String Output", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('a string value');\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 'a string header value';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.text", "a string value");
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.text", "a string header value");
	}

}
