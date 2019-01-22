/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.FieldType;
import com.tibco.n2.common.datamodel.WorkTypeSpec;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Preview</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of the work item data set for a previewed work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemPreview#getFieldPreview <em>Field Preview</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemPreview#getWorkType <em>Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPreview()
 * @model extendedMetaData="name='WorkItemPreview' kind='elementOnly'"
 * @generated
 */
public interface WorkItemPreview extends ManagedObjectID {
    /**
     * Returns the value of the '<em><b>Field Preview</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.datamodel.FieldType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Data field and value pairs for a previewed work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Field Preview</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPreview_FieldPreview()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='FieldPreview'"
     * @generated
     */
    EList<FieldType> getFieldPreview();

    /**
     * Returns the value of the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the work type associated with the work item only if this has been requested.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type</em>' containment reference.
     * @see #setWorkType(WorkTypeSpec)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPreview_WorkType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workType'"
     * @generated
     */
    WorkTypeSpec getWorkType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemPreview#getWorkType <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type</em>' containment reference.
     * @see #getWorkType()
     * @generated
     */
    void setWorkType(WorkTypeSpec value);

} // WorkItemPreview
