/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Start Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerMultiple <em>Trigger Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent()
 * @model extendedMetaData="name='StartEvent_._type' kind='elementOnly' features-order='triggerResultMessage triggerTimer triggerConditional triggerResultLink triggerMultiple'"
 * @generated
 */
public interface StartEvent extends Event {
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_TriggerTimer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerTimer' namespace='##targetNamespace'"
     * @generated
     */
    TriggerTimer getTriggerTimer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerTimer <em>Trigger Timer</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Timer</em>' containment reference.
     * @see #getTriggerTimer()
     * @generated
     */
    void setTriggerTimer(TriggerTimer value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_TriggerConditional()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerConditional' namespace='##targetNamespace'"
     * @generated
     */
    TriggerConditional getTriggerConditional();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerConditional <em>Trigger Conditional</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Conditional</em>' containment reference.
     * @see #getTriggerConditional()
     * @generated
     */
    void setTriggerConditional(TriggerConditional value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_TriggerResultSignal()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultSignal' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultSignal getTriggerResultSignal();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Signal</em>' containment reference.
     * @see #getTriggerResultSignal()
     * @generated
     */
    void setTriggerResultSignal(TriggerResultSignal value);

    /**
     * Returns the value of the '<em><b>Trigger Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Multiple</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Multiple</em>' containment reference.
     * @see #setTriggerMultiple(TriggerMultiple)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_TriggerMultiple()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerMultiple' namespace='##targetNamespace'"
     * @generated
     */
    TriggerMultiple getTriggerMultiple();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTriggerMultiple <em>Trigger Multiple</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Multiple</em>' containment reference.
     * @see #getTriggerMultiple()
     * @generated
     */
    void setTriggerMultiple(TriggerMultiple value);

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' attribute.
     * The default value is <code>"WebService"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ImplementationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Required if the Trigger is Message
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ImplementationType
     * @see #isSetImplementation()
     * @see #unsetImplementation()
     * @see #setImplementation(ImplementationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_Implementation()
     * @model default="WebService" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Implementation'"
     * @generated
     */
    ImplementationType getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getImplementation <em>Implementation</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    void unsetImplementation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getImplementation <em>Implementation</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Trigger</b></em>' attribute.
     * The default value is <code>"None"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.TriggerType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see #isSetTrigger()
     * @see #unsetTrigger()
     * @see #setTrigger(TriggerType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_Trigger()
     * @model default="None" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Trigger'"
     * @generated
     */
    TriggerType getTrigger();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTrigger <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see #isSetTrigger()
     * @see #unsetTrigger()
     * @see #getTrigger()
     * @generated
     */
    void setTrigger(TriggerType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTrigger <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTrigger()
     * @see #getTrigger()
     * @see #setTrigger(TriggerType)
     * @generated
     */
    void unsetTrigger();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getTrigger <em>Trigger</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Trigger</em>' attribute is set.
     * @see #unsetTrigger()
     * @see #getTrigger()
     * @see #setTrigger(TriggerType)
     * @generated
     */
    boolean isSetTrigger();

    /**
     * Returns the value of the '<em><b>Deprecated Trigger Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Trigger Rule</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Trigger Rule</em>' containment reference.
     * @see #setDeprecatedTriggerRule(DeprecatedTriggerRule)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_DeprecatedTriggerRule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerRule' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedTriggerRule getDeprecatedTriggerRule();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Trigger Rule</em>' containment reference.
     * @see #getDeprecatedTriggerRule()
     * @generated
     */
    void setDeprecatedTriggerRule(DeprecatedTriggerRule value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getStartEvent_DeprecatedTriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getDeprecatedTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.StartEvent#getDeprecatedTriggerResultLink <em>Deprecated Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Trigger Result Link</em>' containment reference.
     * @see #getDeprecatedTriggerResultLink()
     * @generated
     */
    void setDeprecatedTriggerResultLink(TriggerResultLink value);

} // StartEvent
