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
 * A representation of the model object '<em><b>Locatable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Locatable#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getLocatable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Locatable extends EObject {
    /**
     * Returns the value of the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' reference.
     * @see #setLocation(Location)
     * @see com.tibco.xpd.om.core.om.OMPackage#getLocatable_Location()
     * @model
     * @generated
     */
    Location getLocation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Locatable#getLocation <em>Location</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' reference.
     * @see #getLocation()
     * @generated
     */
    void setLocation(Location value);

} // Locatable
