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
 * A representation of the model object '<em><b>Async Schedule Work Item With Model Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemWithModelType()
 * @model extendedMetaData="name='asyncScheduleWorkItemWithModel_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncScheduleWorkItemWithModelType extends EObject {
    /**
     * Returns the value of the '<em><b>Schedule Work Item With Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for scheduleWorkItemWithModel operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item With Model</em>' containment reference.
     * @see #setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemWithModelType_ScheduleWorkItemWithModel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItemWithModel' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemWithModelType getScheduleWorkItemWithModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getScheduleWorkItemWithModel <em>Schedule Work Item With Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item With Model</em>' containment reference.
     * @see #getScheduleWorkItemWithModel()
     * @generated
     */
    void setScheduleWorkItemWithModel(ScheduleWorkItemWithModelType value);

    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemWithModelType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemWithModelType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

} // AsyncScheduleWorkItemWithModelType
