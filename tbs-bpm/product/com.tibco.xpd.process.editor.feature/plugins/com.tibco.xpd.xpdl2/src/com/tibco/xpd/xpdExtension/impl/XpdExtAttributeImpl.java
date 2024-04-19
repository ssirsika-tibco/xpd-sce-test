/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xpd Ext Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtAttributeImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XpdExtAttributeImpl extends EObjectImpl implements XpdExtAttribute
{

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright		= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap			mixed;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap			group;

	/**
	 * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAny()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap			any;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String	NAME_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String				name			= NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String	VALUE_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String				value			= VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XpdExtAttributeImpl()
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
		return XpdExtensionPackage.Literals.XPD_EXT_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed()
	{
		if (mixed == null)
		{
			mixed = new BasicFeatureMap(this, XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup()
	{
		if (group == null)
		{
			group = new BasicFeatureMap(this, XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAny()
	{
		if (any == null)
		{
			any = new BasicFeatureMap(this, XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY);
		}
		return any;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.XPD_EXT_ATTRIBUTE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue)
	{
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.XPD_EXT_ATTRIBUTE__VALUE, oldValue, value));
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
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED:
				return ((InternalEList< ? >) getMixed()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP:
				return ((InternalEList< ? >) getGroup()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY:
				return ((InternalEList< ? >) getAny()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal) getMixed()).getWrapper();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal) getGroup()).getWrapper();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY:
				if (coreType) return getAny();
				return ((FeatureMap.Internal) getAny()).getWrapper();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__NAME:
				return getName();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__VALUE:
				return getValue();
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
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED:
				((FeatureMap.Internal) getMixed()).set(newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP:
				((FeatureMap.Internal) getGroup()).set(newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY:
				((FeatureMap.Internal) getAny()).set(newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__NAME:
				setName((String) newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__VALUE:
				setValue((String) newValue);
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
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED:
				getMixed().clear();
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP:
				getGroup().clear();
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY:
				getAny().clear();
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__GROUP:
				return group != null && !group.isEmpty();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__ANY:
				return any != null && !any.isEmpty();
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case XpdExtensionPackage.XPD_EXT_ATTRIBUTE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (mixed: "); //$NON-NLS-1$
		result.append(mixed);
		result.append(", group: "); //$NON-NLS-1$
		result.append(group);
		result.append(", any: "); //$NON-NLS-1$
		result.append(any);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //XpdExtAttributeImpl