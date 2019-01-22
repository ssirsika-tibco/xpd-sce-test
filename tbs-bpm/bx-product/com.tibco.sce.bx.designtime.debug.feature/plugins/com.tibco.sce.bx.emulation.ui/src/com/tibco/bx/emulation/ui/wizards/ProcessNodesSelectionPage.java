package com.tibco.bx.emulation.ui.wizards;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.Messages;

public class ProcessNodesSelectionPage extends WizardPage implements ISelectionPage{

	private IEmulationWizard parentWizard;
	private ProcessNodesTableViewer viewer;
	public ProcessNodesSelectionPage(IEmulationWizard parentWizard) {
		super("ProcessNodesPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ProcessNodesSelectionPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("ProcessNodesSelectionPage_DESC")); //$NON-NLS-1$
		this.parentWizard = parentWizard;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		viewer = new ProcessNodesTableViewer();
		viewer.createTableViewer(composite, ((EmulationData)parentWizard.getInput()).getProcessNodes().toArray(new ProcessNode[0]));
		setControl(composite);
		setPageComplete(true);
	}

	public List<ProcessNode> getAllChecked() {
		return viewer.getAllChecked();
	}

	public void update(Object input) {
		if(viewer != null && input instanceof EmulationData){
			viewer.setInput(((EmulationData)input).getProcessNodes().toArray(new ProcessNode[0]));
		}
	}
	
	public void setFilter(List list){
		//do nothing
	}
}
