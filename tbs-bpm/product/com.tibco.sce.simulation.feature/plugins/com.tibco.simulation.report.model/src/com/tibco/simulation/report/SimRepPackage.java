/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

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
 * @see com.tibco.simulation.report.SimRepFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface SimRepPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "report"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/models/SimulationReport.xsd"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "SimulationReport"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SimRepPackage eINSTANCE =
            com.tibco.simulation.report.impl.SimRepPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepActivitiesImpl <em>Activities</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepActivitiesImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivities()
     * @generated
     */
    int SIM_REP_ACTIVITIES = 0;

    /**
     * The feature id for the '<em><b>Activity</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITIES__ACTIVITY = 0;

    /**
     * The number of structural features of the '<em>Activities</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITIES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl <em>Activity Queue</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepActivityQueueImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivityQueue()
     * @generated
     */
    int SIM_REP_ACTIVITY_QUEUE = 1;

    /**
     * The feature id for the '<em><b>Queue Order</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER = 0;

    /**
     * The feature id for the '<em><b>Observed Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES = 1;

    /**
     * The feature id for the '<em><b>Current Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE = 2;

    /**
     * The feature id for the '<em><b>Max Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__MAX_SIZE = 3;

    /**
     * The feature id for the '<em><b>Average Size</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE = 4;

    /**
     * The feature id for the '<em><b>Average Wait</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT = 5;

    /**
     * The number of structural features of the '<em>Activity Queue</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_QUEUE_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepActivityImpl <em>Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepActivityImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivity()
     * @generated
     */
    int SIM_REP_ACTIVITY = 2;

    /**
     * The feature id for the '<em><b>Duration Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__DURATION_DISTRIBUTION = 0;

    /**
     * The feature id for the '<em><b>Activity Queue</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__ACTIVITY_QUEUE = 1;

    /**
     * The feature id for the '<em><b>Activity Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__ACTIVITY_COST = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__ID = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__NAME = 4;

    /**
     * The feature id for the '<em><b>Average Duration</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY__AVERAGE_DURATION = 5;

    /**
     * The number of structural features of the '<em>Activity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_ACTIVITY_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepCasesImpl <em>Cases</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepCasesImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepCases()
     * @generated
     */
    int SIM_REP_CASES = 3;

    /**
     * The feature id for the '<em><b>Case Start Interval Distribution</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION = 0;

    /**
     * The feature id for the '<em><b>Started Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__STARTED_CASES = 1;

    /**
     * The feature id for the '<em><b>Finished Cases</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__FINISHED_CASES = 2;

    /**
     * The feature id for the '<em><b>Average Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__AVERAGE_CASE_TIME = 3;

    /**
     * The feature id for the '<em><b>Min Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__MIN_CASE_TIME = 4;

    /**
     * The feature id for the '<em><b>Max Case Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__MAX_CASE_TIME = 5;

    /**
     * The feature id for the '<em><b>Case Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES__CASE_COST = 6;

    /**
     * The number of structural features of the '<em>Cases</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_CASES_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepCostImpl <em>Cost</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepCostImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepCost()
     * @generated
     */
    int SIM_REP_COST = 4;

    /**
     * The feature id for the '<em><b>Average Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_COST__AVERAGE_COST = 0;

    /**
     * The feature id for the '<em><b>Min Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_COST__MIN_COST = 1;

    /**
     * The feature id for the '<em><b>Max Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_COST__MAX_COST = 2;

    /**
     * The feature id for the '<em><b>Cumulative Average Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_COST__CUMULATIVE_AVERAGE_COST = 3;

    /**
     * The number of structural features of the '<em>Cost</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_COST_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepDistributionImpl <em>Distribution</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepDistributionImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistribution()
     * @generated
     */
    int SIM_REP_DISTRIBUTION = 5;

    /**
     * The feature id for the '<em><b>Last Reset Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__LAST_RESET_TIME = 0;

    /**
     * The feature id for the '<em><b>Observed Elements</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS = 1;

    /**
     * The feature id for the '<em><b>Distribution Category</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY = 2;

    /**
     * The feature id for the '<em><b>Parameter1</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__PARAMETER1 = 3;

    /**
     * The feature id for the '<em><b>Parameter2</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__PARAMETER2 = 4;

    /**
     * The feature id for the '<em><b>Seed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION__SEED = 5;

    /**
     * The number of structural features of the '<em>Distribution</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_DISTRIBUTION_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.DocumentRootImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 6;

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
     * The feature id for the '<em><b>Experiment</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EXPERIMENT = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl <em>Experiment Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepExperimentDataImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentData()
     * @generated
     */
    int SIM_REP_EXPERIMENT_DATA = 7;

    /**
     * The feature id for the '<em><b>Experiment State</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE = 0;

    /**
     * The feature id for the '<em><b>Simulation Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME = 1;

    /**
     * The feature id for the '<em><b>Reference Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT = 2;

    /**
     * The feature id for the '<em><b>Reference Start Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME = 3;

    /**
     * The feature id for the '<em><b>Real Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__REAL_TIME = 4;

    /**
     * The feature id for the '<em><b>Reference Cost Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT = 5;

    /**
     * The number of structural features of the '<em>Experiment Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_DATA_FEATURE_COUNT = 6;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepExperimentImpl <em>Experiment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepExperimentImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperiment()
     * @generated
     */
    int SIM_REP_EXPERIMENT = 8;

    /**
     * The feature id for the '<em><b>Experiment Data</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__EXPERIMENT_DATA = 0;

    /**
     * The feature id for the '<em><b>Cases</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__CASES = 1;

    /**
     * The feature id for the '<em><b>Participants</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PARTICIPANTS = 2;

    /**
     * The feature id for the '<em><b>Activities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__ACTIVITIES = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__ID = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__NAME = 5;

    /**
     * The feature id for the '<em><b>Package Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PACKAGE_ID = 6;

    /**
     * The feature id for the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PROCESS_ID = 7;

    /**
     * The feature id for the '<em><b>Process Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PROCESS_NAME = 8;

    /**
     * The feature id for the '<em><b>Process Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PROCESS_LABEL = 9;

    /**
     * The feature id for the '<em><b>Package Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT__PACKAGE_NAME = 10;

    /**
     * The number of structural features of the '<em>Experiment</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_EXPERIMENT_FEATURE_COUNT = 11;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepParticipantsImpl <em>Participants</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepParticipantsImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepParticipants()
     * @generated
     */
    int SIM_REP_PARTICIPANTS = 9;

    /**
     * The feature id for the '<em><b>Participant</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANTS__PARTICIPANT = 0;

    /**
     * The number of structural features of the '<em>Participants</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANTS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.impl.SimRepParticipantImpl <em>Participant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.impl.SimRepParticipantImpl
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepParticipant()
     * @generated
     */
    int SIM_REP_PARTICIPANT = 10;

    /**
     * The feature id for the '<em><b>No Of Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__NO_OF_INSTANCES = 0;

    /**
     * The feature id for the '<em><b>Idle Instances</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__IDLE_INSTANCES = 1;

    /**
     * The feature id for the '<em><b>Average Idle</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__AVERAGE_IDLE = 2;

    /**
     * The feature id for the '<em><b>Average Idle Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME = 3;

    /**
     * The feature id for the '<em><b>Average Busy Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME = 4;

    /**
     * The feature id for the '<em><b>Cost Of Work For Case</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE = 5;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__ID = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT__NAME = 7;

    /**
     * The number of structural features of the '<em>Participant</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SIM_REP_PARTICIPANT_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.SimRepDistributionCategory <em>Distribution Category</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistributionCategory()
     * @generated
     */
    int SIM_REP_DISTRIBUTION_CATEGORY = 11;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.SimRepExperimentState <em>Experiment State</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentState()
     * @generated
     */
    int SIM_REP_EXPERIMENT_STATE = 12;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.SimRepQueueOrder <em>Queue Order</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepQueueOrder()
     * @generated
     */
    int SIM_REP_QUEUE_ORDER = 13;

    /**
     * The meta object id for the '{@link com.tibco.simulation.report.SimRepReferenceTimeUnit <em>Reference Time Unit</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepReferenceTimeUnit()
     * @generated
     */
    int SIM_REP_REFERENCE_TIME_UNIT = 14;

    /**
     * The meta object id for the '<em>Distribution Category Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistributionCategoryObject()
     * @generated
     */
    int SIM_REP_DISTRIBUTION_CATEGORY_OBJECT = 15;

    /**
     * The meta object id for the '<em>Experiment State Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentStateObject()
     * @generated
     */
    int SIM_REP_EXPERIMENT_STATE_OBJECT = 16;

    /**
     * The meta object id for the '<em>Queue Order Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepQueueOrderObject()
     * @generated
     */
    int SIM_REP_QUEUE_ORDER_OBJECT = 17;

    /**
     * The meta object id for the '<em>Reference Time Unit Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepReferenceTimeUnitObject()
     * @generated
     */
    int SIM_REP_REFERENCE_TIME_UNIT_OBJECT = 18;

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activities</em>'.
     * @see com.tibco.simulation.report.SimRepActivities
     * @generated
     */
    EClass getSimRepActivities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.simulation.report.SimRepActivities#getActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Activity</em>'.
     * @see com.tibco.simulation.report.SimRepActivities#getActivity()
     * @see #getSimRepActivities()
     * @generated
     */
    EReference getSimRepActivities_Activity();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepActivityQueue <em>Activity Queue</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity Queue</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue
     * @generated
     */
    EClass getSimRepActivityQueue();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder <em>Queue Order</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Queue Order</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getQueueOrder()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_QueueOrder();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getObservedCases <em>Observed Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Observed Cases</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getObservedCases()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_ObservedCases();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize <em>Current Size</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Current Size</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getCurrentSize()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_CurrentSize();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getMaxSize <em>Max Size</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Size</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getMaxSize()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_MaxSize();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageSize <em>Average Size</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Size</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getAverageSize()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_AverageSize();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivityQueue#getAverageWait <em>Average Wait</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Wait</em>'.
     * @see com.tibco.simulation.report.SimRepActivityQueue#getAverageWait()
     * @see #getSimRepActivityQueue()
     * @generated
     */
    EAttribute getSimRepActivityQueue_AverageWait();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity</em>'.
     * @see com.tibco.simulation.report.SimRepActivity
     * @generated
     */
    EClass getSimRepActivity();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepActivity#getDurationDistribution <em>Duration Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Duration Distribution</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getDurationDistribution()
     * @see #getSimRepActivity()
     * @generated
     */
    EReference getSimRepActivity_DurationDistribution();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepActivity#getActivityQueue <em>Activity Queue</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Activity Queue</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getActivityQueue()
     * @see #getSimRepActivity()
     * @generated
     */
    EReference getSimRepActivity_ActivityQueue();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepActivity#getActivityCost <em>Activity Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Activity Cost</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getActivityCost()
     * @see #getSimRepActivity()
     * @generated
     */
    EReference getSimRepActivity_ActivityCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivity#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getId()
     * @see #getSimRepActivity()
     * @generated
     */
    EAttribute getSimRepActivity_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivity#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getName()
     * @see #getSimRepActivity()
     * @generated
     */
    EAttribute getSimRepActivity_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepActivity#getAverageDuration <em>Average Duration</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Duration</em>'.
     * @see com.tibco.simulation.report.SimRepActivity#getAverageDuration()
     * @see #getSimRepActivity()
     * @generated
     */
    EAttribute getSimRepActivity_AverageDuration();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepCases <em>Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cases</em>'.
     * @see com.tibco.simulation.report.SimRepCases
     * @generated
     */
    EClass getSimRepCases();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepCases#getCaseStartIntervalDistribution <em>Case Start Interval Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Start Interval Distribution</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getCaseStartIntervalDistribution()
     * @see #getSimRepCases()
     * @generated
     */
    EReference getSimRepCases_CaseStartIntervalDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCases#getStartedCases <em>Started Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Started Cases</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getStartedCases()
     * @see #getSimRepCases()
     * @generated
     */
    EAttribute getSimRepCases_StartedCases();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCases#getFinishedCases <em>Finished Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Finished Cases</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getFinishedCases()
     * @see #getSimRepCases()
     * @generated
     */
    EAttribute getSimRepCases_FinishedCases();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCases#getAverageCaseTime <em>Average Case Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Case Time</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getAverageCaseTime()
     * @see #getSimRepCases()
     * @generated
     */
    EAttribute getSimRepCases_AverageCaseTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCases#getMinCaseTime <em>Min Case Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Min Case Time</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getMinCaseTime()
     * @see #getSimRepCases()
     * @generated
     */
    EAttribute getSimRepCases_MinCaseTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCases#getMaxCaseTime <em>Max Case Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Case Time</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getMaxCaseTime()
     * @see #getSimRepCases()
     * @generated
     */
    EAttribute getSimRepCases_MaxCaseTime();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepCases#getCaseCost <em>Case Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Case Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCases#getCaseCost()
     * @see #getSimRepCases()
     * @generated
     */
    EReference getSimRepCases_CaseCost();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepCost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCost
     * @generated
     */
    EClass getSimRepCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCost#getAverageCost <em>Average Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCost#getAverageCost()
     * @see #getSimRepCost()
     * @generated
     */
    EAttribute getSimRepCost_AverageCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCost#getMinCost <em>Min Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Min Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCost#getMinCost()
     * @see #getSimRepCost()
     * @generated
     */
    EAttribute getSimRepCost_MinCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCost#getMaxCost <em>Max Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Max Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCost#getMaxCost()
     * @see #getSimRepCost()
     * @generated
     */
    EAttribute getSimRepCost_MaxCost();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost <em>Cumulative Average Cost</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Cumulative Average Cost</em>'.
     * @see com.tibco.simulation.report.SimRepCost#getCumulativeAverageCost()
     * @see #getSimRepCost()
     * @generated
     */
    EAttribute getSimRepCost_CumulativeAverageCost();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepDistribution <em>Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Distribution</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution
     * @generated
     */
    EClass getSimRepDistribution();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getLastResetTime <em>Last Reset Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Last Reset Time</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getLastResetTime()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_LastResetTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getObservedElements <em>Observed Elements</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Observed Elements</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getObservedElements()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_ObservedElements();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getDistributionCategory <em>Distribution Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution Category</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getDistributionCategory()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_DistributionCategory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getParameter1 <em>Parameter1</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter1</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getParameter1()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_Parameter1();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getParameter2 <em>Parameter2</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Parameter2</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getParameter2()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_Parameter2();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepDistribution#getSeed <em>Seed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Seed</em>'.
     * @see com.tibco.simulation.report.SimRepDistribution#getSeed()
     * @see #getSimRepDistribution()
     * @generated
     */
    EAttribute getSimRepDistribution_Seed();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.simulation.report.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.simulation.report.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.simulation.report.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.simulation.report.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.simulation.report.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.simulation.report.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.simulation.report.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.DocumentRoot#getExperiment <em>Experiment</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Experiment</em>'.
     * @see com.tibco.simulation.report.DocumentRoot#getExperiment()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Experiment();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepExperimentData <em>Experiment Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Experiment Data</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData
     * @generated
     */
    EClass getSimRepExperimentData();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getExperimentState <em>Experiment State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Experiment State</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getExperimentState()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_ExperimentState();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getSimulationTime <em>Simulation Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Simulation Time</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getSimulationTime()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_SimulationTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit <em>Reference Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference Time Unit</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getReferenceTimeUnit()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_ReferenceTimeUnit();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceStartTime <em>Reference Start Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference Start Time</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getReferenceStartTime()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_ReferenceStartTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getRealTime <em>Real Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Real Time</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getRealTime()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_RealTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperimentData#getReferenceCostUnit <em>Reference Cost Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference Cost Unit</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentData#getReferenceCostUnit()
     * @see #getSimRepExperimentData()
     * @generated
     */
    EAttribute getSimRepExperimentData_ReferenceCostUnit();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepExperiment <em>Experiment</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Experiment</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment
     * @generated
     */
    EClass getSimRepExperiment();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepExperiment#getExperimentData <em>Experiment Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Experiment Data</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getExperimentData()
     * @see #getSimRepExperiment()
     * @generated
     */
    EReference getSimRepExperiment_ExperimentData();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepExperiment#getCases <em>Cases</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cases</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getCases()
     * @see #getSimRepExperiment()
     * @generated
     */
    EReference getSimRepExperiment_Cases();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepExperiment#getParticipants <em>Participants</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Participants</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getParticipants()
     * @see #getSimRepExperiment()
     * @generated
     */
    EReference getSimRepExperiment_Participants();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepExperiment#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Activities</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getActivities()
     * @see #getSimRepExperiment()
     * @generated
     */
    EReference getSimRepExperiment_Activities();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getId()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getName()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getPackageId <em>Package Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Id</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getPackageId()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_PackageId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getProcessId <em>Process Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Id</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getProcessId()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_ProcessId();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getProcessName <em>Process Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Name</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getProcessName()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_ProcessName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getProcessLabel <em>Process Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Label</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getProcessLabel()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_ProcessLabel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepExperiment#getPackageName <em>Package Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Package Name</em>'.
     * @see com.tibco.simulation.report.SimRepExperiment#getPackageName()
     * @see #getSimRepExperiment()
     * @generated
     */
    EAttribute getSimRepExperiment_PackageName();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepParticipants <em>Participants</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participants</em>'.
     * @see com.tibco.simulation.report.SimRepParticipants
     * @generated
     */
    EClass getSimRepParticipants();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.simulation.report.SimRepParticipants#getParticipant <em>Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Participant</em>'.
     * @see com.tibco.simulation.report.SimRepParticipants#getParticipant()
     * @see #getSimRepParticipants()
     * @generated
     */
    EReference getSimRepParticipants_Participant();

    /**
     * Returns the meta object for class '{@link com.tibco.simulation.report.SimRepParticipant <em>Participant</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Participant</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant
     * @generated
     */
    EClass getSimRepParticipant();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getNoOfInstances <em>No Of Instances</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>No Of Instances</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getNoOfInstances()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_NoOfInstances();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getIdleInstances <em>Idle Instances</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Idle Instances</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getIdleInstances()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_IdleInstances();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdle <em>Average Idle</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Idle</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getAverageIdle()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_AverageIdle();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime <em>Average Idle Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Idle Time</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getAverageIdleTime()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_AverageIdleTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime <em>Average Busy Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Average Busy Time</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getAverageBusyTime()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_AverageBusyTime();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.simulation.report.SimRepParticipant#getCostOfWorkForCase <em>Cost Of Work For Case</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cost Of Work For Case</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getCostOfWorkForCase()
     * @see #getSimRepParticipant()
     * @generated
     */
    EReference getSimRepParticipant_CostOfWorkForCase();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getId()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.simulation.report.SimRepParticipant#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.simulation.report.SimRepParticipant#getName()
     * @see #getSimRepParticipant()
     * @generated
     */
    EAttribute getSimRepParticipant_Name();

    /**
     * Returns the meta object for enum '{@link com.tibco.simulation.report.SimRepDistributionCategory <em>Distribution Category</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Distribution Category</em>'.
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @generated
     */
    EEnum getSimRepDistributionCategory();

    /**
     * Returns the meta object for enum '{@link com.tibco.simulation.report.SimRepExperimentState <em>Experiment State</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Experiment State</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @generated
     */
    EEnum getSimRepExperimentState();

    /**
     * Returns the meta object for enum '{@link com.tibco.simulation.report.SimRepQueueOrder <em>Queue Order</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Queue Order</em>'.
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @generated
     */
    EEnum getSimRepQueueOrder();

    /**
     * Returns the meta object for enum '{@link com.tibco.simulation.report.SimRepReferenceTimeUnit <em>Reference Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Reference Time Unit</em>'.
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @generated
     */
    EEnum getSimRepReferenceTimeUnit();

    /**
     * Returns the meta object for data type '{@link com.tibco.simulation.report.SimRepDistributionCategory <em>Distribution Category Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Distribution Category Object</em>'.
     * @see com.tibco.simulation.report.SimRepDistributionCategory
     * @model instanceClass="com.tibco.simulation.report.SimRepDistributionCategory"
     *        extendedMetaData="name='DistributionCategoryType:Object' baseType='DistributionCategoryType'"
     * @generated
     */
    EDataType getSimRepDistributionCategoryObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.simulation.report.SimRepExperimentState <em>Experiment State Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Experiment State Object</em>'.
     * @see com.tibco.simulation.report.SimRepExperimentState
     * @model instanceClass="com.tibco.simulation.report.SimRepExperimentState"
     *        extendedMetaData="name='ExperimentStateType:Object' baseType='ExperimentStateType'"
     * @generated
     */
    EDataType getSimRepExperimentStateObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.simulation.report.SimRepQueueOrder <em>Queue Order Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Queue Order Object</em>'.
     * @see com.tibco.simulation.report.SimRepQueueOrder
     * @model instanceClass="com.tibco.simulation.report.SimRepQueueOrder"
     *        extendedMetaData="name='QueueOrderType:Object' baseType='QueueOrderType'"
     * @generated
     */
    EDataType getSimRepQueueOrderObject();

    /**
     * Returns the meta object for data type '{@link com.tibco.simulation.report.SimRepReferenceTimeUnit <em>Reference Time Unit Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Reference Time Unit Object</em>'.
     * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
     * @model instanceClass="com.tibco.simulation.report.SimRepReferenceTimeUnit"
     *        extendedMetaData="name='ReferenceTimeUnitType:Object' baseType='ReferenceTimeUnitType'"
     * @generated
     */
    EDataType getSimRepReferenceTimeUnitObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    SimRepFactory getSimRepFactory();

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
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepActivitiesImpl <em>Activities</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepActivitiesImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivities()
         * @generated
         */
        EClass SIM_REP_ACTIVITIES = eINSTANCE.getSimRepActivities();

        /**
         * The meta object literal for the '<em><b>Activity</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_ACTIVITIES__ACTIVITY = eINSTANCE
                .getSimRepActivities_Activity();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepActivityQueueImpl <em>Activity Queue</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepActivityQueueImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivityQueue()
         * @generated
         */
        EClass SIM_REP_ACTIVITY_QUEUE = eINSTANCE.getSimRepActivityQueue();

        /**
         * The meta object literal for the '<em><b>Queue Order</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER = eINSTANCE
                .getSimRepActivityQueue_QueueOrder();

        /**
         * The meta object literal for the '<em><b>Observed Cases</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES = eINSTANCE
                .getSimRepActivityQueue_ObservedCases();

        /**
         * The meta object literal for the '<em><b>Current Size</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE = eINSTANCE
                .getSimRepActivityQueue_CurrentSize();

        /**
         * The meta object literal for the '<em><b>Max Size</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__MAX_SIZE = eINSTANCE
                .getSimRepActivityQueue_MaxSize();

        /**
         * The meta object literal for the '<em><b>Average Size</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE = eINSTANCE
                .getSimRepActivityQueue_AverageSize();

        /**
         * The meta object literal for the '<em><b>Average Wait</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT = eINSTANCE
                .getSimRepActivityQueue_AverageWait();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepActivityImpl <em>Activity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepActivityImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepActivity()
         * @generated
         */
        EClass SIM_REP_ACTIVITY = eINSTANCE.getSimRepActivity();

        /**
         * The meta object literal for the '<em><b>Duration Distribution</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_ACTIVITY__DURATION_DISTRIBUTION = eINSTANCE
                .getSimRepActivity_DurationDistribution();

        /**
         * The meta object literal for the '<em><b>Activity Queue</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_ACTIVITY__ACTIVITY_QUEUE = eINSTANCE
                .getSimRepActivity_ActivityQueue();

        /**
         * The meta object literal for the '<em><b>Activity Cost</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_ACTIVITY__ACTIVITY_COST = eINSTANCE
                .getSimRepActivity_ActivityCost();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY__ID = eINSTANCE.getSimRepActivity_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY__NAME = eINSTANCE.getSimRepActivity_Name();

        /**
         * The meta object literal for the '<em><b>Average Duration</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_ACTIVITY__AVERAGE_DURATION = eINSTANCE
                .getSimRepActivity_AverageDuration();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepCasesImpl <em>Cases</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepCasesImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepCases()
         * @generated
         */
        EClass SIM_REP_CASES = eINSTANCE.getSimRepCases();

        /**
         * The meta object literal for the '<em><b>Case Start Interval Distribution</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION = eINSTANCE
                .getSimRepCases_CaseStartIntervalDistribution();

        /**
         * The meta object literal for the '<em><b>Started Cases</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_CASES__STARTED_CASES = eINSTANCE
                .getSimRepCases_StartedCases();

        /**
         * The meta object literal for the '<em><b>Finished Cases</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_CASES__FINISHED_CASES = eINSTANCE
                .getSimRepCases_FinishedCases();

        /**
         * The meta object literal for the '<em><b>Average Case Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_CASES__AVERAGE_CASE_TIME = eINSTANCE
                .getSimRepCases_AverageCaseTime();

        /**
         * The meta object literal for the '<em><b>Min Case Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_CASES__MIN_CASE_TIME = eINSTANCE
                .getSimRepCases_MinCaseTime();

        /**
         * The meta object literal for the '<em><b>Max Case Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_CASES__MAX_CASE_TIME = eINSTANCE
                .getSimRepCases_MaxCaseTime();

        /**
         * The meta object literal for the '<em><b>Case Cost</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_CASES__CASE_COST = eINSTANCE
                .getSimRepCases_CaseCost();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepCostImpl <em>Cost</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepCostImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepCost()
         * @generated
         */
        EClass SIM_REP_COST = eINSTANCE.getSimRepCost();

        /**
         * The meta object literal for the '<em><b>Average Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_COST__AVERAGE_COST = eINSTANCE
                .getSimRepCost_AverageCost();

        /**
         * The meta object literal for the '<em><b>Min Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_COST__MIN_COST = eINSTANCE.getSimRepCost_MinCost();

        /**
         * The meta object literal for the '<em><b>Max Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_COST__MAX_COST = eINSTANCE.getSimRepCost_MaxCost();

        /**
         * The meta object literal for the '<em><b>Cumulative Average Cost</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_COST__CUMULATIVE_AVERAGE_COST = eINSTANCE
                .getSimRepCost_CumulativeAverageCost();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepDistributionImpl <em>Distribution</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepDistributionImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistribution()
         * @generated
         */
        EClass SIM_REP_DISTRIBUTION = eINSTANCE.getSimRepDistribution();

        /**
         * The meta object literal for the '<em><b>Last Reset Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__LAST_RESET_TIME = eINSTANCE
                .getSimRepDistribution_LastResetTime();

        /**
         * The meta object literal for the '<em><b>Observed Elements</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS = eINSTANCE
                .getSimRepDistribution_ObservedElements();

        /**
         * The meta object literal for the '<em><b>Distribution Category</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY = eINSTANCE
                .getSimRepDistribution_DistributionCategory();

        /**
         * The meta object literal for the '<em><b>Parameter1</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__PARAMETER1 = eINSTANCE
                .getSimRepDistribution_Parameter1();

        /**
         * The meta object literal for the '<em><b>Parameter2</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__PARAMETER2 = eINSTANCE
                .getSimRepDistribution_Parameter2();

        /**
         * The meta object literal for the '<em><b>Seed</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_DISTRIBUTION__SEED = eINSTANCE
                .getSimRepDistribution_Seed();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.DocumentRootImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getDocumentRoot()
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
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE
                .getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE
                .getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Experiment</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EXPERIMENT = eINSTANCE
                .getDocumentRoot_Experiment();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepExperimentDataImpl <em>Experiment Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepExperimentDataImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentData()
         * @generated
         */
        EClass SIM_REP_EXPERIMENT_DATA = eINSTANCE.getSimRepExperimentData();

        /**
         * The meta object literal for the '<em><b>Experiment State</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE = eINSTANCE
                .getSimRepExperimentData_ExperimentState();

        /**
         * The meta object literal for the '<em><b>Simulation Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME = eINSTANCE
                .getSimRepExperimentData_SimulationTime();

        /**
         * The meta object literal for the '<em><b>Reference Time Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT = eINSTANCE
                .getSimRepExperimentData_ReferenceTimeUnit();

        /**
         * The meta object literal for the '<em><b>Reference Start Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME = eINSTANCE
                .getSimRepExperimentData_ReferenceStartTime();

        /**
         * The meta object literal for the '<em><b>Real Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__REAL_TIME = eINSTANCE
                .getSimRepExperimentData_RealTime();

        /**
         * The meta object literal for the '<em><b>Reference Cost Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT = eINSTANCE
                .getSimRepExperimentData_ReferenceCostUnit();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepExperimentImpl <em>Experiment</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepExperimentImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperiment()
         * @generated
         */
        EClass SIM_REP_EXPERIMENT = eINSTANCE.getSimRepExperiment();

        /**
         * The meta object literal for the '<em><b>Experiment Data</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_EXPERIMENT__EXPERIMENT_DATA = eINSTANCE
                .getSimRepExperiment_ExperimentData();

        /**
         * The meta object literal for the '<em><b>Cases</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_EXPERIMENT__CASES = eINSTANCE
                .getSimRepExperiment_Cases();

        /**
         * The meta object literal for the '<em><b>Participants</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_EXPERIMENT__PARTICIPANTS = eINSTANCE
                .getSimRepExperiment_Participants();

        /**
         * The meta object literal for the '<em><b>Activities</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_EXPERIMENT__ACTIVITIES = eINSTANCE
                .getSimRepExperiment_Activities();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__ID = eINSTANCE.getSimRepExperiment_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__NAME = eINSTANCE
                .getSimRepExperiment_Name();

        /**
         * The meta object literal for the '<em><b>Package Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__PACKAGE_ID = eINSTANCE
                .getSimRepExperiment_PackageId();

        /**
         * The meta object literal for the '<em><b>Process Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__PROCESS_ID = eINSTANCE
                .getSimRepExperiment_ProcessId();

        /**
         * The meta object literal for the '<em><b>Process Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__PROCESS_NAME = eINSTANCE
                .getSimRepExperiment_ProcessName();

        /**
         * The meta object literal for the '<em><b>Process Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__PROCESS_LABEL = eINSTANCE
                .getSimRepExperiment_ProcessLabel();

        /**
         * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_EXPERIMENT__PACKAGE_NAME = eINSTANCE
                .getSimRepExperiment_PackageName();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepParticipantsImpl <em>Participants</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepParticipantsImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepParticipants()
         * @generated
         */
        EClass SIM_REP_PARTICIPANTS = eINSTANCE.getSimRepParticipants();

        /**
         * The meta object literal for the '<em><b>Participant</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_PARTICIPANTS__PARTICIPANT = eINSTANCE
                .getSimRepParticipants_Participant();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.impl.SimRepParticipantImpl <em>Participant</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.impl.SimRepParticipantImpl
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepParticipant()
         * @generated
         */
        EClass SIM_REP_PARTICIPANT = eINSTANCE.getSimRepParticipant();

        /**
         * The meta object literal for the '<em><b>No Of Instances</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__NO_OF_INSTANCES = eINSTANCE
                .getSimRepParticipant_NoOfInstances();

        /**
         * The meta object literal for the '<em><b>Idle Instances</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__IDLE_INSTANCES = eINSTANCE
                .getSimRepParticipant_IdleInstances();

        /**
         * The meta object literal for the '<em><b>Average Idle</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__AVERAGE_IDLE = eINSTANCE
                .getSimRepParticipant_AverageIdle();

        /**
         * The meta object literal for the '<em><b>Average Idle Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME = eINSTANCE
                .getSimRepParticipant_AverageIdleTime();

        /**
         * The meta object literal for the '<em><b>Average Busy Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME = eINSTANCE
                .getSimRepParticipant_AverageBusyTime();

        /**
         * The meta object literal for the '<em><b>Cost Of Work For Case</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE = eINSTANCE
                .getSimRepParticipant_CostOfWorkForCase();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__ID = eINSTANCE
                .getSimRepParticipant_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SIM_REP_PARTICIPANT__NAME = eINSTANCE
                .getSimRepParticipant_Name();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.SimRepDistributionCategory <em>Distribution Category</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepDistributionCategory
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistributionCategory()
         * @generated
         */
        EEnum SIM_REP_DISTRIBUTION_CATEGORY = eINSTANCE
                .getSimRepDistributionCategory();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.SimRepExperimentState <em>Experiment State</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepExperimentState
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentState()
         * @generated
         */
        EEnum SIM_REP_EXPERIMENT_STATE = eINSTANCE.getSimRepExperimentState();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.SimRepQueueOrder <em>Queue Order</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepQueueOrder
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepQueueOrder()
         * @generated
         */
        EEnum SIM_REP_QUEUE_ORDER = eINSTANCE.getSimRepQueueOrder();

        /**
         * The meta object literal for the '{@link com.tibco.simulation.report.SimRepReferenceTimeUnit <em>Reference Time Unit</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepReferenceTimeUnit()
         * @generated
         */
        EEnum SIM_REP_REFERENCE_TIME_UNIT = eINSTANCE
                .getSimRepReferenceTimeUnit();

        /**
         * The meta object literal for the '<em>Distribution Category Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepDistributionCategory
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepDistributionCategoryObject()
         * @generated
         */
        EDataType SIM_REP_DISTRIBUTION_CATEGORY_OBJECT = eINSTANCE
                .getSimRepDistributionCategoryObject();

        /**
         * The meta object literal for the '<em>Experiment State Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepExperimentState
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepExperimentStateObject()
         * @generated
         */
        EDataType SIM_REP_EXPERIMENT_STATE_OBJECT = eINSTANCE
                .getSimRepExperimentStateObject();

        /**
         * The meta object literal for the '<em>Queue Order Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepQueueOrder
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepQueueOrderObject()
         * @generated
         */
        EDataType SIM_REP_QUEUE_ORDER_OBJECT = eINSTANCE
                .getSimRepQueueOrderObject();

        /**
         * The meta object literal for the '<em>Reference Time Unit Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.simulation.report.SimRepReferenceTimeUnit
         * @see com.tibco.simulation.report.impl.SimRepPackageImpl#getSimRepReferenceTimeUnitObject()
         * @generated
         */
        EDataType SIM_REP_REFERENCE_TIME_UNIT_OBJECT = eINSTANCE
                .getSimRepReferenceTimeUnitObject();

    }

} //SimRepPackage
