package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.MessageDialogUtil;

public class ProcessDialogUtil {

    public static void enableSolutionDesignCapability(Shell shell,
            boolean enable) {
        String title =
                Messages.ProcessCapabilityUtil_PickerUtil_solutionDesignDialog_title;
        String message = null;
        if (enable) {
            message =
                    Messages.ProcessCapabilityUtil_PickerUtil_enableSolutionDesignDialog_label;
        } else {
            message =
                    Messages.ProcessCapabilityUtil_PickerUtil_disableSolutionDesignDialog_label;
        }
        String toggleMessage =
                Messages.ProcessCapabilityUtil_ChangeSolutionDesignWithoutAsking_Label;
        int returnCode =
                MessageDialogUtil.openOkCancelConfirm(shell,
                        title,
                        message,
                        toggleMessage,
                        true,
                        IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
                        WorkbenchPlugin.getDefault().getPreferenceStore());
        if (returnCode == MessageDialogWithToggle.OK) {
            CapabilityUtil.enableSolutionDesignCapability(enable);
        }
    }

    /**
     * Ask the user whether they want to save all processes in the package (or
     * if preference is set to save without asking then this will return true).
     * 
     * @param shell
     * 
     * @return <code>true</code> if the save can be done, <code>false</code> if
     *         save cannot be done.
     */
    public static boolean canSavePackage(Shell shell) {
        final String toggleMsg =
                Messages.ProcessDiagramEditor_ToggleMsg_shortdesc;

        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();
        boolean prefAskSave =
                prefStore
                        .getBoolean(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);
        if (!prefAskSave) {
            MessageDialogWithToggle saveAllProcsDialog =
                    MessageDialogWithToggle
                            .openOkCancelConfirm(shell,
                                    Messages.ProcessDiagramEditor_SaveProcs_label,
                                    Messages.ProcessDiagramEditor_PrefSaveAllProcs_longdesc,
                                    toggleMsg,
                                    prefAskSave,
                                    prefStore,
                                    ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE);

            if (IDialogConstants.OK_ID == saveAllProcsDialog.getReturnCode()) {
                prefAskSave = saveAllProcsDialog.getToggleState();
                prefStore
                        .setValue(ProcessEditorConstants.PREF_DONT_ASK_AGAIN_FOR_SAVE,
                                prefAskSave);

                prefStore.setValue(ProcessEditorConstants.PREF_SAVE_EDITOR,
                        saveAllProcsDialog.getReturnCode());
                return true;
            }
        } else {
            if (IDialogConstants.OK_ID == prefStore
                    .getInt(ProcessEditorConstants.PREF_SAVE_EDITOR)) {
                return true;
            }
        }

        return false;
    }

}
