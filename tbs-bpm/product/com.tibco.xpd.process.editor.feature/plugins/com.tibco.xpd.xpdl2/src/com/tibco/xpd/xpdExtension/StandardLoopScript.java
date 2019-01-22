/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Standard Loop Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.StandardLoopScript#getLoopExpression <em>Loop Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getStandardLoopScript()
 * @model extendedMetaData="name='StandardLoopScript' kind='elementOnly'"
 * @generated
 */
public interface StandardLoopScript extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Loop Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Expression</em>' containment reference.
     * @see #setLoopExpression(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getStandardLoopScript_LoopExpression()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LoopExpression' namespace='##targetNamespace'"
     * @generated
     */
    Expression getLoopExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.StandardLoopScript#getLoopExpression <em>Loop Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Expression</em>' containment reference.
     * @see #getLoopExpression()
     * @generated
     */
    void setLoopExpression(Expression value);

} // StandardLoopScript