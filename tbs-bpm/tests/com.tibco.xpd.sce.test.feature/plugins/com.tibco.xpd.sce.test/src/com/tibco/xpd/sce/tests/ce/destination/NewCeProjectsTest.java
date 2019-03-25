/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.ce.destination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

import junit.framework.TestCase;

/**
 * Test for DDP 1.a
 * (http://confluence.tibco.com/pages/viewpage.action?pageId=171031408) All BPM
 * projects must have CE destination set by default.
 *
 * @author aallway
 * @since 22 Mar 2019
 */
public class NewCeProjectsTest extends TestCase {

    @Test
    public void testProcessProject() {
        doTest("BPM Process Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.BPMSOADeveloper", //$NON-NLS-1$
                "NewCeProjectsTest_Process", //$NON-NLS-1$
                "processes", //$NON-NLS-1$
                "xpdl"); //$NON-NLS-1$
    }

    @Test
    public void testBOMProject() {
        doTest("Business Data Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.BusinessData", //$NON-NLS-1$
                "NewCeProjectsTest_BDP", //$NON-NLS-1$
                "bom", //$NON-NLS-1$
                "bom"); //$NON-NLS-1$
    }

    @Test
    public void testGlobalSignalProject() {
        doTest("Global Signal Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.GSDDeveloper", //$NON-NLS-1$
                "NewCeProjectsTest_GSD", //$NON-NLS-1$
                "gsd", //$NON-NLS-1$
                "gsd"); //$NON-NLS-1$

    }

    @Test
    public void testOrganisationProject() {
        doTest("Organisation Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.OMDeveloper", //$NON-NLS-1$
                "NewCeProjectsTest_OM", //$NON-NLS-1$
                "om", //$NON-NLS-1$
                "om"); //$NON-NLS-1$

    }

    @Test
    public void testRESTProject() {
        doTest("REST Services Project", //$NON-NLS-1$
                "com.tibco.xpd.rest.wizard.project.new", //$NON-NLS-1$
                "NewCeProjectsTest_REST", //$NON-NLS-1$
                "rest", //$NON-NLS-1$
                "rsd"); //$NON-NLS-1$

    }

    @Test
    public void testWorklistFacadeProject() {
        doTest("Worklist Facade Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.FacadeDeveloper", //$NON-NLS-1$
                "NewCeProjectsTest_WLF", //$NON-NLS-1$
                "wlf", //$NON-NLS-1$
                "wlf"); //$NON-NLS-1$

    }


    /**
     * Launches wizard to create a project, checks CE destination set etc,
     * checks that correct asset is created in project and finally deletes the
     * project when done.
     * 
     * @param wizTitle
     * @param wizId
     * @param projectName
     * @param assetSpecialFolderKind
     * @param assetFileExtension
     */
    private void doTest(String wizTitle, String wizId, String projectName,
            String assetSpecialFolderKind, String assetFileExtension) {
        /*
         * Mock the information defined in the plugin.xml contribution for
         * wizard: "com.tibco.xpd.newProject.BPMSOADeveloper"
         */
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", wizTitle); //$NON-NLS-1$
        parameters.put("presetDestinationEnv", "CE"); //$NON-NLS-1$ //$NON-NLS-2$

        IProject project = TestUtil.createProjectFromWizard(projectName, // $NON-NLS-1$
                wizId, // $NON-NLS-1$
                parameters);

        Set<String> enabledGlobalDestinations = GlobalDestinationUtil
                .getEnabledGlobalDestinations(project, true);

        /* Ensure destination is auto set. */
        assertTrue("CE destination not set", //$NON-NLS-1$
                enabledGlobalDestinations.size() == 1
                        && enabledGlobalDestinations.contains("CE")); //$NON-NLS-1$

        /* Ensure that the correct asset is added. */
        ArrayList<IResource> assets =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(project,
                        assetSpecialFolderKind,
                        assetFileExtension);

        assertTrue(assetFileExtension + " asset not created", //$NON-NLS-1$
                assets.size() == 1);

        try {
            project.delete(true, true, new NullProgressMonitor());
        } catch (CoreException e) {

            e.printStackTrace();
            fail("Could not delete project: " + project.getName()); //$NON-NLS-1$
        }
    }

}
