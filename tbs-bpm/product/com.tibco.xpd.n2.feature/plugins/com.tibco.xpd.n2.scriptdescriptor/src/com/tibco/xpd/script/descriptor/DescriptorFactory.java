/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage
 * @generated
 */
public interface DescriptorFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    DescriptorFactory eINSTANCE = com.tibco.xpd.script.descriptor.impl.DescriptorFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Cac Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Cac Type</em>'.
     * @generated
     */
    CacType createCacType();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Enum Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enum Type</em>'.
     * @generated
     */
    EnumType createEnumType();

    /**
     * Returns a new object of class '<em>Factory Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Factory Type</em>'.
     * @generated
     */
    FactoryType createFactoryType();

    /**
     * Returns a new object of class '<em>Process Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Process Type</em>'.
     * @generated
     */
    ProcessType createProcessType();

    /**
     * Returns a new object of class '<em>Script Descriptor Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Script Descriptor Type</em>'.
     * @generated
     */
    ScriptDescriptorType createScriptDescriptorType();

    /**
     * Returns a new object of class '<em>Script Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Script Type</em>'.
     * @generated
     */
    ScriptType createScriptType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    DescriptorPackage getDescriptorPackage();

} //DescriptorFactory
