/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Capability</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Capability#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getCapability()
 * @model
 * @generated
 */
public interface Capability extends QualifiedOrgElement {

    /**
     * Returns the value of the '<em><b>Category</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.CapabilityCategory#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Category</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Category</em>' reference.
     * @see #setCategory(CapabilityCategory)
     * @see com.tibco.xpd.om.core.om.OMPackage#getCapability_Category()
     * @see com.tibco.xpd.om.core.om.CapabilityCategory#getMembers
     * @model opposite="members"
     * @generated
     */
    CapabilityCategory getCategory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Capability#getCategory <em>Category</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Category</em>' reference.
     * @see #getCategory()
     * @generated
     */
    void setCategory(CapabilityCategory value);

} // Capability
