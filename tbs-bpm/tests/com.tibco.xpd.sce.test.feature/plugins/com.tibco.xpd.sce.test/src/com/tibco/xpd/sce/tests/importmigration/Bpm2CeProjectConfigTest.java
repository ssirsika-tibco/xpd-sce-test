/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
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
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_Org"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testDataProjectMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_Data"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testProcessProjectMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_Process"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testMaaProjectMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_maa"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testProcessAndDataBuildFolderRemovalMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_ProcessBuildFolders"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testOrgBuildFolderRemovalMigration() {
        ProjectImporter projectImporter =
                doTestProject("ProjectMigrationTest_OrgBuildFolders"); //$NON-NLS-1$
        projectImporter.performDelete();
    }

    @Test
    public void testGenBomMoveMigration1_UserBomAlreadyExists() {
        String projectName = "ProjectMigrationTest_GenAndUserBOMData"; //$NON-NLS-1$
        ProjectImporter projectImporter = doTestProject(projectName); // $NON-NLS-1$

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);

        /*
         * Check that the expected BOMs are in new location and not in old
         * location.
         */
        assertTrue(
                "BOM 'Generated Business Objects/org.example.NewWSDLFile.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.NewWSDLFile.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.NewWSDLFile.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile("Business Objects/org.example.NewWSDLFile.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/sub/sub sub/org.example.NewWSDLFile1.bom") //$NON-NLS-1$
                        .exists());

        projectImporter.performDelete();

    }

    @Test
    public void testGenBomMoveMigration1_UserBomNotExist() {
        String projectName = "ProjectMigrationTest_GenBOMOnly"; //$NON-NLS-1$
        ProjectImporter projectImporter = doTestProject(projectName); // $NON-NLS-1$

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(projectName);
        /*
         * Check that the expected BOMs are in new location and not in old
         * location.
         */
        assertTrue(
                "BOM 'Generated Business Objectsorg.example.ShouldMigrateToUserDefWSDL.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom' should not be in generated BOM folder", //$NON-NLS-1$
                !project.getFile(
                        "Generated Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/org.example.ShouldMigrateToUserDefWSDL.bom") //$NON-NLS-1$
                        .exists());

        assertTrue(
                "BOM 'Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom' should have been moved to user defined BOM folder", //$NON-NLS-1$
                project.getFile(
                        "Business Objects/org.example.ShouldMigrateToUserDefWSDL2.bom") //$NON-NLS-1$
                        .exists());

        /* Check that special folder has been added. */
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        boolean found = false;
        for (SpecialFolder specialFolder : projectConfig.getSpecialFolders()
                .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
            if (specialFolder.getGenerated() == null) {
                if (specialFolder.getFolder().exists()) {
                    if ("Business Objects" //$NON-NLS-1$
                            .equals(specialFolder.getFolder().getName())) {
                        found = true;
                    }
                }
            }
        }

        assertTrue(
                "User defined 'Business Objects' folder is not configured as a Special Folder", //$NON-NLS-1$
                found);

        projectImporter.performDelete();

    }

    /**
     * Test the given project.
     * 
     * @param projectName
     */
    private ProjectImporter doTestProject(String projectName) {

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/ImportMigrationTests/" + projectName + "/" }, //$NON-NLS-1$ //$NON-NLS-2$
                new String[] { projectName });

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

        assertTrue("Project should have no 'bom2xsd' special-folder", //$NON-NLS-1$
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

        /*
         * ACE-457 Check that unwanted 'Generate Business Objects' special
         * folder was removed.
         */
        boolean found = false;

        for (SpecialFolder specialFolder : projectConfig.getSpecialFolders()
                .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
            if (BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                    .equals(specialFolder.getGenerated())) {
                found = true;
            }
        }

        if (!found) {
            /*
             * check the actual folder even if special folder removed from
             * .config
             */
            found = projectConfig.getProject()
                    .getFolder("Generated Business Objects").exists(); //$NON-NLS-1$
        }

        assertTrue(
                "'Generated Business Objects' folder has not been removed from Special Folders / file system", //$NON-NLS-1$
                !found);

        /*
         * Make sure all generated/user defined WSDL folders have been removed.
         */
        assertTrue(
                "All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !hasSpecialFolder(projectConfig, "wsdl")); //$NON-NLS-1$

        assertTrue(
                "All generated/user defined WSDL Special folders have not been removed", //$NON-NLS-1$
                !project.getFolder("Generated Services") //$NON-NLS-1$
                        .exists()
                        && !project.getFolder("Services Descriptors").exists()); //$NON-NLS-1$

        /*
         * Ensure that the WSDL project asset as been removed.
         */
        found = false;
        for (AssetType asset : projectConfig.getAssetTypes()) {
            if ("com.tibco.xpd.asset.wsdl".equals(asset.getId())) { //$NON-NLS-1$
                found = true;
            }
        }
        assertTrue("WSDL project asset should have been removed", !found); //$NON-NLS-1$

        /*
         * Ensure that unwanted natures have been removed.
         */
        try {
            IProjectDescription description = project.getDescription();

            List<String> natures = Arrays.asList(description.getNatureIds());

            assertTrue(
                    "Nature 'com.tibco.xpd.wsdltobom.wsdlBomNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdltobom.wsdlBomNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.bom.xsdtransform.xsdNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains(
                            "com.tibco.xpd.bom.xsdtransform.xsdNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.wsdlgen.wsdlGenNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains("com.tibco.xpd.wsdlgen.wsdlGenNature")); //$NON-NLS-1$
            assertTrue(
                    "Nature 'com.tibco.xpd.n2.daa.cleanBpmFolderNature' should have been removed from project", //$NON-NLS-1$
                    !natures.contains(
                            "com.tibco.xpd.n2.daa.cleanBpmFolderNature")); //$NON-NLS-1$

            /*
             * Check builders have been removed.
             */
            assertTrue(
                    "Builder 'com.tibco.xpd.wsdltobom.wsdlToBomBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.wsdltobom.wsdlToBomBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.bom.xsdtransform.xsdBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.bom.xsdtransform.xsdBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.n2.daa.cleanBpmFolderBuilder' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description,
                            "com.tibco.xpd.n2.daa.cleanBpmFolderBuilder")); //$NON-NLS-1$
            assertTrue(
                    "Builder 'com.tibco.xpd.wsdlgen.wsdlGen' should have been removed from project", //$NON-NLS-1$
                    !hasBuilder(description, "com.tibco.xpd.wsdlgen.wsdlGen")); //$NON-NLS-1$

        } catch (CoreException e) {
            e.printStackTrace();
            fail("Exception thrown during test execution: " + e.getMessage()); //$NON-NLS-1$
        }

        return projectImporter;
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

    /**
     * Check if a given builder is configured for the given project.
     * 
     * @param description
     * @param builderName
     * @return <code>true</code> if the project has the given builder configured
     *         on it
     */
    private boolean hasBuilder(IProjectDescription description,
            String builderName) {
        for (ICommand builder : description.getBuildSpec()) {
            if (builderName.equals(builder.getBuilderName())) {
                return true;
            }
        }
        return false;
    }

}
