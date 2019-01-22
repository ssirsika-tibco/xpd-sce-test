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
 * A representation of the model object '<em><b>Async End Group Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getErrorMessage <em>Error Message</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupResponseType()
 * @model extendedMetaData="name='asyncEndGroupResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncEndGroupResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique identifier for this message, this identifier will match the request identifier
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity ID</em>' attribute.
     * @see #setActivityID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupResponseType_ActivityID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityID'"
     * @generated
     */
    String getActivityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getActivityID <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity ID</em>' attribute.
     * @see #getActivityID()
     * @generated
     */
    void setActivityID(String value);

    /**
     * Returns the value of the '<em><b>Group ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID of the group to end
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupResponseType_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID <em>Group ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getGroupID <em>Group ID</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Error Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Any error message that needs to be reported.  If the errorMessage element is not set, then the operation was successful.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Error Message</em>' containment reference.
     * @see #setErrorMessage(ErrorLine)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupResponseType_ErrorMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='errorMessage'"
     * @generated
     */
    ErrorLine getErrorMessage();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupResponseType#getErrorMessage <em>Error Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Error Message</em>' containment reference.
     * @see #getErrorMessage()
     * @generated
     */
    void setErrorMessage(ErrorLine value);

} // AsyncEndGroupResponseType
