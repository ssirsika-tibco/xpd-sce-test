/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Connects to server.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectServerGroupAction extends AbstractDeploymentAction {

    public static final String ID = DeployUIActivator.PLUGIN_ID
            + ".ConnectServerGroupAction"; //$NON-NLS-1$

    private static final String LABEL = Messages.ConnectServerGroupAction_connect_action;

    private static final String TOOLTIP = Messages.ConnectServerGroupAction_connect_tooltip;

    private final StructuredViewer viewer;

    protected ConnectServerGroupAction(StructuredViewer aViewer) {
        super(LABEL);
        viewer = aViewer;
        setToolTipText(TOOLTIP);
        setId(ID);
    }

    /*
     * (non-Javadoc) Action is trying to connect to a server in a separate
     * background job so the UI is not blocked.
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
                        if (connection.isConnected()) {
                            continue;
                        }
                    } catch (ConnectionException e) {
                        handleConnectionExeption(connection.getServer(), e);
                    }
                }
                final String jobDesc = String.format(
                        Messages.ConnectServerGroupAction_connect_message, memberServer.getName());
                Job job = new Job(jobDesc) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                        try {
                            Connection connection = memberServer
                                    .getConnection();
                            if (connection != null) {
                                connection.connect();
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
                        Display.getDefault().syncExec(new Runnable() {
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

    /**
     * Updates viewer to refresh selection.
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
                        if (!connection.isConnected()) {
                            return true;
                        }
                    } catch (ConnectionException e) {
                        handleConnectionExeption(connection.getServer(), e);
                    }
                }
            }
        }
        return false;
    }

}
