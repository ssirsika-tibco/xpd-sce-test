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
 * A representation of the model object '<em><b>Enumeration Value Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.EnumerationValueType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.EnumerationValueType#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getEnumerationValueType()
 * @model extendedMetaData="name='EnumerationValue_._type' kind='elementOnly'"
 * @generated
 */
public interface EnumerationValueType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getEnumerationValueType_Description()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.EnumerationValueType#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

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
     * @see com.tibco.xpd.simulation.SimulationPackage#getEnumerationValueType_Value()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Value'"
     * @generated
     */
    String getValue();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.EnumerationValueType#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(String value);

    /**
     * Returns the value of the '<em><b>Weighting Factor</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Weighting Factor</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Weighting Factor</em>' attribute.
     * @see #isSetWeightingFactor()
     * @see #unsetWeightingFactor()
     * @see #setWeightingFactor(double)
     * @see com.tibco.xpd.simulation.SimulationPackage#getEnumerationValueType_WeightingFactor()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
     *        extendedMetaData="kind='attribute' name='WeightingFactor'"
     * @generated
     */
    double getWeightingFactor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor <em>Weighting Factor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Weighting Factor</em>' attribute.
     * @see #isSetWeightingFactor()
     * @see #unsetWeightingFactor()
     * @see #getWeightingFactor()
     * @generated
     */
    void setWeightingFactor(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor <em>Weighting Factor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWeightingFactor()
     * @see #getWeightingFactor()
     * @see #setWeightingFactor(double)
     * @generated
     */
    void unsetWeightingFactor();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.simulation.EnumerationValueType#getWeightingFactor <em>Weighting Factor</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Weighting Factor</em>' attribute is set.
     * @see #unsetWeightingFactor()
     * @see #getWeightingFactor()
     * @see #setWeightingFactor(double)
     * @generated
     */
    boolean isSetWeightingFactor();

} // EnumerationValueType
