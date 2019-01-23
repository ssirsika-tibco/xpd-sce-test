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
 * A representation of the model object '<em><b>From Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getFromType()
 * @model extendedMetaData="name='From_._type' kind='simple'"
 * @generated
 */
public interface FromType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getFromType_Value()
	 * @model unique="false" dataType="com.tibco.xpd.implementer.nativeservices.emailservice.email.ParameterType"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * The default value is <code>"Custom"</code>.
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
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getFromType_Configuration()
	 * @model default="Custom" unique="false" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Configuration' namespace='##targetNamespace'"
	 * @generated
	 */
	ConfigurationType getConfiguration();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration <em>Configuration</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(ConfigurationType)
	 * @generated
	 */
	void unsetConfiguration();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration <em>Configuration</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Configuration</em>' attribute is set.
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(ConfigurationType)
	 * @generated
	 */
	boolean isSetConfiguration();

} // FromType