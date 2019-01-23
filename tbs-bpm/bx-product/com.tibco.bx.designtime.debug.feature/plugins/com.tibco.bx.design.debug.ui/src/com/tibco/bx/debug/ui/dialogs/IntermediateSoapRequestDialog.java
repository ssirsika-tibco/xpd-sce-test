package com.tibco.bx.debug.ui.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.debug.ui.Messages;

public class IntermediateSoapRequestDialog extends TitleAreaDialog {

	private TextViewer inputSoapViewer;
	private String initSoapMessage;
	private String requestSoapMessage;
	
	private String title;
	private String message;
	 
	public IntermediateSoapRequestDialog(Shell parentShell,String initSoapMessage,
										 String title, String message) {
		super(parentShell);
		this.initSoapMessage = initSoapMessage;
		this.title = title;
		this.message = message;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new FillLayout());
		inputSoapViewer = new TextViewer(composite, SWT.BORDER|SWT.V_SCROLL | SWT.H_SCROLL |SWT.MULTI);
		inputSoapViewer.setDocument(new Document(initSoapMessage));
		
		setTitle(title);
		setMessage(message);
		getShell().setText(Messages.getString("IntermediateSoapRequestDialog_Message")); //$NON-NLS-1$
		
		return composite;
	}

	
	@Override
	protected Point getInitialSize() {
		return new Point(500, 400);
	}

	@Override
	protected void okPressed() {
		requestSoapMessage = inputSoapViewer.getTextWidget().getText();
		super.okPressed();
	}

	public String getInputMessage() {
		return requestSoapMessage;
	}

	
}
