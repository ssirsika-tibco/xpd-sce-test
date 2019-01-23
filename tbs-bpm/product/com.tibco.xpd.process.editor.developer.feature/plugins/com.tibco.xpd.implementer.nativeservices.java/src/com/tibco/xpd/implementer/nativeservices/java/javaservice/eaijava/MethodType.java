/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getReturn <em>Return</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getMethodType()
 * @model extendedMetaData="name='MethodType' kind='elementOnly'"
 * @generated
 */
public interface MethodType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Return</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Return</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Return</em>' containment reference.
     * @see #setReturn(ParameterType)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getMethodType_Return()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Return' namespace='##targetNamespace'"
     * @generated
     */
    ParameterType getReturn();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getReturn <em>Return</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Return</em>' containment reference.
     * @see #getReturn()
     * @generated
     */
    void setReturn(ParameterType value);

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' containment reference.
     * @see #setParameters(ParametersType)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getMethodType_Parameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Parameters' namespace='##targetNamespace'"
     * @generated
     */
    ParametersType getParameters();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getParameters <em>Parameters</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameters</em>' containment reference.
     * @see #getParameters()
     * @generated
     */
    void setParameters(ParametersType value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getMethodType_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // MethodType