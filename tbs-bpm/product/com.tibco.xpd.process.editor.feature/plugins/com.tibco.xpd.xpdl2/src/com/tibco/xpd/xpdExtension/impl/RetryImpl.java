/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.MaxRetryActionType;
import com.tibco.xpd.xpdExtension.Retry;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Retry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RetryImpl#getMax <em>Max</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RetryImpl#getInitialPeriod <em>Initial Period</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RetryImpl#getPeriodIncrement <em>Period Increment</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RetryImpl#getMaxRetryAction <em>Max Retry Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RetryImpl extends EObjectImpl implements Retry
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String					copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected static final int					MAX_EDEFAULT				= 0;

	/**
	 * The cached value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected int								max							= MAX_EDEFAULT;

	/**
	 * This is true if the Max attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							maxESet;

	/**
	 * The default value of the '{@link #getInitialPeriod() <em>Initial Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPeriod()
	 * @generated
	 * @ordered
	 */
	protected static final int					INITIAL_PERIOD_EDEFAULT		= 0;

	/**
	 * The cached value of the '{@link #getInitialPeriod() <em>Initial Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPeriod()
	 * @generated
	 * @ordered
	 */
	protected int								initialPeriod				= INITIAL_PERIOD_EDEFAULT;

	/**
	 * This is true if the Initial Period attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							initialPeriodESet;

	/**
	 * The default value of the '{@link #getPeriodIncrement() <em>Period Increment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriodIncrement()
	 * @generated
	 * @ordered
	 */
	protected static final int					PERIOD_INCREMENT_EDEFAULT	= 0;

	/**
	 * The cached value of the '{@link #getPeriodIncrement() <em>Period Increment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPeriodIncrement()
	 * @generated
	 * @ordered
	 */
	protected int								periodIncrement				= PERIOD_INCREMENT_EDEFAULT;

	/**
	 * This is true if the Period Increment attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							periodIncrementESet;

	/**
	 * The default value of the '{@link #getMaxRetryAction() <em>Max Retry Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxRetryAction()
	 * @generated
	 * @ordered
	 */
	protected static final MaxRetryActionType	MAX_RETRY_ACTION_EDEFAULT	= MaxRetryActionType.HALT;

	/**
	 * The cached value of the '{@link #getMaxRetryAction() <em>Max Retry Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxRetryAction()
	 * @generated
	 * @ordered
	 */
	protected MaxRetryActionType				maxRetryAction				= MAX_RETRY_ACTION_EDEFAULT;

	/**
	 * This is true if the Max Retry Action attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean							maxRetryActionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RetryImpl()
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
		return XpdExtensionPackage.Literals.RETRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMax()
	{
		return max;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMax(int newMax)
	{
		int oldMax = max;
		max = newMax;
		boolean oldMaxESet = maxESet;
		maxESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.RETRY__MAX, oldMax, max, !oldMaxESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMax()
	{
		int oldMax = max;
		boolean oldMaxESet = maxESet;
		max = MAX_EDEFAULT;
		maxESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.RETRY__MAX, oldMax, MAX_EDEFAULT, oldMaxESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMax()
	{
		return maxESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getInitialPeriod()
	{
		return initialPeriod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialPeriod(int newInitialPeriod)
	{
		int oldInitialPeriod = initialPeriod;
		initialPeriod = newInitialPeriod;
		boolean oldInitialPeriodESet = initialPeriodESet;
		initialPeriodESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.RETRY__INITIAL_PERIOD, oldInitialPeriod, initialPeriod, !oldInitialPeriodESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInitialPeriod()
	{
		int oldInitialPeriod = initialPeriod;
		boolean oldInitialPeriodESet = initialPeriodESet;
		initialPeriod = INITIAL_PERIOD_EDEFAULT;
		initialPeriodESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.RETRY__INITIAL_PERIOD,
					oldInitialPeriod, INITIAL_PERIOD_EDEFAULT, oldInitialPeriodESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInitialPeriod()
	{
		return initialPeriodESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPeriodIncrement()
	{
		return periodIncrement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPeriodIncrement(int newPeriodIncrement)
	{
		int oldPeriodIncrement = periodIncrement;
		periodIncrement = newPeriodIncrement;
		boolean oldPeriodIncrementESet = periodIncrementESet;
		periodIncrementESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.RETRY__PERIOD_INCREMENT,
					oldPeriodIncrement, periodIncrement, !oldPeriodIncrementESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPeriodIncrement()
	{
		int oldPeriodIncrement = periodIncrement;
		boolean oldPeriodIncrementESet = periodIncrementESet;
		periodIncrement = PERIOD_INCREMENT_EDEFAULT;
		periodIncrementESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.RETRY__PERIOD_INCREMENT,
					oldPeriodIncrement, PERIOD_INCREMENT_EDEFAULT, oldPeriodIncrementESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPeriodIncrement()
	{
		return periodIncrementESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MaxRetryActionType getMaxRetryAction()
	{
		return maxRetryAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxRetryAction(MaxRetryActionType newMaxRetryAction)
	{
		MaxRetryActionType oldMaxRetryAction = maxRetryAction;
		maxRetryAction = newMaxRetryAction == null ? MAX_RETRY_ACTION_EDEFAULT : newMaxRetryAction;
		boolean oldMaxRetryActionESet = maxRetryActionESet;
		maxRetryActionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.RETRY__MAX_RETRY_ACTION,
					oldMaxRetryAction, maxRetryAction, !oldMaxRetryActionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMaxRetryAction()
	{
		MaxRetryActionType oldMaxRetryAction = maxRetryAction;
		boolean oldMaxRetryActionESet = maxRetryActionESet;
		maxRetryAction = MAX_RETRY_ACTION_EDEFAULT;
		maxRetryActionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.RETRY__MAX_RETRY_ACTION,
					oldMaxRetryAction, MAX_RETRY_ACTION_EDEFAULT, oldMaxRetryActionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMaxRetryAction()
	{
		return maxRetryActionESet;
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
			case XpdExtensionPackage.RETRY__MAX:
				return getMax();
			case XpdExtensionPackage.RETRY__INITIAL_PERIOD:
				return getInitialPeriod();
			case XpdExtensionPackage.RETRY__PERIOD_INCREMENT:
				return getPeriodIncrement();
			case XpdExtensionPackage.RETRY__MAX_RETRY_ACTION:
				return getMaxRetryAction();
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
			case XpdExtensionPackage.RETRY__MAX:
				setMax((Integer) newValue);
				return;
			case XpdExtensionPackage.RETRY__INITIAL_PERIOD:
				setInitialPeriod((Integer) newValue);
				return;
			case XpdExtensionPackage.RETRY__PERIOD_INCREMENT:
				setPeriodIncrement((Integer) newValue);
				return;
			case XpdExtensionPackage.RETRY__MAX_RETRY_ACTION:
				setMaxRetryAction((MaxRetryActionType) newValue);
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
			case XpdExtensionPackage.RETRY__MAX:
				unsetMax();
				return;
			case XpdExtensionPackage.RETRY__INITIAL_PERIOD:
				unsetInitialPeriod();
				return;
			case XpdExtensionPackage.RETRY__PERIOD_INCREMENT:
				unsetPeriodIncrement();
				return;
			case XpdExtensionPackage.RETRY__MAX_RETRY_ACTION:
				unsetMaxRetryAction();
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
			case XpdExtensionPackage.RETRY__MAX:
				return isSetMax();
			case XpdExtensionPackage.RETRY__INITIAL_PERIOD:
				return isSetInitialPeriod();
			case XpdExtensionPackage.RETRY__PERIOD_INCREMENT:
				return isSetPeriodIncrement();
			case XpdExtensionPackage.RETRY__MAX_RETRY_ACTION:
				return isSetMaxRetryAction();
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
		result.append(" (max: "); //$NON-NLS-1$
		if (maxESet) result.append(max);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", initialPeriod: "); //$NON-NLS-1$
		if (initialPeriodESet) result.append(initialPeriod);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", periodIncrement: "); //$NON-NLS-1$
		if (periodIncrementESet) result.append(periodIncrement);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", maxRetryAction: "); //$NON-NLS-1$
		if (maxRetryActionESet) result.append(maxRetryAction);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //RetryImpl
