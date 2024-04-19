/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Ws Soap Security</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapSecurityImpl#getSecurityPolicy <em>Security Policy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapSecurityImpl extends EObjectImpl implements WsSoapSecurity
{
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getSecurityPolicy()
	 * <em>Security Policy</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSecurityPolicy()
	 * @generated
	 * @ordered
	 */
	protected EList<WsSecurityPolicy>	securityPolicy;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected WsSoapSecurityImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.WS_SOAP_SECURITY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<WsSecurityPolicy> getSecurityPolicy()
	{
		if (securityPolicy == null)
		{
			securityPolicy = new EObjectContainmentEList<WsSecurityPolicy>(WsSecurityPolicy.class, this,
					XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY);
		}
		return securityPolicy;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY:
				return ((InternalEList< ? >) getSecurityPolicy()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY:
				return getSecurityPolicy();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY:
				getSecurityPolicy().clear();
				getSecurityPolicy().addAll((Collection< ? extends WsSecurityPolicy>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY:
				getSecurityPolicy().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_SOAP_SECURITY__SECURITY_POLICY:
				return securityPolicy != null && !securityPolicy.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // WsSoapSecurityImpl
