/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.TimeDisplayUnitType;
import com.tibco.xpd.simulation.TimeUnitCostType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Unit Cost Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl#getCost <em>Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl#getTimeDisplayUnit <em>Time Display Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeUnitCostTypeImpl extends EObjectImpl implements
        TimeUnitCostType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getCost() <em>Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCost()
     * @generated
     * @ordered
     */
    protected static final double COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getCost() <em>Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCost()
     * @generated
     * @ordered
     */
    protected double cost = COST_EDEFAULT;

    /**
     * This is true if the Cost attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean costESet;

    /**
     * The default value of the '{@link #getTimeDisplayUnit() <em>Time Display Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeDisplayUnit()
     * @generated
     * @ordered
     */
    protected static final TimeDisplayUnitType TIME_DISPLAY_UNIT_EDEFAULT =
            TimeDisplayUnitType.YEAR_LITERAL;

    /**
     * The cached value of the '{@link #getTimeDisplayUnit() <em>Time Display Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeDisplayUnit()
     * @generated
     * @ordered
     */
    protected TimeDisplayUnitType timeDisplayUnit = TIME_DISPLAY_UNIT_EDEFAULT;

    /**
     * This is true if the Time Display Unit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean timeDisplayUnitESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TimeUnitCostTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.TIME_UNIT_COST_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getCost() {
        return cost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCost(double newCost) {
        double oldCost = cost;
        cost = newCost;
        boolean oldCostESet = costESet;
        costESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.TIME_UNIT_COST_TYPE__COST, oldCost, cost,
                    !oldCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCost() {
        double oldCost = cost;
        boolean oldCostESet = costESet;
        cost = COST_EDEFAULT;
        costESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimulationPackage.TIME_UNIT_COST_TYPE__COST, oldCost,
                    COST_EDEFAULT, oldCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCost() {
        return costESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeDisplayUnitType getTimeDisplayUnit() {
        return timeDisplayUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimeDisplayUnit(TimeDisplayUnitType newTimeDisplayUnit) {
        TimeDisplayUnitType oldTimeDisplayUnit = timeDisplayUnit;
        timeDisplayUnit =
                newTimeDisplayUnit == null ? TIME_DISPLAY_UNIT_EDEFAULT
                        : newTimeDisplayUnit;
        boolean oldTimeDisplayUnitESet = timeDisplayUnitESet;
        timeDisplayUnitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT,
                    oldTimeDisplayUnit, timeDisplayUnit,
                    !oldTimeDisplayUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTimeDisplayUnit() {
        TimeDisplayUnitType oldTimeDisplayUnit = timeDisplayUnit;
        boolean oldTimeDisplayUnitESet = timeDisplayUnitESet;
        timeDisplayUnit = TIME_DISPLAY_UNIT_EDEFAULT;
        timeDisplayUnitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT,
                    oldTimeDisplayUnit, TIME_DISPLAY_UNIT_EDEFAULT,
                    oldTimeDisplayUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTimeDisplayUnit() {
        return timeDisplayUnitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.TIME_UNIT_COST_TYPE__COST:
            return new Double(getCost());
        case SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT:
            return getTimeDisplayUnit();
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
        case SimulationPackage.TIME_UNIT_COST_TYPE__COST:
            setCost(((Double) newValue).doubleValue());
            return;
        case SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT:
            setTimeDisplayUnit((TimeDisplayUnitType) newValue);
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
        case SimulationPackage.TIME_UNIT_COST_TYPE__COST:
            unsetCost();
            return;
        case SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT:
            unsetTimeDisplayUnit();
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
        case SimulationPackage.TIME_UNIT_COST_TYPE__COST:
            return isSetCost();
        case SimulationPackage.TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT:
            return isSetTimeDisplayUnit();
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
        result.append(" (cost: "); //$NON-NLS-1$
        if (costESet)
            result.append(cost);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", timeDisplayUnit: "); //$NON-NLS-1$
        if (timeDisplayUnitESet)
            result.append(timeDisplayUnit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //TimeUnitCostTypeImpl