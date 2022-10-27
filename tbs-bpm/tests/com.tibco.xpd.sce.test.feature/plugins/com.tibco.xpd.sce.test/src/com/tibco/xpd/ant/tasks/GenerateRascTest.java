/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.ant.tasks;

import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

import com.tibco.bpm.dt.rasc.Version;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.rasc.core.impl.RascControllerImpl;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * ACE-5814: Test to check that locked projects maintain consistent version qualifier in RASC Manifest and that draft
 * projects always change version
 * 
 * ACE-6110: Check that Feature-Version property is set correctly.
 *
 * @author aallway
 * @since 14 Oct 2021
 */
@SuppressWarnings("nls")
public class GenerateRascTest extends TestCase {

    /*
     * This needs to be a copy of GenerateRasctTask.DEFAULT_DEST_DIR as: From
     * Eclipse Help: "The task or type can reference any class available for the
     * plug-in but plug-in classes MUST NOT access the tasks or types."
     */
    private static final String DEFAULT_DEST_DIR =
            "Deployment Artifacts";

    public void testConsistentRascVersions() throws Exception {
        ProjectImporter projectImporter = importProjects(
                new String[] { "resources/GenerateRascConsistentVersionTest/AntProject/",
                        "resources/GenerateRascConsistentVersionTest/Sid_Tester_Data/",
                        "resources/GenerateRascConsistentVersionTest/Sid_Tester_Process/" },
                new String[] { "Sid_Tester_Data", "Sid_Tester_Process" });

        try {
            IWorkspaceRoot workspaceRoot =
                    ResourcesPlugin.getWorkspace().getRoot();
            assertNotNull(workspaceRoot.getProject("Sid_Tester_Data"));
            assertNotNull(workspaceRoot.getProject("Sid_Tester_Process"));

            // test that the rasc files does not yet exist
            assertFalse(workspaceRoot.getProject("AntProject").getFolder("project").getFolder("RascOut")
                    .getFile("Sid_Tester_Data.rasc").exists());
            assertFalse(workspaceRoot.getProject("AntProject")
                    .getFolder("project").getFolder("RascOut")
                    .getFile("Sid_Tester_Process.rasc").exists());

            //
            // Lock the project for production
            //
            new GovernanceStateService().lockForProduction(workspaceRoot.getProject("Sid_Tester_Data"));

            //
            // Generate RASCs
            //
            runLaunch("AntProject", "project/build.xml");

            IFolder rascOutFolder = workspaceRoot.getProject("AntProject")
                    .getFolder("project").getFolder("RascOut");
            rascOutFolder.refreshLocal(2, null);

            IFile productionRascFile = rascOutFolder.getFile("Sid_Tester_Data.rasc");
            assertTrue(productionRascFile.exists());

            IFile draftRascFile = rascOutFolder.getFile("Sid_Tester_Process.rasc");
            assertTrue(draftRascFile.exists());

            /*
             * Check the manifest then check version, feature compatibility version and source/target environment
             */
            ManifestProperties originalProductionManifestProps =
                    getRascProperties(productionRascFile.getLocation().toFile());
            assertTrue(originalProductionManifestProps.appVersion.getQualifier() != null
                    && !originalProductionManifestProps.appVersion.getQualifier().isEmpty());

            assertEquals(RascControllerImpl.BPME_COMPATIBILITY_FEATURE_VERSION,
                    originalProductionManifestProps.featureVersion);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, originalProductionManifestProps.sourceEnvironment);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, originalProductionManifestProps.targetEnvironment);

            ManifestProperties originalDraftManifestProps = getRascProperties(draftRascFile.getLocation().toFile());
            assertTrue(originalDraftManifestProps.appVersion.getQualifier() != null
                    && !originalDraftManifestProps.appVersion.getQualifier().isEmpty());

