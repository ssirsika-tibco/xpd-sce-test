/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.datamodel.WorkTypeSpec;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Complete details of a work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getHeader <em>Header</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getBody <em>Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItem#isVisible <em>Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem()
 * @model extendedMetaData="name='WorkItem' kind='elementOnly'"
 * @generated
 */
public interface WorkItem extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' containment reference.
     * @see #setId(ManagedObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_Id()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='id'"
     * @generated
     */
    ManagedObjectID getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getId <em>Id</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' containment reference.
     * @see #getId()
     * @generated
     */
    void setId(ManagedObjectID value);

    /**
     * Returns the value of the '<em><b>Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Header information about the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Header</em>' containment reference.
     * @see #setHeader(WorkItemHeader)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_Header()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='header'"
     * @generated
     */
    WorkItemHeader getHeader();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getHeader <em>Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Header</em>' containment reference.
     * @see #getHeader()
     * @generated
     */
    void setHeader(WorkItemHeader value);

    /**
     * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Four custom data attributes that can be set on a work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attributes</em>' containment reference.
     * @see #setAttributes(WorkItemAttributes)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_Attributes()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='attributes'"
     * @generated
     */
    WorkItemAttributes getAttributes();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getAttributes <em>Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attributes</em>' containment reference.
     * @see #getAttributes()
     * @generated
     */
    void setAttributes(WorkItemAttributes value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Complete body of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Body</em>' containment reference.
     * @see #setBody(WorkItemBody)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_Body()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='body'"
     * @generated
     */
    WorkItemBody getBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getBody <em>Body</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Body</em>' containment reference.
     * @see #getBody()
     * @generated
     */
    void setBody(WorkItemBody value);

    /**
     * Returns the value of the '<em><b>Work Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the work type associated with the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type</em>' containment reference.
     * @see #setWorkType(WorkTypeSpec)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_WorkType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='workType'"
     * @generated
     */
    WorkTypeSpec getWorkType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getWorkType <em>Work Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type</em>' containment reference.
     * @see #getWorkType()
     * @generated
     */
    void setWorkType(WorkTypeSpec value);

    /**
     * Returns the value of the '<em><b>State</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.WorkItemState}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Enumerated value defining the current state of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>State</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkItemState
     * @see #isSetState()
     * @see #unsetState()
     * @see #setState(WorkItemState)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_State()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='state'"
     * @generated
     */
    WorkItemState getState();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getState <em>State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>State</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkItemState
     * @see #isSetState()
     * @see #unsetState()
     * @see #getState()
     * @generated
     */
    void setState(WorkItemState value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItem#getState <em>State</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetState()
     * @see #getState()
     * @see #setState(WorkItemState)
     * @generated
     */
    void unsetState();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItem#getState <em>State</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>State</em>' attribute is set.
     * @see #unsetState()
     * @see #getState()
     * @see #setState(WorkItemState)
     * @generated
     */
    boolean isSetState();

    /**
     * Returns the value of the '<em><b>Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value indicating whether or not the work item is visible.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Visible</em>' attribute.
     * @see #isSetVisible()
     * @see #unsetVisible()
     * @see #setVisible(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItem_Visible()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='element' name='visible'"
     * @generated
     */
    boolean isVisible();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItem#isVisible <em>Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Visible</em>' attribute.
     * @see #isSetVisible()
     * @see #unsetVisible()
     * @see #isVisible()
     * @generated
     */
    void setVisible(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItem#isVisible <em>Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetVisible()
     * @see #isVisible()
     * @see #setVisible(boolean)
     * @generated
     */
    void unsetVisible();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItem#isVisible <em>Visible</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Visible</em>' attribute is set.
     * @see #unsetVisible()
     * @see #isVisible()
     * @see #setVisible(boolean)
     * @generated
     */
    boolean isSetVisible();

} // WorkItem
