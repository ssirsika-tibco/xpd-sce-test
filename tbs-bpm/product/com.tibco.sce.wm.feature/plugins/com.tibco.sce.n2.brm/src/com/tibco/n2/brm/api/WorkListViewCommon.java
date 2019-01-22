/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlModelEntityId;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work List View Common</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of common base object used for reading and writing work list views.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired <em>Resources Required</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems <em>Get Allocated Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#getOwner <em>Owner</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkListViewCommon#isPublic <em>Public</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon()
 * @model extendedMetaData="name='WorkListViewCommon' kind='elementOnly'"
 * @generated
 */
public interface WorkListViewCommon extends EObject {
    /**
     * Returns the value of the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the organization model entity that this view is to be for.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity ID</em>' containment reference.
     * @see #setEntityID(XmlModelEntityId)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_EntityID()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='entityID'"
     * @generated
     */
    XmlModelEntityId getEntityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getEntityID <em>Entity ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity ID</em>' containment reference.
     * @see #getEntityID()
     * @generated
     */
    void setEntityID(XmlModelEntityId value);

    /**
     * Returns the value of the '<em><b>Resources Required</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.ResourcesRequiredType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specifying what if any resource processing is required on the Work List. If sspecified and the user has the correct priviliege, this will ignore any entity specified in the Work View itself.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resources Required</em>' attribute.
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @see #isSetResourcesRequired()
     * @see #unsetResourcesRequired()
     * @see #setResourcesRequired(ResourcesRequiredType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_ResourcesRequired()
     * @model unsettable="true"
     *        extendedMetaData="kind='element' name='resourcesRequired'"
     * @generated
     */
    ResourcesRequiredType getResourcesRequired();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired <em>Resources Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resources Required</em>' attribute.
     * @see com.tibco.n2.brm.api.ResourcesRequiredType
     * @see #isSetResourcesRequired()
     * @see #unsetResourcesRequired()
     * @see #getResourcesRequired()
     * @generated
     */
    void setResourcesRequired(ResourcesRequiredType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired <em>Resources Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetResourcesRequired()
     * @see #getResourcesRequired()
     * @see #setResourcesRequired(ResourcesRequiredType)
     * @generated
     */
    void unsetResourcesRequired();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getResourcesRequired <em>Resources Required</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Resources Required</em>' attribute is set.
     * @see #unsetResourcesRequired()
     * @see #getResourcesRequired()
     * @see #setResourcesRequired(ResourcesRequiredType)
     * @generated
     */
    boolean isSetResourcesRequired();

    /**
     * Returns the value of the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of sort/filter criteria to be applied to the work item list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #setOrderFilterCriteria(OrderFilterCriteria)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_OrderFilterCriteria()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='orderFilterCriteria'"
     * @generated
     */
    OrderFilterCriteria getOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getOrderFilterCriteria <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #getOrderFilterCriteria()
     * @generated
     */
    void setOrderFilterCriteria(OrderFilterCriteria value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optional description that can be given to the work list view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_Description()
     * @model dataType="com.tibco.n2.brm.api.DescriptionType"
     *        extendedMetaData="kind='attribute' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Get Allocated Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Optional default behaviour is false. If true then this is a request for allocated Work Items. If false then the request is for offered Work Items.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Allocated Items</em>' attribute.
     * @see #isSetGetAllocatedItems()
     * @see #unsetGetAllocatedItems()
     * @see #setGetAllocatedItems(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_GetAllocatedItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='getAllocatedItems'"
     * @generated
     */
    boolean isGetAllocatedItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Allocated Items</em>' attribute.
     * @see #isSetGetAllocatedItems()
     * @see #unsetGetAllocatedItems()
     * @see #isGetAllocatedItems()
     * @generated
     */
    void setGetAllocatedItems(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGetAllocatedItems()
     * @see #isGetAllocatedItems()
     * @see #setGetAllocatedItems(boolean)
     * @generated
     */
    void unsetGetAllocatedItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Get Allocated Items</em>' attribute is set.
     * @see #unsetGetAllocatedItems()
     * @see #isGetAllocatedItems()
     * @see #setGetAllocatedItems(boolean)
     * @generated
     */
    boolean isSetGetAllocatedItems();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The user defined name of the view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_Name()
     * @model dataType="com.tibco.n2.brm.api.NameType" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Owner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The resourceID of the owner of this view.   If omitted the ID of the resource calling the createWorkListView API will  be used.   This resource can always edit or use this view.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Owner</em>' attribute.
     * @see #setOwner(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_Owner()
     * @model dataType="com.tibco.n2.brm.api.OwnerType"
     *        extendedMetaData="kind='attribute' name='owner'"
     * @generated
     */
    String getOwner();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#getOwner <em>Owner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Owner</em>' attribute.
     * @see #getOwner()
     * @generated
     */
    void setOwner(String value);

    /**
     * Returns the value of the '<em><b>Public</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Is this view publlic, i.e. can it be used by any AMX-BPM user.   If TRUE then it will be visible to ALL users, not just those in the "users" set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Public</em>' attribute.
     * @see #isSetPublic()
     * @see #unsetPublic()
     * @see #setPublic(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkListViewCommon_Public()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='public'"
     * @generated
     */
    boolean isPublic();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isPublic <em>Public</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Public</em>' attribute.
     * @see #isSetPublic()
     * @see #unsetPublic()
     * @see #isPublic()
     * @generated
     */
    void setPublic(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isPublic <em>Public</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPublic()
     * @see #isPublic()
     * @see #setPublic(boolean)
     * @generated
     */
    void unsetPublic();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkListViewCommon#isPublic <em>Public</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Public</em>' attribute is set.
     * @see #unsetPublic()
     * @see #isPublic()
     * @see #setPublic(boolean)
     * @generated
     */
    boolean isSetPublic();

} // WorkListViewCommon
