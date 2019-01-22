/**
 */
package com.tibco.xpd.globalSignalDefinition.impl;

import com.tibco.xpd.globalSignalDefinition.*;

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
public class GlobalSignalDefinitionFactoryImpl extends EFactoryImpl implements GlobalSignalDefinitionFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static GlobalSignalDefinitionFactory init() {
        try {
            GlobalSignalDefinitionFactory theGlobalSignalDefinitionFactory = (GlobalSignalDefinitionFactory)EPackage.Registry.INSTANCE.getEFactory(GlobalSignalDefinitionPackage.eNS_URI);
            if (theGlobalSignalDefinitionFactory != null) {
                return theGlobalSignalDefinitionFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new GlobalSignalDefinitionFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionFactoryImpl() {
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
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL: return createGlobalSignal();
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS: return createGlobalSignalDefinitions();
            case GlobalSignalDefinitionPackage.PAYLOAD_DATA_FIELD: return createPayloadDataField();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignal createGlobalSignal() {
        GlobalSignalImpl globalSignal = new GlobalSignalImpl();
        return globalSignal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitions createGlobalSignalDefinitions() {
        GlobalSignalDefinitionsImpl globalSignalDefinitions = new GlobalSignalDefinitionsImpl();
        return globalSignalDefinitions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PayloadDataField createPayloadDataField() {
        PayloadDataFieldImpl payloadDataField = new PayloadDataFieldImpl();
        return payloadDataField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionPackage getGlobalSignalDefinitionPackage() {
        return (GlobalSignalDefinitionPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static GlobalSignalDefinitionPackage getPackage() {
        return GlobalSignalDefinitionPackage.eINSTANCE;
    }

} //GlobalSignalDefinitionFactoryImpl
