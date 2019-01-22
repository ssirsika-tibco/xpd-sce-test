/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.daa.internal.deploy;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.navigator.CommonDropAdapter;

import com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.n2.daa.internal.packager.DAAExportUtils;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;

/**
 * Drop assistant to support deploying BPM project by dropping it onto AMX
 * Administrator server.
 * 
 * @author Jan Arciuchiewicz
 */
public class AdminDropAssistantDelegate extends AbstractDeployDragDropAssistant {

    /**
     * @see com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant#isApplicableProjectType(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected boolean isApplicableProjectType(IProject project) {
        DeployableBPMAssetsTester tester = new DeployableBPMAssetsTester();
        return tester.test(project,
                DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                new Object[] {},
                null);
    }

    /**
     * @see com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant#createDeployWizard(com.tibco.xpd.deploy.Server,
     *      org.eclipse.core.resources.IProject)
     * 
     * @param selectedServer
     * @param draggedProject
     * @return
     */
    @Override
    protected IWizard createDeployWizard(Server selectedServer,
            IProject draggedProject) {

        BPMProjectDeployWizard wizard = new BPMProjectDeployWizard();
        wizard.setServer(selectedServer);
        wizard.setSelectedProject(draggedProject);

        return wizard;
    }

    /**
     * @see com.tibco.xpd.daa.internal.util.AbstractDeployDragDropAssistant#handleDrop(org.eclipse.ui.navigator.CommonDropAdapter,
     *      org.eclipse.swt.dnd.DropTargetEvent, java.lang.Object)
     * 
     * @param dropAdapter
     * @param dropTargetEvent
     * @param target
     * @return
     */
    @Override
    public IStatus handleDrop(CommonDropAdapter dropAdapter,
            DropTargetEvent dropTargetEvent, Object target) {
        // XPD-4935 In help->validate & On export DAA we should ensure that the
        // correct target platform is set in Studio.
        Display display =
                Workbench.getInstance() == null ? null : Workbench
                        .getInstance().getDisplay();
        Shell shell = display == null ? null : display.getActiveShell();
        if (DAAExportUtils.isTargetPlatformDefaultORValid(shell)) {
            return super.handleDrop(dropAdapter, dropTargetEvent, target);
        }
        return Status.OK_STATUS;
    }
}
