/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ws Security Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getGovernanceApplicationName <em>Governance Application Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSecurityPolicy()
 * @model extendedMetaData="name='WsSecurityPolicy' kind='empty'"
 * @generated
 */
public interface WsSecurityPolicy extends ExtendedAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Governance Application Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Governance Application Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Governance Application Name</em>' attribute.
     * @see #setGovernanceApplicationName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSecurityPolicy_GovernanceApplicationName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='GovernanceApplicationName'"
     * @generated
     */
    String getGovernanceApplicationName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getGovernanceApplicationName <em>Governance Application Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Governance Application Name</em>' attribute.
     * @see #getGovernanceApplicationName()
     * @generated
     */
    void setGovernanceApplicationName(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SecurityPolicy}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(SecurityPolicy)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsSecurityPolicy_Type()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    SecurityPolicy getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.SecurityPolicy
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(SecurityPolicy value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(SecurityPolicy)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsSecurityPolicy#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(SecurityPolicy)
     * @generated
     */
    boolean isSetType();

} // WsSecurityPolicy
