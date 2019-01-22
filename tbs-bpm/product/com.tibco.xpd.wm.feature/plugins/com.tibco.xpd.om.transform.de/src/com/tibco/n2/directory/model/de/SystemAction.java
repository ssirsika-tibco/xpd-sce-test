/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         Identifies the Privileges required to perform the named system action.
 * 
 *         System Actions are grouped by the component to which the action is associated.
 *         For example, the action of re-allocating a work-item would be associated
 *         with the BRM component. Within the component grouping, each action must have a
 *         unique name.
 * 
 *         The collection of required Privilege associations define the Privileges
 *         a user must hold, or their equivalent, in order for the System Action to
 *         be authorised.
 * 
 *         Elements of this type may be nested within elements of type Group, Organization
 *         Org-Unit and Position. The nesting element defines the scope to which the
 *         system action applies. For example; a Position may have a SystemAction named
 *         "View Work-Item". Someone wanting to view the work-items for that Position
 *         would have to hold the Required Privilege identified in that SystemAction.
 * 
 *         A "global" set of SystemActions can be configured by placing them directly
 *         within the root element "model".
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.SystemAction#getReqPrivilege <em>Req Privilege</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.SystemAction#getComponent <em>Component</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.SystemAction#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getSystemAction()
 * @model extendedMetaData="name='SystemAction' kind='elementOnly'"
 * @generated
 */
public interface SystemAction extends EObject {
    /**
     * Returns the value of the '<em><b>Req Privilege</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.directory.model.de.PrivilegeHolding}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *             Identifies the Privilege(s) (and any qualifing value) required to perform
     *             this System Action.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Req Privilege</em>' containment reference list.
     * @see com.tibco.n2.directory.model.de.DePackage#getSystemAction_ReqPrivilege()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='req-privilege' namespace='##targetNamespace'"
     * @generated
     */
    EList<PrivilegeHolding> getReqPrivilege();

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
     * @see com.tibco.n2.directory.model.de.DePackage#getSystemAction_Component()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='component'"
     * @generated
     */
    String getComponent();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.SystemAction#getComponent <em>Component</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Component</em>' attribute.
     * @see #getComponent()
     * @generated
     */
    void setComponent(String value);

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
     * @see com.tibco.n2.directory.model.de.DePackage#getSystemAction_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.SystemAction#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // SystemAction
