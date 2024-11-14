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
 * specifically for simple type NUMBER payloads.
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
public class SwaggerInputNumberPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * NUMBER(FLOAT) PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	public void testRequestPayloadScript_NumberFloat_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Floating Point Field",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberField = 12345.6789;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.6789");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Floating Point Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = 12345.6789;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.6789");
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
	 * NUMBER(FLOAT) PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	public void testRequestPayloadScript_NumberFloat_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberField = 12345.67;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.67");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Fixed Point Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.67");
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
	 * NUMBER(FLOAT) PAYLOAD from (INTEGER)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerField = 12345;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE BOM (INTEGER)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Integer Attribute",
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
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE (INTEGER)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Integer Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = 12345;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
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
	 * NUMBER(DOUBLE) PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	public void testRequestPayloadScript_NumberDouble_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Floating Point Field",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberField = 12345.6789;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.6789");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Floating Point Attribute",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = 12345.6789;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.6789");
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
	 * NUMBER(DOUBLE) PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	public void testRequestPayloadScript_NumberDouble_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberField = 12345.67;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.67");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Fixed Point Attribute",
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345.67");
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
	 * NUMBER(DOUBLE) PAYLOAD from (INTEGER)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerField = 12345;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE BOM (INTEGER)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Integer Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE (INTEGER)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Integer Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = 12345;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
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
	 * INTEGER PAYLOAD from FLOATING POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER input payload - mapping SOURCE process FLOATING POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Floating Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Floating Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberField = 12345.6789;"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12346");
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE BOM FLOATING POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Floating Point Attribute",
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
	 * Test mapping to INTEGER input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = 12345.6789;\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12346");
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
	 * INTEGER PAYLOAD from FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER input payload - mapping SOURCE process FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Fixed Point Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberField = 12345.67;"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12346");
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE BOM FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Fixed Point Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE BOM FLOATING POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Fixed Point Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of the source data AND the value should be rounded to nearest
		 * Int
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12346");
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
	 * INTEGER PAYLOAD from (INTEGER)FIXED POINT NUMBER / ATTRIBUTE
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
	 * Test mapping to INTEGER input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_IntegerField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_IntegerField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.IntegerField = 12345;"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE BOM (INTEGER)FIXED POINT ATTR is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_IntegerAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Integer Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);
	}

	/**
	 * Test mapping to INTEGER input payload - mapping SOURCE (INTEGER)FIXED POINT POINT ATTR
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_IntegerAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Integer Attribute", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.integer = 12345;\n"//
				));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "12345");
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
	 * INTEGER / NUMBER(FLOAT) / NUMBER(DOUBLE) with ZERO value (ensure that value still considered to be SET).
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
	 * Test mapping to INTEGER input payload - mapping SOURCE process (INTEGER)FIXED POINT FIELD value ZERO (ensure that
	 * value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_Integer_From_IntegerField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "integerInput - from Integer Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.IntegerField = 0;"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "0");
	}

	/**
	 * Test mapping to NUMBER (FLOAT) input payload - mapping SOURCE process FLOATING POINT FIELD value ZERO (ensure
	 * that value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberFloat_From_FloatingPointField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberFloatInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FloatingPointNumberField = 0;"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "0");
	}

	/**
	 * Test mapping to NUMBER (DOUBLE) input payload - mapping SOURCE process FLOATING POINT FIELD value ZERO (ensure
	 * that value still considered to be SET).
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_NumberDouble_From_FloatingPointField_Zero() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadNumberTests_Process", "numberDoubleInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FloatingPointNumberField = 0;"//
		));

		/* REST_PAYLOAD should be JSON stringified version of the source data */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "0");
	}

}
