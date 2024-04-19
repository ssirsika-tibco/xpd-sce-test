/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create Case Operation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getFromFieldPath <em>From Field Path</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getToCaseRefField <em>To Case Ref Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCreateCaseOperationType()
 * @model extendedMetaData="name='Create_._type' kind='empty'"
 * @generated
 */
public interface CreateCaseOperationType extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>From Field Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Field or javascript child content path e.g. myField.child etc
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>From Field Path</em>' attribute.
	 * @see #setFromFieldPath(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCreateCaseOperationType_FromFieldPath()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='FromFieldPath'"
	 * @generated
	 */
	String getFromFieldPath();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getFromFieldPath <em>From Field Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Field Path</em>' attribute.
	 * @see #getFromFieldPath()
	 * @generated
	 */
	void setFromFieldPath(String value);

	/**
	 * Returns the value of the '<em><b>To Case Ref Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If FromClassField is an array then ToClassRefField must also be an array (and visa versa)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>To Case Ref Field</em>' attribute.
	 * @see #setToCaseRefField(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCreateCaseOperationType_ToCaseRefField()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='ToCaseRefField'"
	 * @generated
	 */
	String getToCaseRefField();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CreateCaseOperationType#getToCaseRefField <em>To Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Case Ref Field</em>' attribute.
	 * @see #getToCaseRefField()
	 * @generated
	 */
	void setToCaseRefField(String value);

} // CreateCaseOperationType
