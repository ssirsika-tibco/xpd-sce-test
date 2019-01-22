/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.util;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.ContainerElement;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.LeafElement;
import com.tibco.xpd.deploy.Loadable;
import com.tibco.xpd.deploy.Module;
import com.tibco.xpd.deploy.ModuleContainer;
import com.tibco.xpd.deploy.NamedElement;
import com.tibco.xpd.deploy.Operation;
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
import com.tibco.xpd.deploy.ServerElement;
import com.tibco.xpd.deploy.ServerElementState;
import com.tibco.xpd.deploy.ServerElementType;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ServerModule;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.UniqueIdElement;
import com.tibco.xpd.deploy.WorkspaceModule;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.deploy.DeployPackage
 * @generated
 */
public class DeployAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static DeployPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeployAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = DeployPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DeploySwitch<Adapter> modelSwitch = new DeploySwitch<Adapter>() {
        @Override
        public Adapter caseServerConfig(ServerConfig object) {
            return createServerConfigAdapter();
        }

        @Override
        public Adapter caseConfigParameter(ConfigParameter object) {
            return createConfigParameterAdapter();
        }

        @Override
        public Adapter caseNamedElement(NamedElement object) {
            return createNamedElementAdapter();
        }

        @Override
        public Adapter caseServerType(ServerType object) {
            return createServerTypeAdapter();
        }

        @Override
        public Adapter caseServerConfigInfo(ServerConfigInfo object) {
            return createServerConfigInfoAdapter();
        }

        @Override
        public Adapter caseConfigParameterInfo(ConfigParameterInfo object) {
            return createConfigParameterInfoAdapter();
        }

        @Override
        public Adapter caseModule(Module object) {
            return createModuleAdapter();
        }

        @Override
        public Adapter caseServerModule(ServerModule object) {
            return createServerModuleAdapter();
        }

        @Override
        public Adapter caseServer(Server object) {
            return createServerAdapter();
        }

        @Override
        public Adapter caseServerElement(ServerElement object) {
            return createServerElementAdapter();
        }

        @Override
        public Adapter caseServerContainer(ServerContainer object) {
            return createServerContainerAdapter();
        }

        @Override
        public Adapter caseRepositoryType(RepositoryType object) {
            return createRepositoryTypeAdapter();
        }

        @Override
        public Adapter caseRepository(Repository object) {
            return createRepositoryAdapter();
        }

        @Override
        public Adapter caseRepositoryConfig(RepositoryConfig object) {
            return createRepositoryConfigAdapter();
        }

        @Override
        public Adapter caseRuntime(com.tibco.xpd.deploy.Runtime object) {
            return createRuntimeAdapter();
        }

        @Override
        public Adapter caseRuntimeType(RuntimeType object) {
            return createRuntimeTypeAdapter();
        }

        @Override
        public Adapter caseRuntimeConfig(RuntimeConfig object) {
            return createRuntimeConfigAdapter();
        }

        @Override
        public Adapter caseContainerElement(ContainerElement object) {
            return createContainerElementAdapter();
        }

        @Override
        public Adapter caseLeafElement(LeafElement object) {
            return createLeafElementAdapter();
        }

        @Override
        public Adapter caseModuleContainer(ModuleContainer object) {
            return createModuleContainerAdapter();
        }

        @Override
        public Adapter caseServerElementType(ServerElementType object) {
            return createServerElementTypeAdapter();
        }

        @Override
        public Adapter caseOperation(Operation object) {
            return createOperationAdapter();
        }

        @Override
        public Adapter caseServerElementState(ServerElementState object) {
            return createServerElementStateAdapter();
        }

        @Override
        public Adapter caseWorkspaceModule(WorkspaceModule object) {
            return createWorkspaceModuleAdapter();
        }

        @Override
        public Adapter caseUniqueIdElement(UniqueIdElement object) {
            return createUniqueIdElementAdapter();
        }

        @Override
        public Adapter caseServerGroup(ServerGroup object) {
            return createServerGroupAdapter();
        }

        @Override
        public Adapter caseServerGroupType(ServerGroupType object) {
            return createServerGroupTypeAdapter();
        }

        @Override
        public Adapter caseLoadable(Loadable object) {
            return createLoadableAdapter();
        }

        @Override
        public Adapter caseParameterFacet(ParameterFacet object) {
            return createParameterFacetAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerConfig <em>Server Config</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerConfig
     * @generated
     */
    public Adapter createServerConfigAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerType <em>Server Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerType
     * @generated
     */
    public Adapter createServerTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerConfigInfo <em>Server Config Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerConfigInfo
     * @generated
     */
    public Adapter createServerConfigInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ConfigParameterInfo <em>Config Parameter Info</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ConfigParameterInfo
     * @generated
     */
    public Adapter createConfigParameterInfoAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Module <em>Module</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Module
     * @generated
     */
    public Adapter createModuleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerModule <em>Server Module</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerModule
     * @generated
     */
    public Adapter createServerModuleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Server <em>Server</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Server
     * @generated
     */
    public Adapter createServerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerContainer <em>Server Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerContainer
     * @generated
     */
    public Adapter createServerContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ConfigParameter <em>Config Parameter</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ConfigParameter
     * @generated
     */
    public Adapter createConfigParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.RepositoryType <em>Repository Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.RepositoryType
     * @generated
     */
    public Adapter createRepositoryTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Repository <em>Repository</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Repository
     * @generated
     */
    public Adapter createRepositoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.RepositoryConfig <em>Repository Config</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.RepositoryConfig
     * @generated
     */
    public Adapter createRepositoryConfigAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Runtime <em>Runtime</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Runtime
     * @generated
     */
    public Adapter createRuntimeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.RuntimeType <em>Runtime Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.RuntimeType
     * @generated
     */
    public Adapter createRuntimeTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.RuntimeConfig <em>Runtime Config</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.RuntimeConfig
     * @generated
     */
    public Adapter createRuntimeConfigAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerElement <em>Server Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerElement
     * @generated
     */
    public Adapter createServerElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ContainerElement <em>Container Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ContainerElement
     * @generated
     */
    public Adapter createContainerElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.LeafElement <em>Leaf Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.LeafElement
     * @generated
     */
    public Adapter createLeafElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ModuleContainer <em>Module Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ModuleContainer
     * @generated
     */
    public Adapter createModuleContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerElementType <em>Server Element Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerElementType
     * @generated
     */
    public Adapter createServerElementTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Operation <em>Operation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Operation
     * @generated
     */
    public Adapter createOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerElementState <em>Server Element State</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerElementState
     * @generated
     */
    public Adapter createServerElementStateAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.WorkspaceModule <em>Workspace Module</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.WorkspaceModule
     * @generated
     */
    public Adapter createWorkspaceModuleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.UniqueIdElement <em>Unique Id Element</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.UniqueIdElement
     * @generated
     */
    public Adapter createUniqueIdElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerGroup <em>Server Group</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerGroup
     * @generated
     */
    public Adapter createServerGroupAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ServerGroupType <em>Server Group Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ServerGroupType
     * @generated
     */
    public Adapter createServerGroupTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.Loadable <em>Loadable</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.Loadable
     * @generated
     */
    public Adapter createLoadableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.deploy.ParameterFacet <em>Parameter Facet</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.deploy.ParameterFacet
     * @generated
     */
    public Adapter createParameterFacetAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //DeployAdapterFactory
