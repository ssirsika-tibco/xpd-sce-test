/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Issue Override</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A given validaiton issue-id and a validation rule override that can control it.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getValidationIssueId <em>Validation Issue Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType <em>Override Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationIssueOverride()
 * @model extendedMetaData="name='ValidationIssueOverride' kind='empty'"
 * @generated
 */
public interface ValidationIssueOverride extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Validation Issue Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Issue Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Issue Id</em>' attribute.
	 * @see #setValidationIssueId(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationIssueOverride_ValidationIssueId()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='ValidationIssueId'"
	 * @generated
	 */
	String getValidationIssueId();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getValidationIssueId <em>Validation Issue Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Issue Id</em>' attribute.
	 * @see #getValidationIssueId()
	 * @generated
	 */
	void setValidationIssueId(String value);

	/**
	 * Returns the value of the '<em><b>Override Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.ValidationIssueOverrideType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Override Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Override Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.ValidationIssueOverrideType
	 * @see #isSetOverrideType()
	 * @see #unsetOverrideType()
	 * @see #setOverrideType(ValidationIssueOverrideType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getValidationIssueOverride_OverrideType()
	 * @model unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='OverrideType'"
	 * @generated
	 */
	ValidationIssueOverrideType getOverrideType();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType <em>Override Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Override Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.ValidationIssueOverrideType
	 * @see #isSetOverrideType()
	 * @see #unsetOverrideType()
	 * @see #getOverrideType()
	 * @generated
	 */
	void setOverrideType(ValidationIssueOverrideType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType <em>Override Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOverrideType()
	 * @see #getOverrideType()
	 * @see #setOverrideType(ValidationIssueOverrideType)
	 * @generated
	 */
	void unsetOverrideType();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ValidationIssueOverride#getOverrideType <em>Override Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Override Type</em>' attribute is set.
	 * @see #unsetOverrideType()
	 * @see #getOverrideType()
	 * @see #setOverrideType(ValidationIssueOverrideType)
	 * @generated
	 */
	boolean isSetOverrideType();

} // ValidationIssueOverride
