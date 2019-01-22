/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workflow Process Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.WorkflowProcessSimulationDataType#getParameterDistribution <em>Parameter Distribution</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getWorkflowProcessSimulationDataType()
 * @model extendedMetaData="name='WorkflowProcessSimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface WorkflowProcessSimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Parameter Distribution</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.simulation.ParameterDistribution}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Distribution</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Distribution</em>' containment reference list.
     * @see com.tibco.xpd.simulation.SimulationPackage#getWorkflowProcessSimulationDataType_ParameterDistribution()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ParameterDistribution' namespace='##targetNamespace'"
     * @generated
     */
    EList<ParameterDistribution> getParameterDistribution();

} // WorkflowProcessSimulationDataType
