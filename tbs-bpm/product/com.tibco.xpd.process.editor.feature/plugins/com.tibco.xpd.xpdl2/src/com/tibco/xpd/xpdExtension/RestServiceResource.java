/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rest Service Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceResource#getSecurityPolicy <em>Security Policy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceName <em>Resource Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceResource#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResource()
 * @model extendedMetaData="name='RestServiceResource' kind='empty'"
 * @generated
 */
public interface RestServiceResource extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Security Policy</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.RestServiceResourceSecurity}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Security Policy</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Security Policy</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResource_SecurityPolicy()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SecurityPolicy' namespace='##targetNamespace'"
     * @generated
     */
    EList<RestServiceResourceSecurity> getSecurityPolicy();

    /**
     * Returns the value of the '<em><b>Resource Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Name</em>' attribute.
     * @see #setResourceName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResource_ResourceName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='resourceName'"
     * @generated
     */
    String getResourceName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceName <em>Resource Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Name</em>' attribute.
     * @see #getResourceName()
     * @generated
     */
    void setResourceName(String value);

    /**
     * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Type</em>' attribute.
     * @see #setResourceType(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResource_ResourceType()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='resourceType'"
     * @generated
     */
    String getResourceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getResourceType <em>Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Type</em>' attribute.
     * @see #getResourceType()
     * @generated
     */
    void setResourceType(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceResource_Description()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceResource#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // RestServiceResource
