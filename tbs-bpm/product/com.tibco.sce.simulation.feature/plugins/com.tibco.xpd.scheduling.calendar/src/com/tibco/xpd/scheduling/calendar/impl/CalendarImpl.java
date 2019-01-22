/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.Calendar;
import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.PublicHolidays;
import com.tibco.xpd.scheduling.calendar.WorkingSchedule;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Calendar</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl#getDefaultWorkingSchedule <em>Default Working Schedule</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl#getPublicHolidays <em>Public Holidays</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CalendarImpl extends EObjectImpl implements Calendar {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getLocale() <em>Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocale()
     * @generated
     * @ordered
     */
    protected static final String LOCALE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocale() <em>Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocale()
     * @generated
     * @ordered
     */
    protected String locale = LOCALE_EDEFAULT;

    /**
     * The cached value of the '{@link #getDefaultWorkingSchedule() <em>Default Working Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultWorkingSchedule()
     * @generated
     * @ordered
     */
    protected WorkingSchedule defaultWorkingSchedule;

    /**
     * The cached value of the '{@link #getPublicHolidays() <em>Public Holidays</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPublicHolidays()
     * @generated
     * @ordered
     */
    protected PublicHolidays publicHolidays;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CalendarImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CalendarPackage.Literals.CALENDAR;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLocale() {
        return locale;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocale(String newLocale) {
        String oldLocale = locale;
        locale = newLocale;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__LOCALE, oldLocale, locale));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingSchedule getDefaultWorkingSchedule() {
        return defaultWorkingSchedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDefaultWorkingSchedule(WorkingSchedule newDefaultWorkingSchedule, NotificationChain msgs) {
        WorkingSchedule oldDefaultWorkingSchedule = defaultWorkingSchedule;
        defaultWorkingSchedule = newDefaultWorkingSchedule;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE, oldDefaultWorkingSchedule, newDefaultWorkingSchedule);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultWorkingSchedule(WorkingSchedule newDefaultWorkingSchedule) {
        if (newDefaultWorkingSchedule != defaultWorkingSchedule) {
            NotificationChain msgs = null;
            if (defaultWorkingSchedule != null)
                msgs = ((InternalEObject)defaultWorkingSchedule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE, null, msgs);
            if (newDefaultWorkingSchedule != null)
                msgs = ((InternalEObject)newDefaultWorkingSchedule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE, null, msgs);
            msgs = basicSetDefaultWorkingSchedule(newDefaultWorkingSchedule, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE, newDefaultWorkingSchedule, newDefaultWorkingSchedule));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PublicHolidays getPublicHolidays() {
        return publicHolidays;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPublicHolidays(PublicHolidays newPublicHolidays, NotificationChain msgs) {
        PublicHolidays oldPublicHolidays = publicHolidays;
        publicHolidays = newPublicHolidays;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS, oldPublicHolidays, newPublicHolidays);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPublicHolidays(PublicHolidays newPublicHolidays) {
        if (newPublicHolidays != publicHolidays) {
            NotificationChain msgs = null;
            if (publicHolidays != null)
                msgs = ((InternalEObject)publicHolidays).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS, null, msgs);
            if (newPublicHolidays != null)
                msgs = ((InternalEObject)newPublicHolidays).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS, null, msgs);
            msgs = basicSetPublicHolidays(newPublicHolidays, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS, newPublicHolidays, newPublicHolidays));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE:
                return basicSetDefaultWorkingSchedule(null, msgs);
            case CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS:
                return basicSetPublicHolidays(null, msgs);
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
            case CalendarPackage.CALENDAR__NAME:
                return getName();
            case CalendarPackage.CALENDAR__LOCALE:
                return getLocale();
            case CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE:
                return getDefaultWorkingSchedule();
            case CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS:
                return getPublicHolidays();
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
            case CalendarPackage.CALENDAR__NAME:
                setName((String)newValue);
                return;
            case CalendarPackage.CALENDAR__LOCALE:
                setLocale((String)newValue);
                return;
            case CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE:
                setDefaultWorkingSchedule((WorkingSchedule)newValue);
                return;
            case CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS:
                setPublicHolidays((PublicHolidays)newValue);
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
            case CalendarPackage.CALENDAR__NAME:
                setName(NAME_EDEFAULT);
                return;
            case CalendarPackage.CALENDAR__LOCALE:
                setLocale(LOCALE_EDEFAULT);
                return;
            case CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE:
                setDefaultWorkingSchedule((WorkingSchedule)null);
                return;
            case CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS:
                setPublicHolidays((PublicHolidays)null);
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
            case CalendarPackage.CALENDAR__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case CalendarPackage.CALENDAR__LOCALE:
                return LOCALE_EDEFAULT == null ? locale != null : !LOCALE_EDEFAULT.equals(locale);
            case CalendarPackage.CALENDAR__DEFAULT_WORKING_SCHEDULE:
                return defaultWorkingSchedule != null;
            case CalendarPackage.CALENDAR__PUBLIC_HOLIDAYS:
                return publicHolidays != null;
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
        result.append(" (name: ");
        result.append(name);
        result.append(", locale: ");
        result.append(locale);
        result.append(')');
        return result.toString();
    }

} //CalendarImpl
