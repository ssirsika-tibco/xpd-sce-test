package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

public class DisconnectServerGroupAction extends AbstractDeploymentAction {

    public static final String ID = DeployUIActivator.PLUGIN_ID
            + ".ConnectServerGroupAction"; //$NON-NLS-1$

    private final StructuredViewer viewer;

    protected DisconnectServerGroupAction(StructuredViewer aViewer) {
        super(Messages.DisconnectServerGroupAction_disconnectServerGroup_action);
        viewer = aViewer;
        setToolTipText(Messages.DisconnectServerGroupAction_disconnectServerGroup_tooltip);
        setId(ID);
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
            ServerGroup serverGroup = (ServerGroup) structSel.getFirstElement();
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

                final String jobDesc =
                        String.format(Messages.DisconnectServerAction_DisconnectJob_shortdesc,
                                memberServer.getName());
                Job job = new Job(jobDesc) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                        try {
                            Connection connection =
                                    memberServer.getConnection();
                            if (connection != null) {
                                connection.disconnect();
                                connection.refreshServerContent();
                            }
                        } catch (final ConnectionException e) {
                            Display.getDefault().asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    handleConnectionExeption(memberServer, e);
                                }
                            });
                            return Status.CANCEL_STATUS;
                        } finally {
                            monitor.done();
                        }
                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                updateViewer();
                                updateSelection(structSel);
                            }
                        });
                        return Status.OK_STATUS;
                    }
                };
                // job.setUser(true);
                job.schedule();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.deploy.ui.actions.AbstractDeploymentAction#
     * handleConnectionExeption(com.tibco.xpd.deploy.Server,
     * com.tibco.xpd.deploy.model.extension.ConnectionException)
     */
    @Override
    protected void handleConnectionExeption(Server selectedServer,
            ConnectionException e) {
        Shell s =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title = Messages.DisconnectServerAction_DisconnectProblem_title;
        String message =
                String.format(Messages.DisconnectServerAction_DisconnectFailed_message,
                        selectedServer.getName());
        if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
            message +=
                    '\n' + String
                            .format(Messages.DisconnectServerAction_DisconnectProblemReason_message,
                                    e.getMessage());
        }
        MessageDialog.openError(s, title, message);
    }

    /**
     * Updates viewer.
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
            for (Server memberServer : ((ServerGroup) selection
                    .getFirstElement()).getMembers()) {
                Connection connection = memberServer.getConnection();
                if (connection != null) {
                    try {
                        if (connection.isConnected()) {
                            return true;
                        }
                    } catch (ConnectionException e) {
                        super.handleConnectionExeption(connection.getServer(),
                                e);
                    }
                }
            }
        }
        return false;
    }

}
