/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger INPUT PAYLOAD mapping script
 * specifically for simple type NUMBER ARRAY payloads.
 * 
 * There are so many variations of source and target data that this was worth pulling out into a separate test (from
 * general text and date time) etc.
 * 
 * This is because we have a grid of 6 "Floating Point Field, Fixed Point Field, (Integer)Fixed Point Field, Floating
 * Point Bom.Attr, Fixed Point Bom.Attr, (Integer)Fixed Point Bom.Attr" MULTIPLIED BY the swagger "Number(float),
 * Number(double), Integer types.
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputArrayNumberPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * NUMBER(FLOAT) ARRAY PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FLOATING POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Floating Point Field",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberArrayField = [12345.6789, 98765.4321];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.6789,98765.4321]");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Floating Point Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = [12345.6789, 98765.4321];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.6789,98765.4321]");
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
	 * NUMBER(FLOAT) ARRAY PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Fixed Point Field",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberArrayField = [12345.67, 76543.21];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.67,76543.21]");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Fixed Point Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = [12345.67, 76543.21];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.67,76543.21]");
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
	 * NUMBER(FLOAT) ARRAY PAYLOAD from (INTEGER ARRAY)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Integer Field",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Integer Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerArrayField = [12345, 54321];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM (INTEGER ARRAY)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Integer Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE (INTEGER ARRAY)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Integer Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = [12345, 54321];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
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
	 * NUMBER(DOUBLE) ARRAY PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FLOATING POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Floating Point Field",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberArrayField = [12345.6789, 98765.4321];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.6789,98765.4321]");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Floating Point Attribute",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = [12345.6789, 98765.4321];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.6789,98765.4321]");
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
	 * NUMBER(DOUBLE) ARRAY PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Fixed Point Field",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberArrayField = [12345.67, 76543.21];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.67,76543.21]");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Fixed Point Attribute",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = [12345.67, 76543.21];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345.67,76543.21]");
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
	 * NUMBER(DOUBLE) ARRAY PAYLOAD from (INTEGER ARRAY)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Integer Field",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Integer Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerArrayField = [12345, 54321];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM (INTEGER ARRAY)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Integer Attribute",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE (INTEGER ARRAY)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Integer Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = [12345, 54321];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
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
	 * INTEGER ARRAY PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process FLOATING POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Floating Point Field",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberArrayField = [12345.6789, 98765.4321];"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12346,98765]");
	}

	/**
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Floating Point Attribute",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = [12345.6789, 98765.4321];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12346,98765]");
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
	 * INTEGER ARRAY PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Fixed Point Field",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberArrayField = [12345.67, 76543.21];"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12346,76543]");
	}

	/**
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Fixed Point Attribute",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = [12345.67, 76543.21];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12346,76543]");
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
	 * INTEGER ARRAY PAYLOAD from (INTEGER ARRAY)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Integer Field",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Integer Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerArrayField = [12345, 54321];"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
	}

	/**
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE BOM (INTEGER ARRAY)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Integer Attribute",
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE (INTEGER ARRAY)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Integer Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = [12345, 54321];\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[12345,54321]");
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
	 * INTEGER ARRAY / NUMBER(FLOAT) ARRAY / NUMBER(DOUBLE) ARRAY with ZERO value (ensure that value still considered to
	 * be SET).
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
	 * Test mapping to INTEGER ARRAY input payload - mapping SOURCE process (INTEGER ARRAY)FIXED POINT FIELD value ZERO
	 * (ensure that value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_IntegerArray_From_IntegerField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "integerArrayInput - from Integer Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.IntegerArrayField=[0, 0];"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[0,0]");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FLOATING POINT FIELD value ZERO (ensure
	 * that value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloatArray_From_FloatingPointField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberFloatArrayInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FloatingPointNumberArrayField=[0, 0];"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[0,0]");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FLOATING POINT FIELD value ZERO (ensure
	 * that value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDoubleArray_From_FloatingPointField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberArrayTests_Process", "numberDoubleArrayInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FloatingPointNumberArrayField=[0, 0];"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[0,0]");
	}

}
