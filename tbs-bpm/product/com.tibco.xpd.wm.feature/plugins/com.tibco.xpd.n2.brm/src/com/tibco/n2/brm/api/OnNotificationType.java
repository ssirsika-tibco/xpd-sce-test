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
 * A representation of the model object '<em><b>On Notification Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.OnNotificationType#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.OnNotificationType#getItemBody <em>Item Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.OnNotificationType#getAllocationHistory <em>Allocation History</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getOnNotificationType()
 * @model extendedMetaData="name='onNotification_._type' kind='elementOnly'"
 * @generated
 */
public interface OnNotificationType extends EObject {
    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message Details</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOnNotificationType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OnNotificationType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

    /**
     * Returns the value of the '<em><b>Item Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item Body</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Item Body</em>' containment reference.
     * @see #setItemBody(ItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOnNotificationType_ItemBody()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OnNotificationType#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

    /**
     * Returns the value of the '<em><b>Allocation History</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.AllocationHistory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Allocation History</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Allocation History</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOnNotificationType_AllocationHistory()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='allocationHistory'"
     * @generated
     */
    EList<AllocationHistory> getAllocationHistory();

} // OnNotificationType
