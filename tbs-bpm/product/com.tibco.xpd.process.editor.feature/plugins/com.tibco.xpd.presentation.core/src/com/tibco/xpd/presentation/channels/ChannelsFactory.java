/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage
 * @generated
 */
public interface ChannelsFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ChannelsFactory eINSTANCE = com.tibco.xpd.presentation.channels.impl.ChannelsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Channels</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channels</em>'.
     * @generated
     */
    Channels createChannels();

    /**
     * Returns a new object of class '<em>Channel</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel</em>'.
     * @generated
     */
    Channel createChannel();

    /**
     * Returns a new object of class '<em>Attribute Value</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Attribute Value</em>'.
     * @generated
     */
    AttributeValue createAttributeValue();

    /**
     * Returns a new object of class '<em>Type Association</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Type Association</em>'.
     * @generated
     */
    TypeAssociation createTypeAssociation();

    /**
     * Returns a new object of class '<em>Extended Attribute</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Extended Attribute</em>'.
     * @generated
     */
    ExtendedAttribute createExtendedAttribute();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ChannelsPackage getChannelsPackage();

} //ChannelsFactory
