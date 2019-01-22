/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Helper class for handling common connection problems.
 * <p>
 * <i>Created: 3 September 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ConnectionHelper {

    /** Project Explorer view id. */
    private static final String PROJECT_EXPLORER_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * The private constructor to avoid instantiation.
     */
    private ConnectionHelper() {
    }

    /**
     * Handles connection problem.
     * 
     * @param selectedServer
     *            server with problem.
     * @param e
     *            exception causing connection problem.
     */
    public static void handleConnectionExeption(final Server selectedServer,
            final ConnectionException e) {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                Shell s = getShell();
                if (s == null) {
                    return;
                }
                String title = Messages.ConnectionHelper_ConnectionProblemDialog_title;
                String message = String
                        .format(
                                Messages.ConnectionHelper_ConnectionProblemDialog_message,
                                selectedServer.getName());
                if (e.getMessage() != null
                        && e.getMessage().trim().length() > 0) {
                    message += Messages.ConnectionHelper_ConnectionProblemDialogReason_summary
                            + e.getMessage();
                }
                MessageDialog.openError(s, title, message);

                // Reset selection to the disconnected server to refresh
                // properties.
                try {
                    CommonNavigator navigator = (CommonNavigator) PlatformUI
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(PROJECT_EXPLORER_VIEW_ID);
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

        });
    }

    /**
     * Displays in the error message. If invoked from non UI thread it will be
     * synchronised with UI thread.
     * 
     * @param title
     *            the title of the message dialog.
     * @param message
     *            the message to be displayed.
     * @param status
     *            the status of problem.
     */
    public static void displayError(final String title, final String message,
            final IStatus status) {
        Display defauDisplay = Display.getDefault();
        if (defauDisplay != null) {
            defauDisplay.syncExec(new Runnable() {
                /*
                 * (non-Javadoc)
                 * 
                 * @see java.lang.Runnable#run()
                 */
                public void run() {
                    ErrorDialog.openError(getShell(), title, message, status);
                }
            });
        }
    }

    /**
     * @return the active workbench shell or null.
     */
    private static Shell getShell() {
        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();
        }
        return null;
    }
}
