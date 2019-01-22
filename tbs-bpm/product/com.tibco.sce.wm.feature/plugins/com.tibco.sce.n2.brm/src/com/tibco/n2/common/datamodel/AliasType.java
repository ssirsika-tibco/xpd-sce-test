/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alias Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines single WorkModel alias for Work Item Attribute facade mapping.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.AliasType#getAliasDescription <em>Alias Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.AliasType#getAliasName <em>Alias Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.AliasType#getAliasType <em>Alias Type</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.AliasType#getFacadeName <em>Facade Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasType()
 * @model extendedMetaData="name='AliasType' kind='empty'"
 * @generated
 */
public interface AliasType extends EObject {
    /**
     * Returns the value of the '<em><b>Alias Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Alias Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Alias Description</em>' attribute.
     * @see #setAliasDescription(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasType_AliasDescription()
     * @model dataType="com.tibco.n2.common.datamodel.AliasDescriptionType"
     *        extendedMetaData="kind='attribute' name='aliasDescription'"
     * @generated
     */
    String getAliasDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getAliasDescription <em>Alias Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alias Description</em>' attribute.
     * @see #getAliasDescription()
     * @generated
     */
    void setAliasDescription(String value);

    /**
     * Returns the value of the '<em><b>Alias Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the alias to be used
     * <!-- end-model-doc -->
     * @return the value of the '<em>Alias Name</em>' attribute.
     * @see #setAliasName(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasType_AliasName()
     * @model dataType="com.tibco.n2.common.datamodel.AliasNameType" required="true"
     *        extendedMetaData="kind='attribute' name='aliasName'"
     * @generated
     */
    String getAliasName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getAliasName <em>Alias Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alias Name</em>' attribute.
     * @see #getAliasName()
     * @generated
     */
    void setAliasName(String value);

    /**
     * Returns the value of the '<em><b>Alias Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.datamodel.AliasTypeType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Type of the alias (e.g. string, integer etc.).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Alias Type</em>' attribute.
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @see #isSetAliasType()
     * @see #unsetAliasType()
     * @see #setAliasType(AliasTypeType)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasType_AliasType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='aliasType'"
     * @generated
     */
    AliasTypeType getAliasType();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getAliasType <em>Alias Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alias Type</em>' attribute.
     * @see com.tibco.n2.common.datamodel.AliasTypeType
     * @see #isSetAliasType()
     * @see #unsetAliasType()
     * @see #getAliasType()
     * @generated
     */
    void setAliasType(AliasTypeType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getAliasType <em>Alias Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAliasType()
     * @see #getAliasType()
     * @see #setAliasType(AliasTypeType)
     * @generated
     */
    void unsetAliasType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getAliasType <em>Alias Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Alias Type</em>' attribute is set.
     * @see #unsetAliasType()
     * @see #getAliasType()
     * @see #setAliasType(AliasTypeType)
     * @generated
     */
    boolean isSetAliasType();

    /**
     * Returns the value of the '<em><b>Facade Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Unique name for this set of attribute aliases.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Facade Name</em>' attribute.
     * @see #setFacadeName(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getAliasType_FacadeName()
     * @model dataType="com.tibco.n2.common.datamodel.FacadeNameType" required="true"
     *        extendedMetaData="kind='attribute' name='facadeName'"
     * @generated
     */
    String getFacadeName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.AliasType#getFacadeName <em>Facade Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Facade Name</em>' attribute.
     * @see #getFacadeName()
     * @generated
     */
    void setFacadeName(String value);

} // AliasType
