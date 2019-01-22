/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.SimulationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Based Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.ParameterBasedDistributionImpl#getParameterDependentDistributions <em>Parameter Dependent Distributions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterBasedDistributionImpl extends EObjectImpl implements
        ParameterBasedDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getParameterDependentDistributions() <em>Parameter Dependent Distributions</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterDependentDistributions()
     * @generated
     * @ordered
     */
    protected EList<ParameterDependentDistribution> parameterDependentDistributions;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ParameterBasedDistributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.PARAMETER_BASED_DISTRIBUTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ParameterDependentDistribution> getParameterDependentDistributions() {
        if (parameterDependentDistributions == null) {
            parameterDependentDistributions =
                    new EObjectContainmentEList<ParameterDependentDistribution>(
                            ParameterDependentDistribution.class,
                            this,
                            SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS);
        }
        return parameterDependentDistributions;
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
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS:
            return ((InternalEList<?>) getParameterDependentDistributions())
                    .basicRemove(otherEnd, msgs);
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
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS:
            return getParameterDependentDistributions();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS:
            getParameterDependentDistributions().clear();
            getParameterDependentDistributions()
                    .addAll((Collection<? extends ParameterDependentDistribution>) newValue);
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
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS:
            getParameterDependentDistributions().clear();
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
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS:
            return parameterDependentDistributions != null
                    && !parameterDependentDistributions.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ParameterBasedDistributionImpl