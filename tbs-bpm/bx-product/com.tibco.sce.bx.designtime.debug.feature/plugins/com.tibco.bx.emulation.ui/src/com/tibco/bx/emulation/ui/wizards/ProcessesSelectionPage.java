package com.tibco.bx.emulation.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.xpdl2.Process;

public class ProcessesSelectionPage extends WizardPage implements ISelectionPage{

	private IEmulationWizard parentWizard;
	private TargetProcessesViewer viewer;
	private List<String> FilterProcessIDs = new ArrayList<String>();
	protected ProcessesSelectionPage(IEmulationWizard parentWizard) {
		super("ProcessesPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ProcessesSelectionPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("ProcessesSelectionPage_DESC")); //$NON-NLS-1$
		this.parentWizard = parentWizard;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		IProject project = (IProject)parentWizard.getInput();
		viewer = new TargetProcessesViewer();
		TreeViewer tv = viewer.createTreeViewer(composite, project);
		tv.addPostSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setPageComplete(getAllChecked().size()>0);
			}		
		});
		viewer.setFilterIDList(FilterProcessIDs);
		viewer.getListSource().addListEventListener(new TargetProcessesViewer.IListEventListener(){

			@Override
			public void eventChanged(TargetProcessesViewer.ListEvent event) {
				setPageComplete(event.getProcessList().size() > 0);
			}
			
		});
		setControl(composite);
	}

	public List<EObject> getAllChecked() {
		return viewer.getAllChecked();
	}

	public void update(Object input) {
		if(viewer != null && input instanceof IProject){
			viewer.setInput((IProject)input);
		}
	}
	
	public void setFilter(List list){
		FilterProcessIDs.addAll(list);
	}

	@Override
	public boolean isPageComplete() {
		return getAllChecked().size()>0;
	}

}
