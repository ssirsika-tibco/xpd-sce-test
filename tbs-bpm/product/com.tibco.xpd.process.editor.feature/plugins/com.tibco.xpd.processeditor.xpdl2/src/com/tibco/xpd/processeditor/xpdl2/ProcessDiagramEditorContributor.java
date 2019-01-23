/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

/**
 * @author wzurek
 * 
 */
public class ProcessDiagramEditorContributor extends
        AbstractProcessDiagramEditorContributor {

    /**
     * @return View menu group id (to match the editorActions extension point)
     */
    protected String getViewMenuGroupId() {
        return "com.tibco.xpd.processeditor.viewMenuGroup"; //$NON-NLS-1$
    }

    /**
     * @return View menu id (to match the editorActions extension point)
     */
    protected String getViewMenuId() {
        return "com.tibco.xpd.processeditor.viewMenu"; //$NON-NLS-1$
    }

    @Override
    protected QuickSearchPopupAction getQuickSearchPopupAction() {
        return new QuickSearchPopupAction(
                Messages.ProcessDiagramEditorContributor_FindDiagramElements_label);
    }

}
