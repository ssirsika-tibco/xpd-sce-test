/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Typed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgTypedElement#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgTypedElement#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgTypedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface OrgTypedElement extends OrgElement {
    /**
     * Returns the value of the '<em><b>Attribute Values</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.AttributeValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Values</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Values</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgTypedElement_AttributeValues()
     * @model containment="true"
     * @generated
     */
    EList<AttributeValue> getAttributeValues();

    /**
     * Returns the value of the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' reference.
     * @see #setType(OrgElementType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgTypedElement_Type()
     * @model resolveProxies="false" transient="true" volatile="true" derived="true"
     * @generated
     */
    OrgElementType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgTypedElement#getType <em>Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' reference.
     * @see #getType()
     * @generated
     */
    void setType(OrgElementType value);

} // OrgTypedElement
