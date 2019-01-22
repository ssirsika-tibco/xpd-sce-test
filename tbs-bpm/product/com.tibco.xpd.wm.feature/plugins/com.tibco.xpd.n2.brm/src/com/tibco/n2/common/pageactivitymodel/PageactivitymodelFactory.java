/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage
 * @generated
 */
public interface PageactivitymodelFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    PageactivitymodelFactory eINSTANCE = com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Page Activities</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Page Activities</em>'.
     * @generated
     */
    PageActivities createPageActivities();

    /**
     * Returns a new object of class '<em>Page Activity</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Page Activity</em>'.
     * @generated
     */
    PageActivity createPageActivity();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    PageactivitymodelPackage getPageactivitymodelPackage();

} //PageactivitymodelFactory
