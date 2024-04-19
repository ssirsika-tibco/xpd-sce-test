/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wsdl Generation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Extension element associated with process interfaces to say how the WSDL was being generated (what soap binding style to be used). Deprecated.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle <em>Soap Binding Style</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsdlGeneration()
 * @model extendedMetaData="name='WsdlGeneration' kind='elementOnly'"
 * @generated
 */
public interface WsdlGeneration extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Soap Binding Style</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.SoapBindingStyle}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This attribute stores the binding type of the WSDL generated.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Soap Binding Style</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
	 * @see #isSetSoapBindingStyle()
	 * @see #unsetSoapBindingStyle()
	 * @see #setSoapBindingStyle(SoapBindingStyle)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getWsdlGeneration_SoapBindingStyle()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='SoapBindingStyle'"
	 * @generated
	 */
	SoapBindingStyle getSoapBindingStyle();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle <em>Soap Binding Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Soap Binding Style</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.SoapBindingStyle
	 * @see #isSetSoapBindingStyle()
	 * @see #unsetSoapBindingStyle()
	 * @see #getSoapBindingStyle()
	 * @generated
	 */
	void setSoapBindingStyle(SoapBindingStyle value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle <em>Soap Binding Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSoapBindingStyle()
	 * @see #getSoapBindingStyle()
	 * @see #setSoapBindingStyle(SoapBindingStyle)
	 * @generated
	 */
	void unsetSoapBindingStyle();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.WsdlGeneration#getSoapBindingStyle <em>Soap Binding Style</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Soap Binding Style</em>' attribute is set.
	 * @see #unsetSoapBindingStyle()
	 * @see #getSoapBindingStyle()
	 * @see #setSoapBindingStyle(SoapBindingStyle)
	 * @generated
	 */
	boolean isSetSoapBindingStyle();

} // WsdlGeneration
