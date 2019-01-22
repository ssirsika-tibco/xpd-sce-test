/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import com.tibco.xpd.deploy.model.extension.DeploymentException;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.Operation#getToState <em>To State</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getOperation()
 * @model abstract="true"
 * @generated
 */
public interface Operation extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>To State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To State</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To State</em>' reference.
     * @see #setToState(ServerElementState)
     * @see com.tibco.xpd.deploy.DeployPackage#getOperation_ToState()
     * @model
     * @generated
     */
    ServerElementState getToState();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Operation#getToState <em>To State</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To State</em>' reference.
     * @see #getToState()
     * @generated
     */
    void setToState(ServerElementState value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    boolean canExecute(ServerElement serverElement);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model exceptions="com.tibco.xpd.deploy.EDeploymentException"
     * @generated
     */
    Object execute(ServerElement serverElement) throws DeploymentException;

} // Operation
