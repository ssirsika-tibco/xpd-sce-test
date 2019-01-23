/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemDuration;
import com.tibco.n2.brm.api.ItemSchedule;
import com.tibco.n2.brm.api.N2BRMPackage;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Schedule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemScheduleImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemScheduleImpl#getMaxDuration <em>Max Duration</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ItemScheduleImpl#getTargetDate <em>Target Date</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemScheduleImpl extends EObjectImpl implements ItemSchedule {
    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar startDate = START_DATE_EDEFAULT;

    /**
     * The cached value of the '{@link #getMaxDuration() <em>Max Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxDuration()
     * @generated
     * @ordered
     */
    protected ItemDuration maxDuration;

    /**
     * The default value of the '{@link #getTargetDate() <em>Target Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar TARGET_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTargetDate() <em>Target Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTargetDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar targetDate = TARGET_DATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ItemScheduleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.ITEM_SCHEDULE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(XMLGregorianCalendar newStartDate) {
        XMLGregorianCalendar oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_SCHEDULE__START_DATE, oldStartDate, startDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemDuration getMaxDuration() {
        return maxDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMaxDuration(ItemDuration newMaxDuration, NotificationChain msgs) {
        ItemDuration oldMaxDuration = maxDuration;
        maxDuration = newMaxDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION, oldMaxDuration, newMaxDuration);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxDuration(ItemDuration newMaxDuration) {
        if (newMaxDuration != maxDuration) {
            NotificationChain msgs = null;
            if (maxDuration != null)
                msgs = ((InternalEObject)maxDuration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION, null, msgs);
            if (newMaxDuration != null)
                msgs = ((InternalEObject)newMaxDuration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION, null, msgs);
            msgs = basicSetMaxDuration(newMaxDuration, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION, newMaxDuration, newMaxDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getTargetDate() {
        return targetDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTargetDate(XMLGregorianCalendar newTargetDate) {
        XMLGregorianCalendar oldTargetDate = targetDate;
        targetDate = newTargetDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.ITEM_SCHEDULE__TARGET_DATE, oldTargetDate, targetDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION:
                return basicSetMaxDuration(null, msgs);
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
            case N2BRMPackage.ITEM_SCHEDULE__START_DATE:
                return getStartDate();
            case N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION:
                return getMaxDuration();
            case N2BRMPackage.ITEM_SCHEDULE__TARGET_DATE:
                return getTargetDate();
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
            case N2BRMPackage.ITEM_SCHEDULE__START_DATE:
                setStartDate((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION:
                setMaxDuration((ItemDuration)newValue);
                return;
            case N2BRMPackage.ITEM_SCHEDULE__TARGET_DATE:
                setTargetDate((XMLGregorianCalendar)newValue);
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
            case N2BRMPackage.ITEM_SCHEDULE__START_DATE:
                setStartDate(START_DATE_EDEFAULT);
                return;
            case N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION:
                setMaxDuration((ItemDuration)null);
                return;
            case N2BRMPackage.ITEM_SCHEDULE__TARGET_DATE:
                setTargetDate(TARGET_DATE_EDEFAULT);
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
            case N2BRMPackage.ITEM_SCHEDULE__START_DATE:
                return START_DATE_EDEFAULT == null ? startDate != null : !START_DATE_EDEFAULT.equals(startDate);
            case N2BRMPackage.ITEM_SCHEDULE__MAX_DURATION:
                return maxDuration != null;
            case N2BRMPackage.ITEM_SCHEDULE__TARGET_DATE:
                return TARGET_DATE_EDEFAULT == null ? targetDate != null : !TARGET_DATE_EDEFAULT.equals(targetDate);
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
        result.append(" (startDate: ");
        result.append(startDate);
        result.append(", targetDate: ");
        result.append(targetDate);
        result.append(')');
        return result.toString();
    }

} //ItemScheduleImpl
