/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reschedule Timers</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl#getTimerSelectionType <em>Timer Selection Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimersImpl#getTimerEvents <em>Timer Events</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RescheduleTimersImpl extends EObjectImpl
        implements RescheduleTimers {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getTimerSelectionType() <em>Timer Selection Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimerSelectionType()
     * @generated
     * @ordered
     */
    protected static final RescheduleTimerSelectionType TIMER_SELECTION_TYPE_EDEFAULT =
            RescheduleTimerSelectionType.ALL;

    /**
     * The cached value of the '{@link #getTimerSelectionType() <em>Timer Selection Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimerSelectionType()
     * @generated
     * @ordered
     */
    protected RescheduleTimerSelectionType timerSelectionType =
            TIMER_SELECTION_TYPE_EDEFAULT;

    /**
     * This is true if the Timer Selection Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean timerSelectionTypeESet;

    /**
     * The cached value of the '{@link #getTimerEvents() <em>Timer Events</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimerEvents()
     * @generated
     * @ordered
     */
    protected EList<ActivityRef> timerEvents;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RescheduleTimersImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.RESCHEDULE_TIMERS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleTimerSelectionType getTimerSelectionType() {
        return timerSelectionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimerSelectionType(
            RescheduleTimerSelectionType newTimerSelectionType) {
        RescheduleTimerSelectionType oldTimerSelectionType = timerSelectionType;
        timerSelectionType =
                newTimerSelectionType == null ? TIMER_SELECTION_TYPE_EDEFAULT
                        : newTimerSelectionType;
        boolean oldTimerSelectionTypeESet = timerSelectionTypeESet;
        timerSelectionTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE,
                    oldTimerSelectionType, timerSelectionType,
                    !oldTimerSelectionTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTimerSelectionType() {
        RescheduleTimerSelectionType oldTimerSelectionType = timerSelectionType;
        boolean oldTimerSelectionTypeESet = timerSelectionTypeESet;
        timerSelectionType = TIMER_SELECTION_TYPE_EDEFAULT;
        timerSelectionTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE,
                    oldTimerSelectionType, TIMER_SELECTION_TYPE_EDEFAULT,
                    oldTimerSelectionTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTimerSelectionType() {
        return timerSelectionTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ActivityRef> getTimerEvents() {
        if (timerEvents == null) {
            timerEvents = new EObjectContainmentEList<ActivityRef>(
                    ActivityRef.class, this,
                    XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS);
        }
        return timerEvents;
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
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS:
            return ((InternalEList<?>) getTimerEvents()).basicRemove(otherEnd,
                    msgs);
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
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE:
            return getTimerSelectionType();
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS:
            return getTimerEvents();
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
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE:
            setTimerSelectionType((RescheduleTimerSelectionType) newValue);
            return;
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS:
            getTimerEvents().clear();
            getTimerEvents()
                    .addAll((Collection<? extends ActivityRef>) newValue);
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
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE:
            unsetTimerSelectionType();
            return;
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS:
            getTimerEvents().clear();
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
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_SELECTION_TYPE:
            return isSetTimerSelectionType();
        case XpdExtensionPackage.RESCHEDULE_TIMERS__TIMER_EVENTS:
            return timerEvents != null && !timerEvents.isEmpty();
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (timerSelectionType: "); //$NON-NLS-1$
        if (timerSelectionTypeESet)
            result.append(timerSelectionType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //RescheduleTimersImpl
