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
 * A representation of the model object '<em><b>Get Org Entity Config Attributes Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.GetOrgEntityConfigAttributesResponseType#getOrgEntityConfigAttribute <em>Org Entity Config Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesResponseType()
 * @model extendedMetaData="name='getOrgEntityConfigAttributesResponse_._type' kind='elementOnly'"
 * @generated
 */
public interface GetOrgEntityConfigAttributesResponseType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesResponseType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Org Entity Config Attribute</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.OrgEntityConfigAttribute}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Configuration attribute information for the specified resource.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Org Entity Config Attribute</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getGetOrgEntityConfigAttributesResponseType_OrgEntityConfigAttribute()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='orgEntityConfigAttribute' group='#group:0'"
     * @generated
     */
    EList<OrgEntityConfigAttribute> getOrgEntityConfigAttribute();

} // GetOrgEntityConfigAttributesResponseType
