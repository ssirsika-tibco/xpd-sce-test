/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * AceDuplicatePackageIDTest
 * <p>
 * AceDuplicatePackageIDTest - Test validations are raised if there are duplicate process IDs in a PSL project
 * 
 * OR
 * 
 * Duplicate process IDs in one psl file in a PSL project
 *
 * @author nkelkar
 * @since Mar 28, 2024
 */
public class AceDuplicatePackageIDTest extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public AceDuplicatePackageIDTest()
	{
		super(true);
	}

	/**
	 * AceDuplicatePackageIDTest
	 * 
	 * @throws Exception
	 */
	public void testValidPackageIDTest() throws Exception
	{
		doTestValidations();
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
				new String[]{"resources/AceDuplicatePackageIDTest/"}, new String[]{getTestName()});

		assertTrue("Failed to load projects from \"resources/AceDuplicatePackageIDTest/",
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
		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[]{

				new ValidationsTestProblemMarkerInfo(
						"AceDuplicatePackageIDTest/Process Script Library/AceDuplicatePackageIDTest2.psl", //$NON-NLS-1$
						"bpmn.psl.duplicateIDs", //$NON-NLS-1$
						"", //$NON-NLS-1$
						"BPM  : The following files have duplicate process IDs: /AceDuplicatePackageIDTest/Process Script Library/AceDuplicatePackageIDTest.psl (Process Script Library/AceDuplicatePackageIDTest2.psl)", //$NON-NLS-1$
						"Recreate Objects IDs"), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"AceDuplicatePackageIDTest/Process Script Library/AceDuplicatePackageIDTest.psl", //$NON-NLS-1$
						"bpmn.psl.duplicateIDs", //$NON-NLS-1$
						"", //$NON-NLS-1$
						"BPM  : The following files have duplicate process IDs: /AceDuplicatePackageIDTest/Process Script Library/AceDuplicatePackageIDTest2.psl (Process Script Library/AceDuplicatePackageIDTest.psl)", //$NON-NLS-1$
						""), //$NON-NLS-1$
		};
		return markerInfos;
	}

	@Override
	protected String getTestName()
	{
		return "AceDuplicatePackageIDTest"; //$NON-NLS-1$
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