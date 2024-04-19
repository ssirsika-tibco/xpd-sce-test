/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Ws Outbound</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsOutbound#getVirtualBinding <em>Virtual Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapHttpBinding <em>Soap Http Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapJmsBinding <em>Soap Jms Binding</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsOutbound()
 * @model extendedMetaData="name='WsOutbound' kind='elementOnly'"
 * @generated
 */
public interface WsOutbound extends EObject
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
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsOutbound_VirtualBinding()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='VirtualBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	WsVirtualBinding getVirtualBinding();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsOutbound#getVirtualBinding <em>Virtual Binding</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual Binding</em>' containment reference.
	 * @see #getVirtualBinding()
	 * @generated
	 */
	void setVirtualBinding(WsVirtualBinding value);

	/**
	 * Returns the value of the '<em><b>Soap Http Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Soap Http Binding</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Soap Http Binding</em>' containment reference.
	 * @see #setSoapHttpBinding(WsSoapHttpOutboundBinding)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsOutbound_SoapHttpBinding()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SoapHttpBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	WsSoapHttpOutboundBinding getSoapHttpBinding();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapHttpBinding <em>Soap Http Binding</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Soap Http Binding</em>' containment reference.
	 * @see #getSoapHttpBinding()
	 * @generated
	 */
	void setSoapHttpBinding(WsSoapHttpOutboundBinding value);

	/**
	 * Returns the value of the '<em><b>Soap Jms Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Soap Jms Binding</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Soap Jms Binding</em>' containment reference.
	 * @see #setSoapJmsBinding(WsSoapJmsOutboundBinding)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsOutbound_SoapJmsBinding()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SoapJmsBinding' namespace='##targetNamespace'"
	 * @generated
	 */
	WsSoapJmsOutboundBinding getSoapJmsBinding();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsOutbound#getSoapJmsBinding <em>Soap Jms Binding</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Soap Jms Binding</em>' containment reference.
	 * @see #getSoapJmsBinding()
	 * @generated
	 */
	void setSoapJmsBinding(WsSoapJmsOutboundBinding value);

	/**
	 * <!-- begin-user-doc --> Gets one of the binding which is not set to
	 * <code>null</code> or <code>null</code> if all are <code>null</code>.<!--
	 * end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	WsBinding getBinding();

	/**
	 * <!-- begin-user-doc --> Sets the the binding to an appropriate feature
	 * depending on type and sets all other choice features to <code>null</code>
	 * .
	 * 
	 * @param ouBinding
	 *            the binding to set or <code>null</code> to set all features'
	 *            values to null. <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setBinding(WsBinding inboundBinding);

} // WsOutbound
