/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.impl;

import com.tibco.n2.wp.archive.service.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WPFactoryImpl extends EFactoryImpl implements WPFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WPFactory init() {
        try {
            WPFactory theWPFactory = (WPFactory)EPackage.Registry.INSTANCE.getEFactory("http://service.archive.wp.n2.tibco.com"); 
            if (theWPFactory != null) {
                return theWPFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new WPFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WPFactoryImpl() {
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
            case WPPackage.BUSINESS_SERVICE_TYPE: return createBusinessServiceType();
            case WPPackage.CHANNEL_EXTENTION_TYPE: return createChannelExtentionType();
            case WPPackage.CHANNELS_TYPE: return createChannelsType();
            case WPPackage.CHANNEL_TYPE: return createChannelType();
            case WPPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case WPPackage.EXTENDED_PROPERTIES_TYPE: return createExtendedPropertiesType();
            case WPPackage.FORM_TYPE: return createFormType();
            case WPPackage.PAGE_ACTIVITY_TYPE: return createPageActivityType();
            case WPPackage.PAGE_FLOW_REF_TYPE: return createPageFlowRefType();
            case WPPackage.PAGE_FLOW_TYPE: return createPageFlowType();
            case WPPackage.PROPERTY_TYPE: return createPropertyType();
            case WPPackage.SERVICE_ARCHIVE_DESCRIPTOR_TYPE: return createServiceArchiveDescriptorType();
            case WPPackage.WORK_TYPE_TYPE: return createWorkTypeType();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BusinessServiceType createBusinessServiceType() {
        BusinessServiceTypeImpl businessServiceType = new BusinessServiceTypeImpl();
        return businessServiceType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelExtentionType createChannelExtentionType() {
        ChannelExtentionTypeImpl channelExtentionType = new ChannelExtentionTypeImpl();
        return channelExtentionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelsType createChannelsType() {
        ChannelsTypeImpl channelsType = new ChannelsTypeImpl();
        return channelsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelType createChannelType() {
        ChannelTypeImpl channelType = new ChannelTypeImpl();
        return channelType;
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
    public ExtendedPropertiesType createExtendedPropertiesType() {
        ExtendedPropertiesTypeImpl extendedPropertiesType = new ExtendedPropertiesTypeImpl();
        return extendedPropertiesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormType createFormType() {
        FormTypeImpl formType = new FormTypeImpl();
        return formType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageActivityType createPageActivityType() {
        PageActivityTypeImpl pageActivityType = new PageActivityTypeImpl();
        return pageActivityType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageFlowRefType createPageFlowRefType() {
        PageFlowRefTypeImpl pageFlowRefType = new PageFlowRefTypeImpl();
        return pageFlowRefType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageFlowType createPageFlowType() {
        PageFlowTypeImpl pageFlowType = new PageFlowTypeImpl();
        return pageFlowType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertyType createPropertyType() {
        PropertyTypeImpl propertyType = new PropertyTypeImpl();
        return propertyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServiceArchiveDescriptorType createServiceArchiveDescriptorType() {
        ServiceArchiveDescriptorTypeImpl serviceArchiveDescriptorType = new ServiceArchiveDescriptorTypeImpl();
        return serviceArchiveDescriptorType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeType createWorkTypeType() {
        WorkTypeTypeImpl workTypeType = new WorkTypeTypeImpl();
        return workTypeType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WPPackage getWPPackage() {
        return (WPPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static WPPackage getPackage() {
        return WPPackage.eINSTANCE;
    }

} //WPFactoryImpl
