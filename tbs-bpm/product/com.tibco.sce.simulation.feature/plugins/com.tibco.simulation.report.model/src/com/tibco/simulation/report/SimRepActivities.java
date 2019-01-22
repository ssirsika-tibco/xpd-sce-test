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
 * A representation of the model object '<em><b>Activities</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.SimRepActivities#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivities()
 * @model extendedMetaData="name='Activities_._type' kind='elementOnly'"
 * @generated
 */
public interface SimRepActivities extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.simulation.report.SimRepActivity}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Statistics tor activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity</em>' containment reference list.
     * @see com.tibco.simulation.report.SimRepPackage#getSimRepActivities_Activity()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Activity' namespace='##targetNamespace'"
     * @generated
     */
    EList<SimRepActivity> getActivity();

} // SimRepActivities
