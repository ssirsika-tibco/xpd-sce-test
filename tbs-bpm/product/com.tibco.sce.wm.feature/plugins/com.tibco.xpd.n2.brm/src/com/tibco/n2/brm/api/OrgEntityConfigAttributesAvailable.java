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
 * A representation of the model object '<em><b>Org Entity Config Attributes Available</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Set of configuration attributes provided by BRM that can be applied to organization model entities.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly <em>Read Only</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributesAvailable()
 * @model extendedMetaData="name='OrgEntityConfigAttributesAvailable' kind='elementOnly'"
 * @generated
 */
public interface OrgEntityConfigAttributesAvailable extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the configuration attribute.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Attribute Name</em>' attribute.
     * @see #setAttributeName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributesAvailable_AttributeName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='attributeName'"
     * @generated
     */
    String getAttributeName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#getAttributeName <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Name</em>' attribute.
     * @see #getAttributeName()
     * @generated
     */
    void setAttributeName(String value);

    /**
     * Returns the value of the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value specifying whether the configuration attribute is read only.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Read Only</em>' attribute.
     * @see #isSetReadOnly()
     * @see #unsetReadOnly()
     * @see #setReadOnly(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getOrgEntityConfigAttributesAvailable_ReadOnly()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='element' name='readOnly'"
     * @generated
     */
    boolean isReadOnly();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Read Only</em>' attribute.
     * @see #isSetReadOnly()
     * @see #unsetReadOnly()
     * @see #isReadOnly()
     * @generated
     */
    void setReadOnly(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReadOnly()
     * @see #isReadOnly()
     * @see #setReadOnly(boolean)
     * @generated
     */
    void unsetReadOnly();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.OrgEntityConfigAttributesAvailable#isReadOnly <em>Read Only</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Read Only</em>' attribute is set.
     * @see #unsetReadOnly()
     * @see #isReadOnly()
     * @see #setReadOnly(boolean)
     * @generated
     */
    boolean isSetReadOnly();

} // OrgEntityConfigAttributesAvailable
