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
 * A representation of the model object '<em><b>Text Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A text mapping to an email parameter
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getTextMappingType()
 * @model extendedMetaData="name='TextMappingType' kind='empty'"
 * @generated
 */
public interface TextMappingType extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage#getTextMappingType_Text()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Text' namespace='##targetNamespace'"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // TextMappingType