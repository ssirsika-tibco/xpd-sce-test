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
 * A representation of the model object '<em><b>Get Batch Group Ids Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetBatchGroupIdsResponseType#getGroupID <em>Group ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchGroupIdsResponseType()
 * @model extendedMetaData="name='getBatchGroupIdsResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetBatchGroupIdsResponseType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchGroupIdsResponseType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute list.
     * The list contents are of type {@link java.lang.Long}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * List of group IDs that can be used when a group is required to be created.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetBatchGroupIdsResponseType_GroupID()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='groupID' group='#group:0'"
     * @generated
     */
    EList<Long> getGroupID();

} // GetBatchGroupIdsResponseType
