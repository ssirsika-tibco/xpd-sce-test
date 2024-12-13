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
 * COMPLEX TYPE type payloads
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerInputComplexPayloadTest extends SwaggerScriptGeneratorExecutionTest
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
	 * 
	 * COMPLEX TYPE PAYLOAD from SIMPLE TYPE process data
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is UNSET
	 * 
	 * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set.
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_Unset_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types",
				MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we HAVEN'T setup 'exclude empty objects' on this
		 * task.
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [],\n" //
				+ "    dateArray: [],\n" //
				+ "    dateTimeArray: [],\n" //
				+ "    numberDoubleArray: [],\n" //
				+ "    numberFloatArray: [],\n" //
				+ "    integerArray: [],\n" //
				+ "    boolArray: []\n" //
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is only
	 * partially set.
	 * 
	 * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set - therefore because everything
	 * is optional AND we're providing just one piece of source content, then the whole payload should be created with
	 * empty arrays etc
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_PartiallySet_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types", MappingDirection.IN);

		/*
		 * Setup payload test script
		 * 
		 * The 'exclude empty optional objects/arrays' is set OFF - we will have data in the source mapping for
		 * payload.childComplex.stringArray and other arrays will be created
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [new String('a simple string')],\n" //
				+ "    dateArray: [],\n" //
				+ "    dateTimeArray: [],\n" //
				+ "    numberDoubleArray: [],\n" //
				+ "    numberFloatArray: [],\n" //
				+ "    integerArray: [],\n" //
				+ "    boolArray: []\n" //
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in just one item in one of the process data arrays mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {" //
				+ "  TextArrayField : ['a simple string']"//
				+ "};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is fully set.
	 * 
	 * In this test the 'exclude empty optional objects/arrays' is set OFF - but this time we're providing values for
	 * all source mapping data.
	 * 
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_FullySet_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types", MappingDirection.IN);

		/*
		 * Setup payload test script
		 * 
		 * The 'exclude empty optional objects/arrays' is set OFF - - but anyway we've provided data for all input
		 * anyway, so it should all be there (except childComplexArray because we can't mapp to that from simple type
		 * data)
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "  string : new String('a simple string'),"//
				+ "  stringArray : [new String('Text Item 1'), new String('Text Item 2')],"//
				+ "  date : '2024-10-28',"//
				+ "  dateArray : ['1996-04-20', '1998-03-11'],"//
				+ "  dateTime : '2024-10-28T12:34:56.000Z',"//
				+ "  dateTimeArray : ['1996-04-20T02:15:00.000Z', '1998-03-11T14:30:00.000Z'],"//
				+ "  numberDouble : 12345.67,"//
				+ "  numberDoubleArray : [9876543.21, -123.56],"//
				+ "  numberFloat : 12345.6789,"//
				+ "  numberFloatArray : [98765.4321, -123.5678],"//
				+ "  integer : 123456789,"//
				+ "  integerArray : [987654321, -1235678],"//
				+ "  bool : false,"//
				+ "  boolArray : [true, false, true],"//
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {" //
				+ "  TextField : 'a simple string',"//
				+ "  TextArrayField : ['Text Item 1', 'Text Item 2'],"//
				+ "  DateField : new Date('2024-10-28'),"//
				+ "  DateArrayField : [new Date('1996-04-20'), new Date('1998-03-11')],"//
				+ "  DateTimeField : new Date('2024-10-28T12:34:56.000Z'),"//
				+ "  DateTimeArrayField : [new Date('1996-04-20T02:15:00.000Z'), new Date('1998-03-11T14:30:00.000Z')],"//
				+ "  FixedPointNumberField : 12345.67,"//
				+ "  FixedPointNumberArrayField : [9876543.21, -123.56],"//
				+ "  FloatingPointNumberField : 12345.6789,"//
				+ "  FloatingPointArrayField : [98765.4321, -123.5678],"//
				+ "  IntegerField : 123456789,"//
				+ "  IntegerArrayField : [987654321, -1235678],"//
				+ "  BoolField : false,"//
				+ "  BoolArrayField : [true, false, true],"//
				+ "};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is UNSET
	 * 
	 * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND
	 * we're not providing any source content, then the whole payload should be null
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_Unset_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types Exclude Empty Opt",
				MappingDirection.IN);

		/*
		 * Setup payload test script
		 * 
		 * The 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND we're
		 * not providing any source content, then the whole payload should be null
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("null", //
				"REST_PAYLOAD");

		/*
		 * Execute with No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is only
	 * partially set.
	 * 
	 * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND
	 * we're providing just one piece of source content, then the whole payload should be created with only the bear
	 * minimum of content to hold that non-null value
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_PartiallySet_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types Exclude Empty Opt", MappingDirection.IN);

		/*
		 * Setup payload test script
		 * 
		 * The 'exclude empty optional objects/arrays' is set on - we will have data only in the source mapping for
		 * payload.childComplex.stringArray. So the payload should have just that content in.
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [new String('a simple string')],\n" //
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in just one item in one of the process data arrays mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {" //
						+ "  TextArrayField : ['a simple string']"//
						+ "};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE SIMPLE TYPE process data that is fully set.
	 * 
	 * In this test the 'exclude empty optional objects/arrays' is set on - but this time we're providing values for all
	 * source mapping data.
	 * 
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_SimpleFields_FullySet_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types Exclude Empty Opt", MappingDirection.IN);

		/*
		 * Setup payload test script
		 * 
		 * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
		 * so it should all be there
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "  string : new String('a simple string'),"//
				+ "  stringArray : [new String('Text Item 1'), new String('Text Item 2')],"//
				+ "  date : '2024-10-28',"//
				+ "  dateArray : ['1996-04-20', '1998-03-11'],"//
				+ "  dateTime : '2024-10-28T12:34:56.000Z',"//
				+ "  dateTimeArray : ['1996-04-20T02:15:00.000Z', '1998-03-11T14:30:00.000Z'],"//
				+ "  numberDouble : 12345.67,"//
				+ "  numberDoubleArray : [9876543.21, -123.56],"//
				+ "  numberFloat : 12345.6789,"//
				+ "  numberFloatArray : [98765.4321, -123.5678],"//
				+ "  integer : 123456789,"//
				+ "  integerArray : [987654321, -1235678],"//
				+ "  bool : false,"//
				+ "  boolArray : [true, false, true],"//
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {" //
				+ "  TextField : 'a simple string',"//
				+ "  TextArrayField : ['Text Item 1', 'Text Item 2'],"//
				+ "  DateField : new Date('2024-10-28'),"//
				+ "  DateArrayField : [new Date('1996-04-20'), new Date('1998-03-11')],"//
				+ "  DateTimeField : new Date('2024-10-28T12:34:56.000Z'),"//
				+ "  DateTimeArrayField : [new Date('1996-04-20T02:15:00.000Z'), new Date('1998-03-11T14:30:00.000Z')],"//
				+ "  FixedPointNumberField : 12345.67,"//
				+ "  FixedPointNumberArrayField : [9876543.21, -123.56],"//
				+ "  FloatingPointNumberField : 12345.6789,"//
				+ "  FloatingPointArrayField : [98765.4321, -123.5678],"//
				+ "  IntegerField : 123456789,"//
				+ "  IntegerArrayField : [987654321, -1235678],"//
				+ "  BoolField : false,"//
				+ "  BoolArrayField : [true, false, true],"//
				+ "};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * COMPLEX TYPE PAYLOAD from COMPLEX TYPE process data
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is UNSET
	 * 
	 * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set.
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_Unset_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Include Empty",
				MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we HAVEN'T setup 'exclude empty objects' on this
		 * task.
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [],\n" //
				+ "    dateArray: [],\n" //
				+ "    dateTimeArray: [],\n" //
				+ "    numberDoubleArray: [],\n" //
				+ "    numberFloatArray: [],\n" //
				+ "    integerArray: [],\n" //
				+ "    boolArray: []\n" //
				+ "  },\n" //
				+ "  childComplexArray: []\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is only
	 * partially set.
	 * 
	 * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set - therefore because everything
	 * is optional AND we're providing just one piece of source content, then the whole payload should be created with
	 * empty arrays etc
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_PartiallySet_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we haven't setup 'exclude empty objects' on this
		 * task.
		 * 
		 * The 'exclude empty optional objects/arrays' is set OFF - we will have data in the source mapping for
		 * payload.childComplex.stringArray and other arrays will be created
		 * 
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [new String('a string'), new String('another string')],\n" //
				+ "    dateArray: [],\n" //
				+ "    dateTimeArray: [],\n" //
				+ "    numberDoubleArray: [],\n" //
				+ "    numberFloatArray: [],\n" //
				+ "    integerArray: [],\n" //
				+ "    boolArray: [],\n" //
				+ "  },\n" //
				+ "  childComplexArray: []\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in just one item in one of the process data arrays mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.stringArrayAttribute = ['a string', 'another string'];\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.pathParamAttribute = 'aPathParam';\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.queryParamAttribute = 'aQueryParam';\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.headerParamAttribute = 'aHeaderParam';\n" //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is fully set.
	 *
	 * In this test the 'exclude empty optional objects/arrays' is set OFF - but this time we're providing values for
	 * all source mapping data.
	 *
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_FullySet_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.IN);

		/*
		 * Setup payload test script
		 *
		 * The 'exclude empty optional objects/arrays' is set OFF - - but anyway we've provided data for all input
		 * anyway, so it should all be there (except childComplexArray because we can't mapp to that from simple type
		 * data)
		 *
		 */
		String expectedChildComplex = getExpectedChildComplexObject("CC", "01", "1");
		String expectedChildComplexArray0 = getExpectedChildComplexObject("CCA0", "02", "2");
		String expectedChildComplexArray1 = getExpectedChildComplexObject("CCA1", "03", "3");

		mappingScript += getTestObjectsEqualScript("{\n" //
				+ " childComplex : " //
				+ expectedChildComplex + "," //
				+ " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		String setupChildComplex = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute", "CC", "01", "1");

		String setupChildComplexArray0 = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0]", "CCA0", "02", "2");

		String setupChildComplexArray1 = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1]", "CCA1", "03", "3");

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
				+ setupChildComplex //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute = [];\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0] = {};\n" //
				+ setupChildComplexArray0 //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1] = {};\n" //
				+ setupChildComplexArray1 //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is UNSET
	 *
	 * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND
	 * we're not providing any source content, then the whole payload should be null
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_CompleFields_Unset_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we haven't setup 'exclude empty objects' on this
		 * task.
		 *
		 * The 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND we're
		 * not providing any source content, then the whole payload should be null
		 *
		 */
		mappingScript += getTestObjectsEqualScript("null", //
				"REST_PAYLOAD");

		/*
		 * Execute with No process data
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = {};\n"));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is only
	 * partially set.
	 *
	 * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND
	 * we're providing just one piece of source content, then the whole payload should be created with only the bear
	 * minimum of content to hold that non-null value
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_PartiallySet_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we haven't setup 'exclude empty objects' on this
		 * task.
		 *
		 * The 'exclude empty optional objects/arrays' is set on - we will have data only in the source mapping for
		 * payload.childComplex.stringArray. So the payload should have just that content in.
		 *
		 */
		mappingScript += getTestObjectsEqualScript("{\n" //
				+ "  childComplex : {\n" //
				+ "    stringArray : [new String('a string'), new String('another string')],\n" //
				+ "  }\n" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in just one item in one of the process data arrays mapped to the service content.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.stringArrayAttribute = ['a string', 'another string'];\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.pathParamAttribute = 'aPathParam';\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.queryParamAttribute = 'aQueryParam';\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.headerParamAttribute = 'aHeaderParam';\n" //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	}

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is fully set.
	 *
	 * In this test the 'exclude empty optional objects/arrays' is set on - but this time we're providing values for all
	 * source mapping data.
	 *
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_FullySet_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);

		/*
		 * Setup payload test script
		 *
		 * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
		 * so it should all be there
		 *
		 */
		String expectedChildComplex = getExpectedChildComplexObject("CC", "01", "1");
		String expectedChildComplexArray0 = getExpectedChildComplexObject("CCA0", "02", "2");
		String expectedChildComplexArray1 = getExpectedChildComplexObject("CCA1", "03", "3");

		mappingScript += getTestObjectsEqualScript("{\n" //
				+ " childComplex : " //
				+ expectedChildComplex + "," //
				+ " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		String setupChildComplex = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute", "CC", "01", "1");

		String setupChildComplexArray0 = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0]", "CCA0", "02", "2");

		String setupChildComplexArray1 = getSetupNonMatchingNamesChildComplexScript(
				"data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1]", "CCA1", "03", "3");

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
				+ setupChildComplex //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute = [];\n" //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0] = {};\n" //
				+ setupChildComplexArray0 //
				+ "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1] = {};\n" //
				+ setupChildComplexArray1 //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * COMPLEX TYPE PAYLOAD from COMPLEX TYPE process data using a SINGLE LIKE MAPPING
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to COMPLEX TYPE PAYLOAD input payload - using a single LIKE MAPPING from mapping SOURCE COMPLEX TYPE
	 * process data that is fully set.
	 *
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_From_ComplexFields_Using_LikeMapping() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType", "complexTypeInputOutput - Like mapping",
				MappingDirection.IN);

		/*
		 * Setup payload test script
		 *
		 * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
		 * so it should all be there
		 *
		 */
		String expectedChildComplex = getExpectedChildComplexObject("CC", "01", "1");
		String expectedChildComplexArray0 = getExpectedChildComplexObject("CCA0", "02", "2");
		String expectedChildComplexArray1 = getExpectedChildComplexObject("CCA1", "03", "3");

		mappingScript += getTestObjectsEqualScript("{\n" //
				+ " childComplex : " //
				+ expectedChildComplex + "," //
				+ " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		String setupChildComplex = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames.childComplex", "CC", "01", "1");

		String setupChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[0]", "CCA0", "02", "2");

		String setupChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[1]", "CCA1", "03", "3");

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames = {};\n" //
				+ setupChildComplex //
				+ "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray = [];\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[0] = {};\n" //
				+ setupChildComplexArray0 //
				+ "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[1] = {};\n" //
				+ setupChildComplexArray1 //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * ARRAY COMPLEX TYPE PAYLOAD from ARRAY COMPLEX TYPE process data
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - mapping SOURCE ARRAY COMPLEX TYPE process data that is
	 * UNSET
	 * 
	 * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set.
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields_Unset_IncludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
				"complexArrayInputMultipleOutput - Include Empty", MappingDirection.IN);

		/*
		 * Setup payload test script (will contain empty arrays because we HAVEN'T setup 'exclude empty objects' on this
		 * task.
		 */
		mappingScript += getTestObjectsEqualScript("[]", "REST_PAYLOAD");

		/*
		 * Execute with No process data (testing is done by the script we appended to the mapping script
		 */
		executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"));
	}

	/**
	 * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - mapping SOURCE ARRAY COMPLEX TYPE process data that is
	 * UNSET
	 * 
	 * In this test the task DOES have the 'Exclude Empty Objects/Array' options set.
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields_Unset_ExcludeEmptyOptional()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
				"complexArrayInputMultipleOutput - Exclude Empty", MappingDirection.IN);

		/*
		 * Setup payload test script - the empty payload array should be null'ed as we're excluding empty optional
		 */
		mappingScript += getTestObjectsEqualScript("null", "REST_PAYLOAD");

		/*
		 * Execute with No process data (testing is done by the script we appended to the mapping script
		 */
		executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"));
	}

	/**
	 * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - from mapping ARRAY SOURCE COMPLEX TYPE process data
	 * that is fully set. We used a LIKE mapping in this test, but we already proved in other tests that LIKE mapping is
	 * working equivalence to manual mapping, so won't make a difference here.
	 *
	 * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
				"complexArrayInputMultipleOutput - Exclude Empty",
				MappingDirection.IN);

		/*
		 * Setup payload test script
		 *
		 * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
		 * so it should all be there
		 *
		 */
		String expectedArr0ChildComplex = getExpectedChildComplexObject("ARR0_CC", "01", "1");
		String expectedArr0ChildComplexArray0 = getExpectedChildComplexObject("ARR0_CCA0", "02", "2");
		String expectedArr0ChildComplexArray1 = getExpectedChildComplexObject("ARR0_CCA1", "03", "3");

		String expectedArr1ChildComplex = getExpectedChildComplexObject("ARR1_CC", "04", "4");
		String expectedArr1ChildComplexArray0 = getExpectedChildComplexObject("ARR1_CCA0", "05", "5");
		String expectedArr1ChildComplexArray1 = getExpectedChildComplexObject("ARR1_CCA1", "06", "6");

		mappingScript += getTestObjectsEqualScript( //
				"[" //
						+ "{\n" //
						+ " childComplex : " //
						+ expectedArr0ChildComplex + "," //
						+ " childComplexArray : [" + expectedArr0ChildComplexArray0 + ","
						+ expectedArr0ChildComplexArray1 + "]" //
						+ "}," //
						+ "{\n" //
						+ " childComplex : " //
						+ expectedArr1ChildComplex + "," //
						+ " childComplexArray : [" + expectedArr1ChildComplexArray0 + ","
						+ expectedArr1ChildComplexArray1 + "]" //
						+ "}" //
						+ "]", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		String setupArr0ChildComplex = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[0].childComplex", "ARR0_CC", "01", "1");
		String setupArr0ChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[0]", "ARR0_CCA0", "02", "2");
		String setupArr0ChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[1]", "ARR0_CCA1", "03", "3");

		String setupArr1ChildComplex = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[1].childComplex", "ARR1_CC", "04", "4");
		String setupArr1ChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[0]", "ARR1_CCA0", "05", "5");
		String setupArr1ChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
				"data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[1]", "ARR1_CCA1", "06", "6");

		/*
		 * Execute with No process data (testing is done by the script we appended to the mapping script
		 */
		executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames = [];\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames[0] = {};\n" //
				+ setupArr0ChildComplex //
				+ "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray = [];\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[0] = {};\n" //
				+ setupArr0ChildComplexArray0 //
				+ "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[1] = {};\n" //
				+ setupArr0ChildComplexArray1 //
				+ "data.FieldWithAttribsMatchingSwaggerNames[1] = {};\n" //
				+ setupArr1ChildComplex //
				+ "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray = [];\n" //
				+ "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[0] = {};\n" //
				+ setupArr1ChildComplexArray0 //
				+ "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[1] = {};\n" //
				+ setupArr1ChildComplexArray1 //

		));

	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * INLINE COMPLEX TYPE SCEHMA
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */
	/**
	 * Test mapping to INLINE COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is
	 * fully set.
	 *
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_InlineComplexType_From_ComplexFields() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType", "complexInlineTypeInputOutput",
				MappingDirection.IN);

		/*
		 * Setup payload test script
		 */
		String expectedChildComplex = getExpectedChildComplexObject("CC", "01", "1");
		String expectedChildComplexArray0 = getExpectedChildComplexObject("CCA0", "02", "2");
		String expectedChildComplexArray1 = getExpectedChildComplexObject("CCA1", "03", "3");

		mappingScript += getTestObjectsEqualScript("{\n" //
				+ " string : new String('a simple string')," //
				+ " stringArray : [new String('text item 1'), new String('text item 2')]," //
				+ " childComplex : " //
				+ expectedChildComplex + "," //
				+ " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
				+ "}", //
				"REST_PAYLOAD");

		/*
		 * Execute with a value in all items of the process data mapped to the service content.
		 */
		String setupChildComplex = getSetupMatchingNamesChildComplexScript(
				"data.MatchesAllPropertyTypesAsChildren", "CC", "01", "1");

		String setupChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
				"data.MatchesArrayAllPropertyTypesAsChildren[0]", "CCA0", "02", "2");

		String setupChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
				"data.MatchesArrayAllPropertyTypesAsChildren[1]", "CCA1", "03", "3");

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField = 'a simple string';\n" //
				+ "data.TextArrayField = ['text item 1', 'text item 2'];\n" //
				+ "data.MatchesAllPropertyTypesAsChildren = {};\n" //
				+ setupChildComplex //
				+ "data.MatchesArrayAllPropertyTypesAsChildren = [];\n" //
				+ "data.MatchesArrayAllPropertyTypesAsChildren[0] = {};\n" //
				+ setupChildComplexArray0 //
				+ "data.MatchesArrayAllPropertyTypesAsChildren[1] = {};\n" //
				+ setupChildComplexArray1 //
		));

		/* Make sure the other bits like path/query/header params have also been done. */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
		assertVariableValue(scriptContext, "REST_REQUEST.url",
				"/v2/complexInlineTypeInputOutput");
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 * 
	 * SHARED UTILITY FUNCTIONS
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */


	/**
	 * Returns a script that will define an EXPECTED TEST JSON object matching Swagger complexChild type attributes -
	 * each of which has values prefixed by those given as parameters here.
	 * 
	 * @param stringPrefix
	 * @param dateDayNum
	 * @param numberPrefix
	 * 
	 * @return A JSON object matching Swagger complexChild type attributes - each of which has values prefixed by those
	 *         given as parameters here.
	 */
	private String getExpectedChildComplexObject(String stringPrefix, String dateDayNum, String numberPrefix)
	{
		String expectedChildComplex = "{" //
				+ " string : new String('" + stringPrefix + " string'),"//
				+ " stringArray : [new String('" + stringPrefix + " Item 1'), new String('" + stringPrefix
				+ " Item 2')],"//
				+ " date : '2024-10-" + dateDayNum + "',"//
				+ " dateArray : ['1996-04-" + dateDayNum + "', '1998-03-" + dateDayNum + "'],"//
				+ " dateTime : '2024-10-" + dateDayNum + "T12:34:56.000Z',"//
				+ " dateTimeArray : ['1996-04-" + dateDayNum + "T02:15:00.000Z', '1998-03-" + dateDayNum //
				+ "T14:30:00.000Z'],"//
				+ " numberDouble : " + numberPrefix + "11.67,"//
				+ " numberDoubleArray : [" + numberPrefix + "22.21, -" + numberPrefix + "33.56],"//
				+ " numberFloat : " + numberPrefix + "44.6789,"//
				+ " numberFloatArray : [" + numberPrefix + "55.4321, -" + numberPrefix + "66.5678],"//
				+ " integer : " + numberPrefix + "77,"//
				+ " integerArray : [" + numberPrefix + "88, -" + numberPrefix + "99],"//
				+ " bool : false,"//
				+ " boolArray : [true, false, true],"//
				+ "}";
		return expectedChildComplex;
	}

	/**
	 * Create script to assign all the children of the given child complex type attribute of type
	 * AllPropertyTypesAsChildren. Predefined values are prefixed with the various prefix parameter values depending on
	 * data type.
	 * 
	 * @param complexAttributePath
	 *            The path for child complex field/attribute of type
	 * @param stringPrefix
	 * @param dateDatNum
	 * @param numberPrefix
	 * 
	 * @return script to setup complexAttributePath.xxxxx with default values for type AllPropertyTypesAsChildren - each
	 *         of which has values prefixed by those given as parameters here.
	 */
	private String getSetupNonMatchingNamesChildComplexScript(String complexAttributePath, String stringPrefix,
			String dateDatNum, String numberPrefix)
	{
		String setupChildComplex = complexAttributePath + " = {};\n" //
				+ complexAttributePath + ".stringAttribute = '" + stringPrefix + " string';\n"//
				+ complexAttributePath + ".stringArrayAttribute = ['" + stringPrefix + " Item 1', '" + stringPrefix
				+ " Item 2'];\n"//
				+ complexAttributePath + ".dateAttribute = new Date('2024-10-" + dateDatNum + "');\n"//
				+ complexAttributePath + ".dateArrayAttribute = [new Date('1996-04-" + dateDatNum
				+ "'), new Date('1998-03-" + dateDatNum + "')];\n"//
				+ complexAttributePath + ".dateTimeAttribute = new Date('2024-10-" + dateDatNum + "T12:34:56.000Z');\n"//
				+ complexAttributePath + ".dateTimeArrayAttribute = [new Date('1996-04-" + dateDatNum
				+ "T02:15:00.000Z'), new Date('1998-03-" + dateDatNum + "T14:30:00.000Z')];\n"//
				+ complexAttributePath + ".numberDoubleAttribute = " + numberPrefix + "11.67;\n"//
				+ complexAttributePath + ".numberDoubleArrayAttribute = [" + numberPrefix + "22.21, -" + numberPrefix
				+ "33.56];\n"//
				+ complexAttributePath + ".numberFloatAttribute = " + numberPrefix + "44.6789;\n"//
				+ complexAttributePath + ".numberFloatArrayAttribute = [" + numberPrefix + "55.4321, -" + numberPrefix
				+ "66.5678];\n"//
				+ complexAttributePath + ".integerAttribute = " + numberPrefix + "77;\n"//
				+ complexAttributePath + ".integerArrayAttribute = [" + numberPrefix + "88, -" + numberPrefix + "99];\n"//
				+ complexAttributePath + ".boolAttribute = false;\n"//
				+ complexAttributePath + ".boolArrayAttribute = [true, false, true];\n"//
				+ complexAttributePath + ".pathParamAttribute = 'aPathParam';\n" //
				+ complexAttributePath + ".queryParamAttribute = 'aQueryParam';\n" //
				+ complexAttributePath + ".headerParamAttribute = 'aHeaderParam';\n";
		return setupChildComplex;
	}

	/**
	 * Create script to assign all the children of the given child complex type attribute of type
	 * MatchesSwaggerAllPropertyTypesAsChildren. Predefined values are prefixed with the various prefix parameter values
	 * depending on data type.
	 * 
	 * As all the BOM attribute names match those of the AllPropertyTypesAsChildren Swagger schema type, this can be
	 * used for Like Mapping testing
	 * 
	 * @param complexAttributePath
	 *            The path for child complex field/attribute of type
	 * @param stringPrefix
	 * @param dateDatNum
	 * @param numberPrefix
	 * 
	 * @return script to setup complexAttributePath.xxxxx with default values for type
	 *         MatchesSwaggerAllPropertyTypesAsChildren - each of which has values prefixed by those given as parameters
	 *         here.
	 */
	private String getSetupMatchingNamesChildComplexScript(String complexAttributePath, String stringPrefix,
			String dateDatNum, String numberPrefix)
	{
		String setupChildComplex = complexAttributePath + " = {};\n" //
				+ complexAttributePath + ".string = '" + stringPrefix + " string';\n"//
				+ complexAttributePath + ".stringArray = ['" + stringPrefix + " Item 1', '" + stringPrefix
				+ " Item 2'];\n"//
				+ complexAttributePath + ".date = new Date('2024-10-" + dateDatNum + "');\n"//
				+ complexAttributePath + ".dateArray = [new Date('1996-04-" + dateDatNum
				+ "'), new Date('1998-03-" + dateDatNum + "')];\n"//
				+ complexAttributePath + ".dateTime = new Date('2024-10-" + dateDatNum + "T12:34:56.000Z');\n"//
				+ complexAttributePath + ".dateTimeArray = [new Date('1996-04-" + dateDatNum
				+ "T02:15:00.000Z'), new Date('1998-03-" + dateDatNum + "T14:30:00.000Z')];\n"//
				+ complexAttributePath + ".numberDouble = " + numberPrefix + "11.67;\n"//
				+ complexAttributePath + ".numberDoubleArray = [" + numberPrefix + "22.21, -" + numberPrefix
				+ "33.56];\n"//
				+ complexAttributePath + ".numberFloat = " + numberPrefix + "44.6789;\n"//
				+ complexAttributePath + ".numberFloatArray = [" + numberPrefix + "55.4321, -" + numberPrefix
				+ "66.5678];\n"//
				+ complexAttributePath + ".integer = " + numberPrefix + "77;\n"//
				+ complexAttributePath + ".integerArray = [" + numberPrefix + "88, -" + numberPrefix + "99];\n"//
				+ complexAttributePath + ".bool = false;\n"//
				+ complexAttributePath + ".boolArray = [true, false, true];\n"//
				+ complexAttributePath + ".pathParamAttribute = 'aPathParam';\n" //
				+ complexAttributePath + ".queryParamAttribute = 'aQueryParam';\n" //
				+ complexAttributePath + ".headerParamAttribute = 'aHeaderParam';\n";
		return setupChildComplex;
	}

}
