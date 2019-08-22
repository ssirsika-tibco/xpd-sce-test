package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.MoveProcessParamToIfcAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

public class RefactorProcessParamActionProvider extends CommonActionProvider {

    protected MoveProcessParamToIfcAction moveProcessParamToIfc;

    public RefactorProcessParamActionProvider() {

    }

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
            moveProcessParamToIfc.setEnabled(!isLocked);
        }
        menu.add(moveProcessParamToIfc);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        moveProcessParamToIfc.selectionChanged(selection);
    }

    private void makeActions() {
        moveProcessParamToIfc =
                new MoveProcessParamToIfcAction(
                        Messages.RefactorProcessParamActionProvider_MoveParamAction_menu);
        moveProcessParamToIfc.setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor("icons/moveToInterface.png")); //$NON-NLS-1$
        // Ravi-setimagedescriptor for the action.
    }
}
