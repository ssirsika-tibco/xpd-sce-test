/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.XpdConsts;

import junit.framework.TestCase;

/**
 * Test for DDP 2.a
 * (http://confluence.tibco.com/pages/viewpage.action?pageId=171031408)
 * 
 * Projects with XPD Nature should have all destinations removed and CE
 * destination added.
 *
 * @author aallway
 * @since 22 Mar 2019
 */
public class Bpm2CeProjectConfigTest extends TestCase {


    // @Test
    public void testOrgProjectMigration() {
        doTestProject("ProjectMigrationTest_Org"); //$NON-NLS-1$
    }

    @Test
    public void testDataProjectMigration() {
        doTestProject("ProjectMigrationTest_Data"); //$NON-NLS-1$
    }

    @Test
    public void testProcessProjectMigration() {
        doTestProject("ProjectMigrationTest_Process"); //$NON-NLS-1$
    }

    @Test
    public void testMaaProjectMigration() {
        doTestProject("ProjectMigrationTest_maa"); //$NON-NLS-1$

    }

    @Test
    public void testProcessAndDataBuildFolderRemovalMigration() {
        doTestProject("ProjectMigrationTest_ProcessBuildFolders"); //$NON-NLS-1$

    }

    @Test
    public void testOrgBuildFolderRemovalMigration() {
        doTestProject("ProjectMigrationTest_OrgBuildFolders"); //$NON-NLS-1$
    }


    /**
     * Test the given project.
     * 
     * @param projectName
     */
    private void doTestProject(String projectName) {

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/ImportMigrationTests/" + projectName + "/" }, //$NON-NLS-1$ //$NON-NLS-2$
                new String[] {
                        projectName });

        assertTrue(
                "Failed to load projects from \"resources/ImportMigrationTests/ImportMigrationTests/" //$NON-NLS-1$
                        + projectName + "\"", //$NON-NLS-1$
                projectImporter != null);

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName); // $NON-NLS-1$
        assertTrue(projectName + " project does not exist", //$NON-NLS-1$
                project.isAccessible());

        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        ProjectDetails projectDetails = projectConfig.getProjectDetails();

        /*
         * Check the project has only the CE destination set.
         */
        assertTrue(
                project.getName()
                        + " project should have only one destination set", //$NON-NLS-1$
                projectDetails.getEnabledGlobalDestinationIds().size() == 1);

        assertTrue(
                project.getName() + " project does not have CE destination set", //$NON-NLS-1$
                XpdConsts.ACE_DESTINATION_NAME.equals(projectDetails
                        .getEnabledGlobalDestinationIds().get(0)));

        /*
         * Check for unwanted folders being left behind.
         */

        /* Special folder configurations first. */
        assertTrue(
                "Project should have no 'compositeModulesOutput' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "compositeModulesOutput")); //$NON-NLS-1$

        assertTrue(
                "Project should have no 'bom2xsd' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "bom2xsd")); //$NON-NLS-1$

        assertTrue("Project should have no 'deModulesOutput' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "deModulesOutput")); //$NON-NLS-1$

        assertTrue("Project should have no 'daaBinFolder' special-folder", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "daaBinFolder")); //$NON-NLS-1$

        /* Check physical folders. */
        assertTrue("Project should have no '.bpm' folder", //$NON-NLS-1$
                !project.getFolder(".bpm").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.bom2Xsd' folder", //$NON-NLS-1$
                !project.getFolder(".bom2Xsd").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.processOut' folder", //$NON-NLS-1$
                !project.getFolder(".processOut").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.bomjars' folder", //$NON-NLS-1$
                !project.getFolder(".bomjars").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.deModulesOutput' folder", //$NON-NLS-1$
                !project.getFolder(".deModulesOutput").exists()); //$NON-NLS-1$

        assertTrue("Project should have no '.daabin' folder", //$NON-NLS-1$
                !project.getFolder(".daabin").exists()); //$NON-NLS-1$

        projectImporter.performDelete();

    }

    /**
     * Cannot use projectConfig.getSpecialFolders() .getFoldersOfKind() BECAUSE
     * it relies on the special folder kind being a valid kin and that requires
     * a special older contribution.
     * 
     * If that contribution is for some build folder or other that isn't used in
     * SCE then the contribution won't be there and it will fail.
     * 
     * So we have to look by hand.
     * 
     * @param projectConfig
     * @param kind
     * 
     * @return <code>true</code>if the project has any configuration for the
     *         given special folder kind.
     */
    private boolean hasSpecialFolder(ProjectConfig projectConfig, String kind) {
        SpecialFolders specialFolders = projectConfig.getSpecialFolders();

        if (specialFolders != null) {
            for (SpecialFolder specialFolder : specialFolders.getFolders()) {
                if (kind.equals(specialFolder.getKind())) {
                    return true;
                }
            }
        }

        return false;
    }

}
