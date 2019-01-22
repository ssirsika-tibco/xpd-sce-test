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
 * A representation of the model object '<em><b>Async Reschedule Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncRescheduleWorkItemType()
 * @model extendedMetaData="name='asyncRescheduleWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncRescheduleWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item Schedule</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Item Schedule</em>' containment reference.
     * @see #setItemSchedule(ItemSchedule)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncRescheduleWorkItemType_ItemSchedule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemSchedule'"
     * @generated
     */
    ItemSchedule getItemSchedule();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Schedule</em>' containment reference.
     * @see #getItemSchedule()
     * @generated
     */
    void setItemSchedule(ItemSchedule value);

    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncRescheduleWorkItemType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

    /**
     * Returns the value of the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optional work item body containing data changes on a  reschedule. The body is the the data as a name/value pair.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Body</em>' containment reference.
     * @see #setItemBody(ItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncRescheduleWorkItemType_ItemBody()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncRescheduleWorkItemType#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

} // AsyncRescheduleWorkItemType
