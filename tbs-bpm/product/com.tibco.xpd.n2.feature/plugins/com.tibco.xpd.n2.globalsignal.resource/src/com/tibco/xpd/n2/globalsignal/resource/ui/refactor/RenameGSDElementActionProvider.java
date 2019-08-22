/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Action provider for rename GSD element action.
 * 
 * @author sajain
 * @since Sep 14, 2015
 */
public class RenameGSDElementActionProvider extends CommonActionProvider {

    private RenameGSDElementAction renameAction;

    /**
     * Default constructor.
     */
    public RenameGSDElementActionProvider() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
     * .ICommonActionExtensionSite)
     */
    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);

        if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {

            renameAction =
                    new RenameGSDElementAction(
                            site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree(),
                            ((ICommonViewerWorkbenchSite) site.getViewSite())
                                    .getActionBars());

        } else {

            renameAction =
                    new RenameGSDElementAction(site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree());

        }

        renameAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);
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
            renameAction.setEnabled(!isLocked);
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, renameAction);

    }

    @Override
    public void fillActionBars(IActionBars actionBars) {

        updateSelection();

        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
    }

    private void updateSelection() {

        IStructuredSelection selection =
                (IStructuredSelection) getContext().getSelection();

        if (!selectionValid(selection)) {

            selection = StructuredSelection.EMPTY;
        }

        renameAction.selectionChanged(selection);

        Object firstElement = selection.getFirstElement();

        if (firstElement instanceof EObject) {

            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor((EObject) firstElement);

            /*
             * Working copy is read only if the project is pre-compiled. Then we
             * want to disable this action
             */
            boolean isWorkingCopyReadOnly = false;

            if (null != wc) {

                isWorkingCopyReadOnly = wc.isReadOnly();

            }

            if (isWorkingCopyReadOnly) {

                renameAction.setEnabled(false);

            } else {

                renameAction.setEnabled(true);

            }
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

        boolean valid = true;

        if (selection != null && selection.size() > 1) {

            EObject firstItem = (EObject) selection.getFirstElement();

            for (Iterator<?> iter = selection.iterator(); iter.hasNext()
                    && valid;) {

                valid =
                        (((EObject) iter.next()).eContainer() == firstItem
                                .eContainer());
            }
        }

        return selection != null ? valid : false;
    }
}