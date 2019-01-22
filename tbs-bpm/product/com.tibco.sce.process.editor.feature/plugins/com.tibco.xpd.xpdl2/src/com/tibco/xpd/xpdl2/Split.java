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
 * A representation of the model object '<em><b>Split</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Split#getTransitionRefs <em>Transition Refs</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Split#getOutgoingCondition <em>Outgoing Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Split#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Split#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSplit()
 * @model extendedMetaData="name='Split_._type' kind='elementOnly'"
 * @generated
 */
public interface Split extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Transition Refs</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.TransitionRef}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transition Refs</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transition Refs</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSplit_TransitionRefs()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TransitionRef' namespace='##targetNamespace' wrap='TransitionRefs'"
     * @generated
     */
    EList<TransitionRef> getTransitionRefs();

    /**
     * Returns the value of the '<em><b>Outgoing Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Condition</em>' attribute.
     * @see #setOutgoingCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSplit_OutgoingCondition()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='OutgoingCondition'"
     * @generated
     */
    String getOutgoingCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Split#getOutgoingCondition <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outgoing Condition</em>' attribute.
     * @see #getOutgoingCondition()
     * @generated
     */
    void setOutgoingCondition(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSplit_Type()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    JoinSplitType getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Split#getType <em>Type</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Split#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetType()
     * @see #getType()
     * @see #setType(JoinSplitType)
     * @generated
     */
    void unsetType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Split#getType <em>Type</em>}' attribute is set.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSplit_ExclusiveType()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='ExclusiveType'"
     * @generated
     */
    ExclusiveType getExclusiveType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Split#getExclusiveType <em>Exclusive Type</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Split#getExclusiveType <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    void unsetExclusiveType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Split#getExclusiveType <em>Exclusive Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Exclusive Type</em>' attribute is set.
     * @see #unsetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    boolean isSetExclusiveType();

} // Split
