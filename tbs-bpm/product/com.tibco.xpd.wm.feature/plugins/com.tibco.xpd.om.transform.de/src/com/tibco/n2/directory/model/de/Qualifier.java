/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Describes an arbitrary qualifier attribute that can be associated with a
 *         Privilege or Capability. For qualifiers of data-type "EnumSet" and "Enum"
 *         the sub-elements "enum" list the permitted values.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Qualifier#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.Qualifier#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getQualifier()
 * @model extendedMetaData="name='Qualifier' kind='elementOnly'"
 * @generated
 */
public interface Qualifier extends EObject {
    /**
     * Returns the value of the '<em><b>Enum</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *             Used only for qualifiers of data-type "Enum" and "EnumSet" to list the
     *             permitted enumeration values.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Enum</em>' attribute list.
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifier_Enum()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='enum' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getEnum();

    /**
     * Returns the value of the '<em><b>Data Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.directory.model.de.DataType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           The type of data that the qualifier attribute holds.
     *         
     * <!-- end-model-doc -->
     * @return the value of the '<em>Data Type</em>' attribute.
     * @see com.tibco.n2.directory.model.de.DataType
     * @see #isSetDataType()
     * @see #unsetDataType()
     * @see #setDataType(DataType)
     * @see com.tibco.n2.directory.model.de.DePackage#getQualifier_DataType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='data-type'"
     * @generated
     */
    DataType getDataType();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Qualifier#getDataType <em>Data Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Type</em>' attribute.
     * @see com.tibco.n2.directory.model.de.DataType
     * @see #isSetDataType()
     * @see #unsetDataType()
     * @see #getDataType()
     * @generated
     */
    void setDataType(DataType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.directory.model.de.Qualifier#getDataType <em>Data Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDataType()
     * @see #getDataType()
     * @see #setDataType(DataType)
     * @generated
     */
    void unsetDataType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.directory.model.de.Qualifier#getDataType <em>Data Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Data Type</em>' attribute is set.
     * @see #unsetDataType()
     * @see #getDataType()
     * @see #setDataType(DataType)
     * @generated
     */
    boolean isSetDataType();

} // Qualifier
