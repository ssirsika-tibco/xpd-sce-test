package com.tibco.bx.debug.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.bx.debug.ui.util.DebugUIUtil;

public class ResultCandidatesViewer implements SelectionListener{
	
	private List<IResultCandidate> checkeds;
	private TableViewer viewer;
	public ResultCandidatesViewer(){
		checkeds = new ArrayList<IResultCandidate>();
	}

	public TableViewer createTableViewer(Composite parent, Object input) {
		GridData data = new GridData(GridData.FILL_BOTH);
		viewer = new TableViewer(parent, SWT.MULTI | SWT.CHECK
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		viewer.getTable().setLayoutData(data);
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new CandidateLabelProvider());
		viewer.setInput(input);
		
		createButtons(parent);
		checkAll();
		viewer.getTable().addSelectionListener(this);
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
		selectAll.setText("Select All"); //$NON-NLS-1$
		Button deselectAll = new Button(buttons,SWT.NONE);
		deselectAll.setText("Deselect All"); //$NON-NLS-1$
		
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
	
	private void doCheckItem(TableItem item) {
		item.setGrayed(false);
	}

	public List<IResultCandidate> getAllChecked(){
		TableItem[] children = viewer.getTable().getItems();
		checkeds.clear();
		addItems(children);
		return checkeds;
	}
	
	private void addItems(TableItem[] items){
		for(TableItem item:items){
			if(item.getChecked()){
				checkeds.add((IResultCandidate)item.getData());
			}
		}
	}
	
	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}

	public void widgetSelected(SelectionEvent event) {
		TableItem item = (TableItem) event.item;
		doCheckItem(item);
		
	}
	
	public void setInput(IResultCandidate[] processNodes) {
		viewer.setInput(processNodes);
	}

	class CandidateLabelProvider extends LabelProvider{

		@Override
		public Image getImage(Object element) {
			//IResultCandidate
			return DebugUIUtil.getProcessImage(((IResultCandidate)element).getProcessNode().getModelType());
		}
	}
}