            assertEquals(RascControllerImpl.BPME_COMPATIBILITY_FEATURE_VERSION,
                    originalDraftManifestProps.featureVersion);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, originalDraftManifestProps.sourceEnvironment);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, originalDraftManifestProps.targetEnvironment);

            // Delete and regenerate RASC files.
            productionRascFile.delete(true, new NullProgressMonitor());
            assertTrue(!productionRascFile.exists());
            draftRascFile.delete(true, new NullProgressMonitor());
            assertTrue(!draftRascFile.exists());

            //
            // Regenerate RASCS
            //
            runLaunch("AntProject", "project/build.xml");

            rascOutFolder.refreshLocal(2, null);

            assertTrue(productionRascFile.exists());
            assertTrue(draftRascFile.exists());

            // Recheck manifest versions.
            ManifestProperties newProductionManifestProps =
                    getRascProperties(productionRascFile.getLocation().toFile());
            assertTrue(newProductionManifestProps.appVersion.getQualifier() != null
                    && !newProductionManifestProps.appVersion.getQualifier().isEmpty());

            assertEquals(originalProductionManifestProps.appVersion, newProductionManifestProps.appVersion);

            assertEquals(newProductionManifestProps.featureVersion,
                    RascControllerImpl.BPME_COMPATIBILITY_FEATURE_VERSION);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, newProductionManifestProps.sourceEnvironment);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, newProductionManifestProps.targetEnvironment);

            ManifestProperties newDraftManifestProps = getRascProperties(draftRascFile.getLocation().toFile());
            assertTrue(newDraftManifestProps.appVersion.getQualifier() != null
                    && !newDraftManifestProps.appVersion.getQualifier().isEmpty());
            
            assertNotEquals(originalDraftManifestProps.appVersion, newDraftManifestProps.appVersion);
            
            assertEquals(newDraftManifestProps.featureVersion, RascControllerImpl.BPME_COMPATIBILITY_FEATURE_VERSION);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, newDraftManifestProps.sourceEnvironment);
            assertEquals(RascControllerImpl.BPME_ENVIRONMENT_TAG, newDraftManifestProps.targetEnvironment);

        } catch (Exception e) {
            fail("Exception thrown in test: " + e.getMessage());
        } finally {

            assertTrue("Failed deleting resources at end of test", projectImporter.performDelete());
        }
    }

    /**
     * Get the version from the given RASC
     * 
     * @param rascFile
     * @return version
     */
    private ManifestProperties getRascProperties(File rascFile) {
        try {
            ManifestProperties manifestProps = new ManifestProperties();

            ZipFile rascZip = new ZipFile(rascFile);

            InputStream mainfestStream = rascZip.getInputStream(new ZipEntry("MANIFEST.MF"));

            Manifest manifest = new Manifest(mainfestStream);

            manifestProps.appVersion = new Version(manifest.getMainAttributes().getValue("Application-Version"));

            String appVersion = manifest.getMainAttributes().getValue("Feature-Version");
            manifestProps.featureVersion = appVersion != null ? Integer.parseInt(appVersion) : null;

            manifestProps.sourceEnvironment = manifest.getMainAttributes().getValue("Source-Environment");
            manifestProps.targetEnvironment = manifest.getMainAttributes().getValue("Target-Environment");

            mainfestStream.close();

            rascZip.close();

            return manifestProps;

        } catch (Exception e) {
            fail("Error reading manifest from " + rascFile.getPath() + "  ::  " + e.getMessage());
        }

        return null;
    }

    /**
     * Test the given project.
     * 
     * @param aProjectName
     */
    private ProjectImporter importProjects(String[] aResourcePaths,
            String[] aExpectedProjectNames) {
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter =
                TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                        aResourcePaths,
                        aExpectedProjectNames);

        assertTrue("Failed to load projects", projectImporter != null);

        for (String aProjectName : aExpectedProjectNames) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(aProjectName);
            assertTrue(aProjectName + " project does not exist",
                    project.isAccessible());
        }

        TestUtil.buildAndWait();

        return projectImporter;
    }

    private void runLaunch(String aProject, String aBuildXml)
            throws CoreException {
    
        ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    
        ILaunchConfigurationType type = manager.getLaunchConfigurationType(
                "org.eclipse.ant.AntLaunchConfigurationType");
    
        ILaunchConfigurationWorkingCopy workingCopy =
                type.newInstance(null, "Launch ant");
    
        workingCopy.setAttribute("bad_container_name", "launcher");
        workingCopy.setAttribute("org.eclipse.ant.ui.DEFAULT_VM_INSTALL",
                false);
        workingCopy.setAttribute("org.eclipse.debug.core.ATTR_REFRESH_SCOPE",
                "${workspace}");
    
        workingCopy.setAttribute("org.eclipse.jdt.launching.CLASSPATH_PROVIDER",
                "org.eclipse.ant.ui.AntClasspathProvider");
        workingCopy.setAttribute("org.eclipse.jdt.launching.PROJECT_ATTR",
                aProject);
        workingCopy.setAttribute(
                "org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER",
                "org.eclipse.ant.ui.AntClasspathProvider");
        workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_LOCATION",
                String.format("${workspace_loc:/%1$s/%2$s}",
                        aProject,
                        aBuildXml));
        workingCopy.setAttribute("process_factory_id",
                "org.eclipse.ant.ui.remoteAntProcessFactory");
        workingCopy.setAttribute("org.eclipse.debug.core.MAPPED_RESOURCE_PATHS",
                Arrays.asList(
                        String.format("/%1$s/%2$s", aProject, aBuildXml)));
        workingCopy.setAttribute("org.eclipse.debug.core.MAPPED_RESOURCE_TYPES",
                Arrays.asList("1"));
    
        ILaunch launch = workingCopy.launch(ILaunchManager.RUN_MODE,
                new NullProgressMonitor());
    
        // wait for launch to complete - within 5 minutes
        int count = 0;
        while ((++count < 1000) && (!launch.isTerminated())) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    
        // fail if it failed to complete
        assertTrue("Launch failed to complete.", launch.isTerminated());
    }

    /**
     * Simple data task to gather data from a generated RASC.
     *
     * @author aallway
     * @since Oct 2022
     */
    private static class ManifestProperties {
        Version appVersion;

        int featureVersion;

        String sourceEnvironment;

        String targetEnvironment;
    }
}
