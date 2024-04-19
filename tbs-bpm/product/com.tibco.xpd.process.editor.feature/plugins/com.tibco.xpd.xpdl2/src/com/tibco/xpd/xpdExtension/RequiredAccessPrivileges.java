/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Access Privileges</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Configuration of one or more organisation model privileges used on a resource at run-time
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RequiredAccessPrivileges#getPrivilegeReference <em>Privilege Reference</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRequiredAccessPrivileges()
 * @model extendedMetaData="name='RequiredAccessPrivileges' kind='elementOnly'"
 * @generated
 */
public interface RequiredAccessPrivileges extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Privilege Reference</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdl2.ExternalReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Privilege Reference</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Privilege Reference</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRequiredAccessPrivileges_PrivilegeReference()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='PrivilegeReference' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ExternalReference> getPrivilegeReference();

} // RequiredAccessPrivileges
