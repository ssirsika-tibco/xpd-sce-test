/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage
 * @generated
 */
public interface SimulationFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SimulationFactory eINSTANCE =
            com.tibco.xpd.simulation.impl.SimulationFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Activity Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Activity Simulation Data Type</em>'.
     * @generated
     */
    ActivitySimulationDataType createActivitySimulationDataType();

    /**
     * Returns a new object of class '<em>Constant Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Constant Real Distribution</em>'.
     * @generated
     */
    ConstantRealDistribution createConstantRealDistribution();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Enum Based Expression Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enum Based Expression Type</em>'.
     * @generated
     */
    EnumBasedExpressionType createEnumBasedExpressionType();

    /**
     * Returns a new object of class '<em>Enumeration Value Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Enumeration Value Type</em>'.
     * @generated
     */
    EnumerationValueType createEnumerationValueType();

    /**
     * Returns a new object of class '<em>Exponential Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Exponential Real Distribution</em>'.
     * @generated
     */
    ExponentialRealDistribution createExponentialRealDistribution();

    /**
     * Returns a new object of class '<em>Expression Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Expression Type</em>'.
     * @generated
     */
    ExpressionType createExpressionType();

    /**
     * Returns a new object of class '<em>External Empirical Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>External Empirical Distribution</em>'.
     * @generated
     */
    ExternalEmpiricalDistribution createExternalEmpiricalDistribution();

    /**
     * Returns a new object of class '<em>Loop Control Transition Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Loop Control Transition Type</em>'.
     * @generated
     */
    LoopControlTransitionType createLoopControlTransitionType();

    /**
     * Returns a new object of class '<em>Loop Control Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Loop Control Type</em>'.
     * @generated
     */
    LoopControlType createLoopControlType();

    /**
     * Returns a new object of class '<em>Max Elapse Time Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Max Elapse Time Strategy Type</em>'.
     * @generated
     */
    MaxElapseTimeStrategyType createMaxElapseTimeStrategyType();

    /**
     * Returns a new object of class '<em>Max Loop Count Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Max Loop Count Strategy Type</em>'.
     * @generated
     */
    MaxLoopCountStrategyType createMaxLoopCountStrategyType();

    /**
     * Returns a new object of class '<em>Normal Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Normal Real Distribution</em>'.
     * @generated
     */
    NormalRealDistribution createNormalRealDistribution();

    /**
     * Returns a new object of class '<em>Parameter Based Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter Based Distribution</em>'.
     * @generated
     */
    ParameterBasedDistribution createParameterBasedDistribution();

    /**
     * Returns a new object of class '<em>Parameter Dependent Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter Dependent Distribution</em>'.
     * @generated
     */
    ParameterDependentDistribution createParameterDependentDistribution();

    /**
     * Returns a new object of class '<em>Parameter Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Parameter Distribution</em>'.
     * @generated
     */
    ParameterDistribution createParameterDistribution();

    /**
     * Returns a new object of class '<em>Participant Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Participant Simulation Data Type</em>'.
     * @generated
     */
    ParticipantSimulationDataType createParticipantSimulationDataType();

    /**
     * Returns a new object of class '<em>Real Distribution Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Real Distribution Type</em>'.
     * @generated
     */
    SimulationRealDistributionType createSimulationRealDistributionType();

    /**
     * Returns a new object of class '<em>Split Parameter Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Split Parameter Type</em>'.
     * @generated
     */
    SplitParameterType createSplitParameterType();

    /**
     * Returns a new object of class '<em>Split Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Split Simulation Data Type</em>'.
     * @generated
     */
    SplitSimulationDataType createSplitSimulationDataType();

    /**
     * Returns a new object of class '<em>Time Unit Cost Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Time Unit Cost Type</em>'.
     * @generated
     */
    TimeUnitCostType createTimeUnitCostType();

    /**
     * Returns a new object of class '<em>Transition Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Transition Simulation Data Type</em>'.
     * @generated
     */
    TransitionSimulationDataType createTransitionSimulationDataType();

    /**
     * Returns a new object of class '<em>Uniform Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Uniform Real Distribution</em>'.
     * @generated
     */
    UniformRealDistribution createUniformRealDistribution();

    /**
     * Returns a new object of class '<em>Workflow Process Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Workflow Process Simulation Data Type</em>'.
     * @generated
     */
    WorkflowProcessSimulationDataType createWorkflowProcessSimulationDataType();

    /**
     * Returns a new object of class '<em>Normal Distribution Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Normal Distribution Strategy Type</em>'.
     * @generated
     */
    NormalDistributionStrategyType createNormalDistributionStrategyType();

    /**
     * Returns a new object of class '<em>Start Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Start Simulation Data Type</em>'.
     * @generated
     */
    StartSimulationDataType createStartSimulationDataType();

    /**
     * Returns a new object of class '<em>Structured Condition Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Structured Condition Type</em>'.
     * @generated
     */
    StructuredConditionType createStructuredConditionType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    SimulationPackage getSimulationPackage();

} //SimulationFactory
