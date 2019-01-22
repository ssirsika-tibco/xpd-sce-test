/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Runtime</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.Runtime#getRuntimeType <em>Runtime Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Runtime#getRuntimeConfig <em>Runtime Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getRuntime()
 * @model
 * @generated
 */
public interface Runtime extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

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
     * @see com.tibco.xpd.deploy.DeployPackage#getRuntime_RuntimeType()
     * @model
     * @generated
     */
    RuntimeType getRuntimeType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Runtime#getRuntimeType <em>Runtime Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Runtime Type</em>' reference.
     * @see #getRuntimeType()
     * @generated
     */
    void setRuntimeType(RuntimeType value);

    /**
     * Returns the value of the '<em><b>Runtime Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtime Config</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime Config</em>' containment reference.
     * @see #setRuntimeConfig(RuntimeConfig)
     * @see com.tibco.xpd.deploy.DeployPackage#getRuntime_RuntimeConfig()
     * @model containment="true"
     * @generated
     */
    RuntimeConfig getRuntimeConfig();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Runtime#getRuntimeConfig <em>Runtime Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Runtime Config</em>' containment reference.
     * @see #getRuntimeConfig()
     * @generated
     */
    void setRuntimeConfig(RuntimeConfig value);

} // Runtime
