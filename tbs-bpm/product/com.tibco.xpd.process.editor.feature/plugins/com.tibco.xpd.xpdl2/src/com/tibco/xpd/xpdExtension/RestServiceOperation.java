/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rest Service Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getMethodId <em>Method Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceOperation()
 * @model extendedMetaData="name='RestServiceOperation' kind='elementOnly'"
 * @generated
 */
public interface RestServiceOperation extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceOperation_Location()
	 * @model extendedMetaData="kind='attribute' name='Location' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Method Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Id</em>' attribute.
	 * @see #setMethodId(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRestServiceOperation_MethodId()
	 * @model extendedMetaData="kind='attribute' name='MethodId' namespace='##targetNamespace'"
	 * @generated
	 */
	String getMethodId();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.RestServiceOperation#getMethodId <em>Method Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Id</em>' attribute.
	 * @see #getMethodId()
	 * @generated
	 */
	void setMethodId(String value);

} // RestServiceOperation
