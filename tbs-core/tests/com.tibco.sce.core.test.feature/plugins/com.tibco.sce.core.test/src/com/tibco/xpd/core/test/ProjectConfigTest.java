/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;

/**
 * Test the project config file. The following test will be run:
 * <p>
 * {@link #testConfigFile() testConfigFile()}:
 * <ol>
 * <li>Delete config file</li>
 * <li>Check if config file is re-created when project config accessed</li>
 * </ol>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class ProjectConfigTest extends TestCase {

    private static final String PROJECT_NAME = "SpecialFolderTestProject";

    private IProject project;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        TestUtil.createProject(PROJECT_NAME);

        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);

        assertNotNull("Project not created", project);
    }

    @Override
    protected void tearDown() throws Exception {
        // Remove the project
        TestUtil.removeProject(PROJECT_NAME);
        super.tearDown();
    }

    /**
     * Test the config file. Delete config file to check if it's recreated.
     * 
     * @throws CoreException
     */
    public void testDeletingConfigFile() throws CoreException {
        /*
         * JA: This needed to be wrapped inside atomic workspace operation as
         * the .config file was immediately recreated during notification
         * processing and then test was failing on:
         * assertFalse("Config file was not deleted", file.isAccessible());
         */
        IWorkspaceRunnable configTestRunnable = new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                System.out.println("Testing config file");
                IFile file =
                        project.getFile(XpdResourcesPlugin.PROJECTCONFIGFILE);
                assertTrue("config file not created", file.isAccessible());

                /*
                 * Delete config file
                 */
                System.out.println("-->Deleting the config file");

                // TODO Auto-generated method stub
                file.delete(true, null);

                assertFalse("Config file was not deleted", file.isAccessible());

                /*
                 * Access project config to recreate it
                 */
                ProjectConfig config = TestUtil.getProjectConfig(project);

                assertNotNull("Failed to get config file after deleting it",
                        config);

                file = project.getFile(XpdResourcesPlugin.PROJECTCONFIGFILE);
                assertTrue("config file not re-created after delete",
                        file.isAccessible());

                System.out.println("Completed testing of config file");
            }
        };
        ResourcesPlugin.getWorkspace().run(configTestRunnable,
                new NullProgressMonitor());
    }
}
