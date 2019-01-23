/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import com.tibco.xpd.deploy.model.extension.Connection;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.Server#getServerType <em>Server Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getServerConfig <em>Server Config</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getServerState <em>Server State</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getConnection <em>Connection</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getRepository <em>Repository</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getRuntime <em>Runtime</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getServerElements <em>Server Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getWorkspaceModules <em>Workspace Modules</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Server#getServerGroup <em>Server Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServer()
 * @model
 * @generated
 */
public interface Server extends UniqueIdElement, NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Server Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Type</em>' reference.
     * @see #setServerType(ServerType)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_ServerType()
     * @model
     * @generated
     */
    ServerType getServerType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getServerType <em>Server Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Type</em>' reference.
     * @see #getServerType()
     * @generated
     */
    void setServerType(ServerType value);

    /**
     * Returns the value of the '<em><b>Server Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Config</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Config</em>' containment reference.
     * @see #setServerConfig(ServerConfig)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_ServerConfig()
     * @model containment="true"
     * @generated
     */
    ServerConfig getServerConfig();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getServerConfig <em>Server Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Config</em>' containment reference.
     * @see #getServerConfig()
     * @generated
     */
    void setServerConfig(ServerConfig value);

    /**
     * Returns the value of the '<em><b>Server State</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.deploy.ServerState}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server State</em>' attribute.
     * @see com.tibco.xpd.deploy.ServerState
     * @see #setServerState(ServerState)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_ServerState()
     * @model transient="true"
     * @generated
     */
    ServerState getServerState();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getServerState <em>Server State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server State</em>' attribute.
     * @see com.tibco.xpd.deploy.ServerState
     * @see #getServerState()
     * @generated
     */
    void setServerState(ServerState value);

    /**
     * Returns the value of the '<em><b>Connection</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection</em>' attribute.
     * @see #setConnection(Connection)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_Connection()
     * @model dataType="com.tibco.xpd.deploy.EConnection" transient="true" derived="true"
     * @generated
     */
    Connection getConnection();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getConnection <em>Connection</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connection</em>' attribute.
     * @see #getConnection()
     * @generated
     */
    void setConnection(Connection value);

    /**
     * Returns the value of the '<em><b>Repository</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository</em>' containment reference.
     * @see #setRepository(Repository)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_Repository()
     * @model containment="true"
     * @generated
     */
    Repository getRepository();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getRepository <em>Repository</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repository</em>' containment reference.
     * @see #getRepository()
     * @generated
     */
    void setRepository(Repository value);

    /**
     * Returns the value of the '<em><b>Runtime</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtime</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime</em>' reference.
     * @see #setRuntime(com.tibco.xpd.deploy.Runtime)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_Runtime()
     * @model
     * @generated
     */
    com.tibco.xpd.deploy.Runtime getRuntime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getRuntime <em>Runtime</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Runtime</em>' reference.
     * @see #getRuntime()
     * @generated
     */
    void setRuntime(com.tibco.xpd.deploy.Runtime value);

    /**
     * Returns the value of the '<em><b>Server Elements</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerElement}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Elements</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Elements</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_ServerElements()
     * @model containment="true" transient="true" derived="true"
     * @generated
     */
    EList<ServerElement> getServerElements();

    /**
     * Returns the value of the '<em><b>Workspace Modules</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.WorkspaceModule}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Workspace Modules</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Workspace Modules</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_WorkspaceModules()
     * @model containment="true"
     * @generated
     */
    EList<WorkspaceModule> getWorkspaceModules();

    /**
     * Returns the value of the '<em><b>Server Group</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.deploy.ServerGroup#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Group</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Group</em>' reference.
     * @see #setServerGroup(ServerGroup)
     * @see com.tibco.xpd.deploy.DeployPackage#getServer_ServerGroup()
     * @see com.tibco.xpd.deploy.ServerGroup#getMembers
     * @model opposite="members"
     * @generated
     */
    ServerGroup getServerGroup();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Server#getServerGroup <em>Server Group</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Group</em>' reference.
     * @see #getServerGroup()
     * @generated
     */
    void setServerGroup(ServerGroup value);

} // Server
