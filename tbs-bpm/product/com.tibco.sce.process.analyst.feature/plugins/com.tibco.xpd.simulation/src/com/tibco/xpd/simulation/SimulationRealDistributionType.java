/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Real Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getBasicDistribution <em>Basic Distribution</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getParameterBasedDistribution <em>Parameter Based Distribution</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getSimulationRealDistributionType()
 * @model extendedMetaData="name='SimulationRealDistributionType' kind='elementOnly'"
 * @generated
 */
public interface SimulationRealDistributionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Basic Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Basic Distribution</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Basic Distribution</em>' containment reference.
     * @see #setBasicDistribution(AbstractBasicDistribution)
     * @see com.tibco.xpd.simulation.SimulationPackage#getSimulationRealDistributionType_BasicDistribution()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BasicDistribution' namespace='##targetNamespace' subclass-wrap='BasicDistribution'"
     * @generated
     */
    AbstractBasicDistribution getBasicDistribution();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getBasicDistribution <em>Basic Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Basic Distribution</em>' containment reference.
     * @see #getBasicDistribution()
     * @generated
     */
    void setBasicDistribution(AbstractBasicDistribution value);

    /**
     * Returns the value of the '<em><b>Parameter Based Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Based Distribution</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Based Distribution</em>' containment reference.
     * @see #setParameterBasedDistribution(ParameterBasedDistribution)
     * @see com.tibco.xpd.simulation.SimulationPackage#getSimulationRealDistributionType_ParameterBasedDistribution()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ParameterBasedDistribution' namespace='##targetNamespace'"
     * @generated
     */
    ParameterBasedDistribution getParameterBasedDistribution();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getParameterBasedDistribution <em>Parameter Based Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Based Distribution</em>' containment reference.
     * @see #getParameterBasedDistribution()
     * @generated
     */
    void setParameterBasedDistribution(ParameterBasedDistribution value);

} // SimulationRealDistributionType
