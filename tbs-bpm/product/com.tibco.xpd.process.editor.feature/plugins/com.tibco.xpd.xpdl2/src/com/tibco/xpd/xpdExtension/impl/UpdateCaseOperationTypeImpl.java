/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Case Operation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.UpdateCaseOperationTypeImpl#getFromFieldPath <em>From Field Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UpdateCaseOperationTypeImpl extends EObjectImpl implements UpdateCaseOperationType
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getFromFieldPath() <em>From Field Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromFieldPath()
	 * @generated
	 * @ordered
	 */
	protected static final String	FROM_FIELD_PATH_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getFromFieldPath() <em>From Field Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromFieldPath()
	 * @generated
	 * @ordered
	 */
	protected String				fromFieldPath				= FROM_FIELD_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UpdateCaseOperationTypeImpl()
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
		return XpdExtensionPackage.Literals.UPDATE_CASE_OPERATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFromFieldPath()
	{
		return fromFieldPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromFieldPath(String newFromFieldPath)
	{
		String oldFromFieldPath = fromFieldPath;
		fromFieldPath = newFromFieldPath;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH, oldFromFieldPath, fromFieldPath));
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
			case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
				return getFromFieldPath();
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
			case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
				setFromFieldPath((String) newValue);
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
			case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
				setFromFieldPath(FROM_FIELD_PATH_EDEFAULT);
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
			case XpdExtensionPackage.UPDATE_CASE_OPERATION_TYPE__FROM_FIELD_PATH:
				return FROM_FIELD_PATH_EDEFAULT == null ? fromFieldPath != null
						: !FROM_FIELD_PATH_EDEFAULT.equals(fromFieldPath);
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
		result.append(" (fromFieldPath: "); //$NON-NLS-1$
		result.append(fromFieldPath);
		result.append(')');
		return result.toString();
	}

} //UpdateCaseOperationTypeImpl
