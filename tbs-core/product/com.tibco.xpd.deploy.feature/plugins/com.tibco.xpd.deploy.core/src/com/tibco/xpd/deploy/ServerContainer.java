/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getServers <em>Servers</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getRuntimes <em>Runtimes</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getServerTypes <em>Server Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getRepositoryTypes <em>Repository Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getRuntimeTypes <em>Runtime Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getServerGroupTypes <em>Server Group Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerContainer#getServerGroups <em>Server Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer()
 * @model
 * @generated
 */
public interface ServerContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Servers</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.Server}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Servers</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Servers</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_Servers()
     * @model containment="true"
     * @generated
     */
    EList<Server> getServers();

    /**
     * Returns the value of the '<em><b>Runtimes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.Runtime}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtimes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtimes</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_Runtimes()
     * @model containment="true"
     * @generated
     */
    EList<com.tibco.xpd.deploy.Runtime> getRuntimes();

    /**
     * Returns the value of the '<em><b>Server Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Types</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_ServerTypes()
     * @model containment="true"
     * @generated
     */
    EList<ServerType> getServerTypes();

    /**
     * Returns the value of the '<em><b>Repository Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.RepositoryType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository Types</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_RepositoryTypes()
     * @model containment="true"
     * @generated
     */
    EList<RepositoryType> getRepositoryTypes();

    /**
     * Returns the value of the '<em><b>Runtime Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.RuntimeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtime Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime Types</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_RuntimeTypes()
     * @model containment="true"
     * @generated
     */
    EList<RuntimeType> getRuntimeTypes();

    /**
     * Returns the value of the '<em><b>Server Group Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerGroupType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Group Types</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Group Types</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_ServerGroupTypes()
     * @model containment="true"
     * @generated
     */
    EList<ServerGroupType> getServerGroupTypes();

    /**
     * Returns the value of the '<em><b>Server Groups</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerGroup}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Groups</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Groups</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerContainer_ServerGroups()
     * @model containment="true"
     * @generated
     */
    EList<ServerGroup> getServerGroups();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    RepositoryType getRepositoryTypeById(String repositoryTypeId);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    ServerType getServerTypeById(String serverTypeId);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    RuntimeType getRuntimeTypeById(String runtimeTypeId);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    String getDefaultNewRuntimeName(String prefix);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    String getDefaultNewServerName(String prefix);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    boolean isValidNewRuntimeName(String name);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    boolean isValidNewServerName(String name);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    ServerGroupType getServerGroupTypeById(String serverGroupTypeId);

} // ServerContainer
