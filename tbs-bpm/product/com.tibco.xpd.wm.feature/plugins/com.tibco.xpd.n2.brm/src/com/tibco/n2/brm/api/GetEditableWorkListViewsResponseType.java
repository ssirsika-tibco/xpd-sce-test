/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Editable Work List Views Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetEditableWorkListViewsResponseType#getWorkListViews <em>Work List Views</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetEditableWorkListViewsResponseType()
 * @model extendedMetaData="name='getEditableWorkListViewsResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetEditableWorkListViewsResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work List Views</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkListViewPageItem}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Array of work list views the resource has permissions to edit.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work List Views</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetEditableWorkListViewsResponseType_WorkListViews()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workListViews'"
     * @generated
     */
    EList<WorkListViewPageItem> getWorkListViews();

} // GetEditableWorkListViewsResponseType
