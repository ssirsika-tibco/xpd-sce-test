/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

import com.tibco.xpd.ui.projectexplorer.actions.specialfolder.SpecialFolderRefactorActionGroup;

/**
 * Refactor action providers for the SpecialFolder that are not provided by
 * Eclipse's IResource provider. These are Delete, Rename, Move, Undo and Redo.
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderRefactorActionProvider extends CommonActionProvider {

    private UndoRedoActionGroup undoRedoGroup;

    private SpecialFolderRefactorActionGroup refactorGroup;

    private ICommonActionExtensionSite site;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    public void init(ICommonActionExtensionSite anActionSite) {
        site = anActionSite;
        refactorGroup = new SpecialFolderRefactorActionGroup(site.getViewSite()
                .getShell(), (Tree) site.getStructuredViewer().getControl());

        IUndoContext workspaceContext = (IUndoContext) ResourcesPlugin
                .getWorkspace().getAdapter(IUndoContext.class);
        undoRedoGroup = new UndoRedoActionGroup(
                ((ICommonViewerWorkbenchSite) anActionSite.getViewSite())
                        .getSite(), workspaceContext, true);
    }

    public void dispose() {
        undoRedoGroup.dispose();
        refactorGroup.dispose();
    }

    public void fillActionBars(IActionBars actionBars) {
        undoRedoGroup.fillActionBars(actionBars);
        refactorGroup.fillActionBars(actionBars);
    }

    public void fillContextMenu(IMenuManager menu) {
        undoRedoGroup.fillContextMenu(menu);
        refactorGroup.fillContextMenu(menu);
    }

    public void setContext(ActionContext context) {
        undoRedoGroup.setContext(context);
        refactorGroup.setContext(context);
    }

    public void updateActionBars() {
        undoRedoGroup.updateActionBars();
        refactorGroup.updateActionBars();
    }
}
