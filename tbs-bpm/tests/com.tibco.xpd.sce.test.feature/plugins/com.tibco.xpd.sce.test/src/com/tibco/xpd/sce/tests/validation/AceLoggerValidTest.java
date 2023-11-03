/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceLoggerValidTest
 * <p>
 * Tests for the ACE specific Logger class methods
 * </p>
 * <p>
 * All methods of Logger class used in the test are valid; should not return problem markers.
 * </p>
 *
 * @author nkelkar
 * @since Oct 30, 2023
 */
@SuppressWarnings("nls")
public class AceLoggerValidTest extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public AceLoggerValidTest()
	{
		super(true);
	}

	/**
	 * AceLoggerValidTest
	 * 
	 * @throws Exception
	 */
	public void testValidLoggerMethodsTest() throws Exception
	{
		doTestValidations();

		/*
		 * This project should have no problems.
		 */
		assertFalse("AceLoggerValidTest project should not have any ERROR level problem markers",
				TestUtil.hasErrorProblemMarker(
						ResourcesPlugin.getWorkspace().getRoot().getProject("AceLoggerValidTest"), true,
						getTestName()));

		return;
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
	 *
	 */
	@Override
	protected void customTestResourceSetup()
	{
		/*
		 * This test doesn't create files via the normal getTestResources() because we want to import and migrate a
		 * whole project from AMX BPM.
		 */
		projectImporter = TestUtil.importProjectsFromZip(getTestPlugInId(),
				new String[]{"resources/AceLoggerValidTest/"}, new String[]{getTestName()});

		assertTrue("Failed to load projects from \"resources/AceLoggerValidTest/",
				projectImporter != null);
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

	@Override
	protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos()
	{
		return new ValidationsTestProblemMarkerInfo[]{};
	}

	@Override
	protected String getTestName()
	{
		return "AceLoggerValidTest"; //$NON-NLS-1$
	}

	@Override
	protected String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
	}

	@Override
	protected TestResourceInfo[] getTestResources()
	{
		return new TestResourceInfo[]{};
	}

}
