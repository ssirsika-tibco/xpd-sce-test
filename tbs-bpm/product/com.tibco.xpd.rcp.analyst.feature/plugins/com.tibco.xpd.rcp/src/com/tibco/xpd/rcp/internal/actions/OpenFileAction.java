/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;

/**
 * Action to open a MAA or model file.
 * 
 * @author njpatel
 */
public class OpenFileAction extends OpenAction {

    private static final String FILE_PATH = "openAction_path"; //$NON-NLS-1$

    private static final String[] FILE_EXTS = new String[] {
            "*." + RCPConsts.FILE_EXT, //$NON-NLS-1$
            "*." + RCPConsts.BOM_FILEEXT, "*." + RCPConsts.OM_FILEEXT, //$NON-NLS-1$ //$NON-NLS-2$
            "*." + RCPConsts.PROCESSES_FILEEXT }; //$NON-NLS-1$

    /**
     * @param window
     * @param title
     * @param id
     */
    public OpenFileAction(IWorkbenchWindow window) {
        super(window, Messages.OpenAction_title, "open-file"); //$NON-NLS-1$
        setToolTipText(Messages.OpenAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.MAA
                .getPath()));
        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.openFile"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.OpenAction#getSelection()
     * 
     * @return
     */
    @Override
    public String getSelection() {
        FileDialog dlg = new FileDialog(getWindow().getShell());
        dlg.setFilterExtensions(FILE_EXTS);
        dlg.setText(Messages.OpenAction_fileSelection_dialog_shortdesc);

        // Get initial path from the preferences
        String path =
                RCPActivator.getDefault().getDialogSettings().get(FILE_PATH);
        if (path != null) {
            dlg.setFilterPath(path);
        }

        String selection = dlg.open();

        if (selection != null) {
            // Persist the selected path
            RCPActivator.getDefault().getDialogSettings()
                    .put(FILE_PATH, selection);
        } else {
            // User cancelled the file selection dialog
            setIsActionCancelled(true);
        }

        return selection;
    }

}
