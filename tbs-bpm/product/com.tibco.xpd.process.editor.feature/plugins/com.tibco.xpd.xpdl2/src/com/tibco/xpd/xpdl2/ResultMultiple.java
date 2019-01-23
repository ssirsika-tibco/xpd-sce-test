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
 * A representation of the model object '<em><b>Result Multiple</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultCompensation <em>Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultError <em>Result Error</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultMultiple()
 * @model extendedMetaData="name='ResultMultiple_._type' kind='elementOnly'"
 * @generated
 */
public interface ResultMultiple extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultMultiple_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #getTriggerResultMessage()
     * @generated
     */
    void setTriggerResultMessage(TriggerResultMessage value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultMultiple_TriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResultMultiple#getTriggerResultLink <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Link</em>' containment reference.
     * @see #getTriggerResultLink()
     * @generated
     */
    void setTriggerResultLink(TriggerResultLink value);

    /**
     * Returns the value of the '<em><b>Result Compensation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Result Compensation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Result Compensation</em>' containment reference.
     * @see #setResultCompensation(TriggerResultCompensation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultMultiple_ResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultCompensation getResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultCompensation <em>Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Result Compensation</em>' containment reference.
     * @see #getResultCompensation()
     * @generated
     */
    void setResultCompensation(TriggerResultCompensation value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResultMultiple_ResultError()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultError' namespace='##targetNamespace'"
     * @generated
     */
    ResultError getResultError();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResultMultiple#getResultError <em>Result Error</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Result Error</em>' containment reference.
     * @see #getResultError()
     * @generated
     */
    void setResultError(ResultError value);

} // ResultMultiple
