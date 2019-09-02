/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 *
 *
 * @author pwatson
 * @since 23 Aug 2019
 */
@SuppressWarnings("nls")
public class GlobalSignalMigrationTest extends TestCase {
    // @Test
    public void testSystemActions() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/GlobalSignalMigration/simple-data/",
                        "resources/GlobalSignalMigration/simple-gsd/" },
                new String[] { "simple-data", "simple-gsd" });

        assertTrue("Failed to load projects from resources/GlobalSignalMigration/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-gsd");

            // we expect no validation markers
            TestUtil.outputErrorMarkers(project, true);
            assertTrue(TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured").isEmpty());
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }
}
