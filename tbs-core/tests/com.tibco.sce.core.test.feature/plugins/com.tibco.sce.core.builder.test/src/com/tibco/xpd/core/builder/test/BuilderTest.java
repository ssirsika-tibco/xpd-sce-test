/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.builder.test;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.core.builder.test.builder.TestProjectBuilder;
import com.tibco.xpd.core.builder.test.builder.TestProjectBuilder.BuildData;
import com.tibco.xpd.core.builder.test.builder.TestProjectBuilder.BuildLog;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;

/**
 * JUnit test to test builder. The test does the following:
 * <p>
 * <ol>
 * <li>Create an XPD project (done by setup)</li>
 * <li>Create a test special folder, check kind (FULL or AUTO) and number of
 * times builder was called</li>
 * <li>Create a file in the special folder, check kind and number of times
 * builder was called</li>
 * <li>Delete the file, check kind and number of times builder was called</li>
 * <li>Delete the folder, check kind and number of times builder was called</li>
 * <li>Delete the XPD project (done by teardown)</li>
 * </ol>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class BuilderTest extends TestCase {

    /*
     * NOTE: THIS TEST HAS BEEN DISABLED IN ALLTESTS
     */

    private static final String FILE_NAME = "test.txt"; //$NON-NLS-1$

    private static final String FOLDER_NAME = "My Folder"; //$NON-NLS-1$

    private static final String SPECIALFOLDER_KIND = "testbuilderfolder"; //$NON-NLS-1$

    private static final String PROJECT_NAME = "BuilderTestProject1"; //$NON-NLS-1$

    private static final String BUILDER_ID =
            "com.tibco.xpd.core.builder.test.coreTestBuilder"; //$NON-NLS-1$

    private IFolder folder;

    private IProject project;

    private final IWorkspace workspace = ResourcesPlugin.getWorkspace();

    @Override
    protected void setUp() throws Exception {
        System.out.println("-->Start SetUp"); //$NON-NLS-1$
        super.setUp();

        // setup the project
        project = workspace.getRoot().getProject(PROJECT_NAME);

        final IProjectDescription description =
                workspace.newProjectDescription(project.getName());

        CreateXpdProjectOperation op =
                new XpdProjectWizard.CreateXpdProjectOperation(project,
                        description, null);
        System.out.println(String.format("-- Creating project: %s ...", //$NON-NLS-1$
                PROJECT_NAME));
        op.run(new NullProgressMonitor());
        System.out.println(String.format("-- Project: %s created.", //$NON-NLS-1$
                PROJECT_NAME));

        System.out.println("-- Adding test builder to project..."); //$NON-NLS-1$
        ProjectUtil.addBuilderToProject(project, BUILDER_ID);
        System.out.println("-- Builder added to project"); //$NON-NLS-1$

        TestUtil.waitForJobs();
        TestProjectBuilder.resetLog();

        System.out.println("-->End SetUp"); //$NON-NLS-1$
    }

    @Override
    protected void tearDown() throws Exception {

        // delete project
        System.out.println("-->Delete project"); //$NON-NLS-1$
        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                assertNotNull("Project is not available", project); //$NON-NLS-1$

                project.delete(true, true, new NullProgressMonitor());
            }

        }, null);
        System.out.println("-->Project deleted"); //$NON-NLS-1$

        super.tearDown();
    }

    @SuppressWarnings("restriction")
    public void testBuild() {
        assertNotNull("Project is not available", project); //$NON-NLS-1$

        try {
            // Test creating special folder
            _testCreateFolder();
            // Test creating file
            _testCreateFile();
            // Test deleting file
            _testDeleteFile();
            // Test deleting folder
            _testDeleteFolder();

        } catch (CoreException e) {
            fail("Failed: " + e.getLocalizedMessage()); //$NON-NLS-1$
            e.printStackTrace();
        }
    }

    /**
     * Create the test special folder and check number of times the builder was
     * called.
     * 
     * @throws CoreException
     */
    private void _testCreateFolder() throws CoreException {
        // create concept folder
        System.out.println("--> Creating test special folder ..."); //$NON-NLS-1$
        ProjectConfig pc = getProjectConfig();

        // create folder and mark it as a special folder
        folder = pc.getProject().getFolder(FOLDER_NAME);

        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                folder.create(true, true, monitor);
            }
        }, null);

        TestUtil.waitForJobs();

        BuildLog log = TestProjectBuilder.getLog();

        /*
         * One builds expected
         */
        assertEquals(String
                .format("Number of times builder called after creating folder %s", //$NON-NLS-1$
                        FOLDER_NAME),
                1,
                log.buildCount);

        TestProjectBuilder.resetLog();

        pc.getSpecialFolders().addFolder(folder, SPECIALFOLDER_KIND);

        System.out.println("--> Special folder created"); //$NON-NLS-1$

        TestUtil.waitForJobs();

        // Only one build expected
        assertEquals("Number of times builder called after marking special folder", //$NON-NLS-1$
                1,
                log.buildCount);

        BuildData buildData = log.data.get(0);

        // Full build not expected
        if (buildData.buildKind != IncrementalProjectBuilder.FULL_BUILD) {

            // Expect config file in deltas
            assertEquals("Affected deltas in build", 1, buildData.deltas.length); //$NON-NLS-1$

            assertTrue("Expected config file in resource deltas", //$NON-NLS-1$
                    buildData.deltas[0].getResource().getName()
                            .equals(XpdResourcesPlugin.PROJECTCONFIGFILE));
        }

        TestProjectBuilder.resetLog();
    }

    /**
     * Create the file in the special folder and check the kind and number of
     * times the builder was called
     * 
     * @throws CoreException
     */
    private void _testCreateFile() throws CoreException {

        System.out.println("-->Creating file"); //$NON-NLS-1$

        final IFile file = folder.getFile(FILE_NAME);
        final ByteArrayInputStream inStream =
                new ByteArrayInputStream("This is a test file".getBytes()); //$NON-NLS-1$

        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                file.create(inStream, false, monitor);
            }
        }, null);

        TestUtil.waitForJobs();

        assertTrue("Failed to create test file", file.isAccessible()); //$NON-NLS-1$

        BuildLog log = TestProjectBuilder.getLog();

        assertEquals("Number of times builder called after creating file", //$NON-NLS-1$
                1,
                log.buildCount);

        if (log.data.get(0).buildKind == IncrementalProjectBuilder.AUTO_BUILD) {
            IResourceDelta[] deltas = log.data.get(0).deltas;
            assertEquals("Number of build deltas", 1, deltas.length); //$NON-NLS-1$

            IResource resource = deltas[0].getResource();

            assertEquals("Delta resource", folder, resource); //$NON-NLS-1$

        } else {
            fail(String.format("Expected AUTO BUILD, got build kind %d", //$NON-NLS-1$
                    log.data.get(0).buildKind));
        }

        TestProjectBuilder.resetLog();
    }

    /**
     * Delete the file and check the kind and number of times the builder was
     * called
     * 
     * @throws CoreException
     */
    private void _testDeleteFile() throws CoreException {
        System.out.println("--> Deleting file"); //$NON-NLS-1$

        final IResource file = folder.findMember(FILE_NAME);
        assertNotNull("Cannot access file", file); //$NON-NLS-1$
        // Delete the file
        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                file.delete(true, monitor);
            }
        }, null);

        TestUtil.waitForJobs();

        assertFalse("File not deleted", file.isAccessible()); //$NON-NLS-1$

        BuildLog log = TestProjectBuilder.getLog();

        assertEquals("Number of times builder called after deleting file", //$NON-NLS-1$
                1,
                log.buildCount);

        BuildData buildData = log.data.get(0);

        if (buildData.buildKind == IncrementalProjectBuilder.AUTO_BUILD) {
            IResourceDelta[] deltas = log.data.get(0).deltas;
            assertEquals("Number of build deltas", 1, deltas.length); //$NON-NLS-1$

            IResource resource = deltas[0].getResource();

            assertEquals("Delta resource", folder, resource); //$NON-NLS-1$
        } else {
            fail(String.format("Expected AUTO BUILD, got build kind %d", //$NON-NLS-1$
                    buildData.buildKind));
        }

        TestProjectBuilder.resetLog();
    }

    /**
     * Delete the special folder and check kind and number of times builder was
     * called.
     * 
     * @throws CoreException
     */
    private void _testDeleteFolder() throws CoreException {
        System.out.println("--> Deleting folder"); //$NON-NLS-1$
        workspace.run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                folder.delete(true, monitor);
            }
        }, null);
        TestUtil.waitForJobs();

        assertFalse("Folder not deleted", folder.isAccessible()); //$NON-NLS-1$
        BuildLog buildLog = TestProjectBuilder.getLog();

        assertEquals("Number of builds after deleting folder", //$NON-NLS-1$
                1,
                buildLog.buildCount);

        BuildData buildData = buildLog.data.get(0);

        if (buildData.buildKind == IncrementalProjectBuilder.AUTO_BUILD) {
            IResourceDelta[] deltas = buildData.deltas;

            /*
             * Expect 2 delta - the folder and .config file
             */
            assertEquals("Number of deltas in build after deleting folder", //$NON-NLS-1$
                    2,
                    deltas.length);

            short result = 0;

            for (IResourceDelta delta : deltas) {
                if (delta.getResource().equals(folder)) {
                    result |= 0x1;
                } else if (delta.getResource().getName()
                        .equals(XpdResourcesPlugin.PROJECTCONFIGFILE)) {
                    result |= 0x2;
                } else {
                    result |= 0x4;
                }
            }

            if (result != 0x3) {
                fail("Not found the expected deltas in build after deleting folder"); //$NON-NLS-1$
            }

        } else {
            fail(String.format("Expected AUTO BUILD, got build kind %d", //$NON-NLS-1$
                    buildData.buildKind));
        }
    }

    /**
     * Get the test project config
     * 
     * @return
     */
    private ProjectConfig getProjectConfig() {
        ProjectConfig pc = null;
        XpdResourcesPlugin rp = XpdResourcesPlugin.getDefault();
        if (project != null) {
            pc = rp.getProjectConfig(project);
        }
        assertNotNull("Project Config is not avaiable", pc); //$NON-NLS-1$
        return pc;
    }
}
