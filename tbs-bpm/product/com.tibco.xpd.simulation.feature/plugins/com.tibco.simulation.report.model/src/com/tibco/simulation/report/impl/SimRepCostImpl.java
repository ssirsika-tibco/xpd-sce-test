/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cost</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCostImpl#getAverageCost <em>Average Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCostImpl#getMinCost <em>Min Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCostImpl#getMaxCost <em>Max Cost</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepCostImpl#getCumulativeAverageCost <em>Cumulative Average Cost</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepCostImpl extends EObjectImpl implements SimRepCost {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getAverageCost() <em>Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageCost()
     * @generated
     * @ordered
     */
    protected static final double AVERAGE_COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAverageCost() <em>Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAverageCost()
     * @generated
     * @ordered
     */
    protected double averageCost = AVERAGE_COST_EDEFAULT;

    /**
     * This is true if the Average Cost attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean averageCostESet;

    /**
     * The default value of the '{@link #getMinCost() <em>Min Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinCost()
     * @generated
     * @ordered
     */
    protected static final double MIN_COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getMinCost() <em>Min Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMinCost()
     * @generated
     * @ordered
     */
    protected double minCost = MIN_COST_EDEFAULT;

    /**
     * This is true if the Min Cost attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean minCostESet;

    /**
     * The default value of the '{@link #getMaxCost() <em>Max Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxCost()
     * @generated
     * @ordered
     */
    protected static final double MAX_COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getMaxCost() <em>Max Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxCost()
     * @generated
     * @ordered
     */
    protected double maxCost = MAX_COST_EDEFAULT;

    /**
     * This is true if the Max Cost attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean maxCostESet;

    /**
     * The default value of the '{@link #getCumulativeAverageCost() <em>Cumulative Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCumulativeAverageCost()
     * @generated
     * @ordered
     */
    protected static final double CUMULATIVE_AVERAGE_COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getCumulativeAverageCost() <em>Cumulative Average Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCumulativeAverageCost()
     * @generated
     * @ordered
     */
    protected double cumulativeAverageCost = CUMULATIVE_AVERAGE_COST_EDEFAULT;

    /**
     * This is true if the Cumulative Average Cost attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean cumulativeAverageCostESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepCostImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_COST;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getAverageCost() {
        return averageCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAverageCost(double newAverageCost) {
        double oldAverageCost = averageCost;
        averageCost = newAverageCost;
        boolean oldAverageCostESet = averageCostESet;
        averageCostESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_COST__AVERAGE_COST, oldAverageCost,
                    averageCost, !oldAverageCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAverageCost() {
        double oldAverageCost = averageCost;
        boolean oldAverageCostESet = averageCostESet;
        averageCost = AVERAGE_COST_EDEFAULT;
        averageCostESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_COST__AVERAGE_COST, oldAverageCost,
                    AVERAGE_COST_EDEFAULT, oldAverageCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAverageCost() {
        return averageCostESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getMinCost() {
        return minCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMinCost(double newMinCost) {
        double oldMinCost = minCost;
        minCost = newMinCost;
        boolean oldMinCostESet = minCostESet;
        minCostESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_COST__MIN_COST, oldMinCost, minCost,
                    !oldMinCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMinCost() {
        double oldMinCost = minCost;
        boolean oldMinCostESet = minCostESet;
        minCost = MIN_COST_EDEFAULT;
        minCostESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_COST__MIN_COST, oldMinCost,
                    MIN_COST_EDEFAULT, oldMinCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMinCost() {
        return minCostESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getMaxCost() {
        return maxCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxCost(double newMaxCost) {
        double oldMaxCost = maxCost;
        maxCost = newMaxCost;
        boolean oldMaxCostESet = maxCostESet;
        maxCostESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_COST__MAX_COST, oldMaxCost, maxCost,
                    !oldMaxCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMaxCost() {
        double oldMaxCost = maxCost;
        boolean oldMaxCostESet = maxCostESet;
        maxCost = MAX_COST_EDEFAULT;
        maxCostESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_COST__MAX_COST, oldMaxCost,
                    MAX_COST_EDEFAULT, oldMaxCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaxCost() {
        return maxCostESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getCumulativeAverageCost() {
        return cumulativeAverageCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCumulativeAverageCost(double newCumulativeAverageCost) {
        double oldCumulativeAverageCost = cumulativeAverageCost;
        cumulativeAverageCost = newCumulativeAverageCost;
        boolean oldCumulativeAverageCostESet = cumulativeAverageCostESet;
        cumulativeAverageCostESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST,
                    oldCumulativeAverageCost, cumulativeAverageCost,
                    !oldCumulativeAverageCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCumulativeAverageCost() {
        double oldCumulativeAverageCost = cumulativeAverageCost;
        boolean oldCumulativeAverageCostESet = cumulativeAverageCostESet;
        cumulativeAverageCost = CUMULATIVE_AVERAGE_COST_EDEFAULT;
        cumulativeAverageCostESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST,
                    oldCumulativeAverageCost, CUMULATIVE_AVERAGE_COST_EDEFAULT,
                    oldCumulativeAverageCostESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCumulativeAverageCost() {
        return cumulativeAverageCostESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimRepPackage.SIM_REP_COST__AVERAGE_COST:
            return getAverageCost();
        case SimRepPackage.SIM_REP_COST__MIN_COST:
            return getMinCost();
        case SimRepPackage.SIM_REP_COST__MAX_COST:
            return getMaxCost();
        case SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST:
            return getCumulativeAverageCost();
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
        case SimRepPackage.SIM_REP_COST__AVERAGE_COST:
            setAverageCost((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_COST__MIN_COST:
            setMinCost((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_COST__MAX_COST:
            setMaxCost((Double) newValue);
            return;
        case SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST:
            setCumulativeAverageCost((Double) newValue);
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
        case SimRepPackage.SIM_REP_COST__AVERAGE_COST:
            unsetAverageCost();
            return;
        case SimRepPackage.SIM_REP_COST__MIN_COST:
            unsetMinCost();
            return;
        case SimRepPackage.SIM_REP_COST__MAX_COST:
            unsetMaxCost();
            return;
        case SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST:
            unsetCumulativeAverageCost();
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
        case SimRepPackage.SIM_REP_COST__AVERAGE_COST:
            return isSetAverageCost();
        case SimRepPackage.SIM_REP_COST__MIN_COST:
            return isSetMinCost();
        case SimRepPackage.SIM_REP_COST__MAX_COST:
            return isSetMaxCost();
        case SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST:
            return isSetCumulativeAverageCost();
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
        result.append(" (averageCost: "); //$NON-NLS-1$
        if (averageCostESet)
            result.append(averageCost);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", minCost: "); //$NON-NLS-1$
        if (minCostESet)
            result.append(minCost);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", maxCost: "); //$NON-NLS-1$
        if (maxCostESet)
            result.append(maxCost);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", cumulativeAverageCost: "); //$NON-NLS-1$
        if (cumulativeAverageCostESet)
            result.append(cumulativeAverageCost);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //SimRepCostImpl
