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
 * A representation of the model object '<em><b>Transition Restriction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TransitionRestriction#getJoin <em>Join</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TransitionRestriction#getSplit <em>Split</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransitionRestriction()
 * @model extendedMetaData="name='TransitionRestriction_._type' kind='elementOnly'"
 * @generated
 */
public interface TransitionRestriction extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Join</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Join</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Join</em>' containment reference.
     * @see #setJoin(Join)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransitionRestriction_Join()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Join' namespace='##targetNamespace'"
     * @generated
     */
    Join getJoin();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TransitionRestriction#getJoin <em>Join</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Join</em>' containment reference.
     * @see #getJoin()
     * @generated
     */
    void setJoin(Join value);

    /**
     * Returns the value of the '<em><b>Split</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Split</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Split</em>' containment reference.
     * @see #setSplit(Split)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTransitionRestriction_Split()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Split' namespace='##targetNamespace'"
     * @generated
     */
    Split getSplit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TransitionRestriction#getSplit <em>Split</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Split</em>' containment reference.
     * @see #getSplit()
     * @generated
     */
    void setSplit(Split value);

} // TransitionRestriction
