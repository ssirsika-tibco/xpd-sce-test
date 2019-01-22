package com.tibco.bx.emulation.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;

public class EmulationFileCreationWizard extends Wizard implements IEmulationWizard {

	private IProject project;
	IStructuredSelection selection;
	private EmulationAbstractFileCreationPage page0;
	private ISelectionPage page1;

	public void addPages() {
		page0 = new EmulationFileCreationPage(this, getSelection());
		addPage(page0); 
		page1 = new ProcessesSelectionPage(this);
		addPage(page1);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(Messages.getString("NewEmulationFileWizard_TITLE"));//$NON-NLS-1$
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
		return project;
	}

	public void setProject(IProject project) {
		if(page1 != null && this.project != project){
			this.project = project;
			page1.update(project);
		}
	}

	@Override
	public boolean canFinish() {
		return super.canFinish() && page0.canFlipToNextPage() && page1.isPageComplete();
	}
}
