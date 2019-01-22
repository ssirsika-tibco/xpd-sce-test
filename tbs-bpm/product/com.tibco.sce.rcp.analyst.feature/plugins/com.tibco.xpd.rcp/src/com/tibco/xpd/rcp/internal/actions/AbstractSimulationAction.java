/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;

/**
 * Abstract Simulation Action class
 * 
 * @author mtorres
 * 
 */
public abstract class AbstractSimulationAction extends EditorEventAction {

    public AbstractSimulationAction(IWorkbenchWindow window, String title,
            String id, String toolTipTex) {
        super(window, title);
        setId(id);
        setToolTipText(toolTipTex);

    }

    /**
     * Get the actual input object of the editor.
     * 
     * @param input
     * @return input object or <code>null</code> if one cannot be found.
     */
    protected Object getInput(IEditorInput input) {
        Object ret = null;

        if (input instanceof ProcessEditorInput) {
            ret = ((ProcessEditorInput) input).getProcess();
        }

        return ret;
    }
}
