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
 * A representation of the model object '<em><b>Join</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Join#getIncomingCondtion <em>Incoming Condtion</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Join#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Join#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getJoin()
 * @model extendedMetaData="name='Join_._type' kind='elementOnly'"
 * @generated
 */
public interface Join extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Incoming Condtion</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Condtion</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Condtion</em>' attribute.
     * @see #setIncomingCondtion(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getJoin_IncomingCondtion()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='IncomingCondtion'"
     * @generated
     */
    String getIncomingCondtion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Join#getIncomingCondtion <em>Incoming Condtion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Incoming Condtion</em>' attribute.
     * @see #getIncomingCondtion()
     * @generated
     */
    void setIncomingCondtion(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.JoinSplitType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see #isSetType()
     * @see #unsetType()
     * @see #setType(JoinSplitType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getJoin_Type()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    JoinSplitType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Join#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see #isSetType()
     * @see #unsetType()
     * @see #getType()
     * @generated
     */
    void setType(JoinSplitType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Join#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(JoinSplitType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Join#getType <em>Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Type</em>' attribute is set.
     * @see #unsetType()
     * @see #getType()
     * @see #setType(JoinSplitType)
     * @generated
     */
    boolean isSetType();

    /**
     * Returns the value of the '<em><b>Exclusive Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ExclusiveType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Exclusive Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Exclusive Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @see #isSetExclusiveType()
     * @see #unsetExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getJoin_ExclusiveType()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='ExclusiveType'"
     * @generated
     */
    ExclusiveType getExclusiveType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Join#getExclusiveType <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Exclusive Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @see #isSetExclusiveType()
     * @see #unsetExclusiveType()
     * @see #getExclusiveType()
     * @generated
     */
    void setExclusiveType(ExclusiveType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Join#getExclusiveType <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    void unsetExclusiveType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Join#getExclusiveType <em>Exclusive Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Exclusive Type</em>' attribute is set.
     * @see #unsetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    boolean isSetExclusiveType();

} // Join
