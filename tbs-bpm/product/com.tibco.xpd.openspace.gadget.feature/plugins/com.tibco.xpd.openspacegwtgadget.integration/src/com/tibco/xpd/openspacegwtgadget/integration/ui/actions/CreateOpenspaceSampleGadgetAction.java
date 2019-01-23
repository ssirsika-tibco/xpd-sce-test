/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.openspacegwtgadget.integration.samples.OpenspaceCreateSampleWizard;
import com.tibco.xpd.openspacegwtgadget.integration.samples.OpenspaceSampleCreatorContribution;
import com.tibco.xpd.openspacegwtgadget.integration.ui.OpenspaceGadgetDevPropertyTester;

/**
 * Action to create and execute an Openspace sample creation wizard based upon
 * the given OpenspaceSampleGadget ext point contribution.
 * 
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class CreateOpenspaceSampleGadgetAction extends Action {

    private OpenspaceSampleCreatorContribution sampleContribution;

    private Object selection;

    private boolean disableForInAppropriateSelection;

    public CreateOpenspaceSampleGadgetAction(
            OpenspaceSampleCreatorContribution sampleContribution,
            Object selection, boolean disableForInAppropriateSelection) {
        super(
                String.format(Messages.CreateOpenspaceSampleGadgetAction_CreateSample_menu,
                        sampleContribution.getSampleName()), sampleContribution
                        .getMenuIcon());

        this.setId(OpenspaceGadgetPlugin.PLUGIN_ID + ".sample." //$NON-NLS-1$
                + sampleContribution.getSampleName());

        this.sampleContribution = sampleContribution;
        this.selection = selection;
        this.disableForInAppropriateSelection =
                disableForInAppropriateSelection;

        IProject project =
                OpenspaceGadgetDevPropertyTester.getProject(selection);

        if (project != null
                && OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
            /*
             * Always enabled if the selection is appropriate for sample
             * creation.
             */
            setEnabled(true);
        } else {
            /*
             * Only disable if required to do so. For instance, context
             * popup-menu item should disable when inappropriate selection is
             * right-clicked BUT main toolbar drop down button menu item should
             * not (as the main toolbar tends to take current selection as a
             * hint but allows user to select anyway.
             */
            if (disableForInAppropriateSelection) {
                setEnabled(false);
            }
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
        OpenspaceCreateSampleWizard wizard =
                new OpenspaceCreateSampleWizard(sampleContribution, selection);

        WizardDialog wizardDialog =
                new WizardDialog(PlatformUI.getWorkbench()
                        .getModalDialogShellProvider().getShell(), wizard);

        wizardDialog.open();
    }
}