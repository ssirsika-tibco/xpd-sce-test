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
 * In AMX BPM there were various rules preventing the use of one class type (Case/Local) from other types. There were
 * also rules governing the use of types across projects for case classes. These rules have been removed for ACE as they
 * are no longer required restrictions for the runtime or for BOM to CaseDataModel transformation
 * 
 * The test projects used here break these rules, so this test ensures that they projects are problem free.
 * 
 * @author aallway
 * @since 22 Mar 2019
 */
public class AceAllowCrossClassTypeAndCrosssProjectReferenceTest extends TestCase {

    // @Test
    public void testLocalBOMDataProjectMigration() {
        ProjectImporter projectImporter = importMainTestProjects();
        try {
            assertFalse("TestCrossClassTypeRulesRemoved_1" //$NON-NLS-1$
                    + " project should not have any problem markers", //$NON-NLS-1$
                    hasProblemMarker(
                            ResourcesPlugin.getWorkspace().getRoot().getProject("TestCrossClassTypeRulesRemoved_1"))); //$NON-NLS-1$

            assertFalse("TestCrossClassTypeRulesRemoved_2" //$NON-NLS-1$
                    + " project should not have any problem markers", //$NON-NLS-1$
                    hasProblemMarker(
                            ResourcesPlugin.getWorkspace().getRoot().getProject("TestCrossClassTypeRulesRemoved_2"))); //$NON-NLS-1$

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
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

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/CrossClassTypeAndCrosssProjectTest/TestCrossClassTypeRulesRemoved_1/", //$NON-NLS-1$
                        "resources/CrossClassTypeAndCrosssProjectTest/TestCrossClassTypeRulesRemoved_2/" }, //$NON-NLS-1$
                new String[] { "TestCrossClassTypeRulesRemoved_1", //$NON-NLS-1$
                        "TestCrossClassTypeRulesRemoved_2" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from resources/CrossClassTypeAndCrosssProjectTest/" //$NON-NLS-1$
                + "TestCrossClassTypeRulesRemoved_1 or TestCrossClassTypeRulesRemoved_2", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker raised on it.
     */
    private boolean hasProblemMarker(IResource resource) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);

            if (markers != null && markers.length > 0) {
                return true;
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

}
