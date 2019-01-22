/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditorContributor;
import com.tibco.xpd.processwidget.actions.EnableDisableEditPolicyAction;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;
import com.tibco.xpd.wm.tasklibrary.editor.internal.Messages;

/**
 * Menu / toolbar contributor for the task library editor.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryEditorContributor extends
        AbstractProcessDiagramEditorContributor {

    /**
     * @return View menu group id (to match the editorActions extension point)
     */
    protected String getViewMenuGroupId() {
        return "com.tibco.xpd.wm.tasklibrary.editor.viewMenuGroup"; //$NON-NLS-1$
    }

    /**
     * @return View menu id (to match the editorActions extension point)
     */
    protected String getViewMenuId() {
        return "com.tibco.xpd.wm.tasklibrary.editor.viewMenu"; //$NON-NLS-1$
    }

    @Override
    protected EnableDisableEditPolicyAction createEnableSignalEventGadgetAction() {
        // Don't need an action for this, we don't support that type of object
        // anyway.
        return null;
    }

    @Override
    protected EnableDisableEditPolicyAction createEnableLinkEventGadgetAction() {
        // Don't need an action for this, we don't support that type of object
        // anyway.
        return null;
    }

    @Override
    protected EnableDisableEditPolicyAction createEnableTaskRefGadgetAction() {
        // Don't need an action for this, we don't support that type of object
        // anyway.
        return null;
    }

    @Override
    protected EnableDisableEditPolicyAction createEnableReplyActivityGadgetAction() {
        // Don't need an action for this, we don't support that type of object
        // anyway.
        return null;
    }
    
    @Override
    protected QuickSearchPopupAction getQuickSearchPopupAction() {
        return new QuickSearchPopupAction(
                Messages.TaskLibraryEditorContributor_QuickSearchDefault_label);

    }

}
