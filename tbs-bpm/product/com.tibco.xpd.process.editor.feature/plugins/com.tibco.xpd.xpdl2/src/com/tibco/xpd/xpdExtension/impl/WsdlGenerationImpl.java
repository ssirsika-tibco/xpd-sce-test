/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.BindingType;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wsdl Generation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsdlGenerationImpl#getSoapBindingStyle <em>Soap Binding Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsdlGenerationImpl extends EObjectImpl implements WsdlGeneration
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getSoapBindingStyle() <em>Soap Binding Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSoapBindingStyle()
	 * @generated
	 * @ordered
	 */
	protected static final SoapBindingStyle	SOAP_BINDING_STYLE_EDEFAULT	= SoapBindingStyle.RPC_LITERAL;

	/**
	 * The cached value of the '{@link #getSoapBindingStyle() <em>Soap Binding Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSoapBindingStyle()
	 * @generated
	 * @ordered
	 */
	protected SoapBindingStyle				soapBindingStyle			= SOAP_BINDING_STYLE_EDEFAULT;

	/**
	 * This is true if the Soap Binding Style attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						soapBindingStyleESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WsdlGenerationImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.WSDL_GENERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoapBindingStyle getSoapBindingStyle()
	{
		return soapBindingStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSoapBindingStyle(SoapBindingStyle newSoapBindingStyle)
	{
		SoapBindingStyle oldSoapBindingStyle = soapBindingStyle;
		soapBindingStyle = newSoapBindingStyle == null ? SOAP_BINDING_STYLE_EDEFAULT : newSoapBindingStyle;
		boolean oldSoapBindingStyleESet = soapBindingStyleESet;
		soapBindingStyleESet = true;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE,
						oldSoapBindingStyle, soapBindingStyle, !oldSoapBindingStyleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSoapBindingStyle()
	{
		SoapBindingStyle oldSoapBindingStyle = soapBindingStyle;
		boolean oldSoapBindingStyleESet = soapBindingStyleESet;
		soapBindingStyle = SOAP_BINDING_STYLE_EDEFAULT;
		soapBindingStyleESet = false;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE,
						oldSoapBindingStyle, SOAP_BINDING_STYLE_EDEFAULT, oldSoapBindingStyleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSoapBindingStyle()
	{
		return soapBindingStyleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE:
				return getSoapBindingStyle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE:
				setSoapBindingStyle((SoapBindingStyle) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE:
				unsetSoapBindingStyle();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WSDL_GENERATION__SOAP_BINDING_STYLE:
				return isSetSoapBindingStyle();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (soapBindingStyle: "); //$NON-NLS-1$
		if (soapBindingStyleESet) result.append(soapBindingStyle);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //WsdlGenerationImpl
