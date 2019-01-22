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
 * A representation of the model object '<em><b>Get Work Item Order Filter Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkItemOrderFilterResponseType#getColumnData <em>Column Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkItemOrderFilterResponseType()
 * @model extendedMetaData="name='getWorkItemOrderFilterResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkItemOrderFilterResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Column Data</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.ColumnDefinition}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the fields defined by BRM that can be used in sort/filter criteria expressions for work item lists.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Column Data</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkItemOrderFilterResponseType_ColumnData()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='columnData'"
     * @generated
     */
    EList<ColumnDefinition> getColumnData();

} // GetWorkItemOrderFilterResponseType
