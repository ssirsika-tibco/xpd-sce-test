package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * JSON Schema Editor action contributor to provide Undo and Redo actions.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonSchemaEditorContributor extends EditorActionBarContributor {

    /**
     * @see org.eclipse.ui.part.EditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
     * 
     * @param targetEditor
     *            The editor to contribute actions to.
     */
    @Override
    public void setActiveEditor(IEditorPart targetEditor) {
        // Set Editor Actions
        if (targetEditor instanceof JsonSchemaEditorPart) {
            JsonSchemaEditorPart editor = (JsonSchemaEditorPart) targetEditor;
            // Install Undo and Redo Action handlers
            String ids[] =
                    new String[] { ActionFactory.UNDO.getId(),
                            ActionFactory.REDO.getId(), GlobalActionId.CUT,
                            GlobalActionId.COPY, GlobalActionId.PASTE };

            for (int i = 0; i < ids.length; i++) {
                IAction act = editor.getAction(ids[i]);
                if (act != null) {
                    getActionBars().setGlobalActionHandler(ids[i], act);
                } else {
                    String message =
                            String.format(Messages.JsonSchemaEditorContributor_missingActionHandler,
                                    ids[i]);
                    IStatus status =
                            new Status(IStatus.ERROR,
                                    RestSchemaUiPlugin.PLUGIN_ID, message);
                    RestSchemaUiPlugin.getDefault().getLog().log(status);
                }
            }
            getActionBars().updateActionBars();
        }
    }

}
