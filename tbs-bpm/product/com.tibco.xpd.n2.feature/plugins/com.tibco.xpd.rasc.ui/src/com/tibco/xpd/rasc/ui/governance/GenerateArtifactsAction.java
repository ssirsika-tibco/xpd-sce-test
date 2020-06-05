/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.rasc.ui.RascUiActivator;
import com.tibco.xpd.rasc.ui.export.RascExportWizard;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Action to generate draft or production artifacts
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class GenerateArtifactsAction extends BaseSelectionListenerAction {

    private IStructuredSelection selection;

    /**
     * True if we're generating artifacts for production, false if it's for
     * draft.
     */
    private boolean isGeneratingForProduction;

    /**
     * Constructor for action.
     * 
     * @param text
     *            The action label.
     * @param selection
     *            The current selection.
     * @param isForProduction
     *            true if we are generating production artifacts.
     */
    protected GenerateArtifactsAction(String text, IStructuredSelection selection, boolean isGeneratingForProduction) {
        super(text);
        this.selection = selection;
        this.isGeneratingForProduction = isGeneratingForProduction;
        if (isGeneratingForProduction) {
            setImageDescriptor(RascUiActivator.getImageDescriptor(RascUiConstants.ICON_GENERATE_PRODUCTION_ARTIFACTS));
        } else {
            setImageDescriptor(RascUiActivator.getImageDescriptor(RascUiConstants.ICON_GENERATE_TEST_ARTIFACTS));
        }
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     *
     */
    @Override
    public void run() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        Shell shell = XpdResourcesPlugin.getStandardDisplay().getActiveShell();
        RascExportWizard wizard = new RascExportWizard(isGeneratingForProduction);
        wizard.init(workbench, selection);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.setMinimumPageSize(750, 400);
        dialog.open();
    }

}
