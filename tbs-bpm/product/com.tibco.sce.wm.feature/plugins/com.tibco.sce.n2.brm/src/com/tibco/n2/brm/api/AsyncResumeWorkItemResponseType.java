/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.api.exception.ErrorLine;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Async Resume Work Item Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getMessageDetails <em>Message Details</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getErrorMessage <em>Error Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncResumeWorkItemResponseType()
 * @model extendedMetaData="name='asyncResumeWorkItemResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncResumeWorkItemResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Message Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Information to track which work item this message is associated with.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Message Details</em>' containment reference.
     * @see #setMessageDetails(AsyncWorkItemDetails)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncResumeWorkItemResponseType_MessageDetails()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='messageDetails'"
     * @generated
     */
    AsyncWorkItemDetails getMessageDetails();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getMessageDetails <em>Message Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Message Details</em>' containment reference.
     * @see #getMessageDetails()
     * @generated
     */
    void setMessageDetails(AsyncWorkItemDetails value);

    /**
     * Returns the value of the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Any error message that needs to be reported.  If the errorMessage element is not set, then the operation was successful.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Error Message</em>' containment reference.
     * @see #setErrorMessage(ErrorLine)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncResumeWorkItemResponseType_ErrorMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='errorMessage'"
     * @generated
     */
    ErrorLine getErrorMessage();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncResumeWorkItemResponseType#getErrorMessage <em>Error Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Error Message</em>' containment reference.
     * @see #getErrorMessage()
     * @generated
     */
    void setErrorMessage(ErrorLine value);

} // AsyncResumeWorkItemResponseType
