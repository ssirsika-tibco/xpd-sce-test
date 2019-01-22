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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.MoveResourceAction;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Project explorer's file move action. This overrides the standard
 * {@link MoveResourceAction} to check the selection of any files that have
 * associated working copies that are dirty; if a single file is selected and is
 * dirty then the user will be given a chance to save, otherwise a warning will
 * be displayed asking the user to save any dirty files.
 * 
 * @author njpatel
 * 
 */
public class WCMoveResourceAction extends MoveResourceAction {

    private final Shell shell;

    /**
     * File move action that will check the selection for any files that have an
     * associated <code>WorkingCopy</code> and is dirty. If found the user
     * will be given an opportunity to save the file(s) before the move.
     * 
     * @param shell
     */
    public WCMoveResourceAction(Shell shell) {
        super(shell);
        this.shell = shell;
    }

    @Override
    public void run() {
        boolean doRun = true;
        List<?> resources = getSelectedResources();

        /*
         * If one file is selected and is dirty then ask user to save before
         * continuing, otherwise if selection contains a number of dirty files
         * then ask user to save before trying again
         */
        if (resources != null && !resources.isEmpty()) {
            if (resources.size() == 1 && resources.get(0) instanceof IFile) {
                IFile file = (IFile) resources.get(0);
                WorkingCopy wc = XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);

                if (wc != null && wc.isWorkingCopyDirty()) {
                    doRun = MessageDialog
                            .openQuestion(
                                    shell,
                                    Messages.FileMoveAction_saveRequiredDlg_title,
                                    String
                                            .format(
                                                    Messages.FileMoveAction_singleFileSaveDlg_message,
                                                    file.getName()));

                    if (doRun) {
                        try {
                            // Save the file
                            wc.save();
                        } catch (IOException e) {
                            doRun = false;
                            XpdResourcesUIActivator.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            } else {
                // Check if the selection contains dirty files and warn user
                if (containsDirtyFiles(resources)) {
                    doRun = false;
                    MessageDialog
                            .openWarning(
                                    shell,
                                    Messages.FileMoveAction_saveRequiredDlg_title,
                                    Messages.FileMoveAction_multiSelectionSaveDlg_message);
                }
            }
        }

        if (doRun) {
            super.run();
        }
    }

    /**
     * Check if the selection contains a dirty file.
     * 
     * @param resources
     *            resources selection to move
     * @return <code>true</code> if selection contains a dirty file,
     *         <code>false</code> otherwise.
     */
    private boolean containsDirtyFiles(List<?> resources) {
        final boolean[] isDirty = new boolean[] { false };

        if (resources != null) {
            for (Object next : resources) {
                if (next instanceof IContainer) {
                    try {
                        ((IContainer) next).accept(new IResourceVisitor() {

                            public boolean visit(IResource resource)
                                    throws CoreException {

                                if (!isDirty[0]) {
                                    if (resource instanceof IFile) {
                                        WorkingCopy wc = XpdResourcesPlugin
                                                .getDefault().getWorkingCopy(
                                                        resource);

                                        isDirty[0] = wc != null
                                                && wc.isWorkingCopyDirty();
                                        return false;
                                    }
                                } else {
                                    // Dirty file already found so no point
                                    // searching further
                                    return false;
                                }
                                return true;
                            }

                        });
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger().error(
                                e);
                    }
                } else if (next instanceof IFile) {
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy((IResource) next);

                    isDirty[0] = wc != null && wc.isWorkingCopyDirty();
                }
                
                if (isDirty[0]) {
                    // Dirty file found so no point searching further
                    break;
                }
            }
        }

        return isDirty[0];
    }

}
