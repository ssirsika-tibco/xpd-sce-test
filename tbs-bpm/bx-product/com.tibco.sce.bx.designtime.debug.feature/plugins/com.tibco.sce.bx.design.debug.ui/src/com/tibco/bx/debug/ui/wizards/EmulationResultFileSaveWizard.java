package com.tibco.bx.debug.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.wizards.EmulationAbstractFileCreationPage;
import com.tibco.bx.emulation.ui.wizards.IEmulationWizard;
import com.tibco.bx.emulation.ui.wizards.ISelectionPage;

public class EmulationResultFileSaveWizard extends Wizard implements IEmulationWizard {

	private IProject project;
	IStructuredSelection selection;
	private EmulationAbstractFileCreationPage page0;
	private ISelectionPage page1;
	private IResultCandidate[] candidates;
	public void addPages() {
		//it's probably not register in plugin.xml
		setWindowTitle(Messages.getString("SaveEmulationResultWizard_TITLE"));//$NON-NLS-1$
		setDefaultPageImageDescriptor(EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_EMULATION));
		page0 = new EmulationResultFileSavePage(this, selection);
		page1 = new ResultCandidatesSelectionPage(this);
		addPage(page0); 
		addPage(page1);
		page1.update(candidates);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(Messages.getString("SaveEmulationResultWizard_TITLE"));//$NON-NLS-1$
		setDefaultPageImageDescriptor(EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_EMULATION));
	}
	
	public boolean performFinish() {
		return page0.finish();
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
	
	/**
     * Get the current selection
     */
    public IStructuredSelection getSelection() {
        return selection;
    }

	public Object getInput() {
		return candidates;
	}

	public void setProject(IProject project) {
		if(this.project != project){
			this.project = project;
		}
	}
	
	public void setInput(Object input){
		if(page1 != null && input instanceof IResultCandidate[]){
			page1.update(input);
		}else{
			candidates = (IResultCandidate[])input;
		}
	}
}
