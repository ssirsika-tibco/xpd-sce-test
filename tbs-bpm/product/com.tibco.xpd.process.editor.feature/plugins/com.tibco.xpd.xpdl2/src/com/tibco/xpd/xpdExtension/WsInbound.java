/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Ws Inbound</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsInbound#getVirtualBinding <em>Virtual Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsInbound#getSoapHttpBinding <em>Soap Http Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsInbound#getSoapJmsBinding <em>Soap Jms Binding</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsInbound()
 * @model extendedMetaData="name='WsInbound' kind='elementOnly'"
 * @generated
 */
public interface WsInbound extends EObject
{
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Virtual Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Binding</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Binding</em>' containment reference.
	 * @see #setVirtualBinding(WsVirtualBinding)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsInbound_VirtualBinding()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='VirtualBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	WsVirtualBinding getVirtualBinding();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsInbound#getVirtualBinding <em>Virtual Binding</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual Binding</em>' containment reference.
	 * @see #getVirtualBinding()
	 * @generated
	 */
	void setVirtualBinding(WsVirtualBinding value);

	/**
	 * Returns the value of the '<em><b>Soap Http Binding</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Soap Http Binding</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Soap Http Binding</em>' containment
	 *         reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsInbound_SoapHttpBinding()
	 * @model containment="true" extendedMetaData=
	 *        "kind='element' name='SoapHttpBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<WsSoapHttpInboundBinding> getSoapHttpBinding();

	/**
	 * Returns the value of the '<em><b>Soap Jms Binding</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Soap Jms Binding</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Soap Jms Binding</em>' containment
	 *         reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsInbound_SoapJmsBinding()
	 * @model containment="true" extendedMetaData=
	 *        "kind='element' name='SoapJmsBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<WsSoapJmsInboundBinding> getSoapJmsBinding();

	/**
	 * <!-- begin-user-doc --> Gets all inbound (provider) bindings. <!--
	 * end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<WsBinding> getAllBindings();

} // WsInbound
