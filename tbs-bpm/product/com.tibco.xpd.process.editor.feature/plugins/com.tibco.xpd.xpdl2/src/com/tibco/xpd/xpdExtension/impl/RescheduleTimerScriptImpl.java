/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.impl.ExpressionImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reschedule Timer Script</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.RescheduleTimerScriptImpl#getDurationRelativeTo <em>Duration Relative To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RescheduleTimerScriptImpl extends ExpressionImpl implements RescheduleTimerScript
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright						= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getDurationRelativeTo() <em>Duration Relative To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDurationRelativeTo()
	 * @generated
	 * @ordered
	 */
	protected static final RescheduleDurationType	DURATION_RELATIVE_TO_EDEFAULT	= RescheduleDurationType.RESCHEDULE_TIME;

	/**
	 * The cached value of the '{@link #getDurationRelativeTo() <em>Duration Relative To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDurationRelativeTo()
	 * @generated
	 * @ordered
	 */
	protected RescheduleDurationType				durationRelativeTo				= DURATION_RELATIVE_TO_EDEFAULT;

	/**
	 * This is true if the Duration Relative To attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean								durationRelativeToESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RescheduleTimerScriptImpl()
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
		return XpdExtensionPackage.Literals.RESCHEDULE_TIMER_SCRIPT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RescheduleDurationType getDurationRelativeTo()
	{
		return durationRelativeTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDurationRelativeTo(RescheduleDurationType newDurationRelativeTo)
	{
		RescheduleDurationType oldDurationRelativeTo = durationRelativeTo;
		durationRelativeTo = newDurationRelativeTo == null ? DURATION_RELATIVE_TO_EDEFAULT : newDurationRelativeTo;
		boolean oldDurationRelativeToESet = durationRelativeToESet;
		durationRelativeToESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO, oldDurationRelativeTo,
				durationRelativeTo, !oldDurationRelativeToESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDurationRelativeTo()
	{
		RescheduleDurationType oldDurationRelativeTo = durationRelativeTo;
		boolean oldDurationRelativeToESet = durationRelativeToESet;
		durationRelativeTo = DURATION_RELATIVE_TO_EDEFAULT;
		durationRelativeToESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO, oldDurationRelativeTo,
				DURATION_RELATIVE_TO_EDEFAULT, oldDurationRelativeToESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDurationRelativeTo()
	{
		return durationRelativeToESet;
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
			case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO:
				return getDurationRelativeTo();
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
			case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO:
				setDurationRelativeTo((RescheduleDurationType) newValue);
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
			case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO:
				unsetDurationRelativeTo();
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
			case XpdExtensionPackage.RESCHEDULE_TIMER_SCRIPT__DURATION_RELATIVE_TO:
				return isSetDurationRelativeTo();
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
		result.append(" (durationRelativeTo: "); //$NON-NLS-1$
		if (durationRelativeToESet) result.append(durationRelativeTo);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //RescheduleTimerScriptImpl
