/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.TaskLibraryReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Library Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl#getTaskLibraryId <em>Task Library Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.TaskLibraryReferenceImpl#getPackageRef <em>Package Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskLibraryReferenceImpl extends EObjectImpl implements TaskLibraryReference
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getTaskLibraryId() <em>Task Library Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskLibraryId()
	 * @generated
	 * @ordered
	 */
	protected static final String	TASK_LIBRARY_ID_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getTaskLibraryId() <em>Task Library Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskLibraryId()
	 * @generated
	 * @ordered
	 */
	protected String				taskLibraryId				= TASK_LIBRARY_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageRef()
	 * @generated
	 * @ordered
	 */
	protected static final String	PACKAGE_REF_EDEFAULT		= null;

	/**
	 * The cached value of the '{@link #getPackageRef() <em>Package Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageRef()
	 * @generated
	 * @ordered
	 */
	protected String				packageRef					= PACKAGE_REF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskLibraryReferenceImpl()
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
		return XpdExtensionPackage.Literals.TASK_LIBRARY_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTaskLibraryId()
	{
		return taskLibraryId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTaskLibraryId(String newTaskLibraryId)
	{
		String oldTaskLibraryId = taskLibraryId;
		taskLibraryId = newTaskLibraryId;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID, oldTaskLibraryId, taskLibraryId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageRef()
	{
		return packageRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageRef(String newPackageRef)
	{
		String oldPackageRef = packageRef;
		packageRef = newPackageRef;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.TASK_LIBRARY_REFERENCE__PACKAGE_REF, oldPackageRef, packageRef));
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
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID:
				return getTaskLibraryId();
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__PACKAGE_REF:
				return getPackageRef();
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
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID:
				setTaskLibraryId((String) newValue);
				return;
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__PACKAGE_REF:
				setPackageRef((String) newValue);
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
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID:
				setTaskLibraryId(TASK_LIBRARY_ID_EDEFAULT);
				return;
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__PACKAGE_REF:
				setPackageRef(PACKAGE_REF_EDEFAULT);
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
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__TASK_LIBRARY_ID:
				return TASK_LIBRARY_ID_EDEFAULT == null ? taskLibraryId != null
						: !TASK_LIBRARY_ID_EDEFAULT.equals(taskLibraryId);
			case XpdExtensionPackage.TASK_LIBRARY_REFERENCE__PACKAGE_REF:
				return PACKAGE_REF_EDEFAULT == null ? packageRef != null : !PACKAGE_REF_EDEFAULT.equals(packageRef);
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
		result.append(" (taskLibraryId: "); //$NON-NLS-1$
		result.append(taskLibraryId);
		result.append(", packageRef: "); //$NON-NLS-1$
		result.append(packageRef);
		result.append(')');
		return result.toString();
	}

} //TaskLibraryReferenceImpl
