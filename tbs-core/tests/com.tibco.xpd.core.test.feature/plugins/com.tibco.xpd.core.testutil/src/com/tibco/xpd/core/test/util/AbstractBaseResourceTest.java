/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;

/**
 * A base junit test that, via simple abstract methods, can create a studio test
 * environment (including special folders, folders and files) leaving the sub
 * class to implement its test methods once the setUp has complete.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractBaseResourceTest extends TestCase {

    //
    // ===============================================================
    //
    // ABSTRACT METHODS FOR RESOURCES / VALIDATIONS FOR SPECIFIC TEST
    //
    // ===============================================================

    protected abstract String getTestName();

    protected abstract TestResourceInfo[] getTestResources();

    protected abstract String getTestPlugInId();

    protected boolean cleanProjectAtEnd = true;

    //
    // ===============================================================
    //
    // THE MAIN TESTING CODE (SUBCLASS JUST PROVIDES TEST INFO).
    //
    // ===============================================================

    private Set<IProject> projects = new HashSet<IProject>();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                try {
                    // Create the project.
                    projects = new HashSet<IProject>();

                    String testPlugInId = getTestPlugInId();
                    TestResourceInfo[] testResources = getTestResources();

                    for (TestResourceInfo testRes : testResources) {
                        IFile file = testRes.createFile(testPlugInId);
                        if (file != null) {
                            projects.add(file.getProject());
                        }
                    }

                    for (IProject project : projects) {
                        configureProject(project);
                    }
                } catch (Exception e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            TestUtilPlugin.PLUGIN_ID, e.getMessage(), e));
                }
            }
        },
                new NullProgressMonitor());
    }

    /**
     * Gives the sub-class opportunity to perform any post-creation
     * configuration of test project.
     * 
     * @param testProject
     */
    protected void configureProject(IProject testProject) {
        return;
    }

    @Override
    protected void tearDown() throws Exception {

        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().closeAllEditors(false);
        } catch (Exception e) {
        }

        if (projects != null) {
            if (cleanProjectAtEnd) {
                for (IProject project : projects) {
                    project.close(null);

                    TestUtil.removeProject(project.getName());
                }
            }
        }

        super.tearDown();

        return;
    }

    /**
     * COnvenience method to get the given project name.
     * 
     * @param projectName
     * @return Project or null if not found.
     */
    public IProject getTestProject(String projectName) {
        return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    }

    /**
     * Given only the file name return the test workspace file for it.
     * <p>
     * This method is for convenience and obviously will return the FIRST found
     * regardless of project etc.
     * <p>
     * For more accurate results use {@link #getTestResources()} and iterate.
     * 
     * @param fileName
     * 
     * @return First file in test resources that matches fileName or null if not
     *         s* there.
     */
    public IFile getTestFile(String fileName) {
        TestResourceInfo[] testResources = getTestResources();

        for (TestResourceInfo testRes : testResources) {
            IFile file = testRes.getTestFile();
            if (fileName.equals(file.getName())) {
                return file;
            }
        }

        return null;
    }

    /**
     * Simple test to check that the selected test resources were successfully
     * restored.
     * 
     * @throws Exception
     */
    protected void checkTestFilesCreated() throws Exception {
        TestResourceInfo[] testResources = getTestResources();

        for (TestResourceInfo testRes : testResources) {
            IFile file = testRes.getTestFile();
            if (!file.exists()) {
                fail("Failed to create test Resource: " //$NON-NLS-1$
                        + testRes.getTokenisedPath());
            }
        }

        return;
    }

}
