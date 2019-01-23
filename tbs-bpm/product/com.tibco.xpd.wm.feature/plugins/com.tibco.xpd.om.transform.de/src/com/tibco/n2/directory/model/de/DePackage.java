/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.n2.directory.model.de.DeFactory
 * @model kind="package"
 * @generated
 */
public interface DePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "de";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://tibco.com/bpm/directory-model/2.0";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "de";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DePackage eINSTANCE = com.tibco.n2.directory.model.de.impl.DePackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.AttributeImpl <em>Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.AttributeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAttribute()
     * @generated
     */
    int ATTRIBUTE = 0;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__STRING = 0;

    /**
     * The feature id for the '<em><b>Integer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__INTEGER = 1;

    /**
     * The feature id for the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DECIMAL = 2;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DATE = 3;

    /**
     * The feature id for the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__TIME = 4;

    /**
     * The feature id for the '<em><b>Date Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DATE_TIME = 5;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__BOOLEAN = 6;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ENUM = 7;

    /**
     * The feature id for the '<em><b>Enum Set</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ENUM_SET = 8;

    /**
     * The feature id for the '<em><b>Element</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ELEMENT = 9;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE__DEFINITION = 10;

    /**
     * The number of structural features of the '<em>Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_FEATURE_COUNT = 11;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.NamedEntityImpl <em>Named Entity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.NamedEntityImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getNamedEntity()
     * @generated
     */
    int NAMED_ENTITY = 16;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ENTITY__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ENTITY__ID = 1;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ENTITY__LABEL = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ENTITY__NAME = 3;

    /**
     * The number of structural features of the '<em>Named Entity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_ENTITY_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.AttributeTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAttributeType()
     * @generated
     */
    int ATTRIBUTE_TYPE = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__ENUM = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE__DATA_TYPE = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Attribute Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_TYPE_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityImpl <em>Capability</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.CapabilityImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapability()
     * @generated
     */
    int CAPABILITY = 2;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY__QUALIFIER = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Capability</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl <em>Capability Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapabilityCategory()
     * @generated
     */
    int CAPABILITY_CATEGORY = 3;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__GROUP = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Capability Category</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__CAPABILITY_CATEGORY = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Capability</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY__CAPABILITY = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Capability Category</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_CATEGORY_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl <em>Qualified Holding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getQualifiedHolding()
     * @generated
     */
    int QUALIFIED_HOLDING = 28;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__STRING = 0;

    /**
     * The feature id for the '<em><b>Integer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__INTEGER = 1;

    /**
     * The feature id for the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__DECIMAL = 2;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__DATE = 3;

    /**
     * The feature id for the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__TIME = 4;

    /**
     * The feature id for the '<em><b>Date Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__DATE_TIME = 5;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__BOOLEAN = 6;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__ENUM = 7;

    /**
     * The feature id for the '<em><b>Enum Set</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__ENUM_SET = 8;

    /**
     * The feature id for the '<em><b>Element</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING__ELEMENT = 9;

    /**
     * The number of structural features of the '<em>Qualified Holding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIED_HOLDING_FEATURE_COUNT = 10;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityHoldingImpl <em>Capability Holding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.CapabilityHoldingImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapabilityHolding()
     * @generated
     */
    int CAPABILITY_HOLDING = 4;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__STRING = QUALIFIED_HOLDING__STRING;

    /**
     * The feature id for the '<em><b>Integer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__INTEGER = QUALIFIED_HOLDING__INTEGER;

    /**
     * The feature id for the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__DECIMAL = QUALIFIED_HOLDING__DECIMAL;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__DATE = QUALIFIED_HOLDING__DATE;

    /**
     * The feature id for the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__TIME = QUALIFIED_HOLDING__TIME;

    /**
     * The feature id for the '<em><b>Date Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__DATE_TIME = QUALIFIED_HOLDING__DATE_TIME;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__BOOLEAN = QUALIFIED_HOLDING__BOOLEAN;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__ENUM = QUALIFIED_HOLDING__ENUM;

    /**
     * The feature id for the '<em><b>Enum Set</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__ENUM_SET = QUALIFIED_HOLDING__ENUM_SET;

    /**
     * The feature id for the '<em><b>Element</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__ELEMENT = QUALIFIED_HOLDING__ELEMENT;

    /**
     * The feature id for the '<em><b>Capability</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING__CAPABILITY = QUALIFIED_HOLDING_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Capability Holding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CAPABILITY_HOLDING_FEATURE_COUNT = QUALIFIED_HOLDING_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.DocumentRootImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 5;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MODEL = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.EntityTypeImpl <em>Entity Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.EntityTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getEntityType()
     * @generated
     */
    int ENTITY_TYPE = 6;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE__ATTRIBUTE = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Entity Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENTITY_TYPE_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.FeatureImpl <em>Feature</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.FeatureImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getFeature()
     * @generated
     */
    int FEATURE = 7;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__DEFINITION = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__LOWER_BOUND = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE__UPPER_BOUND = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Feature</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FEATURE_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.GroupImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getGroup()
     * @generated
     */
    int GROUP = 8;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__CHOICE_GROUP = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Req Capability</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__REQ_CAPABILITY = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__PRIVILEGE_HELD = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Group</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__GROUP = NAMED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__SYSTEM_ACTION = NAMED_ENTITY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ALLOC_METHOD = NAMED_ENTITY_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__PLUGIN = NAMED_ENTITY_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Undelivered Queue</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__UNDELIVERED_QUEUE = NAMED_ENTITY_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.GroupHoldingImpl <em>Group Holding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.GroupHoldingImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getGroupHolding()
     * @generated
     */
    int GROUP_HOLDING = 9;

    /**
     * The feature id for the '<em><b>Group</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_HOLDING__GROUP = 0;

    /**
     * The number of structural features of the '<em>Group Holding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_HOLDING_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.TypedEntityImpl <em>Typed Entity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.TypedEntityImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getTypedEntity()
     * @generated
     */
    int TYPED_ENTITY = 32;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__ATTRIBUTE_VALUE = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY__DEFINITION = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Typed Entity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TYPED_ENTITY_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.LocationImpl <em>Location</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.LocationImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getLocation()
     * @generated
     */
    int LOCATION = 10;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__DESCRIPTION = TYPED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__ID = TYPED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__LABEL = TYPED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__NAME = TYPED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__ATTRIBUTE_VALUE = TYPED_ENTITY__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__DEFINITION = TYPED_ENTITY__DEFINITION;

    /**
     * The feature id for the '<em><b>Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__LOCALE = TYPED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Timezone</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION__TIMEZONE = TYPED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Location</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_FEATURE_COUNT = TYPED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.LocationTypeImpl <em>Location Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.LocationTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getLocationType()
     * @generated
     */
    int LOCATION_TYPE = 11;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__DESCRIPTION = ENTITY_TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__ID = ENTITY_TYPE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__LABEL = ENTITY_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__NAME = ENTITY_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__ATTRIBUTE = ENTITY_TYPE__ATTRIBUTE;

    /**
     * The feature id for the '<em><b>Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__LOCALE = ENTITY_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Timezone</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE__TIMEZONE = ENTITY_TYPE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Location Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOCATION_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl <em>Meta Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.MetaModelImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getMetaModel()
     * @generated
     */
    int META_MODEL = 12;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL__CHOICE_GROUP = 0;

    /**
     * The feature id for the '<em><b>Location Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL__LOCATION_TYPE = 1;

    /**
     * The feature id for the '<em><b>Position Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL__POSITION_TYPE = 2;

    /**
     * The feature id for the '<em><b>Org Unit Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL__ORG_UNIT_TYPE = 3;

    /**
     * The feature id for the '<em><b>Organization Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL__ORGANIZATION_TYPE = 4;

    /**
     * The number of structural features of the '<em>Meta Model</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int META_MODEL_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitBaseImpl <em>Org Unit Base</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.OrgUnitBaseImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnitBase()
     * @generated
     */
    int ORG_UNIT_BASE = 20;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__DESCRIPTION = TYPED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__ID = TYPED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__LABEL = TYPED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__NAME = TYPED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__ATTRIBUTE_VALUE = TYPED_ENTITY__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__DEFINITION = TYPED_ENTITY__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__CHOICE_GROUP = TYPED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__PRIVILEGE_HELD = TYPED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__SYSTEM_ACTION = TYPED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__ALLOC_METHOD = TYPED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__FEATURE = TYPED_ENTITY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__LOCATION = TYPED_ENTITY_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE__PLUGIN = TYPED_ENTITY_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Org Unit Base</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_BASE_FEATURE_COUNT = TYPED_ENTITY_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl <em>Model Org Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelOrgUnit()
     * @generated
     */
    int MODEL_ORG_UNIT = 13;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__DESCRIPTION = ORG_UNIT_BASE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__ID = ORG_UNIT_BASE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__LABEL = ORG_UNIT_BASE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__NAME = ORG_UNIT_BASE__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__ATTRIBUTE_VALUE = ORG_UNIT_BASE__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__DEFINITION = ORG_UNIT_BASE__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__CHOICE_GROUP = ORG_UNIT_BASE__CHOICE_GROUP;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__PRIVILEGE_HELD = ORG_UNIT_BASE__PRIVILEGE_HELD;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__SYSTEM_ACTION = ORG_UNIT_BASE__SYSTEM_ACTION;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__ALLOC_METHOD = ORG_UNIT_BASE__ALLOC_METHOD;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__FEATURE = ORG_UNIT_BASE__FEATURE;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__LOCATION = ORG_UNIT_BASE__LOCATION;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__PLUGIN = ORG_UNIT_BASE__PLUGIN;

    /**
     * The feature id for the '<em><b>Model Position</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__MODEL_POSITION = ORG_UNIT_BASE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Model Org Unit</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT__MODEL_ORG_UNIT = ORG_UNIT_BASE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Model Org Unit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_ORG_UNIT_FEATURE_COUNT = ORG_UNIT_BASE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.ModelTemplateImpl <em>Model Template</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.ModelTemplateImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelTemplate()
     * @generated
     */
    int MODEL_TEMPLATE = 14;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__DESCRIPTION = MODEL_ORG_UNIT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__ID = MODEL_ORG_UNIT__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__LABEL = MODEL_ORG_UNIT__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__NAME = MODEL_ORG_UNIT__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__ATTRIBUTE_VALUE = MODEL_ORG_UNIT__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__DEFINITION = MODEL_ORG_UNIT__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__CHOICE_GROUP = MODEL_ORG_UNIT__CHOICE_GROUP;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__PRIVILEGE_HELD = MODEL_ORG_UNIT__PRIVILEGE_HELD;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__SYSTEM_ACTION = MODEL_ORG_UNIT__SYSTEM_ACTION;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__ALLOC_METHOD = MODEL_ORG_UNIT__ALLOC_METHOD;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__FEATURE = MODEL_ORG_UNIT__FEATURE;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__LOCATION = MODEL_ORG_UNIT__LOCATION;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__PLUGIN = MODEL_ORG_UNIT__PLUGIN;

    /**
     * The feature id for the '<em><b>Model Position</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__MODEL_POSITION = MODEL_ORG_UNIT__MODEL_POSITION;

    /**
     * The feature id for the '<em><b>Model Org Unit</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__MODEL_ORG_UNIT = MODEL_ORG_UNIT__MODEL_ORG_UNIT;

    /**
     * The feature id for the '<em><b>Instance Id Attribute</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE = MODEL_ORG_UNIT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Model Template</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TEMPLATE_FEATURE_COUNT = MODEL_ORG_UNIT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl <em>Model Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.ModelTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelType()
     * @generated
     */
    int MODEL_TYPE = 15;

    /**
     * The feature id for the '<em><b>Meta Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__META_MODEL = 0;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__CHOICE_GROUP = 1;

    /**
     * The feature id for the '<em><b>Model Template</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__MODEL_TEMPLATE = 2;

    /**
     * The feature id for the '<em><b>Capability</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__CAPABILITY = 3;

    /**
     * The feature id for the '<em><b>Capability Category</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__CAPABILITY_CATEGORY = 4;

    /**
     * The feature id for the '<em><b>Privilege</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__PRIVILEGE = 5;

    /**
     * The feature id for the '<em><b>Privilege Category</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__PRIVILEGE_CATEGORY = 6;

    /**
     * The feature id for the '<em><b>Location</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__LOCATION = 7;

    /**
     * The feature id for the '<em><b>Group</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__GROUP = 8;

    /**
     * The feature id for the '<em><b>Organization</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__ORGANIZATION = 9;

    /**
     * The feature id for the '<em><b>Resource Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__RESOURCE_ATTRIBUTE = 10;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__SYSTEM_ACTION = 11;

    /**
     * The feature id for the '<em><b>Resource</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__RESOURCE = 12;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__NAME = 13;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE__VERSION = 14;

    /**
     * The number of structural features of the '<em>Model Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MODEL_TYPE_FEATURE_COUNT = 15;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl <em>Organization</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.OrganizationImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrganization()
     * @generated
     */
    int ORGANIZATION = 17;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DESCRIPTION = TYPED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ID = TYPED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__LABEL = TYPED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__NAME = TYPED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ATTRIBUTE_VALUE = TYPED_ENTITY__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__DEFINITION = TYPED_ENTITY__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__CHOICE_GROUP = TYPED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Org Unit</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ORG_UNIT = TYPED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__SYSTEM_ACTION = TYPED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__ALLOC_METHOD = TYPED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__LOCATION = TYPED_ENTITY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION__PLUGIN = TYPED_ENTITY_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Organization</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_FEATURE_COUNT = TYPED_ENTITY_FEATURE_COUNT + 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.OrganizationTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrganizationType()
     * @generated
     */
    int ORGANIZATION_TYPE = 18;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__DESCRIPTION = ENTITY_TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ID = ENTITY_TYPE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__LABEL = ENTITY_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__NAME = ENTITY_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ATTRIBUTE = ENTITY_TYPE__ATTRIBUTE;

    /**
     * The feature id for the '<em><b>Org Unit Feature</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE__ORG_UNIT_FEATURE = ENTITY_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Organization Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORGANIZATION_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitImpl <em>Org Unit</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.OrgUnitImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnit()
     * @generated
     */
    int ORG_UNIT = 19;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__DESCRIPTION = ORG_UNIT_BASE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ID = ORG_UNIT_BASE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__LABEL = ORG_UNIT_BASE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__NAME = ORG_UNIT_BASE__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ATTRIBUTE_VALUE = ORG_UNIT_BASE__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__DEFINITION = ORG_UNIT_BASE__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__CHOICE_GROUP = ORG_UNIT_BASE__CHOICE_GROUP;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__PRIVILEGE_HELD = ORG_UNIT_BASE__PRIVILEGE_HELD;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__SYSTEM_ACTION = ORG_UNIT_BASE__SYSTEM_ACTION;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ALLOC_METHOD = ORG_UNIT_BASE__ALLOC_METHOD;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__FEATURE = ORG_UNIT_BASE__FEATURE;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__LOCATION = ORG_UNIT_BASE__LOCATION;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__PLUGIN = ORG_UNIT_BASE__PLUGIN;

    /**
     * The feature id for the '<em><b>Model Template Ref</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__MODEL_TEMPLATE_REF = ORG_UNIT_BASE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Position</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__POSITION = ORG_UNIT_BASE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Org Unit</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT__ORG_UNIT = ORG_UNIT_BASE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Org Unit</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_FEATURE_COUNT = ORG_UNIT_BASE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl <em>Org Unit Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnitType()
     * @generated
     */
    int ORG_UNIT_TYPE = 21;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__DESCRIPTION = ENTITY_TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ID = ENTITY_TYPE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__LABEL = ENTITY_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__NAME = ENTITY_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ATTRIBUTE = ENTITY_TYPE__ATTRIBUTE;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__CHOICE_GROUP = ENTITY_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Position Feature</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__POSITION_FEATURE = ENTITY_TYPE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Org Unit Feature</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE__ORG_UNIT_FEATURE = ENTITY_TYPE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Org Unit Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ORG_UNIT_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PositionImpl <em>Position</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PositionImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPosition()
     * @generated
     */
    int POSITION = 22;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__DESCRIPTION = TYPED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__ID = TYPED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__LABEL = TYPED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__NAME = TYPED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__ATTRIBUTE_VALUE = TYPED_ENTITY__ATTRIBUTE_VALUE;

    /**
     * The feature id for the '<em><b>Definition</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__DEFINITION = TYPED_ENTITY__DEFINITION;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__CHOICE_GROUP = TYPED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Req Capability</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__REQ_CAPABILITY = TYPED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Privilege Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__PRIVILEGE_HELD = TYPED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>System Action</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__SYSTEM_ACTION = TYPED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Alloc Method</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__ALLOC_METHOD = TYPED_ENTITY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__FEATURE = TYPED_ENTITY_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Ideal Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__IDEAL_NUMBER = TYPED_ENTITY_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__LOCATION = TYPED_ENTITY_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Plugin</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION__PLUGIN = TYPED_ENTITY_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Position</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_FEATURE_COUNT = TYPED_ENTITY_FEATURE_COUNT + 9;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PositionHoldingImpl <em>Position Holding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PositionHoldingImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPositionHolding()
     * @generated
     */
    int POSITION_HOLDING = 23;

    /**
     * The feature id for the '<em><b>Position</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_HOLDING__POSITION = 0;

    /**
     * The number of structural features of the '<em>Position Holding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_HOLDING_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PositionTypeImpl <em>Position Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PositionTypeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPositionType()
     * @generated
     */
    int POSITION_TYPE = 24;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__DESCRIPTION = ENTITY_TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__ID = ENTITY_TYPE__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__LABEL = ENTITY_TYPE__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__NAME = ENTITY_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE__ATTRIBUTE = ENTITY_TYPE__ATTRIBUTE;

    /**
     * The number of structural features of the '<em>Position Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POSITION_TYPE_FEATURE_COUNT = ENTITY_TYPE_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeImpl <em>Privilege</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PrivilegeImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilege()
     * @generated
     */
    int PRIVILEGE = 25;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE__QUALIFIER = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Privilege</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl <em>Privilege Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilegeCategory()
     * @generated
     */
    int PRIVILEGE_CATEGORY = 26;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__DESCRIPTION = NAMED_ENTITY__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__ID = NAMED_ENTITY__ID;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__LABEL = NAMED_ENTITY__LABEL;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__NAME = NAMED_ENTITY__NAME;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__GROUP = NAMED_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Privilege Category</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY = NAMED_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Privilege</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY__PRIVILEGE = NAMED_ENTITY_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Privilege Category</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_CATEGORY_FEATURE_COUNT = NAMED_ENTITY_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeHoldingImpl <em>Privilege Holding</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.PrivilegeHoldingImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilegeHolding()
     * @generated
     */
    int PRIVILEGE_HOLDING = 27;

    /**
     * The feature id for the '<em><b>String</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__STRING = QUALIFIED_HOLDING__STRING;

    /**
     * The feature id for the '<em><b>Integer</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__INTEGER = QUALIFIED_HOLDING__INTEGER;

    /**
     * The feature id for the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__DECIMAL = QUALIFIED_HOLDING__DECIMAL;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__DATE = QUALIFIED_HOLDING__DATE;

    /**
     * The feature id for the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__TIME = QUALIFIED_HOLDING__TIME;

    /**
     * The feature id for the '<em><b>Date Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__DATE_TIME = QUALIFIED_HOLDING__DATE_TIME;

    /**
     * The feature id for the '<em><b>Boolean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__BOOLEAN = QUALIFIED_HOLDING__BOOLEAN;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__ENUM = QUALIFIED_HOLDING__ENUM;

    /**
     * The feature id for the '<em><b>Enum Set</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__ENUM_SET = QUALIFIED_HOLDING__ENUM_SET;

    /**
     * The feature id for the '<em><b>Element</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__ELEMENT = QUALIFIED_HOLDING__ELEMENT;

    /**
     * The feature id for the '<em><b>Privilege</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING__PRIVILEGE = QUALIFIED_HOLDING_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Privilege Holding</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PRIVILEGE_HOLDING_FEATURE_COUNT = QUALIFIED_HOLDING_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.QualifierImpl <em>Qualifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.QualifierImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getQualifier()
     * @generated
     */
    int QUALIFIER = 29;

    /**
     * The feature id for the '<em><b>Enum</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIER__ENUM = 0;

    /**
     * The feature id for the '<em><b>Data Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIER__DATA_TYPE = 1;

    /**
     * The number of structural features of the '<em>Qualifier</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int QUALIFIER_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.ResourceImpl <em>Resource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.ResourceImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResource()
     * @generated
     */
    int RESOURCE = 30;

    /**
     * The feature id for the '<em><b>Choice Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__CHOICE_GROUP = 0;

    /**
     * The feature id for the '<em><b>Capability Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__CAPABILITY_HELD = 1;

    /**
     * The feature id for the '<em><b>Position Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__POSITION_HELD = 2;

    /**
     * The feature id for the '<em><b>Group Held</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__GROUP_HELD = 3;

    /**
     * The feature id for the '<em><b>Attribute Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__ATTRIBUTE_VALUE = 4;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__END_DATE = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__ID = 6;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LABEL = 7;

    /**
     * The feature id for the '<em><b>Ldap Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LDAP_ALIAS = 8;

    /**
     * The feature id for the '<em><b>Ldap Dn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LDAP_DN = 9;

    /**
     * The feature id for the '<em><b>Location</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__LOCATION = 10;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__NAME = 11;

    /**
     * The feature id for the '<em><b>Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__RESOURCE_TYPE = 12;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE__START_DATE = 13;

    /**
     * The number of structural features of the '<em>Resource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RESOURCE_FEATURE_COUNT = 14;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.impl.SystemActionImpl <em>System Action</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.impl.SystemActionImpl
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getSystemAction()
     * @generated
     */
    int SYSTEM_ACTION = 31;

    /**
     * The feature id for the '<em><b>Req Privilege</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__REQ_PRIVILEGE = 0;

    /**
     * The feature id for the '<em><b>Component</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__COMPONENT = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION__NAME = 2;

    /**
     * The number of structural features of the '<em>System Action</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SYSTEM_ACTION_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.AllocationMethod <em>Allocation Method</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAllocationMethod()
     * @generated
     */
    int ALLOCATION_METHOD = 33;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.DataType <em>Data Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.DataType
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDataType()
     * @generated
     */
    int DATA_TYPE = 34;

    /**
     * The meta object id for the '{@link com.tibco.n2.directory.model.de.ResourceType <em>Resource Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResourceType()
     * @generated
     */
    int RESOURCE_TYPE = 35;

    /**
     * The meta object id for the '<em>Allocation Method Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAllocationMethodObject()
     * @generated
     */
    int ALLOCATION_METHOD_OBJECT = 36;

    /**
     * The meta object id for the '<em>Data Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.DataType
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDataTypeObject()
     * @generated
     */
    int DATA_TYPE_OBJECT = 37;

    /**
     * The meta object id for the '<em>Resource Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResourceTypeObject()
     * @generated
     */
    int RESOURCE_TYPE_OBJECT = 38;

    /**
     * The meta object id for the '<em>Version Number</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getVersionNumber()
     * @generated
     */
    int VERSION_NUMBER = 39;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Attribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute
     * @generated
     */
    EClass getAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getString <em>String</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>String</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getString()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_String();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getInteger <em>Integer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Integer</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getInteger()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Integer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getDecimal <em>Decimal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Decimal</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getDecimal()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Decimal();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getDate <em>Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getDate()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Date();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getTime <em>Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getTime()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Time();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getDateTime <em>Date Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date Time</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getDateTime()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_DateTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#isBoolean <em>Boolean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Boolean</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#isBoolean()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Boolean();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Attribute#getEnum <em>Enum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enum</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getEnum()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Enum();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Attribute#getEnumSet <em>Enum Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Enum Set</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getEnumSet()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_EnumSet();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Attribute#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Element</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getElement()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Element();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Attribute#getDefinition <em>Definition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Definition</em>'.
     * @see com.tibco.n2.directory.model.de.Attribute#getDefinition()
     * @see #getAttribute()
     * @generated
     */
    EReference getAttribute_Definition();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.AttributeType <em>Attribute Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Type</em>'.
     * @see com.tibco.n2.directory.model.de.AttributeType
     * @generated
     */
    EClass getAttributeType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.AttributeType#getEnum <em>Enum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Enum</em>'.
     * @see com.tibco.n2.directory.model.de.AttributeType#getEnum()
     * @see #getAttributeType()
     * @generated
     */
    EAttribute getAttributeType_Enum();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.AttributeType#getDataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Data Type</em>'.
     * @see com.tibco.n2.directory.model.de.AttributeType#getDataType()
     * @see #getAttributeType()
     * @generated
     */
    EAttribute getAttributeType_DataType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Capability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability</em>'.
     * @see com.tibco.n2.directory.model.de.Capability
     * @generated
     */
    EClass getCapability();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.directory.model.de.Capability#getQualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Qualifier</em>'.
     * @see com.tibco.n2.directory.model.de.Capability#getQualifier()
     * @see #getCapability()
     * @generated
     */
    EReference getCapability_Qualifier();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.CapabilityCategory <em>Capability Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability Category</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityCategory
     * @generated
     */
    EClass getCapabilityCategory();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.CapabilityCategory#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityCategory#getGroup()
     * @see #getCapabilityCategory()
     * @generated
     */
    EAttribute getCapabilityCategory_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.CapabilityCategory#getCapabilityCategory <em>Capability Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability Category</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityCategory#getCapabilityCategory()
     * @see #getCapabilityCategory()
     * @generated
     */
    EReference getCapabilityCategory_CapabilityCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.CapabilityCategory#getCapability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityCategory#getCapability()
     * @see #getCapabilityCategory()
     * @generated
     */
    EReference getCapabilityCategory_Capability();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.CapabilityHolding <em>Capability Holding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Capability Holding</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityHolding
     * @generated
     */
    EClass getCapabilityHolding();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.CapabilityHolding#getCapability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Capability</em>'.
     * @see com.tibco.n2.directory.model.de.CapabilityHolding#getCapability()
     * @see #getCapabilityHolding()
     * @generated
     */
    EReference getCapabilityHolding_Capability();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.directory.model.de.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.directory.model.de.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.directory.model.de.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.directory.model.de.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.directory.model.de.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.directory.model.de.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.directory.model.de.DocumentRoot#getModel <em>Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Model</em>'.
     * @see com.tibco.n2.directory.model.de.DocumentRoot#getModel()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Model();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.EntityType <em>Entity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Entity Type</em>'.
     * @see com.tibco.n2.directory.model.de.EntityType
     * @generated
     */
    EClass getEntityType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.EntityType#getAttribute <em>Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute</em>'.
     * @see com.tibco.n2.directory.model.de.EntityType#getAttribute()
     * @see #getEntityType()
     * @generated
     */
    EReference getEntityType_Attribute();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Feature <em>Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Feature</em>'.
     * @see com.tibco.n2.directory.model.de.Feature
     * @generated
     */
    EClass getFeature();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Feature#getDefinition <em>Definition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Definition</em>'.
     * @see com.tibco.n2.directory.model.de.Feature#getDefinition()
     * @see #getFeature()
     * @generated
     */
    EReference getFeature_Definition();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Feature#getLowerBound <em>Lower Bound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower Bound</em>'.
     * @see com.tibco.n2.directory.model.de.Feature#getLowerBound()
     * @see #getFeature()
     * @generated
     */
    EAttribute getFeature_LowerBound();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Feature#getUpperBound <em>Upper Bound</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper Bound</em>'.
     * @see com.tibco.n2.directory.model.de.Feature#getUpperBound()
     * @see #getFeature()
     * @generated
     */
    EAttribute getFeature_UpperBound();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.Group
     * @generated
     */
    EClass getGroup();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Group#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getChoiceGroup()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Group#getReqCapability <em>Req Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Req Capability</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getReqCapability()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_ReqCapability();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Group#getPrivilegeHeld <em>Privilege Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Held</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getPrivilegeHeld()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_PrivilegeHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Group#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getGroup()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Group#getSystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getSystemAction()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_SystemAction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Group#getAllocMethod <em>Alloc Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alloc Method</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getAllocMethod()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_AllocMethod();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Group#getPlugin <em>Plugin</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin</em>'.
     * @see com.tibco.n2.directory.model.de.Group#getPlugin()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_Plugin();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Group#isUndeliveredQueue <em>Undelivered Queue</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Undelivered Queue</em>'.
     * @see com.tibco.n2.directory.model.de.Group#isUndeliveredQueue()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_UndeliveredQueue();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.GroupHolding <em>Group Holding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Group Holding</em>'.
     * @see com.tibco.n2.directory.model.de.GroupHolding
     * @generated
     */
    EClass getGroupHolding();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.GroupHolding#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.GroupHolding#getGroup()
     * @see #getGroupHolding()
     * @generated
     */
    EReference getGroupHolding_Group();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Location <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.Location
     * @generated
     */
    EClass getLocation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Location#getLocale <em>Locale</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Locale</em>'.
     * @see com.tibco.n2.directory.model.de.Location#getLocale()
     * @see #getLocation()
     * @generated
     */
    EAttribute getLocation_Locale();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Location#getTimezone <em>Timezone</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timezone</em>'.
     * @see com.tibco.n2.directory.model.de.Location#getTimezone()
     * @see #getLocation()
     * @generated
     */
    EAttribute getLocation_Timezone();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.LocationType <em>Location Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Location Type</em>'.
     * @see com.tibco.n2.directory.model.de.LocationType
     * @generated
     */
    EClass getLocationType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.LocationType#getLocale <em>Locale</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Locale</em>'.
     * @see com.tibco.n2.directory.model.de.LocationType#getLocale()
     * @see #getLocationType()
     * @generated
     */
    EAttribute getLocationType_Locale();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.LocationType#getTimezone <em>Timezone</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timezone</em>'.
     * @see com.tibco.n2.directory.model.de.LocationType#getTimezone()
     * @see #getLocationType()
     * @generated
     */
    EAttribute getLocationType_Timezone();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.MetaModel <em>Meta Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Meta Model</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel
     * @generated
     */
    EClass getMetaModel();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.MetaModel#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel#getChoiceGroup()
     * @see #getMetaModel()
     * @generated
     */
    EAttribute getMetaModel_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.MetaModel#getLocationType <em>Location Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Location Type</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel#getLocationType()
     * @see #getMetaModel()
     * @generated
     */
    EReference getMetaModel_LocationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.MetaModel#getPositionType <em>Position Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position Type</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel#getPositionType()
     * @see #getMetaModel()
     * @generated
     */
    EReference getMetaModel_PositionType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.MetaModel#getOrgUnitType <em>Org Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Type</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel#getOrgUnitType()
     * @see #getMetaModel()
     * @generated
     */
    EReference getMetaModel_OrgUnitType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.MetaModel#getOrganizationType <em>Organization Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Organization Type</em>'.
     * @see com.tibco.n2.directory.model.de.MetaModel#getOrganizationType()
     * @see #getMetaModel()
     * @generated
     */
    EReference getMetaModel_OrganizationType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.ModelOrgUnit <em>Model Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Org Unit</em>'.
     * @see com.tibco.n2.directory.model.de.ModelOrgUnit
     * @generated
     */
    EClass getModelOrgUnit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelOrgUnit#getModelPosition <em>Model Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Model Position</em>'.
     * @see com.tibco.n2.directory.model.de.ModelOrgUnit#getModelPosition()
     * @see #getModelOrgUnit()
     * @generated
     */
    EReference getModelOrgUnit_ModelPosition();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelOrgUnit#getModelOrgUnit <em>Model Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Model Org Unit</em>'.
     * @see com.tibco.n2.directory.model.de.ModelOrgUnit#getModelOrgUnit()
     * @see #getModelOrgUnit()
     * @generated
     */
    EReference getModelOrgUnit_ModelOrgUnit();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.ModelTemplate <em>Model Template</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Template</em>'.
     * @see com.tibco.n2.directory.model.de.ModelTemplate
     * @generated
     */
    EClass getModelTemplate();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.ModelTemplate#getInstanceIdAttribute <em>Instance Id Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Instance Id Attribute</em>'.
     * @see com.tibco.n2.directory.model.de.ModelTemplate#getInstanceIdAttribute()
     * @see #getModelTemplate()
     * @generated
     */
    EAttribute getModelTemplate_InstanceIdAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.ModelType <em>Model Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Model Type</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType
     * @generated
     */
    EClass getModelType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.directory.model.de.ModelType#getMetaModel <em>Meta Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Meta Model</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getMetaModel()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_MetaModel();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.ModelType#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getChoiceGroup()
     * @see #getModelType()
     * @generated
     */
    EAttribute getModelType_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getModelTemplate <em>Model Template</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Model Template</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getModelTemplate()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_ModelTemplate();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getCapability <em>Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getCapability()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Capability();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getCapabilityCategory <em>Capability Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability Category</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getCapabilityCategory()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_CapabilityCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getPrivilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getPrivilege()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Privilege();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getPrivilegeCategory <em>Privilege Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Category</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getPrivilegeCategory()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_PrivilegeCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getLocation()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Location();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getGroup()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getOrganization <em>Organization</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Organization</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getOrganization()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Organization();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getResourceAttribute <em>Resource Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource Attribute</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getResourceAttribute()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_ResourceAttribute();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getSystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getSystemAction()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_SystemAction();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.ModelType#getResource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Resource</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getResource()
     * @see #getModelType()
     * @generated
     */
    EReference getModelType_Resource();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.ModelType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getName()
     * @see #getModelType()
     * @generated
     */
    EAttribute getModelType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.ModelType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.directory.model.de.ModelType#getVersion()
     * @see #getModelType()
     * @generated
     */
    EAttribute getModelType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.NamedEntity <em>Named Entity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Entity</em>'.
     * @see com.tibco.n2.directory.model.de.NamedEntity
     * @generated
     */
    EClass getNamedEntity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.NamedEntity#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.directory.model.de.NamedEntity#getDescription()
     * @see #getNamedEntity()
     * @generated
     */
    EAttribute getNamedEntity_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.NamedEntity#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.directory.model.de.NamedEntity#getId()
     * @see #getNamedEntity()
     * @generated
     */
    EAttribute getNamedEntity_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.NamedEntity#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see com.tibco.n2.directory.model.de.NamedEntity#getLabel()
     * @see #getNamedEntity()
     * @generated
     */
    EAttribute getNamedEntity_Label();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.NamedEntity#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.directory.model.de.NamedEntity#getName()
     * @see #getNamedEntity()
     * @generated
     */
    EAttribute getNamedEntity_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Organization <em>Organization</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Organization</em>'.
     * @see com.tibco.n2.directory.model.de.Organization
     * @generated
     */
    EClass getOrganization();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Organization#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getChoiceGroup()
     * @see #getOrganization()
     * @generated
     */
    EAttribute getOrganization_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Organization#getOrgUnit <em>Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getOrgUnit()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_OrgUnit();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Organization#getSystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getSystemAction()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_SystemAction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Organization#getAllocMethod <em>Alloc Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alloc Method</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getAllocMethod()
     * @see #getOrganization()
     * @generated
     */
    EAttribute getOrganization_AllocMethod();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Organization#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getLocation()
     * @see #getOrganization()
     * @generated
     */
    EReference getOrganization_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Organization#getPlugin <em>Plugin</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin</em>'.
     * @see com.tibco.n2.directory.model.de.Organization#getPlugin()
     * @see #getOrganization()
     * @generated
     */
    EAttribute getOrganization_Plugin();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.OrganizationType <em>Organization Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Organization Type</em>'.
     * @see com.tibco.n2.directory.model.de.OrganizationType
     * @generated
     */
    EClass getOrganizationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrganizationType#getOrgUnitFeature <em>Org Unit Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Feature</em>'.
     * @see com.tibco.n2.directory.model.de.OrganizationType#getOrgUnitFeature()
     * @see #getOrganizationType()
     * @generated
     */
    EReference getOrganizationType_OrgUnitFeature();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.OrgUnit <em>Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnit
     * @generated
     */
    EClass getOrgUnit();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.OrgUnit#getModelTemplateRef <em>Model Template Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Model Template Ref</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnit#getModelTemplateRef()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_ModelTemplateRef();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnit#getPosition <em>Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnit#getPosition()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_Position();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnit#getOrgUnit <em>Org Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnit#getOrgUnit()
     * @see #getOrgUnit()
     * @generated
     */
    EReference getOrgUnit_OrgUnit();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.OrgUnitBase <em>Org Unit Base</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Base</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase
     * @generated
     */
    EClass getOrgUnitBase();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getChoiceGroup()
     * @see #getOrgUnitBase()
     * @generated
     */
    EAttribute getOrgUnitBase_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getPrivilegeHeld <em>Privilege Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Held</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getPrivilegeHeld()
     * @see #getOrgUnitBase()
     * @generated
     */
    EReference getOrgUnitBase_PrivilegeHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getSystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getSystemAction()
     * @see #getOrgUnitBase()
     * @generated
     */
    EReference getOrgUnitBase_SystemAction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getAllocMethod <em>Alloc Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alloc Method</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getAllocMethod()
     * @see #getOrgUnitBase()
     * @generated
     */
    EAttribute getOrgUnitBase_AllocMethod();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getFeature <em>Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getFeature()
     * @see #getOrgUnitBase()
     * @generated
     */
    EReference getOrgUnitBase_Feature();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getLocation()
     * @see #getOrgUnitBase()
     * @generated
     */
    EReference getOrgUnitBase_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.OrgUnitBase#getPlugin <em>Plugin</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitBase#getPlugin()
     * @see #getOrgUnitBase()
     * @generated
     */
    EAttribute getOrgUnitBase_Plugin();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.OrgUnitType <em>Org Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Org Unit Type</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitType
     * @generated
     */
    EClass getOrgUnitType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.OrgUnitType#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitType#getChoiceGroup()
     * @see #getOrgUnitType()
     * @generated
     */
    EAttribute getOrgUnitType_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnitType#getPositionFeature <em>Position Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position Feature</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitType#getPositionFeature()
     * @see #getOrgUnitType()
     * @generated
     */
    EReference getOrgUnitType_PositionFeature();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.OrgUnitType#getOrgUnitFeature <em>Org Unit Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Org Unit Feature</em>'.
     * @see com.tibco.n2.directory.model.de.OrgUnitType#getOrgUnitFeature()
     * @see #getOrgUnitType()
     * @generated
     */
    EReference getOrgUnitType_OrgUnitFeature();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Position <em>Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Position</em>'.
     * @see com.tibco.n2.directory.model.de.Position
     * @generated
     */
    EClass getPosition();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Position#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getChoiceGroup()
     * @see #getPosition()
     * @generated
     */
    EAttribute getPosition_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Position#getReqCapability <em>Req Capability</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Req Capability</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getReqCapability()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_ReqCapability();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Position#getPrivilegeHeld <em>Privilege Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Held</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getPrivilegeHeld()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_PrivilegeHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Position#getSystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getSystemAction()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_SystemAction();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Position#getAllocMethod <em>Alloc Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Alloc Method</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getAllocMethod()
     * @see #getPosition()
     * @generated
     */
    EAttribute getPosition_AllocMethod();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Position#getFeature <em>Feature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Feature</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getFeature()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_Feature();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Position#getIdealNumber <em>Ideal Number</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ideal Number</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getIdealNumber()
     * @see #getPosition()
     * @generated
     */
    EAttribute getPosition_IdealNumber();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Position#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getLocation()
     * @see #getPosition()
     * @generated
     */
    EReference getPosition_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Position#getPlugin <em>Plugin</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin</em>'.
     * @see com.tibco.n2.directory.model.de.Position#getPlugin()
     * @see #getPosition()
     * @generated
     */
    EAttribute getPosition_Plugin();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.PositionHolding <em>Position Holding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Position Holding</em>'.
     * @see com.tibco.n2.directory.model.de.PositionHolding
     * @generated
     */
    EClass getPositionHolding();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.PositionHolding#getPosition <em>Position</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Position</em>'.
     * @see com.tibco.n2.directory.model.de.PositionHolding#getPosition()
     * @see #getPositionHolding()
     * @generated
     */
    EReference getPositionHolding_Position();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.PositionType <em>Position Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Position Type</em>'.
     * @see com.tibco.n2.directory.model.de.PositionType
     * @generated
     */
    EClass getPositionType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Privilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege</em>'.
     * @see com.tibco.n2.directory.model.de.Privilege
     * @generated
     */
    EClass getPrivilege();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.directory.model.de.Privilege#getQualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Qualifier</em>'.
     * @see com.tibco.n2.directory.model.de.Privilege#getQualifier()
     * @see #getPrivilege()
     * @generated
     */
    EReference getPrivilege_Qualifier();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.PrivilegeCategory <em>Privilege Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege Category</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeCategory
     * @generated
     */
    EClass getPrivilegeCategory();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.PrivilegeCategory#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeCategory#getGroup()
     * @see #getPrivilegeCategory()
     * @generated
     */
    EAttribute getPrivilegeCategory_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.PrivilegeCategory#getPrivilegeCategory <em>Privilege Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege Category</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeCategory#getPrivilegeCategory()
     * @see #getPrivilegeCategory()
     * @generated
     */
    EReference getPrivilegeCategory_PrivilegeCategory();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.PrivilegeCategory#getPrivilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Privilege</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeCategory#getPrivilege()
     * @see #getPrivilegeCategory()
     * @generated
     */
    EReference getPrivilegeCategory_Privilege();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.PrivilegeHolding <em>Privilege Holding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Privilege Holding</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeHolding
     * @generated
     */
    EClass getPrivilegeHolding();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.PrivilegeHolding#getPrivilege <em>Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Privilege</em>'.
     * @see com.tibco.n2.directory.model.de.PrivilegeHolding#getPrivilege()
     * @see #getPrivilegeHolding()
     * @generated
     */
    EReference getPrivilegeHolding_Privilege();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.QualifiedHolding <em>Qualified Holding</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Qualified Holding</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding
     * @generated
     */
    EClass getQualifiedHolding();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getString <em>String</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>String</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getString()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_String();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getInteger <em>Integer</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Integer</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getInteger()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Integer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDecimal <em>Decimal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Decimal</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getDecimal()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Decimal();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDate <em>Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getDate()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Date();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getTime <em>Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getTime()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Time();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getDateTime <em>Date Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date Time</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getDateTime()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_DateTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean <em>Boolean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Boolean</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#isBoolean()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Boolean();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getEnum <em>Enum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enum</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getEnum()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Enum();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getEnumSet <em>Enum Set</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Enum Set</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getEnumSet()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_EnumSet();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.QualifiedHolding#getElement <em>Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Element</em>'.
     * @see com.tibco.n2.directory.model.de.QualifiedHolding#getElement()
     * @see #getQualifiedHolding()
     * @generated
     */
    EAttribute getQualifiedHolding_Element();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Qualifier <em>Qualifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Qualifier</em>'.
     * @see com.tibco.n2.directory.model.de.Qualifier
     * @generated
     */
    EClass getQualifier();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Qualifier#getEnum <em>Enum</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Enum</em>'.
     * @see com.tibco.n2.directory.model.de.Qualifier#getEnum()
     * @see #getQualifier()
     * @generated
     */
    EAttribute getQualifier_Enum();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Qualifier#getDataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Data Type</em>'.
     * @see com.tibco.n2.directory.model.de.Qualifier#getDataType()
     * @see #getQualifier()
     * @generated
     */
    EAttribute getQualifier_DataType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.Resource <em>Resource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Resource</em>'.
     * @see com.tibco.n2.directory.model.de.Resource
     * @generated
     */
    EClass getResource();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.directory.model.de.Resource#getChoiceGroup <em>Choice Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Choice Group</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getChoiceGroup()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_ChoiceGroup();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Resource#getCapabilityHeld <em>Capability Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Capability Held</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getCapabilityHeld()
     * @see #getResource()
     * @generated
     */
    EReference getResource_CapabilityHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Resource#getPositionHeld <em>Position Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Position Held</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getPositionHeld()
     * @see #getResource()
     * @generated
     */
    EReference getResource_PositionHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Resource#getGroupHeld <em>Group Held</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Group Held</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getGroupHeld()
     * @see #getResource()
     * @generated
     */
    EReference getResource_GroupHeld();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.Resource#getAttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute Value</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getAttributeValue()
     * @see #getResource()
     * @generated
     */
    EReference getResource_AttributeValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getEndDate <em>End Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Date</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getEndDate()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_EndDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getId()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getLabel <em>Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Label</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getLabel()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_Label();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getLdapAlias <em>Ldap Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ldap Alias</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getLdapAlias()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_LdapAlias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getLdapDn <em>Ldap Dn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ldap Dn</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getLdapDn()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_LdapDn();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.Resource#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Location</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getLocation()
     * @see #getResource()
     * @generated
     */
    EReference getResource_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getName()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resource Type</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getResourceType()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_ResourceType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.Resource#getStartDate <em>Start Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Date</em>'.
     * @see com.tibco.n2.directory.model.de.Resource#getStartDate()
     * @see #getResource()
     * @generated
     */
    EAttribute getResource_StartDate();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.SystemAction <em>System Action</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>System Action</em>'.
     * @see com.tibco.n2.directory.model.de.SystemAction
     * @generated
     */
    EClass getSystemAction();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.SystemAction#getReqPrivilege <em>Req Privilege</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Req Privilege</em>'.
     * @see com.tibco.n2.directory.model.de.SystemAction#getReqPrivilege()
     * @see #getSystemAction()
     * @generated
     */
    EReference getSystemAction_ReqPrivilege();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.SystemAction#getComponent <em>Component</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Component</em>'.
     * @see com.tibco.n2.directory.model.de.SystemAction#getComponent()
     * @see #getSystemAction()
     * @generated
     */
    EAttribute getSystemAction_Component();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.directory.model.de.SystemAction#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.directory.model.de.SystemAction#getName()
     * @see #getSystemAction()
     * @generated
     */
    EAttribute getSystemAction_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.directory.model.de.TypedEntity <em>Typed Entity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Typed Entity</em>'.
     * @see com.tibco.n2.directory.model.de.TypedEntity
     * @generated
     */
    EClass getTypedEntity();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.directory.model.de.TypedEntity#getAttributeValue <em>Attribute Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attribute Value</em>'.
     * @see com.tibco.n2.directory.model.de.TypedEntity#getAttributeValue()
     * @see #getTypedEntity()
     * @generated
     */
    EReference getTypedEntity_AttributeValue();

    /**
     * Returns the meta object for the reference '{@link com.tibco.n2.directory.model.de.TypedEntity#getDefinition <em>Definition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Definition</em>'.
     * @see com.tibco.n2.directory.model.de.TypedEntity#getDefinition()
     * @see #getTypedEntity()
     * @generated
     */
    EReference getTypedEntity_Definition();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.directory.model.de.AllocationMethod <em>Allocation Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Allocation Method</em>'.
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @generated
     */
    EEnum getAllocationMethod();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.directory.model.de.DataType <em>Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Data Type</em>'.
     * @see com.tibco.n2.directory.model.de.DataType
     * @generated
     */
    EEnum getDataType();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.directory.model.de.ResourceType <em>Resource Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Resource Type</em>'.
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @generated
     */
    EEnum getResourceType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.directory.model.de.AllocationMethod <em>Allocation Method Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Allocation Method Object</em>'.
     * @see com.tibco.n2.directory.model.de.AllocationMethod
     * @model instanceClass="com.tibco.n2.directory.model.de.AllocationMethod"
     *        extendedMetaData="name='AllocationMethod:Object' baseType='AllocationMethod'"
     * @generated
     */
    EDataType getAllocationMethodObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.directory.model.de.DataType <em>Data Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Data Type Object</em>'.
     * @see com.tibco.n2.directory.model.de.DataType
     * @model instanceClass="com.tibco.n2.directory.model.de.DataType"
     *        extendedMetaData="name='DataType:Object' baseType='DataType'"
     * @generated
     */
    EDataType getDataTypeObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.directory.model.de.ResourceType <em>Resource Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Resource Type Object</em>'.
     * @see com.tibco.n2.directory.model.de.ResourceType
     * @model instanceClass="com.tibco.n2.directory.model.de.ResourceType"
     *        extendedMetaData="name='ResourceType:Object' baseType='ResourceType'"
     * @generated
     */
    EDataType getResourceTypeObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Version Number</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Version Number</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='VersionNumber' baseType='http://www.eclipse.org/emf/2003/XMLType#token'"
     * @generated
     */
    EDataType getVersionNumber();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DeFactory getDeFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.AttributeImpl <em>Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.AttributeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAttribute()
         * @generated
         */
        EClass ATTRIBUTE = eINSTANCE.getAttribute();

        /**
         * The meta object literal for the '<em><b>String</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__STRING = eINSTANCE.getAttribute_String();

        /**
         * The meta object literal for the '<em><b>Integer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__INTEGER = eINSTANCE.getAttribute_Integer();

        /**
         * The meta object literal for the '<em><b>Decimal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DECIMAL = eINSTANCE.getAttribute_Decimal();

        /**
         * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DATE = eINSTANCE.getAttribute_Date();

        /**
         * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__TIME = eINSTANCE.getAttribute_Time();

        /**
         * The meta object literal for the '<em><b>Date Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__DATE_TIME = eINSTANCE.getAttribute_DateTime();

        /**
         * The meta object literal for the '<em><b>Boolean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__BOOLEAN = eINSTANCE.getAttribute_Boolean();

        /**
         * The meta object literal for the '<em><b>Enum</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__ENUM = eINSTANCE.getAttribute_Enum();

        /**
         * The meta object literal for the '<em><b>Enum Set</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__ENUM_SET = eINSTANCE.getAttribute_EnumSet();

        /**
         * The meta object literal for the '<em><b>Element</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE__ELEMENT = eINSTANCE.getAttribute_Element();

        /**
         * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ATTRIBUTE__DEFINITION = eINSTANCE.getAttribute_Definition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.AttributeTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAttributeType()
         * @generated
         */
        EClass ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

        /**
         * The meta object literal for the '<em><b>Enum</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_TYPE__ENUM = eINSTANCE.getAttributeType_Enum();

        /**
         * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_TYPE__DATA_TYPE = eINSTANCE.getAttributeType_DataType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityImpl <em>Capability</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.CapabilityImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapability()
         * @generated
         */
        EClass CAPABILITY = eINSTANCE.getCapability();

        /**
         * The meta object literal for the '<em><b>Qualifier</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY__QUALIFIER = eINSTANCE.getCapability_Qualifier();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl <em>Capability Category</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.CapabilityCategoryImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapabilityCategory()
         * @generated
         */
        EClass CAPABILITY_CATEGORY = eINSTANCE.getCapabilityCategory();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CAPABILITY_CATEGORY__GROUP = eINSTANCE.getCapabilityCategory_Group();

        /**
         * The meta object literal for the '<em><b>Capability Category</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_CATEGORY__CAPABILITY_CATEGORY = eINSTANCE.getCapabilityCategory_CapabilityCategory();

        /**
         * The meta object literal for the '<em><b>Capability</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_CATEGORY__CAPABILITY = eINSTANCE.getCapabilityCategory_Capability();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.CapabilityHoldingImpl <em>Capability Holding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.CapabilityHoldingImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getCapabilityHolding()
         * @generated
         */
        EClass CAPABILITY_HOLDING = eINSTANCE.getCapabilityHolding();

        /**
         * The meta object literal for the '<em><b>Capability</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CAPABILITY_HOLDING__CAPABILITY = eINSTANCE.getCapabilityHolding_Capability();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.DocumentRootImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__MODEL = eINSTANCE.getDocumentRoot_Model();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.EntityTypeImpl <em>Entity Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.EntityTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getEntityType()
         * @generated
         */
        EClass ENTITY_TYPE = eINSTANCE.getEntityType();

        /**
         * The meta object literal for the '<em><b>Attribute</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ENTITY_TYPE__ATTRIBUTE = eINSTANCE.getEntityType_Attribute();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.FeatureImpl <em>Feature</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.FeatureImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getFeature()
         * @generated
         */
        EClass FEATURE = eINSTANCE.getFeature();

        /**
         * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FEATURE__DEFINITION = eINSTANCE.getFeature_Definition();

        /**
         * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FEATURE__LOWER_BOUND = eINSTANCE.getFeature_LowerBound();

        /**
         * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FEATURE__UPPER_BOUND = eINSTANCE.getFeature_UpperBound();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.GroupImpl <em>Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.GroupImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getGroup()
         * @generated
         */
        EClass GROUP = eINSTANCE.getGroup();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__CHOICE_GROUP = eINSTANCE.getGroup_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Req Capability</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__REQ_CAPABILITY = eINSTANCE.getGroup_ReqCapability();

        /**
         * The meta object literal for the '<em><b>Privilege Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__PRIVILEGE_HELD = eINSTANCE.getGroup_PrivilegeHeld();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__GROUP = eINSTANCE.getGroup_Group();

        /**
         * The meta object literal for the '<em><b>System Action</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__SYSTEM_ACTION = eINSTANCE.getGroup_SystemAction();

        /**
         * The meta object literal for the '<em><b>Alloc Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__ALLOC_METHOD = eINSTANCE.getGroup_AllocMethod();

        /**
         * The meta object literal for the '<em><b>Plugin</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__PLUGIN = eINSTANCE.getGroup_Plugin();

        /**
         * The meta object literal for the '<em><b>Undelivered Queue</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__UNDELIVERED_QUEUE = eINSTANCE.getGroup_UndeliveredQueue();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.GroupHoldingImpl <em>Group Holding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.GroupHoldingImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getGroupHolding()
         * @generated
         */
        EClass GROUP_HOLDING = eINSTANCE.getGroupHolding();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP_HOLDING__GROUP = eINSTANCE.getGroupHolding_Group();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.LocationImpl <em>Location</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.LocationImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getLocation()
         * @generated
         */
        EClass LOCATION = eINSTANCE.getLocation();

        /**
         * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION__LOCALE = eINSTANCE.getLocation_Locale();

        /**
         * The meta object literal for the '<em><b>Timezone</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION__TIMEZONE = eINSTANCE.getLocation_Timezone();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.LocationTypeImpl <em>Location Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.LocationTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getLocationType()
         * @generated
         */
        EClass LOCATION_TYPE = eINSTANCE.getLocationType();

        /**
         * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION_TYPE__LOCALE = eINSTANCE.getLocationType_Locale();

        /**
         * The meta object literal for the '<em><b>Timezone</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOCATION_TYPE__TIMEZONE = eINSTANCE.getLocationType_Timezone();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.MetaModelImpl <em>Meta Model</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.MetaModelImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getMetaModel()
         * @generated
         */
        EClass META_MODEL = eINSTANCE.getMetaModel();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute META_MODEL__CHOICE_GROUP = eINSTANCE.getMetaModel_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Location Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_MODEL__LOCATION_TYPE = eINSTANCE.getMetaModel_LocationType();

        /**
         * The meta object literal for the '<em><b>Position Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_MODEL__POSITION_TYPE = eINSTANCE.getMetaModel_PositionType();

        /**
         * The meta object literal for the '<em><b>Org Unit Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_MODEL__ORG_UNIT_TYPE = eINSTANCE.getMetaModel_OrgUnitType();

        /**
         * The meta object literal for the '<em><b>Organization Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference META_MODEL__ORGANIZATION_TYPE = eINSTANCE.getMetaModel_OrganizationType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl <em>Model Org Unit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.ModelOrgUnitImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelOrgUnit()
         * @generated
         */
        EClass MODEL_ORG_UNIT = eINSTANCE.getModelOrgUnit();

        /**
         * The meta object literal for the '<em><b>Model Position</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_ORG_UNIT__MODEL_POSITION = eINSTANCE.getModelOrgUnit_ModelPosition();

        /**
         * The meta object literal for the '<em><b>Model Org Unit</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_ORG_UNIT__MODEL_ORG_UNIT = eINSTANCE.getModelOrgUnit_ModelOrgUnit();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.ModelTemplateImpl <em>Model Template</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.ModelTemplateImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelTemplate()
         * @generated
         */
        EClass MODEL_TEMPLATE = eINSTANCE.getModelTemplate();

        /**
         * The meta object literal for the '<em><b>Instance Id Attribute</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_TEMPLATE__INSTANCE_ID_ATTRIBUTE = eINSTANCE.getModelTemplate_InstanceIdAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl <em>Model Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.ModelTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getModelType()
         * @generated
         */
        EClass MODEL_TYPE = eINSTANCE.getModelType();

        /**
         * The meta object literal for the '<em><b>Meta Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__META_MODEL = eINSTANCE.getModelType_MetaModel();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_TYPE__CHOICE_GROUP = eINSTANCE.getModelType_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Model Template</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__MODEL_TEMPLATE = eINSTANCE.getModelType_ModelTemplate();

        /**
         * The meta object literal for the '<em><b>Capability</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__CAPABILITY = eINSTANCE.getModelType_Capability();

        /**
         * The meta object literal for the '<em><b>Capability Category</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__CAPABILITY_CATEGORY = eINSTANCE.getModelType_CapabilityCategory();

        /**
         * The meta object literal for the '<em><b>Privilege</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__PRIVILEGE = eINSTANCE.getModelType_Privilege();

        /**
         * The meta object literal for the '<em><b>Privilege Category</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__PRIVILEGE_CATEGORY = eINSTANCE.getModelType_PrivilegeCategory();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__LOCATION = eINSTANCE.getModelType_Location();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__GROUP = eINSTANCE.getModelType_Group();

        /**
         * The meta object literal for the '<em><b>Organization</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__ORGANIZATION = eINSTANCE.getModelType_Organization();

        /**
         * The meta object literal for the '<em><b>Resource Attribute</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__RESOURCE_ATTRIBUTE = eINSTANCE.getModelType_ResourceAttribute();

        /**
         * The meta object literal for the '<em><b>System Action</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__SYSTEM_ACTION = eINSTANCE.getModelType_SystemAction();

        /**
         * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MODEL_TYPE__RESOURCE = eINSTANCE.getModelType_Resource();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_TYPE__NAME = eINSTANCE.getModelType_Name();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MODEL_TYPE__VERSION = eINSTANCE.getModelType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.NamedEntityImpl <em>Named Entity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.NamedEntityImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getNamedEntity()
         * @generated
         */
        EClass NAMED_ENTITY = eINSTANCE.getNamedEntity();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ENTITY__DESCRIPTION = eINSTANCE.getNamedEntity_Description();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ENTITY__ID = eINSTANCE.getNamedEntity_Id();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ENTITY__LABEL = eINSTANCE.getNamedEntity_Label();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_ENTITY__NAME = eINSTANCE.getNamedEntity_Name();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl <em>Organization</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.OrganizationImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrganization()
         * @generated
         */
        EClass ORGANIZATION = eINSTANCE.getOrganization();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORGANIZATION__CHOICE_GROUP = eINSTANCE.getOrganization_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Org Unit</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__ORG_UNIT = eINSTANCE.getOrganization_OrgUnit();

        /**
         * The meta object literal for the '<em><b>System Action</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__SYSTEM_ACTION = eINSTANCE.getOrganization_SystemAction();

        /**
         * The meta object literal for the '<em><b>Alloc Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORGANIZATION__ALLOC_METHOD = eINSTANCE.getOrganization_AllocMethod();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION__LOCATION = eINSTANCE.getOrganization_Location();

        /**
         * The meta object literal for the '<em><b>Plugin</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORGANIZATION__PLUGIN = eINSTANCE.getOrganization_Plugin();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.OrganizationTypeImpl <em>Organization Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.OrganizationTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrganizationType()
         * @generated
         */
        EClass ORGANIZATION_TYPE = eINSTANCE.getOrganizationType();

        /**
         * The meta object literal for the '<em><b>Org Unit Feature</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORGANIZATION_TYPE__ORG_UNIT_FEATURE = eINSTANCE.getOrganizationType_OrgUnitFeature();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitImpl <em>Org Unit</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.OrgUnitImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnit()
         * @generated
         */
        EClass ORG_UNIT = eINSTANCE.getOrgUnit();

        /**
         * The meta object literal for the '<em><b>Model Template Ref</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__MODEL_TEMPLATE_REF = eINSTANCE.getOrgUnit_ModelTemplateRef();

        /**
         * The meta object literal for the '<em><b>Position</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__POSITION = eINSTANCE.getOrgUnit_Position();

        /**
         * The meta object literal for the '<em><b>Org Unit</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT__ORG_UNIT = eINSTANCE.getOrgUnit_OrgUnit();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitBaseImpl <em>Org Unit Base</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.OrgUnitBaseImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnitBase()
         * @generated
         */
        EClass ORG_UNIT_BASE = eINSTANCE.getOrgUnitBase();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_UNIT_BASE__CHOICE_GROUP = eINSTANCE.getOrgUnitBase_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Privilege Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_BASE__PRIVILEGE_HELD = eINSTANCE.getOrgUnitBase_PrivilegeHeld();

        /**
         * The meta object literal for the '<em><b>System Action</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_BASE__SYSTEM_ACTION = eINSTANCE.getOrgUnitBase_SystemAction();

        /**
         * The meta object literal for the '<em><b>Alloc Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_UNIT_BASE__ALLOC_METHOD = eINSTANCE.getOrgUnitBase_AllocMethod();

        /**
         * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_BASE__FEATURE = eINSTANCE.getOrgUnitBase_Feature();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_BASE__LOCATION = eINSTANCE.getOrgUnitBase_Location();

        /**
         * The meta object literal for the '<em><b>Plugin</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_UNIT_BASE__PLUGIN = eINSTANCE.getOrgUnitBase_Plugin();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl <em>Org Unit Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.OrgUnitTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getOrgUnitType()
         * @generated
         */
        EClass ORG_UNIT_TYPE = eINSTANCE.getOrgUnitType();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ORG_UNIT_TYPE__CHOICE_GROUP = eINSTANCE.getOrgUnitType_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Position Feature</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_TYPE__POSITION_FEATURE = eINSTANCE.getOrgUnitType_PositionFeature();

        /**
         * The meta object literal for the '<em><b>Org Unit Feature</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ORG_UNIT_TYPE__ORG_UNIT_FEATURE = eINSTANCE.getOrgUnitType_OrgUnitFeature();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PositionImpl <em>Position</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PositionImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPosition()
         * @generated
         */
        EClass POSITION = eINSTANCE.getPosition();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POSITION__CHOICE_GROUP = eINSTANCE.getPosition_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Req Capability</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__REQ_CAPABILITY = eINSTANCE.getPosition_ReqCapability();

        /**
         * The meta object literal for the '<em><b>Privilege Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__PRIVILEGE_HELD = eINSTANCE.getPosition_PrivilegeHeld();

        /**
         * The meta object literal for the '<em><b>System Action</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__SYSTEM_ACTION = eINSTANCE.getPosition_SystemAction();

        /**
         * The meta object literal for the '<em><b>Alloc Method</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POSITION__ALLOC_METHOD = eINSTANCE.getPosition_AllocMethod();

        /**
         * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__FEATURE = eINSTANCE.getPosition_Feature();

        /**
         * The meta object literal for the '<em><b>Ideal Number</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POSITION__IDEAL_NUMBER = eINSTANCE.getPosition_IdealNumber();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION__LOCATION = eINSTANCE.getPosition_Location();

        /**
         * The meta object literal for the '<em><b>Plugin</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute POSITION__PLUGIN = eINSTANCE.getPosition_Plugin();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PositionHoldingImpl <em>Position Holding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PositionHoldingImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPositionHolding()
         * @generated
         */
        EClass POSITION_HOLDING = eINSTANCE.getPositionHolding();

        /**
         * The meta object literal for the '<em><b>Position</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POSITION_HOLDING__POSITION = eINSTANCE.getPositionHolding_Position();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PositionTypeImpl <em>Position Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PositionTypeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPositionType()
         * @generated
         */
        EClass POSITION_TYPE = eINSTANCE.getPositionType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeImpl <em>Privilege</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PrivilegeImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilege()
         * @generated
         */
        EClass PRIVILEGE = eINSTANCE.getPrivilege();

        /**
         * The meta object literal for the '<em><b>Qualifier</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE__QUALIFIER = eINSTANCE.getPrivilege_Qualifier();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl <em>Privilege Category</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PrivilegeCategoryImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilegeCategory()
         * @generated
         */
        EClass PRIVILEGE_CATEGORY = eINSTANCE.getPrivilegeCategory();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PRIVILEGE_CATEGORY__GROUP = eINSTANCE.getPrivilegeCategory_Group();

        /**
         * The meta object literal for the '<em><b>Privilege Category</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_CATEGORY__PRIVILEGE_CATEGORY = eINSTANCE.getPrivilegeCategory_PrivilegeCategory();

        /**
         * The meta object literal for the '<em><b>Privilege</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_CATEGORY__PRIVILEGE = eINSTANCE.getPrivilegeCategory_Privilege();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.PrivilegeHoldingImpl <em>Privilege Holding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.PrivilegeHoldingImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getPrivilegeHolding()
         * @generated
         */
        EClass PRIVILEGE_HOLDING = eINSTANCE.getPrivilegeHolding();

        /**
         * The meta object literal for the '<em><b>Privilege</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PRIVILEGE_HOLDING__PRIVILEGE = eINSTANCE.getPrivilegeHolding_Privilege();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl <em>Qualified Holding</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.QualifiedHoldingImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getQualifiedHolding()
         * @generated
         */
        EClass QUALIFIED_HOLDING = eINSTANCE.getQualifiedHolding();

        /**
         * The meta object literal for the '<em><b>String</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__STRING = eINSTANCE.getQualifiedHolding_String();

        /**
         * The meta object literal for the '<em><b>Integer</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__INTEGER = eINSTANCE.getQualifiedHolding_Integer();

        /**
         * The meta object literal for the '<em><b>Decimal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__DECIMAL = eINSTANCE.getQualifiedHolding_Decimal();

        /**
         * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__DATE = eINSTANCE.getQualifiedHolding_Date();

        /**
         * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__TIME = eINSTANCE.getQualifiedHolding_Time();

        /**
         * The meta object literal for the '<em><b>Date Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__DATE_TIME = eINSTANCE.getQualifiedHolding_DateTime();

        /**
         * The meta object literal for the '<em><b>Boolean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__BOOLEAN = eINSTANCE.getQualifiedHolding_Boolean();

        /**
         * The meta object literal for the '<em><b>Enum</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__ENUM = eINSTANCE.getQualifiedHolding_Enum();

        /**
         * The meta object literal for the '<em><b>Enum Set</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__ENUM_SET = eINSTANCE.getQualifiedHolding_EnumSet();

        /**
         * The meta object literal for the '<em><b>Element</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIED_HOLDING__ELEMENT = eINSTANCE.getQualifiedHolding_Element();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.QualifierImpl <em>Qualifier</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.QualifierImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getQualifier()
         * @generated
         */
        EClass QUALIFIER = eINSTANCE.getQualifier();

        /**
         * The meta object literal for the '<em><b>Enum</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIER__ENUM = eINSTANCE.getQualifier_Enum();

        /**
         * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute QUALIFIER__DATA_TYPE = eINSTANCE.getQualifier_DataType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.ResourceImpl <em>Resource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.ResourceImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResource()
         * @generated
         */
        EClass RESOURCE = eINSTANCE.getResource();

        /**
         * The meta object literal for the '<em><b>Choice Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__CHOICE_GROUP = eINSTANCE.getResource_ChoiceGroup();

        /**
         * The meta object literal for the '<em><b>Capability Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__CAPABILITY_HELD = eINSTANCE.getResource_CapabilityHeld();

        /**
         * The meta object literal for the '<em><b>Position Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__POSITION_HELD = eINSTANCE.getResource_PositionHeld();

        /**
         * The meta object literal for the '<em><b>Group Held</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__GROUP_HELD = eINSTANCE.getResource_GroupHeld();

        /**
         * The meta object literal for the '<em><b>Attribute Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__ATTRIBUTE_VALUE = eINSTANCE.getResource_AttributeValue();

        /**
         * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__END_DATE = eINSTANCE.getResource_EndDate();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__ID = eINSTANCE.getResource_Id();

        /**
         * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__LABEL = eINSTANCE.getResource_Label();

        /**
         * The meta object literal for the '<em><b>Ldap Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__LDAP_ALIAS = eINSTANCE.getResource_LdapAlias();

        /**
         * The meta object literal for the '<em><b>Ldap Dn</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__LDAP_DN = eINSTANCE.getResource_LdapDn();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference RESOURCE__LOCATION = eINSTANCE.getResource_Location();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__NAME = eINSTANCE.getResource_Name();

        /**
         * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__RESOURCE_TYPE = eINSTANCE.getResource_ResourceType();

        /**
         * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RESOURCE__START_DATE = eINSTANCE.getResource_StartDate();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.SystemActionImpl <em>System Action</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.SystemActionImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getSystemAction()
         * @generated
         */
        EClass SYSTEM_ACTION = eINSTANCE.getSystemAction();

        /**
         * The meta object literal for the '<em><b>Req Privilege</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SYSTEM_ACTION__REQ_PRIVILEGE = eINSTANCE.getSystemAction_ReqPrivilege();

        /**
         * The meta object literal for the '<em><b>Component</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM_ACTION__COMPONENT = eINSTANCE.getSystemAction_Component();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SYSTEM_ACTION__NAME = eINSTANCE.getSystemAction_Name();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.impl.TypedEntityImpl <em>Typed Entity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.impl.TypedEntityImpl
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getTypedEntity()
         * @generated
         */
        EClass TYPED_ENTITY = eINSTANCE.getTypedEntity();

        /**
         * The meta object literal for the '<em><b>Attribute Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPED_ENTITY__ATTRIBUTE_VALUE = eINSTANCE.getTypedEntity_AttributeValue();

        /**
         * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TYPED_ENTITY__DEFINITION = eINSTANCE.getTypedEntity_Definition();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.AllocationMethod <em>Allocation Method</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.AllocationMethod
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAllocationMethod()
         * @generated
         */
        EEnum ALLOCATION_METHOD = eINSTANCE.getAllocationMethod();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.DataType <em>Data Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.DataType
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDataType()
         * @generated
         */
        EEnum DATA_TYPE = eINSTANCE.getDataType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.directory.model.de.ResourceType <em>Resource Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.ResourceType
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResourceType()
         * @generated
         */
        EEnum RESOURCE_TYPE = eINSTANCE.getResourceType();

        /**
         * The meta object literal for the '<em>Allocation Method Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.AllocationMethod
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getAllocationMethodObject()
         * @generated
         */
        EDataType ALLOCATION_METHOD_OBJECT = eINSTANCE.getAllocationMethodObject();

        /**
         * The meta object literal for the '<em>Data Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.DataType
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getDataTypeObject()
         * @generated
         */
        EDataType DATA_TYPE_OBJECT = eINSTANCE.getDataTypeObject();

        /**
         * The meta object literal for the '<em>Resource Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.directory.model.de.ResourceType
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getResourceTypeObject()
         * @generated
         */
        EDataType RESOURCE_TYPE_OBJECT = eINSTANCE.getResourceTypeObject();

        /**
         * The meta object literal for the '<em>Version Number</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.n2.directory.model.de.impl.DePackageImpl#getVersionNumber()
         * @generated
         */
        EDataType VERSION_NUMBER = eINSTANCE.getVersionNumber();

    }

} //DePackage
