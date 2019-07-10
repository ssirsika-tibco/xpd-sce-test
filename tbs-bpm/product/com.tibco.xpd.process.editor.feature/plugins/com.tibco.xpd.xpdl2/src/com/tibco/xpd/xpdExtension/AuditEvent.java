/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Audit Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AuditEvent#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AuditEvent#getInformation <em>Information</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAuditEvent()
 * @model extendedMetaData="name='AuditEvent_._type' kind='elementOnly'"
 * @generated
 */
public interface AuditEvent extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The default value is <code>"Initiated"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.AuditEventType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(AuditEventType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAuditEvent_Type()
     * @model default="Initiated" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    AuditEventType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AuditEvent#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AuditEventType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(AuditEventType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AuditEvent#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(AuditEventType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AuditEvent#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(AuditEventType)
     * @generated
     */
    boolean isSetType();

    /**
     * Returns the value of the '<em><b>Information</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Information</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Information</em>' containment reference.
     * @see #setInformation(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAuditEvent_Information()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Information' namespace='##targetNamespace'"
     * @generated
     */
    Expression getInformation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AuditEvent#getInformation <em>Information</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Information</em>' containment reference.
     * @see #getInformation()
     * @generated
     */
    void setInformation(Expression value);

} // AuditEvent