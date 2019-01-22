/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Org Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.DynamicOrgUnit#getDynamicOrganization <em>Dynamic Organization</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getDynamicOrgUnit()
 * @model
 * @generated
 */
public interface DynamicOrgUnit extends OrgUnit {
    /**
     * Returns the value of the '<em><b>Dynamic Organization</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Organization</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic Organization</em>' reference.
     * @see #setDynamicOrganization(DynamicOrgReference)
     * @see com.tibco.xpd.om.core.om.OMPackage#getDynamicOrgUnit_DynamicOrganization()
     * @model
     * @generated
     */
    DynamicOrgReference getDynamicOrganization();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.DynamicOrgUnit#getDynamicOrganization <em>Dynamic Organization</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dynamic Organization</em>' reference.
     * @see #getDynamicOrganization()
     * @generated
     */
    void setDynamicOrganization(DynamicOrgReference value);

} // DynamicOrgUnit
