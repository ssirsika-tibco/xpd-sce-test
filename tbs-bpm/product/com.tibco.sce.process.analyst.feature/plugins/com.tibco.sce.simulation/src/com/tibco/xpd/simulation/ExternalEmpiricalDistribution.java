/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Empirical Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getReference <em>Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.SimulationPackage#getExternalEmpiricalDistribution()
 * @model extendedMetaData="name='ExternalEmpiricalDistribution_._type' kind='empty'"
 * @generated
 */
public interface ExternalEmpiricalDistribution extends
        AbstractBasicDistribution {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Reference</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reference</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Reference</em>' attribute.
     * @see #setReference(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExternalEmpiricalDistribution_Reference()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Reference'"
     * @generated
     */
    String getReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getReference <em>Reference</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference</em>' attribute.
     * @see #getReference()
     * @generated
     */
    void setReference(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see #setType(String)
     * @see com.tibco.xpd.simulation.SimulationPackage#getExternalEmpiricalDistribution_Type()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Type'"
     * @generated
     */
    String getType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.simulation.ExternalEmpiricalDistribution#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see #getType()
     * @generated
     */
    void setType(String value);

} // ExternalEmpiricalDistribution