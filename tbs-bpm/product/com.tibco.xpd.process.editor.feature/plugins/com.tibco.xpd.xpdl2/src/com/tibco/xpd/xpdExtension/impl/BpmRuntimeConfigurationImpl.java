/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bpm Runtime Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.BpmRuntimeConfigurationImpl#getIncomingRequestTimeout <em>Incoming Request Timeout</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BpmRuntimeConfigurationImpl extends EObjectImpl implements BpmRuntimeConfiguration {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getIncomingRequestTimeout() <em>Incoming Request Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingRequestTimeout()
     * @generated
     * @ordered
     */
    protected static final Integer INCOMING_REQUEST_TIMEOUT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIncomingRequestTimeout() <em>Incoming Request Timeout</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingRequestTimeout()
     * @generated
     * @ordered
     */
    protected Integer incomingRequestTimeout = INCOMING_REQUEST_TIMEOUT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BpmRuntimeConfigurationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.BPM_RUNTIME_CONFIGURATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Integer getIncomingRequestTimeout() {
        return incomingRequestTimeout;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIncomingRequestTimeout(Integer newIncomingRequestTimeout) {
        Integer oldIncomingRequestTimeout = incomingRequestTimeout;
        incomingRequestTimeout = newIncomingRequestTimeout;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT, oldIncomingRequestTimeout,
                    incomingRequestTimeout));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT:
            return getIncomingRequestTimeout();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT:
            setIncomingRequestTimeout((Integer) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT:
            setIncomingRequestTimeout(INCOMING_REQUEST_TIMEOUT_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.BPM_RUNTIME_CONFIGURATION__INCOMING_REQUEST_TIMEOUT:
            return INCOMING_REQUEST_TIMEOUT_EDEFAULT == null ? incomingRequestTimeout != null
                    : !INCOMING_REQUEST_TIMEOUT_EDEFAULT.equals(incomingRequestTimeout);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (incomingRequestTimeout: "); //$NON-NLS-1$
        result.append(incomingRequestTimeout);
        result.append(')');
        return result.toString();
    }

} //BpmRuntimeConfigurationImpl
