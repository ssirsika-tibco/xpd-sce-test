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
 * primitive BOOLEAN ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputBooleanArrayPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * BOOLEAN ARRAY FIELD / ATTRIBUTE from BOOLEAN PAYLOAD
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
	 * Test mapping to BOOLEAN ARRAY FIELD - mapping SOURCE is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Boolean_To_BooleanArrayField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayOutput - to Boolean Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(204, //
				"data.BooleanArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array - process data arrays are always initialised
		 */
		assertVariableValue(scriptContext, "data.BooleanArrayField", new Object[0]);
	}

	/**
	 * Test mapping to BOOLEAN ARRAY FIELD - mapping SOURCE is BOOLEAN
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Boolean_To_BooleanArrayField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayOutput - to Boolean Field",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify([true,false,true]);\n" //
						+ "data.BooleanArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.BooleanArrayField",
				new Object[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE});
	}

	/**
	 * Test mapping to BOOLEAN ARRAY ATTRIBUTE - mapping SOURCE is BOOLEAN
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Boolean_To_BooleanAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayOutput - to Boolean Attr",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(204, //
				"data.bomDataField = {};\n" //
				+ "data.bomDataField.boolean1 = [];\n", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.boolean1", new Object[0]);
	}

	/**
	 * Test mapping to BOOLEAN ARRAY ATTRIBUTE - mapping SOURCE is BOOLEAN
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Boolean_To_BooleanAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadBooleanArrayTests_Process", "booleanArrayOutput - to Boolean Attr",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(204, "REST_RESPONSE.data = JSON.stringify([false,true,false]);\n" //
						+ "data.bomDataField = {};" //
						+ "data.bomDataField.boolean1 = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.boolean1",
				new Object[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE});
	}


}
