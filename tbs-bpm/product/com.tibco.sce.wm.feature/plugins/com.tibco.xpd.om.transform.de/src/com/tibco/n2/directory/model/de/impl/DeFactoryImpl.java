/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DeFactoryImpl extends EFactoryImpl implements DeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DeFactory init() {
        try {
            DeFactory theDeFactory = (DeFactory)EPackage.Registry.INSTANCE.getEFactory(DePackage.eNS_URI);
            if (theDeFactory != null) {
                return theDeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case DePackage.ATTRIBUTE: return createAttribute();
            case DePackage.ATTRIBUTE_TYPE: return createAttributeType();
            case DePackage.CAPABILITY: return createCapability();
            case DePackage.CAPABILITY_CATEGORY: return createCapabilityCategory();
            case DePackage.CAPABILITY_HOLDING: return createCapabilityHolding();
            case DePackage.DOCUMENT_ROOT: return createDocumentRoot();
            case DePackage.FEATURE: return createFeature();
            case DePackage.GROUP: return createGroup();
            case DePackage.GROUP_HOLDING: return createGroupHolding();
            case DePackage.LOCATION: return createLocation();
            case DePackage.LOCATION_TYPE: return createLocationType();
            case DePackage.META_MODEL: return createMetaModel();
            case DePackage.MODEL_ORG_UNIT: return createModelOrgUnit();
            case DePackage.MODEL_TEMPLATE: return createModelTemplate();
            case DePackage.MODEL_TYPE: return createModelType();
            case DePackage.ORGANIZATION: return createOrganization();
            case DePackage.ORGANIZATION_TYPE: return createOrganizationType();
            case DePackage.ORG_UNIT: return createOrgUnit();
            case DePackage.ORG_UNIT_BASE: return createOrgUnitBase();
            case DePackage.ORG_UNIT_TYPE: return createOrgUnitType();
            case DePackage.POSITION: return createPosition();
            case DePackage.POSITION_HOLDING: return createPositionHolding();
            case DePackage.POSITION_TYPE: return createPositionType();
            case DePackage.PRIVILEGE: return createPrivilege();
            case DePackage.PRIVILEGE_CATEGORY: return createPrivilegeCategory();
            case DePackage.PRIVILEGE_HOLDING: return createPrivilegeHolding();
            case DePackage.QUALIFIER: return createQualifier();
            case DePackage.RESOURCE: return createResource();
            case DePackage.SYSTEM_ACTION: return createSystemAction();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case DePackage.ALLOCATION_METHOD:
                return createAllocationMethodFromString(eDataType, initialValue);
            case DePackage.DATA_TYPE:
                return createDataTypeFromString(eDataType, initialValue);
            case DePackage.RESOURCE_TYPE:
                return createResourceTypeFromString(eDataType, initialValue);
            case DePackage.ALLOCATION_METHOD_OBJECT:
                return createAllocationMethodObjectFromString(eDataType, initialValue);
            case DePackage.DATA_TYPE_OBJECT:
                return createDataTypeObjectFromString(eDataType, initialValue);
            case DePackage.RESOURCE_TYPE_OBJECT:
                return createResourceTypeObjectFromString(eDataType, initialValue);
            case DePackage.VERSION_NUMBER:
                return createVersionNumberFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case DePackage.ALLOCATION_METHOD:
                return convertAllocationMethodToString(eDataType, instanceValue);
            case DePackage.DATA_TYPE:
                return convertDataTypeToString(eDataType, instanceValue);
            case DePackage.RESOURCE_TYPE:
                return convertResourceTypeToString(eDataType, instanceValue);
            case DePackage.ALLOCATION_METHOD_OBJECT:
                return convertAllocationMethodObjectToString(eDataType, instanceValue);
            case DePackage.DATA_TYPE_OBJECT:
                return convertDataTypeObjectToString(eDataType, instanceValue);
            case DePackage.RESOURCE_TYPE_OBJECT:
                return convertResourceTypeObjectToString(eDataType, instanceValue);
            case DePackage.VERSION_NUMBER:
                return convertVersionNumberToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribute createAttribute() {
        AttributeImpl attribute = new AttributeImpl();
        return attribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeType createAttributeType() {
        AttributeTypeImpl attributeType = new AttributeTypeImpl();
        return attributeType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Capability createCapability() {
        CapabilityImpl capability = new CapabilityImpl();
        return capability;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CapabilityCategory createCapabilityCategory() {
        CapabilityCategoryImpl capabilityCategory = new CapabilityCategoryImpl();
        return capabilityCategory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CapabilityHolding createCapabilityHolding() {
        CapabilityHoldingImpl capabilityHolding = new CapabilityHoldingImpl();
        return capabilityHolding;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Feature createFeature() {
        FeatureImpl feature = new FeatureImpl();
        return feature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Group createGroup() {
        GroupImpl group = new GroupImpl();
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GroupHolding createGroupHolding() {
        GroupHoldingImpl groupHolding = new GroupHoldingImpl();
        return groupHolding;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Location createLocation() {
        LocationImpl location = new LocationImpl();
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LocationType createLocationType() {
        LocationTypeImpl locationType = new LocationTypeImpl();
        return locationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaModel createMetaModel() {
        MetaModelImpl metaModel = new MetaModelImpl();
        return metaModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModelOrgUnit createModelOrgUnit() {
        ModelOrgUnitImpl modelOrgUnit = new ModelOrgUnitImpl();
        return modelOrgUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModelTemplate createModelTemplate() {
        ModelTemplateImpl modelTemplate = new ModelTemplateImpl();
        return modelTemplate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModelType createModelType() {
        ModelTypeImpl modelType = new ModelTypeImpl();
        return modelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Organization createOrganization() {
        OrganizationImpl organization = new OrganizationImpl();
        return organization;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganizationType createOrganizationType() {
        OrganizationTypeImpl organizationType = new OrganizationTypeImpl();
        return organizationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgUnit createOrgUnit() {
        OrgUnitImpl orgUnit = new OrgUnitImpl();
        return orgUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitBase createOrgUnitBase() {
        OrgUnitBaseImpl orgUnitBase = new OrgUnitBaseImpl();
        return orgUnitBase;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitType createOrgUnitType() {
        OrgUnitTypeImpl orgUnitType = new OrgUnitTypeImpl();
        return orgUnitType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Position createPosition() {
        PositionImpl position = new PositionImpl();
        return position;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PositionHolding createPositionHolding() {
        PositionHoldingImpl positionHolding = new PositionHoldingImpl();
        return positionHolding;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PositionType createPositionType() {
        PositionTypeImpl positionType = new PositionTypeImpl();
        return positionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Privilege createPrivilege() {
        PrivilegeImpl privilege = new PrivilegeImpl();
        return privilege;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PrivilegeCategory createPrivilegeCategory() {
        PrivilegeCategoryImpl privilegeCategory = new PrivilegeCategoryImpl();
        return privilegeCategory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PrivilegeHolding createPrivilegeHolding() {
        PrivilegeHoldingImpl privilegeHolding = new PrivilegeHoldingImpl();
        return privilegeHolding;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Qualifier createQualifier() {
        QualifierImpl qualifier = new QualifierImpl();
        return qualifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Resource createResource() {
        ResourceImpl resource = new ResourceImpl();
        return resource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SystemAction createSystemAction() {
        SystemActionImpl systemAction = new SystemActionImpl();
        return systemAction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationMethod createAllocationMethodFromString(EDataType eDataType, String initialValue) {
        AllocationMethod result = AllocationMethod.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAllocationMethodToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataType createDataTypeFromString(EDataType eDataType, String initialValue) {
        DataType result = DataType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDataTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType createResourceTypeFromString(EDataType eDataType, String initialValue) {
        ResourceType result = ResourceType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResourceTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationMethod createAllocationMethodObjectFromString(EDataType eDataType, String initialValue) {
        return createAllocationMethodFromString(DePackage.Literals.ALLOCATION_METHOD, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAllocationMethodObjectToString(EDataType eDataType, Object instanceValue) {
        return convertAllocationMethodToString(DePackage.Literals.ALLOCATION_METHOD, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataType createDataTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createDataTypeFromString(DePackage.Literals.DATA_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertDataTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertDataTypeToString(DePackage.Literals.DATA_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceType createResourceTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createResourceTypeFromString(DePackage.Literals.RESOURCE_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertResourceTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertResourceTypeToString(DePackage.Literals.RESOURCE_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createVersionNumberFromString(EDataType eDataType, String initialValue) {
        return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.TOKEN, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertVersionNumberToString(EDataType eDataType, Object instanceValue) {
        return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.TOKEN, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DePackage getDePackage() {
        return (DePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DePackage getPackage() {
        return DePackage.eINSTANCE;
    }

} //DeFactoryImpl
