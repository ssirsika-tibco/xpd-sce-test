/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SimulationRealDistributionType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Real Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl#getBasicDistribution <em>Basic Distribution</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl#getParameterBasedDistribution <em>Parameter Based Distribution</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimulationRealDistributionTypeImpl extends EObjectImpl implements
        SimulationRealDistributionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBasicDistribution() <em>Basic Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBasicDistribution()
     * @generated
     * @ordered
     */
    protected AbstractBasicDistribution basicDistribution;

    /**
     * The cached value of the '{@link #getParameterBasedDistribution() <em>Parameter Based Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterBasedDistribution()
     * @generated
     * @ordered
     */
    protected ParameterBasedDistribution parameterBasedDistribution;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimulationRealDistributionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.SIMULATION_REAL_DISTRIBUTION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AbstractBasicDistribution getBasicDistribution() {
        return basicDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBasicDistribution(
            AbstractBasicDistribution newBasicDistribution,
            NotificationChain msgs) {
        AbstractBasicDistribution oldBasicDistribution = basicDistribution;
        basicDistribution = newBasicDistribution;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION,
                            oldBasicDistribution, newBasicDistribution);
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
    public void setBasicDistribution(
            AbstractBasicDistribution newBasicDistribution) {
        if (newBasicDistribution != basicDistribution) {
            NotificationChain msgs = null;
            if (basicDistribution != null)
                msgs =
                        ((InternalEObject) basicDistribution)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION,
                                        null,
                                        msgs);
            if (newBasicDistribution != null)
                msgs =
                        ((InternalEObject) newBasicDistribution)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION,
                                        null,
                                        msgs);
            msgs = basicSetBasicDistribution(newBasicDistribution, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION,
                    newBasicDistribution, newBasicDistribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterBasedDistribution getParameterBasedDistribution() {
        return parameterBasedDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParameterBasedDistribution(
            ParameterBasedDistribution newParameterBasedDistribution,
            NotificationChain msgs) {
        ParameterBasedDistribution oldParameterBasedDistribution =
                parameterBasedDistribution;
        parameterBasedDistribution = newParameterBasedDistribution;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION,
                            oldParameterBasedDistribution,
                            newParameterBasedDistribution);
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
    public void setParameterBasedDistribution(
            ParameterBasedDistribution newParameterBasedDistribution) {
        if (newParameterBasedDistribution != parameterBasedDistribution) {
            NotificationChain msgs = null;
            if (parameterBasedDistribution != null)
                msgs =
                        ((InternalEObject) parameterBasedDistribution)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION,
                                        null,
                                        msgs);
            if (newParameterBasedDistribution != null)
                msgs =
                        ((InternalEObject) newParameterBasedDistribution)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION,
                                        null,
                                        msgs);
            msgs =
                    basicSetParameterBasedDistribution(newParameterBasedDistribution,
                            msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION,
                    newParameterBasedDistribution,
                    newParameterBasedDistribution));
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
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION:
            return basicSetBasicDistribution(null, msgs);
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION:
            return basicSetParameterBasedDistribution(null, msgs);
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
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION:
            return getBasicDistribution();
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION:
            return getParameterBasedDistribution();
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
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION:
            setBasicDistribution((AbstractBasicDistribution) newValue);
            return;
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION:
            setParameterBasedDistribution((ParameterBasedDistribution) newValue);
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
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION:
            setBasicDistribution((AbstractBasicDistribution) null);
            return;
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION:
            setParameterBasedDistribution((ParameterBasedDistribution) null);
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
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION:
            return basicDistribution != null;
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION:
            return parameterBasedDistribution != null;
        }
        return super.eIsSet(featureID);
    }

} //SimulationRealDistributionTypeImpl