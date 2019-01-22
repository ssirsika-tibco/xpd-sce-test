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
 * A representation of the model object '<em><b>Schedule Work Item With Model Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType#getWorkItemID <em>Work Item ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelResponseType()
 * @model extendedMetaData="name='scheduleWorkItemWithModelResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface ScheduleWorkItemWithModelResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item ID</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemWithModelResponseType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemWithModelResponseType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ObjectID value);

} // ScheduleWorkItemWithModelResponseType
