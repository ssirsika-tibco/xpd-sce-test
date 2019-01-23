/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Privilege</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Privilege#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilege()
 * @model
 * @generated
 */
public interface Privilege extends QualifiedOrgElement {

    /**
     * Returns the value of the '<em><b>Category</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.PrivilegeCategory#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Category</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Category</em>' reference.
     * @see #setCategory(PrivilegeCategory)
     * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilege_Category()
     * @see com.tibco.xpd.om.core.om.PrivilegeCategory#getMembers
     * @model opposite="members"
     * @generated
     */
    PrivilegeCategory getCategory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Privilege#getCategory <em>Category</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Category</em>' reference.
     * @see #getCategory()
     * @generated
     */
    void setCategory(PrivilegeCategory value);
} // Privilege
