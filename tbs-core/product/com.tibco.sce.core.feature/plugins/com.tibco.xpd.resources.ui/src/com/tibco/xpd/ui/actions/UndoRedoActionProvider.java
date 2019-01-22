/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006.  All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * Project Explorer's Undo/Redo actions provider
 * 
 * @author njpatel
 * 
 */
public class UndoRedoActionProvider extends CommonActionProvider {

    private UndoRedoAction undoAction;

    private UndoRedoAction redoAction;

    private boolean contribute = false;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
     */
    public void init(ICommonActionExtensionSite aSite) {
        if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            undoAction = new UndoRedoAction(UndoRedoAction.Type.UNDO);
            redoAction = new UndoRedoAction(UndoRedoAction.Type.REDO);
            contribute = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
     */
    public void fillActionBars(IActionBars actionBars) {

        if (contribute) {
            actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
                    undoAction);
            actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
                    redoAction);

            updateActionBars();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
     */
    public void updateActionBars() {
        if (contribute && getContext() != null) {
            IStructuredSelection selection = (IStructuredSelection) getContext()
                    .getSelection();

            undoAction.selectionChanged(selection);
            redoAction.selectionChanged(selection);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.ActionGroup#dispose()
     */
    public void dispose() {
        if (contribute) {
            undoAction.dispose();
            redoAction.dispose();
        }

        super.dispose();
    }

}
