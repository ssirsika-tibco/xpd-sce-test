/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Estimation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TimeEstimation#getWaitingTime <em>Waiting Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TimeEstimation#getWorkingTime <em>Working Time</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TimeEstimation#getDuration <em>Duration</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTimeEstimation()
 * @model extendedMetaData="name='TimeEstimation_._type' kind='elementOnly'"
 * @generated
 */
public interface TimeEstimation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Waiting Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Waiting Time</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Waiting Time</em>' containment reference.
     * @see #setWaitingTime(WaitingTime)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTimeEstimation_WaitingTime()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WaitingTime' namespace='##targetNamespace'"
     * @generated
     */
    WaitingTime getWaitingTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TimeEstimation#getWaitingTime <em>Waiting Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Waiting Time</em>' containment reference.
     * @see #getWaitingTime()
     * @generated
     */
    void setWaitingTime(WaitingTime value);

    /**
     * Returns the value of the '<em><b>Working Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Working Time</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Working Time</em>' containment reference.
     * @see #setWorkingTime(WorkingTime)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTimeEstimation_WorkingTime()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WorkingTime' namespace='##targetNamespace'"
     * @generated
     */
    WorkingTime getWorkingTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TimeEstimation#getWorkingTime <em>Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Working Time</em>' containment reference.
     * @see #getWorkingTime()
     * @generated
     */
    void setWorkingTime(WorkingTime value);

    /**
     * Returns the value of the '<em><b>Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Duration</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Duration</em>' containment reference.
     * @see #setDuration(Duration)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTimeEstimation_Duration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Duration' namespace='##targetNamespace'"
     * @generated
     */
    Duration getDuration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TimeEstimation#getDuration <em>Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Duration</em>' containment reference.
     * @see #getDuration()
     * @generated
     */
    void setDuration(Duration value);

} // TimeEstimation
