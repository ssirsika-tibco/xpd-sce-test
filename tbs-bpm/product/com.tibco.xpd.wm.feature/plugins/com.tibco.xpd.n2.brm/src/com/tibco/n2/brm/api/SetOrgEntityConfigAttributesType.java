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
 * A representation of the model object '<em><b>Set Org Entity Config Attributes Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getOrgEntityConfigAttributeSet <em>Org Entity Config Attribute Set</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getSetOrgEntityConfigAttributesType()
 * @model extendedMetaData="name='setOrgEntityConfigAttributes_._type' kind='elementOnly'"
 * @generated
 */
public interface SetOrgEntityConfigAttributesType extends EObject {
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
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetOrgEntityConfigAttributesType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Org Entity Config Attribute Set</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Set of configuration attributes and values to be set for the specified resource.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Org Entity Config Attribute Set</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetOrgEntityConfigAttributesType_OrgEntityConfigAttributeSet()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='orgEntityConfigAttributeSet' group='#group:0'"
     * @generated
     */
    EList<OrgEntityConfigAttributeSet> getOrgEntityConfigAttributeSet();

    /**
     * Returns the value of the '<em><b>Resource</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the resource for whom configuration attributes should be set.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Resource</em>' attribute.
     * @see #setResource(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetOrgEntityConfigAttributesType_Resource()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='resource'"
     * @generated
     */
    String getResource();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SetOrgEntityConfigAttributesType#getResource <em>Resource</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource</em>' attribute.
     * @see #getResource()
     * @generated
     */
    void setResource(String value);

} // SetOrgEntityConfigAttributesType
