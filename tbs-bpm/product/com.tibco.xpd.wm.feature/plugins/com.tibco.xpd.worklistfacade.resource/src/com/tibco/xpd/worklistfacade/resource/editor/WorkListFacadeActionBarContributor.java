/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.editor;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.util.Messages;

/**
 * Manages the installation/uninstallation of global actions for WorkListFacade
 * editor.
 */
public class WorkListFacadeActionBarContributor extends
        EditorActionBarContributor {

    /**
     * Constructor.
     */
    public WorkListFacadeActionBarContributor() {
        super();
    }

    /**
     * Overridden to add Undo/Redo action s.
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
     * 
     * @param targetEditor
     */
    @Override
    public void setActiveEditor(IEditorPart targetEditor) {
        // Set Editor Actions
        if (targetEditor instanceof WorkListFacadeEditor) {
            WorkListFacadeEditor editor = (WorkListFacadeEditor) targetEditor;
            // Install Undo and Redo Action handlers
            String ids[] =
                    new String[] { ActionFactory.UNDO.getId(),
                            ActionFactory.REDO.getId(), };

            for (int i = 0; i < ids.length; i++) {
                IAction act = editor.getAction(ids[i]);
                if (act != null) {
                    getActionBars().setGlobalActionHandler(ids[i], act);
                } else {
                    // Log Error for missing Action Handler
                    WorkListFacadeResourcePlugin
                            .getDefault()
                            .logError(Messages.WorkListFacadeActionBarContributor_GlobalActionHandlerNotFoundError
                                    + ids[i]);
                }
            }
            getActionBars().updateActionBars();
        }
    }

}
