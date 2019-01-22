/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import com.tibco.xpd.deploy.model.extension.ConnectionFactory;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#getServerConfigInfo <em>Server Config Info</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#getConnectionFactory <em>Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#getRuntimeType <em>Runtime Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#getSupportedRepositoryTypes <em>Supported Repository Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerType#isSuppressConectivity <em>Suppress Conectivity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerType()
 * @model
 * @generated
 */
public interface ServerType extends NamedElement, Loadable {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_Id()
     * @model id="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Server Config Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Config Info</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Config Info</em>' containment reference.
     * @see #setServerConfigInfo(ServerConfigInfo)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_ServerConfigInfo()
     * @model containment="true" transient="true"
     * @generated
     */
    ServerConfigInfo getServerConfigInfo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerType#getServerConfigInfo <em>Server Config Info</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Config Info</em>' containment reference.
     * @see #getServerConfigInfo()
     * @generated
     */
    void setServerConfigInfo(ServerConfigInfo value);

    /**
     * Returns the value of the '<em><b>Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection Factory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection Factory</em>' attribute.
     * @see #setConnectionFactory(ConnectionFactory)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_ConnectionFactory()
     * @model dataType="com.tibco.xpd.deploy.EConnectionFactory" transient="true" derived="true"
     * @generated
     */
    ConnectionFactory getConnectionFactory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerType#getConnectionFactory <em>Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connection Factory</em>' attribute.
     * @see #getConnectionFactory()
     * @generated
     */
    void setConnectionFactory(ConnectionFactory value);

    /**
     * Returns the value of the '<em><b>Runtime Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtime Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime Type</em>' reference.
     * @see #setRuntimeType(RuntimeType)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_RuntimeType()
     * @model
     * @generated
     */
    RuntimeType getRuntimeType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerType#getRuntimeType <em>Runtime Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Runtime Type</em>' reference.
     * @see #getRuntimeType()
     * @generated
     */
    void setRuntimeType(RuntimeType value);

    /**
     * Returns the value of the '<em><b>Supported Repository Types</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.RepositoryType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Supported Repository Types</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Supported Repository Types</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_SupportedRepositoryTypes()
     * @model
     * @generated
     */
    EList<RepositoryType> getSupportedRepositoryTypes();

    /**
     * Returns the value of the '<em><b>Suppress Conectivity</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Suppress Conectivity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Suppress Conectivity</em>' attribute.
     * @see #setSuppressConectivity(boolean)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerType_SuppressConectivity()
     * @model default="false" required="true"
     * @generated
     */
    boolean isSuppressConectivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerType#isSuppressConectivity <em>Suppress Conectivity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Suppress Conectivity</em>' attribute.
     * @see #isSuppressConectivity()
     * @generated
     */
    void setSuppressConectivity(boolean value);

} // ServerType
