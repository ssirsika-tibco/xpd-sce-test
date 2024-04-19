/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * The Process Script Library file (.psl) creation.
 *
 * @author cbabar
 * @since Jan 3, 2024
 */
public class ProcessScriptLibraryCreationWizard extends BasicNewXpdResourceWizard
{
	/**
	 * The Wizard page for PSL file details.
	 */
	protected ProcessScriptLibraryCreationWizardPage pslModelFilePage;

	/**
	 * The Process Script Library Resource.
	 */
	protected Resource									pslResource;

	/**
	 * Returns the PSL Resource.
	 * 
	 * @return the pslResource
	 */
	public final Resource getPslResource()
	{
		return pslResource;
	}

	/**
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

		setWindowTitle(Messages.ProcessScriptLibraryCreationWizard_Title);

		setDefaultPageImageDescriptor(ProcessScriptLibraryResourcePluginActivtor
				.getBundledImageDescriptor(ProcessScriptLibraryResourcePluginActivtor.PSL_WIZARD_IMAGE));

		setNeedsProgressMonitor(true);
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 *
	 */
	@Override
	public void addPages()
	{
		/*
		 * Add PSL File Creation Wizard page
		 */
		pslModelFilePage = new ProcessScriptLibraryCreationWizardPage("DiagramModelFile", getSelection()); //$NON-NLS-1$
		pslModelFilePage.setTitle(Messages.ProcessScriptLibraryCreationWizard_Title);
		pslModelFilePage.setDescription(Messages.ProcessScriptLibraryCreationWizard_Description);

		addPage(pslModelFilePage);


	}

	/**
	 * @see com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish()
	{
		
		IRunnableWithProgress op = new WorkspaceModifyOperation(null)
		{

			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InterruptedException
			{

				pslResource = PslEditorUtil.createPSLFile(pslModelFilePage.getURI(), monitor);

				/*
				 * Open new PSL file in Editor
				 */
				if (pslResource != null)
				{
					PslEditorUtil.openProcessScriptLibraryEditor(pslResource);
				}
			}

		};

		try
		{
			getContainer().run(false, true, op);
		}
		catch (InterruptedException e)
		{

			return false;

		}
		catch (InvocationTargetException e)
		{

			ErrorDialog.openError(getContainer().getShell(), Messages.ProcessScriptLibraryCreationWizard_Error,
					e.getLocalizedMessage(),
					e.getTargetException() instanceof CoreException
							? ((CoreException) e.getTargetException()).getStatus()
							: new Status(IStatus.ERROR, ProcessScriptLibraryResourcePluginActivtor.PLUGIN_ID,
									e.getLocalizedMessage(),
									e.getCause()));

			return false;
		}

		return pslResource != null;
	}

}
