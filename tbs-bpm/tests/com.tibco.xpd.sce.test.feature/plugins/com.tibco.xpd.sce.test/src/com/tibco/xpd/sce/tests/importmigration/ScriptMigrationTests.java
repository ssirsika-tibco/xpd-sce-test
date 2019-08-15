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
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    // @Test
    public void testArraysMigration() throws Exception {
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
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    // @Test
    public void testDatesMigration() throws Exception {
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
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    // @Test
    public void testEnumsMigration() throws Exception {
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
                    "BPM  : At Line:21 column:73, Method get is invalid for the current context (simpleprocProcess:ScriptTask)",
                    // instance.status.indicator = com_example_simpleenumdata_Colour.get("R" + "E" + 'D');
                    "BPM  : At Line:24 column:87, Method get is invalid for the current context (simpleprocProcess:ScriptTask)",
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
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    // @Test
    public void testCaseAccessMigration() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/case-access/simple-cac-data/",
                        "resources/ScriptMigrationTests/case-access/simple-cac-proc/" },
                new String[] { "simple-cac-data", "simple-cac-proc" });
        assertTrue("Failed to load projects from resources/ScriptMigrationTests/case-access/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-cac-proc");

            // we expect some markers
            Collection<IMarker> errorMarkers =
                    TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured");

            TestUtil.outputErrorMarkers(project, true);
            
            String[] expectedFailures = {
                    // var criteria = cac_com_example_simplecacdata_CaseData.createCriteria("attribute1 = 1");
                    "BPM  : At Line:1 column:87, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // cac_com_example_simplecacdata_CaseData.createCriteria("attribute1 = 1", 10, 100);
                    "BPM  : At Line:2 column:81, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // cac_com_example_simplecacdata_CaseData.find(data.caseDataCopy);
                    "BPM  : At Line:3 column:63, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // cac_com_example_simplecacdata_CaseData.find(data.caseDataCopy, 10, 100);
                    "BPM  : At Line:4 column:72, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // bpm.caseData.findAll("com.example.simplecacdata.CaseData");
                    "BPM  : At Line:5 column:59, Method findAll is not applicable for the provided number of arguments  (simpleprocProcess:ScriptTask)",

                    // cac_com_example_simplecacdata_CaseData.findByAutoCaseIdentifier1(123);
                    "BPM  : At Line:7 column:70, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // cac_com_example_simplecacdata_CaseData.findByCriteria (criteria);
                    "BPM  : At Line:8 column:65, Variable cac_com_example_simplecacdata_CaseData not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // bpm.caseData.findByCriteria("attribute1 = 1","com.example.simplecacdata.CaseData");
                    "BPM  : At Line:9 column:83, Method findByCriteria is not applicable for the provided number of arguments  (simpleprocProcess:ScriptTask)",

                    // var orderCriteria = cac_com_example_simplecacdata_Order.createCriteria("attribute1 = 1");
                    "BPM  : At Line:13 column:89, Variable cac_com_example_simplecacdata_Order not defined or is not associated in the task interface. (simpleprocProcess:ScriptTask)",

                    // bpm.caseData.navigateByCriteria(data.caseDataRef, "OrderRef", "attribute1 = 1");
                    "BPM  : At Line:15 column:80, Method navigateByCriteria is not applicable for the provided number of arguments  (simpleprocProcess:ScriptTask)" };
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
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    // @Test
    public void testScriptUtilMigration() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/ScriptMigrationTests/script-util/simple-data/",
                        "resources/ScriptMigrationTests/script-util/simple-proc/" },
                new String[] { "simple-data", "simple-proc" });
        assertTrue("Failed to load projects from resources/ScriptMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("simple-proc");

            // we expect some markers
            Collection<IMarker> errorMarkers =
                    TestUtil.getErrorMarkers(project, true, "com.tibco.xpd.forms.validation.project.misconfigured");

            TestUtil.outputErrorMarkers(project, true);

            assertTrue(errorMarkers.isEmpty());
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }
}
