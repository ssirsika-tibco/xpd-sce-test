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
 * A representation of the model object '<em><b>Org Entity Config Attribute Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a configuration attribute setting (a name/value pair).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeValue <em>Attribute Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributeSet()
 * @model extendedMetaData="name='OrgEntityConfigAttributeSet' kind='elementOnly'"
 * @generated
 */
public interface OrgEntityConfigAttributeSet extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the configuration attribute. This must be one of the following:
     * 
     * - MaxOpenWorkItemCount, which defines the maximum number of open work items allowed concurrently (for the specified organization model entity).
     * 
     * - WorkItemAutoOpen, which defines whether to automatically open the next work item when completing a work item.
     * 
     * NOTE: Other configuration attributes are part of the private API and reserved for internal use.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Name</em>' attribute.
     * @see #setAttributeName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributeSet_AttributeName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='attributeName'"
     * @generated
     */
    String getAttributeName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeName <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Name</em>' attribute.
     * @see #getAttributeName()
     * @generated
     */
    void setAttributeName(String value);

    /**
     * Returns the value of the '<em><b>Attribute Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Value of the configuration attribute. This must be one of the following:
     * 
     * - for MaxOpenWorkItemCount, an integer.
     * - for WorkItemAutoOpen, a boolean.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Value</em>' attribute.
     * @see #setAttributeValue(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributeSet_AttributeValue()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='attributeValue'"
     * @generated
     */
    String getAttributeValue();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributeSet#getAttributeValue <em>Attribute Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Value</em>' attribute.
     * @see #getAttributeValue()
     * @generated
     */
    void setAttributeValue(String value);

} // OrgEntityConfigAttributeSet
