/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Tests the moving of special folders from one project to another where the
 * target project doesn't have the required asset type configured. The folders
 * should be moved but should not be marked as special folders. The test is
 * repeated with the target project having the asset configured - this time the
 * special folders are retained.
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderAssetTest extends TestCase {

    private static final String PROJECT_NAME = "AssetTestProject";

    private static final String TARGET_PROJECT_NAME = "Target Project";

    private static final String TEST_ASSET_ID = "com.tibco.xpd.test.assettype";

    private static final String SINGLETON_SPECIAL_FOLDER_KIND = "testSingleton";

    private static final String MULTIPLE_SPECIAL_FOLDER_KIND = "testMultiple";

    private static final String S_TEST_FOLDER = "test folder Single";

    private static final String M_TEST_FOLDER = "test folder Multiple";

    private static final IWorkspace workspace = ResourcesPlugin.getWorkspace();

    private IProject project;

    private ProjectConfig srcConfig;

    private IProject targetProject;

    private ProjectConfig targetConfig;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Create project
        TestUtil.createProject(PROJECT_NAME);
        project = workspace.getRoot().getProject(PROJECT_NAME);
        assertNotNull("Source project not created", project);
        srcConfig = TestUtil.getProjectConfig(project);
        srcConfig.addAssetTask(TEST_ASSET_ID);

        // Create target project
        TestUtil.createProject(TARGET_PROJECT_NAME);
        targetProject = workspace.getRoot().getProject(TARGET_PROJECT_NAME);
        assertNotNull("Target project not created", targetProject);
        targetConfig = TestUtil.getProjectConfig(targetProject);
    }

    @Override
    protected void tearDown() throws Exception {
        // Remove the projects
        TestUtil.removeProject(PROJECT_NAME);
        TestUtil.removeProject(TARGET_PROJECT_NAME);
        TestUtil.waitForJobs();
        super.tearDown();
    }

    /**
     * Test move of special folders to a project that does not have the asset
     * type configured. The move will occur but the folders will not be marked
     * as special folders.
     * 
     * @throws Exception
     */
    public void testMoveSpecialFoldersToProjectWithoutAssetConfiguration()
            throws Exception {
        _testMoveSpecialFolderToTargetProject(false);
    }

    /**
     * Test move of special folders to a project that does not have the asset
     * type configured. The move will occur and the folders will be marked as
     * special folders.
     * 
     * @throws Exception
     */
    public void testMoveSpecialFoldersToProjectWithAssetConfigured()
            throws Exception {
        targetConfig.addAssetTask(TEST_ASSET_ID);
        _testMoveSpecialFolderToTargetProject(true);
    }

    /**
     * Test the move of special folders (single and multiple instance) to a
     * target project. The results will be published in relation to the value
     * set in <i>expectSuccess</i>.
     * 
     * @param expectSuccess
     *            <code>true</code> if the move of special folders should be
     *            successful.
     * @throws CoreException
     */
    private void _testMoveSpecialFolderToTargetProject(boolean expectSuccess)
            throws CoreException {
        // Create special folders
        final SpecialFolder singleInstFolder =
                TestUtil.createSpecialFolder(project,
                        S_TEST_FOLDER,
                        SINGLETON_SPECIAL_FOLDER_KIND);
        final SpecialFolder multiInstFolder =
                TestUtil.createSpecialFolder(project,
                        M_TEST_FOLDER,
                        MULTIPLE_SPECIAL_FOLDER_KIND);
        final IPath s_destination =
                targetProject.getFullPath().append(S_TEST_FOLDER);
        final IPath m_destination =
                targetProject.getFullPath().append(M_TEST_FOLDER);

        // Move special folders
        workspace.run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                singleInstFolder.getFolder().move(s_destination, true, monitor);
                multiInstFolder.getFolder().move(m_destination, true, monitor);
            }
        },
                null);
        TestUtil.waitForJobs(1000);

        int numExpected = expectSuccess ? 2 : 0;

        assertEquals("Number of special folders in target project after move",
                numExpected,
                targetConfig.getSpecialFolders().getFolders().size());
    }

}
