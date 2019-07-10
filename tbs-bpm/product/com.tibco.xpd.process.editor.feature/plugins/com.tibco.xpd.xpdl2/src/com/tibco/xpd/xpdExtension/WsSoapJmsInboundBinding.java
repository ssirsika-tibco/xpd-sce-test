/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Jms Inbound Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundConnectionFactoryConfiguration <em>Inbound Connection Factory Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundDestination <em>Inbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundSecurity <em>Inbound Security</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsInboundBinding()
 * @model extendedMetaData="name='WsSoapJmsInboundBinding' kind='empty'"
 * @generated
 */
public interface WsSoapJmsInboundBinding extends WsSoapBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Outbound Connection Factory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outbound Connection Factory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outbound Connection Factory</em>' attribute.
     * @see #setOutboundConnectionFactory(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsInboundBinding_OutboundConnectionFactory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ConnectionFactory'"
     * @generated
     */
    String getOutboundConnectionFactory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Connection Factory</em>' attribute.
     * @see #getOutboundConnectionFactory()
     * @generated
     */
    void setOutboundConnectionFactory(String value);

    /**
     * Returns the value of the '<em><b>Inbound Connection Factory Configuration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inbound Connection Factory Configuration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inbound Connection Factory Configuration</em>' attribute.
     * @see #setInboundConnectionFactoryConfiguration(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsInboundBinding_InboundConnectionFactoryConfiguration()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ConnectionFactoryConfigurator'"
     * @generated
     */
    String getInboundConnectionFactoryConfiguration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundConnectionFactoryConfiguration <em>Inbound Connection Factory Configuration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Connection Factory Configuration</em>' attribute.
     * @see #getInboundConnectionFactoryConfiguration()
     * @generated
     */
    void setInboundConnectionFactoryConfiguration(String value);

    /**
     * Returns the value of the '<em><b>Inbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inbound Destination</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inbound Destination</em>' attribute.
     * @see #setInboundDestination(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsInboundBinding_InboundDestination()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='InboundDestination'"
     * @generated
     */
    String getInboundDestination();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundDestination <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Destination</em>' attribute.
     * @see #getInboundDestination()
     * @generated
     */
    void setInboundDestination(String value);

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
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsInboundBinding_InboundSecurity()
     * @model containment="true" required="true"
     * @generated
     */
    WsSoapSecurity getInboundSecurity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding#getInboundSecurity <em>Inbound Security</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Security</em>' containment reference.
     * @see #getInboundSecurity()
     * @generated
     */
    void setInboundSecurity(WsSoapSecurity value);

} // WsSoapJmsInboundBinding
