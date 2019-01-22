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
 * A representation of the model object '<em><b>Parameter Based Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ParameterBasedDistribution#getParameterDependentDistributions <em>Parameter Dependent Distributions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getParameterBasedDistribution()
 * @model extendedMetaData="name='ParameterBasedDistribution_._type' kind='elementOnly'"
 * @generated
 */
public interface ParameterBasedDistribution extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Parameter Dependent Distributions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.simulation.ParameterDependentDistribution}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Dependent Distributions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Dependent Distributions</em>' containment reference list.
     * @see com.tibco.xpd.simulation.SimulationPackage#getParameterBasedDistribution_ParameterDependentDistributions()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ParameterDependentDistribution' namespace='##targetNamespace'"
     * @generated
     */
    EList<ParameterDependentDistribution> getParameterDependentDistributions();

} // ParameterBasedDistribution