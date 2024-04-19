/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.DataMapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Catch Error Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added to the xpdl:ResultError element of an intermediate
 * catch error event to specify the data mappings to the data output with the 
 * caught error.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CatchErrorMappings#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCatchErrorMappings()
 * @model extendedMetaData="name='CatchErrorMappings' kind='elementOnly'"
 * @generated
 */
public interface CatchErrorMappings extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' containment reference.
	 * @see #setMessage(Message)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCatchErrorMappings_Message()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Message' namespace='##targetNamespace'"
	 * @generated
	 */
	Message getMessage();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CatchErrorMappings#getMessage <em>Message</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' containment reference.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(Message value);

} // CatchErrorMappings
