/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.TimeSlot;
import com.tibco.xpd.scheduling.calendar.WorkingTime;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Working Time</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.WorkingTimeImpl#getTimeSlot <em>Time Slot</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkingTimeImpl extends EObjectImpl implements WorkingTime {
    /**
     * The cached value of the '{@link #getTimeSlot() <em>Time Slot</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeSlot()
     * @generated
     * @ordered
     */
    protected EList<TimeSlot> timeSlot;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkingTimeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.WORKING_TIME;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TimeSlot> getTimeSlot() {
        if (timeSlot == null) {
            timeSlot = new EObjectContainmentEList<TimeSlot>(TimeSlot.class, this, CalendarPackage.WORKING_TIME__TIME_SLOT);
        }
        return timeSlot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CalendarPackage.WORKING_TIME__TIME_SLOT:
                return ((InternalEList<?>)getTimeSlot()).basicRemove(otherEnd, msgs);
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
            case CalendarPackage.WORKING_TIME__TIME_SLOT:
                return getTimeSlot();
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
            case CalendarPackage.WORKING_TIME__TIME_SLOT:
                getTimeSlot().clear();
                getTimeSlot().addAll((Collection<? extends TimeSlot>)newValue);
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
            case CalendarPackage.WORKING_TIME__TIME_SLOT:
                getTimeSlot().clear();
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
            case CalendarPackage.WORKING_TIME__TIME_SLOT:
                return timeSlot != null && !timeSlot.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //WorkingTimeImpl
