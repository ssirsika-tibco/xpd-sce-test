/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.daa.OpenspaceExportDAAWizard;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.openspacegwtgadget.integration.ui.OpenspaceGadgetDevPropertyTester;

/**
 * Action to launch the Export DAA wizard for openspace gadget project.
 * 
 * @author aallway
 * @since 14 Feb 2013
 */
public class ExportOpenspaceGadgetProjectDaaAction extends Action {

    private IProject project;

    public ExportOpenspaceGadgetProjectDaaAction(Object selection) {

        super(
                Messages.ExportOpenspaceGadgetProjectDaaAction_ExportOpenspaceDAAAction_menu);

        this.setId(OpenspaceGadgetPlugin.PLUGIN_ID + ".exportDaaAction"); //$NON-NLS-1$
        this.setImageDescriptor(OpenspaceGadgetPlugin
                .getImageDescriptor(OpenspaceGadgetPlugin.IMG_EXPORT_OPENSPACE_DAA));

        project = OpenspaceGadgetDevPropertyTester.getProject(selection);

        if (project != null
                && OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }

    }

    /**
     * /**
     * 
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        OpenspaceExportDAAWizard wizard = new OpenspaceExportDAAWizard();
        wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(project));

        WizardDialog wizardDialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getModalDialogShellProvider().getShell(), wizard);

        wizardDialog.open();

    }
}