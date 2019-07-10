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
 * A representation of the model object '<em><b>Enumeration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.EnumerationType#getEnumerationValue <em>Enumeration Value</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEnumerationType()
 * @model extendedMetaData="name='EnumerationType_._type' kind='elementOnly' features-order='enumerationValue'"
 * @generated
 */
public interface EnumerationType extends DataType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Enumeration Value</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.EnumerationValue}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enumeration Value</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enumeration Value</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEnumerationType_EnumerationValue()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='EnumerationValue' namespace='##targetNamespace'"
     * @generated
     */
    EList<EnumerationValue> getEnumerationValue();

} // EnumerationType
