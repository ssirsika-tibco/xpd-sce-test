/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Org Entity Config Attributes Available Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesAvailableResponseType#getOrgEntityConfigAttributesAvailable <em>Org Entity Config Attributes Available</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableResponseType()
 * @model extendedMetaData="name='getOrgEntityConfigAttributesAvailableResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOrgEntityConfigAttributesAvailableResponseType extends EObject {
    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableResponseType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Org Entity Config Attributes Available</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * List of available configuration attributes that can be applied to an organization model entity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Org Entity Config Attributes Available</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesAvailableResponseType_OrgEntityConfigAttributesAvailable()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='orgEntityConfigAttributesAvailable' group='#group:0'"
     * @generated
     */
    EList<OrgEntityConfigAttributesAvailable> getOrgEntityConfigAttributesAvailable();

} // GetOrgEntityConfigAttributesAvailableResponseType
