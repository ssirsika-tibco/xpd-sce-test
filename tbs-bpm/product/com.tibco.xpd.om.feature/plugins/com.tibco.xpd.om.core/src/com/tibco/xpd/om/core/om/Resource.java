/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Resource#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.Resource#getDn <em>Dn</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getResource()
 * @model
 * @generated
 */
public interface Resource extends OrgTypedElement {
    /**
     * Returns the value of the '<em><b>Resource Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Type</em>' reference.
     * @see #setResourceType(ResourceType)
     * @see com.tibco.xpd.om.core.om.OMPackage#getResource_ResourceType()
     * @model
     * @generated
     */
    ResourceType getResourceType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Resource#getResourceType <em>Resource Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Type</em>' reference.
     * @see #getResourceType()
     * @generated
     */
    void setResourceType(ResourceType value);

    /**
     * Returns the value of the '<em><b>Dn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dn</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dn</em>' attribute.
     * @see #setDn(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getResource_Dn()
     * @model
     * @generated
     */
    String getDn();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.Resource#getDn <em>Dn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dn</em>' attribute.
     * @see #getDn()
     * @generated
     */
    void setDn(String value);

} // Resource
