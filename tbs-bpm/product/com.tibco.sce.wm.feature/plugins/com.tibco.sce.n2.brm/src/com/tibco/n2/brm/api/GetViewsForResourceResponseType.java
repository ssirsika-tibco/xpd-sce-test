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
 * A representation of the model object '<em><b>Get Views For Resource Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetViewsForResourceResponseType#getWorkListViews <em>Work List Views</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceResponseType()
 * @model extendedMetaData="name='getViewsForResourceResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetViewsForResourceResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Work List Views</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkListViewPageItem}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work List Views</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work List Views</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetViewsForResourceResponseType_WorkListViews()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workListViews'"
     * @generated
     */
    EList<WorkListViewPageItem> getWorkListViews();

} // GetViewsForResourceResponseType
