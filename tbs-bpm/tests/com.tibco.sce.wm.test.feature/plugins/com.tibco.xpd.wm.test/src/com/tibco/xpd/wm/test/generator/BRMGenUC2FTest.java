/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.test.generator;

import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.n2.brm.utils.BRMSchemaUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.wm.test.WMTestActivator;

/**
 * @author Jan Arciuchiewicz
 */
public class BRMGenUC2FTest extends TestCase {

    private ProjectImporter projectImporter;

    @Override
    protected void setUp() throws Exception {
        projectImporter =
                ProjectImporter
                        .createPluginProjectImporter(WMTestActivator.PLUGIN_ID,
                                Arrays
                                        .asList("resources/UC2FWorkItemScripts.zip", //$NON-NLS-1$
                                                "resources/UC2ANoneStartEventWithUserTasks.zip")); //$NON-NLS-1$
        assertTrue(projectImporter.performImport());
    }

    public void testWMandWTagainstSchema() throws Exception {
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                migrateXpdls(root);
            }
        }, new NullProgressMonitor());

        // Wait for builds.
        TestUtil.waitForJobsEx(ResourcesPlugin.FAMILY_AUTO_BUILD);

        IProject project = root.getProject("UC2FWorkItemScripts"); //$NON-NLS-1$
        assertTrue(project.isAccessible());
        IFolder brmFolder =
                project.getFolder(BRMGenerator.BRM_MODULES_SPECIAL_FOLDER_NAME);
        ProjectUtil.createFolder(brmFolder, false, new NullProgressMonitor());
        BRMGenerator.getInstance().generateBRMModules(project,
                brmFolder,
                "12345678"); //$NON-NLS-1$
        IFile wtFile = brmFolder.getFile("wt.xml"); //$NON-NLS-1$
        assertTrue(wtFile.exists());
        assertTrue(BRMSchemaUtil.validateAgainstBRMXSD(wtFile) == Status.OK_STATUS);
        IFile wmFile = brmFolder.getFile("wm.xml"); //$NON-NLS-1$
        assertTrue(wmFile.exists());
        assertTrue(BRMSchemaUtil.validateAgainstBRMXSD(wmFile) == Status.OK_STATUS);

    }

    private void migrateXpdls(IWorkspaceRoot root) {
        WorkingCopy wc;
        wc =
                XpdResourcesPlugin
                        .getDefault()
                        .getWorkingCopy(root
                                .getFile(new Path(
                                        "/UC2FWorkItemScripts/Process Packages/WorkItemScriptsPackage.xpdl"))); //$NON-NLS-1$
        assertNotNull(wc);
        TestUtil.migratePackage(wc);

        wc =
                XpdResourcesPlugin
                        .getDefault()
                        .getWorkingCopy(root
                                .getFile(new Path(
                                        "/UC2ANoneStartEventWithUserTasks/Process Packages/UC2ANoneStartEventWithUserTask.xpdl"))); //$NON-NLS-1$
        assertNotNull(wc);
        TestUtil.migratePackage(wc);

    }

    @Override
    protected void tearDown() throws Exception {
        // assertTrue(projectImporter.performDelete());
    }
}
