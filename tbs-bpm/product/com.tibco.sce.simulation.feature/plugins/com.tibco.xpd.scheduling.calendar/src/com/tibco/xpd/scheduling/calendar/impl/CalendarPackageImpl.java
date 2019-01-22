/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.impl;

import com.tibco.xpd.scheduling.calendar.CalculatedHolidayType;
import com.tibco.xpd.scheduling.calendar.Calendar;
import com.tibco.xpd.scheduling.calendar.CalendarFactory;
import com.tibco.xpd.scheduling.calendar.CalendarPackage;
import com.tibco.xpd.scheduling.calendar.DocumentRoot;
import com.tibco.xpd.scheduling.calendar.PublicHolidays;
import com.tibco.xpd.scheduling.calendar.SingleHolidayType;
import com.tibco.xpd.scheduling.calendar.TimeSlot;
import com.tibco.xpd.scheduling.calendar.WorkingDay;
import com.tibco.xpd.scheduling.calendar.WorkingSchedule;
import com.tibco.xpd.scheduling.calendar.WorkingTime;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CalendarPackageImpl extends EPackageImpl implements CalendarPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass calculatedHolidayTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass calendarEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass publicHolidaysEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass singleHolidayTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass timeSlotEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workingDayEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workingScheduleEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workingTimeEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CalendarPackageImpl() {
        super(eNS_URI, CalendarFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CalendarPackage init() {
        if (isInited) return (CalendarPackage)EPackage.Registry.INSTANCE.getEPackage(CalendarPackage.eNS_URI);

        // Obtain or create and register package
        CalendarPackageImpl theCalendarPackage = (CalendarPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof CalendarPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new CalendarPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theCalendarPackage.createPackageContents();

        // Initialize created meta-data
        theCalendarPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCalendarPackage.freeze();

        return theCalendarPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCalculatedHolidayType() {
        return calculatedHolidayTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCalculatedHolidayType_Name() {
        return (EAttribute)calculatedHolidayTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCalculatedHolidayType_Script() {
        return (EAttribute)calculatedHolidayTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCalendar() {
        return calendarEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCalendar_Name() {
        return (EAttribute)calendarEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCalendar_Locale() {
        return (EAttribute)calendarEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCalendar_DefaultWorkingSchedule() {
        return (EReference)calendarEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCalendar_PublicHolidays() {
        return (EReference)calendarEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_Calendar() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPublicHolidays() {
        return publicHolidaysEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPublicHolidays_SingleHoliday() {
        return (EReference)publicHolidaysEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPublicHolidays_CalculatedHoliday() {
        return (EReference)publicHolidaysEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSingleHolidayType() {
        return singleHolidayTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSingleHolidayType_Name() {
        return (EAttribute)singleHolidayTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSingleHolidayType_StartDate() {
        return (EAttribute)singleHolidayTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSingleHolidayType_EndDate() {
        return (EAttribute)singleHolidayTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTimeSlot() {
        return timeSlotEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeSlot_StartTime() {
        return (EAttribute)timeSlotEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeSlot_EndTime() {
        return (EAttribute)timeSlotEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkingDay() {
        return workingDayEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkingDay_DayOfWeek() {
        return (EAttribute)workingDayEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkingDay_WorkingTime() {
        return (EReference)workingDayEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkingSchedule() {
        return workingScheduleEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkingSchedule_WorkingDay() {
        return (EReference)workingScheduleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkingSchedule_DefaultWorkingTime() {
        return (EReference)workingScheduleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkingTime() {
        return workingTimeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkingTime_TimeSlot() {
        return (EReference)workingTimeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalendarFactory getCalendarFactory() {
        return (CalendarFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        calculatedHolidayTypeEClass = createEClass(CALCULATED_HOLIDAY_TYPE);
        createEAttribute(calculatedHolidayTypeEClass, CALCULATED_HOLIDAY_TYPE__NAME);
        createEAttribute(calculatedHolidayTypeEClass, CALCULATED_HOLIDAY_TYPE__SCRIPT);

        calendarEClass = createEClass(CALENDAR);
        createEAttribute(calendarEClass, CALENDAR__NAME);
        createEAttribute(calendarEClass, CALENDAR__LOCALE);
        createEReference(calendarEClass, CALENDAR__DEFAULT_WORKING_SCHEDULE);
        createEReference(calendarEClass, CALENDAR__PUBLIC_HOLIDAYS);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__CALENDAR);

        publicHolidaysEClass = createEClass(PUBLIC_HOLIDAYS);
        createEReference(publicHolidaysEClass, PUBLIC_HOLIDAYS__SINGLE_HOLIDAY);
        createEReference(publicHolidaysEClass, PUBLIC_HOLIDAYS__CALCULATED_HOLIDAY);

        singleHolidayTypeEClass = createEClass(SINGLE_HOLIDAY_TYPE);
        createEAttribute(singleHolidayTypeEClass, SINGLE_HOLIDAY_TYPE__NAME);
        createEAttribute(singleHolidayTypeEClass, SINGLE_HOLIDAY_TYPE__START_DATE);
        createEAttribute(singleHolidayTypeEClass, SINGLE_HOLIDAY_TYPE__END_DATE);

        timeSlotEClass = createEClass(TIME_SLOT);
        createEAttribute(timeSlotEClass, TIME_SLOT__START_TIME);
        createEAttribute(timeSlotEClass, TIME_SLOT__END_TIME);

        workingDayEClass = createEClass(WORKING_DAY);
        createEAttribute(workingDayEClass, WORKING_DAY__DAY_OF_WEEK);
        createEReference(workingDayEClass, WORKING_DAY__WORKING_TIME);

        workingScheduleEClass = createEClass(WORKING_SCHEDULE);
        createEReference(workingScheduleEClass, WORKING_SCHEDULE__WORKING_DAY);
        createEReference(workingScheduleEClass, WORKING_SCHEDULE__DEFAULT_WORKING_TIME);

        workingTimeEClass = createEClass(WORKING_TIME);
        createEReference(workingTimeEClass, WORKING_TIME__TIME_SLOT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(calculatedHolidayTypeEClass, CalculatedHolidayType.class, "CalculatedHolidayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCalculatedHolidayType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, CalculatedHolidayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCalculatedHolidayType_Script(), theXMLTypePackage.getString(), "script", null, 1, 1, CalculatedHolidayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(calendarEClass, Calendar.class, "Calendar", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCalendar_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCalendar_Locale(), theXMLTypePackage.getString(), "locale", null, 1, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCalendar_DefaultWorkingSchedule(), this.getWorkingSchedule(), null, "defaultWorkingSchedule", null, 1, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCalendar_PublicHolidays(), this.getPublicHolidays(), null, "publicHolidays", null, 0, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_Calendar(), this.getCalendar(), null, "calendar", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(publicHolidaysEClass, PublicHolidays.class, "PublicHolidays", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPublicHolidays_SingleHoliday(), this.getSingleHolidayType(), null, "singleHoliday", null, 0, -1, PublicHolidays.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPublicHolidays_CalculatedHoliday(), this.getCalculatedHolidayType(), null, "calculatedHoliday", null, 0, -1, PublicHolidays.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(singleHolidayTypeEClass, SingleHolidayType.class, "SingleHolidayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSingleHolidayType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SingleHolidayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSingleHolidayType_StartDate(), theXMLTypePackage.getString(), "startDate", null, 1, 1, SingleHolidayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSingleHolidayType_EndDate(), theXMLTypePackage.getString(), "endDate", null, 1, 1, SingleHolidayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(timeSlotEClass, TimeSlot.class, "TimeSlot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getTimeSlot_StartTime(), theXMLTypePackage.getString(), "startTime", null, 1, 1, TimeSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTimeSlot_EndTime(), theXMLTypePackage.getString(), "endTime", null, 1, 1, TimeSlot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workingDayEClass, WorkingDay.class, "WorkingDay", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getWorkingDay_DayOfWeek(), theXMLTypePackage.getInt(), "dayOfWeek", null, 1, 1, WorkingDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkingDay_WorkingTime(), this.getWorkingTime(), null, "workingTime", null, 0, 1, WorkingDay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workingScheduleEClass, WorkingSchedule.class, "WorkingSchedule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkingSchedule_WorkingDay(), this.getWorkingDay(), null, "workingDay", null, 1, -1, WorkingSchedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getWorkingSchedule_DefaultWorkingTime(), this.getWorkingTime(), null, "defaultWorkingTime", null, 1, 1, WorkingSchedule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workingTimeEClass, WorkingTime.class, "WorkingTime", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkingTime_TimeSlot(), this.getTimeSlot(), null, "timeSlot", null, 1, -1, WorkingTime.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (calculatedHolidayTypeEClass, 
           source, 
           new String[] {
             "name", "CalculatedHoliday_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCalculatedHolidayType_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Name"
           });			
        addAnnotation
          (getCalculatedHolidayType_Script(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Script"
           });		
        addAnnotation
          (calendarEClass, 
           source, 
           new String[] {
             "name", "Calendar",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getCalendar_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Name"
           });		
        addAnnotation
          (getCalendar_Locale(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Locale"
           });		
        addAnnotation
          (getCalendar_DefaultWorkingSchedule(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "DefaultWorkingSchedule"
           });		
        addAnnotation
          (getCalendar_PublicHolidays(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "PublicHolidays"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getDocumentRoot_Calendar(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Calendar",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (publicHolidaysEClass, 
           source, 
           new String[] {
             "name", "PublicHolidays",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPublicHolidays_SingleHoliday(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "SingleHoliday"
           });		
        addAnnotation
          (getPublicHolidays_CalculatedHoliday(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "CalculatedHoliday"
           });		
        addAnnotation
          (singleHolidayTypeEClass, 
           source, 
           new String[] {
             "name", "SingleHoliday_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSingleHolidayType_Name(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "Name"
           });		
        addAnnotation
          (getSingleHolidayType_StartDate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "StartDate"
           });		
        addAnnotation
          (getSingleHolidayType_EndDate(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "EndDate"
           });		
        addAnnotation
          (timeSlotEClass, 
           source, 
           new String[] {
             "name", "TimeSlot",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getTimeSlot_StartTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "StartTime"
           });		
        addAnnotation
          (getTimeSlot_EndTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "EndTime"
           });		
        addAnnotation
          (workingDayEClass, 
           source, 
           new String[] {
             "name", "WorkingDay",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkingDay_DayOfWeek(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "DayOfWeek"
           });		
        addAnnotation
          (getWorkingDay_WorkingTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkingTime"
           });		
        addAnnotation
          (workingScheduleEClass, 
           source, 
           new String[] {
             "name", "WorkingSchedule",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkingSchedule_WorkingDay(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkingDay"
           });		
        addAnnotation
          (getWorkingSchedule_DefaultWorkingTime(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "DefaultWorkingTime"
           });		
        addAnnotation
          (workingTimeEClass, 
           source, 
           new String[] {
             "name", "WorkingTime",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkingTime_TimeSlot(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "TimeSlot"
           });
    }

} //CalendarPackageImpl
