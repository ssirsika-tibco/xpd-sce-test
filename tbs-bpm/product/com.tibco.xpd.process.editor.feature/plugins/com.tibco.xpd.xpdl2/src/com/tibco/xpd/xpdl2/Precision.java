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
 * A representation of the model object '<em><b>Precision</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Precision#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPrecision()
 * @model extendedMetaData="name='Precision_._type' kind='simple'"
 * @generated
 */
public interface Precision extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #isSetValue()
     * @see #unsetValue()
     * @see #setValue(short)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPrecision_Value()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short"
     *        extendedMetaData="name=':0' kind='simple'"
     * @generated
     */
    short getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Precision#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #isSetValue()
     * @see #unsetValue()
     * @see #getValue()
     * @generated
     */
    void setValue(short value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Precision#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetValue()
     * @see #getValue()
     * @see #setValue(short)
     * @generated
     */
    void unsetValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Precision#getValue <em>Value</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Value</em>' attribute is set.
     * @see #unsetValue()
     * @see #getValue()
     * @see #setValue(short)
     * @generated
     */
    boolean isSetValue();

} // Precision
