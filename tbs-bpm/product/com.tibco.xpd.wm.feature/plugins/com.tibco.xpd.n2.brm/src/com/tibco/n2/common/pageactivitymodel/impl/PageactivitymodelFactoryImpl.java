/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel.impl;

import com.tibco.n2.common.pageactivitymodel.*;

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
public class PageactivitymodelFactoryImpl extends EFactoryImpl implements PageactivitymodelFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PageactivitymodelFactory init() {
        try {
            PageactivitymodelFactory thePageactivitymodelFactory = (PageactivitymodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://pageactivitymodel.common.n2.tibco.com"); 
            if (thePageactivitymodelFactory != null) {
                return thePageactivitymodelFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new PageactivitymodelFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageactivitymodelFactoryImpl() {
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
            case PageactivitymodelPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case PageactivitymodelPackage.PAGE_ACTIVITIES: return createPageActivities();
            case PageactivitymodelPackage.PAGE_ACTIVITY: return createPageActivity();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
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
    public PageActivities createPageActivities() {
        PageActivitiesImpl pageActivities = new PageActivitiesImpl();
        return pageActivities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageActivity createPageActivity() {
        PageActivityImpl pageActivity = new PageActivityImpl();
        return pageActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PageactivitymodelPackage getPageactivitymodelPackage() {
        return (PageactivitymodelPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static PageactivitymodelPackage getPackage() {
        return PageactivitymodelPackage.eINSTANCE;
    }

} //PageactivitymodelFactoryImpl
