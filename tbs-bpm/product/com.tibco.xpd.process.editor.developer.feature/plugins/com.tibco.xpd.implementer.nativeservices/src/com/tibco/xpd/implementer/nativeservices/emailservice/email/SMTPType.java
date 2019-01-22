/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SMTP Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getHost <em>Host</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getSMTPType()
 * @model extendedMetaData="name='SMTPType' kind='empty'"
 * @generated
 */
public interface SMTPType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * The default value is <code>"Server"</code>.
	 * The literals are from the enumeration {@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Configuration type - can be Custom or Server.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Configuration</em>' attribute.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @see #isSetConfiguration()
	 * @see #unsetConfiguration()
	 * @see #setConfiguration(ConfigurationType)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getSMTPType_Configuration()
	 * @model default="Server" unique="false" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Configuration' namespace='##targetNamespace'"
	 * @generated
	 */
	ConfigurationType getConfiguration();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' attribute.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @see #isSetConfiguration()
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(ConfigurationType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(ConfigurationType)
	 * @generated
	 */
	void unsetConfiguration();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration <em>Configuration</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Configuration</em>' attribute is set.
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(ConfigurationType)
	 * @generated
	 */
	boolean isSetConfiguration();

	/**
	 * Returns the value of the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host</em>' attribute.
	 * @see #setHost(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getSMTPType_Host()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Host'"
	 * @generated
	 */
	String getHost();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getHost <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host</em>' attribute.
	 * @see #getHost()
	 * @generated
	 */
	void setHost(String value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(BigDecimal)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getSMTPType_Port()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Decimal"
	 *        extendedMetaData="kind='attribute' name='Port'"
	 * @generated
	 */
	BigDecimal getPort();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(BigDecimal value);

} // SMTPType