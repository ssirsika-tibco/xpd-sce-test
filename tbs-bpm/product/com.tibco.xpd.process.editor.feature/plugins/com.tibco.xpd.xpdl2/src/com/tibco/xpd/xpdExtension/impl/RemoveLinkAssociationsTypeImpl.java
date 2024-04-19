/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove Link Associations Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl#getAssociationName <em>Association Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RemoveLinkAssociationsTypeImpl#getRemoveCaseRefField <em>Remove Case Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RemoveLinkAssociationsTypeImpl extends EObjectImpl implements RemoveLinkAssociationsType
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright						= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getAssociationName() <em>Association Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationName()
	 * @generated
	 * @ordered
	 */
	protected static final String	ASSOCIATION_NAME_EDEFAULT		= null;

	/**
	 * The cached value of the '{@link #getAssociationName() <em>Association Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationName()
	 * @generated
	 * @ordered
	 */
	protected String				associationName					= ASSOCIATION_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemoveCaseRefField() <em>Remove Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoveCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected static final String	REMOVE_CASE_REF_FIELD_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getRemoveCaseRefField() <em>Remove Case Ref Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoveCaseRefField()
	 * @generated
	 * @ordered
	 */
	protected String				removeCaseRefField				= REMOVE_CASE_REF_FIELD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveLinkAssociationsTypeImpl()
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
		return XpdExtensionPackage.Literals.REMOVE_LINK_ASSOCIATIONS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAssociationName()
	{
		return associationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssociationName(String newAssociationName)
	{
		String oldAssociationName = associationName;
		associationName = newAssociationName;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME, oldAssociationName,
				associationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRemoveCaseRefField()
	{
		return removeCaseRefField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoveCaseRefField(String newRemoveCaseRefField)
	{
		String oldRemoveCaseRefField = removeCaseRefField;
		removeCaseRefField = newRemoveCaseRefField;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD, oldRemoveCaseRefField,
				removeCaseRefField));
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
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
				return getAssociationName();
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD:
				return getRemoveCaseRefField();
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
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
				setAssociationName((String) newValue);
				return;
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD:
				setRemoveCaseRefField((String) newValue);
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
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
				setAssociationName(ASSOCIATION_NAME_EDEFAULT);
				return;
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD:
				setRemoveCaseRefField(REMOVE_CASE_REF_FIELD_EDEFAULT);
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
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__ASSOCIATION_NAME:
				return ASSOCIATION_NAME_EDEFAULT == null ? associationName != null
						: !ASSOCIATION_NAME_EDEFAULT.equals(associationName);
			case XpdExtensionPackage.REMOVE_LINK_ASSOCIATIONS_TYPE__REMOVE_CASE_REF_FIELD:
				return REMOVE_CASE_REF_FIELD_EDEFAULT == null ? removeCaseRefField != null
						: !REMOVE_CASE_REF_FIELD_EDEFAULT.equals(removeCaseRefField);
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
		result.append(" (associationName: "); //$NON-NLS-1$
		result.append(associationName);
		result.append(", removeCaseRefField: "); //$NON-NLS-1$
		result.append(removeCaseRefField);
		result.append(')');
		return result.toString();
	}

} //RemoveLinkAssociationsTypeImpl
