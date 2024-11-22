/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

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
					"numberDoubleArray",																				//
					"boolArray",																						//
					"stringArray"}),																					//
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
	public void testResponsePayloadScript_ComplexType_Unset_To_SimpleFields() throws ScriptException
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

		executor.executeScript(mappingScript, getRestResponseInitScript(200,
				getSetupDataScript_ComplexTypesFromSimpleTypes(""), BOM_CLASS_PKG, BOM_CLASSES));
	}

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET SIMPLE TYPE process
	 * data.
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_ComplexType_To_SimpleFields() throws ScriptException
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
		String restResponseSetup = "REST_RESPONSE.data = " + getAllPropertyTypesAsGrandChildrenPayloadJSON() + ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		executor.executeScript(mappingScript, getRestResponseInitScript(200,
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
	public void testResponsePayloadScript_ComplexType_Unset_To_ComplexField() throws ScriptException
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
		String setupDataScript = "data = {};\n"; //

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScript,
				getRestResponseInitScript(200, setupDataScript, BOM_CLASS_PKG, BOM_CLASSES));
	}

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET COMPLEX TYPE process
	 * data
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_ComplexType_To_ComplexField() throws ScriptException
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
		String setupDataScript = "data = {};\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object
		 */
		String restResponseSetup = "REST_RESPONSE.data = " + getAllPropertyTypesAsGrandChildrenPayloadJSON()
				+ ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScript,
				getRestResponseInitScript(200, setupDataScript + restResponseSetup, BOM_CLASS_PKG, BOM_CLASSES));
	}

	/**
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 *
	 * COMPLEX TYPE PAYLOAD to COMPLEX TYPE process data using a SINGLE LIKE MAPPING
	 *
	 * =====================================================================================================
	 * =====================================================================================================
	 * =====================================================================================================
	 */

	/**
	 * Test mapping from COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET COMPLEX TYPE process
	 * 
	 * This test also checks that multiple success mappings are supported correctly and produce the correct result
	 * depending on the status code. The data types for each response is different so it also ensures that there are no
	 * mix ups in mapping script generation between the two separate mapped outputs.
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_ComplexType_To_ComplexField_UsingLikeMapping() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType", "complexTypeInputOutput - Like mapping",
				MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 * 
		 * We have 2 sets of output like mappings, one to Response200FieldWithAttribsNotMatchingSwaggerNames for
		 * response 200 and one to Response201FieldWithAttribsMatchingSwaggerNames for response 201
		 * 
		 * In the first test run we'll test the 200 response, so we should end up with a populated
		 * Response200FieldWithAttribsNotMatchingSwaggerNames and an initialised
		 * Response201FieldWithAttribsMatchingSwaggerNames.
		 */
		String expectedDataObjectResponse200 = "{\n" //
				+ "  Response200FieldWithAttribsMatchingSwaggerNames : {\n" //
				+ "    childComplex: " + getExpectedMatchingNamesChildComplexObject("CC", "01", "1", true) //
				+ " ,\n" //
				+ "    childComplexArray : [" //
				+ getExpectedMatchingNamesChildComplexObject("CCA1", "02", "2", false) + ", " //
				+ getExpectedMatchingNamesChildComplexObject("CCA2", "03", "3", false) + "]\n" //
				+ "  },\n" //
				+ "  Response201FieldWithAttribsMatchingSwaggerNames : {\n" //
				+ "      string : null,\n"//
				+ "      stringArray : [],\n"//
				+ "      date : null,\n"//
				+ "      dateArray : [],\n"//
				+ "      dateTime : null,\n"//
				+ "      dateTimeArray : [],\n"//
				+ "      numberDouble : null,\n"//
				+ "      numberDoubleArray : [],\n"//
				+ "      numberFloat : null,\n"//
				+ "      numberFloatArray : [],\n"//
				+ "      integer : null,\n"//
				+ "      integerArray : [],\n"//
				+ "      bool : null,\n"//
				+ "      boolArray : [],\n"//
				+ "      headerParamAttribute : null\n" //
				+ "  }\n" //
				+ "}"; //

		String mappingScriptResponse200 = mappingScript + getTestProcessDataScript(expectedDataObjectResponse200); //

		/*
		 * Setup initial process data object value.
		 */
		String setupDataScript200 = "data = {};\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object for response 200's
		 * AllPropertyTypesAsGrandChildren object
		 */
		String restResponseSetup200 = "REST_RESPONSE.data = " + getAllPropertyTypesAsGrandChildrenPayloadJSON() + ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScriptResponse200,
				getRestResponseInitScript(200, setupDataScript200 + restResponseSetup200, BOM_CLASS_PKG, BOM_CLASSES));

		/*
		 * In the second test run we'll test the 201 response, so we should end up with a populated
		 * Response201FieldWithAttribsMatchingSwaggerNames and an initialised
		 * Response200FieldWithAttribsNotMatchingSwaggerNames.
		 */
		String expectedDataObjectResponse201 = "{\n" //
				+ "  Response201FieldWithAttribsMatchingSwaggerNames : " //
				+ getExpectedMatchingNamesChildComplexObject("CC_201", "04", "4", true) //
				+ "  ,\n" //
				+ "  Response200FieldWithAttribsMatchingSwaggerNames : {\n" //
				+ "    childComplex : {\n" + "      string : null,\n"//
				+ "      stringArray : [],\n"//
				+ "      date : null,\n"//
				+ "      dateArray : [],\n"//
				+ "      dateTime : null,\n"//
				+ "      dateTimeArray : [],\n"//
				+ "      numberDouble : null,\n"//
				+ "      numberDoubleArray : [],\n"//
				+ "      numberFloat : null,\n"//
				+ "      numberFloatArray : [],\n"//
				+ "      integer : null,\n"//
				+ "      integerArray : [],\n"//
				+ "      bool : null,\n"//
				+ "      boolArray : [],\n"//
				+ "      headerParamAttribute : null\n" //
				+ "    },\n" //
				+ "    childComplexArray : []\n" //
				+ "  }\n" //
				+ "}"; //

		String mappingScriptResponse201 = mappingScript + getTestProcessDataScript(expectedDataObjectResponse201); //

		/*
		 * Setup initial process data object value.
		 */
		String setupDataScript201 = "data = {};\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object for response 200's
		 * AllPropertyTypesAsGrandChildren object
		 */
		String restResponseSetup201 = "REST_RESPONSE.data = "
				+ getAllPropertyTypesAsChildrenPayloadJSON("CC_201", "04", "4") + ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScriptResponse201,
				getRestResponseInitScript(201, setupDataScript201 + restResponseSetup201, BOM_CLASS_PKG, BOM_CLASSES));

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
	 * Test mapping from ARRAY COMPLEX TYPE PAYLOAD output payload that is fully set - mapping TARGET ARRAY COMPLEX TYPE
	 * process
	 * 
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_ComplexType_To_ComplexField_Array() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ArrayComplexTypes-From-ComplexType",
				"complexArrayInputMultipleOutput - Exclude Empty",
				MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 */
		String expectedDataObjectResponse200 = "{\n" //
				+ "  Response200FieldWithAttribsMatchingSwaggerNames : [" //
				+ "  {\n" //
				+ "    childComplex: " + getExpectedMatchingNamesChildComplexObject("GC1_CC", "01", "1", false) //
				+ " ,\n" //
				+ "    childComplexArray : [" //
				+ getExpectedMatchingNamesChildComplexObject("GC1_CCA1", "02", "2", false) + ", " //
				+ getExpectedMatchingNamesChildComplexObject("GC1_CCA2", "03", "3", false) + "]\n" //
				+ "  },\n" //
				+ "  {\n" //
				+ "    childComplex: " + getExpectedMatchingNamesChildComplexObject("GC2_CC", "04", "4", false) //
				+ " ,\n" //
				+ "    childComplexArray : [" //
				+ getExpectedMatchingNamesChildComplexObject("GC2_CCA1", "05", "5", false) + ", " //
				+ getExpectedMatchingNamesChildComplexObject("GC2_CCA2", "06", "6", false) + "]\n" //
				+ "  }" //
				+ " ]\n" //
				+ "}"; //

		String mappingScriptResponse200 = mappingScript + getTestProcessDataScript(expectedDataObjectResponse200); //

		/*
		 * Setup initial process data object value.
		 */
		String setupDataScript200 = "data = {\n" //
				+ "  Response200FieldWithAttribsMatchingSwaggerNames : []" //
				+ "};\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object for response 200's
		 * AllPropertyTypesAsGrandChildren object
		 */
		String restResponseSetup200 = "REST_RESPONSE.data = " + getArrayAllPropertyTypesAsGrandChildrenPayloadJSON()
				+ ";\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScriptResponse200,
				getRestResponseInitScript(200, setupDataScript200 + restResponseSetup200, BOM_CLASS_PKG, BOM_CLASSES));

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
	 * Test mapping to INLINE COMPLEX TYPE PAYLOAD input payload that is fully set. - mapping TARGET COMPLEX TYPE
	 * process data
	 *
	 * @throws ScriptException
	 */
	public void testResponsePayloadScript_InlineComplexType_To_ComplexField() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests_ComplexTypes.xpdl",
				"SwaggerScriptGenTests_ComplexTypes-From-ComplexType",
				"complexInlineTypeInputOutput", MappingDirection.OUT);

		/*
		 * Script to setup 'expected process data object' (all arrays will always be initialised and any mapped
		 * non-array is always set to null if not input value) task.
		 */
		String expectedDataObject = "{\n" //
				+ "  Response200TextField: 'a simple string',\n" //
				+ "  Response200TextArrayField: ['Text item 1', 'Text Item 2'],\n" //
				+ "  Response200MatchesAllPropertyTypesAsChildren : "
				+ getExpectedMatchingNamesChildComplexObject("CC", "01", "1", false) //
				+ " ,\n" //
				+ "  Response200MatchesArrayAllPropertyTypesAsChildren : [\n" //
				+ getExpectedMatchingNamesChildComplexObject("CCA1", "02", "2", false) //
				+ " ,\n" //
				+ getExpectedMatchingNamesChildComplexObject("CCA2", "03", "3", false) //
				+ " ]\n" //
				+ "}"; //

		mappingScript += getTestProcessDataScript(expectedDataObject); //

		/*
		 * Setup initial process data object value.
		 */
		String setupDataScript = "data = { Response200TextArrayField: [], Response200MatchesArrayAllPropertyTypesAsChildren : [] };\n"; //

		/*
		 * Setup REST_RESPONSE.getData() to return fully populated object
		 */
		String restResponseSetup = "REST_RESPONSE.data = JSON.stringify({\n" //
				+ "  string : 'a simple string'," //
				+ "  stringArray : ['Text item 1', 'Text Item 2']," //
				+ "  childComplex : " + getAllPropertyTypesAsChildrenPayloadObject("CC", "01", "1") + "," //
				+ "  childComplexArray : [\n" //
				+ getAllPropertyTypesAsChildrenPayloadObject("CCA1", "02", "2") + ",\n" //
				+ getAllPropertyTypesAsChildrenPayloadObject("CCA2", "03", "3") + "\n]\n" //
				+ "});\n" //
				+ "REST_RESPONSE.headers.HeaderParam = 'aHeaderParam';\n";

		/*
		 * Execute with script with no payload data set.
		 */
		executor.executeScript(mappingScript,
				getRestResponseInitScript(200, setupDataScript + restResponseSetup, BOM_CLASS_PKG, BOM_CLASSES));
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
	 * Returns a script fragment that defines an array of objects matching the AllPropertyTypesAsGrandChildrenChildren
	 * class type with all values set
	 * 
	 * @return JSON object string
	 */
	private String getArrayAllPropertyTypesAsGrandChildrenPayloadJSON()
	{
		String childComplexObject1 = getAllPropertyTypesAsChildrenPayloadObject("GC1_CC", "01", "1");
		String childComplexArrayObject1_1 = getAllPropertyTypesAsChildrenPayloadObject("GC1_CCA1", "02", "2");
		String childComplexArrayObject1_2 = getAllPropertyTypesAsChildrenPayloadObject("GC1_CCA2", "03", "3");

		String childComplexObject2 = getAllPropertyTypesAsChildrenPayloadObject("GC2_CC", "04", "4");
		String childComplexArrayObject2_1 = getAllPropertyTypesAsChildrenPayloadObject("GC2_CCA1", "05", "5");
		String childComplexArrayObject2_2 = getAllPropertyTypesAsChildrenPayloadObject("GC2_CCA2", "06", "6");

		return "JSON.stringify([\n" //
				+ "{\n" //
				+ " childComplex : " + childComplexObject1 + ",\n" //
				+ " childComplexArray : [\n" //
				+ childComplexArrayObject1_1 + ",\n" //
				+ childComplexArrayObject1_2 + "]\n" //
				+ "},\n" //
				+ "{\n" //
				+ " childComplex : " + childComplexObject2 + ",\n" //
				+ " childComplexArray : [\n" //
				+ childComplexArrayObject2_1 + ",\n" //
				+ childComplexArrayObject2_2 + "]\n" //
				+ "}\n" //
				+ "]);"; //
	}

	/**
	 * Returns a script fragment that defines an object matching the AllPropertyTypesAsGrandChildrenChildren class type
	 * with all values set
	 * 
	 * @return JSON object string
	 */
	private String getAllPropertyTypesAsGrandChildrenPayloadJSON()
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
	 * @return object string
	 */
	private String getAllPropertyTypesAsChildrenPayloadJSON(String stringPrefix, String dateDayNum, String numberPrefix)
	{
		return "JSON.stringify(" + getAllPropertyTypesAsChildrenPayloadObject(stringPrefix, dateDayNum, numberPrefix)
				+ ");";
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
	 * 
	 * @return script to define a fully populated object matching BOM type AllPropertyTypesAsChildren - each of which
	 *         has values prefixed by those given as parameters here.
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
				+ "  dateArrayAttribute : [new Date('1996-04-" + dateDatNum + "'), new Date('1998-03-" + dateDatNum
				+ "')],\n"//
				+ "  dateTimeArrayAttribute : [new Date('1996-04-" + dateDatNum + "T02:15:00.000Z'), new Date('1998-03-"
				+ dateDatNum + "T14:30:00.000Z')],\n"//
				+ "  integerArrayAttribute : [" + numberPrefix + "88, -" + numberPrefix + "99],\n"//
				+ "  numberDoubleArrayAttribute : [" + numberPrefix + "22.21, -" + numberPrefix + "33.56],\n"//
				+ "  numberFloatArrayAttribute : [" + numberPrefix + "55.4321, -" + numberPrefix + "66.5678],\n"//
				+ "  stringArrayAttribute : ['" + stringPrefix + " Item 1', '" + stringPrefix + " Item 2']" //
				+ (includeHeadParam ? ",\n  headerParamAttribute : 'aHeaderParam',\n" : "\n") //
				+ "}"; //
		return setupChildComplex;
	}

	/**
	 * Returns expected process data object with all children assigned matching BOM type
	 * MatchesSwaggerAllPropertyTypesAsChildren. Predefined values are prefixed with the various prefix parameter values
	 * depending on data type.
	 * 
	 * Because this type's attribute names don't match the equivalent complex tyype in test swagger service, it cannot
	 * be used for complex type mapping.
	 * 
	 * @param stringPrefix
	 * @param dateDatNum
	 * @param numberPrefix
	 * @param includeHeadParam
	 * 
	 * @return script to define a fully populated object matching BOM type AllPropertyTypesAsChildren - each of which
	 *         has values prefixed by those given as parameters here.
	 */
	private String getExpectedMatchingNamesChildComplexObject(String stringPrefix, String dateDatNum,
			String numberPrefix, boolean includeHeadParam)
	{
		String setupChildComplex = "{\n" //
				+ "  bool : false,\n"//
				+ "  date : new Date('2024-10-" + dateDatNum + "'),\n"//
				+ "  dateTime : new Date('2024-10-" + dateDatNum + "T12:34:56.000Z'),\n"//
				+ "  integer : " + numberPrefix + "77,\n"//
				+ "  numberDouble : " + numberPrefix + "11.67,\n"//
				+ "  numberFloat : " + numberPrefix + "44.6789,\n"//
				+ "  string : '" + stringPrefix + " string',\n"//
				+ "  boolArray : [true, false, true],\n"//
				+ "  dateArray : [new Date('1996-04-" + dateDatNum + "'), new Date('1998-03-" + dateDatNum + "')],\n"//
				+ "  dateTimeArray : [new Date('1996-04-" + dateDatNum + "T02:15:00.000Z'), new Date('1998-03-"
				+ dateDatNum + "T14:30:00.000Z')],\n"//
				+ "  integerArray : [" + numberPrefix + "88, -" + numberPrefix + "99],\n"//
				+ "  numberDoubleArray : [" + numberPrefix + "22.21, -" + numberPrefix + "33.56],\n"//
				+ "  numberFloatArray : [" + numberPrefix + "55.4321, -" + numberPrefix + "66.5678],\n"//
				+ "  stringArray : ['" + stringPrefix + " Item 1', '" + stringPrefix + " Item 2']" //
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
