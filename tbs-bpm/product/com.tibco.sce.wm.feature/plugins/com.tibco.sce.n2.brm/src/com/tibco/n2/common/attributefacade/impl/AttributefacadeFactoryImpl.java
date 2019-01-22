/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade.impl;

import com.tibco.n2.common.attributefacade.*;

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
public class AttributefacadeFactoryImpl extends EFactoryImpl implements AttributefacadeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributefacadeFactory init() {
        try {
            AttributefacadeFactory theAttributefacadeFactory = (AttributefacadeFactory)EPackage.Registry.INSTANCE.getEFactory("http://attributefacade.common.n2.tibco.com"); 
            if (theAttributefacadeFactory != null) {
                return theAttributefacadeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new AttributefacadeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributefacadeFactoryImpl() {
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
            case AttributefacadePackage.ATTRIBUTE_ALIAS_TYPE: return createAttributeAliasType();
            case AttributefacadePackage.DOCUMENT_ROOT: return createDocumentRoot();
            case AttributefacadePackage.WORK_LIST_ATTRIBUTE_FACADE_TYPE: return createWorkListAttributeFacadeType();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeAliasType createAttributeAliasType() {
        AttributeAliasTypeImpl attributeAliasType = new AttributeAliasTypeImpl();
        return attributeAliasType;
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
    public WorkListAttributeFacadeType createWorkListAttributeFacadeType() {
        WorkListAttributeFacadeTypeImpl workListAttributeFacadeType = new WorkListAttributeFacadeTypeImpl();
        return workListAttributeFacadeType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributefacadePackage getAttributefacadePackage() {
        return (AttributefacadePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static AttributefacadePackage getPackage() {
        return AttributefacadePackage.eINSTANCE;
    }

} //AttributefacadeFactoryImpl
