/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Implemented Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This attribute is to store the process designers intent to 
 * have this processes content in-lined into calling processes in
 * the execution environment.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getPackageRef <em>Package Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getProcessInterfaceId <em>Process Interface Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getImplementedInterface()
 * @model extendedMetaData="name='ImplementedInterface' kind='elementOnly'"
 * @generated
 */
public interface ImplementedInterface extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Package Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This stores the package reference to which the implemented interface 
	 * is associated.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Package Ref</em>' attribute.
	 * @see #setPackageRef(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getImplementedInterface_PackageRef()
	 * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString"
	 *        extendedMetaData="kind='attribute' name='PackageRef' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPackageRef();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getPackageRef <em>Package Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Ref</em>' attribute.
	 * @see #getPackageRef()
	 * @generated
	 */
	void setPackageRef(String value);

	/**
	 * Returns the value of the '<em><b>Process Interface Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Stores the Process Interface Id.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Process Interface Id</em>' attribute.
	 * @see #setProcessInterfaceId(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getImplementedInterface_ProcessInterfaceId()
	 * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
	 *        extendedMetaData="kind='attribute' name='ProcessInterfaceId' namespace='##targetNamespace'"
	 * @generated
	 */
	String getProcessInterfaceId();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ImplementedInterface#getProcessInterfaceId <em>Process Interface Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Interface Id</em>' attribute.
	 * @see #getProcessInterfaceId()
	 * @generated
	 */
	void setProcessInterfaceId(String value);

} // ImplementedInterface