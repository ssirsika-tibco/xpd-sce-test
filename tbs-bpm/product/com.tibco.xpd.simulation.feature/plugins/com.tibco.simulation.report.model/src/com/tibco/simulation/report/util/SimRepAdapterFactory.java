/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.util;

import com.tibco.simulation.report.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage
 * @generated
 */
public class SimRepAdapterFactory extends AdapterFactoryImpl {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SimRepPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = SimRepPackage.eINSTANCE;
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
    protected SimRepSwitch<Adapter> modelSwitch = new SimRepSwitch<Adapter>() {
        @Override
        public Adapter caseSimRepActivities(SimRepActivities object) {
            return createSimRepActivitiesAdapter();
        }

        @Override
        public Adapter caseSimRepActivityQueue(SimRepActivityQueue object) {
            return createSimRepActivityQueueAdapter();
        }

        @Override
        public Adapter caseSimRepActivity(SimRepActivity object) {
            return createSimRepActivityAdapter();
        }

        @Override
        public Adapter caseSimRepCases(SimRepCases object) {
            return createSimRepCasesAdapter();
        }

        @Override
        public Adapter caseSimRepCost(SimRepCost object) {
            return createSimRepCostAdapter();
        }

        @Override
        public Adapter caseSimRepDistribution(SimRepDistribution object) {
            return createSimRepDistributionAdapter();
        }

        @Override
        public Adapter caseDocumentRoot(DocumentRoot object) {
            return createDocumentRootAdapter();
        }

        @Override
        public Adapter caseSimRepExperimentData(SimRepExperimentData object) {
            return createSimRepExperimentDataAdapter();
        }

        @Override
        public Adapter caseSimRepExperiment(SimRepExperiment object) {
            return createSimRepExperimentAdapter();
        }

        @Override
        public Adapter caseSimRepParticipants(SimRepParticipants object) {
            return createSimRepParticipantsAdapter();
        }

        @Override
        public Adapter caseSimRepParticipant(SimRepParticipant object) {
            return createSimRepParticipantAdapter();
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
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepActivities
     * @generated
     */
    public Adapter createSimRepActivitiesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepActivityQueue <em>Activity Queue</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepActivityQueue
     * @generated
     */
    public Adapter createSimRepActivityQueueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepActivity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepActivity
     * @generated
     */
    public Adapter createSimRepActivityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepCases <em>Cases</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepCases
     * @generated
     */
    public Adapter createSimRepCasesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepCost <em>Cost</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepCost
     * @generated
     */
    public Adapter createSimRepCostAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepDistribution <em>Distribution</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepDistribution
     * @generated
     */
    public Adapter createSimRepDistributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.DocumentRoot
     * @generated
     */
    public Adapter createDocumentRootAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepExperimentData <em>Experiment Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepExperimentData
     * @generated
     */
    public Adapter createSimRepExperimentDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepExperiment <em>Experiment</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepExperiment
     * @generated
     */
    public Adapter createSimRepExperimentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepParticipants <em>Participants</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepParticipants
     * @generated
     */
    public Adapter createSimRepParticipantsAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link com.tibco.simulation.report.SimRepParticipant <em>Participant</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see com.tibco.simulation.report.SimRepParticipant
     * @generated
     */
    public Adapter createSimRepParticipantAdapter() {
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

} //SimRepAdapterFactory
