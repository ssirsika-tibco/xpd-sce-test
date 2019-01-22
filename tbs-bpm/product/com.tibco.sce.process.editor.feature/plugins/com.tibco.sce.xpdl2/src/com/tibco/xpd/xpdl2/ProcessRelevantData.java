/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Relevant Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray <em>Is Array</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly <em>Read Only</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessRelevantData()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ProcessRelevantData extends NamedElement, DescribedElement,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Data Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Type</em>' containment reference.
     * @see #setDataType(DataType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessRelevantData_DataType()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='DataType' namespace='##targetNamespace' subclass-wrap='DataType'"
     * @generated
     */
    DataType getDataType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getDataType <em>Data Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Type</em>' containment reference.
     * @see #getDataType()
     * @generated
     */
    void setDataType(DataType value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessRelevantData_Length()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Length' namespace='##targetNamespace'"
     * @generated
     */
    Length getLength();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#getLength <em>Length</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Length</em>' containment reference.
     * @see #getLength()
     * @generated
     */
    void setLength(Length value);

    /**
     * Returns the value of the '<em><b>Is Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Array</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Array</em>' attribute.
     * @see #isSetIsArray()
     * @see #unsetIsArray()
     * @see #setIsArray(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessRelevantData_IsArray()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='IsArray'"
     * @generated
     */
    boolean isIsArray();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray <em>Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Array</em>' attribute.
     * @see #isSetIsArray()
     * @see #unsetIsArray()
     * @see #isIsArray()
     * @generated
     */
    void setIsArray(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray <em>Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsArray()
     * @see #isIsArray()
     * @see #setIsArray(boolean)
     * @generated
     */
    void unsetIsArray();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isIsArray <em>Is Array</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * This is a transient, derived field that is calculated from the underlying 
     * persistent field.  This ought to be trivial, but the {@link FormalParameter} 
     * stores the value as a boolean (good), but {@link DataField} stores it
     * as {@link IsArrayType} (bad!).  Because we once stored everything as 
     * boolean the @link {@link DataField#getDataIsArray()} actually handles
     * a string, this will allow the legacy lower case values which are understood 
     * and converted to the upper case {@link IsArrayType} literal.
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Array</em>' attribute is set.
     * @see #unsetIsArray()
     * @see #isIsArray()
     * @see #setIsArray(boolean)
     * @generated
     */
    boolean isSetIsArray();

    /**
     * Returns the value of the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Read Only</em>' attribute.
     * @see #isSetReadOnly()
     * @see #unsetReadOnly()
     * @see #setReadOnly(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getProcessRelevantData_ReadOnly()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='ReadOnly'"
     * @generated
     */
    boolean isReadOnly();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly <em>Read Only</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly <em>Read Only</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReadOnly()
     * @see #isReadOnly()
     * @see #setReadOnly(boolean)
     * @generated
     */
    void unsetReadOnly();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ProcessRelevantData#isReadOnly <em>Read Only</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Read Only</em>' attribute is set.
     * @see #unsetReadOnly()
     * @see #isReadOnly()
     * @see #setReadOnly(boolean)
     * @generated
     */
    boolean isSetReadOnly();

} // ProcessRelevantData