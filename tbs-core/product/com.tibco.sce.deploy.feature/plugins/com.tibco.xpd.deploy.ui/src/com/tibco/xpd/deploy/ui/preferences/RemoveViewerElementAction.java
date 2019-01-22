/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.preferences;

import java.util.Iterator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.components.ViewerAction;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Removes server runtime from model.
 * <p>
 * <i>Created: 4 December 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
final class RemoveViewerElementAction extends ViewerAction {

    public RemoveViewerElementAction(StructuredViewer viewer, String text) {
        super(viewer, text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        IStructuredSelection selection = (IStructuredSelection) getViewer()
                .getSelection();
        if (canRemoveRuntimes(selection)) {
            String message = String
                    .format(Messages.RemoveViewerElementAction_Confirmation_message);
            boolean decision = MessageDialog.openQuestion(getShell(),
                    Messages.RemoveViewerElementAction_Confirmation_title,
                    message);
            if (decision) {
                ServerManager manager = DeployUIActivator.getServerManager();
                manager.getServerContainer().getRuntimes().removeAll(
                        selection.toList());
                getViewer().refresh();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.components.ViewerAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void selectionChanged(IStructuredSelection selection) {
        setEnabled(!selection.isEmpty());
    }

    private boolean canRemoveRuntimes(IStructuredSelection selection) {
        ServerManager manager = DeployUIActivator.getServerManager();
        int usedTimes = 0;
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            com.tibco.xpd.deploy.Runtime runtime = (Runtime) iter.next();
            usedTimes = 0;
            for (Iterator<?> iterator = manager.getServerContainer()
                    .getServers().iterator(); iterator.hasNext();) {
                Server server = (Server) iterator.next();
                if (runtime.equals(server.getRuntime())) {
                    usedTimes++;
                }
            }
            if (usedTimes > 0) {
                String message = String
                        .format(
                                Messages.RemoveViewerElementAction_RemoveProblem_message,
                                runtime.getName(), usedTimes);
                MessageDialog.openInformation(getShell(),
                        Messages.RemoveViewerElementAction_RemoveProblem_title,
                        message);
                return false;
            }
        }
        return true;
    }

}