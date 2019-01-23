/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.HiddenPeriod;
import com.tibco.n2.brm.api.ItemDuration;
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
 * An implementation of the model object '<em><b>Hidden Period</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.HiddenPeriodImpl#getHiddenDuration <em>Hidden Duration</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.HiddenPeriodImpl#getVisibleDate <em>Visible Date</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HiddenPeriodImpl extends EObjectImpl implements HiddenPeriod {
    /**
     * The cached value of the '{@link #getHiddenDuration() <em>Hidden Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHiddenDuration()
     * @generated
     * @ordered
     */
    protected ItemDuration hiddenDuration;

    /**
     * The default value of the '{@link #getVisibleDate() <em>Visible Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVisibleDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar VISIBLE_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVisibleDate() <em>Visible Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVisibleDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar visibleDate = VISIBLE_DATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HiddenPeriodImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.HIDDEN_PERIOD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemDuration getHiddenDuration() {
        return hiddenDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetHiddenDuration(ItemDuration newHiddenDuration, NotificationChain msgs) {
        ItemDuration oldHiddenDuration = hiddenDuration;
        hiddenDuration = newHiddenDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION, oldHiddenDuration, newHiddenDuration);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHiddenDuration(ItemDuration newHiddenDuration) {
        if (newHiddenDuration != hiddenDuration) {
            NotificationChain msgs = null;
            if (hiddenDuration != null)
                msgs = ((InternalEObject)hiddenDuration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION, null, msgs);
            if (newHiddenDuration != null)
                msgs = ((InternalEObject)newHiddenDuration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION, null, msgs);
            msgs = basicSetHiddenDuration(newHiddenDuration, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION, newHiddenDuration, newHiddenDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getVisibleDate() {
        return visibleDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVisibleDate(XMLGregorianCalendar newVisibleDate) {
        XMLGregorianCalendar oldVisibleDate = visibleDate;
        visibleDate = newVisibleDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.HIDDEN_PERIOD__VISIBLE_DATE, oldVisibleDate, visibleDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION:
                return basicSetHiddenDuration(null, msgs);
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
            case N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION:
                return getHiddenDuration();
            case N2BRMPackage.HIDDEN_PERIOD__VISIBLE_DATE:
                return getVisibleDate();
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
            case N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION:
                setHiddenDuration((ItemDuration)newValue);
                return;
            case N2BRMPackage.HIDDEN_PERIOD__VISIBLE_DATE:
                setVisibleDate((XMLGregorianCalendar)newValue);
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
            case N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION:
                setHiddenDuration((ItemDuration)null);
                return;
            case N2BRMPackage.HIDDEN_PERIOD__VISIBLE_DATE:
                setVisibleDate(VISIBLE_DATE_EDEFAULT);
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
            case N2BRMPackage.HIDDEN_PERIOD__HIDDEN_DURATION:
                return hiddenDuration != null;
            case N2BRMPackage.HIDDEN_PERIOD__VISIBLE_DATE:
                return VISIBLE_DATE_EDEFAULT == null ? visibleDate != null : !VISIBLE_DATE_EDEFAULT.equals(visibleDate);
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
        result.append(" (visibleDate: ");
        result.append(visibleDate);
        result.append(')');
        return result.toString();
    }

} //HiddenPeriodImpl
