/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.bom.modeler.custom.enumlitext.EnumlitextPackage
 * @generated
 */
public interface EnumlitextFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EnumlitextFactory eINSTANCE = com.tibco.xpd.bom.modeler.custom.enumlitext.impl.EnumlitextFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Domain Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Domain Value</em>'.
     * @generated
     */
    DomainValue createDomainValue();

    /**
     * Returns a new object of class '<em>Single Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Single Value</em>'.
     * @generated
     */
    SingleValue createSingleValue();

    /**
     * Returns a new object of class '<em>Range Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Range Value</em>'.
     * @generated
     */
    RangeValue createRangeValue();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    EnumlitextPackage getEnumlitextPackage();

} //EnumlitextFactory
