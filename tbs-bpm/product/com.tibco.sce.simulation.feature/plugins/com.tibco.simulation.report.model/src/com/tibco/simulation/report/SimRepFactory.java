/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.simulation.report.SimRepPackage
 * @generated
 */
public interface SimRepFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    SimRepFactory eINSTANCE =
            com.tibco.simulation.report.impl.SimRepFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Activities</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Activities</em>'.
     * @generated
     */
    SimRepActivities createSimRepActivities();

    /**
     * Returns a new object of class '<em>Activity Queue</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Activity Queue</em>'.
     * @generated
     */
    SimRepActivityQueue createSimRepActivityQueue();

    /**
     * Returns a new object of class '<em>Activity</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Activity</em>'.
     * @generated
     */
    SimRepActivity createSimRepActivity();

    /**
     * Returns a new object of class '<em>Cases</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Cases</em>'.
     * @generated
     */
    SimRepCases createSimRepCases();

    /**
     * Returns a new object of class '<em>Cost</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Cost</em>'.
     * @generated
     */
    SimRepCost createSimRepCost();

    /**
     * Returns a new object of class '<em>Distribution</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Distribution</em>'.
     * @generated
     */
    SimRepDistribution createSimRepDistribution();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Experiment Data</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Experiment Data</em>'.
     * @generated
     */
    SimRepExperimentData createSimRepExperimentData();

    /**
     * Returns a new object of class '<em>Experiment</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Experiment</em>'.
     * @generated
     */
    SimRepExperiment createSimRepExperiment();

    /**
     * Returns a new object of class '<em>Participants</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Participants</em>'.
     * @generated
     */
    SimRepParticipants createSimRepParticipants();

    /**
     * Returns a new object of class '<em>Participant</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Participant</em>'.
     * @generated
     */
    SimRepParticipant createSimRepParticipant();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    SimRepPackage getSimRepPackage();

} //SimRepFactory
