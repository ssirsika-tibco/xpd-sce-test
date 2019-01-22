/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerElement#getServerElementType <em>Server Element Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerElement#getServerElementState <em>Server Element State</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerElement#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ServerElement extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Server Element Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Element Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Element Type</em>' reference.
     * @see #setServerElementType(ServerElementType)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElement_ServerElementType()
     * @model transient="true"
     * @generated
     */
    ServerElementType getServerElementType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerElement#getServerElementType <em>Server Element Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Element Type</em>' reference.
     * @see #getServerElementType()
     * @generated
     */
    void setServerElementType(ServerElementType value);

    /**
     * Returns the value of the '<em><b>Server Element State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Element State</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Element State</em>' reference.
     * @see #setServerElementState(ServerElementState)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElement_ServerElementState()
     * @model
     * @generated
     */
    ServerElementState getServerElementState();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerElement#getServerElementState <em>Server Element State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Element State</em>' reference.
     * @see #getServerElementState()
     * @generated
     */
    void setServerElementState(ServerElementState value);

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ConfigParameter}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElement_Parameters()
     * @model containment="true"
     * @generated
     */
    EList<ConfigParameter> getParameters();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    ConfigParameter getConfigParameter(String key);

} // ServerElement
