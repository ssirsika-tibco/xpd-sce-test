/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Process</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl#getPackageRefId <em>Package Ref Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.BusinessProcessImpl#getActivityId <em>Activity Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BusinessProcessImpl extends EObjectImpl implements BusinessProcess {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected String processId = PROCESS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getPackageRefId() <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRefId()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_REF_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageRefId() <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageRefId()
     * @generated
     * @ordered
     */
    protected String packageRefId = PACKAGE_REF_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getActivityId() <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityId()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityId() <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityId()
     * @generated
     * @ordered
     */
    protected String activityId = ACTIVITY_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BusinessProcessImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.BUSINESS_PROCESS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessId(String newProcessId) {
        String oldProcessId = processId;
        processId = newProcessId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.BUSINESS_PROCESS__PROCESS_ID,
                    oldProcessId, processId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageRefId() {
        return packageRefId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageRefId(String newPackageRefId) {
        String oldPackageRefId = packageRefId;
        packageRefId = newPackageRefId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.BUSINESS_PROCESS__PACKAGE_REF_ID,
                    oldPackageRefId, packageRefId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityId(String newActivityId) {
        String oldActivityId = activityId;
        activityId = newActivityId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.BUSINESS_PROCESS__ACTIVITY_ID,
                    oldActivityId, activityId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.BUSINESS_PROCESS__PROCESS_ID:
            return getProcessId();
        case XpdExtensionPackage.BUSINESS_PROCESS__PACKAGE_REF_ID:
            return getPackageRefId();
        case XpdExtensionPackage.BUSINESS_PROCESS__ACTIVITY_ID:
            return getActivityId();
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
        case XpdExtensionPackage.BUSINESS_PROCESS__PROCESS_ID:
            setProcessId((String) newValue);
            return;
        case XpdExtensionPackage.BUSINESS_PROCESS__PACKAGE_REF_ID:
            setPackageRefId((String) newValue);
            return;
        case XpdExtensionPackage.BUSINESS_PROCESS__ACTIVITY_ID:
            setActivityId((String) newValue);
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
        case XpdExtensionPackage.BUSINESS_PROCESS__PROCESS_ID:
            setProcessId(PROCESS_ID_EDEFAULT);
            return;
        case XpdExtensionPackage.BUSINESS_PROCESS__PACKAGE_REF_ID:
            setPackageRefId(PACKAGE_REF_ID_EDEFAULT);
            return;
        case XpdExtensionPackage.BUSINESS_PROCESS__ACTIVITY_ID:
            setActivityId(ACTIVITY_ID_EDEFAULT);
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
        case XpdExtensionPackage.BUSINESS_PROCESS__PROCESS_ID:
            return PROCESS_ID_EDEFAULT == null ? processId != null : !PROCESS_ID_EDEFAULT.equals(processId);
        case XpdExtensionPackage.BUSINESS_PROCESS__PACKAGE_REF_ID:
            return PACKAGE_REF_ID_EDEFAULT == null ? packageRefId != null
                    : !PACKAGE_REF_ID_EDEFAULT.equals(packageRefId);
        case XpdExtensionPackage.BUSINESS_PROCESS__ACTIVITY_ID:
            return ACTIVITY_ID_EDEFAULT == null ? activityId != null : !ACTIVITY_ID_EDEFAULT.equals(activityId);
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
        result.append(" (processId: "); //$NON-NLS-1$
        result.append(processId);
        result.append(", packageRefId: "); //$NON-NLS-1$
        result.append(packageRefId);
        result.append(", activityId: "); //$NON-NLS-1$
        result.append(activityId);
        result.append(')');
        return result.toString();
    }

} //BusinessProcessImpl
