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
 * primitive STRING ARRAY ARRAY type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputArrayStringPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * STRING ARRAY PAYLOAD from TEXT ARRAY FIELD / ATTRIBUTE
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process TEXT ARRAY FIELD that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_TextArrayField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Text Array Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process TEXT ARRAY FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_TextArrayField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Text Array Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextArrayField = ['abc', 'xyz'];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"abc\",\"xyz\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM TEXT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_TextArrayAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Text Array Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM TEXT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_TextArrayAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Text Array Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomArrayAttrsDataField = {};\n"//
						+ "data.bomArrayAttrsDataField.text = ['abc', 'xyz'];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"abc\",\"xyz\"]");
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
	 * STRING ARRAY PAYLOAD from FLOATING POINT FIELD / ATTRIBUTE
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process FLOATING POINT field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FloatingPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Floating Point Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process FLOATING POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FloatingPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Floating Point Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FloatingPointNumberArrayField = [12345.6789, 98765.4321];"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"12345.6789\",\"98765.4321\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM FLOATING POINT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FloatingPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process",
				"stringArrayInputMultipleOutput - from Floating Point Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM FLOATING POINT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FloatingPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process",
				"stringArrayInputMultipleOutput - from Floating Point Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomArrayAttrsDataField = {};\n" //
						+ "data.bomArrayAttrsDataField.floatingPointNumber = [12345.6789, 98765.4321];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"12345.6789\",\"98765.4321\"]");
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
	 * STRING ARRAY PAYLOAD from FIXED POINT FIELD / ATTRIBUTE
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process FIXED POINT field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FixedPointField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Fixed Point Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process FIXED POINT FIELD
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FixedPointField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Fixed Point Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.FixedPointNumberArrayField = [12345.67, 76543.21];"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"12345.67\",\"76543.21\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM FIXED POINT Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FixedPointAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Fixed Point Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM FIXED POINT Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_FixedPointAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Fixed Point Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomArrayAttrsDataField = {};\n" //
						+ "data.bomArrayAttrsDataField.fixedPointNumber = [12345.67, 76543.21];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"12345.67\",\"76543.21\"]");
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
	 * STRING ARRAY PAYLOAD from BOOLEAN FIELD / ATTRIBUTE with TRUE value
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process BOOLEAN field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_BooleanField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Boolean Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process BOOLEAN FIELD with TRUE value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_BooleanField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.BooleanArrayField = [true, false];"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"true\",\"false\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM BOOLEAN Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_BooleanAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Boolean Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM BOOLEAN Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_BooleanAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Boolean Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" //
						+ "data.bomArrayAttrsDataField = {};\n" //
						+ "data.bomArrayAttrsDataField.boolean1 = [true, false];\n"//
				));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"true\",\"false\"]");
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
	 * STRING ARRAY PAYLOAD from DATE FIELD / ATTRIBUTE
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process DATE field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Date Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process DATE FIELD
	 * 
	 * @throws ScriptException
	 * @throws ParseException
	 */
	public void testRequestPayloadScript_StringArray_From_DateField() throws ScriptException, ParseException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Date Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateArrayField = [new Date('2024-10-15'), new Date('1967-05-29')];"//
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
		String expectedDate2;

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
			expectedDate2 = "Mon May 29 1967 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 01:00:00 GMT+0100 (BST)";
			expectedDate2 = "Mon May 29 1967 01:00:00 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"" + expectedDate + "\",\"" + expectedDate2 + "\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM DATE Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Date Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM DATE Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Date Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomArrayAttrsDataField = {};\n" //
				+ "data.bomArrayAttrsDataField.date = [new Date('2024-10-15'), new Date('1967-05-29')];\n"//
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
		String expectedDate2;

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
			expectedDate2 = "Mon May 29 1967 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 01:00:00 GMT+0100 (BST)";
			expectedDate2 = "Mon May 29 1967 01:00:00 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"" + expectedDate + "\",\"" + expectedDate2 + "\"]");

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
	 * STRING ARRAY PAYLOAD from DATETIME FIELD / ATTRIBUTE
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process DATETIME field that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateTimeField_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from DateTime Field",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process DATETIME FIELD
	 * 
	 * @throws ScriptException
	 * @throws ParseException
	 */
	public void testRequestPayloadScript_StringArray_From_DateTimeField() throws ScriptException, ParseException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from DateTime Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.DateTimeArrayField = [new Date('2024-10-15T12:34:56.000Z'),new Date('1967-05-29T12:34:56.000Z')];"//
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
		String expectedDate2;

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
			expectedDate2 = "Mon May 29 1967 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 13:34:56 GMT+0100 (BST)";
			expectedDate2 = "Mon May 29 1967 13:34:56 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"" + expectedDate + "\",\"" + expectedDate2 + "\"]");
	}

	/**
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM DATETIME Attribute that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateTimeAttr_Unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from DateTime Attribute",
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE BOM DATETIME Attribute
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_DateTimeAttr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from DateTime Attribute",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomArrayAttrsDataField = {};\n" //
				+ "data.bomArrayAttrsDataField.datetime = [new Date('2024-10-15T12:34:56.000Z'),new Date('1967-05-29T12:34:56.000Z')];\n"//
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
		String expectedDate2;

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
			expectedDate2 = "Mon May 29 1967 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Tue Oct 15 2024 13:34:56 GMT+0100 (BST)";
			expectedDate2 = "Mon May 29 1967 13:34:56 GMT+0100 (BST)";
		}

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[\"" + expectedDate + "\",\"" + expectedDate2 + "\"]");
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
	 * STRING ARRAY PAYLOAD from TEXT ARRAY FIELD with empty array value
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
	 * Test mapping to STRING ARRAY input payload - mapping SOURCE process TEXT ARRAY FIELD with EMPTY STRING ARRAY
	 * value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_StringArray_From_TextArrayField_EmptyString() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerPayloadStringArrayTests_Process", "stringArrayInputMultipleOutput - from Text Array Field",
				MappingDirection.IN);

		/*
		 * Setup source data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextArrayField = [];"//
		));

		/*
		 * REST_PAYLOAD should be JSON stringified version of new String(sourceData) (because we are mapping TO a string
		 * property, then mapping script will create a new String from the source value
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.data", "[]");
	}

}
