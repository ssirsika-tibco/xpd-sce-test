/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Expression;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Participant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * deprecated.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ConditionalParticipant#getPerformerScript <em>Performer Script</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConditionalParticipant()
 * @model extendedMetaData="name='ConditionalParticipant' kind='elementOnly'"
 * @generated
 */
public interface ConditionalParticipant extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Performer Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Performer Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Performer Script</em>' containment reference.
     * @see #setPerformerScript(Expression)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getConditionalParticipant_PerformerScript()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PerformerScript' namespace='##targetNamespace'"
     * @generated
     */
    Expression getPerformerScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ConditionalParticipant#getPerformerScript <em>Performer Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Performer Script</em>' containment reference.
     * @see #getPerformerScript()
     * @generated
     */
    void setPerformerScript(Expression value);

} // ConditionalParticipant
