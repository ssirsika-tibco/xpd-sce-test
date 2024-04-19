/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Find By File Name Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.FindByFileNameOperation#getFileNameField <em>File Name Field</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFindByFileNameOperation()
 * @model extendedMetaData="name='FindByFileNameOperation' kind='empty'"
 * @generated
 */
public interface FindByFileNameOperation extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>File Name Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * File name to find.Type String.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>File Name Field</em>' attribute.
	 * @see #setFileNameField(String)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getFindByFileNameOperation_FileNameField()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='FileNameField'"
	 * @generated
	 */
	String getFileNameField();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.FindByFileNameOperation#getFileNameField <em>File Name Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name Field</em>' attribute.
	 * @see #getFileNameField()
	 * @generated
	 */
	void setFileNameField(String value);

} // FindByFileNameOperation
