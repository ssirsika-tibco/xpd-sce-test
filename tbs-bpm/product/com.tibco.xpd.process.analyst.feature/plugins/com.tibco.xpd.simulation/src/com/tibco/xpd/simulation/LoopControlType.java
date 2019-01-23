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
 * A representation of the model object '<em><b>Loop Control Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.LoopControlType#getMaxLoopCountStrategy <em>Max Loop Count Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.LoopControlType#getNormalDistributionStrategy <em>Normal Distribution Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.LoopControlType#getMaxElapseTimeStrategy <em>Max Elapse Time Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlType()
 * @model extendedMetaData="name='LoopControlType' kind='elementOnly'"
 * @generated
 */
public interface LoopControlType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Max Loop Count Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Loop Count Strategy</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Loop Count Strategy</em>' containment reference.
     * @see #setMaxLoopCountStrategy(MaxLoopCountStrategyType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlType_MaxLoopCountStrategy()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='MaxLoopCountStrategy' namespace='##targetNamespace'"
     * @generated
     */
    MaxLoopCountStrategyType getMaxLoopCountStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.LoopControlType#getMaxLoopCountStrategy <em>Max Loop Count Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Loop Count Strategy</em>' containment reference.
     * @see #getMaxLoopCountStrategy()
     * @generated
     */
    void setMaxLoopCountStrategy(MaxLoopCountStrategyType value);

    /**
     * Returns the value of the '<em><b>Normal Distribution Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Normal Distribution Strategy</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Normal Distribution Strategy</em>' containment reference.
     * @see #setNormalDistributionStrategy(NormalDistributionStrategyType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlType_NormalDistributionStrategy()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='NormalDistributionStrategy' namespace='##targetNamespace'"
     * @generated
     */
    NormalDistributionStrategyType getNormalDistributionStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.LoopControlType#getNormalDistributionStrategy <em>Normal Distribution Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Normal Distribution Strategy</em>' containment reference.
     * @see #getNormalDistributionStrategy()
     * @generated
     */
    void setNormalDistributionStrategy(NormalDistributionStrategyType value);

    /**
     * Returns the value of the '<em><b>Max Elapse Time Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Elapse Time Strategy</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Elapse Time Strategy</em>' containment reference.
     * @see #setMaxElapseTimeStrategy(MaxElapseTimeStrategyType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlType_MaxElapseTimeStrategy()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='MaxElapseTimeStrategy' namespace='##targetNamespace'"
     * @generated
     */
    MaxElapseTimeStrategyType getMaxElapseTimeStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.LoopControlType#getMaxElapseTimeStrategy <em>Max Elapse Time Strategy</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Elapse Time Strategy</em>' containment reference.
     * @see #getMaxElapseTimeStrategy()
     * @generated
     */
    void setMaxElapseTimeStrategy(MaxElapseTimeStrategyType value);

} // LoopControlType