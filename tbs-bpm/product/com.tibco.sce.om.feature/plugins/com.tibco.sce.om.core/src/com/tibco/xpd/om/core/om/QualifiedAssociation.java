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
 * A representation of the model object '<em><b>Qualified Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.QualifiedAssociation#getQualifierValue <em>Qualifier Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getQualifiedAssociation()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface QualifiedAssociation extends EObject {
    /**
     * Returns the value of the '<em><b>Qualifier Value</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qualifier Value</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Qualifier Value</em>' containment reference.
     * @see #setQualifierValue(AttributeValue)
     * @see com.tibco.xpd.om.core.om.OMPackage#getQualifiedAssociation_QualifierValue()
     * @model containment="true"
     * @generated
     */
    AttributeValue getQualifierValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.QualifiedAssociation#getQualifierValue <em>Qualifier Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier Value</em>' containment reference.
     * @see #getQualifierValue()
     * @generated
     */
    void setQualifierValue(AttributeValue value);

} // QualifiedAssociation
