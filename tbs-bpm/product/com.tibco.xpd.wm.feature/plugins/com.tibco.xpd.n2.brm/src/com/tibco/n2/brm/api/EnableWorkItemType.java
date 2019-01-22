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
 * A representation of the model object '<em><b>Enable Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.EnableWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.EnableWorkItemType#getItemBody <em>Item Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getEnableWorkItemType()
 * @model extendedMetaData="name='enableWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface EnableWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item ID</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getEnableWorkItemType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.EnableWorkItemType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ObjectID value);

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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getEnableWorkItemType_ItemBody()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='itemBody'"
     * @generated
     */
    ItemBody getItemBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.EnableWorkItemType#getItemBody <em>Item Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Item Body</em>' containment reference.
     * @see #getItemBody()
     * @generated
     */
    void setItemBody(ItemBody value);

} // EnableWorkItemType
