/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getExtendedAttribute()
 * @model
 * @generated
 */
public interface ExtendedAttribute extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getExtendedAttribute_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(String)
     * @see com.tibco.xpd.presentation.channels.ChannelsPackage#getExtendedAttribute_Value()
     * @model
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channels.ExtendedAttribute#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

} // ExtendedAttribute
