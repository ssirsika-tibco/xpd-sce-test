/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemDuration;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Duration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemDurationImpl#getYears <em>Years</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemDurationImpl extends EObjectImpl implements ItemDuration {
    /**
     * The default value of the '{@link #getDays() <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDays()
     * @generated
     * @ordered
     */
    protected static final int DAYS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getDays() <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDays()
     * @generated
     * @ordered
     */
    protected int days = DAYS_EDEFAULT;

    /**
     * This is true if the Days attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean daysESet;

    /**
     * The default value of the '{@link #getHours() <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHours()
     * @generated
     * @ordered
     */
    protected static final int HOURS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getHours() <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHours()
     * @generated
     * @ordered
     */
    protected int hours = HOURS_EDEFAULT;

    /**
     * This is true if the Hours attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean hoursESet;

    /**
     * The default value of the '{@link #getMinutes() <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinutes()
     * @generated
     * @ordered
     */
    protected static final int MINUTES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMinutes() <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinutes()
     * @generated
     * @ordered
     */
    protected int minutes = MINUTES_EDEFAULT;

    /**
     * This is true if the Minutes attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean minutesESet;

    /**
     * The default value of the '{@link #getMonths() <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMonths()
     * @generated
     * @ordered
     */
    protected static final int MONTHS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMonths() <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMonths()
     * @generated
     * @ordered
     */
    protected int months = MONTHS_EDEFAULT;

    /**
     * This is true if the Months attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean monthsESet;

    /**
     * The default value of the '{@link #getWeeks() <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeeks()
     * @generated
     * @ordered
     */
    protected static final int WEEKS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getWeeks() <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeeks()
     * @generated
     * @ordered
     */
    protected int weeks = WEEKS_EDEFAULT;

    /**
     * This is true if the Weeks attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean weeksESet;

    /**
     * The default value of the '{@link #getYears() <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYears()
     * @generated
     * @ordered
     */
    protected static final int YEARS_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getYears() <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYears()
     * @generated
     * @ordered
     */
    protected int years = YEARS_EDEFAULT;

    /**
     * This is true if the Years attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean yearsESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ItemDurationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ITEM_DURATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getDays() {
        return days;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDays(int newDays) {
        int oldDays = days;
        days = newDays;
        boolean oldDaysESet = daysESet;
        daysESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__DAYS, oldDays, days, !oldDaysESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDays() {
        int oldDays = days;
        boolean oldDaysESet = daysESet;
        days = DAYS_EDEFAULT;
        daysESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__DAYS, oldDays, DAYS_EDEFAULT, oldDaysESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDays() {
        return daysESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getHours() {
        return hours;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHours(int newHours) {
        int oldHours = hours;
        hours = newHours;
        boolean oldHoursESet = hoursESet;
        hoursESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__HOURS, oldHours, hours, !oldHoursESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetHours() {
        int oldHours = hours;
        boolean oldHoursESet = hoursESet;
        hours = HOURS_EDEFAULT;
        hoursESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__HOURS, oldHours, HOURS_EDEFAULT, oldHoursESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetHours() {
        return hoursESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinutes(int newMinutes) {
        int oldMinutes = minutes;
        minutes = newMinutes;
        boolean oldMinutesESet = minutesESet;
        minutesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__MINUTES, oldMinutes, minutes, !oldMinutesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMinutes() {
        int oldMinutes = minutes;
        boolean oldMinutesESet = minutesESet;
        minutes = MINUTES_EDEFAULT;
        minutesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__MINUTES, oldMinutes, MINUTES_EDEFAULT, oldMinutesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMinutes() {
        return minutesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getMonths() {
        return months;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMonths(int newMonths) {
        int oldMonths = months;
        months = newMonths;
        boolean oldMonthsESet = monthsESet;
        monthsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__MONTHS, oldMonths, months, !oldMonthsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMonths() {
        int oldMonths = months;
        boolean oldMonthsESet = monthsESet;
        months = MONTHS_EDEFAULT;
        monthsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__MONTHS, oldMonths, MONTHS_EDEFAULT, oldMonthsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMonths() {
        return monthsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getWeeks() {
        return weeks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWeeks(int newWeeks) {
        int oldWeeks = weeks;
        weeks = newWeeks;
        boolean oldWeeksESet = weeksESet;
        weeksESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__WEEKS, oldWeeks, weeks, !oldWeeksESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWeeks() {
        int oldWeeks = weeks;
        boolean oldWeeksESet = weeksESet;
        weeks = WEEKS_EDEFAULT;
        weeksESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__WEEKS, oldWeeks, WEEKS_EDEFAULT, oldWeeksESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWeeks() {
        return weeksESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getYears() {
        return years;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setYears(int newYears) {
        int oldYears = years;
        years = newYears;
        boolean oldYearsESet = yearsESet;
        yearsESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_DURATION__YEARS, oldYears, years, !oldYearsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetYears() {
        int oldYears = years;
        boolean oldYearsESet = yearsESet;
        years = YEARS_EDEFAULT;
        yearsESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.ITEM_DURATION__YEARS, oldYears, YEARS_EDEFAULT, oldYearsESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetYears() {
        return yearsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.ITEM_DURATION__DAYS:
                return getDays();
            case N2BRMPackage.ITEM_DURATION__HOURS:
                return getHours();
            case N2BRMPackage.ITEM_DURATION__MINUTES:
                return getMinutes();
            case N2BRMPackage.ITEM_DURATION__MONTHS:
                return getMonths();
            case N2BRMPackage.ITEM_DURATION__WEEKS:
                return getWeeks();
            case N2BRMPackage.ITEM_DURATION__YEARS:
                return getYears();
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
            case N2BRMPackage.ITEM_DURATION__DAYS:
                setDays((Integer)newValue);
                return;
            case N2BRMPackage.ITEM_DURATION__HOURS:
                setHours((Integer)newValue);
                return;
            case N2BRMPackage.ITEM_DURATION__MINUTES:
                setMinutes((Integer)newValue);
                return;
            case N2BRMPackage.ITEM_DURATION__MONTHS:
                setMonths((Integer)newValue);
                return;
            case N2BRMPackage.ITEM_DURATION__WEEKS:
                setWeeks((Integer)newValue);
                return;
            case N2BRMPackage.ITEM_DURATION__YEARS:
                setYears((Integer)newValue);
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
            case N2BRMPackage.ITEM_DURATION__DAYS:
                unsetDays();
                return;
            case N2BRMPackage.ITEM_DURATION__HOURS:
                unsetHours();
                return;
            case N2BRMPackage.ITEM_DURATION__MINUTES:
                unsetMinutes();
                return;
            case N2BRMPackage.ITEM_DURATION__MONTHS:
                unsetMonths();
                return;
            case N2BRMPackage.ITEM_DURATION__WEEKS:
                unsetWeeks();
                return;
            case N2BRMPackage.ITEM_DURATION__YEARS:
                unsetYears();
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
            case N2BRMPackage.ITEM_DURATION__DAYS:
                return isSetDays();
            case N2BRMPackage.ITEM_DURATION__HOURS:
                return isSetHours();
            case N2BRMPackage.ITEM_DURATION__MINUTES:
                return isSetMinutes();
            case N2BRMPackage.ITEM_DURATION__MONTHS:
                return isSetMonths();
            case N2BRMPackage.ITEM_DURATION__WEEKS:
                return isSetWeeks();
            case N2BRMPackage.ITEM_DURATION__YEARS:
                return isSetYears();
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
        result.append(" (days: ");
        if (daysESet) result.append(days); else result.append("<unset>");
        result.append(", hours: ");
        if (hoursESet) result.append(hours); else result.append("<unset>");
        result.append(", minutes: ");
        if (minutesESet) result.append(minutes); else result.append("<unset>");
        result.append(", months: ");
        if (monthsESet) result.append(months); else result.append("<unset>");
        result.append(", weeks: ");
        if (weeksESet) result.append(weeks); else result.append("<unset>");
        result.append(", years: ");
        if (yearsESet) result.append(years); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //ItemDurationImpl
