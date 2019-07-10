/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Record Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.RecordType#getMember <em>Member</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRecordType()
 * @model extendedMetaData="name='RecordType_._type' kind='elementOnly' features-order='member'"
 * @generated
 */
public interface RecordType extends DataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Member</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Member}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Member</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Member</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRecordType_Member()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Member' namespace='##targetNamespace'"
     * @generated
     */
    EList<Member> getMember();

} // RecordType
