/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.ExternalReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port Type Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * PortTypeOperation defines the web service port type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getPortTypeName <em>Port Type Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getOperationName <em>Operation Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getTransport <em>Transport</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPortTypeOperation()
 * @model extendedMetaData="kind='elementOnly' name='PortTypeOperation'"
 * @generated
 */
public interface PortTypeOperation extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Port Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port Type Name</em>' attribute.
	 * @see #setPortTypeName(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPortTypeOperation_PortTypeName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='PortTypeName'"
	 * @generated
	 */
	String getPortTypeName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getPortTypeName <em>Port Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port Type Name</em>' attribute.
	 * @see #getPortTypeName()
	 * @generated
	 */
	void setPortTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Operation Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Name</em>' attribute.
	 * @see #setOperationName(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPortTypeOperation_OperationName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='OperationName'"
	 * @generated
	 */
	String getOperationName();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getOperationName <em>Operation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Name</em>' attribute.
	 * @see #getOperationName()
	 * @generated
	 */
	void setOperationName(String value);

	/**
	 * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Reference</em>' containment reference.
	 * @see #setExternalReference(ExternalReference)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPortTypeOperation_ExternalReference()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
	 * @generated
	 */
	ExternalReference getExternalReference();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getExternalReference <em>External Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Reference</em>' containment reference.
	 * @see #getExternalReference()
	 * @generated
	 */
	void setExternalReference(ExternalReference value);

	/**
	 * Returns the value of the '<em><b>Transport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transport</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transport</em>' attribute.
	 * @see #setTransport(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getPortTypeOperation_Transport()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Transport'"
	 * @generated
	 */
	String getTransport();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.PortTypeOperation#getTransport <em>Transport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transport</em>' attribute.
	 * @see #getTransport()
	 * @generated
	 */
	void setTransport(String value);

} // PortTypeOperation
