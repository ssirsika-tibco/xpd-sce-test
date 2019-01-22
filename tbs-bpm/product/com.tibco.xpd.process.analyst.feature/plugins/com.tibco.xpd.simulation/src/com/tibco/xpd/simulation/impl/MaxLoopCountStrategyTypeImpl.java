/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Max Loop Count Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.MaxLoopCountStrategyTypeImpl#getMaxLoopCount <em>Max Loop Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MaxLoopCountStrategyTypeImpl extends LoopControlTransitionTypeImpl
        implements MaxLoopCountStrategyType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getMaxLoopCount() <em>Max Loop Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxLoopCount()
     * @generated
     * @ordered
     */
    protected static final int MAX_LOOP_COUNT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMaxLoopCount() <em>Max Loop Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxLoopCount()
     * @generated
     * @ordered
     */
    protected int maxLoopCount = MAX_LOOP_COUNT_EDEFAULT;

    /**
     * This is true if the Max Loop Count attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean maxLoopCountESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MaxLoopCountStrategyTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.MAX_LOOP_COUNT_STRATEGY_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getMaxLoopCount() {
        return maxLoopCount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMaxLoopCount(int newMaxLoopCount) {
        int oldMaxLoopCount = maxLoopCount;
        maxLoopCount = newMaxLoopCount;
        boolean oldMaxLoopCountESet = maxLoopCountESet;
        maxLoopCountESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT,
                    oldMaxLoopCount, maxLoopCount, !oldMaxLoopCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMaxLoopCount() {
        int oldMaxLoopCount = maxLoopCount;
        boolean oldMaxLoopCountESet = maxLoopCountESet;
        maxLoopCount = MAX_LOOP_COUNT_EDEFAULT;
        maxLoopCountESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.UNSET,
                    SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT,
                    oldMaxLoopCount, MAX_LOOP_COUNT_EDEFAULT,
                    oldMaxLoopCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMaxLoopCount() {
        return maxLoopCountESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT:
            return new Integer(getMaxLoopCount());
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
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT:
            setMaxLoopCount(((Integer) newValue).intValue());
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
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT:
            unsetMaxLoopCount();
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
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT:
            return isSetMaxLoopCount();
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
        result.append(" (maxLoopCount: "); //$NON-NLS-1$
        if (maxLoopCountESet)
            result.append(maxLoopCount);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //MaxLoopCountStrategyTypeImpl