/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.simulation.SimulationFactory
 * @model kind="package"
 * @generated
 */
public interface SimulationPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "simulation"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/xpd/Simulation1.0.1"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "simulation"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SimulationPackage eINSTANCE =
            com.tibco.xpd.simulation.impl.SimulationPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl <em>Activity Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getActivitySimulationDataType()
     * @generated
     */
    int ACTIVITY_SIMULATION_DATA_TYPE = 0;

    /**
     * The feature id for the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SIMULATION_DATA_TYPE__DURATION = 0;

    /**
     * The feature id for the '<em><b>Display Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT = 1;

    /**
     * The feature id for the '<em><b>Loop Control</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL = 2;

    /**
     * The feature id for the '<em><b>Sla Maximum Delay</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY = 3;

    /**
     * The number of structural features of the '<em>Activity Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_SIMULATION_DATA_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.AbstractBasicDistribution <em>Abstract Basic Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.AbstractBasicDistribution
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getAbstractBasicDistribution()
     * @generated
     */
    int ABSTRACT_BASIC_DISTRIBUTION = 1;

    /**
     * The number of structural features of the '<em>Abstract Basic Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ConstantRealDistributionImpl <em>Constant Real Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ConstantRealDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getConstantRealDistribution()
     * @generated
     */
    int CONSTANT_REAL_DISTRIBUTION = 2;

    /**
     * The feature id for the '<em><b>Constant Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Constant Real Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONSTANT_REAL_DISTRIBUTION_FEATURE_COUNT =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.DocumentRootImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 3;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Activity Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA = 3;

    /**
     * The feature id for the '<em><b>Participant Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA = 4;

    /**
     * The feature id for the '<em><b>Split Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__SPLIT_SIMULATION_DATA = 5;

    /**
     * The feature id for the '<em><b>Start Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__START_SIMULATION_DATA = 6;

    /**
     * The feature id for the '<em><b>Transition Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA = 7;

    /**
     * The feature id for the '<em><b>Workflow Process Simulation Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA = 8;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 9;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl <em>Enum Based Expression Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getEnumBasedExpressionType()
     * @generated
     */
    int ENUM_BASED_EXPRESSION_TYPE = 4;

    /**
     * The feature id for the '<em><b>Enum Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE = 0;

    /**
     * The feature id for the '<em><b>Param Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME = 1;

    /**
     * The number of structural features of the '<em>Enum Based Expression Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUM_BASED_EXPRESSION_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl <em>Enumeration Value Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getEnumerationValueType()
     * @generated
     */
    int ENUMERATION_VALUE_TYPE = 5;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE_TYPE__DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE_TYPE__VALUE = 1;

    /**
     * The feature id for the '<em><b>Weighting Factor</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR = 2;

    /**
     * The number of structural features of the '<em>Enumeration Value Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ENUMERATION_VALUE_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ExponentialRealDistributionImpl <em>Exponential Real Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ExponentialRealDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExponentialRealDistribution()
     * @generated
     */
    int EXPONENTIAL_REAL_DISTRIBUTION = 6;

    /**
     * The feature id for the '<em><b>Mean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPONENTIAL_REAL_DISTRIBUTION__MEAN =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Exponential Real Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPONENTIAL_REAL_DISTRIBUTION_FEATURE_COUNT =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl <em>Expression Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ExpressionTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExpressionType()
     * @generated
     */
    int EXPRESSION_TYPE = 7;

    /**
     * The feature id for the '<em><b>Enum Based Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_TYPE__ENUM_BASED_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Script Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_TYPE__SCRIPT_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Structured Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_TYPE__STRUCTURED_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Default</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_TYPE__DEFAULT = 3;

    /**
     * The number of structural features of the '<em>Expression Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXPRESSION_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ExternalEmpiricalDistributionImpl <em>External Empirical Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ExternalEmpiricalDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExternalEmpiricalDistribution()
     * @generated
     */
    int EXTERNAL_EMPIRICAL_DISTRIBUTION = 8;

    /**
     * The feature id for the '<em><b>Reference</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_EMPIRICAL_DISTRIBUTION__REFERENCE =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_EMPIRICAL_DISTRIBUTION__TYPE =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>External Empirical Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EXTERNAL_EMPIRICAL_DISTRIBUTION_FEATURE_COUNT =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl <em>Loop Control Transition Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlTransitionType()
     * @generated
     */
    int LOOP_CONTROL_TRANSITION_TYPE = 9;

    /**
     * The feature id for the '<em><b>Decision Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY = 0;

    /**
     * The feature id for the '<em><b>To Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY = 1;

    /**
     * The number of structural features of the '<em>Loop Control Transition Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.LoopControlTypeImpl <em>Loop Control Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.LoopControlTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlType()
     * @generated
     */
    int LOOP_CONTROL_TYPE = 10;

    /**
     * The feature id for the '<em><b>Max Loop Count Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY = 0;

    /**
     * The feature id for the '<em><b>Normal Distribution Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY = 1;

    /**
     * The feature id for the '<em><b>Max Elapse Time Strategy</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY = 2;

    /**
     * The number of structural features of the '<em>Loop Control Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOP_CONTROL_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl <em>Max Elapse Time Strategy Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getMaxElapseTimeStrategyType()
     * @generated
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE = 11;

    /**
     * The feature id for the '<em><b>Decision Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE__DECISION_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY;

    /**
     * The feature id for the '<em><b>To Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE__TO_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY;

    /**
     * The feature id for the '<em><b>Display Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Max Elapse Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Max Elapse Time Strategy Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_ELAPSE_TIME_STRATEGY_TYPE_FEATURE_COUNT =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.MaxLoopCountStrategyTypeImpl <em>Max Loop Count Strategy Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.MaxLoopCountStrategyTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getMaxLoopCountStrategyType()
     * @generated
     */
    int MAX_LOOP_COUNT_STRATEGY_TYPE = 12;

    /**
     * The feature id for the '<em><b>Decision Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_LOOP_COUNT_STRATEGY_TYPE__DECISION_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY;

    /**
     * The feature id for the '<em><b>To Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_LOOP_COUNT_STRATEGY_TYPE__TO_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY;

    /**
     * The feature id for the '<em><b>Max Loop Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Max Loop Count Strategy Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAX_LOOP_COUNT_STRATEGY_TYPE_FEATURE_COUNT =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.NormalRealDistributionImpl <em>Normal Real Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.NormalRealDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getNormalRealDistribution()
     * @generated
     */
    int NORMAL_REAL_DISTRIBUTION = 13;

    /**
     * The feature id for the '<em><b>Mean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_REAL_DISTRIBUTION__MEAN =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Standard Deviation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_REAL_DISTRIBUTION__STANDARD_DEVIATION =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Normal Real Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_REAL_DISTRIBUTION_FEATURE_COUNT =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ParameterBasedDistributionImpl <em>Parameter Based Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ParameterBasedDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterBasedDistribution()
     * @generated
     */
    int PARAMETER_BASED_DISTRIBUTION = 14;

    /**
     * The feature id for the '<em><b>Parameter Dependent Distributions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS = 0;

    /**
     * The number of structural features of the '<em>Parameter Based Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_BASED_DISTRIBUTION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl <em>Parameter Dependent Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterDependentDistribution()
     * @generated
     */
    int PARAMETER_DEPENDENT_DISTRIBUTION = 15;

    /**
     * The feature id for the '<em><b>Basic Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION = 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION = 1;

    /**
     * The number of structural features of the '<em>Parameter Dependent Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DEPENDENT_DISTRIBUTION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ParameterDistributionImpl <em>Parameter Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ParameterDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterDistribution()
     * @generated
     */
    int PARAMETER_DISTRIBUTION = 16;

    /**
     * The feature id for the '<em><b>Enumeration Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DISTRIBUTION__ENUMERATION_VALUE = 0;

    /**
     * The feature id for the '<em><b>Parameter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DISTRIBUTION__PARAMETER_ID = 1;

    /**
     * The number of structural features of the '<em>Parameter Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_DISTRIBUTION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl <em>Participant Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParticipantSimulationDataType()
     * @generated
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE = 17;

    /**
     * The feature id for the '<em><b>Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES = 0;

    /**
     * The feature id for the '<em><b>Time Unit Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST = 1;

    /**
     * The feature id for the '<em><b>Sla Minimum Utilisation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION = 2;

    /**
     * The feature id for the '<em><b>Sla Maximum Utilisation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION = 3;

    /**
     * The number of structural features of the '<em>Participant Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARTICIPANT_SIMULATION_DATA_TYPE_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl <em>Real Distribution Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSimulationRealDistributionType()
     * @generated
     */
    int SIMULATION_REAL_DISTRIBUTION_TYPE = 18;

    /**
     * The feature id for the '<em><b>Basic Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION = 0;

    /**
     * The feature id for the '<em><b>Parameter Based Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION = 1;

    /**
     * The number of structural features of the '<em>Real Distribution Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIMULATION_REAL_DISTRIBUTION_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.SplitParameterTypeImpl <em>Split Parameter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.SplitParameterTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSplitParameterType()
     * @generated
     */
    int SPLIT_PARAMETER_TYPE = 19;

    /**
     * The feature id for the '<em><b>Parameter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_PARAMETER_TYPE__PARAMETER_ID = 0;

    /**
     * The number of structural features of the '<em>Split Parameter Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_PARAMETER_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl <em>Split Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSplitSimulationDataType()
     * @generated
     */
    int SPLIT_SIMULATION_DATA_TYPE = 20;

    /**
     * The feature id for the '<em><b>Parameter Determined Split</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT = 0;

    /**
     * The feature id for the '<em><b>Split Parameter</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER = 1;

    /**
     * The number of structural features of the '<em>Split Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPLIT_SIMULATION_DATA_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl <em>Time Unit Cost Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeUnitCostType()
     * @generated
     */
    int TIME_UNIT_COST_TYPE = 23;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl <em>Transition Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTransitionSimulationDataType()
     * @generated
     */
    int TRANSITION_SIMULATION_DATA_TYPE = 24;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.WorkflowProcessSimulationDataTypeImpl <em>Workflow Process Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.WorkflowProcessSimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getWorkflowProcessSimulationDataType()
     * @generated
     */
    int WORKFLOW_PROCESS_SIMULATION_DATA_TYPE = 27;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl <em>Start Simulation Data Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getStartSimulationDataType()
     * @generated
     */
    int START_SIMULATION_DATA_TYPE = 21;

    /**
     * The feature id for the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_SIMULATION_DATA_TYPE__DURATION = 0;

    /**
     * The feature id for the '<em><b>Display Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT = 1;

    /**
     * The feature id for the '<em><b>Number Of Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES = 2;

    /**
     * The number of structural features of the '<em>Start Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int START_SIMULATION_DATA_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl <em>Structured Condition Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getStructuredConditionType()
     * @generated
     */
    int STRUCTURED_CONDITION_TYPE = 22;

    /**
     * The feature id for the '<em><b>Parameter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_CONDITION_TYPE__PARAMETER_ID = 0;

    /**
     * The feature id for the '<em><b>Operator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_CONDITION_TYPE__OPERATOR = 1;

    /**
     * The feature id for the '<em><b>Parameter Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE = 2;

    /**
     * The number of structural features of the '<em>Structured Condition Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STRUCTURED_CONDITION_TYPE_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_UNIT_COST_TYPE__COST = 0;

    /**
     * The feature id for the '<em><b>Time Display Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT = 1;

    /**
     * The number of structural features of the '<em>Time Unit Cost Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_UNIT_COST_TYPE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Parameter Determined Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION = 0;

    /**
     * The feature id for the '<em><b>Structured Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION = 1;

    /**
     * The number of structural features of the '<em>Transition Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TRANSITION_SIMULATION_DATA_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.UniformRealDistributionImpl <em>Uniform Real Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.UniformRealDistributionImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getUniformRealDistribution()
     * @generated
     */
    int UNIFORM_REAL_DISTRIBUTION = 26;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.impl.NormalDistributionStrategyTypeImpl <em>Normal Distribution Strategy Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.impl.NormalDistributionStrategyTypeImpl
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getNormalDistributionStrategyType()
     * @generated
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE = 25;

    /**
     * The feature id for the '<em><b>Decision Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE__DECISION_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY;

    /**
     * The feature id for the '<em><b>To Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE__TO_ACTIVITY =
            LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY;

    /**
     * The feature id for the '<em><b>Mean</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE__MEAN =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Standard Deviation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE__STANDARD_DEVIATION =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Normal Distribution Strategy Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NORMAL_DISTRIBUTION_STRATEGY_TYPE_FEATURE_COUNT =
            LOOP_CONTROL_TRANSITION_TYPE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Lower Border</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Upper Border</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Uniform Real Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIFORM_REAL_DISTRIBUTION_FEATURE_COUNT =
            ABSTRACT_BASIC_DISTRIBUTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Parameter Distribution</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION = 0;

    /**
     * The number of structural features of the '<em>Workflow Process Simulation Data Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKFLOW_PROCESS_SIMULATION_DATA_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.LoopControlStrategy <em>Loop Control Strategy</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.LoopControlStrategy
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlStrategy()
     * @generated
     */
    int LOOP_CONTROL_STRATEGY = 28;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.RealDistributionCategory <em>Real Distribution Category</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.RealDistributionCategory
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getRealDistributionCategory()
     * @generated
     */
    int REAL_DISTRIBUTION_CATEGORY = 29;

    /**
     * The meta object id for the '{@link com.tibco.xpd.simulation.TimeDisplayUnitType <em>Time Display Unit Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeDisplayUnitType()
     * @generated
     */
    int TIME_DISPLAY_UNIT_TYPE = 30;

    /**
     * The meta object id for the '<em>Instances Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.math.BigInteger
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getInstancesType()
     * @generated
     */
    int INSTANCES_TYPE = 31;

    /**
     * The meta object id for the '<em>Loop Control Strategy Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.LoopControlStrategy
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlStrategyObject()
     * @generated
     */
    int LOOP_CONTROL_STRATEGY_OBJECT = 32;

    /**
     * The meta object id for the '<em>Operator Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getOperatorType()
     * @generated
     */
    int OPERATOR_TYPE = 33;

    /**
     * The meta object id for the '<em>Real Distribution Category Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.RealDistributionCategory
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getRealDistributionCategoryObject()
     * @generated
     */
    int REAL_DISTRIBUTION_CATEGORY_OBJECT = 34;

    /**
     * The meta object id for the '<em>Time Display Unit Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeDisplayUnitTypeObject()
     * @generated
     */
    int TIME_DISPLAY_UNIT_TYPE_OBJECT = 35;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ActivitySimulationDataType <em>Activity Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType
     * @generated
     */
    EClass getActivitySimulationDataType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDuration <em>Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Duration</em>'.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType#getDuration()
     * @see #getActivitySimulationDataType()
     * @generated
     */
    EReference getActivitySimulationDataType_Duration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Time Unit</em>'.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType#getDisplayTimeUnit()
     * @see #getActivitySimulationDataType()
     * @generated
     */
    EAttribute getActivitySimulationDataType_DisplayTimeUnit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getLoopControl <em>Loop Control</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Loop Control</em>'.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType#getLoopControl()
     * @see #getActivitySimulationDataType()
     * @generated
     */
    EReference getActivitySimulationDataType_LoopControl();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ActivitySimulationDataType#getSlaMaximumDelay <em>Sla Maximum Delay</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sla Maximum Delay</em>'.
     * @see com.tibco.xpd.simulation.ActivitySimulationDataType#getSlaMaximumDelay()
     * @see #getActivitySimulationDataType()
     * @generated
     */
    EAttribute getActivitySimulationDataType_SlaMaximumDelay();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.AbstractBasicDistribution <em>Abstract Basic Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Abstract Basic Distribution</em>'.
     * @see com.tibco.xpd.simulation.AbstractBasicDistribution
     * @generated
     */
    EClass getAbstractBasicDistribution();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ConstantRealDistribution <em>Constant Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Constant Real Distribution</em>'.
     * @see com.tibco.xpd.simulation.ConstantRealDistribution
     * @generated
     */
    EClass getConstantRealDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue <em>Constant Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Constant Value</em>'.
     * @see com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue()
     * @see #getConstantRealDistribution()
     * @generated
     */
    EAttribute getConstantRealDistribution_ConstantValue();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.simulation.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.simulation.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.simulation.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getActivitySimulationData <em>Activity Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Activity Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getActivitySimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ActivitySimulationData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getParticipantSimulationData <em>Participant Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Participant Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getParticipantSimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ParticipantSimulationData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getSplitSimulationData <em>Split Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Split Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getSplitSimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_SplitSimulationData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getWorkflowProcessSimulationData <em>Workflow Process Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Workflow Process Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getWorkflowProcessSimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WorkflowProcessSimulationData();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.EnumBasedExpressionType <em>Enum Based Expression Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enum Based Expression Type</em>'.
     * @see com.tibco.xpd.simulation.EnumBasedExpressionType
     * @generated
     */
    EClass getEnumBasedExpressionType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getEnumValue <em>Enum Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enum Value</em>'.
     * @see com.tibco.xpd.simulation.EnumBasedExpressionType#getEnumValue()
     * @see #getEnumBasedExpressionType()
     * @generated
     */
    EAttribute getEnumBasedExpressionType_EnumValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.EnumBasedExpressionType#getParamName <em>Param Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Param Name</em>'.
     * @see com.tibco.xpd.simulation.EnumBasedExpressionType#getParamName()
     * @see #getEnumBasedExpressionType()
     * @generated
     */
    EAttribute getEnumBasedExpressionType_ParamName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getStartSimulationData <em>Start Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Start Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getStartSimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_StartSimulationData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.DocumentRoot#getTransitionSimulationData <em>Transition Simulation Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Transition Simulation Data</em>'.
     * @see com.tibco.xpd.simulation.DocumentRoot#getTransitionSimulationData()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_TransitionSimulationData();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.EnumerationValueType <em>Enumeration Value Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Enumeration Value Type</em>'.
     * @see com.tibco.xpd.simulation.EnumerationValueType
     * @generated
     */
    EClass getEnumerationValueType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.EnumerationValueType#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see com.tibco.xpd.simulation.EnumerationValueType#getDescription()
     * @see #getEnumerationValueType()
     * @generated
     */
    EAttribute getEnumerationValueType_Description();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.EnumerationValueType#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.simulation.EnumerationValueType#getValue()
     * @see #getEnumerationValueType()
     * @generated
     */
    EAttribute getEnumerationValueType_Value();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor <em>Weighting Factor</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Weighting Factor</em>'.
     * @see com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor()
     * @see #getEnumerationValueType()
     * @generated
     */
    EAttribute getEnumerationValueType_WeightingFactor();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ExponentialRealDistribution <em>Exponential Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Exponential Real Distribution</em>'.
     * @see com.tibco.xpd.simulation.ExponentialRealDistribution
     * @generated
     */
    EClass getExponentialRealDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ExponentialRealDistribution#getMean <em>Mean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mean</em>'.
     * @see com.tibco.xpd.simulation.ExponentialRealDistribution#getMean()
     * @see #getExponentialRealDistribution()
     * @generated
     */
    EAttribute getExponentialRealDistribution_Mean();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ExpressionType <em>Expression Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Expression Type</em>'.
     * @see com.tibco.xpd.simulation.ExpressionType
     * @generated
     */
    EClass getExpressionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ExpressionType#getEnumBasedExpression <em>Enum Based Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Enum Based Expression</em>'.
     * @see com.tibco.xpd.simulation.ExpressionType#getEnumBasedExpression()
     * @see #getExpressionType()
     * @generated
     */
    EReference getExpressionType_EnumBasedExpression();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ExpressionType#getScriptExpression <em>Script Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script Expression</em>'.
     * @see com.tibco.xpd.simulation.ExpressionType#getScriptExpression()
     * @see #getExpressionType()
     * @generated
     */
    EAttribute getExpressionType_ScriptExpression();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ExpressionType#getStructuredExpression <em>Structured Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Structured Expression</em>'.
     * @see com.tibco.xpd.simulation.ExpressionType#getStructuredExpression()
     * @see #getExpressionType()
     * @generated
     */
    EReference getExpressionType_StructuredExpression();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ExpressionType#getDefault <em>Default</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Default</em>'.
     * @see com.tibco.xpd.simulation.ExpressionType#getDefault()
     * @see #getExpressionType()
     * @generated
     */
    EReference getExpressionType_Default();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution <em>External Empirical Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>External Empirical Distribution</em>'.
     * @see com.tibco.xpd.simulation.ExternalEmpiricalDistribution
     * @generated
     */
    EClass getExternalEmpiricalDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getReference <em>Reference</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference</em>'.
     * @see com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getReference()
     * @see #getExternalEmpiricalDistribution()
     * @generated
     */
    EAttribute getExternalEmpiricalDistribution_Reference();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getType()
     * @see #getExternalEmpiricalDistribution()
     * @generated
     */
    EAttribute getExternalEmpiricalDistribution_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.LoopControlTransitionType <em>Loop Control Transition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loop Control Transition Type</em>'.
     * @see com.tibco.xpd.simulation.LoopControlTransitionType
     * @generated
     */
    EClass getLoopControlTransitionType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.LoopControlTransitionType#getDecisionActivity <em>Decision Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Decision Activity</em>'.
     * @see com.tibco.xpd.simulation.LoopControlTransitionType#getDecisionActivity()
     * @see #getLoopControlTransitionType()
     * @generated
     */
    EAttribute getLoopControlTransitionType_DecisionActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.LoopControlTransitionType#getToActivity <em>To Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>To Activity</em>'.
     * @see com.tibco.xpd.simulation.LoopControlTransitionType#getToActivity()
     * @see #getLoopControlTransitionType()
     * @generated
     */
    EAttribute getLoopControlTransitionType_ToActivity();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.LoopControlType <em>Loop Control Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Loop Control Type</em>'.
     * @see com.tibco.xpd.simulation.LoopControlType
     * @generated
     */
    EClass getLoopControlType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.LoopControlType#getMaxLoopCountStrategy <em>Max Loop Count Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Max Loop Count Strategy</em>'.
     * @see com.tibco.xpd.simulation.LoopControlType#getMaxLoopCountStrategy()
     * @see #getLoopControlType()
     * @generated
     */
    EReference getLoopControlType_MaxLoopCountStrategy();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.LoopControlType#getNormalDistributionStrategy <em>Normal Distribution Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Normal Distribution Strategy</em>'.
     * @see com.tibco.xpd.simulation.LoopControlType#getNormalDistributionStrategy()
     * @see #getLoopControlType()
     * @generated
     */
    EReference getLoopControlType_NormalDistributionStrategy();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.LoopControlType#getMaxElapseTimeStrategy <em>Max Elapse Time Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Max Elapse Time Strategy</em>'.
     * @see com.tibco.xpd.simulation.LoopControlType#getMaxElapseTimeStrategy()
     * @see #getLoopControlType()
     * @generated
     */
    EReference getLoopControlType_MaxElapseTimeStrategy();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType <em>Max Elapse Time Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Max Elapse Time Strategy Type</em>'.
     * @see com.tibco.xpd.simulation.MaxElapseTimeStrategyType
     * @generated
     */
    EClass getMaxElapseTimeStrategyType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit <em>Display Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Time Unit</em>'.
     * @see com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getDisplayTimeUnit()
     * @see #getMaxElapseTimeStrategyType()
     * @generated
     */
    EAttribute getMaxElapseTimeStrategyType_DisplayTimeUnit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime <em>Max Elapse Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Elapse Time</em>'.
     * @see com.tibco.xpd.simulation.MaxElapseTimeStrategyType#getMaxElapseTime()
     * @see #getMaxElapseTimeStrategyType()
     * @generated
     */
    EAttribute getMaxElapseTimeStrategyType_MaxElapseTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType <em>Max Loop Count Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Max Loop Count Strategy Type</em>'.
     * @see com.tibco.xpd.simulation.MaxLoopCountStrategyType
     * @generated
     */
    EClass getMaxLoopCountStrategyType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount <em>Max Loop Count</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Loop Count</em>'.
     * @see com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount()
     * @see #getMaxLoopCountStrategyType()
     * @generated
     */
    EAttribute getMaxLoopCountStrategyType_MaxLoopCount();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.NormalRealDistribution <em>Normal Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Normal Real Distribution</em>'.
     * @see com.tibco.xpd.simulation.NormalRealDistribution
     * @generated
     */
    EClass getNormalRealDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.NormalRealDistribution#getMean <em>Mean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mean</em>'.
     * @see com.tibco.xpd.simulation.NormalRealDistribution#getMean()
     * @see #getNormalRealDistribution()
     * @generated
     */
    EAttribute getNormalRealDistribution_Mean();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.NormalRealDistribution#getStandardDeviation <em>Standard Deviation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Standard Deviation</em>'.
     * @see com.tibco.xpd.simulation.NormalRealDistribution#getStandardDeviation()
     * @see #getNormalRealDistribution()
     * @generated
     */
    EAttribute getNormalRealDistribution_StandardDeviation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ParameterBasedDistribution <em>Parameter Based Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Based Distribution</em>'.
     * @see com.tibco.xpd.simulation.ParameterBasedDistribution
     * @generated
     */
    EClass getParameterBasedDistribution();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.ParameterBasedDistribution#getParameterDependentDistributions <em>Parameter Dependent Distributions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameter Dependent Distributions</em>'.
     * @see com.tibco.xpd.simulation.ParameterBasedDistribution#getParameterDependentDistributions()
     * @see #getParameterBasedDistribution()
     * @generated
     */
    EReference getParameterBasedDistribution_ParameterDependentDistributions();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ParameterDependentDistribution <em>Parameter Dependent Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Dependent Distribution</em>'.
     * @see com.tibco.xpd.simulation.ParameterDependentDistribution
     * @generated
     */
    EClass getParameterDependentDistribution();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getBasicDistribution <em>Basic Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Distribution</em>'.
     * @see com.tibco.xpd.simulation.ParameterDependentDistribution#getBasicDistribution()
     * @see #getParameterDependentDistribution()
     * @generated
     */
    EReference getParameterDependentDistribution_BasicDistribution();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ParameterDependentDistribution#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Expression</em>'.
     * @see com.tibco.xpd.simulation.ParameterDependentDistribution#getExpression()
     * @see #getParameterDependentDistribution()
     * @generated
     */
    EReference getParameterDependentDistribution_Expression();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ParameterDistribution <em>Parameter Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Distribution</em>'.
     * @see com.tibco.xpd.simulation.ParameterDistribution
     * @generated
     */
    EClass getParameterDistribution();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.ParameterDistribution#getEnumerationValue <em>Enumeration Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Enumeration Value</em>'.
     * @see com.tibco.xpd.simulation.ParameterDistribution#getEnumerationValue()
     * @see #getParameterDistribution()
     * @generated
     */
    EReference getParameterDistribution_EnumerationValue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ParameterDistribution#getParameterId <em>Parameter Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Id</em>'.
     * @see com.tibco.xpd.simulation.ParameterDistribution#getParameterId()
     * @see #getParameterDistribution()
     * @generated
     */
    EAttribute getParameterDistribution_ParameterId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType <em>Participant Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participant Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType
     * @generated
     */
    EClass getParticipantSimulationDataType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getInstances <em>Instances</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Instances</em>'.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType#getInstances()
     * @see #getParticipantSimulationDataType()
     * @generated
     */
    EAttribute getParticipantSimulationDataType_Instances();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getTimeUnitCost <em>Time Unit Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Unit Cost</em>'.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType#getTimeUnitCost()
     * @see #getParticipantSimulationDataType()
     * @generated
     */
    EReference getParticipantSimulationDataType_TimeUnitCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMinimumUtilisation <em>Sla Minimum Utilisation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sla Minimum Utilisation</em>'.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMinimumUtilisation()
     * @see #getParticipantSimulationDataType()
     * @generated
     */
    EAttribute getParticipantSimulationDataType_SlaMinimumUtilisation();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMaximumUtilisation <em>Sla Maximum Utilisation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sla Maximum Utilisation</em>'.
     * @see com.tibco.xpd.simulation.ParticipantSimulationDataType#getSlaMaximumUtilisation()
     * @see #getParticipantSimulationDataType()
     * @generated
     */
    EAttribute getParticipantSimulationDataType_SlaMaximumUtilisation();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.SimulationRealDistributionType <em>Real Distribution Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Real Distribution Type</em>'.
     * @see com.tibco.xpd.simulation.SimulationRealDistributionType
     * @generated
     */
    EClass getSimulationRealDistributionType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getBasicDistribution <em>Basic Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Basic Distribution</em>'.
     * @see com.tibco.xpd.simulation.SimulationRealDistributionType#getBasicDistribution()
     * @see #getSimulationRealDistributionType()
     * @generated
     */
    EReference getSimulationRealDistributionType_BasicDistribution();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.SimulationRealDistributionType#getParameterBasedDistribution <em>Parameter Based Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Parameter Based Distribution</em>'.
     * @see com.tibco.xpd.simulation.SimulationRealDistributionType#getParameterBasedDistribution()
     * @see #getSimulationRealDistributionType()
     * @generated
     */
    EReference getSimulationRealDistributionType_ParameterBasedDistribution();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.SplitParameterType <em>Split Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Split Parameter Type</em>'.
     * @see com.tibco.xpd.simulation.SplitParameterType
     * @generated
     */
    EClass getSplitParameterType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.SplitParameterType#getParameterId <em>Parameter Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Id</em>'.
     * @see com.tibco.xpd.simulation.SplitParameterType#getParameterId()
     * @see #getSplitParameterType()
     * @generated
     */
    EAttribute getSplitParameterType_ParameterId();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.SplitSimulationDataType <em>Split Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Split Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.SplitSimulationDataType
     * @generated
     */
    EClass getSplitSimulationDataType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit <em>Parameter Determined Split</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Determined Split</em>'.
     * @see com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit()
     * @see #getSplitSimulationDataType()
     * @generated
     */
    EAttribute getSplitSimulationDataType_ParameterDeterminedSplit();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.SplitSimulationDataType#getSplitParameter <em>Split Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Split Parameter</em>'.
     * @see com.tibco.xpd.simulation.SplitSimulationDataType#getSplitParameter()
     * @see #getSplitSimulationDataType()
     * @generated
     */
    EReference getSplitSimulationDataType_SplitParameter();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.TimeUnitCostType <em>Time Unit Cost Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Time Unit Cost Type</em>'.
     * @see com.tibco.xpd.simulation.TimeUnitCostType
     * @generated
     */
    EClass getTimeUnitCostType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.TimeUnitCostType#getCost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cost</em>'.
     * @see com.tibco.xpd.simulation.TimeUnitCostType#getCost()
     * @see #getTimeUnitCostType()
     * @generated
     */
    EAttribute getTimeUnitCostType_Cost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit <em>Time Display Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time Display Unit</em>'.
     * @see com.tibco.xpd.simulation.TimeUnitCostType#getTimeDisplayUnit()
     * @see #getTimeUnitCostType()
     * @generated
     */
    EAttribute getTimeUnitCostType_TimeDisplayUnit();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.TransitionSimulationDataType <em>Transition Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Transition Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.TransitionSimulationDataType
     * @generated
     */
    EClass getTransitionSimulationDataType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Determined Condition</em>'.
     * @see com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition()
     * @see #getTransitionSimulationDataType()
     * @generated
     */
    EAttribute getTransitionSimulationDataType_ParameterDeterminedCondition();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#getStructuredCondition <em>Structured Condition</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Structured Condition</em>'.
     * @see com.tibco.xpd.simulation.TransitionSimulationDataType#getStructuredCondition()
     * @see #getTransitionSimulationDataType()
     * @generated
     */
    EReference getTransitionSimulationDataType_StructuredCondition();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.UniformRealDistribution <em>Uniform Real Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Uniform Real Distribution</em>'.
     * @see com.tibco.xpd.simulation.UniformRealDistribution
     * @generated
     */
    EClass getUniformRealDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.UniformRealDistribution#getLowerBorder <em>Lower Border</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lower Border</em>'.
     * @see com.tibco.xpd.simulation.UniformRealDistribution#getLowerBorder()
     * @see #getUniformRealDistribution()
     * @generated
     */
    EAttribute getUniformRealDistribution_LowerBorder();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.UniformRealDistribution#getUpperBorder <em>Upper Border</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Upper Border</em>'.
     * @see com.tibco.xpd.simulation.UniformRealDistribution#getUpperBorder()
     * @see #getUniformRealDistribution()
     * @generated
     */
    EAttribute getUniformRealDistribution_UpperBorder();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.WorkflowProcessSimulationDataType <em>Workflow Process Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Workflow Process Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.WorkflowProcessSimulationDataType
     * @generated
     */
    EClass getWorkflowProcessSimulationDataType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.WorkflowProcessSimulationDataType#getParameterDistribution <em>Parameter Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameter Distribution</em>'.
     * @see com.tibco.xpd.simulation.WorkflowProcessSimulationDataType#getParameterDistribution()
     * @see #getWorkflowProcessSimulationDataType()
     * @generated
     */
    EReference getWorkflowProcessSimulationDataType_ParameterDistribution();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.NormalDistributionStrategyType <em>Normal Distribution Strategy Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Normal Distribution Strategy Type</em>'.
     * @see com.tibco.xpd.simulation.NormalDistributionStrategyType
     * @generated
     */
    EClass getNormalDistributionStrategyType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.NormalDistributionStrategyType#getMean <em>Mean</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mean</em>'.
     * @see com.tibco.xpd.simulation.NormalDistributionStrategyType#getMean()
     * @see #getNormalDistributionStrategyType()
     * @generated
     */
    EAttribute getNormalDistributionStrategyType_Mean();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.NormalDistributionStrategyType#getStandardDeviation <em>Standard Deviation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Standard Deviation</em>'.
     * @see com.tibco.xpd.simulation.NormalDistributionStrategyType#getStandardDeviation()
     * @see #getNormalDistributionStrategyType()
     * @generated
     */
    EAttribute getNormalDistributionStrategyType_StandardDeviation();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.simulation.LoopControlStrategy <em>Loop Control Strategy</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Loop Control Strategy</em>'.
     * @see com.tibco.xpd.simulation.LoopControlStrategy
     * @generated
     */
    EEnum getLoopControlStrategy();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.StartSimulationDataType <em>Start Simulation Data Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Start Simulation Data Type</em>'.
     * @see com.tibco.xpd.simulation.StartSimulationDataType
     * @generated
     */
    EClass getStartSimulationDataType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases <em>Number Of Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Number Of Cases</em>'.
     * @see com.tibco.xpd.simulation.StartSimulationDataType#getNumberOfCases()
     * @see #getStartSimulationDataType()
     * @generated
     */
    EAttribute getStartSimulationDataType_NumberOfCases();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.simulation.StructuredConditionType <em>Structured Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Structured Condition Type</em>'.
     * @see com.tibco.xpd.simulation.StructuredConditionType
     * @generated
     */
    EClass getStructuredConditionType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterId <em>Parameter Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Id</em>'.
     * @see com.tibco.xpd.simulation.StructuredConditionType#getParameterId()
     * @see #getStructuredConditionType()
     * @generated
     */
    EAttribute getStructuredConditionType_ParameterId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.StructuredConditionType#getOperator <em>Operator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Operator</em>'.
     * @see com.tibco.xpd.simulation.StructuredConditionType#getOperator()
     * @see #getStructuredConditionType()
     * @generated
     */
    EAttribute getStructuredConditionType_Operator();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.StructuredConditionType#getParameterValue <em>Parameter Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter Value</em>'.
     * @see com.tibco.xpd.simulation.StructuredConditionType#getParameterValue()
     * @see #getStructuredConditionType()
     * @generated
     */
    EAttribute getStructuredConditionType_ParameterValue();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDuration <em>Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Duration</em>'.
     * @see com.tibco.xpd.simulation.StartSimulationDataType#getDuration()
     * @see #getStartSimulationDataType()
     * @generated
     */
    EReference getStartSimulationDataType_Duration();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit <em>Display Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Time Unit</em>'.
     * @see com.tibco.xpd.simulation.StartSimulationDataType#getDisplayTimeUnit()
     * @see #getStartSimulationDataType()
     * @generated
     */
    EAttribute getStartSimulationDataType_DisplayTimeUnit();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.simulation.RealDistributionCategory <em>Real Distribution Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Real Distribution Category</em>'.
     * @see com.tibco.xpd.simulation.RealDistributionCategory
     * @generated
     */
    EEnum getRealDistributionCategory();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.simulation.TimeDisplayUnitType <em>Time Display Unit Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Time Display Unit Type</em>'.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @generated
     */
    EEnum getTimeDisplayUnitType();

    /**
     * Returns the meta object for data type '{@link java.math.BigInteger <em>Instances Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Instances Type</em>'.
     * @see java.math.BigInteger
     * @model instanceClass="java.math.BigInteger"
     *        extendedMetaData="name='Instances_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#integer' minInclusive='0'"
     * @generated
     */
    EDataType getInstancesType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.simulation.LoopControlStrategy <em>Loop Control Strategy Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Loop Control Strategy Object</em>'.
     * @see com.tibco.xpd.simulation.LoopControlStrategy
     * @model instanceClass="com.tibco.xpd.simulation.LoopControlStrategy"
     *        extendedMetaData="name='LoopControlStrategy:Object' baseType='LoopControlStrategy'"
     * @generated
     */
    EDataType getLoopControlStrategyObject();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Operator Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Operator Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     *        extendedMetaData="name='Operator_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string' enumeration='='"
     * @generated
     */
    EDataType getOperatorType();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.simulation.RealDistributionCategory <em>Real Distribution Category Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Real Distribution Category Object</em>'.
     * @see com.tibco.xpd.simulation.RealDistributionCategory
     * @model instanceClass="com.tibco.xpd.simulation.RealDistributionCategory"
     *        extendedMetaData="name='RealDistributionCategory:Object' baseType='RealDistributionCategory'"
     * @generated
     */
    EDataType getRealDistributionCategoryObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.simulation.TimeDisplayUnitType <em>Time Display Unit Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Time Display Unit Type Object</em>'.
     * @see com.tibco.xpd.simulation.TimeDisplayUnitType
     * @model instanceClass="com.tibco.xpd.simulation.TimeDisplayUnitType"
     *        extendedMetaData="name='TimeDisplayUnitType:Object' baseType='TimeDisplayUnitType'"
     * @generated
     */
    EDataType getTimeDisplayUnitTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SimulationFactory getSimulationFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl <em>Activity Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ActivitySimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getActivitySimulationDataType()
         * @generated
         */
        EClass ACTIVITY_SIMULATION_DATA_TYPE =
                eINSTANCE.getActivitySimulationDataType();

        /**
         * The meta object literal for the '<em><b>Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_SIMULATION_DATA_TYPE__DURATION =
                eINSTANCE.getActivitySimulationDataType_Duration();

        /**
         * The meta object literal for the '<em><b>Display Time Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT =
                eINSTANCE.getActivitySimulationDataType_DisplayTimeUnit();

        /**
         * The meta object literal for the '<em><b>Loop Control</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY_SIMULATION_DATA_TYPE__LOOP_CONTROL =
                eINSTANCE.getActivitySimulationDataType_LoopControl();

        /**
         * The meta object literal for the '<em><b>Sla Maximum Delay</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY_SIMULATION_DATA_TYPE__SLA_MAXIMUM_DELAY =
                eINSTANCE.getActivitySimulationDataType_SlaMaximumDelay();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.AbstractBasicDistribution <em>Abstract Basic Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.AbstractBasicDistribution
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getAbstractBasicDistribution()
         * @generated
         */
        EClass ABSTRACT_BASIC_DISTRIBUTION =
                eINSTANCE.getAbstractBasicDistribution();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ConstantRealDistributionImpl <em>Constant Real Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ConstantRealDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getConstantRealDistribution()
         * @generated
         */
        EClass CONSTANT_REAL_DISTRIBUTION =
                eINSTANCE.getConstantRealDistribution();

        /**
         * The meta object literal for the '<em><b>Constant Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONSTANT_REAL_DISTRIBUTION__CONSTANT_VALUE =
                eINSTANCE.getConstantRealDistribution_ConstantValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.DocumentRootImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP =
                eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION =
                eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Activity Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_ActivitySimulationData();

        /**
         * The meta object literal for the '<em><b>Participant Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_ParticipantSimulationData();

        /**
         * The meta object literal for the '<em><b>Split Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__SPLIT_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_SplitSimulationData();

        /**
         * The meta object literal for the '<em><b>Start Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__START_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_StartSimulationData();

        /**
         * The meta object literal for the '<em><b>Transition Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_TransitionSimulationData();

        /**
         * The meta object literal for the '<em><b>Workflow Process Simulation Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA =
                eINSTANCE.getDocumentRoot_WorkflowProcessSimulationData();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl <em>Enum Based Expression Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.EnumBasedExpressionTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getEnumBasedExpressionType()
         * @generated
         */
        EClass ENUM_BASED_EXPRESSION_TYPE =
                eINSTANCE.getEnumBasedExpressionType();

        /**
         * The meta object literal for the '<em><b>Enum Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUM_BASED_EXPRESSION_TYPE__ENUM_VALUE =
                eINSTANCE.getEnumBasedExpressionType_EnumValue();

        /**
         * The meta object literal for the '<em><b>Param Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUM_BASED_EXPRESSION_TYPE__PARAM_NAME =
                eINSTANCE.getEnumBasedExpressionType_ParamName();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl <em>Enumeration Value Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.EnumerationValueTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getEnumerationValueType()
         * @generated
         */
        EClass ENUMERATION_VALUE_TYPE = eINSTANCE.getEnumerationValueType();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUMERATION_VALUE_TYPE__DESCRIPTION =
                eINSTANCE.getEnumerationValueType_Description();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUMERATION_VALUE_TYPE__VALUE =
                eINSTANCE.getEnumerationValueType_Value();

        /**
         * The meta object literal for the '<em><b>Weighting Factor</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ENUMERATION_VALUE_TYPE__WEIGHTING_FACTOR =
                eINSTANCE.getEnumerationValueType_WeightingFactor();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ExponentialRealDistributionImpl <em>Exponential Real Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ExponentialRealDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExponentialRealDistribution()
         * @generated
         */
        EClass EXPONENTIAL_REAL_DISTRIBUTION =
                eINSTANCE.getExponentialRealDistribution();

        /**
         * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPONENTIAL_REAL_DISTRIBUTION__MEAN =
                eINSTANCE.getExponentialRealDistribution_Mean();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ExpressionTypeImpl <em>Expression Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ExpressionTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExpressionType()
         * @generated
         */
        EClass EXPRESSION_TYPE = eINSTANCE.getExpressionType();

        /**
         * The meta object literal for the '<em><b>Enum Based Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXPRESSION_TYPE__ENUM_BASED_EXPRESSION =
                eINSTANCE.getExpressionType_EnumBasedExpression();

        /**
         * The meta object literal for the '<em><b>Script Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXPRESSION_TYPE__SCRIPT_EXPRESSION =
                eINSTANCE.getExpressionType_ScriptExpression();

        /**
         * The meta object literal for the '<em><b>Structured Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXPRESSION_TYPE__STRUCTURED_EXPRESSION =
                eINSTANCE.getExpressionType_StructuredExpression();

        /**
         * The meta object literal for the '<em><b>Default</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EXPRESSION_TYPE__DEFAULT =
                eINSTANCE.getExpressionType_Default();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ExternalEmpiricalDistributionImpl <em>External Empirical Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ExternalEmpiricalDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getExternalEmpiricalDistribution()
         * @generated
         */
        EClass EXTERNAL_EMPIRICAL_DISTRIBUTION =
                eINSTANCE.getExternalEmpiricalDistribution();

        /**
         * The meta object literal for the '<em><b>Reference</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_EMPIRICAL_DISTRIBUTION__REFERENCE =
                eINSTANCE.getExternalEmpiricalDistribution_Reference();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EXTERNAL_EMPIRICAL_DISTRIBUTION__TYPE =
                eINSTANCE.getExternalEmpiricalDistribution_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl <em>Loop Control Transition Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.LoopControlTransitionTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlTransitionType()
         * @generated
         */
        EClass LOOP_CONTROL_TRANSITION_TYPE =
                eINSTANCE.getLoopControlTransitionType();

        /**
         * The meta object literal for the '<em><b>Decision Activity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_CONTROL_TRANSITION_TYPE__DECISION_ACTIVITY =
                eINSTANCE.getLoopControlTransitionType_DecisionActivity();

        /**
         * The meta object literal for the '<em><b>To Activity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute LOOP_CONTROL_TRANSITION_TYPE__TO_ACTIVITY =
                eINSTANCE.getLoopControlTransitionType_ToActivity();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.LoopControlTypeImpl <em>Loop Control Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.LoopControlTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlType()
         * @generated
         */
        EClass LOOP_CONTROL_TYPE = eINSTANCE.getLoopControlType();

        /**
         * The meta object literal for the '<em><b>Max Loop Count Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_CONTROL_TYPE__MAX_LOOP_COUNT_STRATEGY =
                eINSTANCE.getLoopControlType_MaxLoopCountStrategy();

        /**
         * The meta object literal for the '<em><b>Normal Distribution Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_CONTROL_TYPE__NORMAL_DISTRIBUTION_STRATEGY =
                eINSTANCE.getLoopControlType_NormalDistributionStrategy();

        /**
         * The meta object literal for the '<em><b>Max Elapse Time Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LOOP_CONTROL_TYPE__MAX_ELAPSE_TIME_STRATEGY =
                eINSTANCE.getLoopControlType_MaxElapseTimeStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl <em>Max Elapse Time Strategy Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.MaxElapseTimeStrategyTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getMaxElapseTimeStrategyType()
         * @generated
         */
        EClass MAX_ELAPSE_TIME_STRATEGY_TYPE =
                eINSTANCE.getMaxElapseTimeStrategyType();

        /**
         * The meta object literal for the '<em><b>Display Time Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MAX_ELAPSE_TIME_STRATEGY_TYPE__DISPLAY_TIME_UNIT =
                eINSTANCE.getMaxElapseTimeStrategyType_DisplayTimeUnit();

        /**
         * The meta object literal for the '<em><b>Max Elapse Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MAX_ELAPSE_TIME_STRATEGY_TYPE__MAX_ELAPSE_TIME =
                eINSTANCE.getMaxElapseTimeStrategyType_MaxElapseTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.MaxLoopCountStrategyTypeImpl <em>Max Loop Count Strategy Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.MaxLoopCountStrategyTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getMaxLoopCountStrategyType()
         * @generated
         */
        EClass MAX_LOOP_COUNT_STRATEGY_TYPE =
                eINSTANCE.getMaxLoopCountStrategyType();

        /**
         * The meta object literal for the '<em><b>Max Loop Count</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MAX_LOOP_COUNT_STRATEGY_TYPE__MAX_LOOP_COUNT =
                eINSTANCE.getMaxLoopCountStrategyType_MaxLoopCount();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.NormalRealDistributionImpl <em>Normal Real Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.NormalRealDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getNormalRealDistribution()
         * @generated
         */
        EClass NORMAL_REAL_DISTRIBUTION = eINSTANCE.getNormalRealDistribution();

        /**
         * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NORMAL_REAL_DISTRIBUTION__MEAN =
                eINSTANCE.getNormalRealDistribution_Mean();

        /**
         * The meta object literal for the '<em><b>Standard Deviation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NORMAL_REAL_DISTRIBUTION__STANDARD_DEVIATION =
                eINSTANCE.getNormalRealDistribution_StandardDeviation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ParameterBasedDistributionImpl <em>Parameter Based Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ParameterBasedDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterBasedDistribution()
         * @generated
         */
        EClass PARAMETER_BASED_DISTRIBUTION =
                eINSTANCE.getParameterBasedDistribution();

        /**
         * The meta object literal for the '<em><b>Parameter Dependent Distributions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_BASED_DISTRIBUTION__PARAMETER_DEPENDENT_DISTRIBUTIONS =
                eINSTANCE
                        .getParameterBasedDistribution_ParameterDependentDistributions();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl <em>Parameter Dependent Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ParameterDependentDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterDependentDistribution()
         * @generated
         */
        EClass PARAMETER_DEPENDENT_DISTRIBUTION =
                eINSTANCE.getParameterDependentDistribution();

        /**
         * The meta object literal for the '<em><b>Basic Distribution</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_DEPENDENT_DISTRIBUTION__BASIC_DISTRIBUTION =
                eINSTANCE.getParameterDependentDistribution_BasicDistribution();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_DEPENDENT_DISTRIBUTION__EXPRESSION =
                eINSTANCE.getParameterDependentDistribution_Expression();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ParameterDistributionImpl <em>Parameter Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ParameterDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParameterDistribution()
         * @generated
         */
        EClass PARAMETER_DISTRIBUTION = eINSTANCE.getParameterDistribution();

        /**
         * The meta object literal for the '<em><b>Enumeration Value</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETER_DISTRIBUTION__ENUMERATION_VALUE =
                eINSTANCE.getParameterDistribution_EnumerationValue();

        /**
         * The meta object literal for the '<em><b>Parameter Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_DISTRIBUTION__PARAMETER_ID =
                eINSTANCE.getParameterDistribution_ParameterId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl <em>Participant Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.ParticipantSimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getParticipantSimulationDataType()
         * @generated
         */
        EClass PARTICIPANT_SIMULATION_DATA_TYPE =
                eINSTANCE.getParticipantSimulationDataType();

        /**
         * The meta object literal for the '<em><b>Instances</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES =
                eINSTANCE.getParticipantSimulationDataType_Instances();

        /**
         * The meta object literal for the '<em><b>Time Unit Cost</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST =
                eINSTANCE.getParticipantSimulationDataType_TimeUnitCost();

        /**
         * The meta object literal for the '<em><b>Sla Minimum Utilisation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION =
                eINSTANCE
                        .getParticipantSimulationDataType_SlaMinimumUtilisation();

        /**
         * The meta object literal for the '<em><b>Sla Maximum Utilisation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION =
                eINSTANCE
                        .getParticipantSimulationDataType_SlaMaximumUtilisation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl <em>Real Distribution Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.SimulationRealDistributionTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSimulationRealDistributionType()
         * @generated
         */
        EClass SIMULATION_REAL_DISTRIBUTION_TYPE =
                eINSTANCE.getSimulationRealDistributionType();

        /**
         * The meta object literal for the '<em><b>Basic Distribution</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIMULATION_REAL_DISTRIBUTION_TYPE__BASIC_DISTRIBUTION =
                eINSTANCE.getSimulationRealDistributionType_BasicDistribution();

        /**
         * The meta object literal for the '<em><b>Parameter Based Distribution</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIMULATION_REAL_DISTRIBUTION_TYPE__PARAMETER_BASED_DISTRIBUTION =
                eINSTANCE
                        .getSimulationRealDistributionType_ParameterBasedDistribution();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.SplitParameterTypeImpl <em>Split Parameter Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.SplitParameterTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSplitParameterType()
         * @generated
         */
        EClass SPLIT_PARAMETER_TYPE = eINSTANCE.getSplitParameterType();

        /**
         * The meta object literal for the '<em><b>Parameter Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPLIT_PARAMETER_TYPE__PARAMETER_ID =
                eINSTANCE.getSplitParameterType_ParameterId();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl <em>Split Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.SplitSimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getSplitSimulationDataType()
         * @generated
         */
        EClass SPLIT_SIMULATION_DATA_TYPE =
                eINSTANCE.getSplitSimulationDataType();

        /**
         * The meta object literal for the '<em><b>Parameter Determined Split</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPLIT_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_SPLIT =
                eINSTANCE.getSplitSimulationDataType_ParameterDeterminedSplit();

        /**
         * The meta object literal for the '<em><b>Split Parameter</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SPLIT_SIMULATION_DATA_TYPE__SPLIT_PARAMETER =
                eINSTANCE.getSplitSimulationDataType_SplitParameter();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl <em>Start Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.StartSimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getStartSimulationDataType()
         * @generated
         */
        EClass START_SIMULATION_DATA_TYPE =
                eINSTANCE.getStartSimulationDataType();

        /**
         * The meta object literal for the '<em><b>Duration</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference START_SIMULATION_DATA_TYPE__DURATION =
                eINSTANCE.getStartSimulationDataType_Duration();

        /**
         * The meta object literal for the '<em><b>Display Time Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_SIMULATION_DATA_TYPE__DISPLAY_TIME_UNIT =
                eINSTANCE.getStartSimulationDataType_DisplayTimeUnit();

        /**
         * The meta object literal for the '<em><b>Number Of Cases</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute START_SIMULATION_DATA_TYPE__NUMBER_OF_CASES =
                eINSTANCE.getStartSimulationDataType_NumberOfCases();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl <em>Structured Condition Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.StructuredConditionTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getStructuredConditionType()
         * @generated
         */
        EClass STRUCTURED_CONDITION_TYPE =
                eINSTANCE.getStructuredConditionType();

        /**
         * The meta object literal for the '<em><b>Parameter Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute STRUCTURED_CONDITION_TYPE__PARAMETER_ID =
                eINSTANCE.getStructuredConditionType_ParameterId();

        /**
         * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute STRUCTURED_CONDITION_TYPE__OPERATOR =
                eINSTANCE.getStructuredConditionType_Operator();

        /**
         * The meta object literal for the '<em><b>Parameter Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute STRUCTURED_CONDITION_TYPE__PARAMETER_VALUE =
                eINSTANCE.getStructuredConditionType_ParameterValue();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl <em>Time Unit Cost Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.TimeUnitCostTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeUnitCostType()
         * @generated
         */
        EClass TIME_UNIT_COST_TYPE = eINSTANCE.getTimeUnitCostType();

        /**
         * The meta object literal for the '<em><b>Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIME_UNIT_COST_TYPE__COST =
                eINSTANCE.getTimeUnitCostType_Cost();

        /**
         * The meta object literal for the '<em><b>Time Display Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIME_UNIT_COST_TYPE__TIME_DISPLAY_UNIT =
                eINSTANCE.getTimeUnitCostType_TimeDisplayUnit();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl <em>Transition Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.TransitionSimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTransitionSimulationDataType()
         * @generated
         */
        EClass TRANSITION_SIMULATION_DATA_TYPE =
                eINSTANCE.getTransitionSimulationDataType();

        /**
         * The meta object literal for the '<em><b>Parameter Determined Condition</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TRANSITION_SIMULATION_DATA_TYPE__PARAMETER_DETERMINED_CONDITION =
                eINSTANCE
                        .getTransitionSimulationDataType_ParameterDeterminedCondition();

        /**
         * The meta object literal for the '<em><b>Structured Condition</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TRANSITION_SIMULATION_DATA_TYPE__STRUCTURED_CONDITION =
                eINSTANCE.getTransitionSimulationDataType_StructuredCondition();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.UniformRealDistributionImpl <em>Uniform Real Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.UniformRealDistributionImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getUniformRealDistribution()
         * @generated
         */
        EClass UNIFORM_REAL_DISTRIBUTION =
                eINSTANCE.getUniformRealDistribution();

        /**
         * The meta object literal for the '<em><b>Lower Border</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNIFORM_REAL_DISTRIBUTION__LOWER_BORDER =
                eINSTANCE.getUniformRealDistribution_LowerBorder();

        /**
         * The meta object literal for the '<em><b>Upper Border</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNIFORM_REAL_DISTRIBUTION__UPPER_BORDER =
                eINSTANCE.getUniformRealDistribution_UpperBorder();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.WorkflowProcessSimulationDataTypeImpl <em>Workflow Process Simulation Data Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.WorkflowProcessSimulationDataTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getWorkflowProcessSimulationDataType()
         * @generated
         */
        EClass WORKFLOW_PROCESS_SIMULATION_DATA_TYPE =
                eINSTANCE.getWorkflowProcessSimulationDataType();

        /**
         * The meta object literal for the '<em><b>Parameter Distribution</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORKFLOW_PROCESS_SIMULATION_DATA_TYPE__PARAMETER_DISTRIBUTION =
                eINSTANCE
                        .getWorkflowProcessSimulationDataType_ParameterDistribution();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.impl.NormalDistributionStrategyTypeImpl <em>Normal Distribution Strategy Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.impl.NormalDistributionStrategyTypeImpl
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getNormalDistributionStrategyType()
         * @generated
         */
        EClass NORMAL_DISTRIBUTION_STRATEGY_TYPE =
                eINSTANCE.getNormalDistributionStrategyType();

        /**
         * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NORMAL_DISTRIBUTION_STRATEGY_TYPE__MEAN =
                eINSTANCE.getNormalDistributionStrategyType_Mean();

        /**
         * The meta object literal for the '<em><b>Standard Deviation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NORMAL_DISTRIBUTION_STRATEGY_TYPE__STANDARD_DEVIATION =
                eINSTANCE.getNormalDistributionStrategyType_StandardDeviation();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.LoopControlStrategy <em>Loop Control Strategy</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.LoopControlStrategy
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlStrategy()
         * @generated
         */
        EEnum LOOP_CONTROL_STRATEGY = eINSTANCE.getLoopControlStrategy();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.RealDistributionCategory <em>Real Distribution Category</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.RealDistributionCategory
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getRealDistributionCategory()
         * @generated
         */
        EEnum REAL_DISTRIBUTION_CATEGORY =
                eINSTANCE.getRealDistributionCategory();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.simulation.TimeDisplayUnitType <em>Time Display Unit Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.TimeDisplayUnitType
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeDisplayUnitType()
         * @generated
         */
        EEnum TIME_DISPLAY_UNIT_TYPE = eINSTANCE.getTimeDisplayUnitType();

        /**
         * The meta object literal for the '<em>Instances Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.math.BigInteger
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getInstancesType()
         * @generated
         */
        EDataType INSTANCES_TYPE = eINSTANCE.getInstancesType();

        /**
         * The meta object literal for the '<em>Loop Control Strategy Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.LoopControlStrategy
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getLoopControlStrategyObject()
         * @generated
         */
        EDataType LOOP_CONTROL_STRATEGY_OBJECT =
                eINSTANCE.getLoopControlStrategyObject();

        /**
         * The meta object literal for the '<em>Operator Type</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.lang.String
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getOperatorType()
         * @generated
         */
        EDataType OPERATOR_TYPE = eINSTANCE.getOperatorType();

        /**
         * The meta object literal for the '<em>Real Distribution Category Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.RealDistributionCategory
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getRealDistributionCategoryObject()
         * @generated
         */
        EDataType REAL_DISTRIBUTION_CATEGORY_OBJECT =
                eINSTANCE.getRealDistributionCategoryObject();

        /**
         * The meta object literal for the '<em>Time Display Unit Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.simulation.TimeDisplayUnitType
         * @see com.tibco.xpd.simulation.impl.SimulationPackageImpl#getTimeDisplayUnitTypeObject()
         * @generated
         */
        EDataType TIME_DISPLAY_UNIT_TYPE_OBJECT =
                eINSTANCE.getTimeDisplayUnitTypeObject();

    }

} //SimulationPackage
