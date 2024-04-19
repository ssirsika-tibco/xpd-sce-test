/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ws Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsResourceImpl#getInbound <em>Inbound</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsResourceImpl#getOutbound <em>Outbound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsResourceImpl extends EObjectImpl implements WsResource
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String	copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInbound() <em>Inbound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInbound()
	 * @generated
	 * @ordered
	 */
	protected WsInbound			inbound;

	/**
	 * The cached value of the '{@link #getOutbound() <em>Outbound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutbound()
	 * @generated
	 * @ordered
	 */
	protected WsOutbound		outbound;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WsResourceImpl()
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
		return XpdExtensionPackage.Literals.WS_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WsInbound getInbound()
	{
		return inbound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInbound(WsInbound newInbound, NotificationChain msgs)
	{
		WsInbound oldInbound = inbound;
		inbound = newInbound;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.WS_RESOURCE__INBOUND, oldInbound, newInbound);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInbound(WsInbound newInbound)
	{
		if (newInbound != inbound)
		{
			NotificationChain msgs = null;
			if (inbound != null) msgs = ((InternalEObject) inbound).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_RESOURCE__INBOUND, null, msgs);
			if (newInbound != null) msgs = ((InternalEObject) newInbound).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_RESOURCE__INBOUND, null, msgs);
			msgs = basicSetInbound(newInbound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.WS_RESOURCE__INBOUND, newInbound, newInbound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WsOutbound getOutbound()
	{
		return outbound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutbound(WsOutbound newOutbound, NotificationChain msgs)
	{
		WsOutbound oldOutbound = outbound;
		outbound = newOutbound;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.WS_RESOURCE__OUTBOUND, oldOutbound, newOutbound);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutbound(WsOutbound newOutbound)
	{
		if (newOutbound != outbound)
		{
			NotificationChain msgs = null;
			if (outbound != null) msgs = ((InternalEObject) outbound).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_RESOURCE__OUTBOUND, null, msgs);
			if (newOutbound != null) msgs = ((InternalEObject) newOutbound).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.WS_RESOURCE__OUTBOUND, null, msgs);
			msgs = basicSetOutbound(newOutbound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.WS_RESOURCE__OUTBOUND, newOutbound, newOutbound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.WS_RESOURCE__INBOUND:
				return basicSetInbound(null, msgs);
			case XpdExtensionPackage.WS_RESOURCE__OUTBOUND:
				return basicSetOutbound(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case XpdExtensionPackage.WS_RESOURCE__INBOUND:
				return getInbound();
			case XpdExtensionPackage.WS_RESOURCE__OUTBOUND:
				return getOutbound();
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
			case XpdExtensionPackage.WS_RESOURCE__INBOUND:
				setInbound((WsInbound) newValue);
				return;
			case XpdExtensionPackage.WS_RESOURCE__OUTBOUND:
				setOutbound((WsOutbound) newValue);
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
			case XpdExtensionPackage.WS_RESOURCE__INBOUND:
				setInbound((WsInbound) null);
				return;
			case XpdExtensionPackage.WS_RESOURCE__OUTBOUND:
				setOutbound((WsOutbound) null);
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
			case XpdExtensionPackage.WS_RESOURCE__INBOUND:
				return inbound != null;
			case XpdExtensionPackage.WS_RESOURCE__OUTBOUND:
				return outbound != null;
		}
		return super.eIsSet(featureID);
	}

} //WsResourceImpl
