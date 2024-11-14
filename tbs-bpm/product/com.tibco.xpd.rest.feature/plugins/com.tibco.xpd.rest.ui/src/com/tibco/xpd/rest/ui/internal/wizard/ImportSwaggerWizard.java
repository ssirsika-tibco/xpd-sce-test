/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.ui.internal.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.rest.ui.internal.Messages;
import com.tibco.xpd.rest.ui.internal.RestImage;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * Wizard for creating a Swagger/OAS file by importing from external resources - file, URL or content
 *
 * @author nkelkar
 * @since Jul 18, 2024
 */
public class ImportSwaggerWizard extends BasicNewXpdResourceWizard
{
	/**
	 * The page for identifying the target project and file.
	 */
	private SwaggerFilePage page;

	/**
	 * 
	 * @see com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 *
	 * @param workbench
	 * @param selection
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		super.init(workbench, selection);
		setWindowTitle(Messages.ImportSwaggerWizard_windowTitle);
		setDefaultPageImageDescriptor(
				RestServicesActivator.getDefault().getImageDescriptor(RestImage.IMPORT_SWAGGER_WIZARD));
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish()
	{
		IFile newFile = page.createNewFile();
		selectAndReveal(newFile);
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 *
	 */
	@Override
	public void addPages()
	{
		page = new SwaggerFilePage(getSelection());
		page.setTitle(Messages.ImportSwaggerWizard_pageTitle);
		page.setDescription(Messages.ImportSwaggerWizard_pageDescription);
		addPage(page);
	}
}
