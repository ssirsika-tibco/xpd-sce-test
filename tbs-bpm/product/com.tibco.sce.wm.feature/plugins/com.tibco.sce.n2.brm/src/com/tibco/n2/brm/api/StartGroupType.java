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
 * A representation of the model object '<em><b>Start Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.StartGroupType#getGroupType <em>Group Type</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.StartGroupType#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getStartGroupType()
 * @model extendedMetaData="name='startGroup_._type' kind='elementOnly'"
 * @generated
 */
public interface StartGroupType extends EObject {
    /**
     * Returns the value of the '<em><b>Group Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.WorkGroupType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Enumeration identifying the type of work group to be used.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Group Type</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @see #isSetGroupType()
     * @see #unsetGroupType()
     * @see #setGroupType(WorkGroupType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getStartGroupType_GroupType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='groupType'"
     * @generated
     */
    WorkGroupType getGroupType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.StartGroupType#getGroupType <em>Group Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group Type</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkGroupType
     * @see #isSetGroupType()
     * @see #unsetGroupType()
     * @see #getGroupType()
     * @generated
     */
    void setGroupType(WorkGroupType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.StartGroupType#getGroupType <em>Group Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGroupType()
     * @see #getGroupType()
     * @see #setGroupType(WorkGroupType)
     * @generated
     */
    void unsetGroupType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.StartGroupType#getGroupType <em>Group Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Group Type</em>' attribute is set.
     * @see #unsetGroupType()
     * @see #getGroupType()
     * @see #setGroupType(WorkGroupType)
     * @generated
     */
    boolean isSetGroupType();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Textual description of the work group
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getStartGroupType_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.StartGroupType#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // StartGroupType
