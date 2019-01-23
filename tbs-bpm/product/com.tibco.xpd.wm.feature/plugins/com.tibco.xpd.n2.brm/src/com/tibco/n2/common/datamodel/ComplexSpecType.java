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
 * A representation of the model object '<em><b>Complex Spec Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The Global Object Business Reference ID
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.ComplexSpecType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.ComplexSpecType#getClassName <em>Class Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.ComplexSpecType#getGoRefId <em>Go Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.common.datamodel.DatamodelPackage#getComplexSpecType()
 * @model extendedMetaData="name='complexSpec_._type' kind='elementOnly'"
 * @generated
 */
public interface ComplexSpecType extends EObject {
    /**
     * Returns the value of the '<em><b>Value</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The value(s) of the complex object. Note that:
     * 
     * - This value is of xs:anyType since it contains the eintire complex object as XML.
     * 
     * - The XML specified for the value can be either element-based or type-based. See "Handling a Work Item That Contains Business Data > Valid Format for ComplexSpec Business Data" in the BPM Developer's Guide for more information.
     * 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Value</em>' containment reference list.
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getComplexSpecType_Value()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='value'"
     * @generated
     */
    EList<EObject> getValue();

    /**
     * Returns the value of the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The fully qualified name of the complex class from the Business Object Model (BOM).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Class Name</em>' attribute.
     * @see #setClassName(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getComplexSpecType_ClassName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='className'"
     * @generated
     */
    String getClassName();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.ComplexSpecType#getClassName <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Class Name</em>' attribute.
     * @see #getClassName()
     * @generated
     */
    void setClassName(String value);

    /**
     * Returns the value of the '<em><b>Go Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The Global Object Reference ID for BDS data.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Go Ref Id</em>' attribute.
     * @see #setGoRefId(String)
     * @see com.tibco.n2.common.datamodel.DatamodelPackage#getComplexSpecType_GoRefId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='goRefId'"
     * @generated
     */
    String getGoRefId();

    /**
     * Sets the value of the '{@link com.tibco.n2.common.datamodel.ComplexSpecType#getGoRefId <em>Go Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Go Ref Id</em>' attribute.
     * @see #getGoRefId()
     * @generated
     */
    void setGoRefId(String value);

} // ComplexSpecType
