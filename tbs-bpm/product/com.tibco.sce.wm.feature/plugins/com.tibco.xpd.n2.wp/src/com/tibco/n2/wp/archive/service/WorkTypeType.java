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
 * A representation of the model object '<em><b>Work Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getForm <em>Form</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlow <em>Page Flow</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtensionConfig <em>Extension Config</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlowRef <em>Page Flow Ref</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getGuid <em>Guid</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.WorkTypeType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType()
 * @model extendedMetaData="name='work-type_._type' kind='elementOnly'"
 * @generated
 */
public interface WorkTypeType extends EObject {
    /**
     * Returns the value of the '<em><b>Extended Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 																It is a
     * 																provision
     * 																to add
     * 																custom
     * 																properties
     * 																to the
     * 																work
     * 																model
     * 																type
     * 																which
     * 																can be
     * 																interpreted
     * 																by the
     * 																Work
     * 																Presenation
     * 																Deployment
     * 																Analyser
     * 															
     * <!-- end-model-doc -->
     * @return the value of the '<em>Extended Properties</em>' containment reference.
     * @see #isSetExtendedProperties()
     * @see #unsetExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_ExtendedProperties()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='extended-properties' namespace='##targetNamespace'"
     * @generated
     */
    ExtendedPropertiesType getExtendedProperties();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extended Properties</em>' containment reference.
     * @see #isSetExtendedProperties()
     * @see #unsetExtendedProperties()
     * @see #getExtendedProperties()
     * @generated
     */
    void setExtendedProperties(ExtendedPropertiesType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExtendedProperties()
     * @see #getExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @generated
     */
    void unsetExtendedProperties();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtendedProperties <em>Extended Properties</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Extended Properties</em>' containment reference is set.
     * @see #unsetExtendedProperties()
     * @see #getExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @generated
     */
    boolean isSetExtendedProperties();

    /**
     * Returns the value of the '<em><b>Form</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 																	Form
     * 																	indicates
     * 																	the
     * 																	presentation
     * 																	resource
     * 																	that
     * 																	needs
     * 																	to
     * 																	be
     * 																	displayed
     * 																	for
     * 																	this
     * 																	particular
     * 																	work
     * 																	type
     * 																	request
     * 																
     * <!-- end-model-doc -->
     * @return the value of the '<em>Form</em>' containment reference.
     * @see #setForm(FormType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_Form()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='form' namespace='##targetNamespace'"
     * @generated
     */
    FormType getForm();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getForm <em>Form</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form</em>' containment reference.
     * @see #getForm()
     * @generated
     */
    void setForm(FormType value);

    /**
     * Returns the value of the '<em><b>Page Flow</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Flow</em>' containment reference.
     * @see #setPageFlow(PageFlowType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_PageFlow()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='page-flow' namespace='##targetNamespace'"
     * @generated
     */
    PageFlowType getPageFlow();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlow <em>Page Flow</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page Flow</em>' containment reference.
     * @see #getPageFlow()
     * @generated
     */
    void setPageFlow(PageFlowType value);

    /**
     * Returns the value of the '<em><b>Extension Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extension Config</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extension Config</em>' containment reference.
     * @see #setExtensionConfig(ChannelExtentionType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_ExtensionConfig()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='extension-config' namespace='##targetNamespace'"
     * @generated
     */
    ChannelExtentionType getExtensionConfig();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getExtensionConfig <em>Extension Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extension Config</em>' containment reference.
     * @see #getExtensionConfig()
     * @generated
     */
    void setExtensionConfig(ChannelExtentionType value);

    /**
     * Returns the value of the '<em><b>Page Flow Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page Flow Ref</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Flow Ref</em>' containment reference.
     * @see #setPageFlowRef(PageFlowRefType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_PageFlowRef()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='page-flow-ref' namespace='##targetNamespace'"
     * @generated
     */
    PageFlowRefType getPageFlowRef();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getPageFlowRef <em>Page Flow Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page Flow Ref</em>' containment reference.
     * @see #getPageFlowRef()
     * @generated
     */
    void setPageFlowRef(PageFlowRefType value);

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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_Guid()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='guid'"
     * @generated
     */
    String getGuid();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getGuid <em>Guid</em>}' attribute.
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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getName <em>Name</em>}' attribute.
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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getWorkTypeType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.WorkTypeType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // WorkTypeType
