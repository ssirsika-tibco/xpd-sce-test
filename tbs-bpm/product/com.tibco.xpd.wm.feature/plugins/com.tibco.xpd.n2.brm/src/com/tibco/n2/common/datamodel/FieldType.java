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
 * A representation of the model object '<em><b>Field Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines a single data field (its name, type and an optional value).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#getSimpleSpec <em>Simple Spec</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#getComplexSpec <em>Complex Spec</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#isOptional <em>Optional</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.FieldType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType()
 * @model extendedMetaData="name='FieldType' kind='elementOnly'"
 * @generated
 */
public interface FieldType extends EObject {
    /**
     * Returns the value of the '<em><b>Simple Spec</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Value of the data field as a simple type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Simple Spec</em>' containment reference.
     * @see #setSimpleSpec(SimpleSpecType)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_SimpleSpec()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='simpleSpec'"
     * @generated
     */
    SimpleSpecType getSimpleSpec();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getSimpleSpec <em>Simple Spec</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Simple Spec</em>' containment reference.
     * @see #getSimpleSpec()
     * @generated
     */
    void setSimpleSpec(SimpleSpecType value);

    /**
     * Returns the value of the '<em><b>Complex Spec</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Value of the data field as a complex type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Complex Spec</em>' containment reference.
     * @see #setComplexSpec(ComplexSpecType)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_ComplexSpec()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='complexSpec'"
     * @generated
     */
    ComplexSpecType getComplexSpec();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getComplexSpec <em>Complex Spec</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Complex Spec</em>' containment reference.
     * @see #getComplexSpec()
     * @generated
     */
    void setComplexSpec(ComplexSpecType value);

    /**
     * Returns the value of the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Indicates whether this parameter contains an array of values (default is false).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Array</em>' attribute.
     * @see #isSetArray()
     * @see #unsetArray()
     * @see #setArray(boolean)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_Array()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='array'"
     * @generated
     */
    boolean isArray();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isArray <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Array</em>' attribute.
     * @see #isSetArray()
     * @see #unsetArray()
     * @see #isArray()
     * @generated
     */
    void setArray(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isArray <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetArray()
     * @see #isArray()
     * @see #setArray(boolean)
     * @generated
     */
    void unsetArray();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isArray <em>Array</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Array</em>' attribute is set.
     * @see #unsetArray()
     * @see #isArray()
     * @see #setArray(boolean)
     * @generated
     */
    boolean isSetArray();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name of the data field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Optional</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value that defines whether the parameter is optional (default is false).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Optional</em>' attribute.
     * @see #isSetOptional()
     * @see #unsetOptional()
     * @see #setOptional(boolean)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_Optional()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='optional'"
     * @generated
     */
    boolean isOptional();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isOptional <em>Optional</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Optional</em>' attribute.
     * @see #isSetOptional()
     * @see #unsetOptional()
     * @see #isOptional()
     * @generated
     */
    void setOptional(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isOptional <em>Optional</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOptional()
     * @see #isOptional()
     * @see #setOptional(boolean)
     * @generated
     */
    void unsetOptional();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.FieldType#isOptional <em>Optional</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Optional</em>' attribute is set.
     * @see #unsetOptional()
     * @see #isOptional()
     * @see #setOptional(boolean)
     * @generated
     */
    boolean isSetOptional();

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.datamodel.TypeType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Type of the data field (e.g. string, integer etc.).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.n2.common.datamodel.TypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(TypeType)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getFieldType_Type()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='type'"
     * @generated
     */
    TypeType getType();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.n2.common.datamodel.TypeType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(TypeType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(TypeType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.FieldType#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(TypeType)
     * @generated
     */
    boolean isSetType();

} // FieldType
