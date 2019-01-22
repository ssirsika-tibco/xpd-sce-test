/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceAssociation;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class OMFactoryImpl extends EFactoryImpl implements OMFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static OMFactory init() {
        try {
            OMFactory theOMFactory =
                    (OMFactory) EPackage.Registry.INSTANCE
                            .getEFactory("http://www.tibco.com/om/1.0"); //$NON-NLS-1$ 
            if (theOMFactory != null) {
                return theOMFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new OMFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public OMFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case OMPackage.ORG_UNIT:
            return createOrgUnit();
        case OMPackage.DYNAMIC_ORG_UNIT:
            return createDynamicOrgUnit();
        case OMPackage.DYNAMIC_ORG_REFERENCE:
            return createDynamicOrgReference();
        case OMPackage.POSITION:
            return createPosition();
        case OMPackage.LOCATION:
            return createLocation();
        case OMPackage.CAPABILITY:
            return createCapability();
        case OMPackage.GROUP:
            return createGroup();
        case OMPackage.ORGANIZATION:
            return createOrganization();
        case OMPackage.DYNAMIC_ORG_IDENTIFIER:
            return createDynamicOrgIdentifier();
        case OMPackage.ATTRIBUTE_VALUE:
            return createAttributeValue();
        case OMPackage.ATTRIBUTE:
            return createAttribute();
        case OMPackage.ORGANIZATION_TYPE:
            return createOrganizationType();
        case OMPackage.ORG_UNIT_TYPE:
            return createOrgUnitType();
        case OMPackage.POSITION_TYPE:
            return createPositionType();
        case OMPackage.ORG_UNIT_FEATURE:
            return createOrgUnitFeature();
        case OMPackage.POSITION_FEATURE:
            return createPositionFeature();
        case OMPackage.ORG_UNIT_RELATIONSHIP:
            return createOrgUnitRelationship();
        case OMPackage.ORG_UNIT_RELATIONSHIP_TYPE:
            return createOrgUnitRelationshipType();
        case OMPackage.CAPABILITY_CATEGORY:
            return createCapabilityCategory();
        case OMPackage.LOCATION_TYPE:
            return createLocationType();
        case OMPackage.ORG_MODEL:
            return createOrgModel();
        case OMPackage.ENUM_VALUE:
            return createEnumValue();
        case OMPackage.PRIVILEGE:
            return createPrivilege();
        case OMPackage.CAPABILITY_ASSOCIATION:
            return createCapabilityAssociation();
        case OMPackage.ORG_META_MODEL:
            return createOrgMetaModel();
        case OMPackage.PRIVILEGE_ASSOCIATION:
            return createPrivilegeAssociation();
        case OMPackage.PRIVILEGE_CATEGORY:
            return createPrivilegeCategory();
        case OMPackage.RESOURCE_TYPE:
            return createResourceType();
        case OMPackage.RESOURCE:
            return createResource();
        case OMPackage.RESOURCE_ASSOCIATION:
            return createResourceAssociation();
        case OMPackage.ORG_QUERY:
            return createOrgQuery();
        case OMPackage.SYSTEM_ACTION:
            return createSystemAction();
        default:
            throw new IllegalArgumentException(
                    "The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case OMPackage.ATTRIBUTE_TYPE:
            return createAttributeTypeFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case OMPackage.ATTRIBUTE_TYPE:
            return convertAttributeTypeToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public OrgUnit createOrgUnit() {
        OrgUnitImpl orgUnit = new OrgUnitImpl();
        orgUnit.setAllocationMethod("NEXT"); //$NON-NLS-1$
        return orgUnit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public DynamicOrgUnit createDynamicOrgUnit() {
        DynamicOrgUnitImpl dynamicOrgUnit = new DynamicOrgUnitImpl();
        return dynamicOrgUnit;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public DynamicOrgReference createDynamicOrgReference() {
        DynamicOrgReferenceImpl dynamicOrgReference =
                new DynamicOrgReferenceImpl();
        return dynamicOrgReference;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Position createPosition() {
        PositionImpl position = new PositionImpl();
        return position;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Location createLocation() {
        LocationImpl location = new LocationImpl();
        return location;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Capability createCapability() {
        CapabilityImpl capability = new CapabilityImpl();
        return capability;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Group createGroup() {
        GroupImpl group = new GroupImpl();
        return group;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Organization createOrganization() {
        OrganizationImpl organization = new OrganizationImpl();
        organization.setAllocationMethod("NEXT"); //$NON-NLS-1$        
        return organization;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public DynamicOrgIdentifier createDynamicOrgIdentifier() {
        DynamicOrgIdentifierImpl dynamicOrgIdentifier =
                new DynamicOrgIdentifierImpl();
        return dynamicOrgIdentifier;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public AttributeValue createAttributeValue() {
        AttributeValueImpl attributeValue = new AttributeValueImpl();
        return attributeValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Attribute createAttribute() {
        AttributeImpl attribute = new AttributeImpl();
        return attribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrganizationType createOrganizationType() {
        OrganizationTypeImpl organizationType = new OrganizationTypeImpl();
        return organizationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgUnitType createOrgUnitType() {
        OrgUnitTypeImpl orgUnitType = new OrgUnitTypeImpl();
        return orgUnitType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PositionType createPositionType() {
        PositionTypeImpl positionType = new PositionTypeImpl();
        return positionType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgUnitFeature createOrgUnitFeature() {
        OrgUnitFeatureImpl orgUnitFeature = new OrgUnitFeatureImpl();
        return orgUnitFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PositionFeature createPositionFeature() {
        PositionFeatureImpl positionFeature = new PositionFeatureImpl();
        return positionFeature;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgUnitRelationship createOrgUnitRelationship() {
        OrgUnitRelationshipImpl orgUnitRelationship =
                new OrgUnitRelationshipImpl();
        return orgUnitRelationship;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgUnitRelationshipType createOrgUnitRelationshipType() {
        OrgUnitRelationshipTypeImpl orgUnitRelationshipType =
                new OrgUnitRelationshipTypeImpl();
        return orgUnitRelationshipType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CapabilityCategory createCapabilityCategory() {
        CapabilityCategoryImpl capabilityCategory =
                new CapabilityCategoryImpl();
        return capabilityCategory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public LocationType createLocationType() {
        LocationTypeImpl locationType = new LocationTypeImpl();
        return locationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public OrgModel createOrgModel() {
        OrgModelImpl orgModel = new OrgModelImpl();

        // create embedded schema
        OrgMetaModel metaModel = OMFactory.eINSTANCE.createOrgMetaModel();
        orgModel.setEmbeddedMetamodel(metaModel);

        return orgModel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EnumValue createEnumValue() {
        EnumValueImpl enumValue = new EnumValueImpl();
        return enumValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Privilege createPrivilege() {
        PrivilegeImpl privilege = new PrivilegeImpl();
        return privilege;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public CapabilityAssociation createCapabilityAssociation() {
        CapabilityAssociationImpl capabilityAssociation =
                new CapabilityAssociationImpl();
        return capabilityAssociation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgMetaModel createOrgMetaModel() {
        OrgMetaModelImpl orgMetaModel = new OrgMetaModelImpl();
        return orgMetaModel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PrivilegeAssociation createPrivilegeAssociation() {
        PrivilegeAssociationImpl privilegeAssociation =
                new PrivilegeAssociationImpl();
        return privilegeAssociation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public PrivilegeCategory createPrivilegeCategory() {
        PrivilegeCategoryImpl privilegeCategory = new PrivilegeCategoryImpl();
        return privilegeCategory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceType createResourceType() {
        ResourceTypeImpl resourceType = new ResourceTypeImpl();
        return resourceType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Resource createResource() {
        ResourceImpl resource = new ResourceImpl();
        return resource;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceAssociation createResourceAssociation() {
        ResourceAssociationImpl resourceAssociation =
                new ResourceAssociationImpl();
        return resourceAssociation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OrgQuery createOrgQuery() {
        OrgQueryImpl orgQuery = new OrgQueryImpl();
        return orgQuery;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SystemAction createSystemAction() {
        SystemActionImpl systemAction = new SystemActionImpl();
        return systemAction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AttributeType createAttributeTypeFromString(EDataType eDataType,
            String initialValue) {
        AttributeType result = AttributeType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String convertAttributeTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public OMPackage getOMPackage() {
        return (OMPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static OMPackage getPackage() {
        return OMPackage.eINSTANCE;
    }

} // OMFactoryImpl
