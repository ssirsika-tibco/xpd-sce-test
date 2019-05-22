/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.javascript;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Test valid Javascript Array functions
 *
 * @author
 * @since
 */
public class JavascriptArrayValidTest extends AbstractN2BaseValidationTest {

    public JavascriptArrayValidTest() {
        super(true);
        cleanProjectAtEnd = false;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

        /*
         * make sure BOM project is configured as Business Data project to avoid
         * getting unexpected problem markers unrelated to this test.
         */
        BOMUtils.setAsBusinessDataProject(ResourcesPlugin.getWorkspace()
                .getRoot().getProject("JavascriptArrayTest")); //$NON-NLS-1$
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
        IFile okFile1 = getTestFile("JavascriptData.bom"); //$NON-NLS-1$

        List<ValidationsTestProblemMarkerInfo> problemMarkers =
                getProblemMarkers(okFile1);
        String the1stProblem = null;
        if (!problemMarkers.isEmpty()) {
            ValidationsTestProblemMarkerInfo markerInfo = problemMarkers.get(0);
            the1stProblem = markerInfo.getProblemId() + "::" //$NON-NLS-1$
                    + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                    + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                    + markerInfo.getProblemText() + "\"\n"; //$NON-NLS-1$
        }
        assertTrue(
                "JavascriptData.bom: should not have problem markers but has at least one:\n" //$NON-NLS-1$
                        + the1stProblem,
                the1stProblem == null);

        IFile okFile2 = getTestFile("JavascriptArrayValidTest.xpdl"); //$NON-NLS-1$

        problemMarkers = getProblemMarkers(okFile2);
        the1stProblem = null;
        if (!problemMarkers.isEmpty()) {
            ValidationsTestProblemMarkerInfo markerInfo = problemMarkers.get(0);
            the1stProblem = markerInfo.getProblemId() + "::" //$NON-NLS-1$
                    + markerInfo.getResourceURI() + "." //$NON-NLS-1$
                    + markerInfo.getLocationURI() + ":\n  \"" //$NON-NLS-1$
                    + markerInfo.getProblemText() + "\"\n"; //$NON-NLS-1$
        }
        assertTrue(
                "JavascriptArrayValidTest.xpdl: should not have problem markers but has at least one:\n" //$NON-NLS-1$
                        + the1stProblem,
                the1stProblem == null);

        /*
         * Then test the expected problems are raised
         */
        doTestValidations();
    }

    @Override
    protected String getTestName() {
        return "JavascriptArrayValidTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo(
                "resources/JavascriptArrayTest", //$NON-NLS-1$
                "JavascriptArrayTest/Business Objects{bom}/JavascriptData.bom"), //$NON-NLS-1$
                new TestResourceInfo("resources/JavascriptArrayTest", //$NON-NLS-1$
                        "JavascriptArrayTest/Process Packages{processes}/JavascriptArrayValidTest.xpdl") //$NON-NLS-1$
        };
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     *
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        return new ValidationsTestProblemMarkerInfo[] {};
    }

}
