/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.processeditor.xpdl2.wizards.NewPackageWizard;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.xpdl2.Package;

/**
 * Action to add a new Process {@link Package}.
 * 
 * @author njpatel
 * 
 */
public class NewProcessPackageAction extends ModelAction {

    private IProject project;

    private SpecialFolder sFolder;

    public NewProcessPackageAction(IWorkbenchWindow window, String label,
            String image, IProject project, SpecialFolder sFolder) {
        super(window, label, image);
        this.project = project;
        this.sFolder = sFolder;
    }

    @Override
    public void run() {
        if (sFolder.getFolder() == null) {
            // Create the underlying folder
            IFolder folder = project.getFolder(sFolder.getLocation());
            try {
                ProjectUtil.createFolder(folder, false, null);
            } catch (CoreException e) {
                ErrorDialog
                        .openError(getWorkbenchWindow().getShell(),
                                Messages.NewProcessPackageAction_errorCreatingModel_error_title,
                                Messages.NewProcessPackageAction_errorCreatingModel_error_message,
                                e.getStatus());
            }
        }

        NewPackageWizard wizard = new NewPackageWizard();
        wizard.setIsRCP(true);
        wizard.init(getWorkbenchWindow().getWorkbench(),
                new StructuredSelection(sFolder.getFolder()));
        new WizardDialog(getWorkbenchWindow().getShell(), wizard).open();
    }

    @Override
    public boolean isNewAction() {
        return true;
    }

    @Override
    public boolean isNewFileAction() {
        return true;
    }

    /**
     * @return the project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @param project
     *            the project to set
     */
    public void setProject(IProject project) {
        this.project = project;
    }

    /**
     * @return the sFolder
     */
    public SpecialFolder getsFolder() {
        return sFolder;
    }

    /**
     * @param sFolder
     *            the sFolder to set
     */
    public void setsFolder(SpecialFolder sFolder) {
        this.sFolder = sFolder;
    }

}
