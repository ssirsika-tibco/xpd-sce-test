/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimRepFactoryImpl extends EFactoryImpl implements SimRepFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static SimRepFactory init() {
        try {
            SimRepFactory theSimRepFactory =
                    (SimRepFactory) EPackage.Registry.INSTANCE
                            .getEFactory("http://www.tibco.com/models/SimulationReport.xsd"); //$NON-NLS-1$ 
            if (theSimRepFactory != null) {
                return theSimRepFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SimRepFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepFactoryImpl() {
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
        case SimRepPackage.SIM_REP_ACTIVITIES:
            return createSimRepActivities();
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE:
            return createSimRepActivityQueue();
        case SimRepPackage.SIM_REP_ACTIVITY:
            return createSimRepActivity();
        case SimRepPackage.SIM_REP_CASES:
            return createSimRepCases();
        case SimRepPackage.SIM_REP_COST:
            return createSimRepCost();
        case SimRepPackage.SIM_REP_DISTRIBUTION:
            return createSimRepDistribution();
        case SimRepPackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA:
            return createSimRepExperimentData();
        case SimRepPackage.SIM_REP_EXPERIMENT:
            return createSimRepExperiment();
        case SimRepPackage.SIM_REP_PARTICIPANTS:
            return createSimRepParticipants();
        case SimRepPackage.SIM_REP_PARTICIPANT:
            return createSimRepParticipant();
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
        case SimRepPackage.SIM_REP_DISTRIBUTION_CATEGORY:
            return createSimRepDistributionCategoryFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_EXPERIMENT_STATE:
            return createSimRepExperimentStateFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_QUEUE_ORDER:
            return createSimRepQueueOrderFromString(eDataType, initialValue);
        case SimRepPackage.SIM_REP_REFERENCE_TIME_UNIT:
            return createSimRepReferenceTimeUnitFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_DISTRIBUTION_CATEGORY_OBJECT:
            return createSimRepDistributionCategoryObjectFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_EXPERIMENT_STATE_OBJECT:
            return createSimRepExperimentStateObjectFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_QUEUE_ORDER_OBJECT:
            return createSimRepQueueOrderObjectFromString(eDataType,
                    initialValue);
        case SimRepPackage.SIM_REP_REFERENCE_TIME_UNIT_OBJECT:
            return createSimRepReferenceTimeUnitObjectFromString(eDataType,
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
        case SimRepPackage.SIM_REP_DISTRIBUTION_CATEGORY:
            return convertSimRepDistributionCategoryToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_EXPERIMENT_STATE:
            return convertSimRepExperimentStateToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_QUEUE_ORDER:
            return convertSimRepQueueOrderToString(eDataType, instanceValue);
        case SimRepPackage.SIM_REP_REFERENCE_TIME_UNIT:
            return convertSimRepReferenceTimeUnitToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_DISTRIBUTION_CATEGORY_OBJECT:
            return convertSimRepDistributionCategoryObjectToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_EXPERIMENT_STATE_OBJECT:
            return convertSimRepExperimentStateObjectToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_QUEUE_ORDER_OBJECT:
            return convertSimRepQueueOrderObjectToString(eDataType,
                    instanceValue);
        case SimRepPackage.SIM_REP_REFERENCE_TIME_UNIT_OBJECT:
            return convertSimRepReferenceTimeUnitObjectToString(eDataType,
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
    public SimRepActivities createSimRepActivities() {
        SimRepActivitiesImpl simRepActivities = new SimRepActivitiesImpl();
        return simRepActivities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepActivityQueue createSimRepActivityQueue() {
        SimRepActivityQueueImpl simRepActivityQueue =
                new SimRepActivityQueueImpl();
        return simRepActivityQueue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepActivity createSimRepActivity() {
        SimRepActivityImpl simRepActivity = new SimRepActivityImpl();
        return simRepActivity;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCases createSimRepCases() {
        SimRepCasesImpl simRepCases = new SimRepCasesImpl();
        return simRepCases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCost createSimRepCost() {
        SimRepCostImpl simRepCost = new SimRepCostImpl();
        return simRepCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistribution createSimRepDistribution() {
        SimRepDistributionImpl simRepDistribution =
                new SimRepDistributionImpl();
        return simRepDistribution;
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
    public SimRepExperimentData createSimRepExperimentData() {
        SimRepExperimentDataImpl simRepExperimentData =
                new SimRepExperimentDataImpl();
        return simRepExperimentData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepExperiment createSimRepExperiment() {
        SimRepExperimentImpl simRepExperiment = new SimRepExperimentImpl();
        return simRepExperiment;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepParticipants createSimRepParticipants() {
        SimRepParticipantsImpl simRepParticipants =
                new SimRepParticipantsImpl();
        return simRepParticipants;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepParticipant createSimRepParticipant() {
        SimRepParticipantImpl simRepParticipant = new SimRepParticipantImpl();
        return simRepParticipant;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistributionCategory createSimRepDistributionCategoryFromString(
            EDataType eDataType, String initialValue) {
        SimRepDistributionCategory result =
                SimRepDistributionCategory.get(initialValue);
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
    public String convertSimRepDistributionCategoryToString(
            EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepExperimentState createSimRepExperimentStateFromString(
            EDataType eDataType, String initialValue) {
        SimRepExperimentState result = SimRepExperimentState.get(initialValue);
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
    public String convertSimRepExperimentStateToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepQueueOrder createSimRepQueueOrderFromString(
            EDataType eDataType, String initialValue) {
        SimRepQueueOrder result = SimRepQueueOrder.get(initialValue);
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
    public String convertSimRepQueueOrderToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepReferenceTimeUnit createSimRepReferenceTimeUnitFromString(
            EDataType eDataType, String initialValue) {
        SimRepReferenceTimeUnit result =
                SimRepReferenceTimeUnit.get(initialValue);
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
    public String convertSimRepReferenceTimeUnitToString(EDataType eDataType,
            Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepDistributionCategory createSimRepDistributionCategoryObjectFromString(
            EDataType eDataType, String initialValue) {
        return createSimRepDistributionCategoryFromString(SimRepPackage.Literals.SIM_REP_DISTRIBUTION_CATEGORY,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSimRepDistributionCategoryObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertSimRepDistributionCategoryToString(SimRepPackage.Literals.SIM_REP_DISTRIBUTION_CATEGORY,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepExperimentState createSimRepExperimentStateObjectFromString(
            EDataType eDataType, String initialValue) {
        return createSimRepExperimentStateFromString(SimRepPackage.Literals.SIM_REP_EXPERIMENT_STATE,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSimRepExperimentStateObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertSimRepExperimentStateToString(SimRepPackage.Literals.SIM_REP_EXPERIMENT_STATE,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepQueueOrder createSimRepQueueOrderObjectFromString(
            EDataType eDataType, String initialValue) {
        return createSimRepQueueOrderFromString(SimRepPackage.Literals.SIM_REP_QUEUE_ORDER,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSimRepQueueOrderObjectToString(EDataType eDataType,
            Object instanceValue) {
        return convertSimRepQueueOrderToString(SimRepPackage.Literals.SIM_REP_QUEUE_ORDER,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepReferenceTimeUnit createSimRepReferenceTimeUnitObjectFromString(
            EDataType eDataType, String initialValue) {
        return createSimRepReferenceTimeUnitFromString(SimRepPackage.Literals.SIM_REP_REFERENCE_TIME_UNIT,
                initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSimRepReferenceTimeUnitObjectToString(
            EDataType eDataType, Object instanceValue) {
        return convertSimRepReferenceTimeUnitToString(SimRepPackage.Literals.SIM_REP_REFERENCE_TIME_UNIT,
                instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepPackage getSimRepPackage() {
        return (SimRepPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SimRepPackage getPackage() {
        return SimRepPackage.eINSTANCE;
    }

} //SimRepFactoryImpl
