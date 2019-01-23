/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.example.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ModelFactory eINSTANCE = com.tibco.example.model.impl.ModelFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Child Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Child Type</em>'.
     * @generated
     */
    ChildType createChildType();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Main Element Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Main Element Type</em>'.
     * @generated
     */
    MainElementType createMainElementType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ModelPackage getModelPackage();

} //ModelFactory
