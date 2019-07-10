/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Rule#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRule()
 * @model extendedMetaData="name='Rule_._type' kind='elementOnly' features-order='expression'"
 * @generated
 */
public interface Rule extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression</em>' containment reference.
     * @see #setExpression(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRule_Expression()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Rule#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

} // Rule
