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
 * A representation of the model object '<em><b>Working Day</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek <em>Day Of Week</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getWorkingTime <em>Working Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingDay()
 * @model extendedMetaData="name='WorkingDay' kind='elementOnly'"
 * @generated
 */
public interface WorkingDay extends EObject {
    /**
     * Returns the value of the '<em><b>Day Of Week</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Day Of Week</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Day Of Week</em>' attribute.
     * @see #isSetDayOfWeek()
     * @see #unsetDayOfWeek()
     * @see #setDayOfWeek(int)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingDay_DayOfWeek()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='DayOfWeek'"
     * @generated
     */
    int getDayOfWeek();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek <em>Day Of Week</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Day Of Week</em>' attribute.
     * @see #isSetDayOfWeek()
     * @see #unsetDayOfWeek()
     * @see #getDayOfWeek()
     * @generated
     */
    void setDayOfWeek(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek <em>Day Of Week</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDayOfWeek()
     * @see #getDayOfWeek()
     * @see #setDayOfWeek(int)
     * @generated
     */
    void unsetDayOfWeek();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek <em>Day Of Week</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Day Of Week</em>' attribute is set.
     * @see #unsetDayOfWeek()
     * @see #getDayOfWeek()
     * @see #setDayOfWeek(int)
     * @generated
     */
    boolean isSetDayOfWeek();

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
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getWorkingDay_WorkingTime()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WorkingTime'"
     * @generated
     */
    WorkingTime getWorkingTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getWorkingTime <em>Working Time</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Working Time</em>' containment reference.
     * @see #getWorkingTime()
     * @generated
     */
    void setWorkingTime(WorkingTime value);

} // WorkingDay
