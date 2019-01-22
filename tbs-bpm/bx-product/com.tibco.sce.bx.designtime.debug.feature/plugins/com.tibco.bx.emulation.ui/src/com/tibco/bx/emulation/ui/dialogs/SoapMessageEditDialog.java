package com.tibco.bx.emulation.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.emulation.ui.Messages;

public class SoapMessageEditDialog extends SelectionTitleAreaDialog {

	private String soapMessage;
	private String label;
	private TextViewer soapMessageViewer;
	public SoapMessageEditDialog(Shell parentShell, String soapMessage, String label) {
		super(parentShell);
		this.soapMessage =soapMessage;
		this.label = label;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(Messages.getString("SoapMessageEditDialog.title") + label); //$NON-NLS-1$
		setMessage(Messages.getString("SoapMessageEditDialog.message")); //$NON-NLS-1$
		getShell().setText(Messages.getString("SoapMessageEditDialog.shell.title")); //$NON-NLS-1$
		
		soapMessageViewer = new TextViewer(composite, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.MULTI);
		soapMessageViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		soapMessageViewer.setDocument(new Document());
		soapMessageViewer.getDocument().set(soapMessage);
		return composite;
	}


	@Override
	protected void okPressed() {
		List<String> aList = new ArrayList<String>();
		aList.add(soapMessageViewer.getTextWidget().getText());
		setResult(aList);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(480, 480);
	}
	
}