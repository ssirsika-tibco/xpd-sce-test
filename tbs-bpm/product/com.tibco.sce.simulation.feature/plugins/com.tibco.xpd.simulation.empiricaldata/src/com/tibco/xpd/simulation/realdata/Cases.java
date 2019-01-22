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
 * A representation of the model object '<em><b>Cases</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.realdata.Cases#getCase <em>Case</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#getCases()
 * @model extendedMetaData="name='Cases' kind='elementOnly'"
 * @generated
 */
public interface Cases extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Case</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.simulation.realdata.Case}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Case</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Case</em>' containment reference list.
	 * @see com.tibco.xpd.simulation.realdata.RealDataPackage#getCases_Case()
	 * @model type="com.tibco.xpd.simulation.realdata.Case" containment="true"
	 *        extendedMetaData="kind='element' name='Case' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getCase();

} // Cases
