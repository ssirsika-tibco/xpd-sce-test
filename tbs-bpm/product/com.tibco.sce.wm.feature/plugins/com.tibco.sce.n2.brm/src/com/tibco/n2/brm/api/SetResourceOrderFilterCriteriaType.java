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
 * A representation of the model object '<em><b>Set Resource Order Filter Criteria Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getSetResourceOrderFilterCriteriaType()
 * @model extendedMetaData="name='setResourceOrderFilterCriteria_._type' kind='elementOnly'"
 * @generated
 */
public interface SetResourceOrderFilterCriteriaType extends EObject {
    /**
     * Returns the value of the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of the work item list sort/filter criteria that should be be set for the specified resource.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #setOrderFilterCriteria(OrderFilterCriteria)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetResourceOrderFilterCriteriaType_OrderFilterCriteria()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='orderFilterCriteria'"
     * @generated
     */
    OrderFilterCriteria getOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getOrderFilterCriteria <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #getOrderFilterCriteria()
     * @generated
     */
    void setOrderFilterCriteria(OrderFilterCriteria value);

    /**
     * Returns the value of the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the resource for whom work item list sort/filter criteria should be set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource ID</em>' attribute.
     * @see #setResourceID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetResourceOrderFilterCriteriaType_ResourceID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='resourceID'"
     * @generated
     */
    String getResourceID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource ID</em>' attribute.
     * @see #getResourceID()
     * @generated
     */
    void setResourceID(String value);

} // SetResourceOrderFilterCriteriaType
