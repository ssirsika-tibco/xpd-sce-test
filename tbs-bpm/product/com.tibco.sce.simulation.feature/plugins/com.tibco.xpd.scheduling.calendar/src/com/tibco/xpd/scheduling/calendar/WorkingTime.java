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
 * A representation of the model object '<em><b>Working Time</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.WorkingTime#getTimeSlot <em>Time Slot</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingTime()
 * @model extendedMetaData="name='WorkingTime' kind='elementOnly'"
 * @generated
 */
public interface WorkingTime extends EObject {
    /**
     * Returns the value of the '<em><b>Time Slot</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.scheduling.calendar.TimeSlot}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Slot</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Slot</em>' containment reference list.
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingTime_TimeSlot()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TimeSlot'"
     * @generated
     */
    EList<TimeSlot> getTimeSlot();

} // WorkingTime
