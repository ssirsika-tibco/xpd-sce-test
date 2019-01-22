/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Suspend Work Item Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.SuspendWorkItemType#getWorkItemID <em>Work Item ID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getSuspendWorkItemType()
 * @model extendedMetaData="name='suspendWorkItem_._type' kind='elementOnly'"
 * @generated
 */
public interface SuspendWorkItemType extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item ID</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item ID</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item ID</em>' containment reference.
     * @see #setWorkItemID(ObjectID)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSuspendWorkItemType_WorkItemID()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemID'"
     * @generated
     */
    ObjectID getWorkItemID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SuspendWorkItemType#getWorkItemID <em>Work Item ID</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item ID</em>' containment reference.
     * @see #getWorkItemID()
     * @generated
     */
    void setWorkItemID(ObjectID value);

    /**
     * Returns the value of the '<em><b>Force Suspend</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Force Suspend</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Force Suspend</em>' attribute.
     * @see #isSetForceSuspend()
     * @see #unsetForceSuspend()
     * @see #setForceSuspend(boolean)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSuspendWorkItemType_ForceSuspend()
     * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='forceSuspend'"
     * @generated
     */
    boolean isForceSuspend();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Force Suspend</em>' attribute.
     * @see #isSetForceSuspend()
     * @see #unsetForceSuspend()
     * @see #isForceSuspend()
     * @generated
     */
    void setForceSuspend(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetForceSuspend()
     * @see #isForceSuspend()
     * @see #setForceSuspend(boolean)
     * @generated
     */
    void unsetForceSuspend();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.SuspendWorkItemType#isForceSuspend <em>Force Suspend</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Force Suspend</em>' attribute is set.
     * @see #unsetForceSuspend()
     * @see #isForceSuspend()
     * @see #setForceSuspend(boolean)
     * @generated
     */
    boolean isSetForceSuspend();

} // SuspendWorkItemType
