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
 * A representation of the model object '<em><b>Definition Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * E-Mail definition section
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getCc <em>Cc</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getBcc <em>Bcc</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getReplyTo <em>Reply To</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getSubject <em>Subject</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType()
 * @model extendedMetaData="name='DefinitionType' kind='elementOnly'"
 * @generated
 */
public interface DefinitionType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' containment reference.
	 * @see #setFrom(FromType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_From()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='From' namespace='##targetNamespace'"
	 * @generated
	 */
	FromType getFrom();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getFrom <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' containment reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(FromType value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_To()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='To' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTo();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getTo <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(String value);

	/**
	 * Returns the value of the '<em><b>Cc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cc</em>' attribute.
	 * @see #setCc(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_Cc()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Cc' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCc();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getCc <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cc</em>' attribute.
	 * @see #getCc()
	 * @generated
	 */
	void setCc(String value);

	/**
	 * Returns the value of the '<em><b>Bcc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bcc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bcc</em>' attribute.
	 * @see #setBcc(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_Bcc()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Bcc' namespace='##targetNamespace'"
	 * @generated
	 */
	String getBcc();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getBcc <em>Bcc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bcc</em>' attribute.
	 * @see #getBcc()
	 * @generated
	 */
	void setBcc(String value);

	/**
	 * Returns the value of the '<em><b>Reply To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reply To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reply To</em>' attribute.
	 * @see #setReplyTo(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_ReplyTo()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='ReplyTo' namespace='##targetNamespace'"
	 * @generated
	 */
	String getReplyTo();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getReplyTo <em>Reply To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reply To</em>' attribute.
	 * @see #getReplyTo()
	 * @generated
	 */
	void setReplyTo(String value);

	/**
	 * Returns the value of the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Headers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Headers</em>' attribute.
	 * @see #setHeaders(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_Headers()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Headers' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHeaders();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getHeaders <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Headers</em>' attribute.
	 * @see #getHeaders()
	 * @generated
	 */
	void setHeaders(String value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_Priority()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Priority' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPriority();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(String value);

	/**
	 * Returns the value of the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' attribute.
	 * @see #setSubject(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getDefinitionType_Subject()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Subject' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getSubject <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' attribute.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(String value);

} // DefinitionType