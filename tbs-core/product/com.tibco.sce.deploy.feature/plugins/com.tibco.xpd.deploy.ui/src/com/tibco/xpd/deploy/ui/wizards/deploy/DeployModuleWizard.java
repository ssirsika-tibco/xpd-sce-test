/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Main wizard for module deployment. Allows to choose one of contributed sub
 * wizards specific for the server context.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployModuleWizard extends Wizard implements ISimpleDeployWizard {

    private static final String DIALOG_SETTING_KEY = "DeployModuleWizard"; //$NON-NLS-1$

    private Server selectedServer;

    private ModuleTypeSelectionPage moduleTypePage;

    /**
     * Constructor.
     */
    public DeployModuleWizard() {
        super();
        setWindowTitle(Messages.DeployModuleWizard_Wizard_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        setForcePreviousAndNextButtons(true);
        // Get/set dialog setting - memento.
        IDialogSettings settings =
                DeployUIActivator.getDefault().getDialogSettings()
                        .getSection(DIALOG_SETTING_KEY);
        if (settings == null) {
            settings =
                    DeployUIActivator.getDefault().getDialogSettings()
                            .addNewSection(DIALOG_SETTING_KEY);
        }
        setDialogSettings(settings);
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        moduleTypePage = new ModuleTypeSelectionPage();
        addPage(moduleTypePage);
    }

    /*
     * This performFinish method has no logic. The wizard purpose is to enable
     * user to choose one of the other, more specific wizards. This method
     * though is invoked just after more specific wizard performFinish method is
     * invoked, but the result is ignored.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        return true;
    }

    public Server getServer() {
        return selectedServer;
    }

    public void setServer(Server server) {
        selectedServer = server;
    }

    public List<URL> getSelectedModulesUrls() {
        ISimpleDeployWizard w = getSelectedModuleWizard();
        if (w instanceof IDeployWizard) {
            return ((IDeployWizard) w).getModulesUrls();
        }
        return Collections.emptyList();
    }

    public ISimpleDeployWizard getSelectedModuleWizard() {
        if (moduleTypePage != null && moduleTypePage.getSelectedNode() != null) {
            return (ISimpleDeployWizard) moduleTypePage.getSelectedNode()
                    .getWizard();
        }
        return null;
    }

}