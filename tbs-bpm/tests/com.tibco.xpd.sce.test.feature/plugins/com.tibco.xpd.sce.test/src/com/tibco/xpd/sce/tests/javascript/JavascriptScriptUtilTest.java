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
 * Test valid Javascript Array functions
 *
 * @author
 * @since
 */
public class JavascriptScriptUtilTest extends AbstractN2BaseValidationTest {

    public JavascriptScriptUtilTest() {
        super(true);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest#setUpBeforeBuild()
     *
     */
    @Override
    protected void setUpBeforeBuild() {
        super.setUpBeforeBuild();

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("JavascriptScriptUtilTest"); //$NON-NLS-1$
        /*
         * make sure BOM project is configured as Business Data project to avoid
         * getting unexpected problem markers unrelated to this test.
         */
		BOMUtils.setAsBusinessDataProject(project); // $NON-NLS-1$
		// and make sure we have a Forms special folder
		SpecialFolderUtil.getCreateSpecialFolderOfKind(project, "forms", "Forms"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * JavascriptScriptUtilTest
     * 
     * @throws Exception
     */
    public void testAceBomMigrationValidationsTest() throws Exception {
        /*
         * Before checking that the expected problems have been raised, make
         * sure that the BOMs that are expected to be ok have no problems.
         */
        IFile okFile1 = getTestFile("JavascriptScriptUtilData.bom"); //$NON-NLS-1$

		// has no errors except forms one, and 2

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
                "JavascriptScriptUtilData.bom: should not have problem markers but has at least one:\n" //$NON-NLS-1$
                        + the1stProblem,
                the1stProblem == null);

        IFile okFile2 = getTestFile("JavascriptScriptUtilTest.xpdl"); //$NON-NLS-1$

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
				the1stProblem != null);

		assertTrue("JavascriptArrayValidTest.xpdl: should have two problem markers:\n" //$NON-NLS-1$
				+ problemMarkers, problemMarkers.size() == 2);

        /*
         * Then test the expected problems are raised
         */
        doTestValidations();
    }

    @Override
    protected String getTestName() {
        return "JavascriptScriptUtilTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        return new TestResourceInfo[] { new TestResourceInfo(
                "resources/JavascriptScriptUtilTest", //$NON-NLS-1$
                "JavascriptScriptUtilTest/Business Objects{bom}/JavascriptScriptUtilData.bom"), //$NON-NLS-1$
                new TestResourceInfo("resources/JavascriptScriptUtilTest", //$NON-NLS-1$
                        "JavascriptScriptUtilTest/Process Packages{processes}/JavascriptScriptUtilTest.xpdl") //$NON-NLS-1$
        };
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     *
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
		return new ValidationsTestProblemMarkerInfo[]{new ValidationsTestProblemMarkerInfo(
				"/JavascriptScriptUtilTest/Process Packages/JavascriptScriptUtilTest.xpdl", //$NON-NLS-1$
				"bx.validateScriptTask", //$NON-NLS-1$
				"_OdaT8L0JEemp76kOVRbOLw", //$NON-NLS-1$
				"BPM  : At Line:17 column:72, The Class Type Name parameter must identify a valid Class (<package name>.<class name>) (JavascriptScriptUtilTestProcess:ScriptTask)", //$NON-NLS-1$
				""),
				new ValidationsTestProblemMarkerInfo(
				"/JavascriptScriptUtilTest/Process Packages/JavascriptScriptUtilTest.xpdl", //$NON-NLS-1$
				"bx.validateScriptTask", //$NON-NLS-1$
				"_vUIJE5i4Ee6YrtLdONpHxA", //$NON-NLS-1$
				"BPM  : OpenScript::At Line:14 column:72, The Class Type Name parameter must identify a valid Class (<package name>.<class name>) (JavascriptScriptUtilTestProcess2:UserTask)", //$NON-NLS-1$
				"")}; //$NON-NLS-1$
    }

}
