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
 * A representation of the model object '<em><b>Get Batch Work Item Ids Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetBatchWorkItemIdsResponseType#getWorkItemID <em>Work Item ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchWorkItemIdsResponseType()
 * @model extendedMetaData="name='getBatchWorkItemIdsResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetBatchWorkItemIdsResponseType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchWorkItemIdsResponseType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.ObjectID}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item ID</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchWorkItemIdsResponseType_WorkItemID()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItemID' group='#group:0'"
     * @generated
     */
    EList<ObjectID> getWorkItemID();

} // GetBatchWorkItemIdsResponseType
