/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Test;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
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

    private ProjectImporter projectImporter;

    /**
     * @see junit.framework.TestCase#setUp()
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        
        projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                Arrays.asList(
                        "resources/ImportMigrationTests/ProjectMigrationTest_Org/", //$NON-NLS-1$
                        "resources/ImportMigrationTests/ProjectMigrationTest_Data/", //$NON-NLS-1$
                        "resources/ImportMigrationTests/ProjectMigrationTest_Process/", //$NON-NLS-1$
                        "resources/ImportMigrationTests/ProjectMigrationTest_maa/"), //$NON-NLS-1$
                new String[] { "ProjectMigrationTest_Org", //$NON-NLS-1$
                        "ProjectMigrationTest_Data", //$NON-NLS-1$
                        "ProjectMigrationTest_Process", //$NON-NLS-1$
                        "ProjectMigrationTest_maa" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from \"resources/ImportMigrationTests/MigrationSetsCeDestinationTest/\"", //$NON-NLS-1$
                projectImporter != null);

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        projectImporter.performDelete();
    }

    @Test
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

    /**
     * Test the given project.
     * 
     * @param projectName
     */
    private void doTestProject(String projecName) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projecName); // $NON-NLS-1$
        assertTrue(projecName + " project does not exist", //$NON-NLS-1$
                project.isAccessible());

        /*
         * Check the project has only the CE destination set.
         */
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        ProjectDetails projectDetails = projectConfig.getProjectDetails();

        assertTrue(
                project.getName()
                        + " project should have only one destination set", //$NON-NLS-1$
                projectDetails.getEnabledGlobalDestinationIds().size() == 1);

        assertTrue(
                project.getName() + " project does not have CE destination set", //$NON-NLS-1$
                XpdConsts.ACE_DESTINATION_NAME.equals(projectDetails
                        .getEnabledGlobalDestinationIds().get(0)));
    }

}
