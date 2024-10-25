/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Sid ACE-8452 Tests for use of PSL array parameters with spefici regard to array.func(T<>) functions woth GenericType
 * checking like push/pushAll() and similar for Generic T<> return type functions like T<> array.pop();
 * 
 * Contains tests for NOT raising problems in valid use cases
 * 
 * Contains tests for raising problems in valid use cases
 * 
 * Contains regression tests for NOT raising problems for normal use of various types of parameter not related to
 * GenericType handling.
 * 
 * Contains regression tests for NOT raising problems for normal use of various types of parameter not related to
 * GenericType handling.
 * 
 *
 * @author aallway
 * @since June-24
 */
public class PSLArrayGenericTypeParamAndReturnTest extends AbstractN2BaseValidationTest
{


	/**
	 * Tests for invalid and valid data type assignments related to PSL functions parameters and returns and also
	 * regression testing of other basic assignment / method param tyep checking.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public void testPslParamGenericTypeHandling() throws Exception
	{
		/*
		 * Import project with invalid files
		 */
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/PSLArrayGenericTypeParamAndReturnTest/PSLArrayValidationData/",
						"resources/PSLArrayGenericTypeParamAndReturnTest/PSLArrayValidation/",
						"resources/PSLArrayGenericTypeParamAndReturnTest/PSLArrayValidationProcess/"},
				new String[]{"PSLArrayValidationData", "PSLArrayValidation", "PSLArrayValidationProcess"});
		try
		{
			buildAndWait();

			/* Check correct problem markers are raised. */
			doTestValidations();

			/* Check that resources that should be clean are free of problem markers. */
			assertFalse("PSLArrayValidation/Process Script Libraries/ValidPSLLibrary.psl" //$NON-NLS-1$
					+ " ScriptLibrary should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot()
									.getFile(new Path(
											"PSLArrayValidation/Process Script Libraries/ValidPSLLibrary.psl")), //$NON-NLS-1$
							true, "testPslParamGenericTypeHandling"));

			assertFalse("PSLArrayValidationProcess/Process Packages/ValidProcess.xpdl" //$NON-NLS-1$
					+ " ScriptLibrary should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot().getFile(
									new Path("PSLArrayValidationProcess/Process Packages/ValidProcess.xpdl")), //$NON-NLS-1$
							true, "testPslParamGenericTypeHandling"));

		}
		finally
		{
			assertTrue(projectImporter.performDelete());
		}
	}

	@Override
	protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos()
	{
		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[]{

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:10 column:25, Assignment of com.example.pslarrayvalidationdata::Class1 from Date is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:11 column:27, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Date is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:13 column:33, Method push is not applicable for the provided argument types (Number) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:14 column:24, Method push is not applicable for the provided argument types (Decimal) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:15 column:32, Method pushAll is not applicable for the provided argument types (Decimal[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:16 column:26, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:17 column:33, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:18 column:27, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:19 column:35, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:2 column:29, Method push is not applicable for the provided argument types (Number) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:20 column:26, Assignment of Decimal from DateTimeTZ is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:21 column:29, Assignment of com.example.pslarrayvalidationdata::Class1 from DateTimeTZ is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:22 column:31, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from DateTimeTZ is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:24 column:29, Method push is not applicable for the provided argument types (Number) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:25 column:20, Method push is not applicable for the provided argument types (Decimal) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:26 column:28, Method pushAll is not applicable for the provided argument types (Decimal[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:27 column:22, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:28 column:29, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:29 column:23, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:3 column:20, Method push is not applicable for the provided argument types (Decimal) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:30 column:31, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:31 column:22, Assignment of Decimal from Time is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:32 column:25, Assignment of com.example.pslarrayvalidationdata::Class1 from Time is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:33 column:27, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Time is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:35 column:23, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:36 column:25, Method push is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case1>) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:37 column:31, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:38 column:33, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case1>[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:39 column:22, Assignment of Decimal from Text is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:4 column:28, Method pushAll is not applicable for the provided argument types (Decimal[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:40 column:25, Assignment of com.example.pslarrayvalidationdata::Class1 from Text is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:41 column:27, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Text is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:43 column:21, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:44 column:20, Method push is not applicable for the provided argument types (Date) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:45 column:28, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:46 column:20, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:47 column:30, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:48 column:24, Assignment of com.example.pslarrayvalidationdata::Class1 from Decimal is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:49 column:22, Assignment of Date from Decimal is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:5 column:22, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:51 column:23, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:52 column:22, Method push is not applicable for the provided argument types (Date) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:53 column:30, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:54 column:22, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:55 column:32, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:56 column:26, Assignment of com.example.pslarrayvalidationdata::Class1 from Decimal is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:57 column:24, Assignment of Date from Decimal is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:6 column:29, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:60 column:25, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class2) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:61 column:23, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:62 column:33, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class2[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:63 column:27, Assignment of com.example.pslarrayvalidationdata::Class2 from com.example.pslarrayvalidationdata::Class1 is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:64 column:25, Assignment of String from com.example.pslarrayvalidationdata::Class1 is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:66 column:29, Method push is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case2>) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:67 column:26, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Case1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:68 column:37, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case2>[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:69 column:31, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case2> from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:7 column:23, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:70 column:28, Assignment of com.example.pslarrayvalidationdata::Case1 from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:72 column:22, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:73 column:20, Method push is not applicable for the provided argument types (Decimal) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:74 column:29, Method pushAll is not applicable for the provided argument types (String[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:75 column:22, Assignment of Decimal from Boolean is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:77 column:22, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:78 column:30, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:79 column:21, Assignment of Decimal from Text is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:8 column:31, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:81 column:28, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:82 column:36, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:83 column:26, Assignment of Decimal from Text is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_3K2GIi2DEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:9 column:22, Assignment of Decimal from Date is not supported (InvalidPSLLibrary.psl:arrayGenericTypeFunctionsInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:10 column:78, Assignment of com.example.pslarrayvalidationdata::Case1 from CaseReference[] with different multiplicity is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:11 column:11, Assignment of Boolean from Decimal is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:12 column:12, Assignment of Boolean from String is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:13 column:20, Assignment of Time from Number is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:14 column:14, Assignment of String from com.example.pslarrayvalidationdata::Class1 is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:15 column:12, Assignment of Date from String is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:16 column:19, Assignment of Performer from com.example.pslarrayvalidationdata::Class2 is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:17 column:21, Assignment of Performer from CaseReference<com.example.pslarrayvalidationdata::Case2> is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:18 column:14, Assignment of Decimal from Boolean is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:2 column:15, Assignment of Date from Decimal is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:20 column:28, Method push is not applicable for the provided argument types (String) (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:21 column:34, Assignment of DateTimeTZ from Text is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:23 column:66, Assignment of Decimal from Text is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:23 column:66, Method charAt is not applicable for the provided argument types (Text) (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:3 column:14, Assignment of String from com.example.pslarrayvalidationdata::Class1 is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:4 column:16, Assignment of String from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:5 column:19, Assignment of Decimal from Text is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:6 column:17, Assignment of Decimal from Date is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:7 column:67, Assignment of com.example.pslarrayvalidationdata::Class1 from com.example.pslarrayvalidationdata::Class2 is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:8 column:67, Assignment of com.example.pslarrayvalidationdata::Class2 from com.example.pslarrayvalidationdata::Class1 is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/PSLArrayValidation/Process Script Libraries/InvalidPSLLibrary.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_b-j-QC2HEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:9 column:29, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from String is not supported (InvalidPSLLibrary.psl:standardUsageRegressionCheckInvalid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:10 column:35, Assignment of com.example.pslarrayvalidationdata::Class1 from Date is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:11 column:37, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Date is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:13 column:38, Method push is not applicable for the provided argument types (Number) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:14 column:34, Method push is not applicable for the provided argument types (Decimal) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:15 column:42, Method pushAll is not applicable for the provided argument types (Decimal[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:16 column:31, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:17 column:43, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:18 column:37, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:19 column:45, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:2 column:34, Method push is not applicable for the provided argument types (Number) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:20 column:36, Assignment of Decimal from DateTimeTZ is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:21 column:39, Assignment of com.example.pslarrayvalidationdata::Class1 from DateTimeTZ is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:22 column:41, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from DateTimeTZ is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:24 column:34, Method push is not applicable for the provided argument types (Number) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:25 column:30, Method push is not applicable for the provided argument types (Decimal) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:26 column:38, Method pushAll is not applicable for the provided argument types (Decimal[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:27 column:27, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:28 column:39, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:29 column:33, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:3 column:30, Method push is not applicable for the provided argument types (Decimal) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:30 column:41, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:31 column:32, Assignment of Decimal from Time is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:32 column:35, Assignment of com.example.pslarrayvalidationdata::Class1 from Time is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:33 column:37, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Time is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:35 column:33, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:36 column:35, Method push is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case1>) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:37 column:41, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:38 column:43, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case1>[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:39 column:32, Assignment of Decimal from Text is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:4 column:38, Method pushAll is not applicable for the provided argument types (Decimal[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:40 column:35, Assignment of com.example.pslarrayvalidationdata::Class1 from Text is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:41 column:37, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from Text is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:43 column:26, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:44 column:30, Method push is not applicable for the provided argument types (Date) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:45 column:38, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:46 column:30, Method push is not applicable for the provided argument types (Text) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:47 column:40, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:48 column:34, Assignment of com.example.pslarrayvalidationdata::Class1 from Decimal is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:49 column:32, Assignment of Date from Decimal is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:5 column:27, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:51 column:28, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:52 column:32, Method push is not applicable for the provided argument types (Date) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:53 column:40, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:54 column:32, Method push is not applicable for the provided argument types (Text) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:55 column:42, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:56 column:36, Assignment of com.example.pslarrayvalidationdata::Class1 from Decimal is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:57 column:34, Assignment of Date from Decimal is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:6 column:39, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:60 column:35, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class2) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:61 column:33, Method push is not applicable for the provided argument types (Text) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:62 column:43, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class2[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:63 column:37, Assignment of com.example.pslarrayvalidationdata::Class2 from com.example.pslarrayvalidationdata::Class1 is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:64 column:35, Assignment of Text from com.example.pslarrayvalidationdata::Class1 is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:66 column:39, Method push is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case2>) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:67 column:36, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Case1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:68 column:47, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.pslarrayvalidationdata::Case2>[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:69 column:41, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case2> from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:7 column:33, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:70 column:38, Assignment of com.example.pslarrayvalidationdata::Case1 from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:72 column:27, Method push is not applicable for the provided argument types (String) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:73 column:30, Method push is not applicable for the provided argument types (Decimal) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:74 column:39, Method pushAll is not applicable for the provided argument types (Text[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:75 column:32, Assignment of Decimal from Boolean is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:77 column:32, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:78 column:40, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:79 column:31, Assignment of Decimal from Text is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:8 column:41, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:81 column:38, Method push is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:82 column:46, Method pushAll is not applicable for the provided argument types (com.example.pslarrayvalidationdata::Class1[]) (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:83 column:36, Assignment of Decimal from Text is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_n1KKAS2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:9 column:32, Assignment of Decimal from Date is not supported (arrayGenericTypeFunctionsInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:10 column:83, Assignment of com.example.pslarrayvalidationdata::Case1 from CaseReference[] with different multiplicity is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:11 column:21, Assignment of Boolean from Decimal is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:12 column:22, Assignment of Boolean from Text is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:13 column:25, Assignment of Time from Number is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:14 column:24, Assignment of Text from com.example.pslarrayvalidationdata::Class1 is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:15 column:22, Assignment of Date from Text is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:16 column:29, Assignment of Text from com.example.pslarrayvalidationdata::Class2 is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:17 column:31, Assignment of Text from CaseReference<com.example.pslarrayvalidationdata::Case2> is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:18 column:24, Assignment of Decimal from Boolean is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:2 column:25, Assignment of Date from Decimal is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:20 column:38, Method push is not applicable for the provided argument types (Text) (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:21 column:44, Assignment of DateTimeTZ from Text is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:23 column:91, Assignment of Decimal from Text is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:23 column:91, Method charAt is not applicable for the provided argument types (Text) (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:3 column:24, Assignment of Text from com.example.pslarrayvalidationdata::Class1 is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:4 column:26, Assignment of Text from CaseReference<com.example.pslarrayvalidationdata::Case1> is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:5 column:29, Assignment of Decimal from Text is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:6 column:22, Assignment of Decimal from Date is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:7 column:72, Assignment of com.example.pslarrayvalidationdata::Class1 from com.example.pslarrayvalidationdata::Class2 is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:8 column:72, Assignment of com.example.pslarrayvalidationdata::Class2 from com.example.pslarrayvalidationdata::Class1 is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/PSLArrayValidationProcess/Process Packages/InvalidProcess.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_q4AP8S2NEe-cQNuWxyx8bQ", //$NON-NLS-1$
						"BPM  : At Line:9 column:34, Assignment of CaseReference<com.example.pslarrayvalidationdata::Case1> from String is not supported (standardUsageRegressionCheckInvalid:ScriptTask)", //$NON-NLS-1$
						""), //$NON-NLS-1$

		};
		return markerInfos;
	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
	 *
	 * @return
	 */
	@Override
	protected String getTestName()
	{
		return "PSLArrayGenericTypeParamAndReturnTest"; //$NON-NLS-1$
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
