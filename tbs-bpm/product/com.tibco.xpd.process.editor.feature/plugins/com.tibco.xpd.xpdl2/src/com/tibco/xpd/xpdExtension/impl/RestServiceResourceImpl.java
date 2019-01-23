/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExtendedAttribute;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rest Service Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getHttpClientInstanceName <em>Http Client Instance Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceResourceImpl#getSecurityPolicy <em>Security Policy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RestServiceResourceImpl extends EObjectImpl
        implements RestServiceResource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getSecurityPolicy() <em>Security Policy</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSecurityPolicy()
     * @generated
     * @ordered
     */
    protected EList<RestServiceResourceSecurity> securityPolicy;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RestServiceResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.REST_SERVICE_RESOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getHttpClientInstanceName() {
        return httpClientInstanceName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHttpClientInstanceName(String newHttpClientInstanceName) {
        String oldHttpClientInstanceName = httpClientInstanceName;
        httpClientInstanceName = newHttpClientInstanceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.REST_SERVICE_RESOURCE__HTTP_CLIENT_INSTANCE_NAME,
                    oldHttpClientInstanceName, httpClientInstanceName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<RestServiceResourceSecurity> getSecurityPolicy() {
        if (securityPolicy == null) {
            securityPolicy =
                    new EObjectContainmentEList<RestServiceResourceSecurity>(
                            RestServiceResourceSecurity.class, this,
                            XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY);
        }
        return securityPolicy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return ((InternalEList<?>) getSecurityPolicy())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__HTTP_CLIENT_INSTANCE_NAME:
            return getHttpClientInstanceName();
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return getSecurityPolicy();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__HTTP_CLIENT_INSTANCE_NAME:
            setHttpClientInstanceName((String) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            getSecurityPolicy().clear();
            getSecurityPolicy().addAll(
                    (Collection<? extends RestServiceResourceSecurity>) newValue);
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__HTTP_CLIENT_INSTANCE_NAME:
            setHttpClientInstanceName(HTTP_CLIENT_INSTANCE_NAME_EDEFAULT);
            return;
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            getSecurityPolicy().clear();
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
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__HTTP_CLIENT_INSTANCE_NAME:
            return HTTP_CLIENT_INSTANCE_NAME_EDEFAULT == null
                    ? httpClientInstanceName != null
                    : !HTTP_CLIENT_INSTANCE_NAME_EDEFAULT
                            .equals(httpClientInstanceName);
        case XpdExtensionPackage.REST_SERVICE_RESOURCE__SECURITY_POLICY:
            return securityPolicy != null && !securityPolicy.isEmpty();
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (httpClientInstanceName: "); //$NON-NLS-1$
        result.append(httpClientInstanceName);
        result.append(')');
        return result.toString();
    }

} //RestServiceResourceImpl
