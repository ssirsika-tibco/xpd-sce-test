/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.utils;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Show the overwrite file message dialog to allow user to overwrite an existing
 * file. Used by the import/export wizards.
 * <p>
 * Call <code>{@link #getOverwriteStatus(int)}</code> with the result from
 * <code>{@link #open()}</code> to get the <code>{@link OverwriteStatus}</code>
 * enum value of the selection made in the dialog.
 * 
 * @author njpatel
 */
public class OverwriteFileMessageDialog extends MessageDialog {

    /**
     * Overwrite status enum defining the selection made in the
     * <code>OverwriteFileMessageDialog</code>
     * <p>
     * The values are:
     * <ul>
     * <li><b>NO</b> - <i>No</i> was selected in the dialog, indicating that the
     * file should not be overwritten</li>
     * <li><b>FILE</b> - <i>Yes</i> was selected in the dialog, indicating that
     * the file should be overwritten</li>
     * <li><b>ALL_FILES</b> - <i>Yes To All</i> was selected in the dialog,
     * indicating that the file and all future existing files should be
     * overwritten</li>
     * <li><b>CANCEL</b> - <i>Cancel</i> was selected in the dialog, indicating
     * that the process should be stopped</li>
     * </ul>
     * </p>
     */
    public enum OverwriteStatus {
        NO, FILE, ALL_FILES, CANCEL;

        /**
         * Check if the status is set to overwrite
         * 
         * @return <code>true</code> if the value has been set to FILE or
         *         ALL_FILES, <code>false</code> otherwise
         */
        public boolean canOverwrite() {
            return (this == FILE || this == ALL_FILES);
        }

        /**
         * Check if this status is set to CANCEL.
         * 
         * @return
         * @since 3.3
         */
        public boolean isCancelled() {
            return this == CANCEL;
        }
    }

    // Create buttons list for the message dialog
    private static final String buttonsList[] =
            new String[] { IDialogConstants.YES_LABEL,
                    IDialogConstants.YES_TO_ALL_LABEL,
                    IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };

    /**
     * Overwrite file message dialog
     * 
     * @param parentShell
     * @param dialogTitle
     * @param message
     */
    public OverwriteFileMessageDialog(final Shell parentShell,
            final String dialogTitle, final String message) {
        super(parentShell, dialogTitle, null, message, MessageDialog.QUESTION,
                buttonsList, 0);
    }

    /**
     * Get the <code>OverwriteStatus</code> code for the return value from this
     * dialog
     * 
     * @param retValue
     *            The return code from <code>Open()</code>
     * @return <code>OverwriteStatus</code> enum value of the choice made in the
     *         dialog
     */
    public OverwriteStatus getOverwriteStatus(int retValue) {
        String buttonLbl = buttonsList[retValue];
        OverwriteStatus status = OverwriteStatus.FILE;

        if (buttonLbl.equals(IDialogConstants.YES_LABEL)) {
            // Overwrite the file
            status = OverwriteStatus.FILE;
        } else if (buttonLbl.equals(IDialogConstants.YES_TO_ALL_LABEL)) {
            // Overwrite this file and all future exisiting files
            status = OverwriteStatus.ALL_FILES;
        } else if (buttonLbl.equals(IDialogConstants.NO_LABEL)) {
            // Don't overwrite file
            status = OverwriteStatus.NO;
        } else if (buttonLbl.equals(IDialogConstants.CANCEL_LABEL)) {
            // Cancel the export process
            status = OverwriteStatus.CANCEL;
        }

        return status;
    }
}
