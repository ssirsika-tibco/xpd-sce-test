/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Max Loop Count Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount <em>Max Loop Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getMaxLoopCountStrategyType()
 * @model extendedMetaData="name='MaxLoopCountStrategyType' kind='elementOnly'"
 * @generated
 */
public interface MaxLoopCountStrategyType extends LoopControlTransitionType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Max Loop Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max Loop Count</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Max Loop Count</em>' attribute.
     * @see #isSetMaxLoopCount()
     * @see #unsetMaxLoopCount()
     * @see #setMaxLoopCount(int)
     * @see com.tibco.xpd.simulation.SimulationPackage#getMaxLoopCountStrategyType_MaxLoopCount()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
     *        extendedMetaData="kind='element' name='MaxLoopCount' namespace='##targetNamespace'"
     * @generated
     */
    int getMaxLoopCount();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount <em>Max Loop Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Max Loop Count</em>' attribute.
     * @see #isSetMaxLoopCount()
     * @see #unsetMaxLoopCount()
     * @see #getMaxLoopCount()
     * @generated
     */
    void setMaxLoopCount(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount <em>Max Loop Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMaxLoopCount()
     * @see #getMaxLoopCount()
     * @see #setMaxLoopCount(int)
     * @generated
     */
    void unsetMaxLoopCount();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.MaxLoopCountStrategyType#getMaxLoopCount <em>Max Loop Count</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Max Loop Count</em>' attribute is set.
     * @see #unsetMaxLoopCount()
     * @see #getMaxLoopCount()
     * @see #setMaxLoopCount(int)
     * @generated
     */
    boolean isSetMaxLoopCount();

} // MaxLoopCountStrategyType