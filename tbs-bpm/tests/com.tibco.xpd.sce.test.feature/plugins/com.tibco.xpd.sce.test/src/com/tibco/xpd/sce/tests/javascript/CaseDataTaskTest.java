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
public class CaseDataTaskTest extends AbstractN2BaseValidationTest {

    public CaseDataTaskTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("CaseDataTaskTest");
        /*
         * make sure BOM project is configured as Business Data project to avoid
         * getting unexpected problem markers unrelated to this test.
         */
        BOMUtils.setAsBusinessDataProject(project); // $NON-NLS-1$
        // and make sure we have a Forms special folder
        SpecialFolderUtil.getCreateSpecialFolderOfKind(project, "forms", "Forms");
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

        IFile okFile2 = getTestFile("CaseDataTaskTest.xpdl"); //$NON-NLS-1$

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
        return "CaseDataTaskTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo("resources/CaseDataTaskTest", //$NON-NLS-1$
                "CaseDataTaskTest/Business Objects{bom}/data.bom"), //$NON-NLS-1$
                new TestResourceInfo("resources/CaseDataTaskTest", //$NON-NLS-1$
                        "CaseDataTaskTest/Process Packages{processes}/CaseDataTaskTest.xpdl") //$NON-NLS-1$
        };
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        return new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo("/CaseDataTaskTest/Process Packages/CaseDataTaskTest.xpdl", //$NON-NLS-1$
                        "bx.useDeleteByCaseRefField", //$NON-NLS-1$
                        "_0HeKoKfCEemKB8xscno3Rg", //$NON-NLS-1$
                        "BPM  : It is recommended that you use delete by non-array case reference field as this will ensure that the case object is not in use by other process instances (which can cause those instances to halt). (CaseDataTaskTestProcess:DeleteArrayByCaseRef)", //$NON-NLS-1$
                        "") //$NON-NLS-1$

        };
    }

}
