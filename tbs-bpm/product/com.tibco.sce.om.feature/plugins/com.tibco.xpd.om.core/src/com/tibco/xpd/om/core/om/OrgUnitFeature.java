/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Unit Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getFeatureType <em>Feature Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getContextRelationshipType <em>Context Relationship Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitFeature()
 * @model
 * @generated
 */
public interface OrgUnitFeature extends MultipleFeature {
    /**
     * Returns the value of the '<em><b>Feature Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Feature Type</em>' reference.
     * @see #setFeatureType(OrgUnitType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitFeature_FeatureType()
     * @model required="true"
     * @generated
     */
    OrgUnitType getFeatureType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getFeatureType <em>Feature Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature Type</em>' reference.
     * @see #getFeatureType()
     * @generated
     */
    void setFeatureType(OrgUnitType value);

    /**
     * Returns the value of the '<em><b>Context Relationship Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Context Relationship Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Context Relationship Type</em>' reference.
     * @see #setContextRelationshipType(OrgUnitRelationshipType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitFeature_ContextRelationshipType()
     * @model
     * @generated
     */
    OrgUnitRelationshipType getContextRelationshipType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getContextRelationshipType <em>Context Relationship Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Context Relationship Type</em>' reference.
     * @see #getContextRelationshipType()
     * @generated
     */
    void setContextRelationshipType(OrgUnitRelationshipType value);

} // OrgUnitFeature
