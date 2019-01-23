/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetProjectConfigurator;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.openspacegwtgadget.integration.ui.OpenspaceGadgetDevPropertyTester;

/**
 * Action to enable openspace gadget development on a GWT gadget project.
 * <p>
 * Can either work solely on project for given initial selection or optionally
 * use the initial as a guide but still display a dialog to allow user to
 * choose.
 * <p>
 * If not launching a selection dialog and action dfoes not apply to project for
 * given selection the then option is disabled.
 * 
 * @author aallway
 * @since 15 Jan 2013
 */
public class EnableOpenspaceGadgetDevAction extends Action {

    private IProject project;

    /**
     * Action to enable openspace gadget development on a GWT gadget project.
     * 
     * @param selection
     * @param launchProjectSelectionWizard
     */
    public EnableOpenspaceGadgetDevAction(Object selection) {
        super(Messages.EnableOpenspaceGadgetDevAction_EnableOpenspaceDev_menu);

        this.setId(OpenspaceGadgetPlugin.PLUGIN_ID
                + ".enabledOpenspaceGadgetDev"); //$NON-NLS-1$

        boolean enabled = false;
        boolean checked = false;

        project = OpenspaceGadgetDevPropertyTester.getProject(selection);

        if (project != null) {
            if (OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
                checked = true;

            } else if (OpenspaceGadgetNature.isGWTGadgetProject(project)) {
                enabled = true;
            }
        }

        setEnabled(enabled);
        setChecked(checked);

    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        if (project != null
                && !OpenspaceGadgetNature.isOpenspaceGadgetDevProject(project)) {
            OpenspaceGadgetProjectConfigurator configurator =
                    new OpenspaceGadgetProjectConfigurator();

            configurator.enableOpenspaceGadgetDevelopment(project);
        }

    }
}
