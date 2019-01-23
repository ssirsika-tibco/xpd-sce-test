/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Participants</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepParticipants#getParticipant <em>Participant</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipants()
 * @model extendedMetaData="name='Participants_._type' kind='elementOnly'"
 * @generated
 */
public interface SimRepParticipants extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Participant</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.simulation.report.SimRepParticipant}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Statistics for participant.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Participant</em>' containment reference list.
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepParticipants_Participant()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Participant' namespace='##targetNamespace'"
     * @generated
     */
    EList<SimRepParticipant> getParticipant();

} // SimRepParticipants
