/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Soap Jms Outbound Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInboundDestination <em>Inbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundDestination <em>Outbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode <em>Delivery Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration <em>Message Expiration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout <em>Invocation Timeout</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding()
 * @model extendedMetaData="name='WsSoapJmsOutboundBinding' kind='empty'"
 * @generated
 */
public interface WsSoapJmsOutboundBinding extends WsSoapBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

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
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_OutboundConnectionFactory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='OutboundConnectionFactory'"
     * @generated
     */
    String getOutboundConnectionFactory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Connection Factory</em>' attribute.
     * @see #getOutboundConnectionFactory()
     * @generated
     */
    void setOutboundConnectionFactory(String value);

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
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_InboundDestination()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='InboundDestination'"
     * @generated
     */
    String getInboundDestination();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInboundDestination <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inbound Destination</em>' attribute.
     * @see #getInboundDestination()
     * @generated
     */
    void setInboundDestination(String value);

    /**
     * Returns the value of the '<em><b>Outbound Destination</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outbound Destination</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outbound Destination</em>' attribute.
     * @see #setOutboundDestination(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_OutboundDestination()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='OutboundDestination'"
     * @generated
     */
    String getOutboundDestination();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundDestination <em>Outbound Destination</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Destination</em>' attribute.
     * @see #getOutboundDestination()
     * @generated
     */
    void setOutboundDestination(String value);

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
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_OutboundSecurity()
     * @model containment="true" required="true"
     * @generated
     */
    WsSoapSecurity getOutboundSecurity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getOutboundSecurity <em>Outbound Security</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outbound Security</em>' containment reference.
     * @see #getOutboundSecurity()
     * @generated
     */
    void setOutboundSecurity(WsSoapSecurity value);

    /**
     * Returns the value of the '<em><b>Delivery Mode</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.DeliveryMode}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Delivery Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Delivery Mode</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.DeliveryMode
     * @see #isSetDeliveryMode()
     * @see #unsetDeliveryMode()
     * @see #setDeliveryMode(DeliveryMode)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_DeliveryMode()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='DeliveryMode'"
     * @generated
     */
    DeliveryMode getDeliveryMode();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode <em>Delivery Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Delivery Mode</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.DeliveryMode
     * @see #isSetDeliveryMode()
     * @see #unsetDeliveryMode()
     * @see #getDeliveryMode()
     * @generated
     */
    void setDeliveryMode(DeliveryMode value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode <em>Delivery Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeliveryMode()
     * @see #getDeliveryMode()
     * @see #setDeliveryMode(DeliveryMode)
     * @generated
     */
    void unsetDeliveryMode();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getDeliveryMode <em>Delivery Mode</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Delivery Mode</em>' attribute is set.
     * @see #unsetDeliveryMode()
     * @see #getDeliveryMode()
     * @see #setDeliveryMode(DeliveryMode)
     * @generated
     */
    boolean isSetDeliveryMode();

    /**
     * Returns the value of the '<em><b>Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Priority</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #setPriority(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_Priority()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='Priority'"
     * @generated
     */
    int getPriority();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority</em>' attribute.
     * @see #isSetPriority()
     * @see #unsetPriority()
     * @see #getPriority()
     * @generated
     */
    void setPriority(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority <em>Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    void unsetPriority();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getPriority <em>Priority</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Priority</em>' attribute is set.
     * @see #unsetPriority()
     * @see #getPriority()
     * @see #setPriority(int)
     * @generated
     */
    boolean isSetPriority();

    /**
     * Returns the value of the '<em><b>Message Expiration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message Expiration</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message Expiration</em>' attribute.
     * @see #isSetMessageExpiration()
     * @see #unsetMessageExpiration()
     * @see #setMessageExpiration(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_MessageExpiration()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='MessageExpiration'"
     * @generated
     */
    int getMessageExpiration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration <em>Message Expiration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Expiration</em>' attribute.
     * @see #isSetMessageExpiration()
     * @see #unsetMessageExpiration()
     * @see #getMessageExpiration()
     * @generated
     */
    void setMessageExpiration(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration <em>Message Expiration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMessageExpiration()
     * @see #getMessageExpiration()
     * @see #setMessageExpiration(int)
     * @generated
     */
    void unsetMessageExpiration();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getMessageExpiration <em>Message Expiration</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Message Expiration</em>' attribute is set.
     * @see #unsetMessageExpiration()
     * @see #getMessageExpiration()
     * @see #setMessageExpiration(int)
     * @generated
     */
    boolean isSetMessageExpiration();

    /**
     * Returns the value of the '<em><b>Invocation Timeout</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Invocation Timeout</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Invocation Timeout</em>' attribute.
     * @see #isSetInvocationTimeout()
     * @see #unsetInvocationTimeout()
     * @see #setInvocationTimeout(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSoapJmsOutboundBinding_InvocationTimeout()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='InvocationTimeout'"
     * @generated
     */
    int getInvocationTimeout();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout <em>Invocation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Invocation Timeout</em>' attribute.
     * @see #isSetInvocationTimeout()
     * @see #unsetInvocationTimeout()
     * @see #getInvocationTimeout()
     * @generated
     */
    void setInvocationTimeout(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout <em>Invocation Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInvocationTimeout()
     * @see #getInvocationTimeout()
     * @see #setInvocationTimeout(int)
     * @generated
     */
    void unsetInvocationTimeout();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding#getInvocationTimeout <em>Invocation Timeout</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Invocation Timeout</em>' attribute is set.
     * @see #unsetInvocationTimeout()
     * @see #getInvocationTimeout()
     * @see #setInvocationTimeout(int)
     * @generated
     */
    boolean isSetInvocationTimeout();

} // WsSoapJmsOutboundBinding
