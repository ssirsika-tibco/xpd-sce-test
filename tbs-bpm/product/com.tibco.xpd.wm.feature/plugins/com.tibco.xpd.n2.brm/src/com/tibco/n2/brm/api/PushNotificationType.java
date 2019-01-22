/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.WorkTypeSpec;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Push Notification Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.PushNotificationType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PushNotificationType#getWorkTypeID <em>Work Type ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PushNotificationType#getResourceIDs <em>Resource IDs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getPushNotificationType()
 * @model extendedMetaData="name='pushNotification_._type' kind='elementOnly'"
 * @generated
 */
public interface PushNotificationType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work item
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPushNotificationType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ManagedObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.PushNotificationType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ManagedObjectID value);

    /**
     * Returns the value of the '<em><b>Work Type ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work type
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type ID</em>' containment reference.
     * @see #setWorkTypeID(WorkTypeSpec)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPushNotificationType_WorkTypeID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workTypeID'"
     * @generated
     */
    WorkTypeSpec getWorkTypeID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.PushNotificationType#getWorkTypeID <em>Work Type ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type ID</em>' containment reference.
     * @see #getWorkTypeID()
     * @generated
     */
    void setWorkTypeID(WorkTypeSpec value);

    /**
     * Returns the value of the '<em><b>Resource IDs</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlModelEntityId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * list of resource Ids with push destinations
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource IDs</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPushNotificationType_ResourceIDs()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='resourceIDs'"
     * @generated
     */
    EList<XmlModelEntityId> getResourceIDs();

} // PushNotificationType
