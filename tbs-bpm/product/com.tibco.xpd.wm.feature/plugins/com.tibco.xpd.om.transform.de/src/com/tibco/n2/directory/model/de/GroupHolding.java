/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group Holding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         A sub-element of Resource, this element records the assignment of a resource to a Group.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.GroupHolding#getGroup <em>Group</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getGroupHolding()
 * @model extendedMetaData="name='GroupHolding' kind='empty'"
 * @generated
 */
public interface GroupHolding extends EObject {
    /**
     * Returns the value of the '<em><b>Group</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' reference.
     * @see #setGroup(Group)
     * @see com.tibco.n2.directory.model.de.DePackage#getGroupHolding_Group()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='group'"
     * @generated
     */
    Group getGroup();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.GroupHolding#getGroup <em>Group</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group</em>' reference.
     * @see #getGroup()
     * @generated
     */
    void setGroup(Group value);

} // GroupHolding
