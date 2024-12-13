/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of the prepended script of the Swagger OUTPUT
 * mapping script (for variable initialisation etc)
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerResponseScriptPrependTest extends SwaggerScriptGeneratorExecutionTest
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
	 * Check outcome of RESPONSE prepend script if there is no payload at all.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_NullResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestResponseInitScript(299, null, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

		assertVariableValue(scriptContext, "REST_PAYLOAD_200", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", null);
	}

	/**
	 * Check outcome of RESPONSE prepend script if payload is not valid JSON data.
	 * 
	 * Should throw an exception.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_InvalidJsonResponse()
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		try
		{
			executor.executeScript(mappingScript, getRestResponseInitScript(200, "REST_RESPONSE.data = '{ badjson '", "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

			fail("Prepend script should have thrown a script exception.");

		}
		catch (ScriptException e)
		{
		}
	}

	/**
	 * Check outcome of RESPONSE prepend script if payload is a string.
	 * 
	 * Should set up REST_PAYLOAD_200 as a string
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_StringResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('any old string')", "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

		assertVariableValue(scriptContext, "REST_PAYLOAD_200", "any old string");
	}

	/**
	 * Check outcome of RESPONSE prepend script if payload is an integer.
	 * 
	 * Should set up REST_PAYLOAD_200 as a JS int
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_IntResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify(123);\n", "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

		assertVariableValue(scriptContext, "REST_PAYLOAD_200", Double.valueOf(123));
	}

	/**
	 * Check outcome of RESPONSE prepend script if payload is valid JSON data.
	 * 
	 * Should set up REST_PAYLOAD_200 as JSON object
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_JsonResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = '{ \"propertyXXX\" : \"valueXXX\" }'", "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

		Bindings restPayload200 = assertPayloadValueObject(scriptContext, "REST_PAYLOAD_200");

		assertEquals("valueXXX", restPayload200.get("propertyXXX"));
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where the response-code
	 * is that which is expected)...
	 * 
	 * that correct response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) are setup.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_ExpectedSingleResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n" //
						+ "REST_RESPONSE['headers']['X-Rate-Limit'] = 'X-Rate-Limit-Value';\n"//
						+ "REST_RESPONSE['headers']['X-Expires-After'] = 'X-Expires-After-Value';\n"//
, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}
						));

		assertVariableValue(scriptContext, "REST_PAYLOAD_200", "PAYLOAD-DATA");
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", "X-Rate-Limit-Value");
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", "X-Expires-After-Value");
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where the response-code
	 * is NOT that which is expected)...
	 * 
	 * that status code 200 response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) should all be null
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_UnexpectedSingleResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(299, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n", "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}));

		assertVariableValue(scriptContext, "REST_PAYLOAD_200", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", null);
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where there is ONLY a
	 * default response in service)...
	 * 
	 * that correct response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) are setup.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_OnlyDefaultSingleResponse() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "singleSuccessDefaultResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n" //
						+ "REST_RESPONSE['headers']['DefaultHdr_201_1'] = 'Default-Value';\n"//
, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}
				));

		assertVariableValue(scriptContext, "REST_PAYLOAD_default", "PAYLOAD-DATA");
		assertVariableValue(scriptContext, "REST_HEADER_default_DefaultHdr_201_1", "Default-Value");
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where there are MULTIPLE
	 * success (200/234) AND a default response defined in service). Test when service returns 200, then only variable
	 * for 200 reponse are assigned...
	 * 
	 * that correct response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) are setup.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_MultiResponse_200() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "multipleSuccessAndDefaultResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(200, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n" //
						+ "REST_RESPONSE['headers']['X-Rate-Limit'] = 'X-Rate-Limit-Value';\n"//
						+ "REST_RESPONSE['headers']['X-Expires-After'] = 'X-Expires-After-Value';\n"//
, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}
				));

		//
		// Should get the <response>_200 vars setup
		//
		assertVariableValue(scriptContext, "REST_PAYLOAD_200", "PAYLOAD-DATA");
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", "X-Rate-Limit-Value");
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", "X-Expires-After-Value");

		//
		// Should get the <response>_234 vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_234_Hdr_234_1", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_234", null);

		//
		// Should get the <response>_default vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_default_DefaultHdr_Default_1", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_default", null);
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where there are MULTIPLE
	 * success (200/234) AND a default response defined in service). Test when service returns 234, then only variable
	 * for 200 reponse are assigned...
	 * 
	 * that correct response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) are setup.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_MultiResponse_234() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "multipleSuccessAndDefaultResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(234, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n" //
						+ "REST_RESPONSE['headers']['Hdr_234_1'] = 'Hdr_234_1-Value';\n"//
, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}
				));

		//
		// Should get the <response>_200 vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_200", null);

		//
		// Should get the <response>_234 vars setup
		//
		assertVariableValue(scriptContext, "REST_HEADER_234_Hdr_234_1", "Hdr_234_1-Value");
		assertVariableValue(scriptContext, "REST_PAYLOAD_234", "PAYLOAD-DATA");

		//
		// Should get the <response>_default vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_default_DefaultHdr_Default_1", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_default", null);
	}

	/**
	 * Check outcome of RESPONSE prepend script for service with single success response test (where there are MULTIPLE
	 * success (200/234) AND a default response defined in service). Test when service returns default (anything other
	 * than 200 or 234, then only variable for 200 reponse are assigned...
	 * 
	 * that correct response variables (REST_HEADER_nnn, REST_PAYLOAD_nnn) are setup.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testResponsePrependScript_MultiResponse_default() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerResponseScriptPrependTests_Process", "multipleSuccessAndDefaultResponse - No Mappings",
				MappingDirection.OUT);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestResponseInitScript(299, "REST_RESPONSE.data = JSON.stringify('PAYLOAD-DATA');\n" //
						+ "REST_RESPONSE['headers']['DefaultHdr_Default_1'] = 'DefaultHdr_Default_1-Value';\n"//
, "com_example_SwaggerScriptGenTests_data", new String[] {"AllDataTypes", "AllDataArrayTypes"}
				));

		//
		// Should get the <response>_200 vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Rate_Limit", null);
		assertVariableValue(scriptContext, "REST_HEADER_200_X_Expires_After", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_200", null);

		//
		// Should get the <response>_234 vars set to null
		//
		assertVariableValue(scriptContext, "REST_HEADER_234_Hdr_234_1", null);
		assertVariableValue(scriptContext, "REST_PAYLOAD_234", null);

		//
		// Should get the <response>_default vars setup
		//
		assertVariableValue(scriptContext, "REST_HEADER_default_DefaultHdr_Default_1", "DefaultHdr_Default_1-Value");
		assertVariableValue(scriptContext, "REST_PAYLOAD_default", "PAYLOAD-DATA");
	}

}
