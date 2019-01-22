/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Main Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.example.model.MainElementType#getChildElements <em>Child Elements</em>}</li>
 *   <li>{@link com.tibco.example.model.MainElementType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.example.model.MainElementType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.example.model.ModelPackage#getMainElementType()
 * @model extendedMetaData="name='MainElementType' kind='elementOnly'"
 * @generated
 */
public interface MainElementType extends EObject {
    /**
     * Returns the value of the '<em><b>Child Elements</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.example.model.ChildType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Child Elements</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Child Elements</em>' containment reference list.
     * @see com.tibco.example.model.ModelPackage#getMainElementType_ChildElements()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ChildElements' namespace='##targetNamespace'"
     * @generated
     */
    EList<ChildType> getChildElements();

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.example.model.ModelPackage#getMainElementType_Id()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Id'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.example.model.MainElementType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.example.model.ModelPackage#getMainElementType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.example.model.MainElementType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // MainElementType
