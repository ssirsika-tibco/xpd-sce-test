/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.wizards.FileLocationWizardPage;
import com.tibco.xpd.om.transform.de.TransformDEActivator;
import com.tibco.xpd.om.transform.de.actions.ExportToDEAction;
import com.tibco.xpd.om.transform.de.internal.Messages;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage.ISelectionValidator;

public class DEExportWizard extends Wizard implements IExportWizard {

    protected IStructuredSelection selection;

    protected ExportToDEAction action;

    private FileLocationWizardPage fileLocationFilePage;

    private IStatus status = null;

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        setWindowTitle(Messages.DEExportWizard_title);
        // TODO add icon
        // setDefaultPageImageDescriptor(OMResourcesUIActivator.getDefault()
        // .getImageRegistry().getDescriptor(OMImages.IMAGE_NEW_OM_MODEL));
        setNeedsProgressMonitor(true);
        action = new ExportToDEAction();
        action.selectionChanged(selection);
    }

    @Override
    public void addPages() {

        fileLocationFilePage = new FileLocationWizardPage(
                Messages.DEExportWizard_outFileNamePage_label, selection,
                ExportToDEAction.OUTPUT_FILE_EXTENSION);
        fileLocationFilePage
                .setTitle(Messages.DEExportWizard_outFileNamePage_title);
        fileLocationFilePage.setDescription(Messages.DEExportWizard_longdesc);
        fileLocationFilePage.setSelectionValidator(new ISelectionValidator() {

            public IStatus isValid(Object selection) {
                if (status == null) {
                    try {
                        if (!action.isValid()) {
                            status = new Status(
                                    IStatus.ERROR,
                                    TransformDEActivator.PLUGIN_ID,
                                    Messages.DEExportWizard_validationProblems_error_message);
                        } else {
                            status = Status.OK_STATUS;
                        }
                    } catch (CoreException e) {
                        status = e.getStatus();
                    }
                }
                return status;
            }
        });

        // Build the default file name
        if (selection != null && !selection.isEmpty()
                && selection.getFirstElement() instanceof IResource) {
            String name = ((IResource) selection.getFirstElement()).getName();
            if (name.indexOf('.') > 0) {
                name = name.substring(0, name.indexOf('.'));
            }
            fileLocationFilePage.setFileName(FileLocationWizardPage
                    .getUniqueFileName(fileLocationFilePage
                            .getContainerFullPath(), name,
                            ExportToDEAction.OUTPUT_FILE_EXTENSION));
        }
        addPage(fileLocationFilePage);
    }

    @Override
    public boolean performFinish() {
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                action.setOutputPath(fileLocationFilePage.getFilePath()
                        .toPortableString());
                if (action.isEnabled()) {
                    action.run();
                }

            }
        };
        try {
            getContainer().run(false, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof CoreException) {
                ErrorDialog.openError(getContainer().getShell(),
                        Messages.DEExportWizard_errorDialog_title, null,
                        ((CoreException) e.getTargetException()).getStatus());
            } else {
                OMResourcesUIActivator.getDefault().getLogger().error(
                        e.getTargetException(),
                        "Error while Exporting OM model"); //$NON-NLS-1$
            }
            return false;
        }
        return true;
    }
}
