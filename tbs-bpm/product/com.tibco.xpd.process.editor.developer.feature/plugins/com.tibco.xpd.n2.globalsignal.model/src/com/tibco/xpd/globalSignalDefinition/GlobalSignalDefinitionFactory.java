/**
 */
package com.tibco.xpd.globalSignalDefinition;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage
 * @generated
 */
public interface GlobalSignalDefinitionFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    GlobalSignalDefinitionFactory eINSTANCE = com.tibco.xpd.globalSignalDefinition.impl.GlobalSignalDefinitionFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Global Signal</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Global Signal</em>'.
     * @generated
     */
    GlobalSignal createGlobalSignal();

    /**
     * Returns a new object of class '<em>Global Signal Definitions</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Global Signal Definitions</em>'.
     * @generated
     */
    GlobalSignalDefinitions createGlobalSignalDefinitions();

    /**
     * Returns a new object of class '<em>Payload Data Field</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Payload Data Field</em>'.
     * @generated
     */
    PayloadDataField createPayloadDataField();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    GlobalSignalDefinitionPackage getGlobalSignalDefinitionPackage();

} //GlobalSignalDefinitionFactory
