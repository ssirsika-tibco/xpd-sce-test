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
 * A representation of the model object '<em><b>Get Allocated Work List Items Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition <em>Start Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType()
 * @model extendedMetaData="name='getAllocatedWorkListItems_._type' kind='elementOnly'"
 * @generated
 */
public interface GetAllocatedWorkListItemsType extends EObject {
    /**
     * Returns the value of the '<em><b>Entity ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the organization model entity requiring an allocated work item list. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity ID</em>' containment reference.
     * @see #setEntityID(XmlModelEntityId)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType_EntityID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='entityID'"
     * @generated
     */
    XmlModelEntityId getEntityID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getEntityID <em>Entity ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity ID</em>' containment reference.
     * @see #getEntityID()
     * @generated
     */
    void setEntityID(XmlModelEntityId value);

    /**
     * Returns the value of the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of sort/filter criteria to be applied to the work item list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #setOrderFilterCriteria(OrderFilterCriteria)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType_OrderFilterCriteria()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='orderFilterCriteria'"
     * @generated
     */
    OrderFilterCriteria getOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getOrderFilterCriteria <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #getOrderFilterCriteria()
     * @generated
     */
    void setOrderFilterCriteria(OrderFilterCriteria value);

    /**
     * Returns the value of the '<em><b>Get Total Count</b></em>' attribute.
     * The default value is <code>"true"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean defining whether BRM builds a count of the total number of work items in the work item list. If ‘false’ is passed in this attribute, and: 
     * 
     * - there is at least one item in the list, the totalItems value returned in the response is -1.
     * 
     * - there are no work items in the list, the totalItems value returned in the response is 0 (zero).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Get Total Count</em>' attribute.
     * @see #isSetGetTotalCount()
     * @see #unsetGetTotalCount()
     * @see #setGetTotalCount(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType_GetTotalCount()
     * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='getTotalCount'"
     * @generated
     */
    boolean isGetTotalCount();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Get Total Count</em>' attribute.
     * @see #isSetGetTotalCount()
     * @see #unsetGetTotalCount()
     * @see #isGetTotalCount()
     * @generated
     */
    void setGetTotalCount(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGetTotalCount()
     * @see #isGetTotalCount()
     * @see #setGetTotalCount(boolean)
     * @generated
     */
    void unsetGetTotalCount();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#isGetTotalCount <em>Get Total Count</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Get Total Count</em>' attribute is set.
     * @see #unsetGetTotalCount()
     * @see #isGetTotalCount()
     * @see #setGetTotalCount(boolean)
     * @generated
     */
    boolean isSetGetTotalCount();

    /**
     * Returns the value of the '<em><b>Number Of Items</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of work items (in the work item list) to include in this page.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Number Of Items</em>' attribute.
     * @see #isSetNumberOfItems()
     * @see #unsetNumberOfItems()
     * @see #setNumberOfItems(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType_NumberOfItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='numberOfItems'"
     * @generated
     */
    long getNumberOfItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Number Of Items</em>' attribute.
     * @see #isSetNumberOfItems()
     * @see #unsetNumberOfItems()
     * @see #getNumberOfItems()
     * @generated
     */
    void setNumberOfItems(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    void unsetNumberOfItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getNumberOfItems <em>Number Of Items</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Number Of Items</em>' attribute is set.
     * @see #unsetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    boolean isSetNumberOfItems();

    /**
     * Returns the value of the '<em><b>Start Position</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Position in the work item list from which to start this page. (The list is zero-based. To start at the first item, specify 0.)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #setStartPosition(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetAllocatedWorkListItemsType_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Position</em>' attribute.
     * @see #isSetStartPosition()
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @generated
     */
    void setStartPosition(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetAllocatedWorkListItemsType#getStartPosition <em>Start Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Position</em>' attribute is set.
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    boolean isSetStartPosition();

} // GetAllocatedWorkListItemsType
