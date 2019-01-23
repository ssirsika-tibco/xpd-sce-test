/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.impl;

import com.tibco.xpd.presentation.channels.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChannelsFactoryImpl extends EFactoryImpl implements ChannelsFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChannelsFactory init() {
        try {
            ChannelsFactory theChannelsFactory = (ChannelsFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/xpd/presentation/channels/1.0"); 
            if (theChannelsFactory != null) {
                return theChannelsFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ChannelsFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelsFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case ChannelsPackage.CHANNELS: return createChannels();
            case ChannelsPackage.CHANNEL: return createChannel();
            case ChannelsPackage.ATTRIBUTE_VALUE: return createAttributeValue();
            case ChannelsPackage.TYPE_ASSOCIATION: return createTypeAssociation();
            case ChannelsPackage.EXTENDED_ATTRIBUTE: return createExtendedAttribute();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Channels createChannels() {
        ChannelsImpl channels = new ChannelsImpl();
        return channels;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Channel createChannel() {
        ChannelImpl channel = new ChannelImpl();
        return channel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeValue createAttributeValue() {
        AttributeValueImpl attributeValue = new AttributeValueImpl();
        return attributeValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TypeAssociation createTypeAssociation() {
        TypeAssociationImpl typeAssociation = new TypeAssociationImpl();
        return typeAssociation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExtendedAttribute createExtendedAttribute() {
        ExtendedAttributeImpl extendedAttribute = new ExtendedAttributeImpl();
        return extendedAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelsPackage getChannelsPackage() {
        return (ChannelsPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ChannelsPackage getPackage() {
        return ChannelsPackage.eINSTANCE;
    }

} //ChannelsFactoryImpl
