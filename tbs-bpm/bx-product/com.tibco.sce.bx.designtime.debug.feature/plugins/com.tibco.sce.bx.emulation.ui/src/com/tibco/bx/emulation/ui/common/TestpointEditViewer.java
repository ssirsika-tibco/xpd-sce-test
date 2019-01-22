package com.tibco.bx.emulation.ui.common;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.common.IActivityElement;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.IProcessVariableElement;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.emulation.core.common.VariableElement;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.dialogs.VariableListSelectionDialog;
import com.tibco.bx.emulation.ui.properties.ListCellEditor;
import com.tibco.bx.emulation.ui.properties.NameColumnLabelProvider;
import com.tibco.bx.emulation.ui.properties.TypeColumnLabelProvider;
import com.tibco.bx.emulation.ui.properties.ValueColumnLabelProvider;
import com.tibco.bx.emulation.ui.properties.VariablesContentProvider;

public class TestpointEditViewer {

	private IActivityElement activityElement;
	private TreeViewer varTreeViewer;
	private Button addButton, deleteButton;
	private Shell shell;
	
	
    String[] columnHeaders = new String[]{Messages.getString("TestpointEditViewer.column0.label"),//$NON-NLS-1$
    		Messages.getString("TestpointEditViewer.column1.label") ,//$NON-NLS-1$
    		Messages.getString("TestpointEditViewer.column2.label")};//$NON-NLS-1$
    public TestpointEditViewer(Shell shell, IActivityElement activityElement) {
    	this.shell = shell;
    	this.activityElement = activityElement;
    }
    
