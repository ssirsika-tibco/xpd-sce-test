/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Control</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element used in XPDL:Process to override the default validate behaviour of the specific validation to supress it until next flow container change OR until it is reset to validate.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ValidationControl#getValidationIssueOverrides <em>Validation Issue Overrides</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationControl()
 * @model extendedMetaData="name='ValidationControl' kind='empty'"
 * @generated
 */
public interface ValidationControl extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Validation Issue Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdExtension.ValidationIssueOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Issue Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Issue Overrides</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationControl_ValidationIssueOverrides()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ValidationIssueOverride' namespace='##targetNamespace' wrap='ValidationIssueOverrides'"
	 * @generated
	 */
	EList<ValidationIssueOverride> getValidationIssueOverrides();

} // ValidationControl
