/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage
 * @generated
 */
public interface CalendarFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CalendarFactory eINSTANCE = com.tibco.xpd.scheduling.calendar.impl.CalendarFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Calculated Holiday Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Calculated Holiday Type</em>'.
     * @generated
     */
    CalculatedHolidayType createCalculatedHolidayType();

    /**
     * Returns a new object of class '<em>Calendar</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Calendar</em>'.
     * @generated
     */
    Calendar createCalendar();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Public Holidays</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Public Holidays</em>'.
     * @generated
     */
    PublicHolidays createPublicHolidays();

    /**
     * Returns a new object of class '<em>Single Holiday Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Single Holiday Type</em>'.
     * @generated
     */
    SingleHolidayType createSingleHolidayType();

    /**
     * Returns a new object of class '<em>Time Slot</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Time Slot</em>'.
     * @generated
     */
    TimeSlot createTimeSlot();

    /**
     * Returns a new object of class '<em>Working Day</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Working Day</em>'.
     * @generated
     */
    WorkingDay createWorkingDay();

    /**
     * Returns a new object of class '<em>Working Schedule</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Working Schedule</em>'.
     * @generated
     */
    WorkingSchedule createWorkingSchedule();

    /**
     * Returns a new object of class '<em>Working Time</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Working Time</em>'.
     * @generated
     */
    WorkingTime createWorkingTime();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    CalendarPackage getCalendarPackage();

} //CalendarFactory
