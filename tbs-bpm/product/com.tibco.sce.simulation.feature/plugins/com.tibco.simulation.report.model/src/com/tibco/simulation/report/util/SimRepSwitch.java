/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.util;

import com.tibco.simulation.report.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
 * @see com.tibco.simulation.report.SimRepPackage
 * @generated
 */
public class SimRepSwitch<T> extends Switch<T> {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static SimRepPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepSwitch() {
        if (modelPackage == null) {
            modelPackage = SimRepPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
        case SimRepPackage.SIM_REP_ACTIVITIES: {
            SimRepActivities simRepActivities = (SimRepActivities) theEObject;
            T result = caseSimRepActivities(simRepActivities);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_ACTIVITY_QUEUE: {
            SimRepActivityQueue simRepActivityQueue =
                    (SimRepActivityQueue) theEObject;
            T result = caseSimRepActivityQueue(simRepActivityQueue);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_ACTIVITY: {
            SimRepActivity simRepActivity = (SimRepActivity) theEObject;
            T result = caseSimRepActivity(simRepActivity);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_CASES: {
            SimRepCases simRepCases = (SimRepCases) theEObject;
            T result = caseSimRepCases(simRepCases);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_COST: {
            SimRepCost simRepCost = (SimRepCost) theEObject;
            T result = caseSimRepCost(simRepCost);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_DISTRIBUTION: {
            SimRepDistribution simRepDistribution =
                    (SimRepDistribution) theEObject;
            T result = caseSimRepDistribution(simRepDistribution);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.DOCUMENT_ROOT: {
            DocumentRoot documentRoot = (DocumentRoot) theEObject;
            T result = caseDocumentRoot(documentRoot);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_EXPERIMENT_DATA: {
            SimRepExperimentData simRepExperimentData =
                    (SimRepExperimentData) theEObject;
            T result = caseSimRepExperimentData(simRepExperimentData);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_EXPERIMENT: {
            SimRepExperiment simRepExperiment = (SimRepExperiment) theEObject;
            T result = caseSimRepExperiment(simRepExperiment);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_PARTICIPANTS: {
            SimRepParticipants simRepParticipants =
                    (SimRepParticipants) theEObject;
            T result = caseSimRepParticipants(simRepParticipants);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case SimRepPackage.SIM_REP_PARTICIPANT: {
            SimRepParticipant simRepParticipant =
                    (SimRepParticipant) theEObject;
            T result = caseSimRepParticipant(simRepParticipant);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activities</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activities</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepActivities(SimRepActivities object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity Queue</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity Queue</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepActivityQueue(SimRepActivityQueue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepActivity(SimRepActivity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cases</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cases</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepCases(SimRepCases object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cost</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cost</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepCost(SimRepCost object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Distribution</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Distribution</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepDistribution(SimRepDistribution object) {
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
     * Returns the result of interpreting the object as an instance of '<em>Experiment Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Experiment Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepExperimentData(SimRepExperimentData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Experiment</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Experiment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepExperiment(SimRepExperiment object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participants</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participants</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepParticipants(SimRepParticipants object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Participant</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Participant</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSimRepParticipant(SimRepParticipant object) {
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
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //SimRepSwitch
