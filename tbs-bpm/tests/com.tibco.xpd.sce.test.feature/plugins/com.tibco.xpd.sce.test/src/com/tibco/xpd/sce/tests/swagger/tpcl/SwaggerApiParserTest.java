/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swagger.tpcl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import junit.framework.TestCase;

/**
 * This test just checks the stability of the Swagger/OpenAPI parsing performed by the Apaches swagger-parser libraries
 * wrapped in com.tibco.xpd.tpcl.org.apache.swagger
 * 
 * @author aallway
 * @since 20 Jun 2024
 */
@SuppressWarnings("nls")
public class SwaggerApiParserTest extends TestCase
{

	/**
	 * Test load OpenAPI model from JSON content as String
	 * 
	 * @throws Exception
	 */
	public void testOpenAPI30JsonFromString() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/pet-store-openapi-30.json";

		SwaggerParseResult swaggerResult = getSwaggerResultFromStringContent(testFilePath);

		checkPetStoreOpenAPI30Content(swaggerResult);

	}

	/**
	 * Test load OpenAPI model from JSON content as String
	 * 
	 * @throws Exception
	 */
	public void testOpenAPI30JsonFromFile() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/pet-store-openapi-30.json";

		SwaggerParseResult swaggerResult = getSwaggerResultFromFile(testFilePath);

		checkPetStoreOpenAPI30Content(swaggerResult);
	}

	/**
	 * Test load OpenAPI model from YAML content as String
	 * 
	 * @throws Exception
	 */
	public void testOpenAPI30YamlFromString() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/pet-store-openapi-30.yaml";

		SwaggerParseResult swaggerResult = getSwaggerResultFromStringContent(testFilePath);

		checkPetStoreOpenAPI30Content(swaggerResult);

	}

	/**
	 * Test load OpenAPI model from YAML content as String
	 * 
	 * @throws Exception
	 */
	public void testOpenAPI30YamlFromFile() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/pet-store-openapi-30.yaml";

		SwaggerParseResult swaggerResult = getSwaggerResultFromFile(testFilePath);

		checkPetStoreOpenAPI30Content(swaggerResult);
	}

	/**
	 * Check the content from the "resources/SwaggerApiTest/pet-store-openapi-30.json" document has parsed correctly.
	 * 
	 * @param swaggerResult
	 */
	private void checkPetStoreOpenAPI30Content(SwaggerParseResult swaggerResult)
	{
		OpenAPI openAPI = swaggerResult.getOpenAPI();
		assertNotNull(openAPI);

		/* Just check enough to be confident that the doc parsed ok. */
		Paths paths = openAPI.getPaths();
		assertNotNull(paths);
		assertEquals(2, paths.size());
		assertNotNull(paths.get("/pets"));
		assertNotNull(paths.get("/pets/{id}"));

		PathItem pathItem = paths.get("/pets/{id}");
		Operation get = pathItem.getGet();
		assertNotNull(get);
		assertEquals("find pet by id", get.getOperationId());

		Components components = openAPI.getComponents();
		assertNotNull(components);

		Map<String, Schema> schemas = components.getSchemas();
		assertNotNull(schemas);
		assertEquals(6, schemas.size());
		assertNotNull(schemas.get("Pet"));
		assertNotNull(schemas.get("User"));
		assertNotNull(schemas.get("NewPet"));
		assertNotNull(schemas.get("OfficeAddress"));
		assertNotNull(schemas.get("HomeAddress"));
		assertNotNull(schemas.get("Error"));
	}

	/**
	 * Test load OpenAPI model from JSON content as String
	 * 
	 * @throws Exception
	 */
	public void testSwagger20JsonFromFile() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/manufacturing-swagger-20.json";

		SwaggerParseResult swaggerResult = getSwaggerResultFromFile(testFilePath);

		checkManufacturingSwagger20Content(swaggerResult);
	}

	/**
	 * Test load OpenAPI model from JSON content as String
	 * 
	 * @throws Exception
	 */
	public void testSwagger20JsonFromString() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/manufacturing-swagger-20.json";

		SwaggerParseResult swaggerResult = getSwaggerResultFromStringContent(testFilePath);

		checkManufacturingSwagger20Content(swaggerResult);
	}

	/**
	 * Test load OpenAPI model from YAML content as String
	 * 
	 * @throws Exception
	 */
	public void testSwagger20YamlFromFile() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/manufacturing-swagger-20.json";

		SwaggerParseResult swaggerResult = getSwaggerResultFromFile(testFilePath);

		checkManufacturingSwagger20Content(swaggerResult);
	}

	/**
	 * Test load OpenAPI model from YAML content as String
	 * 
	 * @throws Exception
	 */
	public void testSwagger20YamlFromString() throws Exception
	{
		String testFilePath = "resources/SwaggerApiTest/manufacturing-swagger-20.yaml";

		SwaggerParseResult swaggerResult = getSwaggerResultFromStringContent(testFilePath);

		checkManufacturingSwagger20Content(swaggerResult);
	}

	/**
	 * Check the content from the "resources/SwaggerApiTest/manufacturing-swagger-20.json" document has parsed
	 * correctly.
	 * 
	 * @param swaggerResult
	 */
	private void checkManufacturingSwagger20Content(SwaggerParseResult swaggerResult)
	{
		OpenAPI openAPI = swaggerResult.getOpenAPI();
		assertNotNull(openAPI);

		/* Just check enough to be confident that the doc parsed ok. */
		Paths paths = openAPI.getPaths();
		assertNotNull(paths);
		assertEquals(1, paths.size());
		assertNotNull(paths.get("/manufacturingSlot"));

		PathItem pathItem = paths.get("/manufacturingSlot");
		Operation post = pathItem.getPost();
		assertNotNull(post);
		assertEquals("createEntity", post.getOperationId());

		Components components = openAPI.getComponents();
		assertNotNull(components);

		Map<String, Schema> schemas = components.getSchemas();
		assertNotNull(schemas);
		assertEquals(2, schemas.size());
		assertNotNull(schemas.get("ServiceEntity"));
		assertNotNull(schemas.get("ErrorResponse"));
	}

	/**
	 * Get the SwaggerResult object by loading swagger from the given file in the test plugin.
	 * 
	 * asserts on failure
	 * 
	 * @param testFilePath
	 * 
	 * @return The SwaggerResult
	 * 
	 * @throws Exception
	 */
	private SwaggerParseResult getSwaggerResultFromFile(String testFilePath) throws MalformedURLException, IOException
	{
		OpenAPIParser openAPIParser = new OpenAPIParser();

		SwaggerParseResult swaggerResult = openAPIParser.readLocation(getTestFileOSPath(testFilePath), null, null);

		assertNotNull(swaggerResult);

		return swaggerResult;
	}

	/**
	 * Get the SwaggerResult object by loading swagger from the String contents extracted from the given file in the
	 * test plugin.
	 * 
	 * @param testFilePath
	 * 
	 * @return The SwaggerResult
	 * 
	 * @throws Exception
	 */
	private SwaggerParseResult getSwaggerResultFromStringContent(String testFilePath) throws Exception
	{
		OpenAPIParser openAPIParser = new OpenAPIParser();

		String fileContents = getTestFileContents(testFilePath);

		ParseOptions options = new ParseOptions();
		options.setInferSchemaType(true);

		SwaggerParseResult swaggerResult = openAPIParser.readContents(fileContents, null, options);

		assertNotNull(swaggerResult);

		return swaggerResult;
	}

	/**
	 * Load a test file's content as String from given test plugin relative path.
	 * 
	 * @param testPluginRelativePath
	 * 
	 * @return String file contents.
	 * 
	 * @throws Exception
	 */
	private String getTestFileContents(String testPluginRelativePath) throws Exception
	{
		Bundle bundle = Platform.getBundle(getTestPlugInId());

		InputStream stream = FileLocator.openStream(bundle, new Path(testPluginRelativePath), false);

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null)
		{
			sb.append(line).append("\n");
		}

		stream.close();

		return sb.toString();
	}

	/**
	 * The absolute file path of the file within the test plugin.
	 * 
	 * @param testPluginRelativePath
	 * 
	 * @return The absolute file path of the file within the bundle.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private String getTestFileOSPath(String testPluginRelativePath) throws MalformedURLException, IOException
	{
		Bundle bundle = Platform.getBundle(getTestPlugInId());

		String pathURL = FileLocator.toFileURL(bundle.getResource(testPluginRelativePath)).getPath();

		return new File(pathURL).getAbsolutePath();
	}

	private String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
	}
}
