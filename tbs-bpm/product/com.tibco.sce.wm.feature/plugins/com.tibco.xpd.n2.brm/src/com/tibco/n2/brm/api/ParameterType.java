/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a data parameter passed to BRM when a work item is scheduled.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.ParameterType#getComplexValue <em>Complex Value</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ParameterType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ParameterType#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.ParameterType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getParameterType()
 * @model extendedMetaData="name='ParameterType' kind='elementOnly'"
 * @generated
 */
public interface ParameterType extends EObject {
    /**
     * Returns the value of the '<em><b>Complex Value</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Value(s) of the complex object.  The value is of xs:anyType as it contains the entire complex object as XML.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Complex Value</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getParameterType_ComplexValue()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ComplexValue'"
     * @generated
     */
    EList<EObject> getComplexValue();

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Parameter value(s) - for example, 10.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Value</em>' attribute list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getParameterType_Value()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='Value'"
     * @generated
     */
    EList<String> getValue();

    /**
     * Returns the value of the '<em><b>Array</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Boolean value indicating whether this parameter contains an array of values (default is false).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Array</em>' attribute.
     * @see #isSetArray()
     * @see #unsetArray()
     * @see #setArray(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getParameterType_Array()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='Array'"
     * @generated
     */
    boolean isArray();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ParameterType#isArray <em>Array</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.n2.brm.api.ParameterType#isArray <em>Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetArray()
     * @see #isArray()
     * @see #setArray(boolean)
     * @generated
     */
    void unsetArray();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.ParameterType#isArray <em>Array</em>}' attribute is set.
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
     * Parameter name - for example, Age.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getParameterType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.ParameterType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // ParameterType
