package com.tibco.xpd.script.sourceviewer.internal.action;

import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.xpd.script.sourceviewer.client.ISiteProvider;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

public class TextOperationAction extends TextViewerAction {

    /** The text operation target */
    private ITextOperationTarget fOperationTarget;

    /** The text operation code */
    private int fOperationCode = -1;
    /**
     * Flag to prevent running twice trough {@link #update()} when creating this
     * action.
     * 
     * @since 3.2
     */
    private boolean fAllowUpdate = false;

    public TextOperationAction(ScriptSourceViewer viewer, int operationCode) {                
    	super(viewer);
        fOperationCode = operationCode;
        fAllowUpdate = true;
        update();
    }  
    

    /**
     * @see TextEditorAction#setEditor(ITextEditor)
     */
    public void setJScriptSourceViewer(ScriptSourceViewer viewer) {
        super.setJScriptSourceViewer(viewer);
        fOperationTarget = null;
    }

    /**
     * Runs the content assist operation on the editor's text operation target.
     */
    public void run() {
        if (fOperationCode == -1 || fOperationTarget == null) {
            return;
        }
        ScriptSourceViewer viewer = getJScriptSourceViewer();
        if (viewer == null) {
            return;
        }
        Display display = null;
        IWorkbenchPartSite site = null;
        if (viewer instanceof ISiteProvider) {
            ISiteProvider siteProvider = (ISiteProvider) viewer;
            site = siteProvider.getWorkbenchPartSite();
        }
        if (site == null) {
            return;
        }
        Shell shell = site.getShell();
        if (shell != null && !shell.isDisposed())
            display = shell.getDisplay();

        BusyIndicator.showWhile(display, new Runnable() {
            public void run() {
                fOperationTarget.doOperation(fOperationCode);
            }
        });

    }

    /**
     * The <code>TextOperationAction</code> implementation of this
     * <code>IUpdate</code> method discovers the operation through the current
     * editor's <code>ITextOperationTarget</code> adapter, and sets the
     * enabled state accordingly.
     */
    public void update() {
        if (!fAllowUpdate) {
            return;
        }

        super.update();
        ITextViewer viewer = getJScriptSourceViewer().getSourceViewer();
        if (fOperationTarget == null && viewer != null && fOperationCode != -1) {
            fOperationTarget = viewer.getTextOperationTarget();
        }
        boolean isEnabled = (fOperationTarget != null && fOperationTarget
                .canDoOperation(fOperationCode));
        setEnabled(isEnabled);
    }

}
