/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CalendarFactoryImpl extends EFactoryImpl implements CalendarFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static CalendarFactory init() {
        try {
            CalendarFactory theCalendarFactory = (CalendarFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/calendar"); 
            if (theCalendarFactory != null) {
                return theCalendarFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CalendarFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalendarFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case CalendarPackage.CALCULATED_HOLIDAY_TYPE: return createCalculatedHolidayType();
            case CalendarPackage.CALENDAR: return createCalendar();
            case CalendarPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case CalendarPackage.PUBLIC_HOLIDAYS: return createPublicHolidays();
            case CalendarPackage.SINGLE_HOLIDAY_TYPE: return createSingleHolidayType();
            case CalendarPackage.TIME_SLOT: return createTimeSlot();
            case CalendarPackage.WORKING_DAY: return createWorkingDay();
            case CalendarPackage.WORKING_SCHEDULE: return createWorkingSchedule();
            case CalendarPackage.WORKING_TIME: return createWorkingTime();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalculatedHolidayType createCalculatedHolidayType() {
        CalculatedHolidayTypeImpl calculatedHolidayType = new CalculatedHolidayTypeImpl();
        return calculatedHolidayType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Calendar createCalendar() {
        CalendarImpl calendar = new CalendarImpl();
        return calendar;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PublicHolidays createPublicHolidays() {
        PublicHolidaysImpl publicHolidays = new PublicHolidaysImpl();
        return publicHolidays;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SingleHolidayType createSingleHolidayType() {
        SingleHolidayTypeImpl singleHolidayType = new SingleHolidayTypeImpl();
        return singleHolidayType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeSlot createTimeSlot() {
        TimeSlotImpl timeSlot = new TimeSlotImpl();
        return timeSlot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingDay createWorkingDay() {
        WorkingDayImpl workingDay = new WorkingDayImpl();
        return workingDay;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingSchedule createWorkingSchedule() {
        WorkingScheduleImpl workingSchedule = new WorkingScheduleImpl();
        return workingSchedule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkingTime createWorkingTime() {
        WorkingTimeImpl workingTime = new WorkingTimeImpl();
        return workingTime;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalendarPackage getCalendarPackage() {
        return (CalendarPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static CalendarPackage getPackage() {
        return CalendarPackage.eINSTANCE;
    }

} //CalendarFactoryImpl
