package com.tibco.xpd.ant.tasks;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.PlatformUI;

public class TaskUtil {
    /**
     * This method is to set the target platform before executing some of the
     * ant tasks like createDAA. Also the projects are built after the target
     * platform is set.
     */
    public static void initTargetPlatformIfNeeded(boolean buildAllProjects) {
        if (!PlatformUI.isWorkbenchRunning()) {
            // In the case of running this ant task in a new workspace, the
            // target platform will not be set because the
            // SetTargetPlatformStartup#earlyStartup() gets called only after
            // the Workbench is initializes. But in the command-line mode
            // (headless-mode) there is no Workbench and so we need to
            // explicitly call this.

            /*
             * ACE-125 - not sure if equivalent of this will be required for ACE
             */
            throw new RuntimeException(
                    "ACE-125 - SetTargetPlatformStartup no longer available for AMX target platform setup");
            // SetTargetPlatformStartup setTargetPlatformOp =
            // new SetTargetPlatformStartup();
            // setTargetPlatformOp.earlyStartup();
        }
        if (buildAllProjects) {
            buildAllProjects();
        }
    }

    /**
     * Sid ACE-125 - Shamelessly copied from
     * com.tibco.amf.tools.packager.util.CmdLineUtils
     */
    public static void buildAllProjects() {
        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

        IProject[] projects = wsRoot.getProjects();
        IProject[] var5 = projects;
        int var4 = projects.length;
        for (int var3 = 0; var3 < var4; ++var3) {
            IProject project = var5[var3];
            try {
                IProgressMonitor progressMonitor = new NullProgressMonitor();
                project.build(15, progressMonitor);

            } catch (CoreException var7) {
                var7.printStackTrace();
            }
        }

    }

}
