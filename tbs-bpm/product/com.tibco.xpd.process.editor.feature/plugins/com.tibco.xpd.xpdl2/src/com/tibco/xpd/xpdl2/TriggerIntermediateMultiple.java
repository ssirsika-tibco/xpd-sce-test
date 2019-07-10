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
 * A representation of the model object '<em><b>Trigger Intermediate Multiple</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple()
 * @model extendedMetaData="name='TriggerIntermediateMultiple_._type' kind='elementOnly'"
 * @generated
 */
public interface TriggerIntermediateMultiple extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Result Message</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #setTriggerResultMessage(TriggerResultMessage)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #getTriggerResultMessage()
     * @generated
     */
    void setTriggerResultMessage(TriggerResultMessage value);

    /**
     * Returns the value of the '<em><b>Trigger Timer</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Timer</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Timer</em>' containment reference.
     * @see #setTriggerTimer(TriggerTimer)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_TriggerTimer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerTimer' namespace='##targetNamespace'"
     * @generated
     */
    TriggerTimer getTriggerTimer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerTimer <em>Trigger Timer</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Timer</em>' containment reference.
     * @see #getTriggerTimer()
     * @generated
     */
    void setTriggerTimer(TriggerTimer value);

    /**
     * Returns the value of the '<em><b>Result Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Result Error</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Result Error</em>' containment reference.
     * @see #setResultError(ResultError)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_ResultError()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultError' namespace='##targetNamespace'"
     * @generated
     */
    ResultError getResultError();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getResultError <em>Result Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Result Error</em>' containment reference.
     * @see #getResultError()
     * @generated
     */
    void setResultError(ResultError value);

    /**
     * Returns the value of the '<em><b>Trigger Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Result Compensation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Result Compensation</em>' containment reference.
     * @see #setTriggerResultCompensation(TriggerResultCompensation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_TriggerResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultCompensation getTriggerResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultCompensation <em>Trigger Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Compensation</em>' containment reference.
     * @see #getTriggerResultCompensation()
     * @generated
     */
    void setTriggerResultCompensation(TriggerResultCompensation value);

    /**
     * Returns the value of the '<em><b>Deprecated Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Result Compensation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Result Compensation</em>' containment reference.
     * @see #setDeprecatedResultCompensation(DeprecatedResultCompensation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_DeprecatedResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedResultCompensation getDeprecatedResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Result Compensation</em>' containment reference.
     * @see #getDeprecatedResultCompensation()
     * @generated
     */
    void setDeprecatedResultCompensation(DeprecatedResultCompensation value);

    /**
     * Returns the value of the '<em><b>Trigger Conditional</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Conditional</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Conditional</em>' containment reference.
     * @see #setTriggerConditional(TriggerConditional)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_TriggerConditional()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerConditional' namespace='##targetNamespace'"
     * @generated
     */
    TriggerConditional getTriggerConditional();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerConditional <em>Trigger Conditional</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Conditional</em>' containment reference.
     * @see #getTriggerConditional()
     * @generated
     */
    void setTriggerConditional(TriggerConditional value);

    /**
     * Returns the value of the '<em><b>Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Result Link</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Result Link</em>' containment reference.
     * @see #setTriggerResultLink(TriggerResultLink)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerIntermediateMultiple_TriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerIntermediateMultiple#getTriggerResultLink <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Link</em>' containment reference.
     * @see #getTriggerResultLink()
     * @generated
     */
    void setTriggerResultLink(TriggerResultLink value);

} // TriggerIntermediateMultiple
