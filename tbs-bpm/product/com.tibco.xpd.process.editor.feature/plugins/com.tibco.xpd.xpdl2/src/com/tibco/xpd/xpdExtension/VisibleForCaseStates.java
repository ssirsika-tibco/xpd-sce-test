/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visible For Case States</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates#isVisibleForUnsetCaseState <em>Visible For Unset Case State</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates#getCaseState <em>Case State</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getVisibleForCaseStates()
 * @model extendedMetaData="name='VisibleForCaseStates' kind='elementOnly'"
 * @generated
 */
public interface VisibleForCaseStates extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Visible For Unset Case State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visible For Unset Case State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visible For Unset Case State</em>' attribute.
	 * @see #setVisibleForUnsetCaseState(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getVisibleForCaseStates_VisibleForUnsetCaseState()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='VisibleForUnsetCaseState' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isVisibleForUnsetCaseState();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.VisibleForCaseStates#isVisibleForUnsetCaseState <em>Visible For Unset Case State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visible For Unset Case State</em>' attribute.
	 * @see #isVisibleForUnsetCaseState()
	 * @generated
	 */
	void setVisibleForUnsetCaseState(boolean value);

	/**
	 * Returns the value of the '<em><b>Case State</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdl2.ExternalReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Reference to BOM enumeration attributes
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Case State</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getVisibleForCaseStates_CaseState()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='CaseState' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ExternalReference> getCaseState();

} // VisibleForCaseStates
