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

import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Soap Http Inbound Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl#getInboundSecurity <em>Inbound Security</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl#getEndpointUrlPath <em>Endpoint Url Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapHttpInboundBindingImpl#getHttpConnectorInstanceName <em>Http Connector Instance Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapHttpInboundBindingImpl extends WsSoapBindingImpl
        implements WsSoapHttpInboundBinding {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

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
     * The default value of the '{@link #getEndpointUrlPath() <em>Endpoint Url Path</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getEndpointUrlPath()
     * @generated
     * @ordered
     */
    protected static final String ENDPOINT_URL_PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndpointUrlPath() <em>Endpoint Url Path</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getEndpointUrlPath()
     * @generated
     * @ordered
     */
    protected String endpointUrlPath = ENDPOINT_URL_PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getHttpConnectorInstanceName() <em>Http Connector Instance Name</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getHttpConnectorInstanceName()
     * @generated
     * @ordered
     */
    protected static final String HTTP_CONNECTOR_INSTANCE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHttpConnectorInstanceName() <em>Http Connector Instance Name</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getHttpConnectorInstanceName()
     * @generated
     * @ordered
     */
    protected String httpConnectorInstanceName =
            HTTP_CONNECTOR_INSTANCE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsSoapHttpInboundBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_SOAP_HTTP_INBOUND_BINDING;
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
                    XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY,
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
                                - XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY,
                        null,
                        msgs);
            if (newInboundSecurity != null)
                msgs = ((InternalEObject) newInboundSecurity).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY,
                        null,
                        msgs);
            msgs = basicSetInboundSecurity(newInboundSecurity, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY,
                    newInboundSecurity, newInboundSecurity));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getEndpointUrlPath() {
        return endpointUrlPath;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setEndpointUrlPath(String newEndpointUrlPath) {
        String oldEndpointUrlPath = endpointUrlPath;
        endpointUrlPath = newEndpointUrlPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH,
                    oldEndpointUrlPath, endpointUrlPath));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getHttpConnectorInstanceName() {
        return httpConnectorInstanceName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setHttpConnectorInstanceName(
            String newHttpConnectorInstanceName) {
        String oldHttpConnectorInstanceName = httpConnectorInstanceName;
        httpConnectorInstanceName = newHttpConnectorInstanceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME,
                    oldHttpConnectorInstanceName, httpConnectorInstanceName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY:
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
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY:
            return getInboundSecurity();
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH:
            return getEndpointUrlPath();
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME:
            return getHttpConnectorInstanceName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY:
            setInboundSecurity((WsSoapSecurity) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH:
            setEndpointUrlPath((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME:
            setHttpConnectorInstanceName((String) newValue);
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
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY:
            setInboundSecurity((WsSoapSecurity) null);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH:
            setEndpointUrlPath(ENDPOINT_URL_PATH_EDEFAULT);
            return;
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME:
            setHttpConnectorInstanceName(HTTP_CONNECTOR_INSTANCE_NAME_EDEFAULT);
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
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__INBOUND_SECURITY:
            return inboundSecurity != null;
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__ENDPOINT_URL_PATH:
            return ENDPOINT_URL_PATH_EDEFAULT == null ? endpointUrlPath != null
                    : !ENDPOINT_URL_PATH_EDEFAULT.equals(endpointUrlPath);
        case XpdExtensionPackage.WS_SOAP_HTTP_INBOUND_BINDING__HTTP_CONNECTOR_INSTANCE_NAME:
            return HTTP_CONNECTOR_INSTANCE_NAME_EDEFAULT == null
                    ? httpConnectorInstanceName != null
                    : !HTTP_CONNECTOR_INSTANCE_NAME_EDEFAULT
                            .equals(httpConnectorInstanceName);
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
        result.append(" (endpointUrlPath: "); //$NON-NLS-1$
        result.append(endpointUrlPath);
        result.append(", httpConnectorInstanceName: "); //$NON-NLS-1$
        result.append(httpConnectorInstanceName);
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
} // WsSoapHttpInboundBindingImpl
