/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.editor;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.rsd.ui.RsdUIPlugin;

/**
 * REST Service Descriptor editor action bars contributor.
 * 
 * @author jarciuch
 * @since 20 Feb 2015
 */
public class RsdEditorContributor extends EditorActionBarContributor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveEditor(IEditorPart targetEditor) {
        // Set Editor Actions
        if (targetEditor instanceof RsdEditorPart) {
            RsdEditorPart editor = (RsdEditorPart) targetEditor;
            String ids[] =
                    new String[] { ActionFactory.UNDO.getId(),
                            ActionFactory.REDO.getId(),
                            ActionFactory.CUT.getId(),
                            ActionFactory.COPY.getId(),
                            ActionFactory.PASTE.getId(),
                            ActionFactory.DELETE.getId() };

            for (int i = 0; i < ids.length; i++) {
                IAction act = editor.getAction(ids[i]);
                if (act != null) {
                    getActionBars().setGlobalActionHandler(ids[i], act);
                } else {
                    RsdUIPlugin
                            .getLogger()
                            .error(String.format("Missing ActionHandler for: %s", ids[i])); //$NON-NLS-1$
                }
            }
            getActionBars().updateActionBars();
        }
    }

}
