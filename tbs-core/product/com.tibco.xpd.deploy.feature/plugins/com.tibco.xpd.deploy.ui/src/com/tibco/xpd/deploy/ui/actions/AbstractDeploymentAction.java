/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Base for all deployment actions.
 * <p>
 * <i>Created: 27 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class AbstractDeploymentAction extends
        BaseSelectionListenerAction {

    /** Project Explorer view id. */
    private static final String PROJECT_EXPLORER_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * Creates new deployment action with a given text.
     * 
     * @param text
     *            the string used as the text for the action, or
     *            <code>null</code> if there is no text
     */
    protected AbstractDeploymentAction(String text) {
        super(text);
    }

    /**
     * Handles connection exception by printing appropriate message to the user
     * and refreshing necessary elements.
     * 
     * @param selectedServer
     *            the server exception was thrown for.
     * @param e
     *            the exception thrown.
     */
    protected void handleConnectionExeption(Server selectedServer,
            ConnectionException e) {
        Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();
        String title = Messages.AbstractDeploymentAction_ConnectionProblem_title;
        String message = String.format(
                Messages.AbstractDeploymentAction_CannotConnect_message,
                selectedServer.getName());
        if (e.getLocalizedMessage() != null
                && e.getLocalizedMessage().trim().length() > 0) {
            message += '\n' + String.format(
                    Messages.AbstractDeploymentAction_Reason_message, e
                            .getLocalizedMessage());
        }
        MessageDialog.openError(s, title, message);

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
}
