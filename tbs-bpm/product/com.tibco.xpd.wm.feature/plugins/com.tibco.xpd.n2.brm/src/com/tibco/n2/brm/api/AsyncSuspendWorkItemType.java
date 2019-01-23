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
 * A representation of the model object '<em><b>Async Suspend Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncSuspendWorkItemType()
 * @model extendedMetaData="name='asyncSuspendWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncSuspendWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncSuspendWorkItemType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

    /**
     * Returns the value of the '<em><b>Force Suspend</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If the suspend operation should be forced
     * <!-- end-model-doc -->
     * @return the value of the '<em>Force Suspend</em>' attribute.
     * @see #isSetForceSuspend()
     * @see #unsetForceSuspend()
     * @see #setForceSuspend(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncSuspendWorkItemType_ForceSuspend()
     * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='forceSuspend'"
     * @generated
     */
    boolean isForceSuspend();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Force Suspend</em>' attribute.
     * @see #isSetForceSuspend()
     * @see #unsetForceSuspend()
     * @see #isForceSuspend()
     * @generated
     */
    void setForceSuspend(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetForceSuspend()
     * @see #isForceSuspend()
     * @see #setForceSuspend(boolean)
     * @generated
     */
    void unsetForceSuspend();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AsyncSuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Force Suspend</em>' attribute is set.
     * @see #unsetForceSuspend()
     * @see #isForceSuspend()
     * @see #setForceSuspend(boolean)
     * @generated
     */
    boolean isSetForceSuspend();

} // AsyncSuspendWorkItemType
