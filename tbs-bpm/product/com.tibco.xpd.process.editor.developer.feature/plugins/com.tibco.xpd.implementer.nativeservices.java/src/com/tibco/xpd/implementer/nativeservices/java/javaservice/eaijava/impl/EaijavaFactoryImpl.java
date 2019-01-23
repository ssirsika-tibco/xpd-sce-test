/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.*;

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
public class EaijavaFactoryImpl extends EFactoryImpl implements EaijavaFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static EaijavaFactory init() {
        try {
            EaijavaFactory theEaijavaFactory = (EaijavaFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/XPD/EAIJava1.0.0");  //$NON-NLS-1$
            if (theEaijavaFactory != null) {
                return theEaijavaFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new EaijavaFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EaijavaFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case EaijavaPackage.CLASS_TYPE: return createClassType();
            case EaijavaPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case EaijavaPackage.EAI_JAVA: return createEAIJava();
            case EaijavaPackage.METHOD_TYPE: return createMethodType();
            case EaijavaPackage.PARAMETERS_TYPE: return createParametersType();
            case EaijavaPackage.PARAMETER_TYPE: return createParameterType();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ClassType createClassType() {
        ClassTypeImpl classType = new ClassTypeImpl();
        return classType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAIJava createEAIJava() {
        EAIJavaImpl eaiJava = new EAIJavaImpl();
        return eaiJava;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MethodType createMethodType() {
        MethodTypeImpl methodType = new MethodTypeImpl();
        return methodType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParametersType createParametersType() {
        ParametersTypeImpl parametersType = new ParametersTypeImpl();
        return parametersType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterType createParameterType() {
        ParameterTypeImpl parameterType = new ParameterTypeImpl();
        return parameterType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EaijavaPackage getEaijavaPackage() {
        return (EaijavaPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    public static EaijavaPackage getPackage() {
        return EaijavaPackage.eINSTANCE;
    }

} //EaijavaFactoryImpl
