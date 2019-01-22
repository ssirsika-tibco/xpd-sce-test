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
 * A representation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Organization#getUnits <em>Units</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Organization#getOrganizationType <em>Organization Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Organization#getOrgUnitRelationships <em>Org Unit Relationships</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Organization#isDynamic <em>Dynamic</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Organization#getDynamicOrgIdentifiers <em>Dynamic Org Identifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization()
 * @model
 * @generated
 */
public interface Organization extends OrgTypedElement, Allocable, Locatable,
        OrgNode {
    /**
     * Returns the value of the '<em><b>Units</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnit}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Units</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Units</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization_Units()
     * @model containment="true"
     * @generated
     */
    EList<OrgUnit> getUnits();

    /**
     * Returns the value of the '<em><b>Organization Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Organization Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Organization Type</em>' reference.
     * @see #setOrganizationType(OrganizationType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization_OrganizationType()
     * @model
     * @generated
     */
    OrganizationType getOrganizationType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Organization#getOrganizationType <em>Organization Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Organization Type</em>' reference.
     * @see #getOrganizationType()
     * @generated
     */
    void setOrganizationType(OrganizationType value);

    /**
     * Returns the value of the '<em><b>Org Unit Relationships</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitRelationship}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Relationships</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Relationships</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization_OrgUnitRelationships()
     * @model containment="true"
     * @generated
     */
    EList<OrgUnitRelationship> getOrgUnitRelationships();

    /**
     * Returns the value of the '<em><b>Dynamic</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic</em>' attribute.
     * @see #setDynamic(boolean)
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization_Dynamic()
     * @model
     * @generated
     */
    boolean isDynamic();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Organization#isDynamic <em>Dynamic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dynamic</em>' attribute.
     * @see #isDynamic()
     * @generated
     */
    void setDynamic(boolean value);

    /**
     * Returns the value of the '<em><b>Dynamic Org Identifiers</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.DynamicOrgIdentifier}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Org Identifiers</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic Org Identifiers</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrganization_DynamicOrgIdentifiers()
     * @model containment="true"
     * @generated
     */
    EList<DynamicOrgIdentifier> getDynamicOrgIdentifiers();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<OrgUnit> getSubUnits();

} // Organization
