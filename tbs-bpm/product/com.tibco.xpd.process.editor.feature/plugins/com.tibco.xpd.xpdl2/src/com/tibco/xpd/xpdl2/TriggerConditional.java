/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Conditional</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerConditional#getConditionName <em>Condition Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerConditional#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerConditional()
 * @model extendedMetaData="name='TriggerConditional_._type' kind='elementOnly'"
 * @generated
 */
public interface TriggerConditional extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Condition Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This is the nameof a Rule element.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Condition Name</em>' attribute.
     * @see #setConditionName(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerConditional_ConditionName()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='ConditionName'"
     * @generated
     */
    String getConditionName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerConditional#getConditionName <em>Condition Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Condition Name</em>' attribute.
     * @see #getConditionName()
     * @generated
     */
    void setConditionName(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerConditional_Expression()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerConditional#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

} // TriggerConditional
