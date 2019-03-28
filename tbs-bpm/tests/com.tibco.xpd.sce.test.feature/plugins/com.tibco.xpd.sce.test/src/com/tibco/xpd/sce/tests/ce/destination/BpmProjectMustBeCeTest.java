/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.ce.destination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.provider.IIssue;

import junit.framework.TestCase;

/**
 * Test for DDP 1.b
 * (http://confluence.tibco.com/pages/viewpage.action?pageId=171031408) All BPM
 * projects must have CE destination set else a validation marker will be
 * raised.
 *
 * @author aallway
 * @since 22 Mar 2019
 */
public class BpmProjectMustBeCeTest extends TestCase {

    /**
     * Test: Projects created (or effectively imported) without the CE
     * destination should have a single validation problem...
     * 
     * "Older BPM related projects must be imported using Import Studio Projects
     * or MAA Projects (delete and re-import the project)"
     */
    @Test
    public void testProjectWithoutCeDestination() {
        IProject project = createProject("BPM Process Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.BPMSOADeveloper", //$NON-NLS-1$
                "BpmProjectMustBeCeTest_WithoutCEDest", //$NON-NLS-1$
                "processes", //$NON-NLS-1$
                "xpdl", //$NON-NLS-1$
                false); // $NON-NLS-1$

        TestUtil.buildAndWait(project);

        List<IMarker> errorMarkers = TestUtil.getErrorMarkers(project);

        try {
            assertTrue(
                    "Project without CE destination should have problem marker: \"Older BPM related projects must be imported using Import Studio Projects or MAA Projects (delete and re-import the project)\"", //$NON-NLS-1$
                    errorMarkers != null && errorMarkers.size() == 1
                            && XpdConsts.PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE
                                    .equals(errorMarkers.get(0).getType())
                            && XpdConsts.PROJECT_NOT_ACE_DESTINATION_ISSUE_ID
                                    .equals(errorMarkers.get(0)
                                            .getAttribute(IIssue.ID)));

            project.delete(true, true, new NullProgressMonitor());

        } catch (CoreException e) {
            fail("Exception getting error markers: " + e.getMessage()); //$NON-NLS-1$
        }
    }

    /**
     * Test: Projects created (or effectively imported and migrated properly)
     * with the CE destination should NOT have the validation problem...
     * 
     * "Older BPM related projects must be imported using Import Studio Projects
     * or MAA Projects (delete and re-import the project)"
     */
    @Test
    public void testProjectWithCeDestination() {
        IProject project = createProject("BPM Process Project", //$NON-NLS-1$
                "com.tibco.xpd.newProject.BPMSOADeveloper", //$NON-NLS-1$
                "BpmProjectMustBeCeTest_WithCEDest", //$NON-NLS-1$
                "processes", //$NON-NLS-1$
                "xpdl", //$NON-NLS-1$
                true); // $NON-NLS-1$

        TestUtil.buildAndWait(project);

        List<IMarker> errorMarkers = TestUtil.getErrorMarkers(project);

        if (errorMarkers != null) {
            try {
                for (IMarker iMarker : errorMarkers) {
                    assertTrue(
                            "Project without CE destination should NOT have problem marker: \"Older BPM related projects must be imported using Import Studio Projects or MAA Projects (delete and re-import the project)\"", //$NON-NLS-1$ ,
                            !XpdConsts.PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE
                                    .equals(iMarker.getType())
                                    && !XpdConsts.PROJECT_NOT_ACE_DESTINATION_ISSUE_ID
                                            .equals(iMarker
                                                    .getAttribute(IIssue.ID)));
                }

                project.delete(true, true, new NullProgressMonitor());

            } catch (CoreException e) {
                fail("Exception getting error markers: " + e.getMessage()); //$NON-NLS-1$
            }
        }
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
    private IProject createProject(String wizTitle, String wizId,
            String projectName, String assetSpecialFolderKind,
            String assetFileExtension, boolean setCEDest) {
        /*
         * Mock the information defined in the plugin.xml contribution for
         * wizard: "com.tibco.xpd.newProject.BPMSOADeveloper"
         */
        Map<String, String> parameters = new HashMap<>();
        parameters.put("title", wizTitle); //$NON-NLS-1$

        if (setCEDest) {
            parameters.put("presetDestinationEnv", //$NON-NLS-1$
                    XpdConsts.ACE_DESTINATION_NAME);
        }

        parameters.put("hideProjectVersion", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        parameters.put("hideAssetSelection", "true"); //$NON-NLS-1$ //$NON-NLS-2$

        IProject project = TestUtil.createProjectFromWizard(projectName, // $NON-NLS-1$
                wizId, // $NON-NLS-1$
                parameters);

        return project;
    }

}
