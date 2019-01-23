/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partner Link Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLinkType#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLinkType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLinkType()
 * @model extendedMetaData="name='PartnerLinkType_._type' kind='elementOnly' features-order='partnerLinkType'"
 * @generated
 */
public interface PartnerLinkType extends UniqueIdElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Role</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Role}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Role</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Role</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLinkType_Role()
     * @model containment="true" required="true" upper="2"
     *        extendedMetaData="kind='element' name='Role' namespace='##targetNamespace'"
     * @generated
     */
    EList<Role> getRole();

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLinkType_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PartnerLinkType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // PartnerLinkType
