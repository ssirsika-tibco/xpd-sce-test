/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getBasicType <em>Basic Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getDeclaredType <em>Declared Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getSchemaType <em>Schema Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getRecordType <em>Record Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getUnionType <em>Union Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getEnumerationType <em>Enumeration Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getArrayType <em>Array Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getListType <em>List Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getLowerIndex <em>Lower Index</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ArrayType#getUpperIndex <em>Upper Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType()
 * @model extendedMetaData="name='ArrayType_._type' kind='elementOnly'"
 * @generated
 */
public interface ArrayType extends DataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Basic Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Basic Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Basic Type</em>' containment reference.
     * @see #setBasicType(BasicType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_BasicType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BasicType' namespace='##targetNamespace'"
     * @generated
     */
    BasicType getBasicType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getBasicType <em>Basic Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Basic Type</em>' containment reference.
     * @see #getBasicType()
     * @generated
     */
    void setBasicType(BasicType value);

    /**
     * Returns the value of the '<em><b>Declared Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Declared Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Declared Type</em>' containment reference.
     * @see #setDeclaredType(DeclaredType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_DeclaredType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DeclaredType' namespace='##targetNamespace'"
     * @generated
     */
    DeclaredType getDeclaredType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getDeclaredType <em>Declared Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Declared Type</em>' containment reference.
     * @see #getDeclaredType()
     * @generated
     */
    void setDeclaredType(DeclaredType value);

    /**
     * Returns the value of the '<em><b>Schema Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Schema Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Schema Type</em>' containment reference.
     * @see #setSchemaType(Schema)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_SchemaType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SchemaType' namespace='##targetNamespace'"
     * @generated
     */
    Schema getSchemaType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getSchemaType <em>Schema Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Schema Type</em>' containment reference.
     * @see #getSchemaType()
     * @generated
     */
    void setSchemaType(Schema value);

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Reference</em>' containment reference.
     * @see #setExternalReference(ExternalReference)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_ExternalReference()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
     * @generated
     */
    ExternalReference getExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getExternalReference <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>External Reference</em>' containment reference.
     * @see #getExternalReference()
     * @generated
     */
    void setExternalReference(ExternalReference value);

    /**
     * Returns the value of the '<em><b>Record Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Record Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Record Type</em>' containment reference.
     * @see #setRecordType(RecordType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_RecordType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RecordType' namespace='##targetNamespace'"
     * @generated
     */
    RecordType getRecordType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getRecordType <em>Record Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Record Type</em>' containment reference.
     * @see #getRecordType()
     * @generated
     */
    void setRecordType(RecordType value);

    /**
     * Returns the value of the '<em><b>Union Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Union Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Union Type</em>' containment reference.
     * @see #setUnionType(UnionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_UnionType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='UnionType' namespace='##targetNamespace'"
     * @generated
     */
    UnionType getUnionType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getUnionType <em>Union Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Union Type</em>' containment reference.
     * @see #getUnionType()
     * @generated
     */
    void setUnionType(UnionType value);

    /**
     * Returns the value of the '<em><b>Enumeration Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enumeration Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enumeration Type</em>' containment reference.
     * @see #setEnumerationType(EnumerationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_EnumerationType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='EnumerationType' namespace='##targetNamespace'"
     * @generated
     */
    EnumerationType getEnumerationType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getEnumerationType <em>Enumeration Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enumeration Type</em>' containment reference.
     * @see #getEnumerationType()
     * @generated
     */
    void setEnumerationType(EnumerationType value);

    /**
     * Returns the value of the '<em><b>Array Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Array Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Array Type</em>' containment reference.
     * @see #setArrayType(ArrayType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_ArrayType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ArrayType' namespace='##targetNamespace'"
     * @generated
     */
    ArrayType getArrayType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getArrayType <em>Array Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Array Type</em>' containment reference.
     * @see #getArrayType()
     * @generated
     */
    void setArrayType(ArrayType value);

    /**
     * Returns the value of the '<em><b>List Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>List Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>List Type</em>' containment reference.
     * @see #setListType(ListType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_ListType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ListType' namespace='##targetNamespace'"
     * @generated
     */
    ListType getListType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getListType <em>List Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>List Type</em>' containment reference.
     * @see #getListType()
     * @generated
     */
    void setListType(ListType value);

    /**
     * Returns the value of the '<em><b>Lower Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lower Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lower Index</em>' attribute.
     * @see #setLowerIndex(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_LowerIndex()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='LowerIndex'"
     * @generated
     */
    String getLowerIndex();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getLowerIndex <em>Lower Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lower Index</em>' attribute.
     * @see #getLowerIndex()
     * @generated
     */
    void setLowerIndex(String value);

    /**
     * Returns the value of the '<em><b>Upper Index</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Upper Index</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Upper Index</em>' attribute.
     * @see #setUpperIndex(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArrayType_UpperIndex()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='UpperIndex'"
     * @generated
     */
    String getUpperIndex();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ArrayType#getUpperIndex <em>Upper Index</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Upper Index</em>' attribute.
     * @see #getUpperIndex()
     * @generated
     */
    void setUpperIndex(String value);

} // ArrayType
