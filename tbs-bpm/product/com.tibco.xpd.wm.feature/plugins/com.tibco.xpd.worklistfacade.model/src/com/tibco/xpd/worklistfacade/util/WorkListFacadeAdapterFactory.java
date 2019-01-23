/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage
 * @generated
 */
public class WorkListFacadeAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static WorkListFacadePackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public WorkListFacadeAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = WorkListFacadePackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc --> This implementation returns <code>true</code> if
     * the object is either the model's package or is an instance object of the
     * model. <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected WorkListFacadeSwitch<Adapter> modelSwitch =
            new WorkListFacadeSwitch<Adapter>() {
                @Override
                public Adapter caseDocumentRoot(DocumentRoot object) {
                    return createDocumentRootAdapter();
                }

                @Override
                public Adapter caseWorkItemAttribute(WorkItemAttribute object) {
                    return createWorkItemAttributeAdapter();
                }

                @Override
                public Adapter caseWorkItemAttributes(WorkItemAttributes object) {
                    return createWorkItemAttributesAdapter();
                }

                @Override
                public Adapter caseWorkListFacade(WorkListFacade object) {
                    return createWorkListFacadeAdapter();
                }

                @Override
                public Adapter defaultCase(EObject object) {
                    return createEObjectAdapter();
                }
            };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link com.tibco.xpd.worklistfacade.model.DocumentRoot
     * <em>Document Root</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's
     * useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute
     * <em>Work Item Attribute</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's
     * useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttribute
     * @generated
     */
    public Adapter createWorkItemAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link com.tibco.xpd.worklistfacade.model.WorkItemAttributes
     * <em>Work Item Attributes</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's
     * useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttributes
     * @generated
     */
    public Adapter createWorkItemAttributesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '
     * {@link com.tibco.xpd.worklistfacade.model.WorkListFacade
     * <em>Work List Facade</em>}'. <!-- begin-user-doc --> This default
     * implementation returns null so that we can easily ignore cases; it's
     * useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacade
     * @generated
     */
    public Adapter createWorkListFacadeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This
     * default implementation returns null. <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // WorkListFacadeAdapterFactory
