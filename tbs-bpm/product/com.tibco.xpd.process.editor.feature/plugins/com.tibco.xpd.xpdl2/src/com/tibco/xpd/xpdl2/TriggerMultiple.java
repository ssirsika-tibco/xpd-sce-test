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
 * A representation of the model object '<em><b>Trigger Multiple</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerTimer <em>Trigger Timer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerConditional <em>Trigger Conditional</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultLink <em>Trigger Result Link</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerMultiple#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple()
 * @model extendedMetaData="name='TriggerMultiple_._type' kind='elementOnly'"
 * @generated
 */
public interface TriggerMultiple extends OtherAttributesContainer {
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple_TriggerTimer()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerTimer' namespace='##targetNamespace'"
     * @generated
     */
    TriggerTimer getTriggerTimer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerTimer <em>Trigger Timer</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple_TriggerConditional()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerConditional' namespace='##targetNamespace'"
     * @generated
     */
    TriggerConditional getTriggerConditional();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerConditional <em>Trigger Conditional</em>}' containment reference.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple_TriggerResultLink()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultLink' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultLink getTriggerResultLink();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getTriggerResultLink <em>Trigger Result Link</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Link</em>' containment reference.
     * @see #getTriggerResultLink()
     * @generated
     */
    void setTriggerResultLink(TriggerResultLink value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerMultiple_DeprecatedTriggerRule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerRule' namespace='##targetNamespace'"
     * @generated
     */
    DeprecatedTriggerRule getDeprecatedTriggerRule();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerMultiple#getDeprecatedTriggerRule <em>Deprecated Trigger Rule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Trigger Rule</em>' containment reference.
     * @see #getDeprecatedTriggerRule()
     * @generated
     */
    void setDeprecatedTriggerRule(DeprecatedTriggerRule value);

} // TriggerMultiple
