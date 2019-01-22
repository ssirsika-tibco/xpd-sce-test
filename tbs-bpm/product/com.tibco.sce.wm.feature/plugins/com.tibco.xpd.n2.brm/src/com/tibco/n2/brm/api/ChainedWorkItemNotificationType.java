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
 * A representation of the model object '<em><b>Chained Work Item Notification Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getWorkItemID <em>Work Item ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getChainedWorkItemNotificationType()
 * @model extendedMetaData="name='chainedWorkItemNotification_._type' kind='elementOnly'"
 * @generated
 */
public interface ChainedWorkItemNotificationType extends EObject {
    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group ID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getChainedWorkItemNotificationType_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @generated
     */
    void setGroupID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getGroupID <em>Group ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Group ID</em>' attribute is set.
     * @see #unsetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    boolean isSetGroupID();

    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the next work item in the chain, if there is no workItemID present then it means that the chaining group has been completed as there are no more work items in this chain.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getChainedWorkItemNotificationType_WorkItemID()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ChainedWorkItemNotificationType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ManagedObjectID value);

} // ChainedWorkItemNotificationType
