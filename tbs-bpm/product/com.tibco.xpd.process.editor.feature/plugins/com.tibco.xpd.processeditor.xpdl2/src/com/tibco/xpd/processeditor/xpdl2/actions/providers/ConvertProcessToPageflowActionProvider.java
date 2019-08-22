/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processeditor.xpdl2.actions.ProcessToPageflowAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * Action provider for convert business process to pageflow process.
 * 
 * @author aallway
 * @since 3.4.2 (20 Sep 2010)
 */
public class ConvertProcessToPageflowActionProvider extends
        CommonActionProvider {

    protected ProcessToPageflowAction converToPageflowAction;

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        makeActions();
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();

        /*
         * ACE-2473: Saket: Action should be disabled for locked application.
         */
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        if (selection.getFirstElement() instanceof EObject) {
            boolean isLocked =
                    (new GovernanceStateService()).isLockedForProduction((EObject) (selection.getFirstElement()));
            converToPageflowAction.setEnabled(!isLocked);
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                new Separator());
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                converToPageflowAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        converToPageflowAction.selectionChanged(selection);
    }

    private void makeActions() {
        converToPageflowAction = new ProcessToPageflowAction();
    }

}
