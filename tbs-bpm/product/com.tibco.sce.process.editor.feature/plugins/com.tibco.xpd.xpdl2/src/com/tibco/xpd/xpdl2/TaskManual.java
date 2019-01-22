/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Manual</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskManual#getPerformers <em>Performers</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskManual()
 * @model extendedMetaData="name='TaskManual_._type' kind='elementOnly'"
 * @generated
 */
public interface TaskManual extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Performers</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Performer}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Performers</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Performers</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskManual_Performers()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Performer' namespace='##targetNamespace' wrap='Performers'"
     * @generated
     */
    EList<Performer> getPerformers();

} // TaskManual
