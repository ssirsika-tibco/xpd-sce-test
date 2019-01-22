package com.tibco.bx.debug.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;
import com.tibco.bx.debug.ui.wizards.EmulationResultFileSaveWizard;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.ui.actions.AbstractHandleTestpointsAction;

public class SaveEmulationResultAction extends AbstractHandleTestpointsAction{

	@Override
	public void run(IAction action) {
		IResultCandidate[] tabCandidates = ((IProcessTabPaneCreator)getEmulationView()).getResultCandidates();
		EmulationResultFileSaveWizard wizard = new EmulationResultFileSaveWizard();
		wizard.setInput(tabCandidates);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.setTitle(Messages.getString("SaveEmulationResultAction.WizardDialog.title")); //$NON-NLS-1$
		dialog.open();
	}

	@Override
	protected boolean isEnabled() {
		if(getEmulationView() != null){
			return getEmulationView().hasElements();
		}
		return false;
	}

}
