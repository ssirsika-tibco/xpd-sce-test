/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Http Outbound Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getHttpClientInstanceName <em>Http Client Instance Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpOutboundBinding()
 * @model extendedMetaData="name='WsSoapHttpOutboundBinding' kind='elementOnly'"
 * @generated
 */
public interface WsSoapHttpOutboundBinding extends WsSoapBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Outbound Security</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outbound Security</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outbound Security</em>' containment reference.
     * @see #setOutboundSecurity(WsSoapSecurity)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpOutboundBinding_OutboundSecurity()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='OutboundSecurity' namespace='##targetNamespace'"
     * @generated
     */
    WsSoapSecurity getOutboundSecurity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Security</em>' containment reference.
     * @see #getOutboundSecurity()
     * @generated
     */
    void setOutboundSecurity(WsSoapSecurity value);

    /**
     * Returns the value of the '<em><b>Http Client Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Http Client Instance Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Http Client Instance Name</em>' attribute.
     * @see #setHttpClientInstanceName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapHttpOutboundBinding_HttpClientInstanceName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='HttpClientInstanceName'"
     * @generated
     */
    String getHttpClientInstanceName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding#getHttpClientInstanceName <em>Http Client Instance Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Http Client Instance Name</em>' attribute.
     * @see #getHttpClientInstanceName()
     * @generated
     */
    void setHttpClientInstanceName(String value);

} // WsSoapHttpOutboundBinding
