/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
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
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.deploy.Operation;
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Action representing operation on server element.
 * <p>
 * <i>Created: 8 Aug 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerElementAction extends BaseSelectionListenerAction {

    private final StructuredViewer viewer;

    private final Operation operation;

    /**
     * Creates server element action associated with the server element
     * operation.
     * 
     * @param aViewer
     * @param operation
     *            the operation to perform.
     */
    protected ServerElementAction(StructuredViewer aViewer, Operation operation) {
        super(operation.getName());
        viewer = aViewer;
        this.operation = operation;
        if (operation.getDescription() != null) {

        } else {
            setToolTipText(String.format(Messages.ServerElementAction_tooltip,
                    operation.getName()));
        }
        setId(DeployUIActivator.PLUGIN_ID + '.' + operation.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        final IStructuredSelection structSel = getStructuredSelection();
        if (structSel.getFirstElement() instanceof ServerElement) {
            final ServerElement serverElement =
                    (ServerElement) structSel.getFirstElement();
            final String jobDesc =
                    String.format(Messages.ServerElementAction_Job_shortdesc,
                            operation.getName(),
                            serverElement.getName());
            Job job = new Job(jobDesc) {
                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    monitor.beginTask(jobDesc, IProgressMonitor.UNKNOWN);
                    try {
                        operation.execute(serverElement);
                    } catch (final DeploymentException e) {
                        Display.getDefault().asyncExec(new Runnable() {
                            public void run() {
                                handleConnectionExeption(serverElement, e);
                            }
                        });
                        return Status.CANCEL_STATUS;
                    } finally {
                        monitor.done();
                    }
                    Display.getDefault().asyncExec(new Runnable() {
                        public void run() {
                            updateViewerSelection();
                        }
                    });
                    return Status.OK_STATUS;
                }
            };
            // job.setUser(true);
            job.schedule();

        }
    }

    /**
     * @param serverElement
     * @param e
     */
    private void handleConnectionExeption(ServerElement serverElement,
            DeploymentException e) {
        Shell s =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        String title =
                String
                        .format(Messages.ServerElementAction_OperationProblem_title,
                                operation.getName());
        String message =
                String
                        .format(Messages.ServerElementAction_OperationProblem_message,
                                operation.getName(),
                                serverElement.getName());
        if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
            message +=
                    '\n' + String
                            .format(Messages.ServerElementAction_OperationProblemReason_message,
                                    e.getMessage());
        }
        MessageDialog.openError(s, title, message);
    }

    /**
     * Reselect selected object in viewer to refresh state.
     */
    private void updateViewerSelection() {
        ISelection sel = viewer.getSelection();
        viewer.setSelection(StructuredSelection.EMPTY);
        viewer.setSelection(sel);
    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() == 1
                && selection.getFirstElement() instanceof ServerElement) {
            ServerElement serverElement =
                    (ServerElement) selection.getFirstElement();
            return operation.canExecute(serverElement);
        }
        return false;
    }

    /**
     * Gets an associated action's operation.
     */
    public Operation getOperation() {
        return operation;
    }
}
