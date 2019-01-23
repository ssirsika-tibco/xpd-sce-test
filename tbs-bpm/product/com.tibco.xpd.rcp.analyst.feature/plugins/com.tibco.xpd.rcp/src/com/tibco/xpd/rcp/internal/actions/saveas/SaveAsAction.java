/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions.saveas;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.MonitorDialog;

/**
 * Abstract class to be extended by a Save As action.
 * 
 * @author njpatel
 */
/* public */abstract class SaveAsAction extends Action {

    private final Shell shell;

    /**
     * Abstract Save As action.
     * 
     * @param shell
     * @param title
     *            title of this action.
     */
    public SaveAsAction(Shell shell, String title) {
        super(title);
        this.shell = shell;
    }

    /**
     * 
     * @return the shell
     */
    protected Shell getShell() {
        return shell;
    }

    /**
     * Any checks that needs to be performed before export
     * 
     * @param target
     * @return
     */
    protected abstract boolean preExportCheck(final File target);

    /**
     * Export the given resource to the given file/folder.
     * 
     * @param resource
     *            resource to save
     * @param target
     *            target file/folder
     * @param monitor
     * @throws CoreException
     */
    protected abstract void export(IRCPContainer resource, File target,
            IProgressMonitor monitor) throws CoreException;

    /**
     * Get the target path of this save action. Typically the user will be asked
     * for the path to a file/folder.
     * 
     * @return
     */
    protected abstract String getTargetPath();

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        final IRCPContainer resource = RCPResourceManager.getResource();

        if (resource.isDirty()) {
            if (MessageDialog.openQuestion(shell,
                    Messages.SaveAsAction_saveRequired_dialog_title,
                    Messages.SaveAsAction_saveRequired_dialog_longdesc)) {

                // Save
                BusyIndicator.showWhile(shell.getDisplay(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ResourcesPlugin.getWorkspace()
                                    .run(new IWorkspaceRunnable() {
                                        @Override
                                        public void run(IProgressMonitor monitor)
                                                throws CoreException {
                                            resource.save(new NullProgressMonitor());
                                        }
                                    },
                                            null);
                        } catch (CoreException e1) {
                            ErrorDialog
                                    .openError(shell,
                                            Messages.SaveAllAction_errorDialog_title,
                                            Messages.SaveAllAction_problemSaving_error_message,
                                            e1.getStatus());
                            return;
                        }
                    }
                });

            } else {
                // User cancelled save so cannot continue
                return;
            }
        }

        String path = getTargetPath();

        if (path != null) {
            /*
             * Kapil : XPD-4493:Check if the Save-As Dialog returns the
             * filename.EXTENSION(.maa) in the path , If not then simply add the
             * extension to the path.(Supports Linux machines)
             */
            IPath filePath = new Path(path);
            if (!RCPConsts.FILE_EXT.equals(filePath.getFileExtension())) {
                filePath =
                        filePath.removeFileExtension()
                                .addFileExtension(RCPConsts.FILE_EXT);
                path = filePath.toOSString();
            }

            final File targetFile = new File(path);

            // First create any parent folders
            File folder = targetFile.getParentFile();

            if (!folder.exists()) {
                try {
                    if (!folder.mkdirs()) {
                        // Failed to create folders
                        MessageDialog
                                .openError(shell,
                                        Messages.SaveAsAction_SaveAs_dialog_title,
                                        String.format(Messages.SaveAsAction_failedToCreateFolder_errorDlg_longdesc,
                                                folder.getAbsolutePath()));
                        return;
                    }
                } catch (SecurityException e) {
                    ErrorDialog.openError(getShell(),
                            "Error Creating Folders",
                            String.format("Failed to create folder '%s'",
                                    folder.getAbsolutePath()),
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID, e
                                    .getLocalizedMessage(), e));

                }
            }

            try {
                new MonitorDialog(shell).run(new IRunnableWithProgress() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        try {
                            if (preExportCheck(targetFile)) {
                                export(resource, targetFile, monitor);
                            }
                        } catch (CoreException e) {
                            throw new InvocationTargetException(e);
                        }

                    }
                });
            } catch (InvocationTargetException e) {
                IStatus status;
                if (e.getCause() instanceof CoreException) {
                    status = ((CoreException) e.getCause()).getStatus();
                } else {
                    status =
                            new Status(IStatus.ERROR, RCPActivator.PLUGIN_ID,
                                    e.getLocalizedMessage(), e.getCause());
                }

                ErrorDialog
                        .openError(shell,
                                Messages.SaveAsAction_SaveAs_dialog_title,
                                String.format(Messages.SaveAsAction_problemCreatingMAA_errorDialog_longdesc,
                                        targetFile.getAbsolutePath()),
                                status);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }
}
