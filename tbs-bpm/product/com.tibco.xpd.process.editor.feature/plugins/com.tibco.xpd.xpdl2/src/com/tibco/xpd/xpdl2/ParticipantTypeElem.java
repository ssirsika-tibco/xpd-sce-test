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
 * A representation of the model object '<em><b>Participant Type Elem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ParticipantTypeElem#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipantTypeElem()
 * @model extendedMetaData="name='ParticipantType_._type' kind='elementOnly'"
 * @generated
 */
public interface ParticipantTypeElem extends OtherAttributesContainer, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The default value is <code>"RESOURCE_SET"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ParticipantType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ParticipantType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(ParticipantType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipantTypeElem_Type()
     * @model default="RESOURCE_SET" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    ParticipantType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ParticipantType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(ParticipantType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(ParticipantType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ParticipantTypeElem#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(ParticipantType)
     * @generated
     */
    boolean isSetType();

} // ParticipantTypeElem
