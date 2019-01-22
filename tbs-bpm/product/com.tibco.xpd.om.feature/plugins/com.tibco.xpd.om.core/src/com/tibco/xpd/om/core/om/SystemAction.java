/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemAction#getActionId <em>Action Id</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.SystemAction#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getSystemAction()
 * @model
 * @generated
 */
public interface SystemAction extends AssociableWithPrivileges, ModelElement {
    /**
     * Returns the value of the '<em><b>Action Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Action Id</em>' attribute.
     * @see #setActionId(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemAction_ActionId()
     * @model
     * @generated
     */
    String getActionId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemAction#getActionId <em>Action Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Action Id</em>' attribute.
     * @see #getActionId()
     * @generated
     */
    void setActionId(String value);

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
     * @see com.tibco.xpd.om.core.om.OMPackage#getSystemAction_Component()
     * @model
     * @generated
     */
    String getComponent();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.SystemAction#getComponent <em>Component</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Component</em>' attribute.
     * @see #getComponent()
     * @generated
     */
    void setComponent(String value);

} // SystemAction
