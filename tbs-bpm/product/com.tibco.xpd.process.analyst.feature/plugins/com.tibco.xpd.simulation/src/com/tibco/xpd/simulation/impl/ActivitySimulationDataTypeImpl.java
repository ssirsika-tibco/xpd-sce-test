/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.TimeDisplayUnitType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl#getLoopControl <em>Loop Control</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl#getSlaMaximumDelay <em>Sla Maximum Delay</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivitySimulationDataTypeImpl extends EObjectImpl implements
        ActivitySimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDuration() <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDuration()
     * @generated
     * @ordered
     */
    protected SimulationRealDistributionType duration;

    /**
     * The default value of the '{@link #getDisplayTimeUnit() <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayTimeUnit()
     * @generated
     * @ordered
     */
    protected static final TimeDisplayUnitType DISPLAY_TIME_UNIT_EDEFAULT =
            TimeDisplayUnitType.YEAR_LITERAL;

    /**
     * The cached value of the '{@link #getDisplayTimeUnit() <em>Display Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDisplayTimeUnit()
     * @generated
     * @ordered
     */
    protected TimeDisplayUnitType displayTimeUnit = DISPLAY_TIME_UNIT_EDEFAULT;

    /**
     * This is true if the Display Time Unit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean displayTimeUnitESet;

    /**
     * The cached value of the '{@link #getLoopControl() <em>Loop Control</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLoopControl()
     * @generated
     * @ordered
     */
    protected LoopControlType loopControl;

    /**
     * The default value of the '{@link #getSlaMaximumDelay() <em>Sla Maximum Delay</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMaximumDelay()
     * @generated
     * @ordered
     */
    protected static final Double SLA_MAXIMUM_DELAY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSlaMaximumDelay() <em>Sla Maximum Delay</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSlaMaximumDelay()
     * @generated
     * @ordered
     */
    protected Double slaMaximumDelay = SLA_MAXIMUM_DELAY_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ActivitySimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.ACTIVITY_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationRealDistributionType getDuration() {
        return duration;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDuration(
            SimulationRealDistributionType newDuration, NotificationChain msgs) {
        SimulationRealDistributionType oldDuration = duration;
        duration = newDuration;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION,
                            oldDuration, newDuration);
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
    public void setDuration(SimulationRealDistributionType newDuration) {
        if (newDuration != duration) {
            NotificationChain msgs = null;
            if (duration != null)
                msgs =
                        ((InternalEObject) duration)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION,
                                        null,
                                        msgs);
            if (newDuration != null)
                msgs =
                        ((InternalEObject) newDuration)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION,
                                        null,
                                        msgs);
            msgs = basicSetDuration(newDuration, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION,
                    newDuration, newDuration));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeDisplayUnitType getDisplayTimeUnit() {
        return displayTimeUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDisplayTimeUnit(TimeDisplayUnitType newDisplayTimeUnit) {
        TimeDisplayUnitType oldDisplayTimeUnit = displayTimeUnit;
        displayTimeUnit =
                newDisplayTimeUnit == null ? DISPLAY_TIME_UNIT_EDEFAULT
                        : newDisplayTimeUnit;
        boolean oldDisplayTimeUnitESet = displayTimeUnitESet;
        displayTimeUnitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT,
                    oldDisplayTimeUnit, displayTimeUnit,
                    !oldDisplayTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDisplayTimeUnit() {
        TimeDisplayUnitType oldDisplayTimeUnit = displayTimeUnit;
        boolean oldDisplayTimeUnitESet = displayTimeUnitESet;
        displayTimeUnit = DISPLAY_TIME_UNIT_EDEFAULT;
        displayTimeUnitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT,
                    oldDisplayTimeUnit, DISPLAY_TIME_UNIT_EDEFAULT,
                    oldDisplayTimeUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDisplayTimeUnit() {
        return displayTimeUnitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopControlType getLoopControl() {
        return loopControl;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLoopControl(
            LoopControlType newLoopControl, NotificationChain msgs) {
        LoopControlType oldLoopControl = loopControl;
        loopControl = newLoopControl;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL,
                            oldLoopControl, newLoopControl);
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
    public void setLoopControl(LoopControlType newLoopControl) {
        if (newLoopControl != loopControl) {
            NotificationChain msgs = null;
            if (loopControl != null)
                msgs =
                        ((InternalEObject) loopControl)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL,
                                        null,
                                        msgs);
            if (newLoopControl != null)
                msgs =
                        ((InternalEObject) newLoopControl)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL,
                                        null,
                                        msgs);
            msgs = basicSetLoopControl(newLoopControl, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL,
                    newLoopControl, newLoopControl));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Double getSlaMaximumDelay() {
        return slaMaximumDelay;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSlaMaximumDelay(Double newSlaMaximumDelay) {
        Double oldSlaMaximumDelay = slaMaximumDelay;
        slaMaximumDelay = newSlaMaximumDelay;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY,
                    oldSlaMaximumDelay, slaMaximumDelay));
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
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION:
            return basicSetDuration(null, msgs);
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL:
            return basicSetLoopControl(null, msgs);
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
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION:
            return getDuration();
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            return getDisplayTimeUnit();
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL:
            return getLoopControl();
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY:
            return getSlaMaximumDelay();
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
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION:
            setDuration((SimulationRealDistributionType) newValue);
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            setDisplayTimeUnit((TimeDisplayUnitType) newValue);
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL:
            setLoopControl((LoopControlType) newValue);
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY:
            setSlaMaximumDelay((Double) newValue);
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
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION:
            setDuration((SimulationRealDistributionType) null);
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            unsetDisplayTimeUnit();
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL:
            setLoopControl((LoopControlType) null);
            return;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY:
            setSlaMaximumDelay(SLA_MAXIMUM_DELAY_EDEFAULT);
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
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DURATION:
            return duration != null;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT:
            return isSetDisplayTimeUnit();
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL:
            return loopControl != null;
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY:
            return SLA_MAXIMUM_DELAY_EDEFAULT == null ? slaMaximumDelay != null
                    : !SLA_MAXIMUM_DELAY_EDEFAULT.equals(slaMaximumDelay);
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
        result.append(" (displayTimeUnit: "); //$NON-NLS-1$
        if (displayTimeUnitESet)
            result.append(displayTimeUnit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", slaMaximumDelay: "); //$NON-NLS-1$
        result.append(slaMaximumDelay);
        result.append(')');
        return result.toString();
    }

} //ActivitySimulationDataTypeImpl