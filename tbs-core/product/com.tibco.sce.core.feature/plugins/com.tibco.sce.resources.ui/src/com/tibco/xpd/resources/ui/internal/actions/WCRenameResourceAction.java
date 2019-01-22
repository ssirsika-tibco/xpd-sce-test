/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.actions.RenameResourceAction;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Project explorer rename action. This will work in the following ways:
 * <ul>
 * <li>Rename file that has no <code>WorkingCopy</code> or is not dirty -
 * this will act as the standard rename action</li>
 * <li>Rename container - this will check if the container contains any file
 * that has a working copy and is dirty; the user will be warned that the rename
 * can only happen after these files are saved</li>
 * <li>Rename file that has a WorkingCopy and is dirty - this will ask the user
 * to save the file to continue</li>
 * </ul>
 * 
 * @author njpatel
 * 
 */
public class WCRenameResourceAction extends RenameResourceAction {

    private final Shell shell;

    /**
     * File rename action.
     * 
     * @param shell
     * @param tree
     */
    public WCRenameResourceAction(Shell shell, Tree tree) {
        super(shell, tree);
        this.shell = shell;
    }

    @Override
    public void run() {
        boolean doRun = true;

        IResource resource = getCurrentResource();

        if (resource instanceof IFile) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    resource);
            if (wc != null && wc.isWorkingCopyDirty()) {
                if (MessageDialog.openQuestion(shell,
                        Messages.FileRenameAction_saveReqDlg_title,
                        String.format(
                                Messages.FileRenameAction_saveReqDlg_message,
                                resource.getName()))) {
                    try {
                        // Save the file
                        wc.save();
                    } catch (IOException e) {
                        doRun = false;
                        XpdResourcesUIActivator.getDefault().getLogger().error(
                                e);
                        ErrorDialog
                                .openError(
                                        shell,
                                        Messages.FileRenameAction_errorOnSaveDlg_title,
                                        Messages.FileRenameAction_errorOnSaveDlg_message,
                                        new Status(IStatus.ERROR,
                                                XpdResourcesUIActivator.ID, e
                                                        .getLocalizedMessage(),
                                                e));
                    }
                } else {
                    // User decided not to save so rename will not be allowed
                    doRun = false;
                }
            }
        } else if (resource instanceof IContainer) {
            if (containsDirtyFile((IContainer) resource)) {
                doRun = false;
                MessageDialog
                        .openWarning(
                                shell,
                                Messages.FileRenameAction_saveReqDlg_title,
                                String
                                        .format(
                                                Messages.FileRenameAction_unsavedFilesDlg_message,
                                                resource.getName()));
            }
        }

        if (doRun) {
            super.run();
        }
    }

    /**
     * Check if the container contains a dirty file.
     * 
     * @param container
     *            container to check
     * @return <code>true</code> if container contains dirty files,
     *         <code>false</code> otherwise.
     */
    private boolean containsDirtyFile(IContainer container) {
        final boolean[] isDirty = new boolean[] { false };

        if (container != null && container.isAccessible()) {
            try {
                container.accept(new IResourceVisitor() {

                    public boolean visit(IResource resource)
                            throws CoreException {

                        if (!isDirty[0]) {
                            if (resource instanceof IFile) {
                                WorkingCopy wc = XpdResourcesPlugin
                                        .getDefault().getWorkingCopy(resource);

                                isDirty[0] = wc != null
                                        && wc.isWorkingCopyDirty();
                                return false;
                            }
                        } else {
                            // Dirty file already found so no point searching on
                            return false;
                        }

                        return true;
                    }

                });
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e);
            }
        }

        return isDirty[0];
    }

    /**
     * Return the currently selected resource. Only return an IResouce if there
     * is one and only one resource selected.
     * 
     * @return IResource or <code>null</code> if there is zero or more than
     *         one resources selected.
     */
    private IResource getCurrentResource() {
        List<?> resources = getSelectedResources();
        if (resources.size() == 1) {
            return (IResource) resources.get(0);
        }
        return null;

    }
}
