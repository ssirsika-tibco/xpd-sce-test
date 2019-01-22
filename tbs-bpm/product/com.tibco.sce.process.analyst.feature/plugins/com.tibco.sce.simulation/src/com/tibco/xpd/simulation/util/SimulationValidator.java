/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.util;

import com.tibco.xpd.simulation.*;

import java.math.BigInteger;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationPackage
 * @generated
 */
public class SimulationValidator extends EObjectValidator {
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
    public static final SimulationValidator INSTANCE =
            new SimulationValidator();

    /**
     * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.common.util.Diagnostic#getSource()
     * @see org.eclipse.emf.common.util.Diagnostic#getCode()
     * @generated
     */
    public static final String DIAGNOSTIC_SOURCE = "com.tibco.xpd.simulation"; //$NON-NLS-1$

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

    /**
     * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static final int DIAGNOSTIC_CODE_COUNT =
            GENERATED_DIAGNOSTIC_CODE_COUNT;

    /**
     * The cached base package validator.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XMLTypeValidator xmlTypeValidator;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationValidator() {
        super();
        xmlTypeValidator = XMLTypeValidator.INSTANCE;
    }

    /**
     * Returns the package of this validator switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EPackage getEPackage() {
        return SimulationPackage.eINSTANCE;
    }

    /**
     * Calls <code>validateXXX</code> for the corresponding classifier of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean validate(int classifierID, Object value,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        switch (classifierID) {
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE:
            return validateActivitySimulationDataType((ActivitySimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.ABSTRACT_BASIC_DISTRIBUTION:
            return validateAbstractBasicDistribution((AbstractBasicDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION:
            return validateConstantRealDistribution((ConstantRealDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.DOCUMENT_ROOT:
            return validateDocumentRoot((DocumentRoot) value,
                    diagnostics,
                    context);
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE:
            return validateEnumBasedExpressionType((EnumBasedExpressionType) value,
                    diagnostics,
                    context);
        case SimulationPackage.ENUMERATION_VALUE_TYPE:
            return validateEnumerationValueType((EnumerationValueType) value,
                    diagnostics,
                    context);
        case SimulationPackage.EXPONENTIAL_REAL_DISTRIBUTION:
            return validateExponentialRealDistribution((ExponentialRealDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.EXPRESSION_TYPE:
            return validateExpressionType((ExpressionType) value,
                    diagnostics,
                    context);
        case SimulationPackage.EXTERNAL_EMPIRICAL_DISTRIBUTION:
            return validateExternalEmpiricalDistribution((ExternalEmpiricalDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE:
            return validateLoopControlTransitionType((LoopControlTransitionType) value,
                    diagnostics,
                    context);
        case SimulationPackage.LOOP_CONTROL_TYPE:
            return validateLoopControlType((LoopControlType) value,
                    diagnostics,
                    context);
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE:
            return validateMaxElapseTimeStrategyType((MaxElapseTimeStrategyType) value,
                    diagnostics,
                    context);
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE:
            return validateMaxLoopCountStrategyType((MaxLoopCountStrategyType) value,
                    diagnostics,
                    context);
        case SimulationPackage.NORMAL_REAL_DISTRIBUTION:
            return validateNormalRealDistribution((NormalRealDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION:
            return validateParameterBasedDistribution((ParameterBasedDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION:
            return validateParameterDependentDistribution((ParameterDependentDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.PARAMETER_DISTRIBUTION:
            return validateParameterDistribution((ParameterDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE:
            return validateParticipantSimulationDataType((ParticipantSimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE:
            return validateSimulationRealDistributionType((SimulationRealDistributionType) value,
                    diagnostics,
                    context);
        case SimulationPackage.SPLIT_PARAMETER_TYPE:
            return validateSplitParameterType((SplitParameterType) value,
                    diagnostics,
                    context);
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE:
            return validateSplitSimulationDataType((SplitSimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.START_SIMULATION_DATA_TYPE:
            return validateStartSimulationDataType((StartSimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.STRUCTURED_CONDITION_TYPE:
            return validateStructuredConditionType((StructuredConditionType) value,
                    diagnostics,
                    context);
        case SimulationPackage.TIME_UNIT_COST_TYPE:
            return validateTimeUnitCostType((TimeUnitCostType) value,
                    diagnostics,
                    context);
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE:
            return validateTransitionSimulationDataType((TransitionSimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.NORMAL_DISTRIBUTION_STRATEGY_TYPE:
            return validateNormalDistributionStrategyType((NormalDistributionStrategyType) value,
                    diagnostics,
                    context);
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION:
            return validateUniformRealDistribution((UniformRealDistribution) value,
                    diagnostics,
                    context);
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE:
            return validateWorkflowProcessSimulationDataType((WorkflowProcessSimulationDataType) value,
                    diagnostics,
                    context);
        case SimulationPackage.LOOP_CONTROL_STRATEGY:
            return validateLoopControlStrategy((LoopControlStrategy) value,
                    diagnostics,
                    context);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY:
            return validateRealDistributionCategory((RealDistributionCategory) value,
                    diagnostics,
                    context);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE:
            return validateTimeDisplayUnitType((TimeDisplayUnitType) value,
                    diagnostics,
                    context);
        case SimulationPackage.INSTANCES_TYPE:
            return validateInstancesType((BigInteger) value,
                    diagnostics,
                    context);
        case SimulationPackage.LOOP_CONTROL_STRATEGY_OBJECT:
            return validateLoopControlStrategyObject((LoopControlStrategy) value,
                    diagnostics,
                    context);
        case SimulationPackage.OPERATOR_TYPE:
            return validateOperatorType((String) value, diagnostics, context);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY_OBJECT:
            return validateRealDistributionCategoryObject((RealDistributionCategory) value,
                    diagnostics,
                    context);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE_OBJECT:
            return validateTimeDisplayUnitTypeObject((TimeDisplayUnitType) value,
                    diagnostics,
                    context);
        default:
            return true;
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateActivitySimulationDataType(
            ActivitySimulationDataType activitySimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(activitySimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateAbstractBasicDistribution(
            AbstractBasicDistribution abstractBasicDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(abstractBasicDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateConstantRealDistribution(
            ConstantRealDistribution constantRealDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(constantRealDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateDocumentRoot(DocumentRoot documentRoot,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(documentRoot,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEnumBasedExpressionType(
            EnumBasedExpressionType enumBasedExpressionType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(enumBasedExpressionType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateEnumerationValueType(
            EnumerationValueType enumerationValueType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(enumerationValueType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateExponentialRealDistribution(
            ExponentialRealDistribution exponentialRealDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(exponentialRealDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateExpressionType(ExpressionType expressionType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(expressionType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateExternalEmpiricalDistribution(
            ExternalEmpiricalDistribution externalEmpiricalDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(externalEmpiricalDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLoopControlTransitionType(
            LoopControlTransitionType loopControlTransitionType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(loopControlTransitionType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLoopControlType(LoopControlType loopControlType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(loopControlType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMaxElapseTimeStrategyType(
            MaxElapseTimeStrategyType maxElapseTimeStrategyType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(maxElapseTimeStrategyType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateMaxLoopCountStrategyType(
            MaxLoopCountStrategyType maxLoopCountStrategyType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(maxLoopCountStrategyType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateNormalRealDistribution(
            NormalRealDistribution normalRealDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(normalRealDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateParameterBasedDistribution(
            ParameterBasedDistribution parameterBasedDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(parameterBasedDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateParameterDependentDistribution(
            ParameterDependentDistribution parameterDependentDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(parameterDependentDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateParameterDistribution(
            ParameterDistribution parameterDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(parameterDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateParticipantSimulationDataType(
            ParticipantSimulationDataType participantSimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(participantSimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSimulationRealDistributionType(
            SimulationRealDistributionType simulationRealDistributionType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(simulationRealDistributionType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSplitParameterType(
            SplitParameterType splitParameterType, DiagnosticChain diagnostics,
            Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(splitParameterType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateSplitSimulationDataType(
            SplitSimulationDataType splitSimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(splitSimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTimeUnitCostType(TimeUnitCostType timeUnitCostType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(timeUnitCostType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTransitionSimulationDataType(
            TransitionSimulationDataType transitionSimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(transitionSimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateUniformRealDistribution(
            UniformRealDistribution uniformRealDistribution,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(uniformRealDistribution,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateWorkflowProcessSimulationDataType(
            WorkflowProcessSimulationDataType workflowProcessSimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(workflowProcessSimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateNormalDistributionStrategyType(
            NormalDistributionStrategyType normalDistributionStrategyType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(normalDistributionStrategyType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLoopControlStrategy(
            LoopControlStrategy loopControlStrategy,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateRealDistributionCategory(
            RealDistributionCategory realDistributionCategory,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTimeDisplayUnitType(
            TimeDisplayUnitType timeDisplayUnitType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateStartSimulationDataType(
            StartSimulationDataType startSimulationDataType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(startSimulationDataType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateStructuredConditionType(
            StructuredConditionType structuredConditionType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return validate_EveryDefaultConstraint(structuredConditionType,
                diagnostics,
                context);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateInstancesType(BigInteger instancesType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result =
                validateInstancesType_Min(instancesType, diagnostics, context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @see #validateInstancesType_Min
     */
    public static final BigInteger INSTANCES_TYPE__MIN__VALUE =
            new BigInteger("0"); //$NON-NLS-1$

