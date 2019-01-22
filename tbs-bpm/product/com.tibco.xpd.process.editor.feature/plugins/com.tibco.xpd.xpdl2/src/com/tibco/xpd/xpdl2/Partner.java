/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partner</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Partner#getPartnerLinkId <em>Partner Link Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Partner#getRoleType <em>Role Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartner()
 * @model extendedMetaData="name='Partner_._type' kind='elementOnly'"
 * @generated
 */
public interface Partner extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Partner Link Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner Link Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner Link Id</em>' attribute.
     * @see #setPartnerLinkId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartner_PartnerLinkId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='PartnerLinkId'"
     * @generated
     */
    String getPartnerLinkId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Partner#getPartnerLinkId <em>Partner Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Partner Link Id</em>' attribute.
     * @see #getPartnerLinkId()
     * @generated
     */
    void setPartnerLinkId(String value);

    /**
     * Returns the value of the '<em><b>Role Type</b></em>' attribute.
     * The default value is <code>"MyRole"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.RoleType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Role Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Role Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.RoleType
     * @see #isSetRoleType()
     * @see #unsetRoleType()
     * @see #setRoleType(RoleType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartner_RoleType()
     * @model default="MyRole" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='RoleType'"
     * @generated
     */
    RoleType getRoleType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Partner#getRoleType <em>Role Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Role Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.RoleType
     * @see #isSetRoleType()
     * @see #unsetRoleType()
     * @see #getRoleType()
     * @generated
     */
    void setRoleType(RoleType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Partner#getRoleType <em>Role Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetRoleType()
     * @see #getRoleType()
     * @see #setRoleType(RoleType)
     * @generated
     */
    void unsetRoleType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Partner#getRoleType <em>Role Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Role Type</em>' attribute is set.
     * @see #unsetRoleType()
     * @see #getRoleType()
     * @see #setRoleType(RoleType)
     * @generated
     */
    boolean isSetRoleType();

} // Partner
