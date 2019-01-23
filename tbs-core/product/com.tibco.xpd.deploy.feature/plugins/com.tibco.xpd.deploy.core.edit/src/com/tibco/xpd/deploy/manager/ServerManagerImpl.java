/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.manager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.ParameterFacet;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.impl.ConfigParameterInfoImpl;
import com.tibco.xpd.deploy.impl.UniqueIdElementImpl;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;
import com.tibco.xpd.deploy.model.extension.DefaultValueProvider;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;
import com.tibco.xpd.deploy.provider.DeployItemProviderAdapterFactory;
import com.tibco.xpd.deploy.provider.DeployModelEditPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * 
 * This is provisional API. This class could be replaced by WrokingCopy
 * implementation in the future.
 * <p>
 * <i>Created: 27 Feb 2007</i>
 * 
 * @author Jan Arciuchiewicz
 */
public class ServerManagerImpl implements ServerManager {

    /** */
    private static final String DEFAULT_VALUE_PROVIDER_ATTRIBUTE =
            "defaultValueProvider"; //$NON-NLS-1$

    /** */
    private static final String SERVER_TYPE_ID_ATTRIBUTE = "serverTypeId"; //$NON-NLS-1$

    /** */
    private static final String SERVER_TYPE_REFERENCE_ELEMENT =
            "serverTypeReference"; //$NON-NLS-1$

    private static final String DEPLOYMENT_SERVERS_FILE =
            "DeploymentServers.xml"; //$NON-NLS-1$

    private static final String BS_SERVERS_PROJECT = ".BSServers"; //$NON-NLS-1$

    private static final String DEFAULT_VALUE_ATTRIBUTE = "defaultValue"; //$NON-NLS-1$

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String REQUIRED_ATTRIBUTE = "required"; //$NON-NLS-1$

    private static final String AUTOMATIC_ATTRIBUTE = "automatic"; //$NON-NLS-1$

    private static final String PARAMETER_TYPE_ATTRIBUTE = "parameterType"; //$NON-NLS-1$

    private static final String ICON_ATTRIBUTE = "icon"; //$NON-NLS-1$

    private static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$

    private static final String KEY_ATTRIBUTE = "key"; //$NON-NLS-1$

    private static final String CONNECTION_FACTORY_ATTRIBUTE =
            "connectionFactory"; //$NON-NLS-1$

    private static final String DESCRIPTION_ATTRIBUTE = "description"; //$NON-NLS-1$

    private static final String SERVER_TYPES_EXTENSION_ID = "serverTypes"; //$NON-NLS-1$

    private static final String SERVER_GROUP_TYPES_EXTENSION_ID =
            "serverGroupTypes"; //$NON-NLS-1$

    private static final String REPOSITORY_TYPES_EXTENSION_ID =
            "repositoryTypes"; //$NON-NLS-1$

    private static final String RUNTIME_TYPES_EXTENSION_ID = "runtimeTypes"; //$NON-NLS-1$

    private static final String CONFIG_PARAMETER_ELEMENT = "configParameter"; //$NON-NLS-1$

    private static final String PARAMETERS_ELEMENT = "parameters"; //$NON-NLS-1$

    private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    private static final String REPOSITORY_PUBLISHER_ATTRIBUTE =
            "repositoryPublisher"; //$NON-NLS-1$

    public static final String LITEHOUSE_MODULE_OUTPUT_FOLDER_KIND =
            "lighthouseModuleOutput"; //$NON-NLS-1$

    private static final String SUPPORTED_REPOSITORY_ELEMENT =
            "supportedRepository"; //$NON-NLS-1$

    private static final String REPOSITORY_ID_ATTRIBUTE = "repositoryId"; //$NON-NLS-1$

    private static final String ASSOCIATED_RUNTIME_TYPE_ELEMENT =
            "associatedRuntimeType"; //$NON-NLS-1$

    private static final String RUNTIME_TYPE_ID_ATTRIBUTE = "runtimeTypeId"; //$NON-NLS-1$

    private static final String PARAMETER_FACET_ELEMENT = "parameterFacet"; //$NON-NLS-1$

    private static final String VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$

    private static final String SUPPRESS_CONNECTIVITY_UI_ATTRIBUTE =
            "suppressConnectivityUI"; //$NON-NLS-1$

    private ServerContainer serverContainer;

