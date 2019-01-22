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
 * A representation of the model object '<em><b>Org Meta Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getLocationTypes <em>Location Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitRelationshipTypes <em>Org Unit Relationship Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrganizationTypes <em>Organization Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitTypes <em>Org Unit Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getPositionTypes <em>Position Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#getResourceTypes <em>Resource Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.OrgMetaModel#isEmbedded <em>Embedded</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel()
 * @model
 * @generated
 */
public interface OrgMetaModel extends BaseOrgModel {
    /**
     * Returns the value of the '<em><b>Location Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.LocationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_LocationTypes()
     * @model containment="true"
     * @generated
     */
    EList<LocationType> getLocationTypes();

    /**
     * Returns the value of the '<em><b>Org Unit Relationship Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitRelationshipType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Relationship Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Relationship Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_OrgUnitRelationshipTypes()
     * @model containment="true"
     * @generated
     */
    EList<OrgUnitRelationshipType> getOrgUnitRelationshipTypes();

    /**
     * Returns the value of the '<em><b>Organization Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrganizationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Organization Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Organization Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_OrganizationTypes()
     * @model containment="true"
     * @generated
     */
    EList<OrganizationType> getOrganizationTypes();

    /**
     * Returns the value of the '<em><b>Org Unit Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.OrgUnitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Unit Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Unit Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_OrgUnitTypes()
     * @model containment="true"
     * @generated
     */
    EList<OrgUnitType> getOrgUnitTypes();

    /**
     * Returns the value of the '<em><b>Position Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.PositionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Position Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Position Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_PositionTypes()
     * @model containment="true"
     * @generated
     */
    EList<PositionType> getPositionTypes();

    /**
     * Returns the value of the '<em><b>Resource Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.ResourceType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Types</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_ResourceTypes()
     * @model containment="true"
     * @generated
     */
    EList<ResourceType> getResourceTypes();

    /**
     * Returns the value of the '<em><b>Embedded</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Embedded</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Embedded</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getOrgMetaModel_Embedded()
     * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    boolean isEmbedded();

} // OrgMetaModel
