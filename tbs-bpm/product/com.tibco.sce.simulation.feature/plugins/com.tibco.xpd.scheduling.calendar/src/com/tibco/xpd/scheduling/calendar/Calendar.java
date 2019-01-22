/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Calendar</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.Calendar#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.Calendar#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.Calendar#getDefaultWorkingSchedule <em>Default Working Schedule</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.Calendar#getPublicHolidays <em>Public Holidays</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalendar()
 * @model extendedMetaData="name='Calendar' kind='elementOnly'"
 * @generated
 */
public interface Calendar extends EObject{
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalendar_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.Calendar#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Locale</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Locale</em>' attribute.
     * @see #setLocale(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalendar_Locale()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Locale'"
     * @generated
     */
    String getLocale();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.Calendar#getLocale <em>Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Locale</em>' attribute.
     * @see #getLocale()
     * @generated
     */
    void setLocale(String value);

    /**
     * Returns the value of the '<em><b>Default Working Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Working Schedule</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Working Schedule</em>' containment reference.
     * @see #setDefaultWorkingSchedule(WorkingSchedule)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalendar_DefaultWorkingSchedule()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='DefaultWorkingSchedule'"
     * @generated
     */
    WorkingSchedule getDefaultWorkingSchedule();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.Calendar#getDefaultWorkingSchedule <em>Default Working Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Working Schedule</em>' containment reference.
     * @see #getDefaultWorkingSchedule()
     * @generated
     */
    void setDefaultWorkingSchedule(WorkingSchedule value);

    /**
     * Returns the value of the '<em><b>Public Holidays</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Public Holidays</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Public Holidays</em>' containment reference.
     * @see #setPublicHolidays(PublicHolidays)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalendar_PublicHolidays()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PublicHolidays'"
     * @generated
     */
    PublicHolidays getPublicHolidays();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.Calendar#getPublicHolidays <em>Public Holidays</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Public Holidays</em>' containment reference.
     * @see #getPublicHolidays()
     * @generated
     */
    void setPublicHolidays(PublicHolidays value);

} // Calendar
