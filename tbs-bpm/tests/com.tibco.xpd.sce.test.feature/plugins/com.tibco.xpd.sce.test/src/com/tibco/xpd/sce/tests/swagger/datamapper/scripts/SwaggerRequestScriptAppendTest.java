/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.datamapper.scripts;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.pe.rest.datamapper.SwaggerScriptGeneratorInfoProvider;

/**
 * Tests for {@link SwaggerScriptGeneratorInfoProvider} in its handling of the appended script of the Swagger INPUT
 * mapping script (for REST_REQUEST data initialisation etc)
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerRequestScriptAppendTest extends SwaggerScriptGeneratorExecutionTest
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
	 * Check outcome of REQUEST prepend script if there are no params/payload inputs at all
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestAppendScript_NoInputParamsOrPayload() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptAppendTests_Process", "noInputData - PUT /noInputData",
				MappingDirection.IN);

		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};"));
		assertNotNull(scriptContext);

		/*
		 * The URL should be a concatenation of the basePath and the resource path
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/v2/noInputData");

		/* Method should have been set to that in the operation definition */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "GET");

		/* Payload Data should not have been set. */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);

	}

	
	/**
	 * Check outcome of REQUEST apppend script if all input param types (path/query/header) & payload is defined for
	 * service.
	 * 
	 * Some params have a name that is not valid for JS variable (i.e. contains "-" chars). This is to ensure correct
	 * handling of the REST_xxx JS variables that are used to represent these values.
	 * 
	 * The Swagger basePath and resource path both have characters that require URI encoding (space and %) to prove that
	 * the REST_REQUEST URL is set with basePath, resourcePath, path-param and query param content URI encoded.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestApppendScript_AllInputParamTypes_AndRequiringUriEncode() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptAppendTests_Process", "inputWithAllParamTypes", MappingDirection.IN);

		/*
		 * Setup path, param, query and body mapping source data with chars that require URI encoding
		 * 
		 * With various locations for encodable characters
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField01 = '%Value for X Path param';\n" // Path X-Path-With-Bad-JS-Varname-Chars
				+ "data.TextField02 = 'Value for/param%1';\n" // Path param1
				+ "data.TextField03 = 'Value%for%param 2';\n" // Path param2
				+ "data.TextField04 = 'Value for X%Query/param';\n" // Query X-Query-With-Bad-JS-Varname-Chars
				+ "data.TextField05 = '%Value for nonProblematic query%param';\n" // Query nonProblematicQueryName
				+ "data.TextField06 = 'Value for X%Header%param';\n" // Header X-Header-With-Bad-JS-Varname-Chars
				+ "data.TextField07 = 'Value for nonProblematic header/%/param';\n" // Header nonProblematicHeaderName
		));

		/*
		 * Check the header parameter values should NOT have been URL encoded.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.X-Header-With-Bad-JS-Varname-Chars",
				"Value for X%Header%param");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.nonProblematicHeaderName",
				"Value for nonProblematic header/%/param");

		/**
		 * Check the URL has been set and encoded correctly.
		 * 
		 * Unencoded URL components are the field values above.
		 * 
		 * <pre>
		 * basePath:      "/basePath %With% Spaces"
		 * respourcePath: "/resource path/with spaces and multiple params {param1}/{param2}/{X-Path-With-Bad-JS-Varname-Chars}"
		 * </pre>
		 * 
		 * So final URL should be as follows given the values above and including the query parameters (manually created
		 * usingJS encodeURI() and encodeURIComponent())...
		 */
		String expectedEncodedUrl = "/basePath%20%25With%25%20Spaces/resource%20path/with%20spaces%20and%20multiple%20params%20Value%20for%2Fparam%251/Value%25for%25param%202/%25Value%20for%20X%20Path%20param?X-Query-With-Bad-JS-Varname-Chars=Value%20for%20X%25Query%2Fparam&nonProblematicQueryName=%25Value%20for%20nonProblematic%20query%25param";
		assertVariableValue(scriptContext, "REST_REQUEST.url", expectedEncodedUrl);

	}

	/**
	 * Check outcome of REQUEST append script if all input param types (path/query/header) & payload is defined for
	 * service.
	 * 
	 * Same as {@link #testRequestApppendScript_AllInputParamTypes_AndRequiringUriEncode()} EXCEPT this time, we do not
	 * provide a value for one of the Query Parameters, ,'X-Query-With-Bad-JS-Varname-Chars' which is optional.
	 * 
	 * In this situation the queryparam=value should be excluded from the URL.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestApppendScript_AllInputParamTypes_AndRequiringUriEncode_NullOptionalQueryParam()
			throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptAppendTests_Process", "inputWithAllParamTypes", MappingDirection.IN);

		/*
		 * Setup path, param, query and body mapping source data with chars that require URI encoding
		 * 
		 * With various locations for encodable characters
		 */
		ScriptContext scriptContext = executor.executeScript(mappingScript, getRestRequestInitScript("var data = {};\n" //
				+ "data.TextField01 = '%Value for X Path param';\n" // Path X-Path-With-Bad-JS-Varname-Chars
				+ "data.TextField02 = 'Value for/param%1';\n" // Path param1
				+ "data.TextField03 = 'Value%for%param 2';\n" // Path param2
				+ "data.TextField04 = null;\n" // UNSET Query X-Query-With-Bad-JS-Varname-Chars
				+ "data.TextField05 = '%Value for nonProblematic query%param';\n" // Query nonProblematicQueryName
				+ "data.TextField06 = 'Value for X%Header%param';\n" // Header X-Header-With-Bad-JS-Varname-Chars
				+ "data.TextField07 = 'Value for nonProblematic header/%/param';\n" // Header nonProblematicHeaderName
		));

		/*
		 * Check the header parameter values should NOT have been URL encoded.
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.headers.X-Header-With-Bad-JS-Varname-Chars",
				"Value for X%Header%param");
		assertVariableValue(scriptContext, "REST_REQUEST.headers.nonProblematicHeaderName",
				"Value for nonProblematic header/%/param");

		/**
		 * Check the URL has been set and encoded correctly.
		 * 
		 * Unencoded URL components are the field values above.
		 * 
		 * <pre>
		 * basePath:      "/basePath %With% Spaces"
		 * respourcePath: "/resource path/with spaces and multiple params {param1}/{param2}/{X-Path-With-Bad-JS-Varname-Chars}"
		 * </pre>
		 * 
		 * So final URL should be as follows given the values above and including the query parameters (manually created
		 * usingJS encodeURI() and encodeURIComponent())...
		 */
		String expectedEncodedUrl = "/basePath%20%25With%25%20Spaces/resource%20path/with%20spaces%20and%20multiple%20params%20Value%20for%2Fparam%251/Value%25for%25param%202/%25Value%20for%20X%20Path%20param?nonProblematicQueryName=%25Value%20for%20nonProblematic%20query%25param";
		assertVariableValue(scriptContext, "REST_REQUEST.url", expectedEncodedUrl);

	}

	/**
	 * Check outcome of REQUEST prepend script if there is no basePath / server URL specification.
	 * 
	 * Originally there was a defect in script generation that always tried to create a full URL from the basePath /
	 * server URL - which is optional in Swagger/OAS and can also be partial (i.e. not host definition). This caused
	 * script gen to throw exception and no BPEL was produced.
	 * 
	 * So this test ensures that we don't fail if there is no server spec.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestAppendScript_NoBasePathOrServers() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptAppendTests_Process", "Service with not base URL / host defined",
				MappingDirection.IN);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = { };\n" + "data.IntegerField01 = 1234;"));
		assertNotNull(scriptContext);

		/*
		 * The URL should be a concatenation of the basePath and the resource path
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/serviceWithoutServerSpec/1234");

		/* Method should have been set to that in the operation definition */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "POST");

		/* Payload Data should not have been set. */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);

	}

	/**
	 * Check outcome of REQUEST prepend script if there is a basePath but no server host/protocol specification.
	 * 
	 * Originally there was a defect in script generation that always tried to create a full URL from the basePath /
	 * server URL - which is optional in Swagger/OAS and can also be partial (i.e. not host definition). This caused
	 * script gen to throw exception and no BPEL was produced.
	 * 
	 * So this test ensures that we don't fail if there is no server spec.
	 * 
	 * @throws ScriptException
	 * 
	 * @throws Exception
	 */
	public void testRequestAppendScript_NoHostSpecification() throws ScriptException
	{
		String mappingScript = generateRestMappingScript(
				"/SwaggerScriptGenTests_Process/Process Packages/SwaggerScriptGenTests1.xpdl",
				"SwaggerRequestScriptAppendTests_Process", "Service with no host defined",
				MappingDirection.IN);

		ScriptContext scriptContext = executor.executeScript(mappingScript,
				getRestRequestInitScript("var data = { };\n" + "data.IntegerField01 = 1234;"));
		assertNotNull(scriptContext);

		/*
		 * The URL should be a concatenation of the basePath and the resource path
		 */
		assertVariableValue(scriptContext, "REST_REQUEST.url", "/basePath/serviceWithoutServerHostInPath/1234");

		/* Method should have been set to that in the operation definition */
		assertVariableValue(scriptContext, "REST_REQUEST.method", "POST");

		/* Payload Data should not have been set. */
		assertVariableValue(scriptContext, "REST_REQUEST.data", null);

	}
}
