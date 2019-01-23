/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumeration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor <em>Weighting Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getEnumeration()
 * @model extendedMetaData="name='Enumeration' kind='empty'"
 * @generated
 */
public interface Enumeration extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getEnumeration_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getEnumeration_WeightingFactor()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='WeightingFactor' namespace='##targetNamespace'"
	 * @generated
	 */
	double getWeightingFactor();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor <em>Weighting Factor</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor <em>Weighting Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWeightingFactor()
	 * @see #getWeightingFactor()
	 * @see #setWeightingFactor(double)
	 * @generated
	 */
	void unsetWeightingFactor();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.simulation.empiricaldata.Enumeration#getWeightingFactor <em>Weighting Factor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Weighting Factor</em>' attribute is set.
	 * @see #unsetWeightingFactor()
	 * @see #getWeightingFactor()
	 * @see #setWeightingFactor(double)
	 * @generated
	 */
	boolean isSetWeightingFactor();

} // Enumeration
