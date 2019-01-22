package com.tibco.xpd.processeditor.xpdl2.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processeditor.xpdl2.actions.ExternalizePackageAction;

public class ExternalizeXpdlPackageActionProvider extends CommonActionProvider {

    protected ExternalizePackageAction externalizePkgAction;

    public ExternalizeXpdlPackageActionProvider() {
        init();
    }

    private void init() {
        externalizePkgAction = new ExternalizePackageAction();
    }
    
    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();

//        ISelectionProvider sp = this.getActionSite().getViewSite().getSelectionProvider();
//        externalizePkgAction.setSelectionProvider(sp);
        
        
        // Add a separator to separate ourselves from delete etc above.
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                externalizePkgAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();
        externalizePkgAction.selectionChanged(selection);
    }

}
