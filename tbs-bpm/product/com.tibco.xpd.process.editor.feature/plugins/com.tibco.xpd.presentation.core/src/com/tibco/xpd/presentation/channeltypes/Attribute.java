/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Attribute#getEnumLiterals <em>Enum Literals</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultEnumSet <em>Default Enum Set</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Attribute#isRequired <em>Required</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends NamedElement {
    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.presentation.channeltypes.AttributeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.presentation.channeltypes.AttributeType
     * @see #setType(AttributeType)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute_Type()
     * @model required="true"
     * @generated
     */
    AttributeType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.presentation.channeltypes.AttributeType
     * @see #getType()
     * @generated
     */
    void setType(AttributeType value);

    /**
     * Returns the value of the '<em><b>Enum Literals</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.EnumLiteral}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Literals</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Literals</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute_EnumLiterals()
     * @model containment="true"
     * @generated
     */
    EList<EnumLiteral> getEnumLiterals();

    /**
     * Returns the value of the '<em><b>Default Enum Set</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.EnumLiteral}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Enum Set</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Enum Set</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute_DefaultEnumSet()
     * @model
     * @generated
     */
    EList<EnumLiteral> getDefaultEnumSet();

    /**
     * Returns the value of the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Value</em>' attribute.
     * @see #isSetDefaultValue()
     * @see #unsetDefaultValue()
     * @see #setDefaultValue(String)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute_DefaultValue()
     * @model unsettable="true"
     * @generated
     */
    String getDefaultValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Value</em>' attribute.
     * @see #isSetDefaultValue()
     * @see #unsetDefaultValue()
     * @see #getDefaultValue()
     * @generated
     */
    void setDefaultValue(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDefaultValue()
     * @see #getDefaultValue()
     * @see #setDefaultValue(String)
     * @generated
     */
    void unsetDefaultValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.presentation.channeltypes.Attribute#getDefaultValue <em>Default Value</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Default Value</em>' attribute is set.
     * @see #unsetDefaultValue()
     * @see #getDefaultValue()
     * @see #setDefaultValue(String)
     * @generated
     */
    boolean isSetDefaultValue();

    /**
     * Returns the value of the '<em><b>Required</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Required</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Required</em>' attribute.
     * @see #setRequired(boolean)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getAttribute_Required()
     * @model
     * @generated
     */
    boolean isRequired();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.Attribute#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Required</em>' attribute.
     * @see #isRequired()
     * @generated
     */
    void setRequired(boolean value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    String getResolvedDefaultValue();

} // Attribute
