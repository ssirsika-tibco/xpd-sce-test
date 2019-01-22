/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page Flow Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This is just a place holder to accomadate any future requirements of page flow necessary for a particular type work type.This may not have much importance in the current deployment scenario
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getPageActivity <em>Page Activity</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleName <em>Module Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleVersion <em>Module Version</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.PageFlowType#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType()
 * @model extendedMetaData="name='pageFlowType' kind='elementOnly'"
 * @generated
 */
public interface PageFlowType extends EObject {
    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_Group()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='group:0'"
     * @generated
     */
    FeatureMap getGroup();

    /**
     * Returns the value of the '<em><b>Page Activity</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.PageActivityType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page Activity</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Activity</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_PageActivity()
     * @model containment="true" required="true" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='page-activity' namespace='##targetNamespace' group='#group:0'"
     * @generated
     */
    EList<PageActivityType> getPageActivity();

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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_Id()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='id'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Module Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Module Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Module Name</em>' attribute.
     * @see #setModuleName(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_ModuleName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='moduleName'"
     * @generated
     */
    String getModuleName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleName <em>Module Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Module Name</em>' attribute.
     * @see #getModuleName()
     * @generated
     */
    void setModuleName(String value);

    /**
     * Returns the value of the '<em><b>Module Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Module Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Module Version</em>' attribute.
     * @see #setModuleVersion(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_ModuleVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='moduleVersion'"
     * @generated
     */
    String getModuleVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowType#getModuleVersion <em>Module Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Module Version</em>' attribute.
     * @see #getModuleVersion()
     * @generated
     */
    void setModuleVersion(String value);

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
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Url</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Url</em>' attribute.
     * @see #setUrl(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getPageFlowType_Url()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='url'"
     * @generated
     */
    String getUrl();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.PageFlowType#getUrl <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Url</em>' attribute.
     * @see #getUrl()
     * @generated
     */
    void setUrl(String value);

} // PageFlowType
