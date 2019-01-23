/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.realdata;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.realdata.Case#getParamter <em>Paramter</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.realdata.Case#getStartTime <em>Start Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#getCase()
 * @model extendedMetaData="name='Case' kind='elementOnly'"
 * @generated
 */
public interface Case extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Paramter</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.simulation.realdata.Paramter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paramter</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paramter</em>' containment reference list.
	 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#getCase_Paramter()
	 * @model type="com.tibco.xpd.simulation.realdata.Paramter" containment="true"
	 *        extendedMetaData="kind='element' name='Paramter' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getParamter();

	/**
	 * Returns the value of the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Time</em>' attribute.
	 * @see #setStartTime(Object)
	 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#getCase_StartTime()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='startTime' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getStartTime();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.simulation.realdata.Case#getStartTime <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Time</em>' attribute.
	 * @see #getStartTime()
	 * @generated
	 */
	void setStartTime(Object value);

} // Case
