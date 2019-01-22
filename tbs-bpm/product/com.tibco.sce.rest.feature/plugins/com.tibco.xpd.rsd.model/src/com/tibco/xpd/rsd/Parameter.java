/**
 */
package com.tibco.xpd.rsd;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.Parameter#getStyle <em>Style</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Parameter#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Parameter#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Parameter#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.Parameter#isFixed <em>Fixed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.rsd.RsdPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends NamedElement {
    /**
     * Returns the value of the '<em><b>Style</b></em>' attribute.
     * The default value is <code>"QUERY"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.rsd.ParameterStyle}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Style</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Style</em>' attribute.
     * @see com.tibco.xpd.rsd.ParameterStyle
     * @see #setStyle(ParameterStyle)
     * @see com.tibco.xpd.rsd.RsdPackage#getParameter_Style()
     * @model default="QUERY"
     * @generated
     */
    ParameterStyle getStyle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Parameter#getStyle <em>Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Style</em>' attribute.
     * @see com.tibco.xpd.rsd.ParameterStyle
     * @see #getStyle()
     * @generated
     */
    void setStyle(ParameterStyle value);

    /**
     * Returns the value of the '<em><b>Data Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.rsd.DataType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Type</em>' attribute.
     * @see com.tibco.xpd.rsd.DataType
     * @see #setDataType(DataType)
     * @see com.tibco.xpd.rsd.RsdPackage#getParameter_DataType()
     * @model
     * @generated
     */
    DataType getDataType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Parameter#getDataType <em>Data Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Type</em>' attribute.
     * @see com.tibco.xpd.rsd.DataType
     * @see #getDataType()
     * @generated
     */
    void setDataType(DataType value);

    /**
     * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mandatory</em>' attribute.
     * @see #setMandatory(boolean)
     * @see com.tibco.xpd.rsd.RsdPackage#getParameter_Mandatory()
     * @model default="false"
     * @generated
     */
    boolean isMandatory();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Parameter#isMandatory <em>Mandatory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mandatory</em>' attribute.
     * @see #isMandatory()
     * @generated
     */
    void setMandatory(boolean value);

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
     * @see com.tibco.xpd.rsd.RsdPackage#getParameter_DefaultValue()
     * @model unsettable="true"
     * @generated
     */
    String getDefaultValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Parameter#getDefaultValue <em>Default Value</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.rsd.Parameter#getDefaultValue <em>Default Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDefaultValue()
     * @see #getDefaultValue()
     * @see #setDefaultValue(String)
     * @generated
     */
    void unsetDefaultValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.rsd.Parameter#getDefaultValue <em>Default Value</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Fixed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fixed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fixed</em>' attribute.
     * @see #setFixed(boolean)
     * @see com.tibco.xpd.rsd.RsdPackage#getParameter_Fixed()
     * @model
     * @generated
     */
    boolean isFixed();

    /**
     * Sets the value of the '{@link com.tibco.xpd.rsd.Parameter#isFixed <em>Fixed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fixed</em>' attribute.
     * @see #isFixed()
     * @generated
     */
    void setFixed(boolean value);

} // Parameter
