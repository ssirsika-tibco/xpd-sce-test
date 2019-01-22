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
 * A representation of the model object '<em><b>Class Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Class type representing the Java class.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getMethod <em>Method</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getClassType()
 * @model extendedMetaData="name='ClassType' kind='elementOnly'"
 * @generated
 */
public interface ClassType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Method</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Method to invoke of this class.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Method</em>' containment reference.
     * @see #setMethod(MethodType)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getClassType_Method()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Method' namespace='##targetNamespace'"
     * @generated
     */
    MethodType getMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getMethod <em>Method</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Method</em>' containment reference.
     * @see #getMethod()
     * @generated
     */
    void setMethod(MethodType value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The fully qualified name of the class
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getClassType_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // ClassType