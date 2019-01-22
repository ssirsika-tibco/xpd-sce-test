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
 * A representation of the model object '<em><b>Complete Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getNextWorkItem <em>Next Work Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemResponseType()
 * @model extendedMetaData="name='completeWorkItemResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface CompleteWorkItemResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work group that this work item belongs to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemResponseType_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
     *        extendedMetaData="kind='element' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID <em>Group ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getGroupID <em>Group ID</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Next Work Item</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the next work item in the specified work group.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Next Work Item</em>' containment reference.
     * @see #setNextWorkItem(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getCompleteWorkItemResponseType_NextWorkItem()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='nextWorkItem'"
     * @generated
     */
    ManagedObjectID getNextWorkItem();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.CompleteWorkItemResponseType#getNextWorkItem <em>Next Work Item</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Next Work Item</em>' containment reference.
     * @see #getNextWorkItem()
     * @generated
     */
    void setNextWorkItem(ManagedObjectID value);

} // CompleteWorkItemResponseType
