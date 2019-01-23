/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeUnitCostType;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Participant Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl#getTimeUnitCost <em>Time Unit Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl#getSlaMinimumUtilisation <em>Sla Minimum Utilisation</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl#getSlaMaximumUtilisation <em>Sla Maximum Utilisation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParticipantSimulationDataTypeImpl extends EObjectImpl implements
        ParticipantSimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getInstances() <em>Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstances()
     * @generated
     * @ordered
     */
    protected static final BigInteger INSTANCES_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInstances() <em>Instances</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInstances()
     * @generated
     * @ordered
     */
    protected BigInteger instances = INSTANCES_EDEFAULT;

    /**
     * The cached value of the '{@link #getTimeUnitCost() <em>Time Unit Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeUnitCost()
     * @generated
     * @ordered
     */
    protected TimeUnitCostType timeUnitCost;

    /**
     * The default value of the '{@link #getSlaMinimumUtilisation() <em>Sla Minimum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMinimumUtilisation()
     * @generated
     * @ordered
     */
    protected static final Double SLA_MINIMUM_UTILISATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSlaMinimumUtilisation() <em>Sla Minimum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMinimumUtilisation()
     * @generated
     * @ordered
     */
    protected Double slaMinimumUtilisation = SLA_MINIMUM_UTILISATION_EDEFAULT;

    /**
     * The default value of the '{@link #getSlaMaximumUtilisation() <em>Sla Maximum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMaximumUtilisation()
     * @generated
     * @ordered
     */
    protected static final Double SLA_MAXIMUM_UTILISATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSlaMaximumUtilisation() <em>Sla Maximum Utilisation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMaximumUtilisation()
     * @generated
     * @ordered
     */
    protected Double slaMaximumUtilisation = SLA_MAXIMUM_UTILISATION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ParticipantSimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger getInstances() {
        return instances;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInstances(BigInteger newInstances) {
        BigInteger oldInstances = instances;
        instances = newInstances;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES,
                    oldInstances, instances));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeUnitCostType getTimeUnitCost() {
        return timeUnitCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTimeUnitCost(
            TimeUnitCostType newTimeUnitCost, NotificationChain msgs) {
        TimeUnitCostType oldTimeUnitCost = timeUnitCost;
        timeUnitCost = newTimeUnitCost;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST,
                            oldTimeUnitCost, newTimeUnitCost);
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
    public void setTimeUnitCost(TimeUnitCostType newTimeUnitCost) {
        if (newTimeUnitCost != timeUnitCost) {
            NotificationChain msgs = null;
            if (timeUnitCost != null)
                msgs =
                        ((InternalEObject) timeUnitCost)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST,
                                        null,
                                        msgs);
            if (newTimeUnitCost != null)
                msgs =
                        ((InternalEObject) newTimeUnitCost)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST,
                                        null,
                                        msgs);
            msgs = basicSetTimeUnitCost(newTimeUnitCost, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST,
                    newTimeUnitCost, newTimeUnitCost));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Double getSlaMinimumUtilisation() {
        return slaMinimumUtilisation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSlaMinimumUtilisation(Double newSlaMinimumUtilisation) {
        Double oldSlaMinimumUtilisation = slaMinimumUtilisation;
        slaMinimumUtilisation = newSlaMinimumUtilisation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION,
                    oldSlaMinimumUtilisation, slaMinimumUtilisation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Double getSlaMaximumUtilisation() {
        return slaMaximumUtilisation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSlaMaximumUtilisation(Double newSlaMaximumUtilisation) {
        Double oldSlaMaximumUtilisation = slaMaximumUtilisation;
        slaMaximumUtilisation = newSlaMaximumUtilisation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION,
                    oldSlaMaximumUtilisation, slaMaximumUtilisation));
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
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            return basicSetTimeUnitCost(null, msgs);
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
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES:
            return getInstances();
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            return getTimeUnitCost();
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION:
            return getSlaMinimumUtilisation();
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION:
            return getSlaMaximumUtilisation();
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
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES:
            setInstances((BigInteger) newValue);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            setTimeUnitCost((TimeUnitCostType) newValue);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION:
            setSlaMinimumUtilisation((Double) newValue);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION:
            setSlaMaximumUtilisation((Double) newValue);
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
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES:
            setInstances(INSTANCES_EDEFAULT);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            setTimeUnitCost((TimeUnitCostType) null);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION:
            setSlaMinimumUtilisation(SLA_MINIMUM_UTILISATION_EDEFAULT);
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION:
            setSlaMaximumUtilisation(SLA_MAXIMUM_UTILISATION_EDEFAULT);
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
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES:
            return INSTANCES_EDEFAULT == null ? instances != null
                    : !INSTANCES_EDEFAULT.equals(instances);
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            return timeUnitCost != null;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION:
            return SLA_MINIMUM_UTILISATION_EDEFAULT == null ? slaMinimumUtilisation != null
                    : !SLA_MINIMUM_UTILISATION_EDEFAULT
                            .equals(slaMinimumUtilisation);
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION:
            return SLA_MAXIMUM_UTILISATION_EDEFAULT == null ? slaMaximumUtilisation != null
                    : !SLA_MAXIMUM_UTILISATION_EDEFAULT
                            .equals(slaMaximumUtilisation);
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
        result.append(" (instances: "); //$NON-NLS-1$
        result.append(instances);
        result.append(", slaMinimumUtilisation: "); //$NON-NLS-1$
        result.append(slaMinimumUtilisation);
        result.append(", slaMaximumUtilisation: "); //$NON-NLS-1$
        result.append(slaMaximumUtilisation);
        result.append(')');
        return result.toString();
    }

} //ParticipantSimulationDataTypeImpl