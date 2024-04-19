/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Priority</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WorkItemPriorityImpl#getInitialPriority <em>Initial Priority</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WorkItemPriorityImpl extends EObjectImpl implements WorkItemPriority
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getInitialPriority() <em>Initial Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPriority()
	 * @generated
	 * @ordered
	 */
	protected static final String	INITIAL_PRIORITY_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getInitialPriority() <em>Initial Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPriority()
	 * @generated
	 * @ordered
	 */
	protected String				initialPriority				= INITIAL_PRIORITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkItemPriorityImpl()
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
		return XpdExtensionPackage.Literals.WORK_ITEM_PRIORITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitialPriority()
	{
		return initialPriority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialPriority(String newInitialPriority)
	{
		String oldInitialPriority = initialPriority;
		initialPriority = newInitialPriority;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.WORK_ITEM_PRIORITY__INITIAL_PRIORITY, oldInitialPriority, initialPriority));
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
			case XpdExtensionPackage.WORK_ITEM_PRIORITY__INITIAL_PRIORITY:
				return getInitialPriority();
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
			case XpdExtensionPackage.WORK_ITEM_PRIORITY__INITIAL_PRIORITY:
				setInitialPriority((String) newValue);
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
			case XpdExtensionPackage.WORK_ITEM_PRIORITY__INITIAL_PRIORITY:
				setInitialPriority(INITIAL_PRIORITY_EDEFAULT);
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
			case XpdExtensionPackage.WORK_ITEM_PRIORITY__INITIAL_PRIORITY:
				return INITIAL_PRIORITY_EDEFAULT == null ? initialPriority != null
						: !INITIAL_PRIORITY_EDEFAULT.equals(initialPriority);
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
		result.append(" (initialPriority: "); //$NON-NLS-1$
		result.append(initialPriority);
		result.append(')');
		return result.toString();
	}

} //WorkItemPriorityImpl
