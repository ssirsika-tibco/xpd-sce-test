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
 * A representation of the model object '<em><b>Get Work Models Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkModelsResponseType#getWorkModelList <em>Work Model List</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelsResponseType()
 * @model extendedMetaData="name='getWorkModelsResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkModelsResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model List</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model List</em>' containment reference.
     * @see #setWorkModelList(WorkModelList)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelsResponseType_WorkModelList()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workModelList'"
     * @generated
     */
    WorkModelList getWorkModelList();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkModelsResponseType#getWorkModelList <em>Work Model List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model List</em>' containment reference.
     * @see #getWorkModelList()
     * @generated
     */
    void setWorkModelList(WorkModelList value);

} // GetWorkModelsResponseType
