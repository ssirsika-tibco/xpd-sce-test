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
 * Sid ACE-8226 Tests for PSL functions with BOM and Case Reference parameters/returns.
 * 
 * The introduction of this capability introduced, for the first time, the requirement to validation SPECIFIC TYPES of
 * BOM and CaseRef parameter passing and return assignment. Previously all BOM / CaseRef related param passing and
 * returns have been treated as 'any BOM / any Case Ref' generic-type functions (such bpm.scriptUtil.copy() and
 * bpm.caseData.read()).
 * 
 * Now we need to deal with...
 * <li>Checking valid type passed for PSL Function BOM param from various sources (Process fields, BOM attributes, PSL
 * function parameters, returns from other PSL functions)</li>
 * <li>Checking valid type passed for PSL Function CaseRef param from various sources (Process fields, BOM attributes,
 * PSL function parameters, returns from other PSL functions)</li>
 * <li>Checking valid assignment from BOM class PSL function return to various targets (Process fields, BOM attributes,
 * PSL function parameters, functions with generic 'any BOM' params)</li>
 * <li>Checking valid assignment from CaseRef PSL function return to various targets (Process fields, BOM attributes,
 * PSL function parameters, functions with generic 'any case-ref' params)</li>
 * <li>Checking return statement validation of correct BOM class and CaseRef types.</li>
 * 
 * And regression testing that
 * <li>Generic 'any BOM class' methods (params/returns) work for all valid sources (Process fields, BOM attributes, PSL
 * function parameters, functions with generic 'any case-ref' params)</li>
 * <li>Generic 'any CaseRef' methods (params/returns) work for all valid sources (Process fields, BOM attributes, PSL
 * function parameters, functions with generic 'any case-ref' params)</li>
 * <li>.... Such as bpm.scriptUtil.xxx() and bpm.caseData.xxx()</li>
 * <li>Ensuring that BOM Case Class and CaseRef's are not treated as equivalents.</li>
 * <li></li>
 *
 * @author aallway
 * @since May-24
 */
public class PSLFunctionTypeCheckingValidationTest extends AbstractN2BaseValidationTest
{


	/**
	 * Tests for invalid and valid data type assignments related to PSL functions parameters and returns and also
	 * regression testing of other basic assignment / method param tyep checking.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	public void testPslFunctionTypeCheckingValidationTesT() throws Exception
	{
		/*
		 * Import project with invalid files
		 */
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
				new String[]{"resources/PSLFunctionTypeCheckingValidationTest/Data/",
						"resources/PSLFunctionTypeCheckingValidationTest/Utilities/",
						"resources/PSLFunctionTypeCheckingValidationTest/TypeCheckingProcesses/"},
				new String[]{"Data", "Utilities", "TypeCheckingProcesses"});
		try
		{
			buildAndWait();

			/* Check correct problem markers are raised. */
			doTestValidations();

			/* Check that resources that should be clean are free of problem markers. */
			assertFalse("Utilities/Process Script Libraries/ValidUseCases.psl" //$NON-NLS-1$
					+ " ScriptLibrary should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot()
									.getFile(new Path("Utilities/Process Script Libraries/InalidUseCases.psl")), //$NON-NLS-1$
							true, "pslFunctionTypeCheckingValidationTest"));

			assertFalse("Utilities/Process Script Libraries/UtilitiesLibrary.psl" //$NON-NLS-1$
					+ " ScriptLibrary should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot().getFile(
									new Path("Utilities/Process Script Libraries/UtilitiesLibrary.psl")), //$NON-NLS-1$
							true, "pslFunctionTypeCheckingValidationTest"));

			assertFalse("TypeCheckingProcesses/Process Packages/ValidUseCases.xpdl" //$NON-NLS-1$
					+ " ScriptLibrary should not have any ERROR level problem markers", //$NON-NLS-1$
					TestUtil.hasErrorProblemMarker(
							ResourcesPlugin.getWorkspace().getRoot().getFile(
									new Path("TypeCheckingProcesses/Process Packages/ValidUseCases.xpdl")), //$NON-NLS-1$
							true, "pslFunctionTypeCheckingValidationTest"));

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

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:10 column:140, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2,com.example.data::Case1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:12 column:146, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.another::Case1,com.example.another::Case2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:14 column:115, Assignment of com.example.data.Case2 from com.example.data.Case1 is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:17 column:99, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2,com.example.data::Class1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:19 column:106, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:21 column:105, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.another::Class1,com.example.data::Case2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:23 column:143, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2,com.example.data::Class1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:25 column:149, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.another::Class1,com.example.another::Class2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:28 column:119, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:30 column:134, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:33 column:96, Method caseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case2>,CaseReference<com.example.data::Case1>) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:35 column:115, Assignment of CaseReference<com.example.data::Case2> from CaseReference<com.example.data::Case1> is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:37 column:115, Assignment of com.example.data.Case1 from CaseReference<com.example.data::Case1> is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:39 column:134, Method caseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case1>,CaseReference<com.example.data::Case1>) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:42 column:99, Method primitiveTypes is not applicable for the provided argument types (Text,Text,DateTimeTZ) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:44 column:118, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:47 column:111, Method arrayBomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2[],com.example.data::Case1[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:49 column:101, Method arrayBomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case1,com.example.data::Case2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:51 column:142, Method pushAll is not applicable for the provided argument types (com.example.data::Case1[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:53 column:130, Assignment of com.example.data.Case1 from com.example.data.Case1[] with different multiplicity is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:56 column:114, Method arrayBomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2[],com.example.data::Class1[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:58 column:104, Method arrayBomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:6 column:96, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2,com.example.data::Case1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:60 column:146, Method pushAll is not applicable for the provided argument types (com.example.data::Class1[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:62 column:134, Assignment of com.example.data.Class1 from com.example.data.Class1[] with different multiplicity is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:64 column:85, Method pushAll is not applicable for the provided argument types (com.example.data::Class1) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:67 column:111, Method arrayCaseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case2>[],CaseReference<com.example.data::Case1>[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:69 column:142, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:71 column:142, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:73 column:144, Method arrayCaseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[],CaseReference<com.example.data::Case1>) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:76 column:119, Method arrayPrimitiveTypes is not applicable for the provided argument types (Text[],Text[],DateTimeTZ[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:78 column:150, Method pushAll is not applicable for the provided argument types (DateTimeTZ[]) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:8 column:103, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.another::Case1,com.example.data::Case2) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:80 column:140, Assignment of DateTimeTZ from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_D7dJYRawEe-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:82 column:82, Method pushAll is not applicable for the provided argument types (Decimal) (InvalidUseCasesProcess:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:10 column:56, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case2>[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:11 column:45, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:14 column:88, Method pushAll is not applicable for the provided argument types (CaseReference[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:14 column:88, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:15 column:96, Assignment of com.example.data.Case1 from CaseReference is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:15 column:96, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:16 column:120, Method pushAll is not applicable for the provided argument types (CaseReference[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:16 column:120, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:17 column:124, Method pushAll is not applicable for the provided argument types (CaseReference[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:17 column:124, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:18 column:55, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:19 column:75, Method pushAll is not applicable for the provided argument types (Object[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:20 column:64, Assignment of com.example.data.Class1 from Object[] with different multiplicity is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:21 column:44, Method readAll is not applicable for the provided argument types (com.example.data::Case1[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:24 column:58, Method copy is not applicable for the provided argument types (Text[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:25 column:58, Method copy is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:26 column:60, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:27 column:75, Method copyAll is not applicable for the provided argument types (com.example.data::Case1) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:28 column:68, Assignment of com.example.data.Case1 from Object[] with different multiplicity is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:29 column:68, Method copyAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:30 column:80, Method pushAll is not applicable for the provided argument types (Object[]) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:31 column:90, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:31 column:90, The Class Type Name parameter must identify a valid Class (<package name>.<class name>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:32 column:65, Assignment of CaseReference<com.example.data::Case1> from String is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:35 column:45, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:36 column:63, Method charAt is invalid for the current context (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:40 column:157, Method findAll is not applicable for the provided argument types (String,CaseReference<com.example.data::Case1>,com.example.data::Class1) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:42 column:89, Method read is not applicable for the provided argument types (Text) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:43 column:93, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:46 column:58, Assignment of CaseReference<com.example.data::Case1> from com.example.data.Case1 is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:47 column:58, Assignment of com.example.data.Case2 from com.example.data.Case1 is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:48 column:60, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:6 column:35, Assignment of DateTimeTZ from Text is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:7 column:39, Assignment of CaseReference<com.example.data::Case1> from CaseReference<com.example.data::Case2> is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:8 column:49, Arrays cannot be directly assigned. Use 'array.length = 0' to clear an array followed by 'array.pushAll(sourceArray)' to copy all array entries (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:8 column:49, Assignment of CaseReference<com.example.data::Case1>[] from CaseReference<com.example.data::Case2>[] is not supported (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:9 column:51, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>) (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:24 column:58, Unable to determine type, operation may not be supported and content assist will not be available (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:25 column:58, Unable to determine type, operation may not be supported and content assist will not be available (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:36 column:63, Unable to determine type, operation may not be supported and content assist will not be available (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/TypeCheckingProcesses/Process Packages/InvalidUseCases.xpdl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_nvuu8Ra5Ee-CIqEineWfWg", //$NON-NLS-1$
						"BPM  : At Line:42 column:89, Unable to determine type, operation may not be supported and content assist will not be available (invalidPslFunctionGeneralRegressionTests:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:10 column:140, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2,com.example.data::Case1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:12 column:146, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.another::Case1,com.example.another::Case2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:14 column:100, Assignment of com.example.data.Case2 from com.example.data.Case1 is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:17 column:89, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2,com.example.data::Class1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:19 column:96, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:95, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.another::Class1,com.example.data::Case2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:23 column:143, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2,com.example.data::Class1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:25 column:149, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.another::Class1,com.example.another::Class2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:28 column:104, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:30 column:129, Method bomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:33 column:86, Method caseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case2>,CaseReference<com.example.data::Case1>) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:35 column:100, Assignment of CaseReference<com.example.data::Case2> from CaseReference<com.example.data::Case1> is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:37 column:100, Assignment of com.example.data.Case1 from CaseReference<com.example.data::Case1> is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:39 column:129, Method caseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case1>,CaseReference<com.example.data::Case1>) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:42 column:84, Method primitiveTypes is not applicable for the provided argument types (String,String,DateTime) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:44 column:98, Assignment of Decimal from DateTimeTZ is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:47 column:101, Method arrayBomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2[],com.example.data::Case1[]) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:49 column:91, Method arrayBomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case1,com.example.data::Case2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:51 column:120, Assignment of com.example.data.Case2[] from com.example.data.Case1[] is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:53 column:115, Assignment of com.example.data.Case1 from com.example.data.Case1[] with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:56 column:104, Method arrayBomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class2[],com.example.data::Class1[]) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:58 column:94, Method arrayBomClassParamAndReturn is not applicable for the provided argument types (com.example.data::Class1,com.example.data::Class2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:86, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.data::Case2,com.example.data::Case1) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:60 column:124, Assignment of com.example.data.Class2[] from com.example.data.Class1[] is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:62 column:119, Assignment of com.example.data.Class1 from com.example.data.Class1[] with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:64 column:73, Assignment of com.example.data.Class1[] from com.example.data.Class1 with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:67 column:101, Method arrayCaseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case2>[],CaseReference<com.example.data::Case1>[]) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:69 column:120, Assignment of CaseReference<com.example.data::Case2>[] from CaseReference<com.example.data::Case1>[] is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:71 column:120, Assignment of com.example.data.Case2[] from CaseReference<com.example.data::Case1>[] is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:73 column:139, Method arrayCaseRefParamAndReturn is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[],CaseReference<com.example.data::Case1>) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:76 column:104, Method arrayPrimitiveTypes is not applicable for the provided argument types (String[],String[],DateTime[]) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:78 column:123, Assignment of Decimal[] from DateTimeTZ[] is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:8 column:93, Method bomCaseParamAndReturn is not applicable for the provided argument types (com.example.another::Case1,com.example.data::Case2) (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:80 column:120, Assignment of DateTime from DateTimeTZ[] with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gIhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:82 column:70, Assignment of Decimal[] from Decimal with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionCallFromPslFunctionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:10 column:46, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case2>[]) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:11 column:35, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:14 column:76, Assignment of com.example.data.Case1[] from CaseReference[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:14 column:76, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:15 column:86, Assignment of com.example.data.Case1 from CaseReference is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:15 column:86, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:98, Assignment of com.example.data.Case1[] from CaseReference[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:98, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:17 column:102, Assignment of com.example.data.Case1[] from CaseReference[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:17 column:102, The Case Type Name parameter must identify a valid Case Class (<package name>.<class name>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:18 column:45, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:19 column:58, Assignment of CaseReference<com.example.data::Case1>[] from Object[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:20 column:54, Assignment of com.example.data.Class1 from Object[] with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:39, Method readAll is not applicable for the provided argument types (com.example.data::Case1[]) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:24 column:48, Method copy is not applicable for the provided argument types (String[]) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:25 column:48, Method copy is not applicable for the provided argument types (CaseReference<com.example.data::Case1>[]) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:26 column:50, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:27 column:58, Method copyAll is not applicable for the provided argument types (com.example.data::Case1) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:28 column:58, Assignment of com.example.data.Case1 from Object[] with different multiplicity is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:29 column:51, Method copyAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:30 column:63, Assignment of CaseReference<com.example.data::Case1>[] from Object[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:31 column:80, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:31 column:80, The Class Type Name parameter must identify a valid Class (<package name>.<class name>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:32 column:55, Assignment of CaseReference<com.example.data::Case1> from String is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:35 column:35, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:36 column:48, Method charAt is invalid for the current context (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:40 column:160, Method findAll is not applicable for the provided argument types (String,CaseReference<com.example.data::Case1>,com.example.data::Class1) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:42 column:84, Method read is not applicable for the provided argument types (Text) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:43 column:88, Assignment of CaseReference<com.example.data::Case1> from Object is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:46 column:53, Assignment of CaseReference<com.example.data::Case1> from com.example.data.Case1 is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:47 column:53, Assignment of com.example.data.Case2 from com.example.data.Case1 is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:48 column:55, Assignment of com.example.data.Class2 from com.example.data.Class1 is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:25, Assignment of DateTime from String is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:7 column:29, Assignment of CaseReference<com.example.data::Case1> from CaseReference<com.example.data::Case2> is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:8 column:39, Assignment of CaseReference<com.example.data::Case1>[] from CaseReference<com.example.data::Case2>[] is not supported (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:9 column:41, Method pushAll is not applicable for the provided argument types (CaseReference<com.example.data::Case1>) (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:24 column:48, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:25 column:48, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:27 column:58, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:29 column:51, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:36 column:48, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.warning.validateScriptTask", //$NON-NLS-1$
						"_-m6gNhOaEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:42 column:84, Unable to determine type, operation may not be supported and content assist will not be available (InvalidUseCases.psl:invalidPslFunctionGeneralRegressionTests)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_2GUrwBaSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:4 column:1, Return type 'CaseReference<com.example.data::Case1>' with differing multiplicities is not compatible with the function return type 'CaseReference<com.example.data::Case1>[]'. (InvalidUseCases.psl:invalidCaseRefArrayReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_2GUrwBaSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:8 column:1, Return type 'CaseReference<com.example.another::Case1>[]' is not compatible with the function return type 'CaseReference<com.example.data::Case1>[]'. (InvalidUseCases.psl:invalidCaseRefArrayReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:11 column:1, Return type 'DateTime[]' with differing multiplicities is not compatible with the function return type 'DateTime'. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:1, Return type 'Number' is not compatible with the function return type 'DateTime'. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:1, Return type 'CaseReference<com.example.data::Case1>' is not compatible with the function return type 'DateTime'. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:26 column:1, Return type 'DateTimeTZ[]' with differing multiplicities is not compatible with the function return type 'DateTime'. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:30 column:1, Return statements without a value are not supported for functions that return a non-void type. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjfhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:1, Return type 'String' is not compatible with the function return type 'DateTime'. (InvalidUseCases.psl:invalidPrimitiveTypeReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:11 column:1, Return type 'com.example.data.Class1[]' with differing multiplicities is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:1, Return type 'String' is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:1, Return type 'String' is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:26 column:1, Return type 'com.example.data.Case1' is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:31 column:1, Return type 'com.example.data.Class1[]' with differing multiplicities is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:35 column:1, Return statements without a value are not supported for functions that return a non-void type. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjghRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:1, Return type 'com.example.data.Case1' is not compatible with the function return type 'com.example.data.Class1'. (InvalidUseCases.psl:invalidBomClassReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:11 column:1, Return type 'CaseReference<com.example.data::Case1>[]' with differing multiplicities is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:1, Return type 'CaseReference<com.example.another::Case1>' is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:1, Return type 'CaseReference<com.example.data::Case2>' is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:26 column:1, Return type 'String' is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:31 column:1, Return type 'String' is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:35 column:1, Return statements without a value are not supported for functions that return a non-void type. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjhhRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:1, Return type 'CaseReference<com.example.data::Case2>' is not compatible with the function return type 'CaseReference<com.example.data::Case1>'. (InvalidUseCases.psl:invalidCaseRefReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:11 column:1, Return type 'com.example.data.Class1' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:1, Return type 'com.example.data.Case1[]' with differing multiplicities is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:21 column:1, Return type 'com.example.another.Case1' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:26 column:1, Return type 'CaseReference<com.example.data::Case1>' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:31 column:1, Return type 'String' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:36 column:1, Return type 'String' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:46 column:1, Return type 'com.example.data.Class1' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:51 column:1, Return type 'com.example.data.Case1[]' with differing multiplicities is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:55 column:1, Return statements without a value are not supported for functions that return a non-void type. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_66sjiRRSEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:6 column:1, Return type 'com.example.data.Case2' is not compatible with the function return type 'com.example.data.Case1'. (InvalidUseCases.psl:invalidBomCaseReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_GVhbEhaPEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:12 column:1, Return statements with value are not supported for functions with a Void return type. (InvalidUseCases.psl:invalidReturnVoid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_GVhbEhaPEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:16 column:1, Return statements with value are not supported for functions with a Void return type. (InvalidUseCases.psl:invalidReturnVoid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_GVhbEhaPEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:17 column:1, Return statements with value are not supported for functions with a Void return type. (InvalidUseCases.psl:invalidReturnVoid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_GVhbEhaPEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:4 column:1, Return statements with value are not supported for functions with a Void return type. (InvalidUseCases.psl:invalidReturnVoid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_GVhbEhaPEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:8 column:1, Return statements with value are not supported for functions with a Void return type. (InvalidUseCases.psl:invalidReturnVoid)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_YmZ_MxaUEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:14 column:1, Return type 'com.example.data.Case1' with differing multiplicities is not compatible with the function return type 'com.example.data.Case1[]'. (InvalidUseCases.psl:invalidBomCaseArrayReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_YmZ_MxaUEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:18 column:1, Return type 'CaseReference<com.example.data::Case1>[]' is not compatible with the function return type 'com.example.data.Case1[]'. (InvalidUseCases.psl:invalidBomCaseArrayReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_YmZ_MxaUEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:4 column:1, Return type 'com.example.data.Case2[]' is not compatible with the function return type 'com.example.data.Case1[]'. (InvalidUseCases.psl:invalidBomCaseArrayReturn)", //$NON-NLS-1$
						""), //$NON-NLS-1$

				new ValidationsTestProblemMarkerInfo("/Utilities/Process Script Libraries/InvalidUseCases.psl", //$NON-NLS-1$
						"bx.validateScriptTask", //$NON-NLS-1$
						"_YmZ_MxaUEe-fuvwZ8PuNhw", //$NON-NLS-1$
						"BPM  : At Line:9 column:1, Return type 'String' with differing multiplicities is not compatible with the function return type 'com.example.data.Case1[]'. (InvalidUseCases.psl:invalidBomCaseArrayReturn)", //$NON-NLS-1$
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
		return "PSLFunctionTypeCheckingValidationTest"; //$NON-NLS-1$
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
