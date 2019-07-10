/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Participants Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ParticipantsContainer#getParticipants <em>Participants</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipantsContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ParticipantsContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Participants</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Participant}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Participants</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Participants</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getParticipantsContainer_Participants()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Participant' namespace='##targetNamespace' wrap='Participants'"
     * @generated
     */
    EList<Participant> getParticipants();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Participant getParticipant(String id);

} // ParticipantsContainer
