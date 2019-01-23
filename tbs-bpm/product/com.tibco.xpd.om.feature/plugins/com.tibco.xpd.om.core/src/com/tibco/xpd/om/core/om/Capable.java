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
 * A representation of the model object '<em><b>Capable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Capable#getCapabilitiesAssociations <em>Capabilities Associations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getCapable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Capable extends EObject {
    /**
     * Returns the value of the '<em><b>Capabilities Associations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.CapabilityAssociation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Capabilities Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Capabilities Associations</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getCapable_CapabilitiesAssociations()
     * @model containment="true"
     * @generated
     */
    EList<CapabilityAssociation> getCapabilitiesAssociations();

} // Capable
