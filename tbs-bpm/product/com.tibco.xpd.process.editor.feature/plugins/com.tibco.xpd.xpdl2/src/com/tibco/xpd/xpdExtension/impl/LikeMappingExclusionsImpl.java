/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Like Mapping Exclusions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.LikeMappingExclusionsImpl#getExclusions <em>Exclusions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LikeMappingExclusionsImpl extends EObjectImpl implements LikeMappingExclusions
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getExclusions() <em>Exclusions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExclusions()
	 * @generated
	 * @ordered
	 */
	protected EList<LikeMappingExclusion>	exclusions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LikeMappingExclusionsImpl()
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
		return XpdExtensionPackage.Literals.LIKE_MAPPING_EXCLUSIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LikeMappingExclusion> getExclusions()
	{
		if (exclusions == null)
		{
			exclusions = new EObjectContainmentEList<LikeMappingExclusion>(LikeMappingExclusion.class, this,
					XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS);
		}
		return exclusions;
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
			case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS:
				return ((InternalEList< ? >) getExclusions()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS:
				return getExclusions();
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
			case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS:
				getExclusions().clear();
				getExclusions().addAll((Collection< ? extends LikeMappingExclusion>) newValue);
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
			case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS:
				getExclusions().clear();
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
			case XpdExtensionPackage.LIKE_MAPPING_EXCLUSIONS__EXCLUSIONS:
				return exclusions != null && !exclusions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LikeMappingExclusionsImpl
