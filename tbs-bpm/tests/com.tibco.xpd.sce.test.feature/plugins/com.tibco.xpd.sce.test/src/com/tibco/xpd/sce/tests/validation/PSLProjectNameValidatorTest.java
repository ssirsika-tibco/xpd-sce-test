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
 * Test for {@link PSLProjectNameValidator}
 *
 * @author ssirsika
 * @since 21-Mar-2024
 */
public class PSLProjectNameValidatorTest extends AbstractN2BaseValidationTest
{

	/**
	 * PSLProjectNameValidatorTest
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public void testPSLProjectNameValidatorTest() throws Exception
	{
		/*
		 * Import two projects, '1ABC' and '.ABC' is project with invalid name and 'ABC1' is project with valid name.
		 */
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/PSLNameValidator/1ABC.zip", "resources/PSLNameValidator/ABC1.zip",
						"resources/PSLNameValidator/A.B.C.zip", "resources/PSLNameValidator/.ABC.zip"},
				new String[]{"1ABC", "ABC1", "A.B.C", ".ABC"});
		try
		{
			buildAndWait();

			doTestValidations();
		}
		finally
		{
			assertTrue(projectImporter.performDelete());
		}
	}

	/**
	 * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
	 *
	 * @return
	 */
	@SuppressWarnings("nls")
	@Override
	protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos()
	{
		// Check that validation should be present
		return new ValidationsTestProblemMarkerInfo[]{
				new ValidationsTestProblemMarkerInfo("/1ABC",
						"bx.invalidPSLProjectName", "",
						"BPM : BPM Script project name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (1ABC)",
						""),
				new ValidationsTestProblemMarkerInfo("/A.B.C", "bx.invalidPSLProjectName", "",
						"BPM : BPM Script project name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (A.B.C)",
						""),
				new ValidationsTestProblemMarkerInfo("/.ABC", "bx.invalidPSLProjectName", "",
						"BPM : BPM Script project name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (.ABC)",
						"")};
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
	 *
	 * @return
	 */
	@Override
	protected String getTestName()
	{
		return "PSLProjectNameValidatorTest"; //$NON-NLS-1$
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
	 *
	 * @return
	 */
	@Override
	protected TestResourceInfo[] getTestResources()
	{
		return new TestResourceInfo[0];
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
	 *
	 * @return
	 */
	@Override
	protected String getTestPlugInId()
	{
		return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
	}

}
