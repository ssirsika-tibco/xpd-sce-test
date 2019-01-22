/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loop Control Transition Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.LoopControlTransitionType#getDecisionActivity <em>Decision Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.LoopControlTransitionType#getToActivity <em>To Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlTransitionType()
 * @model extendedMetaData="name='LoopControlTransitionType' kind='elementOnly'"
 * @generated
 */
public interface LoopControlTransitionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Decision Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Decision Activity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Decision Activity</em>' attribute.
     * @see #setDecisionActivity(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlTransitionType_DecisionActivity()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='element' name='DecisionActivity' namespace='##targetNamespace'"
     * @generated
     */
    String getDecisionActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.LoopControlTransitionType#getDecisionActivity <em>Decision Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Decision Activity</em>' attribute.
     * @see #getDecisionActivity()
     * @generated
     */
    void setDecisionActivity(String value);

    /**
     * Returns the value of the '<em><b>To Activity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>To Activity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>To Activity</em>' attribute.
     * @see #setToActivity(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getLoopControlTransitionType_ToActivity()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='element' name='ToActivity' namespace='##targetNamespace'"
     * @generated
     */
    String getToActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.LoopControlTransitionType#getToActivity <em>To Activity</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>To Activity</em>' attribute.
     * @see #getToActivity()
     * @generated
     */
    void setToActivity(String value);

} // LoopControlTransitionType