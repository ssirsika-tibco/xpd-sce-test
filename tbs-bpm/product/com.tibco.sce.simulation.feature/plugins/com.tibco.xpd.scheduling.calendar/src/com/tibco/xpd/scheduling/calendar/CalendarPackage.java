/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.scheduling.calendar.CalendarFactory
 * @model kind="package"
 * @generated
 */
public interface CalendarPackage extends EPackage{
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "calendar"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/calendar"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "calendar"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CalendarPackage eINSTANCE = com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.CalculatedHolidayTypeImpl <em>Calculated Holiday Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.CalculatedHolidayTypeImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getCalculatedHolidayType()
     * @generated
     */
    int CALCULATED_HOLIDAY_TYPE = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALCULATED_HOLIDAY_TYPE__NAME = 0;

    /**
     * The feature id for the '<em><b>Script</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALCULATED_HOLIDAY_TYPE__SCRIPT = 1;

    /**
     * The number of structural features of the '<em>Calculated Holiday Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALCULATED_HOLIDAY_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl <em>Calendar</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getCalendar()
     * @generated
     */
    int CALENDAR = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR__NAME = 0;

    /**
     * The feature id for the '<em><b>Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR__LOCALE = 1;

    /**
     * The feature id for the '<em><b>Default Working Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR__DEFAULT_WORKING_SCHEDULE = 2;

    /**
     * The feature id for the '<em><b>Public Holidays</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR__PUBLIC_HOLIDAYS = 3;

    /**
     * The number of structural features of the '<em>Calendar</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CALENDAR_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.DocumentRootImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 2;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Calendar</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__CALENDAR = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl <em>Public Holidays</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getPublicHolidays()
     * @generated
     */
    int PUBLIC_HOLIDAYS = 3;

    /**
     * The feature id for the '<em><b>Single Holiday</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUBLIC_HOLIDAYS__SINGLE_HOLIDAY = 0;

    /**
     * The feature id for the '<em><b>Calculated Holiday</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY = 1;

    /**
     * The number of structural features of the '<em>Public Holidays</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PUBLIC_HOLIDAYS_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.SingleHolidayTypeImpl <em>Single Holiday Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.SingleHolidayTypeImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getSingleHolidayType()
     * @generated
     */
    int SINGLE_HOLIDAY_TYPE = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_HOLIDAY_TYPE__NAME = 0;

    /**
     * The feature id for the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_HOLIDAY_TYPE__START_DATE = 1;

    /**
     * The feature id for the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_HOLIDAY_TYPE__END_DATE = 2;

    /**
     * The number of structural features of the '<em>Single Holiday Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SINGLE_HOLIDAY_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl <em>Time Slot</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getTimeSlot()
     * @generated
     */
    int TIME_SLOT = 5;

    /**
     * The feature id for the '<em><b>Start Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_SLOT__START_TIME = 0;

    /**
     * The feature id for the '<em><b>End Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_SLOT__END_TIME = 1;

    /**
     * The number of structural features of the '<em>Time Slot</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_SLOT_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl <em>Working Day</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingDay()
     * @generated
     */
    int WORKING_DAY = 6;

    /**
     * The feature id for the '<em><b>Day Of Week</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_DAY__DAY_OF_WEEK = 0;

    /**
     * The feature id for the '<em><b>Working Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_DAY__WORKING_TIME = 1;

    /**
     * The number of structural features of the '<em>Working Day</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_DAY_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl <em>Working Schedule</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingSchedule()
     * @generated
     */
    int WORKING_SCHEDULE = 7;

    /**
     * The feature id for the '<em><b>Working Day</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_SCHEDULE__WORKING_DAY = 0;

    /**
     * The feature id for the '<em><b>Default Working Time</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_SCHEDULE__DEFAULT_WORKING_TIME = 1;

    /**
     * The number of structural features of the '<em>Working Schedule</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_SCHEDULE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingTimeImpl <em>Working Time</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.scheduling.calendar.impl.WorkingTimeImpl
     * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingTime()
     * @generated
     */
    int WORKING_TIME = 8;

