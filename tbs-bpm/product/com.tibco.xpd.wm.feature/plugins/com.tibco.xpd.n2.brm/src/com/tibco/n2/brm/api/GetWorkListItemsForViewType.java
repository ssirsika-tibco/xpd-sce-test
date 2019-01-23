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
 * A representation of the model object '<em><b>Get Work List Items For View Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems <em>Get Allocated Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount <em>Get Total Count</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID <em>Work List View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType()
 * @model extendedMetaData="name='getWorkListItemsForView_._type' kind='empty'"
 * @generated
 */
public interface GetWorkListItemsForViewType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType_GetAllocatedItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='getAllocatedItems'"
     * @generated
     */
    boolean isGetAllocatedItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGetAllocatedItems()
     * @see #isGetAllocatedItems()
     * @see #setGetAllocatedItems(boolean)
     * @generated
     */
    void unsetGetAllocatedItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetAllocatedItems <em>Get Allocated Items</em>}' attribute is set.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType_GetTotalCount()
     * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='getTotalCount'"
     * @generated
     */
    boolean isGetTotalCount();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount <em>Get Total Count</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount <em>Get Total Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGetTotalCount()
     * @see #isGetTotalCount()
     * @see #setGetTotalCount(boolean)
     * @generated
     */
    void unsetGetTotalCount();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#isGetTotalCount <em>Get Total Count</em>}' attribute is set.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType_NumberOfItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='numberOfItems'"
     * @generated
     */
    long getNumberOfItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems <em>Number Of Items</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    void unsetNumberOfItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getNumberOfItems <em>Number Of Items</em>}' attribute is set.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition <em>Start Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getStartPosition <em>Start Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Position</em>' attribute is set.
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    boolean isSetStartPosition();

    /**
     * Returns the value of the '<em><b>Work List View ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID of the work list view from which to get the page of work items.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #setWorkListViewID(long)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForViewType_WorkListViewID()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='workListViewID'"
     * @generated
     */
    long getWorkListViewID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work List View ID</em>' attribute.
     * @see #isSetWorkListViewID()
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @generated
     */
    void setWorkListViewID(long value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID <em>Work List View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    void unsetWorkListViewID();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForViewType#getWorkListViewID <em>Work List View ID</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Work List View ID</em>' attribute is set.
     * @see #unsetWorkListViewID()
     * @see #getWorkListViewID()
     * @see #setWorkListViewID(long)
     * @generated
     */
    boolean isSetWorkListViewID();

} // GetWorkListItemsForViewType
