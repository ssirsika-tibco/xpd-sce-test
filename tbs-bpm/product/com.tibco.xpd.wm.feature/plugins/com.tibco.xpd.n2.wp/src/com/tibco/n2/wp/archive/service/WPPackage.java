/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.n2.wp.archive.service.WPFactory
 * @model kind="package"
 * @generated
 */
public interface WPPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "service";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://service.archive.wp.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "service";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    WPPackage eINSTANCE = com.tibco.n2.wp.archive.service.impl.WPPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelExtentionTypeImpl <em>Channel Extention Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.ChannelExtentionTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelExtentionType()
     * @generated
     */
    int CHANNEL_EXTENTION_TYPE = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelsTypeImpl <em>Channels Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.ChannelsTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelsType()
     * @generated
     */
    int CHANNELS_TYPE = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelType()
     * @generated
     */
    int CHANNEL_TYPE = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.DocumentRootImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.ExtendedPropertiesTypeImpl <em>Extended Properties Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.ExtendedPropertiesTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getExtendedPropertiesType()
     * @generated
     */
    int EXTENDED_PROPERTIES_TYPE = 5;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl <em>Form Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.FormTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getFormType()
     * @generated
     */
    int FORM_TYPE = 6;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl <em>Page Activity Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageActivityType()
     * @generated
     */
    int PAGE_ACTIVITY_TYPE = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl <em>Page Flow Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageFlowType()
     * @generated
     */
    int PAGE_FLOW_TYPE = 9;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__GROUP = 0;

    /**
     * The feature id for the '<em><b>Page Activity</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__PAGE_ACTIVITY = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__ID = 2;

    /**
     * The feature id for the '<em><b>Module Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__MODULE_NAME = 3;

    /**
     * The feature id for the '<em><b>Module Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__MODULE_VERSION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__NAME = 5;

    /**
     * The feature id for the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE__URL = 6;

    /**
     * The number of structural features of the '<em>Page Flow Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_TYPE_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.BusinessServiceTypeImpl <em>Business Service Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.BusinessServiceTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getBusinessServiceType()
     * @generated
     */
    int BUSINESS_SERVICE_TYPE = 0;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__GROUP = PAGE_FLOW_TYPE__GROUP;

    /**
     * The feature id for the '<em><b>Page Activity</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__PAGE_ACTIVITY = PAGE_FLOW_TYPE__PAGE_ACTIVITY;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__ID = PAGE_FLOW_TYPE__ID;

    /**
     * The feature id for the '<em><b>Module Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__MODULE_NAME = PAGE_FLOW_TYPE__MODULE_NAME;

    /**
     * The feature id for the '<em><b>Module Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__MODULE_VERSION = PAGE_FLOW_TYPE__MODULE_VERSION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__NAME = PAGE_FLOW_TYPE__NAME;

    /**
     * The feature id for the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE__URL = PAGE_FLOW_TYPE__URL;

    /**
     * The number of structural features of the '<em>Business Service Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUSINESS_SERVICE_TYPE_FEATURE_COUNT = PAGE_FLOW_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Filename</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_EXTENTION_TYPE__FILENAME = 0;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_EXTENTION_TYPE__LOCATION = 1;

    /**
     * The number of structural features of the '<em>Channel Extention Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_EXTENTION_TYPE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Channel</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNELS_TYPE__CHANNEL = 0;

    /**
     * The number of structural features of the '<em>Channels Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNELS_TYPE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Target Channel Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__TARGET_CHANNEL_TYPE = 1;

    /**
     * The feature id for the '<em><b>Presentation Channel Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE = 2;

    /**
     * The feature id for the '<em><b>Implementation Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__IMPLEMENTATION_TYPE = 3;

    /**
     * The feature id for the '<em><b>Work Type</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__WORK_TYPE = 4;

    /**
     * The feature id for the '<em><b>Domain</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DOMAIN = 5;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__EXTENDED_PROPERTIES = 6;

    /**
     * The feature id for the '<em><b>Extension Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__EXTENSION_CONFIG = 7;

    /**
     * The feature id for the '<em><b>Business Service</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__BUSINESS_SERVICE = 8;

    /**
     * The feature id for the '<em><b>Page Flow</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__PAGE_FLOW = 9;

    /**
     * The feature id for the '<em><b>Channel Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__CHANNEL_ID = 10;

    /**
     * The feature id for the '<em><b>Default Channel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__DEFAULT_CHANNEL = 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__NAME = 12;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE__VERSION = 13;

    /**
     * The number of structural features of the '<em>Channel Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHANNEL_TYPE_FEATURE_COUNT = 14;

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
     * The feature id for the '<em><b>Service Archive Descriptor</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SERVICE_ARCHIVE_DESCRIPTOR = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The feature id for the '<em><b>Property</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_PROPERTIES_TYPE__PROPERTY = 0;

    /**
     * The number of structural features of the '<em>Extended Properties Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTENDED_PROPERTIES_TYPE_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Form Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__FORM_IDENTIFIER = 0;

    /**
     * The feature id for the '<em><b>Relative Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__RELATIVE_PATH = 1;

    /**
     * The feature id for the '<em><b>Base Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__BASE_PATH = 2;

    /**
     * The feature id for the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__GUID = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__NAME = 4;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE__VERSION = 5;

    /**
     * The number of structural features of the '<em>Form Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FORM_TYPE_FEATURE_COUNT = 6;

    /**
     * The feature id for the '<em><b>Page Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY_TYPE__PAGE_REFERENCE = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY_TYPE__ID = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY_TYPE__NAME = 2;

    /**
     * The number of structural features of the '<em>Page Activity Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.PageFlowRefTypeImpl <em>Page Flow Ref Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.PageFlowRefTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageFlowRefType()
     * @generated
     */
    int PAGE_FLOW_REF_TYPE = 8;

    /**
     * The feature id for the '<em><b>Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_REF_TYPE__REF_ID = 0;

    /**
     * The number of structural features of the '<em>Page Flow Ref Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_FLOW_REF_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.PropertyTypeImpl <em>Property Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.PropertyTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPropertyType()
     * @generated
     */
    int PROPERTY_TYPE = 10;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_TYPE__NAME = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_TYPE__VALUE = 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_TYPE__TYPE = 2;

    /**
     * The number of structural features of the '<em>Property Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROPERTY_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl <em>Service Archive Descriptor Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getServiceArchiveDescriptorType()
     * @generated
     */
    int SERVICE_ARCHIVE_DESCRIPTOR_TYPE = 11;

    /**
     * The feature id for the '<em><b>Channels</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION = 1;

    /**
     * The number of structural features of the '<em>Service Archive Descriptor Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SERVICE_ARCHIVE_DESCRIPTOR_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl <em>Work Type Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl
     * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getWorkTypeType()
     * @generated
     */
    int WORK_TYPE_TYPE = 12;

    /**
     * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__EXTENDED_PROPERTIES = 0;

    /**
     * The feature id for the '<em><b>Form</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__FORM = 1;

    /**
     * The feature id for the '<em><b>Page Flow</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__PAGE_FLOW = 2;

    /**
     * The feature id for the '<em><b>Extension Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__EXTENSION_CONFIG = 3;

    /**
     * The feature id for the '<em><b>Page Flow Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__PAGE_FLOW_REF = 4;

    /**
     * The feature id for the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__GUID = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__NAME = 6;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE__VERSION = 7;

    /**
     * The number of structural features of the '<em>Work Type Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_TYPE_FEATURE_COUNT = 8;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.BusinessServiceType <em>Business Service Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Business Service Type</em>'.
     * @see com.tibco.n2.wp.archive.service.BusinessServiceType
     * @generated
     */
    EClass getBusinessServiceType();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.ChannelExtentionType <em>Channel Extention Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel Extention Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelExtentionType
     * @generated
     */
    EClass getChannelExtentionType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelExtentionType#getFilename <em>Filename</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Filename</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelExtentionType#getFilename()
     * @see #getChannelExtentionType()
     * @generated
     */
    EAttribute getChannelExtentionType_Filename();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelExtentionType#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelExtentionType#getLocation()
     * @see #getChannelExtentionType()
     * @generated
     */
    EAttribute getChannelExtentionType_Location();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.ChannelsType <em>Channels Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channels Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelsType
     * @generated
     */
    EClass getChannelsType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.ChannelsType#getChannel <em>Channel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Channel</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelsType#getChannel()
     * @see #getChannelsType()
     * @generated
     */
    EReference getChannelsType_Channel();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.ChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Channel Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType
     * @generated
     */
    EClass getChannelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getDescription()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType <em>Target Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Target Channel Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_TargetChannelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType <em>Presentation Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Presentation Channel Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_PresentationChannelType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getImplementationType <em>Implementation Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Implementation Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getImplementationType()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_ImplementationType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.ChannelType#getWorkType <em>Work Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getWorkType()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_WorkType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getDomain <em>Domain</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Domain</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getDomain()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_Domain();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties <em>Extended Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extended Properties</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_ExtendedProperties();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig <em>Extension Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extension Config</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_ExtensionConfig();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.ChannelType#getBusinessService <em>Business Service</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Business Service</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getBusinessService()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_BusinessService();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.ChannelType#getPageFlow <em>Page Flow</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Page Flow</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getPageFlow()
     * @see #getChannelType()
     * @generated
     */
    EReference getChannelType_PageFlow();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getChannelId <em>Channel Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Channel Id</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getChannelId()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_ChannelId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel <em>Default Channel</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Channel</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_DefaultChannel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getName()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.ChannelType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.wp.archive.service.ChannelType#getVersion()
     * @see #getChannelType()
     * @generated
     */
    EAttribute getChannelType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.wp.archive.service.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.wp.archive.service.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.wp.archive.service.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.DocumentRoot#getServiceArchiveDescriptor <em>Service Archive Descriptor</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Service Archive Descriptor</em>'.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot#getServiceArchiveDescriptor()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ServiceArchiveDescriptor();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.ExtendedPropertiesType <em>Extended Properties Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Extended Properties Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ExtendedPropertiesType
     * @generated
     */
    EClass getExtendedPropertiesType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.ExtendedPropertiesType#getProperty <em>Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Property</em>'.
     * @see com.tibco.n2.wp.archive.service.ExtendedPropertiesType#getProperty()
     * @see #getExtendedPropertiesType()
     * @generated
     */
    EReference getExtendedPropertiesType_Property();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.FormType <em>Form Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Form Type</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType
     * @generated
     */
    EClass getFormType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getFormIdentifier <em>Form Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Form Identifier</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getFormIdentifier()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_FormIdentifier();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getRelativePath <em>Relative Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Relative Path</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getRelativePath()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_RelativePath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getBasePath <em>Base Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Base Path</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getBasePath()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_BasePath();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getGuid <em>Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Guid</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getGuid()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_Guid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getName()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.FormType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.wp.archive.service.FormType#getVersion()
     * @see #getFormType()
     * @generated
     */
    EAttribute getFormType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.PageActivityType <em>Page Activity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page Activity Type</em>'.
     * @see com.tibco.n2.wp.archive.service.PageActivityType
     * @generated
     */
    EClass getPageActivityType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.PageActivityType#getPageReference <em>Page Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Page Reference</em>'.
     * @see com.tibco.n2.wp.archive.service.PageActivityType#getPageReference()
     * @see #getPageActivityType()
     * @generated
     */
    EReference getPageActivityType_PageReference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageActivityType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.wp.archive.service.PageActivityType#getId()
     * @see #getPageActivityType()
     * @generated
     */
    EAttribute getPageActivityType_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageActivityType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.PageActivityType#getName()
     * @see #getPageActivityType()
     * @generated
     */
    EAttribute getPageActivityType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.PageFlowRefType <em>Page Flow Ref Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page Flow Ref Type</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowRefType
     * @generated
     */
    EClass getPageFlowRefType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowRefType#getRefId <em>Ref Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ref Id</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowRefType#getRefId()
     * @see #getPageFlowRefType()
     * @generated
     */
    EAttribute getPageFlowRefType_RefId();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.PageFlowType <em>Page Flow Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page Flow Type</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType
     * @generated
     */
    EClass getPageFlowType();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.wp.archive.service.PageFlowType#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Group</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getGroup()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_Group();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.wp.archive.service.PageFlowType#getPageActivity <em>Page Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Page Activity</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getPageActivity()
     * @see #getPageFlowType()
     * @generated
     */
    EReference getPageFlowType_PageActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getId()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleName <em>Module Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Module Name</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getModuleName()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_ModuleName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleVersion <em>Module Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Module Version</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getModuleVersion()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_ModuleVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getName()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PageFlowType#getUrl <em>Url</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Url</em>'.
     * @see com.tibco.n2.wp.archive.service.PageFlowType#getUrl()
     * @see #getPageFlowType()
     * @generated
     */
    EAttribute getPageFlowType_Url();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.PropertyType <em>Property Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Property Type</em>'.
     * @see com.tibco.n2.wp.archive.service.PropertyType
     * @generated
     */
    EClass getPropertyType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PropertyType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.PropertyType#getName()
     * @see #getPropertyType()
     * @generated
     */
    EAttribute getPropertyType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PropertyType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.n2.wp.archive.service.PropertyType#getValue()
     * @see #getPropertyType()
     * @generated
     */
    EAttribute getPropertyType_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.PropertyType#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.n2.wp.archive.service.PropertyType#getType()
     * @see #getPropertyType()
     * @generated
     */
    EAttribute getPropertyType_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType <em>Service Archive Descriptor Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Service Archive Descriptor Type</em>'.
     * @see com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType
     * @generated
     */
    EClass getServiceArchiveDescriptorType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getChannels <em>Channels</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Channels</em>'.
     * @see com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getChannels()
     * @see #getServiceArchiveDescriptorType()
     * @generated
     */
    EReference getServiceArchiveDescriptorType_Channels();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Version</em>'.
     * @see com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType#getVersion()
     * @see #getServiceArchiveDescriptorType()
     * @generated
     */
    EReference getServiceArchiveDescriptorType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.wp.archive.service.WorkTypeType <em>Work Type Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Type Type</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType
     * @generated
     */
    EClass getWorkTypeType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties <em>Extended Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extended Properties</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties()
     * @see #getWorkTypeType()
     * @generated
     */
    EReference getWorkTypeType_ExtendedProperties();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getForm <em>Form</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Form</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getForm()
     * @see #getWorkTypeType()
     * @generated
     */
    EReference getWorkTypeType_Form();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlow <em>Page Flow</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Page Flow</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlow()
     * @see #getWorkTypeType()
     * @generated
     */
    EReference getWorkTypeType_PageFlow();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtensionConfig <em>Extension Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Extension Config</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getExtensionConfig()
     * @see #getWorkTypeType()
     * @generated
     */
    EReference getWorkTypeType_ExtensionConfig();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlowRef <em>Page Flow Ref</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Page Flow Ref</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlowRef()
     * @see #getWorkTypeType()
     * @generated
     */
    EReference getWorkTypeType_PageFlowRef();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getGuid <em>Guid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Guid</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getGuid()
     * @see #getWorkTypeType()
     * @generated
     */
    EAttribute getWorkTypeType_Guid();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getName()
     * @see #getWorkTypeType()
     * @generated
     */
    EAttribute getWorkTypeType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType#getVersion()
     * @see #getWorkTypeType()
     * @generated
     */
    EAttribute getWorkTypeType_Version();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    WPFactory getWPFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.BusinessServiceTypeImpl <em>Business Service Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.BusinessServiceTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getBusinessServiceType()
         * @generated
         */
        EClass BUSINESS_SERVICE_TYPE = eINSTANCE.getBusinessServiceType();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelExtentionTypeImpl <em>Channel Extention Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.ChannelExtentionTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelExtentionType()
         * @generated
         */
        EClass CHANNEL_EXTENTION_TYPE = eINSTANCE.getChannelExtentionType();

        /**
         * The meta object literal for the '<em><b>Filename</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_EXTENTION_TYPE__FILENAME = eINSTANCE.getChannelExtentionType_Filename();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_EXTENTION_TYPE__LOCATION = eINSTANCE.getChannelExtentionType_Location();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelsTypeImpl <em>Channels Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.ChannelsTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelsType()
         * @generated
         */
        EClass CHANNELS_TYPE = eINSTANCE.getChannelsType();

        /**
         * The meta object literal for the '<em><b>Channel</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNELS_TYPE__CHANNEL = eINSTANCE.getChannelsType_Channel();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl <em>Channel Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.ChannelTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getChannelType()
         * @generated
         */
        EClass CHANNEL_TYPE = eINSTANCE.getChannelType();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__DESCRIPTION = eINSTANCE.getChannelType_Description();

        /**
         * The meta object literal for the '<em><b>Target Channel Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__TARGET_CHANNEL_TYPE = eINSTANCE.getChannelType_TargetChannelType();

        /**
         * The meta object literal for the '<em><b>Presentation Channel Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE = eINSTANCE.getChannelType_PresentationChannelType();

        /**
         * The meta object literal for the '<em><b>Implementation Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__IMPLEMENTATION_TYPE = eINSTANCE.getChannelType_ImplementationType();

        /**
         * The meta object literal for the '<em><b>Work Type</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__WORK_TYPE = eINSTANCE.getChannelType_WorkType();

        /**
         * The meta object literal for the '<em><b>Domain</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__DOMAIN = eINSTANCE.getChannelType_Domain();

        /**
         * The meta object literal for the '<em><b>Extended Properties</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__EXTENDED_PROPERTIES = eINSTANCE.getChannelType_ExtendedProperties();

        /**
         * The meta object literal for the '<em><b>Extension Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__EXTENSION_CONFIG = eINSTANCE.getChannelType_ExtensionConfig();

        /**
         * The meta object literal for the '<em><b>Business Service</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__BUSINESS_SERVICE = eINSTANCE.getChannelType_BusinessService();

        /**
         * The meta object literal for the '<em><b>Page Flow</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CHANNEL_TYPE__PAGE_FLOW = eINSTANCE.getChannelType_PageFlow();

        /**
         * The meta object literal for the '<em><b>Channel Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__CHANNEL_ID = eINSTANCE.getChannelType_ChannelId();

        /**
         * The meta object literal for the '<em><b>Default Channel</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__DEFAULT_CHANNEL = eINSTANCE.getChannelType_DefaultChannel();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__NAME = eINSTANCE.getChannelType_Name();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHANNEL_TYPE__VERSION = eINSTANCE.getChannelType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.DocumentRootImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Service Archive Descriptor</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SERVICE_ARCHIVE_DESCRIPTOR = eINSTANCE.getDocumentRoot_ServiceArchiveDescriptor();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.ExtendedPropertiesTypeImpl <em>Extended Properties Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.ExtendedPropertiesTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getExtendedPropertiesType()
         * @generated
         */
        EClass EXTENDED_PROPERTIES_TYPE = eINSTANCE.getExtendedPropertiesType();

        /**
         * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXTENDED_PROPERTIES_TYPE__PROPERTY = eINSTANCE.getExtendedPropertiesType_Property();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.FormTypeImpl <em>Form Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.FormTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getFormType()
         * @generated
         */
        EClass FORM_TYPE = eINSTANCE.getFormType();

        /**
         * The meta object literal for the '<em><b>Form Identifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__FORM_IDENTIFIER = eINSTANCE.getFormType_FormIdentifier();

        /**
         * The meta object literal for the '<em><b>Relative Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__RELATIVE_PATH = eINSTANCE.getFormType_RelativePath();

        /**
         * The meta object literal for the '<em><b>Base Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__BASE_PATH = eINSTANCE.getFormType_BasePath();

        /**
         * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__GUID = eINSTANCE.getFormType_Guid();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__NAME = eINSTANCE.getFormType_Name();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FORM_TYPE__VERSION = eINSTANCE.getFormType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl <em>Page Activity Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.PageActivityTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageActivityType()
         * @generated
         */
        EClass PAGE_ACTIVITY_TYPE = eINSTANCE.getPageActivityType();

        /**
         * The meta object literal for the '<em><b>Page Reference</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAGE_ACTIVITY_TYPE__PAGE_REFERENCE = eINSTANCE.getPageActivityType_PageReference();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY_TYPE__ID = eINSTANCE.getPageActivityType_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY_TYPE__NAME = eINSTANCE.getPageActivityType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.PageFlowRefTypeImpl <em>Page Flow Ref Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.PageFlowRefTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageFlowRefType()
         * @generated
         */
        EClass PAGE_FLOW_REF_TYPE = eINSTANCE.getPageFlowRefType();

        /**
         * The meta object literal for the '<em><b>Ref Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_REF_TYPE__REF_ID = eINSTANCE.getPageFlowRefType_RefId();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl <em>Page Flow Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.PageFlowTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPageFlowType()
         * @generated
         */
        EClass PAGE_FLOW_TYPE = eINSTANCE.getPageFlowType();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__GROUP = eINSTANCE.getPageFlowType_Group();

        /**
         * The meta object literal for the '<em><b>Page Activity</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAGE_FLOW_TYPE__PAGE_ACTIVITY = eINSTANCE.getPageFlowType_PageActivity();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__ID = eINSTANCE.getPageFlowType_Id();

        /**
         * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__MODULE_NAME = eINSTANCE.getPageFlowType_ModuleName();

        /**
         * The meta object literal for the '<em><b>Module Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__MODULE_VERSION = eINSTANCE.getPageFlowType_ModuleVersion();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__NAME = eINSTANCE.getPageFlowType_Name();

        /**
         * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_FLOW_TYPE__URL = eINSTANCE.getPageFlowType_Url();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.PropertyTypeImpl <em>Property Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.PropertyTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getPropertyType()
         * @generated
         */
        EClass PROPERTY_TYPE = eINSTANCE.getPropertyType();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROPERTY_TYPE__NAME = eINSTANCE.getPropertyType_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROPERTY_TYPE__VALUE = eINSTANCE.getPropertyType_Value();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROPERTY_TYPE__TYPE = eINSTANCE.getPropertyType_Type();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl <em>Service Archive Descriptor Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.ServiceArchiveDescriptorTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getServiceArchiveDescriptorType()
         * @generated
         */
        EClass SERVICE_ARCHIVE_DESCRIPTOR_TYPE = eINSTANCE.getServiceArchiveDescriptorType();

        /**
         * The meta object literal for the '<em><b>Channels</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS = eINSTANCE.getServiceArchiveDescriptorType_Channels();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION = eINSTANCE.getServiceArchiveDescriptorType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl <em>Work Type Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.wp.archive.service.impl.WorkTypeTypeImpl
         * @see com.tibco.n2.wp.archive.service.impl.WPPackageImpl#getWorkTypeType()
         * @generated
         */
        EClass WORK_TYPE_TYPE = eINSTANCE.getWorkTypeType();

        /**
         * The meta object literal for the '<em><b>Extended Properties</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_TYPE__EXTENDED_PROPERTIES = eINSTANCE.getWorkTypeType_ExtendedProperties();

        /**
         * The meta object literal for the '<em><b>Form</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_TYPE__FORM = eINSTANCE.getWorkTypeType_Form();

        /**
         * The meta object literal for the '<em><b>Page Flow</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_TYPE__PAGE_FLOW = eINSTANCE.getWorkTypeType_PageFlow();

        /**
         * The meta object literal for the '<em><b>Extension Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_TYPE__EXTENSION_CONFIG = eINSTANCE.getWorkTypeType_ExtensionConfig();

        /**
         * The meta object literal for the '<em><b>Page Flow Ref</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_TYPE__PAGE_FLOW_REF = eINSTANCE.getWorkTypeType_PageFlowRef();

        /**
         * The meta object literal for the '<em><b>Guid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_TYPE__GUID = eINSTANCE.getWorkTypeType_Guid();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_TYPE__NAME = eINSTANCE.getWorkTypeType_Name();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_TYPE_TYPE__VERSION = eINSTANCE.getWorkTypeType_Version();

    }

} //WPPackage
