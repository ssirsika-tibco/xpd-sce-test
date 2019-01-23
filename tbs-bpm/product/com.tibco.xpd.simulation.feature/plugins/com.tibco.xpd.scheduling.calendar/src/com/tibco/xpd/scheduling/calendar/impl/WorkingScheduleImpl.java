/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.WorkingDay;
import com.tibco.xpd.scheduling.calendar.WorkingSchedule;
import com.tibco.xpd.scheduling.calendar.WorkingTime;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Working Schedule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl#getWorkingDay <em>Working Day</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl#getDefaultWorkingTime <em>Default Working Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkingScheduleImpl extends EObjectImpl implements WorkingSchedule {
    /**
     * The cached value of the '{@link #getWorkingDay() <em>Working Day</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkingDay()
     * @generated
     * @ordered
     */
    protected EList<WorkingDay> workingDay;

    /**
     * The cached value of the '{@link #getDefaultWorkingTime() <em>Default Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultWorkingTime()
     * @generated
     * @ordered
     */
    protected WorkingTime defaultWorkingTime;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkingScheduleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.WORKING_SCHEDULE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkingDay> getWorkingDay() {
        if (workingDay == null) {
            workingDay = new EObjectContainmentEList<WorkingDay>(WorkingDay.class, this, CalendarPackage.WORKING_SCHEDULE__WORKING_DAY);
        }
        return workingDay;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingTime getDefaultWorkingTime() {
        return defaultWorkingTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDefaultWorkingTime(WorkingTime newDefaultWorkingTime, NotificationChain msgs) {
        WorkingTime oldDefaultWorkingTime = defaultWorkingTime;
        defaultWorkingTime = newDefaultWorkingTime;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME, oldDefaultWorkingTime, newDefaultWorkingTime);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultWorkingTime(WorkingTime newDefaultWorkingTime) {
        if (newDefaultWorkingTime != defaultWorkingTime) {
            NotificationChain msgs = null;
            if (defaultWorkingTime != null)
                msgs = ((InternalEObject)defaultWorkingTime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME, null, msgs);
            if (newDefaultWorkingTime != null)
                msgs = ((InternalEObject)newDefaultWorkingTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME, null, msgs);
            msgs = basicSetDefaultWorkingTime(newDefaultWorkingTime, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME, newDefaultWorkingTime, newDefaultWorkingTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CalendarPackage.WORKING_SCHEDULE__WORKING_DAY:
                return ((InternalEList<?>)getWorkingDay()).basicRemove(otherEnd, msgs);
            case CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME:
                return basicSetDefaultWorkingTime(null, msgs);
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
            case CalendarPackage.WORKING_SCHEDULE__WORKING_DAY:
                return getWorkingDay();
            case CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME:
                return getDefaultWorkingTime();
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
            case CalendarPackage.WORKING_SCHEDULE__WORKING_DAY:
                getWorkingDay().clear();
                getWorkingDay().addAll((Collection<? extends WorkingDay>)newValue);
                return;
            case CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME:
                setDefaultWorkingTime((WorkingTime)newValue);
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
            case CalendarPackage.WORKING_SCHEDULE__WORKING_DAY:
                getWorkingDay().clear();
                return;
            case CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME:
                setDefaultWorkingTime((WorkingTime)null);
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
            case CalendarPackage.WORKING_SCHEDULE__WORKING_DAY:
                return workingDay != null && !workingDay.isEmpty();
            case CalendarPackage.WORKING_SCHEDULE__DEFAULT_WORKING_TIME:
                return defaultWorkingTime != null;
        }
        return super.eIsSet(featureID);
    }

} //WorkingScheduleImpl
