/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.directory.model.de.DePackage
 * @generated
 */
public interface DeFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DeFactory eINSTANCE = com.tibco.n2.directory.model.de.impl.DeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Attribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute</em>'.
     * @generated
     */
    Attribute createAttribute();

    /**
     * Returns a new object of class '<em>Attribute Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute Type</em>'.
     * @generated
     */
    AttributeType createAttributeType();

    /**
     * Returns a new object of class '<em>Capability</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability</em>'.
     * @generated
     */
    Capability createCapability();

    /**
     * Returns a new object of class '<em>Capability Category</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability Category</em>'.
     * @generated
     */
    CapabilityCategory createCapabilityCategory();

    /**
     * Returns a new object of class '<em>Capability Holding</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Capability Holding</em>'.
     * @generated
     */
    CapabilityHolding createCapabilityHolding();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Feature</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Feature</em>'.
     * @generated
     */
    Feature createFeature();

    /**
     * Returns a new object of class '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Group</em>'.
     * @generated
     */
    Group createGroup();

    /**
     * Returns a new object of class '<em>Group Holding</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Group Holding</em>'.
     * @generated
     */
    GroupHolding createGroupHolding();

    /**
     * Returns a new object of class '<em>Location</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Location</em>'.
     * @generated
     */
    Location createLocation();

    /**
     * Returns a new object of class '<em>Location Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Location Type</em>'.
     * @generated
     */
    LocationType createLocationType();

    /**
     * Returns a new object of class '<em>Meta Model</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Meta Model</em>'.
     * @generated
     */
    MetaModel createMetaModel();

    /**
     * Returns a new object of class '<em>Model Org Unit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Model Org Unit</em>'.
     * @generated
     */
    ModelOrgUnit createModelOrgUnit();

    /**
     * Returns a new object of class '<em>Model Template</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Model Template</em>'.
     * @generated
     */
    ModelTemplate createModelTemplate();

    /**
     * Returns a new object of class '<em>Model Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Model Type</em>'.
     * @generated
     */
    ModelType createModelType();

    /**
     * Returns a new object of class '<em>Organization</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Organization</em>'.
     * @generated
     */
    Organization createOrganization();

    /**
     * Returns a new object of class '<em>Organization Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Organization Type</em>'.
     * @generated
     */
    OrganizationType createOrganizationType();

    /**
     * Returns a new object of class '<em>Org Unit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit</em>'.
     * @generated
     */
    OrgUnit createOrgUnit();

    /**
     * Returns a new object of class '<em>Org Unit Base</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Base</em>'.
     * @generated
     */
    OrgUnitBase createOrgUnitBase();

    /**
     * Returns a new object of class '<em>Org Unit Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Org Unit Type</em>'.
     * @generated
     */
    OrgUnitType createOrgUnitType();

    /**
     * Returns a new object of class '<em>Position</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position</em>'.
     * @generated
     */
    Position createPosition();

    /**
     * Returns a new object of class '<em>Position Holding</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position Holding</em>'.
     * @generated
     */
    PositionHolding createPositionHolding();

    /**
     * Returns a new object of class '<em>Position Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Position Type</em>'.
     * @generated
     */
    PositionType createPositionType();

    /**
     * Returns a new object of class '<em>Privilege</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege</em>'.
     * @generated
     */
    Privilege createPrivilege();

    /**
     * Returns a new object of class '<em>Privilege Category</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege Category</em>'.
     * @generated
     */
    PrivilegeCategory createPrivilegeCategory();

    /**
     * Returns a new object of class '<em>Privilege Holding</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Privilege Holding</em>'.
     * @generated
     */
    PrivilegeHolding createPrivilegeHolding();

    /**
     * Returns a new object of class '<em>Qualifier</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Qualifier</em>'.
     * @generated
     */
    Qualifier createQualifier();

    /**
     * Returns a new object of class '<em>Resource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Resource</em>'.
     * @generated
     */
    Resource createResource();

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
    DePackage getDePackage();

} //DeFactory
