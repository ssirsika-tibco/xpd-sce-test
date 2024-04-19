/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.PilingInfo;
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

import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.WorkItemPriority;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Resource Patterns</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl#getSeparationOfDutiesActivities <em>Separation Of Duties Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessResourcePatternsImpl#getRetainFamiliarActivities <em>Retain Familiar Activities</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessResourcePatternsImpl extends EObjectImpl implements ProcessResourcePatterns
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getSeparationOfDutiesActivities() <em>Separation Of Duties Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeparationOfDutiesActivities()
	 * @generated
	 * @ordered
	 */
	protected EList<SeparationOfDutiesActivities>	separationOfDutiesActivities;

	/**
	 * The cached value of the '{@link #getRetainFamiliarActivities() <em>Retain Familiar Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetainFamiliarActivities()
	 * @generated
	 * @ordered
	 */
	protected EList<RetainFamiliarActivities>		retainFamiliarActivities;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessResourcePatternsImpl()
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
		return XpdExtensionPackage.Literals.PROCESS_RESOURCE_PATTERNS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SeparationOfDutiesActivities> getSeparationOfDutiesActivities()
	{
		if (separationOfDutiesActivities == null)
		{
			separationOfDutiesActivities = new EObjectContainmentEList<SeparationOfDutiesActivities>(
					SeparationOfDutiesActivities.class, this,
					XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES);
		}
		return separationOfDutiesActivities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RetainFamiliarActivities> getRetainFamiliarActivities()
	{
		if (retainFamiliarActivities == null)
		{
			retainFamiliarActivities = new EObjectContainmentEList<RetainFamiliarActivities>(
					RetainFamiliarActivities.class, this,
					XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES);
		}
		return retainFamiliarActivities;
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
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES:
				return ((InternalEList< ? >) getSeparationOfDutiesActivities()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES:
				return ((InternalEList< ? >) getRetainFamiliarActivities()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES:
				return getSeparationOfDutiesActivities();
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES:
				return getRetainFamiliarActivities();
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
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES:
				getSeparationOfDutiesActivities().clear();
				getSeparationOfDutiesActivities()
						.addAll((Collection< ? extends SeparationOfDutiesActivities>) newValue);
				return;
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES:
				getRetainFamiliarActivities().clear();
				getRetainFamiliarActivities().addAll((Collection< ? extends RetainFamiliarActivities>) newValue);
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
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES:
				getSeparationOfDutiesActivities().clear();
				return;
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES:
				getRetainFamiliarActivities().clear();
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
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__SEPARATION_OF_DUTIES_ACTIVITIES:
				return separationOfDutiesActivities != null && !separationOfDutiesActivities.isEmpty();
			case XpdExtensionPackage.PROCESS_RESOURCE_PATTERNS__RETAIN_FAMILIAR_ACTIVITIES:
				return retainFamiliarActivities != null && !retainFamiliarActivities.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProcessResourcePatternsImpl
