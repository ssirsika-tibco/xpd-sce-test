/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.javascript;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Test valid Case Data Tasks.
 *
 * @author nwilson
 */
public class CaseDataTaskInvalidTest extends AbstractN2BaseValidationTest {

    public CaseDataTaskInvalidTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("CaseDataTaskInvalidTest"); //$NON-NLS-1$
        /*
         * make sure BOM project is configured as Business Data project to avoid
         * getting unexpected problem markers unrelated to this test.
         */
        BOMUtils.setAsBusinessDataProject(project); // $NON-NLS-1$
        // and make sure we have a Forms special folder
        SpecialFolderUtil.getCreateSpecialFolderOfKind(project, "forms", "Forms"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * AceBomMigrationValidationsTest
     * 
     * @throws Exception
     */
    public void testAceBomMigrationValidationsTest() throws Exception {
        /*
         * Before checking that the expected problems have been raised, make
         * sure that the BOMs that are expected to be ok have no problems.
         */
        IFile okFile1 = getTestFile("data.bom"); //$NON-NLS-1$

        List<ValidationsTestProblemMarkerInfo> problemMarkers = getProblemMarkers(okFile1);
        String the1stProblem = null;
        if (!problemMarkers.isEmpty()) {
            ValidationsTestProblemMarkerInfo markerInfo = problemMarkers.get(0);
            the1stProblem = markerInfo.getProblemId() + "::" //$NON-NLS-1$
                    + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                    + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                    + markerInfo.getProblemText() + "\"\n"; //$NON-NLS-1$
        }
        assertTrue("data.bom: should not have problem markers but has at least one:\n" //$NON-NLS-1$
                + the1stProblem, the1stProblem == null);

        IFile okFile2 = getTestFile("CaseDataTaskInvalidTest.xpdl"); //$NON-NLS-1$

        problemMarkers = getProblemMarkers(okFile2);
        the1stProblem = null;
        if (!problemMarkers.isEmpty()) {
            ValidationsTestProblemMarkerInfo markerInfo = problemMarkers.get(0);
            the1stProblem = markerInfo.getProblemId() + "::" //$NON-NLS-1$
                    + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                    + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                    + markerInfo.getProblemText() + "\"\n"; //$NON-NLS-1$
        }
        assertTrue("CaseDataTaskTest.xpdl: should have 1 problem marker but has at least one:\n", //$NON-NLS-1$
                the1stProblem != null);

        /*
         * Then test the expected problems are raised
         */
        doTestValidations();
    }

    @Override
    protected String getTestName() {
        return "CaseDataTaskInvalidTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo("resources/CaseDataTaskTest", //$NON-NLS-1$
                "CaseDataTaskInvalidTest/Business Objects{bom}/data.bom"), //$NON-NLS-1$
                new TestResourceInfo("resources/CaseDataTaskTest", //$NON-NLS-1$
                        "CaseDataTaskInvalidTest/Process Packages{processes}/CaseDataTaskInvalidTest.xpdl") //$NON-NLS-1$
        };
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        return new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : At Line:1 column:48, Method navigateAll is not applicable for the provided argument types (Text,String,Integer,Integer) (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : At Line:2 column:58, Method navigateByCriteria is not applicable for the provided argument types (Text,String,String,Integer,Integer) (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : At Line:3 column:62, Method navigateBySimpleSearch is not applicable for the provided argument types (Text,String,String,Integer,Integer) (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : At Line:4 column:34, Method read is not applicable for the provided argument types (Text) (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
						"BPM  : At Line:5 column:42, Method readAll is not applicable for the provided argument types (Text[]) (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bpmn.dev.globalDataTask.deleteArrayCaseReferenceField", //$NON-NLS-1$
                        "_Ou6k0LKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : Array case reference fields cannot be used with the Delete operation (CaseDataTaskInvalidTestProcess:DeleteArrayByRef)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.useDeleteByCaseRefField", //$NON-NLS-1$
                        "_Ou6k0LKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : It is recommended that you use delete by non-array case reference field as this will ensure that the case object is not in use by other process instances (which can cause those instances to halt). (CaseDataTaskInvalidTestProcess:DeleteArrayByRef)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
						"BPM  : At Line:6 column:30, Assignment of Text from CaseReference<com.example.data::MyCase> is not supported (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
						"BPM  : At Line:7 column:30, Assignment of CaseReference<com.example.data::MyCase> from Text is not supported (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/CaseDataTaskInvalidTest/Process Packages/CaseDataTaskInvalidTest.xpdl", //$NON-NLS-1$
                        "bx.validateScriptTask", //$NON-NLS-1$
                        "_M7e4ULKvEemYQ8BLJRMPWQ", //$NON-NLS-1$
                        "BPM  : At Line:8 column:15, Property c is invalid for the current context (CaseDataTaskInvalidTestProcess:ScriptTask)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$
        };
    }

}
