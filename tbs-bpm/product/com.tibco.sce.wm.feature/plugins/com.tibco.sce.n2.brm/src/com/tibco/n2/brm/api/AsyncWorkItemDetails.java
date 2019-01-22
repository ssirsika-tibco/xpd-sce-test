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
 * A representation of the model object '<em><b>Async Work Item Details</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of the additional information required for asynchronous work item handling.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getWorkItemId <em>Work Item Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId <em>Ua Sequence Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId <em>Brm Sequence Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncWorkItemDetails()
 * @model extendedMetaData="name='AsyncWorkItemDetails' kind='elementOnly'"
 * @generated
 */
public interface AsyncWorkItemDetails extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item Id</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item Id</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item Id</em>' containment reference.
     * @see #setWorkItemId(ObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncWorkItemDetails_WorkItemId()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemId'"
     * @generated
     */
    ObjectID getWorkItemId();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getWorkItemId <em>Work Item Id</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item Id</em>' containment reference.
     * @see #getWorkItemId()
     * @generated
     */
    void setWorkItemId(ObjectID value);

    /**
     * Returns the value of the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique identifier for this message, this identifier will match the request and the response
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity ID</em>' attribute.
     * @see #setActivityID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncWorkItemDetails_ActivityID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityID'"
     * @generated
     */
    String getActivityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getActivityID <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity ID</em>' attribute.
     * @see #getActivityID()
     * @generated
     */
    void setActivityID(String value);

    /**
     * Returns the value of the '<em><b>Ua Sequence Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Order in which to process the messages for this work item, starting at 1 for the first message in the case of a request. (For a response this is the last message processed from User Task) [A zero implies no messages processed for this work item]
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ua Sequence Id</em>' attribute.
     * @see #isSetUaSequenceId()
     * @see #unsetUaSequenceId()
     * @see #setUaSequenceId(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncWorkItemDetails_UaSequenceId()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='uaSequenceId'"
     * @generated
     */
    int getUaSequenceId();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId <em>Ua Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ua Sequence Id</em>' attribute.
     * @see #isSetUaSequenceId()
     * @see #unsetUaSequenceId()
     * @see #getUaSequenceId()
     * @generated
     */
    void setUaSequenceId(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId <em>Ua Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetUaSequenceId()
     * @see #getUaSequenceId()
     * @see #setUaSequenceId(int)
     * @generated
     */
    void unsetUaSequenceId();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getUaSequenceId <em>Ua Sequence Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Ua Sequence Id</em>' attribute is set.
     * @see #unsetUaSequenceId()
     * @see #getUaSequenceId()
     * @see #setUaSequenceId(int)
     * @generated
     */
    boolean isSetUaSequenceId();

    /**
     * Returns the value of the '<em><b>Brm Sequence Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Order in which to process the messages for this work item, starting at 1 for the first message in the case of a request. (For a response this is the last BRM sequence ID processed for this work item before sending this message). [A zero implies no messages processed for this work item]
     * <!-- end-model-doc -->
     * @return the value of the '<em>Brm Sequence Id</em>' attribute.
     * @see #isSetBrmSequenceId()
     * @see #unsetBrmSequenceId()
     * @see #setBrmSequenceId(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncWorkItemDetails_BrmSequenceId()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='brmSequenceId'"
     * @generated
     */
    int getBrmSequenceId();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId <em>Brm Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Brm Sequence Id</em>' attribute.
     * @see #isSetBrmSequenceId()
     * @see #unsetBrmSequenceId()
     * @see #getBrmSequenceId()
     * @generated
     */
    void setBrmSequenceId(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId <em>Brm Sequence Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBrmSequenceId()
     * @see #getBrmSequenceId()
     * @see #setBrmSequenceId(int)
     * @generated
     */
    void unsetBrmSequenceId();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AsyncWorkItemDetails#getBrmSequenceId <em>Brm Sequence Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Brm Sequence Id</em>' attribute is set.
     * @see #unsetBrmSequenceId()
     * @see #getBrmSequenceId()
     * @see #setBrmSequenceId(int)
     * @generated
     */
    boolean isSetBrmSequenceId();

} // AsyncWorkItemDetails
