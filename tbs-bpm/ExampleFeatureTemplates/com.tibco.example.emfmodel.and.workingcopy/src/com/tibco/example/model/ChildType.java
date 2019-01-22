/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Child Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.example.model.ChildType#getTestElement <em>Test Element</em>}</li>
 *   <li>{@link com.tibco.example.model.ChildType#getChildAttribute <em>Child Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.example.model.ModelPackage#getChildType()
 * @model extendedMetaData="name='ChildType' kind='elementOnly'"
 * @generated
 */
public interface ChildType extends EObject {
    /**
     * Returns the value of the '<em><b>Test Element</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Test Element</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Test Element</em>' attribute.
     * @see #setTestElement(String)
     * @see com.tibco.example.model.ModelPackage#getChildType_TestElement()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='TestElement' namespace='##targetNamespace'"
     * @generated
     */
    String getTestElement();

    /**
     * Sets the value of the '{@link com.tibco.example.model.ChildType#getTestElement <em>Test Element</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Test Element</em>' attribute.
     * @see #getTestElement()
     * @generated
     */
    void setTestElement(String value);

    /**
     * Returns the value of the '<em><b>Child Attribute</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Child Attribute</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Child Attribute</em>' attribute.
     * @see #setChildAttribute(String)
     * @see com.tibco.example.model.ModelPackage#getChildType_ChildAttribute()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ChildAttribute'"
     * @generated
     */
    String getChildAttribute();

    /**
     * Sets the value of the '{@link com.tibco.example.model.ChildType#getChildAttribute <em>Child Attribute</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Child Attribute</em>' attribute.
     * @see #getChildAttribute()
     * @generated
     */
    void setChildAttribute(String value);

} // ChildType
