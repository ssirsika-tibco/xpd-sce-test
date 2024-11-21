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
public class SwaggerOutputComplexPayloadTest extends SwaggerScriptGeneratorExecutionTest
{
	/**
	 * 
	 */
	private static final BomClassAndArrayProperties[]	BOM_CLASSES		= new BomClassAndArrayProperties[]{
			new BomClassAndArrayProperties("AllDataArrayTypes", new String[]{"time",									//
					"boolean1",																							//
					"datetime",																							//
					"date",																								//
					"integer",																							//
					"fixedPointNumber",																					//
					"floatingPointNumber",																				//
					"text"}),																							//
			new BomClassAndArrayProperties("AllDataTypes", new String[]{}),												//
			new BomClassAndArrayProperties("AllPropertyTypesAsChildren", new String[]{"dateTimeArrayAttribute",			//
					"dateArrayAttribute",																				//
					"integerArrayAttribute",																			//
					"numberFloatArrayAttribute",																		//
					"numberDoubleArrayAttribute",																		//
					"boolArrayAttribute",																				//
					"stringArrayAttribute"}),																			//
			new BomClassAndArrayProperties("AllPropertyTypesAsGrandChildren",
					new String[]{"childComplexArrayAttribute"}),														//
			new BomClassAndArrayProperties("MatchesSwaggerAllPropertyTypesAsChildren", new String[]{"dateTimeArray",	//
					"dateArray",																						//
					"integerArray",																						//
					"numberFloatArray",																					//
					"numberDoubleArray",																							//
					"boolArray",																									//
					"stringArray"}),																								//
			new BomClassAndArrayProperties("MatchesSwaggerAllPropertyTypesAsGrandChildren",
					new String[]{"childComplexArray"}),																	//
			new BomClassAndArrayProperties("Tag", new String[]{})														//
	};

	/**
	 * 
	 */
	private static final String							BOM_CLASS_PKG	= "com_example_SwaggerScriptGenTests_data";

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
	 * COMPLEX TYPE PAYLOAD to SIMPLE TYPE process data
	 * 
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is UNSET - mapping TARGET SIMPLE TYPE process data
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_Unset_To_SimpleFields() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types", MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 */
		mappingScript += getTestProcessDataScript("{\n" //
				+ "   BoolArrayField : [],\n" //
				+ "   DateArrayField : [],\n" //
				+ "   DateTimeArrayField : [],\n" //
				+ "   FixedPointNumberArrayField : [],\n" //
				+ "   FixedPointNumberField : null,\n" //
				+ "   FloatingPointArrayField : [],\n" //
				+ "   IntegerArrayField : [],\n" //
				+ "   MatchingSwaggerAllPropertyTypesAsChildrenArray : [],\n" //
				+ "   MatchingSwaggerAllPropertyTypesAsGrandChildrenArray : [],\n" //
				+ "   TextArrayField : [],\n" //
				+ "   BoolField : null,\n" //
				+ "   DateField : null,\n" //
				+ "   DateTimeField : null,\n" //
				+ "   FloatingPointNumberField : null,\n" //
				+ "   IntegerField : null,\n" //
				+ "   OutputHeaderParam : null,\n" //
				+ "   TextField : null\n" //
				+ "}"); //

