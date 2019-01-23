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
 * A representation of the model object '<em><b>Dynamic Org Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getDynamicOrgReference()
 * @model
 * @generated
 */
public interface DynamicOrgReference extends EObject {
    /**
     * Returns the value of the '<em><b>From</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>From</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>From</em>' reference.
     * @see #setFrom(DynamicOrgUnit)
     * @see com.tibco.xpd.om.core.om.OMPackage#getDynamicOrgReference_From()
     * @model
     * @generated
     */
    DynamicOrgUnit getFrom();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getFrom <em>From</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>From</em>' reference.
     * @see #getFrom()
     * @generated
     */
    void setFrom(DynamicOrgUnit value);

    /**
     * Returns the value of the '<em><b>To</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To</em>' reference.
     * @see #setTo(Organization)
     * @see com.tibco.xpd.om.core.om.OMPackage#getDynamicOrgReference_To()
     * @model required="true"
     * @generated
     */
    Organization getTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getTo <em>To</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To</em>' reference.
     * @see #getTo()
     * @generated
     */
    void setTo(Organization value);

} // DynamicOrgReference
