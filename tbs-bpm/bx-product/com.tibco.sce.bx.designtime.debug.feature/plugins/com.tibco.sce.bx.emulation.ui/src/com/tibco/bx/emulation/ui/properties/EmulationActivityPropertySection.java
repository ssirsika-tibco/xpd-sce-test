package com.tibco.bx.emulation.ui.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.dialogs.TestpointEditDialog;
import com.tibco.bx.emulation.ui.tooltip.ToolTipHandler;
import com.tibco.bx.emulation.ui.tooltip.VariableToolTipLabelProvider;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public class EmulationActivityPropertySection extends AbstractEmulationPropertySection{
	private TreeViewer varTreeViewer;
	private Button editButton;
	private Shell shell;
	private static final String EDIT_BUTTON_ID = "edit";//$NON-NLS-1$
	public EmulationActivityPropertySection() {
		super(EmulationPackage.eINSTANCE.getTestpoint());
	}
	
	private void doEdit(){
		TestpointEditDialog dialog = new TestpointEditDialog(shell,
				EmulationUtil.createTestpointElement((Testpoint)getInput(), ((Testpoint)getInput()).getProcessNode()));
		if (dialog.open() == IDialogConstants.OK_ID) {
			ITestpointElement activityElement = (ITestpointElement) dialog.getResult()[0];
			IUndoableOperation operation = EmulationCommandHelper.getSetTestpointExpressionOperation(activityElement.getTestpoint(), activityElement.getValueString());
			try {
				OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
			} catch (ExecutionException e) {
				EmulationUIActivator.log(e);
			}
		}
	}
	
	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		shell = parent.getShell();
		int numCols = 3;
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout(numCols, false));
		
		Group group = toolkit.createGroup(composite, 
		Messages.getString("EmulationActivityPropertySection.group.label")); //$NON-NLS-1$
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		group.setLayoutData(data);
		GridLayout gLayout = new GridLayout(1, false);
		group.setLayout(gLayout);

		Tree tree = toolkit.createTree(group,SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL, "tree");//$NON-NLS-1$
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan=2;
		data.heightHint = 10 * tree.getItemHeight();
		tree.setLayoutData(data);
		
		varTreeViewer = new TreeViewer(tree);
		varTreeViewer.setContentProvider(new VariablesContentProvider());
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		//to display the type name of hovered element 
		new ToolTipHandler(tree, new VariableToolTipLabelProvider());
		
		TreeViewerColumn viewerColumn0 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn0.getColumn().setWidth(200);
		viewerColumn0.setLabelProvider(new NameColumnLabelProvider());
		viewerColumn0.getColumn().setText(Messages.getString("EmulationActivityPropertySection.column0.label")); //$NON-NLS-1$
		
		TreeViewerColumn viewerColumn1 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn1.getColumn().setWidth(300);
		viewerColumn1.setLabelProvider(new ValueColumnLabelProvider());
		viewerColumn1.getColumn().setText(Messages.getString("EmulationActivityPropertySection.column1.label")); //$NON-NLS-1$
		
		Composite subComposite = toolkit.createComposite(composite);
		subComposite.setLayout(new GridLayout(1, true));
		subComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		editButton = toolkit.createButton(subComposite, Messages.getString("EmulationActivityPropertySection.editButton.label"),SWT.FLAT, EDIT_BUTTON_ID); //$NON-NLS-1$
		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.verticalAlignment = GridData.BEGINNING;
		editButton.setLayoutData(layoutData);
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doEdit();
	        }
		});	
		
		varTreeViewer.expandAll();
		return composite;
	}

	protected Command doGetCommand(Object arg0) {
		return null;
	}

	protected void doRefresh() {
		if((Testpoint)getInput() == null || ((Testpoint)getInput()).getProcessNode() == null){
			varTreeViewer.setInput(null);
			editButton.setEnabled(false);
		}else{
			editButton.setEnabled(true);
			varTreeViewer.setInput(EmulationUtil.createTestpointElement((Testpoint)getInput(), ((Testpoint)getInput()).getProcessNode()));
			varTreeViewer.expandAll();
		}
	}
}




