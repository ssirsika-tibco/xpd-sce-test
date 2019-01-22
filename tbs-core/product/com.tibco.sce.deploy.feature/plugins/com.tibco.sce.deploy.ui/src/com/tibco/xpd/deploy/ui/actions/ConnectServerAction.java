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
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionCoreException;
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
public class ConnectServerAction extends AbstractDeploymentAction {

    public static final String ID = DeployUIActivator.PLUGIN_ID
            + ".ConnectServerAction"; //$NON-NLS-1$

    private static final String LABEL =
            Messages.ConnectServerAction_Connect_action;

    private static final String TOOLTIP =
            Messages.ConnectServerAction_ConnectAction_tooltip;

    private final StructuredViewer viewer;

    public ConnectServerAction(StructuredViewer aViewer) {
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

        /*
         * get the Server we want to connect.
         */
        final Server selectedServer = getServer(structSel);

        if (selectedServer != null) {

            final String jobDesc =
                    String.format(Messages.ConnectServerAction_ConnectJob_shortdesc,
                            selectedServer.getName());
            Job job = new Job(jobDesc) {
                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                    Connection connection = null;
                    boolean cancelled = false;
                    try {
                        connection = selectedServer.getConnection();
                        if (connection != null) {
                            connection.connect();
                            connection.refreshServerContent();
                        }
                    } catch (final ConnectionCoreException e) {
                        /*
                         * If connection was cancelled by the user, we ignore
                         * the exception.
                         */
                        if (e.getStatus() == null
                                || IStatus.CANCEL != e.getStatus()
                                        .getSeverity()) {

                            Display.getDefault().asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    handleConnectionExeption(selectedServer, e);
                                }
                            });
                        }
                        cancelled = true;

                    } catch (final ConnectionException e) {
                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                handleConnectionExeption(selectedServer, e);
                            }
                        });
                        cancelled = true;
                    } catch (final Exception e) {
                        Display.getDefault().asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                handleConnectionExeption(selectedServer,
                                        new ConnectionException(e));
                            }
                        });
                        cancelled = true;
                    } finally {
                        monitor.done();
                    }

                    if (cancelled) {
                        try {
                            // Disconnect the server if it was connected but
                            // cancelled
                            if (connection != null && connection.isConnected()) {
                                connection.disconnect();
                            }
                        } catch (ConnectionException e) {
                            DeployUIActivator.getDefault().getLogger().error(e);
                        }
                        return Status.CANCEL_STATUS;
                    } else {
                        Display.getDefault().syncExec(new Runnable() {
                            @Override
                            public void run() {
                                updateViewer();
                                updateSelection(structSel);
                            }
                        });
                        return Status.OK_STATUS;
                    }
                }
            };
            // job.setUser(true);
            job.schedule();

        }
    }

    /**
     * Gets the Server which should be connected.
     * <p>
     * This method was added as a part of SCF-75, so that we can give a chance
     * to extending classes to decide the server which they want to connect.
     * 
     * @param structSel
     * @return the Server which should be connected
     * 
     */
    protected Server getServer(IStructuredSelection structSel) {

        if (structSel.getFirstElement() instanceof Server) {
            return (Server) structSel.getFirstElement();
        }
        return null;
    }

    /**
     * Updates viewer to refresh selection.
     */
    private void updateViewer() {
        if (viewer != null) {
            ISelection sel = viewer.getSelection();
            viewer.setSelection(StructuredSelection.EMPTY);
            viewer.setSelection(sel);
        }
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
        if (selection != null && selection.size() == 1
                && selection.getFirstElement() instanceof Server) {
            Connection connection =
                    ((Server) selection.getFirstElement()).getConnection();
            if (connection != null) {
                try {
                    return !connection.isConnected();
                } catch (ConnectionException e) {
                    handleConnectionExeption(connection.getServer(), e);
                }
            }
        }
        return false;
    }

}
