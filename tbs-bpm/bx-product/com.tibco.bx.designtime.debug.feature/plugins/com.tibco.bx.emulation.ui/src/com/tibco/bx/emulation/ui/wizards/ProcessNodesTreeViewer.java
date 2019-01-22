package com.tibco.bx.emulation.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.navigator.EMNavigatorLabelProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
public class ProcessNodesTreeViewer implements SelectionListener{
	
	private List<ProcessNode> checkeds;
	private TreeViewer viewer;
	public ProcessNodesTreeViewer(){
		checkeds = new ArrayList<ProcessNode>();
	}

	public TreeViewer createTreeeViewer(Composite parent, Object input) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.CHECK
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		
		GridData data = new GridData(GridData.FILL_BOTH);
		viewer.getTree().setLayoutData(data);
		
		viewer.setContentProvider(new EmulationFileContentProvider());
		viewer.setLabelProvider(new EMNavigatorLabelProvider());
		viewer.setInput(input);
		
		viewer.addFilter(new DataFilter());
		viewer.getTree().addSelectionListener(this);
		return viewer;
	}
	
	public TreeViewer getTreeViewer(){
		return viewer;
	}
	
	public List<ProcessNode> getAllChecked(){
		TreeItem[] children = viewer.getTree().getItems();
		checkeds.clear();
		addItems(children);
		return checkeds;
	}
	
	private void addItems(TreeItem[] items){
		for(TreeItem item:items){
			if(item.getChecked() && item.getData() instanceof ProcessNode){
				checkeds.add((ProcessNode)item.getData());
			}else{
				addItems(item.getItems());
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		TreeItem item = (TreeItem) e.item;
		doCheckItem(item);
	}
	
	private void doCheckItem(TreeItem item) {
		item.setGrayed(false);
		
		checkProgenitures(item);
		checkAncestor(item);
	}

	private void checkAncestor(TreeItem item) {
		TreeItem parentItem = item.getParentItem();
		if(parentItem==null){
			return;
		}
		TreeItem[] children = parentItem.getItems();
		parentItem.setChecked(item.getChecked());
		boolean isSame = true;
		boolean isGrayed = false;
		for(TreeItem child:children){
			if(child.getChecked()!=item.getChecked()){
				isSame = false;
				isGrayed = true;
				break;
			}
			if(child.getGrayed()){
				isGrayed = true;
			}
		}
		if(!isSame){
			parentItem.setChecked(true);
		}
		parentItem.setGrayed(isGrayed);
		checkAncestor(parentItem);
		return;
	}

	private void checkProgenitures(TreeItem item) {
		TreeItem[] children = item.getItems();
		boolean checked = item.getChecked();
		for(TreeItem child:children){
			child.setChecked(checked);
			child.setGrayed(false);
			checkProgenitures(child);
		}
		return;
	}
	
	final class DataFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if(element instanceof ProcessNode) return true;
			if(element instanceof IFile){
				WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy((IFile)element);
				if(workingCopy != null){
					EObject object = workingCopy.getRootElement();
					if(object instanceof EmulationData)
						return true;
				}
			}
			return false ;
		}
		
	}
	
	 /**
     * Content provider for the ProcessNodes dialog
     * 
     */
    class EmulationFileContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        public Object getParent(Object element) {
            if (element instanceof ProcessNode) {
                return WorkingCopyUtil.getFile((ProcessNode) element);
            }
            return null;
        }

        public boolean hasChildren(Object element) {

            if (element instanceof IFile) {
                return getElements(element).length > 0;
            }
            return false;
        }

        @SuppressWarnings("unchecked") //$NON-NLS-1$
        public Object[] getElements(Object inputElement) {
        	if(inputElement instanceof List){
        		return ((List)inputElement).toArray();
        	}else if (inputElement instanceof IFile) {
                // get ProcessNodes
            	WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy((IFile)inputElement);
				if(workingCopy != null){
					EObject object = workingCopy.getRootElement();
					if(object instanceof EmulationData)
						return ((EmulationData)object).getProcessNodes().toArray();
				}
            }
            return new Object[0];
        }

        public void dispose() {
            // Do nothing
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        	// Do nothing
        }
    }
}
