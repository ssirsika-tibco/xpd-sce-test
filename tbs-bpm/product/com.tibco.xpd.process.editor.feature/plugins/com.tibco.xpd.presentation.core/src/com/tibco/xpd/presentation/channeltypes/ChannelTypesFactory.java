/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage
 * @generated
 */
public interface ChannelTypesFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChannelTypesFactory eINSTANCE = com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Target</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Target</em>'.
     * @generated
     */
    Target createTarget();

    /**
     * Returns a new object of class '<em>Presentation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Presentation</em>'.
     * @generated
     */
    Presentation createPresentation();

    /**
     * Returns a new object of class '<em>Implementation</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Implementation</em>'.
     * @generated
     */
    Implementation createImplementation();

    /**
     * Returns a new object of class '<em>Attribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute</em>'.
     * @generated
     */
    Attribute createAttribute();

    /**
     * Returns a new object of class '<em>Channel Types</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel Types</em>'.
     * @generated
     */
    ChannelTypes createChannelTypes();

    /**
     * Returns a new object of class '<em>Channel Destination</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel Destination</em>'.
     * @generated
     */
    ChannelDestination createChannelDestination();

    /**
     * Returns a new object of class '<em>Enum Literal</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enum Literal</em>'.
     * @generated
     */
    EnumLiteral createEnumLiteral();

    /**
     * Returns a new object of class '<em>Channel Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel Type</em>'.
     * @generated
     */
    ChannelType createChannelType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ChannelTypesPackage getChannelTypesPackage();

} //ChannelTypesFactory
