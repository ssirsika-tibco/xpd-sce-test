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
 * A representation of the model object '<em><b>Transition Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.TransitionSimulationDataType#getStructuredCondition <em>Structured Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getTransitionSimulationDataType()
 * @model extendedMetaData="name='TransitionSimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface TransitionSimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Parameter Determined Condition</b></em>' attribute.
     * The default value is <code>"true"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Determined Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Determined Condition</em>' attribute.
     * @see #isSetParameterDeterminedCondition()
     * @see #unsetParameterDeterminedCondition()
     * @see #setParameterDeterminedCondition(boolean)
     * @see com.tibco.xpd.simulation.SimulationPackage#getTransitionSimulationDataType_ParameterDeterminedCondition()
     * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='element' name='ParameterDeterminedCondition' namespace='##targetNamespace'"
     * @generated
     */
    boolean isParameterDeterminedCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Determined Condition</em>' attribute.
     * @see #isSetParameterDeterminedCondition()
     * @see #unsetParameterDeterminedCondition()
     * @see #isParameterDeterminedCondition()
     * @generated
     */
    void setParameterDeterminedCondition(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetParameterDeterminedCondition()
     * @see #isParameterDeterminedCondition()
     * @see #setParameterDeterminedCondition(boolean)
     * @generated
     */
    void unsetParameterDeterminedCondition();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#isParameterDeterminedCondition <em>Parameter Determined Condition</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Parameter Determined Condition</em>' attribute is set.
     * @see #unsetParameterDeterminedCondition()
     * @see #isParameterDeterminedCondition()
     * @see #setParameterDeterminedCondition(boolean)
     * @generated
     */
    boolean isSetParameterDeterminedCondition();

    /**
     * Returns the value of the '<em><b>Structured Condition</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Structured Condition</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Structured Condition</em>' containment reference.
     * @see #setStructuredCondition(StructuredConditionType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getTransitionSimulationDataType_StructuredCondition()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='StructuredCondition' namespace='##targetNamespace'"
     * @generated
     */
    StructuredConditionType getStructuredCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.TransitionSimulationDataType#getStructuredCondition <em>Structured Condition</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Structured Condition</em>' containment reference.
     * @see #getStructuredCondition()
     * @generated
     */
    void setStructuredCondition(StructuredConditionType value);

} // TransitionSimulationDataType
