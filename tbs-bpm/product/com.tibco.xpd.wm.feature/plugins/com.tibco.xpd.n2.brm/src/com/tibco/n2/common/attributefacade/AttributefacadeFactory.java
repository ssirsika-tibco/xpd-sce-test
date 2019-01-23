/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.attributefacade.AttributefacadePackage
 * @generated
 */
public interface AttributefacadeFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AttributefacadeFactory eINSTANCE = com.tibco.n2.common.attributefacade.impl.AttributefacadeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Attribute Alias Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute Alias Type</em>'.
     * @generated
     */
    AttributeAliasType createAttributeAliasType();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Work List Attribute Facade Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work List Attribute Facade Type</em>'.
     * @generated
     */
    WorkListAttributeFacadeType createWorkListAttributeFacadeType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    AttributefacadePackage getAttributefacadePackage();

} //AttributefacadeFactory
