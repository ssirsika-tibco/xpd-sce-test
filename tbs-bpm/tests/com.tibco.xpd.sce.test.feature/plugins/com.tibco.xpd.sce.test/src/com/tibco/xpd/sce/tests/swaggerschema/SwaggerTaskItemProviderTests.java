/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.swaggerschema;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerInputParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerInputParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadRootTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.properties.SwaggerTaskItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Sid ACE-8885: JUnits for {@link SwaggerTaskItemProvider} to ensure that Swagger schema's are interpreted correctly
 *
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerTaskItemProviderTests extends TestCase
{

	private ProjectImporter		projectImporter;

	/**
	 * Pass as expectedParent param to
	 * {@link #assertSwaggerTreeItem(SwaggerTaskItemProvider, Object, Class, String, String, String, SwaggerPropertyType, boolean, SwaggerMapperTreeItem, Activity, boolean, String, String)}
	 * to skip the parent check
	 */
	private static final Object	SKIP_PARENT_CHECK	= new Object();

	/**
	 * 
	 * @see junit.framework.TestCase#setUp()
	 *
	 */
	@Override
	@Before
	public void setUp()
	{
		projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/SwaggerTaskItemProviderTests/SwaggerTaskItemProviderTests_Services/",
						"resources/SwaggerTaskItemProviderTests/SwaggerTaskItemProviderTests_Processes/"},
				new String[]{"SwaggerTaskItemProviderTests_Services", "SwaggerTaskItemProviderTests_Processes"});

		assertTrue("Failed to load projects from \"resources/SwaggerWorkingCopyTest/", projectImporter != null);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 *
	 * @throws Exception
	 */
	@Override
	@After
	protected void tearDown() throws Exception
	{
		if (projectImporter != null)
		{
			projectImporter.performDelete();
			projectImporter = null;
		}
	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * A SET OF 3 TESTS TO CHECK THE BASIC TOP LEVEL TREE STRUCTURE FOR REQUEST / RESPONSE / ERROR RESPONSE
	 * 
	 * These focus on the top level structure of containership and param / payload items.
	 * 
	 * This then allows other tests to focus only on the correct {@link SwaggerTypedTreeItem} creation for all the
	 * different ways of declaring types in Swagger/OAS (without repeating everything for request/response/error
	 * separately - the base assumption is that if the top level structure is correct up to and including the top level
	 * param/payload item then if the param/payload item type is different, it won't matter if it is in the request,
	 * response or error content).
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * This is comprehensive test that the TOP LEVEL TREE STRUCTURE looks correct for a simple service REQUEST
	 * 
	 * See SwaggerSchemaTests.yaml -> /serviceToTestTreeContainers for example schema that is covered.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Request_serviceToTestTreeContainers() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "treeItem Containers Test");

		List<SwaggerMapperTreeItem> requestTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getTopLevelItemsFromTaskItemProvider(activity, MappingDirection.IN,
				requestTreeItems);

		/*
		 * There should be path parameters, query parameters, header parameters and payload.
		 */
		assertEquals(4, requestTreeItems.size());

		SwaggerMapperTreeItem pathParamsTreeItem = requestTreeItems.get(0);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				pathParamsTreeItem, // tree item to test
				SwaggerInputParamContainerTreeItem.class, // Expected treeItem class type
				"Path Parameters", // expected treeItem label
				"Path Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_PATH_pathParam", // to test findChild() - add item text (for payload this would be tree item's
										// path element name
				"pathParam : Text"); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem queryParamsTreeItem = requestTreeItems.get(1);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				queryParamsTreeItem, // tree item to test
				SwaggerInputParamContainerTreeItem.class, // Expected treeItem class type
				"Query Parameters", // expected treeItem label
				"Query Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_QUERY_queryParam", // to test findChild() - add item text (for payload this would be tree item's
											// path element name
				"queryParam : Text"); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem headerParamsTreeItem = requestTreeItems.get(2);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				headerParamsTreeItem, // tree item to test
				SwaggerInputParamContainerTreeItem.class, // Expected treeItem class type
				"Header Parameters", // expected treeItem label
				"Header Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_HEADER_headerParam", // to test findChild() - add item text (for payload this would be tree item's
											// path element name
				"headerParam : Text"); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem payloadTreeItem = requestTreeItems.get(3);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadContainerTreeItem.class, // Expected treeItem class type
				"Payload", // expected treeItem label
				"Payload", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_PAYLOAD", // to test findChild() - add item text (for payload this would be tree item's path
								// element name
				"Text"); // to test findChild() findChild() expected label for given child

		/*
		 * There should be correct content in the Path Parameters folder (single item)
		 */
		Object[] pathParamItems = taskItemProvider.getChildren(pathParamsTreeItem);
		assertNotNull(pathParamItems);
		assertEquals(1, pathParamItems.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				pathParamItems[0], // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"pathParam : Text", // expected treeItem label
				"pathParam", // expected treeItem text
				"REST_PATH_pathParam", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				pathParamsTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * There should be correct content in the Query Parameters folder (single item)
		 */
		Object[] queryParamItems = taskItemProvider.getChildren(queryParamsTreeItem);
		assertNotNull(queryParamItems);
		assertEquals(1, queryParamItems.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				queryParamItems[0], // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"queryParam : Text", // expected treeItem label
				"queryParam", // expected treeItem text
				"REST_QUERY_queryParam", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				queryParamsTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * There should be correct content in the Header Parameters folder (single item)
		 */
		Object[] headerParamItems = taskItemProvider.getChildren(headerParamsTreeItem);
		assertNotNull(headerParamItems);
		assertEquals(1, headerParamItems.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				headerParamItems[0], // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"headerParam : Text", // expected treeItem label
				"headerParam", // expected treeItem text
				"REST_HEADER_headerParam", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				headerParamsTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * There should be correct content in the Payload folder (single item)
		 */
		Object[] payloadItems = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(payloadItems);
		assertEquals(1, payloadItems.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadItems[0], // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * This is comprehensive test that the TOP LEVEL TREE STRUCTURE looks correct for a simple service SUCCESS RESPONSES
	 * 
	 * See SwaggerSchemaTests.yaml -> /serviceToTestTreeContainers for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Response_serviceToTestTreeContainers() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "treeItem Containers Test");

		List<SwaggerMapperTreeItem> responseTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getTopLevelItemsFromTaskItemProvider(activity, MappingDirection.OUT,
				responseTreeItems);

		/*
		 * There should be 2 separate responses (200 & 201)
		 */
		assertEquals(2, responseTreeItems.size());

		SwaggerMapperTreeItem response200TreeItem = responseTreeItems.get(0);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response200TreeItem, // tree item to test
				SwaggerResponseContainerTreeItem.class, // Expected treeItem class type
				"Response Code 200", // expected treeItem label
				"Response Code 200", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"Header Parameters", // to test findChild() - add item text (for payload this would be tree item's path
										// element name
				"Header Parameters"); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem response201TreeItem = responseTreeItems.get(1);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response201TreeItem, // tree item to test
				SwaggerResponseContainerTreeItem.class, // Expected treeItem class type
				"Response Code 201", // expected treeItem label
				"Response Code 201", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"Payload", // to test findChild() - add item text (for payload this would be tree item's path element
							// name
				"Payload"); // to test findChild() findChild() expected label for given child

		/**
		 * ===================================================================
		 * 
		 * Response 200 should have 2 folders Header Parameters and Payload
		 * 
		 * ===================================================================
		 */
		Object[] response200Children = taskItemProvider.getChildren(response200TreeItem);
		assertNotNull(response200Children);
		assertEquals(2, response200Children.length);

		/*
		 * Check response 200 header parameters folder and content....
		 */
		assertTrue(response200Children[0] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response200HeaderParams = (SwaggerMapperTreeItem) response200Children[0];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response200HeaderParams, // tree item to test
				SwaggerResponseParamContainerTreeItem.class, // Expected treeItem class type
				"Header Parameters", // expected treeItem label
				"Header Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response200TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_HEADER_200_headerParam200", // to test findChild() - add item text (for payload this would be tree
													// item's path element name
				"headerParam200 : Text"); // to test findChild() findChild() expected label for given child

		Object[] response200HeaderChildren = taskItemProvider.getChildren(response200HeaderParams);
		assertNotNull(response200HeaderChildren);
		assertEquals(1, response200HeaderChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response200HeaderChildren[0], // tree item to test
				SwaggerResponseParamTreeItem.class, // Expected treeItem class type
				"headerParam200 : Text", // expected treeItem label
				"headerParam200", // expected treeItem text
				"REST_HEADER_200_headerParam200", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				response200HeaderParams, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Check response 200 payload folder and content....
		 */
		assertTrue(response200Children[1] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response200PayloadContainer = (SwaggerMapperTreeItem) response200Children[1];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response200PayloadContainer, // tree item to test
				SwaggerPayloadContainerTreeItem.class, // Expected treeItem class type
				"Payload", // expected treeItem label
				"Payload", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response200TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_PAYLOAD_200", // to test findChild() - add item text (for payload this would be tree item's path
									// element name
				"Integer"); // to test findChild() findChild() expected label for given child

		Object[] response200PayloadChildren = taskItemProvider.getChildren(response200PayloadContainer);
		assertNotNull(response200PayloadChildren);
		assertEquals(1, response200PayloadChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response200PayloadChildren[0], // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Integer", // expected treeItem label
				"REST_PAYLOAD_200", // expected treeItem text
				"REST_PAYLOAD_200", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				response200PayloadContainer, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/**
		 * ===================================================================
		 * 
		 * Response 201 should have 2 folders Header Parameters and Payload
		 * 
		 * ===================================================================
		 */
		Object[] response201Children = taskItemProvider.getChildren(response201TreeItem);
		assertNotNull(response201Children);
		assertEquals(2, response201Children.length);

		/*
		 * Check response 201 header parameters folder and content....
		 */
		assertTrue(response201Children[0] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response201HeaderParams = (SwaggerMapperTreeItem) response201Children[0];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response201HeaderParams, // tree item to test
				SwaggerResponseParamContainerTreeItem.class, // Expected treeItem class type
				"Header Parameters", // expected treeItem label
				"Header Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response201TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_HEADER_201_headerParam201", // to test findChild() - add item text (for payload this would be tree
													// item's path element name
				"headerParam201 : Integer"); // to test findChild() findChild() expected label for given child

		Object[] response201HeaderChildren = taskItemProvider.getChildren(response201HeaderParams);
		assertNotNull(response201HeaderChildren);
		assertEquals(1, response201HeaderChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response201HeaderChildren[0], // tree item to test
				SwaggerResponseParamTreeItem.class, // Expected treeItem class type
				"headerParam201 : Integer", // expected treeItem label
				"headerParam201", // expected treeItem text
				"REST_HEADER_201_headerParam201", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				response201HeaderParams, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Check response 201 payload folder and content....
		 */
		assertTrue(response201Children[1] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response201PayloadContainer = (SwaggerMapperTreeItem) response201Children[1];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response201PayloadContainer, // tree item to test
				SwaggerPayloadContainerTreeItem.class, // Expected treeItem class type
				"Payload", // expected treeItem label
				"Payload", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response201TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_PAYLOAD_201", // to test findChild() - add item text (for payload this would be tree item's path
									// element name
				"Text"); // to test findChild() findChild() expected label for given child

		Object[] response201PayloadChildren = taskItemProvider.getChildren(response201PayloadContainer);
		assertNotNull(response201PayloadChildren);
		assertEquals(1, response201PayloadChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response201PayloadChildren[0], // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text", // expected treeItem label
				"REST_PAYLOAD_201", // expected treeItem text
				"REST_PAYLOAD_201", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				response201PayloadContainer, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

	}

	/**
	 * This is comprehensive test that the TOP LEVEL TREE STRUCTURE looks correct for a simple service ERROR RESPONSE
	 * 
	 * See SwaggerSchemaTests.yaml -> /serviceToTestTreeContainers for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_ErrorResponse_serviceToTestTreeContainers() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "treeItem Containers Test - Error Event");

		List<SwaggerMapperTreeItem> responseTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getTopLevelItemsFromTaskItemProvider(activity, MappingDirection.OUT,
				responseTreeItems);

		/*
		 * There should be one 400 response (just the error selected for the given error event).
		 */
		assertEquals(1, responseTreeItems.size());

		SwaggerMapperTreeItem response400TreeItem = responseTreeItems.get(0);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response400TreeItem, // tree item to test
				SwaggerResponseContainerTreeItem.class, // Expected treeItem class type
				"Response Code 400", // expected treeItem label
				"Response Code 400", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				null, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"Header Parameters", // to test findChild() - add item text (for payload this would be tree item's path
										// element name
				"Header Parameters"); // to test findChild() findChild() expected label for given child

		/**
		 * ===================================================================
		 * 
		 * Response 400 should have 2 folders Header Parameters and Payload
		 * 
		 * ===================================================================
		 */
		Object[] response400Children = taskItemProvider.getChildren(response400TreeItem);
		assertNotNull(response400Children);
		assertEquals(2, response400Children.length);

		/*
		 * Check response 400 header parameters folder and content....
		 */
		assertTrue(response400Children[0] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response400HeaderParams = (SwaggerMapperTreeItem) response400Children[0];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response400HeaderParams, // tree item to test
				SwaggerResponseParamContainerTreeItem.class, // Expected treeItem class type
				"Header Parameters", // expected treeItem label
				"Header Parameters", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response400TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_HEADER_400_headerParam400", // to test findChild() - add item text (for payload this would be tree
													// item's path element name
				"headerParam400 : Number"); // to test findChild() findChild() expected label for given child

		Object[] response400HeaderChildren = taskItemProvider.getChildren(response400HeaderParams);
		assertNotNull(response400HeaderChildren);
		assertEquals(1, response400HeaderChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response400HeaderChildren[0], // tree item to test
				SwaggerResponseParamTreeItem.class, // Expected treeItem class type
				"headerParam400 : Number", // expected treeItem label
				"headerParam400", // expected treeItem text
				"REST_HEADER_400_headerParam400", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				response400HeaderParams, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Check response 400 payload folder and content....
		 */
		assertTrue(response400Children[1] instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem response400PayloadContainer = (SwaggerMapperTreeItem) response400Children[1];
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response400PayloadContainer, // tree item to test
				SwaggerPayloadContainerTreeItem.class, // Expected treeItem class type
				"Payload", // expected treeItem label
				"Payload", // expected treeItem text
				null, // expected treeItem path
				null, // expected treeItem type
				false, // expected treeItem isArray?
				response400TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"REST_PAYLOAD_400", // to test findChild() - add item text (for payload this would be tree item's path
									// element name
				"Text"); // to test findChild() findChild() expected label for given child

		Object[] response400PayloadChildren = taskItemProvider.getChildren(response400PayloadContainer);
		assertNotNull(response400PayloadChildren);
		assertEquals(1, response400PayloadChildren.length);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				response400PayloadChildren[0], // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text", // expected treeItem label
				"REST_PAYLOAD_400", // expected treeItem text
				"REST_PAYLOAD_400", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				response400PayloadContainer, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * TESTS FOR GENERATION OF CORRECT SWAGGER TREE ITEMS FOR USE OF $ref TO INDIRECTION TYPES THAT ARE THEMSELVES JUST
	 * $ref (or array $ref) TO ANOTHER TYPE DEFINITION
	 * 
	 * We can just do this on requests generally, because we've tested that the top level structure down to and
	 * including top level param / payload items is all correct for a simnple type. The assumption is that if in the
	 * above tests the correct param/payload items were created in the correct place, then it doesn't matter where you
	 * use other types definitions for params/payload, the same type of SwaggerTypeTreeitem will be generated the same
	 * everywhere (in request/response/error).
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Designed to test that tree items for all possible parameter data types are correct
	 * 
	 * See SwaggerSchemaTests.yaml -> /serviceWithQueryParamsOfAllTypes for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Request_serviceWithQueryParamsOfAllTypes() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "serviceWithQueryParamsOfAllTypes");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		assertEquals(14, mappableTreeItems.size());

		/*
		 * Text and Text Array
		 */
		SwaggerMapperTreeItem textParam = mappableTreeItems.get(0);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				textParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"textParam : Text", // expected treeItem label
				"textParam", // expected treeItem text
				"REST_QUERY_textParam", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem textArrayParam = mappableTreeItems.get(1);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				textArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"textArrayParam : Text[]", // expected treeItem label
				"textArrayParam", // expected treeItem text
				"REST_QUERY_textArrayParam", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Integer and Integer Array
		 */
		SwaggerMapperTreeItem integerParam = mappableTreeItems.get(2);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				integerParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"integerParam : Integer", // expected treeItem label
				"integerParam", // expected treeItem text
				"REST_QUERY_integerParam", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem integerArrayParam = mappableTreeItems.get(3);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				integerArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"integerArrayParam : Integer[]", // expected treeItem label
				"integerArrayParam", // expected treeItem text
				"REST_QUERY_integerArrayParam", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Number(double) and Number(double) Array
		 */
		SwaggerMapperTreeItem doubleParam = mappableTreeItems.get(4);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				doubleParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"doubleParam : Number", // expected treeItem label
				"doubleParam", // expected treeItem text
				"REST_QUERY_doubleParam", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem doubleArrayParam = mappableTreeItems.get(5);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				doubleArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"doubleArrayParam : Number[]", // expected treeItem label
				"doubleArrayParam", // expected treeItem text
				"REST_QUERY_doubleArrayParam", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Number(float) and Number(float) Array
		 */
		SwaggerMapperTreeItem floatParam = mappableTreeItems.get(6);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				floatParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"floatParam : Number", // expected treeItem label
				"floatParam", // expected treeItem text
				"REST_QUERY_floatParam", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem floatArrayParam = mappableTreeItems.get(7);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				floatArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"floatArrayParam : Number[]", // expected treeItem label
				"floatArrayParam", // expected treeItem text
				"REST_QUERY_floatArrayParam", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Date and Date Array
		 */
		SwaggerMapperTreeItem dateParam = mappableTreeItems.get(8);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				dateParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"dateParam : Date", // expected treeItem label
				"dateParam", // expected treeItem text
				"REST_QUERY_dateParam", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem dateArrayParam = mappableTreeItems.get(9);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				dateArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"dateArrayParam : Date[]", // expected treeItem label
				"dateArrayParam", // expected treeItem text
				"REST_QUERY_dateArrayParam", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * DateTime and DateTime Array
		 */
		SwaggerMapperTreeItem dateTimeParam = mappableTreeItems.get(10);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				dateTimeParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"dateTimeParam : DateTime", // expected treeItem label
				"dateTimeParam", // expected treeItem text
				"REST_QUERY_dateTimeParam", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem dateTimeArrayParam = mappableTreeItems.get(11);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				dateTimeArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"dateTimeArrayParam : DateTime[]", // expected treeItem label
				"dateTimeArrayParam", // expected treeItem text
				"REST_QUERY_dateTimeArrayParam", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/*
		 * Boolean and Boolean Array
		 */
		SwaggerMapperTreeItem booleanParam = mappableTreeItems.get(12);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				booleanParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"booleanParam : Boolean", // expected treeItem label
				"booleanParam", // expected treeItem text
				"REST_QUERY_booleanParam", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		SwaggerMapperTreeItem booleanArrayParam = mappableTreeItems.get(13);
		assertSwaggerTreeItem(taskItemProvider, // item provider
				booleanArrayParam, // tree item to test
				SwaggerInputParamTreeItem.class, // Expected treeItem class type
				"booleanArrayParam : Boolean[]", // expected treeItem label
				"booleanArrayParam", // expected treeItem text
				"REST_QUERY_booleanArrayParam", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is $ref to type that is an indirection to a primitive STRING type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToSingleSimpleType for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToSingleSimpleType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToSingleSimpleType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to string type.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToSingleSimpleType : Text", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is $ref to type that is an indirection to a ARRAY primitive STRING type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToArraySimpleType for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToArraySimpleType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToArraySimpleType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to string type.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToArraySimpleType : Text[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is $ref to type that is an indirection to a primitive NUMBER type - JUST to prove that an indirection to
	 * a simple type doesn't (cos of some bug) always create String type as tested by previous two tests)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToSingleNumberType for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToSingleNumberType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToSingleNumberType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to number type.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToSingleNumberType : Number", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is $ref to type that is an indirection to a ARRAY primitive NUMBER type - JUST to prove that an
	 * indirection to a simple type doesn't (cos of some bug) always create String type as tested by previous two tests)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToArrayNumberType for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToArrayNumberType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToArrayNumberType -payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to number type.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToArrayNumberType : Number[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is $ref to type (IndirectionToSingleComplexTypeRef) that is an indirection to type-definition
	 * (ComplexType) that is yet another indirection to a 3rd type (ComplexType2) that is a complex type definition.
	 * 
	 * i.e. this is a MULTI-LEVEL INDIRECTION test - in the case of a ROOT payload $ref to a type that indirects to
	 * another type, then the label should show as 'TopRefTypeName : FinalType'
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToSingleComplexTypeRef for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToSingleComplexTypeRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToSingleComplexTypeRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to a type that is just an
		 * indirection to a complex type 'ComplexType2'.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToSingleComplexTypeRef : ComplexType2", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem(payloadTreeItem, "REST_PAYLOAD", activity, taskItemProvider);
	}

	/**
	 * Payload is $ref to type (IndirectionToArrayComplexTypeRef) that is an indirection to type-definition
	 * (ComplexType) that is yet another indirection to a 3rd type (ComplexType2) that is a complex type definition.
	 * 
	 * i.e. this is a MULTI-LEVEL INDIRECTION test - in the case of a ROOT payload $ref to a type that indirects to
	 * another type, then the label should show as 'TopRefTypeName : FinalType[]'
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_IndirectionToArrayComplexTypeRef for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_IndirectionToArrayComplexTypeRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IndirectionToArrayComplexTypeRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to a type that is just an
		 * indirection to a complex type 'ComplexType2'.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToArrayComplexTypeRef : ComplexType2[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem(payloadTreeItem, "REST_PAYLOAD", activity, taskItemProvider);
	}

	/**
	 * Payload is $ref to type that is an indirection to an array complex type (MiddleIndirectionIntroducesArrayType1)
	 * that is an indirection to a type (MiddleIndirectionIntroducesArrayType2) that introduces the fact that the final
	 * indirection to (MiddleIndirectionIntroducesArrayType3) should be an array.
	 * 
	 * i.e. there are 3 levels of indirection and the middle level introduces array to the type.
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDollarRefTo_MiddleIndirectionIntroducesArrayType1 for example schema
	 * that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDollarRefTo_MiddleIndirectionIntroducesArrayType1() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "MiddleIndirectionIntroducesArrayType1 - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a $ref --> a type that is just an indirection to a type that is just an
		 * indirection to a complex type 'ComplexType2'.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"MiddleIndirectionIntroducesArrayType1 : ComplexType2[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem(payloadTreeItem, "REST_PAYLOAD", activity, taskItemProvider);

	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * TESTS FOR GENERATION OF CORRECT SWAGGER TREE ITEMS FOR USE OF DIRECT REFERENCE TO SPECIFIC PRIMITIVE or COMPLEX
	 * TYPE (i.e. not a $ref to type that is just indirection $ref to yet another type).
	 * 
	 * We can just do this on requests generally, because we've tested that the top level structure down to and
	 * including top level param / payload items is all correct for a simnple type. The assumption is that if in the
	 * above tests the correct param/payload items were created in the correct place, then it doesn't matter where you
	 * use other types definitions for params/payload, the same type of SwaggerTypeTreeitem will be generated the same
	 * everywhere (in request/response/error).
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Payload is $ref to a simple complex type definition (that is not an indirection to another type)
	 * 
	 * In this case, because there is no indirection to another type, the root payload will be the ref'd complex type
	 * name (without any ' Type : finaltype' style qualification)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_ComplexType for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_ComplexType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "ComplexType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is an $ref --> 'ComplexType2'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"ComplexType2", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem(payloadTreeItem, "REST_PAYLOAD", activity, taskItemProvider);
	}

	/**
	 * Payload is an array $ref to a simple complex type definition (that is not an indirection to another type)
	 * 
	 * In this case, because there is no indirection to another type, the root payload will be the ref'd complex type
	 * name (without any ' Type : finaltype' style qualification)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_ComplexTypeArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_ComplexTypeArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "ComplexTypeArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is an array $ref --> 'ComplexType2'.
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"ComplexType2[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem(payloadTreeItem, "REST_PAYLOAD", activity, taskItemProvider);
	}

	/**
	 * Payload is String primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_String for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_String() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "String - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a String type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is StringArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_StringArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_StringArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "StringArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a String array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is Date primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_Date for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_Date() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "Date - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Date type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Date", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is DateArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_DateArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_DateArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "DateArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Date array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Date[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is DateTime primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_DateTime for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_DateTime() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "DateTime - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a DateTime type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"DateTime", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is DateTimeArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_DateTimeArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_DateTimeArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "DateTimeArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a DateTime array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"DateTime[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is NumberDouble primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_NumberDouble for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_NumberDouble() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "NumberDouble - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a NumberDouble type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Number", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is NumberDoubleArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_NumberDoubleArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_NumberDoubleArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "NumberDoubleArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a NumberDouble array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Number[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is NumberFloat primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_NumberFloat for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_NumberFloat() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "NumberFloat - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a NumberFloat type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Number", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is NumberFloatArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_NumberFloatArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_NumberFloatArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "NumberFloatArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a NumberFloat array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Number[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is Integer primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_Integer for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_Integer() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "Integer - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Integer type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Integer", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is IntegerArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_IntegerArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_IntegerArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "IntegerArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Integer array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Integer[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is Boolean primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_Boolean for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_Boolean() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "Boolean - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Boolean type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Boolean", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is BooleanArray primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsDirectRefTo_BooleanArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsDirectRefTo_BooleanArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "BooleanArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is a Boolean array type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Boolean[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * TESTS FOR GENERATION OF CORRECT SWAGGER TREE ITEMS FOR USE PROPERTY INLINE COMPLEX TYPE DEFINITIONS
	 * 
	 * We can just do this on requests generally, because we've tested that the top level structure down to and
	 * including top level param / payload items is all correct for a simnple type. The assumption is that if in the
	 * above tests the correct param/payload items were created in the correct place, then it doesn't matter where you
	 * use other types definitions for params/payload, the same type of SwaggerTypeTreeitem will be generated the same
	 * everywhere (in request/response/error).
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Payload property has an inlined complex type schema (rather than $ref to complex type)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_InlineComplexType for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_InlineComplexType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "InlineComplexType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is an Inlined complex type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Object", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"topLevelString", // to test findChild() - add item text (for payload this would be tree item's path
									// element name)
				"topLevelString : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Expect there to be 3 top level children
		 */
		Object[] topLevelChildren = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(topLevelChildren);
		assertEquals(3, topLevelChildren.length);

		/* A string property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"topLevelString : Text", // expected treeItem label
				"topLevelString", // expected treeItem text
				"REST_PAYLOAD.topLevelString", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* And a string array property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"topLevelStringArray : Text[]", // expected treeItem label
				"topLevelStringArray", // expected treeItem text
				"REST_PAYLOAD.topLevelStringArray", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* And an inlined complex type child property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[2], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childInlineComplex : Object", // expected treeItem label
				"childInlineComplex", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"childInteger", // to test findChild() - add item text (for payload this would be tree item's path
								// element name)
				"childInteger : Integer"); // to test findChild() findChild() expected label for given child

		/*
		 * The child inlined complex type should have 2 properties
		 */
		Object[] inlineComplexChildChildren = taskItemProvider.getChildren(topLevelChildren[2]);
		assertNotNull(inlineComplexChildChildren);
		assertEquals(2, inlineComplexChildChildren.length);

		/* An integer property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexChildChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childInteger : Integer", // expected treeItem label
				"childInteger", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex.childInteger", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* A property with a $ref to a complex type definition */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexChildChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childComplexType2Ref : ComplexType2", // expected treeItem label
				"childComplexType2Ref", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex.childComplexType2Ref", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem((SwaggerMapperTreeItem) inlineComplexChildChildren[1],
				"REST_PAYLOAD.childInlineComplex.childComplexType2Ref", activity, taskItemProvider);
	}

	/**
	 * Payload property has an inlined array complex type schema (rather than $ref to complex type)
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_InlineArrayComplexType for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_InlineArrayComplexType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "InlineArrayComplexType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is an Inlined array complex type'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Object[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"topLevelString", // to test findChild() - add item text (for payload this would be tree item's path
									// element name)
				"topLevelString : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Expect there to be 3 top level children
		 */
		Object[] topLevelChildren = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(topLevelChildren);
		assertEquals(3, topLevelChildren.length);

		/* A string property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"topLevelString : Text", // expected treeItem label
				"topLevelString", // expected treeItem text
				"REST_PAYLOAD.topLevelString", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* And a string array property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"topLevelStringArray : Text[]", // expected treeItem label
				"topLevelStringArray", // expected treeItem text
				"REST_PAYLOAD.topLevelStringArray", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* And an inlined complex type child property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				topLevelChildren[2], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childInlineComplex : Object", // expected treeItem label
				"childInlineComplex", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"childInteger", // to test findChild() - add item text (for payload this would be tree item's path
								// element name)
				"childInteger : Integer"); // to test findChild() findChild() expected label for given child

		/*
		 * The child inlined complex type should have 2 properties
		 */
		Object[] inlineComplexChildChildren = taskItemProvider.getChildren(topLevelChildren[2]);
		assertNotNull(inlineComplexChildChildren);
		assertEquals(2, inlineComplexChildChildren.length);

		/* An integer property */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexChildChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childInteger : Integer", // expected treeItem label
				"childInteger", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex.childInteger", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* A property with a $ref to a complex type definition */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexChildChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childComplexType2Ref : ComplexType2", // expected treeItem label
				"childComplexType2Ref", // expected treeItem text
				"REST_PAYLOAD.childInlineComplex.childComplexType2Ref", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the Payload that should directly or indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem((SwaggerMapperTreeItem) inlineComplexChildChildren[1],
				"REST_PAYLOAD.childInlineComplex.childComplexType2Ref", activity, taskItemProvider);
	}

	/**
	 * Payload is $ref to type includes just about everything that we supprot in terms of complex type and properties
	 * within it string type.
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_AllSupportedComplexTypeContent for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_AllSupportedComplexTypeContent() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_Processes.xpdl",
				"SwaggerTaskItemProviderTests_Processes", "AllSupportedComplexTypeContent - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload tree item that is an $ref --> 'ComplexType2'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"AllPropertyTypesAsGrandChildren", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"childComplex", // to test findChild() - add item text (for payload this would be tree item's path
								// element name)
				"childComplex : AllPropertyTypesAsChildren"); // to test findChild() findChild() expected label for
																// given child

		/*
		 * Expect 3 payload children, a complex type ref, an array complex type ref and an inlined complex type.
		 */
		Object[] payloadChildren = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(payloadChildren);
		assertEquals(3, payloadChildren.length);

		Object childComplexTreeItem = payloadChildren[0];
		Object childComplexArrayTreeItem = payloadChildren[1];
		Object inlineComplexTypeTreeItem = payloadChildren[2];

		/* Property AllPropertyTypesAsGrandChildren.complexType */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				childComplexTreeItem, // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childComplex : AllPropertyTypesAsChildren", // expected treeItem label
				"childComplex", // expected treeItem text
				"REST_PAYLOAD.childComplex", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"numberDoubleProperty", // to test findChild() - add item text (for payload this would be tree item's
										// path element name)
				"numberDoubleProperty : Number"); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexTypeArray */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				childComplexArrayTreeItem, // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"childComplexArray : AllPropertyTypesAsChildren[]", // expected treeItem label
				"childComplexArray", // expected treeItem text
				"REST_PAYLOAD.childComplexArray", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"booleanArrayProperty", // to test findChild() - add item text (for payload this would be tree item's
										// path element name)
				"booleanArrayProperty : Boolean[]"); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.inlineComplexType */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexTypeTreeItem, // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"inlineComplexType : Object", // expected treeItem label
				"inlineComplexType", // expected treeItem text
				"REST_PAYLOAD.inlineComplexType", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"ictObject", // to test findChild() - add item text (for payload this would be tree item's
								// path element name)
				"ictObject : Object"); // to test findChild() findChild() expected label for given child

		/* Now we can check the content of PAYLOAD.complexChild */
		assertContentOf_AllPropertyTypesAsChildren((SwaggerMapperTreeItem) childComplexTreeItem,
				"REST_PAYLOAD.childComplex", activity, taskItemProvider);

		/* Now we can check the content of PAYLOAD.complexChildArray */
		assertContentOf_AllPropertyTypesAsChildren((SwaggerMapperTreeItem) childComplexArrayTreeItem,
				"REST_PAYLOAD.childComplexArray", activity, taskItemProvider);

		/*
		 * And finally the inline complex type
		 * 
		 * Expect 2 children, a string property and an inlined child complex type
		 */
		Object[] inlineComplexTypeChildren = taskItemProvider.getChildren(inlineComplexTypeTreeItem);
		assertNotNull(inlineComplexTypeChildren);
		assertEquals(2, inlineComplexTypeChildren.length);

		/* Property AllPropertyTypesAsGrandChildren.inlineComplexType.ictText */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexTypeChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"ictText : Text", // expected treeItem label
				"ictText", // expected treeItem text
				"REST_PAYLOAD.inlineComplexType.ictText", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				inlineComplexTypeTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.inlineComplexType.ictObject */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				inlineComplexTypeChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"ictObject : Object", // expected treeItem label
				"ictObject", // expected treeItem text
				"REST_PAYLOAD.inlineComplexType.ictObject", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				inlineComplexTypeTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"ictObjectChildText", // to test findChild() - add item text (for payload this would be tree item's path
										// element name)
				"ictObjectChildText : Integer"); // to test findChild() findChild() expected label for given child

		/*
		 * And finally the child imline complex type of inline complex type
		 * 
		 * Expect 1 child, an integer
		 */
		Object[] ictObjectChildren = taskItemProvider.getChildren(inlineComplexTypeChildren[1]);
		assertNotNull(ictObjectChildren);
		assertEquals(1, ictObjectChildren.length);

		/* Property AllPropertyTypesAsGrandChildren.inlineComplexType.ictObject.ictObjectChildText */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				ictObjectChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"ictObjectChildText : Integer", // expected treeItem label
				"ictObjectChildText", // expected treeItem text
				"REST_PAYLOAD.inlineComplexType.ictObject.ictObjectChildText", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				inlineComplexTypeChildren[1], // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * UNSUPPORTED ARRAY OF ARRAYS SCENARIOS
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Payload is an array $ref to a type that is just an array $ref to another Complex type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadOverloadsArrayIndirectionToComplexType for example
	 * schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadOverloadsArrayIndirectionToComplexType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems",
				"PayloadOverloadsArrayIndirectionToComplexType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported 'Array of arrays'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToArrayComplexTypeRef : ComplexType[]..[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is an array $ref to a type that is just an array $ref to another primitive type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadOverloadsArrayIndirectionToPrimitiveType for
	 * example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadOverloadsArrayIndirectionToPrimitiveType() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems",
				"PayloadOverloadsArrayIndirectionToPrimitiveType - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported 'Array of arrays'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToArrayNumberType : Number[]..[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is an inline schema of array of string arrays
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadIsInlineArrayOfPrimitiveTypeArrays for example
	 * schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadIsInlineArrayOfPrimitiveTypeArrays() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsInlineArrayOfPrimitiveTypeArrays - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported 'Array of arrays'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Text[]..[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is an inline schema of array of inline complex type arrays
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadIsInlineArrayOfComplexTypeArrays for example schema
	 * that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadIsInlineArrayOfComplexTypeArrays() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsInlineArrayOfComplexTypeArrays - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported 'Array of arrays'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Object[]..[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload a $ref to a type that has several layers of indirection two of which are declared as arrays (thus
	 * defining an array of arrays))
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadIsTypeThatHasMutlipleArrayIndirections for example
	 * schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadIsTypeThatHasMutlipleArrayIndirections() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems",
				"PayloadIsTypeThatHasMutlipleArrayIndirections - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported 'Array of arrays'
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"CreatesArrayOfArraysJustInIndirections : MiddleIndirectionIntroducesArrayType3[]..[]", // expected
																										// treeItem
																										// label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is a $ref to a type contains an array of arrays property
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIsArrayOfArrays_PayloadIsTypeWithArrayOfArraysProperty for example schema
	 * that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIsArrayOfArrays_PayloadIsTypeWithArrayOfArraysProperty() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsTypeWithArrayOfArraysProperty - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload type TypeWithArrayOfArraysProperty
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"TypeWithArrayOfArraysProperty", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"arrayOfArraysProperty", // to test findChild() - add item text (for payload this would be tree item's
											// path element name)
				"arrayOfArraysProperty : Text[]..[]"); // to test findChild() findChild() expected label for given child

		/*
		 * Top level type children = Expect 2 children, an unsupported array of arrays and a valid string arry
		 */
		Object[] children = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(children);
		assertEquals(2, children.length);

		/* Property TypeWithArrayOfArraysProperty.arrayOfArraysProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"arrayOfArraysProperty : Text[]..[]", // expected treeItem label
				"arrayOfArraysProperty", // expected treeItem text
				"REST_PAYLOAD.arrayOfArraysProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS, // expected treeItem type
				true, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property TypeWithArrayOfArraysProperty.stringArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"stringArrayProperty : Text[]", // expected treeItem label
				"stringArrayProperty", // expected treeItem text
				"REST_PAYLOAD.stringArrayProperty", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * EXTERNAL REFERENCE SCENARIOS
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * ExternalRef Payload is a direct reference to external type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_ExternalRef for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_ExternalRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "ExternalRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported external reference
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"./external-schema.json/ExternalType : External type (unsupported)", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ArrayExternalRef Payload is a direct array reference to external type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_ArrayExternalRef for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_ArrayExternalRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "ArrayExternalRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported external reference
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"./external-schema.json/ExternalType : External type (unsupported)[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * IndirectionToExternalRef Payload is a type which is indirect reference to external type
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_ExternalRef for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_IndirectionToExternalRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "IndirectionToExternalRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported external reference
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"IndirectionToExternalRef : External type (unsupported)", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ArrayIndirectionToExternalRef Payload is a type which is indirect array reference to external
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_ArrayIndirectionToExternalRef for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_ArrayIndirectionToExternalRef() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "ArrayIndirectionToExternalRef - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported external reference
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"ArrayIndirectionToExternalRef : External type (unsupported)[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is a type contains properties that are external references
	 * 
	 * See SwaggerSchemaTests.yaml -> /payloadIs_TypeWithExternalRefProperties for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_payloadIs_TypeWithExternalRefProperties() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "TypeWithExternalRefProperties - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is reference to local type
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"TypeWithExternalTypeProperties", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"externalRefProperty", // to test findChild() - add item text (for payload this would be tree item's
										// path element name)
				"externalRefProperty : ./external-schema.json/ExternalType"); // to test findChild() findChild()
																				// expected label for given child

		/*
		 * Top level type children = Expect 2 children, a single instance external ref and an array external ref
		 */
		Object[] children = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(children);
		assertEquals(2, children.length);

		/* Property TypeWithExternalRefProperties.externalRefProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"externalRefProperty : ./external-schema.json/ExternalType", // expected treeItem label
				"externalRefProperty", // expected treeItem text
				"REST_PAYLOAD.externalRefProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				false, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property TypeWithExternalRefProperties.externalRefProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"externalRefArrayProperty : ./external-schema.json/ExternalType[]", // expected treeItem label
				"externalRefArrayProperty", // expected treeItem text
				"REST_PAYLOAD.externalRefArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_EXTERNALREF, // expected treeItem type
				true, // expected treeItem isArray?
				payloadTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * MIXED-TYPE CONSTRUCT (oneOf, anyOf, allOf, not) SCENARIOS
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Payload schema is a oneOf construct
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsOneOf for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsOneOf() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsOneOf - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported oneOf construct
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Mixed type (unsupported) : oneOf", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload schema is an array oneOf construct
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsArrayOneOf for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsArrayOneOf() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsArrayOneOf - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported array oneOf construct
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Mixed type (unsupported) : oneOf[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload schema is an anyOf construct
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsAnyOf for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsAnyOf() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsAnyOf - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported anyOf construct
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Mixed type (unsupported) : anyOf", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload schema is an allOf construct
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsAllOf for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsAllOf() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsAllOf - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported allOf construct
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Mixed type (unsupported) : allOf", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload schema is a not construct
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsNot for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsNot() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsNot - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported not construct
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Mixed type (unsupported) : not", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload schema an any type array
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsAnyTypeArray for example schema that is covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsAnyTypeArray() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsAnyTypeArray - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload that is unsupported any type array
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"Undetermined type[]", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_UNDETERMINED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * Payload is a type that has all kinds of mixed type
	 * 
	 * See SwaggerSchemaTests.yaml -> /mixedTypes_PayloadIsTypeWithMixedTypeProperties for example schema that is
	 * covered.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_mixedTypes_PayloadIsTypeWithMixedTypeProperties() throws Exception
	{
		Activity activity = findActivity(
				"/SwaggerTaskItemProviderTests_Processes/Process Packages/SwaggerTaskItemProviderTests_UnsupportedItems.xpdl",
				"SwaggerTaskItemProviderTests_UnsupportedItems", "PayloadIsTypeWithMixedTypeProperties - payload");

		List<SwaggerMapperTreeItem> mappableTreeItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getMappableItemsFromTaskItemProvider(activity, MappingDirection.IN,
				mappableTreeItems);

		/*
		 * Expect 1 payload TypeWithMixedTypeProperties type
		 */
		assertEquals(1, mappableTreeItems.size());

		SwaggerMapperTreeItem payloadTreeItem = mappableTreeItems.get(0);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadTreeItem, // tree item to test
				SwaggerPayloadRootTreeItem.class, // Expected treeItem class type
				"TypeWithMixedTypeProperties", // expected treeItem label
				"REST_PAYLOAD", // expected treeItem text
				"REST_PAYLOAD", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"oneOfProperty", // to test findChild() - add item text (for payload this would be tree item's path
									// element name)
				"oneOfProperty : Mixed type (unsupported) : oneOf"); // to test findChild() findChild() expected label
																		// for given child

		/*
		 * Expect 9 payload children, one each of oneOf, anyOf, allOf and not construct property (+ array equivalent of
		 * each) AND an 'any type array'
		 */
		Object[] payloadChildren = taskItemProvider.getChildren(payloadTreeItem);
		assertNotNull(payloadChildren);
		assertEquals(9, payloadChildren.length);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"oneOfProperty : Mixed type (unsupported) : oneOf", // expected treeItem label
				"oneOfProperty", // expected treeItem text
				"REST_PAYLOAD.oneOfProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"oneOfArrayProperty : Mixed type (unsupported) : oneOf[]", // expected treeItem label
				"oneOfArrayProperty", // expected treeItem text
				"REST_PAYLOAD.oneOfArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[2], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"anyOfProperty : Mixed type (unsupported) : anyOf", // expected treeItem label
				"anyOfProperty", // expected treeItem text
				"REST_PAYLOAD.anyOfProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[3], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"anyOfArrayProperty : Mixed type (unsupported) : anyOf[]", // expected treeItem label
				"anyOfArrayProperty", // expected treeItem text
				"REST_PAYLOAD.anyOfArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[4], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"allOfProperty : Mixed type (unsupported) : allOf", // expected treeItem label
				"allOfProperty", // expected treeItem text
				"REST_PAYLOAD.allOfProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[5], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"allOfArrayProperty : Mixed type (unsupported) : allOf[]", // expected treeItem label
				"allOfArrayProperty", // expected treeItem text
				"REST_PAYLOAD.allOfArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[6], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"notProperty : Mixed type (unsupported) : not", // expected treeItem label
				"notProperty", // expected treeItem text
				"REST_PAYLOAD.notProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				false, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[7], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"notArrayProperty : Mixed type (unsupported) : not[]", // expected treeItem label
				"notArrayProperty", // expected treeItem text
				"REST_PAYLOAD.notArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				payloadChildren[8], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"anyTypeArrayProperty : Undetermined type[]", // expected treeItem label
				"anyTypeArrayProperty", // expected treeItem text
				"REST_PAYLOAD.anyTypeArrayProperty", // expected treeItem path
				SwaggerPropertyType.UNSUPPORTED_UNDETERMINED_TYPE, // expected treeItem type
				true, // expected treeItem isArray?
				SKIP_PARENT_CHECK, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * SHARED COMPLEX TYPE SPEICIC CONTENT assertion functions
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Assert the content of any tree item that is effectively of type : AllPropertyTypesAsChildren
	 * 
	 * @param allPropertyTypesAsChildrenTreeItem
	 * @param leadPath
	 * @param activity
	 * @param taskItemProvider
	 */
	private void assertContentOf_AllPropertyTypesAsChildren(SwaggerMapperTreeItem allPropertyTypesAsChildrenTreeItem,
			String leadPath, Activity activity, SwaggerTaskItemProvider taskItemProvider)
	{
		/**
		 * Now we can check the content of PAYLOAD.complexChild
		 * 
		 * Expect 16 children, a single and array property for each of the 7 primitive types PLUS a single instance and
		 * an array inlined complex type as child properties
		 */
		Object[] children = taskItemProvider.getChildren(allPropertyTypesAsChildrenTreeItem);
		assertNotNull(children);
		assertEquals(16, children.length);

		/* Property AllPropertyTypesAsGrandChildren.complexType.stringProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"stringProperty : Text", // expected treeItem label
				"stringProperty", // expected treeItem text
				leadPath + ".stringProperty", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.stringArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"stringArrayProperty : Text[]", // expected treeItem label
				"stringArrayProperty", // expected treeItem text
				leadPath + ".stringArrayProperty", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.dateProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[2], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"dateProperty : Date", // expected treeItem label
				"dateProperty", // expected treeItem text
				leadPath + ".dateProperty", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.dateArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[3], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"dateArrayProperty : Date[]", // expected treeItem label
				"dateArrayProperty", // expected treeItem text
				leadPath + ".dateArrayProperty", // expected treeItem path
				SwaggerPropertyType.DATE, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.dateTimeProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[4], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"dateTimeProperty : DateTime", // expected treeItem label
				"dateTimeProperty", // expected treeItem text
				leadPath + ".dateTimeProperty", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.dateTimeArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[5], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"dateTimeArrayProperty : DateTime[]", // expected treeItem label
				"dateTimeArrayProperty", // expected treeItem text
				leadPath + ".dateTimeArrayProperty", // expected treeItem path
				SwaggerPropertyType.DATETIME, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.numberDoubleProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[6], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"numberDoubleProperty : Number", // expected treeItem label
				"numberDoubleProperty", // expected treeItem text
				leadPath + ".numberDoubleProperty", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.numberDoubleArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[7], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"numberDoubleArrayProperty : Number[]", // expected treeItem label
				"numberDoubleArrayProperty", // expected treeItem text
				leadPath + ".numberDoubleArrayProperty", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.numberFloatProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[8], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"numberFloatProperty : Number", // expected treeItem label
				"numberFloatProperty", // expected treeItem text
				leadPath + ".numberFloatProperty", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.numberFloatArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[9], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"numberFloatArrayProperty : Number[]", // expected treeItem label
				"numberFloatArrayProperty", // expected treeItem text
				leadPath + ".numberFloatArrayProperty", // expected treeItem path
				SwaggerPropertyType.NUMBER, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.integerProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[10], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"integerProperty : Integer", // expected treeItem label
				"integerProperty", // expected treeItem text
				leadPath + ".integerProperty", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.integerArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[11], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"integerArrayProperty : Integer[]", // expected treeItem label
				"integerArrayProperty", // expected treeItem text
				leadPath + ".integerArrayProperty", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.booleanProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[12], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"booleanProperty : Boolean", // expected treeItem label
				"booleanProperty", // expected treeItem text
				leadPath + ".booleanProperty", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.booleanArrayProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[13], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"booleanArrayProperty : Boolean[]", // expected treeItem label
				"booleanArrayProperty", // expected treeItem text
				leadPath + ".booleanArrayProperty", // expected treeItem path
				SwaggerPropertyType.BOOLEAN, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		/* Property AllPropertyTypesAsGrandChildren.complexType.indirectComplexTypeProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[14], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"indirectComplexTypeProperty : IndirectionToSingleComplexTypeRef", // expected treeItem label
				"indirectComplexTypeProperty", // expected treeItem text
				leadPath + ".indirectComplexTypeProperty", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				false, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the REST_PAYLOAD.childComplex.indirectComplexTypeProperty that should directly or
		 * indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem((SwaggerMapperTreeItem) children[14],
				leadPath + ".indirectComplexTypeProperty", activity, taskItemProvider);

		/* Property AllPropertyTypesAsGrandChildren.complexType.indirectArrayComplexTypeProperty */
		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[15], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"indirectArrayComplexTypeProperty : IndirectionToArrayComplexTypeRef[]", // expected treeItem label
				"indirectArrayComplexTypeProperty", // expected treeItem text
				leadPath + ".indirectArrayComplexTypeProperty", // expected treeItem path
				SwaggerPropertyType.OBJECT, // expected treeItem type
				true, // expected treeItem isArray?
				allPropertyTypesAsChildrenTreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				true, // expect treeItem to have children?
				"p1", // to test findChild() - add item text (for payload this would be tree item's path element name)
				"p1 : Text"); // to test findChild() findChild() expected label for given child

		/*
		 * Assert the content of the REST_PAYLOAD.childComplex.indirectComplexTypeProperty that should directly or
		 * indirectly be : ComplexType2
		 */
		assertContentOf_ComplexType2_TreeItem((SwaggerMapperTreeItem) children[15],
				leadPath + ".indirectArrayComplexTypeProperty", activity, taskItemProvider);
	}

	/**
	 * Assert the content tree items of any tree item whose type should be effectively ComplexType2
	 * 
	 * @param complexType2TreeItem
	 * @param leadPath
	 * @param activity
	 * @param taskItemProvider
	 */
	private void assertContentOf_ComplexType2_TreeItem(SwaggerMapperTreeItem complexType2TreeItem, String leadPath,
			Activity activity, SwaggerTaskItemProvider taskItemProvider)
	{
		/*
		 * Expect the children of the final referenced complex type to be in place and correct for ComplexType2
		 */
		Object[] children = taskItemProvider.getChildren(complexType2TreeItem);
		assertNotNull(children);
		assertEquals(2, children.length);

		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[0], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"p1 : Text", // expected treeItem label
				"p1", // expected treeItem text
				leadPath + ".p1", // expected treeItem path
				SwaggerPropertyType.STRING, // expected treeItem type
				false, // expected treeItem isArray?
				complexType2TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child

		assertSwaggerTreeItem(taskItemProvider, // item provider
				children[1], // tree item to test
				SwaggerPayloadTreeItem.class, // Expected treeItem class type
				"p2 : Integer", // expected treeItem label
				"p2", // expected treeItem text
				leadPath + ".p2", // expected treeItem path
				SwaggerPropertyType.INTEGER, // expected treeItem type
				false, // expected treeItem isArray?
				complexType2TreeItem, // expected treeItem parent
				activity, // expected treeItem input activity
				false, // expect treeItem to have children?
				null, // to test findChild() - add item text (for payload this would be tree item's path element name)
				null); // to test findChild() findChild() expected label for given child
	}

	/**
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * 
	 * SHARED UTILITY METHODS
	 * 
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 * ======================================================================================================
	 */

	/**
	 * Assert the basic content of the given {@link SwaggerMapperTreeItem}
	 * 
	 * Tests and asserts all the features and functions of the passed treeObject
	 * 
	 * @param taskItemProvider
	 * @param treeObject
	 * @param expectedClassType
	 * @param expectedLabel
	 *            To exercise the {@link SwaggerMapperTreeItem#getLabel()} function
	 * @param expectedText
	 *            To exercise the {@link SwaggerMapperTreeItem#getText()} function
	 * @param expectedPath
	 *            To exercise the {@link SwaggerMapperTreeItem#getPath()} function
	 * @param expectedType
	 *            To exercise the {@link SwaggerTypedTreeItem#getType()} function if the item is a
	 *            {@link SwaggerTypedTreeItem} subclass
	 * @param expectedIsArray
	 *            To exercise the {@link SwaggerTypedTreeItem#isArray()} function if the item is a
	 *            {@link SwaggerTypedTreeItem} subclass
	 * @param expectedParent
	 *            To exercise the {@link SwaggerMapperTreeItem#getParent()} function
	 * @param expectedActivity
	 *            To exercise the {@link SwaggerMapperTreeItem#getActivity()} function
	 * @param expectChildren
	 *            To exercise the {@link SwaggerTaskItemProvider#hasChildren(Object)}
	 * @param findChildPath
	 *            To exercise the {@link SwaggerMapperTreeItem#findChild(String)} function. If not <code>null</code>
	 *            then used to find a child with given text and compare the given label is same.
	 * @param expectedChildLabel
	 *            To exercise the {@link SwaggerMapperTreeItem#findChild(String)} function and ensure it finds the
	 *            correct child (see above param)
	 */
	private void assertSwaggerTreeItem(SwaggerTaskItemProvider taskItemProvider, Object treeObject,
			Class< ? > expectedClassType, String expectedLabel, String expectedText, String expectedPath,
			SwaggerPropertyType expectedType, boolean expectedIsArray, Object expectedParent, Activity expectedActivity,
			boolean expectChildren, String findChildPath, String expectedChildLabel)
	{
		assertTrue(treeObject instanceof SwaggerMapperTreeItem);
		SwaggerMapperTreeItem treeItem = (SwaggerMapperTreeItem) treeObject;

		assertEquals(expectedClassType.getCanonicalName(), treeItem.getClass().getCanonicalName());

		assertEquals(expectedLabel, treeItem.getLabel());
		assertEquals(expectedText, treeItem.getText());
		assertEquals(expectedPath, treeItem.getPath());

		if (expectedParent != SKIP_PARENT_CHECK)
		{
			assertEquals(expectedParent, treeItem.getParent());
		}

		assertEquals(expectedActivity, treeItem.getActivity());

		if (expectedType != null)
		{
			assertTrue(treeItem instanceof SwaggerTypedTreeItem);
			assertEquals(expectedType, ((SwaggerTypedTreeItem) treeItem).getType());
			assertEquals(expectedIsArray, ((SwaggerTypedTreeItem) treeItem).isArray());
		}

		if (expectedChildLabel != null)
		{
			/* Expect at least one child. */
			SwaggerMapperTreeItem childTreeItem = treeItem.findChild(findChildPath);
			assertNotNull(childTreeItem);
			assertEquals(expectedChildLabel, childTreeItem.getLabel());
		}

		if (!expectChildren)
		{
			assertFalse(taskItemProvider.hasChildren(treeItem));
			Object[] children = taskItemProvider.getChildren(treeItem);
			assertTrue(children == null || children.length == 0);
		}
		else
		{
			assertTrue(taskItemProvider.hasChildren(treeItem));
			Object[] children = taskItemProvider.getChildren(treeItem);
			assertTrue(children != null && children.length > 0);
		}
	}

	/**
	 * Get the top level mappable service tree items elements for given activity and mapping direction.
	 * 
	 * These will be the top level of the mappable content i.e. will NOT include container folders (like 'response
	 * 200'), 'Path parameters' etc.
	 * 
	 * @param activity
	 * @param direction
	 * @param mappableTreeItems
	 *            List to return the top level tree items in.
	 * 
	 * @return The task item provider used to produce the list of items.
	 */
	private SwaggerTaskItemProvider getMappableItemsFromTaskItemProvider(Activity activity, MappingDirection direction,
			List<SwaggerMapperTreeItem> mappableTreeItems)
	{
		List<SwaggerMapperTreeItem> topLevelItems = new ArrayList<>();

		SwaggerTaskItemProvider taskItemProvider = getTopLevelItemsFromTaskItemProvider(activity, direction,
				topLevelItems);

		recursiveGetMappableItemsFromTaskItemProvider(topLevelItems, mappableTreeItems);

		return taskItemProvider;
	}

	/**
	 * Recursive function to drill down thru given set of items to add only mappable items to the mappableTreeItems
	 * list.
	 * 
	 * Asserts if there is any unexpected content in the given et of items.
	 * 
	 * @param treeItems
	 * @param mappableTreeItems
	 */
	private void recursiveGetMappableItemsFromTaskItemProvider(List<SwaggerMapperTreeItem> treeItems,
			List<SwaggerMapperTreeItem> mappableTreeItems)
	{
		for (SwaggerMapperTreeItem treeItem : treeItems)
		{
			if (treeItem instanceof SwaggerTypedTreeItem)
			{
				mappableTreeItems.add(treeItem);
			}
			else if (treeItem instanceof SwaggerContainerTreeItem)
			{
				mappableTreeItems.addAll(((SwaggerContainerTreeItem) treeItem).getMappableContentItems());
			}
			else
			{
				fail("Unexpected tree item type for element: " + treeItem.toString());
			}
		}
	}

	/**
	 * Get the top level service tree items elements for given activity and mapping direction.
	 * 
	 * Asserts that items aere at least {@link SwaggerMapperTreeItem} type
	 * 
	 * @param activity
	 * @param direction
	 * @param topLevelItems
	 *            List to return the top level tree items in.
	 * 
	 * @return The task item provider used to produce the list of items.
	 */
	private SwaggerTaskItemProvider getTopLevelItemsFromTaskItemProvider(Activity activity, MappingDirection direction,
			List<SwaggerMapperTreeItem> topLevelItems)
	{
		SwaggerTaskItemProvider taskItemProvider = new SwaggerTaskItemProvider(direction);

		taskItemProvider.inputChanged(null, null, activity);

		Object[] elements = taskItemProvider.getElements(activity);

		for (Object element : elements)
		{
			assertTrue("Expected all items to be imnstacneof SwaggerMapperTreeItem",
					element instanceof SwaggerMapperTreeItem);

			topLevelItems.add((SwaggerMapperTreeItem) element);
		}

		return taskItemProvider;
	}

	/**
	 * @param xpdlFilePath
	 * @param activityName
	 * @param processName
	 * 
	 * @return The activity or asserts if file / process / activity cannot be found.
	 */
	private Activity findActivity(String xpdlFilePath, String processName, String activityName)
	{
		IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(xpdlFilePath));

		assertNotNull(xpdlFile);
		assertTrue(String.format("XPDL File not found (%s)", xpdlFilePath), xpdlFile.isAccessible());

		WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
		assertTrue(wc instanceof Xpdl2WorkingCopyImpl);

		Package pkg = (Package) wc.getRootElement();

		Process process = null;
		for (Process p : pkg.getProcesses())
		{
			if (processName.equals(p.getName()) || processName.equals(Xpdl2ModelUtil.getDisplayName(p)))
			{
				process = p;
				break;
			}
		}

		assertNotNull(String.format("Process not found (%s, %s)", xpdlFilePath, processName), process);

		Activity activity = null;
		for (Activity a : Xpdl2ModelUtil.getAllActivitiesInProc(process))
		{
			if (activityName.equals(a.getName()) || activityName.equals(Xpdl2ModelUtil.getDisplayName(a)))
			{
				activity = a;
				break;
			}
		}

		assertNotNull(String.format("Activity not found (%s, %s, %s)", xpdlFilePath, processName, activityName),
				activity);

		return activity;
	}

}
