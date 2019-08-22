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

import com.tibco.xpd.processeditor.xpdl2.actions.PageflowToProcessAction;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action provider for convert pageflow process to business process.
 * 
 * @author aallway
 * @since 3.4.2 (20 Sep 2010)
 */
public class ConvertPageflowToProcessActionProvider extends
        CommonActionProvider {

    protected PageflowToProcessAction converToProcessAction;

    /**
     * Boolean that tells whether the menu option 'Convert to Business Process'
     * to be shown on a Case Action
     */
    private boolean isCaseService = false;

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
            converToProcessAction.setEnabled(!isLocked);
        }

        /*
         * Menu option 'Convert to Business Process' must not be shown on a Case
         * Action
         */
        if (!isCaseService) {

            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    new Separator());

            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    converToProcessAction);
        }
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();
        Object firstElement = selection.getFirstElement();
        if (firstElement instanceof Process) {

            if (Xpdl2ModelUtil.isCaseService((Process) firstElement)) {

                isCaseService = true;
            } else {

                converToProcessAction.selectionChanged(selection);
                isCaseService = false;
            }
        }
    }

    private void makeActions() {

        converToProcessAction = new PageflowToProcessAction();
    }
}
