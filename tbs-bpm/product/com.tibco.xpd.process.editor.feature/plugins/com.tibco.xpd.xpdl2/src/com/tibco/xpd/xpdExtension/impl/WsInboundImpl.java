/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.WsBinding;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Inbound</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsInboundImpl#getVirtualBinding <em>Virtual Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsInboundImpl#getSoapHttpBinding <em>Soap Http Binding</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsInboundImpl#getSoapJmsBinding <em>Soap Jms Binding</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsInboundImpl extends EObjectImpl implements WsInbound {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getSoapHttpBinding()
     * <em>Soap Http Binding</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSoapHttpBinding()
     * @generated
     * @ordered
     */
    protected EList<WsSoapHttpInboundBinding> soapHttpBinding;

    /**
     * The cached value of the '{@link #getSoapJmsBinding()
     * <em>Soap Jms Binding</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSoapJmsBinding()
     * @generated
     * @ordered
     */
    protected EList<WsSoapJmsInboundBinding> soapJmsBinding;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected WsInboundImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_INBOUND;
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
    public NotificationChain basicSetVirtualBinding(WsVirtualBinding newVirtualBinding, NotificationChain msgs) {
        WsVirtualBinding oldVirtualBinding = virtualBinding;
        virtualBinding = newVirtualBinding;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING, oldVirtualBinding, newVirtualBinding);
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
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING,
                        null,
                        msgs);
            if (newVirtualBinding != null)
                msgs = ((InternalEObject) newVirtualBinding).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING,
                        null,
                        msgs);
            msgs = basicSetVirtualBinding(newVirtualBinding, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING,
                    newVirtualBinding, newVirtualBinding));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<WsSoapHttpInboundBinding> getSoapHttpBinding() {
        if (soapHttpBinding == null) {
            soapHttpBinding = new EObjectContainmentEList<WsSoapHttpInboundBinding>(WsSoapHttpInboundBinding.class,
                    this, XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING);
        }
        return soapHttpBinding;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<WsSoapJmsInboundBinding> getSoapJmsBinding() {
        if (soapJmsBinding == null) {
            soapJmsBinding = new EObjectContainmentEList<WsSoapJmsInboundBinding>(WsSoapJmsInboundBinding.class, this,
                    XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING);
        }
        return soapJmsBinding;
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */
    public EList<WsBinding> getAllBindings() {
        ArrayList<WsBinding> list = new ArrayList<WsBinding>();
        if (virtualBinding != null) {
            list.add(virtualBinding);
        }
        list.addAll(getSoapHttpBinding());
        list.addAll(getSoapJmsBinding());
        return new BasicEList.UnmodifiableEList<WsBinding>(list.size(), list.toArray());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING:
            return basicSetVirtualBinding(null, msgs);
        case XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING:
            return ((InternalEList<?>) getSoapHttpBinding()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING:
            return ((InternalEList<?>) getSoapJmsBinding()).basicRemove(otherEnd, msgs);
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
        case XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING:
            return getVirtualBinding();
        case XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING:
            return getSoapHttpBinding();
        case XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING:
            return getSoapJmsBinding();
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
        case XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING:
            setVirtualBinding((WsVirtualBinding) newValue);
            return;
        case XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING:
            getSoapHttpBinding().clear();
            getSoapHttpBinding().addAll((Collection<? extends WsSoapHttpInboundBinding>) newValue);
            return;
        case XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING:
            getSoapJmsBinding().clear();
            getSoapJmsBinding().addAll((Collection<? extends WsSoapJmsInboundBinding>) newValue);
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
        case XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING:
            setVirtualBinding((WsVirtualBinding) null);
            return;
        case XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING:
            getSoapHttpBinding().clear();
            return;
        case XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING:
            getSoapJmsBinding().clear();
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
        case XpdExtensionPackage.WS_INBOUND__VIRTUAL_BINDING:
            return virtualBinding != null;
        case XpdExtensionPackage.WS_INBOUND__SOAP_HTTP_BINDING:
            return soapHttpBinding != null && !soapHttpBinding.isEmpty();
        case XpdExtensionPackage.WS_INBOUND__SOAP_JMS_BINDING:
            return soapJmsBinding != null && !soapJmsBinding.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // WsInboundImpl
