package com.tibco.bx.emulation.ui.wizards;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;

public interface ISelectionPage extends IWizardPage{

	public List getAllChecked();
	public void update(Object input);
	public void setFilter(List list);
}
