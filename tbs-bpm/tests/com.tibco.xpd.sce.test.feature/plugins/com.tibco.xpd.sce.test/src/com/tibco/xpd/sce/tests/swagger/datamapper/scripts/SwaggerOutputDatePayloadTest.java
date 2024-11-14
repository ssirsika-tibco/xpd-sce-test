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
 * primitive DATE and DATETIME type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputDatePayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * DATE PAYLOAD to DATE FIELD / ATTRIBUTE
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
	 * Test mapping from DATE output payload that is UNSET - mapping target DATE FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Date_To_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateOutput - to Date Field", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(205, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.DateField", null);
		assertVariableValue(scriptContext, "data.DateFieldFromHeader", null);
	}

	/**
	 * Test mapping from DATE output payload - mapping target DATE FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Date_To_DateField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateOutput - to Date Field", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		mappingScript += getDateTestScript("data.DateField", "2024-10-16");
		mappingScript += getDateTestScript("data.DateFieldFromHeader", "1967-05-29");

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(205, "REST_RESPONSE.data = JSON.stringify(new Date('2024-10-16'));\n" //
						+ "REST_RESPONSE.headers.HeaderParam = '1967-05-29';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}


	/**
	 * Test mapping from DATE output payload that is UNSET- mapping target DATE ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Date_To_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateOutput - to Date Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(205, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.date", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.date", null);
	}

	/**
	 * Test mapping from DATE output payload - mapping target DATE ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Date_To_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateOutput - to Date Attr", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		mappingScript += getDateTestScript("data.bomDataField.date", "2024-10-16");
		mappingScript += getDateTestScript("data.bomDataFieldFromHeader.date", "1967-05-29");

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(205, "REST_RESPONSE.data = JSON.stringify(new Date('2024-10-16'));\n" //
						+ "REST_RESPONSE.headers.HeaderParam = '1967-05-29';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
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
	 * DATETIME PAYLOAD to DATETIME FIELD / ATTRIBUTE
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
	 * Test mapping from DATETIME output payload that is UNSET - mapping target DATETIME FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTime_To_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeOutput - to DateTime Field", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(206, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.DateTimeField", null);
		assertVariableValue(scriptContext, "data.DateTimeFieldFromHeader", null);
	}

	/**
	 * Test mapping from DATETIME output payload - mapping target DATETIME FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTime_To_DateTimeField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeOutput - to DateTime Field", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		mappingScript += getDateTestScript("data.DateTimeField", "2024-10-16T12:34:56.000Z");
		mappingScript += getDateTestScript("data.DateTimeFieldFromHeader", "1967-05-29T03:15:00.000Z");

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(206,
						"REST_RESPONSE.data = JSON.stringify(new Date('2024-10-16T12:34:56.000Z'));\n" //
								+ "REST_RESPONSE.headers.HeaderParam = '1967-05-29T03:15:00.000Z';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}

	/**
	 * Test mapping from DATETIME output payload that is UNSET- mapping target DATETIME ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTime_To_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeOutput - to DateTime Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(206, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.datetime", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.datetime", null);
	}

	/**
	 * Test mapping from DATETIME output payload - mapping target DATETIME ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTime_To_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateTests_Process", "dateTimeOutput - to DateTime Attr", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		mappingScript += getDateTestScript("data.bomDataField.datetime", "2024-10-16T12:34:56.000Z");
		mappingScript += getDateTestScript("data.bomDataFieldFromHeader.datetime",
				"1967-05-29T03:15:00.000Z");

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(206,
						"REST_RESPONSE.data = JSON.stringify(new Date('2024-10-16T12:34:56.000Z'));\n" //
								+ "REST_RESPONSE.headers.HeaderParam = '1967-05-29T03:15:00.000Z';\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}

	/**
	 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript to
	 * check the correct value and set a boolean variable accordingly.
	 * 
	 * If the resultant Date.toISOString() does not match new Date(expectedValue).toISOString() then a script exception
	 * is thrown to fail the test.
	 * 
	 * @param targetData
	 *            The process data path to check (i.e. "data.DateField" or "data.bomField.dateAttr"
	 * @param expectedValue
	 *            The value to use to construct a JS Date class to validate against.
	 * 
	 * @return Script to test targetData is a Date class object that matches a date constructed from the expected Value.
	 */
	protected String getDateTestScript(String targetData, String expectedValue)
	{
		return String.format("\n\n//\n// TEST CODE - NOT PART OF GENERATED MAPPING SCRIPT...\n//\n" //
				+ "var expectedValue = new Date('%2$s');\n" //
				+ "if (!(%1$s instanceof Date) || %1$s.toISOString() != new Date('%2$s').toISOString()) {\n" //
				+ "      throw 'Expected %1$s.toISOString() to equal ' + expectedValue.toISOString() + ' but was ' + %1$s.toISOString();\n" //
				+ "}\n\n", //
				targetData, expectedValue);
	}

}
