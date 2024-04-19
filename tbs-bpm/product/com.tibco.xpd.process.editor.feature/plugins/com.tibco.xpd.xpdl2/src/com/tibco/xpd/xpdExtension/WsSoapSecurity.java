/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Security</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapSecurity#getSecurityPolicy <em>Security Policy</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapSecurity()
 * @model extendedMetaData="name='WsSoapSecurity' kind='elementOnly'"
 * @generated
 */
public interface WsSoapSecurity extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Security Policy</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.WsSecurityPolicy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Policy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Policy</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapSecurity_SecurityPolicy()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SecurityPolicy' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<WsSecurityPolicy> getSecurityPolicy();

} // WsSoapSecurity
