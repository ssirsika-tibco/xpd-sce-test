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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.deploy.DeployPackage
 * @generated
 */
public class DeploySwitch<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static DeployPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeploySwitch() {
        if (modelPackage == null) {
            modelPackage = DeployPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(
                    eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case DeployPackage.SERVER_CONFIG: {
            ServerConfig serverConfig = (ServerConfig) theEObject;
            T result = caseServerConfig(serverConfig);
            if (result == null)
                result = caseNamedElement(serverConfig);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.CONFIG_PARAMETER: {
            ConfigParameter configParameter = (ConfigParameter) theEObject;
            T result = caseConfigParameter(configParameter);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.NAMED_ELEMENT: {
            NamedElement namedElement = (NamedElement) theEObject;
            T result = caseNamedElement(namedElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_TYPE: {
            ServerType serverType = (ServerType) theEObject;
            T result = caseServerType(serverType);
            if (result == null)
                result = caseNamedElement(serverType);
            if (result == null)
                result = caseLoadable(serverType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_CONFIG_INFO: {
            ServerConfigInfo serverConfigInfo = (ServerConfigInfo) theEObject;
            T result = caseServerConfigInfo(serverConfigInfo);
            if (result == null)
                result = caseNamedElement(serverConfigInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.CONFIG_PARAMETER_INFO: {
            ConfigParameterInfo configParameterInfo = (ConfigParameterInfo) theEObject;
            T result = caseConfigParameterInfo(configParameterInfo);
            if (result == null)
                result = caseNamedElement(configParameterInfo);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.MODULE: {
            Module module = (Module) theEObject;
            T result = caseModule(module);
            if (result == null)
                result = caseNamedElement(module);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_MODULE: {
            ServerModule serverModule = (ServerModule) theEObject;
            T result = caseServerModule(serverModule);
            if (result == null)
                result = caseModule(serverModule);
            if (result == null)
                result = caseLeafElement(serverModule);
            if (result == null)
                result = caseNamedElement(serverModule);
            if (result == null)
                result = caseServerElement(serverModule);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER: {
            Server server = (Server) theEObject;
            T result = caseServer(server);
            if (result == null)
                result = caseUniqueIdElement(server);
            if (result == null)
                result = caseNamedElement(server);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_ELEMENT: {
            ServerElement serverElement = (ServerElement) theEObject;
            T result = caseServerElement(serverElement);
            if (result == null)
                result = caseNamedElement(serverElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_CONTAINER: {
            ServerContainer serverContainer = (ServerContainer) theEObject;
            T result = caseServerContainer(serverContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.REPOSITORY_TYPE: {
            RepositoryType repositoryType = (RepositoryType) theEObject;
            T result = caseRepositoryType(repositoryType);
            if (result == null)
                result = caseNamedElement(repositoryType);
            if (result == null)
                result = caseLoadable(repositoryType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.REPOSITORY: {
            Repository repository = (Repository) theEObject;
            T result = caseRepository(repository);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.REPOSITORY_CONFIG: {
            RepositoryConfig repositoryConfig = (RepositoryConfig) theEObject;
            T result = caseRepositoryConfig(repositoryConfig);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.RUNTIME: {
            com.tibco.xpd.deploy.Runtime runtime = (com.tibco.xpd.deploy.Runtime) theEObject;
            T result = caseRuntime(runtime);
            if (result == null)
                result = caseNamedElement(runtime);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.RUNTIME_TYPE: {
            RuntimeType runtimeType = (RuntimeType) theEObject;
            T result = caseRuntimeType(runtimeType);
            if (result == null)
                result = caseNamedElement(runtimeType);
            if (result == null)
                result = caseLoadable(runtimeType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.RUNTIME_CONFIG: {
            RuntimeConfig runtimeConfig = (RuntimeConfig) theEObject;
            T result = caseRuntimeConfig(runtimeConfig);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.CONTAINER_ELEMENT: {
            ContainerElement containerElement = (ContainerElement) theEObject;
            T result = caseContainerElement(containerElement);
            if (result == null)
                result = caseServerElement(containerElement);
            if (result == null)
                result = caseNamedElement(containerElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.LEAF_ELEMENT: {
            LeafElement leafElement = (LeafElement) theEObject;
            T result = caseLeafElement(leafElement);
            if (result == null)
                result = caseServerElement(leafElement);
            if (result == null)
                result = caseNamedElement(leafElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.MODULE_CONTAINER: {
            ModuleContainer moduleContainer = (ModuleContainer) theEObject;
            T result = caseModuleContainer(moduleContainer);
            if (result == null)
                result = caseContainerElement(moduleContainer);
            if (result == null)
                result = caseServerElement(moduleContainer);
            if (result == null)
                result = caseNamedElement(moduleContainer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_ELEMENT_TYPE: {
            ServerElementType serverElementType = (ServerElementType) theEObject;
            T result = caseServerElementType(serverElementType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.OPERATION: {
            Operation operation = (Operation) theEObject;
            T result = caseOperation(operation);
            if (result == null)
                result = caseNamedElement(operation);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_ELEMENT_STATE: {
            ServerElementState serverElementState = (ServerElementState) theEObject;
            T result = caseServerElementState(serverElementState);
            if (result == null)
                result = caseNamedElement(serverElementState);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.WORKSPACE_MODULE: {
            WorkspaceModule workspaceModule = (WorkspaceModule) theEObject;
            T result = caseWorkspaceModule(workspaceModule);
            if (result == null)
                result = caseModule(workspaceModule);
            if (result == null)
                result = caseNamedElement(workspaceModule);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.UNIQUE_ID_ELEMENT: {
            UniqueIdElement uniqueIdElement = (UniqueIdElement) theEObject;
            T result = caseUniqueIdElement(uniqueIdElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_GROUP: {
            ServerGroup serverGroup = (ServerGroup) theEObject;
            T result = caseServerGroup(serverGroup);
            if (result == null)
                result = caseNamedElement(serverGroup);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.SERVER_GROUP_TYPE: {
            ServerGroupType serverGroupType = (ServerGroupType) theEObject;
            T result = caseServerGroupType(serverGroupType);
            if (result == null)
                result = caseNamedElement(serverGroupType);
            if (result == null)
                result = caseLoadable(serverGroupType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.LOADABLE: {
            Loadable loadable = (Loadable) theEObject;
            T result = caseLoadable(loadable);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case DeployPackage.PARAMETER_FACET: {
            ParameterFacet parameterFacet = (ParameterFacet) theEObject;
            T result = caseParameterFacet(parameterFacet);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Config</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Config</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerConfig(ServerConfig object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerType(ServerType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Config Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Config Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerConfigInfo(ServerConfigInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Config Parameter Info</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Config Parameter Info</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConfigParameterInfo(ConfigParameterInfo object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Module</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Module</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModule(Module object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Module</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Module</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerModule(ServerModule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServer(Server object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerContainer(ServerContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Config Parameter</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Config Parameter</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConfigParameter(ConfigParameter object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Repository Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Repository Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepositoryType(RepositoryType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Repository</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Repository</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepository(Repository object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Repository Config</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Repository Config</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepositoryConfig(RepositoryConfig object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Runtime</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Runtime</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRuntime(com.tibco.xpd.deploy.Runtime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Runtime Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Runtime Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRuntimeType(RuntimeType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Runtime Config</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Runtime Config</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRuntimeConfig(RuntimeConfig object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerElement(ServerElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Container Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Container Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseContainerElement(ContainerElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Leaf Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Leaf Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLeafElement(LeafElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Module Container</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Module Container</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseModuleContainer(ModuleContainer object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Element Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Element Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerElementType(ServerElementType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperation(Operation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Element State</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Element State</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerElementState(ServerElementState object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Workspace Module</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Workspace Module</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkspaceModule(WorkspaceModule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unique Id Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUniqueIdElement(UniqueIdElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Group</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Group</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerGroup(ServerGroup object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Server Group Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Server Group Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServerGroupType(ServerGroupType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loadable</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loadable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoadable(Loadable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Facet</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Facet</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterFacet(ParameterFacet object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //DeploySwitch
