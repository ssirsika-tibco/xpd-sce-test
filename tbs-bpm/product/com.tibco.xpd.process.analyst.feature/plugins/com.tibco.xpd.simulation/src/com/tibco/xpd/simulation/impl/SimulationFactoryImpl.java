/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.*;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimulationFactoryImpl extends EFactoryImpl implements
        SimulationFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimulationFactory init() {
        try {
            SimulationFactory theSimulationFactory =
                    (SimulationFactory) EPackage.Registry.INSTANCE
                            .getEFactory("http://www.tibco.com/xpd/Simulation1.0.1"); //$NON-NLS-1$ 
            if (theSimulationFactory != null) {
                return theSimulationFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SimulationFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case SimulationPackage.ACTIVITY_SIMULATION_DATA_TYPE:
            return createActivitySimulationDataType();
        case SimulationPackage.CONSTANT_REAL_DISTRIBUTION:
            return createConstantRealDistribution();
        case SimulationPackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case SimulationPackage.ENUM_BASED_EXPRESSION_TYPE:
            return createEnumBasedExpressionType();
        case SimulationPackage.ENUMERATION_VALUE_TYPE:
            return createEnumerationValueType();
        case SimulationPackage.EXPONENTIAL_REAL_DISTRIBUTION:
            return createExponentialRealDistribution();
        case SimulationPackage.EXPRESSION_TYPE:
            return createExpressionType();
        case SimulationPackage.EXTERNAL_EMPIRICAL_DISTRIBUTION:
            return createExternalEmpiricalDistribution();
        case SimulationPackage.LOOP_CONTROL_TRANSITION_TYPE:
            return createLoopControlTransitionType();
        case SimulationPackage.LOOP_CONTROL_TYPE:
            return createLoopControlType();
        case SimulationPackage.MAX_ELAPSE_TIME_STRATEGY_TYPE:
            return createMaxElapseTimeStrategyType();
        case SimulationPackage.MAX_LOOP_COUNT_STRATEGY_TYPE:
            return createMaxLoopCountStrategyType();
        case SimulationPackage.NORMAL_REAL_DISTRIBUTION:
            return createNormalRealDistribution();
        case SimulationPackage.PARAMETER_BASED_DISTRIBUTION:
            return createParameterBasedDistribution();
        case SimulationPackage.PARAMETER_DEPENDENT_DISTRIBUTION:
            return createParameterDependentDistribution();
        case SimulationPackage.PARAMETER_DISTRIBUTION:
            return createParameterDistribution();
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE:
            return createParticipantSimulationDataType();
        case SimulationPackage.SIMULATION_REAL_DISTRIBUTION_TYPE:
            return createSimulationRealDistributionType();
        case SimulationPackage.SPLIT_PARAMETER_TYPE:
            return createSplitParameterType();
        case SimulationPackage.SPLIT_SIMULATION_DATA_TYPE:
            return createSplitSimulationDataType();
        case SimulationPackage.START_SIMULATION_DATA_TYPE:
            return createStartSimulationDataType();
        case SimulationPackage.STRUCTURED_CONDITION_TYPE:
            return createStructuredConditionType();
        case SimulationPackage.TIME_UNIT_COST_TYPE:
            return createTimeUnitCostType();
        case SimulationPackage.TRANSITION_SIMULATION_DATA_TYPE:
            return createTransitionSimulationDataType();
        case SimulationPackage.NORMAL_DISTRIBUTION_STRATEGY_TYPE:
            return createNormalDistributionStrategyType();
        case SimulationPackage.UNIFORM_REAL_DISTRIBUTION:
            return createUniformRealDistribution();
        case SimulationPackage.WORKFLOW_PROCESS_SIMULATION_DATA_TYPE:
            return createWorkflowProcessSimulationDataType();
        default:
            throw new IllegalArgumentException(
                    "The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case SimulationPackage.LOOP_CONTROL_STRATEGY:
            return createLoopControlStrategyFromString(eDataType, initialValue);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY:
            return createRealDistributionCategoryFromString(eDataType,
                    initialValue);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE:
            return createTimeDisplayUnitTypeFromString(eDataType, initialValue);
        case SimulationPackage.INSTANCES_TYPE:
            return createInstancesTypeFromString(eDataType, initialValue);
        case SimulationPackage.LOOP_CONTROL_STRATEGY_OBJECT:
            return createLoopControlStrategyObjectFromString(eDataType,
                    initialValue);
        case SimulationPackage.OPERATOR_TYPE:
            return createOperatorTypeFromString(eDataType, initialValue);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY_OBJECT:
            return createRealDistributionCategoryObjectFromString(eDataType,
                    initialValue);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE_OBJECT:
            return createTimeDisplayUnitTypeObjectFromString(eDataType,
                    initialValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case SimulationPackage.LOOP_CONTROL_STRATEGY:
            return convertLoopControlStrategyToString(eDataType, instanceValue);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY:
            return convertRealDistributionCategoryToString(eDataType,
                    instanceValue);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE:
            return convertTimeDisplayUnitTypeToString(eDataType, instanceValue);
        case SimulationPackage.INSTANCES_TYPE:
            return convertInstancesTypeToString(eDataType, instanceValue);
        case SimulationPackage.LOOP_CONTROL_STRATEGY_OBJECT:
            return convertLoopControlStrategyObjectToString(eDataType,
                    instanceValue);
        case SimulationPackage.OPERATOR_TYPE:
            return convertOperatorTypeToString(eDataType, instanceValue);
        case SimulationPackage.REAL_DISTRIBUTION_CATEGORY_OBJECT:
            return convertRealDistributionCategoryObjectToString(eDataType,
                    instanceValue);
        case SimulationPackage.TIME_DISPLAY_UNIT_TYPE_OBJECT:
            return convertTimeDisplayUnitTypeObjectToString(eDataType,
                    instanceValue);
        default:
            throw new IllegalArgumentException(
                    "The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivitySimulationDataType createActivitySimulationDataType() {
        ActivitySimulationDataTypeImpl activitySimulationDataType =
                new ActivitySimulationDataTypeImpl();
        return activitySimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConstantRealDistribution createConstantRealDistribution() {
        ConstantRealDistributionImpl constantRealDistribution =
                new ConstantRealDistributionImpl();
        return constantRealDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumBasedExpressionType createEnumBasedExpressionType() {
        EnumBasedExpressionTypeImpl enumBasedExpressionType =
                new EnumBasedExpressionTypeImpl();
        return enumBasedExpressionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EnumerationValueType createEnumerationValueType() {
        EnumerationValueTypeImpl enumerationValueType =
                new EnumerationValueTypeImpl();
        return enumerationValueType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExponentialRealDistribution createExponentialRealDistribution() {
        ExponentialRealDistributionImpl exponentialRealDistribution =
                new ExponentialRealDistributionImpl();
        return exponentialRealDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExpressionType createExpressionType() {
        ExpressionTypeImpl expressionType = new ExpressionTypeImpl();
        return expressionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalEmpiricalDistribution createExternalEmpiricalDistribution() {
        ExternalEmpiricalDistributionImpl externalEmpiricalDistribution =
                new ExternalEmpiricalDistributionImpl();
        return externalEmpiricalDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopControlTransitionType createLoopControlTransitionType() {
        LoopControlTransitionTypeImpl loopControlTransitionType =
                new LoopControlTransitionTypeImpl();
        return loopControlTransitionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopControlType createLoopControlType() {
        LoopControlTypeImpl loopControlType = new LoopControlTypeImpl();
        return loopControlType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MaxElapseTimeStrategyType createMaxElapseTimeStrategyType() {
        MaxElapseTimeStrategyTypeImpl maxElapseTimeStrategyType =
                new MaxElapseTimeStrategyTypeImpl();
        return maxElapseTimeStrategyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MaxLoopCountStrategyType createMaxLoopCountStrategyType() {
        MaxLoopCountStrategyTypeImpl maxLoopCountStrategyType =
                new MaxLoopCountStrategyTypeImpl();
        return maxLoopCountStrategyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NormalRealDistribution createNormalRealDistribution() {
        NormalRealDistributionImpl normalRealDistribution =
                new NormalRealDistributionImpl();
        return normalRealDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterBasedDistribution createParameterBasedDistribution() {
        ParameterBasedDistributionImpl parameterBasedDistribution =
                new ParameterBasedDistributionImpl();
        return parameterBasedDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterDependentDistribution createParameterDependentDistribution() {
        ParameterDependentDistributionImpl parameterDependentDistribution =
                new ParameterDependentDistributionImpl();
        return parameterDependentDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParameterDistribution createParameterDistribution() {
        ParameterDistributionImpl parameterDistribution =
                new ParameterDistributionImpl();
        return parameterDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParticipantSimulationDataType createParticipantSimulationDataType() {
        ParticipantSimulationDataTypeImpl participantSimulationDataType =
                new ParticipantSimulationDataTypeImpl();
        return participantSimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationRealDistributionType createSimulationRealDistributionType() {
        SimulationRealDistributionTypeImpl simulationRealDistributionType =
                new SimulationRealDistributionTypeImpl();
        return simulationRealDistributionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SplitParameterType createSplitParameterType() {
        SplitParameterTypeImpl splitParameterType =
                new SplitParameterTypeImpl();
        return splitParameterType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SplitSimulationDataType createSplitSimulationDataType() {
        SplitSimulationDataTypeImpl splitSimulationDataType =
                new SplitSimulationDataTypeImpl();
        return splitSimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartSimulationDataType createStartSimulationDataType() {
        StartSimulationDataTypeImpl startSimulationDataType =
                new StartSimulationDataTypeImpl();
        return startSimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StructuredConditionType createStructuredConditionType() {
        StructuredConditionTypeImpl structuredConditionType =
                new StructuredConditionTypeImpl();
        return structuredConditionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeUnitCostType createTimeUnitCostType() {
        TimeUnitCostTypeImpl timeUnitCostType = new TimeUnitCostTypeImpl();
        return timeUnitCostType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitionSimulationDataType createTransitionSimulationDataType() {
        TransitionSimulationDataTypeImpl transitionSimulationDataType =
                new TransitionSimulationDataTypeImpl();
        return transitionSimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UniformRealDistribution createUniformRealDistribution() {
        UniformRealDistributionImpl uniformRealDistribution =
                new UniformRealDistributionImpl();
        return uniformRealDistribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkflowProcessSimulationDataType createWorkflowProcessSimulationDataType() {
        WorkflowProcessSimulationDataTypeImpl workflowProcessSimulationDataType =
                new WorkflowProcessSimulationDataTypeImpl();
        return workflowProcessSimulationDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NormalDistributionStrategyType createNormalDistributionStrategyType() {
        NormalDistributionStrategyTypeImpl normalDistributionStrategyType =
                new NormalDistributionStrategyTypeImpl();
        return normalDistributionStrategyType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopControlStrategy createLoopControlStrategyFromString(
            EDataType eDataType, String initialValue) {
        LoopControlStrategy result = LoopControlStrategy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLoopControlStrategyToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RealDistributionCategory createRealDistributionCategoryFromString(
            EDataType eDataType, String initialValue) {
        RealDistributionCategory result =
                RealDistributionCategory.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertRealDistributionCategoryToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeDisplayUnitType createTimeDisplayUnitTypeFromString(
            EDataType eDataType, String initialValue) {
        TimeDisplayUnitType result = TimeDisplayUnitType.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTimeDisplayUnitTypeToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BigInteger createInstancesTypeFromString(EDataType eDataType,
            String initialValue) {
        return (BigInteger) XMLTypeFactory.eINSTANCE
                .createFromString(XMLTypePackage.Literals.INTEGER, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertInstancesTypeToString(EDataType eDataType,
            Object instanceValue) {
        return XMLTypeFactory.eINSTANCE
                .convertToString(XMLTypePackage.Literals.INTEGER, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LoopControlStrategy createLoopControlStrategyObjectFromString(
            EDataType eDataType, String initialValue) {
        return createLoopControlStrategyFromString(SimulationPackage.Literals.LOOP_CONTROL_STRATEGY,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertLoopControlStrategyObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertLoopControlStrategyToString(SimulationPackage.Literals.LOOP_CONTROL_STRATEGY,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String createOperatorTypeFromString(EDataType eDataType,
            String initialValue) {
        return (String) XMLTypeFactory.eINSTANCE
                .createFromString(XMLTypePackage.Literals.STRING, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOperatorTypeToString(EDataType eDataType,
            Object instanceValue) {
        return XMLTypeFactory.eINSTANCE
                .convertToString(XMLTypePackage.Literals.STRING, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RealDistributionCategory createRealDistributionCategoryObjectFromString(
            EDataType eDataType, String initialValue) {
        return createRealDistributionCategoryFromString(SimulationPackage.Literals.REAL_DISTRIBUTION_CATEGORY,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertRealDistributionCategoryObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertRealDistributionCategoryToString(SimulationPackage.Literals.REAL_DISTRIBUTION_CATEGORY,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeDisplayUnitType createTimeDisplayUnitTypeObjectFromString(
            EDataType eDataType, String initialValue) {
        return createTimeDisplayUnitTypeFromString(SimulationPackage.Literals.TIME_DISPLAY_UNIT_TYPE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTimeDisplayUnitTypeObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertTimeDisplayUnitTypeToString(SimulationPackage.Literals.TIME_DISPLAY_UNIT_TYPE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimulationPackage getSimulationPackage() {
        return (SimulationPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SimulationPackage getPackage() {
        return SimulationPackage.eINSTANCE;
    }

} //SimulationFactoryImpl
