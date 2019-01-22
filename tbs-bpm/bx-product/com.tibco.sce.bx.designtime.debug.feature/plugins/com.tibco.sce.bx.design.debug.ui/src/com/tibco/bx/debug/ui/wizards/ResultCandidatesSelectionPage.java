package com.tibco.bx.debug.ui.wizards;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.debug.ui.wizards.ResultCandidatesViewer;
import com.tibco.bx.emulation.ui.wizards.IEmulationWizard;
import com.tibco.bx.emulation.ui.wizards.ISelectionPage;

public class ResultCandidatesSelectionPage extends WizardPage implements ISelectionPage{

	private IEmulationWizard parentWizard;
	private ResultCandidatesViewer viewer;
	public ResultCandidatesSelectionPage(IEmulationWizard parentWizard) {
		super("ResultCandidatesPage"); //$NON-NLS-1$
		setTitle(Messages.getString("ResultCandidatesSelectionPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("ResultCandidatesSelectionPage_DESC")); //$NON-NLS-1$
		this.parentWizard = parentWizard;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		viewer = new ResultCandidatesViewer();
		viewer.createTableViewer(composite, ((IResultCandidate[])parentWizard.getInput()));
		setControl(composite);
		setPageComplete(true);
	}

	public List<IResultCandidate> getAllChecked() {
		return viewer.getAllChecked();
	}

	public void update(Object input) {
		if(viewer != null && input instanceof IResultCandidate[]){
			viewer.setInput((IResultCandidate[])input);
		}
	}
	
	public void setFilter(List list){
		//do nothing
	}
}
