package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.presentation.BxModelContentProvider;
import com.tibco.bx.debug.ui.util.ModelLabelProviderUtil;

public class ShowProcessSummaryDialog extends TitleAreaDialog {

	private BxThread thread; 
	private TreeViewer treeViewer;
	private String message ; 
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Point getInitialSize(){
		return new Point(496,279);
	}

	public ShowProcessSummaryDialog(Shell parentShell,BxThread thread, String message) {
		super(parentShell);
		this.thread = thread;
		this.message = message;
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		setTitle(getTitle());
		getShell().setText(Messages.getString("ShowProcessSummaryDialog.shell.title")); //$NON-NLS-1$
		setMessage(getMessage());
		treeViewer = new TreeViewer(composite);
		treeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		String modelType = ((BxDebugTarget) thread.getDebugTarget()).getModelType();
		treeViewer.setLabelProvider(ModelLabelProviderUtil.getModelLabelProvider(modelType));
		treeViewer.setContentProvider(new BxModelContentProvider(thread));
		treeViewer.setInput(thread.getDebugTarget());
		treeViewer.expandAll();
		return composite;
	}
	

	public String getMessage(){
		if(!StringUtils.isNotBlank(message)){
			 return String.format(Messages.getString("ShowProcessSummaryDialog.message"), new Object[]{thread.getInstanceId(),thread.getState()}); //$NON-NLS-1$
		}
		return null;
	}
	
	private String getTitle(){
		String threadName = ""; //$NON-NLS-1$
		if(!StringUtils.isNotBlank(message)){
			try {
				threadName = thread.getName();
			} catch (DebugException e) {
				DebugUIActivator.log(e);
			}
		}else{
			threadName = this.message;
		}
		return threadName;
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
	    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}
}
