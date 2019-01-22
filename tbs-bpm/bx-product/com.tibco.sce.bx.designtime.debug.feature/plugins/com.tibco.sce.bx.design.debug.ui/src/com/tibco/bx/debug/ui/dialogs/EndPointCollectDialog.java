package com.tibco.bx.debug.ui.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.debug.core.util.NetUtil;
import com.tibco.bx.debug.ui.Messages;

public class EndPointCollectDialog extends InputDialog {

	public EndPointCollectDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue, IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	@Override
	protected void okPressed() {
		try {
			if(NetUtil.validateURLViaNet(this.getValue())){
				super.okPressed();
			}
		} catch (Exception e) {
			setErrorMessage(Messages.getString("EndPointCollectDialog_ErrorMessage")); //$NON-NLS-1$
		}
		
	}
	
	

}