    /**
     * The feature id for the '<em><b>Time Slot</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_TIME__TIME_SLOT = 0;

    /**
     * The number of structural features of the '<em>Working Time</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORKING_TIME_FEATURE_COUNT = 1;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType <em>Calculated Holiday Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Calculated Holiday Type</em>'.
     * @see com.tibco.xpd.scheduling.calendar.CalculatedHolidayType
     * @generated
     */
    EClass getCalculatedHolidayType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getName()
     * @see #getCalculatedHolidayType()
     * @generated
     */
    EAttribute getCalculatedHolidayType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getScript <em>Script</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Script</em>'.
     * @see com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getScript()
     * @see #getCalculatedHolidayType()
     * @generated
     */
    EAttribute getCalculatedHolidayType_Script();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.Calendar <em>Calendar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Calendar</em>'.
     * @see com.tibco.xpd.scheduling.calendar.Calendar
     * @generated
     */
    EClass getCalendar();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.Calendar#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.scheduling.calendar.Calendar#getName()
     * @see #getCalendar()
     * @generated
     */
    EAttribute getCalendar_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.Calendar#getLocale <em>Locale</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Locale</em>'.
     * @see com.tibco.xpd.scheduling.calendar.Calendar#getLocale()
     * @see #getCalendar()
     * @generated
     */
    EAttribute getCalendar_Locale();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.scheduling.calendar.Calendar#getDefaultWorkingSchedule <em>Default Working Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Default Working Schedule</em>'.
     * @see com.tibco.xpd.scheduling.calendar.Calendar#getDefaultWorkingSchedule()
     * @see #getCalendar()
     * @generated
     */
    EReference getCalendar_DefaultWorkingSchedule();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.scheduling.calendar.Calendar#getPublicHolidays <em>Public Holidays</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Public Holidays</em>'.
     * @see com.tibco.xpd.scheduling.calendar.Calendar#getPublicHolidays()
     * @see #getCalendar()
     * @generated
     */
    EReference getCalendar_PublicHolidays();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.scheduling.calendar.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.scheduling.calendar.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.scheduling.calendar.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.scheduling.calendar.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.scheduling.calendar.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.scheduling.calendar.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.scheduling.calendar.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.scheduling.calendar.DocumentRoot#getCalendar <em>Calendar</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Calendar</em>'.
     * @see com.tibco.xpd.scheduling.calendar.DocumentRoot#getCalendar()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_Calendar();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.PublicHolidays <em>Public Holidays</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Public Holidays</em>'.
     * @see com.tibco.xpd.scheduling.calendar.PublicHolidays
     * @generated
     */
    EClass getPublicHolidays();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.scheduling.calendar.PublicHolidays#getSingleHoliday <em>Single Holiday</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Single Holiday</em>'.
     * @see com.tibco.xpd.scheduling.calendar.PublicHolidays#getSingleHoliday()
     * @see #getPublicHolidays()
     * @generated
     */
    EReference getPublicHolidays_SingleHoliday();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.scheduling.calendar.PublicHolidays#getCalculatedHoliday <em>Calculated Holiday</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Calculated Holiday</em>'.
     * @see com.tibco.xpd.scheduling.calendar.PublicHolidays#getCalculatedHoliday()
     * @see #getPublicHolidays()
     * @generated
     */
    EReference getPublicHolidays_CalculatedHoliday();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType <em>Single Holiday Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Single Holiday Type</em>'.
     * @see com.tibco.xpd.scheduling.calendar.SingleHolidayType
     * @generated
     */
    EClass getSingleHolidayType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.scheduling.calendar.SingleHolidayType#getName()
     * @see #getSingleHolidayType()
     * @generated
     */
    EAttribute getSingleHolidayType_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getStartDate <em>Start Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Date</em>'.
     * @see com.tibco.xpd.scheduling.calendar.SingleHolidayType#getStartDate()
     * @see #getSingleHolidayType()
     * @generated
     */
    EAttribute getSingleHolidayType_StartDate();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getEndDate <em>End Date</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Date</em>'.
     * @see com.tibco.xpd.scheduling.calendar.SingleHolidayType#getEndDate()
     * @see #getSingleHolidayType()
     * @generated
     */
    EAttribute getSingleHolidayType_EndDate();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.TimeSlot <em>Time Slot</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Time Slot</em>'.
     * @see com.tibco.xpd.scheduling.calendar.TimeSlot
     * @generated
     */
    EClass getTimeSlot();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getStartTime <em>Start Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Time</em>'.
     * @see com.tibco.xpd.scheduling.calendar.TimeSlot#getStartTime()
     * @see #getTimeSlot()
     * @generated
     */
    EAttribute getTimeSlot_StartTime();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.TimeSlot#getEndTime <em>End Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>End Time</em>'.
     * @see com.tibco.xpd.scheduling.calendar.TimeSlot#getEndTime()
     * @see #getTimeSlot()
     * @generated
     */
    EAttribute getTimeSlot_EndTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.WorkingDay <em>Working Day</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Working Day</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingDay
     * @generated
     */
    EClass getWorkingDay();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek <em>Day Of Week</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Day Of Week</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingDay#getDayOfWeek()
     * @see #getWorkingDay()
     * @generated
     */
    EAttribute getWorkingDay_DayOfWeek();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.scheduling.calendar.WorkingDay#getWorkingTime <em>Working Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Working Time</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingDay#getWorkingTime()
     * @see #getWorkingDay()
     * @generated
     */
    EReference getWorkingDay_WorkingTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule <em>Working Schedule</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Working Schedule</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingSchedule
     * @generated
     */
    EClass getWorkingSchedule();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule#getWorkingDay <em>Working Day</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Working Day</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingSchedule#getWorkingDay()
     * @see #getWorkingSchedule()
     * @generated
     */
    EReference getWorkingSchedule_WorkingDay();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.scheduling.calendar.WorkingSchedule#getDefaultWorkingTime <em>Default Working Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Default Working Time</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingSchedule#getDefaultWorkingTime()
     * @see #getWorkingSchedule()
     * @generated
     */
    EReference getWorkingSchedule_DefaultWorkingTime();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.scheduling.calendar.WorkingTime <em>Working Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Working Time</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingTime
     * @generated
     */
    EClass getWorkingTime();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.scheduling.calendar.WorkingTime#getTimeSlot <em>Time Slot</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Time Slot</em>'.
     * @see com.tibco.xpd.scheduling.calendar.WorkingTime#getTimeSlot()
     * @see #getWorkingTime()
     * @generated
     */
    EReference getWorkingTime_TimeSlot();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CalendarFactory getCalendarFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.CalculatedHolidayTypeImpl <em>Calculated Holiday Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.CalculatedHolidayTypeImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getCalculatedHolidayType()
         * @generated
         */
        EClass CALCULATED_HOLIDAY_TYPE = eINSTANCE.getCalculatedHolidayType();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALCULATED_HOLIDAY_TYPE__NAME = eINSTANCE.getCalculatedHolidayType_Name();

