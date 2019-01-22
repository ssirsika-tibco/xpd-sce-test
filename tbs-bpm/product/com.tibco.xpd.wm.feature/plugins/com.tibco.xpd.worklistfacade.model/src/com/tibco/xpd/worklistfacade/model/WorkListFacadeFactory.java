/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage
 * @generated
 */
public interface WorkListFacadeFactory extends EFactory {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    WorkListFacadeFactory eINSTANCE =
            com.tibco.xpd.worklistfacade.impl.WorkListFacadeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Document Root</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Work Item Attribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Item Attribute</em>'.
     * @generated
     */
    WorkItemAttribute createWorkItemAttribute();

    /**
     * Returns a new object of class '<em>Work Item Attributes</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Item Attributes</em>'.
     * @generated
     */
    WorkItemAttributes createWorkItemAttributes();

    /**
     * Returns a new object of class '<em>Work List Facade</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work List Facade</em>'.
     * @generated
     */
    WorkListFacade createWorkListFacade();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    WorkListFacadePackage getWorkListFacadePackage();

} // WorkListFacadeFactory
