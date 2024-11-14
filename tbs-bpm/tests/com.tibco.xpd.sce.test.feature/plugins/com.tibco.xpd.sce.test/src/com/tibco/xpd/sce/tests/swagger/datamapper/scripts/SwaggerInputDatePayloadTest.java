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
 * primitive DATE and DATETIME type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputDatePayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * DATE PAYLOAD from DATE FIELD / ATTRIBUTE
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
	 * Test mapping to DATE input payload - mapping SOURCE process DATE FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Date_From_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateInput - from Date Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to DATE input payload - mapping SOURCE process DATE FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Date_From_DateField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateInput - from Date Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateField = new Date('2024-10-16');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"2024-10-16\"");
	}

	/**
	 * Test mapping to DATE input payload - mapping SOURCE process DATE ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Date_From_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateInput - from Date Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to DATE input payload - mapping SOURCE process DATE ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Date_From_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateInput - from Date Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.date = new Date('2024-10-16');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"2024-10-16\"");
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
	 * DATETIME PAYLOAD from DATETIME FIELD / ATTRIBUTE
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
	 * Test mapping to DATETIME input payload - mapping SOURCE process DATETIME FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTime_From_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeInput - from DateTime Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to DATETIME input payload - mapping SOURCE process DATETIME FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTime_From_DateTimeField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeInput - from DateTime Field", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateTimeField = new Date('2024-10-16T00:00:00.000Z');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"2024-10-16T00:00:00.000Z\"");
	}

	/**
	 * Test mapping to DATETIME input payload - mapping SOURCE process DATETIME ATTR that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTime_From_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeInput - from DateTime Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to DATETIME input payload - mapping SOURCE process DATETIME ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_DateTime_From_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeInput - from DateTime Attr", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};"//
				+ "data.bomDataField.datetime = new Date('2024-10-16T00:00:00.000Z');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the data we set as payload.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"2024-10-16T00:00:00.000Z\"");
	}

}
