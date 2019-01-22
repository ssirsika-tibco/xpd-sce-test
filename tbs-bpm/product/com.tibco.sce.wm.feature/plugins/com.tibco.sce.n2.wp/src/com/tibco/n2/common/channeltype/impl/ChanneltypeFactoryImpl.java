/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.channeltype.impl;

import com.tibco.n2.common.channeltype.*;

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
public class ChanneltypeFactoryImpl extends EFactoryImpl implements ChanneltypeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChanneltypeFactory init() {
        try {
            ChanneltypeFactory theChanneltypeFactory = (ChanneltypeFactory)EPackage.Registry.INSTANCE.getEFactory("http://channeltype.common.n2.tibco.com"); 
            if (theChanneltypeFactory != null) {
                return theChanneltypeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ChanneltypeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChanneltypeFactoryImpl() {
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
            case ChanneltypePackage.CHANNEL_TYPE:
                return createChannelTypeFromString(eDataType, initialValue);
            case ChanneltypePackage.IMPLEMENTATION_TYPE:
                return createImplementationTypeFromString(eDataType, initialValue);
            case ChanneltypePackage.PRESENTATION_TYPE:
                return createPresentationTypeFromString(eDataType, initialValue);
            case ChanneltypePackage.CHANNEL_TYPE_OBJECT:
                return createChannelTypeObjectFromString(eDataType, initialValue);
            case ChanneltypePackage.IMPLEMENTATION_TYPE_OBJECT:
                return createImplementationTypeObjectFromString(eDataType, initialValue);
            case ChanneltypePackage.PRESENTATION_TYPE_OBJECT:
                return createPresentationTypeObjectFromString(eDataType, initialValue);
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
            case ChanneltypePackage.CHANNEL_TYPE:
                return convertChannelTypeToString(eDataType, instanceValue);
            case ChanneltypePackage.IMPLEMENTATION_TYPE:
                return convertImplementationTypeToString(eDataType, instanceValue);
            case ChanneltypePackage.PRESENTATION_TYPE:
                return convertPresentationTypeToString(eDataType, instanceValue);
            case ChanneltypePackage.CHANNEL_TYPE_OBJECT:
                return convertChannelTypeObjectToString(eDataType, instanceValue);
            case ChanneltypePackage.IMPLEMENTATION_TYPE_OBJECT:
                return convertImplementationTypeObjectToString(eDataType, instanceValue);
            case ChanneltypePackage.PRESENTATION_TYPE_OBJECT:
                return convertPresentationTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelType createChannelTypeFromString(EDataType eDataType, String initialValue) {
        ChannelType result = ChannelType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertChannelTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType createImplementationTypeFromString(EDataType eDataType, String initialValue) {
        ImplementationType result = ImplementationType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertImplementationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PresentationType createPresentationTypeFromString(EDataType eDataType, String initialValue) {
        PresentationType result = PresentationType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertPresentationTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChannelType createChannelTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createChannelTypeFromString(ChanneltypePackage.Literals.CHANNEL_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertChannelTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertChannelTypeToString(ChanneltypePackage.Literals.CHANNEL_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType createImplementationTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createImplementationTypeFromString(ChanneltypePackage.Literals.IMPLEMENTATION_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertImplementationTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertImplementationTypeToString(ChanneltypePackage.Literals.IMPLEMENTATION_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PresentationType createPresentationTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createPresentationTypeFromString(ChanneltypePackage.Literals.PRESENTATION_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertPresentationTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertPresentationTypeToString(ChanneltypePackage.Literals.PRESENTATION_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChanneltypePackage getChanneltypePackage() {
        return (ChanneltypePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ChanneltypePackage getPackage() {
        return ChanneltypePackage.eINSTANCE;
    }

} //ChanneltypeFactoryImpl
