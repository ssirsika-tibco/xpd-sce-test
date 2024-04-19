/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.NamespaceMapEntry;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace Map Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl#getPrefix <em>Prefix</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.NamespaceMapEntryImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamespaceMapEntryImpl extends EObjectImpl implements NamespaceMapEntry
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright			= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String	PREFIX_EDEFAULT		= null;																	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected String				prefix				= PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String	NAMESPACE_EDEFAULT	= null;																	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String				namespace			= NAMESPACE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamespaceMapEntryImpl()
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
		return XpdExtensionPackage.Literals.NAMESPACE_MAP_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrefix()
	{
		return prefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrefix(String newPrefix)
	{
		String oldPrefix = prefix;
		prefix = newPrefix;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.NAMESPACE_MAP_ENTRY__PREFIX, oldPrefix, prefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(String newNamespace)
	{
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.NAMESPACE_MAP_ENTRY__NAMESPACE, oldNamespace, namespace));
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
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__PREFIX:
				return getPrefix();
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__NAMESPACE:
				return getNamespace();
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
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__PREFIX:
				setPrefix((String) newValue);
				return;
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__NAMESPACE:
				setNamespace((String) newValue);
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
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__PREFIX:
				setPrefix(PREFIX_EDEFAULT);
				return;
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
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
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__PREFIX:
				return PREFIX_EDEFAULT == null ? prefix != null : !PREFIX_EDEFAULT.equals(prefix);
			case XpdExtensionPackage.NAMESPACE_MAP_ENTRY__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
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
		result.append(" (Prefix: "); //$NON-NLS-1$
		result.append(prefix);
		result.append(", Namespace: "); //$NON-NLS-1$
		result.append(namespace);
		result.append(')');
		return result.toString();
	}

} //NamespaceMapEntryImpl
