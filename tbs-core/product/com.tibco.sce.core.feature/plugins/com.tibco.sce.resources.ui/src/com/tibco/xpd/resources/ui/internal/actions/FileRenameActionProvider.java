/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

/**
 * Project explorer's file rename action provider. This will be activated when
 * renaming a file that has an associated <code>WorkingCopy</code> and is
 * dirty. This will allow the user to save the file before rename.
 * <p>
 * NOTE: The rename action has been overriden from the
 * {@link ResourceRefactorActionProvider} as it's enablement in the extension
 * causes problems with rename - the inline editor does not appear after the
 * question dialog closes.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class FileRenameActionProvider extends CommonActionProvider {

    WCRenameResourceAction renameAction;

    /**
     * File rename action provider. This will be activated when renaming a file
     * that has an associated <code>WorkingCopy</code> and is dirty. This will
     * allow the user to save the file before rename.
     */
    public FileRenameActionProvider() {
    }

    @Override
    public void init(ICommonActionExtensionSite site) {
        super.init(site);

        renameAction = new WCRenameResourceAction(site.getViewSite().getShell(),
                (Tree) site.getStructuredViewer().getControl());
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        updateSelection();
        actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(),
                renameAction);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        updateSelection();
        menu.insertAfter(WCMoveResourceAction.ID, renameAction);
    }

    private void updateSelection() {
        renameAction.selectionChanged((IStructuredSelection) getContext()
                .getSelection());
    }

}
