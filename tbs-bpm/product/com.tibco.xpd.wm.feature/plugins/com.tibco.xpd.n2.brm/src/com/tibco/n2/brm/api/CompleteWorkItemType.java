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
 * A representation of the model object '<em><b>Complete Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem <em>Get Next Piled Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemType()
 * @model extendedMetaData="name='completeWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface CompleteWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work item that is to be completed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ManagedObjectID value);

    /**
     * Returns the value of the '<em><b>Work Item Payload</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete body of the specified work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item Payload</em>' containment reference.
     * @see #setWorkItemPayload(WorkItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemType_WorkItemPayload()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemPayload'"
     * @generated
     */
    WorkItemBody getWorkItemPayload();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item Payload</em>' containment reference.
     * @see #getWorkItemPayload()
     * @generated
     */
    void setWorkItemPayload(WorkItemBody value);

    /**
     * Returns the value of the '<em><b>Get Next Piled Item</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * True, then return the next piled work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Next Piled Item</em>' attribute.
     * @see #isSetGetNextPiledItem()
     * @see #unsetGetNextPiledItem()
     * @see #setGetNextPiledItem(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemType_GetNextPiledItem()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='getNextPiledItem'"
     * @generated
     */
    boolean isGetNextPiledItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem <em>Get Next Piled Item</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Next Piled Item</em>' attribute.
     * @see #isSetGetNextPiledItem()
     * @see #unsetGetNextPiledItem()
     * @see #isGetNextPiledItem()
     * @generated
     */
    void setGetNextPiledItem(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem <em>Get Next Piled Item</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGetNextPiledItem()
     * @see #isGetNextPiledItem()
     * @see #setGetNextPiledItem(boolean)
     * @generated
     */
    void unsetGetNextPiledItem();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemType#isGetNextPiledItem <em>Get Next Piled Item</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Get Next Piled Item</em>' attribute is set.
     * @see #unsetGetNextPiledItem()
     * @see #isGetNextPiledItem()
     * @see #setGetNextPiledItem(boolean)
     * @generated
     */
    boolean isSetGetNextPiledItem();

} // CompleteWorkItemType
