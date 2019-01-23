/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Schedule information to be associated with a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ItemSchedule#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemSchedule#getMaxDuration <em>Max Duration</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemSchedule#getTargetDate <em>Target Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItemSchedule()
 * @model extendedMetaData="name='ItemSchedule' kind='elementOnly'"
 * @generated
 */
public interface ItemSchedule extends EObject {
    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Date at which this work item can be started.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemSchedule_StartDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='element' name='startDate'"
     * @generated
     */
    XMLGregorianCalendar getStartDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemSchedule#getStartDate <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Date</em>' attribute.
     * @see #getStartDate()
     * @generated
     */
    void setStartDate(XMLGregorianCalendar value);

    /**
     * Returns the value of the '<em><b>Max Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Duration of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Max Duration</em>' containment reference.
     * @see #setMaxDuration(ItemDuration)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemSchedule_MaxDuration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='maxDuration'"
     * @generated
     */
    ItemDuration getMaxDuration();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemSchedule#getMaxDuration <em>Max Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Duration</em>' containment reference.
     * @see #getMaxDuration()
     * @generated
     */
    void setMaxDuration(ItemDuration value);

    /**
     * Returns the value of the '<em><b>Target Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Date by which this work item must be finished.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Date</em>' attribute.
     * @see #setTargetDate(XMLGregorianCalendar)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemSchedule_TargetDate()
     * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
     *        extendedMetaData="kind='element' name='targetDate'"
     * @generated
     */
    XMLGregorianCalendar getTargetDate();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ItemSchedule#getTargetDate <em>Target Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Date</em>' attribute.
     * @see #getTargetDate()
     * @generated
     */
    void setTargetDate(XMLGregorianCalendar value);

} // ItemSchedule
