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
 * A representation of the model object '<em><b>Allocate And Open Next Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType#getWorkItem <em>Work Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenNextWorkItemResponseType()
 * @model extendedMetaData="name='allocateAndOpenNextWorkItemResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface AllocateAndOpenNextWorkItemResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete details of the work item that has been allocated and opened.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item</em>' containment reference.
     * @see #setWorkItem(WorkItem)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenNextWorkItemResponseType_WorkItem()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItem'"
     * @generated
     */
    WorkItem getWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AllocateAndOpenNextWorkItemResponseType#getWorkItem <em>Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item</em>' containment reference.
     * @see #getWorkItem()
     * @generated
     */
    void setWorkItem(WorkItem value);

} // AllocateAndOpenNextWorkItemResponseType
