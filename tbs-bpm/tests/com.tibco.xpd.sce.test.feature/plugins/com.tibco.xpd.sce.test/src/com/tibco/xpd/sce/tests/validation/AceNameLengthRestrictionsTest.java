/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * AceNameLengthRestrictionsTest
 * <p>
 * Tests for the ACE specific name length restrictions (that are there to
 * protect runtime deployment manager from overlength RASC / artifact names.
 * </p>
 * <p>
 * <li>Project names must not exceed 100 characters.</li>
 * <li>Project ID must not exceed 100 characters.</li>
 * <li>Project-relative file-path must not exceed 100 characters.</li>
 * <li>Process names must not exceed 100 characters.</li>
 * <li>Business data package names must not exceed 100 characters.</li>
 * </p>
 *
 * @author aallway
 * @since 10 Jul 2019
 */
public class AceNameLengthRestrictionsTest extends AbstractN2BaseValidationTest {

    private ProjectImporter projectImporter;

    public AceNameLengthRestrictionsTest() {
		super(true);
	}

	/**
     * AceNameLengthRestrictionsTest
     * 
     * @throws Exception
     */
    public void testAceNameLengthRestrictionsTest() throws Exception {
        doTestValidations();

        /*
         * Base class doesn't do project validations so we'll check specifically
         * for project name and id length validations.
         */
        assertTrue("NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test" //$NON-NLS-1$
                + " project should have the 'Project names must not exceed 100 characters.' raised on it ", //$NON-NLS-1$
                hasSpecificErrorProblemMarker(ResourcesPlugin.getWorkspace().getRoot().getProject(
                        "NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test"), //$NON-NLS-1$
                        "ace.project.name.too.long")); //$NON-NLS-1$

        assertTrue(
                "NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test" //$NON-NLS-1$
                        + " project should have the 'Project ID must not exceed 100 characters.' raised on it ", //$NON-NLS-1$
                hasSpecificErrorProblemMarker(ResourcesPlugin.getWorkspace().getRoot().getProject(
                        "NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test"), //$NON-NLS-1$
                        "ace.project.id.too.long")); //$NON-NLS-1$

        /*
         * These projects have up to the maximum name lengths but don't exceed
         * them - so should have no problems.
         */
        assertFalse("NameLimitsOKBOMTest" //$NON-NLS-1$
                + " project should not have any ERROR level problem markers", //$NON-NLS-1$
                TestUtil.hasErrorProblemMarker(ResourcesPlugin.getWorkspace().getRoot()
                        .getProject("NameLimitsOKBOMTest"), true, "NameLimitsOKBOMTest"));

        /*
         * Sid - FORMs import intermittently doesn't migrate/auto-resolve its asset / nature configuration so you
         * occasionally get the problem marker...
         * 
         * Forms Resources 11.x : The project natures, builders, special folders etc. do not match the asset
         * configuration
         * 
         * So we need to allow for that.
         */
        assertFalse(
                "NameLimitsOKProcess_012345678901234567890123456789012345678901234567890123456789012345678901234_Test" //$NON-NLS-1$
                        + " project should not have any ERROR level problem markers", //$NON-NLS-1$
                TestUtil.hasErrorProblemMarker(
                        ResourcesPlugin.getWorkspace().getRoot().getProject("NameLimitsOKBOMTest"),
                        true,
                        Collections.singletonList("com.tibco.xpd.forms.validation.project.misconfigured"),
                        "NameLimitsOKProcess_012345678901234567890123456789012345678901234567890123456789012345678901234_Test"));

        return;
	}

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
     *
     */
    @Override
    protected void customTestResourceSetup() {
        /*
         * This test doesn't create files via the normal getTestResources()
         * because we want to import and migrate a whole project from AMX BPM.
         */
        projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/AceNameLengthRestrictionsTest/NameLimitsExceededBOMTest/", //$NON-NLS-1$
                        "resources/AceNameLengthRestrictionsTest/NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test/", //$NON-NLS-1$
                        "resources/AceNameLengthRestrictionsTest/NameLimitsOKBOMTest/", //$NON-NLS-1$
                        "resources/AceNameLengthRestrictionsTest/NameLimitsOKProcess_012345678901234567890123456789012345678901234567890123456789012345678901234_Test/" }, //$NON-NLS-1$
                new String[] { "NameLimitsExceededBOMTest", //$NON-NLS-1$
                        "NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test", //$NON-NLS-1$
                        "NameLimitsOKBOMTest", //$NON-NLS-1$
                        "NameLimitsOKProcess_012345678901234567890123456789012345678901234567890123456789012345678901234_Test" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from \"resources/AceNameLengthRestrictionsTest/", //$NON-NLS-1$
                projectImporter != null);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (projectImporter != null) {
            projectImporter.performDelete();
        }
        super.tearDown();
    }

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[] { 
			
			    new ValidationsTestProblemMarkerInfo(
			    		"/NameLimitsExceededBOMTest/Business Objects/NameLimitsExceededBOMTest.bom", //$NON-NLS-1$ 
			    		"ace.bom.package.id.too.long", //$NON-NLS-1$ 
			    		"_OUXq0KMtEemIrYb-Ct7CJw", //$NON-NLS-1$ 
			    		"BPM  : Business data package names must not exceed 100 characters. (com.example.namelimitsexceededbomtest_012345678901234567890123456789012345678901234567890123456789012)", //$NON-NLS-1$ 
			    		""), //$NON-NLS-1$ 
			    		
			
			    new ValidationsTestProblemMarkerInfo(
                        "/NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test/Process Packages long 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456/NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl", //$NON-NLS-1$
			    		"ace.process.name.too.long", //$NON-NLS-1$ 
			    		"_PNju4KMoEemdiaHFKLPsUg", //$NON-NLS-1$ 
                        "BPM  : Process names must not exceed 100 characters. (NameLimitsProcessTestProcess_012345678901234567890123456789012345678901234567890123456789012345678901)", //$NON-NLS-1$
			    		""), //$NON-NLS-1$ 
			    		
			
                new ValidationsTestProblemMarkerInfo(
                        "/NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test/Process Packages long 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456/NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl", //$NON-NLS-1$
                        "ace.filepath.too.long", //$NON-NLS-1$
                        "Process Packages long 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456/NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl", //$NON-NLS-1$
                        "BPM : Project-relative file-path must not exceed 250 characters. (NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$

                new ValidationsTestProblemMarkerInfo(
                        "/NameLimitsExceededProcess_0123456789012345678901234567890123456789012345678901234567890123456789_Test/Process Packages long 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456/NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl", //$NON-NLS-1$
                        "ace.filename.too.long", //$NON-NLS-1$
                        "Process Packages long 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456/NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl", //$NON-NLS-1$
                        "BPM : File names must not exceed 100 characters. (NameLimitsProcessTest_01234567890123456789012345678901234567890123456789012345678901234567890123.xpdl)", //$NON-NLS-1$
                        ""), //$NON-NLS-1$
			                
        };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "AceNameLengthRestrictionsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {};

        return testResources;
    }


    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker
     *         raised on it.
     */
    private boolean hasSpecificErrorProblemMarker(IResource resource, String issueId) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (issueId.equals(marker.getAttribute(IIssue.ID))) {
                        return true;
                    }
                }

            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

}
