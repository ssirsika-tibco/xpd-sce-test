/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partner Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLink#getMyRole <em>My Role</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerRole <em>Partner Role</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerLinkTypeId <em>Partner Link Type Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PartnerLink#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLink()
 * @model extendedMetaData="name='PartnerLink_._type' kind='elementOnly' features-order='myRole partnerRole'"
 * @generated
 */
public interface PartnerLink extends UniqueIdElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>My Role</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>My Role</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>My Role</em>' containment reference.
     * @see #setMyRole(MyRole)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLink_MyRole()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='MyRole' namespace='##targetNamespace'"
     * @generated
     */
    MyRole getMyRole();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PartnerLink#getMyRole <em>My Role</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>My Role</em>' containment reference.
     * @see #getMyRole()
     * @generated
     */
    void setMyRole(MyRole value);

    /**
     * Returns the value of the '<em><b>Partner Role</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner Role</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner Role</em>' containment reference.
     * @see #setPartnerRole(PartnerRole)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLink_PartnerRole()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PartnerRole' namespace='##targetNamespace'"
     * @generated
     */
    PartnerRole getPartnerRole();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerRole <em>Partner Role</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Partner Role</em>' containment reference.
     * @see #getPartnerRole()
     * @generated
     */
    void setPartnerRole(PartnerRole value);

    /**
     * Returns the value of the '<em><b>Partner Link Type Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner Link Type Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner Link Type Id</em>' attribute.
     * @see #setPartnerLinkTypeId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLink_PartnerLinkTypeId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='PartnerLinkTypeId'"
     * @generated
     */
    String getPartnerLinkTypeId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PartnerLink#getPartnerLinkTypeId <em>Partner Link Type Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Partner Link Type Id</em>' attribute.
     * @see #getPartnerLinkTypeId()
     * @generated
     */
    void setPartnerLinkTypeId(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPartnerLink_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PartnerLink#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // PartnerLink
