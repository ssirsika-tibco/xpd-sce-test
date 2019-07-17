/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * Tests for automated AMX BPM to ACE script migration...
 * 
 * <li>All process data must be wrapped in 'bpm' object.</li> TBD ... ...
 *
 * @author aallway
 * @since 12 Jul 2019
 */
public class ScriptMigrationTests extends TestCase {

    // @Test
    public void testBasicScriptMigrations() {
        ProjectImporter projectImporter = importMainTestProjects();

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("ScriptMigrationTests"); //$NON-NLS-1$

        /*
         * Seem to occasionally get a Forms Resource 11.x issue (The project
         * natures, special folders etc do not match the asset configuration)
         */


        assertFalse("ScriptMigrationTests project should have migrated to have no problem markers anywhere.", //$NON-NLS-1$
                TestUtil.hasErrorProblemMarker(project,
                        true,
                        Collections.singletonList("com.tibco.xpd.forms.validation.project.misconfigured")));

        projectImporter.performDelete();
    }

    /**
     * Import all projects from test plugin resources for the main test
     * 
     * @return the project importer
     */
    private ProjectImporter importMainTestProjects() {
        /*
         * Import and migrate the project
         */

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/ScriptMigrationTests/ScriptMigrationTests/", //$NON-NLS-1$
                        "resources/ScriptMigrationTests/ScriptMigrationTestsData/" }, //$NON-NLS-1$
                new String[] { "ScriptMigrationTestsData", "ScriptMigrationTests" }); //$NON-NLS-1$ //$NON-NLS-2$

        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

}
