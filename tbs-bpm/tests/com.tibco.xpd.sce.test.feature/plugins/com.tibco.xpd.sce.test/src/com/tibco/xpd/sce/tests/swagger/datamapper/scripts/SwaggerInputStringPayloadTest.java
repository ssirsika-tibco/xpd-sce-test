/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger INPUT PAYLOAD mapping script for
 * primitive STRING type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputStringPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * STRING PAYLOAD from TEXT FIELD / ATTRIBUTE
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
	 * Test mapping to STRING input payload - mapping SOURCE process TEXT FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_TextField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Text Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process TEXT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_TextField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Text Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField01 = 'Test Data!';"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"Test Data!\"");
	}

	/**
	 * Test mapping to text/plain service payload - mapping SOURCE process TEXT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testPlainTextRequestPayloadScript_String_From_TextField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPlainTextPayloadTests_Process", "Uses text/plain service", MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField01 = 'Test Data!';"//
		));

		/*
		 * text/plain REST_PAYLOAD should NOT be JSON stringified (so no quotes output).
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "Test Data!");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM TEXT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_TextAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Text Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM TEXT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_TextAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Text Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'Test Data!';\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"Test Data!\"");
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
	 * STRING PAYLOAD from FLOATING POINT FIELD / ATTRIBUTE
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
	 * Test mapping to STRING input payload - mapping SOURCE process FLOATING POINT field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberField = 12345.6789;"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"12345.6789\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM FLOATING POINT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM FLOATING POINT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.floatingPointNumber = 12345.6789;\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"12345.6789\"");
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
	 * STRING PAYLOAD from FIXED POINT FIELD / ATTRIBUTE
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
	 * Test mapping to STRING input payload - mapping SOURCE process FIXED POINT field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberField = 12345.67;"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"12345.67\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM FIXED POINT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM FIXED POINT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"12345.67\"");
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
	 * STRING PAYLOAD from BOOLEAN FIELD / ATTRIBUTE with TRUE value
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
	 * Test mapping to STRING input payload - mapping SOURCE process BOOLEAN field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanTrueField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process BOOLEAN FIELD with TRUE value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanTrueField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.BooleanField = true;"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"true\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM BOOLEAN Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanTrueAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM BOOLEAN Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanTrueAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomDataField = {};\n" //
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"true\"");
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
	 * STRING PAYLOAD from BOOLEAN FIELD / ATTRIBUTE with FALSE value
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
	 * Test mapping to STRING input payload - mapping SOURCE process BOOLEAN FIELD with FALSE value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanFalseField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.BooleanField = false;"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"false\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM BOOLEAN Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_BooleanFalseAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Boolean Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};\n" //
				+ "data.bomDataField.boolean1 = false;\n"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"false\"");
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
	 * STRING PAYLOAD from DATE FIELD / ATTRIBUTE
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
	 * Test mapping to STRING input payload - mapping SOURCE process DATE field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Date Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process DATE FIELD
	 * 
	 * @throws ScriptException
	 * @throws ParseException
	 */
	public void testRequestPayloadScript_String_From_DateField() throws ScriptException, ParseException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Date Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateField = new Date('2024-10-15');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 * 
		 * For date test, the result will depend on the timezone of the machine at time of execution, so we'll work
		 * around that.
		 * 
		 * AND just in case you are wondering 'why aren't these ISO8601 dates?'.. it is because we are doing
		 * date->string, so it is effectively target = new String(date).toString() which results in the human readable
		 * date string.
		 */
		String expectedDate;

		/*
		 * if System DST is set off, then date in middle of the year should be false.
		 * 
		 * If DST is off then JSON.stringify() will do Date.toString() and that will output in GMT.
		 * 
		 * if DST is on then it will be in local format (BST) and adjusted for DST.
		 */
		if (!TimeZone.getDefault().inDaylightTime(new Date("2024/06/01")))
		{
			expectedDate = "Tue Oct 15 2024 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 01:00:00 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"" + expectedDate + "\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM DATE Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Date Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM DATE Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Date Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};\n" //
				+ "data.bomDataField.date = new Date('2024-10-15');\n"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 * 
		 * For date test, the result will depend on the timezone of the machine at time of execution, so we'll work
		 * around that.
		 * 
		 * AND just in case you are wondering 'why aren't these ISO8601 dates?'.. it is because we are doing
		 * date->string, so it is effectively target = new String(date).toString() which results in the human readable
		 * date string.
		 */
		String expectedDate;

		/*
		 * if System DST is set off, then date in middle of the year should be false.
		 * 
		 * If DST is off then JSON.stringify() will do Date.toString() and that will output in GMT.
		 * 
		 * if DST is on then it will be in local format (BST) and adjusted for DST.
		 */
		if (!TimeZone.getDefault().inDaylightTime(new Date("2024/06/01")))
		{
			expectedDate = "Tue Oct 15 2024 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 01:00:00 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"" + expectedDate + "\"");
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
	 * STRING PAYLOAD from DATETIME FIELD / ATTRIBUTE
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
	 * Test mapping to STRING input payload - mapping SOURCE process DATETIME field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from DateTime Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE process DATETIME FIELD
	 * 
	 * @throws ScriptException
	 * @throws ParseException
	 */
	public void testRequestPayloadScript_String_From_DateTimeField() throws ScriptException, ParseException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from DateTime Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateTimeField = new Date('2024-10-15T12:34:56.000Z');"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 * 
		 * For date test, the result will depend on the timezone of the machine at time of execution, so we'll work
		 * around that.
		 * 
		 * AND just in case you are wondering 'why aren't these ISO8601 dates?'.. it is because we are doing
		 * date->string, so it is effectively target = new String(date).toString() which results in the human readable
		 * date string.
		 */
		String expectedDate;

		/*
		 * if System DST is set off, then date in middle of the year should be false.
		 * 
		 * If DST is off then JSON.stringify() will do Date.toString() and that will output in GMT.
		 * 
		 * if DST is on then it will be in local format (BST) and adjusted for DST.
		 */
		if (!TimeZone.getDefault().inDaylightTime(new Date("2024/06/01")))
		{
			expectedDate = "Tue Oct 15 2024 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 13:34:56 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"" + expectedDate + "\"");
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM DATETIME Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from DateTime Attribute",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* REST_PAYLOAD should be null */
		assertVariableValue(scriptContext, "REST_PAYLOAD", null);
	}

	/**
	 * Test mapping to STRING input payload - mapping SOURCE BOM DATETIME Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from DateTime Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};\n" //
				+ "data.bomDataField.datetime = new Date('2024-10-15T12:34:56.000Z');\n"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 * 
		 * For date test, the result will depend on the timezone of the machine at time of execution, so we'll work
		 * around that.
		 * 
		 * AND just in case you are wondering 'why aren't these ISO8601 dates?'.. it is because we are doing
		 * date->string, so it is effectively target = new String(date).toString() which results in the human readable
		 * date string.
		 */
		String expectedDate;

		/*
		 * if System DST is set off, then date in middle of the year should be false.
		 * 
		 * If DST is off then JSON.stringify() will do Date.toString() and that will output in GMT.
		 * 
		 * if DST is on then it will be in local format (BST) and adjusted for DST.
		 */
		if (!TimeZone.getDefault().inDaylightTime(new Date("2024/06/01")))
		{
			expectedDate = "Tue Oct 15 2024 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 13:34:56 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"" + expectedDate + "\"");
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
	 * STRING PAYLOAD from TEXT FIELD with empty string value
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
	 * Test mapping to STRING input payload - mapping SOURCE process TEXT FIELD with EMPTY STRING value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_String_From_TextField_EmptyString() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringTests_Process", "stringInput - from Text Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField01 = '';"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "\"\"");
	}


}
