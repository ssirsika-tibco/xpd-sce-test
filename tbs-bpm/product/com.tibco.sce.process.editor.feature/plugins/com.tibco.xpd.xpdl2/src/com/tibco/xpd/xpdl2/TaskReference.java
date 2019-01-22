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
 * A representation of the model object '<em><b>Task Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskReference#getTaskRef <em>Task Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskReference()
 * @model extendedMetaData="name='TaskReference_._type' kind='elementOnly'"
 * @generated
 */
public interface TaskReference extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Task Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Ref</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Ref</em>' attribute.
     * @see #setTaskRef(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskReference_TaskRef()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='TaskRef'"
     * @generated
     */
    String getTaskRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskReference#getTaskRef <em>Task Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Ref</em>' attribute.
     * @see #getTaskRef()
     * @generated
     */
    void setTaskRef(String value);

} // TaskReference