        /**
         * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALCULATED_HOLIDAY_TYPE__SCRIPT = eINSTANCE.getCalculatedHolidayType_Script();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.CalendarImpl <em>Calendar</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getCalendar()
         * @generated
         */
        EClass CALENDAR = eINSTANCE.getCalendar();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALENDAR__NAME = eINSTANCE.getCalendar_Name();

        /**
         * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CALENDAR__LOCALE = eINSTANCE.getCalendar_Locale();

        /**
         * The meta object literal for the '<em><b>Default Working Schedule</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CALENDAR__DEFAULT_WORKING_SCHEDULE = eINSTANCE.getCalendar_DefaultWorkingSchedule();

        /**
         * The meta object literal for the '<em><b>Public Holidays</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CALENDAR__PUBLIC_HOLIDAYS = eINSTANCE.getCalendar_PublicHolidays();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.DocumentRootImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Calendar</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__CALENDAR = eINSTANCE.getDocumentRoot_Calendar();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl <em>Public Holidays</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.PublicHolidaysImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getPublicHolidays()
         * @generated
         */
        EClass PUBLIC_HOLIDAYS = eINSTANCE.getPublicHolidays();

        /**
         * The meta object literal for the '<em><b>Single Holiday</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PUBLIC_HOLIDAYS__SINGLE_HOLIDAY = eINSTANCE.getPublicHolidays_SingleHoliday();

        /**
         * The meta object literal for the '<em><b>Calculated Holiday</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY = eINSTANCE.getPublicHolidays_CalculatedHoliday();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.SingleHolidayTypeImpl <em>Single Holiday Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.SingleHolidayTypeImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getSingleHolidayType()
         * @generated
         */
        EClass SINGLE_HOLIDAY_TYPE = eINSTANCE.getSingleHolidayType();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SINGLE_HOLIDAY_TYPE__NAME = eINSTANCE.getSingleHolidayType_Name();

        /**
         * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SINGLE_HOLIDAY_TYPE__START_DATE = eINSTANCE.getSingleHolidayType_StartDate();

        /**
         * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SINGLE_HOLIDAY_TYPE__END_DATE = eINSTANCE.getSingleHolidayType_EndDate();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl <em>Time Slot</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.TimeSlotImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getTimeSlot()
         * @generated
         */
        EClass TIME_SLOT = eINSTANCE.getTimeSlot();

        /**
         * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIME_SLOT__START_TIME = eINSTANCE.getTimeSlot_StartTime();

        /**
         * The meta object literal for the '<em><b>End Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIME_SLOT__END_TIME = eINSTANCE.getTimeSlot_EndTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl <em>Working Day</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.WorkingDayImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingDay()
         * @generated
         */
        EClass WORKING_DAY = eINSTANCE.getWorkingDay();

        /**
         * The meta object literal for the '<em><b>Day Of Week</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORKING_DAY__DAY_OF_WEEK = eINSTANCE.getWorkingDay_DayOfWeek();

        /**
         * The meta object literal for the '<em><b>Working Time</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORKING_DAY__WORKING_TIME = eINSTANCE.getWorkingDay_WorkingTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl <em>Working Schedule</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.WorkingScheduleImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingSchedule()
         * @generated
         */
        EClass WORKING_SCHEDULE = eINSTANCE.getWorkingSchedule();

        /**
         * The meta object literal for the '<em><b>Working Day</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORKING_SCHEDULE__WORKING_DAY = eINSTANCE.getWorkingSchedule_WorkingDay();

        /**
         * The meta object literal for the '<em><b>Default Working Time</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORKING_SCHEDULE__DEFAULT_WORKING_TIME = eINSTANCE.getWorkingSchedule_DefaultWorkingTime();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.scheduling.calendar.impl.WorkingTimeImpl <em>Working Time</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.scheduling.calendar.impl.WorkingTimeImpl
         * @see com.tibco.xpd.scheduling.calendar.impl.CalendarPackageImpl#getWorkingTime()
         * @generated
         */
        EClass WORKING_TIME = eINSTANCE.getWorkingTime();

        /**
         * The meta object literal for the '<em><b>Time Slot</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORKING_TIME__TIME_SLOT = eINSTANCE.getWorkingTime_TimeSlot();

    }

} //CalendarPackage
