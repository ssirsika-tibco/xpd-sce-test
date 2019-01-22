/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Intermediate Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getResultError <em>Result Error</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getAnyAttribute <em>Any Attribute</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCancel <em>Trigger Result Cancel</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent()
 * @model extendedMetaData="name='IntermediateEvent_._type' kind='elementOnly' features-order='triggerResultMessage triggerTimer resultError triggerResultCompensation triggerConditional triggerResultLink triggerIntermediateMultiple'"
 * @generated
 */
public interface IntermediateEvent extends Event {
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerTimer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerTimer' namespace='##targetNamespace'"
     * @generated
     */
    TriggerTimer getTriggerTimer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerTimer <em>Trigger Timer</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_ResultError()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultError' namespace='##targetNamespace'"
     * @generated
     */
    ResultError getResultError();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getResultError <em>Result Error</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultCompensation getTriggerResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCompensation <em>Trigger Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Compensation</em>' containment reference.
     * @see #getTriggerResultCompensation()
     * @generated
     */
    void setTriggerResultCompensation(TriggerResultCompensation value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerConditional()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerConditional' namespace='##targetNamespace'"
     * @generated
     */
    TriggerConditional getTriggerConditional();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerConditional <em>Trigger Conditional</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultLink <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Link</em>' containment reference.
     * @see #getTriggerResultLink()
     * @generated
     */
    void setTriggerResultLink(TriggerResultLink value);

    /**
     * Returns the value of the '<em><b>Trigger Intermediate Multiple</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Intermediate Multiple</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Intermediate Multiple</em>' containment reference.
     * @see #setTriggerIntermediateMultiple(TriggerIntermediateMultiple)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerIntermediateMultiple()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerIntermediateMultiple' namespace='##targetNamespace'"
     * @generated
     */
    TriggerIntermediateMultiple getTriggerIntermediateMultiple();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerIntermediateMultiple <em>Trigger Intermediate Multiple</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Intermediate Multiple</em>' containment reference.
     * @see #getTriggerIntermediateMultiple()
     * @generated
     */
    void setTriggerIntermediateMultiple(TriggerIntermediateMultiple value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_Implementation()
     * @model default="WebService" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Implementation'"
     * @generated
     */
    ImplementationType getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation <em>Implementation</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementation()
     * @see #getImplementation()
     * @see #setImplementation(ImplementationType)
     * @generated
     */
    void unsetImplementation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getImplementation <em>Implementation</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  A Target MAY be included for the Intermediate Event. The Target MUST be an activity (Sub-Process or Task). This means that the Intermediate Event is attached to the boundary of the activity and is used to signify an exception or compensation for that activity.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target</em>' attribute.
     * @see #setTarget(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_Target()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Target'"
     * @generated
     */
    String getTarget();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTarget <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' attribute.
     * @see #getTarget()
     * @generated
     */
    void setTarget(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_Trigger()
     * @model default="None" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Trigger'"
     * @generated
     */
    TriggerType getTrigger();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger <em>Trigger</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTrigger()
     * @see #getTrigger()
     * @see #setTrigger(TriggerType)
     * @generated
     */
    void unsetTrigger();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTrigger <em>Trigger</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Any Attribute</em>' attribute list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':10' processing='lax'"
     * @generated
     */
    FeatureMap getAnyAttribute();

    /**
     * Returns the value of the '<em><b>Trigger Result Cancel</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Trigger Result Cancel</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Trigger Result Cancel</em>' containment reference.
     * @see #setTriggerResultCancel(TriggerResultCancel)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerResultCancel()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultCancel' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultCancel getTriggerResultCancel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultCancel <em>Trigger Result Cancel</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Cancel</em>' containment reference.
     * @see #getTriggerResultCancel()
     * @generated
     */
    void setTriggerResultCancel(TriggerResultCancel value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_TriggerResultSignal()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultSignal' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultSignal getTriggerResultSignal();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getTriggerResultSignal <em>Trigger Result Signal</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Signal</em>' containment reference.
     * @see #getTriggerResultSignal()
     * @generated
     */
    void setTriggerResultSignal(TriggerResultSignal value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_DeprecatedTriggerRule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerRule' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedTriggerRule getDeprecatedTriggerRule();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Trigger Rule</em>' containment reference.
     * @see #getDeprecatedTriggerRule()
     * @generated
     */
    void setDeprecatedTriggerRule(DeprecatedTriggerRule value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIntermediateEvent_DeprecatedResultCompensation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResultCompensation' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedResultCompensation getDeprecatedResultCompensation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.IntermediateEvent#getDeprecatedResultCompensation <em>Deprecated Result Compensation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Result Compensation</em>' containment reference.
     * @see #getDeprecatedResultCompensation()
     * @generated
     */
    void setDeprecatedResultCompensation(DeprecatedResultCompensation value);

} // IntermediateEvent
