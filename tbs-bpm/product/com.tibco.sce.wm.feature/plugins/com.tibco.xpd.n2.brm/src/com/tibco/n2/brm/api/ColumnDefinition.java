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
 * A representation of the model object '<em><b>Column Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Details of a field defined by BRM that can be used in sort/filter criteria expressions for work item lists.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ColumnDefinition#getCapability <em>Capability</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ColumnDefinition#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ColumnDefinition#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ColumnDefinition#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ColumnDefinition#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition()
 * @model extendedMetaData="name='ColumnDefinition' kind='empty'"
 * @generated
 */
public interface ColumnDefinition extends EObject {
    /**
     * Returns the value of the '<em><b>Capability</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.ColumnCapability}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Enumerated value that defines whether the field can be used in expressions that define sort criteria and/or filter criteria.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Capability</em>' attribute.
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @see #isSetCapability()
     * @see #unsetCapability()
     * @see #setCapability(ColumnCapability)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition_Capability()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='capability'"
     * @generated
     */
    ColumnCapability getCapability();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getCapability <em>Capability</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Capability</em>' attribute.
     * @see com.tibco.n2.brm.api.ColumnCapability
     * @see #isSetCapability()
     * @see #unsetCapability()
     * @see #getCapability()
     * @generated
     */
    void setCapability(ColumnCapability value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getCapability <em>Capability</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCapability()
     * @see #getCapability()
     * @see #setCapability(ColumnCapability)
     * @generated
     */
    void unsetCapability();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getCapability <em>Capability</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Capability</em>' attribute is set.
     * @see #unsetCapability()
     * @see #getCapability()
     * @see #setCapability(ColumnCapability)
     * @generated
     */
    boolean isSetCapability();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Textual description of the field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition_Description()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #isSetId()
     * @see #unsetId()
     * @see #setId(short)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition_Id()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    short getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #isSetId()
     * @see #unsetId()
     * @see #getId()
     * @generated
     */
    void setId(short value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetId()
     * @see #getId()
     * @see #setId(short)
     * @generated
     */
    void unsetId();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getId <em>Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Id</em>' attribute is set.
     * @see #unsetId()
     * @see #getId()
     * @see #setId(short)
     * @generated
     */
    boolean isSetId();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.ColumnType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Type of the field (for example, string or numeric).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.n2.brm.api.ColumnType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(ColumnType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getColumnDefinition_Type()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='type'"
     * @generated
     */
    ColumnType getType();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.n2.brm.api.ColumnType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(ColumnType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(ColumnType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ColumnDefinition#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(ColumnType)
     * @generated
     */
    boolean isSetType();

} // ColumnDefinition
