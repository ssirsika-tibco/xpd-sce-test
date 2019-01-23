/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.om.core.om.OMFactory
 * @model kind="package"
 * @generated
 */
public interface OMPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "om"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/om/1.0"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "om"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    OMPackage eINSTANCE = com.tibco.xpd.om.core.om.impl.OMPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.ModelElementImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getModelElement()
     * @generated
     */
    int MODEL_ELEMENT = 36;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl <em>Named Element</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.NamedElementImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getNamedElement()
     * @generated
     */
    int NAMED_ELEMENT = 24;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Namespace <em>Namespace</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Namespace
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getNamespace()
     * @generated
     */
    int NAMESPACE = 32;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.OrgElement <em>Org Element</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.OrgElement
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgElement()
     * @generated
     */
    int ORG_ELEMENT = 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.OrgTypedElement <em>Org Typed Element</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.OrgTypedElement
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgTypedElement()
     * @generated
     */
    int ORG_TYPED_ELEMENT = 31;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.OrgNode <em>Org Node</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.OrgNode
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgNode()
     * @generated
     */
    int ORG_NODE = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl <em>Org Unit</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgUnitImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnit()
     * @generated
     */
    int ORG_UNIT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PositionImpl <em>Position</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PositionImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPosition()
     * @generated
     */
    int POSITION = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.LocationImpl <em>Location</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.LocationImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocation()
     * @generated
     */
    int LOCATION = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl <em>Qualified Org Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getQualifiedOrgElement()
     * @generated
     */
    int QUALIFIED_ORG_ELEMENT = 28;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityImpl <em>Capability</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.CapabilityImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapability()
     * @generated
     */
    int CAPABILITY = 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.GroupImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getGroup()
     * @generated
     */
    int GROUP = 8;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Feature <em>Feature</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Feature
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getFeature()
     * @generated
     */
    int FEATURE = 30;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgElementTypeImpl <em>Org Element Type</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgElementTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgElementType()
     * @generated
     */
    int ORG_ELEMENT_TYPE = 19;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl <em>Org Unit Type</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitType()
     * @generated
     */
    int ORG_UNIT_TYPE = 14;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PositionTypeImpl <em>Position Type</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PositionTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPositionType()
     * @generated
     */
    int POSITION_TYPE = 15;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.MultipleFeature <em>Multiple Feature</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.MultipleFeature
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getMultipleFeature()
     * @generated
     */
    int MULTIPLE_FEATURE = 17;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl <em>Org Unit Feature</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitFeature()
     * @generated
     */
    int ORG_UNIT_FEATURE = 16;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PositionFeatureImpl <em>Position Feature</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PositionFeatureImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPositionFeature()
     * @generated
     */
    int POSITION_FEATURE = 18;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl <em>Org Unit Relationship</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitRelationship()
     * @generated
     */
    int ORG_UNIT_RELATIONSHIP = 20;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipTypeImpl <em>Org Unit Relationship Type</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitRelationshipType()
     * @generated
     */
    int ORG_UNIT_RELATIONSHIP_TYPE = 21;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl <em>Capability Category</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapabilityCategory()
     * @generated
     */
    int CAPABILITY_CATEGORY = 22;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.LocationTypeImpl <em>Location Type</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.LocationTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocationType()
     * @generated
     */
    int LOCATION_TYPE = 23;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl <em>Org Model</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgModelImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgModel()
     * @generated
     */
    int ORG_MODEL = 25;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.EnumValueImpl <em>Enum Value</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.EnumValueImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getEnumValue()
     * @generated
     */
    int ENUM_VALUE = 26;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeImpl <em>Privilege</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PrivilegeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilege()
     * @generated
     */
    int PRIVILEGE = 27;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl <em>Organization</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrganizationImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrganization()
     * @generated
     */
    int ORGANIZATION = 9;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl <em>Attribute Value</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.AttributeValueImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttributeValue()
     * @generated
     */
    int ATTRIBUTE_VALUE = 11;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.AttributeImpl <em>Attribute</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.AttributeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttribute()
     * @generated
     */
    int ATTRIBUTE = 12;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrganizationTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrganizationType()
     * @generated
     */
    int ORGANIZATION_TYPE = 13;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl <em>Base Org Model</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getBaseOrgModel()
     * @generated
     */
    int BASE_ORG_MODEL = 35;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Authorizable <em>Authorizable</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Authorizable
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAuthorizable()
     * @generated
     */
    int AUTHORIZABLE = 29;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT__ID = 0;

    /**
     * The number of structural features of the '<em>Model Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ELEMENT_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__ID = MODEL_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAME = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__NAMESPACE = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__LABEL = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__QUALIFIED_NAME = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__LABEL_KEY = MODEL_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT__DISPLAY_NAME = MODEL_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Named Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMESPACE__ID = NAMED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMESPACE__NAME = NAMED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMESPACE__NAMESPACE = NAMED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMESPACE__LABEL = NAMED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int NAMESPACE__LABEL_KEY = NAMED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE__DISPLAY_NAME = NAMED_ELEMENT__DISPLAY_NAME;

    /**
     * The number of structural features of the '<em>Namespace</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMESPACE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__PURPOSE = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__START_DATE = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__END_DATE = NAMESPACE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT__DESCRIPTION = NAMESPACE_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Org Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__ID = ORG_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__NAME = ORG_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__NAMESPACE = ORG_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__LABEL = ORG_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__QUALIFIED_NAME = ORG_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__LABEL_KEY = ORG_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__DISPLAY_NAME = ORG_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__PURPOSE = ORG_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__START_DATE = ORG_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__END_DATE = ORG_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__DESCRIPTION = ORG_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES = ORG_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT__TYPE = ORG_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Org Typed Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_TYPED_ELEMENT_FEATURE_COUNT = ORG_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__QUALIFIED_NAME = ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ATTRIBUTE_VALUES = ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__PRIVILEGE_ASSOCIATIONS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__SYSTEM_ACTIONS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ALLOCATION_METHOD = ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__LOCATION = ORG_TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT__FEATURE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Positions</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__POSITIONS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Org Unit Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ORG_UNIT_TYPE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Outgoing Relationships</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__OUTGOING_RELATIONSHIPS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Incoming Relationships</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__INCOMING_RELATIONSHIPS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Org Unit</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE_COUNT = ORG_TYPED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgUnitImpl <em>Dynamic Org Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.DynamicOrgUnitImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgUnit()
     * @generated
     */
    int DYNAMIC_ORG_UNIT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__ID = ORG_UNIT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__NAME = ORG_UNIT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__NAMESPACE = ORG_UNIT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__LABEL = ORG_UNIT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__QUALIFIED_NAME = ORG_UNIT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__LABEL_KEY = ORG_UNIT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__DISPLAY_NAME = ORG_UNIT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__PURPOSE = ORG_UNIT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__START_DATE = ORG_UNIT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__END_DATE = ORG_UNIT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__DESCRIPTION = ORG_UNIT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__ATTRIBUTE_VALUES = ORG_UNIT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__TYPE = ORG_UNIT__TYPE;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__PRIVILEGE_ASSOCIATIONS =
            ORG_UNIT__PRIVILEGE_ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__SYSTEM_ACTIONS = ORG_UNIT__SYSTEM_ACTIONS;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__ALLOCATION_METHOD = ORG_UNIT__ALLOCATION_METHOD;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__LOCATION = ORG_UNIT__LOCATION;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__FEATURE = ORG_UNIT__FEATURE;

    /**
     * The feature id for the '<em><b>Positions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__POSITIONS = ORG_UNIT__POSITIONS;

    /**
     * The feature id for the '<em><b>Org Unit Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__ORG_UNIT_TYPE = ORG_UNIT__ORG_UNIT_TYPE;

    /**
     * The feature id for the '<em><b>Outgoing Relationships</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__OUTGOING_RELATIONSHIPS =
            ORG_UNIT__OUTGOING_RELATIONSHIPS;

    /**
     * The feature id for the '<em><b>Incoming Relationships</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__INCOMING_RELATIONSHIPS =
            ORG_UNIT__INCOMING_RELATIONSHIPS;

    /**
     * The feature id for the '<em><b>Dynamic Organization</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION = ORG_UNIT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Dynamic Org Unit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_UNIT_FEATURE_COUNT = ORG_UNIT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl <em>Dynamic Org Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgReference()
     * @generated
     */
    int DYNAMIC_ORG_REFERENCE = 2;

    /**
     * The feature id for the '<em><b>From</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_REFERENCE__FROM = 0;

    /**
     * The feature id for the '<em><b>To</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_REFERENCE__TO = 1;

    /**
     * The number of structural features of the '<em>Dynamic Org Reference</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_REFERENCE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__QUALIFIED_NAME = ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__ATTRIBUTE_VALUES = ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__PRIVILEGE_ASSOCIATIONS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__SYSTEM_ACTIONS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Capabilities Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__CAPABILITIES_ASSOCIATIONS =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__ALLOCATION_METHOD = ORG_TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Resource Association</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__RESOURCE_ASSOCIATION = ORG_TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__LOCATION = ORG_TYPED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Ideal Number</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__IDEAL_NUMBER = ORG_TYPED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION__FEATURE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Position Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__POSITION_TYPE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Position</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE_COUNT = ORG_TYPED_ELEMENT_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__QUALIFIED_NAME = ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__ATTRIBUTE_VALUES = ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__ALLOCATION_METHOD = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Location Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__LOCATION_TYPE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Locale</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__LOCALE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Time Zone</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION__TIME_ZONE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Location</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_FEATURE_COUNT = ORG_TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Org Node</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_NODE_FEATURE_COUNT = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__ID = ORG_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__NAME = ORG_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__NAMESPACE = ORG_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__LABEL = ORG_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__QUALIFIED_NAME = ORG_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__LABEL_KEY = ORG_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__DISPLAY_NAME = ORG_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__PURPOSE = ORG_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__START_DATE = ORG_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__END_DATE = ORG_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__DESCRIPTION = ORG_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Qualifier Attribute</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE =
            ORG_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Qualified Org Element</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ORG_ELEMENT_FEATURE_COUNT = ORG_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__ID = QUALIFIED_ORG_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__NAME = QUALIFIED_ORG_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__NAMESPACE = QUALIFIED_ORG_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__LABEL = QUALIFIED_ORG_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__QUALIFIED_NAME = QUALIFIED_ORG_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__LABEL_KEY = QUALIFIED_ORG_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__DISPLAY_NAME = QUALIFIED_ORG_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__PURPOSE = QUALIFIED_ORG_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__START_DATE = QUALIFIED_ORG_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__END_DATE = QUALIFIED_ORG_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__DESCRIPTION = QUALIFIED_ORG_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Qualifier Attribute</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__QUALIFIER_ATTRIBUTE =
            QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE;

    /**
     * The feature id for the '<em><b>Category</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY__CATEGORY = QUALIFIED_ORG_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Capability</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_FEATURE_COUNT = QUALIFIED_ORG_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__ID = ORG_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__NAME = ORG_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__NAMESPACE = ORG_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__LABEL = ORG_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__QUALIFIED_NAME = ORG_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__LABEL_KEY = ORG_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__DISPLAY_NAME = ORG_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__PURPOSE = ORG_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__START_DATE = ORG_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__END_DATE = ORG_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP__DESCRIPTION = ORG_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Capabilities Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__CAPABILITIES_ASSOCIATIONS = ORG_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ALLOCATION_METHOD = ORG_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__PRIVILEGE_ASSOCIATIONS = ORG_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__SYSTEM_ACTIONS = ORG_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Resource Association</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__RESOURCE_ASSOCIATION = ORG_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Sub Groups</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__SUB_GROUPS = ORG_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Group</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int GROUP_FEATURE_COUNT = ORG_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.QualifiedAssociation <em>Qualified Association</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.QualifiedAssociation
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getQualifiedAssociation()
     * @generated
     */
    int QUALIFIED_ASSOCIATION = 39;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl <em>Capability Association</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapabilityAssociation()
     * @generated
     */
    int CAPABILITY_ASSOCIATION = 33;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl <em>Org Meta Model</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgMetaModel()
     * @generated
     */
    int ORG_META_MODEL = 34;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl <em>Privilege Association</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilegeAssociation()
     * @generated
     */
    int PRIVILEGE_ASSOCIATION = 37;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Capable <em>Capable</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Capable
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapable()
     * @generated
     */
    int CAPABLE = 38;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeCategoryImpl <em>Privilege Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.PrivilegeCategoryImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilegeCategory()
     * @generated
     */
    int PRIVILEGE_CATEGORY = 40;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Allocable <em>Allocable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Allocable
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAllocable()
     * @generated
     */
    int ALLOCABLE = 41;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__QUALIFIED_NAME = ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ATTRIBUTE_VALUES = ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ALLOCATION_METHOD = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__LOCATION = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Units</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__UNITS = ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Organization Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ORGANIZATION_TYPE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Org Unit Relationships</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ORG_UNIT_RELATIONSHIPS =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Dynamic</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DYNAMIC = ORG_TYPED_ELEMENT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Dynamic Org Identifiers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Organization</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_FEATURE_COUNT = ORG_TYPED_ELEMENT_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgIdentifierImpl <em>Dynamic Org Identifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.DynamicOrgIdentifierImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgIdentifier()
     * @generated
     */
    int DYNAMIC_ORG_IDENTIFIER = 10;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The number of structural features of the '<em>Dynamic Org Identifier</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DYNAMIC_ORG_IDENTIFIER_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__ATTRIBUTE = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__VALUE = 1;

    /**
     * The feature id for the '<em><b>Enum Set Values</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__ENUM_SET_VALUES = 2;

    /**
     * The feature id for the '<em><b>Set Values</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__SET_VALUES = 3;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE__TYPE = 4;

    /**
     * The number of structural features of the '<em>Attribute Value</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_VALUE_FEATURE_COUNT = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FEATURE__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FEATURE__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FEATURE__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FEATURE__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int FEATURE__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The number of structural features of the '<em>Feature</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ID = FEATURE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__NAME = FEATURE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__NAMESPACE = FEATURE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__LABEL = FEATURE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__QUALIFIED_NAME = FEATURE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__LABEL_KEY = FEATURE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DISPLAY_NAME = FEATURE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ATTRIBUTE__TYPE = FEATURE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Values</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__VALUES = FEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DEFAULT_VALUE = FEATURE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Default Enum Set Values</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DEFAULT_ENUM_SET_VALUES = FEATURE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DESCRIPTION = FEATURE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Attribute</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_FEATURE_COUNT = FEATURE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE__ATTRIBUTES = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Org Element Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_ELEMENT_TYPE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__QUALIFIED_NAME = ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__DISPLAY_NAME = ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Org Unit Features</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ORG_UNIT_FEATURES =
            ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Organization Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE_FEATURE_COUNT = ORG_ELEMENT_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__QUALIFIED_NAME = ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__DISPLAY_NAME = ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Org Unit Features</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ORG_UNIT_FEATURES = ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Position Features</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__POSITION_FEATURES = ORG_ELEMENT_TYPE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Org Unit Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE_FEATURE_COUNT = ORG_ELEMENT_TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__QUALIFIED_NAME = ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__DISPLAY_NAME = ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The number of structural features of the '<em>Position Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE_FEATURE_COUNT = ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__ID = FEATURE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__NAME = FEATURE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__NAMESPACE = FEATURE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__LABEL = FEATURE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__QUALIFIED_NAME = FEATURE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__LABEL_KEY = FEATURE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__DISPLAY_NAME = FEATURE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__PURPOSE = FEATURE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__START_DATE = FEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__END_DATE = FEATURE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__DESCRIPTION = FEATURE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__LOWER_BOUND = FEATURE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE__UPPER_BOUND = FEATURE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Multiple Feature</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MULTIPLE_FEATURE_FEATURE_COUNT = FEATURE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__ID = MULTIPLE_FEATURE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__NAME = MULTIPLE_FEATURE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__NAMESPACE = MULTIPLE_FEATURE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__LABEL = MULTIPLE_FEATURE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__QUALIFIED_NAME = MULTIPLE_FEATURE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__LABEL_KEY = MULTIPLE_FEATURE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__DISPLAY_NAME = MULTIPLE_FEATURE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__PURPOSE = MULTIPLE_FEATURE__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__START_DATE = MULTIPLE_FEATURE__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__END_DATE = MULTIPLE_FEATURE__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__DESCRIPTION = MULTIPLE_FEATURE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__LOWER_BOUND = MULTIPLE_FEATURE__LOWER_BOUND;

    /**
     * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__UPPER_BOUND = MULTIPLE_FEATURE__UPPER_BOUND;

    /**
     * The feature id for the '<em><b>Feature Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__FEATURE_TYPE = MULTIPLE_FEATURE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Context Relationship Type</b></em>' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE =
            MULTIPLE_FEATURE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Org Unit Feature</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE_FEATURE_COUNT = MULTIPLE_FEATURE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__ID = MULTIPLE_FEATURE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__NAME = MULTIPLE_FEATURE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__NAMESPACE = MULTIPLE_FEATURE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__LABEL = MULTIPLE_FEATURE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__QUALIFIED_NAME = MULTIPLE_FEATURE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__LABEL_KEY = MULTIPLE_FEATURE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__DISPLAY_NAME = MULTIPLE_FEATURE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__PURPOSE = MULTIPLE_FEATURE__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__START_DATE = MULTIPLE_FEATURE__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__END_DATE = MULTIPLE_FEATURE__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__DESCRIPTION = MULTIPLE_FEATURE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__LOWER_BOUND = MULTIPLE_FEATURE__LOWER_BOUND;

    /**
     * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__UPPER_BOUND = MULTIPLE_FEATURE__UPPER_BOUND;

    /**
     * The feature id for the '<em><b>Feature Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE__FEATURE_TYPE = MULTIPLE_FEATURE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Position Feature</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE_FEATURE_COUNT = MULTIPLE_FEATURE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__QUALIFIED_NAME =
            ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES =
            ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>From</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__FROM = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>To</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__TO = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Relationship Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Is Hierarchical</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Org Unit Relationship</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_FEATURE_COUNT =
            ORG_TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__QUALIFIED_NAME =
            ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__DISPLAY_NAME =
            ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The number of structural features of the '<em>Org Unit Relationship Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_RELATIONSHIP_TYPE_FEATURE_COUNT =
            ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Sub Categories</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__SUB_CATEGORIES = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Members</b></em>' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__MEMBERS = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Capability Category</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__QUALIFIED_NAME = ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__DISPLAY_NAME = ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The number of structural features of the '<em>Location Type</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE_FEATURE_COUNT = ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__VERSION = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__STATUS = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__AUTHOR = NAMESPACE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Date Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL__DATE_CREATED = NAMESPACE_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Base Org Model</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BASE_ORG_MODEL_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__ID = BASE_ORG_MODEL__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__NAME = BASE_ORG_MODEL__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__NAMESPACE = BASE_ORG_MODEL__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__LABEL = BASE_ORG_MODEL__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__QUALIFIED_NAME = BASE_ORG_MODEL__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__LABEL_KEY = BASE_ORG_MODEL__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__DISPLAY_NAME = BASE_ORG_MODEL__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_MODEL__VERSION = BASE_ORG_MODEL__VERSION;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__STATUS = BASE_ORG_MODEL__STATUS;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__AUTHOR = BASE_ORG_MODEL__AUTHOR;

    /**
     * The feature id for the '<em><b>Date Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__DATE_CREATED = BASE_ORG_MODEL__DATE_CREATED;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__PRIVILEGE_ASSOCIATIONS = BASE_ORG_MODEL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__SYSTEM_ACTIONS = BASE_ORG_MODEL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Groups</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__GROUPS = BASE_ORG_MODEL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Capability Categories</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__CAPABILITY_CATEGORIES = BASE_ORG_MODEL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Capabilities</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__CAPABILITIES = BASE_ORG_MODEL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Organizations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__ORGANIZATIONS = BASE_ORG_MODEL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Locations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__LOCATIONS = BASE_ORG_MODEL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Privileges</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__PRIVILEGES = BASE_ORG_MODEL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Privilege Categories</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__PRIVILEGE_CATEGORIES = BASE_ORG_MODEL_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Metamodels</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__METAMODELS = BASE_ORG_MODEL_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Resources</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__RESOURCES = BASE_ORG_MODEL_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Embedded Metamodel</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__EMBEDDED_METAMODEL = BASE_ORG_MODEL_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Queries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__QUERIES = BASE_ORG_MODEL_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Human Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__HUMAN_RESOURCE_TYPE = BASE_ORG_MODEL_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Consumable Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__CONSUMABLE_RESOURCE_TYPE = BASE_ORG_MODEL_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Durable Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__DURABLE_RESOURCE_TYPE = BASE_ORG_MODEL_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Dynamic Org References</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL__DYNAMIC_ORG_REFERENCES = BASE_ORG_MODEL_FEATURE_COUNT + 16;

    /**
     * The number of structural features of the '<em>Org Model</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_MODEL_FEATURE_COUNT = BASE_ORG_MODEL_FEATURE_COUNT + 17;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ENUM_VALUE__VALUE = 0;

    /**
     * The number of structural features of the '<em>Enum Value</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_VALUE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__ID = QUALIFIED_ORG_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__NAME = QUALIFIED_ORG_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__NAMESPACE = QUALIFIED_ORG_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__LABEL = QUALIFIED_ORG_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__QUALIFIED_NAME = QUALIFIED_ORG_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__LABEL_KEY = QUALIFIED_ORG_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__DISPLAY_NAME = QUALIFIED_ORG_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__PURPOSE = QUALIFIED_ORG_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__START_DATE = QUALIFIED_ORG_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__END_DATE = QUALIFIED_ORG_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE__DESCRIPTION = QUALIFIED_ORG_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Qualifier Attribute</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__QUALIFIER_ATTRIBUTE =
            QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE;

    /**
     * The feature id for the '<em><b>Category</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__CATEGORY = QUALIFIED_ORG_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Privilege</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_FEATURE_COUNT = QUALIFIED_ORG_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges <em>Associable With Privileges</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.AssociableWithPrivileges
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAssociableWithPrivileges()
     * @generated
     */
    int ASSOCIABLE_WITH_PRIVILEGES = 49;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS = 0;

    /**
     * The number of structural features of the '<em>Associable With Privileges</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTHORIZABLE__PRIVILEGE_ASSOCIATIONS =
            ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>System Actions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTHORIZABLE__SYSTEM_ACTIONS =
            ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Authorizable</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTHORIZABLE_FEATURE_COUNT =
            ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Qualifier Value</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ASSOCIATION__QUALIFIER_VALUE = 0;

    /**
     * The number of structural features of the '<em>Qualified Association</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_ASSOCIATION_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Qualifier Value</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_ASSOCIATION__QUALIFIER_VALUE =
            QUALIFIED_ASSOCIATION__QUALIFIER_VALUE;

    /**
     * The feature id for the '<em><b>Capability</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int CAPABILITY_ASSOCIATION__CAPABILITY =
            QUALIFIED_ASSOCIATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Capability Association</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_ASSOCIATION_FEATURE_COUNT =
            QUALIFIED_ASSOCIATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__ID = BASE_ORG_MODEL__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__NAME = BASE_ORG_MODEL__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__NAMESPACE = BASE_ORG_MODEL__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__LABEL = BASE_ORG_MODEL__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__QUALIFIED_NAME = BASE_ORG_MODEL__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__LABEL_KEY = BASE_ORG_MODEL__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__DISPLAY_NAME = BASE_ORG_MODEL__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__VERSION = BASE_ORG_MODEL__VERSION;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__STATUS = BASE_ORG_MODEL__STATUS;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__AUTHOR = BASE_ORG_MODEL__AUTHOR;

    /**
     * The feature id for the '<em><b>Date Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__DATE_CREATED = BASE_ORG_MODEL__DATE_CREATED;

    /**
     * The feature id for the '<em><b>Location Types</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__LOCATION_TYPES = BASE_ORG_MODEL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Org Unit Relationship Types</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES =
            BASE_ORG_MODEL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Organization Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__ORGANIZATION_TYPES = BASE_ORG_MODEL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Org Unit Types</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__ORG_UNIT_TYPES = BASE_ORG_MODEL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Position Types</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__POSITION_TYPES = BASE_ORG_MODEL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Resource Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__RESOURCE_TYPES = BASE_ORG_MODEL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Embedded</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL__EMBEDDED = BASE_ORG_MODEL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Org Meta Model</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_META_MODEL_FEATURE_COUNT = BASE_ORG_MODEL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Qualifier Value</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE =
            QUALIFIED_ASSOCIATION__QUALIFIER_VALUE;

    /**
     * The feature id for the '<em><b>Privilege</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int PRIVILEGE_ASSOCIATION__PRIVILEGE =
            QUALIFIED_ASSOCIATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Privilege Association</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_ASSOCIATION_FEATURE_COUNT =
            QUALIFIED_ASSOCIATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Capabilities Associations</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABLE__CAPABILITIES_ASSOCIATIONS = 0;

    /**
     * The number of structural features of the '<em>Capable</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABLE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Sub Categories</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__SUB_CATEGORIES = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Members</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__MEMBERS = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Privilege Category</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Allocation Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCABLE__ALLOCATION_METHOD = 0;

    /**
     * The number of structural features of the '<em>Allocable</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ALLOCABLE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.ResourceTypeImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResourceType()
     * @generated
     */
    int RESOURCE_TYPE = 42;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__ID = ORG_ELEMENT_TYPE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__NAME = ORG_ELEMENT_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__NAMESPACE = ORG_ELEMENT_TYPE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__LABEL = ORG_ELEMENT_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__QUALIFIED_NAME = ORG_ELEMENT_TYPE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__LABEL_KEY = ORG_ELEMENT_TYPE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__DISPLAY_NAME = ORG_ELEMENT_TYPE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__ATTRIBUTES = ORG_ELEMENT_TYPE__ATTRIBUTES;

    /**
     * The feature id for the '<em><b>Human Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__HUMAN_RESOURCE_TYPE = ORG_ELEMENT_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Durable Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__DURABLE_RESOURCE_TYPE =
            ORG_ELEMENT_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Consumable Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE__CONSUMABLE_RESOURCE_TYPE =
            ORG_ELEMENT_TYPE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Resource Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_TYPE_FEATURE_COUNT = ORG_ELEMENT_TYPE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.ResourceImpl <em>Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.ResourceImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResource()
     * @generated
     */
    int RESOURCE = 43;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__ID = ORG_TYPED_ELEMENT__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__NAME = ORG_TYPED_ELEMENT__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__NAMESPACE = ORG_TYPED_ELEMENT__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LABEL = ORG_TYPED_ELEMENT__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__QUALIFIED_NAME = ORG_TYPED_ELEMENT__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LABEL_KEY = ORG_TYPED_ELEMENT__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__DISPLAY_NAME = ORG_TYPED_ELEMENT__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Purpose</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__PURPOSE = ORG_TYPED_ELEMENT__PURPOSE;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__START_DATE = ORG_TYPED_ELEMENT__START_DATE;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__END_DATE = ORG_TYPED_ELEMENT__END_DATE;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__DESCRIPTION = ORG_TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__ATTRIBUTE_VALUES = ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES;

    /**
     * The feature id for the '<em><b>Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__TYPE = ORG_TYPED_ELEMENT__TYPE;

    /**
     * The feature id for the '<em><b>Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__RESOURCE_TYPE = ORG_TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Dn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__DN = ORG_TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_FEATURE_COUNT = ORG_TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.ResourceAssociationImpl <em>Resource Association</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.ResourceAssociationImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResourceAssociation()
     * @generated
     */
    int RESOURCE_ASSOCIATION = 44;

    /**
     * The feature id for the '<em><b>Resource</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_ASSOCIATION__RESOURCE = 0;

    /**
     * The number of structural features of the '<em>Resource Association</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_ASSOCIATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.AssociableWithResources <em>Associable With Resources</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.AssociableWithResources
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAssociableWithResources()
     * @generated
     */
    int ASSOCIABLE_WITH_RESOURCES = 45;

    /**
     * The feature id for the '<em><b>Resource Association</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION = 0;

    /**
     * The number of structural features of the '<em>Associable With Resources</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIABLE_WITH_RESOURCES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.OrgQueryImpl <em>Org Query</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.OrgQueryImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgQuery()
     * @generated
     */
    int ORG_QUERY = 46;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__ID = NAMESPACE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__NAME = NAMESPACE__NAME;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__NAMESPACE = NAMESPACE__NAMESPACE;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__LABEL = NAMESPACE__LABEL;

    /**
     * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

    /**
     * The feature id for the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__LABEL_KEY = NAMESPACE__LABEL_KEY;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__DISPLAY_NAME = NAMESPACE__DISPLAY_NAME;

    /**
     * The feature id for the '<em><b>Grammar</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__GRAMMAR = NAMESPACE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY__QUERY = NAMESPACE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Org Query</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_QUERY_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.Locatable <em>Locatable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.Locatable
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocatable()
     * @generated
     */
    int LOCATABLE = 47;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATABLE__LOCATION = 0;

    /**
     * The number of structural features of the '<em>Locatable</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATABLE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl <em>System Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.impl.SystemActionImpl
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getSystemAction()
     * @generated
     */
    int SYSTEM_ACTION = 48;

    /**
     * The feature id for the '<em><b>Privilege Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS =
            ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__ID = ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Action Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__ACTION_ID = ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Component</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__COMPONENT = ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>System Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION_FEATURE_COUNT =
            ASSOCIABLE_WITH_PRIVILEGES_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.om.core.om.AttributeType <em>Attribute Type</em>}' enum.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see com.tibco.xpd.om.core.om.AttributeType
     * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttributeType()
     * @generated
     */
    int ATTRIBUTE_TYPE = 50;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgUnit <em>Org Unit</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit
     * @generated
     */
    EClass getOrgUnit();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnit#getFeature <em>Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit#getFeature()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_Feature();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.OrgUnit#getOutgoingRelationships <em>Outgoing Relationships</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Outgoing Relationships</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit#getOutgoingRelationships()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_OutgoingRelationships();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.OrgUnit#getIncomingRelationships <em>Incoming Relationships</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Incoming Relationships</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit#getIncomingRelationships()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_IncomingRelationships();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.DynamicOrgUnit <em>Dynamic Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Org Unit</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgUnit
     * @generated
     */
    EClass getDynamicOrgUnit();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.DynamicOrgUnit#getDynamicOrganization <em>Dynamic Organization</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Dynamic Organization</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgUnit#getDynamicOrganization()
     * @see #getDynamicOrgUnit()
     * @generated
     */
    EReference getDynamicOrgUnit_DynamicOrganization();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.DynamicOrgReference <em>Dynamic Org Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Org Reference</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgReference
     * @generated
     */
    EClass getDynamicOrgReference();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getFrom <em>From</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>From</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgReference#getFrom()
     * @see #getDynamicOrgReference()
     * @generated
     */
    EReference getDynamicOrgReference_From();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.DynamicOrgReference#getTo <em>To</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>To</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgReference#getTo()
     * @see #getDynamicOrgReference()
     * @generated
     */
    EReference getDynamicOrgReference_To();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgUnit#getPositions <em>Positions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Positions</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit#getPositions()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_Positions();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnit#getOrgUnitType <em>Org Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Org Unit Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnit#getOrgUnitType()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_OrgUnitType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Position <em>Position</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Position</em>'.
     * @see com.tibco.xpd.om.core.om.Position
     * @generated
     */
    EClass getPosition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Position#getIdealNumber <em>Ideal Number</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ideal Number</em>'.
     * @see com.tibco.xpd.om.core.om.Position#getIdealNumber()
     * @see #getPosition()
     * @generated
     */
    EAttribute getPosition_IdealNumber();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Position#getFeature <em>Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature</em>'.
     * @see com.tibco.xpd.om.core.om.Position#getFeature()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_Feature();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Position#getPositionType <em>Position Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Position Type</em>'.
     * @see com.tibco.xpd.om.core.om.Position#getPositionType()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_PositionType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Location <em>Location</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Location</em>'.
     * @see com.tibco.xpd.om.core.om.Location
     * @generated
     */
    EClass getLocation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Location#getLocationType <em>Location Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location Type</em>'.
     * @see com.tibco.xpd.om.core.om.Location#getLocationType()
     * @see #getLocation()
     * @generated
     */
    EReference getLocation_LocationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Location#getLocale <em>Locale</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Locale</em>'.
     * @see com.tibco.xpd.om.core.om.Location#getLocale()
     * @see #getLocation()
     * @generated
     */
    EAttribute getLocation_Locale();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Location#getTimeZone <em>Time Zone</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time Zone</em>'.
     * @see com.tibco.xpd.om.core.om.Location#getTimeZone()
     * @see #getLocation()
     * @generated
     */
    EAttribute getLocation_TimeZone();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgNode <em>Org Node</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Node</em>'.
     * @see com.tibco.xpd.om.core.om.OrgNode
     * @generated
     */
    EClass getOrgNode();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgElement <em>Org Element</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Element</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElement
     * @generated
     */
    EClass getOrgElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgElement#getPurpose <em>Purpose</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Purpose</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElement#getPurpose()
     * @see #getOrgElement()
     * @generated
     */
    EAttribute getOrgElement_Purpose();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgElement#getStartDate <em>Start Date</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Date</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElement#getStartDate()
     * @see #getOrgElement()
     * @generated
     */
    EAttribute getOrgElement_StartDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgElement#getEndDate <em>End Date</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Date</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElement#getEndDate()
     * @see #getOrgElement()
     * @generated
     */
    EAttribute getOrgElement_EndDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgElement#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElement#getDescription()
     * @see #getOrgElement()
     * @generated
     */
    EAttribute getOrgElement_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Capability <em>Capability</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability</em>'.
     * @see com.tibco.xpd.om.core.om.Capability
     * @generated
     */
    EClass getCapability();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Capability#getCategory <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Category</em>'.
     * @see com.tibco.xpd.om.core.om.Capability#getCategory()
     * @see #getCapability()
     * @generated
     */
    EReference getCapability_Category();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Group <em>Group</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Group</em>'.
     * @see com.tibco.xpd.om.core.om.Group
     * @generated
     */
    EClass getGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Group#getSubGroups <em>Sub Groups</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Sub Groups</em>'.
     * @see com.tibco.xpd.om.core.om.Group#getSubGroups()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_SubGroups();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Organization <em>Organization</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Organization</em>'.
     * @see com.tibco.xpd.om.core.om.Organization
     * @generated
     */
    EClass getOrganization();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Organization#getUnits <em>Units</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Units</em>'.
     * @see com.tibco.xpd.om.core.om.Organization#getUnits()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_Units();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Organization#getOrganizationType <em>Organization Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Organization Type</em>'.
     * @see com.tibco.xpd.om.core.om.Organization#getOrganizationType()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_OrganizationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Organization#getOrgUnitRelationships <em>Org Unit Relationships</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Relationships</em>'.
     * @see com.tibco.xpd.om.core.om.Organization#getOrgUnitRelationships()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_OrgUnitRelationships();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Organization#isDynamic <em>Dynamic</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Dynamic</em>'.
     * @see com.tibco.xpd.om.core.om.Organization#isDynamic()
     * @see #getOrganization()
     * @generated
     */
    EAttribute getOrganization_Dynamic();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Organization#getDynamicOrgIdentifiers <em>Dynamic Org Identifiers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dynamic Org Identifiers</em>'.
     * @see com.tibco.xpd.om.core.om.Organization#getDynamicOrgIdentifiers()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_DynamicOrgIdentifiers();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.DynamicOrgIdentifier <em>Dynamic Org Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Dynamic Org Identifier</em>'.
     * @see com.tibco.xpd.om.core.om.DynamicOrgIdentifier
     * @generated
     */
    EClass getDynamicOrgIdentifier();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.AttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Value</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue
     * @generated
     */
    EClass getAttributeValue();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.AttributeValue#getAttribute <em>Attribute</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Attribute</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue#getAttribute()
     * @see #getAttributeValue()
     * @generated
     */
    EReference getAttributeValue_Attribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.AttributeValue#getValue <em>Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue#getValue()
     * @see #getAttributeValue()
     * @generated
     */
    EAttribute getAttributeValue_Value();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.AttributeValue#getEnumSetValues <em>Enum Set Values</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Enum Set Values</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue#getEnumSetValues()
     * @see #getAttributeValue()
     * @generated
     */
    EReference getAttributeValue_EnumSetValues();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.om.core.om.AttributeValue#getSetValues <em>Set Values</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Set Values</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue#getSetValues()
     * @see #getAttributeValue()
     * @generated
     */
    EAttribute getAttributeValue_SetValues();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.AttributeValue#getType <em>Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeValue#getType()
     * @see #getAttributeValue()
     * @generated
     */
    EAttribute getAttributeValue_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Attribute <em>Attribute</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute
     * @generated
     */
    EClass getAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Attribute#getType <em>Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute#getType()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Type();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Attribute#getValues <em>Values</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Values</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute#getValues()
     * @see #getAttribute()
     * @generated
     */
    EReference getAttribute_Values();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultEnumSetValues <em>Default Enum Set Values</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Default Enum Set Values</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute#getDefaultEnumSetValues()
     * @see #getAttribute()
     * @generated
     */
    EReference getAttribute_DefaultEnumSetValues();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Attribute#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute#getDescription()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Description();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrganizationType <em>Organization Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Organization Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrganizationType
     * @generated
     */
    EClass getOrganizationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrganizationType#getOrgUnitFeatures <em>Org Unit Features</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Features</em>'.
     * @see com.tibco.xpd.om.core.om.OrganizationType#getOrgUnitFeatures()
     * @see #getOrganizationType()
     * @generated
     */
    EReference getOrganizationType_OrgUnitFeatures();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see com.tibco.xpd.om.core.om.Attribute#getDefaultValue()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_DefaultValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgUnitType <em>Org Unit Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitType
     * @generated
     */
    EClass getOrgUnitType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgUnitType#getOrgUnitFeatures <em>Org Unit Features</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Features</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitType#getOrgUnitFeatures()
     * @see #getOrgUnitType()
     * @generated
     */
    EReference getOrgUnitType_OrgUnitFeatures();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgUnitType#getPositionFeatures <em>Position Features</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position Features</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitType#getPositionFeatures()
     * @see #getOrgUnitType()
     * @generated
     */
    EReference getOrgUnitType_PositionFeatures();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.PositionType <em>Position Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Position Type</em>'.
     * @see com.tibco.xpd.om.core.om.PositionType
     * @generated
     */
    EClass getPositionType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgUnitFeature <em>Org Unit Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Feature</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitFeature
     * @generated
     */
    EClass getOrgUnitFeature();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getFeatureType <em>Feature Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitFeature#getFeatureType()
     * @see #getOrgUnitFeature()
     * @generated
     */
    EReference getOrgUnitFeature_FeatureType();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnitFeature#getContextRelationshipType <em>Context Relationship Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Context Relationship Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitFeature#getContextRelationshipType()
     * @see #getOrgUnitFeature()
     * @generated
     */
    EReference getOrgUnitFeature_ContextRelationshipType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.MultipleFeature <em>Multiple Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Multiple Feature</em>'.
     * @see com.tibco.xpd.om.core.om.MultipleFeature
     * @generated
     */
    EClass getMultipleFeature();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.MultipleFeature#getLowerBound <em>Lower Bound</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower Bound</em>'.
     * @see com.tibco.xpd.om.core.om.MultipleFeature#getLowerBound()
     * @see #getMultipleFeature()
     * @generated
     */
    EAttribute getMultipleFeature_LowerBound();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.MultipleFeature#getUpperBound <em>Upper Bound</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper Bound</em>'.
     * @see com.tibco.xpd.om.core.om.MultipleFeature#getUpperBound()
     * @see #getMultipleFeature()
     * @generated
     */
    EAttribute getMultipleFeature_UpperBound();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.PositionFeature <em>Position Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Position Feature</em>'.
     * @see com.tibco.xpd.om.core.om.PositionFeature
     * @generated
     */
    EClass getPositionFeature();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.PositionFeature#getFeatureType <em>Feature Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature Type</em>'.
     * @see com.tibco.xpd.om.core.om.PositionFeature#getFeatureType()
     * @see #getPositionFeature()
     * @generated
     */
    EReference getPositionFeature_FeatureType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgElementType <em>Org Element Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Element Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElementType
     * @generated
     */
    EClass getOrgElementType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgElementType#getAttributes <em>Attributes</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attributes</em>'.
     * @see com.tibco.xpd.om.core.om.OrgElementType#getAttributes()
     * @see #getOrgElementType()
     * @generated
     */
    EReference getOrgElementType_Attributes();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship <em>Org Unit Relationship</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Relationship</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship
     * @generated
     */
    EClass getOrgUnitRelationship();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom <em>From</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>From</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#getFrom()
     * @see #getOrgUnitRelationship()
     * @generated
     */
    EReference getOrgUnitRelationship_From();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo <em>To</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>To</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#getTo()
     * @see #getOrgUnitRelationship()
     * @generated
     */
    EReference getOrgUnitRelationship_To();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#getRelationshipType <em>Relationship Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Relationship Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#getRelationshipType()
     * @see #getOrgUnitRelationship()
     * @generated
     */
    EReference getOrgUnitRelationship_RelationshipType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgUnitRelationship#isIsHierarchical <em>Is Hierarchical</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Hierarchical</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationship#isIsHierarchical()
     * @see #getOrgUnitRelationship()
     * @generated
     */
    EAttribute getOrgUnitRelationship_IsHierarchical();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgUnitRelationshipType <em>Org Unit Relationship Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Relationship Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgUnitRelationshipType
     * @generated
     */
    EClass getOrgUnitRelationshipType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.CapabilityCategory <em>Capability Category</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability Category</em>'.
     * @see com.tibco.xpd.om.core.om.CapabilityCategory
     * @generated
     */
    EClass getCapabilityCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.CapabilityCategory#getSubCategories <em>Sub Categories</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Sub Categories</em>'.
     * @see com.tibco.xpd.om.core.om.CapabilityCategory#getSubCategories()
     * @see #getCapabilityCategory()
     * @generated
     */
    EReference getCapabilityCategory_SubCategories();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.CapabilityCategory#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Members</em>'.
     * @see com.tibco.xpd.om.core.om.CapabilityCategory#getMembers()
     * @see #getCapabilityCategory()
     * @generated
     */
    EReference getCapabilityCategory_Members();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.LocationType <em>Location Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Location Type</em>'.
     * @see com.tibco.xpd.om.core.om.LocationType
     * @generated
     */
    EClass getLocationType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.NamedElement <em>Named Element</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Element</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement
     * @generated
     */
    EClass getNamedElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.NamedElement#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.NamedElement#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Namespace</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getNamespace()
     * @see #getNamedElement()
     * @generated
     */
    EReference getNamedElement_Namespace();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.NamedElement#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getLabel()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Label();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.NamedElement#getQualifiedName <em>Qualified Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Qualified Name</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getQualifiedName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_QualifiedName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.NamedElement#getLabelKey <em>Label Key</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label Key</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getLabelKey()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_LabelKey();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.NamedElement#getDisplayName <em>Display Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Name</em>'.
     * @see com.tibco.xpd.om.core.om.NamedElement#getDisplayName()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_DisplayName();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgModel <em>Org Model</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Model</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel
     * @generated
     */
    EClass getOrgModel();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Groups</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getGroups()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Groups();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getCapabilityCategories <em>Capability Categories</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability Categories</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getCapabilityCategories()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_CapabilityCategories();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getCapabilities <em>Capabilities</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capabilities</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getCapabilities()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Capabilities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getOrganizations <em>Organizations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Organizations</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getOrganizations()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Organizations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getLocations <em>Locations</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Locations</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getLocations()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Locations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getPrivileges <em>Privileges</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privileges</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getPrivileges()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Privileges();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getPrivilegeCategories <em>Privilege Categories</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Categories</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getPrivilegeCategories()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_PrivilegeCategories();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getMetamodels <em>Metamodels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Metamodels</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getMetamodels()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Metamodels();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getResources <em>Resources</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resources</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getResources()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Resources();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.om.core.om.OrgModel#getEmbeddedMetamodel <em>Embedded Metamodel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Embedded Metamodel</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getEmbeddedMetamodel()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_EmbeddedMetamodel();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgModel#getHumanResourceType <em>Human Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Human Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getHumanResourceType()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_HumanResourceType();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgModel#getConsumableResourceType <em>Consumable Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Consumable Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getConsumableResourceType()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_ConsumableResourceType();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgModel#getDurableResourceType <em>Durable Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Durable Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getDurableResourceType()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_DurableResourceType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getDynamicOrgReferences <em>Dynamic Org References</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Dynamic Org References</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getDynamicOrgReferences()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_DynamicOrgReferences();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgModel#getQueries <em>Queries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Queries</em>'.
     * @see com.tibco.xpd.om.core.om.OrgModel#getQueries()
     * @see #getOrgModel()
     * @generated
     */
    EReference getOrgModel_Queries();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.EnumValue <em>Enum Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Enum Value</em>'.
     * @see com.tibco.xpd.om.core.om.EnumValue
     * @generated
     */
    EClass getEnumValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.EnumValue#getValue <em>Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.om.core.om.EnumValue#getValue()
     * @see #getEnumValue()
     * @generated
     */
    EAttribute getEnumValue_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Privilege <em>Privilege</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege</em>'.
     * @see com.tibco.xpd.om.core.om.Privilege
     * @generated
     */
    EClass getPrivilege();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Privilege#getCategory <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Category</em>'.
     * @see com.tibco.xpd.om.core.om.Privilege#getCategory()
     * @see #getPrivilege()
     * @generated
     */
    EReference getPrivilege_Category();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.QualifiedOrgElement <em>Qualified Org Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Qualified Org Element</em>'.
     * @see com.tibco.xpd.om.core.om.QualifiedOrgElement
     * @generated
     */
    EClass getQualifiedOrgElement();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.om.core.om.QualifiedOrgElement#getQualifierAttribute <em>Qualifier Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Qualifier Attribute</em>'.
     * @see com.tibco.xpd.om.core.om.QualifiedOrgElement#getQualifierAttribute()
     * @see #getQualifiedOrgElement()
     * @generated
     */
    EReference getQualifiedOrgElement_QualifierAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Authorizable <em>Authorizable</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Authorizable</em>'.
     * @see com.tibco.xpd.om.core.om.Authorizable
     * @generated
     */
    EClass getAuthorizable();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Authorizable#getSystemActions <em>System Actions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Actions</em>'.
     * @see com.tibco.xpd.om.core.om.Authorizable#getSystemActions()
     * @see #getAuthorizable()
     * @generated
     */
    EReference getAuthorizable_SystemActions();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Feature <em>Feature</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Feature</em>'.
     * @see com.tibco.xpd.om.core.om.Feature
     * @generated
     */
    EClass getFeature();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgTypedElement <em>Org Typed Element</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Typed Element</em>'.
     * @see com.tibco.xpd.om.core.om.OrgTypedElement
     * @generated
     */
    EClass getOrgTypedElement();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgTypedElement#getAttributeValues <em>Attribute Values</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute Values</em>'.
     * @see com.tibco.xpd.om.core.om.OrgTypedElement#getAttributeValues()
     * @see #getOrgTypedElement()
     * @generated
     */
    EReference getOrgTypedElement_AttributeValues();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.OrgTypedElement#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Type</em>'.
     * @see com.tibco.xpd.om.core.om.OrgTypedElement#getType()
     * @see #getOrgTypedElement()
     * @generated
     */
    EReference getOrgTypedElement_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Namespace <em>Namespace</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Namespace</em>'.
     * @see com.tibco.xpd.om.core.om.Namespace
     * @generated
     */
    EClass getNamespace();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.CapabilityAssociation <em>Capability Association</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability Association</em>'.
     * @see com.tibco.xpd.om.core.om.CapabilityAssociation
     * @generated
     */
    EClass getCapabilityAssociation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.CapabilityAssociation#getCapability <em>Capability</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Capability</em>'.
     * @see com.tibco.xpd.om.core.om.CapabilityAssociation#getCapability()
     * @see #getCapabilityAssociation()
     * @generated
     */
    EReference getCapabilityAssociation_Capability();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgMetaModel <em>Org Meta Model</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Meta Model</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel
     * @generated
     */
    EClass getOrgMetaModel();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getLocationTypes <em>Location Types</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Location Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getLocationTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_LocationTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitRelationshipTypes <em>Org Unit Relationship Types</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Relationship Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitRelationshipTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_OrgUnitRelationshipTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrganizationTypes <em>Organization Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Organization Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getOrganizationTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_OrganizationTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitTypes <em>Org Unit Types</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getOrgUnitTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_OrgUnitTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getPositionTypes <em>Position Types</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getPositionTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_PositionTypes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.OrgMetaModel#getResourceTypes <em>Resource Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource Types</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#getResourceTypes()
     * @see #getOrgMetaModel()
     * @generated
     */
    EReference getOrgMetaModel_ResourceTypes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgMetaModel#isEmbedded <em>Embedded</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Embedded</em>'.
     * @see com.tibco.xpd.om.core.om.OrgMetaModel#isEmbedded()
     * @see #getOrgMetaModel()
     * @generated
     */
    EAttribute getOrgMetaModel_Embedded();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.BaseOrgModel <em>Base Org Model</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Base Org Model</em>'.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel
     * @generated
     */
    EClass getBaseOrgModel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel#getVersion()
     * @see #getBaseOrgModel()
     * @generated
     */
    EAttribute getBaseOrgModel_Version();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getStatus <em>Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Status</em>'.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel#getStatus()
     * @see #getBaseOrgModel()
     * @generated
     */
    EAttribute getBaseOrgModel_Status();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getAuthor <em>Author</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Author</em>'.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel#getAuthor()
     * @see #getBaseOrgModel()
     * @generated
     */
    EAttribute getBaseOrgModel_Author();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.BaseOrgModel#getDateCreated <em>Date Created</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date Created</em>'.
     * @see com.tibco.xpd.om.core.om.BaseOrgModel#getDateCreated()
     * @see #getBaseOrgModel()
     * @generated
     */
    EAttribute getBaseOrgModel_DateCreated();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.ModelElement <em>Model Element</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Element</em>'.
     * @see com.tibco.xpd.om.core.om.ModelElement
     * @generated
     */
    EClass getModelElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.ModelElement#getId <em>Id</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.om.core.om.ModelElement#getId()
     * @see #getModelElement()
     * @generated
     */
    EAttribute getModelElement_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.PrivilegeAssociation <em>Privilege Association</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege Association</em>'.
     * @see com.tibco.xpd.om.core.om.PrivilegeAssociation
     * @generated
     */
    EClass getPrivilegeAssociation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.PrivilegeAssociation#getPrivilege <em>Privilege</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Privilege</em>'.
     * @see com.tibco.xpd.om.core.om.PrivilegeAssociation#getPrivilege()
     * @see #getPrivilegeAssociation()
     * @generated
     */
    EReference getPrivilegeAssociation_Privilege();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Capable <em>Capable</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Capable</em>'.
     * @see com.tibco.xpd.om.core.om.Capable
     * @generated
     */
    EClass getCapable();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.Capable#getCapabilitiesAssociations <em>Capabilities Associations</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capabilities Associations</em>'.
     * @see com.tibco.xpd.om.core.om.Capable#getCapabilitiesAssociations()
     * @see #getCapable()
     * @generated
     */
    EReference getCapable_CapabilitiesAssociations();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.QualifiedAssociation <em>Qualified Association</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Qualified Association</em>'.
     * @see com.tibco.xpd.om.core.om.QualifiedAssociation
     * @generated
     */
    EClass getQualifiedAssociation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.om.core.om.QualifiedAssociation#getQualifierValue <em>Qualifier Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Qualifier Value</em>'.
     * @see com.tibco.xpd.om.core.om.QualifiedAssociation#getQualifierValue()
     * @see #getQualifiedAssociation()
     * @generated
     */
    EReference getQualifiedAssociation_QualifierValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.PrivilegeCategory <em>Privilege Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege Category</em>'.
     * @see com.tibco.xpd.om.core.om.PrivilegeCategory
     * @generated
     */
    EClass getPrivilegeCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.PrivilegeCategory#getSubCategories <em>Sub Categories</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Sub Categories</em>'.
     * @see com.tibco.xpd.om.core.om.PrivilegeCategory#getSubCategories()
     * @see #getPrivilegeCategory()
     * @generated
     */
    EReference getPrivilegeCategory_SubCategories();

    /**
     * Returns the meta object for the reference list '{@link com.tibco.xpd.om.core.om.PrivilegeCategory#getMembers <em>Members</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Members</em>'.
     * @see com.tibco.xpd.om.core.om.PrivilegeCategory#getMembers()
     * @see #getPrivilegeCategory()
     * @generated
     */
    EReference getPrivilegeCategory_Members();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Allocable <em>Allocable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Allocable</em>'.
     * @see com.tibco.xpd.om.core.om.Allocable
     * @generated
     */
    EClass getAllocable();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Allocable#getAllocationMethod <em>Allocation Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Allocation Method</em>'.
     * @see com.tibco.xpd.om.core.om.Allocable#getAllocationMethod()
     * @see #getAllocable()
     * @generated
     */
    EAttribute getAllocable_AllocationMethod();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.ResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceType
     * @generated
     */
    EClass getResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.ResourceType#isHumanResourceType <em>Human Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Human Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceType#isHumanResourceType()
     * @see #getResourceType()
     * @generated
     */
    EAttribute getResourceType_HumanResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.ResourceType#isDurableResourceType <em>Durable Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Durable Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceType#isDurableResourceType()
     * @see #getResourceType()
     * @generated
     */
    EAttribute getResourceType_DurableResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.ResourceType#isConsumableResourceType <em>Consumable Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Consumable Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceType#isConsumableResourceType()
     * @see #getResourceType()
     * @generated
     */
    EAttribute getResourceType_ConsumableResourceType();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Resource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource</em>'.
     * @see com.tibco.xpd.om.core.om.Resource
     * @generated
     */
    EClass getResource();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Resource#getResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Resource Type</em>'.
     * @see com.tibco.xpd.om.core.om.Resource#getResourceType()
     * @see #getResource()
     * @generated
     */
    EReference getResource_ResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.Resource#getDn <em>Dn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Dn</em>'.
     * @see com.tibco.xpd.om.core.om.Resource#getDn()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_Dn();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.ResourceAssociation <em>Resource Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource Association</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceAssociation
     * @generated
     */
    EClass getResourceAssociation();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.ResourceAssociation#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Resource</em>'.
     * @see com.tibco.xpd.om.core.om.ResourceAssociation#getResource()
     * @see #getResourceAssociation()
     * @generated
     */
    EReference getResourceAssociation_Resource();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.AssociableWithResources <em>Associable With Resources</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associable With Resources</em>'.
     * @see com.tibco.xpd.om.core.om.AssociableWithResources
     * @generated
     */
    EClass getAssociableWithResources();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.AssociableWithResources#getResourceAssociation <em>Resource Association</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource Association</em>'.
     * @see com.tibco.xpd.om.core.om.AssociableWithResources#getResourceAssociation()
     * @see #getAssociableWithResources()
     * @generated
     */
    EReference getAssociableWithResources_ResourceAssociation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.OrgQuery <em>Org Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Query</em>'.
     * @see com.tibco.xpd.om.core.om.OrgQuery
     * @generated
     */
    EClass getOrgQuery();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgQuery#getGrammar <em>Grammar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Grammar</em>'.
     * @see com.tibco.xpd.om.core.om.OrgQuery#getGrammar()
     * @see #getOrgQuery()
     * @generated
     */
    EAttribute getOrgQuery_Grammar();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.OrgQuery#getQuery <em>Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Query</em>'.
     * @see com.tibco.xpd.om.core.om.OrgQuery#getQuery()
     * @see #getOrgQuery()
     * @generated
     */
    EAttribute getOrgQuery_Query();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.Locatable <em>Locatable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Locatable</em>'.
     * @see com.tibco.xpd.om.core.om.Locatable
     * @generated
     */
    EClass getLocatable();

    /**
     * Returns the meta object for the reference '{@link com.tibco.xpd.om.core.om.Locatable#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location</em>'.
     * @see com.tibco.xpd.om.core.om.Locatable#getLocation()
     * @see #getLocatable()
     * @generated
     */
    EReference getLocatable_Location();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.SystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>System Action</em>'.
     * @see com.tibco.xpd.om.core.om.SystemAction
     * @generated
     */
    EClass getSystemAction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.SystemAction#getActionId <em>Action Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Action Id</em>'.
     * @see com.tibco.xpd.om.core.om.SystemAction#getActionId()
     * @see #getSystemAction()
     * @generated
     */
    EAttribute getSystemAction_ActionId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.om.core.om.SystemAction#getComponent <em>Component</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Component</em>'.
     * @see com.tibco.xpd.om.core.om.SystemAction#getComponent()
     * @see #getSystemAction()
     * @generated
     */
    EAttribute getSystemAction_Component();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges <em>Associable With Privileges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Associable With Privileges</em>'.
     * @see com.tibco.xpd.om.core.om.AssociableWithPrivileges
     * @generated
     */
    EClass getAssociableWithPrivileges();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges#getPrivilegeAssociations <em>Privilege Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Associations</em>'.
     * @see com.tibco.xpd.om.core.om.AssociableWithPrivileges#getPrivilegeAssociations()
     * @see #getAssociableWithPrivileges()
     * @generated
     */
    EReference getAssociableWithPrivileges_PrivilegeAssociations();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.om.core.om.AttributeType <em>Attribute Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for enum '<em>Attribute Type</em>'.
     * @see com.tibco.xpd.om.core.om.AttributeType
     * @generated
     */
    EEnum getAttributeType();

    /**
     * Returns the factory that creates the instances of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    OMFactory getOMFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that
     * represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitImpl <em>Org Unit</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgUnitImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnit()
         * @generated
         */
        EClass ORG_UNIT = eINSTANCE.getOrgUnit();

        /**
         * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__FEATURE = eINSTANCE.getOrgUnit_Feature();

        /**
         * The meta object literal for the '<em><b>Outgoing Relationships</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__OUTGOING_RELATIONSHIPS = eINSTANCE
                .getOrgUnit_OutgoingRelationships();

        /**
         * The meta object literal for the '<em><b>Incoming Relationships</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__INCOMING_RELATIONSHIPS = eINSTANCE
                .getOrgUnit_IncomingRelationships();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgUnitImpl <em>Dynamic Org Unit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.DynamicOrgUnitImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgUnit()
         * @generated
         */
        EClass DYNAMIC_ORG_UNIT = eINSTANCE.getDynamicOrgUnit();

        /**
         * The meta object literal for the '<em><b>Dynamic Organization</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION = eINSTANCE
                .getDynamicOrgUnit_DynamicOrganization();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl <em>Dynamic Org Reference</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.DynamicOrgReferenceImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgReference()
         * @generated
         */
        EClass DYNAMIC_ORG_REFERENCE = eINSTANCE.getDynamicOrgReference();

        /**
         * The meta object literal for the '<em><b>From</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DYNAMIC_ORG_REFERENCE__FROM = eINSTANCE
                .getDynamicOrgReference_From();

        /**
         * The meta object literal for the '<em><b>To</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DYNAMIC_ORG_REFERENCE__TO = eINSTANCE
                .getDynamicOrgReference_To();

        /**
         * The meta object literal for the '<em><b>Positions</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__POSITIONS = eINSTANCE.getOrgUnit_Positions();

        /**
         * The meta object literal for the '<em><b>Org Unit Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__ORG_UNIT_TYPE = eINSTANCE.getOrgUnit_OrgUnitType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PositionImpl <em>Position</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PositionImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPosition()
         * @generated
         */
        EClass POSITION = eINSTANCE.getPosition();

        /**
         * The meta object literal for the '<em><b>Ideal Number</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute POSITION__IDEAL_NUMBER = eINSTANCE.getPosition_IdealNumber();

        /**
         * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__FEATURE = eINSTANCE.getPosition_Feature();

        /**
         * The meta object literal for the '<em><b>Position Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__POSITION_TYPE = eINSTANCE
                .getPosition_PositionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.LocationImpl <em>Location</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.LocationImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocation()
         * @generated
         */
        EClass LOCATION = eINSTANCE.getLocation();

        /**
         * The meta object literal for the '<em><b>Location Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOCATION__LOCATION_TYPE = eINSTANCE
                .getLocation_LocationType();

        /**
         * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION__LOCALE = eINSTANCE.getLocation_Locale();

        /**
         * The meta object literal for the '<em><b>Time Zone</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION__TIME_ZONE = eINSTANCE.getLocation_TimeZone();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.OrgNode <em>Org Node</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.OrgNode
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgNode()
         * @generated
         */
        EClass ORG_NODE = eINSTANCE.getOrgNode();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.OrgElement <em>Org Element</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.OrgElement
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgElement()
         * @generated
         */
        EClass ORG_ELEMENT = eINSTANCE.getOrgElement();

        /**
         * The meta object literal for the '<em><b>Purpose</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ELEMENT__PURPOSE = eINSTANCE.getOrgElement_Purpose();

        /**
         * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ELEMENT__START_DATE = eINSTANCE
                .getOrgElement_StartDate();

        /**
         * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ELEMENT__END_DATE = eINSTANCE.getOrgElement_EndDate();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_ELEMENT__DESCRIPTION = eINSTANCE
                .getOrgElement_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityImpl <em>Capability</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.CapabilityImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapability()
         * @generated
         */
        EClass CAPABILITY = eINSTANCE.getCapability();

        /**
         * The meta object literal for the '<em><b>Category</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY__CATEGORY = eINSTANCE.getCapability_Category();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.GroupImpl <em>Group</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.GroupImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getGroup()
         * @generated
         */
        EClass GROUP = eINSTANCE.getGroup();

        /**
         * The meta object literal for the '<em><b>Sub Groups</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference GROUP__SUB_GROUPS = eINSTANCE.getGroup_SubGroups();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrganizationImpl <em>Organization</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrganizationImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrganization()
         * @generated
         */
        EClass ORGANIZATION = eINSTANCE.getOrganization();

        /**
         * The meta object literal for the '<em><b>Units</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__UNITS = eINSTANCE.getOrganization_Units();

        /**
         * The meta object literal for the '<em><b>Organization Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__ORGANIZATION_TYPE = eINSTANCE
                .getOrganization_OrganizationType();

        /**
         * The meta object literal for the '<em><b>Org Unit Relationships</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__ORG_UNIT_RELATIONSHIPS = eINSTANCE
                .getOrganization_OrgUnitRelationships();

        /**
         * The meta object literal for the '<em><b>Dynamic</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORGANIZATION__DYNAMIC = eINSTANCE.getOrganization_Dynamic();

        /**
         * The meta object literal for the '<em><b>Dynamic Org Identifiers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS = eINSTANCE
                .getOrganization_DynamicOrgIdentifiers();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.DynamicOrgIdentifierImpl <em>Dynamic Org Identifier</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.DynamicOrgIdentifierImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getDynamicOrgIdentifier()
         * @generated
         */
        EClass DYNAMIC_ORG_IDENTIFIER = eINSTANCE.getDynamicOrgIdentifier();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.AttributeValueImpl <em>Attribute Value</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.AttributeValueImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttributeValue()
         * @generated
         */
        EClass ATTRIBUTE_VALUE = eINSTANCE.getAttributeValue();

        /**
         * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE_VALUE__ATTRIBUTE = eINSTANCE
                .getAttributeValue_Attribute();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_VALUE__VALUE = eINSTANCE.getAttributeValue_Value();

        /**
         * The meta object literal for the '<em><b>Enum Set Values</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE_VALUE__ENUM_SET_VALUES = eINSTANCE
                .getAttributeValue_EnumSetValues();

        /**
         * The meta object literal for the '<em><b>Set Values</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_VALUE__SET_VALUES = eINSTANCE
                .getAttributeValue_SetValues();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_VALUE__TYPE = eINSTANCE.getAttributeValue_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.AttributeImpl <em>Attribute</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.AttributeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttribute()
         * @generated
         */
        EClass ATTRIBUTE = eINSTANCE.getAttribute();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

        /**
         * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE__VALUES = eINSTANCE.getAttribute_Values();

        /**
         * The meta object literal for the '<em><b>Default Enum Set Values</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE__DEFAULT_ENUM_SET_VALUES = eINSTANCE
                .getAttribute_DefaultEnumSetValues();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DESCRIPTION = eINSTANCE
                .getAttribute_Description();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrganizationTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrganizationType()
         * @generated
         */
        EClass ORGANIZATION_TYPE = eINSTANCE.getOrganizationType();

        /**
         * The meta object literal for the '<em><b>Org Unit Features</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION_TYPE__ORG_UNIT_FEATURES = eINSTANCE
                .getOrganizationType_OrgUnitFeatures();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DEFAULT_VALUE = eINSTANCE
                .getAttribute_DefaultValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl <em>Org Unit Type</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgUnitTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitType()
         * @generated
         */
        EClass ORG_UNIT_TYPE = eINSTANCE.getOrgUnitType();

        /**
         * The meta object literal for the '<em><b>Org Unit Features</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_TYPE__ORG_UNIT_FEATURES = eINSTANCE
                .getOrgUnitType_OrgUnitFeatures();

        /**
         * The meta object literal for the '<em><b>Position Features</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_TYPE__POSITION_FEATURES = eINSTANCE
                .getOrgUnitType_PositionFeatures();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PositionTypeImpl <em>Position Type</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PositionTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPositionType()
         * @generated
         */
        EClass POSITION_TYPE = eINSTANCE.getPositionType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl <em>Org Unit Feature</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitFeature()
         * @generated
         */
        EClass ORG_UNIT_FEATURE = eINSTANCE.getOrgUnitFeature();

        /**
         * The meta object literal for the '<em><b>Feature Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_FEATURE__FEATURE_TYPE = eINSTANCE
                .getOrgUnitFeature_FeatureType();

        /**
         * The meta object literal for the '<em><b>Context Relationship Type</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE = eINSTANCE
                .getOrgUnitFeature_ContextRelationshipType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.MultipleFeature <em>Multiple Feature</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.MultipleFeature
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getMultipleFeature()
         * @generated
         */
        EClass MULTIPLE_FEATURE = eINSTANCE.getMultipleFeature();

        /**
         * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute MULTIPLE_FEATURE__LOWER_BOUND = eINSTANCE
                .getMultipleFeature_LowerBound();

        /**
         * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute MULTIPLE_FEATURE__UPPER_BOUND = eINSTANCE
                .getMultipleFeature_UpperBound();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PositionFeatureImpl <em>Position Feature</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PositionFeatureImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPositionFeature()
         * @generated
         */
        EClass POSITION_FEATURE = eINSTANCE.getPositionFeature();

        /**
         * The meta object literal for the '<em><b>Feature Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION_FEATURE__FEATURE_TYPE = eINSTANCE
                .getPositionFeature_FeatureType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgElementTypeImpl <em>Org Element Type</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgElementTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgElementType()
         * @generated
         */
        EClass ORG_ELEMENT_TYPE = eINSTANCE.getOrgElementType();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_ELEMENT_TYPE__ATTRIBUTES = eINSTANCE
                .getOrgElementType_Attributes();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl <em>Org Unit Relationship</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitRelationship()
         * @generated
         */
        EClass ORG_UNIT_RELATIONSHIP = eINSTANCE.getOrgUnitRelationship();

        /**
         * The meta object literal for the '<em><b>From</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_RELATIONSHIP__FROM = eINSTANCE
                .getOrgUnitRelationship_From();

        /**
         * The meta object literal for the '<em><b>To</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_RELATIONSHIP__TO = eINSTANCE
                .getOrgUnitRelationship_To();

        /**
         * The meta object literal for the '<em><b>Relationship Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE = eINSTANCE
                .getOrgUnitRelationship_RelationshipType();

        /**
         * The meta object literal for the '<em><b>Is Hierarchical</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL = eINSTANCE
                .getOrgUnitRelationship_IsHierarchical();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipTypeImpl <em>Org Unit Relationship Type</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgUnitRelationshipType()
         * @generated
         */
        EClass ORG_UNIT_RELATIONSHIP_TYPE = eINSTANCE
                .getOrgUnitRelationshipType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl <em>Capability Category</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.CapabilityCategoryImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapabilityCategory()
         * @generated
         */
        EClass CAPABILITY_CATEGORY = eINSTANCE.getCapabilityCategory();

        /**
         * The meta object literal for the '<em><b>Sub Categories</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_CATEGORY__SUB_CATEGORIES = eINSTANCE
                .getCapabilityCategory_SubCategories();

        /**
         * The meta object literal for the '<em><b>Members</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_CATEGORY__MEMBERS = eINSTANCE
                .getCapabilityCategory_Members();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.LocationTypeImpl <em>Location Type</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.LocationTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocationType()
         * @generated
         */
        EClass LOCATION_TYPE = eINSTANCE.getLocationType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.NamedElementImpl <em>Named Element</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.NamedElementImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getNamedElement()
         * @generated
         */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference NAMED_ELEMENT__NAMESPACE = eINSTANCE
                .getNamedElement_Namespace();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__LABEL = eINSTANCE.getNamedElement_Label();

        /**
         * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__QUALIFIED_NAME = eINSTANCE
                .getNamedElement_QualifiedName();

        /**
         * The meta object literal for the '<em><b>Label Key</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__LABEL_KEY = eINSTANCE
                .getNamedElement_LabelKey();

        /**
         * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ELEMENT__DISPLAY_NAME = eINSTANCE
                .getNamedElement_DisplayName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgModelImpl <em>Org Model</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgModelImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgModel()
         * @generated
         */
        EClass ORG_MODEL = eINSTANCE.getOrgModel();

        /**
         * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__GROUPS = eINSTANCE.getOrgModel_Groups();

        /**
         * The meta object literal for the '<em><b>Capability Categories</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__CAPABILITY_CATEGORIES = eINSTANCE
                .getOrgModel_CapabilityCategories();

        /**
         * The meta object literal for the '<em><b>Capabilities</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__CAPABILITIES = eINSTANCE
                .getOrgModel_Capabilities();

        /**
         * The meta object literal for the '<em><b>Organizations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__ORGANIZATIONS = eINSTANCE
                .getOrgModel_Organizations();

        /**
         * The meta object literal for the '<em><b>Locations</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__LOCATIONS = eINSTANCE.getOrgModel_Locations();

        /**
         * The meta object literal for the '<em><b>Privileges</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__PRIVILEGES = eINSTANCE.getOrgModel_Privileges();

        /**
         * The meta object literal for the '<em><b>Privilege Categories</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__PRIVILEGE_CATEGORIES = eINSTANCE
                .getOrgModel_PrivilegeCategories();

        /**
         * The meta object literal for the '<em><b>Metamodels</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__METAMODELS = eINSTANCE.getOrgModel_Metamodels();

        /**
         * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__RESOURCES = eINSTANCE.getOrgModel_Resources();

        /**
         * The meta object literal for the '<em><b>Embedded Metamodel</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__EMBEDDED_METAMODEL = eINSTANCE
                .getOrgModel_EmbeddedMetamodel();

        /**
         * The meta object literal for the '<em><b>Human Resource Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__HUMAN_RESOURCE_TYPE = eINSTANCE
                .getOrgModel_HumanResourceType();

        /**
         * The meta object literal for the '<em><b>Consumable Resource Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__CONSUMABLE_RESOURCE_TYPE = eINSTANCE
                .getOrgModel_ConsumableResourceType();

        /**
         * The meta object literal for the '<em><b>Durable Resource Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__DURABLE_RESOURCE_TYPE = eINSTANCE
                .getOrgModel_DurableResourceType();

        /**
         * The meta object literal for the '<em><b>Dynamic Org References</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__DYNAMIC_ORG_REFERENCES = eINSTANCE
                .getOrgModel_DynamicOrgReferences();

        /**
         * The meta object literal for the '<em><b>Queries</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_MODEL__QUERIES = eINSTANCE.getOrgModel_Queries();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.EnumValueImpl <em>Enum Value</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.EnumValueImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getEnumValue()
         * @generated
         */
        EClass ENUM_VALUE = eINSTANCE.getEnumValue();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUM_VALUE__VALUE = eINSTANCE.getEnumValue_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeImpl <em>Privilege</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PrivilegeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilege()
         * @generated
         */
        EClass PRIVILEGE = eINSTANCE.getPrivilege();

        /**
         * The meta object literal for the '<em><b>Category</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE__CATEGORY = eINSTANCE.getPrivilege_Category();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl <em>Qualified Org Element</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getQualifiedOrgElement()
         * @generated
         */
        EClass QUALIFIED_ORG_ELEMENT = eINSTANCE.getQualifiedOrgElement();

        /**
         * The meta object literal for the '<em><b>Qualifier Attribute</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE = eINSTANCE
                .getQualifiedOrgElement_QualifierAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Authorizable <em>Authorizable</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Authorizable
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAuthorizable()
         * @generated
         */
        EClass AUTHORIZABLE = eINSTANCE.getAuthorizable();

        /**
         * The meta object literal for the '<em><b>System Actions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference AUTHORIZABLE__SYSTEM_ACTIONS = eINSTANCE
                .getAuthorizable_SystemActions();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Feature <em>Feature</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Feature
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getFeature()
         * @generated
         */
        EClass FEATURE = eINSTANCE.getFeature();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.OrgTypedElement <em>Org Typed Element</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.OrgTypedElement
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgTypedElement()
         * @generated
         */
        EClass ORG_TYPED_ELEMENT = eINSTANCE.getOrgTypedElement();

        /**
         * The meta object literal for the '<em><b>Attribute Values</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES = eINSTANCE
                .getOrgTypedElement_AttributeValues();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_TYPED_ELEMENT__TYPE = eINSTANCE
                .getOrgTypedElement_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Namespace <em>Namespace</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Namespace
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getNamespace()
         * @generated
         */
        EClass NAMESPACE = eINSTANCE.getNamespace();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl <em>Capability Association</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.CapabilityAssociationImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapabilityAssociation()
         * @generated
         */
        EClass CAPABILITY_ASSOCIATION = eINSTANCE.getCapabilityAssociation();

        /**
         * The meta object literal for the '<em><b>Capability</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_ASSOCIATION__CAPABILITY = eINSTANCE
                .getCapabilityAssociation_Capability();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl <em>Org Meta Model</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgMetaModel()
         * @generated
         */
        EClass ORG_META_MODEL = eINSTANCE.getOrgMetaModel();

        /**
         * The meta object literal for the '<em><b>Location Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__LOCATION_TYPES = eINSTANCE
                .getOrgMetaModel_LocationTypes();

        /**
         * The meta object literal for the '<em><b>Org Unit Relationship Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES = eINSTANCE
                .getOrgMetaModel_OrgUnitRelationshipTypes();

        /**
         * The meta object literal for the '<em><b>Organization Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__ORGANIZATION_TYPES = eINSTANCE
                .getOrgMetaModel_OrganizationTypes();

        /**
         * The meta object literal for the '<em><b>Org Unit Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__ORG_UNIT_TYPES = eINSTANCE
                .getOrgMetaModel_OrgUnitTypes();

        /**
         * The meta object literal for the '<em><b>Position Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__POSITION_TYPES = eINSTANCE
                .getOrgMetaModel_PositionTypes();

        /**
         * The meta object literal for the '<em><b>Resource Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_META_MODEL__RESOURCE_TYPES = eINSTANCE
                .getOrgMetaModel_ResourceTypes();

        /**
         * The meta object literal for the '<em><b>Embedded</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_META_MODEL__EMBEDDED = eINSTANCE
                .getOrgMetaModel_Embedded();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl <em>Base Org Model</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.BaseOrgModelImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getBaseOrgModel()
         * @generated
         */
        EClass BASE_ORG_MODEL = eINSTANCE.getBaseOrgModel();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ORG_MODEL__VERSION = eINSTANCE
                .getBaseOrgModel_Version();

        /**
         * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ORG_MODEL__STATUS = eINSTANCE.getBaseOrgModel_Status();

        /**
         * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ORG_MODEL__AUTHOR = eINSTANCE.getBaseOrgModel_Author();

        /**
         * The meta object literal for the '<em><b>Date Created</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BASE_ORG_MODEL__DATE_CREATED = eINSTANCE
                .getBaseOrgModel_DateCreated();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.ModelElementImpl <em>Model Element</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.ModelElementImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getModelElement()
         * @generated
         */
        EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_ELEMENT__ID = eINSTANCE.getModelElement_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl <em>Privilege Association</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilegeAssociation()
         * @generated
         */
        EClass PRIVILEGE_ASSOCIATION = eINSTANCE.getPrivilegeAssociation();

        /**
         * The meta object literal for the '<em><b>Privilege</b></em>' reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_ASSOCIATION__PRIVILEGE = eINSTANCE
                .getPrivilegeAssociation_Privilege();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Capable <em>Capable</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Capable
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getCapable()
         * @generated
         */
        EClass CAPABLE = eINSTANCE.getCapable();

        /**
         * The meta object literal for the '<em><b>Capabilities Associations</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference CAPABLE__CAPABILITIES_ASSOCIATIONS = eINSTANCE
                .getCapable_CapabilitiesAssociations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.QualifiedAssociation <em>Qualified Association</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.QualifiedAssociation
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getQualifiedAssociation()
         * @generated
         */
        EClass QUALIFIED_ASSOCIATION = eINSTANCE.getQualifiedAssociation();

        /**
         * The meta object literal for the '<em><b>Qualifier Value</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference QUALIFIED_ASSOCIATION__QUALIFIER_VALUE = eINSTANCE
                .getQualifiedAssociation_QualifierValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.PrivilegeCategoryImpl <em>Privilege Category</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.PrivilegeCategoryImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getPrivilegeCategory()
         * @generated
         */
        EClass PRIVILEGE_CATEGORY = eINSTANCE.getPrivilegeCategory();

        /**
         * The meta object literal for the '<em><b>Sub Categories</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_CATEGORY__SUB_CATEGORIES = eINSTANCE
                .getPrivilegeCategory_SubCategories();

        /**
         * The meta object literal for the '<em><b>Members</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_CATEGORY__MEMBERS = eINSTANCE
                .getPrivilegeCategory_Members();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Allocable <em>Allocable</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Allocable
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAllocable()
         * @generated
         */
        EClass ALLOCABLE = eINSTANCE.getAllocable();

        /**
         * The meta object literal for the '<em><b>Allocation Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ALLOCABLE__ALLOCATION_METHOD = eINSTANCE
                .getAllocable_AllocationMethod();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.ResourceTypeImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResourceType()
         * @generated
         */
        EClass RESOURCE_TYPE = eINSTANCE.getResourceType();

        /**
         * The meta object literal for the '<em><b>Human Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE_TYPE__HUMAN_RESOURCE_TYPE = eINSTANCE
                .getResourceType_HumanResourceType();

        /**
         * The meta object literal for the '<em><b>Durable Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE_TYPE__DURABLE_RESOURCE_TYPE = eINSTANCE
                .getResourceType_DurableResourceType();

        /**
         * The meta object literal for the '<em><b>Consumable Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE_TYPE__CONSUMABLE_RESOURCE_TYPE = eINSTANCE
                .getResourceType_ConsumableResourceType();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.ResourceImpl <em>Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.ResourceImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResource()
         * @generated
         */
        EClass RESOURCE = eINSTANCE.getResource();

        /**
         * The meta object literal for the '<em><b>Resource Type</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__RESOURCE_TYPE = eINSTANCE
                .getResource_ResourceType();

        /**
         * The meta object literal for the '<em><b>Dn</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__DN = eINSTANCE.getResource_Dn();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.ResourceAssociationImpl <em>Resource Association</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.ResourceAssociationImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getResourceAssociation()
         * @generated
         */
        EClass RESOURCE_ASSOCIATION = eINSTANCE.getResourceAssociation();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE_ASSOCIATION__RESOURCE = eINSTANCE
                .getResourceAssociation_Resource();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.AssociableWithResources <em>Associable With Resources</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.AssociableWithResources
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAssociableWithResources()
         * @generated
         */
        EClass ASSOCIABLE_WITH_RESOURCES = eINSTANCE
                .getAssociableWithResources();

        /**
         * The meta object literal for the '<em><b>Resource Association</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIABLE_WITH_RESOURCES__RESOURCE_ASSOCIATION = eINSTANCE
                .getAssociableWithResources_ResourceAssociation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.OrgQueryImpl <em>Org Query</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.OrgQueryImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getOrgQuery()
         * @generated
         */
        EClass ORG_QUERY = eINSTANCE.getOrgQuery();

        /**
         * The meta object literal for the '<em><b>Grammar</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_QUERY__GRAMMAR = eINSTANCE.getOrgQuery_Grammar();

        /**
         * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_QUERY__QUERY = eINSTANCE.getOrgQuery_Query();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.Locatable <em>Locatable</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.Locatable
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getLocatable()
         * @generated
         */
        EClass LOCATABLE = eINSTANCE.getLocatable();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOCATABLE__LOCATION = eINSTANCE.getLocatable_Location();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl <em>System Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.impl.SystemActionImpl
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getSystemAction()
         * @generated
         */
        EClass SYSTEM_ACTION = eINSTANCE.getSystemAction();

        /**
         * The meta object literal for the '<em><b>Action Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM_ACTION__ACTION_ID = eINSTANCE
                .getSystemAction_ActionId();

        /**
         * The meta object literal for the '<em><b>Component</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM_ACTION__COMPONENT = eINSTANCE
                .getSystemAction_Component();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.AssociableWithPrivileges <em>Associable With Privileges</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.AssociableWithPrivileges
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAssociableWithPrivileges()
         * @generated
         */
        EClass ASSOCIABLE_WITH_PRIVILEGES = eINSTANCE
                .getAssociableWithPrivileges();

        /**
         * The meta object literal for the '<em><b>Privilege Associations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS =
                eINSTANCE.getAssociableWithPrivileges_PrivilegeAssociations();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.om.core.om.AttributeType <em>Attribute Type</em>}' enum.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see com.tibco.xpd.om.core.om.AttributeType
         * @see com.tibco.xpd.om.core.om.impl.OMPackageImpl#getAttributeType()
         * @generated
         */
        EEnum ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

    }

} // OMPackage
