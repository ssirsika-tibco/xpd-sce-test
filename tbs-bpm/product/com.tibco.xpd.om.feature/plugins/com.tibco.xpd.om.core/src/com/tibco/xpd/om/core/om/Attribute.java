/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Attribute#getValues <em>Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Attribute#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Attribute#getDefaultEnumSetValues <em>Default Enum Set Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Attribute#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends Feature {
    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.om.core.om.AttributeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.AttributeType
     * @see #setType(AttributeType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute_Type()
     * @model required="true"
     * @generated
     */
    AttributeType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.om.core.om.AttributeType
     * @see #getType()
     * @generated
     */
    void setType(AttributeType value);

    /**
     * Returns the value of the '<em><b>Values</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.EnumValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Values</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute_Values()
     * @model containment="true"
     * @generated
     */
    EList<EnumValue> getValues();

    /**
     * Returns the value of the '<em><b>Default Enum Set Values</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.EnumValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Enum Set Values</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Enum Set Values</em>' reference list.
     * @see #isSetDefaultEnumSetValues()
     * @see #unsetDefaultEnumSetValues()
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute_DefaultEnumSetValues()
     * @model unsettable="true"
     * @generated
     */
    EList<EnumValue> getDefaultEnumSetValues();

    /**
     * Unsets the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultEnumSetValues <em>Default Enum Set Values</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDefaultEnumSetValues()
     * @see #getDefaultEnumSetValues()
     * @generated
     */
    void unsetDefaultEnumSetValues();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultEnumSetValues <em>Default Enum Set Values</em>}' reference list is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Default Enum Set Values</em>' reference list is set.
     * @see #unsetDefaultEnumSetValues()
     * @see #getDefaultEnumSetValues()
     * @generated
     */
    boolean isSetDefaultEnumSetValues();

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

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
     * @see com.tibco.xpd.om.core.om.OMPackage#getAttribute_DefaultValue()
     * @model unsettable="true"
     * @generated
     */
    String getDefaultValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultValue <em>Default Value</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDefaultValue()
     * @see #getDefaultValue()
     * @see #setDefaultValue(String)
     * @generated
     */
    void unsetDefaultValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.om.core.om.Attribute#getDefaultValue <em>Default Value</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Default Value</em>' attribute is set.
     * @see #unsetDefaultValue()
     * @see #getDefaultValue()
     * @see #setDefaultValue(String)
     * @generated
     */
    boolean isSetDefaultValue();

} // Attribute
