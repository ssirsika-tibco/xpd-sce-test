/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Spec Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.SimpleSpecType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal <em>Decimal</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.SimpleSpecType#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getSimpleSpecType()
 * @model extendedMetaData="name='simpleSpec_._type' kind='elementOnly'"
 * @generated
 */
public interface SimpleSpecType extends EObject {
    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If this is a standard type filed this will be the real value of the data field. If the data is of type Data Reference this value will be a reference to a Global Data Object.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Value</em>' attribute list.
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getSimpleSpecType_Value()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='value'"
     * @generated
     */
    EList<String> getValue();

    /**
     * Returns the value of the '<em><b>Decimal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Number of decimal places allowed for the data field.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Decimal</em>' attribute.
     * @see #isSetDecimal()
     * @see #unsetDecimal()
     * @see #setDecimal(int)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getSimpleSpecType_Decimal()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='decimal'"
     * @generated
     */
    int getDecimal();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Decimal</em>' attribute.
     * @see #isSetDecimal()
     * @see #unsetDecimal()
     * @see #getDecimal()
     * @generated
     */
    void setDecimal(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal <em>Decimal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDecimal()
     * @see #getDecimal()
     * @see #setDecimal(int)
     * @generated
     */
    void unsetDecimal();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getDecimal <em>Decimal</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Decimal</em>' attribute is set.
     * @see #unsetDecimal()
     * @see #getDecimal()
     * @see #setDecimal(int)
     * @generated
     */
    boolean isSetDecimal();

    /**
     * Returns the value of the '<em><b>Length</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Maximum length allowed for the data field (not including decimal places).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Length</em>' attribute.
     * @see #isSetLength()
     * @see #unsetLength()
     * @see #setLength(int)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getSimpleSpecType_Length()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='length'"
     * @generated
     */
    int getLength();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getLength <em>Length</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Length</em>' attribute.
     * @see #isSetLength()
     * @see #unsetLength()
     * @see #getLength()
     * @generated
     */
    void setLength(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getLength <em>Length</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLength()
     * @see #getLength()
     * @see #setLength(int)
     * @generated
     */
    void unsetLength();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.common.datamodel.SimpleSpecType#getLength <em>Length</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Length</em>' attribute is set.
     * @see #unsetLength()
     * @see #getLength()
     * @see #setLength(int)
     * @generated
     */
    boolean isSetLength();

} // SimpleSpecType
