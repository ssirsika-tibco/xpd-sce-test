package com.tibco.bx.emulation.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;

public class EmulationFolderCreationWizard extends Wizard implements IEmulationWizard {

	private IProject project;
	IStructuredSelection selection;
	private EmulationFolderCreationPage page0;
	public void addPages() {
		page0 = new EmulationFolderCreationPage(this, selection);
		addPage(page0);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(Messages.getString("NewEmulationFolderWizard_TITLE"));//$NON-NLS-1$
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
		this.project = project;
	}

	
}
