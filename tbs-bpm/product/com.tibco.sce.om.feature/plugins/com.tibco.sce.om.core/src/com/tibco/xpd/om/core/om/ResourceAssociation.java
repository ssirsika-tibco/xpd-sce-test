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
 * A representation of the model object '<em><b>Resource Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.ResourceAssociation#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getResourceAssociation()
 * @model
 * @generated
 */
public interface ResourceAssociation extends EObject {
    /**
     * Returns the value of the '<em><b>Resource</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource</em>' reference.
     * @see #setResource(Resource)
     * @see com.tibco.xpd.om.core.om.OMPackage#getResourceAssociation_Resource()
     * @model required="true"
     * @generated
     */
    Resource getResource();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.ResourceAssociation#getResource <em>Resource</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource</em>' reference.
     * @see #getResource()
     * @generated
     */
    void setResource(Resource value);

} // ResourceAssociation
