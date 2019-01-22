/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalculatedHolidayType;
import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.PublicHolidays;
import com.tibco.xpd.scheduling.calendar.SingleHolidayType;

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
 * An implementation of the model object '<em><b>Public Holidays</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl#getSingleHoliday <em>Single Holiday</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl#getCalculatedHoliday <em>Calculated Holiday</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PublicHolidaysImpl extends EObjectImpl implements PublicHolidays {
    /**
     * The cached value of the '{@link #getSingleHoliday() <em>Single Holiday</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSingleHoliday()
     * @generated
     * @ordered
     */
    protected EList<SingleHolidayType> singleHoliday;

    /**
     * The cached value of the '{@link #getCalculatedHoliday() <em>Calculated Holiday</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCalculatedHoliday()
     * @generated
     * @ordered
     */
    protected EList<CalculatedHolidayType> calculatedHoliday;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PublicHolidaysImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.PUBLIC_HOLIDAYS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SingleHolidayType> getSingleHoliday() {
        if (singleHoliday == null) {
            singleHoliday = new EObjectContainmentEList<SingleHolidayType>(SingleHolidayType.class, this, CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY);
        }
        return singleHoliday;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CalculatedHolidayType> getCalculatedHoliday() {
        if (calculatedHoliday == null) {
            calculatedHoliday = new EObjectContainmentEList<CalculatedHolidayType>(CalculatedHolidayType.class, this, CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY);
        }
        return calculatedHoliday;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY:
                return ((InternalEList<?>)getSingleHoliday()).basicRemove(otherEnd, msgs);
            case CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY:
                return ((InternalEList<?>)getCalculatedHoliday()).basicRemove(otherEnd, msgs);
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
            case CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY:
                return getSingleHoliday();
            case CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY:
                return getCalculatedHoliday();
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
            case CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY:
                getSingleHoliday().clear();
                getSingleHoliday().addAll((Collection<? extends SingleHolidayType>)newValue);
                return;
            case CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY:
                getCalculatedHoliday().clear();
                getCalculatedHoliday().addAll((Collection<? extends CalculatedHolidayType>)newValue);
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
            case CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY:
                getSingleHoliday().clear();
                return;
            case CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY:
                getCalculatedHoliday().clear();
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
            case CalendarPackage.PUBLIC_HOLIDAYS__SINGLE_HOLIDAY:
                return singleHoliday != null && !singleHoliday.isEmpty();
            case CalendarPackage.PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY:
                return calculatedHoliday != null && !calculatedHoliday.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //PublicHolidaysImpl
