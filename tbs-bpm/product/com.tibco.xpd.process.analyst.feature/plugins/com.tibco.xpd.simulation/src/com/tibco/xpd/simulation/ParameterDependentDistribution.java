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
 * A representation of the model object '<em><b>Parameter Dependent Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getBasicDistribution <em>Basic Distribution</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDependentDistribution()
 * @model extendedMetaData="name='ParameterDependentDistribution_._type' kind='elementOnly'"
 * @generated
 */
public interface ParameterDependentDistribution extends EObject {
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
     * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDependentDistribution_BasicDistribution()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BasicDistribution' namespace='##targetNamespace' subclass-wrap='BasicDistribution'"
     * @generated
     */
    AbstractBasicDistribution getBasicDistribution();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getBasicDistribution <em>Basic Distribution</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Basic Distribution</em>' containment reference.
     * @see #getBasicDistribution()
     * @generated
     */
    void setBasicDistribution(AbstractBasicDistribution value);

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression</em>' containment reference.
     * @see #setExpression(ExpressionType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDependentDistribution_Expression()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
     * @generated
     */
    ExpressionType getExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(ExpressionType value);

} // ParameterDependentDistribution