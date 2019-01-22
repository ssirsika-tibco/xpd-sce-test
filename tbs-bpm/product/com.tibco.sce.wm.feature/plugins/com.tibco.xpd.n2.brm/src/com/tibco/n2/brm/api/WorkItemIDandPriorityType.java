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
 * A representation of the model object '<em><b>Work Item IDand Priority Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemPriority <em>Work Item Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemIDandPriorityType()
 * @model extendedMetaData="name='workItemIDandPriority_._type' kind='elementOnly'"
 * @generated
 */
public interface WorkItemIDandPriorityType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of a work item that is to be allocated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemIDandPriorityType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ManagedObjectID value);

    /**
     * Returns the value of the '<em><b>Work Item Priority</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item Priority</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item Priority</em>' containment reference.
     * @see #setWorkItemPriority(WorkItemPriorityType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemIDandPriorityType_WorkItemPriority()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemPriority'"
     * @generated
     */
    WorkItemPriorityType getWorkItemPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemIDandPriorityType#getWorkItemPriority <em>Work Item Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item Priority</em>' containment reference.
     * @see #getWorkItemPriority()
     * @generated
     */
    void setWorkItemPriority(WorkItemPriorityType value);

} // WorkItemIDandPriorityType
