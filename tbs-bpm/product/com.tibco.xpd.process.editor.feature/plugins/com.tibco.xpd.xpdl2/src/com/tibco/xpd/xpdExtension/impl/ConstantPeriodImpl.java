/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constant Period</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getMicroSeconds <em>Micro Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ConstantPeriodImpl#getYears <em>Years</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstantPeriodImpl extends EObjectImpl implements ConstantPeriod {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getDays() <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDays()
     * @generated
     * @ordered
     */
    protected static final BigInteger DAYS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDays() <em>Days</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDays()
     * @generated
     * @ordered
     */
    protected BigInteger days = DAYS_EDEFAULT;

    /**
     * The default value of the '{@link #getHours() <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHours()
     * @generated
     * @ordered
     */
    protected static final BigInteger HOURS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHours() <em>Hours</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHours()
     * @generated
     * @ordered
     */
    protected BigInteger hours = HOURS_EDEFAULT;

    /**
     * The default value of the '{@link #getMicroSeconds() <em>Micro Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMicroSeconds()
     * @generated
     * @ordered
     */
    protected static final BigInteger MICRO_SECONDS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMicroSeconds() <em>Micro Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMicroSeconds()
     * @generated
     * @ordered
     */
    protected BigInteger microSeconds = MICRO_SECONDS_EDEFAULT;

    /**
     * The default value of the '{@link #getMinutes() <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinutes()
     * @generated
     * @ordered
     */
    protected static final BigInteger MINUTES_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMinutes() <em>Minutes</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinutes()
     * @generated
     * @ordered
     */
    protected BigInteger minutes = MINUTES_EDEFAULT;

    /**
     * The default value of the '{@link #getMonths() <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMonths()
     * @generated
     * @ordered
     */
    protected static final BigInteger MONTHS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMonths() <em>Months</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMonths()
     * @generated
     * @ordered
     */
    protected BigInteger months = MONTHS_EDEFAULT;

    /**
     * The default value of the '{@link #getSeconds() <em>Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeconds()
     * @generated
     * @ordered
     */
    protected static final BigInteger SECONDS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSeconds() <em>Seconds</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeconds()
     * @generated
     * @ordered
     */
    protected BigInteger seconds = SECONDS_EDEFAULT;

    /**
     * The default value of the '{@link #getWeeks() <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeeks()
     * @generated
     * @ordered
     */
    protected static final BigInteger WEEKS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWeeks() <em>Weeks</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeeks()
     * @generated
     * @ordered
     */
    protected BigInteger weeks = WEEKS_EDEFAULT;

    /**
     * The default value of the '{@link #getYears() <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYears()
     * @generated
     * @ordered
     */
    protected static final BigInteger YEARS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getYears() <em>Years</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYears()
     * @generated
     * @ordered
     */
    protected BigInteger years = YEARS_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConstantPeriodImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CONSTANT_PERIOD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getDays() {
        return days;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDays(BigInteger newDays) {
        BigInteger oldDays = days;
        days = newDays;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__DAYS, oldDays,
                    days));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getHours() {
        return hours;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHours(BigInteger newHours) {
        BigInteger oldHours = hours;
        hours = newHours;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__HOURS, oldHours,
                    hours));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getMicroSeconds() {
        return microSeconds;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMicroSeconds(BigInteger newMicroSeconds) {
        BigInteger oldMicroSeconds = microSeconds;
        microSeconds = newMicroSeconds;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS,
                    oldMicroSeconds, microSeconds));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getMinutes() {
        return minutes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinutes(BigInteger newMinutes) {
        BigInteger oldMinutes = minutes;
        minutes = newMinutes;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__MINUTES,
                    oldMinutes, minutes));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getMonths() {
        return months;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMonths(BigInteger newMonths) {
        BigInteger oldMonths = months;
        months = newMonths;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__MONTHS,
                    oldMonths, months));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getSeconds() {
        return seconds;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSeconds(BigInteger newSeconds) {
        BigInteger oldSeconds = seconds;
        seconds = newSeconds;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__SECONDS,
                    oldSeconds, seconds));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getWeeks() {
        return weeks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWeeks(BigInteger newWeeks) {
        BigInteger oldWeeks = weeks;
        weeks = newWeeks;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__WEEKS, oldWeeks,
                    weeks));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getYears() {
        return years;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setYears(BigInteger newYears) {
        BigInteger oldYears = years;
        years = newYears;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.CONSTANT_PERIOD__YEARS, oldYears,
                    years));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.CONSTANT_PERIOD__DAYS:
            return getDays();
        case XpdExtensionPackage.CONSTANT_PERIOD__HOURS:
            return getHours();
        case XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS:
            return getMicroSeconds();
        case XpdExtensionPackage.CONSTANT_PERIOD__MINUTES:
            return getMinutes();
        case XpdExtensionPackage.CONSTANT_PERIOD__MONTHS:
            return getMonths();
        case XpdExtensionPackage.CONSTANT_PERIOD__SECONDS:
            return getSeconds();
        case XpdExtensionPackage.CONSTANT_PERIOD__WEEKS:
            return getWeeks();
        case XpdExtensionPackage.CONSTANT_PERIOD__YEARS:
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
        case XpdExtensionPackage.CONSTANT_PERIOD__DAYS:
            setDays((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__HOURS:
            setHours((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS:
            setMicroSeconds((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MINUTES:
            setMinutes((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MONTHS:
            setMonths((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__SECONDS:
            setSeconds((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__WEEKS:
            setWeeks((BigInteger) newValue);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__YEARS:
            setYears((BigInteger) newValue);
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
        case XpdExtensionPackage.CONSTANT_PERIOD__DAYS:
            setDays(DAYS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__HOURS:
            setHours(HOURS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS:
            setMicroSeconds(MICRO_SECONDS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MINUTES:
            setMinutes(MINUTES_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__MONTHS:
            setMonths(MONTHS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__SECONDS:
            setSeconds(SECONDS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__WEEKS:
            setWeeks(WEEKS_EDEFAULT);
            return;
        case XpdExtensionPackage.CONSTANT_PERIOD__YEARS:
            setYears(YEARS_EDEFAULT);
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
        case XpdExtensionPackage.CONSTANT_PERIOD__DAYS:
            return DAYS_EDEFAULT == null ? days != null : !DAYS_EDEFAULT.equals(days);
        case XpdExtensionPackage.CONSTANT_PERIOD__HOURS:
            return HOURS_EDEFAULT == null ? hours != null : !HOURS_EDEFAULT.equals(hours);
        case XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS:
            return MICRO_SECONDS_EDEFAULT == null ? microSeconds != null : !MICRO_SECONDS_EDEFAULT.equals(microSeconds);
        case XpdExtensionPackage.CONSTANT_PERIOD__MINUTES:
            return MINUTES_EDEFAULT == null ? minutes != null : !MINUTES_EDEFAULT.equals(minutes);
        case XpdExtensionPackage.CONSTANT_PERIOD__MONTHS:
            return MONTHS_EDEFAULT == null ? months != null : !MONTHS_EDEFAULT.equals(months);
        case XpdExtensionPackage.CONSTANT_PERIOD__SECONDS:
            return SECONDS_EDEFAULT == null ? seconds != null : !SECONDS_EDEFAULT.equals(seconds);
        case XpdExtensionPackage.CONSTANT_PERIOD__WEEKS:
            return WEEKS_EDEFAULT == null ? weeks != null : !WEEKS_EDEFAULT.equals(weeks);
        case XpdExtensionPackage.CONSTANT_PERIOD__YEARS:
            return YEARS_EDEFAULT == null ? years != null : !YEARS_EDEFAULT.equals(years);
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
        result.append(" (days: "); //$NON-NLS-1$
        result.append(days);
        result.append(", hours: "); //$NON-NLS-1$
        result.append(hours);
        result.append(", microSeconds: "); //$NON-NLS-1$
        result.append(microSeconds);
        result.append(", minutes: "); //$NON-NLS-1$
        result.append(minutes);
        result.append(", months: "); //$NON-NLS-1$
        result.append(months);
        result.append(", seconds: "); //$NON-NLS-1$
        result.append(seconds);
        result.append(", weeks: "); //$NON-NLS-1$
        result.append(weeks);
        result.append(", years: "); //$NON-NLS-1$
        result.append(years);
        result.append(')');
        return result.toString();
    }

} //ConstantPeriodImpl