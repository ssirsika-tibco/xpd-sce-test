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
 * primitive DATE ARRAY and DATETIME ARRAY ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputDateArrayPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * DATE ARRAY PAYLOAD to DATE ARRAY FIELD / ATTRIBUTE
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
	 * Test mapping from DATE ARRAY output payload that is UNSET - mapping target DATE ARRAY FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateArray_To_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayOutput - to Date Field", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(205, //
				"data.DateArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload should be empty array (arrays in BPM are always pre-initialised) */
		assertVariableValue(scriptContext, "data.DateArrayField", new Object[0]);

	}

	/**
	 * Test mapping from DATE ARRAY output payload - mapping target DATE ARRAY FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateArray_To_DateField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayOutput - to Date Field", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		String expectedValuesCreation = "[new Date('2024-10-16'),new Date('1967-05-29')]";
		mappingScript += getDateTestScript("data.DateArrayField", expectedValuesCreation);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(205,
						"REST_RESPONSE.data = JSON.stringify(" + expectedValuesCreation + ");\n" //
						+ "data.DateArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}

	/**
	 * Test mapping from DATE ARRAY output payload that is UNSET- mapping target DATE ARRAY ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateArray_To_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayOutput - to Date Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(205, //
				"data.bomDataField = {};\n" //
				+ "data.bomDataField.date = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload should be empty array (arrays in BPM are always pre-initialised) */
		assertVariableValue(scriptContext, "data.bomDataField.date", new Object[0]);

	}

	/**
	 * Test mapping from DATE ARRAY output payload - mapping target DATE ARRAY ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateArray_To_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateArrayOutput - to Date Attr", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		String expectedValuesCreation = "[new Date('2024-10-16'),new Date('1967-05-29')]";
		mappingScript += getDateTestScript("data.bomDataField.date", expectedValuesCreation);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(205, //
						"REST_RESPONSE.data = JSON.stringify(" + expectedValuesCreation + ");\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.date = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
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
	 * DATETIME ARRAY PAYLOAD to DATETIME ARRAY FIELD / ATTRIBUTE
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
	 * Test mapping from DATETIME ARRAY output payload that is UNSET - mapping target DATETIME ARRAY FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTimeArray_To_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayOutput - to DateTime Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(206, //
				"data.DateTimeArrayField = [];\n", // In BPM proc/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload should be empty array (arrays in BPM are always pre-initialised) */
		assertVariableValue(scriptContext, "data.DateTimeArrayField", new Object[0]);

	}

	/**
	 * Test mapping from DATETIME ARRAY output payload - mapping target DATETIME ARRAY FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTimeArray_To_DateTimeField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayOutput - to DateTime Field",
				MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		String expectedValuesCreation = "[new Date('2024-10-16T12:34:56.000Z'),new Date('1967-05-29T03:15:00.000Z')]";
		mappingScript += getDateTestScript("data.DateTimeArrayField", expectedValuesCreation);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(206, "REST_RESPONSE.data = JSON.stringify(" + expectedValuesCreation + ");\n" //
						+ "data.DateTimeArrayField = [];", // In BPM proc/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}

	/**
	 * Test mapping from DATETIME ARRAY output payload that is UNSET- mapping target DATETIME ARRAY ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTimeArray_To_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayOutput - to DateTime Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(206,  //
				"data.bomDataField = {};\n" //
				+ "data.bomDataField.datetime = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload should be empty array (arrays in BPM are always pre-initialised) */
		assertVariableValue(scriptContext, "data.bomDataField.datetime", new Object[0]);
	}

	/**
	 * Test mapping from DATETIME ARRAY output payload - mapping target DATETIME ARRAY ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_DateTimeArray_To_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadDateArrayTests_Process", "dateTimeArrayOutput - to DateTime Attr", MappingDirection.OUT);

		/*
		 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript
		 * to check the correct value and set a boolean variable accordingly
		 */
		String expectedValuesCreation = "[new Date('2024-10-16T12:34:56.000Z'),new Date('1967-05-29T03:15:00.000Z')]";
		mappingScript += getDateTestScript("data.bomDataField.datetime", expectedValuesCreation);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(206, //
						"REST_RESPONSE.data = JSON.stringify(" + expectedValuesCreation + ");\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.datetime = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));
	}

	/**
	 * Date class objects are awkward to test for in our Java JS scope representation, so instead append JavaScript to
	 * check the correct value and set a boolean variable accordingly.
	 * 
	 * This script will throw script exception when executed if the value is not as expected
	 * @param targetData
	 *            The process data path to check (i.e. "data.DateArrayField" or "data.bomField.dateAttr"
	 * @param expectedValuesCreationScript
	 *            Script to construct a JS Date class array to validate against.
	 * 
	 * @return script to append to mapping script.
	 */
	protected String getDateTestScript(String targetData, String expectedValuesCreationScript)
	{
		return String.format("\n\n//\n// TEST CODE - NOT PART OF GENERATED MAPPING SCRIPT...\n//\n" //
				+ "var expectedValues = %2$s;\n" //
				+ "if (%1$s == null) {\n"//
				+ "  throw 'Expected %1$s to be set (but is null or undefined)';\n" //
				+ "}\n" //
				+ "if (!(%1$s instanceof Array)) {\n"//
				+ "  throw 'Expected %1$s to be an array (but is not)';\n" //
				+ "}\n" //
				+ "if (%1$s.length != expectedValues.length) {\n"//
				+ "  throw 'Expected %1$s array to be length ' + expectedValues.length + ' but was ' + %1$s.length;\n" //
				+ "}\n" //
				+ "var i = 0;\n" //
				+ "for (; i < expectedValues.length; i++) {\n" //
				+ "  if (!(%1$s[i] instanceof Date) || (%1$s[i].toISOString() != expectedValues[i].toISOString())) {\n" //
				+ "      throw 'Expected %1$s['+i+'] to equal ' + expectedValues[i].toISOString() + ' but was ' + %1$s[i].toISOString();\n" //
				+ "  }\n" //
				+ "}\n\n", //
				targetData, expectedValuesCreationScript);
	}

}
