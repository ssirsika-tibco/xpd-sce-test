/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.core.test;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * 
 * <p>
 * <i>Created: 3 Nov 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class SpecialFolderUtilTest extends TestCase {

    private static final String SPECIAL_FOLDER_NAME = "MultipleTestFolder";
    private static final String REFERENCE_SPECIAL_FOLDER_NAME = "ReferenceMultipleTestFolder";

    private static final String PROJECT_NAME = "TestProject";
    private static final String REFERENCED_PROJECT_NAME = "ReferencedTestProject";

    private static final String MULTIPLE_SPECIAL_FOLDER_KIND = "testMultiple";
    private static final String TEST_ASSET_ID = "com.tibco.xpd.test.assettype";

    private IProject project;
    private IProject referencedProject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        // Create project
        TestUtil.createProject(PROJECT_NAME);
        project = workspace.getRoot().getProject(PROJECT_NAME);
        assertNotNull("Project not created", project);
        ProjectConfig projectConfig = TestUtil.getProjectConfig(project);
        projectConfig.addAssetTask(TEST_ASSET_ID);
        TestUtil.createSpecialFolder(project, SPECIAL_FOLDER_NAME,
                MULTIPLE_SPECIAL_FOLDER_KIND);

        // Create target project
        TestUtil.createProject(REFERENCED_PROJECT_NAME);
        referencedProject = workspace.getRoot().getProject(
                REFERENCED_PROJECT_NAME);
        assertNotNull("Referenced project not created", referencedProject);
        ProjectConfig referencedProjectConfig = TestUtil
                .getProjectConfig(referencedProject);
        referencedProjectConfig.addAssetTask(TEST_ASSET_ID);
        TestUtil.createSpecialFolder(referencedProject,
                REFERENCE_SPECIAL_FOLDER_NAME, MULTIPLE_SPECIAL_FOLDER_KIND);

        // Add project to referenced projects.
        ProjectUtil.addReferenceProject(project, referencedProject);

        // Check if referenced project is really referenced.
        Set<IProject> refProjects = new HashSet<IProject>();
        ProjectUtil.getReferencedProjectsHierarchy(project, refProjects);
        assertTrue(referencedProject.contains(referencedProject));
    }

    @Override
    protected void tearDown() throws Exception {
        // Remove the projects
        TestUtil.removeProject(PROJECT_NAME);
        TestUtil.removeProject(REFERENCED_PROJECT_NAME);
        TestUtil.waitForJobs();
        super.tearDown();
    }

    /**
     * Test method for
     * {@link com.tibco.xpd.resources.util.SpecialFolderUtil#resolveSpecialFolderRelativePath(org.eclipse.core.resources.IProject, java.lang.String, java.lang.String, boolean)}
     * .
     */
    public void testResolveSpecialFolderRelativePath() throws Exception {
        IFile file = referencedProject.getFolder(REFERENCE_SPECIAL_FOLDER_NAME)
                .getFile("test.txt");

        file.create(new ByteArrayInputStream("Test file content".getBytes()),
                true, null);
        assertTrue(file.exists());

        // Test the method without fourth parameter (default is false).
        IFile resFile1 = SpecialFolderUtil.resolveSpecialFolderRelativePath(
                project, MULTIPLE_SPECIAL_FOLDER_KIND, "test.txt");
        assertNull(resFile1);

        // Test the method with parameter set to true so it should find file in
        // ref
        // project.
        IFile resFile2 = SpecialFolderUtil.resolveSpecialFolderRelativePath(
                project, MULTIPLE_SPECIAL_FOLDER_KIND, "test.txt", true);
        assertNotNull(resFile2);
        assertTrue(resFile2.exists());

    }
}
