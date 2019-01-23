/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepParticipant;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getNoOfInstances <em>No Of Instances</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getIdleInstances <em>Idle Instances</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getAverageIdle <em>Average Idle</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getAverageIdleTime <em>Average Idle Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getAverageBusyTime <em>Average Busy Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getCostOfWorkForCase <em>Cost Of Work For Case</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepParticipantImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepParticipantImpl extends EObjectImpl implements
        SimRepParticipant {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getNoOfInstances() <em>No Of Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoOfInstances()
     * @generated
     * @ordered
     */
    protected static final int NO_OF_INSTANCES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNoOfInstances() <em>No Of Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoOfInstances()
     * @generated
     * @ordered
     */
    protected int noOfInstances = NO_OF_INSTANCES_EDEFAULT;

    /**
     * This is true if the No Of Instances attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean noOfInstancesESet;

    /**
     * The default value of the '{@link #getIdleInstances() <em>Idle Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdleInstances()
     * @generated
     * @ordered
     */
    protected static final int IDLE_INSTANCES_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getIdleInstances() <em>Idle Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdleInstances()
     * @generated
     * @ordered
     */
    protected int idleInstances = IDLE_INSTANCES_EDEFAULT;

    /**
     * This is true if the Idle Instances attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean idleInstancesESet;

    /**
     * The default value of the '{@link #getAverageIdle() <em>Average Idle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageIdle()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_IDLE_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageIdle() <em>Average Idle</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageIdle()
     * @generated
     * @ordered
     */
    protected double averageIdle = AVERAGE_IDLE_EDEFAULT;

    /**
     * This is true if the Average Idle attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageIdleESet;

    /**
     * The default value of the '{@link #getAverageIdleTime() <em>Average Idle Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageIdleTime()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_IDLE_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageIdleTime() <em>Average Idle Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageIdleTime()
     * @generated
     * @ordered
     */
    protected double averageIdleTime = AVERAGE_IDLE_TIME_EDEFAULT;

    /**
     * This is true if the Average Idle Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageIdleTimeESet;

    /**
     * The default value of the '{@link #getAverageBusyTime() <em>Average Busy Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageBusyTime()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_BUSY_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageBusyTime() <em>Average Busy Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageBusyTime()
     * @generated
     * @ordered
     */
    protected double averageBusyTime = AVERAGE_BUSY_TIME_EDEFAULT;

    /**
     * This is true if the Average Busy Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageBusyTimeESet;

    /**
     * The cached value of the '{@link #getCostOfWorkForCase() <em>Cost Of Work For Case</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCostOfWorkForCase()
     * @generated
     * @ordered
     */
    protected SimRepCost costOfWorkForCase;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepParticipantImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_PARTICIPANT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNoOfInstances() {
        return noOfInstances;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNoOfInstances(int newNoOfInstances) {
        int oldNoOfInstances = noOfInstances;
        noOfInstances = newNoOfInstances;
        boolean oldNoOfInstancesESet = noOfInstancesESet;
        noOfInstancesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES,
                    oldNoOfInstances, noOfInstances, !oldNoOfInstancesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetNoOfInstances() {
        int oldNoOfInstances = noOfInstances;
        boolean oldNoOfInstancesESet = noOfInstancesESet;
        noOfInstances = NO_OF_INSTANCES_EDEFAULT;
        noOfInstancesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES,
                    oldNoOfInstances, NO_OF_INSTANCES_EDEFAULT,
                    oldNoOfInstancesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetNoOfInstances() {
        return noOfInstancesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getIdleInstances() {
        return idleInstances;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdleInstances(int newIdleInstances) {
        int oldIdleInstances = idleInstances;
        idleInstances = newIdleInstances;
        boolean oldIdleInstancesESet = idleInstancesESet;
        idleInstancesESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES,
                    oldIdleInstances, idleInstances, !oldIdleInstancesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIdleInstances() {
        int oldIdleInstances = idleInstances;
        boolean oldIdleInstancesESet = idleInstancesESet;
        idleInstances = IDLE_INSTANCES_EDEFAULT;
        idleInstancesESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES,
                    oldIdleInstances, IDLE_INSTANCES_EDEFAULT,
                    oldIdleInstancesESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIdleInstances() {
        return idleInstancesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageIdle() {
        return averageIdle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageIdle(double newAverageIdle) {
        double oldAverageIdle = averageIdle;
        averageIdle = newAverageIdle;
        boolean oldAverageIdleESet = averageIdleESet;
        averageIdleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE,
                    oldAverageIdle, averageIdle, !oldAverageIdleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageIdle() {
        double oldAverageIdle = averageIdle;
        boolean oldAverageIdleESet = averageIdleESet;
        averageIdle = AVERAGE_IDLE_EDEFAULT;
        averageIdleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE,
                    oldAverageIdle, AVERAGE_IDLE_EDEFAULT, oldAverageIdleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageIdle() {
        return averageIdleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageIdleTime() {
        return averageIdleTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageIdleTime(double newAverageIdleTime) {
        double oldAverageIdleTime = averageIdleTime;
        averageIdleTime = newAverageIdleTime;
        boolean oldAverageIdleTimeESet = averageIdleTimeESet;
        averageIdleTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME,
                    oldAverageIdleTime, averageIdleTime,
                    !oldAverageIdleTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageIdleTime() {
        double oldAverageIdleTime = averageIdleTime;
        boolean oldAverageIdleTimeESet = averageIdleTimeESet;
        averageIdleTime = AVERAGE_IDLE_TIME_EDEFAULT;
        averageIdleTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME,
                    oldAverageIdleTime, AVERAGE_IDLE_TIME_EDEFAULT,
                    oldAverageIdleTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageIdleTime() {
        return averageIdleTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageBusyTime() {
        return averageBusyTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageBusyTime(double newAverageBusyTime) {
        double oldAverageBusyTime = averageBusyTime;
        averageBusyTime = newAverageBusyTime;
        boolean oldAverageBusyTimeESet = averageBusyTimeESet;
        averageBusyTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME,
                    oldAverageBusyTime, averageBusyTime,
                    !oldAverageBusyTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageBusyTime() {
        double oldAverageBusyTime = averageBusyTime;
        boolean oldAverageBusyTimeESet = averageBusyTimeESet;
        averageBusyTime = AVERAGE_BUSY_TIME_EDEFAULT;
        averageBusyTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME,
                    oldAverageBusyTime, AVERAGE_BUSY_TIME_EDEFAULT,
                    oldAverageBusyTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageBusyTime() {
        return averageBusyTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCost getCostOfWorkForCase() {
        return costOfWorkForCase;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCostOfWorkForCase(
            SimRepCost newCostOfWorkForCase, NotificationChain msgs) {
        SimRepCost oldCostOfWorkForCase = costOfWorkForCase;
        costOfWorkForCase = newCostOfWorkForCase;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE,
                            oldCostOfWorkForCase, newCostOfWorkForCase);
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
    public void setCostOfWorkForCase(SimRepCost newCostOfWorkForCase) {
        if (newCostOfWorkForCase != costOfWorkForCase) {
            NotificationChain msgs = null;
            if (costOfWorkForCase != null)
                msgs =
                        ((InternalEObject) costOfWorkForCase)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE,
                                        null,
                                        msgs);
            if (newCostOfWorkForCase != null)
                msgs =
                        ((InternalEObject) newCostOfWorkForCase)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE,
                                        null,
                                        msgs);
            msgs = basicSetCostOfWorkForCase(newCostOfWorkForCase, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE,
                    newCostOfWorkForCase, newCostOfWorkForCase));
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
                    SimRepPackage.SIM_REP_PARTICIPANT__ID, oldId, id));
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
                    SimRepPackage.SIM_REP_PARTICIPANT__NAME, oldName, name));
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
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
            return basicSetCostOfWorkForCase(null, msgs);
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
        case SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES:
            return getNoOfInstances();
        case SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES:
            return getIdleInstances();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE:
            return getAverageIdle();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME:
            return getAverageIdleTime();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME:
            return getAverageBusyTime();
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
            return getCostOfWorkForCase();
        case SimRepPackage.SIM_REP_PARTICIPANT__ID:
            return getId();
        case SimRepPackage.SIM_REP_PARTICIPANT__NAME:
            return getName();
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
        case SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES:
            setNoOfInstances((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES:
            setIdleInstances((Integer) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE:
            setAverageIdle((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME:
            setAverageIdleTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME:
            setAverageBusyTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
            setCostOfWorkForCase((SimRepCost) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__ID:
            setId((String) newValue);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__NAME:
            setName((String) newValue);
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
        case SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES:
            unsetNoOfInstances();
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES:
            unsetIdleInstances();
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE:
            unsetAverageIdle();
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME:
            unsetAverageIdleTime();
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME:
            unsetAverageBusyTime();
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
            setCostOfWorkForCase((SimRepCost) null);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__ID:
            setId(ID_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__NAME:
            setName(NAME_EDEFAULT);
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
        case SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES:
            return isSetNoOfInstances();
        case SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES:
            return isSetIdleInstances();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE:
            return isSetAverageIdle();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME:
            return isSetAverageIdleTime();
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME:
            return isSetAverageBusyTime();
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
            return costOfWorkForCase != null;
        case SimRepPackage.SIM_REP_PARTICIPANT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case SimRepPackage.SIM_REP_PARTICIPANT__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
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
        result.append(" (noOfInstances: "); //$NON-NLS-1$
        if (noOfInstancesESet)
            result.append(noOfInstances);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", idleInstances: "); //$NON-NLS-1$
        if (idleInstancesESet)
            result.append(idleInstances);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageIdle: "); //$NON-NLS-1$
        if (averageIdleESet)
            result.append(averageIdle);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageIdleTime: "); //$NON-NLS-1$
        if (averageIdleTimeESet)
            result.append(averageIdleTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", averageBusyTime: "); //$NON-NLS-1$
        if (averageBusyTimeESet)
            result.append(averageBusyTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", id: "); //$NON-NLS-1$
        result.append(id);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //SimRepParticipantImpl
