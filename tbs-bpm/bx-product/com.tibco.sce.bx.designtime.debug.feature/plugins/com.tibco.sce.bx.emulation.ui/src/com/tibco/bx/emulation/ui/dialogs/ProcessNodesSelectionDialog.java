package com.tibco.bx.emulation.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.wizards.ProcessNodesTreeViewer;

public class ProcessNodesSelectionDialog extends SelectionTitleAreaDialog {

	List<IFile> fileList;
	IProject[] projects;
	IContainer container;
	IProject currentProject;
	ProcessNodesTreeViewer viewer;
	public ProcessNodesSelectionDialog(Shell shell, IContainer container) {
		super(shell);
		this.container = container;
		fileList = new ArrayList<IFile>();
		init();
	}

	private void init(){
		if(container instanceof IWorkspaceRoot){
			projects = ((IWorkspaceRoot)container).getProjects();
		}else if(container instanceof IProject || container instanceof IFolder){
			projects = new IProject[]{container.getProject()};
		}
	}
	
	private void getFiles(IProject project){
		currentProject = project;
		fileList.clear();
		addFiles(project);
	}
	
	private void addFiles(final IResource resource) {
		int type = resource.getType();
		if (type == IResource.FILE && EmulationCoreActivator.EMULATION_FILE_EXTENSION
				.equalsIgnoreCase(((IFile) resource).getFileExtension())) 
			fileList.add((IFile) resource);
		else if(type != IResource.FILE){
			IResource[] resources = null;
			try {
				resources = ((IContainer) resource).members();
			} catch (CoreException e) {
				// ignore
				e.printStackTrace();
			}
			for (int i = 0; i < resources.length; i++) {
				addFiles(resources[i]);
			}
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		Composite composite = new Composite(dialogArea, SWT.NONE);
		GridLayout layout =new GridLayout();
		layout.makeColumnsEqualWidth = false;
		layout.numColumns = 1;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		setTitle(Messages.getString("ProcessNodesSelectionDialog.title"));  //$NON-NLS-1$
	    setMessage(Messages.getString("ProcessNodesSelectionDialog.message"));  //$NON-NLS-1$
	        
		new Label(composite, SWT.NONE).setText(Messages.getString("ProcessNodesSelectionDialog.label.text")); //$NON-NLS-1$
		ComboViewer comboViewer = new ComboViewer(composite, SWT.BORDER|SWT.READ_ONLY);
		comboViewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setLabelProvider(new WorkbenchLabelProvider());
		comboViewer.setInput(projects);
		comboViewer.addFilter(new ProjectFilter());
		
		comboViewer.addPostSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				Object object = event.getSelection();
				if(object instanceof IStructuredSelection){
					IProject project = (IProject)((IStructuredSelection)object).getFirstElement();
					if(project != null){
						getFiles(project);
						viewer.getTreeViewer().refresh(true);
						viewer.getTreeViewer().expandAll();
					}else{
						fileList.clear();
						viewer.getTreeViewer().refresh(true);
					}
				}
			}
			
		});

		viewer = new ProcessNodesTreeViewer();
		viewer.createTreeeViewer(composite, fileList);
		viewer.getTreeViewer().addPostSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(hasDuplicateNodes()){
					setErrorMessage(Messages.getString("ProcessNodesSelectionDialog.errorMessage.text")); //$NON-NLS-1$
					getButton(ProcessNodesSelectionDialog.OK).setEnabled(false);
				}else{
					setMessage(Messages.getString("ProcessNodesSelectionDialog.message.text")); //$NON-NLS-1$
					setErrorMessage(null);
					getButton(ProcessNodesSelectionDialog.OK).setEnabled(true);
				}
			}
			
		});
		if(projects.length == 1){
			comboViewer.setSelection(new StructuredSelection(projects[2]));
			getFiles(projects[2]);
		}
		return dialogArea;
	}
	
	@Override
	protected void okPressed() {
		setResult(viewer.getAllChecked());
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 400);
	}

	private boolean hasDuplicateNodes(){
		List<ProcessNode> list = viewer.getAllChecked();
		List<String> ids = new ArrayList<String>();
		for (ProcessNode processNode : list) {
			if(ids.contains(processNode.getId())){
				return true;
			}else{
				ids.add(processNode.getId());
			}
		}
		return false;
	}

	final class ProjectFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			try {
				if(element instanceof IProject){
					IProject project = (IProject)element;
					return !".debug".equals(project.getName()) && project.isOpen() && matchNature(project); //$NON-NLS-1$
				}
				
			} catch (CoreException e) {
				EmulationUIActivator.log(e);
				return false;
			}
			return false;
		}
		
		protected boolean matchNature(IProject project) throws CoreException {
			return project.getNature("com.tibco.xpd.resources.xpdNature") != null; //$NON-NLS-1$
		}
		
	}
	
	
}
