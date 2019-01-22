/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * This is the action group for a resource refactor.
 * <p>
 * NOTE: The rename action is only included if the file being renamed has no
 * <code>WorkingCopy</code> or has a saved <code>WorkingCopy</code> or is a
 * container. If a dirty WorkingCopy is selected then
 * {@link FileRenameActionProvider} is activated - please see the class for more
 * details.
 * </p>
 * 
 */
public class RefactorActionGroup extends ActionGroup {

    private WCRenameResourceAction renameAction;

    private WCMoveResourceAction moveAction;

    private Shell shell;

    private Tree tree;

    /**
     * Refactor action group for a resource.
     * 
     * @param aShell
     * @param aTree
     */
    public RefactorActionGroup(Shell aShell, Tree aTree) {
        shell = aShell;
        tree = aTree;
        makeActions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        boolean fileSelected = !selection.isEmpty()
                && allResourceAreFiles(selection);

        if (fileSelected) {
            moveAction.selectionChanged(selection);
            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    moveAction);
        }
        if (includeRename()) {
            renameAction.selectionChanged(selection);
            menu.appendToGroup(ICommonMenuConstants.GROUP_REORGANIZE,
                    renameAction);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {

        updateActionBars();

        actionBars.setGlobalActionHandler(ActionFactory.MOVE.getId(),
                moveAction);

        if (includeRename()) {
            actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                    renameAction);
        }
    }

    private boolean includeRename() {
        boolean include = true;
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        if (selection.size() == 1
                && selection.getFirstElement() instanceof IFile) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    (IResource) selection.getFirstElement());

            if (wc != null) {
                // If file has an associated working copy and is dirty then
                // don't include rename action
                include = !wc.isWorkingCopyDirty();
            }
        }

        return include;
    }

    /**
     * Create the actions.
     */
    protected void makeActions() {

        moveAction = new WCMoveResourceAction(shell);
        moveAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.MOVE);

        renameAction = new WCRenameResourceAction(shell, tree);
        renameAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    @Override
    public void updateActionBars() {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        moveAction.selectionChanged(selection);
        renameAction.selectionChanged(selection);
    }

    /**
     * Check if the selection is of files only.
     * 
     * @param selection
     * @return <code>true</code> if all items selected are <code>IFile</code>s,
     *         <code>false</code> otherwise.
     */
    private boolean allResourceAreFiles(IStructuredSelection selection) {
        boolean allFiles = false;

        if (selection != null) {
            for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                if (!(allFiles = iter.next() instanceof IFile)) {
                    break;
                }
            }
        }
        return allFiles;
    }

}
