/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsResource#getInbound <em>Inbound</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsResource#getOutbound <em>Outbound</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsResource()
 * @model extendedMetaData="name='WsResource' kind='elementOnly'"
 * @generated
 */
public interface WsResource extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Inbound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inbound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inbound</em>' containment reference.
	 * @see #setInbound(WsInbound)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsResource_Inbound()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Inbound' namespace='##targetNamespace'"
	 * @generated
	 */
	WsInbound getInbound();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsResource#getInbound <em>Inbound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inbound</em>' containment reference.
	 * @see #getInbound()
	 * @generated
	 */
	void setInbound(WsInbound value);

	/**
	 * Returns the value of the '<em><b>Outbound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outbound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outbound</em>' containment reference.
	 * @see #setOutbound(WsOutbound)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsResource_Outbound()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Outbound' namespace='##targetNamespace'"
	 * @generated
	 */
	WsOutbound getOutbound();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsResource#getOutbound <em>Outbound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outbound</em>' containment reference.
	 * @see #getOutbound()
	 * @generated
	 */
	void setOutbound(WsOutbound value);

} // WsResource
