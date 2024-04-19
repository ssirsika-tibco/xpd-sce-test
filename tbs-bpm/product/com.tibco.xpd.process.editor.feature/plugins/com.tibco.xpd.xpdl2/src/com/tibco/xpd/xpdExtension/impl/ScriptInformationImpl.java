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

import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.impl.NamedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script Information</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ScriptInformationImpl#isReference <em>Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScriptInformationImpl extends NamedElementImpl implements ScriptInformation
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright			= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression					expression;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final DirectionType	DIRECTION_EDEFAULT	= DirectionType.IN_LITERAL;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected DirectionType					direction			= DIRECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected Activity						activity;

	/**
	 * The default value of the '{@link #isReference() <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReference()
	 * @generated
	 * @ordered
	 */
	protected static final boolean			REFERENCE_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isReference() <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReference()
	 * @generated
	 * @ordered
	 */
	protected boolean						reference			= REFERENCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScriptInformationImpl()
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
		return XpdExtensionPackage.Literals.SCRIPT_INFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExpression()
	{
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs)
	{
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION, oldExpression, newExpression);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(Expression newExpression)
	{
		if (newExpression != expression)
		{
			NotificationChain msgs = null;
			if (expression != null) msgs = ((InternalEObject) expression).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION, null, msgs);
			if (newExpression != null) msgs = ((InternalEObject) newExpression).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectionType getDirection()
	{
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(DirectionType newDirection)
	{
		DirectionType oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_INFORMATION__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity()
	{
		if (activity != null && activity.eIsProxy())
		{
			InternalEObject oldActivity = (InternalEObject) activity;
			activity = (Activity) eResolveProxy(oldActivity);
			if (activity != oldActivity)
			{
				if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY, oldActivity, activity));
			}
		}
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity basicGetActivity()
	{
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity)
	{
		Activity oldActivity = activity;
		activity = newActivity;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY, oldActivity, activity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReference()
	{
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReference(boolean newReference)
	{
		boolean oldReference = reference;
		reference = newReference;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.SCRIPT_INFORMATION__REFERENCE, oldReference, reference));
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
			case XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION:
				return basicSetExpression(null, msgs);
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
			case XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION:
				return getExpression();
			case XpdExtensionPackage.SCRIPT_INFORMATION__DIRECTION:
				return getDirection();
			case XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY:
				if (resolve) return getActivity();
				return basicGetActivity();
			case XpdExtensionPackage.SCRIPT_INFORMATION__REFERENCE:
				return isReference();
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
			case XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION:
				setExpression((Expression) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__DIRECTION:
				setDirection((DirectionType) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY:
				setActivity((Activity) newValue);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__REFERENCE:
				setReference((Boolean) newValue);
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
			case XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION:
				setExpression((Expression) null);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY:
				setActivity((Activity) null);
				return;
			case XpdExtensionPackage.SCRIPT_INFORMATION__REFERENCE:
				setReference(REFERENCE_EDEFAULT);
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
			case XpdExtensionPackage.SCRIPT_INFORMATION__EXPRESSION:
				return expression != null;
			case XpdExtensionPackage.SCRIPT_INFORMATION__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case XpdExtensionPackage.SCRIPT_INFORMATION__ACTIVITY:
				return activity != null;
			case XpdExtensionPackage.SCRIPT_INFORMATION__REFERENCE:
				return reference != REFERENCE_EDEFAULT;
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
		result.append(" (direction: "); //$NON-NLS-1$
		result.append(direction);
		result.append(", reference: "); //$NON-NLS-1$
		result.append(reference);
		result.append(')');
		return result.toString();
	}

} //ScriptInformationImpl