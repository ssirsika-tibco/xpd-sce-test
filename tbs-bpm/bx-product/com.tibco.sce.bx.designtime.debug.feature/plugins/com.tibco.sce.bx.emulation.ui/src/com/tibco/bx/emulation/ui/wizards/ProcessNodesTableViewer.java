package com.tibco.bx.emulation.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.navigator.EMNavigatorLabelProvider;
public class ProcessNodesTableViewer{
	
	private List<ProcessNode> checkeds;
	private TableViewer viewer;
	public ProcessNodesTableViewer(){
		checkeds = new ArrayList<ProcessNode>();
	}

	public TableViewer createTableViewer(Composite parent, Object input) {
		GridData data = new GridData(GridData.FILL_BOTH);
		viewer = new TableViewer(parent, SWT.MULTI | SWT.CHECK
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		viewer.getTable().setLayoutData(data);
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new EMNavigatorLabelProvider());
		viewer.setInput(input);
		
		checkAll();
		createButtons(parent);
		return viewer;
	}
	
	private void createButtons(Composite parent) {
		Composite buttons = new Composite(parent,SWT.NONE);
		buttons.setLayout(new GridLayout(2,true));
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.END;
		gd.verticalAlignment = GridData.END;
		buttons.setLayoutData(gd);
		
		Button selectAll = new Button(buttons,SWT.NONE);
		selectAll.setText(Messages.getString("TargetProcessesViewer.selectAll")); //$NON-NLS-1$
		Button deselectAll = new Button(buttons,SWT.NONE);
		deselectAll.setText(Messages.getString("TargetProcessesViewer.deselectAll")); //$NON-NLS-1$
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		selectAll.setLayoutData(gridData);
		deselectAll.setLayoutData(gridData);
		
		selectAll.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				checkAll();
			}
		});
		deselectAll.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				decheckAll();
			}
		});
	}
	
	private void checkAll(){
		TableItem[] items = viewer.getTable().getItems();
		for(TableItem item:items){
			if(item.getChecked()&&!item.getGrayed()){
				continue;
			}
			item.setChecked(true);
		}
	}
	
	private void decheckAll(){
		TableItem[] items = viewer.getTable().getItems();
		for(TableItem item:items){
			if(!item.getChecked()){
				continue;
			}
			item.setChecked(false);
		}
	}
	
	public List<ProcessNode> getAllChecked(){
		TableItem[] children = viewer.getTable().getItems();
		checkeds.clear();
		addItems(children);
		return checkeds;
	}
	
	private void addItems(TableItem[] items){
		for(TableItem item:items){
			if(item.getChecked()){
				checkeds.add((ProcessNode)item.getData());
			}
		}
	}

	public void setInput(ProcessNode[] processNodes) {
		viewer.setInput(processNodes);
	}

}
