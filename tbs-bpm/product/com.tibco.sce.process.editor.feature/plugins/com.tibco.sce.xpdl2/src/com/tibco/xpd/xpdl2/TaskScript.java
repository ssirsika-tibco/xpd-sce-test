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
 * A representation of the model object '<em><b>Task Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TaskScript#getScript <em>Script</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskScript()
 * @model extendedMetaData="name='TaskScript_._type' kind='elementOnly'"
 * @generated
 */
public interface TaskScript extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPMN
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script</em>' containment reference.
     * @see #setScript(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTaskScript_Script()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Script' namespace='##targetNamespace'"
     * @generated
     */
    Expression getScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TaskScript#getScript <em>Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script</em>' containment reference.
     * @see #getScript()
     * @generated
     */
    void setScript(Expression value);

} // TaskScript
