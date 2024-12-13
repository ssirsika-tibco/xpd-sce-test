/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.swagger.tpcl;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.swagger.SwaggerObjectImpl;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import junit.framework.TestCase;

/**
 * ACE-8243: JUnits for SwaggerWorkingCopy factory. Tests that the working copy for a Swagger file (json/yaml file) has
 * loaded and returns the Open API model correctly.
 *
 *
 * @author nkelkar
 * @since Jul 10, 2024
 */
public class SwaggerWorkingCopyTest extends TestCase
{

	private ProjectImporter projectImporter;

	/**
	 * 
	 * @see junit.framework.TestCase#setUp()
	 *
	 */
	@Override
	public void setUp()
	{
		projectImporter = TestUtil.importProjectsFromZip(getTestPlugInId(),
				new String[]{"resources/SwaggerWorkingCopyTest/ValidSwaggerWorkingCopyTest/",
						"resources/SwaggerWorkingCopyTest/InvalidSwaggerWorkingCopyTest/"},
				new String[]{"ValidSwaggerWorkingCopyTest", "InvalidSwaggerWorkingCopyTest"});

		assertTrue("Failed to load projects from \"resources/SwaggerWorkingCopyTest/",
				projectImporter != null);
	}

	/**
	 * ValidSwaggerWorkingCopyTest
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetValidSwaggerModelTest() throws Exception
	{

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("ValidSwaggerWorkingCopyTest"); //$NON-NLS-1$

		checkManufacturingSwagger20Content(getSwaggerModel(project, "REST Services/manufacturing-swagger-20.json"));
		checkManufacturingSwagger20Content(getSwaggerModel(project, "REST Services/manufacturing-swagger-20.yaml"));
		checkPetStoreOpenAPI30Content(getSwaggerModel(project, "REST Services/pet-store-openapi-30.json"));
		checkPetStoreOpenAPI30Content(getSwaggerModel(project, "REST Services/pet-store-openapi-30.yaml"));
		/*
		 * This project should have no problems.
		 */
		assertFalse("ValidSwaggerWorkingCopyTest project should not have any ERROR level problem markers",
				TestUtil.hasErrorProblemMarker(
						ResourcesPlugin.getWorkspace().getRoot().getProject(
								"ValidSwaggerWorkingCopyTest"),
						true,
						"ValidSwaggerWorkingCopyTest"));

		return;
	}

	/**
	 * InvalidSwaggerWorkingCopyTest
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetInvalidSwaggerModelTest() throws Exception
	{

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("InvalidSwaggerWorkingCopyTest");

		/*
		 * This project should have the following error markers
		 */
		verifyInvalidFileMarker(project, "REST Services/emptyFile.yaml",
				"Problems while loading the file.(Detail: attribute  is not of type `object`).");

		verifyInvalidFileMarker(project, "REST Services/petstore.yaml",
				"Problems while loading the file.(Detail: attribute paths is missing).");

		verifyInvalidFileMarker(project, "REST Services/emptySwaggerObj.json",
				"Problems while loading the file.(Detail: attribute swagger is missing, attribute info is missing, attribute paths is missing).");

		verifyInvalidFileMarker(project, "REST Services/SwaggerPetStore.json",
				"Problems while loading the file.(Detail: attribute swagger is missing, attribute info is missing).");

		return;
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
	 *
	 * @throws Exception
	 */
	@Override
	protected void tearDown() throws Exception
	{
		if (projectImporter != null)
		{
			projectImporter.performDelete();
		}
		super.tearDown();
	}

	protected String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
	}

	/**
	 * Returns the model from a valid Swagger working copy
	 * 
	 * @param project
	 * @param fileName
	 * @return
	 */
	private OpenAPI getSwaggerModel(IProject project, String fileName)
	{
		IFile swaggerFile = project.getFile(fileName);
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(swaggerFile);

		SwaggerObjectImpl swaggerObj = (SwaggerObjectImpl) testWC.getRootElement();

		return swaggerObj.getModel();
	}
	
	/**
	 * Verifies that a Swagger file is invalid; Checks the specific error message and that the model returned by the
	 * Working Copy is null
	 * 
	 * @param project
	 * @param fileName
	 * @param errorMsg
	 */
	@SuppressWarnings("nls")
	private void verifyInvalidFileMarker(IProject project, String fileName, String errorMsg)
	{
		IFile swaggerFile = project.getFile(fileName);
		WorkingCopy testWC = WorkingCopyUtil.getWorkingCopy(swaggerFile);
		assertNull(testWC.getRootElement()); // Root element is null because file has error

		if (TestUtil.hasErrorProblemMarker(swaggerFile, true, "InvalidSwaggerWorkingCopyTest"))
		{
			IMarker invalidFileMarker = TestUtil.getErrorMarkers(swaggerFile).get(0);
			try
			{
				assertEquals(invalidFileMarker.getType(), "com.tibco.xpd.resources.invalidFileProblem");
				assertEquals(invalidFileMarker.getAttribute("message"), errorMsg);
			}
			catch (CoreException e)
			{
				e.printStackTrace();
			}
		}

	}
	/**
	 * Check the Open API 2.0 model has parsed correctly.
	 * 
	 * @param openAPI
	 *            model
	 */
	private void checkManufacturingSwagger20Content(OpenAPI openAPI)
	{
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
	 * Check the Open API 3.0 model has parsed correctly.
	 * 
	 * @param openAPI
	 *            model
	 */
	private void checkPetStoreOpenAPI30Content(OpenAPI openAPI)
	{
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
}
