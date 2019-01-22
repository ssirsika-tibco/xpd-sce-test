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
 * A representation of the model object '<em><b>Reschedule Work Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.RescheduleWorkItem#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getRescheduleWorkItem()
 * @model extendedMetaData="name='rescheduleWorkItem' kind='elementOnly'"
 * @generated
 */
public interface RescheduleWorkItem extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of a work item that is to be rescheduled. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getRescheduleWorkItem_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ObjectID value);

    /**
     * Returns the value of the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optional item schedule period to be associated with the work item.   If no object is passed then the item schedule period will not be changed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Schedule</em>' containment reference.
     * @see #setItemSchedule(ItemSchedule)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getRescheduleWorkItem_ItemSchedule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemSchedule'"
     * @generated
     */
    ItemSchedule getItemSchedule();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemSchedule <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Schedule</em>' containment reference.
     * @see #getItemSchedule()
     * @generated
     */
    void setItemSchedule(ItemSchedule value);

    /**
     * Returns the value of the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optional work item body containing data changes on a  reschedule. The body is the the data as a name/value pair.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Body</em>' containment reference.
     * @see #setItemBody(ItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getRescheduleWorkItem_ItemBody()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.RescheduleWorkItem#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

} // RescheduleWorkItem
