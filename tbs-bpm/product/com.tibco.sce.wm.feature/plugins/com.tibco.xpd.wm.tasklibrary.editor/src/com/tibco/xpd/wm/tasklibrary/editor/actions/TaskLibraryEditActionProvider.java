/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.providers.AbstractXpdl2EditActionProvider;

/**
 * Edit action provider (copy/paste/delete) for task library content in project explorer.
 *  
 * @author aallway
 * @since 3.2 
 */
public class TaskLibraryEditActionProvider extends
        AbstractXpdl2EditActionProvider {

    private PasteTasksFromClipTextAction pasteTaskFromClipTextAction;

    
    @Override
    protected void makeActions() {
        super.makeActions();
        
        pasteTaskFromClipTextAction = new PasteTasksFromClipTextAction();
    }
    
    @Override
    protected BaseSelectionListenerAction createCopyAction(Shell shell) {
        return new TaskLibraryContentCopyAction();
    }

    @Override
    protected BaseSelectionListenerAction createDeleteAction(Shell shell) {
        return new TaskLibraryContentDeleteAction(shell);
    }

    @Override
    protected BaseSelectionListenerAction createPasteAction(Shell shell) {
        return new TaskLibraryContentPasteAction();
    }

    @Override
    protected void updateActionSelections(IStructuredSelection selection) {
        super.updateActionSelections(selection);
        pasteTaskFromClipTextAction.selectionChanged(selection);
    }
    
    @Override
    public void fillContextMenu(IMenuManager menu) {
        menu.appendToGroup(ICommonMenuConstants.GROUP_EDIT, copyAction);
        menu.insertAfter(copyAction.getId(), pasteAction);
        menu.insertAfter(pasteAction.getId(), pasteTaskFromClipTextAction);
        menu.insertAfter(pasteTaskFromClipTextAction.getId(), deleteAction);
    }
}
