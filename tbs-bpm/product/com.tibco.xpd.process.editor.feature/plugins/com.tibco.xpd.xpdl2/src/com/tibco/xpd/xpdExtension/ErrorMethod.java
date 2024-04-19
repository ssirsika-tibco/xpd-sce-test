/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ErrorMethod#getErrorCode <em>Error Code</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorMethod()
 * @model extendedMetaData="name='ErrorMethod' kind='element'"
 * @generated
 */
public interface ErrorMethod extends UniqueIdElement, AssociatedParametersContainer
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Error Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error Code</em>' attribute.
	 * @see #setErrorCode(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getErrorMethod_ErrorCode()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='ErrorCode'"
	 * @generated
	 */
	String getErrorCode();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ErrorMethod#getErrorCode <em>Error Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error Code</em>' attribute.
	 * @see #getErrorCode()
	 * @generated
	 */
	void setErrorCode(String value);

} // ErrorMethod
