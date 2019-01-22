/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.core.om.OMPackage
 * @generated
 */
public interface OMFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OMFactory eINSTANCE = com.tibco.xpd.om.core.om.impl.OMFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Org Unit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit</em>'.
     * @generated
     */
    OrgUnit createOrgUnit();

    /**
     * Returns a new object of class '<em>Dynamic Org Unit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Org Unit</em>'.
     * @generated
     */
    DynamicOrgUnit createDynamicOrgUnit();

    /**
     * Returns a new object of class '<em>Dynamic Org Reference</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Org Reference</em>'.
     * @generated
     */
    DynamicOrgReference createDynamicOrgReference();

    /**
     * Returns a new object of class '<em>Position</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position</em>'.
     * @generated
     */
    Position createPosition();

    /**
     * Returns a new object of class '<em>Location</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Location</em>'.
     * @generated
     */
    Location createLocation();

    /**
     * Returns a new object of class '<em>Capability</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability</em>'.
     * @generated
     */
    Capability createCapability();

    /**
     * Returns a new object of class '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Group</em>'.
     * @generated
     */
    Group createGroup();

    /**
     * Returns a new object of class '<em>Organization</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Organization</em>'.
     * @generated
     */
    Organization createOrganization();

    /**
     * Returns a new object of class '<em>Dynamic Org Identifier</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Dynamic Org Identifier</em>'.
     * @generated
     */
    DynamicOrgIdentifier createDynamicOrgIdentifier();

    /**
     * Returns a new object of class '<em>Attribute Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute Value</em>'.
     * @generated
     */
    AttributeValue createAttributeValue();

    /**
     * Returns a new object of class '<em>Attribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute</em>'.
     * @generated
     */
    Attribute createAttribute();

    /**
     * Returns a new object of class '<em>Organization Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Organization Type</em>'.
     * @generated
     */
    OrganizationType createOrganizationType();

    /**
     * Returns a new object of class '<em>Org Unit Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Type</em>'.
     * @generated
     */
    OrgUnitType createOrgUnitType();

    /**
     * Returns a new object of class '<em>Position Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position Type</em>'.
     * @generated
     */
    PositionType createPositionType();

    /**
     * Returns a new object of class '<em>Org Unit Feature</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Feature</em>'.
     * @generated
     */
    OrgUnitFeature createOrgUnitFeature();

    /**
     * Returns a new object of class '<em>Position Feature</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position Feature</em>'.
     * @generated
     */
    PositionFeature createPositionFeature();

    /**
     * Returns a new object of class '<em>Org Unit Relationship</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Relationship</em>'.
     * @generated
     */
    OrgUnitRelationship createOrgUnitRelationship();

    /**
     * Returns a new object of class '<em>Org Unit Relationship Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Relationship Type</em>'.
     * @generated
     */
    OrgUnitRelationshipType createOrgUnitRelationshipType();

    /**
     * Returns a new object of class '<em>Capability Category</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability Category</em>'.
     * @generated
     */
    CapabilityCategory createCapabilityCategory();

    /**
     * Returns a new object of class '<em>Location Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Location Type</em>'.
     * @generated
     */
    LocationType createLocationType();

    /**
     * Returns a new object of class '<em>Org Model</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Model</em>'.
     * @generated
     */
    OrgModel createOrgModel();

    /**
     * Returns a new object of class '<em>Enum Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enum Value</em>'.
     * @generated
     */
    EnumValue createEnumValue();

    /**
     * Returns a new object of class '<em>Privilege</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege</em>'.
     * @generated
     */
    Privilege createPrivilege();

    /**
     * Returns a new object of class '<em>Capability Association</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability Association</em>'.
     * @generated
     */
    CapabilityAssociation createCapabilityAssociation();

    /**
     * Returns a new object of class '<em>Org Meta Model</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Meta Model</em>'.
     * @generated
     */
    OrgMetaModel createOrgMetaModel();

    /**
     * Returns a new object of class '<em>Privilege Association</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege Association</em>'.
     * @generated
     */
    PrivilegeAssociation createPrivilegeAssociation();

    /**
     * Returns a new object of class '<em>Privilege Category</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege Category</em>'.
     * @generated
     */
    PrivilegeCategory createPrivilegeCategory();

    /**
     * Returns a new object of class '<em>Resource Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Resource Type</em>'.
     * @generated
     */
    ResourceType createResourceType();

    /**
     * Returns a new object of class '<em>Resource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Resource</em>'.
     * @generated
     */
    Resource createResource();

    /**
     * Returns a new object of class '<em>Resource Association</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Resource Association</em>'.
     * @generated
     */
    ResourceAssociation createResourceAssociation();

    /**
     * Returns a new object of class '<em>Org Query</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Query</em>'.
     * @generated
     */
    OrgQuery createOrgQuery();

    /**
     * Returns a new object of class '<em>System Action</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>System Action</em>'.
     * @generated
     */
    SystemAction createSystemAction();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    OMPackage getOMPackage();

} //OMFactory
