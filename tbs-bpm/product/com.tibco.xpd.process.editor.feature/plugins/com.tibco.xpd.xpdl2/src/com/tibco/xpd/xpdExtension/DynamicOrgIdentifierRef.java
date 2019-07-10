/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Org Identifier Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getIdentifierName <em>Identifier Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getDynamicOrgId <em>Dynamic Org Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getOrgModelPath <em>Org Model Path</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrgIdentifierRef()
 * @model extendedMetaData="name='DynamicOrgIdentifierRef' kind='element'"
 * @generated
 */
public interface DynamicOrgIdentifierRef extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Identifier Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier Name</em>' attribute.
     * @see #setIdentifierName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrgIdentifierRef_IdentifierName()
     * @model extendedMetaData="kind='attribute' name='IdentifierName'"
     * @generated
     */
    String getIdentifierName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getIdentifierName <em>Identifier Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier Name</em>' attribute.
     * @see #getIdentifierName()
     * @generated
     */
    void setIdentifierName(String value);

    /**
     * Returns the value of the '<em><b>Dynamic Org Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dynamic Org Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Dynamic Org Id</em>' attribute.
     * @see #setDynamicOrgId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrgIdentifierRef_DynamicOrgId()
     * @model extendedMetaData="kind='attribute' name='DynamicOrgId'"
     * @generated
     */
    String getDynamicOrgId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getDynamicOrgId <em>Dynamic Org Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dynamic Org Id</em>' attribute.
     * @see #getDynamicOrgId()
     * @generated
     */
    void setDynamicOrgId(String value);

    /**
     * Returns the value of the '<em><b>Org Model Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Org Model Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Org Model Path</em>' attribute.
     * @see #setOrgModelPath(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getDynamicOrgIdentifierRef_OrgModelPath()
     * @model extendedMetaData="kind='attribute' name='OrgModelPath'"
     * @generated
     */
    String getOrgModelPath();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef#getOrgModelPath <em>Org Model Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Org Model Path</em>' attribute.
     * @see #getOrgModelPath()
     * @generated
     */
    void setOrgModelPath(String value);

} // DynamicOrgIdentifierRef
