/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.worktype.impl;

import com.tibco.n2.common.worktype.*;

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
public class WorktypeFactoryImpl extends EFactoryImpl implements WorktypeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static WorktypeFactory init() {
        try {
            WorktypeFactory theWorktypeFactory = (WorktypeFactory)EPackage.Registry.INSTANCE.getEFactory("http://worktype.common.n2.tibco.com"); 
            if (theWorktypeFactory != null) {
                return theWorktypeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new WorktypeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorktypeFactoryImpl() {
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
            case WorktypePackage.DOCUMENT_ROOT: return createDocumentRoot();
            case WorktypePackage.WORK_TYPE: return createWorkType();
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
    public WorkType createWorkType() {
        WorkTypeImpl workType = new WorkTypeImpl();
        return workType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorktypePackage getWorktypePackage() {
        return (WorktypePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static WorktypePackage getPackage() {
        return WorktypePackage.eINSTANCE;
    }

} //WorktypeFactoryImpl
