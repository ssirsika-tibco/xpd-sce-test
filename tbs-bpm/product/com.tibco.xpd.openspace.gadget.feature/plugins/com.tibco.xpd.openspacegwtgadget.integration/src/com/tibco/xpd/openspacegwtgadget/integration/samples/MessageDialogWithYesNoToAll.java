/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.openspacegwtgadget.integration.samples;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Message dialog with buttons for No, Yes, No to all and Yes to all.
 * 
 * @author aallway
 * @since 9 Jan 2013
 */
class MessageDialogWithYesNoToAll extends MessageDialog {

    public static final int BTN_NO = 0;

    public static final int BTN_YES = 1;

    public static final int BTN_NO_TO_ALL = 2;

    public static final int BTN_YES_TO_ALL = 3;

    public static final int BTN_CANCEL = 4;

    /**
     * Open a dialog with buttons for No, Yes, No to all and Yes to all.
     * 
     * @param parentShell
     * @param dialogTitle
     * @param dialogMessage
     * 
     * @return {@link #BTN_NO}, {@link #BTN_YES}, {@link #BTN_NO_TO_ALL},
     *         {@link #BTN_YES_TO_ALL}, {@link #BTN_CANCEL}
     */
    public static int open(Shell parentShell, String dialogTitle,
            String dialogMessage) {
        MessageDialogWithYesNoToAll dlg =
                new MessageDialogWithYesNoToAll(parentShell, dialogTitle,
                        dialogMessage);

        return dlg.open();
    }

    /**
     * Message dialog with buttons for No, Yes, No to all and Yes to all.
     * 
     * @param parentShell
     * @param dialogTitle
     * @param dialogTitleImage
     * @param dialogMessage
     * @param dialogImageType
     * @param dialogButtonLabels
     * @param defaultIndex
     */
    public MessageDialogWithYesNoToAll(Shell parentShell, String dialogTitle,
            String dialogMessage) {
        super(parentShell, dialogTitle, null, dialogMessage,
                MessageDialog.QUESTION, new String[] {
                        Messages.OpenspaceSampleFileFactory_No_button, // BTN_NO
                        Messages.OpenspaceSampleFileFactory_Yes_button, // BTN_YES
                        Messages.OpenspaceSampleFileFactory_NoToAll_button, // BTN_NO_TO_ALL
                        Messages.OpenspaceSampleFileFactory_YesToAll_button, // BTTM_YES_TO_ALL
                        Messages.OpenspaceSampleFileFactory_Cancel // BTN_CANCEL
                }, BTN_CANCEL);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
     * 
     */
    @Override
    protected void cancelPressed() {
        setReturnCode(BTN_NO);
    }
}