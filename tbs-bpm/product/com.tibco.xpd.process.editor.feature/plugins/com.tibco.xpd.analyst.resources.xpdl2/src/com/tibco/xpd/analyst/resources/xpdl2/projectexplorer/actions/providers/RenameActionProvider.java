package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.RenameAction;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * Rename action provider for the Xpdl2 EObjects (BPM Content)
 * 
 * @author njpatel
 * 
 */
public class RenameActionProvider extends CommonActionProvider {

    private RenameAction renameAction;

    @Override
    public void init(ICommonActionExtensionSite aSite) {
        super.init(aSite);

        renameAction = getRenameAction(aSite);
    }

    protected RenameAction getRenameAction(ICommonActionExtensionSite aSite) {
        RenameAction action;

        if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            action =
                    new RenameAction(aSite.getViewSite().getShell(),
                            ((TreeViewer) aSite.getStructuredViewer())
                                    .getTree(),
                            ((ICommonViewerWorkbenchSite) aSite.getViewSite())
                                    .getActionBars());
        } else {
            action =
                    new RenameAction(aSite.getViewSite().getShell(),
                            ((TreeViewer) aSite.getStructuredViewer())
                                    .getTree());
        }

        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);

        return action;
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        updateSelection();
        /*
         * ACE-2473: Saket: Action should be disabled for locked application.
         */
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        if (selection.getFirstElement() instanceof EObject) {
            boolean isLocked =
                    (new GovernanceStateService()).isLockedForProduction((EObject) (selection.getFirstElement()));
            renameAction.setEnabled(!isLocked);
        }
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);

    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();

        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, renameAction);
    }

    /**
     * Update the selection of the actions
     */
    private void updateSelection() {
        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();

        renameAction.selectionChanged(selection);
    }

}
