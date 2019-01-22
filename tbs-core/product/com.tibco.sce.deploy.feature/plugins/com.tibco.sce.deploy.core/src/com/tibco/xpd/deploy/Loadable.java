/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loadable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.Loadable#isValid <em>Valid</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Loadable#getLastExtensionId <em>Last Extension Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getLoadable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Loadable extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Valid</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Valid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Valid</em>' attribute.
     * @see #setValid(boolean)
     * @see com.tibco.xpd.deploy.DeployPackage#getLoadable_Valid()
     * @model default="false" required="true" transient="true"
     * @generated
     */
    boolean isValid();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Loadable#isValid <em>Valid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Valid</em>' attribute.
     * @see #isValid()
     * @generated
     */
    void setValid(boolean value);

    /**
     * Returns the value of the '<em><b>Last Extension Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Last Extension Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Last Extension Id</em>' attribute.
     * @see #setLastExtensionId(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getLoadable_LastExtensionId()
     * @model
     * @generated
     */
    String getLastExtensionId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Loadable#getLastExtensionId <em>Last Extension Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Extension Id</em>' attribute.
     * @see #getLastExtensionId()
     * @generated
     */
    void setLastExtensionId(String value);

} // Loadable
