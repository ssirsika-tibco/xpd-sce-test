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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Outbound</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsOutboundImpl#getVirtualBinding <em>Virtual Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsOutboundImpl#getSoapHttpBinding <em>Soap Http Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsOutboundImpl#getSoapJmsBinding <em>Soap Jms Binding</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsOutboundImpl extends EObjectImpl implements WsOutbound {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getVirtualBinding() <em>Virtual Binding</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVirtualBinding()
     * @generated
     * @ordered
     */
    protected WsVirtualBinding virtualBinding;

    /**
     * The cached value of the '{@link #getSoapHttpBinding() <em>Soap Http Binding</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getSoapHttpBinding()
     * @generated
     * @ordered
     */
    protected WsSoapHttpOutboundBinding soapHttpBinding;

    /**
     * The cached value of the '{@link #getSoapJmsBinding() <em>Soap Jms Binding</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getSoapJmsBinding()
     * @generated
     * @ordered
     */
    protected WsSoapJmsOutboundBinding soapJmsBinding;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsOutboundImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_OUTBOUND;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WsVirtualBinding getVirtualBinding() {
        return virtualBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetVirtualBinding(
            WsVirtualBinding newVirtualBinding, NotificationChain msgs) {
        WsVirtualBinding oldVirtualBinding = virtualBinding;
        virtualBinding = newVirtualBinding;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING,
                            oldVirtualBinding, newVirtualBinding);
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
    public void setVirtualBinding(WsVirtualBinding newVirtualBinding) {
        if (newVirtualBinding != virtualBinding) {
            NotificationChain msgs = null;
            if (virtualBinding != null)
                msgs = ((InternalEObject) virtualBinding).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING,
                        null,
                        msgs);
            if (newVirtualBinding != null)
                msgs = ((InternalEObject) newVirtualBinding).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING,
                        null,
                        msgs);
            msgs = basicSetVirtualBinding(newVirtualBinding, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING,
                    newVirtualBinding, newVirtualBinding));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WsSoapHttpOutboundBinding getSoapHttpBinding() {
        return soapHttpBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSoapHttpBinding(
            WsSoapHttpOutboundBinding newSoapHttpBinding,
            NotificationChain msgs) {
        WsSoapHttpOutboundBinding oldSoapHttpBinding = soapHttpBinding;
        soapHttpBinding = newSoapHttpBinding;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING,
                            oldSoapHttpBinding, newSoapHttpBinding);
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
    public void setSoapHttpBinding(
            WsSoapHttpOutboundBinding newSoapHttpBinding) {
        if (newSoapHttpBinding != soapHttpBinding) {
            NotificationChain msgs = null;
            if (soapHttpBinding != null)
                msgs = ((InternalEObject) soapHttpBinding).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING,
                        null,
                        msgs);
            if (newSoapHttpBinding != null)
                msgs = ((InternalEObject) newSoapHttpBinding).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING,
                        null,
                        msgs);
            msgs = basicSetSoapHttpBinding(newSoapHttpBinding, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING,
                    newSoapHttpBinding, newSoapHttpBinding));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public WsSoapJmsOutboundBinding getSoapJmsBinding() {
        return soapJmsBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSoapJmsBinding(
            WsSoapJmsOutboundBinding newSoapJmsBinding,
            NotificationChain msgs) {
        WsSoapJmsOutboundBinding oldSoapJmsBinding = soapJmsBinding;
        soapJmsBinding = newSoapJmsBinding;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING,
                            oldSoapJmsBinding, newSoapJmsBinding);
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
    public void setSoapJmsBinding(WsSoapJmsOutboundBinding newSoapJmsBinding) {
        if (newSoapJmsBinding != soapJmsBinding) {
            NotificationChain msgs = null;
            if (soapJmsBinding != null)
                msgs = ((InternalEObject) soapJmsBinding).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING,
                        null,
                        msgs);
            if (newSoapJmsBinding != null)
                msgs = ((InternalEObject) newSoapJmsBinding).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING,
                        null,
                        msgs);
            msgs = basicSetSoapJmsBinding(newSoapJmsBinding, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING,
                    newSoapJmsBinding, newSoapJmsBinding));
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    public WsBinding getBinding() {
        if (virtualBinding != null) {
            return virtualBinding;
        } else if (soapHttpBinding != null) {
            return soapHttpBinding;
        } else if (soapJmsBinding != null) {
            return soapJmsBinding;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    public void setBinding(WsBinding outboundBinding) {
        if (outboundBinding instanceof WsVirtualBinding) {
            setVirtualBinding((WsVirtualBinding) outboundBinding);
            setSoapHttpBinding(null);
            setSoapJmsBinding(null);
        } else if (outboundBinding instanceof WsSoapHttpOutboundBinding) {
            setVirtualBinding(null);
            setSoapHttpBinding((WsSoapHttpOutboundBinding) outboundBinding);
            setSoapJmsBinding(null);
        } else if (outboundBinding instanceof WsSoapJmsOutboundBinding) {
            setVirtualBinding(null);
            setSoapHttpBinding(null);
            setSoapJmsBinding((WsSoapJmsOutboundBinding) outboundBinding);
        } else if (outboundBinding == null) {
            setVirtualBinding(null);
            setSoapHttpBinding(null);
            setSoapJmsBinding(null);
        } else {
            throw new IllegalArgumentException(
                    "'outBinding' parameter is incorrect. (" + outboundBinding //$NON-NLS-1$
                            + ")"); //$NON-NLS-1$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING:
            return basicSetVirtualBinding(null, msgs);
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING:
            return basicSetSoapHttpBinding(null, msgs);
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING:
            return basicSetSoapJmsBinding(null, msgs);
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
        case XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING:
            return getVirtualBinding();
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING:
            return getSoapHttpBinding();
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING:
            return getSoapJmsBinding();
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
        case XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING:
            setVirtualBinding((WsVirtualBinding) newValue);
            return;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING:
            setSoapHttpBinding((WsSoapHttpOutboundBinding) newValue);
            return;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING:
            setSoapJmsBinding((WsSoapJmsOutboundBinding) newValue);
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
        case XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING:
            setVirtualBinding((WsVirtualBinding) null);
            return;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING:
            setSoapHttpBinding((WsSoapHttpOutboundBinding) null);
            return;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING:
            setSoapJmsBinding((WsSoapJmsOutboundBinding) null);
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
        case XpdExtensionPackage.WS_OUTBOUND__VIRTUAL_BINDING:
            return virtualBinding != null;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_HTTP_BINDING:
            return soapHttpBinding != null;
        case XpdExtensionPackage.WS_OUTBOUND__SOAP_JMS_BINDING:
            return soapJmsBinding != null;
        }
        return super.eIsSet(featureID);
    }

} // WsOutboundImpl
