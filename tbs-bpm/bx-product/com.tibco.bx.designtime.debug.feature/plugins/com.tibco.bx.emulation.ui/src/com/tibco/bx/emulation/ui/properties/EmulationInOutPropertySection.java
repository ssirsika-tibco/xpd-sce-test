package com.tibco.bx.emulation.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.IProcessVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.dialogs.TestpointEditDialog;
import com.tibco.bx.emulation.ui.tooltip.ToolTipHandler;
import com.tibco.bx.emulation.ui.tooltip.VariableToolTipLabelProvider;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

public abstract class EmulationInOutPropertySection extends AbstractEmulationPropertySection{
	private TreeViewer varTreeViewer;
	private Button editButton;
	private Shell shell;
	private StackLayout stackLayout;
	private Composite treeVieverComposite, textVieverComposite;
	private TextViewer soapMessageViewer;
	private Group soapMessageGroup;
	private static final String EDIT_BUTTON_ID = "edit";//$NON-NLS-1$
	private OperationRunnable operationRunnable;
	private ModifyListener textInputListener = new ModifyListener(){
		@Override
		public void modifyText(ModifyEvent e) {
			String text = ((StyledText)e.getSource()).getText();
			IUndoableOperation operation = EmulationCommandHelper.getSetInputSoapMessageOperation((InOutDataType)getInput(), text);
			if(operationRunnable != null){
				operationRunnable.popOperation();
			}
			operationRunnable = new OperationRunnable();
			operationRunnable.setOperation(operation);
			shell.getDisplay().timerExec(300, operationRunnable);
			return;
		}
		
	};
	
	public EmulationInOutPropertySection(EClass eClass) {
		super(eClass);
	}
	
	private void switchComposite(){
		if(getInput() == null){
			stackLayout.topControl = treeVieverComposite;
		}else{
			NamedElement inout = (NamedElement)getInput();//Input or Output
			ProcessNode processNode = (ProcessNode)inout.eContainer();
			EObject activity = ProcessUtil.getActivity(processNode.getId(), inout.getId(),processNode.getModelType());
			boolean isWS = ProcessUtil.isWebServiceImplementationActivity(activity);
			if(isWS){
				stackLayout.topControl = textVieverComposite;
				soapMessageGroup.setText(Messages.getString("EmulationInOutPropertySection_GROUP_REQUEST")); //$NON-NLS-1$
			}else{
				stackLayout.topControl = treeVieverComposite;
			}
		}
		forceLayout();
		doRefresh();
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
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		soapMessageViewer.getTextWidget().removeModifyListener(textInputListener);
		switchComposite();
		soapMessageViewer.getTextWidget().addModifyListener(textInputListener);
	}

	private void doEdit(){
		IInOutElement activityElement = null;
		if(getInput() instanceof Input)
			activityElement = EmulationUtil.createInputElement((Input)getInput(),(ProcessNode)(getInput().eContainer()));
		else
			activityElement = EmulationUtil.createOutputElement((Output)getInput(),(ProcessNode)(getInput().eContainer()));
		TestpointEditDialog dialog = new TestpointEditDialog(shell, activityElement);
		if (dialog.open() == IDialogConstants.OK_ID) {
			activityElement = (IInOutElement) dialog.getResult()[0];
			try {
				OperationHistoryFactory.getOperationHistory().execute(getUpdateInOutDataOperation(activityElement), null, null);
	    	 } catch (ExecutionException e) {
					EmulationUIActivator.log(e);
	    	 }
		}
	}
	
	private IUndoableOperation getUpdateInOutDataOperation(IInOutElement activityElement){
		List<Parameter> list = new ArrayList<Parameter>();
		IVariableElement[] elements = activityElement.getVariableElements();
		for (int i = 0; i < elements.length; i++) {
			Parameter parameter = EmulationFactory.eINSTANCE.createParameter();
			parameter.setName(elements[i].getName());
			parameter.setId(ProcessUtil.getElementId(elements[i].getEMFCharacter()));
			parameter.setValue(((IProcessVariableElement)elements[i]).getVariableValueString());
			list.add(parameter);
		}
		
		return EmulationCommandHelper.getSetInOutOperation((InOutDataType)getInput(), list);
	}
	
	protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
		shell = parent.getShell();
		stackLayout = new StackLayout();
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(stackLayout);
		treeVieverComposite = createTreeVieverPane(composite, toolkit);
		textVieverComposite = createTextViewPane(composite, toolkit);
		return composite;
	}

	private Composite createTreeVieverPane(Composite parent, XpdFormToolkit toolkit){
		Composite composite = toolkit.createComposite(parent);
		int numCols = 3;
		composite.setLayout(new GridLayout(numCols, false));
		
		Group group = toolkit.createGroup(composite, Messages.getString("EmulationInOutPropertySection.group.label")); //$NON-NLS-1$
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
		viewerColumn0.getColumn().setText(Messages.getString("EmulationInOutPropertySection.column0.label")); //$NON-NLS-1$
		
		TreeViewerColumn viewerColumn1 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn1.getColumn().setWidth(300);
		viewerColumn1.setLabelProvider(new ValueColumnLabelProvider());
		viewerColumn1.getColumn().setText(Messages.getString("EmulationInOutPropertySection.column1.label")); //$NON-NLS-1$
		
        Composite subComposite = toolkit.createComposite(composite);
        subComposite.setLayout(new GridLayout(1, true));
        subComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		editButton = toolkit.createButton(subComposite, Messages.getString("EmulationInOutPropertySection.editButton.label"),SWT.FLAT, EDIT_BUTTON_ID); //$NON-NLS-1$
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
	
	private Composite createTextViewPane(Composite parent, XpdFormToolkit toolkit){
		Composite composite = toolkit.createComposite(parent);
		int numCols = 3;
		composite.setLayout(new GridLayout(numCols, false));
		
		soapMessageGroup = toolkit.createGroup(composite, "");//$NON-NLS-1$
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
		return composite;
	}
	
	@Override
	protected Command doGetCommand(Object arg0) {
		return null;
	}

	@Override
	protected void doRefresh() {
		if (getInput() == null || getInput().eContainer() == null) {
		      setText(""); //$NON-NLS-1$
		      varTreeViewer.setInput(null);
			  editButton.setEnabled(false);
		 }else{
			 //TODO
			 setText(((InOutDataType)getInput()).getSoapMessage());
			 editButton.setEnabled(true);
			 if(getInput() instanceof Input)
				 varTreeViewer.setInput(EmulationUtil.createInputElement((Input)getInput(), (ProcessNode)getInput().eContainer()));
			 else
				 varTreeViewer.setInput(EmulationUtil.createOutputElement((Output)getInput(), (ProcessNode)getInput().eContainer()));
			 varTreeViewer.expandAll();
		 }
		return;
	}
}




