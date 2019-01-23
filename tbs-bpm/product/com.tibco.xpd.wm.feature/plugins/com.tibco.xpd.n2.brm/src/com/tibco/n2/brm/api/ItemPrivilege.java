/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Privilege</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of the privileges required to perform specific actions on a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getSuspend <em>Suspend</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getStatelessRealloc <em>Stateless Realloc</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getStatefulRealloc <em>Stateful Realloc</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getDellocate <em>Dellocate</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getDelegate <em>Delegate</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getSkip <em>Skip</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ItemPrivilege#getPiling <em>Piling</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege()
 * @model extendedMetaData="name='ItemPrivilege' kind='elementOnly'"
 * @generated
 */
public interface ItemPrivilege extends EObject {
    /**
     * Returns the value of the '<em><b>Suspend</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to suspend a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Suspend</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_Suspend()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Suspend'"
     * @generated
     */
    EList<Privilege> getSuspend();

    /**
     * Returns the value of the '<em><b>Stateless Realloc</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to (statelessly) reallocate a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Stateless Realloc</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_StatelessRealloc()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StatelessRealloc'"
     * @generated
     */
    EList<Privilege> getStatelessRealloc();

    /**
     * Returns the value of the '<em><b>Stateful Realloc</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to (statefully) reallocate a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Stateful Realloc</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_StatefulRealloc()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StatefulRealloc'"
     * @generated
     */
    EList<Privilege> getStatefulRealloc();

    /**
     * Returns the value of the '<em><b>Dellocate</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to deallocate a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Dellocate</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_Dellocate()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Dellocate'"
     * @generated
     */
    EList<Privilege> getDellocate();

    /**
     * Returns the value of the '<em><b>Delegate</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to delegate a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Delegate</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_Delegate()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Delegate'"
     * @generated
     */
    EList<Privilege> getDelegate();

    /**
     * Returns the value of the '<em><b>Skip</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to skip a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Skip</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_Skip()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Skip'"
     * @generated
     */
    EList<Privilege> getSkip();

    /**
     * Returns the value of the '<em><b>Piling</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.Privilege}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the privileges required to pile a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Piling</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItemPrivilege_Piling()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Piling'"
     * @generated
     */
    EList<Privilege> getPiling();

} // ItemPrivilege
