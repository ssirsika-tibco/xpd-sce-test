/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.daa.internal.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonDropAdapter;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.ui.navigator.DropAssistantDelegate;
import com.tibco.xpd.deploy.ui.util.DeployUtil;

/**
 * Abstracted from {@link AdminDropAssistantDelegate} for re-use in multiple
 * project/DAA types.
 * 
 * @author aallway
 * @since 26 Feb 2013
 */
public abstract class AbstractDeployDragDropAssistant extends
        DropAssistantDelegate {

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatus handleDrop(CommonDropAdapter dropAdapter,
            DropTargetEvent dropTargetEvent, Object target) {
        try {
            if (isConnectedApplicableAdminServer(target)) {

                final Server selectedServer = (Server) target;
                final IProject draggedproject = getDraggedProject();

                if (draggedproject != null
                        && isApplicableProjectType(draggedproject)) {
                    Display.getDefault().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            if (!DeployUtil.saveAllDirtyResourcesInWS()) {
                                return;
                            }

                            IWizard deployWizard =
                                    createDeployWizard(selectedServer,
                                            draggedproject);
                            WizardDialog dialog =
                                    new WizardDialog(PlatformUI.getWorkbench()
                                            .getActiveWorkbenchWindow()
                                            .getShell(), deployWizard);
                            dialog.open();
                        }
                    });
                }
            }

        } catch (Exception e) {
            Status errorStatus =
                    new Status(
                            IStatus.ERROR,
                            DaaActivator.PLUGIN_ID,
                            "Exception during server drop. See error log for details.", //$NON-NLS-1$
                            e);
            DaaActivator.getDefault().getLog().log(errorStatus);
            return errorStatus;
        }
        return Status.OK_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {
        if (isConnectedApplicableAdminServer(target)) {
            if (getDraggedProject() != null
                    && isApplicableProjectType(getDraggedProject())) {
                return Status.OK_STATUS;
            }
        }

        return Status.CANCEL_STATUS;
    }

    /**
     * Returns dragged IProject if a single BPM project, 'null' otherwise.
     */
    private IProject getDraggedProject() {
        IStructuredSelection selection =
                (IStructuredSelection) LocalSelectionTransfer.getTransfer()
                        .getSelection();
        if (selection != null && selection.size() == 1
                && selection.getFirstElement() instanceof IProject) {
            IProject project = (IProject) selection.getFirstElement();

            return project;
        }
        return null;
    }

    /**
     * Checks if target is a connected admin server.
     */
    private boolean isConnectedApplicableAdminServer(Object target) {
        try {
            if (target instanceof Server) {
                Server s = (Server) target;

                Connection conn = s.getConnection();

                if (conn != null && conn.isConnected()) {
                    if (isApplicableAmxServerType(s)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Status errorStatus =
                    new Status(
                            IStatus.ERROR,
                            DaaActivator.PLUGIN_ID,
                            "Exception during server drop. See error log for details.", //$NON-NLS-1$
                            e);
            if (Platform.inDebugMode()) {
                DaaActivator.getDefault().getLog().log(errorStatus);
            }
        }
        return false;
    }

    /**
     * @param server
     * @return <code>true</code> If the given AMX administration deploy server
     *         is of appropriate type for this type of deploy project drag-drop.
     */
    protected boolean isApplicableAmxServerType(Server server) {
        ConfigParameter appParam =
                server.getServerConfig()
                        .getConfigParameter(DaaActivator.DEPLOY_SERVER_APP_NAME_CONFIG_PARAM);

        ServerType sType = server.getServerType();

        if (sType != null
                && DAAConstants.ADMIN_SERVER_TYPE_ID.equals(sType.getId())
                && appParam != null) {
            return true;
        }
        return false;
    }

    /**
     * @param project
     * 
     * @return If this drag drop assistant handle this type of project.
     */
    protected abstract boolean isApplicableProjectType(IProject project);

    /**
     * Create the deploy wizard for given project deploy on given AMX
     * Administration server.
     * 
     * @param selectedServer
     * @param draggedproject
     * @return wizard
     */
    protected abstract IWizard createDeployWizard(Server selectedServer,
            IProject draggedproject);

}
