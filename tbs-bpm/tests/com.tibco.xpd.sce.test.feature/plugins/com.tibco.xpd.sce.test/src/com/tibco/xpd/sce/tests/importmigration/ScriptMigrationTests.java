/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IMarker;
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
@SuppressWarnings("nls")
public class ScriptMigrationTests extends TestCase {

    // @Test
    public void testBasicScriptMigrations() {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/ScriptMigrationTests/",
                        "resources/ScriptMigrationTests/ScriptMigrationTestsData/" },
                new String[] { "ScriptMigrationTestsData", "ScriptMigrationTests" });

        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("ScriptMigrationTests");

            /*
             * Seem to occasionally get a Forms Resource 11.x issue (The project natures, special folders etc do not
             * match the asset configuration)
             */

            assertFalse("ScriptMigrationTests project should have migrated to have no problem markers anywhere.",
                    TestUtil.hasErrorProblemMarker(project,
                            true,
                            Collections.singletonList("com.tibco.xpd.forms.validation.project.misconfigured")));
        } finally {
            projectImporter.performDelete();
        }
    }

    // @Test
    public void testScriptMigrationArrays() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/simple-data/",
                        "resources/ScriptMigrationTests/simple-proc/" },
                new String[] { "simple-data", "simple-proc" });
        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-proc");

            // we expect some markers
            Collection<IMarker> errorMarkers =
                    TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured");

            TestUtil.outputErrorMarkers(project, true);

            // expect "com_example_simpledata_Factory.createDataClass().arrayAttribute.add(100);" to fail due to
            // function reference
            assertEquals(1, errorMarkers.size());
            String message = (String) errorMarkers.iterator().next().getAttribute(IMarker.MESSAGE);
            assertTrue(message.contains("At Line:11 column:73, Method add is invalid for the current context"));
        } finally {
            projectImporter.performDelete();
        }
    }

    // @Test
    public void testScriptMigrationDates() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/simple-data/",
                        "resources/ScriptMigrationTests/simple-date-proc/" },
                new String[] { "simple-data", "simple-date-proc" });
        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-date-proc");

            // we expect some markers
            Collection<IMarker> errorMarkers =
                    TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured");

            TestUtil.outputErrorMarkers(project, true);
            assertEquals(0, errorMarkers.size());
        } finally {
            projectImporter.performDelete();
        }
    }

    // @Test
    public void testScriptMigrationEnums() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/simple-enum-data/",
                        "resources/ScriptMigrationTests/simple-enum-proc/" },
                new String[] { "simple-enum-data", "simple-enum-proc" });
        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-enum-proc");

            // we expect some markers
            Collection<IMarker> errorMarkers =
                    TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured");

            TestUtil.outputErrorMarkers(project, true);

            String[] expectedFailures = {
                    // instance.status.indicator = com_example_simpleenumdata_Colour.get(1);
                    "BPM  : At Line:21 column:69, Variable com_example_simpleenumdata_Colour not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",
                    // instance.status.indicator = com_example_simpleenumdata_Colour.get("R" + "E" + 'D');
                    "BPM  : At Line:24 column:83, Variable com_example_simpleenumdata_Colour not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",
                    // var x = com_example_simpleenumdata_AComplexEnumeration.GET.ENUMLIT1;
                    "BPM  : At Line:26 column:72, Property ENUMLIT1 is invalid for the current context (simpleprocProcess:ScriptTask)" };
            assertEquals(expectedFailures.length, errorMarkers.size());
            for (IMarker marker : errorMarkers) {
                String message = (String) marker.getAttribute(IMarker.MESSAGE);
                boolean found = false;
                for (String expected : expectedFailures) {
                    if (expected.equals(message)) {
                        found = true;
                        break;
                    }
                }
                assertTrue("unexpected: " + message, found);
            }
        } finally {
            projectImporter.performDelete();
        }
    }
}
