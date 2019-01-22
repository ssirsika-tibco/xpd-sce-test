/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.LocationType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Location Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.LocationTypeImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.LocationTypeImpl#getTimezone <em>Timezone</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocationTypeImpl extends EntityTypeImpl implements LocationType {
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
     * The default value of the '{@link #getTimezone() <em>Timezone</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimezone()
     * @generated
     * @ordered
     */
    protected static final String TIMEZONE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTimezone() <em>Timezone</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimezone()
     * @generated
     * @ordered
     */
    protected String timezone = TIMEZONE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LocationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.LOCATION_TYPE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.LOCATION_TYPE__LOCALE, oldLocale, locale));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimezone(String newTimezone) {
        String oldTimezone = timezone;
        timezone = newTimezone;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.LOCATION_TYPE__TIMEZONE, oldTimezone, timezone));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case DePackage.LOCATION_TYPE__LOCALE:
                return getLocale();
            case DePackage.LOCATION_TYPE__TIMEZONE:
                return getTimezone();
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
            case DePackage.LOCATION_TYPE__LOCALE:
                setLocale((String)newValue);
                return;
            case DePackage.LOCATION_TYPE__TIMEZONE:
                setTimezone((String)newValue);
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
            case DePackage.LOCATION_TYPE__LOCALE:
                setLocale(LOCALE_EDEFAULT);
                return;
            case DePackage.LOCATION_TYPE__TIMEZONE:
                setTimezone(TIMEZONE_EDEFAULT);
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
            case DePackage.LOCATION_TYPE__LOCALE:
                return LOCALE_EDEFAULT == null ? locale != null : !LOCALE_EDEFAULT.equals(locale);
            case DePackage.LOCATION_TYPE__TIMEZONE:
                return TIMEZONE_EDEFAULT == null ? timezone != null : !TIMEZONE_EDEFAULT.equals(timezone);
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
        result.append(" (locale: ");
        result.append(locale);
        result.append(", timezone: ");
        result.append(timezone);
        result.append(')');
        return result.toString();
    }

} //LocationTypeImpl
