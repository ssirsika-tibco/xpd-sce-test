/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service.util;

import com.tibco.n2.wp.archive.service.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.wp.archive.service.WPPackage
 * @generated
 */
public class WPAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static WPPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WPAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = WPPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WPSwitch<Adapter> modelSwitch =
        new WPSwitch<Adapter>() {
            @Override
            public Adapter caseBusinessServiceType(BusinessServiceType object) {
                return createBusinessServiceTypeAdapter();
            }
            @Override
            public Adapter caseChannelExtentionType(ChannelExtentionType object) {
                return createChannelExtentionTypeAdapter();
            }
            @Override
            public Adapter caseChannelsType(ChannelsType object) {
                return createChannelsTypeAdapter();
            }
            @Override
            public Adapter caseChannelType(ChannelType object) {
                return createChannelTypeAdapter();
            }
            @Override
            public Adapter caseDocumentRoot(DocumentRoot object) {
                return createDocumentRootAdapter();
            }
            @Override
            public Adapter caseExtendedPropertiesType(ExtendedPropertiesType object) {
                return createExtendedPropertiesTypeAdapter();
            }
            @Override
            public Adapter caseFormType(FormType object) {
                return createFormTypeAdapter();
            }
            @Override
            public Adapter casePageActivityType(PageActivityType object) {
                return createPageActivityTypeAdapter();
            }
            @Override
            public Adapter casePageFlowRefType(PageFlowRefType object) {
                return createPageFlowRefTypeAdapter();
            }
            @Override
            public Adapter casePageFlowType(PageFlowType object) {
                return createPageFlowTypeAdapter();
            }
            @Override
            public Adapter casePropertyType(PropertyType object) {
                return createPropertyTypeAdapter();
            }
            @Override
            public Adapter caseServiceArchiveDescriptorType(ServiceArchiveDescriptorType object) {
                return createServiceArchiveDescriptorTypeAdapter();
            }
            @Override
            public Adapter caseWorkTypeType(WorkTypeType object) {
                return createWorkTypeTypeAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.BusinessServiceType <em>Business Service Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.BusinessServiceType
     * @generated
     */
    public Adapter createBusinessServiceTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.ChannelExtentionType <em>Channel Extention Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.ChannelExtentionType
     * @generated
     */
    public Adapter createChannelExtentionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.ChannelsType <em>Channels Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.ChannelsType
     * @generated
     */
    public Adapter createChannelsTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.ChannelType <em>Channel Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.ChannelType
     * @generated
     */
    public Adapter createChannelTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.ExtendedPropertiesType <em>Extended Properties Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.ExtendedPropertiesType
     * @generated
     */
    public Adapter createExtendedPropertiesTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.FormType <em>Form Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.FormType
     * @generated
     */
    public Adapter createFormTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.PageActivityType <em>Page Activity Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.PageActivityType
     * @generated
     */
    public Adapter createPageActivityTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.PageFlowRefType <em>Page Flow Ref Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.PageFlowRefType
     * @generated
     */
    public Adapter createPageFlowRefTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.PageFlowType <em>Page Flow Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.PageFlowType
     * @generated
     */
    public Adapter createPageFlowTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.PropertyType <em>Property Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.PropertyType
     * @generated
     */
    public Adapter createPropertyTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType <em>Service Archive Descriptor Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.ServiceArchiveDescriptorType
     * @generated
     */
    public Adapter createServiceArchiveDescriptorTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.n2.wp.archive.service.WorkTypeType <em>Work Type Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.n2.wp.archive.service.WorkTypeType
     * @generated
     */
    public Adapter createWorkTypeTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //WPAdapterFactory
