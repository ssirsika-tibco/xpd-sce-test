package com.tibco.bx.debug.ui.propertypages;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;

import com.tibco.bx.debug.core.models.BxBreakpoint;

public class BreakpointConditionEditor {

	private SourceViewer fViewer;
	private String fOldValue;
	private BxBreakpointPage fPage;
	private BxBreakpoint fBreakpoint;
	private IHandlerService fHandlerService;
	private IHandler fHandler;
	private IHandlerActivation fActivation;
    private IDocumentListener fDocumentListener;
    
	/**
	 * Constructor
	 * @param parent the parent to add this widget to
	 * @param page the page that is associated with this widget
	 */
	public BreakpointConditionEditor(Composite parent, BxBreakpointPage page) {
		fPage = page;
		fBreakpoint = (BxBreakpoint) fPage.getBreakpoint();
		String condition = null;
		try {
			condition = fBreakpoint.getCondition();
			fOldValue = ""; //$NON-NLS-1$
			
			fViewer = new SourceViewer(parent, null, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			fViewer.setInput(parent);
			IDocument document = new Document();
			
			fViewer.setEditable(true);
			document.set(condition);
			fViewer.setDocument(document);
			fViewer.setUndoManager(new TextViewerUndoManager(10));
			fViewer.getUndoManager().connect(fViewer);
			fDocumentListener = new IDocumentListener() {
	            public void documentAboutToBeChanged(DocumentEvent event) {
	            }
	            public void documentChanged(DocumentEvent event) {
	                valueChanged();
	            }
	        };
			fViewer.getDocument().addDocumentListener(fDocumentListener);
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.heightHint = fPage.convertHeightInCharsToPixels(10);
			gd.widthHint = fPage.convertWidthInCharsToPixels(40);
			fViewer.getControl().setLayoutData(gd);
			fHandler = new AbstractHandler() {
				public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
					fViewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
					return null;
				}
			};
			fHandlerService = (IHandlerService) PlatformUI.getWorkbench().getAdapter(IHandlerService.class);
		} 
		catch (Exception exception) {}
	}

	/**
	 * Returns the condition defined in the source viewer.
	 * @return the contents of this condition editor
	 */
	public String getCondition() {
		return fViewer.getDocument().get();
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#refreshValidState()
	 */
	protected void refreshValidState() {
		if (!fViewer.isEditable()) {
			//fPage.removeErrorMessage(fErrorMessage);
		} else {
			String text = fViewer.getDocument().get();
			if (!(text != null && text.trim().length() > 0)) {
				//fPage.addErrorMessage(fErrorMessage);
			} else {
				//fPage.removeErrorMessage(fErrorMessage);
			}
		}
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean, org.eclipse.swt.widgets.Composite)
	 */
	public void setEnabled(boolean enabled) {
	    fViewer.setEditable(enabled);
	    fViewer.getTextWidget().setEnabled(enabled);
		if (enabled) {
			Color color = fViewer.getControl().getDisplay().getSystemColor(SWT.COLOR_WHITE);
			fViewer.getTextWidget().setBackground(color);
			fViewer.getTextWidget().setFocus();
			//fActivation = fHandlerService.activateHandler(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, fHandler);
		} else {
			Color color = fViewer.getControl().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			fViewer.getTextWidget().setBackground(color);
			if(fActivation != null) {
				fHandlerService.deactivateHandler(fActivation);
			}
		}
		valueChanged();
	}
	
	
	/**
	 * Handle that the value changed
	 */
	protected void valueChanged() {
		String newValue = fViewer.getDocument().get();
		if (!newValue.equals(fOldValue)) {
			fOldValue = newValue;
		}
		refreshValidState();
	}
	
	/**
	 * Dispose of the handlers, etc
	 */
	public void dispose() {
	    if (fViewer.isEditable()) {
	    	fHandlerService.deactivateHandler(fActivation);
	    }
	    fViewer.getDocument().removeDocumentListener(fDocumentListener);
	}
}