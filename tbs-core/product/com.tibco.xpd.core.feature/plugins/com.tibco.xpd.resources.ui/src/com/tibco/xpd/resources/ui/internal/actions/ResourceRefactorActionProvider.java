/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

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

/**
 * Project explorer action provider to refactor resources. This overrides the
 * standard Eclipse resource refactoring to enable us to control the move and
 * rename actions.
 * <p>
 * The user will be disallowed to rename or move a resource if it is or contains
 * unsaved models.
 * </p>
 * <p>
 * A separate file rename action ({@link FileRenameActionProvider}) is
 * provided to overcome problems with the rename action in this provider - see
 * the class for more details.
 * </p>
 */
public class ResourceRefactorActionProvider extends CommonActionProvider {

    private UndoRedoActionGroup undoRedoGroup;

    private RefactorActionGroup refactorGroup;

    private ICommonActionExtensionSite site;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    public void init(ICommonActionExtensionSite anActionSite) {
        site = anActionSite;
        refactorGroup = new RefactorActionGroup(site.getViewSite().getShell(),
                (Tree) site.getStructuredViewer().getControl());

        IUndoContext workspaceContext = (IUndoContext) ResourcesPlugin
                .getWorkspace().getAdapter(IUndoContext.class);
        undoRedoGroup = new UndoRedoActionGroup(
                ((ICommonViewerWorkbenchSite) anActionSite.getViewSite())
                        .getSite(), workspaceContext, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#dispose()
     */
    public void dispose() {
        undoRedoGroup.dispose();
        refactorGroup.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
        undoRedoGroup.fillActionBars(actionBars);
        refactorGroup.fillActionBars(actionBars);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        undoRedoGroup.fillContextMenu(menu);
        refactorGroup.fillContextMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#setContext(org.eclipse.ui.actions.ActionContext)
     */
    public void setContext(ActionContext context) {
        undoRedoGroup.setContext(context);
        refactorGroup.setContext(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    public void updateActionBars() {
        undoRedoGroup.updateActionBars();
        refactorGroup.updateActionBars();
    }

}
