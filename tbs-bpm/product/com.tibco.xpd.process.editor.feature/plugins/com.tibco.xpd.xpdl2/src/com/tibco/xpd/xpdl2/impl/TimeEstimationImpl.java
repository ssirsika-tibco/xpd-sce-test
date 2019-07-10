/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Duration;
import com.tibco.xpd.xpdl2.TimeEstimation;
import com.tibco.xpd.xpdl2.WaitingTime;
import com.tibco.xpd.xpdl2.WorkingTime;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Estimation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TimeEstimationImpl#getWaitingTime <em>Waiting Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TimeEstimationImpl#getWorkingTime <em>Working Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TimeEstimationImpl#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TimeEstimationImpl extends EObjectImpl implements TimeEstimation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getWaitingTime() <em>Waiting Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWaitingTime()
     * @generated
     * @ordered
     */
    protected WaitingTime waitingTime;

    /**
     * The cached value of the '{@link #getWorkingTime() <em>Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkingTime()
     * @generated
     * @ordered
     */
    protected WorkingTime workingTime;

    /**
     * The cached value of the '{@link #getDuration() <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDuration()
     * @generated
     * @ordered
     */
    protected Duration duration;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TimeEstimationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TIME_ESTIMATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WaitingTime getWaitingTime() {
        return waitingTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWaitingTime(WaitingTime newWaitingTime, NotificationChain msgs) {
        WaitingTime oldWaitingTime = waitingTime;
        waitingTime = newWaitingTime;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TIME_ESTIMATION__WAITING_TIME, oldWaitingTime, newWaitingTime);
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
    public void setWaitingTime(WaitingTime newWaitingTime) {
        if (newWaitingTime != waitingTime) {
            NotificationChain msgs = null;
            if (waitingTime != null)
                msgs = ((InternalEObject) waitingTime).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__WAITING_TIME,
                        null,
                        msgs);
            if (newWaitingTime != null)
                msgs = ((InternalEObject) newWaitingTime).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__WAITING_TIME,
                        null,
                        msgs);
            msgs = basicSetWaitingTime(newWaitingTime, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TIME_ESTIMATION__WAITING_TIME,
                    newWaitingTime, newWaitingTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingTime getWorkingTime() {
        return workingTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkingTime(WorkingTime newWorkingTime, NotificationChain msgs) {
        WorkingTime oldWorkingTime = workingTime;
        workingTime = newWorkingTime;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TIME_ESTIMATION__WORKING_TIME, oldWorkingTime, newWorkingTime);
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
    public void setWorkingTime(WorkingTime newWorkingTime) {
        if (newWorkingTime != workingTime) {
            NotificationChain msgs = null;
            if (workingTime != null)
                msgs = ((InternalEObject) workingTime).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__WORKING_TIME,
                        null,
                        msgs);
            if (newWorkingTime != null)
                msgs = ((InternalEObject) newWorkingTime).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__WORKING_TIME,
                        null,
                        msgs);
            msgs = basicSetWorkingTime(newWorkingTime, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TIME_ESTIMATION__WORKING_TIME,
                    newWorkingTime, newWorkingTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDuration(Duration newDuration, NotificationChain msgs) {
        Duration oldDuration = duration;
        duration = newDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TIME_ESTIMATION__DURATION, oldDuration, newDuration);
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
    public void setDuration(Duration newDuration) {
        if (newDuration != duration) {
            NotificationChain msgs = null;
            if (duration != null)
                msgs = ((InternalEObject) duration).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__DURATION,
                        null,
                        msgs);
            if (newDuration != null)
                msgs = ((InternalEObject) newDuration)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.TIME_ESTIMATION__DURATION, null, msgs);
            msgs = basicSetDuration(newDuration, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TIME_ESTIMATION__DURATION, newDuration,
                    newDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TIME_ESTIMATION__WAITING_TIME:
            return basicSetWaitingTime(null, msgs);
        case Xpdl2Package.TIME_ESTIMATION__WORKING_TIME:
            return basicSetWorkingTime(null, msgs);
        case Xpdl2Package.TIME_ESTIMATION__DURATION:
            return basicSetDuration(null, msgs);
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
        case Xpdl2Package.TIME_ESTIMATION__WAITING_TIME:
            return getWaitingTime();
        case Xpdl2Package.TIME_ESTIMATION__WORKING_TIME:
            return getWorkingTime();
        case Xpdl2Package.TIME_ESTIMATION__DURATION:
            return getDuration();
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
        case Xpdl2Package.TIME_ESTIMATION__WAITING_TIME:
            setWaitingTime((WaitingTime) newValue);
            return;
        case Xpdl2Package.TIME_ESTIMATION__WORKING_TIME:
            setWorkingTime((WorkingTime) newValue);
            return;
        case Xpdl2Package.TIME_ESTIMATION__DURATION:
            setDuration((Duration) newValue);
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
        case Xpdl2Package.TIME_ESTIMATION__WAITING_TIME:
            setWaitingTime((WaitingTime) null);
            return;
        case Xpdl2Package.TIME_ESTIMATION__WORKING_TIME:
            setWorkingTime((WorkingTime) null);
            return;
        case Xpdl2Package.TIME_ESTIMATION__DURATION:
            setDuration((Duration) null);
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
        case Xpdl2Package.TIME_ESTIMATION__WAITING_TIME:
            return waitingTime != null;
        case Xpdl2Package.TIME_ESTIMATION__WORKING_TIME:
            return workingTime != null;
        case Xpdl2Package.TIME_ESTIMATION__DURATION:
            return duration != null;
        }
        return super.eIsSet(featureID);
    }

} //TimeEstimationImpl
