/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.NamespaceMapEntry;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Namespace Prefix Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl#getNamespaceEntries <em>Namespace Entries</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.NamespacePrefixMapImpl#isPrefixMappingDisabled <em>Prefix Mapping Disabled</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamespacePrefixMapImpl extends EObjectImpl implements NamespacePrefixMap
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright							= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getNamespaceEntries() <em>Namespace Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<NamespaceMapEntry>	namespaceEntries;

	/**
	 * The default value of the '{@link #isPrefixMappingDisabled() <em>Prefix Mapping Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrefixMappingDisabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean		PREFIX_MAPPING_DISABLED_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isPrefixMappingDisabled() <em>Prefix Mapping Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrefixMappingDisabled()
	 * @generated
	 * @ordered
	 */
	protected boolean					prefixMappingDisabled				= PREFIX_MAPPING_DISABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamespacePrefixMapImpl()
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
		return XpdExtensionPackage.Literals.NAMESPACE_PREFIX_MAP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NamespaceMapEntry> getNamespaceEntries()
	{
		if (namespaceEntries == null)
		{
			namespaceEntries = new EObjectContainmentEList<NamespaceMapEntry>(NamespaceMapEntry.class, this,
					XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES);
		}
		return namespaceEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPrefixMappingDisabled()
	{
		return prefixMappingDisabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrefixMappingDisabled(boolean newPrefixMappingDisabled)
	{
		boolean oldPrefixMappingDisabled = prefixMappingDisabled;
		prefixMappingDisabled = newPrefixMappingDisabled;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED, oldPrefixMappingDisabled,
				prefixMappingDisabled));
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
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES:
				return ((InternalEList< ? >) getNamespaceEntries()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES:
				return getNamespaceEntries();
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED:
				return isPrefixMappingDisabled();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES:
				getNamespaceEntries().clear();
				getNamespaceEntries().addAll((Collection< ? extends NamespaceMapEntry>) newValue);
				return;
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED:
				setPrefixMappingDisabled((Boolean) newValue);
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
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES:
				getNamespaceEntries().clear();
				return;
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED:
				setPrefixMappingDisabled(PREFIX_MAPPING_DISABLED_EDEFAULT);
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
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__NAMESPACE_ENTRIES:
				return namespaceEntries != null && !namespaceEntries.isEmpty();
			case XpdExtensionPackage.NAMESPACE_PREFIX_MAP__PREFIX_MAPPING_DISABLED:
				return prefixMappingDisabled != PREFIX_MAPPING_DISABLED_EDEFAULT;
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
		result.append(" (PrefixMappingDisabled: "); //$NON-NLS-1$
		result.append(prefixMappingDisabled);
		result.append(')');
		return result.toString();
	}

} //NamespacePrefixMapImpl
