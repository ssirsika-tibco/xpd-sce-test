/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Participant#getParticipantType <em>Participant Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Participant#getExternalReference <em>External Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipant()
 * @model extendedMetaData="name='Participant_._type' kind='elementOnly' features-order='participantType description externalReference extendedAttributes otherElements'"
 * @generated
 */
public interface Participant extends NamedElement, ExtendedAttributesContainer,
        DescribedElement, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Participant Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Participant Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Participant Type</em>' containment reference.
     * @see #setParticipantType(ParticipantTypeElem)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipant_ParticipantType()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ParticipantType' namespace='##targetNamespace'"
     * @generated
     */
    ParticipantTypeElem getParticipantType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Participant#getParticipantType <em>Participant Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Participant Type</em>' containment reference.
     * @see #getParticipantType()
     * @generated
     */
    void setParticipantType(ParticipantTypeElem value);

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Reference</em>' containment reference.
     * @see #setExternalReference(ExternalReference)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipant_ExternalReference()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
     * @generated
     */
    ExternalReference getExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Participant#getExternalReference <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>External Reference</em>' containment reference.
     * @see #getExternalReference()
     * @generated
     */
    void setExternalReference(ExternalReference value);

} // Participant
