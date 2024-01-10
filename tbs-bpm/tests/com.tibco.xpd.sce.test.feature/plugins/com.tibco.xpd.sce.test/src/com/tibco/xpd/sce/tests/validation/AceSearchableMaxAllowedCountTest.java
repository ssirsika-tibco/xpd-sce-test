/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceSearchableMaxAllowedCountTest
 * <p>
 * AceSearchableMaxAllowedCountTest - Test validations are raised if the count of searchable attributes in a case class
 * is more than 30. It takes into consideration the attributes directly or indirectly included in the case object via
 * composition of normal classes.
 * 
 *
 * @author nkelkar
 * @since Jan 9, 2024
 */
public class AceSearchableMaxAllowedCountTest extends AbstractBaseValidationTest
{

	public AceSearchableMaxAllowedCountTest()
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
	public void testAceSearchableMaxAllowedCountTest() throws Exception
	{
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/AceSearchableMaxAllowedCountTest/searchable-data-in-composition.zip"},
				new String[]{"searchable-data-in-composition"});
		try
		{
			buildAndWait();

			doTestValidations();
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
		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[]{

				new ValidationsTestProblemMarkerInfo(
						"/searchable-data-in-composition/Business Objects/searchabledataincomposition.bom",
						"ace.bom.max.searchable", "_CrTLpq6tEe6yJdXJCDD7dw",
						"BPM  : Case classes cannot have more than 30 searchable attributes (Customer Record (com.example.searchabledataincomposition))",
						""),

				new ValidationsTestProblemMarkerInfo(
						"/searchable-data-in-composition/Business Objects/searchabledataincomposition.bom",
						"ace.bom.max.searchable", "_wAC4f63mEe6fUot3poa0zQ",
						"BPM  : Case classes cannot have more than 30 searchable attributes (Order Record (com.example.searchabledataincomposition))",
						""),

		};
		return markerInfos;
	}

	@Override
	protected String getTestName()
	{
		return "AceSearchableMaxAllowedCountTest";
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
				new TestResourceInfo("resources/AceSearchableMaxAllowedCountTest",
						"searchable-data-in-composition/Business Objects{bom}/searchabledataincomposition.bom"),
				new TestResourceInfo("resources/AceSearchableMaxAllowedCountTest",
						"searchable-data-in-composition/Business Objects{bom}/searchabledataincomposition.bom"),};

		return testResources;
	}

}
