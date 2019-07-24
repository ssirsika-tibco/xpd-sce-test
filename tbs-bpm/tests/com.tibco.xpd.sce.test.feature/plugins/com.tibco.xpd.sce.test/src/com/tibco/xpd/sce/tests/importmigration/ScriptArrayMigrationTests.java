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
 * Tests for automated AMX BPM to ACE script migration for arrays.
 * <ul>
 * <li>change list.add()/addAll() method to array.push()/pushAll() method</li>
 * </ul>
 * 
 * @author pwatson
 * @since 22 Jul 2019
 */
@SuppressWarnings("nls")
public class ScriptArrayMigrationTests extends TestCase {

    // @Test
    public void testBasicScriptMigrations() {
        ProjectImporter projectImporter = importMainTestProjects();

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-proc");

        /*
         * Seem to occasionally get a Forms Resource 11.x issue (The project natures, special folders etc do not match
         * the asset configuration)
         */
        TestUtil.outputErrorMarkers(project, true);
        assertFalse("ScriptMigrationTests project should have migrated to have no problem markers anywhere.",
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

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/simple-data/",
                        "resources/ScriptMigrationTests/simple-proc/" },
                new String[] { "simple-data", "simple-proc" });

        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

}
