/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Qualified Org Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.QualifiedOrgElement#getQualifierAttribute <em>Qualifier Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getQualifiedOrgElement()
 * @model abstract="true"
 * @generated
 */
public interface QualifiedOrgElement extends OrgElement {
    /**
     * Returns the value of the '<em><b>Qualifier Attribute</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qualifier Attribute</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Qualifier Attribute</em>' containment reference.
     * @see #setQualifierAttribute(Attribute)
     * @see com.tibco.xpd.om.core.om.OMPackage#getQualifiedOrgElement_QualifierAttribute()
     * @model containment="true"
     * @generated
     */
    Attribute getQualifierAttribute();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.QualifiedOrgElement#getQualifierAttribute <em>Qualifier Attribute</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier Attribute</em>' containment reference.
     * @see #getQualifierAttribute()
     * @generated
     */
    void setQualifierAttribute(Attribute value);

} // QualifiedOrgElement
