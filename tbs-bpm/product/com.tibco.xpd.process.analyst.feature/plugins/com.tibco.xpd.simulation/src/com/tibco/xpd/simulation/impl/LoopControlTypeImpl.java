/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.NormalDistributionStrategyType;
import com.tibco.xpd.simulation.SimulationPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Control Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.LoopControlTypeImpl#getMaxLoopCountStrategy <em>Max Loop Count Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.LoopControlTypeImpl#getNormalDistributionStrategy <em>Normal Distribution Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.LoopControlTypeImpl#getMaxElapseTimeStrategy <em>Max Elapse Time Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoopControlTypeImpl extends EObjectImpl implements LoopControlType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMaxLoopCountStrategy() <em>Max Loop Count Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxLoopCountStrategy()
     * @generated
     * @ordered
     */
    protected MaxLoopCountStrategyType maxLoopCountStrategy;

    /**
     * The cached value of the '{@link #getNormalDistributionStrategy() <em>Normal Distribution Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNormalDistributionStrategy()
     * @generated
     * @ordered
     */
    protected NormalDistributionStrategyType normalDistributionStrategy;

    /**
     * The cached value of the '{@link #getMaxElapseTimeStrategy() <em>Max Elapse Time Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMaxElapseTimeStrategy()
     * @generated
     * @ordered
     */
    protected MaxElapseTimeStrategyType maxElapseTimeStrategy;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LoopControlTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.LOOP_CONTROL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MaxLoopCountStrategyType getMaxLoopCountStrategy() {
        return maxLoopCountStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMaxLoopCountStrategy(
            MaxLoopCountStrategyType newMaxLoopCountStrategy,
            NotificationChain msgs) {
        MaxLoopCountStrategyType oldMaxLoopCountStrategy = maxLoopCountStrategy;
        maxLoopCountStrategy = newMaxLoopCountStrategy;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY,
                            oldMaxLoopCountStrategy, newMaxLoopCountStrategy);
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
    public void setMaxLoopCountStrategy(
            MaxLoopCountStrategyType newMaxLoopCountStrategy) {
        if (newMaxLoopCountStrategy != maxLoopCountStrategy) {
            NotificationChain msgs = null;
            if (maxLoopCountStrategy != null)
                msgs =
                        ((InternalEObject) maxLoopCountStrategy)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY,
                                        null,
                                        msgs);
            if (newMaxLoopCountStrategy != null)
                msgs =
                        ((InternalEObject) newMaxLoopCountStrategy)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY,
                                        null,
                                        msgs);
            msgs = basicSetMaxLoopCountStrategy(newMaxLoopCountStrategy, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY,
                    newMaxLoopCountStrategy, newMaxLoopCountStrategy));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NormalDistributionStrategyType getNormalDistributionStrategy() {
        return normalDistributionStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetNormalDistributionStrategy(
            NormalDistributionStrategyType newNormalDistributionStrategy,
            NotificationChain msgs) {
        NormalDistributionStrategyType oldNormalDistributionStrategy =
                normalDistributionStrategy;
        normalDistributionStrategy = newNormalDistributionStrategy;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY,
                            oldNormalDistributionStrategy,
                            newNormalDistributionStrategy);
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
    public void setNormalDistributionStrategy(
            NormalDistributionStrategyType newNormalDistributionStrategy) {
        if (newNormalDistributionStrategy != normalDistributionStrategy) {
            NotificationChain msgs = null;
            if (normalDistributionStrategy != null)
                msgs =
                        ((InternalEObject) normalDistributionStrategy)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY,
                                        null,
                                        msgs);
            if (newNormalDistributionStrategy != null)
                msgs =
                        ((InternalEObject) newNormalDistributionStrategy)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY,
                                        null,
                                        msgs);
            msgs =
                    basicSetNormalDistributionStrategy(newNormalDistributionStrategy,
                            msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY,
                    newNormalDistributionStrategy,
                    newNormalDistributionStrategy));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MaxElapseTimeStrategyType getMaxElapseTimeStrategy() {
        return maxElapseTimeStrategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMaxElapseTimeStrategy(
            MaxElapseTimeStrategyType newMaxElapseTimeStrategy,
            NotificationChain msgs) {
        MaxElapseTimeStrategyType oldMaxElapseTimeStrategy =
                maxElapseTimeStrategy;
        maxElapseTimeStrategy = newMaxElapseTimeStrategy;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY,
                            oldMaxElapseTimeStrategy, newMaxElapseTimeStrategy);
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
    public void setMaxElapseTimeStrategy(
            MaxElapseTimeStrategyType newMaxElapseTimeStrategy) {
        if (newMaxElapseTimeStrategy != maxElapseTimeStrategy) {
            NotificationChain msgs = null;
            if (maxElapseTimeStrategy != null)
                msgs =
                        ((InternalEObject) maxElapseTimeStrategy)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY,
                                        null,
                                        msgs);
            if (newMaxElapseTimeStrategy != null)
                msgs =
                        ((InternalEObject) newMaxElapseTimeStrategy)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY,
                                        null,
                                        msgs);
            msgs =
                    basicSetMaxElapseTimeStrategy(newMaxElapseTimeStrategy,
                            msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY,
                    newMaxElapseTimeStrategy, newMaxElapseTimeStrategy));
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
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY:
            return basicSetMaxLoopCountStrategy(null, msgs);
        case SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY:
            return basicSetNormalDistributionStrategy(null, msgs);
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY:
            return basicSetMaxElapseTimeStrategy(null, msgs);
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
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY:
            return getMaxLoopCountStrategy();
        case SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY:
            return getNormalDistributionStrategy();
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY:
            return getMaxElapseTimeStrategy();
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
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY:
            setMaxLoopCountStrategy((MaxLoopCountStrategyType) newValue);
            return;
        case SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY:
            setNormalDistributionStrategy((NormalDistributionStrategyType) newValue);
            return;
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY:
            setMaxElapseTimeStrategy((MaxElapseTimeStrategyType) newValue);
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
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY:
            setMaxLoopCountStrategy((MaxLoopCountStrategyType) null);
            return;
        case SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY:
            setNormalDistributionStrategy((NormalDistributionStrategyType) null);
            return;
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY:
            setMaxElapseTimeStrategy((MaxElapseTimeStrategyType) null);
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
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY:
            return maxLoopCountStrategy != null;
        case SimulationPackage.LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY:
            return normalDistributionStrategy != null;
        case SimulationPackage.LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY:
            return maxElapseTimeStrategy != null;
        }
        return super.eIsSet(featureID);
    }

} //LoopControlTypeImpl