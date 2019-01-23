/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionException;
import com.tibco.xpd.deploy.model.extension.DeploymentInterceptor;
import com.tibco.xpd.deploy.model.extension.WorkspaceModulesSupport;
import com.tibco.xpd.deploy.ui.actions.DeployAction;
import com.tibco.xpd.deploy.ui.util.ConnectionHelper;

/**
 * Deploys modules in auto-deploy mode when the modules change.
 * <p>
 * <i>Created: 3 September 2006</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class AutoDeployManager implements IResourceChangeListener {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        if (event.getType() != IResourceChangeEvent.POST_BUILD
                || event.getDelta() == null
                || event.getDelta().getResource() == null)
            return;
        IResourceDelta rootDelta = event.getDelta();
        switch (rootDelta.getKind()) {
        case IResourceDelta.ADDED:
        case IResourceDelta.CHANGED:
        case IResourceDelta.REMOVED:
            ServerContainer serverContainer = DeployUIActivator
                    .getServerManager().getServerContainer();
            for (Iterator<?> iter = serverContainer.getServers().iterator(); iter
                    .hasNext();) {
                Server server = (Server) iter.next();
                Connection connection = server.getConnection();              
                if (connection != null) {
                    WorkspaceModulesSupport wms = (WorkspaceModulesSupport) connection
                            .getAdapter(WorkspaceModulesSupport.class);
                    if (wms != null) {
                        List<WorkspaceModule> modulesList = wms
                                .getAffectedWorkspaceModules(rootDelta);
                        wms.cleanWorkspaceModules(rootDelta);
                        if (modulesList.size() == 0) {
                            continue;
                        }
                        List<URL> modulesUrls = new ArrayList<URL>();
                        for (WorkspaceModule module : modulesList) {
                            URL workspaceModuleURL = wms
                                    .getWorkspaceModuleDeploymentURL(module);
                            if (workspaceModuleURL != null) {
                                modulesUrls.add(workspaceModuleURL);
                            }
                        }
                        try {
                            AutoDeployManager.deployModule(connection,
                                    server,
                                    modulesUrls,
                                    modulesList);
                        } catch (ConnectionException e) {
                            ConnectionHelper
                                    .handleConnectionExeption(server, e);
                        }
                    }
                }
            }
            break;
        }
    }
    
    public static void deployModule(Connection connection, Server server,
            List<URL> modulesUrls, List<WorkspaceModule> modulesList)
            throws ConnectionException {
        if (connection.isConnected()) {

            // if this adapter is supported then perform the relevant
            // pre-deployment
            // operations on it
            DeploymentInterceptor deploymentSupport =
                    (DeploymentInterceptor) connection
                            .getAdapter(DeploymentInterceptor.class);
            if (deploymentSupport != null) {
                deploymentSupport.beforeDeployment(server,
                        modulesUrls,
                        modulesList);
            }

            new DeployAction(server, modulesUrls, false).run();

            // if this adapter is supported then perform the relevant
            // post-deployment
            // operations on it
            if (deploymentSupport != null) {
                deploymentSupport.afterDeployment(server,
                        modulesUrls,
                        modulesList);
            }
            if (connection.isConnected()) {
                for (WorkspaceModule module : modulesList) {
                    module.setDirty(false);
                }
            }
        } else {
            for (WorkspaceModule module : modulesList) {
                module.setDirty(true);
            }
        }
    }

}
