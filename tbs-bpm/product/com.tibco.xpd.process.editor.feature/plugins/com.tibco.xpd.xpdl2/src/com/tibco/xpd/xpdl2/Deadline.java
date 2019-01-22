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
 * A representation of the model object '<em><b>Deadline</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Deadline#getDeadlineDuration <em>Deadline Duration</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Deadline#getExceptionName <em>Exception Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Deadline#getExecution <em>Execution</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeadline()
 * @model extendedMetaData="name='Deadline_._type' kind='elementOnly'"
 * @generated
 */
public interface Deadline extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Deadline Duration</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deadline Duration</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deadline Duration</em>' containment reference.
     * @see #setDeadlineDuration(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeadline_DeadlineDuration()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DeadlineDuration' namespace='##targetNamespace'"
     * @generated
     */
    Expression getDeadlineDuration();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Deadline#getDeadlineDuration <em>Deadline Duration</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deadline Duration</em>' containment reference.
     * @see #getDeadlineDuration()
     * @generated
     */
    void setDeadlineDuration(Expression value);

    /**
     * Returns the value of the '<em><b>Exception Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This name should match that specified in Transition/Condition/Expression
     * <!-- end-model-doc -->
     * @return the value of the '<em>Exception Name</em>' containment reference.
     * @see #setExceptionName(ExceptionName)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeadline_ExceptionName()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExceptionName' namespace='##targetNamespace'"
     * @generated
     */
    ExceptionName getExceptionName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Deadline#getExceptionName <em>Exception Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Exception Name</em>' containment reference.
     * @see #getExceptionName()
     * @generated
     */
    void setExceptionName(ExceptionName value);

    /**
     * Returns the value of the '<em><b>Execution</b></em>' attribute.
     * The default value is <code>"ASYNCHR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ExecutionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Execution</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Execution</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see #isSetExecution()
     * @see #unsetExecution()
     * @see #setExecution(ExecutionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDeadline_Execution()
     * @model default="ASYNCHR" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Execution'"
     * @generated
     */
    ExecutionType getExecution();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Deadline#getExecution <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Execution</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see #isSetExecution()
     * @see #unsetExecution()
     * @see #getExecution()
     * @generated
     */
    void setExecution(ExecutionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Deadline#getExecution <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExecution()
     * @see #getExecution()
     * @see #setExecution(ExecutionType)
     * @generated
     */
    void unsetExecution();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Deadline#getExecution <em>Execution</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Execution</em>' attribute is set.
     * @see #unsetExecution()
     * @see #getExecution()
     * @see #setExecution(ExecutionType)
     * @generated
     */
    boolean isSetExecution();

} // Deadline
