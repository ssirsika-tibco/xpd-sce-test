/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Working Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule#getWorkingDay <em>Working Day</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule#getDefaultWorkingTime <em>Default Working Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingSchedule()
 * @model extendedMetaData="name='WorkingSchedule' kind='elementOnly'"
 * @generated
 */
public interface WorkingSchedule extends EObject {
    /**
     * Returns the value of the '<em><b>Working Day</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.scheduling.calendar.WorkingDay}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Working Day</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Working Day</em>' containment reference list.
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingSchedule_WorkingDay()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkingDay'"
     * @generated
     */
    EList<WorkingDay> getWorkingDay();

    /**
     * Returns the value of the '<em><b>Default Working Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Working Time</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Working Time</em>' containment reference.
     * @see #setDefaultWorkingTime(WorkingTime)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingSchedule_DefaultWorkingTime()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='DefaultWorkingTime'"
     * @generated
     */
    WorkingTime getDefaultWorkingTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule#getDefaultWorkingTime <em>Default Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Working Time</em>' containment reference.
     * @see #getDefaultWorkingTime()
     * @generated
     */
    void setDefaultWorkingTime(WorkingTime value);

} // WorkingSchedule
