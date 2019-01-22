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
 * A representation of the model object '<em><b>Async Schedule Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getScheduleWorkItem <em>Schedule Work Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemType()
 * @model extendedMetaData="name='asyncScheduleWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncScheduleWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Schedule Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for scheduleWorkItem operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Schedule Work Item</em>' containment reference.
     * @see #setScheduleWorkItem(ScheduleWorkItemType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemType_ScheduleWorkItem()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='scheduleWorkItem' namespace='##targetNamespace'"
     * @generated
     */
    ScheduleWorkItemType getScheduleWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getScheduleWorkItem <em>Schedule Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schedule Work Item</em>' containment reference.
     * @see #getScheduleWorkItem()
     * @generated
     */
    void setScheduleWorkItem(ScheduleWorkItemType value);

    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncScheduleWorkItemType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncScheduleWorkItemType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

} // AsyncScheduleWorkItemType
