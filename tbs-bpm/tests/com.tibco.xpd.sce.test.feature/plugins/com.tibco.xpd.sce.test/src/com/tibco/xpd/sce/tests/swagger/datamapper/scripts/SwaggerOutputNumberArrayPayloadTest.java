/*
 * Copyright (c) 2014-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger OUTPUT PAYLOAD mapping script for
 * primitive NUMBER ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputNumberArrayPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * FIXED POINT ARRAY FIELD / ATTRIBUTE from NUMBER(DOUBLE) PAYLOAD
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
	 * Test mapping to FIXED POINT ARRAY FIELD - mapping source NUMBER(DOUBLE) ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDoubleArray_To_FixedPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayOutput - to Fixed Point Number Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, //
				"data.FixedPointNumberArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array - process data arrays are always initialised
		 */
		assertVariableValue(scriptContext, "data.FixedPointNumberArrayField", new Object[0]);
	}

	/**
	 * Test mapping to FIXED POINT ARRAY FIELD - mapping source is NUMBER(DOUBLE) ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDoubleArray_To_FixedPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayOutput - to Fixed Point Number Field",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201,
						"REST_RESPONSE.data = JSON.stringify([12345.67, 9876543.21]);\n" //
								+ "data.FixedPointNumberArrayField = [];", // In BPM process/BOM arrays are ALWAYS
																			// pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.FixedPointNumberArrayField",
				new Object[]{new Double(12345.67), new Double(9876543.21)});
	}

	/**
	 * Test mapping to FIXED POINT ARRAY ATTRIBUTE - mapping source NUMBER(DOUBLE) ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDoubleArray_To_FixedPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayOutput - to Fixed Point Number Attr",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, //
				"data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = [];\n", // In BPM process/BOM arrays are ALWAYS
																		// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", new Object[0]);
	}

	/**
	 * Test mapping to FIXED POINT ARRAY ATTRIBUTE - mapping source is NUMBER(DOUBLE) ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDoubleArray_To_FixedPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayOutput - to Fixed Point Number Attr",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, //
				"REST_RESPONSE.data = JSON.stringify([12345.67, 9876543.21]);\n" //
						+ "data.bomDataField = {};" //
						+ "data.bomDataField.fixedPointNumber = [];", // In BPM process/BOM arrays are ALWAYS
															// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber",
				new Object[]{new Double(12345.67), new Double(9876543.21)});
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
	 * FLOATING POINT ARRAY FIELD / ATTRIBUTE from NUMBER(FLOAT) PAYLOAD
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
	 * Test mapping to FLOATING POINT ARRAY FIELD - mapping source NUMBER(FLOAT) ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloatArray_To_FloatingPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayOutput - to Floating Point Number Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, //
				"data.FloatingPointNumberArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by
															// CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array - process data arrays are always initialised
		 */
		assertVariableValue(scriptContext, "data.FloatingPointNumberArrayField", new Object[0]);
	}

	/**
	 * Test mapping to FLOATING POINT ARRAY FIELD - mapping source is NUMBER(FLOAT) ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloatArray_To_FloatingPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayOutput - to Floating Point Number Field",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify([12345.6789, 98765.4321]);\n" //
						+ "data.FloatingPointNumberArrayField = [];", // In BPM process/BOM arrays are ALWAYS
																		// pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.FloatingPointNumberArrayField",
				new Object[]{new Double(12345.6789), new Double(98765.4321)});
	}

	/**
	 * Test mapping to FLOATING POINT ARRAY ATTRIBUTE - mapping source NUMBER(FLOAT) ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloatArray_To_FloatingPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayOutput - to Floating Point Number Attr",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, //
				"data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = [];\n", // In BPM process/BOM arrays are ALWAYS
																			// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", new Object[0]);
	}

	/**
	 * Test mapping to FLOATING POINT ARRAY ATTRIBUTE - mapping source is NUMBER(FLOAT) ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloatArray_To_FloatingPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayOutput - to Floating Point Number Attr",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, //
				"REST_RESPONSE.data = JSON.stringify([12345.6789, 98765.4321]);\n" //
						+ "data.bomDataField = {};" //
						+ "data.bomDataField.floatingPointNumber = [];", // In BPM process/BOM arrays are ALWAYS
				// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber",
				new Object[]{new Double(12345.6789), new Double(98765.4321)});
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
	 * INTEGER ARRAY FIELD / ATTRIBUTE from INTEGER PAYLOAD
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
	 * Test mapping to INTEGER ARRAY FIELD - mapping source INTEGER ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_IntegerArray_To_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayOutput - to Integer Field",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, //
				"data.IntegerArrayField = [];", // In BPM process/BOM arrays are ALWAYS pre-initialised by
												// CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array - process data arrays are always initialised
		 */
		assertVariableValue(scriptContext, "data.IntegerArrayField", new Object[0]);
	}

	/**
	 * Test mapping to INTEGER ARRAY FIELD - mapping source is INTEGER ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_IntegerArray_To_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayOutput - to Integer Field",
				MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify([1234567, 987654321]);\n" //
						+ "data.IntegerArrayField = [];", // In BPM process/BOM arrays are ALWAYS
															// pre-initialised by CDM
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.IntegerArrayField",
				new Object[]{new Integer(1234567), new Integer(987654321)});
	}

	/**
	 * Test mapping to INTEGER ARRAY ATTRIBUTE - mapping source INTEGER ARRAY is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_IntegerArray_To_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayOutput - to Integer Attr", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, //
				"data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = [];\n", // In BPM process/BOM arrays are ALWAYS
																// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be empty array.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.integer", new Object[0]);
	}

	/**
	 * Test mapping to INTEGER ARRAY ATTRIBUTE - mapping source is INTEGER ARRAY
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_IntegerArray_To_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayOutput - to Integer Attr", MappingDirection.OUT);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, //
				"REST_RESPONSE.data = JSON.stringify([1234567, 987654321]);\n" //
						+ "data.bomDataField = {};" //
						+ "data.bomDataField.integer = [];", // In BPM process/BOM arrays are ALWAYS
				// pre-initialised by CDM
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Check correct output header/payload data is placed in process data object.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.integer",
				new Object[]{new Integer(1234567), new Integer(987654321)});
	}

}
