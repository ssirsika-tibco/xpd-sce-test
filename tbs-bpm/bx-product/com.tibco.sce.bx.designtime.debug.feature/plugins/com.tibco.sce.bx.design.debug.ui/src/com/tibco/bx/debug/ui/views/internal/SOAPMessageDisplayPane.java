package com.tibco.bx.debug.ui.views.internal;


import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;

public class SOAPMessageDisplayPane extends Composite {

    private TextViewer inputSoapViewer;
    private TextViewer outputSoapViewer;
    
    public SOAPMessageDisplayPane(Composite parent, int style , FormToolkit toolkit, String soapRequest) {
        super(parent, style);
        this.setLayout(new GridLayout(2, true));
        createInputSoapMessageText(this, toolkit);
        createOutputSoapMessageText(this, toolkit);
        
        configInputTextViewer(inputSoapViewer, soapRequest);
        configOutputTextViewer(outputSoapViewer);
    }
    
    private Section createInputSoapMessageText(Composite parent, FormToolkit toolkit) {
        Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
        section.setLayoutData(new GridData(GridData.FILL_BOTH));
        section.setLayout(new FillLayout());
        section.setText(Messages.getString("SOAPMessageDisplayPane.RequestMessage")); //$NON-NLS-1$
        Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new FillLayout());
		section.setClient(client);
        inputSoapViewer = new TextViewer(client, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI|SWT.READ_ONLY);
        toolkit.adapt(inputSoapViewer.getControl() , true, true);
        return section;
    }

    private Section createOutputSoapMessageText(Composite parent,FormToolkit toolkit) {
    	 Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
         section.setLayoutData(new GridData(GridData.FILL_BOTH));
         section.setLayout(new FillLayout());
         section.setText(Messages.getString("SOAPMessageDisplayPane.ResponseMessage")); //$NON-NLS-1$
         Composite client = toolkit.createComposite(section, SWT.WRAP);
 		 client.setLayout(new FillLayout());
 		 section.setClient(client);
         outputSoapViewer = new TextViewer(client, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI|SWT.READ_ONLY);
         toolkit.adapt(outputSoapViewer.getControl() , true, true);
         return section;
    }

    private void configOutputTextViewer(TextViewer textViewer) {
        textViewer.setDocument(new Document());
    }
    
    private void configInputTextViewer(TextViewer textViewer, String soapRequest) {
    	textViewer.setDocument(new Document(soapRequest));
    }

    public void setRequestValue(String request) {
        try {
            if( inputSoapViewer.getDocument() != null) {
                inputSoapViewer.getDocument().replace(0, inputSoapViewer.getDocument().getLength(), request);
            }
        } catch (BadLocationException e) {
        	DebugUIActivator.log(e);
        }
    }
    
    public void setResponseValue(String response) {
    	try {
        	if( outputSoapViewer.getDocument() != null) {
        		outputSoapViewer.getDocument().replace(0, outputSoapViewer.getDocument().getLength(), response);
        	}
        } catch (BadLocationException e) {
            DebugUIActivator.log(e);
        }
    }

	public TextViewer getInputSoapViewer() {
		return inputSoapViewer;
	}

	public TextViewer getOutputSoapViewer() {
		return outputSoapViewer;
	}
}
