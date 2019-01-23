/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.common.channeltype.ChanneltypePackage;

import com.tibco.n2.common.channeltype.impl.ChanneltypePackageImpl;

import com.tibco.n2.wp.archive.service.BusinessServiceType;
import com.tibco.n2.wp.archive.service.ChannelExtentionType;
import com.tibco.n2.wp.archive.service.ChannelExtention;
import com.tibco.n2.wp.archive.service.ChannelType;
import com.tibco.n2.wp.archive.service.ChannelsType;
import com.tibco.n2.wp.archive.service.DocumentRoot;
import com.tibco.n2.wp.archive.service.ExtendedPropertiesType;
import com.tibco.n2.wp.archive.service.FormType;
import com.tibco.n2.wp.archive.service.PageActivityType;
import com.tibco.n2.wp.archive.service.PageFlowRefType;
import com.tibco.n2.wp.archive.service.PageFlowType;
import com.tibco.n2.wp.archive.service.PropertyType;
import com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType;
import com.tibco.n2.wp.archive.service.WPFactory;
import com.tibco.n2.wp.archive.service.WPPackage;
import com.tibco.n2.wp.archive.service.WorkTypeType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WPPackageImpl extends EPackageImpl implements WPPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass businessServiceTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelExtentionTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelsTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass channelTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass extendedPropertiesTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass formTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pageActivityTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pageFlowRefTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pageFlowTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass propertyTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass serviceArchiveDescriptorTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workTypeTypeEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.n2.wp.archive.service.WPPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private WPPackageImpl() {
        super(eNS_URI, WPFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link WPPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static WPPackage init() {
        if (isInited) return (WPPackage)EPackage.Registry.INSTANCE.getEPackage(WPPackage.eNS_URI);

        // Obtain or create and register package
        WPPackageImpl theWPPackage = (WPPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof WPPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new WPPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        ChanneltypePackageImpl theChanneltypePackage = (ChanneltypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChanneltypePackage.eNS_URI) instanceof ChanneltypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChanneltypePackage.eNS_URI) : ChanneltypePackage.eINSTANCE);

        // Create package meta-data objects
        theWPPackage.createPackageContents();
        theChanneltypePackage.createPackageContents();

        // Initialize created meta-data
        theWPPackage.initializePackageContents();
        theChanneltypePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theWPPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(WPPackage.eNS_URI, theWPPackage);
        return theWPPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getBusinessServiceType() {
        return businessServiceTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelExtentionType() {
        return channelExtentionTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelExtentionType_Filename() {
        return (EAttribute)channelExtentionTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelExtentionType_Location() {
        return (EAttribute)channelExtentionTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelsType() {
        return channelsTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelsType_Channel() {
        return (EReference)channelsTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getChannelType() {
        return channelTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_Description() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_TargetChannelType() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_PresentationChannelType() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_ImplementationType() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_WorkType() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_Domain() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_ExtendedProperties() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_ExtensionConfig() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_BusinessService() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getChannelType_PageFlow() {
        return (EReference)channelTypeEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_ChannelId() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_DefaultChannel() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_Name() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getChannelType_Version() {
        return (EAttribute)channelTypeEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ServiceArchiveDescriptor() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getExtendedPropertiesType() {
        return extendedPropertiesTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getExtendedPropertiesType_Property() {
        return (EReference)extendedPropertiesTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFormType() {
        return formTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_FormIdentifier() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_RelativePath() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_BasePath() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_Guid() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_Name() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFormType_Version() {
        return (EAttribute)formTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPageActivityType() {
        return pageActivityTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPageActivityType_PageReference() {
        return (EReference)pageActivityTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageActivityType_Id() {
        return (EAttribute)pageActivityTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageActivityType_Name() {
        return (EAttribute)pageActivityTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPageFlowRefType() {
        return pageFlowRefTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowRefType_RefId() {
        return (EAttribute)pageFlowRefTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPageFlowType() {
        return pageFlowTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_Group() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPageFlowType_PageActivity() {
        return (EReference)pageFlowTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_Id() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_ModuleName() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_ModuleVersion() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_Name() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPageFlowType_Url() {
        return (EAttribute)pageFlowTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPropertyType() {
        return propertyTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPropertyType_Name() {
        return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPropertyType_Value() {
        return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPropertyType_Type() {
        return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getServiceArchiveDescriptorType() {
        return serviceArchiveDescriptorTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServiceArchiveDescriptorType_Channels() {
        return (EReference)serviceArchiveDescriptorTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getServiceArchiveDescriptorType_Version() {
        return (EReference)serviceArchiveDescriptorTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkTypeType() {
        return workTypeTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeType_ExtendedProperties() {
        return (EReference)workTypeTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeType_Form() {
        return (EReference)workTypeTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeType_PageFlow() {
        return (EReference)workTypeTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeType_ExtensionConfig() {
        return (EReference)workTypeTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeType_PageFlowRef() {
        return (EReference)workTypeTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeType_Guid() {
        return (EAttribute)workTypeTypeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeType_Name() {
        return (EAttribute)workTypeTypeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkTypeType_Version() {
        return (EAttribute)workTypeTypeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WPFactory getWPFactory() {
        return (WPFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        businessServiceTypeEClass = createEClass(BUSINESS_SERVICE_TYPE);

        channelExtentionTypeEClass = createEClass(CHANNEL_EXTENTION_TYPE);
        createEAttribute(channelExtentionTypeEClass, CHANNEL_EXTENTION_TYPE__FILENAME);
        createEAttribute(channelExtentionTypeEClass, CHANNEL_EXTENTION_TYPE__LOCATION);

        channelsTypeEClass = createEClass(CHANNELS_TYPE);
        createEReference(channelsTypeEClass, CHANNELS_TYPE__CHANNEL);

        channelTypeEClass = createEClass(CHANNEL_TYPE);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__DESCRIPTION);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__TARGET_CHANNEL_TYPE);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__PRESENTATION_CHANNEL_TYPE);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__IMPLEMENTATION_TYPE);
        createEReference(channelTypeEClass, CHANNEL_TYPE__WORK_TYPE);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__DOMAIN);
        createEReference(channelTypeEClass, CHANNEL_TYPE__EXTENDED_PROPERTIES);
        createEReference(channelTypeEClass, CHANNEL_TYPE__EXTENSION_CONFIG);
        createEReference(channelTypeEClass, CHANNEL_TYPE__BUSINESS_SERVICE);
        createEReference(channelTypeEClass, CHANNEL_TYPE__PAGE_FLOW);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__CHANNEL_ID);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__DEFAULT_CHANNEL);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__NAME);
        createEAttribute(channelTypeEClass, CHANNEL_TYPE__VERSION);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SERVICE_ARCHIVE_DESCRIPTOR);

        extendedPropertiesTypeEClass = createEClass(EXTENDED_PROPERTIES_TYPE);
        createEReference(extendedPropertiesTypeEClass, EXTENDED_PROPERTIES_TYPE__PROPERTY);

        formTypeEClass = createEClass(FORM_TYPE);
        createEAttribute(formTypeEClass, FORM_TYPE__FORM_IDENTIFIER);
        createEAttribute(formTypeEClass, FORM_TYPE__RELATIVE_PATH);
        createEAttribute(formTypeEClass, FORM_TYPE__BASE_PATH);
        createEAttribute(formTypeEClass, FORM_TYPE__GUID);
        createEAttribute(formTypeEClass, FORM_TYPE__NAME);
        createEAttribute(formTypeEClass, FORM_TYPE__VERSION);

        pageActivityTypeEClass = createEClass(PAGE_ACTIVITY_TYPE);
        createEReference(pageActivityTypeEClass, PAGE_ACTIVITY_TYPE__PAGE_REFERENCE);
        createEAttribute(pageActivityTypeEClass, PAGE_ACTIVITY_TYPE__ID);
        createEAttribute(pageActivityTypeEClass, PAGE_ACTIVITY_TYPE__NAME);

        pageFlowRefTypeEClass = createEClass(PAGE_FLOW_REF_TYPE);
        createEAttribute(pageFlowRefTypeEClass, PAGE_FLOW_REF_TYPE__REF_ID);

        pageFlowTypeEClass = createEClass(PAGE_FLOW_TYPE);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__GROUP);
        createEReference(pageFlowTypeEClass, PAGE_FLOW_TYPE__PAGE_ACTIVITY);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__ID);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__MODULE_NAME);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__MODULE_VERSION);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__NAME);
        createEAttribute(pageFlowTypeEClass, PAGE_FLOW_TYPE__URL);

        propertyTypeEClass = createEClass(PROPERTY_TYPE);
        createEAttribute(propertyTypeEClass, PROPERTY_TYPE__NAME);
        createEAttribute(propertyTypeEClass, PROPERTY_TYPE__VALUE);
        createEAttribute(propertyTypeEClass, PROPERTY_TYPE__TYPE);

        serviceArchiveDescriptorTypeEClass = createEClass(SERVICE_ARCHIVE_DESCRIPTOR_TYPE);
        createEReference(serviceArchiveDescriptorTypeEClass, SERVICE_ARCHIVE_DESCRIPTOR_TYPE__CHANNELS);
        createEReference(serviceArchiveDescriptorTypeEClass, SERVICE_ARCHIVE_DESCRIPTOR_TYPE__VERSION);

        workTypeTypeEClass = createEClass(WORK_TYPE_TYPE);
        createEReference(workTypeTypeEClass, WORK_TYPE_TYPE__EXTENDED_PROPERTIES);
        createEReference(workTypeTypeEClass, WORK_TYPE_TYPE__FORM);
        createEReference(workTypeTypeEClass, WORK_TYPE_TYPE__PAGE_FLOW);
        createEReference(workTypeTypeEClass, WORK_TYPE_TYPE__EXTENSION_CONFIG);
        createEReference(workTypeTypeEClass, WORK_TYPE_TYPE__PAGE_FLOW_REF);
        createEAttribute(workTypeTypeEClass, WORK_TYPE_TYPE__GUID);
        createEAttribute(workTypeTypeEClass, WORK_TYPE_TYPE__NAME);
        createEAttribute(workTypeTypeEClass, WORK_TYPE_TYPE__VERSION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
        ChanneltypePackage theChanneltypePackage = (ChanneltypePackage)EPackage.Registry.INSTANCE.getEPackage(ChanneltypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        businessServiceTypeEClass.getESuperTypes().add(this.getPageFlowType());

        // Initialize classes and features; add operations and parameters
        initEClass(businessServiceTypeEClass, BusinessServiceType.class, "BusinessServiceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(channelExtentionTypeEClass, ChannelExtentionType.class, "ChannelExtentionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getChannelExtentionType_Filename(), theXMLTypePackage.getString(), "filename", null, 1, 1, ChannelExtentionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelExtentionType_Location(), theXMLTypePackage.getString(), "location", null, 1, 1, ChannelExtentionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(channelsTypeEClass, ChannelsType.class, "ChannelsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getChannelsType_Channel(), this.getChannelType(), null, "channel", null, 1, -1, ChannelsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(channelTypeEClass, ChannelType.class, "ChannelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getChannelType_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_TargetChannelType(), theChanneltypePackage.getChannelType(), "targetChannelType", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_PresentationChannelType(), theChanneltypePackage.getPresentationType(), "presentationChannelType", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_ImplementationType(), theChanneltypePackage.getImplementationType(), "implementationType", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_WorkType(), this.getWorkTypeType(), null, "workType", null, 0, -1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_Domain(), theXMLTypePackage.getString(), "domain", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_ExtendedProperties(), this.getExtendedPropertiesType(), null, "extendedProperties", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_ExtensionConfig(), this.getChannelExtentionType(), null, "extensionConfig", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_BusinessService(), this.getBusinessServiceType(), null, "businessService", null, 0, -1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getChannelType_PageFlow(), this.getPageFlowType(), null, "pageFlow", null, 0, -1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_ChannelId(), theXMLTypePackage.getString(), "channelId", null, 1, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_DefaultChannel(), theXMLTypePackage.getBoolean(), "defaultChannel", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getChannelType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, ChannelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_ServiceArchiveDescriptor(), this.getServiceArchiveDescriptorType(), null, "serviceArchiveDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(extendedPropertiesTypeEClass, ExtendedPropertiesType.class, "ExtendedPropertiesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getExtendedPropertiesType_Property(), this.getPropertyType(), null, "property", null, 0, -1, ExtendedPropertiesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(formTypeEClass, FormType.class, "FormType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFormType_FormIdentifier(), theXMLTypePackage.getString(), "formIdentifier", null, 1, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFormType_RelativePath(), theXMLTypePackage.getString(), "relativePath", null, 1, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFormType_BasePath(), theXMLTypePackage.getString(), "basePath", null, 0, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFormType_Guid(), theXMLTypePackage.getString(), "guid", null, 0, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFormType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFormType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, FormType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(pageActivityTypeEClass, PageActivityType.class, "PageActivityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPageActivityType_PageReference(), this.getFormType(), null, "pageReference", null, 1, 1, PageActivityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageActivityType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, PageActivityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageActivityType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PageActivityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(pageFlowRefTypeEClass, PageFlowRefType.class, "PageFlowRefType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPageFlowRefType_RefId(), theXMLTypePackage.getString(), "refId", null, 1, 1, PageFlowRefType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(pageFlowTypeEClass, PageFlowType.class, "PageFlowType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPageFlowType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPageFlowType_PageActivity(), this.getPageActivityType(), null, "pageActivity", null, 1, -1, PageFlowType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageFlowType_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageFlowType_ModuleName(), theXMLTypePackage.getString(), "moduleName", null, 1, 1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageFlowType_ModuleVersion(), theXMLTypePackage.getString(), "moduleVersion", null, 1, 1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageFlowType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPageFlowType_Url(), theXMLTypePackage.getString(), "url", null, 1, 1, PageFlowType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(propertyTypeEClass, PropertyType.class, "PropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getPropertyType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPropertyType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getPropertyType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(serviceArchiveDescriptorTypeEClass, ServiceArchiveDescriptorType.class, "ServiceArchiveDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getServiceArchiveDescriptorType_Channels(), this.getChannelsType(), null, "channels", null, 1, 1, ServiceArchiveDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getServiceArchiveDescriptorType_Version(), ecorePackage.getEObject(), null, "version", null, 0, 1, ServiceArchiveDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workTypeTypeEClass, WorkTypeType.class, "WorkTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkTypeType_ExtendedProperties(), this.getExtendedPropertiesType(), null, "extendedProperties", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkTypeType_Form(), this.getFormType(), null, "form", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkTypeType_PageFlow(), this.getPageFlowType(), null, "pageFlow", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkTypeType_ExtensionConfig(), this.getChannelExtentionType(), null, "extensionConfig", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkTypeType_PageFlowRef(), this.getPageFlowRefType(), null, "pageFlowRef", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeType_Guid(), theXMLTypePackage.getString(), "guid", null, 1, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkTypeType_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, WorkTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (businessServiceTypeEClass, 
           source, 
           new String[] {
             "name", "businessServiceType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (channelExtentionTypeEClass, 
           source, 
           new String[] {
             "name", "channelExtentionType",
             "kind", "empty"
           });		
        addAnnotation
          (getChannelExtentionType_Filename(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "filename"
           });		
        addAnnotation
          (getChannelExtentionType_Location(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "location"
           });		
        addAnnotation
          (channelsTypeEClass, 
           source, 
           new String[] {
             "name", "channels_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getChannelsType_Channel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "channel",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (channelTypeEClass, 
           source, 
           new String[] {
             "name", "channel_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getChannelType_Description(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "description",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_TargetChannelType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "target-channel-type",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_PresentationChannelType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "presentation-channel-type",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_ImplementationType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "implementation-type",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_WorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "work-type",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getChannelType_Domain(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "domain",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_ExtendedProperties(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "extended-properties",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_ExtensionConfig(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "extension-config",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getChannelType_BusinessService(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "business-service",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getChannelType_PageFlow(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "page-flow",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getChannelType_ChannelId(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "channelId"
           });			
        addAnnotation
          (getChannelType_DefaultChannel(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "defaultChannel"
           });		
        addAnnotation
          (getChannelType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getChannelType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });			
        addAnnotation
          (getDocumentRoot_ServiceArchiveDescriptor(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Service-Archive-Descriptor",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (extendedPropertiesTypeEClass, 
           source, 
           new String[] {
             "name", "extendedPropertiesType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getExtendedPropertiesType_Property(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "property",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (formTypeEClass, 
           source, 
           new String[] {
             "name", "formType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getFormType_FormIdentifier(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "formIdentifier",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getFormType_RelativePath(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "relative-path",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getFormType_BasePath(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "base-path",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getFormType_Guid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "guid"
           });		
        addAnnotation
          (getFormType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getFormType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });		
        addAnnotation
          (pageActivityTypeEClass, 
           source, 
           new String[] {
             "name", "page-activity_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPageActivityType_PageReference(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "page-reference",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getPageActivityType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getPageActivityType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });			
        addAnnotation
          (pageFlowRefTypeEClass, 
           source, 
           new String[] {
             "name", "pageFlowRefType",
             "kind", "empty"
           });		
        addAnnotation
          (getPageFlowRefType_RefId(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "refId"
           });			
        addAnnotation
          (pageFlowTypeEClass, 
           source, 
           new String[] {
             "name", "pageFlowType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPageFlowType_Group(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "group:0"
           });		
        addAnnotation
          (getPageFlowType_PageActivity(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "page-activity",
             "namespace", "##targetNamespace",
             "group", "#group:0"
           });		
        addAnnotation
          (getPageFlowType_Id(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "id"
           });		
        addAnnotation
          (getPageFlowType_ModuleName(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "moduleName"
           });		
        addAnnotation
          (getPageFlowType_ModuleVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "moduleVersion"
           });		
        addAnnotation
          (getPageFlowType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getPageFlowType_Url(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "url"
           });		
        addAnnotation
          (propertyTypeEClass, 
           source, 
           new String[] {
             "name", "property_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getPropertyType_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "name",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getPropertyType_Value(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "value",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getPropertyType_Type(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "type",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (serviceArchiveDescriptorTypeEClass, 
           source, 
           new String[] {
             "name", "Service-Archive-Descriptor_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getServiceArchiveDescriptorType_Channels(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "channels",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getServiceArchiveDescriptorType_Version(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "version",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (workTypeTypeEClass, 
           source, 
           new String[] {
             "name", "work-type_._type",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getWorkTypeType_ExtendedProperties(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "extended-properties",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (getWorkTypeType_Form(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "form",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkTypeType_PageFlow(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "page-flow",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkTypeType_ExtensionConfig(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "extension-config",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkTypeType_PageFlowRef(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "page-flow-ref",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkTypeType_Guid(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "guid"
           });		
        addAnnotation
          (getWorkTypeType_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getWorkTypeType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });
    }

} //WPPackageImpl
