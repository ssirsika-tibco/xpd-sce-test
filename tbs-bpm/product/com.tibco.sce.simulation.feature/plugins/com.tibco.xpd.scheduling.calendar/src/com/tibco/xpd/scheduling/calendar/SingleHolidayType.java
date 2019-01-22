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
 * A representation of the model object '<em><b>Single Holiday Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getEndDate <em>End Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getSingleHolidayType()
 * @model extendedMetaData="name='SingleHoliday_._type' kind='elementOnly'"
 * @generated
 */
public interface SingleHolidayType extends EObject {
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
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getSingleHolidayType_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getSingleHolidayType_StartDate()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='StartDate'"
     * @generated
     */
    String getStartDate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getStartDate <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Date</em>' attribute.
     * @see #getStartDate()
     * @generated
     */
    void setStartDate(String value);

    /**
     * Returns the value of the '<em><b>End Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Date</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Date</em>' attribute.
     * @see #setEndDate(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getSingleHolidayType_EndDate()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='EndDate'"
     * @generated
     */
    String getEndDate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.SingleHolidayType#getEndDate <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Date</em>' attribute.
     * @see #getEndDate()
     * @generated
     */
    void setEndDate(String value);

} // SingleHolidayType
