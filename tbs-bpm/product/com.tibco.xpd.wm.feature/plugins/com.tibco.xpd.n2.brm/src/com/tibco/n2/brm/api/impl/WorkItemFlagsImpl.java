/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.ScheduleStatus;
import com.tibco.n2.brm.api.WorkItemFlags;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Flags</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemFlagsImpl#getScheduleStatus <em>Schedule Status</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemFlagsImpl extends EObjectImpl implements WorkItemFlags {
    /**
     * The default value of the '{@link #getScheduleStatus() <em>Schedule Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScheduleStatus()
     * @generated
     * @ordered
     */
    protected static final ScheduleStatus SCHEDULE_STATUS_EDEFAULT = ScheduleStatus.NOSCHEDULE;

    /**
     * The cached value of the '{@link #getScheduleStatus() <em>Schedule Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScheduleStatus()
     * @generated
     * @ordered
     */
    protected ScheduleStatus scheduleStatus = SCHEDULE_STATUS_EDEFAULT;

    /**
     * This is true if the Schedule Status attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean scheduleStatusESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemFlagsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_FLAGS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScheduleStatus(ScheduleStatus newScheduleStatus) {
        ScheduleStatus oldScheduleStatus = scheduleStatus;
        scheduleStatus = newScheduleStatus == null ? SCHEDULE_STATUS_EDEFAULT : newScheduleStatus;
        boolean oldScheduleStatusESet = scheduleStatusESet;
        scheduleStatusESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS, oldScheduleStatus, scheduleStatus, !oldScheduleStatusESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetScheduleStatus() {
        ScheduleStatus oldScheduleStatus = scheduleStatus;
        boolean oldScheduleStatusESet = scheduleStatusESet;
        scheduleStatus = SCHEDULE_STATUS_EDEFAULT;
        scheduleStatusESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS, oldScheduleStatus, SCHEDULE_STATUS_EDEFAULT, oldScheduleStatusESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetScheduleStatus() {
        return scheduleStatusESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS:
                return getScheduleStatus();
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
            case N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS:
                setScheduleStatus((ScheduleStatus)newValue);
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
            case N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS:
                unsetScheduleStatus();
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
            case N2BRMPackage.WORK_ITEM_FLAGS__SCHEDULE_STATUS:
                return isSetScheduleStatus();
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (scheduleStatus: ");
        if (scheduleStatusESet) result.append(scheduleStatus); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkItemFlagsImpl
