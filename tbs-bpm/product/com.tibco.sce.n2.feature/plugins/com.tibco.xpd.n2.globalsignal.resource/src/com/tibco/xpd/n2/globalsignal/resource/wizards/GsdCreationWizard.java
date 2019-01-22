/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

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

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.editor.util.GsdEditorUtil;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * The Global Signal Definition file (.gsd) creation.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdCreationWizard extends
        BasicNewXpdResourceWizard {

    /**
     * The Wizard page for GSD file details.
     */
    protected GsdCreationWizardPage gsdModelFilePage;

    /**
     * The Global Signal Definition Resource.
     */
    protected Resource gsdResource;

    /**
     * Returns the GSD Resource.
     * 
     * @return GSD resource.
     */
    public final Resource getGSDResource() {
        return gsdResource;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {

        super.init(workbench, selection);

        setWindowTitle(Messages.GlobalSignalDefinitionCreationWizard_Title);

        setDefaultPageImageDescriptor(GsdResourcePlugin
                .getBundledImageDescriptor(GsdResourcePlugin.GSD_WIZARD_IMAGE));

        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {

        /*
         * Add GSD File Creation Wizard page
         */
        gsdModelFilePage =
                new GsdCreationWizardPage(
                        "DiagramModelFile", getSelection()); //$NON-NLS-1$
        gsdModelFilePage
                .setTitle(Messages.GlobalSignalDefinitionCreationWizard_Title);
        gsdModelFilePage
                .setDescription(Messages.GlobalSignalDefinitionCreationWizard_Description);
        addPage(gsdModelFilePage);
    }

    @Override
    public boolean performFinish() {

        IRunnableWithProgress op = new WorkspaceModifyOperation(null) {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InterruptedException {

                gsdResource =
                        GsdEditorUtil
                                .createGSDFile(gsdModelFilePage.getURI(),
                                        monitor);

                /*
                 * Open new GSD file in Editor
                 */
                if (gsdResource != null) {
                    GsdEditorUtil
                            .openGlobalSignalDefinitionEditor(gsdResource);
                }
            }

        };

        try {
            getContainer().run(false, true, op);

            if (gsdResource != null) {

                selectAndReveal(gsdResource);

            }

        } catch (InterruptedException e) {

            return false;

        } catch (InvocationTargetException e) {

            ErrorDialog
                    .openError(getContainer().getShell(),
                            Messages.GlobalSignalDefinitionCreationWizard_Error,
                            e.getLocalizedMessage(),
                            e.getTargetException() instanceof CoreException ? ((CoreException) e
                                    .getTargetException()).getStatus()
                                    : new Status(
                                            IStatus.ERROR,
                                            GsdResourcePlugin.PLUGIN_ID,
                                            e.getLocalizedMessage(), e
                                                    .getCause()));

            return false;
        }
        return gsdResource != null;
    }

}
