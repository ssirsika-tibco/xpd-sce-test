/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.DataModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Complete work item body returned when a client opens the work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemBody#getDataModel <em>Data Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemBody()
 * @model extendedMetaData="name='WorkItemBody' kind='elementOnly'"
 * @generated
 */
public interface WorkItemBody extends EObject {
    /**
     * Returns the value of the '<em><b>Data Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Sets of INPUT, OUTPUT and INOUT parameters that define the data model for the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Data Model</em>' containment reference.
     * @see #setDataModel(DataModel)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemBody_DataModel()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='dataModel'"
     * @generated
     */
    DataModel getDataModel();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemBody#getDataModel <em>Data Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Model</em>' containment reference.
     * @see #getDataModel()
     * @generated
     */
    void setDataModel(DataModel value);

} // WorkItemBody
