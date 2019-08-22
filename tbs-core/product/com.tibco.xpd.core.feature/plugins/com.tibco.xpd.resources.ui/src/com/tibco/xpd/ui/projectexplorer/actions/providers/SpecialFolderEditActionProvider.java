/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.TextActionHandler;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.ui.projectexplorer.actions.CopyAction;
import com.tibco.xpd.ui.projectexplorer.actions.PasteAction;

/**
 * Project Explorer edit action provider for the <code>SpecialFolder</code>
 * objects. This will provide actions for copy, paste and delete.
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderEditActionProvider extends CommonActionProvider {

    private Clipboard clipboard;

    private CopyAction copyAction;

    private PasteAction pasteAction;

    private DeleteResourceAction deleteAction;

    private TextActionHandler textActionHandler;

    @Override
    public void init(ICommonActionExtensionSite aSite) {
        super.init(aSite);

        makeActions();

    }

    @Override
    public void fillContextMenu(IMenuManager menu) {

        /*
         * ACE-2473: Saket: Action should be disabled for locked application.
         */
        IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
        if (selection.getFirstElement() instanceof EObject) {
            boolean isLocked =
                    (new GovernanceStateService()).isLockedForProduction((EObject) (selection.getFirstElement()));
            pasteAction.setEnabled(!isLocked);
            deleteAction.setEnabled(!isLocked);
        }

        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
        menu.insertAfter(copyAction.getId(), pasteAction);
        menu.insertAfter(pasteAction.getId(), deleteAction);
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        if (textActionHandler == null) {
            textActionHandler = new TextActionHandler(actionBars);

            textActionHandler.setCopyAction(copyAction);
        }

        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        copyAction.selectionChanged(selection);
        pasteAction.selectionChanged(selection);
        deleteAction.selectionChanged(selection);

        actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                copyAction);
        actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                pasteAction);
        actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                deleteAction);
    }

    /**
     * Instantiate the actions
     */
    protected void makeActions() {
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        Shell shell = getActionSite().getViewSite().getShell();

        clipboard = new Clipboard(shell.getDisplay());

        copyAction = new CopyAction(clipboard);
        copyAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
        copyAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
        copyAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);

        pasteAction = new PasteAction(shell, clipboard);
        pasteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
        pasteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
        pasteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);

        deleteAction = new DeleteResourceAction(shell);
        deleteAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
        deleteAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        deleteAction
                .setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
    }
}
