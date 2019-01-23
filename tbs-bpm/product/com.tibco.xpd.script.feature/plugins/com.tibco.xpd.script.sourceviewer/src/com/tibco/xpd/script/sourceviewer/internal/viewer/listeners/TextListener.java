package com.tibco.xpd.script.sourceviewer.internal.viewer.listeners;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;

import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;

/**
 * Internal text listener for updating all content dependent actions. The
 * updating is done asynchronously.
 */
public class TextListener implements ITextListener, ITextInputListener {

    private ScriptSourceViewer scriptSourceViewer;

    public TextListener(ScriptSourceViewer scriptSourceViewer) {
        this.scriptSourceViewer = scriptSourceViewer;
    }

    /** The posted updater code. */
    private Runnable fRunnable = new Runnable() {
        public void run() {
            fIsRunnablePosted = false;
            SourceViewer fSourceViewer = scriptSourceViewer.getSourceViewer();
            if (fSourceViewer != null) {
                updateContentDependentActions();

                // remember the last edit position
                if (isDirty() && fUpdateLastEditPosition) {
                    fUpdateLastEditPosition = false;
                    ISelection sel = scriptSourceViewer.getSourceViewer()
                            .getSelection();
                    IDocument document = scriptSourceViewer.getSourceViewer()
                            .getDocument();

                    if (fLocalLastEditPosition != null) {
                        document.removePosition(fLocalLastEditPosition);
                        fLocalLastEditPosition = null;
                    }

                    if (sel instanceof ITextSelection && !sel.isEmpty()) {
                        ITextSelection s = (ITextSelection) sel;
                        fLocalLastEditPosition = new Position(s.getOffset(), s
                                .getLength());
                        try {
                            document.addPosition(fLocalLastEditPosition);
                        } catch (BadLocationException ex) {
                            fLocalLastEditPosition = null;
                        }
                    }
                }
            }
        }
    };

    /** Display used for posting the updater code. */
    private Display fDisplay;

    /**
     * The editor's last edit position
     * 
     * @since 3.0
     */
    private Position fLocalLastEditPosition;

    /**
     * Has the runnable been posted?
     * 
     * @since 3.0
     */
    private boolean fIsRunnablePosted = false;

    /**
     * Should the last edit position be updated?
     * 
     * @since 3.0
     */
    private boolean fUpdateLastEditPosition = false;

    /*
     * @see ITextListener#textChanged(TextEvent)
     */
    public void textChanged(TextEvent event) {

        /*
         * Also works for text events which do not base on a DocumentEvent. This
         * way, if the visible document of the viewer changes, all content
         * dependent actions are updated as well.
         */
        if(getSite() != null){        
            if (fDisplay == null)
                fDisplay = getSite().getShell().getDisplay();
    
            if (event.getDocumentEvent() != null)
                fUpdateLastEditPosition = true;
    
            if (!fIsRunnablePosted) {
                fIsRunnablePosted = true;
                fDisplay.asyncExec(fRunnable);
            }
        }
    }

    /*
     * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument,
     *      org.eclipse.jface.text.IDocument)
     */
    public void inputDocumentAboutToBeChanged(IDocument oldInput,
            IDocument newInput) {
        if (oldInput != null && fLocalLastEditPosition != null) {
            oldInput.removePosition(fLocalLastEditPosition);
            fLocalLastEditPosition = null;
        }
    }

    /*
     * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument,
     *      org.eclipse.jface.text.IDocument)
     */
    public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
    }

    private boolean isDirty() {
        // TODO, kam need to check what to do here
        return true;
    }

    private IWorkbenchPartSite getSite() {
        return this.scriptSourceViewer.getWorkbenchPartSite();
    }

    /**
     * Updates all content dependent actions.
     */
    protected void updateContentDependentActions() {
        this.scriptSourceViewer.updateContentDependentActions();
    }

}
