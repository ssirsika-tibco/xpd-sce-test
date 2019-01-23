/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * Action to open a folder location containing projects to import.
 * 
 * @author njpatel
 */
public class OpenFolderAction extends OpenAction {

    private static final String FOLDER_PATH = "openAction_folder_path"; //$NON-NLS-1$

    /**
     * @param window
     * @param title
     * @param id
     */
    public OpenFolderAction(IWorkbenchWindow window) {
        super(window, Messages.OpenFolderAction_openFolder_action,
                "open-folder"); //$NON-NLS-1$
        setToolTipText(Messages.OpenFolderAction_openFolder_action_tooltip);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.openFolder"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.OpenAction#getSelection()
     * 
     * @return
     */
    @Override
    public String getSelection() {
        String selection = null;
        DirectoryDialog dlg = new DirectoryDialog(getWindow().getShell());
        dlg.setText(Messages.OpenFolderAction_selectFolder_dialog_title);
        dlg.setMessage(Messages.OpenFolderAction_selectFolder_dialog_message);

        // Get initial path from the preferences
        String path =
                RCPActivator.getDefault().getDialogSettings().get(FOLDER_PATH);
        if (path != null) {
            dlg.setFilterPath(path);
        }

        selection = dlg.open();

        if (selection != null) {
            // Persist the selected path
            RCPActivator.getDefault().getDialogSettings()
                    .put(FOLDER_PATH, selection);
        } else {
            // User cancelled the folder selection dialog
            setIsActionCancelled(true);
        }

        return selection;
    }

}
