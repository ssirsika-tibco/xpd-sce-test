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
 * A representation of the model object '<em><b>Close Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.CloseWorkItemType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CloseWorkItemType#getWorkItemPayload <em>Work Item Payload</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CloseWorkItemType#getHiddenPeriod <em>Hidden Period</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getCloseWorkItemType()
 * @model extendedMetaData="name='closeWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface CloseWorkItemType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCloseWorkItemType_Group()
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
     * ID of a work item that is to be closed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCloseWorkItemType_WorkItemID()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItemID' group='#group:0'"
     * @generated
     */
    EList<ManagedObjectID> getWorkItemID();

    /**
     * Returns the value of the '<em><b>Work Item Payload</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkItemBody}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete body of the specified work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item Payload</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCloseWorkItemType_WorkItemPayload()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItemPayload' group='#group:0'"
     * @generated
     */
    EList<WorkItemBody> getWorkItemPayload();

    /**
     * Returns the value of the '<em><b>Hidden Period</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.HiddenPeriod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information about how long the specified work item is hidden.
     * 
     * 
     * NOTE: If a hiddenPeriod is specified the work item will transition to the PendHidden state, rather than Pended. Operations that work on the Pended state (e.g. reallocateWorkItem) cannot then access the work item until the hiddenPeriod expires, at which point the system automatically transitions the work item back to the Pended state.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Hidden Period</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCloseWorkItemType_HiddenPeriod()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='hiddenPeriod' group='#group:0'"
     * @generated
     */
    EList<HiddenPeriod> getHiddenPeriod();

} // CloseWorkItemType
