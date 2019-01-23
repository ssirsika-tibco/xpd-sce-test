/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Privilege Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.PrivilegeAssociation#getPrivilege <em>Privilege</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilegeAssociation()
 * @model
 * @generated
 */
public interface PrivilegeAssociation extends QualifiedAssociation {
    /**
     * Returns the value of the '<em><b>Privilege</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Privilege</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Privilege</em>' reference.
     * @see #setPrivilege(Privilege)
     * @see com.tibco.xpd.om.core.om.OMPackage#getPrivilegeAssociation_Privilege()
     * @model required="true"
     * @generated
     */
    Privilege getPrivilege();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.PrivilegeAssociation#getPrivilege <em>Privilege</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Privilege</em>' reference.
     * @see #getPrivilege()
     * @generated
     */
    void setPrivilege(Privilege value);

} // PrivilegeAssociation
