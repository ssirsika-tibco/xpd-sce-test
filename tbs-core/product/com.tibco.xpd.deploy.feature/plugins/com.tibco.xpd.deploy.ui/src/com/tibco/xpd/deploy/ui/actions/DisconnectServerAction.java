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
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

public class DisconnectServerAction extends AbstractDeploymentAction {

    public static final String ID = DeployUIActivator.PLUGIN_ID
            + ".ConnectServerAction"; //$NON-NLS-1$

    private final StructuredViewer viewer;

    protected DisconnectServerAction(StructuredViewer aViewer) {
        super(Messages.DisconnectServerAction_Disconnect_action);
        viewer = aViewer;
        setToolTipText(Messages.DisconnectServerAction_DisconnectAction_tooltip);
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
        if (structSel.getFirstElement() instanceof Server) {
            final Server selectedServer = (Server) structSel.getFirstElement();
            final String jobDesc = String.format(
                    Messages.DisconnectServerAction_DisconnectJob_shortdesc,
                    selectedServer.getName());
            Job job = new Job(jobDesc) {
                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                    try {
                        Connection connection = selectedServer.getConnection();
                        if (connection != null) {
                            connection.disconnect();
                            connection.refreshServerContent();
                        }
                    } catch (final ConnectionException e) {
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                handleConnectionExeption(selectedServer, e);
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
            // job.setUser(true);
            job.schedule();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.actions.AbstractDeploymentAction#handleConnectionExeption(com.tibco.xpd.deploy.Server,
     *      com.tibco.xpd.deploy.model.extension.ConnectionException)
     */
    @Override
    protected void handleConnectionExeption(Server selectedServer,
            ConnectionException e) {
        Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        String title = Messages.DisconnectServerAction_DisconnectProblem_title;
        String message = String.format(
                Messages.DisconnectServerAction_DisconnectFailed_message,
                selectedServer.getName());
        if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
            message += '\n' + String
                    .format(
                            Messages.DisconnectServerAction_DisconnectProblemReason_message,
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
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() == 1
                && selection.getFirstElement() instanceof Server) {
            Connection connection = ((Server) selection.getFirstElement())
                    .getConnection();
            if (connection != null) {
                try {
                    return connection.isConnected();
                } catch (ConnectionException e) {
                    super.handleConnectionExeption(connection.getServer(), e);
                }
            }
        }
        return false;
    }

}
