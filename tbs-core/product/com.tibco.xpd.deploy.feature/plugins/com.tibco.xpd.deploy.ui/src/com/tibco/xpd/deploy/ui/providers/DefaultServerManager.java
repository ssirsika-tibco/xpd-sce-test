/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IStartup;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.api.DefaultServerProvider;
import com.tibco.xpd.resources.logger.Logger;

/**
 * This class will be used to add default deployment servers contributed through
 * 'dafultServers' extension point on creation of workspace.
 * 
 * 
 * @author agondal
 * @since 1 Sep 2014
 */
public class DefaultServerManager implements IStartup {

    private static final String DEFAULT_SERVERS_EXTENSION_POINT_ID =
            "defaultServers"; //$NON-NLS-1$

    private static final String BS_SERVERS_PROJECT = ".BSServers"; //$NON-NLS-1$

    private static Logger logger = DeployUIActivator.getDefault().getLogger();

    /**
     * Get default servers from contributors and save it to the file system.
     */
    public void saveDefaultServers() {
        /*
         * Only need to add default servers if its a new workspace, i.e., the
         * .BSServers project containing DeploymentServers.xml file doesn't
         * exist
         */
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(BS_SERVERS_PROJECT);

        if (!project.isAccessible()) {

            ServerManager serverManager = DeployUIActivator.getServerManager();
            ServerContainer serverContainer =
                    serverManager.getServerContainer();

            /* Get servers from contributors of 'dafultServers' extension point */
            List<Server> defualtServers = getDefaultServers(serverContainer);

            if (!defualtServers.isEmpty()) {

                serverContainer.getServers().addAll(defualtServers);
                serverManager.saveServerContainer();
            }
        }
    }

    /**
     * @param serverContainer
     * @return list of servers contributed through 'dafultServers' extension
     *         point
     */
    private List<Server> getDefaultServers(ServerContainer serverContainer) {

        List<Server> servers = new ArrayList<>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DeployUIActivator.PLUGIN_ID,
                                DEFAULT_SERVERS_EXTENSION_POINT_ID);
        if (extensionPoint != null) {
            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();

            try {
                for (IConfigurationElement config : configs) {

                    Object contributor =
                            config.createExecutableExtension("providerClass"); //$NON-NLS-1$
                    String serverId = config.getAttribute("serverId"); //$NON-NLS-1$

                    if (contributor instanceof DefaultServerProvider) {
                        String contributorName =
                                config.getContributor().getName();
                        DefaultServerProvider serverProvider =
                                (DefaultServerProvider) contributor;

                        Server server =
                                createServer(serverProvider,
                                        serverContainer,
                                        contributorName,
                                        serverId);
                        if (server != null) {
                            servers.add(server);
                        }
                    }
                }
            } catch (CoreException e) {
                logger.error(e);
            }
        }
        return servers;
    }

    /**
     * @param serverProvider
     * @param serverContainer
     * @param contributorName
     * @return
     */
    private Server createServer(DefaultServerProvider serverProvider,
            ServerContainer serverContainer, String contributorName,
            String serverId) {
        DeployFactory factory = DeployFactory.eINSTANCE;

        String errorMsg =
                "The following contributor to 'defaultServers' extension point provided invalid value for '%1$s': '" + contributorName + "'."; //$NON-NLS-1$ //$NON-NLS-2$

        /* server */
        Server server = factory.createServer();
        String serverName = serverProvider.getServerName();
        if (serverName == null) {
            logger.error(String.format(errorMsg, "serverName")); //$NON-NLS-1$
            return null;
        }
        server.setName(serverName);

        // If the server ID has been set in the extension point then set it in
        // the model, otherwise use the default generated ID.
        if (serverId != null && serverId.length() > 0) {
            server.eSet(DeployPackage.eINSTANCE.getUniqueIdElement_Id(),
                    serverId);
        }

        /* server config */
        ServerConfig serverConfig = serverProvider.getServerConfig();
        if (serverConfig == null) {
            logger.error(String.format(errorMsg, "serverConfig")); //$NON-NLS-1$
            return null;
        }
        server.setServerConfig(serverConfig);

        /* server type */
        ServerType serverType =
                serverContainer.getServerTypeById(serverProvider
                        .getServerTypeId());
        if (serverType == null) {
            logger.error(String.format(errorMsg, "serverType")); //$NON-NLS-1$
            return null;
        }
        server.setServerType(serverType);

        /* repository type */
        RepositoryType repoType =
                serverContainer.getRepositoryTypeById(serverProvider
                        .getServerRepoTypeId());
        if (repoType == null) {
            logger.error(String.format(errorMsg, "repoType")); //$NON-NLS-1$
            return null;
        }

        /* repository */
        Repository repo = factory.createRepository();
        repo.setRepositoryType(repoType);
        repo.setRepositoryConfig(factory.createRepositoryConfig());
        server.setRepository(repo);

        return server;
    }

    /**
     * @see org.eclipse.ui.IStartup#earlyStartup()
     * 
     */
    @Override
    public void earlyStartup() {
        /*
         * create servers contributed through 'defaultServers' extension point
         * and save to file
         */
        saveDefaultServers();
    }
}
