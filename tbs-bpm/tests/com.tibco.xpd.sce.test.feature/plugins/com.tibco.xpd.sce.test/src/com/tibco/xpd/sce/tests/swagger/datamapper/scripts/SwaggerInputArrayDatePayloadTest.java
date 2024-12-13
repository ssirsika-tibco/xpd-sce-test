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
 * primitive DATE ARRAY and DATETIME ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputArrayDatePayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * DATE ARRAY PAYLOAD from DATE ARRAY FIELD / ATTRIBUTE
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
	 * Test mapping to DATE ARRAY input payload - mapping SOURCE process DATE ARRAY FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateArray_From_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayInput - from Date Field",
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
	 * Test mapping to DATE ARRAY input payload - mapping SOURCE process DATE ARRAY FIELD = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateArray_From_DateField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayInput - from Date Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateArrayField = [new Date('2024-10-16'), new Date('1967-05-29')];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"2024-10-16\",\"1967-05-29\"]");
	}

	/**
	 * Test mapping to DATE ARRAY input payload - mapping SOURCE process DATE ARRAY ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateArray_From_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayInput - from Date Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be empty array */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[]");
	}

	/**
	 * Test mapping to DATE ARRAY input payload - mapping SOURCE process DATE ARRAY ATTR = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateArray_From_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayInput - from Date Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.date = [new Date('2024-10-16'), new Date('1967-05-29')];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"2024-10-16\",\"1967-05-29\"]");
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
	 * DATETIME ARRAY PAYLOAD from DATETIME ARRAY FIELD / ATTRIBUTE
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
	 * Test mapping to DATETIME ARRAY input payload - mapping SOURCE process DATETIME ARRAY FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTimeArray_From_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayInput - from DateTime Field",
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
	 * Test mapping to DATETIME ARRAY input payload - mapping SOURCE process DATETIME ARRAY FIELD = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTimeArray_From_DateTimeField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayInput - from DateTime Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateTimeArrayField = [new Date('2024-10-16T00:00:00.000Z'),new Date('1967-05-29T12:34:56.000Z')];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data",
				"[\"2024-10-16T00:00:00.000Z\",\"1967-05-29T12:34:56.000Z\"]");
	}

	/**
	 * Test mapping to DATETIME ARRAY input payload - mapping SOURCE process DATETIME ARRAY ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTimeArray_From_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayInput - from DateTime Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be empty array */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[]");
	}

	/**
	 * Test mapping to DATETIME ARRAY input payload - mapping SOURCE process DATETIME ARRAY ATTR = TRUE
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTimeArray_From_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayInput - from DateTime Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.datetime = [new Date('2024-10-16T00:00:00.000Z'),new Date('1967-05-29T12:34:56.000Z')];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data",
				"[\"2024-10-16T00:00:00.000Z\",\"1967-05-29T12:34:56.000Z\"]");
	}

}
