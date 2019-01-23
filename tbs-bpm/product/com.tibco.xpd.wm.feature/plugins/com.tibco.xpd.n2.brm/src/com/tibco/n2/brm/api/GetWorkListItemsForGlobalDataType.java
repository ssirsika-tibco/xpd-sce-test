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
 * A representation of the model object '<em><b>Get Work List Items For Global Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getGlobalDataRef <em>Global Data Ref</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition <em>Start Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForGlobalDataType()
 * @model extendedMetaData="name='getWorkListItemsForGlobalData_._type' kind='elementOnly'"
 * @generated
 */
public interface GetWorkListItemsForGlobalDataType extends EObject {
    /**
     * Returns the value of the '<em><b>Global Data Ref</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * List of Global Data References to search for.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Global Data Ref</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForGlobalDataType_GlobalDataRef()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='globalDataRef'"
     * @generated
     */
    EList<String> getGlobalDataRef();

    /**
     * Returns the value of the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of sort/filter criteria to be applied to the work item list.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #setOrderFilterCriteria(OrderFilterCriteria)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForGlobalDataType_OrderFilterCriteria()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='orderFilterCriteria'"
     * @generated
     */
    OrderFilterCriteria getOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getOrderFilterCriteria <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #getOrderFilterCriteria()
     * @generated
     */
    void setOrderFilterCriteria(OrderFilterCriteria value);

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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForGlobalDataType_NumberOfItems()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='numberOfItems'"
     * @generated
     */
    long getNumberOfItems();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems <em>Number Of Items</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems <em>Number Of Items</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetNumberOfItems()
     * @see #getNumberOfItems()
     * @see #setNumberOfItems(long)
     * @generated
     */
    void unsetNumberOfItems();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getNumberOfItems <em>Number Of Items</em>}' attribute is set.
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetWorkListItemsForGlobalDataType_StartPosition()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
     *        extendedMetaData="kind='attribute' name='startPosition'"
     * @generated
     */
    long getStartPosition();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition <em>Start Position</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    void unsetStartPosition();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.GetWorkListItemsForGlobalDataType#getStartPosition <em>Start Position</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Start Position</em>' attribute is set.
     * @see #unsetStartPosition()
     * @see #getStartPosition()
     * @see #setStartPosition(long)
     * @generated
     */
    boolean isSetStartPosition();

} // GetWorkListItemsForGlobalDataType
