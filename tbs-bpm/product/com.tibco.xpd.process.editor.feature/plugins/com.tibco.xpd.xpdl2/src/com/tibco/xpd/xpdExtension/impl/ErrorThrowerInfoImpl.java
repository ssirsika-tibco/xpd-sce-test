/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Message;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Thrower Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl#getThrowerId <em>Thrower Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl#getThrowerContainerId <em>Thrower Container Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorThrowerInfoImpl#getThrowerType <em>Thrower Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorThrowerInfoImpl extends EObjectImpl implements ErrorThrowerInfo
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright						= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getThrowerId() <em>Thrower Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerId()
	 * @generated
	 * @ordered
	 */
	protected static final String			THROWER_ID_EDEFAULT				= null;

	/**
	 * The cached value of the '{@link #getThrowerId() <em>Thrower Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerId()
	 * @generated
	 * @ordered
	 */
	protected String						throwerId						= THROWER_ID_EDEFAULT;

	/**
	 * This is true if the Thrower Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						throwerIdESet;

	/**
	 * The default value of the '{@link #getThrowerContainerId() <em>Thrower Container Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerContainerId()
	 * @generated
	 * @ordered
	 */
	protected static final String			THROWER_CONTAINER_ID_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getThrowerContainerId() <em>Thrower Container Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerContainerId()
	 * @generated
	 * @ordered
	 */
	protected String						throwerContainerId				= THROWER_CONTAINER_ID_EDEFAULT;

	/**
	 * This is true if the Thrower Container Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						throwerContainerIdESet;

	/**
	 * The default value of the '{@link #getThrowerType() <em>Thrower Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerType()
	 * @generated
	 * @ordered
	 */
	protected static final ErrorThrowerType	THROWER_TYPE_EDEFAULT			= ErrorThrowerType.PROCESS_ACTIVITY;

	/**
	 * The cached value of the '{@link #getThrowerType() <em>Thrower Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThrowerType()
	 * @generated
	 * @ordered
	 */
	protected ErrorThrowerType				throwerType						= THROWER_TYPE_EDEFAULT;

	/**
	 * This is true if the Thrower Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						throwerTypeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorThrowerInfoImpl()
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
		return XpdExtensionPackage.Literals.ERROR_THROWER_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThrowerId()
	{
		return throwerId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThrowerId(String newThrowerId)
	{
		String oldThrowerId = throwerId;
		throwerId = newThrowerId;
		boolean oldThrowerIdESet = throwerIdESet;
		throwerIdESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID, oldThrowerId, throwerId, !oldThrowerIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThrowerId()
	{
		String oldThrowerId = throwerId;
		boolean oldThrowerIdESet = throwerIdESet;
		throwerId = THROWER_ID_EDEFAULT;
		throwerIdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID,
					oldThrowerId, THROWER_ID_EDEFAULT, oldThrowerIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThrowerId()
	{
		return throwerIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getThrowerContainerId()
	{
		return throwerContainerId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThrowerContainerId(String newThrowerContainerId)
	{
		String oldThrowerContainerId = throwerContainerId;
		throwerContainerId = newThrowerContainerId;
		boolean oldThrowerContainerIdESet = throwerContainerIdESet;
		throwerContainerIdESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID, oldThrowerContainerId, throwerContainerId,
				!oldThrowerContainerIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThrowerContainerId()
	{
		String oldThrowerContainerId = throwerContainerId;
		boolean oldThrowerContainerIdESet = throwerContainerIdESet;
		throwerContainerId = THROWER_CONTAINER_ID_EDEFAULT;
		throwerContainerIdESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID, oldThrowerContainerId,
				THROWER_CONTAINER_ID_EDEFAULT, oldThrowerContainerIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThrowerContainerId()
	{
		return throwerContainerIdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorThrowerType getThrowerType()
	{
		return throwerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThrowerType(ErrorThrowerType newThrowerType)
	{
		ErrorThrowerType oldThrowerType = throwerType;
		throwerType = newThrowerType == null ? THROWER_TYPE_EDEFAULT : newThrowerType;
		boolean oldThrowerTypeESet = throwerTypeESet;
		throwerTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE,
					oldThrowerType, throwerType, !oldThrowerTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThrowerType()
	{
		ErrorThrowerType oldThrowerType = throwerType;
		boolean oldThrowerTypeESet = throwerTypeESet;
		throwerType = THROWER_TYPE_EDEFAULT;
		throwerTypeESet = false;
		if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE,
						oldThrowerType, THROWER_TYPE_EDEFAULT, oldThrowerTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThrowerType()
	{
		return throwerTypeESet;
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
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID:
				return getThrowerId();
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID:
				return getThrowerContainerId();
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE:
				return getThrowerType();
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
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID:
				setThrowerId((String) newValue);
				return;
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID:
				setThrowerContainerId((String) newValue);
				return;
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE:
				setThrowerType((ErrorThrowerType) newValue);
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
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID:
				unsetThrowerId();
				return;
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID:
				unsetThrowerContainerId();
				return;
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE:
				unsetThrowerType();
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
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_ID:
				return isSetThrowerId();
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_CONTAINER_ID:
				return isSetThrowerContainerId();
			case XpdExtensionPackage.ERROR_THROWER_INFO__THROWER_TYPE:
				return isSetThrowerType();
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
		result.append(" (throwerId: "); //$NON-NLS-1$
		if (throwerIdESet) result.append(throwerId);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", throwerContainerId: "); //$NON-NLS-1$
		if (throwerContainerIdESet) result.append(throwerContainerId);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", throwerType: "); //$NON-NLS-1$
		if (throwerTypeESet) result.append(throwerType);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //ErrorThrowerInfoImpl
