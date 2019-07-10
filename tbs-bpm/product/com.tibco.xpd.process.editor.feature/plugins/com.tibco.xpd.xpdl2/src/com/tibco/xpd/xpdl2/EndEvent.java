/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getResultMultiple <em>Result Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getResult <em>Result</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent()
 * @model extendedMetaData="name='EndEvent_._type' kind='elementOnly' features-order='triggerResultMessage resultError triggerResultCompensation triggerResultLink resultMultiple'"
 * @generated
 */
public interface EndEvent extends Event {
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #getTriggerResultMessage()
     * @generated
     */
    void setTriggerResultMessage(TriggerResultMessage value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_ResultError()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultError' namespace='##targetNamespace'"
     * @generated
     */
    ResultError getResultError();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getResultError <em>Result Error</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_TriggerResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultCompensation getTriggerResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Compensation</em>' containment reference.
     * @see #getTriggerResultCompensation()
     * @generated
     */
    void setTriggerResultCompensation(TriggerResultCompensation value);

    /**
     * Returns the value of the '<em><b>Trigger Result Signal</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Result Signal</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Result Signal</em>' containment reference.
     * @see #setTriggerResultSignal(TriggerResultSignal)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_TriggerResultSignal()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultSignal' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultSignal getTriggerResultSignal();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Signal</em>' containment reference.
     * @see #getTriggerResultSignal()
     * @generated
     */
    void setTriggerResultSignal(TriggerResultSignal value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_DeprecatedResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedResultCompensation getDeprecatedResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Result Compensation</em>' containment reference.
     * @see #getDeprecatedResultCompensation()
     * @generated
     */
    void setDeprecatedResultCompensation(DeprecatedResultCompensation value);

    /**
     * Returns the value of the '<em><b>Result Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Result Multiple</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Result Multiple</em>' containment reference.
     * @see #setResultMultiple(ResultMultiple)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_ResultMultiple()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultMultiple' namespace='##targetNamespace'"
     * @generated
     */
    ResultMultiple getResultMultiple();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getResultMultiple <em>Result Multiple</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Result Multiple</em>' containment reference.
     * @see #getResultMultiple()
     * @generated
     */
    void setResultMultiple(ResultMultiple value);

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' attribute.
     * The default value is <code>"WebService"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ImplementationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Required if the Trigger or Result is Message
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #setImplementation(ImplementationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_Implementation()
     * @model default="WebService" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Implementation'"
     * @generated
     */
    ImplementationType getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @generated
     */
    void setImplementation(ImplementationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    void unsetImplementation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getImplementation <em>Implementation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Implementation</em>' attribute is set.
     * @see #unsetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    boolean isSetImplementation();

    /**
     * Returns the value of the '<em><b>Result</b></em>' attribute.
     * The default value is <code>"None"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ResultType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Result</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Result</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ResultType
     * @see #isSetResult()
     * @see #unsetResult()
     * @see #setResult(ResultType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_Result()
     * @model default="None" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Result'"
     * @generated
     */
    ResultType getResult();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getResult <em>Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Result</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ResultType
     * @see #isSetResult()
     * @see #unsetResult()
     * @see #getResult()
     * @generated
     */
    void setResult(ResultType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getResult <em>Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetResult()
     * @see #getResult()
     * @see #setResult(ResultType)
     * @generated
     */
    void unsetResult();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getResult <em>Result</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Result</em>' attribute is set.
     * @see #unsetResult()
     * @see #getResult()
     * @see #setResult(ResultType)
     * @generated
     */
    boolean isSetResult();

    /**
     * Returns the value of the '<em><b>Deprecated Trigger Result Link</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Trigger Result Link</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Trigger Result Link</em>' containment reference.
     * @see #setDeprecatedTriggerResultLink(TriggerResultLink)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndEvent_DeprecatedTriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getDeprecatedTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Trigger Result Link</em>' containment reference.
     * @see #getDeprecatedTriggerResultLink()
     * @generated
     */
    void setDeprecatedTriggerResultLink(TriggerResultLink value);

} // EndEvent
