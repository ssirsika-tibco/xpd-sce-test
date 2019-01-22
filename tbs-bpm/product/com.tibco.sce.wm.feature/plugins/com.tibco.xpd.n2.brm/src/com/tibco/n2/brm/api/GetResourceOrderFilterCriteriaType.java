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
 * A representation of the model object '<em><b>Get Resource Order Filter Criteria Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetResourceOrderFilterCriteriaType()
 * @model extendedMetaData="name='getResourceOrderFilterCriteria_._type' kind='elementOnly'"
 * @generated
 */
public interface GetResourceOrderFilterCriteriaType extends EObject {
    /**
     * Returns the value of the '<em><b>Resource ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the resource for whom work item list sort/filter criteria is required.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource ID</em>' attribute.
     * @see #setResourceID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetResourceOrderFilterCriteriaType_ResourceID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='resourceID'"
     * @generated
     */
    String getResourceID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetResourceOrderFilterCriteriaType#getResourceID <em>Resource ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource ID</em>' attribute.
     * @see #getResourceID()
     * @generated
     */
    void setResourceID(String value);

} // GetResourceOrderFilterCriteriaType
