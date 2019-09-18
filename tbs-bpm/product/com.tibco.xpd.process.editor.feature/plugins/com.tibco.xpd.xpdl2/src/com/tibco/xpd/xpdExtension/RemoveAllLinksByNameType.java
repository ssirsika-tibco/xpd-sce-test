/**
 * Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove All Links By Name Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RemoveAllLinksByNameType#getAssociationName <em>Association Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRemoveAllLinksByNameType()
 * @model extendedMetaData="name='RemoveAllLinksByName_._type' kind='empty'"
 * @generated
 */
public interface RemoveAllLinksByNameType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Association Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Association Name</em>' attribute.
     * @see #setAssociationName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRemoveAllLinksByNameType_AssociationName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='AssociationName'"
     * @generated
     */
    String getAssociationName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RemoveAllLinksByNameType#getAssociationName <em>Association Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Association Name</em>' attribute.
     * @see #getAssociationName()
     * @generated
     */
    void setAssociationName(String value);

} // RemoveAllLinksByNameType
