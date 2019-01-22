/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.wizards;

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

import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;

/**
 * The WorkListFacade File creation .
 */
public class WorkListFacadeCreationWizard extends BasicNewXpdResourceWizard {

	/**
	 * The Wizard page for WorkListFacade file details.
	 */
	protected WorkListFacadeCreationWizardPage facadeModelFilePage;

	/**
	 * The WorkListFacade Resource.
	 */
	protected Resource workListFacadeRes;

	private boolean openNewlyCreatedFacadeEditor = true;

	/**
	 * Returns the WorkListFacade Resource.
	 * 
	 * @return Facade resource.
	 */
	public final Resource getFacadeResource() {
		return workListFacadeRes;
	}

	/**
	 * Returns the open status of the newly created WorkListFacade Editor.
	 * 
	 * @return, true if the editor is open.
	 */
	public final boolean isOpenNewlyCreatedFacadeEditor() {
		return openNewlyCreatedFacadeEditor;
	}

	/**
	 * Sets the open option for the newly created WorkListFacade Editor.
	 * 
	 * @param openNewlyCreatedFacadeEditor
	 */
	public void setOpenNewlyCreatedFacadeEditor(
			boolean openNewlyCreatedFacadeEditor) {
		this.openNewlyCreatedFacadeEditor = openNewlyCreatedFacadeEditor;
	}

	@Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle(Messages.WorkListFacadeCreationWizard_Title);
		setDefaultPageImageDescriptor(WorkListFacadeResourcePlugin
                .getBundledImageDescriptor(WorkListFacadeResourcePlugin.WLF_WIZARD_IMAGE));
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		// Add WLF File Creation Wizard page
		facadeModelFilePage = new WorkListFacadeCreationWizardPage(
				"DiagramModelFile", getSelection()); //$NON-NLS-1$ //$NON-NLS-2$
		facadeModelFilePage
				.setTitle(Messages.WorkListFacadeCreationWizard_Title);
		facadeModelFilePage
				.setDescription(Messages.WorkListFacadeCreationWizard_Description);
		addPage(facadeModelFilePage);
	}

	@Override
	public boolean performFinish() {

		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InterruptedException {
				workListFacadeRes = WorkListFacadeEditorUtil
						.createWorkListFacadeFile(facadeModelFilePage.getURI(),
								monitor);
				// Open new Work List Facade file in Editor
				if (workListFacadeRes != null) {
					WorkListFacadeEditorUtil
							.openWorkListFacadeEditor(workListFacadeRes);
				}
			}

		};
		try {
			getContainer().run(false, true, op);
			/*
			 * TODO_WLF Select And reveal does not work when the resource is not
			 * the first resource under the WLF Special Folder, same behaviour
			 * is seen if Org Model is modified to reveal the OrModel file and
			 * not the Organization.
			 */
			if (workListFacadeRes != null) {
				selectAndReveal(workListFacadeRes);

			}
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			ErrorDialog
					.openError(
							getContainer().getShell(),
							Messages.WorkListFacadeCreationWizard_Error,
							e.getLocalizedMessage(),
							e.getTargetException() instanceof CoreException ? ((CoreException) e
									.getTargetException()).getStatus()
									: new Status(
											IStatus.ERROR,
											WorkListFacadeResourcePlugin.PLUGIN_ID,
											e.getLocalizedMessage(), e
													.getCause()));
			return false;
		}
		return workListFacadeRes != null;
	}

}
