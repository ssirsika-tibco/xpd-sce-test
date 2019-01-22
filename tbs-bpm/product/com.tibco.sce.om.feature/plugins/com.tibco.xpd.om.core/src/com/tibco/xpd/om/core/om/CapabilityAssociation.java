/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.CapabilityAssociation#getCapability <em>Capability</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getCapabilityAssociation()
 * @model
 * @generated
 */
public interface CapabilityAssociation extends QualifiedAssociation {
    /**
     * Returns the value of the '<em><b>Capability</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capability</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capability</em>' reference.
     * @see #setCapability(Capability)
     * @see com.tibco.xpd.om.core.om.OMPackage#getCapabilityAssociation_Capability()
     * @model required="true"
     * @generated
     */
    Capability getCapability();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.CapabilityAssociation#getCapability <em>Capability</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Capability</em>' reference.
     * @see #getCapability()
     * @generated
     */
    void setCapability(Capability value);

} // CapabilityAssociation
