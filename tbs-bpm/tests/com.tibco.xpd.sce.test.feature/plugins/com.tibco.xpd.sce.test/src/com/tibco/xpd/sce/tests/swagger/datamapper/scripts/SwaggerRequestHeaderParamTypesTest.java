/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of Swagger INPUT HEADER param mapping script for
 * all data types
 * 
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerRequestHeaderParamTypesTest extends SwaggerScriptGeneratorExecutionTest
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
	 * MAPPING ALL HEADER PARAM TYPES FROM MATCHING FIELD / ATTR TYPE
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
	 * Test mapping from UNSET DATA FIELDS to all types of header param,
	 * 
	 * @throws ScriptException
	 */
	public void testHeaderParamType_AllTypes_From_DataFields_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - all types from Data Fields",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The headers set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberDoubleHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberFloatHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.integerHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateTimeHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.booleanHeader", null);
	}

	/**
	 * Test mapping from DATA FIELDS to all types of header param,
	 * 
	 * @throws ScriptException
	 */
	public void testHeaderParamType_AllTypes_From_DataFields() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - all types from Data Fields",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "    data.TextField01 = 'a string value';\n"//
				+ "    data.FixedPointNumberField = 12345.67;\n"//
				+ "    data.FloatingPointNumberField = 98765.4321;\n"//
				+ "    data.IntegerField = 123456789;\n"//
				+ "    data.DateField = new Date('2024-10-21');\n"//
				+ "    data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
				+ "    data.BooleanField = true;\n"//
		));

		/* The headers set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "a string value");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberDoubleHeader", "12345.67");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberFloatHeader", "98765.4321");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.integerHeader", "123456789");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateHeader", "2024-10-21");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateTimeHeader", "2024-10-21T12:34:56.000Z");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.booleanHeader", "true");
	}

	/**
	 * Test mapping from UNSET BOM ATTRIBUTES to all types of header param,
	 * 
	 * @throws ScriptException
	 */
	public void testHeaderParamType_AllTypes_From_Attributes_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - all types from Attrs",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The headers set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberDoubleHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberFloatHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.integerHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateTimeHeader", null);
		assertVariableValue(scriptContext, "REST_REQUEST.headers.booleanHeader", null);
	}

	/**
	 * Test mapping from BOM ATTRIBUTES to all types of header param,
	 * 
	 * @throws ScriptException
	 */
	public void testHeaderParamType_AllTypes_From_Attributes() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - all types from Attrs",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.bomDataField = {};\n"//
				+ "data.bomDataField.text = 'a string value';\n"//
				+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
				+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
				+ "data.bomDataField.integer = 123456789;\n"//
				+ "data.bomDataField.date = new Date('2024-10-21');\n"//
				+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
				+ "data.bomDataField.boolean1 = true;\n"//
		));

		/* The headers set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "a string value");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberDoubleHeader", "12345.67");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.numberFloatHeader", "98765.4321");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.integerHeader", "123456789");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateHeader", "2024-10-21");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.dateTimeHeader", "2024-10-21T12:34:56.000Z");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.booleanHeader", "true");
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
	 * MAPPING STRING HEADER PARAM TYPES FROM NON-TEXT DATA FIELD TYPES
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
	 * Test mapping from UNSET FIXED POINT NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FixedPointNumber_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Fixed Point Number Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from FIXED POINT NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FixedPointNumber_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Fixed Point Number Field",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
				+ " data.TextField01 = 'a string value';\n"//
				+ " data.FixedPointNumberField = 12345.67;\n"//
				+ " data.FloatingPointNumberField = 98765.4321;\n"//
				+ " data.IntegerField = 123456789;\n"//
				+ " data.DateField = new Date('2024-10-21');\n"//
				+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
				+ " data.BooleanField = true;\n"//
		));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "12345.67");
	}

	/**
	 * Test mapping from UNSET FLOATING POINT NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FloatingPointNumber_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process",
				"inputWithAllHeaderTypes - string from Floating Point Number Field", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from FLOATING POINT NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FloatingPointNumber_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process",
				"inputWithAllHeaderTypes - string from Floating Point Number Field", MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ " data.FixedPointNumberField = 12345.67;\n"//
						+ " data.FloatingPointNumberField = 98765.4321;\n"//
						+ " data.IntegerField = 123456789;\n"//
						+ " data.DateField = new Date('2024-10-21');\n"//
						+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ " data.BooleanField = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "98765.4321");
	}

	/**
	 * Test mapping from UNSET INTEGER NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Integer_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Integer Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from INTEGER NUMBER DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Integer_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Integer Field",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ " data.FixedPointNumberField = 12345.67;\n"//
						+ " data.FloatingPointNumberField = 98765.4321;\n"//
						+ " data.IntegerField = 123456789;\n"//
						+ " data.DateField = new Date('2024-10-21');\n"//
						+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ " data.BooleanField = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "123456789");
	}

	/**
	 * Test mapping from UNSET DATE DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Date_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Date Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from DATE DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Date_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Date Field",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ " data.FixedPointNumberField = 12345.67;\n"//
						+ " data.FloatingPointNumberField = 98765.4321;\n"//
						+ " data.IntegerField = 123456789;\n"//
						+ " data.DateField = new Date('2024-10-21');\n"//
						+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ " data.BooleanField = true;\n"//
				));

		/*
		 * Header should be set to new String(sourceData) (because we are mapping TO a string property, then mapping
		 * script will create a new String from the source value
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
			expectedDate = "Mon Oct 21 2024 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Mon Oct 21 2024 01:00:00 GMT+0100 (BST)";
		}

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", expectedDate);
	}

	/**
	 * Test mapping from UNSET DATETIME DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_DateTime_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from DateTime Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from DATETIME DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_DateTime_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from DateTime Field",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ " data.FixedPointNumberField = 12345.67;\n"//
						+ " data.FloatingPointNumberField = 98765.4321;\n"//
						+ " data.IntegerField = 123456789;\n"//
						+ " data.DateField = new Date('2024-10-21');\n"//
						+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ " data.BooleanField = true;\n"//
				));

		/*
		 * Header should be set to new String(sourceData) (because we are mapping TO a string property, then mapping
		 * script will create a new String from the source value
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
			expectedDate = "Mon Oct 21 2024 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Mon Oct 21 2024 13:34:56 GMT+0100 (BST)";
		}

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", expectedDate);
	}

	/**
	 * Test mapping from UNSET BOOLEAN DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Boolean_Field_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Boolean Field",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from BOOLEAN DATA FIELD to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Boolean_Field() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Boolean Field",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ " data.FixedPointNumberField = 12345.67;\n"//
						+ " data.FloatingPointNumberField = 98765.4321;\n"//
						+ " data.IntegerField = 123456789;\n"//
						+ " data.DateField = new Date('2024-10-21');\n"//
						+ " data.DateTimeField = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ " data.BooleanField = false;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "false");
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
	 * MAPPING STRING HEADER PARAM TYPES FROM NON-TEXT BOM ATTRIBUTE TYPES
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
	 * Test mapping from UNSET FIXED POINT NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FixedPointNumber_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Fixed Point Number Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from FIXED POINT NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FixedPointNumber_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Fixed Point Number Attr",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "12345.67");
	}

	/**
	 * Test mapping from UNSET FLOATING POINT NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FloatingPointNumber_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process",
				"inputWithAllHeaderTypes - string from Floating Point Number Attr", MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from FLOATING POINT NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_FloatingPointNumber_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process",
				"inputWithAllHeaderTypes - string from Floating Point Number Attr", MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "98765.4321");
	}

	/**
	 * Test mapping from UNSET INTEGER NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Integer_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Integer Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from INTEGER NUMBER BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Integer_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Integer Attr",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "123456789");
	}

	/**
	 * Test mapping from UNSET DATE BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Date_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Date Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from DATE BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Date_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Date Attr",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/*
		 * Header should be set to new String(sourceData) (because we are mapping TO a string property, then mapping
		 * script will create a new String from the source value
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
			expectedDate = "Mon Oct 21 2024 00:00:00 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Mon Oct 21 2024 01:00:00 GMT+0100 (BST)";
		}

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", expectedDate);
	}

	/**
	 * Test mapping from UNSET DATETIME BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_DateTime_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from DateTime Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from DATETIME BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_DateTime_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from DateTime Attr",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/*
		 * Header should be set to new String(sourceData) (because we are mapping TO a string property, then mapping
		 * script will create a new String from the source value
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
			expectedDate = "Mon Oct 21 2024 12:34:56 GMT+0000 (GMT)";
		}
		else
		{
			expectedDate = "Mon Oct 21 2024 13:34:56 GMT+0100 (BST)";
		}

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", expectedDate);
	}

	/**
	 * Test mapping from UNSET BOOLEAN BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Boolean_Attr_unset() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Boolean Attr",
				MappingDirection.IN);

		/*
		 * No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* The header set via REST_REQUEST.setHeader() should be null */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);

	}

	/**
	 * Test mapping from BOOLEAN BOM ATTRIBUTE to STRING header param
	 *
	 * @throws ScriptException
	 */
	public void testHeaderParamType_String_From_Boolean_Attr() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerHeaderParamTypeTests_Process", "inputWithAllHeaderTypes - string from Boolean Attr",
				MappingDirection.IN);

		/*
		 * Setup process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n" + " data.TextField01 = 'a string value';\n"//
						+ "data.bomDataField = {};\n"//
						+ "data.bomDataField.text = 'a string value';\n"//
						+ "data.bomDataField.fixedPointNumber = 12345.67;\n"//
						+ "data.bomDataField.floatingPointNumber = 98765.4321;\n"//
						+ "data.bomDataField.integer = 123456789;\n"//
						+ "data.bomDataField.date = new Date('2024-10-21');\n"//
						+ "data.bomDataField.datetime = new Date('2024-10-21T12:34:56.000Z');\n"//
						+ "data.bomDataField.boolean1 = true;\n"//
				));

		/* The header set via REST_REQUEST.setHeader() should be the string equivalent of the given field type */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "true");
	}

}
