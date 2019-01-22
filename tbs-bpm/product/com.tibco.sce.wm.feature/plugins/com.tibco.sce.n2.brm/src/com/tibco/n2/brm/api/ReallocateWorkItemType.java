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
 * A representation of the model object '<em><b>Reallocate Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ReallocateWorkItemType#getRevertData <em>Revert Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getReallocateWorkItemType()
 * @model extendedMetaData="name='reallocateWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface ReallocateWorkItemType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getReallocateWorkItemType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.ManagedObjectID}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of a work item that is to be reallocated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getReallocateWorkItemType_WorkItemID()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItemID' group='#group:0'"
     * @generated
     */
    EList<ManagedObjectID> getWorkItemID();

    /**
     * Returns the value of the '<em><b>Resource</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the resource to whom the specified work item should be reallocated.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getReallocateWorkItemType_Resource()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='resource' group='#group:0'"
     * @generated
     */
    EList<String> getResource();

    /**
     * Returns the value of the '<em><b>Revert Data</b></em>' attribute list.
     * The list contents are of type {@link java.lang.Boolean}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean that defines whether the data associated with the specified work item is to be reverted or not.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Revert Data</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getReallocateWorkItemType_RevertData()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='revertData' group='#group:0'"
     * @generated
     */
    EList<Boolean> getRevertData();

} // ReallocateWorkItemType
