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
 * A representation of the model object '<em><b>Split Simulation Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit <em>Parameter Determined Split</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.SplitSimulationDataType#getSplitParameter <em>Split Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getSplitSimulationDataType()
 * @model extendedMetaData="name='SplitSimulationDataType' kind='elementOnly'"
 * @generated
 */
public interface SplitSimulationDataType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Parameter Determined Split</b></em>' attribute.
     * The default value is <code>"true"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Determined Split</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Determined Split</em>' attribute.
     * @see #isSetParameterDeterminedSplit()
     * @see #unsetParameterDeterminedSplit()
     * @see #setParameterDeterminedSplit(boolean)
     * @see com.tibco.xpd.simulation.SimulationPackage#getSplitSimulationDataType_ParameterDeterminedSplit()
     * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='element' name='ParameterDeterminedSplit' namespace='##targetNamespace'"
     * @generated
     */
    boolean isParameterDeterminedSplit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit <em>Parameter Determined Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Determined Split</em>' attribute.
     * @see #isSetParameterDeterminedSplit()
     * @see #unsetParameterDeterminedSplit()
     * @see #isParameterDeterminedSplit()
     * @generated
     */
    void setParameterDeterminedSplit(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit <em>Parameter Determined Split</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetParameterDeterminedSplit()
     * @see #isParameterDeterminedSplit()
     * @see #setParameterDeterminedSplit(boolean)
     * @generated
     */
    void unsetParameterDeterminedSplit();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.SplitSimulationDataType#isParameterDeterminedSplit <em>Parameter Determined Split</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Parameter Determined Split</em>' attribute is set.
     * @see #unsetParameterDeterminedSplit()
     * @see #isParameterDeterminedSplit()
     * @see #setParameterDeterminedSplit(boolean)
     * @generated
     */
    boolean isSetParameterDeterminedSplit();

    /**
     * Returns the value of the '<em><b>Split Parameter</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Split Parameter</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Split Parameter</em>' containment reference.
     * @see #setSplitParameter(SplitParameterType)
     * @see com.tibco.xpd.simulation.SimulationPackage#getSplitSimulationDataType_SplitParameter()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='SplitParameter' namespace='##targetNamespace'"
     * @generated
     */
    SplitParameterType getSplitParameter();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.SplitSimulationDataType#getSplitParameter <em>Split Parameter</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Split Parameter</em>' containment reference.
     * @see #getSplitParameter()
     * @generated
     */
    void setSplitParameter(SplitParameterType value);

} // SplitSimulationDataType
