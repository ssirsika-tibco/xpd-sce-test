package com.tibco.bx.emulation.ui.wizards;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;
public class TargetProcessesViewer implements SelectionListener{
	
	private List<EObject> checkedProcesses;
	private TreeViewer viewer;
	private List<String> filterIDList = new ArrayList<String>();
	private ListSource listSource;
	public TargetProcessesViewer(){
		checkedProcesses = new ArrayList<EObject>();
		listSource = new ListSource(checkedProcesses);
	}

	public TreeViewer createTreeViewer(Composite parent, Object input) {
		GridData data = new GridData(GridData.FILL_BOTH);
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.CHECK
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		viewer.getTree().setLayoutData(data);
		
		viewer.setContentProvider(new ProcessContentProvider());
		viewer.setLabelProvider(new ProjectExplorerLabelProvider());
		viewer.addFilter(new BpmFilter());
		viewer.setInput(input);
		viewer.expandAll();
		
		createButtons(parent);
		viewer.getTree().addSelectionListener(this);
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
		TreeItem[] items = viewer.getTree().getItems();
		for(TreeItem item:items){
			if(item.getChecked()&&!item.getGrayed()){
				continue;
			}
			item.setChecked(true);
			doCheckItem(item);
		}
	}
	
	private void decheckAll(){
		TreeItem[] items = viewer.getTree().getItems();
		for(TreeItem item:items){
			if(!item.getChecked()){
				continue;
			}
			item.setChecked(false);
			doCheckItem(item);
		}
		
	}
	
	private void doCheckItem(TreeItem item) {
		item.setGrayed(false);
		checkProgenitures(item);
		checkAncestor(item);
		listSource.setNList(checkedProcesses);
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
	
	
	public List<EObject> getAllChecked(){
		TreeItem[] children = viewer.getTree().getItems();
		checkedProcesses.clear();
		addItems(children);
		return checkedProcesses;
	}
	
	private void addItems(TreeItem[] items){
		for(TreeItem item:items){
			if(item.getChecked() && item.getData() instanceof EObject && ProcessUtil.isProcess((EObject)item.getData())){
				checkedProcesses.add((EObject)item.getData());
			}else{
				addItems(item.getItems());
			}
		}
	}
	
	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}

	public void widgetSelected(SelectionEvent event) {
		TreeItem item = (TreeItem) event.item;
		doCheckItem(item);
		
	}
	
	/**
	 * 
	 * @see ViewerFilter
	 *
	 */
	final class BpmFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if(element instanceof IFile && ProcessUtil.isProcessFile((IFile)element)) 
				return true;
			if(element instanceof SpecialFolder && EmulationUtil.isProcessSpecialFolder((SpecialFolder)element))
				return true;
			if(element instanceof IFolder) 
				return true;
			if(element instanceof EObject && (ProcessUtil.isProcess((EObject)element) || ProcessUtil.isPageflow((EObject) element))
					&& !filterIDList.contains(ProcessUtil.getElementId((EObject)element)))
				return true;
			return false;
		}
	}
	
	/**
	 * @see ITreeContentProvider
	 * 
	 */
	class ProcessContentProvider implements ITreeContentProvider {

		public Object[] getElements(Object element) {
			return getChildren(element);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getChildren(Object element) {
			if (element instanceof IProject)
				return new Object[]{EmulationUtil.getProcessSpecialFolder((IProject)element)};
			else if (element instanceof SpecialFolder)
				return getProcessFilesAndFolders(((SpecialFolder)element).getFolder());
			else if (element instanceof IFolder)
				return getProcessFilesAndFolders((IFolder)element);
			else if (element instanceof IFile)
				return ProcessUtil.getProcesses((IFile)element);
			else
				return new Object[0];
		}

		public Object getParent(Object arg0) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return !(element instanceof com.tibco.xpd.xpdl2.Process);
		}

		private Object[] getProcessFilesAndFolders(IFolder folder){
			List<IResource> list = new ArrayList<IResource>();
			try {
				IResource[] resources = folder.members();
				for (int i = 0; i < resources.length; i++) {
					if(resources[i] instanceof IFolder || ProcessUtil.isProcessFile(resources[i])){
						list.add(resources[i]);
					}
				}
			} catch (CoreException e) {
				EmulationUIActivator.log(e);
			}
			return list.toArray(new Object[list.size()]);
		}
		
	}

	public void setInput(IProject project) {
		viewer.setInput(project);
		viewer.expandAll();
	}

	public void setFilterIDList(List<String> idList) {
		filterIDList.addAll(idList);
		viewer.refresh();
	}

	protected class ListEvent extends EventObject{

		private static final long serialVersionUID = 1L;
		
		private Object object;
		private List<EObject> processList;
		
		public ListEvent(Object source, List<EObject> list) {
			super(source);
			this.object = source;
			this.processList = list;
		}

		public List<EObject> getProcessList() {
			return processList;
		}
		
	}
	
	protected interface IListEventListener extends EventListener{
		public void eventChanged(ListEvent event);
	}
	
	protected class ListSource{
		
		private List<IListEventListener> listeners = new ArrayList<IListEventListener>();
		
		private IListEventListener listener;
		
		private List<EObject> procesList;
		
		public ListSource(List<EObject> list){
			procesList = list;
		}
		
		public void addListEventListener(IListEventListener listener){
			if(!listeners.contains(listener)){
				listeners.add(listener);
			}
		}
		
		public void removeListEventListener(IListEventListener listener){
			if(listeners.contains(listener)){
				listeners.remove(listener);
			}
		}
		
		public void notifyEvent(ListEvent event){
			Iterator<IListEventListener> iter = listeners.iterator();
			while(iter.hasNext()){
				listener = (IListEventListener) iter.next();
				listener.eventChanged(event);
			}
		}

		public void setNList(List<EObject> list) {
			procesList = list;
			notifyEvent(new ListEvent(this, procesList));
		}
		
	}

	public ListSource getListSource() {
		return listSource;
	}
	
}
