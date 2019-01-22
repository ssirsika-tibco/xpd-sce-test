package com.tibco.bx.emulation.ui.actions;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.ui.views.IEmulationView;
import com.tibco.bx.emulation.ui.wizards.EmulationFileSaveWizard;

public class SaveAsEmulationFileAction extends AbstractHandleTestpointsAction {

	
	public void run(IAction action) {
		Shell shell = getShell();
		EmulationFileSaveWizard wizard = new EmulationFileSaveWizard();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		if(getEmulationView() != null){
			final EmulationData emulationData = (EmulationData)EcoreUtil.copy(getEmulationView().getEmulationData());
			wizard.setInput(emulationData);
			dialog.open();
			
		}
	}

	protected boolean isEnabled() {
		IEmulationView view = getEmulationView();
		if(view != null)
			return view.hasElements();
		return false;
	}

}
