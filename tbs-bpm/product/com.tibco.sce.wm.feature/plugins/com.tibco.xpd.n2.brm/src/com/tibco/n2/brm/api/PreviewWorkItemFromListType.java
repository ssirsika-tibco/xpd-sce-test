/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Preview Work Item From List Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getRequiredField <em>Required Field</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType <em>Require Work Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getPreviewWorkItemFromListType()
 * @model extendedMetaData="name='previewWorkItemFromList_._type' kind='elementOnly'"
 * @generated
 */
public interface PreviewWorkItemFromListType extends EObject {
    /**
     * Returns the value of the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the organization model entity for whom work items are to be previewed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity ID</em>' containment reference.
     * @see #setEntityID(XmlModelEntityId)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPreviewWorkItemFromListType_EntityID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='entityID'"
     * @generated
     */
    XmlModelEntityId getEntityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#getEntityID <em>Entity ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity ID</em>' containment reference.
     * @see #getEntityID()
     * @generated
     */
    void setEntityID(XmlModelEntityId value);

    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.ObjectID}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of a work item to be previewed.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPreviewWorkItemFromListType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    EList<ObjectID> getWorkItemID();

    /**
     * Returns the value of the '<em><b>Required Field</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Fieldname to be included in the returned data. This defines the set of fields for which preview data is to be to be returned:
     * 
     * - If any fieldnames are specified, only those fields will be returned for each work item.
     * 
     * - If no fieldnames are specified, all fields will be returned for each work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Required Field</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPreviewWorkItemFromListType_RequiredField()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='requiredField'"
     * @generated
     */
    EList<String> getRequiredField();

    /**
     * Returns the value of the '<em><b>Require Work Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optiional element if set to true, then the response should include Work Type information.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Require Work Type</em>' attribute.
     * @see #isSetRequireWorkType()
     * @see #unsetRequireWorkType()
     * @see #setRequireWorkType(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getPreviewWorkItemFromListType_RequireWorkType()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='requireWorkType'"
     * @generated
     */
    boolean isRequireWorkType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType <em>Require Work Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Require Work Type</em>' attribute.
     * @see #isSetRequireWorkType()
     * @see #unsetRequireWorkType()
     * @see #isRequireWorkType()
     * @generated
     */
    void setRequireWorkType(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType <em>Require Work Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetRequireWorkType()
     * @see #isRequireWorkType()
     * @see #setRequireWorkType(boolean)
     * @generated
     */
    void unsetRequireWorkType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.PreviewWorkItemFromListType#isRequireWorkType <em>Require Work Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Require Work Type</em>' attribute is set.
     * @see #unsetRequireWorkType()
     * @see #isRequireWorkType()
     * @see #setRequireWorkType(boolean)
     * @generated
     */
    boolean isSetRequireWorkType();

} // PreviewWorkItemFromListType
