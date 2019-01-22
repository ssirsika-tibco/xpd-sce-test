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
 * A representation of the model object '<em><b>Calculated Holiday Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getScript <em>Script</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalculatedHolidayType()
 * @model extendedMetaData="name='CalculatedHoliday_._type' kind='elementOnly'"
 * @generated
 */
public interface CalculatedHolidayType extends EObject {
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
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalculatedHolidayType_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Script</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * JavaScript which, given a year, will return the date of the holiday in that year.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script</em>' attribute.
     * @see #setScript(String)
     * @see com.tibco.xpd.scheduling.calendar.CalendarPackage#getCalculatedHolidayType_Script()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Script'"
     * @generated
     */
    String getScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.scheduling.calendar.CalculatedHolidayType#getScript <em>Script</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script</em>' attribute.
     * @see #getScript()
     * @generated
     */
    void setScript(String value);

} // CalculatedHolidayType
