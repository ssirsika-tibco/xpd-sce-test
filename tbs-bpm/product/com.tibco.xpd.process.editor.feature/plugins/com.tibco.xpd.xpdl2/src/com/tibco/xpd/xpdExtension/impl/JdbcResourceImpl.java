/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Jdbc Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.JdbcResourceImpl#getJdbcProfileName <em>Jdbc Profile Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JdbcResourceImpl extends EObjectImpl implements JdbcResource
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected static final String	INSTANCE_NAME_EDEFAULT		= null;

	/**
	 * The cached value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected String				instanceName				= INSTANCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getJdbcProfileName() <em>Jdbc Profile Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcProfileName()
	 * @generated
	 * @ordered
	 */
	protected static final String	JDBC_PROFILE_NAME_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getJdbcProfileName() <em>Jdbc Profile Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcProfileName()
	 * @generated
	 * @ordered
	 */
	protected String				jdbcProfileName				= JDBC_PROFILE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JdbcResourceImpl()
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
		return XpdExtensionPackage.Literals.JDBC_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceName()
	{
		return instanceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceName(String newInstanceName)
	{
		String oldInstanceName = instanceName;
		instanceName = newInstanceName;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME, oldInstanceName, instanceName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJdbcProfileName()
	{
		return jdbcProfileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJdbcProfileName(String newJdbcProfileName)
	{
		String oldJdbcProfileName = jdbcProfileName;
		jdbcProfileName = newJdbcProfileName;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME, oldJdbcProfileName, jdbcProfileName));
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
			case XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME:
				return getInstanceName();
			case XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME:
				return getJdbcProfileName();
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
			case XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME:
				setInstanceName((String) newValue);
				return;
			case XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME:
				setJdbcProfileName((String) newValue);
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
			case XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME:
				setInstanceName(INSTANCE_NAME_EDEFAULT);
				return;
			case XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME:
				setJdbcProfileName(JDBC_PROFILE_NAME_EDEFAULT);
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
			case XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME:
				return INSTANCE_NAME_EDEFAULT == null ? instanceName != null
						: !INSTANCE_NAME_EDEFAULT.equals(instanceName);
			case XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME:
				return JDBC_PROFILE_NAME_EDEFAULT == null ? jdbcProfileName != null
						: !JDBC_PROFILE_NAME_EDEFAULT.equals(jdbcProfileName);
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
		result.append(" (instanceName: "); //$NON-NLS-1$
		result.append(instanceName);
		result.append(", jdbcProfileName: "); //$NON-NLS-1$
		result.append(jdbcProfileName);
		result.append(')');
		return result.toString();
	}

} //JdbcResourceImpl
