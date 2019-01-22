/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Org Unit Relationship</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getRelationshipType <em>Relationship Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#isIsHierarchical <em>Is Hierarchical</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitRelationship()
 * @model
 * @generated
 */
public interface OrgUnitRelationship extends OrgTypedElement {
    /**
     * Returns the value of the '<em><b>From</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.OrgUnit#getOutgoingRelationships <em>Outgoing Relationships</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>From</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>From</em>' reference.
     * @see #setFrom(OrgUnit)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitRelationship_From()
     * @see com.tibco.xpd.om.core.om.OrgUnit#getOutgoingRelationships
     * @model opposite="outgoingRelationships" required="true"
     * @generated
     */
    OrgUnit getFrom();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom <em>From</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>From</em>' reference.
     * @see #getFrom()
     * @generated
     */
    void setFrom(OrgUnit value);

    /**
     * Returns the value of the '<em><b>To</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.OrgUnit#getIncomingRelationships <em>Incoming Relationships</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To</em>' reference.
     * @see #setTo(OrgUnit)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitRelationship_To()
     * @see com.tibco.xpd.om.core.om.OrgUnit#getIncomingRelationships
     * @model opposite="incomingRelationships" required="true"
     * @generated
     */
    OrgUnit getTo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo <em>To</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To</em>' reference.
     * @see #getTo()
     * @generated
     */
    void setTo(OrgUnit value);

    /**
     * Returns the value of the '<em><b>Relationship Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Relationship Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Relationship Type</em>' reference.
     * @see #setRelationshipType(OrgUnitRelationshipType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitRelationship_RelationshipType()
     * @model
     * @generated
     */
    OrgUnitRelationshipType getRelationshipType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getRelationshipType <em>Relationship Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Relationship Type</em>' reference.
     * @see #getRelationshipType()
     * @generated
     */
    void setRelationshipType(OrgUnitRelationshipType value);

    /**
     * Returns the value of the '<em><b>Is Hierarchical</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Hierarchical</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Hierarchical</em>' attribute.
     * @see #setIsHierarchical(boolean)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgUnitRelationship_IsHierarchical()
     * @model default="false"
     * @generated
     */
    boolean isIsHierarchical();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#isIsHierarchical <em>Is Hierarchical</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Hierarchical</em>' attribute.
     * @see #isIsHierarchical()
     * @generated
     */
    void setIsHierarchical(boolean value);

} // OrgUnitRelationship
