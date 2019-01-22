/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.specialfolder;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.ui.internal.actions.WCMoveResourceAction;
import com.tibco.xpd.resources.ui.internal.actions.WCRenameResourceAction;

/**
 * Project explorer refactor action group for the <code>SpecialFolder</code>
 * objects. This will include actions to Rename and Move the special folder.
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderRefactorActionGroup extends ActionGroup {

    private WCRenameResourceAction renameAction;

    private WCMoveResourceAction moveAction;

    private Shell shell;

    private Tree tree;

    /**
     * 
     * @param aShell
     * @param aTree
     */
    public SpecialFolderRefactorActionGroup(Shell aShell, Tree aTree) {
        shell = aShell;
        tree = aTree;
        makeActions();
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        // Update the selection
        moveAction.selectionChanged(selection);
        renameAction.selectionChanged(selection);

        actionBars.setGlobalActionHandler(ActionFactory.MOVE.getId(),
                moveAction);
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);

    }

    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE, moveAction);
        menu.insertAfter(moveAction.getId(), renameAction);
    }

    /**
     * Instantiate the actions
     */
    protected void makeActions() {
        moveAction = new WCMoveResourceAction(shell);
        moveAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.MOVE);

        renameAction = new WCRenameResourceAction(shell, tree);
        renameAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);
    }
}