		/*
		 * Execute with unset payload data
		 */

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200,
				getSetupDataScript_ComplexTypesFromSimpleTypes(""), BOM_CLASS_PKG, BOM_CLASSES));
	}

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET SIMPLE TYPE process
	 * data.
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_To_SimpleFields()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes",
				"complexTypeInputOutput - mapped from Simple Types", MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value)
		 */
		mappingScript += getTestProcessDataScript("{\n" //
				+ "   BoolArrayField : [true, false, true],\n" //
				+ "   DateArrayField : [new Date('1996-04-01'), new Date('1998-03-01')],\n" //
				+ "   DateTimeArrayField : [new Date('1996-04-01T02:15:00.000Z'), new Date('1998-03-01T14:30:00.000Z')],\n" //
				+ "   FixedPointNumberArrayField : [122.21, -133.56],\n" //
				+ "   FixedPointNumberField : 111.67,\n" //
				+ "   FloatingPointArrayField : [155.4321, -166.5678],\n" //
				+ "   IntegerArrayField : [188, -199],\n" //
				+ "   MatchingSwaggerAllPropertyTypesAsChildrenArray : [],\n" //
				+ "   MatchingSwaggerAllPropertyTypesAsGrandChildrenArray : [],\n" //
				+ "   TextArrayField : ['CC Item 1', 'CC Item 2'],\n" //
				+ "   BoolField : false,\n" //
				+ "   DateField : new Date('2024-10-01'),\n" //
				+ "   DateTimeField : new Date('2024-10-01T12:34:56.000Z'),\n" //
				+ "   FloatingPointNumberField : 144.6789,\n" //
				+ "   IntegerField : 177,\n" //
				+ "   OutputHeaderParam : 'aHeaderParam',\n" //
				+ "   TextField : 'CC string'\n" //
				+ "}"); //

		/*
		 * Execute with fully loaded payload data
		 */
		String restResponseSetup = "REST_RESPONSE.data = " + getAllPropertyTypesAsGrandChildrenPayloadObject() + ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200,
				getSetupDataScript_ComplexTypesFromSimpleTypes(restResponseSetup), BOM_CLASS_PKG, BOM_CLASSES));
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
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is unset - mapping TARGET COMPLEX TYPE process data
	 * that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_Unset_To_ComplexField()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 */
		String expectedDataObject = "{\n" //
				+ "  Response200FieldWithAttribsNotMatchingSwaggerNames : {\n" //
				+ "    childComplexAttribute : {\n" //
				+ "      stringAttribute : null,\n"//
				+ "      stringArrayAttribute : [],\n"//
				+ "      dateAttribute : null,\n"//
				+ "      dateArrayAttribute : [],\n"//
				+ "      dateTimeAttribute : null,\n"//
				+ "      dateTimeArrayAttribute : [],\n"//
				+ "      numberDoubleAttribute : null,\n"//
				+ "      numberDoubleArrayAttribute : [],\n"//
				+ "      numberFloatAttribute : null,\n"//
				+ "      numberFloatArrayAttribute : [],\n"//
				+ "      integerAttribute : null,\n"//
				+ "      integerArrayAttribute : [],\n"//
				+ "      boolAttribute : null,\n"//
				+ "      boolArrayAttribute : [],\n"//
				+ "      headerParamAttribute : null\n" //
				+ "    },\n" //
				+ "    childComplexArrayAttribute : []\n" //
				+ "  }\n" //
				+ "}"; //

		mappingScript += getTestProcessDataScript(expectedDataObject); //
		
		/*
		 * Setup initial data object value.
		 */
		String setupDataScript = "data = {\n" //
				+ "  Response200FieldWithAttribsNotMatchingSwaggerNames : " //
				+ getInitialBomAllTypesAsGrandChildrenInitialValue() //
				+ "};\n"; //

		/*
		 * Execute with script with no payload data set.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(200,
				setupDataScript, BOM_CLASS_PKG, BOM_CLASSES));
	}

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET COMPLEX TYPE process
	 * data that is UNSET
	 * 
	 * @throws ScriptException
	 */
	public void testRequestPayloadScript_ComplexType_To_ComplexField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 */
		String expectedDataObject = "{\n" //
				+ "  Response200FieldWithAttribsNotMatchingSwaggerNames : {\n" //
				+ "    childComplexAttribute : " + getExpectedNonMatchingNamesChildComplexObject("CC", "01", "1", true) //
				+ " ,\n" //
				+ "    childComplexArrayAttribute : [" //
				+ getExpectedNonMatchingNamesChildComplexObject("CCA1", "02", "2", false) + ", " //
				+ getExpectedNonMatchingNamesChildComplexObject("CCA2", "03", "3", false) + "]\n" //
				+ "  }\n" //
				+ "}"; //

		mappingScript += getTestProcessDataScript(expectedDataObject); //

		/*
		 * Setup initial process data object value.
		 */
		String setupDataScript = "data = {\n" //
				+ "  Response200FieldWithAttribsNotMatchingSwaggerNames : " //
				+ getInitialBomAllTypesAsGrandChildrenInitialValue() //
				+ "};\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object
		 */
		String restResponseSetup = "REST_RESPONSE.data = " + getAllPropertyTypesAsGrandChildrenPayloadObject() + ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, setupDataScript + restResponseSetup, BOM_CLASS_PKG, BOM_CLASSES));
	}

	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - mapping SOURCE COMPLEX TYPE process data that is only
	// * partially set.
	// *
	// * In this test the task DOES NOT have the 'Exclude Empty Objects/Array' options set - therefore because
	// everything
	// * is optional AND we're providing just one piece of source content, then the whole payload should be created with
	// * empty arrays etc
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_ComplexFields_PartiallySet_IncludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
	// "complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value) task.
	// *
	// * The 'exclude empty optional objects/arrays' is set OFF - we will have data in the source mapping for
	// * payload.childComplex.stringArray and other arrays will be created
	// *
	// */
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " childComplex : {\n" //
	// + " stringArray : [new String('a string'), new String('another string')],\n" //
	// + " dateArray: [],\n" //
	// + " dateTimeArray: [],\n" //
	// + " numberDoubleArray: [],\n" //
	// + " numberFloatArray: [],\n" //
	// + " integerArray: [],\n" //
	// + " boolArray: [],\n" //
	// + " },\n" //
	// + " childComplexArray: []\n" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in just one item in one of the process data arrays mapped to the service content.
	// */
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute = {};\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.stringArrayAttribute = ['a string',
	// 'another string'];\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.pathParamAttribute = 'aPathParam';\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.queryParamAttribute = 'aQueryParam';\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.headerParamAttribute = 'aHeaderParam';\n"
	// //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url",
	// "/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	// }
	//
	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - mapping SOURCE COMPLEX TYPE process data that is fully
	// * set.
	// *
	// * In this test the 'exclude empty optional objects/arrays' is set OFF - but this time we're providing values for
	// * all source mapping data.
	// *
	// * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_ComplexFields_FullySet_IncludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
	// "complexTypeInputOutput - mapped from Complex Type - Include Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value)
	// *
	// * The 'exclude empty optional objects/arrays' is set OFF - - but anyway we've provided data for all input
	// * anyway, so it should all be there (except childComplexArray because we can't mapp to that from simple type
	// * data)
	// *
	// */
	// String expectedChildComplex = getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1");
	// String expectedChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("CCA0", "02", "2");
	// String expectedChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("CCA1", "03", "3");
	//
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " childComplex : " //
	// + expectedChildComplex + "," //
	// + " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in all items of the process data mapped to the service content.
	// */
	// String setupChildComplex = getExpectedNonMatchingNamesChildComplexObject(
	// "CC", "01", "1", true);
	//
	// String setupChildComplexArray0 = getExpectedNonMatchingNamesChildComplexObject(
	// "CCA0", "02", "2", false);
	//
	// String setupChildComplexArray1 = getExpectedNonMatchingNamesChildComplexObject(
	// "CCA1", "03", "3", false);
	//
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
	// + setupChildComplex //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute = [];\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0] = {};\n" //
	// + setupChildComplexArray0 //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1] = {};\n" //
	// + setupChildComplexArray1 //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url",
	// "/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	// }
	//
	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - mapping SOURCE COMPLEX TYPE process data that is UNSET
	// *
	// * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional
	// AND
	// * we're not providing any source content, then the whole payload should be null
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_CompleFields_Unset_ExcludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
	// "complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value) task.
	// *
	// * The 'exclude empty optional objects/arrays' is set on - therefore because everything is optional AND we're
	// * not providing any source content, then the whole payload should be null
	// *
	// */
	// mappingScript += getTestObjectsEqualScript("null", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with No process data
	// */
	// ScriptContext scriptContext = executor.executeScript(mappingScript,
	// getRestRequestInitScript("var data = {};\n"));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInputMultipleOutput/null");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", null);
	// }
	//
	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - mapping SOURCE COMPLEX TYPE process data that is only
	// * partially set.
	// *
	// * In this test the 'exclude empty optional objects/arrays' is set on - therefore because everything is optional
	// AND
	// * we're providing just one piece of source content, then the whole payload should be created with only the bear
	// * minimum of content to hold that non-null value
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_ComplexFields_PartiallySet_ExcludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
	// "complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value) task.
	// *
	// * The 'exclude empty optional objects/arrays' is set on - we will have data only in the source mapping for
	// * payload.childComplex.stringArray. So the payload should have just that content in.
	// *
	// */
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " childComplex : {\n" //
	// + " stringArray : [new String('a string'), new String('another string')],\n" //
	// + " }\n" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in just one item in one of the process data arrays mapped to the service content.
	// */
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute = {};\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.stringArrayAttribute = ['a string',
	// 'another string'];\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.pathParamAttribute = 'aPathParam';\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.queryParamAttribute = 'aQueryParam';\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexAttribute.headerParamAttribute = 'aHeaderParam';\n"
	// //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url",
	// "/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	// }
	//
	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - mapping SOURCE COMPLEX TYPE process data that is fully
	// * set.
	// *
	// * In this test the 'exclude empty optional objects/arrays' is set on - but this time we're providing values for
	// all
	// * source mapping data.
	// *
	// * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_ComplexFields_FullySet_ExcludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
	// "complexTypeInputOutput - mapped from Complex Type - Exclude Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value)
	// *
	// * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
	// * so it should all be there
	// *
	// */
	// String expectedChildComplex = getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1");
	// String expectedChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("CCA0", "02", "2");
	// String expectedChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("CCA1", "03", "3");
	//
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " childComplex : " //
	// + expectedChildComplex + "," //
	// + " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in all items of the process data mapped to the service content.
	// */
	// String setupChildComplex = getExpectedNonMatchingNamesChildComplexObject(
	// "CC", "01", "1", true);
	//
	// String setupChildComplexArray0 = getExpectedNonMatchingNamesChildComplexObject(
	// "CCA0", "02", "2", false);
	//
	// String setupChildComplexArray1 = getExpectedNonMatchingNamesChildComplexObject(
	// "CCA1", "03", "3", false);
	//
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames = {};\n" //
	// + setupChildComplex //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute = [];\n" //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[0] = {};\n" //
	// + setupChildComplexArray0 //
	// + "data.FieldWithAttribsNotMatchingSwaggerNames.childComplexArrayAttribute[1] = {};\n" //
	// + setupChildComplexArray1 //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url",
	// "/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	// }
	//
	// /**
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// *
	// * COMPLEX TYPE PAYLOAD from COMPLEX TYPE process data using a SINGLE LIKE MAPPING
	// *
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// */
	//
	// /**
	// * Test mapping from COMPLEX TYPE PAYLOAD output payload - using a single LIKE MAPPING from mapping SOURCE COMPLEX
	// * TYPE process data that is fully set.
	// *
	// * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ComplexType_From_ComplexFields_Using_LikeMapping() throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType", "complexTypeInputOutput - Like mapping",
	// MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value)
	// *
	// * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
	// * so it should all be there
	// *
	// */
	// String expectedChildComplex = getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1");
	// String expectedChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("CCA0", "02", "2");
	// String expectedChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("CCA1", "03", "3");
	//
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " childComplex : " //
	// + expectedChildComplex + "," //
	// + " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in all items of the process data mapped to the service content.
	// */
	// String setupChildComplex = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames.childComplex", "CC", "01", "1");
	//
	// String setupChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[0]", "CCA0", "02", "2");
	//
	// String setupChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[1]", "CCA1", "03", "3");
	//
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.FieldWithAttribsMatchingSwaggerNames = {};\n" //
	// + setupChildComplex //
	// + "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray = [];\n" //
	// + "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[0] = {};\n" //
	// + setupChildComplexArray0 //
	// + "data.FieldWithAttribsMatchingSwaggerNames.childComplexArray[1] = {};\n" //
	// + setupChildComplexArray1 //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url",
	// "/v2/complexInputMultipleOutput/aPathParam?stringQuery=aQueryParam");
	// assertVariableValue(scriptContext, "REST_REQUEST.headers.stringHeader", "aHeaderParam");
	// }
	//
	// /**
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// *
	// * ARRAY COMPLEX TYPE PAYLOAD from ARRAY COMPLEX TYPE process data
	// *
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// */
	//
	// /**
	// * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - mapping SOURCE ARRAY COMPLEX TYPE process data that
	// is
	// * UNSET
	// *
	// *
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields_Unset_IncludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
	// "complexArrayInputMultipleOutput - Include Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value) task.
	// */
	// mappingScript += getTestObjectsEqualScript("[]", "REST_PAYLOAD");
	//
	// /*
	// * Execute with No process data (testing is done by the script we appended to the mapping script
	// */
	// executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"));
	// }
	//
	// /**
	// * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - mapping SOURCE ARRAY COMPLEX TYPE process data that
	// is
	// * UNSET
	// *
	// * In this test the task DOES have the 'Exclude Empty Objects/Array' options set.
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields_Unset_ExcludeEmptyOptional()
	// throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
	// "complexArrayInputMultipleOutput - Exclude Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value) - the empty payload array should be null'ed as we're
	// * excluding empty optional
	// */
	// mappingScript += getTestObjectsEqualScript("null", "REST_PAYLOAD");
	//
	// /*
	// * Execute with No process data (testing is done by the script we appended to the mapping script
	// */
	// executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"));
	// }
	//
	// /**
	// * Test mapping to ARRAY COMPLEX TYPE PAYLOAD input payload - from mapping ARRAY SOURCE COMPLEX TYPE process data
	// * that is fully set. We used a LIKE mapping in this test, but we already proved in other tests that LIKE mapping
	// is
	// * working equivalence to manual mapping, so won't make a difference here.
	// *
	// * So we'll be testing correct mapping of all simple type properties and arrays under Payload.childComplex object
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_ArrayComplexType_From_ArrayComplexFields() throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
	// "complexArrayInputMultipleOutput - Exclude Empty", MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value)
	// *
	// * The 'exclude empty optional objects/arrays' is set on - but anyway we've provided data for all input anyway,
	// * so it should all be there
	// *
	// */
	// String expectedArr0ChildComplex = getAllPropertyTypesAsChildrenPayloadObject("ARR0_CC", "01", "1");
	// String expectedArr0ChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("ARR0_CCA0", "02", "2");
	// String expectedArr0ChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("ARR0_CCA1", "03", "3");
	//
	// String expectedArr1ChildComplex = getAllPropertyTypesAsChildrenPayloadObject("ARR1_CC", "04", "4");
	// String expectedArr1ChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("ARR1_CCA0", "05", "5");
	// String expectedArr1ChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("ARR1_CCA1", "06", "6");
	//
	// mappingScript += getTestObjectsEqualScript( //
	// "[" //
	// + "{\n" //
	// + " childComplex : " //
	// + expectedArr0ChildComplex + "," //
	// + " childComplexArray : [" + expectedArr0ChildComplexArray0 + ","
	// + expectedArr0ChildComplexArray1 + "]" //
	// + "}," //
	// + "{\n" //
	// + " childComplex : " //
	// + expectedArr1ChildComplex + "," //
	// + " childComplexArray : [" + expectedArr1ChildComplexArray0 + ","
	// + expectedArr1ChildComplexArray1 + "]" //
	// + "}" //
	// + "]", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in all items of the process data mapped to the service content.
	// */
	// String setupArr0ChildComplex = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[0].childComplex", "ARR0_CC", "01", "1");
	// String setupArr0ChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[0]", "ARR0_CCA0", "02", "2");
	// String setupArr0ChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[1]", "ARR0_CCA1", "03", "3");
	//
	// String setupArr1ChildComplex = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[1].childComplex", "ARR1_CC", "04", "4");
	// String setupArr1ChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[0]", "ARR1_CCA0", "05", "5");
	// String setupArr1ChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
	// "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[1]", "ARR1_CCA1", "06", "6");
	//
	// /*
	// * Execute with No process data (testing is done by the script we appended to the mapping script
	// */
	// executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
	// + "data.FieldWithAttribsMatchingSwaggerNames = [];\n" //
	// + "data.FieldWithAttribsMatchingSwaggerNames[0] = {};\n" //
	// + setupArr0ChildComplex //
	// + "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray = [];\n" //
	// + "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[0] = {};\n" //
	// + setupArr0ChildComplexArray0 //
	// + "data.FieldWithAttribsMatchingSwaggerNames[0].childComplexArray[1] = {};\n" //
	// + setupArr0ChildComplexArray1 //
	// + "data.FieldWithAttribsMatchingSwaggerNames[1] = {};\n" //
	// + setupArr1ChildComplex //
	// + "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray = [];\n" //
	// + "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[0] = {};\n" //
	// + setupArr1ChildComplexArray0 //
	// + "data.FieldWithAttribsMatchingSwaggerNames[1].childComplexArray[1] = {};\n" //
	// + setupArr1ChildComplexArray1 //
	//
	// ));
	//
	// }
	//
	// /**
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// *
	// * INLINE COMPLEX TYPE SCEHMA
	// *
	// * =====================================================================================================
	// * =====================================================================================================
	// * =====================================================================================================
	// */
	// /**
	// * Test mapping to INLINE COMPLEX TYPE PAYLOAD input payload - mapping SOURCE COMPLEX TYPE process data that is
	// * fully set.
	// *
	// * @throws ScriptException
	// */
	// public void testRequestPayloadScript_InlineComplexType_From_ComplexFields() throws ScriptException
	// {
	// fail("Not implemented yet");
	// String mappingScript = generateRestMappingScript(
	// "/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
	// "SwaggerScriptGenTests_ComplexTypes-From-ComplexType", "complexInlineTypeInputOutput",
	// MappingDirection.IN);
	//
	// /*
	// * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
	// * non-array is always set to null if not input value)
	// */
	// String expectedChildComplex = getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1");
	// String expectedChildComplexArray0 = getAllPropertyTypesAsChildrenPayloadObject("CCA0", "02", "2");
	// String expectedChildComplexArray1 = getAllPropertyTypesAsChildrenPayloadObject("CCA1", "03", "3");
	//
	// mappingScript += getTestObjectsEqualScript("{\n" //
	// + " string : new String('a simple string')," //
	// + " stringArray : [new String('text item 1'), new String('text item 2')]," //
	// + " childComplex : " //
	// + expectedChildComplex + "," //
	// + " childComplexArray : [" + expectedChildComplexArray0 + "," + expectedChildComplexArray1 + "]" //
	// + "}", //
	// "REST_PAYLOAD");
	//
	// /*
	// * Execute with a value in all items of the process data mapped to the service content.
	// */
	// String setupChildComplex = getSetupMatchingNamesChildComplexScript("data.MatchesAllPropertyTypesAsChildren",
	// "CC", "01", "1");
	//
	// String setupChildComplexArray0 = getSetupMatchingNamesChildComplexScript(
	// "data.MatchesArrayAllPropertyTypesAsChildren[0]", "CCA0", "02", "2");
	//
	// String setupChildComplexArray1 = getSetupMatchingNamesChildComplexScript(
	// "data.MatchesArrayAllPropertyTypesAsChildren[1]", "CCA1", "03", "3");
	//
	// ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n"
	// //
	// + "data.TextField = 'a simple string';\n" //
	// + "data.TextArrayField = ['text item 1', 'text item 2'];\n" //
	// + "data.MatchesAllPropertyTypesAsChildren = {};\n" //
	// + setupChildComplex //
	// + "data.MatchesArrayAllPropertyTypesAsChildren = [];\n" //
	// + "data.MatchesArrayAllPropertyTypesAsChildren[0] = {};\n" //
	// + setupChildComplexArray0 //
	// + "data.MatchesArrayAllPropertyTypesAsChildren[1] = {};\n" //
	// + setupChildComplexArray1 //
	// ));
	//
	// /* Make sure the other bits like path/query/header params have also been done. */
	// assertVariableValue(scriptContext, "REST_REQUEST.method", "PUT");
	// assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/complexInlineTypeInputOutput");
	// }

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
	 * Get a script that initialises the the process data in process
	 * "SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes"
	 * 
	 * @return script to setup the process "data" object for the "SwaggerScriptGenTests_ComplexTypes-From-SimpleTypes"
	 *         process.
	 */
	private String getSetupDataScript_ComplexTypesFromSimpleTypes(String responsePayloadSetup)
	{
		// The main object and top level arrays need to be created
		return "var data = {};\n" //
				+ "data.BoolArrayField = [];\n" //
				+ "data.DateArrayField = [];\n" //
				+ "data.DateTimeArrayField = [];\n" //
				+ "data.FixedPointNumberArrayField = [];\n" //
				+ "data.FixedPointNumberField = [];\n" //
				+ "data.FloatingPointArrayField = [];\n" //
				+ "data.IntegerArrayField = [];\n" //
				+ "data.MatchingSwaggerAllPropertyTypesAsChildrenArray = [];\n" //
				+ "data.MatchingSwaggerAllPropertyTypesAsGrandChildrenArray = [];\n" //
				+ "data.TextArrayField = [];\n" //
				+ (responsePayloadSetup != null ? responsePayloadSetup : ""); //
	}

	/**
	 * Get JS object that would be initial value of a field of type AllPropertyTypesAsGrandChildren
	 * 
	 * @param responsePayloadSetup
	 * 
	 * @return script to setup the process "data" object for the "SwaggerScriptGenTests_ComplexTypes-From-ComplexType"
	 *         process.
	 */
	private String getInitialBomAllTypesAsGrandChildrenInitialValue()
	{
		return "{\n" //
				+ "    childComplexAttribute : {\n"//
				+ "      stringArrayAttribute : [],\n"//
				+ "      dateArrayAttribute : [],\n"//
				+ "      dateTimeArrayAttribute : [],\n"//
				+ "      numberDoubleArrayAttribute : [],\n"//
				+ "      numberFloatArrayAttribute : [],\n"//
				+ "      integerArrayAttribute : [],\n"//
				+ "      boolArrayAttribute : []\n"//
				+ "    }," //
				+ "    childComplexArrayAttribute : [],\n"//
				+ "  }\n"; //
	}
	/**
	 * Returns a script fragment that defines an object matching the AllPropertyTypesAsGrandChildrenChildren class type
	 * with all values set
	 * 
	 * @return JSON object string
	 */
	private String getAllPropertyTypesAsGrandChildrenPayloadObject()
	{
		String childComplexObject = getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1");
		String childComplexArrayObject1 = getAllPropertyTypesAsChildrenPayloadObject("CCA1", "02", "2");
		String childComplexArrayObject2 = getAllPropertyTypesAsChildrenPayloadObject("CCA2", "03", "3");

		return "JSON.stringify({\n" //
				+ " childComplex : " + childComplexObject + ",\n" //
				+ " childComplexArray : [\n" //
				+ childComplexArrayObject1 + ",\n" //
				+ childComplexArrayObject2 + "]\n" //
				+ "});"; //
	}

	/**
	 * Returns a script fragment that defines an object matching the AllPropertyTypesAsChildren class type with values
	 * altered according to the parameter values
	 * 
	 * @param stringPrefix
	 * @param dateDayNum
	 * @param numberPrefix
	 * 
	 * @return A JSON object matching Swagger complexChild type attributes - each of which has values prefixed by those
	 *         given as parameters here.
	 */
	private String getAllPropertyTypesAsChildrenPayloadObject(String stringPrefix, String dateDayNum,
			String numberPrefix)
	{
		String expectedChildComplex = "{" //
				+ "  string : '" + stringPrefix + " string',"//
				+ "  stringArray : ['" + stringPrefix + " Item 1', '" + stringPrefix + " Item 2'],"//
				+ "  date : '2024-10-" + dateDayNum + "',"//
				+ "  dateArray : [new Date('1996-04-" + dateDayNum + "'), new Date('1998-03-" + dateDayNum + "')],"//
				+ "  dateTime : new Date('2024-10-" + dateDayNum + "T12:34:56.000Z'),"//
				+ "  dateTimeArray : [new Date('1996-04-" + dateDayNum + "T02:15:00.000Z'), new Date('1998-03-"
				+ dateDayNum + "T14:30:00.000Z')],"//
				+ "  numberDouble : " + numberPrefix + "11.67,"//
				+ "  numberDoubleArray : [" + numberPrefix + "22.21, -" + numberPrefix + "33.56],"//
				+ "  numberFloat : " + numberPrefix + "44.6789,"//
				+ "  numberFloatArray : [" + numberPrefix + "55.4321, -" + numberPrefix + "66.5678],"//
				+ "  integer : " + numberPrefix + "77,"//
				+ "  integerArray : [" + numberPrefix + "88, -" + numberPrefix + "99],"//
				+ "  bool : false,"//
				+ "  boolArray : [true, false, true],"//
				+ "}";
		return expectedChildComplex;
	}

	/**
	 * Returns expected process data object with all children assigned matching BOM type AllPropertyTypesAsChildren.
	 * Predefined values are prefixed with the various prefix parameter values depending on data type.
	 * 
	 * Because this type's attribute names don't match the equivalent complex tyype in test swagger service, it cannot
	 * be used for complex type mapping.
	 * 
	 * @param stringPrefix
	 * @param dateDatNum
	 * @param numberPrefix
	 * @param includeHeadParam
	 *            TODO
	 * @return script to define an object complexAttributePath.xxxxx with default values for type
	 *         AllPropertyTypesAsChildren - each of which has values prefixed by those given as parameters here.
	 */
	private String getExpectedNonMatchingNamesChildComplexObject(String stringPrefix, String dateDatNum,
			String numberPrefix, boolean includeHeadParam)
	{
		String setupChildComplex = "{\n" //
				+ "  boolAttribute : false,\n"//
				+ "  dateAttribute : new Date('2024-10-" + dateDatNum + "'),\n"//
				+ "  dateTimeAttribute : new Date('2024-10-" + dateDatNum + "T12:34:56.000Z'),\n"//
				+ "  integerAttribute : " + numberPrefix + "77,\n"//
				+ "  numberDoubleAttribute : " + numberPrefix + "11.67,\n"//
				+ "  numberFloatAttribute : " + numberPrefix + "44.6789,\n"//
				+ "  stringAttribute : '" + stringPrefix + " string',\n"//
				+ "  boolArrayAttribute : [true, false, true],\n"//
				+ "  dateArrayAttribute : [new Date('1996-04-" + dateDatNum
				+ "'), new Date('1998-03-" + dateDatNum + "')],\n"//
				+ "  dateTimeArrayAttribute : [new Date('1996-04-" + dateDatNum
				+ "T02:15:00.000Z'), new Date('1998-03-" + dateDatNum + "T14:30:00.000Z')],\n"//
				+ "  integerArrayAttribute : [" + numberPrefix + "88, -" + numberPrefix + "99],\n"//
				+ "  numberDoubleArrayAttribute : [" + numberPrefix + "22.21, -" + numberPrefix
				+ "33.56],\n"//
				+ "  numberFloatArrayAttribute : [" + numberPrefix + "55.4321, -" + numberPrefix
				+ "66.5678],\n"//
				+ "  stringArrayAttribute : ['" + stringPrefix + " Item 1', '" + stringPrefix + " Item 2']" //
				+ (includeHeadParam ? ",\n  headerParamAttribute : 'aHeaderParam',\n" : "\n") //
				+ "}"; //
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
				+ complexAttributePath + ".dateArray = [new Date('1996-04-" + dateDatNum + "'), new Date('1998-03-"
				+ dateDatNum + "')];\n"//
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
