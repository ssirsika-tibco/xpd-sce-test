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

import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Soap Http Outbound Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl#getOutboundSecurity <em>Outbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpOutboundBindingImpl#getHttpClientInstanceName <em>Http Client Instance Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapHttpOutboundBindingImpl extends WsSoapBindingImpl
        implements WsSoapHttpOutboundBinding {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

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
     * The default value of the '{@link #getHttpClientInstanceName() <em>Http Client Instance Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHttpClientInstanceName()
     * @generated
     * @ordered
     */
    protected static final String HTTP_CLIENT_INSTANCE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHttpClientInstanceName() <em>Http Client Instance Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHttpClientInstanceName()
     * @generated
     * @ordered
     */
    protected String httpClientInstanceName =
            HTTP_CLIENT_INSTANCE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsSoapHttpOutboundBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_SOAP_HTTP_OUTBOUND_BINDING;
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
                    XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY,
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
                                - XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                        null,
                        msgs);
            if (newOutboundSecurity != null)
                msgs = ((InternalEObject) newOutboundSecurity).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                        null,
                        msgs);
            msgs = basicSetOutboundSecurity(newOutboundSecurity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                    newOutboundSecurity, newOutboundSecurity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getHttpClientInstanceName() {
        return httpClientInstanceName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setHttpClientInstanceName(String newHttpClientInstanceName) {
        String oldHttpClientInstanceName = httpClientInstanceName;
        httpClientInstanceName = newHttpClientInstanceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME,
                    oldHttpClientInstanceName, httpClientInstanceName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY:
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
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            return getOutboundSecurity();
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME:
            return getHttpClientInstanceName();
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
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            setOutboundSecurity((WsSoapSecurity) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME:
            setHttpClientInstanceName((String) newValue);
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
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            setOutboundSecurity((WsSoapSecurity) null);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME:
            setHttpClientInstanceName(HTTP_CLIENT_INSTANCE_NAME_EDEFAULT);
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
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            return outboundSecurity != null;
        case XpdExtensionPackage.WS_SOAP_HTTP_OUTBOUND_BINDING__HTTP_CLIENT_INSTANCE_NAME:
            return HTTP_CLIENT_INSTANCE_NAME_EDEFAULT == null
                    ? httpClientInstanceName != null
                    : !HTTP_CLIENT_INSTANCE_NAME_EDEFAULT
                            .equals(httpClientInstanceName);
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
        result.append(" (httpClientInstanceName: "); //$NON-NLS-1$
        result.append(httpClientInstanceName);
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

} // WsSoapHttpOutboundBindingImpl
