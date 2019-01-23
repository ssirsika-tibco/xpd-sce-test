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
 * A representation of the model object '<em><b>Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The parameter type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getClassName <em>Class Name</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getSignature <em>Signature</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getParameterType()
 * @model extendedMetaData="name='ParameterType' kind='empty'"
 * @generated
 */
public interface ParameterType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Class Name</em>' attribute.
     * @see #setClassName(String)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getParameterType_ClassName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='className'"
     * @generated
     */
    String getClassName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getClassName <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Class Name</em>' attribute.
     * @see #getClassName()
     * @generated
     */
    void setClassName(String value);

    /**
     * Returns the value of the '<em><b>Signature</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *   				The signature of the parameter as used in the JDT.
     *   			
     * <!-- end-model-doc -->
     * @return the value of the '<em>Signature</em>' attribute.
     * @see #setSignature(String)
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#getParameterType_Signature()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='signature'"
     * @generated
     */
    String getSignature();

    /**
     * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getSignature <em>Signature</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Signature</em>' attribute.
     * @see #getSignature()
     * @generated
     */
    void setSignature(String value);

} // ParameterType