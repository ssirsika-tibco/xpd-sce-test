/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.util;

import com.tibco.xpd.simulation.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage
 * @generated
 */
public class SimulationAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SimulationPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = SimulationPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimulationSwitch<Adapter> modelSwitch =
            new SimulationSwitch<Adapter>() {
                @Override
                public Adapter caseActivitySimulationDataType(
                        ActivitySimulationDataType object) {
                    return createActivitySimulationDataTypeAdapter();
                }

                @Override
                public Adapter caseAbstractBasicDistribution(
                        AbstractBasicDistribution object) {
                    return createAbstractBasicDistributionAdapter();
                }

                @Override
                public Adapter caseConstantRealDistribution(
                        ConstantRealDistribution object) {
                    return createConstantRealDistributionAdapter();
                }

                @Override
                public Adapter caseDocumentRoot(DocumentRoot object) {
                    return createDocumentRootAdapter();
                }

                @Override
                public Adapter caseEnumBasedExpressionType(
                        EnumBasedExpressionType object) {
                    return createEnumBasedExpressionTypeAdapter();
                }

                @Override
                public Adapter caseEnumerationValueType(
                        EnumerationValueType object) {
                    return createEnumerationValueTypeAdapter();
                }

                @Override
                public Adapter caseExponentialRealDistribution(
                        ExponentialRealDistribution object) {
                    return createExponentialRealDistributionAdapter();
                }

                @Override
                public Adapter caseExpressionType(ExpressionType object) {
                    return createExpressionTypeAdapter();
                }

                @Override
                public Adapter caseExternalEmpiricalDistribution(
                        ExternalEmpiricalDistribution object) {
                    return createExternalEmpiricalDistributionAdapter();
                }

                @Override
                public Adapter caseLoopControlTransitionType(
                        LoopControlTransitionType object) {
                    return createLoopControlTransitionTypeAdapter();
                }

                @Override
                public Adapter caseLoopControlType(LoopControlType object) {
                    return createLoopControlTypeAdapter();
                }

                @Override
                public Adapter caseMaxElapseTimeStrategyType(
                        MaxElapseTimeStrategyType object) {
                    return createMaxElapseTimeStrategyTypeAdapter();
                }

                @Override
                public Adapter caseMaxLoopCountStrategyType(
                        MaxLoopCountStrategyType object) {
                    return createMaxLoopCountStrategyTypeAdapter();
                }

                @Override
                public Adapter caseNormalRealDistribution(
                        NormalRealDistribution object) {
                    return createNormalRealDistributionAdapter();
                }

                @Override
                public Adapter caseParameterBasedDistribution(
                        ParameterBasedDistribution object) {
                    return createParameterBasedDistributionAdapter();
                }

                @Override
                public Adapter caseParameterDependentDistribution(
                        ParameterDependentDistribution object) {
                    return createParameterDependentDistributionAdapter();
                }

                @Override
                public Adapter caseParameterDistribution(
                        ParameterDistribution object) {
                    return createParameterDistributionAdapter();
                }

                @Override
                public Adapter caseParticipantSimulationDataType(
                        ParticipantSimulationDataType object) {
                    return createParticipantSimulationDataTypeAdapter();
                }

                @Override
                public Adapter caseSimulationRealDistributionType(
                        SimulationRealDistributionType object) {
                    return createSimulationRealDistributionTypeAdapter();
                }

                @Override
                public Adapter caseSplitParameterType(SplitParameterType object) {
                    return createSplitParameterTypeAdapter();
                }

                @Override
                public Adapter caseSplitSimulationDataType(
                        SplitSimulationDataType object) {
                    return createSplitSimulationDataTypeAdapter();
                }

                @Override
                public Adapter caseStartSimulationDataType(
                        StartSimulationDataType object) {
                    return createStartSimulationDataTypeAdapter();
                }

                @Override
                public Adapter caseStructuredConditionType(
                        StructuredConditionType object) {
                    return createStructuredConditionTypeAdapter();
                }

                @Override
                public Adapter caseTimeUnitCostType(TimeUnitCostType object) {
                    return createTimeUnitCostTypeAdapter();
                }

                @Override
                public Adapter caseTransitionSimulationDataType(
                        TransitionSimulationDataType object) {
                    return createTransitionSimulationDataTypeAdapter();
                }

                @Override
                public Adapter caseNormalDistributionStrategyType(
                        NormalDistributionStrategyType object) {
                    return createNormalDistributionStrategyTypeAdapter();
                }

                @Override
                public Adapter caseUniformRealDistribution(
                        UniformRealDistribution object) {
                    return createUniformRealDistributionAdapter();
                }

                @Override
                public Adapter caseWorkflowProcessSimulationDataType(
                        WorkflowProcessSimulationDataType object) {
                    return createWorkflowProcessSimulationDataTypeAdapter();
                }

                @Override
                public Adapter defaultCase(EObject object) {
                    return createEObjectAdapter();
                }
            };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ActivitySimulationDataType <em>Activity Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType
     * @generated
     */
    public Adapter createActivitySimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.AbstractBasicDistribution <em>Abstract Basic Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.AbstractBasicDistribution
     * @generated
     */
    public Adapter createAbstractBasicDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ConstantRealDistribution <em>Constant Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ConstantRealDistribution
     * @generated
     */
    public Adapter createConstantRealDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.EnumBasedExpressionType <em>Enum Based Expression Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.EnumBasedExpressionType
     * @generated
     */
    public Adapter createEnumBasedExpressionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.EnumerationValueType <em>Enumeration Value Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.EnumerationValueType
     * @generated
     */
    public Adapter createEnumerationValueTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ExponentialRealDistribution <em>Exponential Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ExponentialRealDistribution
     * @generated
     */
    public Adapter createExponentialRealDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ExpressionType <em>Expression Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ExpressionType
     * @generated
     */
    public Adapter createExpressionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution <em>External Empirical Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ExternalEmpiricalDistribution
     * @generated
     */
    public Adapter createExternalEmpiricalDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.LoopControlTransitionType <em>Loop Control Transition Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.LoopControlTransitionType
     * @generated
     */
    public Adapter createLoopControlTransitionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.LoopControlType <em>Loop Control Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.LoopControlType
     * @generated
     */
    public Adapter createLoopControlTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType <em>Max Elapse Time Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.MaxElapseTimeStrategyType
     * @generated
     */
    public Adapter createMaxElapseTimeStrategyTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType <em>Max Loop Count Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.MaxLoopCountStrategyType
     * @generated
     */
    public Adapter createMaxLoopCountStrategyTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.NormalRealDistribution <em>Normal Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.NormalRealDistribution
     * @generated
     */
    public Adapter createNormalRealDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ParameterBasedDistribution <em>Parameter Based Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ParameterBasedDistribution
     * @generated
     */
    public Adapter createParameterBasedDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ParameterDependentDistribution <em>Parameter Dependent Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ParameterDependentDistribution
     * @generated
     */
    public Adapter createParameterDependentDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ParameterDistribution <em>Parameter Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ParameterDistribution
     * @generated
     */
    public Adapter createParameterDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType <em>Participant Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType
     * @generated
     */
    public Adapter createParticipantSimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.SimulationRealDistributionType <em>Real Distribution Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.SimulationRealDistributionType
     * @generated
     */
    public Adapter createSimulationRealDistributionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.SplitParameterType <em>Split Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.SplitParameterType
     * @generated
     */
    public Adapter createSplitParameterTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.SplitSimulationDataType <em>Split Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.SplitSimulationDataType
     * @generated
     */
    public Adapter createSplitSimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.TimeUnitCostType <em>Time Unit Cost Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.TimeUnitCostType
     * @generated
     */
    public Adapter createTimeUnitCostTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.TransitionSimulationDataType <em>Transition Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.TransitionSimulationDataType
     * @generated
     */
    public Adapter createTransitionSimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.UniformRealDistribution <em>Uniform Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.UniformRealDistribution
     * @generated
     */
    public Adapter createUniformRealDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.WorkflowProcessSimulationDataType <em>Workflow Process Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.WorkflowProcessSimulationDataType
     * @generated
     */
    public Adapter createWorkflowProcessSimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.NormalDistributionStrategyType <em>Normal Distribution Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.NormalDistributionStrategyType
     * @generated
     */
    public Adapter createNormalDistributionStrategyTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.StartSimulationDataType <em>Start Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.StartSimulationDataType
     * @generated
     */
    public Adapter createStartSimulationDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.xpd.simulation.StructuredConditionType <em>Structured Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.xpd.simulation.StructuredConditionType
     * @generated
     */
    public Adapter createStructuredConditionTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //SimulationAdapterFactory
