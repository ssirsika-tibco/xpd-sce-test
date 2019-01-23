/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ConfigParameterType;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.DeploymentType;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.ParameterFacet;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.RuntimeConfig;
import com.tibco.xpd.deploy.RuntimeType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerConfigInfo;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerModule;
import com.tibco.xpd.deploy.ServerState;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.UniqueIdElement;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.model.extension.DeploymentException;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class DeployFactoryImpl extends EFactoryImpl implements DeployFactory {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public static DeployFactory init() {
        try {
            DeployFactory theDeployFactory = (DeployFactory) EPackage.Registry.INSTANCE
                    .getEFactory("http:///com/tibco/xpd/deploy/model.ecore");
            if (theDeployFactory != null) {
                return theDeployFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DeployFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public DeployFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case DeployPackage.SERVER_CONFIG:
            return createServerConfig();
        case DeployPackage.CONFIG_PARAMETER:
            return createConfigParameter();
        case DeployPackage.SERVER_TYPE:
            return createServerType();
        case DeployPackage.SERVER_CONFIG_INFO:
            return createServerConfigInfo();
        case DeployPackage.CONFIG_PARAMETER_INFO:
            return createConfigParameterInfo();
        case DeployPackage.SERVER_MODULE:
            return createServerModule();
        case DeployPackage.SERVER:
            return createServer();
        case DeployPackage.SERVER_CONTAINER:
            return createServerContainer();
        case DeployPackage.REPOSITORY_TYPE:
            return createRepositoryType();
        case DeployPackage.REPOSITORY:
            return createRepository();
        case DeployPackage.REPOSITORY_CONFIG:
            return createRepositoryConfig();
        case DeployPackage.RUNTIME:
            return createRuntime();
        case DeployPackage.RUNTIME_TYPE:
            return createRuntimeType();
        case DeployPackage.RUNTIME_CONFIG:
            return createRuntimeConfig();
        case DeployPackage.MODULE_CONTAINER:
            return createModuleContainer();
        case DeployPackage.SERVER_ELEMENT_TYPE:
            return createServerElementType();
        case DeployPackage.SERVER_ELEMENT_STATE:
            return createServerElementState();
        case DeployPackage.WORKSPACE_MODULE:
            return createWorkspaceModule();
        case DeployPackage.SERVER_GROUP:
            return createServerGroup();
        case DeployPackage.SERVER_GROUP_TYPE:
            return createServerGroupType();
        case DeployPackage.PARAMETER_FACET:
            return createParameterFacet();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName()
                    + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case DeployPackage.SERVER_STATE:
            return createServerStateFromString(eDataType, initialValue);
        case DeployPackage.CONFIG_PARAMETER_TYPE:
            return createConfigParameterTypeFromString(eDataType, initialValue);
        case DeployPackage.DEPLOYMENT_TYPE:
            return createDeploymentTypeFromString(eDataType, initialValue);
        case DeployPackage.EREPOSITORY_PUBLISHER:
            return createERepositoryPublisherFromString(eDataType, initialValue);
        case DeployPackage.EDEPLOYMENT_EXCEPTION:
            return createEDeploymentExceptionFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '"
                    + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case DeployPackage.SERVER_STATE:
            return convertServerStateToString(eDataType, instanceValue);
        case DeployPackage.CONFIG_PARAMETER_TYPE:
            return convertConfigParameterTypeToString(eDataType, instanceValue);
        case DeployPackage.DEPLOYMENT_TYPE:
            return convertDeploymentTypeToString(eDataType, instanceValue);
        case DeployPackage.EREPOSITORY_PUBLISHER:
            return convertERepositoryPublisherToString(eDataType, instanceValue);
        case DeployPackage.EDEPLOYMENT_EXCEPTION:
            return convertEDeploymentExceptionToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '"
                    + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerConfig createServerConfig() {
        ServerConfigImpl serverConfig = new ServerConfigImpl();
        return serverConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerType createServerType() {
        ServerTypeImpl serverType = new ServerTypeImpl();
        return serverType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerConfigInfo createServerConfigInfo() {
        ServerConfigInfoImpl serverConfigInfo = new ServerConfigInfoImpl();
        return serverConfigInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConfigParameterInfo createConfigParameterInfo() {
        ConfigParameterInfoImpl configParameterInfo = new ConfigParameterInfoImpl();
        return configParameterInfo;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerModule createServerModule() {
        ServerModuleImpl serverModule = new ServerModuleImpl();
        return serverModule;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Server createServer() {
        ServerImpl server = new ServerImpl();
        return server;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerContainer createServerContainer() {
        ServerContainerImpl serverContainer = new ServerContainerImpl();
        return serverContainer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConfigParameter createConfigParameter() {
        ConfigParameterImpl configParameter = new ConfigParameterImpl();
        return configParameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryType createRepositoryType() {
        RepositoryTypeImpl repositoryType = new RepositoryTypeImpl();
        return repositoryType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Repository createRepository() {
        RepositoryImpl repository = new RepositoryImpl();
        return repository;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryConfig createRepositoryConfig() {
        RepositoryConfigImpl repositoryConfig = new RepositoryConfigImpl();
        return repositoryConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.deploy.Runtime createRuntime() {
        RuntimeImpl runtime = new RuntimeImpl();
        return runtime;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuntimeType createRuntimeType() {
        RuntimeTypeImpl runtimeType = new RuntimeTypeImpl();
        return runtimeType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RuntimeConfig createRuntimeConfig() {
        RuntimeConfigImpl runtimeConfig = new RuntimeConfigImpl();
        return runtimeConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ModuleContainer createModuleContainer() {
        ModuleContainerImpl moduleContainer = new ModuleContainerImpl();
        return moduleContainer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementType createServerElementType() {
        ServerElementTypeImpl serverElementType = new ServerElementTypeImpl();
        return serverElementType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerElementState createServerElementState() {
        ServerElementStateImpl serverElementState = new ServerElementStateImpl();
        return serverElementState;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WorkspaceModule createWorkspaceModule() {
        WorkspaceModuleImpl workspaceModule = new WorkspaceModuleImpl();
        return workspaceModule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroup createServerGroup() {
        ServerGroupImpl serverGroup = new ServerGroupImpl();
        return serverGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroupType createServerGroupType() {
        ServerGroupTypeImpl serverGroupType = new ServerGroupTypeImpl();
        return serverGroupType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterFacet createParameterFacet() {
        ParameterFacetImpl parameterFacet = new ParameterFacetImpl();
        return parameterFacet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ServerState createServerStateFromString(EDataType eDataType,
            String initialValue) {
        ServerState result = ServerState.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue
                    + "' is not a valid enumerator of '" + eDataType.getName()
                    + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertServerStateToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConfigParameterType createConfigParameterTypeFromString(
            EDataType eDataType, String initialValue) {
        ConfigParameterType result = ConfigParameterType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue
                    + "' is not a valid enumerator of '" + eDataType.getName()
                    + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertConfigParameterTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeploymentType createDeploymentTypeFromString(EDataType eDataType,
            String initialValue) {
        DeploymentType result = DeploymentType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue
                    + "' is not a valid enumerator of '" + eDataType.getName()
                    + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertDeploymentTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RepositoryPublisher createERepositoryPublisherFromString(
            EDataType eDataType, String initialValue) {
        return (RepositoryPublisher) super.createFromString(eDataType,
                initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertERepositoryPublisherToString(EDataType eDataType,
            Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeploymentException createEDeploymentExceptionFromString(
            EDataType eDataType, String initialValue) {
        return (DeploymentException) super.createFromString(eDataType,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertEDeploymentExceptionToString(EDataType eDataType,
            Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DeployPackage getDeployPackage() {
        return (DeployPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DeployPackage getPackage() {
        return DeployPackage.eINSTANCE;
    }

} // DeployFactoryImpl
