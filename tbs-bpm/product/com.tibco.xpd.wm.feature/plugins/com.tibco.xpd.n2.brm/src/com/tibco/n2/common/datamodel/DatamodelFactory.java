/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.datamodel.DatamodelPackage
 * @generated
 */
public interface DatamodelFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DatamodelFactory eINSTANCE = com.tibco.n2.common.datamodel.impl.DatamodelFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Alias Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Alias Type</em>'.
     * @generated
     */
    AliasType createAliasType();

    /**
     * Returns a new object of class '<em>Complex Spec Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Complex Spec Type</em>'.
     * @generated
     */
    ComplexSpecType createComplexSpecType();

    /**
     * Returns a new object of class '<em>Data Model</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Data Model</em>'.
     * @generated
     */
    DataModel createDataModel();

    /**
     * Returns a new object of class '<em>Field Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Field Type</em>'.
     * @generated
     */
    FieldType createFieldType();

    /**
     * Returns a new object of class '<em>Simple Spec Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Simple Spec Type</em>'.
     * @generated
     */
    SimpleSpecType createSimpleSpecType();

    /**
     * Returns a new object of class '<em>Work Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Type</em>'.
     * @generated
     */
    WorkType createWorkType();

    /**
     * Returns a new object of class '<em>Work Type Spec</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Type Spec</em>'.
     * @generated
     */
    WorkTypeSpec createWorkTypeSpec();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    DatamodelPackage getDatamodelPackage();

} //DatamodelFactory
