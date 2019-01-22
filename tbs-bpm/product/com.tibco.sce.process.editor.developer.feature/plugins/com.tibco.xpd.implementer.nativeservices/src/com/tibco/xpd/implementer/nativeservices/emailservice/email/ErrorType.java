/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnCode <em>Return Code</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnMessage <em>Return Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getErrorType()
 * @model extendedMetaData="name='ErrorType' kind='elementOnly'"
 * @generated
 */
public interface ErrorType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Return Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Code</em>' attribute.
	 * @see #setReturnCode(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getErrorType_ReturnCode()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='ReturnCode' namespace='##targetNamespace'"
	 * @generated
	 */
	String getReturnCode();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnCode <em>Return Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Code</em>' attribute.
	 * @see #getReturnCode()
	 * @generated
	 */
	void setReturnCode(String value);

	/**
	 * Returns the value of the '<em><b>Return Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Message</em>' attribute.
	 * @see #setReturnMessage(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getErrorType_ReturnMessage()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='ReturnMessage' namespace='##targetNamespace'"
	 * @generated
	 */
	String getReturnMessage();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnMessage <em>Return Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Message</em>' attribute.
	 * @see #getReturnMessage()
	 * @generated
	 */
	void setReturnMessage(String value);

} // ErrorType