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

import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Soap Jms Inbound Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl#getOutboundConnectionFactory <em>Outbound Connection Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl#getInboundConnectionFactoryConfiguration <em>Inbound Connection Factory Configuration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl#getInboundDestination <em>Inbound Destination</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapJmsInboundBindingImpl#getInboundSecurity <em>Inbound Security</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapJmsInboundBindingImpl extends WsSoapBindingImpl
        implements WsSoapJmsInboundBinding {
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
     * The default value of the '
     * {@link #getInboundConnectionFactoryConfiguration()
     * <em>Inbound Connection Factory Configuration</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInboundConnectionFactoryConfiguration()
     * @generated
     * @ordered
     */
    protected static final String INBOUND_CONNECTION_FACTORY_CONFIGURATION_EDEFAULT =
            null;

    /**
     * The cached value of the '
     * {@link #getInboundConnectionFactoryConfiguration()
     * <em>Inbound Connection Factory Configuration</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInboundConnectionFactoryConfiguration()
     * @generated
     * @ordered
     */
    protected String inboundConnectionFactoryConfiguration =
            INBOUND_CONNECTION_FACTORY_CONFIGURATION_EDEFAULT;

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
     * The cached value of the '{@link #getInboundSecurity() <em>Inbound Security</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getInboundSecurity()
     * @generated
     * @ordered
     */
    protected WsSoapSecurity inboundSecurity;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsSoapJmsInboundBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING;
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
                    XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY,
                    oldOutboundConnectionFactory, outboundConnectionFactory));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getInboundConnectionFactoryConfiguration() {
        return inboundConnectionFactoryConfiguration;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setInboundConnectionFactoryConfiguration(
            String newInboundConnectionFactoryConfiguration) {
        String oldInboundConnectionFactoryConfiguration =
                inboundConnectionFactoryConfiguration;
        inboundConnectionFactoryConfiguration =
                newInboundConnectionFactoryConfiguration;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION,
                    oldInboundConnectionFactoryConfiguration,
                    inboundConnectionFactoryConfiguration));
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
                    XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION,
                    oldInboundDestination, inboundDestination));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public WsSoapSecurity getInboundSecurity() {
        return inboundSecurity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInboundSecurity(
            WsSoapSecurity newInboundSecurity, NotificationChain msgs) {
        WsSoapSecurity oldInboundSecurity = inboundSecurity;
        inboundSecurity = newInboundSecurity;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY,
                    oldInboundSecurity, newInboundSecurity);
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
    public void setInboundSecurity(WsSoapSecurity newInboundSecurity) {
        if (newInboundSecurity != inboundSecurity) {
            NotificationChain msgs = null;
            if (inboundSecurity != null)
                msgs = ((InternalEObject) inboundSecurity).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY,
                        null,
                        msgs);
            if (newInboundSecurity != null)
                msgs = ((InternalEObject) newInboundSecurity).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY,
                        null,
                        msgs);
            msgs = basicSetInboundSecurity(newInboundSecurity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY,
                    newInboundSecurity, newInboundSecurity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            return basicSetInboundSecurity(null, msgs);
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
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            return getOutboundConnectionFactory();
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION:
            return getInboundConnectionFactoryConfiguration();
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION:
            return getInboundDestination();
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            return getInboundSecurity();
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
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            setOutboundConnectionFactory((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION:
            setInboundConnectionFactoryConfiguration((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION:
            setInboundDestination((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            setInboundSecurity((WsSoapSecurity) newValue);
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
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            setOutboundConnectionFactory(OUTBOUND_CONNECTION_FACTORY_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION:
            setInboundConnectionFactoryConfiguration(
                    INBOUND_CONNECTION_FACTORY_CONFIGURATION_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION:
            setInboundDestination(INBOUND_DESTINATION_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            setInboundSecurity((WsSoapSecurity) null);
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
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
            return OUTBOUND_CONNECTION_FACTORY_EDEFAULT == null
                    ? outboundConnectionFactory != null
                    : !OUTBOUND_CONNECTION_FACTORY_EDEFAULT
                            .equals(outboundConnectionFactory);
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION:
            return INBOUND_CONNECTION_FACTORY_CONFIGURATION_EDEFAULT == null
                    ? inboundConnectionFactoryConfiguration != null
                    : !INBOUND_CONNECTION_FACTORY_CONFIGURATION_EDEFAULT
                            .equals(inboundConnectionFactoryConfiguration);
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION:
            return INBOUND_DESTINATION_EDEFAULT == null
                    ? inboundDestination != null
                    : !INBOUND_DESTINATION_EDEFAULT.equals(inboundDestination);
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            return inboundSecurity != null;
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
        result.append(", inboundConnectionFactoryConfiguration: "); //$NON-NLS-1$
        result.append(inboundConnectionFactoryConfiguration);
        result.append(", inboundDestination: "); //$NON-NLS-1$
        result.append(inboundDestination);
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
        return getInboundSecurity();
    }

    /**
     * @see com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#setSoapSecurity(com.tibco.xpd.xpdExtension.WsSoapSecurity)
     * 
     * @param newSoapSecurity
     */
    @Override
    public void setSoapSecurity(WsSoapSecurity newSoapSecurity) {
        setInboundSecurity(newSoapSecurity);
    }

} // WsSoapJmsInboundBindingImpl
