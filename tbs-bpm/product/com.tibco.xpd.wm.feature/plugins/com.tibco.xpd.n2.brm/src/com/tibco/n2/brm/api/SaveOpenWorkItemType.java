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
 * A representation of the model object '<em><b>Save Open Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getSaveOpenWorkItemType()
 * @model extendedMetaData="name='saveOpenWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface SaveOpenWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the open work item that is to be saved.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSaveOpenWorkItemType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemID <em>Work Item ID</em>}' containment reference.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSaveOpenWorkItemType_WorkItemPayload()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemPayload'"
     * @generated
     */
    WorkItemBody getWorkItemPayload();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SaveOpenWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item Payload</em>' containment reference.
     * @see #getWorkItemPayload()
     * @generated
     */
    void setWorkItemPayload(WorkItemBody value);

} // SaveOpenWorkItemType
