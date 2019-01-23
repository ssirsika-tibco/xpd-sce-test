/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Allocate And Open Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AllocateAndOpenWorkItemResponseType#getWorkItem <em>Work Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenWorkItemResponseType()
 * @model extendedMetaData="name='allocateAndOpenWorkItemResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface AllocateAndOpenWorkItemResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenWorkItemResponseType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Work Item</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkItem}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete details of a work item that has been allocated and opened.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAllocateAndOpenWorkItemResponseType_WorkItem()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItem' group='#group:0'"
     * @generated
     */
    EList<WorkItem> getWorkItem();

} // AllocateAndOpenWorkItemResponseType
