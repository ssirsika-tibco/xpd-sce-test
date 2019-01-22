/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import com.tibco.xpd.presentation.channeltypes.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class ChannelTypesFactoryImpl extends EFactoryImpl implements ChannelTypesFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChannelTypesFactory init() {
        try {
            ChannelTypesFactory theChannelTypesFactory = (ChannelTypesFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/xpd/presentation/channeltypes/1.0"); 
            if (theChannelTypesFactory != null) {
                return theChannelTypesFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ChannelTypesFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypesFactoryImpl() {
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
            case ChannelTypesPackage.TARGET: return createTarget();
            case ChannelTypesPackage.PRESENTATION: return createPresentation();
            case ChannelTypesPackage.IMPLEMENTATION: return createImplementation();
            case ChannelTypesPackage.ATTRIBUTE: return createAttribute();
            case ChannelTypesPackage.ENUM_LITERAL: return createEnumLiteral();
            case ChannelTypesPackage.CHANNEL_TYPE: return createChannelType();
            case ChannelTypesPackage.CHANNEL_TYPES: return createChannelTypes();
            case ChannelTypesPackage.CHANNEL_DESTINATION: return createChannelDestination();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case ChannelTypesPackage.ATTRIBUTE_TYPE:
                return createAttributeTypeFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case ChannelTypesPackage.ATTRIBUTE_TYPE:
                return convertAttributeTypeToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Target createTarget() {
        TargetImpl target = new TargetImpl();
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Presentation createPresentation() {
        PresentationImpl presentation = new PresentationImpl();
        return presentation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Implementation createImplementation() {
        ImplementationImpl implementation = new ImplementationImpl();
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribute createAttribute() {
        AttributeImpl attribute = new AttributeImpl();
        return attribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypes createChannelTypes() {
        ChannelTypesImpl channelTypes = new ChannelTypesImpl();
        return channelTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelDestination createChannelDestination() {
        ChannelDestinationImpl channelDestination = new ChannelDestinationImpl();
        return channelDestination;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumLiteral createEnumLiteral() {
        EnumLiteralImpl enumLiteral = new EnumLiteralImpl();
        return enumLiteral;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelType createChannelType() {
        ChannelTypeImpl channelType = new ChannelTypeImpl();
        return channelType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeType createAttributeTypeFromString(EDataType eDataType, String initialValue) {
        AttributeType result = AttributeType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttributeTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelTypesPackage getChannelTypesPackage() {
        return (ChannelTypesPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ChannelTypesPackage getPackage() {
        return ChannelTypesPackage.eINSTANCE;
    }

} //ChannelTypesFactoryImpl