    private final DeployFactory factory = DeployFactory.eINSTANCE;

    private final ComposedAdapterFactory adapterFactory;

    private final AdapterFactoryEditingDomain editingDomain;

    private static ServerManagerImpl instance;

    @SuppressWarnings("unchecked")
    private ServerManagerImpl() {
        // Create an adapter factory that yields item providers.
        //
        List factories = new ArrayList();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new DeployItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());

        adapterFactory = new ComposedAdapterFactory(factories) {
            @Override
            public Object adapt(Object target, Object type) {
                final Object adapter = target;
                // we have to have this for localisation as emf doesn't seem to
                // bother with giving us a label provider
                // for enumerator elements
                if (target instanceof Enumerator) {
                    IItemLabelProvider provider = new IItemLabelProvider() {
                        public String getText(Object object) {
                            String serverState = ""; //$NON-NLS-1$
                            if (adapter instanceof ServerState) {
                                if (((ServerState) adapter)
                                        .equals(ServerState.CONNECTED_LITERAL)) {
                                    serverState =
                                            Platform
                                                    .getResourceString(DeployModelEditPlugin
                                                            .getPlugin()
                                                            .getBundle(),
                                                            "%_UI_ServerState_CONNECTED_literal"); //$NON-NLS-1$
                                } else {
                                    serverState =
                                            Platform
                                                    .getResourceString(DeployModelEditPlugin
                                                            .getPlugin()
                                                            .getBundle(),
                                                            "%_UI_ServerState_DISCONNECTED_literal"); //$NON-NLS-1$
                                }
                            }
                            return serverState;
                        }

                        public Object getImage(Object object) {
                            return null;
                        }
                    };
                    return provider;
                } else {
                    return super.adapt(target, type);
                }
            }
        };
        BasicCommandStack commandStack = new BasicCommandStack();
        editingDomain =
                new AdapterFactoryEditingDomain(adapterFactory, commandStack,
                        new HashMap());

    }

    public static synchronized ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManagerImpl();
            instance.loadServerContainer();
            if (instance.cleanUpInvalidUnreferencedTypes()) {
                instance.saveServerContainer();
            }

        }
        return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ServerManager#loadServerContainer()
     */
    synchronized public void loadServerContainer() {
        if (serverContainer == null) {
            serverContainer = factory.createServerContainer();
            File deployServersFile = getDeployServersFile(false);
            if (deployServersFile != null) {
                // load serverContainers from file.
                URI fileURI =
                        URI.createFileURI(deployServersFile.getAbsolutePath());
                Resource res = new XMLResourceImpl(fileURI);
                try {
                    res.load(null);
                    Object root = res.getContents().get(0);
                    if (root instanceof ServerContainer) {
                        ServerContainer loadedContainer =
                                (ServerContainer) root;
                        serverContainer = loadedContainer;
                    }
                } catch (IOException e) {
                    DeployModelEditPlugin
                            .getPlugin()
                            .logWarning("Could not load default server configuration.", e); //$NON-NLS-1$
                }
            }
            checkIds();
            createRuntimeTypesFromExtensions();
            createRepositoryTypesFromExtensions();
            createServerTypesFromExtensions();
            createServerGroupTypesFromExtensions();
            linkConfigParameterInfos();
        }
    }

    /**
     * Adds references from parameters to parameterInfos in the loaded server
     * model.
     */
    private void linkConfigParameterInfos() {
        // Servers.
        for (Server server : serverContainer.getServers()) {
            ServerType serverType = server.getServerType();
            ServerConfig serverConfig = server.getServerConfig();
            if (serverType != null && serverType.getServerConfigInfo() != null
                    && serverConfig != null) {
                EList<ConfigParameterInfo> configParameterInfos =
                        serverType.getServerConfigInfo()
                                .getConfigParameterInfos();
                for (ConfigParameterInfo info : configParameterInfos) {
                    ConfigParameter configParameter =
                            serverConfig.getConfigParameter(info.getKey());
                    if (configParameter != null) {
                        configParameter.setConfigParameterInfo(info);
                    }
                }
            }
            // Server's Repository.
            Repository repository = server.getRepository();
            if (repository != null) {
                RepositoryType repositoryType = repository.getRepositoryType();
                RepositoryConfig repositoryConfig =
                        repository.getRepositoryConfig();
                if (repositoryType != null
                        && repositoryType.getRepositoryParameterInfos() != null
                        && repositoryConfig != null) {
                    EList<ConfigParameterInfo> configParameterInfos =
                            repositoryType.getRepositoryParameterInfos();
                    for (ConfigParameterInfo info : configParameterInfos) {
                        ConfigParameter configParameter =
                                repositoryConfig.getConfigParameter(info
                                        .getKey());
                        if (configParameter != null) {
                            configParameter.setConfigParameterInfo(info);
                        }
                    }
                }
            }
        }

        // Runtimes.
        for (Runtime runtime : serverContainer.getRuntimes()) {
            RuntimeType runtimeType = runtime.getRuntimeType();
            RuntimeConfig runtimeConfig = runtime.getRuntimeConfig();
            if (runtimeType != null
                    && runtimeType.getRuntimeParameterInfos() != null
                    && runtimeConfig != null) {
                EList<ConfigParameterInfo> configParameterInfos =
                        runtimeType.getRuntimeParameterInfos();
                for (ConfigParameterInfo info : configParameterInfos) {
                    ConfigParameter configParameter =
                            runtimeConfig.getConfigParameter(info.getKey());
                    if (configParameter != null) {
                        configParameter.setConfigParameterInfo(info);
                    }
                }
            }
        }
    }

    /**
     * Cleans up the invalid and not referenced types.
     */
    private boolean cleanUpInvalidUnreferencedTypes() {
        boolean needSave = false;
        // Find and remove unreferenced serverTypes.
        HashSet<ServerType> referencedServerTypes = new HashSet<ServerType>();
        for (Server server : serverContainer.getServers()) {
            referencedServerTypes.add(server.getServerType());
        }
        ArrayList<ServerType> toDelete = new ArrayList<ServerType>();
        for (ServerType serverType : serverContainer.getServerTypes()) {
            if (!referencedServerTypes.contains(serverType)
                    && !serverType.isValid()) {
                toDelete.add(serverType);
            }
        }
        Command deleteReferencedTypes =
                DeleteCommand.create(editingDomain, toDelete);
        if (deleteReferencedTypes.canExecute()) {
            editingDomain.getCommandStack().execute(deleteReferencedTypes);
            needSave = true;
        }
        return needSave;

    }

    /**
     * Server IDs were added in Studio 3.1.0 alpha 7 and are required to link
     * the main server file with extension files. This check ensures that all of
     * the servers in the main file have an ID persisted.
     */
    private void checkIds() {
        boolean added = false;
        for (Server server : serverContainer.getServers()) {
            // Check if ID has been automatically generated rather than set (via
            // load).
            if (!((UniqueIdElementImpl) server).isIdFromFile()) {
                added = true;
                break;
            }
        }
        if (added) {
            new Job(DEPLOYMENT_SERVERS_FILE) {

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    saveServerContainer();
                    return Status.OK_STATUS;
                }

            }.schedule();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ServerManager#saveServerContainer()
     */
    @SuppressWarnings("unchecked")
    public void saveServerContainer() {
        if (serverContainer == null) {
            return;
        }
        File deployServersFile = getDeployServersFile(true);
        URI fileURI = URI.createFileURI(deployServersFile.getAbsolutePath());
        Resource res = new XMLResourceImpl(fileURI);
        res.getContents().add(serverContainer);
        try {
            res.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // refresh
        Path filePath = new Path(deployServersFile.getAbsolutePath());
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(BS_SERVERS_PROJECT);
        if (project.getLocation().isPrefixOf(filePath)) {
            if (project.isOpen()) {
                try {
                    project.refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getDeployServersFile(boolean createIfNotExists) {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = workspaceRoot.getProject(BS_SERVERS_PROJECT);
        NullProgressMonitor progressMonitor = new NullProgressMonitor();
        try {
            if (project != null && project.exists()) {
                if (!project.isOpen()) {
                    try {
                        project.open(progressMonitor);
                    } catch (CoreException e) {
                        // project exists but cannot be open
                        if (createIfNotExists) {
                            // recreate if needed
                            project.delete(true, progressMonitor);
                            project.create(progressMonitor);
                            project.open(progressMonitor);
                            ProjectUtil.addNature(project,
                                    BSServersNature.NATURE_ID);
                        } else {
                            return null;
                        }
                    }
                }
                if (!project.hasNature(BSServersNature.NATURE_ID)) {
                    String message =
                            String
                                    .format("Special project %s doesn't have required nature: %s", //$NON-NLS-1$
                                            project.getName(),
                                            BSServersNature.NATURE_ID);
                    throw new RuntimeException(message);
                }
            } else {
                if (createIfNotExists) {
                    project.create(progressMonitor);
                    project.open(progressMonitor);
                    ProjectUtil.addNature(project, BSServersNature.NATURE_ID);
                } else {
                    return null;
                }
            }
            IFile file = project.getFile(DEPLOYMENT_SERVERS_FILE);
            return file.getLocation().toFile();
        } catch (CoreException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // TODO Choose where you want to store server information
        // (workspace/project) depending on preferences.
        // return DeployModelEditPlugin.getPlugin().getStateLocation().append(
        // "DeployServers.xml").toFile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ServerManager#getServerContainer()
     */
    public ServerContainer getServerContainer() {
        return serverContainer;
    }

    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private void createRuntimeTypesFromExtensions() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point =
                registry
                        .getExtensionPoint(com.tibco.xpd.deploy.DeployCoreActivator.PLUGIN_ID,
                                RUNTIME_TYPES_EXTENSION_ID);
        if (point == null)
            return;
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] configElements =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < configElements.length; j++) {
                IConfigurationElement configElement = configElements[j];
                String id = getAttribute(configElement, ID_ATTRIBUTE, null);
                RuntimeType runtimeType =
                        serverContainer.getRuntimeTypeById(id);
                if (runtimeType == null) {
                    runtimeType = factory.createRuntimeType();
                    runtimeType.setId(id);
                }
                runtimeType.setName(getAttribute(configElement,
                        NAME_ATTRIBUTE,
                        runtimeType.getId()));
                runtimeType.setDescription(getAttribute(configElement,
                        DESCRIPTION_ATTRIBUTE,
                        "")); //$NON-NLS-1$
                runtimeType.getRuntimeParameterInfos()
                        .addAll(getConfigInfos(configElement));
                runtimeType.setValid(true);
                runtimeType.setLastExtensionId(extensions[i].getContributor()
                        .getName());
                if (!serverContainer.getRuntimeTypes().contains(runtimeType)) {
                    serverContainer.getRuntimeTypes().add(runtimeType);
                }
            }
        }
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private void createRepositoryTypesFromExtensions() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point =
                registry
                        .getExtensionPoint(com.tibco.xpd.deploy.DeployCoreActivator.PLUGIN_ID,
                                REPOSITORY_TYPES_EXTENSION_ID);
        if (point == null)
            return;
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] configElements =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < configElements.length; j++) {
                IConfigurationElement configElement = configElements[j];
                String id = getAttribute(configElement, ID_ATTRIBUTE, null);
                RepositoryType repositoryType =
                        serverContainer.getRepositoryTypeById(id);
                if (repositoryType == null) {
                    repositoryType = factory.createRepositoryType();
                    repositoryType.setId(id);
                }
                repositoryType.setName(getAttribute(configElement,
                        NAME_ATTRIBUTE,
                        repositoryType.getId()));
                repositoryType.setDescription(getAttribute(configElement,
                        DESCRIPTION_ATTRIBUTE,
                        "")); //$NON-NLS-1$
                repositoryType.getRepositoryParameterInfos()
                        .addAll(getConfigInfos(configElement));
                repositoryType.setValid(true);
                repositoryType.setLastExtensionId(extensions[i]
                        .getContributor().getName());
                RepositoryPublisher repositoryPublisher = null;
                try {
                    repositoryPublisher =
                            (RepositoryPublisher) configElement
                                    .createExecutableExtension(REPOSITORY_PUBLISHER_ATTRIBUTE);
                    repositoryType.setRepositoryPublisher(repositoryPublisher);
                } catch (CoreException e) {
                    throw new IllegalArgumentException(
                            "Cannot create executable extension for: " //$NON-NLS-1$
                                    + repositoryPublisher, e);
                }
                if (!serverContainer.getRepositoryTypes()
                        .contains(repositoryType)) {
                    serverContainer.getRepositoryTypes().add(repositoryType);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void createServerTypesFromExtensions() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point =
                registry
                        .getExtensionPoint(com.tibco.xpd.deploy.DeployCoreActivator.PLUGIN_ID,
                                SERVER_TYPES_EXTENSION_ID);
        if (point == null)
            return;
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] configElements =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < configElements.length; j++) {
                IConfigurationElement configElement = configElements[j];
                String id = getAttribute(configElement, ID_ATTRIBUTE, null);
                ServerType serverType = serverContainer.getServerTypeById(id);
                if (serverType == null) {
                    serverType = factory.createServerType();
                    serverType.setId(id);
                }
                serverType.setName(getAttribute(configElement,
                        NAME_ATTRIBUTE,
                        serverType.getId()));
                serverType.setDescription(getAttribute(configElement,
                        DESCRIPTION_ATTRIBUTE,
                        "")); //$NON-NLS-1$
                serverType
                        .setServerConfigInfo(createServerConfigInfo(configElement));
                serverType.setSuppressConectivity(Boolean
                        .parseBoolean(getAttribute(configElement,
                                SUPPRESS_CONNECTIVITY_UI_ATTRIBUTE,
                                "false"))); //$NON-NLS-1$
                serverType.setValid(true);
                serverType.setLastExtensionId(extensions[i].getContributor()
                        .getName());
                setSupportedRepositoryTypes(serverType, configElement);
                setRuntimeType(serverType, configElement);
                ConnectionFactory connFactory = null;
                try {
                    connFactory =
                            (ConnectionFactory) configElement
                                    .createExecutableExtension(CONNECTION_FACTORY_ATTRIBUTE);
                    serverType.setConnectionFactory(connFactory);
                } catch (CoreException e) {
                    throw new IllegalArgumentException(
                            "Cannot create executable extension for: " //$NON-NLS-1$
                                    + connFactory, e);
                }
                if (!serverContainer.getServerTypes().contains(serverType)) {
                    serverContainer.getServerTypes().add(serverType);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    private void createServerGroupTypesFromExtensions() {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point =
                registry
                        .getExtensionPoint(com.tibco.xpd.deploy.DeployCoreActivator.PLUGIN_ID,
                                SERVER_GROUP_TYPES_EXTENSION_ID);
        if (point == null)
            return;
        IExtension[] extensions = point.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] configElements =
                    extensions[i].getConfigurationElements();
            for (int j = 0; j < configElements.length; j++) {
                IConfigurationElement configElement = configElements[j];
                String id = getAttribute(configElement, ID_ATTRIBUTE, null);
                ServerGroupType serverGroupType =
                        serverContainer.getServerGroupTypeById(id);
                if (serverGroupType == null) {
                    serverGroupType = factory.createServerGroupType();
                    serverGroupType.setId(id);
                }
                serverGroupType.setName(getAttribute(configElement,
                        NAME_ATTRIBUTE,
                        serverGroupType.getId()));
                serverGroupType.setDescription(getAttribute(configElement,
                        DESCRIPTION_ATTRIBUTE,
                        "")); //$NON-NLS-1$
                setServerTypeReferences(serverGroupType, configElement);
                serverGroupType.setValid(true);
                serverGroupType.setLastExtensionId(extensions[i]
                        .getContributor().getName());
                if (!serverContainer.getServerGroupTypes()
                        .contains(serverGroupType)) {
                    serverContainer.getServerGroupTypes().add(serverGroupType);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void setRuntimeType(ServerType serverType,
            IConfigurationElement configElement) {
        List<RuntimeType> runtimeTypes = serverContainer.getRuntimeTypes();
        IConfigurationElement[] configElements =
                configElement.getChildren(ASSOCIATED_RUNTIME_TYPE_ELEMENT);
        for (int i = 0; i < configElements.length; i++) {
            IConfigurationElement e = configElements[i];
            String runtimeTypeId =
                    getAttribute(e, RUNTIME_TYPE_ID_ATTRIBUTE, null);
            boolean existringRepository = false;
            for (RuntimeType runtimeType : runtimeTypes) {
                if (runtimeTypeId.equals(runtimeType.getId())) {
                    serverType.setRuntimeType(runtimeType);
                    existringRepository = true;
                    break;
                }
            }
            if (!existringRepository) {
                String message =
                        String
                                .format("Server Runtime Type: %s is associated with nonexisting Runtime Type Client :%s", //$NON-NLS-1$
                                        serverType,
                                        runtimeTypeId);
                DeployModelEditPlugin.getPlugin().logWarning(message, null);
            }

            // the loop will be invoked only once so the first associated
            // RuntimeType will be taken into consideration
            break;
        }
    }

    @SuppressWarnings("unchecked")
    private void setSupportedRepositoryTypes(ServerType serverType,
            IConfigurationElement configElement) {
        List<RepositoryType> repositoryTypes =
                serverContainer.getRepositoryTypes();
        IConfigurationElement[] configElements =
                configElement.getChildren(SUPPORTED_REPOSITORY_ELEMENT);
        for (int i = 0; i < configElements.length; i++) {
            IConfigurationElement e = configElements[i];
            String repositoryId =
                    getAttribute(e, REPOSITORY_ID_ATTRIBUTE, null);
            boolean existringRepository = false;
            for (RepositoryType repoType : repositoryTypes) {
                if (repositoryId.equals(repoType.getId())) {
                    serverType.getSupportedRepositoryTypes().add(repoType);
                    existringRepository = true;
                    break;
                }
            }
            if (!existringRepository) {
                String message =
                        String
                                .format("Server Runtime Type: %s supports non existing Repository Type :%s", //$NON-NLS-1$
                                        serverType,
                                        repositoryId);
                DeployModelEditPlugin.getPlugin().logWarning(message, null);
            }

        }
    }

    @SuppressWarnings("unchecked")
    private void setServerTypeReferences(ServerGroupType serverGroupType,
            IConfigurationElement configElement) {
        List<ServerType> serverTypes = serverContainer.getServerTypes();
        IConfigurationElement[] configElements =
                configElement.getChildren(SERVER_TYPE_REFERENCE_ELEMENT);
        for (int i = 0; i < configElements.length; i++) {
            IConfigurationElement e = configElements[i];
            String serverTypeId =
                    getAttribute(e, SERVER_TYPE_ID_ATTRIBUTE, null);
            boolean existringServerType = false;
            for (ServerType serverType : serverTypes) {
                if (serverTypeId.equals(serverType.getId())
                        && serverType.isValid()) {
                    serverGroupType.getServerTypes().add(serverType);
                    existringServerType = true;
                    break;
                }
            }
            if (!existringServerType) {
                String message =
                        String
                                .format("Server Group Type: %s references non existing Server Type :%s", //$NON-NLS-1$
                                        serverGroupType.getId(),
                                        serverTypeId);
                DeployModelEditPlugin.getPlugin().logWarning(message, null);
            }

        }
    }

    @SuppressWarnings("unchecked")
    private ServerConfigInfo createServerConfigInfo(
            IConfigurationElement configElement) {
        ServerConfigInfo configInfo = factory.createServerConfigInfo();
        configInfo.setName("Configuration info for " //$NON-NLS-1$
                + getAttribute(configElement, NAME_ATTRIBUTE, null));
        IConfigurationElement[] configElements =
                configElement.getChildren(CONFIG_PARAMETER_ELEMENT);
        for (int i = 0; i < configElements.length; i++) {
            ConfigParameterInfo configParam =
                    createConfigParameterInfo(configElements[i]);
            configInfo.getConfigParameterInfos().add(configParam);
        }
        return configInfo;
    }

    @SuppressWarnings("unchecked")
    private List getConfigInfos(IConfigurationElement configElement) {

        IConfigurationElement[] parametersElements =
                configElement.getChildren(PARAMETERS_ELEMENT);
        if (parametersElements.length != 1) {
            return Collections.EMPTY_LIST;
        }
        IConfigurationElement[] configElements =
                parametersElements[0].getChildren(CONFIG_PARAMETER_ELEMENT);
        List<ConfigParameterInfo> parameters =
                new ArrayList<ConfigParameterInfo>();
        for (int i = 0; i < configElements.length; i++) {
            ConfigParameterInfo configParam =
                    createConfigParameterInfo(configElements[i]);
            parameters.add(configParam);
        }
        return parameters;
    }

    /**
     * Creates configration parameter info from extension element representing
     * parameter.
     * 
     * @param e
     *            extension element representing configParameter.
     * @return a new ConfigInfoParameter build based on extension information.
     */
    private ConfigParameterInfo createConfigParameterInfo(
            IConfigurationElement e) {
        ConfigParameterInfo configParam = factory.createConfigParameterInfo();
        configParam.setKey(getAttribute(e, KEY_ATTRIBUTE, null));
        configParam.setName(getAttribute(e, NAME_ATTRIBUTE, null));
        configParam.setLabel(getAttribute(e, LABEL_ATTRIBUTE, null));
        configParam.setIcon(getAttribute(e, ICON_ATTRIBUTE, "")); //$NON-NLS-1$
        configParam.setParameterType(ConfigParameterType.get(getAttribute(e,
                PARAMETER_TYPE_ATTRIBUTE,
                ConfigParameterType.STRING_LITERAL.getLiteral())));
        String defaultVal = getAttribute(e, DEFAULT_VALUE_ATTRIBUTE, ""); //$NON-NLS-1$
        defaultVal = resolve(defaultVal);
        configParam.setDefaultValue(defaultVal);
        configParam.setRequired(Boolean.parseBoolean(getAttribute(e,
                REQUIRED_ATTRIBUTE,
                "false"))); //$NON-NLS-1$
        configParam.setDescription(getAttribute(e, DESCRIPTION_ATTRIBUTE, "")); //$NON-NLS-1$
        configParam.setAutomatic(Boolean.parseBoolean(getAttribute(e,
                AUTOMATIC_ATTRIBUTE,
                "true"))); //$NON-NLS-1$
        IConfigurationElement[] paremterFacetElements =
                e.getChildren(PARAMETER_FACET_ELEMENT);

        for (IConfigurationElement paramFacetElem : paremterFacetElements) {
            ParameterFacet facet = factory.createParameterFacet();
            facet.setKey(getAttribute(paramFacetElem, KEY_ATTRIBUTE, null));
            facet.setValue(getAttribute(paramFacetElem, VALUE_ATTRIBUTE, "")); //$NON-NLS-1$
            configParam.getFacets().add(facet);
        }

        // dynamic default value
        String defaultValuProviderString =
                e.getAttribute(DEFAULT_VALUE_PROVIDER_ATTRIBUTE);
        if (defaultValuProviderString != null
                && defaultValuProviderString.trim().length() > 0) {
            if (configParam instanceof ConfigParameterInfoImpl) {
                ConfigParameterInfoImpl configParamImpl =
                        (ConfigParameterInfoImpl) configParam;
                try {
                    DefaultValueProvider defaultValueProvider =
                            (DefaultValueProvider) e
                                    .createExecutableExtension(DEFAULT_VALUE_PROVIDER_ATTRIBUTE);
                    configParamImpl
                            .setDefaultValueProvider(defaultValueProvider);
                } catch (CoreException ex) {
                    DeployModelEditPlugin
                            .getPlugin()
                            .logError("Could not load defaultValueProvider: " + defaultValuProviderString, ex); //$NON-NLS-1$
                }
            }
        }
        return configParam;

    }

    private static String resolve(String s) {
        String pluginStateLoc =
                DeployModelEditPlugin.getPlugin().getStateLocation().toString();
        String pluginStateLocUrl = ""; //$NON-NLS-1$
        try {
            pluginStateLocUrl =
                    DeployModelEditPlugin.getPlugin().getStateLocation()
                            .toFile().toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // table with all possible substitutions.
        String[][] subst =
                new String[][] {
                        {
                                "${eclipse.pluginStateLocation}", pluginStateLoc, "path" }, //$NON-NLS-1$ //$NON-NLS-2$
                        {
                                "${eclipse.pluginStateLocation.url}", pluginStateLocUrl, "" } }; //$NON-NLS-1$//$NON-NLS-2$
        for (int i = 0; i < subst.length; i++) {
            if (s.indexOf(subst[i][0]) != -1) {
                s = s.replace(subst[i][0], subst[i][1]);
                if ("path".equals(subst[i][2])) { //$NON-NLS-1$
                    s = new File(s).toString();
                }
            }
        }
        return s;
    }

    private static String getAttribute(IConfigurationElement configElement,
            String attrName, String defaultValue) {
        String value = configElement.getAttribute(attrName);
        if (value != null) {
            return value;
        }
        if (defaultValue != null) {
            return defaultValue;
        }
        throw new IllegalArgumentException("Missing attribute: " + attrName); //$NON-NLS-1$
    }

    public EditingDomain getEditingDomain() {
        return editingDomain;
    }

}
