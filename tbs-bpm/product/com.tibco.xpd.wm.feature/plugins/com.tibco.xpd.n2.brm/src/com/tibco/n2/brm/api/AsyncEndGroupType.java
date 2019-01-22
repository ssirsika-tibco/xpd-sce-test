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
 * A representation of the model object '<em><b>Async End Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.AsyncEndGroupType#getActivityID <em>Activity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.AsyncEndGroupType#getEndGroup <em>End Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupType()
 * @model extendedMetaData="name='asyncEndGroup_._type' kind='elementOnly'"
 * @generated
 */
public interface AsyncEndGroupType extends EObject {
    /**
     * Returns the value of the '<em><b>Activity ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique identifier for this operation, this identifier should be used to identify the reply to this message
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activity ID</em>' attribute.
     * @see #setActivityID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupType_ActivityID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='activityID'"
     * @generated
     */
    String getActivityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupType#getActivityID <em>Activity ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity ID</em>' attribute.
     * @see #getActivityID()
     * @generated
     */
    void setActivityID(String value);

    /**
     * Returns the value of the '<em><b>End Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Request element for endGroup operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>End Group</em>' containment reference.
     * @see #setEndGroup(EndGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getAsyncEndGroupType_EndGroup()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='endGroup' namespace='##targetNamespace'"
     * @generated
     */
    EndGroupType getEndGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.AsyncEndGroupType#getEndGroup <em>End Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Group</em>' containment reference.
     * @see #getEndGroup()
     * @generated
     */
    void setEndGroup(EndGroupType value);

} // AsyncEndGroupType
