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
 * A representation of the model object '<em><b>Split Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.SplitParameterType#getParameterId <em>Parameter Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getSplitParameterType()
 * @model extendedMetaData="name='SplitParameter_._type' kind='empty'"
 * @generated
 */
public interface SplitParameterType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

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
     * @see com.tibco.xpd.simulation.SimulationPackage#getSplitParameterType_ParameterId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='ParameterId'"
     * @generated
     */
    String getParameterId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.SplitParameterType#getParameterId <em>Parameter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parameter Id</em>' attribute.
     * @see #getParameterId()
     * @generated
     */
    void setParameterId(String value);

} // SplitParameterType
