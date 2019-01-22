package com.tibco.bx.emulation.ui.dialogs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.util.EmulationResourceFactoryImpl;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;

public class FileListSelectionDialog extends SelectionDialog {

	/**
	 * filename extension
	 */
	String extension;

	List<IFile> fileList;
	IProject[] projects;
	TableViewer tableViewer;
	IContainer container;
	IProject currentProject;
	
	public FileListSelectionDialog(Shell shell, IContainer container,
			String extension) {
		super(shell);
		this.extension = extension;
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
		if (type == IResource.FILE) 
			fileList.add((IFile) resource);
		else {
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
	
	public String getExtension() {
		return extension;
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
		
		new Label(composite, SWT.NONE).setText(Messages.getString("FileListSelectionDialog.label.text")); //$NON-NLS-1$
		ComboViewer comboViewer = new ComboViewer(composite, SWT.BORDER);
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
						tableViewer.setInput(fileList);
					}else{
						fileList.clear();
						tableViewer.setInput(fileList);
					}
				}
			}
			
		});

		tableViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		Table table = tableViewer.getTable();
		GridData data = new GridData(GridData.FILL_BOTH);
		data = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(data);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new WorkbenchLabelProvider());
		tableViewer.addFilter(new FileFilter());
		tableViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent arg0) {
				okPressed();
			}
			
		});
		if(projects.length == 1){
			comboViewer.setSelection(new StructuredSelection(projects[2]));
			getFiles(projects[2]);
			tableViewer.setInput(fileList);
		}
		return dialogArea;
	}
	
	@Override
	protected void okPressed() {
		IFile file = (IFile)((IStructuredSelection)tableViewer.getSelection()).getFirstElement();
		if(file != null){
			List<IFile> fList = new ArrayList<IFile>();
			fList.add(file);
			setResult(fList);
		}
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 300);
	}

	protected InputStream getInitialContents() {
		EmulationData data =EmulationFactory.eINSTANCE.createEmulationData();
		EmulationResourceFactoryImpl factory = new EmulationResourceFactoryImpl();
		Resource resource = factory.createResource(null);
		resource.getContents().add(data);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			resource.save(out,Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return in;
	}

	
	final class FileFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			return element instanceof IFile && matchExtension((IFile)element);
		}
		
		protected boolean matchExtension(IFile file) {
			return getExtension().equals(file.getFileExtension());
		}
		
	}
	
	final class ProjectFilter extends ViewerFilter{

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			try {
				return element instanceof IProject && matchNature((IProject)element);
			} catch (CoreException e) {
				EmulationUIActivator.log(e);
				return false;
			}
		}
		
		protected boolean matchNature(IProject project) throws CoreException {
			return project.getNature("com.tibco.xpd.resources.xpdNature") != null; //$NON-NLS-1$
		}
		
	}
	
}
