package com.tibco.xpd.n2.globalsignal.resource.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.editor.GsdFormEditor;

/**
 * GSD editor action bars contributor.
 * 
 * @author sajain
 */
public class GsdEditorContributor extends
        EditorActionBarContributor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActiveEditor(IEditorPart targetEditor) {
        // Set Editor Actions
        if (targetEditor instanceof GsdFormEditor) {

            GsdFormEditor editor =
                    (GsdFormEditor) targetEditor;
            String ids[] =
                    new String[] { ActionFactory.UNDO.getId(),
                            ActionFactory.REDO.getId() /*
                                                        * ,
                                                        * ActionFactory.CUT.getId
                                                        * (),
                                                        * ActionFactory.COPY.
                                                        * getId(),
                                                        * ActionFactory.
                                                        * PASTE.getId(),
                                                        * ActionFactory
                                                        * .DELETE.getId()
                                                        */
                    };

            for (int i = 0; i < ids.length; i++) {
                IAction act = editor.getAction(ids[i]);
                if (act != null) {
                    getActionBars().setGlobalActionHandler(ids[i], act);
                } else {
                    GsdResourcePlugin
                            .getDefault()
                            .logError(String.format("Missing ActionHandler for: %s", ids[i])); //$NON-NLS-1$
                }
            }
            getActionBars().updateActionBars();
        }
    }
}
