/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Work Item Priority</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.SetWorkItemPriority#getWorkItemIDandPriority <em>Work Item IDand Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getSetWorkItemPriority()
 * @model extendedMetaData="name='setWorkItemPriority' kind='elementOnly'"
 * @generated
 */
public interface SetWorkItemPriority extends EObject {
    /**
     * Returns the value of the '<em><b>Work Item IDand Priority</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.brm.api.WorkItemIDandPriorityType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item IDand Priority</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item IDand Priority</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getSetWorkItemPriority_WorkItemIDandPriority()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='workItemIDandPriority'"
     * @generated
     */
    EList<WorkItemIDandPriorityType> getWorkItemIDandPriority();

} // SetWorkItemPriority
