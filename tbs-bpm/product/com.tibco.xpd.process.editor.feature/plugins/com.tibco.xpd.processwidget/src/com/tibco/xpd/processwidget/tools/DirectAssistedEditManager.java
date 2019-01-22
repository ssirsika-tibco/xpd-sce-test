/*
 ** 
 **  MODULE:             $RCSfile: DirectAssistedEditManager.java $ 
 **                      $Revision: 1.6 $ 
 **                      $Date: 2005/05/09 15:46:49Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: DirectAssistedEditManager.java $ 
 **    Revision 1.6  2005/05/09 15:46:49Z  wzurek 
 **    Revision 1.5  2005/04/21 15:21:40Z  wzurek 
 **    Moving actual prameters 
 **    Revision 1.4  2005/03/08 13:06:55Z  wzurek 
 **    Revision 1.3  2005/02/18 17:30:35Z  wzurek 
 **    Revision 1.2  2005/01/25 10:37:32Z  WojciechZ 
 **    changes: proper error mesage when XPDL is not valid, close processes editors when close package editor, direct edit also works in response for F2 key 
 **    Revision 1.1  2004/12/16 15:50:55Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.processwidget.tools;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.contentassist.ContentAssistHandler;

/**
 * DirectEdit Manager with ContentAssist
 * 
 * @author WojciechZ
 */
public abstract class DirectAssistedEditManager extends DirectEditManager {

    private ISubjectControlContentAssistProcessor assistProcessor;

    /**
     * @param source
     * @param locator
     * @param assistProcessor
     */
    public DirectAssistedEditManager(GraphicalEditPart source,
            CellEditorLocator locator,
            ISubjectControlContentAssistProcessor assistProcessor) {
        super(source, TextCellEditor.class, locator);
        this.assistProcessor = assistProcessor;
    }

    /**
     * @see org.eclipse.gef.tools.DirectEditManager#createCellEditorOn(org.eclipse.swt.widgets.Composite)
     */
    protected CellEditor createCellEditorOn(Composite composite) {
        TextCellEditor cellEditor = (TextCellEditor) super
                .createCellEditorOn(composite);

        if (assistProcessor != null) {
            SubjectControlContentAssistant assistant;

            assistant = new SubjectControlContentAssistant();
            assistant.setContentAssistProcessor(assistProcessor,
                    IDocument.DEFAULT_CONTENT_TYPE);
            Text text = (Text) cellEditor.getControl();
            text.selectAll();

            ContentAssistHandler.createHandlerForText(text, assistant);
        }
        return cellEditor;
    }

    /**
     * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
     */
    protected void unhookListeners() {
        if (getCellEditor() != null) {
            Control c = getCellEditor().getControl();
            super.unhookListeners();

            // when the user exit the control by clicking outside the
            // cell editor, the control is disposed without notifing 
            // focus listener, this will invoke this event to uninstall
            // content assist
            c.notifyListeners(SWT.FocusOut, null);
        } else {
            super.unhookListeners();
        }
    }

    /**
     * @return Returns the assistProcessor.
     */
    public ISubjectControlContentAssistProcessor getAssistProcessor() {
        return assistProcessor;
    }

    /**
     * @param assistProcessor The assistProcessor to set.
     */
    public void setAssistProcessor(
            ISubjectControlContentAssistProcessor assistProcessor) {
        this.assistProcessor = assistProcessor;
    }
}