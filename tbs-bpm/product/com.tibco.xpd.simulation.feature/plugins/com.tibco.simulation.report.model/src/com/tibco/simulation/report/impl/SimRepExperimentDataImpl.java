/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepReferenceTimeUnit;

import javax.xml.datatype.XMLGregorianCalendar;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Experiment Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getExperimentState <em>Experiment State</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getSimulationTime <em>Simulation Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getReferenceTimeUnit <em>Reference Time Unit</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getReferenceStartTime <em>Reference Start Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getRealTime <em>Real Time</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl#getReferenceCostUnit <em>Reference Cost Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepExperimentDataImpl extends EObjectImpl implements
        SimRepExperimentData {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getExperimentState() <em>Experiment State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExperimentState()
     * @generated
     * @ordered
     */
    protected static final SimRepExperimentState EXPERIMENT_STATE_EDEFAULT =
            SimRepExperimentState.NOT_STARTED_LITERAL;

    /**
     * The cached value of the '{@link #getExperimentState() <em>Experiment State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExperimentState()
     * @generated
     * @ordered
     */
    protected SimRepExperimentState experimentState = EXPERIMENT_STATE_EDEFAULT;

    /**
     * This is true if the Experiment State attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean experimentStateESet;

    /**
     * The default value of the '{@link #getSimulationTime() <em>Simulation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSimulationTime()
     * @generated
     * @ordered
     */
    protected static final double SIMULATION_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getSimulationTime() <em>Simulation Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSimulationTime()
     * @generated
     * @ordered
     */
    protected double simulationTime = SIMULATION_TIME_EDEFAULT;

    /**
     * This is true if the Simulation Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean simulationTimeESet;

    /**
     * The default value of the '{@link #getReferenceTimeUnit() <em>Reference Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceTimeUnit()
     * @generated
     * @ordered
     */
    protected static final SimRepReferenceTimeUnit REFERENCE_TIME_UNIT_EDEFAULT =
            SimRepReferenceTimeUnit.SECOND_LITERAL;

    /**
     * The cached value of the '{@link #getReferenceTimeUnit() <em>Reference Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceTimeUnit()
     * @generated
     * @ordered
     */
    protected SimRepReferenceTimeUnit referenceTimeUnit =
            REFERENCE_TIME_UNIT_EDEFAULT;

    /**
     * This is true if the Reference Time Unit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean referenceTimeUnitESet;

    /**
     * The default value of the '{@link #getReferenceStartTime() <em>Reference Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceStartTime()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_START_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceStartTime() <em>Reference Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceStartTime()
     * @generated
     * @ordered
     */
    protected String referenceStartTime = REFERENCE_START_TIME_EDEFAULT;

    /**
     * The default value of the '{@link #getRealTime() <em>Real Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRealTime()
     * @generated
     * @ordered
     */
    protected static final String REAL_TIME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRealTime() <em>Real Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRealTime()
     * @generated
     * @ordered
     */
    protected String realTime = REAL_TIME_EDEFAULT;

    /**
     * The default value of the '{@link #getReferenceCostUnit() <em>Reference Cost Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceCostUnit()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_COST_UNIT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceCostUnit() <em>Reference Cost Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReferenceCostUnit()
     * @generated
     * @ordered
     */
    protected String referenceCostUnit = REFERENCE_COST_UNIT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepExperimentDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_EXPERIMENT_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepExperimentState getExperimentState() {
        return experimentState;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExperimentState(SimRepExperimentState newExperimentState) {
        SimRepExperimentState oldExperimentState = experimentState;
        experimentState =
                newExperimentState == null ? EXPERIMENT_STATE_EDEFAULT
                        : newExperimentState;
        boolean oldExperimentStateESet = experimentStateESet;
        experimentStateESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE,
                    oldExperimentState, experimentState,
                    !oldExperimentStateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExperimentState() {
        SimRepExperimentState oldExperimentState = experimentState;
        boolean oldExperimentStateESet = experimentStateESet;
        experimentState = EXPERIMENT_STATE_EDEFAULT;
        experimentStateESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE,
                    oldExperimentState, EXPERIMENT_STATE_EDEFAULT,
                    oldExperimentStateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExperimentState() {
        return experimentStateESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getSimulationTime() {
        return simulationTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSimulationTime(double newSimulationTime) {
        double oldSimulationTime = simulationTime;
        simulationTime = newSimulationTime;
        boolean oldSimulationTimeESet = simulationTimeESet;
        simulationTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME,
                    oldSimulationTime, simulationTime, !oldSimulationTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSimulationTime() {
        double oldSimulationTime = simulationTime;
        boolean oldSimulationTimeESet = simulationTimeESet;
        simulationTime = SIMULATION_TIME_EDEFAULT;
        simulationTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME,
                    oldSimulationTime, SIMULATION_TIME_EDEFAULT,
                    oldSimulationTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSimulationTime() {
        return simulationTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepReferenceTimeUnit getReferenceTimeUnit() {
        return referenceTimeUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReferenceTimeUnit(
            SimRepReferenceTimeUnit newReferenceTimeUnit) {
        SimRepReferenceTimeUnit oldReferenceTimeUnit = referenceTimeUnit;
        referenceTimeUnit =
                newReferenceTimeUnit == null ? REFERENCE_TIME_UNIT_EDEFAULT
                        : newReferenceTimeUnit;
        boolean oldReferenceTimeUnitESet = referenceTimeUnitESet;
        referenceTimeUnitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT,
                    oldReferenceTimeUnit, referenceTimeUnit,
                    !oldReferenceTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReferenceTimeUnit() {
        SimRepReferenceTimeUnit oldReferenceTimeUnit = referenceTimeUnit;
        boolean oldReferenceTimeUnitESet = referenceTimeUnitESet;
        referenceTimeUnit = REFERENCE_TIME_UNIT_EDEFAULT;
        referenceTimeUnitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT,
                    oldReferenceTimeUnit, REFERENCE_TIME_UNIT_EDEFAULT,
                    oldReferenceTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReferenceTimeUnit() {
        return referenceTimeUnitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getReferenceStartTime() {
        return referenceStartTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReferenceStartTime(String newReferenceStartTime) {
        String oldReferenceStartTime = referenceStartTime;
        referenceStartTime = newReferenceStartTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME,
                    oldReferenceStartTime, referenceStartTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRealTime() {
        return realTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRealTime(String newRealTime) {
        String oldRealTime = realTime;
        realTime = newRealTime;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__REAL_TIME,
                    oldRealTime, realTime));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getReferenceCostUnit() {
        return referenceCostUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReferenceCostUnit(String newReferenceCostUnit) {
        String oldReferenceCostUnit = referenceCostUnit;
        referenceCostUnit = newReferenceCostUnit;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT,
                    oldReferenceCostUnit, referenceCostUnit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE:
            return getExperimentState();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME:
            return getSimulationTime();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT:
            return getReferenceTimeUnit();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME:
            return getReferenceStartTime();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REAL_TIME:
            return getRealTime();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT:
            return getReferenceCostUnit();
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
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE:
            setExperimentState((SimRepExperimentState) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME:
            setSimulationTime((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT:
            setReferenceTimeUnit((SimRepReferenceTimeUnit) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME:
            setReferenceStartTime((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REAL_TIME:
            setRealTime((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT:
            setReferenceCostUnit((String) newValue);
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
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE:
            unsetExperimentState();
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME:
            unsetSimulationTime();
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT:
            unsetReferenceTimeUnit();
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME:
            setReferenceStartTime(REFERENCE_START_TIME_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REAL_TIME:
            setRealTime(REAL_TIME_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT:
            setReferenceCostUnit(REFERENCE_COST_UNIT_EDEFAULT);
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
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE:
            return isSetExperimentState();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME:
            return isSetSimulationTime();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT:
            return isSetReferenceTimeUnit();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME:
            return REFERENCE_START_TIME_EDEFAULT == null ? referenceStartTime != null
                    : !REFERENCE_START_TIME_EDEFAULT.equals(referenceStartTime);
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REAL_TIME:
            return REAL_TIME_EDEFAULT == null ? realTime != null
                    : !REAL_TIME_EDEFAULT.equals(realTime);
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT:
            return REFERENCE_COST_UNIT_EDEFAULT == null ? referenceCostUnit != null
                    : !REFERENCE_COST_UNIT_EDEFAULT.equals(referenceCostUnit);
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
        result.append(" (experimentState: "); //$NON-NLS-1$
        if (experimentStateESet)
            result.append(experimentState);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", simulationTime: "); //$NON-NLS-1$
        if (simulationTimeESet)
            result.append(simulationTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", referenceTimeUnit: "); //$NON-NLS-1$
        if (referenceTimeUnitESet)
            result.append(referenceTimeUnit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", referenceStartTime: "); //$NON-NLS-1$
        result.append(referenceStartTime);
        result.append(", realTime: "); //$NON-NLS-1$
        result.append(realTime);
        result.append(", referenceCostUnit: "); //$NON-NLS-1$
        result.append(referenceCostUnit);
        result.append(')');
        return result.toString();
    }

} //SimRepExperimentDataImpl
