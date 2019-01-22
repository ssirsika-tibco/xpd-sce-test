/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.util;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage
 * @generated
 */
public class EaijavaAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static EaijavaPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EaijavaAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = EaijavaPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch the delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EaijavaSwitch modelSwitch =
        new EaijavaSwitch() {
            public Object caseClassType(ClassType object) {
                return createClassTypeAdapter();
            }
            public Object caseDocumentRoot(DocumentRoot object) {
                return createDocumentRootAdapter();
            }
            public Object caseEAIJava(EAIJava object) {
                return createEAIJavaAdapter();
            }
            public Object caseMethodType(MethodType object) {
                return createMethodTypeAdapter();
            }
            public Object caseParametersType(ParametersType object) {
                return createParametersTypeAdapter();
            }
            public Object caseParameterType(ParameterType object) {
                return createParameterTypeAdapter();
            }
            public Object defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    public Adapter createAdapter(Notifier target) {
        return (Adapter)modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType <em>Class Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType
     * @generated
     */
    public Adapter createClassTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava <em>EAI Java</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava
     * @generated
     */
    public Adapter createEAIJavaAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType <em>Method Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType
     * @generated
     */
    public Adapter createMethodTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType <em>Parameters Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType
     * @generated
     */
    public Adapter createParametersTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType
     * @generated
     */
    public Adapter createParameterTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //EaijavaAdapterFactory
