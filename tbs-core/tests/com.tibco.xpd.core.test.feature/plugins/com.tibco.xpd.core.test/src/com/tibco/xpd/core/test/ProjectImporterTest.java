/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.core.test;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * Tests project importer.
 * 
 * @author Jan Arciuchiewicz
 */
public class ProjectImporterTest extends TestCase {

    public static final String PLUGIN_ID = "com.tibco.xpd.core.test";

    public void testProjectFolderImport() throws Exception {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(ProjectImporterTest.PLUGIN_ID,
                                Arrays
                                        .asList("resources/ProjectImporter/com.tibco.n2.ut.test/")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());

        List<String> projectNames = Arrays.asList("com.tibco.n2.ut.test"); //$NON-NLS-1$
        checkProjectExistance(projectNames, true);
        projectImporter.performDelete();
        checkProjectExistance(projectNames, false);
    }

    public void testProjectZipImport() throws Exception {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(ProjectImporterTest.PLUGIN_ID,
                                Arrays
                                        .asList("resources/ProjectImporter/UC2F Pageflow.zip")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());

        List<String> projectNames = Arrays.asList("UC2F Pageflow"); //$NON-NLS-1$
        checkProjectExistance(projectNames, true);
        projectImporter.performDelete();
        checkProjectExistance(projectNames, false);
    }

    public void testFolderImport() throws Exception {
        ProjectImporter projectImporter =
                ProjectImporter
                        .createPluginFolderProjectImporter(ProjectImporterTest.PLUGIN_ID,
                                "resources/ProjectImporter/"); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());

        List<String> projectNames = Arrays.asList("UC2F Pageflow", //$NON-NLS-1$
                "com.tibco.n2.ut.test"); //$NON-NLS-1$
        checkProjectExistance(projectNames, true);
        projectImporter.performDelete();
        checkProjectExistance(projectNames, false);
    }

    private void checkProjectExistance(List<String> projectNames,
            boolean shouldExist) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for (String projectName : projectNames) {
            IProject project = root.getProject(projectName);
            if (shouldExist) {
                assertTrue(project.exists());
            } else {
                assertFalse(project.exists());
            }
        }
    }
}
