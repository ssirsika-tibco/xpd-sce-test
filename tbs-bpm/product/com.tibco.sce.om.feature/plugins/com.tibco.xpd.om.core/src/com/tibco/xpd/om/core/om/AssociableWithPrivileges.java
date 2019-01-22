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
 * A representation of the model object '<em><b>Associable With Privileges</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAssociableWithPrivileges()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AssociableWithPrivileges extends EObject {
    /**
     * Returns the value of the '<em><b>Privilege Associations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.PrivilegeAssociation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privilege Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privilege Associations</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAssociableWithPrivileges_PrivilegeAssociations()
     * @model containment="true"
     * @generated
     */
    EList<PrivilegeAssociation> getPrivilegeAssociations();

} // AssociableWithPrivileges
