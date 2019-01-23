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
 * A representation of the model object '<em><b>Get Work Types Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkTypesResponseType#getWorkTypelList <em>Work Typel List</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypesResponseType()
 * @model extendedMetaData="name='getWorkTypesResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkTypesResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Typel List</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Defines a 'page' of work type objects, which is a window over the entire set of work types defined in BRM.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Typel List</em>' containment reference.
     * @see #setWorkTypelList(WorkTypeList)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkTypesResponseType_WorkTypelList()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workTypelList'"
     * @generated
     */
    WorkTypeList getWorkTypelList();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkTypesResponseType#getWorkTypelList <em>Work Typel List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Typel List</em>' containment reference.
     * @see #getWorkTypelList()
     * @generated
     */
    void setWorkTypelList(WorkTypeList value);

} // GetWorkTypesResponseType
