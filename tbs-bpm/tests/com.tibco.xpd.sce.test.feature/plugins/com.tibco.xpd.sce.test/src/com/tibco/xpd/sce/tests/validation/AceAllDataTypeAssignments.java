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
 * Sid ACE-8871 Tests for EVERY valid data type assignment / comparison in JavaScripts (that I can think of). And many
 * negative tests of invalid usage of data types.
 * 
 * If we change script expressions evaluation / validation, then this test is a good place to ensure that nothing
 * existing has been broken inadvertently.
 *
 * @author aallway
 * @since June-24
 */
public class AceAllDataTypeAssignments extends AbstractN2BaseValidationTest
{
	/**
	 * Tests for invalid and valid data type assignments related to PSL functions parameters and returns and also
	 * regression testing of other basic assignment / method param tyep checking.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public void testAllDataTypeAssignmentsValidation() throws Exception
	{
		/*
		 * Import project with invalid files
		 */
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/AceAllDataTypesScriptAssignments/CaseData/",
						"resources/AceAllDataTypesScriptAssignments/Scripts/",
						"resources/AceAllDataTypesScriptAssignments/ScriptsWithAllDataTypeAssignments/"},
				new String[]{"CaseData", "Scripts", "ScriptsWithAllDataTypeAssignments"});
		try
		{
			buildAndWait();

			/* Check correct problem markers are raised. */
			doTestValidations();

			/* Check that resources that should be clean are free of problem markers. */
			assertFalse("ScriptsWithAllDataTypeAssignments/Process Packages/ValidUseCases.xpdl" //$NON-NLS-1$
					+ " ValidUseCases.xpdl should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot().getFile(
									new Path("ScriptsWithAllDataTypeAssignments/Process Packages/ValidUseCases.xpdl")), //$NON-NLS-1$
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
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:38, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:100 column:81, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:101 column:76, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:102 column:84, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:103 column:81, Method returnDateArray is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:104 column:81, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:105 column:89, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:106 column:81, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:109 column:40, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:39, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:110 column:49, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:111 column:48, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:113 column:37, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:114 column:46, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:115 column:45, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:118 column:37, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:119 column:46, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:120 column:45, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:123 column:41, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:124 column:50, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:125 column:49, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:128 column:36, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:129 column:45, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:51, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:130 column:44, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:133 column:42, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:134 column:51, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:135 column:50, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:137 column:42, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:138 column:51, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:139 column:50, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:51, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:51, Assignment of Decimal[] from Decimal with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:141 column:41, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:142 column:50, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:143 column:49, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:58, Method pushAll is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:59, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:34, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:43, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:42, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:46, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:68, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:76, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:73, Method returnDate is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:73, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:81, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:78, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:73, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:81, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:78, Method returnDateArray is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:78, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:86, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:78, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:37, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:46, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:45, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:34, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:43, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:42, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:34, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:43, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:42, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:38, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:47, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:46, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:33, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:42, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:41, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:39, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:48, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:47, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:39, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:48, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:47, Property bomObject is invalid for the current context (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:38, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:47, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:46, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:81 column:33, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:82 column:41, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:83 column:42, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:85 column:57, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:86 column:57, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:86 column:57, Assignment of Decimal[] from Decimal with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:87 column:64, Method pushAll is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:65, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:30, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:90 column:37, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:91 column:46, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:92 column:45, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:94 column:49, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:95 column:71, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:96 column:79, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:97 column:76, Method returnDate is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:98 column:76, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:99 column:84, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:137 column:42, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:139 column:50, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:39, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxd5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:47, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidNumberFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:27, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:27, Assignment of Text[] from Text with different multiplicity is not supported (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:34, Method pushAll is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:35, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:26, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:35, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:34, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:38, Method push is not applicable for the provided argument types (com.example.casedata::AllDataTypes) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:56, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:64, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:61, Method returnDate is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:61, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:69, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:66, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:61, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:69, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:66, Method returnDateArray is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:66, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:74, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:66, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxdpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:27, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidTextFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:25, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:25, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:25, Assignment of Text[] from Text with different multiplicity is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:32, Method pushAll is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:33, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:28, Assignment of Text from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:37, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:36, Assignment of Text from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:55, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:63, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:60, Method returnDate is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:60, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:68, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:65, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:60, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:68, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:65, Method returnDateArray is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:65, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:73, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:65, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:25, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:34, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxe5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:33, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidURIFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:22, Assignment of DateTimeTZ from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:101 column:25, Assignment of Date from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:102 column:34, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:103 column:33, Assignment of Date from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:105 column:21, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:106 column:30, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:107 column:29, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:30, Assignment of DateTimeTZ from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:110 column:27, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:111 column:36, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:112 column:35, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:114 column:29, Assignment of Date from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:115 column:38, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:116 column:37, Assignment of Date from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:118 column:26, Assignment of Date from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:119 column:35, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:31, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:120 column:34, Assignment of Date from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:124 column:18, Assignment of Time from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:125 column:26, Assignment of Time from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:126 column:27, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:128 column:34, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:129 column:58, Method returnNumber is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:130 column:66, Method returnNumber is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:131 column:63, Method returnNumber is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:132 column:61, Method returnBomObject is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:133 column:69, Method returnBomObject is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:134 column:66, Method returnBomObject is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:136 column:63, Method returnNumberArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:137 column:71, Method returnNumberArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:138 column:68, Method returnNumberArray is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:139 column:71, Method returnBomObjectArray is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:38, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:140 column:74, Method returnBomObjectArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:141 column:66, Method returnBomObject is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:144 column:27, Assignment of Time from Time[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:145 column:27, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:145 column:27, Assignment of Time[] from Time with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:146 column:34, Method pushAll is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:147 column:35, Assignment of Time from Time[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:149 column:22, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:62, Method returnNumber is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:150 column:31, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:151 column:39, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:152 column:30, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:154 column:34, Assignment of Time from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:155 column:43, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:156 column:42, Assignment of Time from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:158 column:25, Assignment of Time from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:159 column:34, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:70, Method returnNumber is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:160 column:33, Assignment of Time from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:162 column:21, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:163 column:30, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:164 column:29, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:167 column:27, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:168 column:36, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:169 column:35, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:67, Method returnNumber is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:171 column:29, Assignment of Time from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:172 column:38, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:173 column:37, Assignment of Time from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:175 column:26, Assignment of Time from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:176 column:35, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:177 column:34, Assignment of Time from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:65, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:73, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:70, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:67, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:75, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:72, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:75, Method returnBomObjectArray is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:78, Method returnBomObjectArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:70, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:35, Assignment of DateTimeTZ from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:35, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:35, Assignment of DateTimeTZ[] from DateTimeTZ with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:42, Method pushAll is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:43, Assignment of DateTimeTZ from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:26, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:36 column:35, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:43, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:34, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:40 column:38, Assignment of DateTimeTZ from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:47, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:46, Assignment of DateTimeTZ from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:44 column:29, Assignment of DateTimeTZ from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:45 column:38, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:37, Assignment of DateTimeTZ from Boolean is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:25, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:49 column:34, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:50 column:33, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:31, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:54 column:40, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:55 column:39, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:33, Assignment of DateTimeTZ from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:42, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:59 column:41, Assignment of DateTimeTZ from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:30, Assignment of DateTimeTZ from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:39, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:38, Assignment of DateTimeTZ from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:18, Assignment of Date from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:68 column:26, Assignment of Date from String is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:27, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:34, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:72 column:58, Method returnNumber is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:73 column:66, Method returnNumber is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:74 column:63, Method returnNumber is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:75 column:61, Method returnBomObject is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:76 column:69, Method returnBomObject is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:77 column:66, Method returnBomObject is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:79 column:63, Method returnNumberArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:80 column:71, Method returnNumberArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:81 column:68, Method returnNumberArray is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:82 column:71, Method returnBomObjectArray is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:83 column:74, Method returnBomObjectArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:84 column:66, Method returnBomObject is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:87 column:27, Assignment of Date from Date[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:27, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:27, Assignment of Date[] from Date with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:89 column:34, Method pushAll is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:90 column:35, Assignment of Date from Date[] with different multiplicity is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:92 column:22, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:93 column:31, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:94 column:39, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:95 column:30, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:97 column:34, Assignment of Date from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:98 column:43, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:99 column:42, Assignment of Date from Decimal is not supported (InvalidUseCases:InvalidDateFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:37, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:37, Assignment of Text[] from Text with different multiplicity is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:44, Method pushAll is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:45, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:34, Assignment of Text from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:43, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:42, Assignment of Text from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:61, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:69, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:66, Method returnDate is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:66, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:74, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:71, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:66, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:74, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:71, Method returnDateArray is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:71, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:79, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:71, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:31, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:40, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:39, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxepIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:37, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidPerformerFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:29, Assignment of Boolean from String is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:30, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:33, Assignment of Boolean from Boolean[] with different multiplicity is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:33, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:33, Assignment of Boolean[] from Boolean with different multiplicity is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:40, Method pushAll is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:41, Assignment of Boolean from Boolean[] with different multiplicity is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:37, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:59, Method returnDate is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:67, Method returnDate is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:64, Method returnDate is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:64, Method returnBomObject is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:72, Method returnBomObject is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:69, Method returnBomObject is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:64, Method returnDateArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:72, Method returnDateArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:69, Method returnDateArray is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:69, Method returnBomObjectArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:77, Method returnBomObjectArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:69, Method returnBomObject is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:25, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:34, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:42, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:33, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:37, Assignment of Boolean from Decimal is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:46, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:45, Assignment of Boolean from Decimal is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:25, Assignment of Boolean from Date is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:34, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:33, Assignment of Boolean from Date is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:25, Assignment of Boolean from Time is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:34, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:33, Assignment of Boolean from Time is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:29, Assignment of Boolean from DateTimeTZ is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:38, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:37, Assignment of Boolean from DateTimeTZ is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:24, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:33, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:32, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:30, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:39, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:38, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:32, Assignment of Boolean from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:41, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:40, Assignment of Boolean from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:29, Assignment of Boolean from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:38, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:37, Assignment of Boolean from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxeZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:21, Assignment of Boolean from String is not supported (InvalidUseCases:InvalidBooleanFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:50, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:100 column:93, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:101 column:88, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:102 column:96, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:103 column:93, Method returnDateArray is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:104 column:93, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:105 column:101, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:106 column:93, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:109 column:65, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:51, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:110 column:74, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:111 column:73, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:113 column:61, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:114 column:70, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:115 column:69, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:118 column:61, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:119 column:70, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:120 column:69, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:123 column:65, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:124 column:74, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:125 column:73, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:128 column:60, Assignment of Decimal from URI is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:129 column:69, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:75, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:130 column:68, Assignment of Decimal from URI is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:133 column:54, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:134 column:63, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:135 column:62, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:137 column:66, Assignment of Decimal from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:138 column:75, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:139 column:74, Assignment of Decimal from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:75, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:75, Assignment of Decimal[] from Decimal with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:141 column:53, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:142 column:62, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:143 column:61, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:82, Method pushAll is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:83, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:58, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:67, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:66, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:70, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:80, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:88, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:85, Method returnDate is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:85, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:93, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:90, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:85, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:93, Method returnDateArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:90, Method returnDateArray is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:90, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:98, Method returnBomObjectArray is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:90, Method returnBomObject is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:62, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:71, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:70, Assignment of Decimal from Boolean is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:58, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:67, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:66, Assignment of Decimal from Date is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:58, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:67, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:66, Assignment of Decimal from Time is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:62, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:71, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:70, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:57, Assignment of Decimal from URI is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:66, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:65, Assignment of Decimal from URI is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:51, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:60, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:59, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:63, Assignment of Decimal from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:72, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:71, Assignment of Decimal from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:50, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:59, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:58, Assignment of Decimal from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:81 column:45, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:82 column:53, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:83 column:54, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:85 column:81, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:86 column:81, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:86 column:81, Assignment of Decimal[] from Decimal with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:87 column:88, Method pushAll is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:89, Assignment of Decimal from Decimal[] with different multiplicity is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:42, Assignment of Decimal from String is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:90 column:61, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:91 column:70, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:92 column:69, Assignment of Decimal from Text is not supported (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:94 column:73, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:95 column:83, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:96 column:91, Method returnDate is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:97 column:88, Method returnDate is not applicable for the provided argument types (Decimal[]) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:98 column:88, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxf5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:99 column:96, Method returnBomObject is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidNumberBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:30, Assignment of CaseReference<com.example.casedata::Case1> from String is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:31, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:35, Assignment of CaseReference<com.example.casedata::Case1> from CaseReference<com.example.casedata::Case1>[] with different multiplicity is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:35, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:35, Assignment of CaseReference<com.example.casedata::Case1>[] from CaseReference<com.example.casedata::Case1> with different multiplicity is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:42, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:43, Assignment of CaseReference<com.example.casedata::Case1> from CaseReference<com.example.casedata::Case1>[] with different multiplicity is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:38, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:60, Method returnDate is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:68, Method returnDate is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:65, Method returnDate is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>[]) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:65, Method returnBomObject is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:73, Method returnBomObject is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:70, Method returnBomObject is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>[]) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:65, Method returnDateArray is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:73, Method returnDateArray is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:70, Method returnDateArray is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>[]) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:70, Method returnBomObjectArray is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:78, Method returnBomObjectArray is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:70, Method returnBomObject is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>[]) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:26, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:35, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:34, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:36 column:38, Assignment of CaseReference<com.example.casedata::Case1> from Decimal is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:47, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:46, Assignment of CaseReference<com.example.casedata::Case1> from Decimal is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:40 column:29, Assignment of CaseReference<com.example.casedata::Case1> from Boolean is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:38, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:37, Assignment of CaseReference<com.example.casedata::Case1> from Boolean is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:44 column:26, Assignment of CaseReference<com.example.casedata::Case1> from Date is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:45 column:35, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:34, Assignment of CaseReference<com.example.casedata::Case1> from Date is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:49 column:26, Assignment of CaseReference<com.example.casedata::Case1> from Time is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:50 column:35, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:34, Assignment of CaseReference<com.example.casedata::Case1> from Time is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:54 column:30, Assignment of CaseReference<com.example.casedata::Case1> from DateTimeTZ is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:55 column:39, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:38, Assignment of CaseReference<com.example.casedata::Case1> from DateTimeTZ is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:59 column:25, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:60 column:34, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:33, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:64 column:31, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:40, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:39, Assignment of CaseReference<com.example.casedata::Case1> from Text is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:68 column:33, Assignment of CaseReference<com.example.casedata::Case1> from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:42, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:41, Assignment of CaseReference<com.example.casedata::Case1> from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:22, Assignment of CaseReference<com.example.casedata::Case1> from String is not supported (InvalidUseCases:InvalidCaseRefFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:51, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:51, Assignment of Text[] from Text with different multiplicity is not supported (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:58, Method pushAll is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:59, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:50, Property case1Ref is invalid for the current context (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:59, Property case1Ref is invalid for the current context (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:58, Property case1Ref is invalid for the current context (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:62, Property bomAllTypes is invalid for the current context (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:68, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:76, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:73, Method returnDate is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:73, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:81, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:78, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:73, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:81, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:78, Method returnDateArray is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:78, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:86, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:78, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:51, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:50, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxfpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:58, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidTextBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:33, Assignment of com.example.casedata::Case1 from String is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:34, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:36, Assignment of com.example.casedata::Case1 from com.example.casedata::AllDataTypes is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:41, Assignment of com.example.casedata::Case1 from com.example.casedata::Case1[] with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:41, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:41, Assignment of com.example.casedata::Case1[] from com.example.casedata::Case1 with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:48, Method pushAll is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:49, Assignment of com.example.casedata::Case1 from com.example.casedata::Case1[] with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:29, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:38, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:37, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:41, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:63, Method returnDate is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:71, Method returnDate is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:68, Method returnDate is not applicable for the provided argument types (com.example.casedata::Case1[]) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:68, Method returnBomObject is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:76, Method returnBomObject is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:73, Method returnBomObject is not applicable for the provided argument types (com.example.casedata::Case1[]) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:68, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:76, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:73, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Case1[]) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:73, Method returnBomObjectArray is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:81, Method returnBomObjectArray is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:73, Method returnBomObject is not applicable for the provided argument types (com.example.casedata::Case1[]) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:41, Assignment of com.example.casedata::Case1 from Decimal is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:50, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:49, Assignment of com.example.casedata::Case1 from Decimal is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:32, Assignment of com.example.casedata::Case1 from Boolean is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:41, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:40, Assignment of com.example.casedata::Case1 from Boolean is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:45 column:29, Assignment of com.example.casedata::Case1 from Date is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:38, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:37, Assignment of com.example.casedata::Case1 from Date is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:50 column:29, Assignment of com.example.casedata::Case1 from Time is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:38, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:37, Assignment of com.example.casedata::Case1 from Time is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:55 column:33, Assignment of com.example.casedata::Case1 from DateTimeTZ is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:42, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:41, Assignment of com.example.casedata::Case1 from DateTimeTZ is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:60 column:28, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:37, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:36, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:34, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:43, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:42, Assignment of com.example.casedata::Case1 from Text is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:33, Assignment of com.example.casedata::Case1 from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:42, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:41, Assignment of com.example.casedata::Case1 from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxfZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:25, Assignment of com.example.casedata::Case1 from String is not supported (InvalidUseCases:InvalidBOMObjectFieldUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:33, Assignment of CaseReference<com.example.casedata::Case1> from com.example.casedata::Case1 is not supported (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:33, Assignment of com.example.casedata::Case1 from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:47, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.casedata::Case2>[]) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:45, Method pushAll is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:44, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case2>[]) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:47, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case2>) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:42, Method push is not applicable for the provided argument types (com.example.casedata::Case1) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:47, Method push is not applicable for the provided argument types (com.example.casedata::Case1[]) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:43, Method pushAll is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:40, Method push is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:43, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:85, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:85, Method navigateAll is not applicable for the provided argument types (CaseReference<com.example.casedata::Case2>[],String,Integer,Integer) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:89, Method navigateAll is not applicable for the provided argument types (CaseReference<com.example.casedata::Case2>[],String,Integer,Integer) (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:30, Assignment of CaseReference<com.example.casedata::Case1> from CaseReference<com.example.casedata::Case2> is not supported (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxg5IhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:85, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidCaseRefBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:34, Assignment of DateTimeTZ from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:101 column:50, Assignment of Date from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:102 column:59, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:103 column:58, Assignment of Date from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:105 column:45, Assignment of Date from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:106 column:54, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:107 column:53, Assignment of Date from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:42, Assignment of DateTimeTZ from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:110 column:39, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:111 column:48, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:112 column:47, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:114 column:51, Assignment of Date from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:115 column:60, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:116 column:59, Assignment of Date from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:118 column:38, Assignment of Date from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:119 column:47, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:43, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:120 column:46, Assignment of Date from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:124 column:30, Assignment of Time from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:125 column:38, Assignment of Time from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:126 column:39, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:128 column:58, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:129 column:70, Method returnNumber is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:130 column:78, Method returnNumber is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:131 column:75, Method returnNumber is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:132 column:73, Method returnBomObject is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:133 column:81, Method returnBomObject is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:134 column:78, Method returnBomObject is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:136 column:75, Method returnNumberArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:137 column:83, Method returnNumberArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:138 column:80, Method returnNumberArray is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:139 column:83, Method returnBomObjectArray is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:62, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:140 column:86, Method returnBomObjectArray is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:141 column:78, Method returnBomObject is not applicable for the provided argument types (Time[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:144 column:51, Assignment of Time from Time[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:145 column:51, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:145 column:51, Assignment of Time[] from Time with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:146 column:58, Method pushAll is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:147 column:59, Assignment of Time from Time[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:149 column:46, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:74, Method returnNumber is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:150 column:55, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:151 column:63, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:152 column:54, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:154 column:58, Assignment of Time from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:155 column:67, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:156 column:66, Assignment of Time from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:158 column:50, Assignment of Time from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:159 column:59, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:82, Method returnNumber is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:160 column:58, Assignment of Time from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:162 column:45, Assignment of Time from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:163 column:54, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:164 column:53, Assignment of Time from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:167 column:39, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:168 column:48, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:169 column:47, Assignment of Time from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:79, Method returnNumber is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:171 column:51, Assignment of Time from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:172 column:60, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:173 column:59, Assignment of Time from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:175 column:38, Assignment of Time from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:176 column:47, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:177 column:46, Assignment of Time from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:77, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:85, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:82, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:79, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:87, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:84, Method returnNumberArray is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:87, Method returnBomObjectArray is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:90, Method returnBomObjectArray is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:82, Method returnBomObject is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:59, Assignment of DateTimeTZ from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:59, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:59, Assignment of DateTimeTZ[] from DateTimeTZ with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:66, Method pushAll is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:67, Assignment of DateTimeTZ from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:50, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:36 column:59, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:67, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:58, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:40 column:62, Assignment of DateTimeTZ from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:71, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:70, Assignment of DateTimeTZ from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:44 column:54, Assignment of DateTimeTZ from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:45 column:63, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:62, Assignment of DateTimeTZ from Boolean is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:49, Assignment of DateTimeTZ from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:49 column:58, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:50 column:57, Assignment of DateTimeTZ from URI is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:43, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:54 column:52, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:55 column:51, Assignment of DateTimeTZ from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:55, Assignment of DateTimeTZ from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:64, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:59 column:63, Assignment of DateTimeTZ from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:42, Assignment of DateTimeTZ from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:51, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:50, Assignment of DateTimeTZ from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:30, Assignment of Date from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:68 column:38, Assignment of Date from String is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:39, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:58, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:72 column:70, Method returnNumber is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:73 column:78, Method returnNumber is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:74 column:75, Method returnNumber is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:75 column:73, Method returnBomObject is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:76 column:81, Method returnBomObject is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:77 column:78, Method returnBomObject is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:79 column:75, Method returnNumberArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:80 column:83, Method returnNumberArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:81 column:80, Method returnNumberArray is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:82 column:83, Method returnBomObjectArray is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:83 column:86, Method returnBomObjectArray is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:84 column:78, Method returnBomObject is not applicable for the provided argument types (Date[]) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:87 column:51, Assignment of Date from Date[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:51, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:88 column:51, Assignment of Date[] from Date with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:89 column:58, Method pushAll is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:90 column:59, Assignment of Date from Date[] with different multiplicity is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:92 column:46, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:93 column:55, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:94 column:63, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:95 column:54, Assignment of Date from Text is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:97 column:58, Assignment of Date from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:98 column:67, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:99 column:66, Assignment of Date from Decimal is not supported (InvalidUseCases:InvalidDateBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:49, Assignment of URI from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:49, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:49, Assignment of Text[] from URI with different multiplicity is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:56, Method pushAll is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:57, Assignment of Text from Text[] with different multiplicity is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:50, Assignment of URI from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:59, Method push is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:58, Assignment of Text from com.example.casedata::Class1 is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:67, Method returnDate is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:75, Method returnDate is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:72, Method returnDate is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:72, Method returnBomObject is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:80, Method returnBomObject is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:77, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:72, Method returnDateArray is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:80, Method returnDateArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:77, Method returnDateArray is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:77, Method returnBomObjectArray is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:85, Method returnBomObjectArray is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:77, Method returnBomObject is not applicable for the provided argument types (Text[]) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:37, Assignment of URI from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:46, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgpIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:45, Assignment of Text from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidURIBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:42, Assignment of Boolean from String is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:43, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:13 column:59, Assignment of Boolean from Boolean[] with different multiplicity is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:59, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:59, Assignment of Boolean[] from Boolean with different multiplicity is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:66, Method pushAll is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:67, Assignment of Boolean from Boolean[] with different multiplicity is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:18 column:62, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:72, Method returnDate is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:80, Method returnDate is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:77, Method returnDate is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:22 column:77, Method returnBomObject is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:85, Method returnBomObject is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:82, Method returnBomObject is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:77, Method returnDateArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:85, Method returnDateArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:82, Method returnDateArray is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:82, Method returnBomObjectArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:90, Method returnBomObjectArray is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:82, Method returnBomObject is not applicable for the provided argument types (Boolean[]) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:50, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:59, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:67, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:58, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:62, Assignment of Boolean from Decimal is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:71, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:70, Assignment of Boolean from Decimal is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:50, Assignment of Boolean from Date is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:59, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:58, Assignment of Boolean from Date is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:50, Assignment of Boolean from Time is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:59, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:48 column:58, Assignment of Boolean from Time is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:54, Assignment of Boolean from DateTimeTZ is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:63, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:53 column:62, Assignment of Boolean from DateTimeTZ is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:49, Assignment of Boolean from URI is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:58, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:58 column:57, Assignment of Boolean from URI is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:43, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:52, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:63 column:51, Assignment of Boolean from Text is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:57, Property case1Object is invalid for the current context (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:66, Property case1Object is invalid for the current context (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:65, Property case1Object is invalid for the current context (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:42, Assignment of Boolean from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:51, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:50, Assignment of Boolean from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:34, Assignment of Boolean from String is not supported (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:57, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_ascxgZIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:65, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases:InvalidBooleanBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:10 column:43, Assignment of com.example.casedata::Class1 from String is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:11 column:44, Method push is not applicable for the provided argument types (String) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:12 column:46, Assignment of com.example.casedata::Class1 from com.example.casedata::AllDataTypes is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:14 column:61, Assignment of com.example.casedata::Class1 from com.example.casedata::Class1[] with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:61, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:15 column:61, Assignment of com.example.casedata::Class1[] from com.example.casedata::Class1 with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:16 column:68, Method pushAll is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:17 column:69, Assignment of com.example.casedata::Class1 from com.example.casedata::Class1[] with different multiplicity is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:19 column:51, Assignment of com.example.casedata::Class1 from Text is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:20 column:60, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:21 column:59, Assignment of com.example.casedata::Class1 from Text is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:23 column:63, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:24 column:73, Method returnDate is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:25 column:81, Method returnDate is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:26 column:78, Method returnDate is not applicable for the provided argument types (com.example.casedata::Class1[]) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:27 column:80, Method returnCase1Object is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:28 column:88, Method returnCase1Object is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:29 column:85, Method returnCase1Object is not applicable for the provided argument types (com.example.casedata::Class1[]) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:30 column:78, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:31 column:86, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:32 column:83, Method returnDateArray is not applicable for the provided argument types (com.example.casedata::Class1[]) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:33 column:83, Method returnBomObjectArray is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:34 column:91, Method returnBomObjectArray is not applicable for the provided argument types (com.example.casedata::Class1) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:35 column:83, Method returnBomObject is not applicable for the provided argument types (com.example.casedata::Class1[]) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:37 column:63, Assignment of com.example.casedata::Class1 from Decimal is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:38 column:72, Method push is not applicable for the provided argument types (Decimal) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:39 column:71, Assignment of com.example.casedata::Class1 from Decimal is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:41 column:55, Assignment of com.example.casedata::Class1 from Boolean is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:42 column:64, Method push is not applicable for the provided argument types (Boolean) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:43 column:63, Assignment of com.example.casedata::Class1 from Boolean is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:45 column:51, Assignment of com.example.casedata::Class1 from Date is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:46 column:60, Method push is not applicable for the provided argument types (Date) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:47 column:59, Assignment of com.example.casedata::Class1 from Date is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:50 column:51, Assignment of com.example.casedata::Class1 from Time is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:51 column:60, Method push is not applicable for the provided argument types (Time) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:52 column:59, Assignment of com.example.casedata::Class1 from Time is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:55 column:55, Assignment of com.example.casedata::Class1 from DateTimeTZ is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:56 column:64, Method push is not applicable for the provided argument types (DateTimeTZ) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:57 column:63, Assignment of com.example.casedata::Class1 from DateTimeTZ is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:60 column:50, Assignment of com.example.casedata::Class1 from URI is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:61 column:59, Method push is not applicable for the provided argument types (URI) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:62 column:58, Assignment of com.example.casedata::Class1 from URI is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:65 column:44, Assignment of com.example.casedata::Class1 from Text is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:66 column:53, Method push is not applicable for the provided argument types (Text) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:67 column:52, Assignment of com.example.casedata::Class1 from Text is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:69 column:43, Assignment of com.example.casedata::Class1 from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:70 column:52, Method push is not applicable for the provided argument types (CaseReference<com.example.casedata::Case1>) (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:71 column:51, Assignment of com.example.casedata::Class1 from CaseReference<com.example.casedata::Case1> is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo(
						"/ScriptsWithAllDataTypeAssignments/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_ascxhJIhEe-2GuL-Yhj3Jg", //$NON-NLS-1$
						"BPM  : At Line:9 column:35, Assignment of com.example.casedata::Class1 from String is not supported (InvalidUseCases:InvalidBOMObjectBOMAttrUseCases)", //$NON-NLS-1$
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
		return "AceAllDataTypeAssignments"; //$NON-NLS-1$
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
