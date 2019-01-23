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
 * A representation of the model object '<em><b>Async Start Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncStartGroupType#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID <em>Group ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncStartGroupType#getStartGroup <em>Start Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncStartGroupType()
 * @model extendedMetaData="name='asyncStartGroup_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncStartGroupType extends EObject {
    /**
     * Returns the value of the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique identifier for this operation, this identifier should be used to identify the reply to this message
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity ID</em>' attribute.
     * @see #setActivityID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncStartGroupType_ActivityID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityID'"
     * @generated
     */
    String getActivityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getActivityID <em>Activity ID</em>}' attribute.
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
     * The ID of the group to start
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group ID</em>' attribute.
     * @see #isSetGroupID()
     * @see #unsetGroupID()
     * @see #setGroupID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncStartGroupType_GroupID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='element' name='groupID'"
     * @generated
     */
    long getGroupID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID <em>Group ID</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID <em>Group ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupID()
     * @see #getGroupID()
     * @see #setGroupID(long)
     * @generated
     */
    void unsetGroupID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getGroupID <em>Group ID</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Start Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for startGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Group</em>' containment reference.
     * @see #setStartGroup(StartGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncStartGroupType_StartGroup()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='startGroup' namespace='##targetNamespace'"
     * @generated
     */
    StartGroupType getStartGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncStartGroupType#getStartGroup <em>Start Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Group</em>' containment reference.
     * @see #getStartGroup()
     * @generated
     */
    void setStartGroup(StartGroupType value);

} // AsyncStartGroupType
