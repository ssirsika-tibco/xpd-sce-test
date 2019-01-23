/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * A utility class to use message dialogs
 * 
 * @author mtorres
 * 
 * @since 3.1
 */
public class MessageDialogUtil {

    private static final String DIALOG_RETURN_CODE_PREFIX =
            "DIALOG_RETURN_CODE_PREFIX";//$NON-NLS-1$

    /**
     * This method will open an "OK", "CANCEL" confirmation dialog with a toggle
     * to perform the same action without showing the dialog the next time for
     * the same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @return the returnCode of the dialog
     * 
     * 
     **/
    public static int openOkCancelConfirm(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key) {
        return openOkCancelConfirm(parent,
                title,
                message,
                toggleMessage,
                toggleStateToShow,
                key,
                null);
    }

    /**
     * This method will open an "OK", "CANCEL" confirmation dialog with a toggle
     * to perform the same action without showing the dialog the next time for
     * the same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param store
     *            the preference store
     * @return the returnCode of the dialog
     * 
     * 
     **/
    public static int openOkCancelConfirm(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key, IPreferenceStore store) {
        if (store == null) {
            store = XpdResourcesPlugin.getDefault().getPreferenceStore();
        }
        boolean toggleState = store.getBoolean(key);
        int returnCode = MessageDialogWithToggle.OK;
        if (toggleState == toggleStateToShow) {
            MessageDialogWithToggle dialog =
                    MessageDialogWithToggle.openOkCancelConfirm(parent,
                            title,
                            message,
                            toggleMessage,
                            toggleState,
                            store,
                            key);
            returnCode = dialog.getReturnCode();
            if (returnCode == MessageDialogWithToggle.OK) {
                store.setValue(dialog.getPrefKey(), dialog.getToggleState());
            }
        }
        return returnCode;
    }

    /**
     * This method will open an "YES", "NO" question dialog with a toggle to
     * perform the same action without showing the dialog the next time for the
     * same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * 
     * @return the returnCode of the dialog
     * 
     * 
     **/
    public static int openYesNoQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key) {
        return openYesNoQuestion(parent,
                title,
                message,
                toggleMessage,
                toggleStateToShow,
                key,
                null);
    }

    /**
     * This method will open an "YES", "NO" question dialog with a toggle to
     * perform the same action without showing the dialog the next time for the
     * same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @param store
     *            the preference store
     * 
     * @return the returnCode of the dialog
     * 
     * 
     **/
    public static int openYesNoQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key, IPreferenceStore store) {
        return openYesNoQuestion(parent,
                title,
                message,
                toggleMessage,
                toggleStateToShow,
                key,
                store,
                0);
    }

    /**
     * This method will open an "YES", "NO" question dialog with a toggle to
     * perform the same action without showing the dialog the next time for the
     * same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @param store
     *            the preference store
     * @param defaultButtonIndex
     *            Zero based index of the default button to activate (0=YES,
     *            1=NO)
     * 
     * @return the returnCode of the dialog
     */
    public static int openYesNoQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key, IPreferenceStore store, int defaultButtonIndex) {

        if (store == null) {
            store = XpdResourcesPlugin.getDefault().getPreferenceStore();
        }
        boolean toggleState = store.getBoolean(key);
        int returnCode =
                store.getInt(MessageDialogUtil
                        .getMessageDialogReturnCodeStoreKey(key));
        if (returnCode == 0) {
            // Set the default return code "YES"
            returnCode = 2;
        }

        if (toggleState == toggleStateToShow) {
            MessageDialogWithToggle dialog =
                    new MessageDialogWithToggle(parent, title, null, message,
                            MessageDialog.QUESTION, new String[] {
                                    IDialogConstants.YES_LABEL,
                                    IDialogConstants.NO_LABEL },
                            defaultButtonIndex, toggleMessage, toggleState);
            dialog.setPrefStore(store);
            dialog.setPrefKey(key);
            dialog.open();

            returnCode = dialog.getReturnCode();
            store.setValue(MessageDialogUtil
                    .getMessageDialogReturnCodeStoreKey(dialog.getPrefKey()),
                    returnCode);
            store.setValue(dialog.getPrefKey(), dialog.getToggleState());
        }
        return returnCode;
    }

    /**
     * This method will open an "YES", "NO", "CANCEL" question dialog with a
     * toggle to perform the same action without showing the dialog the next
     * time for the same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * 
     * @return the returnCode of the dialog
     * 
     **/
    public static int openYesNoCancelQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key) {
        return openYesNoCancelQuestion(parent,
                title,
                message,
                toggleMessage,
                toggleStateToShow,
                key,
                null);
    }

    /**
     * This method will open an "YES", "NO", "CANCEL" question dialog with a
     * toggle to perform the same action without showing the dialog the next
     * time for the same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @param store
     *            the preference store
     * 
     * @return the returnCode of the dialog
     * 
     * 
     **/
    public static int openYesNoCancelQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key, IPreferenceStore store) {
        return openYesNoQuestion(parent,
                title,
                message,
                toggleMessage,
                toggleStateToShow,
                key,
                store,
                0);
    }

    /**
     * This method will open an "YES", "NO", "CANCEL" question dialog with a
     * toggle to perform the same action without showing the dialog the next
     * time for the same key.
     * 
     * Note, this method does not create the preference page needed to disable
     * the "Do without asking" functionality.
     * 
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleStateToShow
     *            the state that toggle should have to show the message
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
     * @param store
     *            the preference store
     * @param defaultButtonIndex
     *            Zero based index of the default button to activate (0=YES,
     *            1=NO, 2 = CANCEL)
     * 
     * @return the returnCode of the dialog
     */

    public static int openYesNoCancelQuestion(Shell parent, String title,
            String message, String toggleMessage, boolean toggleStateToShow,
            String key, IPreferenceStore store, int defaultButtonIndex) {
        if (store == null) {
            store = XpdResourcesPlugin.getDefault().getPreferenceStore();
        }
        boolean toggleState = store.getBoolean(key);
        int returnCode =
                store.getInt(MessageDialogUtil
                        .getMessageDialogReturnCodeStoreKey(key));
        if (returnCode == 0) {
            // Set the default return code "YES"
            returnCode = 2;
        }
        if (toggleState == toggleStateToShow) {
            MessageDialogWithToggle dialog =
                    new MessageDialogWithToggle(parent, title, null, message,
                            MessageDialog.QUESTION_WITH_CANCEL, new String[] {
                                    IDialogConstants.YES_LABEL,
                                    IDialogConstants.NO_LABEL,
                                    IDialogConstants.CANCEL_LABEL },
                            defaultButtonIndex, toggleMessage, toggleState);
            dialog.setPrefStore(store);
            dialog.setPrefKey(key);
            dialog.open();

            returnCode = dialog.getReturnCode();

            if (returnCode != MessageDialogWithToggle.CANCEL) {
                store.setValue(MessageDialogUtil
                        .getMessageDialogReturnCodeStoreKey(dialog.getPrefKey()),
                        returnCode);
                store.setValue(dialog.getPrefKey(), dialog.getToggleState());
            }
        }
        return returnCode;
    }

    private static String getMessageDialogReturnCodeStoreKey(String key) {
        return DIALOG_RETURN_CODE_PREFIX + key;
    }
}
