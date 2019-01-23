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
 * A representation of the model object '<em><b>Schedule Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItem <em>Item</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemType()
 * @model extendedMetaData="name='scheduleWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface ScheduleWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Basic, participant and work type information about the work item
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item</em>' containment reference.
     * @see #setItem(Item)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemType_Item()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='item'"
     * @generated
     */
    Item getItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItem <em>Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item</em>' containment reference.
     * @see #getItem()
     * @generated
     */
    void setItem(Item value);

    /**
     * Returns the value of the '<em><b>Item Schedule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The schedule period to be associated with the work item.   If no object is passed then the item with have no schedule and cannot be worked on.   If an empty object is passed BRM will default this to immediately available with no target date.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Schedule</em>' containment reference.
     * @see #setItemSchedule(ItemSchedule)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemType_ItemSchedule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='itemSchedule'"
     * @generated
     */
    ItemSchedule getItemSchedule();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemSchedule <em>Item Schedule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Schedule</em>' containment reference.
     * @see #getItemSchedule()
     * @generated
     */
    void setItemSchedule(ItemSchedule value);

    /**
     * Returns the value of the '<em><b>Item Context</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The work item's context, as supplied by the application that scheduled the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Context</em>' containment reference.
     * @see #setItemContext(ItemContext)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemType_ItemContext()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemContext'"
     * @generated
     */
    ItemContext getItemContext();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemContext <em>Item Context</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Context</em>' containment reference.
     * @see #getItemContext()
     * @generated
     */
    void setItemContext(ItemContext value);

    /**
     * Returns the value of the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The work item body, containing each data field (as a name/value pair) required by the data model in the work item's work type. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Item Body</em>' containment reference.
     * @see #setItemBody(ItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getScheduleWorkItemType_ItemBody()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ScheduleWorkItemType#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

} // ScheduleWorkItemType
