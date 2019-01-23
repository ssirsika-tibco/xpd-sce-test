/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Refreshes the server obtaining objects form server.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RefreshServerGroupAction extends AbstractDeploymentAction {

    /** Refresh action context id */
    private static final String ORG_ECLIPSE_UI_IDE_REFRESH_ACTION_CONTEXT = "org.eclipse.ui.ide.refresh_action_context"; //$NON-NLS-1$

    /** Project Explorer view id. */
    private static final String PROJECT_EXPLORER_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /** Unique action identifier. */
    private static final String ID = DeployUIActivator.PLUGIN_ID
            + ".RefreshServerGroupAction"; //$NON-NLS-1$

    /** Action definition id used by commands. */
    private static final String ACTION_DEFINITION_ID = "org.eclipse.ui.file.refresh"; //$NON-NLS-1$

    private final StructuredViewer viewer;

    public RefreshServerGroupAction(StructuredViewer aViewer) {
        super(Messages.RefreshAction_Refresh_action);
        setId(ID);
        setActionDefinitionId(ACTION_DEFINITION_ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                ORG_ECLIPSE_UI_IDE_REFRESH_ACTION_CONTEXT);
        setImageDescriptor(DeployUIActivator.getDefault().getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_REFRESH));
        viewer = aViewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        final IStructuredSelection structSel = getStructuredSelection();
        if (structSel.getFirstElement() instanceof ServerGroup) {
            final ServerGroup serverGroup = (ServerGroup) structSel
                    .getFirstElement();
            for (final Server memberServer : serverGroup.getMembers()) {

                Connection connection = memberServer.getConnection();
                if (connection != null) {
                    try {
                        if (!connection.isConnected()) {
                            continue;
                        }
                    } catch (ConnectionException e) {
                        handleConnectionExeption(connection.getServer(), e);
                    }
                }
                final String jobDesc = String.format(
                        Messages.RefreshAction_RefreshJob_shortdesc,
                        memberServer.getName());
                Job job = new Job(jobDesc) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                        try {
                            Connection connection = memberServer
                                    .getConnection();
                            if (connection != null && connection.isConnected()) {
                                connection.refreshServerContent();
                            }

                        } catch (final ConnectionException e) {
                            Display.getDefault().asyncExec(new Runnable() {
                                public void run() {
                                    handleConnectionExeption(memberServer, e);
                                }
                            });
                            return Status.CANCEL_STATUS;
                        } finally {
                            monitor.done();
                        }
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                updateViewer();
                                updateSelection(structSel);
                            }
                        });
                        return Status.OK_STATUS;
                    }
                };
                job.schedule();
            }
        }
    }

    @Override
    protected void handleConnectionExeption(Server selectedServer,
            ConnectionException e) {
        Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        String title = Messages.RefreshAction_RefreshProblem_title;
        StringBuffer message = new StringBuffer(String.format(
                Messages.RefreshAction_RefreshProblem_message, selectedServer
                        .getName()));
        if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
            message.append('\n');
            message.append(String.format(
                    Messages.RefreshAction_RefreshProblemReason_message, e
                            .getMessage()));
        }
        MessageDialog.openError(s, title, message.toString());

        // Reset selection to the disconnected server to refresh properties.
        try {
            CommonNavigator navigator = (CommonNavigator) PlatformUI
                    .getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .showView(PROJECT_EXPLORER_VIEW_ID);
            if (navigator != null) {
                CommonViewer commonViewer = navigator.getCommonViewer();
                commonViewer.setSelection(StructuredSelection.EMPTY);
                commonViewer.setSelection(new StructuredSelection(
                        selectedServer));
            }
        } catch (PartInitException e1) {
            // intentionally do nothing except logging.
            DeployUIActivator.getDefault().getLog().log(e1.getStatus());
        }
    }

    /**
     * Updates selection in the viewer to refresh properties properties of the
     * selected object.
     */
    private void updateViewer() {
        ISelection sel = viewer.getSelection();
        viewer.setSelection(StructuredSelection.EMPTY);
        viewer.setSelection(sel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() == 1
                && selection.getFirstElement() instanceof ServerGroup) {
            ServerGroup serverGroup = (ServerGroup) selection.getFirstElement();
            if (validateTypes(selection, serverGroup)) {
                for (Server memberServer : serverGroup.getMembers()) {
                    Connection connection = memberServer.getConnection();
                    if (connection != null) {
                        try {
                            if (connection.isConnected()) {
                                return true;
                            }
                        } catch (ConnectionException e) {
                            handleConnectionExeption(connection.getServer(), e);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean validateTypes(IStructuredSelection selection,
            ServerGroup serverGroup) {
        if (serverGroup.getServerGroupType() != null
                && !serverGroup.getServerGroupType().isValid()) {
            String title = Messages.RefreshServerGroupAction_missingGroupType_title;
            String message = Messages.RefreshServerGroupAction_missingGroupType_longdesc;
            showProblemDialog(selection, title, message);
            return false;
        }
        return true;
    }

    /**
     * 
     * @param selection
     *            the current selection
     */
    private void showProblemDialog(IStructuredSelection selection,
            String title, String message) {
        Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        MessageDialog dialog = new MessageDialog(
                s,
                title,
                null, // accept
                message,
                MessageDialog.WARNING,
                new String[] {
                        Messages.RefreshServerGroupAction_deleteServerGroup_button,
                        IDialogConstants.CANCEL_LABEL }, 1); // yes is
        if (dialog.open() == 0) {
            DeleteServerGroupAction deleteAction = new DeleteServerGroupAction();
            deleteAction.selectionChanged(selection);
            deleteAction.run();
        }
    }
}
