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
 * A representation of the model object '<em><b>Pend Work Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.PendWorkItem#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PendWorkItem#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PendWorkItem#getHiddenPeriod <em>Hidden Period</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getPendWorkItem()
 * @model extendedMetaData="name='pendWorkItem' kind='elementOnly'"
 * @generated
 */
public interface PendWorkItem extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPendWorkItem_Group()
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
     * ID of a work item that is to be pended.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPendWorkItem_WorkItemID()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='workItemID' group='#group:0'"
     * @generated
     */
    EList<ManagedObjectID> getWorkItemID();

    /**
     * Returns the value of the '<em><b>Hidden Period</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.HiddenPeriod}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information about how long the specified work item should remain hidden.
     * 
     * If a hiddenPeriod is specified the work item will transition to the PendHidden state, rather than Pended. Operations that work on the Pended state (e.g. reallocateWorkItem) cannot then access the work item until either of the following occurs.
     * 
     * - the hiddenPeriod expires.
     * 
     * - a further pendWorkItem operation is specified with a hiddenPeriod of 0. This cancels the current hiddenPeriod. (A negative duration or date that is in the past will have the same effect.)
     * 
     * The work item then transitions back to the Pended state.
     * 
     * NOTE: The duration of a hiddenPeriod can also be extended or reduced by issuing a further pendWorkItem operation with a new hiddenPeriod. The new hiddenPeriod overrides the existing hiddenPeriod and is calculated from the current date/time.
     * 
     * For example, suppose that a work item was pended at 2.30 with a hiddenPeriod of 30 minutes. If a further pendWorkItem is issued at 2.45 with:
     * 
     * - a hiddenPeriod of 2 hours, the work item will remain hidden until 4.45.
     * 
     * - a hiddenPeriod of 5 minutes, the work item will remain hidden until 2.50.
     * 
     * - a hiddenPeriod of 0, the work item will immediately transition to the Pended state.
     * 
     * - a hiddenPeriod of -5 minutes, the work item will immediately transition to the Pended state.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Hidden Period</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPendWorkItem_HiddenPeriod()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='hiddenPeriod' group='#group:0'"
     * @generated
     */
    EList<HiddenPeriod> getHiddenPeriod();

} // PendWorkItem
