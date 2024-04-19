/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExternalReference;

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
 * An implementation of the model object '<em><b>Visible For Case States</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl#isVisibleForUnsetCaseState <em>Visible For Unset Case State</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.VisibleForCaseStatesImpl#getCaseState <em>Case State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VisibleForCaseStatesImpl extends EObjectImpl implements VisibleForCaseStates
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright								= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #isVisibleForUnsetCaseState() <em>Visible For Unset Case State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleForUnsetCaseState()
	 * @generated
	 * @ordered
	 */
	protected static final boolean		VISIBLE_FOR_UNSET_CASE_STATE_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isVisibleForUnsetCaseState() <em>Visible For Unset Case State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleForUnsetCaseState()
	 * @generated
	 * @ordered
	 */
	protected boolean					visibleForUnsetCaseState				= VISIBLE_FOR_UNSET_CASE_STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCaseState() <em>Case State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseState()
	 * @generated
	 * @ordered
	 */
	protected EList<ExternalReference>	caseState;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VisibleForCaseStatesImpl()
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
		return XpdExtensionPackage.Literals.VISIBLE_FOR_CASE_STATES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisibleForUnsetCaseState()
	{
		return visibleForUnsetCaseState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibleForUnsetCaseState(boolean newVisibleForUnsetCaseState)
	{
		boolean oldVisibleForUnsetCaseState = visibleForUnsetCaseState;
		visibleForUnsetCaseState = newVisibleForUnsetCaseState;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE, oldVisibleForUnsetCaseState,
				visibleForUnsetCaseState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExternalReference> getCaseState()
	{
		if (caseState == null)
		{
			caseState = new EObjectContainmentEList<ExternalReference>(ExternalReference.class, this,
					XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE);
		}
		return caseState;
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
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE:
				return ((InternalEList< ? >) getCaseState()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE:
				return isVisibleForUnsetCaseState();
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE:
				return getCaseState();
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
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE:
				setVisibleForUnsetCaseState((Boolean) newValue);
				return;
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE:
				getCaseState().clear();
				getCaseState().addAll((Collection< ? extends ExternalReference>) newValue);
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
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE:
				setVisibleForUnsetCaseState(VISIBLE_FOR_UNSET_CASE_STATE_EDEFAULT);
				return;
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE:
				getCaseState().clear();
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
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__VISIBLE_FOR_UNSET_CASE_STATE:
				return visibleForUnsetCaseState != VISIBLE_FOR_UNSET_CASE_STATE_EDEFAULT;
			case XpdExtensionPackage.VISIBLE_FOR_CASE_STATES__CASE_STATE:
				return caseState != null && !caseState.isEmpty();
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
		result.append(" (visibleForUnsetCaseState: "); //$NON-NLS-1$
		result.append(visibleForUnsetCaseState);
		result.append(')');
		return result.toString();
	}

} //VisibleForCaseStatesImpl
