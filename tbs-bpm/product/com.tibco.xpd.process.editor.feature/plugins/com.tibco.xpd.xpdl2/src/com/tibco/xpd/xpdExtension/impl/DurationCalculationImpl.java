/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.DurationCalculation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Duration Calculation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getYears <em>Years</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getMonths <em>Months</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getWeeks <em>Weeks</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getDays <em>Days</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getHours <em>Hours</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getMinutes <em>Minutes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getSeconds <em>Seconds</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DurationCalculationImpl#getMicroseconds <em>Microseconds</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DurationCalculationImpl extends EObjectImpl
        implements DurationCalculation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getYears() <em>Years</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getYears()
     * @generated
     * @ordered
     */
    protected Expression years;

    /**
     * The cached value of the '{@link #getMonths() <em>Months</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMonths()
     * @generated
     * @ordered
     */
    protected Expression months;

    /**
     * The cached value of the '{@link #getWeeks() <em>Weeks</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWeeks()
     * @generated
     * @ordered
     */
    protected Expression weeks;

    /**
     * The cached value of the '{@link #getDays() <em>Days</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDays()
     * @generated
     * @ordered
     */
    protected Expression days;

    /**
     * The cached value of the '{@link #getHours() <em>Hours</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHours()
     * @generated
     * @ordered
     */
    protected Expression hours;

    /**
     * The cached value of the '{@link #getMinutes() <em>Minutes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinutes()
     * @generated
     * @ordered
     */
    protected Expression minutes;

    /**
     * The cached value of the '{@link #getSeconds() <em>Seconds</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSeconds()
     * @generated
     * @ordered
     */
    protected Expression seconds;

    /**
     * The cached value of the '{@link #getMicroseconds() <em>Microseconds</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMicroseconds()
     * @generated
     * @ordered
     */
    protected Expression microseconds;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DurationCalculationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DURATION_CALCULATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getYears() {
        return years;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetYears(Expression newYears,
            NotificationChain msgs) {
        Expression oldYears = years;
        years = newYears;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__YEARS,
                            oldYears, newYears);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setYears(Expression newYears) {
        if (newYears != years) {
            NotificationChain msgs = null;
            if (years != null)
                msgs = ((InternalEObject) years).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__YEARS,
                        null,
                        msgs);
            if (newYears != null)
                msgs = ((InternalEObject) newYears).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__YEARS,
                        null,
                        msgs);
            msgs = basicSetYears(newYears, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__YEARS, newYears,
                    newYears));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getMonths() {
        return months;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMonths(Expression newMonths,
            NotificationChain msgs) {
        Expression oldMonths = months;
        months = newMonths;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__MONTHS,
                            oldMonths, newMonths);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMonths(Expression newMonths) {
        if (newMonths != months) {
            NotificationChain msgs = null;
            if (months != null)
                msgs = ((InternalEObject) months).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MONTHS,
                        null,
                        msgs);
            if (newMonths != null)
                msgs = ((InternalEObject) newMonths).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MONTHS,
                        null,
                        msgs);
            msgs = basicSetMonths(newMonths, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__MONTHS, newMonths,
                    newMonths));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getWeeks() {
        return weeks;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWeeks(Expression newWeeks,
            NotificationChain msgs) {
        Expression oldWeeks = weeks;
        weeks = newWeeks;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__WEEKS,
                            oldWeeks, newWeeks);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWeeks(Expression newWeeks) {
        if (newWeeks != weeks) {
            NotificationChain msgs = null;
            if (weeks != null)
                msgs = ((InternalEObject) weeks).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__WEEKS,
                        null,
                        msgs);
            if (newWeeks != null)
                msgs = ((InternalEObject) newWeeks).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__WEEKS,
                        null,
                        msgs);
            msgs = basicSetWeeks(newWeeks, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__WEEKS, newWeeks,
                    newWeeks));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getDays() {
        return days;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDays(Expression newDays,
            NotificationChain msgs) {
        Expression oldDays = days;
        days = newDays;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__DAYS,
                            oldDays, newDays);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDays(Expression newDays) {
        if (newDays != days) {
            NotificationChain msgs = null;
            if (days != null)
                msgs = ((InternalEObject) days).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__DAYS,
                        null,
                        msgs);
            if (newDays != null)
                msgs = ((InternalEObject) newDays).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__DAYS,
                        null,
                        msgs);
            msgs = basicSetDays(newDays, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__DAYS, newDays,
                    newDays));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getHours() {
        return hours;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetHours(Expression newHours,
            NotificationChain msgs) {
        Expression oldHours = hours;
        hours = newHours;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__HOURS,
                            oldHours, newHours);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHours(Expression newHours) {
        if (newHours != hours) {
            NotificationChain msgs = null;
            if (hours != null)
                msgs = ((InternalEObject) hours).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__HOURS,
                        null,
                        msgs);
            if (newHours != null)
                msgs = ((InternalEObject) newHours).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__HOURS,
                        null,
                        msgs);
            msgs = basicSetHours(newHours, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__HOURS, newHours,
                    newHours));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getMinutes() {
        return minutes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMinutes(Expression newMinutes,
            NotificationChain msgs) {
        Expression oldMinutes = minutes;
        minutes = newMinutes;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__MINUTES,
                            oldMinutes, newMinutes);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinutes(Expression newMinutes) {
        if (newMinutes != minutes) {
            NotificationChain msgs = null;
            if (minutes != null)
                msgs = ((InternalEObject) minutes).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MINUTES,
                        null,
                        msgs);
            if (newMinutes != null)
                msgs = ((InternalEObject) newMinutes).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MINUTES,
                        null,
                        msgs);
            msgs = basicSetMinutes(newMinutes, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__MINUTES,
                    newMinutes, newMinutes));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getSeconds() {
        return seconds;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSeconds(Expression newSeconds,
            NotificationChain msgs) {
        Expression oldSeconds = seconds;
        seconds = newSeconds;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            XpdExtensionPackage.DURATION_CALCULATION__SECONDS,
                            oldSeconds, newSeconds);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSeconds(Expression newSeconds) {
        if (newSeconds != seconds) {
            NotificationChain msgs = null;
            if (seconds != null)
                msgs = ((InternalEObject) seconds).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__SECONDS,
                        null,
                        msgs);
            if (newSeconds != null)
                msgs = ((InternalEObject) newSeconds).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__SECONDS,
                        null,
                        msgs);
            msgs = basicSetSeconds(newSeconds, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__SECONDS,
                    newSeconds, newSeconds));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Expression getMicroseconds() {
        return microseconds;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMicroseconds(Expression newMicroseconds,
            NotificationChain msgs) {
        Expression oldMicroseconds = microseconds;
        microseconds = newMicroseconds;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS,
                    oldMicroseconds, newMicroseconds);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMicroseconds(Expression newMicroseconds) {
        if (newMicroseconds != microseconds) {
            NotificationChain msgs = null;
            if (microseconds != null)
                msgs = ((InternalEObject) microseconds).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS,
                        null,
                        msgs);
            if (newMicroseconds != null)
                msgs = ((InternalEObject) newMicroseconds).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS,
                        null,
                        msgs);
            msgs = basicSetMicroseconds(newMicroseconds, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS,
                    newMicroseconds, newMicroseconds));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
            return basicSetYears(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
            return basicSetMonths(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
            return basicSetWeeks(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
            return basicSetDays(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
            return basicSetHours(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
            return basicSetMinutes(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
            return basicSetSeconds(null, msgs);
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
            return basicSetMicroseconds(null, msgs);
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
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
            return getYears();
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
            return getMonths();
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
            return getWeeks();
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
            return getDays();
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
            return getHours();
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
            return getMinutes();
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
            return getSeconds();
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
            return getMicroseconds();
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
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
            setYears((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
            setMonths((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
            setWeeks((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
            setDays((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
            setHours((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
            setMinutes((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
            setSeconds((Expression) newValue);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
            setMicroseconds((Expression) newValue);
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
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
            setYears((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
            setMonths((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
            setWeeks((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
            setDays((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
            setHours((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
            setMinutes((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
            setSeconds((Expression) null);
            return;
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
            setMicroseconds((Expression) null);
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
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
            return years != null;
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
            return months != null;
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
            return weeks != null;
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
            return days != null;
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
            return hours != null;
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
            return minutes != null;
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
            return seconds != null;
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
            return microseconds != null;
        }
        return super.eIsSet(featureID);
    }

} //DurationCalculationImpl
