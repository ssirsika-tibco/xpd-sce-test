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
 * A representation of the model object '<em><b>Assigments Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.AssigmentsContainer#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssigmentsContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AssigmentsContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Assignments</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assignments</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Assignments</em>' containment reference.
     * @see #setAssignments(Assignment)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssigmentsContainer_Assignments()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Assignment' namespace='##targetNamespace' wrap='Assignments'"
     * @generated
     */
    Assignment getAssignments();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.AssigmentsContainer#getAssignments <em>Assignments</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Assignments</em>' containment reference.
     * @see #getAssignments()
     * @generated
     */
    void setAssignments(Assignment value);

} // AssigmentsContainer
