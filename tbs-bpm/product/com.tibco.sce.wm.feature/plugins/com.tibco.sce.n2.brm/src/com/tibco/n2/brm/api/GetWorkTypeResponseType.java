/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.WorkType;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Work Type Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkTypeResponseType#getWorkType <em>Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypeResponseType()
 * @model extendedMetaData="name='getWorkTypeResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkTypeResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Type</em>' containment reference.
     * @see #setWorkType(WorkType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypeResponseType_WorkType()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workType'"
     * @generated
     */
    WorkType getWorkType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkTypeResponseType#getWorkType <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type</em>' containment reference.
     * @see #getWorkType()
     * @generated
     */
    void setWorkType(WorkType value);

} // GetWorkTypeResponseType
