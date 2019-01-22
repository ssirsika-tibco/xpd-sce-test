package com.tibco.bx.emulation.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.INewWizard;

public interface IEmulationWizard extends INewWizard{

	public Object getInput();
	public void setProject(IProject project);
}
