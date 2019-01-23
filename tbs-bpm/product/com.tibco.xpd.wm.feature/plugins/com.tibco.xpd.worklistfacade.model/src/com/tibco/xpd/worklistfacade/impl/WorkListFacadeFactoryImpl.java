/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadeFactory;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class WorkListFacadeFactoryImpl extends EFactoryImpl implements
        WorkListFacadeFactory {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public static WorkListFacadeFactory init() {
        try {
            WorkListFacadeFactory theWorkListFacadeFactory =
                    (WorkListFacadeFactory) EPackage.Registry.INSTANCE
                            .getEFactory("http://www.tibco.com/XPD/workListFacade1.0.0"); //$NON-NLS-1$ 
            if (theWorkListFacadeFactory != null) {
                return theWorkListFacadeFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new WorkListFacadeFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    public WorkListFacadeFactoryImpl() {
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
        case WorkListFacadePackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTE:
            return createWorkItemAttribute();
        case WorkListFacadePackage.WORK_ITEM_ATTRIBUTES:
            return createWorkItemAttributes();
        case WorkListFacadePackage.WORK_LIST_FACADE:
            return createWorkListFacade();
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
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public WorkItemAttribute createWorkItemAttribute() {
        WorkItemAttributeImpl workItemAttribute = new WorkItemAttributeImpl();
        return workItemAttribute;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public WorkItemAttributes createWorkItemAttributes() {
        WorkItemAttributesImpl workItemAttributes =
                new WorkItemAttributesImpl();
        return workItemAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public WorkListFacade createWorkListFacade() {
        WorkListFacadeImpl workListFacade = new WorkListFacadeImpl();
        return workListFacade;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public WorkListFacadePackage getWorkListFacadePackage() {
        return (WorkListFacadePackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static WorkListFacadePackage getPackage() {
        return WorkListFacadePackage.eINSTANCE;
    }

} // WorkListFacadeFactoryImpl
