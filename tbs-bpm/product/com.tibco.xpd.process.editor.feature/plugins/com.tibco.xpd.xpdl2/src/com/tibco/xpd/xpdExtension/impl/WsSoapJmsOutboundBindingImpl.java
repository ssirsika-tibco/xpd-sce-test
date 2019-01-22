/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.xpdExtension.DeliveryMode;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Soap Jms Outbound Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getInboundDestination <em>Inbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getOutboundDestination <em>Outbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getOutboundSecurity <em>Outbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getDeliveryMode <em>Delivery Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getMessageExpiration <em>Message Expiration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsOutboundBindingImpl#getInvocationTimeout <em>Invocation Timeout</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapJmsOutboundBindingImpl extends WsSoapBindingImpl
        implements WsSoapJmsOutboundBinding {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getOutboundConnectionFactory() <em>Outbound Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutboundConnectionFactory()
     * @generated
     * @ordered
     */
    protected static final String OUTBOUND_CONNECTION_FACTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutboundConnectionFactory() <em>Outbound Connection Factory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutboundConnectionFactory()
     * @generated
     * @ordered
     */
    protected String outboundConnectionFactory =
            OUTBOUND_CONNECTION_FACTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getInboundDestination() <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getInboundDestination()
     * @generated
     * @ordered
     */
    protected static final String INBOUND_DESTINATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInboundDestination() <em>Inbound Destination</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getInboundDestination()
     * @generated
     * @ordered
     */
    protected String inboundDestination = INBOUND_DESTINATION_EDEFAULT;

    /**
     * The default value of the '{@link #getOutboundDestination() <em>Outbound Destination</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getOutboundDestination()
     * @generated
     * @ordered
     */
    protected static final String OUTBOUND_DESTINATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutboundDestination() <em>Outbound Destination</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getOutboundDestination()
     * @generated
     * @ordered
     */
    protected String outboundDestination = OUTBOUND_DESTINATION_EDEFAULT;

    /**
     * The cached value of the '{@link #getOutboundSecurity() <em>Outbound Security</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getOutboundSecurity()
     * @generated
     * @ordered
     */
    protected WsSoapSecurity outboundSecurity;

    /**
     * The default value of the '{@link #getDeliveryMode()
     * <em>Delivery Mode</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDeliveryMode()
     * @generated NOT
     * @ordered
     */
    protected static final DeliveryMode DELIVERY_MODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeliveryMode() <em>Delivery Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getDeliveryMode()
     * @generated
     * @ordered
     */
    protected DeliveryMode deliveryMode = DELIVERY_MODE_EDEFAULT;

    /**
     * This is true if the Delivery Mode attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean deliveryModeESet;

    /**
     * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected static final int PRIORITY_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected int priority = PRIORITY_EDEFAULT;

    /**
     * This is true if the Priority attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean priorityESet;

    /**
     * The default value of the '{@link #getMessageExpiration() <em>Message Expiration</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getMessageExpiration()
     * @generated
     * @ordered
     */
    protected static final int MESSAGE_EXPIRATION_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMessageExpiration() <em>Message Expiration</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getMessageExpiration()
     * @generated
     * @ordered
     */
    protected int messageExpiration = MESSAGE_EXPIRATION_EDEFAULT;

    /**
     * This is true if the Message Expiration attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean messageExpirationESet;

    /**
     * The default value of the '{@link #getInvocationTimeout() <em>Invocation Timeout</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getInvocationTimeout()
     * @generated
     * @ordered
     */
    protected static final int INVOCATION_TIMEOUT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getInvocationTimeout() <em>Invocation Timeout</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getInvocationTimeout()
     * @generated
     * @ordered
     */
    protected int invocationTimeout = INVOCATION_TIMEOUT_EDEFAULT;

    /**
     * This is true if the Invocation Timeout attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean invocationTimeoutESet;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsSoapJmsOutboundBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getOutboundConnectionFactory() {
        return outboundConnectionFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOutboundConnectionFactory(
            String newOutboundConnectionFactory) {
        String oldOutboundConnectionFactory = outboundConnectionFactory;
        outboundConnectionFactory = newOutboundConnectionFactory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY,
                    oldOutboundConnectionFactory, outboundConnectionFactory));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getInboundDestination() {
        return inboundDestination;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInboundDestination(String newInboundDestination) {
        String oldInboundDestination = inboundDestination;
        inboundDestination = newInboundDestination;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION,
                    oldInboundDestination, inboundDestination));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getOutboundDestination() {
        return outboundDestination;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOutboundDestination(String newOutboundDestination) {
        String oldOutboundDestination = outboundDestination;
        outboundDestination = newOutboundDestination;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION,
                    oldOutboundDestination, outboundDestination));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapSecurity getOutboundSecurity() {
        return outboundSecurity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOutboundSecurity(
            WsSoapSecurity newOutboundSecurity, NotificationChain msgs) {
        WsSoapSecurity oldOutboundSecurity = outboundSecurity;
        outboundSecurity = newOutboundSecurity;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                    oldOutboundSecurity, newOutboundSecurity);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOutboundSecurity(WsSoapSecurity newOutboundSecurity) {
        if (newOutboundSecurity != outboundSecurity) {
            NotificationChain msgs = null;
            if (outboundSecurity != null)
                msgs = ((InternalEObject) outboundSecurity).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                        null,
                        msgs);
            if (newOutboundSecurity != null)
                msgs = ((InternalEObject) newOutboundSecurity).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                        null,
                        msgs);
            msgs = basicSetOutboundSecurity(newOutboundSecurity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                    newOutboundSecurity, newOutboundSecurity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setDeliveryMode(DeliveryMode newDeliveryMode) {
        DeliveryMode oldDeliveryMode = deliveryMode;
        deliveryMode = newDeliveryMode == null ? DELIVERY_MODE_EDEFAULT
                : newDeliveryMode;
        boolean oldDeliveryModeESet = deliveryModeESet;
        deliveryModeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE,
                    oldDeliveryMode, deliveryMode, !oldDeliveryModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetDeliveryMode() {
        DeliveryMode oldDeliveryMode = deliveryMode;
        boolean oldDeliveryModeESet = deliveryModeESet;
        deliveryMode = DELIVERY_MODE_EDEFAULT;
        deliveryModeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE,
                    oldDeliveryMode, DELIVERY_MODE_EDEFAULT,
                    oldDeliveryModeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetDeliveryMode() {
        return deliveryModeESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setPriority(int newPriority) {
        int oldPriority = priority;
        priority = newPriority;
        boolean oldPriorityESet = priorityESet;
        priorityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY,
                    oldPriority, priority, !oldPriorityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetPriority() {
        int oldPriority = priority;
        boolean oldPriorityESet = priorityESet;
        priority = PRIORITY_EDEFAULT;
        priorityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY,
                    oldPriority, PRIORITY_EDEFAULT, oldPriorityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetPriority() {
        return priorityESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getMessageExpiration() {
        return messageExpiration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setMessageExpiration(int newMessageExpiration) {
        int oldMessageExpiration = messageExpiration;
        messageExpiration = newMessageExpiration;
        boolean oldMessageExpirationESet = messageExpirationESet;
        messageExpirationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION,
                    oldMessageExpiration, messageExpiration,
                    !oldMessageExpirationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetMessageExpiration() {
        int oldMessageExpiration = messageExpiration;
        boolean oldMessageExpirationESet = messageExpirationESet;
        messageExpiration = MESSAGE_EXPIRATION_EDEFAULT;
        messageExpirationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION,
                    oldMessageExpiration, MESSAGE_EXPIRATION_EDEFAULT,
                    oldMessageExpirationESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetMessageExpiration() {
        return messageExpirationESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getInvocationTimeout() {
        return invocationTimeout;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInvocationTimeout(int newInvocationTimeout) {
        int oldInvocationTimeout = invocationTimeout;
        invocationTimeout = newInvocationTimeout;
        boolean oldInvocationTimeoutESet = invocationTimeoutESet;
        invocationTimeoutESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT,
                    oldInvocationTimeout, invocationTimeout,
                    !oldInvocationTimeoutESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetInvocationTimeout() {
        int oldInvocationTimeout = invocationTimeout;
        boolean oldInvocationTimeoutESet = invocationTimeoutESet;
        invocationTimeout = INVOCATION_TIMEOUT_EDEFAULT;
        invocationTimeoutESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT,
                    oldInvocationTimeout, INVOCATION_TIMEOUT_EDEFAULT,
                    oldInvocationTimeoutESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetInvocationTimeout() {
        return invocationTimeoutESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            return basicSetOutboundSecurity(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            return getOutboundConnectionFactory();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION:
            return getInboundDestination();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION:
            return getOutboundDestination();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            return getOutboundSecurity();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE:
            return getDeliveryMode();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY:
            return getPriority();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION:
            return getMessageExpiration();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT:
            return getInvocationTimeout();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            setOutboundConnectionFactory((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION:
            setInboundDestination((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION:
            setOutboundDestination((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            setOutboundSecurity((WsSoapSecurity) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE:
            setDeliveryMode((DeliveryMode) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY:
            setPriority((Integer) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION:
            setMessageExpiration((Integer) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT:
            setInvocationTimeout((Integer) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            setOutboundConnectionFactory(OUTBOUND_CONNECTION_FACTORY_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION:
            setInboundDestination(INBOUND_DESTINATION_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION:
            setOutboundDestination(OUTBOUND_DESTINATION_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            setOutboundSecurity((WsSoapSecurity) null);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE:
            unsetDeliveryMode();
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY:
            unsetPriority();
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION:
            unsetMessageExpiration();
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT:
            unsetInvocationTimeout();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            return OUTBOUND_CONNECTION_FACTORY_EDEFAULT == null
                    ? outboundConnectionFactory != null
                    : !OUTBOUND_CONNECTION_FACTORY_EDEFAULT
                            .equals(outboundConnectionFactory);
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION:
            return INBOUND_DESTINATION_EDEFAULT == null
                    ? inboundDestination != null
                    : !INBOUND_DESTINATION_EDEFAULT.equals(inboundDestination);
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION:
            return OUTBOUND_DESTINATION_EDEFAULT == null
                    ? outboundDestination != null
                    : !OUTBOUND_DESTINATION_EDEFAULT
                            .equals(outboundDestination);
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            return outboundSecurity != null;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE:
            return isSetDeliveryMode();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY:
            return isSetPriority();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION:
            return isSetMessageExpiration();
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT:
            return isSetInvocationTimeout();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (outboundConnectionFactory: "); //$NON-NLS-1$
        result.append(outboundConnectionFactory);
        result.append(", inboundDestination: "); //$NON-NLS-1$
        result.append(inboundDestination);
        result.append(", outboundDestination: "); //$NON-NLS-1$
        result.append(outboundDestination);
        result.append(", deliveryMode: "); //$NON-NLS-1$
        if (deliveryModeESet)
            result.append(deliveryMode);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", priority: "); //$NON-NLS-1$
        if (priorityESet)
            result.append(priority);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", messageExpiration: "); //$NON-NLS-1$
        if (messageExpirationESet)
            result.append(messageExpiration);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", invocationTimeout: "); //$NON-NLS-1$
        if (invocationTimeoutESet)
            result.append(invocationTimeout);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

    /**
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#getSoapSecurity()
     * 
     * @return
     */
    @Override
    public WsSoapSecurity getSoapSecurity() {
        return getOutboundSecurity();
    }

    /**
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#setSoapSecurity(com.tibco.xpd.xpdExtension.WsSoapSecurity)
     * 
     * @param newSoapSecurity
     */
    @Override
    public void setSoapSecurity(WsSoapSecurity newSoapSecurity) {
        setOutboundSecurity(newSoapSecurity);
    }

} // WsSoapJmsOutboundBindingImpl
