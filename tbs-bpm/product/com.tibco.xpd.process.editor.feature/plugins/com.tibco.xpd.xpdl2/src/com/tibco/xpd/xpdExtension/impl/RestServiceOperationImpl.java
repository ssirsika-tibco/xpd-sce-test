/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rest Service Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RestServiceOperationImpl#getMethodId <em>Method Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RestServiceOperationImpl extends EObjectImpl implements RestServiceOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected static final String LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected String location = LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getMethodId() <em>Method Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMethodId()
     * @generated
     * @ordered
     */
    protected static final String METHOD_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMethodId() <em>Method Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMethodId()
     * @generated
     * @ordered
     */
    protected String methodId = METHOD_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RestServiceOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.REST_SERVICE_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(String newLocation) {
        String oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.REST_SERVICE_OPERATION__LOCATION,
                    oldLocation, location));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMethodId() {
        return methodId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMethodId(String newMethodId) {
        String oldMethodId = methodId;
        methodId = newMethodId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.REST_SERVICE_OPERATION__METHOD_ID,
                    oldMethodId, methodId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.REST_SERVICE_OPERATION__LOCATION:
            return getLocation();
        case XpdExtensionPackage.REST_SERVICE_OPERATION__METHOD_ID:
            return getMethodId();
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
        case XpdExtensionPackage.REST_SERVICE_OPERATION__LOCATION:
            setLocation((String) newValue);
            return;
        case XpdExtensionPackage.REST_SERVICE_OPERATION__METHOD_ID:
            setMethodId((String) newValue);
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
        case XpdExtensionPackage.REST_SERVICE_OPERATION__LOCATION:
            setLocation(LOCATION_EDEFAULT);
            return;
        case XpdExtensionPackage.REST_SERVICE_OPERATION__METHOD_ID:
            setMethodId(METHOD_ID_EDEFAULT);
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
        case XpdExtensionPackage.REST_SERVICE_OPERATION__LOCATION:
            return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
        case XpdExtensionPackage.REST_SERVICE_OPERATION__METHOD_ID:
            return METHOD_ID_EDEFAULT == null ? methodId != null : !METHOD_ID_EDEFAULT.equals(methodId);
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
        result.append(" (location: "); //$NON-NLS-1$
        result.append(location);
        result.append(", methodId: "); //$NON-NLS-1$
        result.append(methodId);
        result.append(')');
        return result.toString();
    }

} //RestServiceOperationImpl
