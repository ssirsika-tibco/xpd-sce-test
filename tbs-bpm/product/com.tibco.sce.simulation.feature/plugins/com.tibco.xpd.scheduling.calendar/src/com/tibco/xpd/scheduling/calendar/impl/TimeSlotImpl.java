/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.TimeSlot;

import javax.xml.datatype.XMLGregorianCalendar;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl#getEndTime <em>End Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeSlotImpl extends EObjectImpl implements TimeSlot {
    /**
     * The default value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartTime()
     * @generated
     * @ordered
     */
    protected static final String START_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartTime()
     * @generated
     * @ordered
     */
    protected String startTime = START_TIME_EDEFAULT;

    /**
     * The default value of the '{@link #getEndTime() <em>End Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndTime()
     * @generated
     * @ordered
     */
    protected static final String END_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndTime() <em>End Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndTime()
     * @generated
     * @ordered
     */
    protected String endTime = END_TIME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TimeSlotImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.TIME_SLOT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartTime(String newStartTime) {
        String oldStartTime = startTime;
        startTime = newStartTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.TIME_SLOT__START_TIME, oldStartTime, startTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndTime(String newEndTime) {
        String oldEndTime = endTime;
        endTime = newEndTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.TIME_SLOT__END_TIME, oldEndTime, endTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CalendarPackage.TIME_SLOT__START_TIME:
                return getStartTime();
            case CalendarPackage.TIME_SLOT__END_TIME:
                return getEndTime();
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
            case CalendarPackage.TIME_SLOT__START_TIME:
                setStartTime((String)newValue);
                return;
            case CalendarPackage.TIME_SLOT__END_TIME:
                setEndTime((String)newValue);
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
            case CalendarPackage.TIME_SLOT__START_TIME:
                setStartTime(START_TIME_EDEFAULT);
                return;
            case CalendarPackage.TIME_SLOT__END_TIME:
                setEndTime(END_TIME_EDEFAULT);
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
            case CalendarPackage.TIME_SLOT__START_TIME:
                return START_TIME_EDEFAULT == null ? startTime != null : !START_TIME_EDEFAULT.equals(startTime);
            case CalendarPackage.TIME_SLOT__END_TIME:
                return END_TIME_EDEFAULT == null ? endTime != null : !END_TIME_EDEFAULT.equals(endTime);
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
        result.append(" (startTime: ");
        result.append(startTime);
        result.append(", endTime: ");
        result.append(endTime);
        result.append(')');
        return result.toString();
    }

} //TimeSlotImpl
