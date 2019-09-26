/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

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
public class InvestigateTest extends TestCase {

    static int numiterations = 100;

    // @Test
    public void testSystemActions() throws Exception {
        for (int i = 0; i < numiterations; i++) {
            importProjects(
                    new String[] { "resources/ScriptMigrationTests/ScriptMigrationTests/",
                            "resources/ScriptMigrationTests/ScriptMigrationTestsData/" },
                    new String[] { "ScriptMigrationTestsData", "ScriptMigrationTests" });

            importProjects(
                    new String[] { "resources/ScriptMigrationTests/simple-data/",
                            "resources/ScriptMigrationTests/simple-proc/" },
                    new String[] { "simple-data", "simple-proc" });

            importProjects(
                    new String[] { "resources/ScriptMigrationTests/simple-data/",
                            "resources/ScriptMigrationTests/simple-date-proc/" },
                    new String[] { "simple-data", "simple-date-proc" });

            importProjects(
                    new String[] { "resources/ScriptMigrationTests/simple-enum-data/",
                            "resources/ScriptMigrationTests/simple-enum-proc/" },
                    new String[] { "simple-enum-data", "simple-enum-proc" });

            importProjects(
                    new String[] { "resources/ScriptMigrationTests/case-access/simple-cac-data/",
                            "resources/ScriptMigrationTests/case-access/simple-cac-proc/" },
                    new String[] { "simple-cac-data", "simple-cac-proc" });

            importProjects(
                    new String[] { "resources/ScriptMigrationTests/script-util/simple-data/",
                            "resources/ScriptMigrationTests/script-util/simple-proc/" },
                    new String[] { "simple-data", "simple-proc" });

            importProjects(
                    new String[] { "resources/AntRascTest/Org-Model/", "resources/AntRascTest/AntProject/",
                            "resources/AntRascTest/ErrorProcess/" },
                    new String[] { "Org-Model", "AntProject", "ErrorProcess" });

            importProjects(
                    new String[] { "resources/AntRascTest/Org-Model/", "resources/AntRascTest/AntProject/",
                            "resources/AntRascTest/ErrorProcess/" },
                    new String[] { "Org-Model", "AntProject", "ErrorProcess" });

            importProjects(
                    new String[] { "resources/AntRascTest/Org-Model/", "resources/AntRascTest/AntProject/",
                            "resources/AntRascTest/ErrorProcess/" },
                    new String[] { "Org-Model", "AntProject", "ErrorProcess" });

            importProjects(new String[] { "resources/SystemActionMigrationTest/system-actions/" },
                    new String[] { "system-actions" });


        }

    }


    /**
     * @param projects
     * @param projectNames
     * @throws CoreException
     */
    public void importProjects(String[] projects, String[] projectNames) throws CoreException {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                projects,
                projectNames);

        assertTrue("Failed to load projects " + projects, projectImporter != null);
        try {
            TestUtil.buildAndWait();

            for (String pn : projectNames) {
                IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(pn);

                // we expect no validation markers

                assertFalse("Project " + pn + " - has Errors",
                        TestUtil.hasErrorProblemMarker(project,
                                true,
                                Arrays.asList(new String[] { "com.tibco.xpd.forms.validation.project.misconfigured",
                                        "ace.bom.illegal.property.date.type", "ace.bom.illegal.property.type",
                                        "bx.validateScriptTask", "ace.bom.caseid.must.be.mandatory.nonarray",
                                        "casestate.no.terminal.states.issue", "ace.bom.case.must.have.casestate",
                                        "ace.bom.caseid.must.be.text", "n2.ut.userTaskWithoutPerformer" }),
                                pn));

            }

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }


}
