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
 * A representation of the model object '<em><b>Async Resume Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncResumeWorkItemType#getMessageDetails <em>Message Details</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncResumeWorkItemType()
 * @model extendedMetaData="name='asyncResumeWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncResumeWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncResumeWorkItemType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

} // AsyncResumeWorkItemType
