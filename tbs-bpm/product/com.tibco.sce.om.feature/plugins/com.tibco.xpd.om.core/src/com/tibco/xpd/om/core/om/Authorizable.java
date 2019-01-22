/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authorizable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Authorizable#getSystemActions <em>System Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAuthorizable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Authorizable extends AssociableWithPrivileges {
    /**
     * Returns the value of the '<em><b>System Actions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.SystemAction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>System Actions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>System Actions</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAuthorizable_SystemActions()
     * @model containment="true"
     * @generated
     */
    EList<SystemAction> getSystemActions();

} // Authorizable
