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
 * PSLFunctionNameValidator
 * <p>
 * Tests validation rule for Process Scripts Function name ensuring it
 * - has alpha-numeric or _ characters only
 * - has leading alpha character
 * - is a valid JavaScript identifier
 *
 * Tests validation rule against the defined maxLengths for Process Scripts Function name(100) and description(1024)
 * 
 * Tests validation rule for duplicate function names in same script library
 * 
 * @author nkelkar
 * @since Mar 22, 2024
 */
public class PSLFunctionNameValidator extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public PSLFunctionNameValidator()
	{
		super(true);
	}

	/**
	 * PSLFunctionNameValidator
	 * 
	 * @throws Exception
	 */
	public void testValidPSLFunctionNameTest() throws Exception
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
				new String[]{"resources/PSLProjects/PSLFunctionNameValidator/"}, new String[]{getTestName()});

		assertTrue("Failed to load projects from \"resources/PSLProjects/PSLFunctionNameValidator/",
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
						"PSLFunctionNameValidator/Process Script Library/PSLFunctionNameValidator.psl", //$NON-NLS-1$
						"bpmn.functionDescription.length", //$NON-NLS-1$
						"_s5V0EOgJEe6oh7ZIPMssSA", //$NON-NLS-1$
						"BPM  : Process Script Function description should have max length of 1024 characters. (PSLFunctionNameValidator:function_2)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionNameValidator/Process Script Library/PSLFunctionNameValidator.psl", //$NON-NLS-1$
						"bpmn.functionName.validJSIdentifier", //$NON-NLS-1$
						"_lO7tIOgJEe6oh7ZIPMssSA", //$NON-NLS-1$
						"BPM  : Process Script Function name can only contain the characters A-Z, a-z, 0-9 and underscore and must be a valid JavaScript identifier. (PSLFunctionNameValidator:1$function1)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionNameValidator/Process Script Library/PSLFunctionNameValidator.psl", //$NON-NLS-1$
						"bpmn.functionName.duplicate", //$NON-NLS-1$
						"_qFnvQOgJEe6oh7ZIPMssSA", //$NON-NLS-1$
						"BPM  : Process Script Function name must be unique. (PSLFunctionNameValidator:function_2)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionNameValidator/Process Script Library/PSLFunctionNameValidator.psl", //$NON-NLS-1$
						"bpmn.functionName.duplicate", //$NON-NLS-1$
						"_s5V0EOgJEe6oh7ZIPMssSA", //$NON-NLS-1$
						"BPM  : Process Script Function name must be unique. (PSLFunctionNameValidator:function_2)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionNameValidator/Process Script Library/PSLFunctionNameValidator.psl", //$NON-NLS-1$
						"bpmn.functionName.length", //$NON-NLS-1$
						"__RpoIOgJEe6oh7ZIPMssSA", //$NON-NLS-1$
						"BPM  : Process Script Function name should have max length of 100 characters. (PSLFunctionNameValidator:TheNameOfThisFunctionExceeds100CharactersWhichIsNotAllowedThisShouldReturnAValidationErrorStatingMaxAllowedIs100)", //$NON-NLS-1$
						""), //$NON-NLS-1$
		};
		return markerInfos;
	}

	@Override
	protected String getTestName()
	{
		return "PSLFunctionNameValidator"; //$NON-NLS-1$
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
