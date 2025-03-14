/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * PSLFunctionParameterValidation
 * <p>
 * PSLFunctionParameterValidation - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * 
 *
 * @author
 * @since
 */
public class PSLFunctionParameterValidation extends AbstractN2BaseValidationTest
{

	private ProjectImporter projectImporter;

	public PSLFunctionParameterValidation()
	{
		super(true);
	}

	/**
	 * PSLFunctionParameterValidationTest
	 * 
	 * @throws Exception
	 */
	public void testPSLFunctionParameterValidation() throws Exception
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
		 * This test doesn't create files via the normal getTestResources() because we want to import a whole project
		 * from AMX BPM.
		 */
		projectImporter = TestUtil.importProjectsFromZip(getTestPlugInId(),
				new String[]{"resources/PSLProjects/PSLFunctionParameterValidation/"}, new String[]{getTestName()});

		assertTrue("Failed to load projects from \"resources/PSLProjects/PSLFunctionParameterValidation/",
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
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.functionParam.invalidName", //$NON-NLS-1$
						"_1dMcYfZrEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Name can only contain alpha-numeric characters and underscores. (PSLFunctionParameterValidation:function2:parameter 2)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.noScaleMustHaveNoLength", //$NON-NLS-1$
						"_CTenwPZrEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Number data with no decimals defined must also have no length defined. (PSLFunctionParameterValidation:function1:parameter3)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.functionParam.description.length", //$NON-NLS-1$
						"_EVkl4fZsEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Process Script Function Parameter description should have max length of 1024 characters. (PSLFunctionParameterValidation:function2:ThisParameterNameLengthWillBeMoreThan100Characters_________ExpectingAnErrorToBeRaisedForThisParameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.functionParam.name.length", //$NON-NLS-1$
						"_EVkl4fZsEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Process Script Function Parameter name should have max length of 100 characters. (PSLFunctionParameterValidation:function2:ThisParameterNameLengthWillBeMoreThan100Characters_________ExpectingAnErrorToBeRaisedForThisParameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.unsetDataExternalReference", //$NON-NLS-1$
						"_iPYjsfZrEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : The reference to business object model data type has not been set. (PSLFunctionParameterValidation:function1:parameter5)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.scaleNotCorrect", //$NON-NLS-1$
						"_t0I1w_ZqEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Decimal Places must be a non-negative numeric value, and cannot be greater than the length. (PSLFunctionParameterValidation:function1:parameter2)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.lengthNotCorrect", //$NON-NLS-1$
						"_t0I1wvZqEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Length of the Data Field / Parameter / Type Declaration must be a value greater than zero if decimal places are specified. (PSLFunctionParameterValidation:function1:parameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.scaleNotCorrect", //$NON-NLS-1$
						"_t0I1wvZqEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Decimal Places must be a non-negative numeric value, and cannot be greater than the length. (PSLFunctionParameterValidation:function1:parameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.functionParam.duplicate", //$NON-NLS-1$
						"_vgWqg_ZrEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Process Script Function Parameter name must be unique. (PSLFunctionParameterValidation:function2:parameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"PSLFunctionParameterValidation/Process Script Library/PSLFunctionParameterValidation.psl", //$NON-NLS-1$
						"bpmn.functionParam.duplicate", //$NON-NLS-1$
						"_vgWqgvZrEe6qP54o_4-7Lw", //$NON-NLS-1$
						"BPM  : Process Script Function Parameter name must be unique. (PSLFunctionParameterValidation:function2:parameter)", //$NON-NLS-1$
						""), //$NON-NLS-1$

		};
		return markerInfos;
	}

	@Override
	protected String getTestName()
	{
		return "PSLFunctionParameterValidation"; //$NON-NLS-1$
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
