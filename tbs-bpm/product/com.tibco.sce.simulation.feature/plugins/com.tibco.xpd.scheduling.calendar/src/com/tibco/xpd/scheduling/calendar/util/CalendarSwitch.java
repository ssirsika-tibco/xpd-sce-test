/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.scheduling.calendar.util;

import com.tibco.xpd.scheduling.calendar.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage
 * @generated
 */
public class CalendarSwitch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static CalendarPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CalendarSwitch() {
        if (modelPackage == null) {
            modelPackage = CalendarPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case CalendarPackage.CALCULATED_HOLIDAY_TYPE: {
                CalculatedHolidayType calculatedHolidayType = (CalculatedHolidayType)theEObject;
                T result = caseCalculatedHolidayType(calculatedHolidayType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.CALENDAR: {
                Calendar calendar = (Calendar)theEObject;
                T result = caseCalendar(calendar);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.DOCUMENT_ROOT: {
                DocumentRoot documentRoot = (DocumentRoot)theEObject;
                T result = caseDocumentRoot(documentRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.PUBLIC_HOLIDAYS: {
                PublicHolidays publicHolidays = (PublicHolidays)theEObject;
                T result = casePublicHolidays(publicHolidays);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.SINGLE_HOLIDAY_TYPE: {
                SingleHolidayType singleHolidayType = (SingleHolidayType)theEObject;
                T result = caseSingleHolidayType(singleHolidayType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.TIME_SLOT: {
                TimeSlot timeSlot = (TimeSlot)theEObject;
                T result = caseTimeSlot(timeSlot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.WORKING_DAY: {
                WorkingDay workingDay = (WorkingDay)theEObject;
                T result = caseWorkingDay(workingDay);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.WORKING_SCHEDULE: {
                WorkingSchedule workingSchedule = (WorkingSchedule)theEObject;
                T result = caseWorkingSchedule(workingSchedule);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case CalendarPackage.WORKING_TIME: {
                WorkingTime workingTime = (WorkingTime)theEObject;
                T result = caseWorkingTime(workingTime);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Calculated Holiday Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Calculated Holiday Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCalculatedHolidayType(CalculatedHolidayType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Calendar</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Calendar</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCalendar(Calendar object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDocumentRoot(DocumentRoot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Public Holidays</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Public Holidays</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePublicHolidays(PublicHolidays object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Single Holiday Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Single Holiday Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSingleHolidayType(SingleHolidayType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Time Slot</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Time Slot</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTimeSlot(TimeSlot object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Working Day</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Working Day</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkingDay(WorkingDay object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Working Schedule</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Working Schedule</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkingSchedule(WorkingSchedule object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Working Time</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Working Time</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkingTime(WorkingTime object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //CalendarSwitch
