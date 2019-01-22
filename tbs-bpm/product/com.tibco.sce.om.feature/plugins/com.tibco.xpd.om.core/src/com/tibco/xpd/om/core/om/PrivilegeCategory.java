/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Privilege Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.PrivilegeCategory#getSubCategories <em>Sub Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.PrivilegeCategory#getMembers <em>Members</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilegeCategory()
 * @model
 * @generated
 */
public interface PrivilegeCategory extends Namespace {
    /**
     * Returns the value of the '<em><b>Sub Categories</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.PrivilegeCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sub Categories</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sub Categories</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilegeCategory_SubCategories()
     * @model containment="true"
     * @generated
     */
    EList<PrivilegeCategory> getSubCategories();

    /**
     * Returns the value of the '<em><b>Members</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Privilege}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.Privilege#getCategory <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Members</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Members</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilegeCategory_Members()
     * @see com.tibco.xpd.om.core.om.Privilege#getCategory
     * @model opposite="category"
     * @generated
     */
    EList<Privilege> getMembers();

} // PrivilegeCategory
