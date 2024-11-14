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
 * primitive SRING ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputStringArrayPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * STRING ARRAY FIELD / ATTRIBUTE from STRING PAYLOAD
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
	 * Test mapping to STRING ARRAY FIELD - mapping source STRING ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_StringArray_To_TextField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayOutput - to Text Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200, //
				"data.TextArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array - process data arrays are always initialised
		 */
		assertVariableValue(scriptContext, "data.TextArrayField", new Object[0]);
	}

	/**
	 * Test mapping to STRING ARRAY FIELD - mapping source is STRING ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_StringArray_To_TextField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayOutput - to Text Field",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200,
						"REST_RESPONSE.data = JSON.stringify(['a', 'string', 'array payload']);\n" //
						+ "data.TextArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.TextArrayField",
				new Object[]{"a", "string", "array payload"});
	}

	/**
	 * Test mapping to STRING ARRAY ATTRIBUTE - mapping source STRING ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_StringArray_To_TextAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayOutput - to Text Attr",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200, //
				"data.bomArrayAttrsDataField = {};\n" //
						+ "data.bomArrayAttrsDataField.text = [];\n", // In BPM process/BOM arrays are ALWAYS
																		// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array.
		 */
		assertVariableValue(scriptContext, "data.bomArrayAttrsDataField.text", new Object[0]);
	}

	/**
	 * Test mapping to STRING ARRAY ATTRIBUTE - mapping source is STRING ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_StringArray_To_TextAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayOutput - to Text Attr",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200, //
				"REST_RESPONSE.data = JSON.stringify(['a', 'string', 'array payload']);\n" //
				+ "data.bomArrayAttrsDataField = {};" //
				+ "data.bomArrayAttrsDataField.text = [];", // In BPM process/BOM arrays are ALWAYS
															// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomArrayAttrsDataField.text",
				new Object[]{"a", "string", "array payload"});
	}

}
