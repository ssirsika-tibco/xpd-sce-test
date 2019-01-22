package com.tibco.xpd.ant.tasks;

import org.eclipse.ui.PlatformUI;

import com.tibco.amf.tools.composite.resources.internal.tp.SetTargetPlatformStartup;
import com.tibco.amf.tools.packager.util.CmdLineUtils;

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
            SetTargetPlatformStartup setTargetPlatformOp =
                    new SetTargetPlatformStartup();
            setTargetPlatformOp.earlyStartup();
        }
        if (buildAllProjects) {
            CmdLineUtils.buildAllProjects();
        }
    }
}
