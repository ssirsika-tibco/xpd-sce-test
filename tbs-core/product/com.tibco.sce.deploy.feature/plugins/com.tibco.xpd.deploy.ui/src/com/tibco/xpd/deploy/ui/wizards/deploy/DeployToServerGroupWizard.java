/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Main wizard for module deployment to Server Group. Allows to choose one of
 * contributed sub wizards specific for the selected servers.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployToServerGroupWizard extends Wizard {

    private ServerGroup serverGroup;

    private final DeployProjectServerSelectionPage projectsServersPage;

    private LinkedHashMap<Server, List<URL>> serverModulesURLs;

    private LinkedHashMap<Server, List<WorkspaceModule>> serverWorkspaceModules;

    /**
     * Constructor.
     */
    public DeployToServerGroupWizard() {
        super();
        setWindowTitle(Messages.DeployModuleWizard_Wizard_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        projectsServersPage = new DeployProjectServerSelectionPage();
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        addPage(projectsServersPage);
    }

    @Override
    public boolean performFinish() {
        serverModulesURLs = new LinkedHashMap<Server, List<URL>>();
        serverWorkspaceModules =
                new LinkedHashMap<Server, List<WorkspaceModule>>();
        for (ModuleDeploymentWizardNode node : projectsServersPage
                .getSelectedWizardNodes()) {
            node.getWizard().performFinish();
            Server server = node.getServer();
            if (node.getDeployWizard() instanceof IDeployWizard) {
                IDeployWizard wiz = (IDeployWizard) node.getDeployWizard();
                List<URL> modulesURLs = wiz.getModulesUrls();
                serverModulesURLs.put(server, modulesURLs);
                List<WorkspaceModule> workspaceModules =
                        new ArrayList<WorkspaceModule>(wiz
                                .getWorkspaceModules());
                serverWorkspaceModules.put(server, workspaceModules);
            } else {
                throw new RuntimeException(
                        "TODO no sever group implementation of ISimpleWizard yet");
            }
        }
        return true;
    }

    public ServerGroup getServerGroup() {
        return serverGroup;
    }

    public void setServerGroup(ServerGroup serverGroup) {
        projectsServersPage.setContextServerGroup(serverGroup);
        this.serverGroup = serverGroup;
    }

    /**
     * Returns URLs of modules selected to deployment per server. The method
     * should be called after performFinish()
     * 
     * @return URLs of modules selected to deployment per server.
     */
    public Map<Server, List<URL>> getServerModulesUrls() {
        return serverModulesURLs;
    }

    /**
     * Returns WorkspaceModules of selected to be auto-deployable per server.
     * The method should be called after the performFinish().
     * 
     * @return WorkspaceModules of selected to be auto-deployable per server.
     */
    public Map<Server, List<WorkspaceModule>> getWorkspaceModules() {
        return serverWorkspaceModules;
    }

}