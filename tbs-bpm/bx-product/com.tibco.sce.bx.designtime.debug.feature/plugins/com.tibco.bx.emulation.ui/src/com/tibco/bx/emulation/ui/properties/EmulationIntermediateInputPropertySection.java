package com.tibco.bx.emulation.ui.properties;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public class EmulationIntermediateInputPropertySection extends AbstractEmulationPropertySection {
	private TextViewer soapMessageViewer;
	private Button autoGenButton;
	private Shell shell;
	private OperationRunnable operationRunnable;
	private ModifyListener textInputListener = new ModifyListener(){
		@Override
		public void modifyText(ModifyEvent e) {
			String text = ((StyledText)e.getSource()).getText();
			IUndoableOperation operation = EmulationCommandHelper.getSetIntermediateInputRequestMessageOperation((IntermediateInput)getInput(), text);
			if(operationRunnable != null){
				operationRunnable.popOperation();
			}
			operationRunnable = new OperationRunnable();
			operationRunnable.setOperation(operation);
			shell.getDisplay().timerExec(300, operationRunnable);
			return;
		}
		
	};
	
	
	
	public EmulationIntermediateInputPropertySection() {
		super(EmulationPackage.eINSTANCE.getIntermediateInput());
	}

	@Override
	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		shell = parent.getShell();
		Composite composite = toolkit.createComposite(parent);
		int numCols = 3;
		composite.setLayout(new GridLayout(numCols, false));
		
		Group soapMessageGroup = toolkit.createGroup(composite, "Request Message");//$NON-NLS-1$
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		soapMessageGroup.setLayoutData(data);
		GridLayout gLayout = new GridLayout(1, false);
		soapMessageGroup.setLayout(gLayout);
		
		soapMessageViewer = new TextViewer(soapMessageGroup, SWT.BORDER|SWT.V_SCROLL |SWT.H_SCROLL|SWT.MULTI);
		data = new GridData(GridData.FILL_BOTH);
		soapMessageViewer.getControl().setLayoutData(data);
		toolkit.adapt(soapMessageViewer.getControl() , true, true);
		soapMessageViewer.setDocument(new Document());
		soapMessageViewer.getTextWidget().addModifyListener(textInputListener);
		
		Composite subComposite = toolkit.createComposite(composite);
        subComposite.setLayout(new GridLayout(1, true));
        subComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		autoGenButton = toolkit.createButton(subComposite, "Generate",SWT.FLAT, "Generate");//$NON-NLS-1$ //$NON-NLS-2$
		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.verticalAlignment = GridData.BEGINNING;
		autoGenButton.setLayoutData(layoutData);
		autoGenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//TODO
	        }
		});	
		return composite;
	}

	@Override
	protected void doRefresh() {
		if (getInput() == null  || getInput().eContainer() == null) {
			setText(""); //$NON-NLS-1$
		 }else{
			 //TODO
			 setText(((IntermediateInput)getInput()).getRequestMessage());
		 }
		return;
	}

	private void setText(String text){
		try {
			if( soapMessageViewer.getDocument() != null) {
				text = (text != null) ? text : ""; //$NON-NLS-1$
			    Point point = soapMessageViewer.getTextWidget().getSelection();
            	soapMessageViewer.getDocument().replace(0, soapMessageViewer.getDocument().getLength(), text);
            	soapMessageViewer.getTextWidget().setSelection(point);
            	//TODO does not correct after undo/redo operation
            }
        } catch (BadLocationException e) {
        	EmulationUIActivator.log(e);
        }
	}
	
}
