/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar;

import javax.xml.datatype.XMLGregorianCalendar;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getEndTime <em>End Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getTimeSlot()
 * @model extendedMetaData="name='TimeSlot' kind='elementOnly'"
 * @generated
 */
public interface TimeSlot extends EObject {
    /**
     * Returns the value of the '<em><b>Start Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Time</em>' attribute.
     * @see #setStartTime(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getTimeSlot_StartTime()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='StartTime'"
     * @generated
     */
    String getStartTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getStartTime <em>Start Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Time</em>' attribute.
     * @see #getStartTime()
     * @generated
     */
    void setStartTime(String value);

    /**
     * Returns the value of the '<em><b>End Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Time</em>' attribute.
     * @see #setEndTime(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getTimeSlot_EndTime()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='EndTime'"
     * @generated
     */
    String getEndTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getEndTime <em>End Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Time</em>' attribute.
     * @see #getEndTime()
     * @generated
     */
    void setEndTime(String value);

} // TimeSlot
