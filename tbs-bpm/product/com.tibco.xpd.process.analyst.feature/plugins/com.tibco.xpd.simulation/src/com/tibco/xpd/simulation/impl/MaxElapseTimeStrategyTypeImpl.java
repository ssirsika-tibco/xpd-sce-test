/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Max Elapse Time Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl#getDisplayTimeUnit <em>Display Time Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl#getMaxElapseTime <em>Max Elapse Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MaxElapseTimeStrategyTypeImpl extends
        LoopControlTransitionTypeImpl implements MaxElapseTimeStrategyType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

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
     * The default value of the '{@link #getMaxElapseTime() <em>Max Elapse Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxElapseTime()
     * @generated
     * @ordered
     */
    protected static final double MAX_ELAPSE_TIME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getMaxElapseTime() <em>Max Elapse Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxElapseTime()
     * @generated
     * @ordered
     */
    protected double maxElapseTime = MAX_ELAPSE_TIME_EDEFAULT;

    /**
     * This is true if the Max Elapse Time attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean maxElapseTimeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MaxElapseTimeStrategyTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.MAX_ELAPSE_TIME_STRATEGY_TYPE;
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
                    SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT,
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
                    SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT,
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
    public double getMaxElapseTime() {
        return maxElapseTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxElapseTime(double newMaxElapseTime) {
        double oldMaxElapseTime = maxElapseTime;
        maxElapseTime = newMaxElapseTime;
        boolean oldMaxElapseTimeESet = maxElapseTimeESet;
        maxElapseTimeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME,
                    oldMaxElapseTime, maxElapseTime, !oldMaxElapseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMaxElapseTime() {
        double oldMaxElapseTime = maxElapseTime;
        boolean oldMaxElapseTimeESet = maxElapseTimeESet;
        maxElapseTime = MAX_ELAPSE_TIME_EDEFAULT;
        maxElapseTimeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME,
                    oldMaxElapseTime, MAX_ELAPSE_TIME_EDEFAULT,
                    oldMaxElapseTimeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaxElapseTime() {
        return maxElapseTimeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT:
            return getDisplayTimeUnit();
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME:
            return new Double(getMaxElapseTime());
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
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT:
            setDisplayTimeUnit((TimeDisplayUnitType) newValue);
            return;
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME:
            setMaxElapseTime(((Double) newValue).doubleValue());
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
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT:
            unsetDisplayTimeUnit();
            return;
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME:
            unsetMaxElapseTime();
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
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT:
            return isSetDisplayTimeUnit();
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME:
            return isSetMaxElapseTime();
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
        result.append(", maxElapseTime: "); //$NON-NLS-1$
        if (maxElapseTimeESet)
            result.append(maxElapseTime);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //MaxElapseTimeStrategyTypeImpl