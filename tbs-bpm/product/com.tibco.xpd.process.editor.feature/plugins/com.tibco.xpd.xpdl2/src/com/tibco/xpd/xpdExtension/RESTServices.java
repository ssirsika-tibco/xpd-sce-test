/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.OtherElementsContainer;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>REST Services</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The REST services for a Business Process (stored as a pageflow process)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.RESTServices#getRESTServices <em>REST Services</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRESTServices()
 * @model extendedMetaData="name='RESTServices'"
 * @generated
 */
public interface RESTServices extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>REST Services</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.xpd.xpdl2.Process}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>REST Services</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>REST Services</em>' containment reference list.
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getRESTServices_RESTServices()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='RESTService' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<com.tibco.xpd.xpdl2.Process> getRESTServices();

} // RESTServices
