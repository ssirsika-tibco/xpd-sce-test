/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Action Identifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getActionName <em>Action Name</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getComponent <em>Component</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getSystemActionIdentifier()
 * @model
 * @generated
 */
public interface SystemActionIdentifier extends ModelElement {
    /**
     * Returns the value of the '<em><b>Action Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Action Name</em>' attribute.
     * @see #setActionName(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemActionIdentifier_ActionName()
     * @model
     * @generated
     */
    String getActionName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getActionName <em>Action Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Action Name</em>' attribute.
     * @see #getActionName()
     * @generated
     */
    void setActionName(String value);

    /**
     * Returns the value of the '<em><b>Component</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Component</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Component</em>' attribute.
     * @see #setComponent(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemActionIdentifier_Component()
     * @model
     * @generated
     */
    String getComponent();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getComponent <em>Component</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Component</em>' attribute.
     * @see #getComponent()
     * @generated
     */
    void setComponent(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemActionIdentifier_Label()
     * @model
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemActionIdentifier_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemActionIdentifier#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // SystemActionIdentifier
