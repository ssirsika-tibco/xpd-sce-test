/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.ant.tasks;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

import com.tibco.xpd.core.test.util.AbstractBuildingBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 *
 *
 * @author pwatson
 * @since 24 Apr 2019
 */
@SuppressWarnings("nls")
public class GenerateRascTaskTest extends AbstractBuildingBaseResourceTest {

    private TestResourceInfo[] orgmodelResourceInfo = {
            new TestResourceInfo("resources/AntRascTest",
                    "AntProject/project/build.xml"),
            new TestResourceInfo("resources/AntRascTest",
                    "AntProject/project/build-no-destdir.xml"),
            new TestResourceInfo("resources/OrgModelRascTest",
                    "RASC/Organization{om}/OrganizationModel.om") };

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     */
    @Override
    protected String getTestName() {
        return "RASC Transform Ant Task Test";
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        return orgmodelResourceInfo;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test";
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

        workingCopy.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
    }

    public void testExecuteTask() throws Exception {
        checkTestFilesCreated();
        IFile testFile = getTestFile("OrganizationModel.om");
        if (testFile == null) {
            throw new NullPointerException("Unable to find test file");
        }

        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        assertNotNull(workspaceRoot.getProject("RASC"));
        assertNotNull(workspaceRoot.getProject("AntProject"));

        // test that the rasc file does not yet exist
        assertFalse(workspaceRoot.getProject("AntProject").getFolder("project")
                .getFolder("RascOut").getFile("RASC.rasc").exists());

        runLaunch("AntProject", "project/build.xml");

        IProject project = workspaceRoot.getProject("AntProject");
        IFolder folder = project.getFolder("project").getFolder("RascOut");
        folder.refreshLocal(2, null);

        // wait for refresh to complete - can't see how else to do this
        Object lock = new Object();
        synchronized (lock) {
            lock.wait(2000);
        }

        IFile rascFile = folder.getFile("RASC.rasc");
        assertTrue(rascFile.exists());
    }

    /**
     * Test that the rasc file is output to the default folder when no destdir
     * is specified.
     * 
     * @throws Exception
     */
    public void testExecuteNoDestDir() throws Exception {
        checkTestFilesCreated();
        IFile testFile = getTestFile("OrganizationModel.om");
        if (testFile == null) {
            throw new NullPointerException("Unable to find test file");
        }

        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        assertNotNull(workspaceRoot.getProject("RASC"));
        assertNotNull(workspaceRoot.getProject("AntProject"));

        // test that the rasc file does not yet exist
        assertFalse(workspaceRoot.getProject("RASC")
                .getFolder(GenerateRascTask.DEFAULT_DEST_DIR)
                .getFile("RASC.rasc").exists());

        runLaunch("AntProject", "project/build-no-destdir.xml");

        // uses default dir within project's root
        IProject project = workspaceRoot.getProject("RASC");
        IFolder folder = project.getFolder(GenerateRascTask.DEFAULT_DEST_DIR);
        folder.refreshLocal(2, null);

        // wait for refresh to complete - can't see how else to do this
        Object lock = new Object();
        synchronized (lock) {
            lock.wait(3000);
        }

        IFile rascFile = folder.getFile("RASC.rasc");
        assertTrue(rascFile.exists());
    }
}