    /**
     * Validates the Min constraint of '<em>Instances Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateInstancesType_Min(BigInteger instancesType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result =
                instancesType.compareTo(INSTANCES_TYPE__MIN__VALUE) >= 0;
        if (!result && diagnostics != null)
            reportMinViolation(SimulationPackage.Literals.INSTANCES_TYPE,
                    instancesType,
                    INSTANCES_TYPE__MIN__VALUE,
                    true,
                    diagnostics,
                    context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateLoopControlStrategyObject(
            LoopControlStrategy loopControlStrategyObject,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOperatorType(String operatorType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result =
                validateOperatorType_Enumeration(operatorType,
                        diagnostics,
                        context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @see #validateOperatorType_Enumeration
     */
    public static final Collection<Object> OPERATOR_TYPE__ENUMERATION__VALUES =
            wrapEnumerationValues(new Object[] { "=" }); //$NON-NLS-1$

    /**
     * Validates the Enumeration constraint of '<em>Operator Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateOperatorType_Enumeration(String operatorType,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean result =
                OPERATOR_TYPE__ENUMERATION__VALUES.contains(operatorType);
        if (!result && diagnostics != null)
            reportEnumerationViolation(SimulationPackage.Literals.OPERATOR_TYPE,
                    operatorType,
                    OPERATOR_TYPE__ENUMERATION__VALUES,
                    diagnostics,
                    context);
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateRealDistributionCategoryObject(
            RealDistributionCategory realDistributionCategoryObject,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean validateTimeDisplayUnitTypeObject(
            TimeDisplayUnitType timeDisplayUnitTypeObject,
            DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    /**
     * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        // TODO
        // Specialize this to return a resource locator for messages specific to this validator.
        // Ensure that you remove @generated or mark it @generated NOT
        return super.getResourceLocator();
    }

} //SimulationValidator
