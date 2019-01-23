/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Edit action provider for the UML objects in the BOM file.
 * 
 * @author njpatel
 * 
 */
public class OMEditActionProvider extends CommonActionProvider {

    private OMCopyAction copyAction;

    private OMPasteAction pasteAction;

    private DeleteAction deleteAction;

    private RenameAction renameAction;

    /**
     * Default constructor.
     */
    public OMEditActionProvider() {

    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        copyAction = new OMCopyAction(ed);
        copyAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

        pasteAction = new OMPasteAction(ed);
        pasteAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

        deleteAction =
                new OMDeleteAction(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), true);
        deleteAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

        if (site.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            // Set the action bars so that the copy/paste is handled correctly
            // from the inline editor
            renameAction =
                    new RenameAction(
                            site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree(),
                            ((ICommonViewerWorkbenchSite) site.getViewSite())
                                    .getActionBars());
        } else {
            renameAction =
                    new RenameAction(site.getViewSite().getShell(),
                            ((TreeViewer) site.getStructuredViewer()).getTree());
        }

        renameAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.RENAME);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, renameAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, pasteAction);
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, deleteAction);
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            if (selectionValid(selection)) {
            } else {
                selection = StructuredSelection.EMPTY;
            }

            // Update actions' selection
            copyAction.selectionChanged(selection);
            pasteAction.selectionChanged(selection);
            deleteAction.selectionChanged(selection);
            renameAction.selectionChanged(selection);
        }

        actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                copyAction);
        actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                pasteAction);
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteAction);
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
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
                EObject next = (EObject) iter.next();
                valid = next.eClass() == firstItem.eClass();

                if (valid) {
                    valid = (next.eContainer() == firstItem.eContainer());
                }
            }
        }

        return selection != null ? valid : false;
    }

}
