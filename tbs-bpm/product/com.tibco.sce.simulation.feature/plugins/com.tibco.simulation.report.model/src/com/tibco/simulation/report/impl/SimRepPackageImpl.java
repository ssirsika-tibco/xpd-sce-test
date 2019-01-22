/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.DocumentRoot;
import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepActivityQueue;
import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepDistributionCategory;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepParticipant;
import com.tibco.simulation.report.SimRepParticipants;
import com.tibco.simulation.report.SimRepQueueOrder;
import com.tibco.simulation.report.SimRepReferenceTimeUnit;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimRepPackageImpl extends EPackageImpl implements SimRepPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepActivitiesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepActivityQueueEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepActivityEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepCasesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepCostEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepDistributionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepExperimentDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepExperimentEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepParticipantsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass simRepParticipantEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum simRepDistributionCategoryEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum simRepExperimentStateEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum simRepQueueOrderEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum simRepReferenceTimeUnitEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType simRepDistributionCategoryObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType simRepExperimentStateObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType simRepQueueOrderObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType simRepReferenceTimeUnitObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.simulation.report.SimRepPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private SimRepPackageImpl() {
        super(eNS_URI, SimRepFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link SimRepPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static SimRepPackage init() {
        if (isInited)
            return (SimRepPackage) EPackage.Registry.INSTANCE
                    .getEPackage(SimRepPackage.eNS_URI);

        // Obtain or create and register package
        SimRepPackageImpl theSimRepPackage =
                (SimRepPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SimRepPackageImpl ? EPackage.Registry.INSTANCE
                        .get(eNS_URI) : new SimRepPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theSimRepPackage.createPackageContents();

        // Initialize created meta-data
        theSimRepPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theSimRepPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(SimRepPackage.eNS_URI, theSimRepPackage);
        return theSimRepPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepActivities() {
        return simRepActivitiesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepActivities_Activity() {
        return (EReference) simRepActivitiesEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepActivityQueue() {
        return simRepActivityQueueEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_QueueOrder() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_ObservedCases() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_CurrentSize() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_MaxSize() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_AverageSize() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivityQueue_AverageWait() {
        return (EAttribute) simRepActivityQueueEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepActivity() {
        return simRepActivityEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepActivity_DurationDistribution() {
        return (EReference) simRepActivityEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepActivity_ActivityQueue() {
        return (EReference) simRepActivityEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepActivity_ActivityCost() {
        return (EReference) simRepActivityEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivity_Id() {
        return (EAttribute) simRepActivityEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivity_Name() {
        return (EAttribute) simRepActivityEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepActivity_AverageDuration() {
        return (EAttribute) simRepActivityEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepCases() {
        return simRepCasesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepCases_CaseStartIntervalDistribution() {
        return (EReference) simRepCasesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCases_StartedCases() {
        return (EAttribute) simRepCasesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCases_FinishedCases() {
        return (EAttribute) simRepCasesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCases_AverageCaseTime() {
        return (EAttribute) simRepCasesEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCases_MinCaseTime() {
        return (EAttribute) simRepCasesEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCases_MaxCaseTime() {
        return (EAttribute) simRepCasesEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepCases_CaseCost() {
        return (EReference) simRepCasesEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepCost() {
        return simRepCostEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCost_AverageCost() {
        return (EAttribute) simRepCostEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCost_MinCost() {
        return (EAttribute) simRepCostEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCost_MaxCost() {
        return (EAttribute) simRepCostEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepCost_CumulativeAverageCost() {
        return (EAttribute) simRepCostEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepDistribution() {
        return simRepDistributionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_LastResetTime() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_ObservedElements() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_DistributionCategory() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_Parameter1() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_Parameter2() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepDistribution_Seed() {
        return (EAttribute) simRepDistributionEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Experiment() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepExperimentData() {
        return simRepExperimentDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_ExperimentState() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_SimulationTime() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_ReferenceTimeUnit() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_ReferenceStartTime() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_RealTime() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperimentData_ReferenceCostUnit() {
        return (EAttribute) simRepExperimentDataEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepExperiment() {
        return simRepExperimentEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepExperiment_ExperimentData() {
        return (EReference) simRepExperimentEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepExperiment_Cases() {
        return (EReference) simRepExperimentEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepExperiment_Participants() {
        return (EReference) simRepExperimentEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepExperiment_Activities() {
        return (EReference) simRepExperimentEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_Id() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_Name() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_PackageId() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_ProcessId() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_ProcessName() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_ProcessLabel() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepExperiment_PackageName() {
        return (EAttribute) simRepExperimentEClass.getEStructuralFeatures()
                .get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepParticipants() {
        return simRepParticipantsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepParticipants_Participant() {
        return (EReference) simRepParticipantsEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSimRepParticipant() {
        return simRepParticipantEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_NoOfInstances() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_IdleInstances() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_AverageIdle() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_AverageIdleTime() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_AverageBusyTime() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSimRepParticipant_CostOfWorkForCase() {
        return (EReference) simRepParticipantEClass.getEStructuralFeatures()
                .get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_Id() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSimRepParticipant_Name() {
        return (EAttribute) simRepParticipantEClass.getEStructuralFeatures()
                .get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSimRepDistributionCategory() {
        return simRepDistributionCategoryEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSimRepExperimentState() {
        return simRepExperimentStateEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSimRepQueueOrder() {
        return simRepQueueOrderEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSimRepReferenceTimeUnit() {
        return simRepReferenceTimeUnitEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSimRepDistributionCategoryObject() {
        return simRepDistributionCategoryObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSimRepExperimentStateObject() {
        return simRepExperimentStateObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSimRepQueueOrderObject() {
        return simRepQueueOrderObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSimRepReferenceTimeUnitObject() {
        return simRepReferenceTimeUnitObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepFactory getSimRepFactory() {
        return (SimRepFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        simRepActivitiesEClass = createEClass(SIM_REP_ACTIVITIES);
        createEReference(simRepActivitiesEClass, SIM_REP_ACTIVITIES__ACTIVITY);

        simRepActivityQueueEClass = createEClass(SIM_REP_ACTIVITY_QUEUE);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__QUEUE_ORDER);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__OBSERVED_CASES);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__CURRENT_SIZE);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__MAX_SIZE);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__AVERAGE_SIZE);
        createEAttribute(simRepActivityQueueEClass,
                SIM_REP_ACTIVITY_QUEUE__AVERAGE_WAIT);

        simRepActivityEClass = createEClass(SIM_REP_ACTIVITY);
        createEReference(simRepActivityEClass,
                SIM_REP_ACTIVITY__DURATION_DISTRIBUTION);
        createEReference(simRepActivityEClass, SIM_REP_ACTIVITY__ACTIVITY_QUEUE);
        createEReference(simRepActivityEClass, SIM_REP_ACTIVITY__ACTIVITY_COST);
        createEAttribute(simRepActivityEClass, SIM_REP_ACTIVITY__ID);
        createEAttribute(simRepActivityEClass, SIM_REP_ACTIVITY__NAME);
        createEAttribute(simRepActivityEClass,
                SIM_REP_ACTIVITY__AVERAGE_DURATION);

        simRepCasesEClass = createEClass(SIM_REP_CASES);
        createEReference(simRepCasesEClass,
                SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION);
        createEAttribute(simRepCasesEClass, SIM_REP_CASES__STARTED_CASES);
        createEAttribute(simRepCasesEClass, SIM_REP_CASES__FINISHED_CASES);
        createEAttribute(simRepCasesEClass, SIM_REP_CASES__AVERAGE_CASE_TIME);
        createEAttribute(simRepCasesEClass, SIM_REP_CASES__MIN_CASE_TIME);
        createEAttribute(simRepCasesEClass, SIM_REP_CASES__MAX_CASE_TIME);
        createEReference(simRepCasesEClass, SIM_REP_CASES__CASE_COST);

        simRepCostEClass = createEClass(SIM_REP_COST);
        createEAttribute(simRepCostEClass, SIM_REP_COST__AVERAGE_COST);
        createEAttribute(simRepCostEClass, SIM_REP_COST__MIN_COST);
        createEAttribute(simRepCostEClass, SIM_REP_COST__MAX_COST);
        createEAttribute(simRepCostEClass,
                SIM_REP_COST__CUMULATIVE_AVERAGE_COST);

        simRepDistributionEClass = createEClass(SIM_REP_DISTRIBUTION);
        createEAttribute(simRepDistributionEClass,
                SIM_REP_DISTRIBUTION__LAST_RESET_TIME);
        createEAttribute(simRepDistributionEClass,
                SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS);
        createEAttribute(simRepDistributionEClass,
                SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY);
        createEAttribute(simRepDistributionEClass,
                SIM_REP_DISTRIBUTION__PARAMETER1);
        createEAttribute(simRepDistributionEClass,
                SIM_REP_DISTRIBUTION__PARAMETER2);
        createEAttribute(simRepDistributionEClass, SIM_REP_DISTRIBUTION__SEED);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EXPERIMENT);

        simRepExperimentDataEClass = createEClass(SIM_REP_EXPERIMENT_DATA);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__EXPERIMENT_STATE);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__SIMULATION_TIME);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__REFERENCE_TIME_UNIT);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__REFERENCE_START_TIME);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__REAL_TIME);
        createEAttribute(simRepExperimentDataEClass,
                SIM_REP_EXPERIMENT_DATA__REFERENCE_COST_UNIT);

        simRepExperimentEClass = createEClass(SIM_REP_EXPERIMENT);
        createEReference(simRepExperimentEClass,
                SIM_REP_EXPERIMENT__EXPERIMENT_DATA);
        createEReference(simRepExperimentEClass, SIM_REP_EXPERIMENT__CASES);
        createEReference(simRepExperimentEClass,
                SIM_REP_EXPERIMENT__PARTICIPANTS);
        createEReference(simRepExperimentEClass, SIM_REP_EXPERIMENT__ACTIVITIES);
        createEAttribute(simRepExperimentEClass, SIM_REP_EXPERIMENT__ID);
        createEAttribute(simRepExperimentEClass, SIM_REP_EXPERIMENT__NAME);
        createEAttribute(simRepExperimentEClass, SIM_REP_EXPERIMENT__PACKAGE_ID);
        createEAttribute(simRepExperimentEClass, SIM_REP_EXPERIMENT__PROCESS_ID);
        createEAttribute(simRepExperimentEClass,
                SIM_REP_EXPERIMENT__PROCESS_NAME);
        createEAttribute(simRepExperimentEClass,
                SIM_REP_EXPERIMENT__PROCESS_LABEL);
        createEAttribute(simRepExperimentEClass,
                SIM_REP_EXPERIMENT__PACKAGE_NAME);

        simRepParticipantsEClass = createEClass(SIM_REP_PARTICIPANTS);
        createEReference(simRepParticipantsEClass,
                SIM_REP_PARTICIPANTS__PARTICIPANT);

        simRepParticipantEClass = createEClass(SIM_REP_PARTICIPANT);
        createEAttribute(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__NO_OF_INSTANCES);
        createEAttribute(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__IDLE_INSTANCES);
        createEAttribute(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__AVERAGE_IDLE);
        createEAttribute(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME);
        createEAttribute(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__AVERAGE_BUSY_TIME);
        createEReference(simRepParticipantEClass,
                SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE);
        createEAttribute(simRepParticipantEClass, SIM_REP_PARTICIPANT__ID);
        createEAttribute(simRepParticipantEClass, SIM_REP_PARTICIPANT__NAME);

        // Create enums
        simRepDistributionCategoryEEnum =
                createEEnum(SIM_REP_DISTRIBUTION_CATEGORY);
        simRepExperimentStateEEnum = createEEnum(SIM_REP_EXPERIMENT_STATE);
        simRepQueueOrderEEnum = createEEnum(SIM_REP_QUEUE_ORDER);
        simRepReferenceTimeUnitEEnum = createEEnum(SIM_REP_REFERENCE_TIME_UNIT);

        // Create data types
        simRepDistributionCategoryObjectEDataType =
                createEDataType(SIM_REP_DISTRIBUTION_CATEGORY_OBJECT);
        simRepExperimentStateObjectEDataType =
                createEDataType(SIM_REP_EXPERIMENT_STATE_OBJECT);
        simRepQueueOrderObjectEDataType =
                createEDataType(SIM_REP_QUEUE_ORDER_OBJECT);
        simRepReferenceTimeUnitObjectEDataType =
                createEDataType(SIM_REP_REFERENCE_TIME_UNIT_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE
                        .getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(simRepActivitiesEClass,
                SimRepActivities.class,
                "SimRepActivities", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimRepActivities_Activity(),
                this.getSimRepActivity(),
                null,
                "activity", null, 0, -1, SimRepActivities.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepActivityQueueEClass,
                SimRepActivityQueue.class,
                "SimRepActivityQueue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSimRepActivityQueue_QueueOrder(),
                this.getSimRepQueueOrder(),
                "queueOrder", "FIFO", 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getSimRepActivityQueue_ObservedCases(),
                theXMLTypePackage.getInt(),
                "observedCases", null, 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivityQueue_CurrentSize(),
                theXMLTypePackage.getInt(),
                "currentSize", null, 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivityQueue_MaxSize(),
                theXMLTypePackage.getInt(),
                "maxSize", null, 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivityQueue_AverageSize(),
                theXMLTypePackage.getDouble(),
                "averageSize", null, 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivityQueue_AverageWait(),
                theXMLTypePackage.getDouble(),
                "averageWait", null, 1, 1, SimRepActivityQueue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepActivityEClass,
                SimRepActivity.class,
                "SimRepActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimRepActivity_DurationDistribution(),
                this.getSimRepDistribution(),
                null,
                "durationDistribution", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepActivity_ActivityQueue(),
                this.getSimRepActivityQueue(),
                null,
                "activityQueue", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepActivity_ActivityCost(),
                this.getSimRepCost(),
                null,
                "activityCost", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivity_Id(),
                theXMLTypePackage.getString(),
                "id", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivity_Name(),
                theXMLTypePackage.getString(),
                "name", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepActivity_AverageDuration(),
                theXMLTypePackage.getDouble(),
                "averageDuration", null, 1, 1, SimRepActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepCasesEClass,
                SimRepCases.class,
                "SimRepCases", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimRepCases_CaseStartIntervalDistribution(),
                this.getSimRepDistribution(),
                null,
                "caseStartIntervalDistribution", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCases_StartedCases(),
                theXMLTypePackage.getInt(),
                "startedCases", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCases_FinishedCases(),
                theXMLTypePackage.getInt(),
                "finishedCases", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCases_AverageCaseTime(),
                theXMLTypePackage.getDouble(),
                "averageCaseTime", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCases_MinCaseTime(),
                theXMLTypePackage.getDouble(),
                "minCaseTime", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCases_MaxCaseTime(),
                theXMLTypePackage.getDouble(),
                "maxCaseTime", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepCases_CaseCost(),
                this.getSimRepCost(),
                null,
                "caseCost", null, 1, 1, SimRepCases.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepCostEClass,
                SimRepCost.class,
                "SimRepCost", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSimRepCost_AverageCost(),
                theXMLTypePackage.getDouble(),
                "averageCost", null, 1, 1, SimRepCost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCost_MinCost(),
                theXMLTypePackage.getDouble(),
                "minCost", null, 1, 1, SimRepCost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCost_MaxCost(),
                theXMLTypePackage.getDouble(),
                "maxCost", null, 1, 1, SimRepCost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepCost_CumulativeAverageCost(),
                theXMLTypePackage.getDouble(),
                "cumulativeAverageCost", null, 1, 1, SimRepCost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepDistributionEClass,
                SimRepDistribution.class,
                "SimRepDistribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSimRepDistribution_LastResetTime(),
                theXMLTypePackage.getDouble(),
                "lastResetTime", null, 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepDistribution_ObservedElements(),
                theXMLTypePackage.getLong(),
                "observedElements", null, 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepDistribution_DistributionCategory(),
                this.getSimRepDistributionCategory(),
                "distributionCategory", "REAL_CONSTANT", 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getSimRepDistribution_Parameter1(),
                theXMLTypePackage.getDouble(),
                "parameter1", null, 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepDistribution_Parameter2(),
                theXMLTypePackage.getDouble(),
                "parameter2", null, 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepDistribution_Seed(),
                theXMLTypePackage.getLong(),
                "seed", null, 1, 1, SimRepDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getDocumentRoot_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_Experiment(),
                this.getSimRepExperiment(),
                null,
                "experiment", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepExperimentDataEClass,
                SimRepExperimentData.class,
                "SimRepExperimentData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSimRepExperimentData_ExperimentState(),
                this.getSimRepExperimentState(),
                "experimentState", "NOT_STARTED", 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getSimRepExperimentData_SimulationTime(),
                theXMLTypePackage.getDouble(),
                "simulationTime", null, 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperimentData_ReferenceTimeUnit(),
                this.getSimRepReferenceTimeUnit(),
                "referenceTimeUnit", "SECOND", 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(getSimRepExperimentData_ReferenceStartTime(),
                theXMLTypePackage.getString(),
                "referenceStartTime", null, 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperimentData_RealTime(),
                theXMLTypePackage.getString(),
                "realTime", null, 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperimentData_ReferenceCostUnit(),
                theXMLTypePackage.getString(),
                "referenceCostUnit", null, 1, 1, SimRepExperimentData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepExperimentEClass,
                SimRepExperiment.class,
                "SimRepExperiment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimRepExperiment_ExperimentData(),
                this.getSimRepExperimentData(),
                null,
                "experimentData", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepExperiment_Cases(),
                this.getSimRepCases(),
                null,
                "cases", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepExperiment_Participants(),
                this.getSimRepParticipants(),
                null,
                "participants", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepExperiment_Activities(),
                this.getSimRepActivities(),
                null,
                "activities", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_Id(),
                theXMLTypePackage.getString(),
                "id", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_Name(),
                theXMLTypePackage.getString(),
                "name", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_PackageId(),
                theXMLTypePackage.getString(),
                "packageId", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_ProcessId(),
                theXMLTypePackage.getString(),
                "processId", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_ProcessName(),
                theXMLTypePackage.getString(),
                "processName", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_ProcessLabel(),
                theXMLTypePackage.getString(),
                "processLabel", null, 0, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepExperiment_PackageName(),
                theXMLTypePackage.getString(),
                "packageName", null, 1, 1, SimRepExperiment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepParticipantsEClass,
                SimRepParticipants.class,
                "SimRepParticipants", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getSimRepParticipants_Participant(),
                this.getSimRepParticipant(),
                null,
                "participant", null, 0, -1, SimRepParticipants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(simRepParticipantEClass,
                SimRepParticipant.class,
                "SimRepParticipant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_NoOfInstances(),
                theXMLTypePackage.getInt(),
                "noOfInstances", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_IdleInstances(),
                theXMLTypePackage.getInt(),
                "idleInstances", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_AverageIdle(),
                theXMLTypePackage.getDouble(),
                "averageIdle", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_AverageIdleTime(),
                theXMLTypePackage.getDouble(),
                "averageIdleTime", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_AverageBusyTime(),
                theXMLTypePackage.getDouble(),
                "averageBusyTime", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getSimRepParticipant_CostOfWorkForCase(),
                this.getSimRepCost(),
                null,
                "costOfWorkForCase", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_Id(),
                theXMLTypePackage.getString(),
                "id", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getSimRepParticipant_Name(),
                theXMLTypePackage.getString(),
                "name", null, 1, 1, SimRepParticipant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.class,
                "SimRepDistributionCategory"); //$NON-NLS-1$
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.REAL_CONSTANT_LITERAL);
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.REAL_UNIFORM_LITERAL);
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.REAL_NORMAL_LITERAL);
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.REAL_EXPONENTIAL_LITERAL);
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.PARAMETER_BASED_LITERAL);
        addEEnumLiteral(simRepDistributionCategoryEEnum,
                SimRepDistributionCategory.EMPIRICAL_LITERAL);

        initEEnum(simRepExperimentStateEEnum,
                SimRepExperimentState.class,
                "SimRepExperimentState"); //$NON-NLS-1$
        addEEnumLiteral(simRepExperimentStateEEnum,
                SimRepExperimentState.NOT_STARTED_LITERAL);
        addEEnumLiteral(simRepExperimentStateEEnum,
                SimRepExperimentState.RUNNING_LITERAL);
        addEEnumLiteral(simRepExperimentStateEEnum,
                SimRepExperimentState.PAUSED_LITERAL);
        addEEnumLiteral(simRepExperimentStateEEnum,
                SimRepExperimentState.FINISHED_LITERAL);
        addEEnumLiteral(simRepExperimentStateEEnum,
                SimRepExperimentState.ABORTED_LITERAL);

        initEEnum(simRepQueueOrderEEnum,
                SimRepQueueOrder.class,
                "SimRepQueueOrder"); //$NON-NLS-1$
        addEEnumLiteral(simRepQueueOrderEEnum, SimRepQueueOrder.FIFO_LITERAL);
        addEEnumLiteral(simRepQueueOrderEEnum, SimRepQueueOrder.LIFO_LITERAL);

        initEEnum(simRepReferenceTimeUnitEEnum,
                SimRepReferenceTimeUnit.class,
                "SimRepReferenceTimeUnit"); //$NON-NLS-1$
        addEEnumLiteral(simRepReferenceTimeUnitEEnum,
                SimRepReferenceTimeUnit.SECOND_LITERAL);
        addEEnumLiteral(simRepReferenceTimeUnitEEnum,
                SimRepReferenceTimeUnit.MINUTE_LITERAL);
        addEEnumLiteral(simRepReferenceTimeUnitEEnum,
                SimRepReferenceTimeUnit.HOUR_LITERAL);

        // Initialize data types
        initEDataType(simRepDistributionCategoryObjectEDataType,
                SimRepDistributionCategory.class,
                "SimRepDistributionCategoryObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(simRepExperimentStateObjectEDataType,
                SimRepExperimentState.class,
                "SimRepExperimentStateObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(simRepQueueOrderObjectEDataType,
                SimRepQueueOrder.class,
                "SimRepQueueOrderObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(simRepReferenceTimeUnitObjectEDataType,
                SimRepReferenceTimeUnit.class,
                "SimRepReferenceTimeUnitObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation(this, source, new String[] { "qualified", "false" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepActivitiesEClass, source, new String[] {
                "name", "Activities_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivities_Activity(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Activity", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepActivityQueueEClass, source, new String[] {
                "name", "ActivityQueueType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivityQueue_QueueOrder(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "QueueOrder", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepActivityQueue_ObservedCases(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ObservedCases", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepActivityQueue_CurrentSize(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CurrentSize", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepActivityQueue_MaxSize(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "MaxSize", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivityQueue_AverageSize(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AverageSize", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepActivityQueue_AverageWait(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AverageWait", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepActivityEClass, source, new String[] {
                "name", "ActivityType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivity_DurationDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DurationDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepActivity_ActivityQueue(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "ActivityQueue", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivity_ActivityCost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "ActivityCost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivity_Id(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Id", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivity_Name(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepActivity_AverageDuration(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AverageDuration", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepCasesEClass, source, new String[] {
                "name", "CasesType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_CaseStartIntervalDistribution(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CaseStartIntervalDistribution", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepCases_StartedCases(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "StartedCases", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_FinishedCases(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "FinishedCases", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_AverageCaseTime(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "AverageCaseTime", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_MinCaseTime(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "MinCaseTime", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_MaxCaseTime(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "MaxCaseTime", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCases_CaseCost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "CaseCost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepCostEClass, source, new String[] {
                "name", "CostType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCost_AverageCost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "AverageCost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCost_MinCost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "MinCost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCost_MaxCost(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "MaxCost", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepCost_CumulativeAverageCost(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CumulativeAverageCost", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepDistributionCategoryEEnum, source, new String[] {
                "name", "DistributionCategoryType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepDistributionCategoryObjectEDataType,
                source,
                new String[] { "name", "DistributionCategoryType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "DistributionCategoryType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepDistributionEClass, source, new String[] {
                "name", "DistributionType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepDistribution_LastResetTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "LastResetTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepDistribution_ObservedElements(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ObservedElements", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepDistribution_DistributionCategory(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DistributionCategory", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepDistribution_Parameter1(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Parameter1", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepDistribution_Parameter2(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Parameter2", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepDistribution_Seed(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Seed", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(documentRootEClass, source, new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_Mixed(), source, new String[] {
                "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_Experiment(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Experiment", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepExperimentDataEClass, source, new String[] {
                "name", "ExperimentDataType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperimentData_ExperimentState(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ExperimentState", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepExperimentData_SimulationTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "SimulationTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepExperimentData_ReferenceTimeUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReferenceTimeUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepExperimentData_ReferenceStartTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReferenceStartTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepExperimentData_RealTime(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "RealTime", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperimentData_ReferenceCostUnit(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ReferenceCostUnit", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepExperimentStateEEnum, source, new String[] {
                "name", "ExperimentStateType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepExperimentStateObjectEDataType,
                source,
                new String[] { "name", "ExperimentStateType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "ExperimentStateType" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepExperimentEClass, source, new String[] {
                "name", "ExperimentType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_ExperimentData(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "ExperimentData", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepExperiment_Cases(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Cases", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_Participants(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Participants", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_Activities(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Activities", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_Id(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Id", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_Name(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_PackageId(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "PackageId", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_ProcessId(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "ProcessId", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_ProcessName(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "ProcessName", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_ProcessLabel(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "ProcessLabel", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepExperiment_PackageName(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "PackageName", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepParticipantsEClass, source, new String[] {
                "name", "Participants_._type", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepParticipants_Participant(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "Participant", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(simRepParticipantEClass, source, new String[] {
                "name", "ParticipantType", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepParticipant_NoOfInstances(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "NoOfInstances", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepParticipant_IdleInstances(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "IdleInstances", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepParticipant_AverageIdle(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "AverageIdle", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepParticipant_AverageIdleTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AverageIdleTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepParticipant_AverageBusyTime(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "AverageBusyTime", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepParticipant_CostOfWorkForCase(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "CostOfWorkForCase", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getSimRepParticipant_Id(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Id", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getSimRepParticipant_Name(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepQueueOrderEEnum, source, new String[] {
                "name", "QueueOrderType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepQueueOrderObjectEDataType, source, new String[] {
                "name", "QueueOrderType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                "baseType", "QueueOrderType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepReferenceTimeUnitEEnum, source, new String[] {
                "name", "ReferenceTimeUnitType" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(simRepReferenceTimeUnitObjectEDataType,
                source,
                new String[] { "name", "ReferenceTimeUnitType:Object", //$NON-NLS-1$ //$NON-NLS-2$
                        "baseType", "ReferenceTimeUnitType" //$NON-NLS-1$ //$NON-NLS-2$
                });
    }

} //SimRepPackageImpl
