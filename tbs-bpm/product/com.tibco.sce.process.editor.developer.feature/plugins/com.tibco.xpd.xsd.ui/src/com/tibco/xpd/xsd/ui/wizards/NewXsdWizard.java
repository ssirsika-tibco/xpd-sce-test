/*
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.xsd.ui.internal.Messages;

/**
 * Creates a new XML Schema file resource in the provided container (project or
 * sub-folder). If the container resource is selected in the workspace when the
 * wizard is opened, it will accept it as the target container.
 */
public class NewXsdWizard extends Wizard implements INewWizard {
	private NewXsdWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewXsdWizard() {
		super();
		setWindowTitle(Messages.NewXsdWizard_XmlSchemaTitle);
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		page = new NewXsdWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String xmlFileName = page.getXmlFileName();
		final String xsdFileName = page.getXsdFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, xmlFileName, xsdFileName, monitor);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(),
					Messages.NewXsdWizard_ErrorTitle, realException
							.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 * 
	 * @param containerName
	 *            The name of the file container (project or sub-folder) to save
	 *            the XSD to.
	 * @param inputFileName
	 *            The XML document to infer an XSD for.
	 * @throws IOException
	 *             If the XSD creation fails, including if the input file cannot
	 *             be found or parsed.
	 */
	private void doFinish(String containerName, String inputFileName,
			String outputFileName, IProgressMonitor monitor)
			throws CoreException, IOException {
		// create a sample file
		monitor.beginTask(String.format(Messages.NewXsdWizard_CreatingFileMessage, outputFileName), 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException(String.format(Messages.NewXsdWizard_FolderDoesNotExistMessage, containerName));
		}
		IContainer container = (IContainer) resource;
		// final IFile inputFile = inputFileName);
		final IFile outputFile = container.getFile(new Path(outputFileName));

		// Invoke the XSD creation code
		Inst2XsdWrapper wrapper = new Inst2XsdWrapper();
		wrapper.createXsd(inputFileName, outputFile);

		monitor.worked(1);
		monitor.setTaskName(Messages.NewXsdWizard_OpeningFileShortDesc);
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, outputFile, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR,
				"com.tibco.xpd.xsd.ui", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}