package com.tibco.bx.emulation.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.common.TestpointEditViewer;

public class TestpointEditDialog extends SelectionTitleAreaDialog {

	private IActivityElement activityElement;
	private TestpointEditViewer pointEditViewer;
    String[] columnHeaders = new String[]{Messages.getString("TestpointEditDialog.column0.label"),//$NON-NLS-1$
    		Messages.getString("TestpointEditDialog.column1.label") , //$NON-NLS-1$
    		Messages.getString("TestpointEditDialog.column2.label")};   //$NON-NLS-1$
	public TestpointEditDialog(Shell parentShell, IActivityElement activityElement) {
		super(parentShell);
		pointEditViewer = new TestpointEditViewer(parentShell, activityElement);
		this.activityElement =activityElement;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(Messages.getString("TestpointEditDialog.composite.title") + getElementLabel()); //$NON-NLS-1$
		setMessage(Messages.getString("TestpointEditDialog.composite.text")); //$NON-NLS-1$
		getShell().setText(Messages.getString("TestpointEditDialog.shell.label") + getElementLabel()); //$NON-NLS-1$
		pointEditViewer.createContorl(composite);
		return composite;
	}


	private String getElementLabel(){
		if(activityElement instanceof ITestpointElement){
			return "Testpoint"; //$NON-NLS-1$
		}else{
			return (((IInOutElement)activityElement).getInOutDataType() instanceof Input)? "Input" : "Output"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	
	@Override
	protected void okPressed() {
		if(activityElement.isValid()){
			List<IActivityElement> aList = new ArrayList<IActivityElement>();
			aList.add(activityElement);
			setResult(aList);
			super.okPressed();
		}else{
			MessageDialog.openError(getShell(), Messages.getString("TestpointEditDialog.errorDialog.title"),
					Messages.getString("TestpointEditDialog.errorDialog.message"));
		}
	}

	@Override
	protected Point getInitialSize() {
		return new Point(480, 480);
	}
	
	
}