/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Test the project config file. The following tests will be run:
 * <p>
 * {@link #testCreateMoveDeleteSingleInstanceSpecialFolder()} (after each step
 * the project config will be verified):
 * <ol>
 * <li>Create special folder</li>
 * <li>Move special folder into another folder</li>
 * <li>Move folder containing special folder to another project</li>
 * <li>Delete special folder</li>
 * </ol>
 * </p>
 * <p>
 * {@link #testCreateMoveDeleteMultipleInstanceSpecialFolder()}: This will carry
 * out the same test as above but with a multiple-instance special folder.
 * </p>
 * <p>
 * {@link #testCreatingTwoSingleInstanceSpecialFolders()}: This will test
 * creating of two special folders of a single-instance kind, and
 * {@link #testCreatingTwoMultipleInstanceSpecialFolders()} will repeat this
 * test for a multiple-instance special folder.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderTest extends TestCase {
    private static final String PROJECT_NAME = "SpecialFolderTestProject";

    private static final String TEST_ASSET_ID = "com.tibco.xpd.test.assettype";

    private static final String TARGET_PROJECT_NAME = "Target Project";

    private static final String SINGLETON_SPECIAL_FOLDER_KIND = "testSingleton";

    private static final String MULTIPLE_SPECIAL_FOLDER_KIND = "testMultiple";

    private static final String TEST_FOLDER = "test folder";

    private static final String TARGET_FOLDER = "Target";

    private static final IWorkspace workspace = ResourcesPlugin.getWorkspace();

    private IProject project;

    private ProjectConfig srcConfig;

    private IProject targetProject;

    private ProjectConfig targetConfig;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtil.createProject(PROJECT_NAME);
        TestUtil.createProject(TARGET_PROJECT_NAME);

        project = workspace.getRoot().getProject(PROJECT_NAME);

        assertNotNull("Source project not created", project);

        srcConfig = TestUtil.getProjectConfig(project);
        srcConfig.addAssetTask(TEST_ASSET_ID);

        targetProject = workspace.getRoot().getProject(TARGET_PROJECT_NAME);

        assertNotNull("Target project not created", targetProject);

        targetConfig = TestUtil.getProjectConfig(targetProject);
        targetConfig.addAssetTask(TEST_ASSET_ID);
    }

    @Override
    protected void tearDown() throws Exception {
        // Remove the projects
        TestUtil.removeProject(PROJECT_NAME);
        TestUtil.removeProject(TARGET_PROJECT_NAME);
        super.tearDown();
    }

    /**
     * Test the creation, move and deletion of a single instance special folder.
     * 
     * @throws Exception
     */
    public void testCreateMoveDeleteSingleInstanceSpecialFolder()
            throws Exception {
        _testCreateMoveDeleteSpecialFolder(SINGLETON_SPECIAL_FOLDER_KIND);
    }

    /**
     * Test the creation, move and deletion of a multiple-instance special
     * folder.
     * 
     * @throws Exception
     */
    public void testCreateMoveDeleteMultipleInstanceSpecialFolder()
            throws Exception {
        _testCreateMoveDeleteSpecialFolder(MULTIPLE_SPECIAL_FOLDER_KIND);
    }

    /**
     * Test creating of two special folders of the single-instance kind special
     * folder.
     * 
     * @throws Exception
     */
    public void testCreatingTwoSingleInstanceSpecialFolders() throws Exception {
        _testCreateTwoSpecialFolders(SINGLETON_SPECIAL_FOLDER_KIND, false);
    }

    /**
     * Test creting of two special folders of the multiple-instance kind special
     * folder.
     * 
     * @throws Exception
     */
    public void testCreatingTwoMultipleInstanceSpecialFolders()
            throws Exception {
        _testCreateTwoSpecialFolders(MULTIPLE_SPECIAL_FOLDER_KIND, true);
    }

    /**
     * Test the creation, move and deletion of the given special folder kind.
     * 
     * @param specialFolderKind
     *            kind of special folder to test
     * 
     * @throws CoreException
     */
    private void _testCreateMoveDeleteSpecialFolder(String specialFolderKind)
            throws CoreException {
        System.out
                .println("-->Testing create, move and delete of special folder");

        IFile file = project.getFile(XpdResourcesPlugin.PROJECTCONFIGFILE);
        assertTrue("config file not created", file.isAccessible());

        /*
         * Create special folder
         */
        System.out.println("Test creating special folder...");
        SpecialFolder sf =
                TestUtil.createSpecialFolder(project,
                        TEST_FOLDER,
                        specialFolderKind);

        assertNotNull("Failed to get special folder resource", sf.getFolder());

        // Check config
        assertEquals("Number of special folders after create", 1, srcConfig
                .getSpecialFolders().getFoldersOfKind(specialFolderKind).size());

        /*
         * Move special folder into another folder
         */
        IFolder container = _testMoveSpecialFolder(sf);

        /*
         * Move folder containing special folder into another project
         */
        IFolder target =
                _testMoveFolderIntoAnotherProject(container, specialFolderKind);

        /*
         * Delete folder containing special folder
         */
        _testDeleteSpecialFolder(target, specialFolderKind);
    }

    /**
     * Test creating two special folders of the given kind
     * 
     * @param specialFolderKind
     *            kind of special folder to test
     * @param multipleAllowed
     *            <code>false</code> if this is a single-instance special
     *            folder.
     */
    private void _testCreateTwoSpecialFolders(String specialFolderKind,
            boolean multipleAllowed) {
        Exception expectedException = null;

        System.out.println("-->Testing creating two special folders");

        TestUtil.createSpecialFolder(project, TEST_FOLDER, specialFolderKind);

        // If multiple not allowed then expected exception in second attempt
        try {
            TestUtil.createSpecialFolder(project,
                    TARGET_FOLDER,
                    specialFolderKind);
        } catch (IllegalArgumentException e) {
            expectedException = e;
        }

        if (!multipleAllowed) {
            assertNotNull("Expected IllegalArgumentException when trying to add two special folders",
                    expectedException);
        } else {
            assertNull("Did not expect IllegalArgumentException when trying to add two special folders",
                    expectedException);
        }
    }

    /**
     * Test moving of special folder into another folder - the config should be
     * updated accordingly
     * 
     * @param sf
     *            <code>SpecialFolder</code> to move into folder
     * @return the container <code>IFolder</code> that contains the
     *         <code>SpecialFolder</code>
     * @throws CoreException
     */
    private IFolder _testMoveSpecialFolder(SpecialFolder sf)
            throws CoreException {
        System.out
                .println("-->Testing moving special folder to another folder");
        IFolder sfResource = sf.getFolder();

        // Create folder to move special folder into
        final IFolder folder = project.getFolder(TARGET_FOLDER);
        workspace.run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                folder.create(false, true, monitor);
            }
        }, null);
        TestUtil.waitForJobs(1000);
        assertTrue("Cannot access folder " + TARGET_FOLDER,
                folder.isAccessible());

        IPath destination = folder.getFullPath().append(sfResource.getName());

        // Move special folder
        sfResource.move(destination, false, null);
        TestUtil.waitForJobs(1500); // JA: Increased as the test was
                                    // occasionally failing.

        // Config should still show one special folder
        assertEquals("Number of special folder after move to folder",
                1,
                srcConfig.getSpecialFolders().getFoldersOfKind(sf.getKind())
                        .size());

        return folder;
    }

    /**
     * Test moving the folder that contains the <code>SpecialFolder</code> into
     * another project.
     * 
     * @param container
     *            the <code>IFolder</code> containing the
     *            <code>SpecialFolder</code>
     * @param specialFolderKind
     *            kind of special folder being tested
     * @return the <code>IFolder</code> that is moved into the target project
     * @throws CoreException
     */
    private IFolder _testMoveFolderIntoAnotherProject(IFolder container,
            String specialFolderKind) throws CoreException {
        IFolder target;

        System.out
                .println("-->Test moving special folder into another project");
        // Move the container into the target project
        IPath destination = targetProject.getFullPath().append(TARGET_FOLDER);
        container.move(destination, false, null);
        TestUtil.waitForJobs(1000);

        target = (IFolder) targetProject.findMember(TARGET_FOLDER);

        assertNotNull("Failed to get moved folder", target);

        // Source project should have no special folders
        assertEquals("Special folders in source project after move",
                0,
                srcConfig.getSpecialFolders()
                        .getFoldersOfKind(specialFolderKind).size());

        // Target project should have 1 special folder
        assertEquals("Special folders in target project after move",
                1,
                targetConfig.getSpecialFolders()
                        .getFoldersOfKind(specialFolderKind).size());

        return target;
    }

    /**
     * Test deleting of folder that contains the special folder (which is now in
     * the target project). This should remove the special folder from the
     * project config.
     * 
     * @param container
     *            the <code>IFolder</code> containing the special folder in the
     *            target project
     * @param specialFolderKind
     *            kind of special folder being tested
     * @throws CoreException
     */
    private void _testDeleteSpecialFolder(final IFolder container,
            String specialFolderKind) throws CoreException {
        System.out
                .println("-->Testing deleting of folder containing special folder");
        workspace.run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                container.delete(true, monitor);
            }
        }, null);
        TestUtil.waitForJobs(1000);

        assertEquals("Special folders in the target project after delete of parent folder",
                0,
                targetConfig.getSpecialFolders()
                        .getFoldersOfKind(specialFolderKind).size());
    }

}
