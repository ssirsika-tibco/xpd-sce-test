/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ExpressionType#getEnumBasedExpression <em>Enum Based Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ExpressionType#getScriptExpression <em>Script Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ExpressionType#getStructuredExpression <em>Structured Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ExpressionType#getDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getExpressionType()
 * @model extendedMetaData="name='Expresion_._type' kind='elementOnly'"
 * @generated
 */
public interface ExpressionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Enum Based Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum Based Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enum Based Expression</em>' containment reference.
     * @see #setEnumBasedExpression(EnumBasedExpressionType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExpressionType_EnumBasedExpression()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='EnumBasedExpression' namespace='##targetNamespace'"
     * @generated
     */
    EnumBasedExpressionType getEnumBasedExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExpressionType#getEnumBasedExpression <em>Enum Based Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enum Based Expression</em>' containment reference.
     * @see #getEnumBasedExpression()
     * @generated
     */
    void setEnumBasedExpression(EnumBasedExpressionType value);

    /**
     * Returns the value of the '<em><b>Script Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script Expression</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Script Expression</em>' attribute.
     * @see #setScriptExpression(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExpressionType_ScriptExpression()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='ScriptExpression' namespace='##targetNamespace'"
     * @generated
     */
    String getScriptExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExpressionType#getScriptExpression <em>Script Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Expression</em>' attribute.
     * @see #getScriptExpression()
     * @generated
     */
    void setScriptExpression(String value);

    /**
     * Returns the value of the '<em><b>Structured Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structured Expression</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Structured Expression</em>' containment reference.
     * @see #setStructuredExpression(EObject)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExpressionType_StructuredExpression()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StructuredExpression' namespace='##targetNamespace'"
     * @generated
     */
    EObject getStructuredExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExpressionType#getStructuredExpression <em>Structured Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Structured Expression</em>' containment reference.
     * @see #getStructuredExpression()
     * @generated
     */
    void setStructuredExpression(EObject value);

    /**
     * Returns the value of the '<em><b>Default</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default</em>' containment reference.
     * @see #setDefault(EObject)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExpressionType_Default()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Default' namespace='##targetNamespace'"
     * @generated
     */
    EObject getDefault();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExpressionType#getDefault <em>Default</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default</em>' containment reference.
     * @see #getDefault()
     * @generated
     */
    void setDefault(EObject value);

} // ExpressionType