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
 * A representation of the model object '<em><b>Uniform Real Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.UniformRealDistributionType#getLowerBorder <em>Lower Border</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.UniformRealDistributionType#getUpperBorder <em>Upper Border</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getUniformRealDistributionType()
 * @model extendedMetaData="name='UniformRealDistribution_._type' kind='empty'"
 * @generated
 */
public interface UniformRealDistributionType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Lower Border</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lower Border</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lower Border</em>' attribute.
     * @see #isSetLowerBorder()
     * @see #unsetLowerBorder()
     * @see #setLowerBorder(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getUniformRealDistributionType_LowerBorder()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='attribute' name='LowerBorder'"
     * @generated
     */
    double getLowerBorder();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getLowerBorder <em>Lower Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lower Border</em>' attribute.
     * @see #isSetLowerBorder()
     * @see #unsetLowerBorder()
     * @see #getLowerBorder()
     * @generated
     */
    void setLowerBorder(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getLowerBorder <em>Lower Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLowerBorder()
     * @see #getLowerBorder()
     * @see #setLowerBorder(double)
     * @generated
     */
    void unsetLowerBorder();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getLowerBorder <em>Lower Border</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Lower Border</em>' attribute is set.
     * @see #unsetLowerBorder()
     * @see #getLowerBorder()
     * @see #setLowerBorder(double)
     * @generated
     */
    boolean isSetLowerBorder();

    /**
     * Returns the value of the '<em><b>Upper Border</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Upper Border</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Upper Border</em>' attribute.
     * @see #isSetUpperBorder()
     * @see #unsetUpperBorder()
     * @see #setUpperBorder(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getUniformRealDistributionType_UpperBorder()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='attribute' name='UpperBorder'"
     * @generated
     */
    double getUpperBorder();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getUpperBorder <em>Upper Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Upper Border</em>' attribute.
     * @see #isSetUpperBorder()
     * @see #unsetUpperBorder()
     * @see #getUpperBorder()
     * @generated
     */
    void setUpperBorder(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getUpperBorder <em>Upper Border</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetUpperBorder()
     * @see #getUpperBorder()
     * @see #setUpperBorder(double)
     * @generated
     */
    void unsetUpperBorder();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.UniformRealDistributionType#getUpperBorder <em>Upper Border</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Upper Border</em>' attribute is set.
     * @see #unsetUpperBorder()
     * @see #getUpperBorder()
     * @see #setUpperBorder(double)
     * @generated
     */
    boolean isSetUpperBorder();

} // UniformRealDistributionType
