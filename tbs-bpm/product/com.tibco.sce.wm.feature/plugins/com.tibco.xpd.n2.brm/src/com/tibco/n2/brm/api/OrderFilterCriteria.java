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
 * A representation of the model object '<em><b>Order Filter Criteria</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Specification of sort/filter criteria to be applied to a work item list.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.OrderFilterCriteria#getOrder <em>Order</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.OrderFilterCriteria#getFilter <em>Filter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getOrderFilterCriteria()
 * @model extendedMetaData="name='OrderFilterCriteria' kind='elementOnly'"
 * @generated
 */
public interface OrderFilterCriteria extends EObject {
    /**
     * Returns the value of the '<em><b>Order</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Expression defining the sort criteria to be applied to the work item list. 
     * 
     * Refer to the developer's guide for more information about how to define this expression.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order</em>' attribute.
     * @see #setOrder(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrderFilterCriteria_Order()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='order'"
     * @generated
     */
    String getOrder();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrderFilterCriteria#getOrder <em>Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order</em>' attribute.
     * @see #getOrder()
     * @generated
     */
    void setOrder(String value);

    /**
     * Returns the value of the '<em><b>Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Expression defining the filter criteria to be applied to the work item list. 
     * 
     * Refer to the developer's guide for more information about how to define this expression.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Filter</em>' attribute.
     * @see #setFilter(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrderFilterCriteria_Filter()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='filter'"
     * @generated
     */
    String getFilter();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrderFilterCriteria#getFilter <em>Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Filter</em>' attribute.
     * @see #getFilter()
     * @generated
     */
    void setFilter(String value);

} // OrderFilterCriteria
