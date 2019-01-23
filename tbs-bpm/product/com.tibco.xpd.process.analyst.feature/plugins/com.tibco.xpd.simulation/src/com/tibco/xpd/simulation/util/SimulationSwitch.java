/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.util;

import com.tibco.xpd.simulation.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage
 * @generated
 */
public class SimulationSwitch<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SimulationPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationSwitch() {
        if (modelPackage == null) {
            modelPackage = SimulationPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject)
                    : doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE: {
            ActivitySimulationDataType activitySimulationDataType =
                    (ActivitySimulationDataType) theEObject;
            T result =
                    caseActivitySimulationDataType(activitySimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.ABSTRACT_BASIC_DISTRIBUTION: {
            AbstractBasicDistribution abstractBasicDistribution =
                    (AbstractBasicDistribution) theEObject;
            T result = caseAbstractBasicDistribution(abstractBasicDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION: {
            ConstantRealDistribution constantRealDistribution =
                    (ConstantRealDistribution) theEObject;
            T result = caseConstantRealDistribution(constantRealDistribution);
            if (result == null)
                result =
                        caseAbstractBasicDistribution(constantRealDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE: {
            EnumBasedExpressionType enumBasedExpressionType =
                    (EnumBasedExpressionType) theEObject;
            T result = caseEnumBasedExpressionType(enumBasedExpressionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.ENUMERATION_VALUE_TYPE: {
            EnumerationValueType enumerationValueType =
                    (EnumerationValueType) theEObject;
            T result = caseEnumerationValueType(enumerationValueType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.EXPONENTIAL_REAL_DISTRIBUTION: {
            ExponentialRealDistribution exponentialRealDistribution =
                    (ExponentialRealDistribution) theEObject;
            T result =
                    caseExponentialRealDistribution(exponentialRealDistribution);
            if (result == null)
                result =
                        caseAbstractBasicDistribution(exponentialRealDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.EXPRESSION_TYPE: {
            ExpressionType expressionType = (ExpressionType) theEObject;
            T result = caseExpressionType(expressionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.EXTERNAL_EMPIRICAL_DISTRIBUTION: {
            ExternalEmpiricalDistribution externalEmpiricalDistribution =
                    (ExternalEmpiricalDistribution) theEObject;
            T result =
                    caseExternalEmpiricalDistribution(externalEmpiricalDistribution);
            if (result == null)
                result =
                        caseAbstractBasicDistribution(externalEmpiricalDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE: {
            LoopControlTransitionType loopControlTransitionType =
                    (LoopControlTransitionType) theEObject;
            T result = caseLoopControlTransitionType(loopControlTransitionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.LOOP_CONTROL_TYPE: {
            LoopControlType loopControlType = (LoopControlType) theEObject;
            T result = caseLoopControlType(loopControlType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE: {
            MaxElapseTimeStrategyType maxElapseTimeStrategyType =
                    (MaxElapseTimeStrategyType) theEObject;
            T result = caseMaxElapseTimeStrategyType(maxElapseTimeStrategyType);
            if (result == null)
                result =
                        caseLoopControlTransitionType(maxElapseTimeStrategyType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE: {
            MaxLoopCountStrategyType maxLoopCountStrategyType =
                    (MaxLoopCountStrategyType) theEObject;
            T result = caseMaxLoopCountStrategyType(maxLoopCountStrategyType);
            if (result == null)
                result =
                        caseLoopControlTransitionType(maxLoopCountStrategyType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.NORMAL_REAL_DISTRIBUTION: {
            NormalRealDistribution normalRealDistribution =
                    (NormalRealDistribution) theEObject;
            T result = caseNormalRealDistribution(normalRealDistribution);
            if (result == null)
                result = caseAbstractBasicDistribution(normalRealDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION: {
            ParameterBasedDistribution parameterBasedDistribution =
                    (ParameterBasedDistribution) theEObject;
            T result =
                    caseParameterBasedDistribution(parameterBasedDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION: {
            ParameterDependentDistribution parameterDependentDistribution =
                    (ParameterDependentDistribution) theEObject;
            T result =
                    caseParameterDependentDistribution(parameterDependentDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.PARAMETER_DISTRIBUTION: {
            ParameterDistribution parameterDistribution =
                    (ParameterDistribution) theEObject;
            T result = caseParameterDistribution(parameterDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE: {
            ParticipantSimulationDataType participantSimulationDataType =
                    (ParticipantSimulationDataType) theEObject;
            T result =
                    caseParticipantSimulationDataType(participantSimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE: {
            SimulationRealDistributionType simulationRealDistributionType =
                    (SimulationRealDistributionType) theEObject;
            T result =
                    caseSimulationRealDistributionType(simulationRealDistributionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.SPLIT_PARAMETER_TYPE: {
            SplitParameterType splitParameterType =
                    (SplitParameterType) theEObject;
            T result = caseSplitParameterType(splitParameterType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE: {
            SplitSimulationDataType splitSimulationDataType =
                    (SplitSimulationDataType) theEObject;
            T result = caseSplitSimulationDataType(splitSimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.START_SIMULATION_DATA_TYPE: {
            StartSimulationDataType startSimulationDataType =
                    (StartSimulationDataType) theEObject;
            T result = caseStartSimulationDataType(startSimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.STRUCTURED_CONDITION_TYPE: {
            StructuredConditionType structuredConditionType =
                    (StructuredConditionType) theEObject;
            T result = caseStructuredConditionType(structuredConditionType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.TIME_UNIT_COST_TYPE: {
            TimeUnitCostType timeUnitCostType = (TimeUnitCostType) theEObject;
            T result = caseTimeUnitCostType(timeUnitCostType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE: {
            TransitionSimulationDataType transitionSimulationDataType =
                    (TransitionSimulationDataType) theEObject;
            T result =
                    caseTransitionSimulationDataType(transitionSimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.NORMAL_DISTRIBUTION_STRATEGY_TYPE: {
            NormalDistributionStrategyType normalDistributionStrategyType =
                    (NormalDistributionStrategyType) theEObject;
            T result =
                    caseNormalDistributionStrategyType(normalDistributionStrategyType);
            if (result == null)
                result =
                        caseLoopControlTransitionType(normalDistributionStrategyType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION: {
            UniformRealDistribution uniformRealDistribution =
                    (UniformRealDistribution) theEObject;
            T result = caseUniformRealDistribution(uniformRealDistribution);
            if (result == null)
                result = caseAbstractBasicDistribution(uniformRealDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE: {
            WorkflowProcessSimulationDataType workflowProcessSimulationDataType =
                    (WorkflowProcessSimulationDataType) theEObject;
            T result =
                    caseWorkflowProcessSimulationDataType(workflowProcessSimulationDataType);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseActivitySimulationDataType(ActivitySimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Basic Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Basic Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractBasicDistribution(AbstractBasicDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Constant Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Constant Real Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConstantRealDistribution(ConstantRealDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enum Based Expression Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enum Based Expression Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumBasedExpressionType(EnumBasedExpressionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Enumeration Value Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Enumeration Value Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEnumerationValueType(EnumerationValueType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Exponential Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Exponential Real Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExponentialRealDistribution(ExponentialRealDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Expression Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Expression Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExpressionType(ExpressionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>External Empirical Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>External Empirical Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExternalEmpiricalDistribution(
            ExternalEmpiricalDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop Control Transition Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop Control Transition Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoopControlTransitionType(LoopControlTransitionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop Control Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop Control Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoopControlType(LoopControlType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Max Elapse Time Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Max Elapse Time Strategy Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMaxElapseTimeStrategyType(MaxElapseTimeStrategyType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Max Loop Count Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Max Loop Count Strategy Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMaxLoopCountStrategyType(MaxLoopCountStrategyType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Normal Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Normal Real Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNormalRealDistribution(NormalRealDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Based Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Based Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterBasedDistribution(ParameterBasedDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Dependent Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Dependent Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterDependentDistribution(
            ParameterDependentDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Parameter Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Parameter Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParameterDistribution(ParameterDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participant Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participant Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseParticipantSimulationDataType(
            ParticipantSimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Real Distribution Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Real Distribution Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimulationRealDistributionType(
            SimulationRealDistributionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Split Parameter Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Split Parameter Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSplitParameterType(SplitParameterType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Split Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Split Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSplitSimulationDataType(SplitSimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Time Unit Cost Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Time Unit Cost Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTimeUnitCostType(TimeUnitCostType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Transition Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Transition Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTransitionSimulationDataType(
            TransitionSimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Uniform Real Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Uniform Real Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUniformRealDistribution(UniformRealDistribution object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Workflow Process Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Workflow Process Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkflowProcessSimulationDataType(
            WorkflowProcessSimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Normal Distribution Strategy Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Normal Distribution Strategy Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNormalDistributionStrategyType(
            NormalDistributionStrategyType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Simulation Data Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Simulation Data Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartSimulationDataType(StartSimulationDataType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Structured Condition Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Structured Condition Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStructuredConditionType(StructuredConditionType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //SimulationSwitch
