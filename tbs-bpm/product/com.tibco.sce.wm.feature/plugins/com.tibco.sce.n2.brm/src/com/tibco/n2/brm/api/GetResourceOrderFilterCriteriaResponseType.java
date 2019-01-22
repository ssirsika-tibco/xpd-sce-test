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
 * A representation of the model object '<em><b>Get Resource Order Filter Criteria Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType#getOrderFilterCriteria <em>Order Filter Criteria</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetResourceOrderFilterCriteriaResponseType()
 * @model extendedMetaData="name='getResourceOrderFilterCriteriaResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetResourceOrderFilterCriteriaResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Order Filter Criteria</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of the work item list sort/filter defined for the requested resource.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #setOrderFilterCriteria(OrderFilterCriteria)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetResourceOrderFilterCriteriaResponseType_OrderFilterCriteria()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='orderFilterCriteria'"
     * @generated
     */
    OrderFilterCriteria getOrderFilterCriteria();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaResponseType#getOrderFilterCriteria <em>Order Filter Criteria</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Order Filter Criteria</em>' containment reference.
     * @see #getOrderFilterCriteria()
     * @generated
     */
    void setOrderFilterCriteria(OrderFilterCriteria value);

} // GetResourceOrderFilterCriteriaResponseType
