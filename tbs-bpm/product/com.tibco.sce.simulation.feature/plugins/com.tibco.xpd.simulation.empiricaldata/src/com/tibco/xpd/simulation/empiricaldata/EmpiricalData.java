/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.empiricaldata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Empirical Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.empiricaldata.EmpiricalData#getPeriod <em>Period</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getEmpiricalData()
 * @model extendedMetaData="name='EmpiricalData' kind='elementOnly'"
 * @generated
 */
public interface EmpiricalData extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Period</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.simulation.empiricaldata.Period}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Period</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Period</em>' containment reference list.
	 * @see com.tibco.xpd.simulation.empiricaldata.EmpiricalDataPackage#getEmpiricalData_Period()
	 * @model type="com.tibco.xpd.simulation.empiricaldata.Period" containment="true"
	 *        extendedMetaData="kind='element' name='Period' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getPeriod();

} // EmpiricalData
