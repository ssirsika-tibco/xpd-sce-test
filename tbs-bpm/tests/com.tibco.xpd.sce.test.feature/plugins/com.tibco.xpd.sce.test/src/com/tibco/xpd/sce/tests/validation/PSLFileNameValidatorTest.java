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
 * Test for PSLFileNameValidator
 *
 * @author ssirsika
 * @since 21-Mar-2024
 */
public class PSLFileNameValidatorTest extends AbstractN2BaseValidationTest
{

	private static final String ERROR_CODE = "bx.pslFileNameRestrictions";

	/**
	 * PSLProjectNameValidatorTest
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public void testPSLProjectNameValidatorTest() throws Exception
	{
		/*
		 * Import project with invalid files
		 */
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/PSLNameValidator/InvalidPSLs/"}, new String[]{"InvalidPSLs"});
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
				new ValidationsTestProblemMarkerInfo("/InvalidPSLs/Process Script Library/1ABC.psl",
						ERROR_CODE, "",
						"BPM : Process Script Library file name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (1ABC.psl)",
						""),
				new ValidationsTestProblemMarkerInfo("/InvalidPSLs/Process Script Library/A$BC.psl",
						ERROR_CODE, "",
						"BPM : Process Script Library file name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (A$BC.psl)",
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
		return "PSLFieNameValidatorTest"; //$NON-NLS-1$
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
