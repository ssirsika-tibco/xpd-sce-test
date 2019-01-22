/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;

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
 * An implementation of the model object '<em><b>Workflow Process Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.WorkflowProcessSimulationDataTypeImpl#getParameterDistribution <em>Parameter Distribution</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkflowProcessSimulationDataTypeImpl extends EObjectImpl
        implements WorkflowProcessSimulationDataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getParameterDistribution() <em>Parameter Distribution</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameterDistribution()
     * @generated
     * @ordered
     */
    protected EList<ParameterDistribution> parameterDistribution;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkflowProcessSimulationDataTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ParameterDistribution> getParameterDistribution() {
        if (parameterDistribution == null) {
            parameterDistribution =
                    new EObjectContainmentEList<ParameterDistribution>(
                            ParameterDistribution.class,
                            this,
                            SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION);
        }
        return parameterDistribution;
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
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION:
            return ((InternalEList<?>) getParameterDistribution())
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
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION:
            return getParameterDistribution();
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
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION:
            getParameterDistribution().clear();
            getParameterDistribution()
                    .addAll((Collection<? extends ParameterDistribution>) newValue);
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
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION:
            getParameterDistribution().clear();
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
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION:
            return parameterDistribution != null
                    && !parameterDistribution.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //WorkflowProcessSimulationDataTypeImpl