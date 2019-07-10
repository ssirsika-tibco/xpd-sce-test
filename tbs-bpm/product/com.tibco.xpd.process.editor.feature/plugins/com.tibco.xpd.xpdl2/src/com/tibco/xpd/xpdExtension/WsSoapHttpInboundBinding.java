/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Http Inbound Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getInboundSecurity <em>Inbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getEndpointUrlPath <em>Endpoint Url Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getHttpConnectorInstanceName <em>Http Connector Instance Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpInboundBinding()
 * @model extendedMetaData="name='WsSoapHttpInboundBinding' kind='elementOnly'"
 * @generated
 */
public interface WsSoapHttpInboundBinding extends WsSoapBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Inbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inbound Security</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inbound Security</em>' containment reference.
     * @see #setInboundSecurity(WsSoapSecurity)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpInboundBinding_InboundSecurity()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='InboundSecurity' namespace='##targetNamespace'"
     * @generated
     */
    WsSoapSecurity getInboundSecurity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getInboundSecurity <em>Inbound Security</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Security</em>' containment reference.
     * @see #getInboundSecurity()
     * @generated
     */
    void setInboundSecurity(WsSoapSecurity value);

    /**
     * Returns the value of the '<em><b>Endpoint Url Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Endpoint Url Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Endpoint Url Path</em>' attribute.
     * @see #setEndpointUrlPath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpInboundBinding_EndpointUrlPath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
     *        extendedMetaData="kind='attribute' name='EndpointUrlPath'"
     * @generated
     */
    String getEndpointUrlPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getEndpointUrlPath <em>Endpoint Url Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Endpoint Url Path</em>' attribute.
     * @see #getEndpointUrlPath()
     * @generated
     */
    void setEndpointUrlPath(String value);

    /**
     * Returns the value of the '<em><b>Http Connector Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Http Connector Instance Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Http Connector Instance Name</em>' attribute.
     * @see #setHttpConnectorInstanceName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpInboundBinding_HttpConnectorInstanceName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='HttpConnectorInstanceName'"
     * @generated
     */
    String getHttpConnectorInstanceName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding#getHttpConnectorInstanceName <em>Http Connector Instance Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Http Connector Instance Name</em>' attribute.
     * @see #getHttpConnectorInstanceName()
     * @generated
     */
    void setHttpConnectorInstanceName(String value);

} // WsSoapHttpInboundBinding
