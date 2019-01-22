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
 * A representation of the model object '<em><b>Get Org Entity Config Attributes Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesType()
 * @model extendedMetaData="name='getOrgEntityConfigAttributes_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOrgEntityConfigAttributesType extends EObject {
    /**
     * Returns the value of the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the resource for whom configuration attribute information is required.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource</em>' attribute.
     * @see #setResource(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesType_Resource()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='resource'"
     * @generated
     */
    String getResource();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesType#getResource <em>Resource</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource</em>' attribute.
     * @see #getResource()
     * @generated
     */
    void setResource(String value);

} // GetOrgEntityConfigAttributesType
