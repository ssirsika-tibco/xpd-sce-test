/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;

/**
 * Dialog used to ask the user if current changes should be saved.
 * 
 * @author njpatel
 * 
 */
public class SaveChangesDialog extends MessageDialog {

    public static final int YES_ID = IDialogConstants.YES_ID;

    public static final int NO_ID = IDialogConstants.NO_ID;

    public static final int CANCEL_ID = IDialogConstants.CANCEL_ID;

    public SaveChangesDialog(Shell parentShell, IRCPResource resource) {
        super(parentShell, Messages.SaveChangesDialog_title, null,
                getMessage(resource), MessageDialog.QUESTION, new String[] {
                        IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
                        IDialogConstants.CANCEL_LABEL }, 0);
    }

    /**
     * @param resource
     * @return
     */
    private static String getMessage(IRCPResource resource) {
        if (resource.getName() != null) {
            return String.format(Messages.SaveChangesDialog_message,
                    resource.getName());
        }
        return Messages.SaveChangesDialog_saveChanges_message;
    }

    @Override
    public int getReturnCode() {
        int code = super.getReturnCode();

        switch (code) {
        case 0:
            code = YES_ID;
            break;
        case 1:
            code = NO_ID;
            break;
        default:
            code = CANCEL_ID;
        }
        return code;
    }

    @Override
    public int open() {
        super.open();
        return getReturnCode();
    }
}
