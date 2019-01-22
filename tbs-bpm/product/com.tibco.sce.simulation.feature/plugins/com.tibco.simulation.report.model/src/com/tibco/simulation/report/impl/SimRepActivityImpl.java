/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepActivityQueue;
import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getDurationDistribution <em>Duration Distribution</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getActivityQueue <em>Activity Queue</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getActivityCost <em>Activity Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepActivityImpl#getAverageDuration <em>Average Duration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepActivityImpl extends EObjectImpl implements SimRepActivity {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDurationDistribution() <em>Duration Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDurationDistribution()
     * @generated
     * @ordered
     */
    protected SimRepDistribution durationDistribution;

    /**
     * The cached value of the '{@link #getActivityQueue() <em>Activity Queue</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityQueue()
     * @generated
     * @ordered
     */
    protected SimRepActivityQueue activityQueue;

    /**
     * The cached value of the '{@link #getActivityCost() <em>Activity Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityCost()
     * @generated
     * @ordered
     */
    protected SimRepCost activityCost;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

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
     * The default value of the '{@link #getAverageDuration() <em>Average Duration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageDuration()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_DURATION_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageDuration() <em>Average Duration</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageDuration()
     * @generated
     * @ordered
     */
    protected double averageDuration = AVERAGE_DURATION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_ACTIVITY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistribution getDurationDistribution() {
        return durationDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDurationDistribution(
            SimRepDistribution newDurationDistribution, NotificationChain msgs) {
        SimRepDistribution oldDurationDistribution = durationDistribution;
        durationDistribution = newDurationDistribution;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION,
                            oldDurationDistribution, newDurationDistribution);
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
    public void setDurationDistribution(
            SimRepDistribution newDurationDistribution) {
        if (newDurationDistribution != durationDistribution) {
            NotificationChain msgs = null;
            if (durationDistribution != null)
                msgs =
                        ((InternalEObject) durationDistribution)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION,
                                        null,
                                        msgs);
            if (newDurationDistribution != null)
                msgs =
                        ((InternalEObject) newDurationDistribution)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION,
                                        null,
                                        msgs);
            msgs = basicSetDurationDistribution(newDurationDistribution, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION,
                    newDurationDistribution, newDurationDistribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepActivityQueue getActivityQueue() {
        return activityQueue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActivityQueue(
            SimRepActivityQueue newActivityQueue, NotificationChain msgs) {
        SimRepActivityQueue oldActivityQueue = activityQueue;
        activityQueue = newActivityQueue;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE,
                            oldActivityQueue, newActivityQueue);
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
    public void setActivityQueue(SimRepActivityQueue newActivityQueue) {
        if (newActivityQueue != activityQueue) {
            NotificationChain msgs = null;
            if (activityQueue != null)
                msgs =
                        ((InternalEObject) activityQueue)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE,
                                        null,
                                        msgs);
            if (newActivityQueue != null)
                msgs =
                        ((InternalEObject) newActivityQueue)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE,
                                        null,
                                        msgs);
            msgs = basicSetActivityQueue(newActivityQueue, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE,
                    newActivityQueue, newActivityQueue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCost getActivityCost() {
        return activityCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActivityCost(SimRepCost newActivityCost,
            NotificationChain msgs) {
        SimRepCost oldActivityCost = activityCost;
        activityCost = newActivityCost;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST,
                            oldActivityCost, newActivityCost);
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
    public void setActivityCost(SimRepCost newActivityCost) {
        if (newActivityCost != activityCost) {
            NotificationChain msgs = null;
            if (activityCost != null)
                msgs =
                        ((InternalEObject) activityCost)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST,
                                        null,
                                        msgs);
            if (newActivityCost != null)
                msgs =
                        ((InternalEObject) newActivityCost)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST,
                                        null,
                                        msgs);
            msgs = basicSetActivityCost(newActivityCost, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST,
                    newActivityCost, newActivityCost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__ID, oldId, id));
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
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageDuration() {
        return averageDuration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageDuration(double newAverageDuration) {
        double oldAverageDuration = averageDuration;
        averageDuration = newAverageDuration;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_ACTIVITY__AVERAGE_DURATION,
                    oldAverageDuration, averageDuration));
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
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
            return basicSetDurationDistribution(null, msgs);
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
            return basicSetActivityQueue(null, msgs);
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
            return basicSetActivityCost(null, msgs);
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
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
            return getDurationDistribution();
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
            return getActivityQueue();
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
            return getActivityCost();
        case SimRepPackage.SIM_REP_ACTIVITY__ID:
            return getId();
        case SimRepPackage.SIM_REP_ACTIVITY__NAME:
            return getName();
        case SimRepPackage.SIM_REP_ACTIVITY__AVERAGE_DURATION:
            return getAverageDuration();
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
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
            setDurationDistribution((SimRepDistribution) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
            setActivityQueue((SimRepActivityQueue) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
            setActivityCost((SimRepCost) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ID:
            setId((String) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__NAME:
            setName((String) newValue);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__AVERAGE_DURATION:
            setAverageDuration((Double) newValue);
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
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
            setDurationDistribution((SimRepDistribution) null);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
            setActivityQueue((SimRepActivityQueue) null);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
            setActivityCost((SimRepCost) null);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__ID:
            setId(ID_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__NAME:
            setName(NAME_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__AVERAGE_DURATION:
            setAverageDuration(AVERAGE_DURATION_EDEFAULT);
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
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
            return durationDistribution != null;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
            return activityQueue != null;
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
            return activityCost != null;
        case SimRepPackage.SIM_REP_ACTIVITY__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case SimRepPackage.SIM_REP_ACTIVITY__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case SimRepPackage.SIM_REP_ACTIVITY__AVERAGE_DURATION:
            return averageDuration != AVERAGE_DURATION_EDEFAULT;
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
        result.append(" (id: "); //$NON-NLS-1$
        result.append(id);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", averageDuration: "); //$NON-NLS-1$
        result.append(averageDuration);
        result.append(')');
        return result.toString();
    }

} //SimRepActivityImpl
