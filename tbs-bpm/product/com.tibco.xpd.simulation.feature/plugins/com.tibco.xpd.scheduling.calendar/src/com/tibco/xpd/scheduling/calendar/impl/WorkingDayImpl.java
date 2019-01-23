/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.WorkingDay;
import com.tibco.xpd.scheduling.calendar.WorkingTime;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Working Day</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl#getDayOfWeek <em>Day Of Week</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl#getWorkingTime <em>Working Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkingDayImpl extends EObjectImpl implements WorkingDay {
    /**
     * The default value of the '{@link #getDayOfWeek() <em>Day Of Week</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDayOfWeek()
     * @generated
     * @ordered
     */
    protected static final int DAY_OF_WEEK_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getDayOfWeek() <em>Day Of Week</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDayOfWeek()
     * @generated
     * @ordered
     */
    protected int dayOfWeek = DAY_OF_WEEK_EDEFAULT;

    /**
     * This is true if the Day Of Week attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean dayOfWeekESet;

    /**
     * The cached value of the '{@link #getWorkingTime() <em>Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkingTime()
     * @generated
     * @ordered
     */
    protected WorkingTime workingTime;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkingDayImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.WORKING_DAY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDayOfWeek(int newDayOfWeek) {
        int oldDayOfWeek = dayOfWeek;
        dayOfWeek = newDayOfWeek;
        boolean oldDayOfWeekESet = dayOfWeekESet;
        dayOfWeekESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.WORKING_DAY__DAY_OF_WEEK, oldDayOfWeek, dayOfWeek, !oldDayOfWeekESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDayOfWeek() {
        int oldDayOfWeek = dayOfWeek;
        boolean oldDayOfWeekESet = dayOfWeekESet;
        dayOfWeek = DAY_OF_WEEK_EDEFAULT;
        dayOfWeekESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, CalendarPackage.WORKING_DAY__DAY_OF_WEEK, oldDayOfWeek, DAY_OF_WEEK_EDEFAULT, oldDayOfWeekESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDayOfWeek() {
        return dayOfWeekESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingTime getWorkingTime() {
        return workingTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkingTime(WorkingTime newWorkingTime, NotificationChain msgs) {
        WorkingTime oldWorkingTime = workingTime;
        workingTime = newWorkingTime;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalendarPackage.WORKING_DAY__WORKING_TIME, oldWorkingTime, newWorkingTime);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkingTime(WorkingTime newWorkingTime) {
        if (newWorkingTime != workingTime) {
            NotificationChain msgs = null;
            if (workingTime != null)
                msgs = ((InternalEObject)workingTime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.WORKING_DAY__WORKING_TIME, null, msgs);
            if (newWorkingTime != null)
                msgs = ((InternalEObject)newWorkingTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.WORKING_DAY__WORKING_TIME, null, msgs);
            msgs = basicSetWorkingTime(newWorkingTime, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.WORKING_DAY__WORKING_TIME, newWorkingTime, newWorkingTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CalendarPackage.WORKING_DAY__WORKING_TIME:
                return basicSetWorkingTime(null, msgs);
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
            case CalendarPackage.WORKING_DAY__DAY_OF_WEEK:
                return new Integer(getDayOfWeek());
            case CalendarPackage.WORKING_DAY__WORKING_TIME:
                return getWorkingTime();
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
            case CalendarPackage.WORKING_DAY__DAY_OF_WEEK:
                setDayOfWeek(((Integer)newValue).intValue());
                return;
            case CalendarPackage.WORKING_DAY__WORKING_TIME:
                setWorkingTime((WorkingTime)newValue);
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
            case CalendarPackage.WORKING_DAY__DAY_OF_WEEK:
                unsetDayOfWeek();
                return;
            case CalendarPackage.WORKING_DAY__WORKING_TIME:
                setWorkingTime((WorkingTime)null);
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
            case CalendarPackage.WORKING_DAY__DAY_OF_WEEK:
                return isSetDayOfWeek();
            case CalendarPackage.WORKING_DAY__WORKING_TIME:
                return workingTime != null;
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
        result.append(" (dayOfWeek: ");
        if (dayOfWeekESet) result.append(dayOfWeek); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkingDayImpl
