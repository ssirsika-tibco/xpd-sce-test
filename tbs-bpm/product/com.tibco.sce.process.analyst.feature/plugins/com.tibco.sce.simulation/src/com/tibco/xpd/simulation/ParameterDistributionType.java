/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Distribution Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ParameterDistributionType#getEnumerationValue <em>Enumeration Value</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ParameterDistributionType#getParameterId <em>Parameter Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDistributionType()
 * @model extendedMetaData="name='ParameterDistributionType' kind='elementOnly'"
 * @generated
 */
public interface ParameterDistributionType extends EObject{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Enumeration Value</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.simulation.EnumerationValueType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enumeration Value</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enumeration Value</em>' containment reference list.
     * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDistributionType_EnumerationValue()
     * @model type="com.tibco.xpd.simulation.EnumerationValueType" containment="true" resolveProxies="false"
     *        extendedMetaData="kind='element' name='EnumerationValue' namespace='##targetNamespace'"
     * @generated
     */
    EList getEnumerationValue();

    /**
     * Returns the value of the '<em><b>Parameter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameter Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameter Id</em>' attribute.
     * @see #setParameterId(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getParameterDistributionType_ParameterId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='ParameterId'"
     * @generated
     */
    String getParameterId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ParameterDistributionType#getParameterId <em>Parameter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Id</em>' attribute.
     * @see #getParameterId()
     * @generated
     */
    void setParameterId(String value);

} // ParameterDistributionType
