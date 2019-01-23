/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant Real Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue <em>Constant Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getConstantRealDistribution()
 * @model extendedMetaData="name='ConstantRealDistribution_._type' kind='empty'"
 * @generated
 */
public interface ConstantRealDistribution extends AbstractBasicDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Constant Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Constant Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Constant Value</em>' attribute.
     * @see #isSetConstantValue()
     * @see #unsetConstantValue()
     * @see #setConstantValue(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getConstantRealDistribution_ConstantValue()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='attribute' name='ConstantValue'"
     * @generated
     */
    double getConstantValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue <em>Constant Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Constant Value</em>' attribute.
     * @see #isSetConstantValue()
     * @see #unsetConstantValue()
     * @see #getConstantValue()
     * @generated
     */
    void setConstantValue(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue <em>Constant Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetConstantValue()
     * @see #getConstantValue()
     * @see #setConstantValue(double)
     * @generated
     */
    void unsetConstantValue();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.ConstantRealDistribution#getConstantValue <em>Constant Value</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Constant Value</em>' attribute is set.
     * @see #unsetConstantValue()
     * @see #getConstantValue()
     * @see #setConstantValue(double)
     * @generated
     */
    boolean isSetConstantValue();

} // ConstantRealDistribution