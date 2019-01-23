/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Organization Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getSourcePath <em>Source Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getDynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrganizationMapping()
 * @model extendedMetaData="name='DynamicOrganizationMapping' kind='element'"
 * @generated
 */
public interface DynamicOrganizationMapping extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Source Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source Path</em>' attribute.
     * @see #setSourcePath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrganizationMapping_SourcePath()
     * @model extendedMetaData="kind='element' name='SourcePath' namespace='##targetNamespace'"
     * @generated
     */
    String getSourcePath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getSourcePath <em>Source Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Path</em>' attribute.
     * @see #getSourcePath()
     * @generated
     */
    void setSourcePath(String value);

    /**
     * Returns the value of the '<em><b>Dynamic Org Identifier Ref</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Org Identifier Ref</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic Org Identifier Ref</em>' containment reference.
     * @see #setDynamicOrgIdentifierRef(DynamicOrgIdentifierRef)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrganizationMapping_DynamicOrgIdentifierRef()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DynamicOrgIdentifierRef' namespace='##targetNamespace'"
     * @generated
     */
    DynamicOrgIdentifierRef getDynamicOrgIdentifierRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DynamicOrganizationMapping#getDynamicOrgIdentifierRef <em>Dynamic Org Identifier Ref</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dynamic Org Identifier Ref</em>' containment reference.
     * @see #getDynamicOrgIdentifierRef()
     * @generated
     */
    void setDynamicOrgIdentifierRef(DynamicOrgIdentifierRef value);

} // DynamicOrganizationMapping
