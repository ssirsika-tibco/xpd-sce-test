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
 * A representation of the model object '<em><b>Form Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getFormIdentifier <em>Form Identifier</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getRelativePath <em>Relative Path</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getBasePath <em>Base Path</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.FormType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType()
 * @model extendedMetaData="name='formType' kind='elementOnly'"
 * @generated
 */
public interface FormType extends EObject {
    /**
     * Returns the value of the '<em><b>Form Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This path is relative to the deployable module toor directory
     * <!-- end-model-doc -->
     * @return the value of the '<em>Form Identifier</em>' attribute.
     * @see #setFormIdentifier(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_FormIdentifier()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='formIdentifier' namespace='##targetNamespace'"
     * @generated
     */
    String getFormIdentifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getFormIdentifier <em>Form Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form Identifier</em>' attribute.
     * @see #getFormIdentifier()
     * @generated
     */
    void setFormIdentifier(String value);

    /**
     * Returns the value of the '<em><b>Relative Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This path is relative to the base context path
     * <!-- end-model-doc -->
     * @return the value of the '<em>Relative Path</em>' attribute.
     * @see #setRelativePath(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_RelativePath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='relative-path' namespace='##targetNamespace'"
     * @generated
     */
    String getRelativePath();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getRelativePath <em>Relative Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Relative Path</em>' attribute.
     * @see #getRelativePath()
     * @generated
     */
    void setRelativePath(String value);

    /**
     * Returns the value of the '<em><b>Base Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The base context of the form url
     * <!-- end-model-doc -->
     * @return the value of the '<em>Base Path</em>' attribute.
     * @see #setBasePath(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_BasePath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='base-path' namespace='##targetNamespace'"
     * @generated
     */
    String getBasePath();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getBasePath <em>Base Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Base Path</em>' attribute.
     * @see #getBasePath()
     * @generated
     */
    void setBasePath(String value);

    /**
     * Returns the value of the '<em><b>Guid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Guid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Guid</em>' attribute.
     * @see #setGuid(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_Guid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='guid'"
     * @generated
     */
    String getGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getGuid <em>Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Guid</em>' attribute.
     * @see #getGuid()
     * @generated
     */
    void setGuid(String value);

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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getFormType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.FormType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // FormType
