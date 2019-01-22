/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Flags</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Various flags that can be set against a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus <em>Schedule Status</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemFlags()
 * @model extendedMetaData="name='WorkItemFlags' kind='elementOnly'"
 * @generated
 */
public interface WorkItemFlags extends EObject {
    /**
     * Returns the value of the '<em><b>Schedule Status</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.ScheduleStatus}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Enumerated value defining whether a work item is within its schedule period.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Status</em>' attribute.
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @see #isSetScheduleStatus()
     * @see #unsetScheduleStatus()
     * @see #setScheduleStatus(ScheduleStatus)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemFlags_ScheduleStatus()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='scheduleStatus'"
     * @generated
     */
    ScheduleStatus getScheduleStatus();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus <em>Schedule Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Status</em>' attribute.
     * @see com.tibco.n2.brm.api.ScheduleStatus
     * @see #isSetScheduleStatus()
     * @see #unsetScheduleStatus()
     * @see #getScheduleStatus()
     * @generated
     */
    void setScheduleStatus(ScheduleStatus value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus <em>Schedule Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetScheduleStatus()
     * @see #getScheduleStatus()
     * @see #setScheduleStatus(ScheduleStatus)
     * @generated
     */
    void unsetScheduleStatus();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItemFlags#getScheduleStatus <em>Schedule Status</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Schedule Status</em>' attribute is set.
     * @see #unsetScheduleStatus()
     * @see #getScheduleStatus()
     * @see #setScheduleStatus(ScheduleStatus)
     * @generated
     */
    boolean isSetScheduleStatus();

} // WorkItemFlags
