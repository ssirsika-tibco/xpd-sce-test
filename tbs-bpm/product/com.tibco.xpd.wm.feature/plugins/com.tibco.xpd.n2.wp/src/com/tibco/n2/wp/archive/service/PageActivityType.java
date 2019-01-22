/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Activity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageActivityType#getPageReference <em>Page Reference</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageActivityType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageActivityType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getPageActivityType()
 * @model extendedMetaData="name='page-activity_._type' kind='elementOnly'"
 * @generated
 */
public interface PageActivityType extends EObject {
    /**
     * Returns the value of the '<em><b>Page Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Reference</em>' containment reference.
     * @see #setPageReference(FormType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageActivityType_PageReference()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='page-reference' namespace='##targetNamespace'"
     * @generated
     */
    FormType getPageReference();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageActivityType#getPageReference <em>Page Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page Reference</em>' containment reference.
     * @see #getPageReference()
     * @generated
     */
    void setPageReference(FormType value);

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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageActivityType_Id()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageActivityType#getId <em>Id</em>}' attribute.
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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageActivityType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageActivityType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // PageActivityType
