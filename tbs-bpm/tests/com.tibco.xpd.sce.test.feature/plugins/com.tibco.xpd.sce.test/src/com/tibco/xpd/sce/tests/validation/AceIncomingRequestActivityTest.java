/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * In ACE all process incoming request activity/event is replacing Message-Catch event and ReceiveTask.
 * 
 * This test validates that the required patterns for the use of incoming request activities are valid (don't raise
 * validation problems):
 * <ul>
 * <li>Receive task</li>
 * <li>In-flow intermediate catch type none event</li>
 * <li>Intermediate catch type none event attached to a task or embedded sub-process boundary</li>
 * <li>An intermediate catch type none event that triggers and event handler flow</li>
 * <li>A start type none event in an event sub-process.</li>
 * </ul>
 * 
 * @author jarciuch
 * @since 23 Jul 2019
 */
public class AceIncomingRequestActivityTest extends TestCase {

    // @Test
    public void testIncomingRequestActivityScenarios() {
        ProjectImporter projectImporter = importMainTestProjects();

        assertFalse(
                "IncomingRequestAct" //$NON-NLS-1$
                        + " project should not have any ERROR level problem markers", //$NON-NLS-1$
                hasErrorProblemMarker(
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(
                                        "IncomingRequestAct"))); //$NON-NLS-1$


        projectImporter.performDelete();
    }

    /**
     * Import all projects from test plugin resources for the main test
     * 
     * @return the project importer
     */
    private ProjectImporter importMainTestProjects() {
        /*
         * Import and mgirate the project
         */

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "/resources/AceIncomingRequestActivityTest/IncomingRequestAct/" }, //$NON-NLS-1$
                new String[] { "IncomingRequestAct" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from resources/AceIncomingRequestActivityTest/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker
     *         raised on it.
     */
    private boolean hasErrorProblemMarker(IResource resource) {
        try {
            IMarker[] markers = resource
                    .findMarkers(IMarker.PROBLEM,
                            true,
                            IResource.DEPTH_INFINITE);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (marker.getAttribute(
                            IMarker.SEVERITY,
                            -1) == IMarker.SEVERITY_ERROR) {
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
