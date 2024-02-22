/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.javascript;

import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Test valid Javascript String functions
 *
 * @author nkelkar
 * @since Feb 19, 2024
 */
public class JavascriptStringTest extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public JavascriptStringTest()
	{
		super(true);
	}

	/**
	 * JavascriptStringTest
	 * 
	 * @throws Exception
	 */
	public void testValidLoggerMethodsTest() throws Exception
	{
		doTestValidations();

		/*
		 * This project should have no problems.
		 */
		assertFalse("JavascriptStringTest project should not have any ERROR level problem markers",
				TestUtil.hasErrorProblemMarker(
						ResourcesPlugin.getWorkspace().getRoot().getProject("JavascriptStringTest"), true,
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
				new String[]{"resources/JavascriptStringTest/"}, new String[]{getTestName()});

		assertTrue("Failed to load projects from \"resources/JavascriptStringTest/",
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
		return "JavascriptStringTest"; //$NON-NLS-1$
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
