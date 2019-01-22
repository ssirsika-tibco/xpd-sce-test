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
 * A representation of the model object '<em><b>Get Work Model Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkModelResponseType#getWorkModel <em>Work Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelResponseType()
 * @model extendedMetaData="name='getWorkModelResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkModelResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Model</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Model</em>' containment reference.
     * @see #setWorkModel(WorkModel)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkModelResponseType_WorkModel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workModel'"
     * @generated
     */
    WorkModel getWorkModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkModelResponseType#getWorkModel <em>Work Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Model</em>' containment reference.
     * @see #getWorkModel()
     * @generated
     */
    void setWorkModel(WorkModel value);

} // GetWorkModelResponseType
