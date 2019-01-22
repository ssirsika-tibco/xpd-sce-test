/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalActionManager;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Edit action provider for the RSD objects.
 * 
 * @author jarciuch
 * @since 27 Feb 2015
 */
public class RsdEditActionProvider extends CommonActionProvider {

    /**
     * Project explorer view id.
     */
    private static final String PROJECT_EXPLORER_VIEW_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    private DeleteAction deleteAction;

    private RsdRenameAction renameAction;

    private Map<String, IAction> actions; // cut, copy, paste actions handled by
                                          // gmf.

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);

        // Handled by GMF action handlers.
        if (actions == null) {
            actions = new HashMap<>();
            String[] actionIds =
                    new String[] { ActionFactory.CUT.getId(),
                            ActionFactory.COPY.getId(),
                            ActionFactory.PASTE.getId() };
            IViewPart peView =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().findView(PROJECT_EXPLORER_VIEW_ID);
            assert peView != null : "ProjectExplorer view part was not found."; //$NON-NLS-1$
            GlobalAction[] globalActions =
                    GlobalActionManager.getInstance()
                            .createGlobalActions(peView, actionIds);
            for (GlobalAction globalAction : globalActions) {
                actions.put(globalAction.getActionId(), globalAction);
            }
        }

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        deleteAction = new RsdDeleteAction(ed);

        Shell shell = site.getViewSite().getShell();
        Tree tree = ((TreeViewer) site.getStructuredViewer()).getTree();
        if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            // Set the action bars so that the copy/paste is handled correctly
            // from the inline editor
            renameAction =
                    new RsdRenameAction(shell, tree,
                            ((ICommonViewerWorkbenchSite) site.getViewSite())
                                    .getActionBars());
        } else {
            renameAction = new RsdRenameAction(shell, tree);
        }
        renameAction
                .setActionDefinitionId(IWorkbenchCommandConstants.FILE_RENAME);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, renameAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT,
                actions.get(ActionFactory.CUT.getId()));
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT,
                actions.get(ActionFactory.COPY.getId()));
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT,
                actions.get(ActionFactory.PASTE.getId()));
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, deleteAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillActionBars(IActionBars actionBars) {
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
        actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),
                actions.get(ActionFactory.CUT.getId()));
        actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                actions.get(ActionFactory.COPY.getId()));
        actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                actions.get(ActionFactory.PASTE.getId()));
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteAction);
        /*
         * No need to call actionBars.updateActionBars(); as the caller will do
         * that after.
         */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateActionBars() {
        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();
            if (!selectionValid(selection)) {
                selection = StructuredSelection.EMPTY;
            }
            renameAction.selectionChanged(selection);
            deleteAction.selectionChanged(selection);
            /* copy/cut/paste - context updates are handled by gmf extension. */
        }

    }

    /**
     * Checks if all selected objects are of the same type and are siblings.
     * 
     * @param selection
     * @return <code>true</code> if all objects are of same type and are
     *         siblings, <code>false</code> otherwise.
     */
    private boolean selectionValid(IStructuredSelection selection) {
        if (selection != null && !selection.isEmpty()) {
            if (selection.getFirstElement() instanceof EObject) {
                EObject firstItem = (EObject) selection.getFirstElement();
                EClass firstEClass = firstItem.eClass();
                EObject firstEContainer = firstItem.eContainer();
                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();
                    if (next instanceof EObject) {
                        EObject eo = (EObject) next;
                        if (!(eo.eClass() == firstEClass && eo.eContainer() == firstEContainer)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
