/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceSearchableInNonCaseClassTest
 * <p>
 * AceSearchableInNonCaseClassTest - Test no validations are raised for searchable attributes in non-case classes
 * </p>
 * </p>
 *
 * @author nkelkar
 * @since Jan 5, 2024
 */
public class AceSearchableInNonCaseClassTest extends AbstractBaseValidationTest
{
	public AceSearchableInNonCaseClassTest()
	{
		super(true);
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
	}

	/**
	 * SearchSummaryValidationTest
	 * 
	 * @throws Exception
	 */
	public void testAceSearchableInNonCaseClassTest() throws Exception
	{
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/AceSearchableInNonCaseClassTest/simple-searchable-data.zip"},
				new String[]{"simple-searchable-data"});
		try
		{
			buildAndWait();

			doTestValidations();

			/*
			 * This project should have no problems.
			 */
			assertFalse("AceSearchableInNonCaseClassTest project should not have any ERROR level problem markers",
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot().getProject("AceSearchableInNonCaseClassTest"),
							true, getTestName()));
		}
		finally
		{
			assertTrue(projectImporter.performDelete());
		}
		return;
	}

	@Override
	protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos()
	{
		return new ValidationsTestProblemMarkerInfo[]{};
	}

	@Override
	protected String getTestName()
	{
		return "AceSearchableInNonCaseClassTest";
	}

	@Override
	protected String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test";
	}

	@Override
	protected TestResourceInfo[] getTestResources()
	{
		TestResourceInfo[] testResources = new TestResourceInfo[]{
				new TestResourceInfo("resources/AceSearchableInNonCaseClassTest",
						"simple-searchable-data/Business Objects{bom}/simplesearchabledata.bom"),
				new TestResourceInfo("resources/AceSearchableInNonCaseClassTest",
						"simple-searchable-data/Business Objects{bom}/simplesearchabledata.bom"),};

		return testResources;
	}
}

