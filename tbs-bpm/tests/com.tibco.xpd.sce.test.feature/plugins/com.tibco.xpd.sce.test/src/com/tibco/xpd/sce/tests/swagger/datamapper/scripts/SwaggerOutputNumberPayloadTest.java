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
 * primitive NUMBER type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOutputNumberPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * FIXED POINT NUMBER FIELD from NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FixedPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", null);
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FixedPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.67);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 9876543.21;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", new Double(12345.67));
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", new Double(9876543.21));
	}

	/**
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FixedPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", null);
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FixedPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.67);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 9876543.21;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", new Double(12345.67));
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", new Double(9876543.21));
	}

	/**
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FixedPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Integer Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", null);
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER FIELD - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FixedPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberField - from Integer Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FixedPointNumberField", new Double(12345678));
		assertVariableValue(scriptContext, "data.FixedPointNumberFieldFromHeader", new Double(987654321));
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
	 * FLOATING POINT NUMBER FIELD from NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FloatingPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", null);
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FloatingPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", new Double(98765.4321));
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FloatingPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", null);
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FloatingPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", new Double(98765.4321));
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FloatingPointNumberField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Integer Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", null);
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER FIELD - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FloatingPointNumberField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberField - from Integer Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.FloatingPointNumberField", new Double(12345678));
		assertVariableValue(scriptContext, "data.FloatingPointNumberFieldFromHeader", new Double(987654321));
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
	 * INTEGER FIELD from NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to INTEGER FIELD - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Number(Double) Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.IntegerField", null);
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", null);
	}

	/**
	 * Test mapping to INTEGER FIELD - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Number(Double) Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be set correctly
		 * 
		 * NOTE: It may look odd that we are testing that target Integer is actually a decimal. This is because the
		 * PROCESS DATA mapper script gen info provider doesn't bother doing Math.round() on incoming data. It has never
		 * done this, and so the assumption is that this works and is handled by CDM in some way at runtime.
		 */
		assertVariableValue(scriptContext, "data.IntegerField", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", new Double(98765.4321));
	}

	/**
	 * Test mapping to INTEGER FIELD - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Number(Float) Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.IntegerField", null);
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", null);
	}

	/**
	 * Test mapping to INTEGER FIELD - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Number(Float) Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be set correctly
		 * 
		 * NOTE: It may look odd that we are testing that target Integer is actually a decimal. This is because the
		 * PROCESS DATA mapper script gen info provider doesn't bother doing Math.round() on incoming data. It has never
		 * done this, and so the assumption is that this works and is handled by CDM in some way at runtime.
		 */
		assertVariableValue(scriptContext, "data.IntegerField", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", new Double(98765.4321));
	}

	/**
	 * Test mapping to INTEGER FIELD - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Integer Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.IntegerField", null);
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", null);
	}

	/**
	 * Test mapping to INTEGER FIELD - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerField - from Integer Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.IntegerField", new Double(12345678));
		assertVariableValue(scriptContext, "data.IntegerFieldFromHeader", new Double(987654321));
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
	 * FIXED POINT NUMBER BOM ATTRIBUTE NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FixedPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FixedPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.67);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 9876543.21;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", new Double(12345.67));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", new Double(9876543.21));
	}

	/**
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FixedPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FixedPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.67);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 9876543.21;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", new Double(12345.67));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", new Double(9876543.21));
	}

	/**
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FixedPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Integer Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", null);
	}

	/**
	 * Test mapping to FIXED POINT NUMBER BOM ATTR - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FixedPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "fixedPointNumberAttr - from Integer Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.fixedPointNumber", new Double(12345678));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.fixedPointNumber", new Double(987654321));
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
	 * FLOATING POINT NUMBER BOM ATTRIBUTE NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FloatingPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_FloatingPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Number(Double) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", new Double(98765.4321));
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FloatingPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_FloatingPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Number(Float) Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", new Double(98765.4321));
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FloatingPointNumberAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Integer Output",
				MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", null);
	}

	/**
	 * Test mapping to FLOATING POINT NUMBER BOM ATTR - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_FloatingPointNumberAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "floatingPointNumberAttr - from Integer Output",
				MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.floatingPointNumber", new Double(12345678));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.floatingPointNumber", new Double(987654321));
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
	 * INTEGER BOM ATTRIBUTE NUMBER(DOUBLE) PAYLOAD / HEADER
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
	 * Test mapping to INTEGER BOM ATTR - mapping source is UNSET NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Number(Double) Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(201, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.integer", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", null);
	}

	/**
	 * Test mapping to INTEGER BOM ATTR - mapping source is NUMBER(DOUBLE)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberDouble_To_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Number(Double) Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(201, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be set correctly
		 * 
		 * NOTE: It may look odd that we are testing that target Integer is actually a decimal. This is because the
		 * PROCESS DATA mapper script gen info provider doesn't bother doing Math.round() on incoming data. It has never
		 * done this, and so the assumption is that this works and is handled by CDM in some way at runtime.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.integer", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", new Double(98765.4321));
	}

	/**
	 * Test mapping to INTEGER BOM ATTR - mapping source is UNSET NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Number(Float) Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(202, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.integer", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", null);
	}

	/**
	 * Test mapping to INTEGER BOM ATTR - mapping source is NUMBER(FLOAT)
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_NumberFloat_To_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Number(Float) Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(202, "REST_RESPONSE.data = JSON.stringify(12345.6789);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 98765.4321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/*
		 * Process data mapped from payload / header should be set correctly
		 * 
		 * NOTE: It may look odd that we are testing that target Integer is actually a decimal. This is because the
		 * PROCESS DATA mapper script gen info provider doesn't bother doing Math.round() on incoming data. It has never
		 * done this, and so the assumption is that this works and is handled by CDM in some way at runtime.
		 */
		assertVariableValue(scriptContext, "data.bomDataField.integer", new Double(12345.6789));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", new Double(98765.4321));
	}

	/**
	 * Test mapping to INTEGER BOM ATTR - mapping source is UNSET INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Integer Output", MappingDirection.OUT);

		/*
		 * No response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(203, "",
				"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be null */
		assertVariableValue(scriptContext, "data.bomDataField.integer", null);
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", null);
	}

	/**
	 * Test mapping to INTEGER BOM ATTR - mapping source is INTEGER
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_Integer_To_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerAttr - from Integer Output", MappingDirection.OUT);

		/*
		 * Setup response data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(203, "REST_RESPONSE.data = JSON.stringify(12345678);\n" //
						+ "REST_RESPONSE.headers.HeaderParam = 987654321;\n", //
						"com_example_SwaggerScriptGenTests_data", new String[]{"AllDataTypes", "AllDataArrayTypes"}));

		/* Process data mapped from payload / header should be set correctly */
		assertVariableValue(scriptContext, "data.bomDataField.integer", new Double(12345678));
		assertVariableValue(scriptContext, "data.bomDataFieldFromHeader.integer", new Double(987654321));
	}

}
