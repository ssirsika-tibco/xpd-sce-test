/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Assignment#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Assignment#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Assignment#getAssignTime <em>Assign Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssignment()
 * @model extendedMetaData="name='Assignment_._type' kind='elementOnly'"
 * @generated
 */
public interface Assignment extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Target</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  lvalue expression of the assignment, in XPDL may be the name of a DataField, in BPMN name of a Property, in XPATH a reference
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target</em>' containment reference.
     * @see #setTarget(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssignment_Target()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Target' namespace='##targetNamespace'"
     * @generated
     */
    Expression getTarget();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Assignment#getTarget <em>Target</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' containment reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(Expression value);

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * rvalue expression of the assignment
     * <!-- end-model-doc -->
     * @return the value of the '<em>Expression</em>' containment reference.
     * @see #setExpression(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssignment_Expression()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
     * @generated
     */
    Expression getExpression();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Assignment#getExpression <em>Expression</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' containment reference.
     * @see #getExpression()
     * @generated
     */
    void setExpression(Expression value);

    /**
     * Returns the value of the '<em><b>Assign Time</b></em>' attribute.
     * The default value is <code>"Start"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.AssignTimeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Assign Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Assign Time</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @see #isSetAssignTime()
     * @see #unsetAssignTime()
     * @see #setAssignTime(AssignTimeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssignment_AssignTime()
     * @model default="Start" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AssignTime'"
     * @generated
     */
    AssignTimeType getAssignTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Assignment#getAssignTime <em>Assign Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Assign Time</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AssignTimeType
     * @see #isSetAssignTime()
     * @see #unsetAssignTime()
     * @see #getAssignTime()
     * @generated
     */
    void setAssignTime(AssignTimeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Assignment#getAssignTime <em>Assign Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAssignTime()
     * @see #getAssignTime()
     * @see #setAssignTime(AssignTimeType)
     * @generated
     */
    void unsetAssignTime();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Assignment#getAssignTime <em>Assign Time</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Assign Time</em>' attribute is set.
     * @see #unsetAssignTime()
     * @see #getAssignTime()
     * @see #setAssignTime(AssignTimeType)
     * @generated
     */
    boolean isSetAssignTime();

} // Assignment
