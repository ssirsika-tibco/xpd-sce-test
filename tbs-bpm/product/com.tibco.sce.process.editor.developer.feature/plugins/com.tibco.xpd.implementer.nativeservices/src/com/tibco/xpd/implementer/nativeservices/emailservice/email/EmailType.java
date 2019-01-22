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
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getDefinition <em>Definition</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getBody <em>Body</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getAttachment <em>Attachment</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getSMTP <em>SMTP</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getError <em>Error</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType()
 * @model extendedMetaData="name='Email_._type' kind='elementOnly'"
 * @generated
 */
public interface EmailType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' containment reference.
	 * @see #setDefinition(DefinitionType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType_Definition()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Definition' namespace='##targetNamespace'"
	 * @generated
	 */
	DefinitionType getDefinition();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getDefinition <em>Definition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' containment reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(DefinitionType value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' attribute.
	 * @see #setBody(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType_Body()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="kind='element' name='Body' namespace='##targetNamespace'"
	 * @generated
	 */
	String getBody();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getBody <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' attribute.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(String value);

	/**
	 * Returns the value of the '<em><b>Attachment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attachment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attachment</em>' containment reference.
	 * @see #setAttachment(AttachmentType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType_Attachment()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Attachment' namespace='##targetNamespace'"
	 * @generated
	 */
	AttachmentType getAttachment();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getAttachment <em>Attachment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attachment</em>' containment reference.
	 * @see #getAttachment()
	 * @generated
	 */
	void setAttachment(AttachmentType value);

	/**
	 * Returns the value of the '<em><b>SMTP</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SMTP</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SMTP</em>' containment reference.
	 * @see #setSMTP(SMTPType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType_SMTP()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SMTP' namespace='##targetNamespace'"
	 * @generated
	 */
	SMTPType getSMTP();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getSMTP <em>SMTP</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SMTP</em>' containment reference.
	 * @see #getSMTP()
	 * @generated
	 */
	void setSMTP(SMTPType value);

	/**
	 * Returns the value of the '<em><b>Error</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error</em>' containment reference.
	 * @see #setError(ErrorType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getEmailType_Error()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Error' namespace='##targetNamespace'"
	 * @generated
	 */
	ErrorType getError();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getError <em>Error</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error</em>' containment reference.
	 * @see #getError()
	 * @generated
	 */
	void setError(ErrorType value);

} // EmailType