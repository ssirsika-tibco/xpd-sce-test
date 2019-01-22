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
 * A representation of the model object '<em><b>Associable With Resources</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.AssociableWithResources#getResourceAssociation <em>Resource Association</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAssociableWithResources()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AssociableWithResources extends EObject {
    /**
     * Returns the value of the '<em><b>Resource Association</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.ResourceAssociation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Association</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Association</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAssociableWithResources_ResourceAssociation()
     * @model containment="true"
     * @generated
     */
    EList<ResourceAssociation> getResourceAssociation();

} // AssociableWithResources
