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
 * A representation of the model object '<em><b>Public Holidays</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.PublicHolidays#getSingleHoliday <em>Single Holiday</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.PublicHolidays#getCalculatedHoliday <em>Calculated Holiday</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getPublicHolidays()
 * @model extendedMetaData="name='PublicHolidays' kind='elementOnly'"
 * @generated
 */
public interface PublicHolidays extends EObject{
    /**
     * Returns the value of the '<em><b>Single Holiday</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.scheduling.calendar.SingleHolidayType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Single Holiday</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Single Holiday</em>' containment reference list.
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getPublicHolidays_SingleHoliday()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SingleHoliday'"
     * @generated
     */
    EList<SingleHolidayType> getSingleHoliday();

    /**
     * Returns the value of the '<em><b>Calculated Holiday</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Calculated Holiday</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Calculated Holiday</em>' containment reference list.
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getPublicHolidays_CalculatedHoliday()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CalculatedHoliday'"
     * @generated
     */
    EList<CalculatedHolidayType> getCalculatedHoliday();

} // PublicHolidays
