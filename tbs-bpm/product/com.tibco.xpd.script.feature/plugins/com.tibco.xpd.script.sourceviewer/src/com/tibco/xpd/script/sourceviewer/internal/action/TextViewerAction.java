package com.tibco.xpd.script.sourceviewer.internal.action;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.texteditor.IUpdate;

import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

public class TextViewerAction extends Action implements IUpdate{

    private ScriptSourceViewer textViewer;

    public TextViewerAction(ScriptSourceViewer viewer) {        
        super();
        this.setJScriptSourceViewer(viewer);
    }

    /**
     * @param textViewer the textViewer to set
     */
    public void setJScriptSourceViewer(ScriptSourceViewer textViewer) {
        this.textViewer = textViewer;
    }

    /**
     * @return the textViewer
     */
    public ScriptSourceViewer getJScriptSourceViewer() {
        return textViewer;
    }
    
    /**
     * Always enables this action if it is connected to a text editor.
     * If the associated editor is <code>null</code>, the action is disabled.
     * Subclasses may override.
     */
    public void update() {
        setEnabled(textViewer.getSourceViewer() != null);
    }  
    

}
