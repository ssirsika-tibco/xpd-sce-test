/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.TextActionHandler;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

/**
 * Project Explorer's Edit action provider - this will provide copy, paste and
 * delete actions.
 * 
 * @author njpatel (aallway, extacted from original EditActionProvider).
 * 
 */
public abstract class AbstractXpdl2EditActionProvider extends CommonActionProvider {

    protected BaseSelectionListenerAction copyAction;

    protected BaseSelectionListenerAction pasteAction;

    protected BaseSelectionListenerAction deleteAction;

    protected TextActionHandler textActionHandler;

    private Shell shell = null;

    @Override
    public void init(ICommonActionExtensionSite aSite) {
        super.init(aSite);
        this.shell = aSite.getViewSite().getShell();

        makeActions();
    }

    /**
     * @return the delete action
     */
    protected abstract BaseSelectionListenerAction createDeleteAction(Shell shell);

    /**
     * @return The paste from clipboard action.
     */
    protected abstract BaseSelectionListenerAction createPasteAction(Shell shell);

    /**
     * @return The copy to clipboard action.
     */
    protected abstract BaseSelectionListenerAction createCopyAction(Shell shell);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
        menu.insertAfter(copyAction.getId(), pasteAction);
        menu.insertAfter(pasteAction.getId(), deleteAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {
        if (textActionHandler == null) {
            textActionHandler = new TextActionHandler(actionBars); // hooks
            // handlers
            textActionHandler.setCopyAction(copyAction);
            textActionHandler.setPasteAction(pasteAction);
        }
        updateActionBars();

        actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                copyAction);
        actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                pasteAction);
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteAction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    public void updateActionBars() {
        if (getContext() != null) {
            IStructuredSelection selection = (IStructuredSelection) getContext()
                    .getSelection();

            // Update selection of all actions
            updateActionSelections(selection);
        }
    }

    /**
     * @param selection
     */
    protected void updateActionSelections(IStructuredSelection selection) {
        copyAction.selectionChanged(selection);
        pasteAction.selectionChanged(selection);
        deleteAction.selectionChanged(selection);
    }

    /**
     * Create the actions
     */
    protected void makeActions() {
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

        // Copy action
        copyAction = createCopyAction(shell);
        copyAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
        copyAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
        copyAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);

        // Paste action
        pasteAction = createPasteAction(shell);
        pasteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
        pasteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
        pasteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);

        // Delete action
        deleteAction = createDeleteAction(shell);
        deleteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        deleteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        deleteAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
    }

 }