    public Composite createFixedControl(Composite parent, boolean readOnly){
    	Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		if(parent.getLayout() instanceof GridLayout){
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		
		createTreeViwer(composite, readOnly);
		
        varTreeViewer.setInput(activityElement);
        if(activityElement != null && activityElement.getVariableElements().length ==0){
        	addAllInput();
        }
        if(!readOnly)
        	addDelKeyListener();
        return composite;
    }
    
    private void createTreeViwer(Composite composite, boolean readOnly){
    	Tree tree = new Tree(composite,SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData data = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(data);
		varTreeViewer = new TreeViewer(tree);
		varTreeViewer.setContentProvider(new VariablesContentProvider());
		varTreeViewer.setColumnProperties(columnHeaders);
		
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		TreeViewerColumn viewerColumn0 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn0.getColumn().setWidth(150);
		viewerColumn0.getColumn().setText(columnHeaders[0]);
		viewerColumn0.setLabelProvider(new NameColumnLabelProvider());
		
		TreeViewerColumn viewerColumn1 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn1.getColumn().setWidth(150);
		viewerColumn1.getColumn().setText(columnHeaders[1]);
		viewerColumn1.setLabelProvider(new TypeColumnLabelProvider());
		
		TreeViewerColumn viewerColumn2 = new TreeViewerColumn(varTreeViewer, SWT.LEFT);
		viewerColumn2.getColumn().setWidth(150);
		viewerColumn2.getColumn().setText(columnHeaders[2]);
		viewerColumn2.setLabelProvider(new ValueColumnLabelProvider());
		if(!readOnly)
			viewerColumn2.setEditingSupport(new ValueEditingSupport(varTreeViewer));
        
    }
    
    private void addDelKeyListener(){
    	 varTreeViewer.getTree().addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.DEL){
						Object source = e.getSource();
						if(source instanceof Tree){
							TreeItem[] items = varTreeViewer.getTree().getSelection();
							if(items.length > 0){
								IVariableElement var = (IVariableElement)items[0].getData();
								TreeItem parentItem = items[0].getParentItem();
								if(parentItem != null){//handle items of IVariableElementList
									IVariableElement parentVar = (IVariableElement)parentItem.getData();
									if(parentVar instanceof IVariableElementList){
										doDelete();
									}
								}
							}
						}
					}
				}
			});
    }
    
    public void setInput(IActivityElement activityElement){
    	this.activityElement = activityElement;
		if (!varTreeViewer.getControl().isDisposed()) {
			varTreeViewer.setInput(this.activityElement);
			// addAllInput();
			varTreeViewer.refresh(true);
			varTreeViewer.expandAll();
		}
    }
    
	public IActivityElement getInput() {
		if (varTreeViewer != null) {
			varTreeViewer.refresh(true);
		}
		return activityElement;
	}
	
    //only for BPM
    private void addAllInput() {
        if(activityElement == null) {
            return ;
        }
        activityElement.clearVariableElements();
		EObject process = ProcessUtil.getProcess(activityElement.getProcessId(), activityElement.getProcessModelType());
		if (process != null && activityElement instanceof IInOutElement) {
			List<EObject> pList = ProcessUtil.getAllParms(process);
				for (EObject prd : pList) {
					if(((IInOutElement)activityElement).isVisual(prd)){
						activityElement.createVariableElement(prd, null);
					}
				}
			varTreeViewer.refresh(true);
			varTreeViewer.expandAll();
		}
	}
    
    public Composite createContorl(Composite parent){
    	Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createTreeViwer(composite, false);
		addDelKeyListener();
		
        if(activityElement instanceof ITestpointElement){
        	Composite buttonContainer = new Composite(composite, SWT.NONE);
            buttonContainer.setLayout(new GridLayout(2, true));
            GridData gd = new GridData();
     		gd.horizontalAlignment = GridData.END;
     		gd.verticalAlignment = GridData.END;
     		buttonContainer.setLayoutData(gd);
     		addButton = new Button(buttonContainer, SWT.NONE);
     		addButton.setText(Messages.getString("TestpointEditViewer.addButton.label")); //$NON-NLS-1$
     		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
     		addButton.addSelectionListener(new SelectionAdapter() {
     			public void widgetSelected(SelectionEvent e) {
     				doAdd();
     	        }
     		});	
     		deleteButton = new Button(buttonContainer, SWT.NONE);
     		deleteButton.setText(Messages.getString("TestpointEditViewer.deleteButton.label")); //$NON-NLS-1$
     		deleteButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
     		deleteButton.addSelectionListener(new SelectionAdapter() {
     			public void widgetSelected(SelectionEvent e) {
     				doDelete();
     	        }
     		});
        }
       
		//should set input after setting tree columns
		varTreeViewer.setInput(activityElement);
		varTreeViewer.expandAll();
		
		return composite;
    }
    
    private void doAdd(){
		EObject process = ProcessUtil.getProcess(activityElement.getProcessId(), activityElement.getProcessModelType());
		//TODO if process is null
		List<EObject> pList = ProcessUtil.getAllParms(process);
		removeExistVariable(pList);
		VariableListSelectionDialog dialog = new VariableListSelectionDialog(shell, pList);
		if (dialog.open() == IDialogConstants.OK_ID) {
        	Object[] variables = dialog.getResult();
        	if(variables.length > 0){
        		for (int i = 0; i < variables.length; i++) {
        			activityElement.createVariableElement((EObject)variables[i], null);
				}
        	}
        }
		varTreeViewer.refresh(true);
		varTreeViewer.expandAll();
	}
    
	//only remove Process Variable Elements
	private void removeExistVariable(List<EObject> pList){
		IVariableElement[] elements = activityElement.getVariableElements();
		for(int i=0; i<elements.length;i++){
			if(pList.remove(elements[i].getEMFCharacter()));
		}
	}
	
	private void doDelete(){
		if(activityElement != null){
			TreeItem[] items = varTreeViewer.getTree().getSelection();
			if(items.length > 0){
				IVariableElement var = (IVariableElement)items[0].getData();
				TreeItem parentItem = items[0].getParentItem();
				if(parentItem != null){//handle items of IVariableElementList
					IVariableElement parentVar = (IVariableElement)parentItem.getData();
					if(parentVar instanceof IVariableElementList){
						//TODO should check lower bound
						((IVariableElementList)parentVar).removeVariableElement(var);
					}
				}else if (var instanceof IProcessVariableElement){
					activityElement.removeVariableElement(var);
				}
			}
			varTreeViewer.refresh(true);
			varTreeViewer.expandAll();
		}
	}
	
	class ValueEditingSupport extends EditingSupport implements ICellEditorListener, TraverseListener, FocusListener {

		private TreeViewer viewer;
		private CellEditor currentCell;
		private IVariableElement varElement;

		public ValueEditingSupport(TreeViewer treeViewer) {
			super(treeViewer);
			this.viewer = treeViewer;
		}

		protected boolean canEdit(Object element) {
			return (element instanceof IVariableElementList)
					|| ((element instanceof IVariableElement) && !((IVariableElement) element).hasVariables());
		}

		protected CellEditor getCellEditor(Object element) {
			CellEditor cellEditor = CellEditorFactory.getInstance().createCellEditor(viewer.getTree(), (IVariableElement) element);
			if (cellEditor instanceof ListCellEditor) {
				cellEditor.addListener(this);
			}
			cellEditor.getControl().addTraverseListener(this);
			cellEditor.getControl().addFocusListener(this);
			currentCell = cellEditor;
			varElement = (IVariableElement) element;
			return cellEditor;
		}

		protected Object getValue(Object element) {
			return ((IVariableElement) element).getValue();
		}

		protected void setValue(Object element, Object value) {
			if (element instanceof IVariableElement) {
				IVariableElement var = (IVariableElement) element;
				if (value == null) {
					// keep the former value
				} else if (value instanceof String) {
					var.setValueString((String) value);
				} else {
					var.setValueString(value.toString());
				}
				viewer.update(element, null);
				updateParent((IVariableElement) element);
			}
		}

		private void updateParent(IVariableElement element) {
			if ((element instanceof VariableElement)) {
				IVariableElement parent = ((VariableElement) element).getParent();
				if (parent != null) {
					viewer.update(parent, null);
					updateParent(parent);
				}
			}
		}

		@Override
		public void applyEditorValue() {
			viewer.refresh();
		}

		@Override
		public void cancelEditor() {
			// TODO Auto-generated method stub
		}

		@Override
		public void editorValueChanged(boolean oldValidState, boolean newValidState) {
		}

		@Override
		public void keyTraversed(TraverseEvent e) {
			if (e.character != SWT.TAB) {
				return;
			}
			Rectangle bound = currentCell.getControl().getBounds();
			Tree tree = (Tree) getViewer().getControl();

			int x = bound.x;
			int y = bound.y + bound.height + 2;
			Event origin = new Event();
			origin.widget = tree;
			origin.x = x;
			origin.y = y;
			origin.display = tree.getDisplay();
			origin.time = e.time;
			origin.count = 1;
			origin.type = SWT.MouseDown;
			origin.button = 1;

			tree.notifyListeners(SWT.MouseDown, origin);
		}

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {
			if (e.getSource() instanceof Text) {
				String value = ((Text) e.getSource()).getText().trim();
				setValue(varElement, value);
			}
		}

	}
	
}
