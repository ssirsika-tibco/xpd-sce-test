/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Basic Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.BasicType#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BasicType#getPrecision <em>Precision</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BasicType#getScale <em>Scale</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.BasicType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBasicType()
 * @model extendedMetaData="name='BasicType_._type' kind='elementOnly' features-order='length precision scale otherElements'"
 * @generated
 */
public interface BasicType extends DataType, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Length</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Length</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Length</em>' containment reference.
     * @see #setLength(Length)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBasicType_Length()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Length' namespace='##targetNamespace'"
     * @generated
     */
    Length getLength();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getLength <em>Length</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Length</em>' containment reference.
     * @see #getLength()
     * @generated
     */
    void setLength(Length value);

    /**
     * Returns the value of the '<em><b>Precision</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Precision</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Precision</em>' containment reference.
     * @see #setPrecision(Precision)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBasicType_Precision()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Precision' namespace='##targetNamespace'"
     * @generated
     */
    Precision getPrecision();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getPrecision <em>Precision</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Precision</em>' containment reference.
     * @see #getPrecision()
     * @generated
     */
    void setPrecision(Precision value);

    /**
     * Returns the value of the '<em><b>Scale</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Scale</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Scale</em>' containment reference.
     * @see #setScale(Scale)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBasicType_Scale()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Scale' namespace='##targetNamespace'"
     * @generated
     */
    Scale getScale();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getScale <em>Scale</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Scale</em>' containment reference.
     * @see #getScale()
     * @generated
     */
    void setScale(Scale value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The default value is <code>"STRING"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.BasicTypeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.BasicTypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(BasicTypeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getBasicType_Type()
     * @model default="STRING" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    BasicTypeType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.BasicTypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(BasicTypeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(BasicTypeType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.BasicType#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(BasicTypeType)
     * @generated
     */
    boolean isSetType();

} // BasicType
