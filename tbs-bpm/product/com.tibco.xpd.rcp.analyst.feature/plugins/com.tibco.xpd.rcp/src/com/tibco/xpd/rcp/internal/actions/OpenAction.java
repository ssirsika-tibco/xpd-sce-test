/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.MonitorDialog;

/**
 * Open an .maa, model file or project folder location action.
 * 
 * @author njpatel
 * 
 */
/* public */abstract class OpenAction extends RegisterCommandAction implements
        ICancellableAction {

    private boolean isCancelled = false;

    public OpenAction(IWorkbenchWindow window, String title, String id) {
        super(window, title);
        setId(id);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.ICancellableAction#isCancelled()
     * 
     * @return
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Set the cancel state of this action.
     * 
     * @param isCancelled
     */
    protected void setIsActionCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    /**
     * Get the open selection path.
     * 
     * @return the path to open (file or folder path), <code>null</code>
     *         indicates user cancelled selection.
     */
    public abstract String getSelection();

    @Override
    public void run() {
        // Assume action cancelled until it is successfully executed
        setIsActionCancelled(true);
        /*
         * If the current resource, if any, is dirty then get user to save first
         * before continuing
         */
        if (!RCPResourceManager.saveCurrentResource(getWindow().getShell())) {
            // User cancelled save
            return;
        }

        // Get the path to the file/folder
        String selection = getSelection();

        if (selection != null) {
            IStatus result =
                    openApplication(getWindow().getShell(), new File(selection));

            if (result.getSeverity() == IStatus.ERROR) {
                // Report error to user
                ErrorDialog
                        .openError(getWindow().getShell(),
                                Messages.OpenAction_errorOpeningApplication_errorDlg_title,
                                String.format(Messages.OpenAction_errorOpeningApplication_errorDlg_longdesc,
                                        selection),
                                result);
            }

            setIsActionCancelled(result.getSeverity() == IStatus.ERROR
                    || result.getSeverity() == IStatus.CANCEL);
        }
    }

    /**
     * Open the given application file/folder.
     * 
     * @param shell
     * @param file
     * @return
     */
    private static IStatus openApplication(Shell shell, File file) {
        IRCPContainer currentResource = RCPResourceManager.getResource();
        /*
         * Check if this resource is already being edited by this instance of
         * the application
         */
        if (currentResource != null && currentResource.getPath() != null
                && file.equals(currentResource.getPath().toFile())) {
            /*
             * Ask the user if they wish to overwrite the open application.
             */
            if (!MessageDialog
                    .openQuestion(shell,
                            Messages.OpenAction_applicationAlreadyOpen_dialog_title,
                            String.format(Messages.OpenAction_applicationAlreadyOpen_overwrite_dialog_message,
                                    file.getAbsolutePath()))) {
                return Status.CANCEL_STATUS;

            }
        }

        // Open the new file
        try {
            new MonitorDialog(shell).run(new OpenOperation(file));
        } catch (InvocationTargetException e) {
            RCPActivator.getDefault().getLogger()
                    .error(e, "Problem opening file: " + file.getName()); //$NON-NLS-1$
            if (e.getCause() instanceof CoreException) {
                return ((CoreException) e.getCause()).getStatus();
            }
            return new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID,
                    e.getCause() != null ? e.getCause().getLocalizedMessage()
                            : e.getLocalizedMessage(), e.getCause());
        } catch (InterruptedException e) {
            return Status.CANCEL_STATUS;
        }

        return Status.OK_STATUS;
    }

    /**
     * Open the given maa file. Note this will run in a scheduled UI job.
     * 
     * @param shell
     * @param file
     */
    public static void open(Shell shell, File file) {

        /*
         * Running in a scheduled UI job is important especially when opening a
         * project by double-clicking in the file system. Without this the UI
         * thread goes into deadlock as working copies start loading on startup.
         * By scheduling it we can ensure that the load happens when the UI
         * thread is free.
         */
        new OpenJob(shell, file).schedule();
    }

    /**
     * UI Job to open a file.
     * 
     * @author njpatel
     * 
     */
    private static class OpenJob extends UIJob {

        private final File file;

        private final Shell shell;

        public OpenJob(Shell shell, File file) {
            super(String.format(Messages.OpenAction_opening_monitor_shortdesc,
                    file.getName()));
            this.shell = shell;
            this.file = file;
        }

        @Override
        public IStatus runInUIThread(IProgressMonitor monitor) {
            IStatus result = openApplication(shell, file);

            /*
             * If error then report it here and always return OK as this job
             * will be scheduled during start-up of this application and won't
             * get reported otherwise.
             */
            if (result.getSeverity() == IStatus.ERROR) {
                // Report error to the user
                ErrorDialog
                        .openError(shell,
                                Messages.OpenAction_errorOpeningApplication_errorDlg_title,
                                String.format(Messages.OpenAction_errorOpeningApplication_errorDlg_longdesc,
                                        file.getAbsolutePath()),
                                result);
            }

            return Status.OK_STATUS;
        }
    }

    /**
     * Workspace modify operation to open the new file.
     */
    private static class OpenOperation extends WorkspaceModifyOperation {

        private final File file;

        public OpenOperation(File file) {
            this.file = file;
        }

        @Override
        protected void execute(IProgressMonitor monitor) throws CoreException,
                InvocationTargetException, InterruptedException {
            RCPResourceManager.open(PlatformUI.getWorkbench(), file, monitor);
        }

    }

}
